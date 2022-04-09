//package com.example.leetcode.dataSync;
//
//import java.io.Serializable;
//import java.util.List;
//public class Sync {
//
//    /**
//     * Created by Administrator on 2017/4/19.
//     * 这是基于是单线程来执行同步 千万不允许多线程执行 多线程执行太难写了 放弃吧！！！！！
//     * 这里我们考虑有时间因素、以及mysql mvcc非锁定读的因素。
//     * 时间因素我们这样解决：1、以主服务器的时间为准。而非本地时间。本地时间快与慢不会影响同步功能
//     * 2、主服务器可以调整它的时间，可以向前（改小时间）或向后（改大时间）调整，程序都是支持的。
//     * 假设我们的同步时间是1小时同步一次，事物最大超时时间是5分钟（就是没有事物可以超过5分钟）。假设当前主服务器时间
//     * 是: 08:00 , 那么上次同步时间是：06:55
//     * 2.1、向前调整：假设向前调整5小时，那么是 03:00 , 当我们同步的时候，获取主服务器的时间是 X ( 03:00 <= X <= 04:00) 因为我们的同步时间间隔是1小时
//     * 假设X=03:56 , 那么min(06:55+01:00,03:56-01:00-00:05)=02:51 那么我们此次的同步时间将会是：02:51，那么是正确同步时间。
//     * 下次认为的上次同步时间将会是：02:51
//     * 2.2、向后调整：假设向后调整5小时，那么是13:00， 当我们同步的时候，获取主服务器的时间是：Y (13:00 <= Y <= 14:00) 同理。
//     * 假设Y=13:56，那么min(06:55+01:00,13:56-01:00-00:05)=07:55 那么我们此次的同步时间将会是：07:55，那么是正确同步时间。
//     * 下次认为的上次同步时间将会是：13:56-01:00-00:05 = 12:51 这个时候时间就一下进步了很多了（跟上时间的步伐）。
//     *
//     * <T>是id类型
//     */
//
//    public abstract class BaseSyncUserInfoTaskImpl<T extends Serializable> implements ISyncUserInfoTask{
//
//        //同步时间间隔 可以稍微大点（比真实在定时任务的执行中的间隔大，但是千万别小于他，等于定时任务执行间隔最好）
//        private int sync_time_interval_in_milsecond = 10*60*1000;
//
//        //事物处理最长时间 建议同步时间间隔大于此时间
//        private int tx_time_out_in_milsecond = 5*60*1000;
//
//        //上次同步时间
//        private Long up_sync_time = null;
//        //本次同步时间
//        private Long this_sync_time = null;
//
//        /**
//         * 执行总体架构
//         */
//        @Override
//        public final void sync() {
//
////获取同步时间 与主服务器商定同步时间
//            long nowSyncTime = getSyncTime();
//
////开始数据同步
//            syncDatas(nowSyncTime);
//
////同步数据仅仅解决更新与插入的问题 这里去解决删除的问题
////有些表不会存在删除操作，这里对那些不需要删除的表直接跳过
//            if(isNeedDel)
//                syncDel();
//
////这个放到最后 怕事物回滚 而时间没有被回滚 导致下次同步时，up_sync_time不正确
//            updateUpSyncTime();
//        }
//
//        //加synchronized 仅仅是怕jvm优化 导致语句重排 一定要在最后来更新这个时间
//        protected synchronized void updateUpSyncTime(){
//            up_sync_time = this_sync_time;
//        }
//
//
//        private void syncDel() {
//
////1、本地取全部id集合的摘要 MD5，以及记录数 拿去远程比较，相等则啥都不做
//            CommonKVQuery<String,Integer> abstractAndCount = getLocalIdsAbstractAndCount();
//            boolean isMach = isMachMasterServer(abstractAndCount);
//            if(isMach)
//                return ;
////2、把本地的数据按照id进行分页拿到远程去对比，没有则拿回来进行删除 。
//            DefaultPageRequest pageRequest = new DefaultPageRequest();
//            pageRequest.setLimit(100);
//            pageRequest.setPage(1);
//            List<T> ids = null;
//            List<T> delIds = null;
//            do {
//                ids = listLocalIds(pageRequest);
//                delIds = getNeedDelIdsFromMasterServer(ids);
//                deleteLocalByIds(delIds);
//                pageRequest.setPage(pageRequest.getPage()+1);
//            } while (ids.size() == pageRequest.getLimit());
//        }
//
//        /**
//         * 删除本地的数据 通过id集合
//         * @param delIds
//         */
//        protected abstract void deleteLocalByIds(List<T> delIds);
//
//        /**
//         * 去远处匹配 找出需要删除的id集合
//         * @param ids
//         * @return
//         */
//        protected abstract List<T> getNeedDelIdsFromMasterServer(List ids);
//
//        //分页获取本地id集合
//        protected abstract List<T> listLocalIds(DefaultPageRequest pageRequest);
//
//        //去主服务器匹配摘要及记录数
//        protected abstract boolean isMachMasterServer(CommonKVQuery<String,Integer> abstractAndCount);
//
//        //获取本地id集合摘要及记录数量
//        protected abstract CommonKVQuery<String,Integer> getLocalIdsAbstractAndCount() ;
//
//        //同步数据 更新时间在指定的时间之后的数据进行更新
//        protected abstract void syncDatas(long nowSyncTime) ;
//
//        private long getSyncTime(){
//
//            final long masterServerTime = getMasterServiceTime();
//            if(up_sync_time == null){
//
//                up_sync_time = getLocalMaxUpdateTime() - sync_time_interval_in_milsecond;
//
////若上次跟新时间为null，表示系统重启，进行第一次同步，那么来一次全量同步
//// this_sync_time = masterServerTime - sync_time_interval_in_milsecond-tx_time_out_in_milsecond;
//// return 0l;
//
//            }
//
//// min(主服务器时间 - 同步时间间隔（1小时） - 最大事物超时时间（5分钟）,上次商定的时间 + 同步时间间隔)
//// 这里的5分钟我考虑的是最大事物的用时。就是假定所有事物的时间长度不可以超过5分钟。
//// 因为我们在程序中经常是先设置更新时间，然后插入数据库，然后再做些别的（浪费了一些时间），
//// 最后提交了事物。那么根据mvcc模式，非锁定读，是读快照。导致更新时间本应该在本次同步中被同步的，而并没有同步到
////（不可见），而下一次的同步时间又大于了这个更新时间。导致会丢失更新。所以每次同步，都多同步5分钟的数据。
//// 就怕丢下这种间隙中的数据。
//            this_sync_time = Math.min(up_sync_time + sync_time_interval_in_milsecond,
//                    masterServerTime-sync_time_interval_in_milsecond-tx_time_out_in_milsecond) ;
//            final long result = Math.max(0,this_sync_time);
////这里的这一次同步时间取值是 主服务器时间-同步时间间隔-事物最大超时时间
////而舍弃了up_sync_time + sync_time_interval_in_milsecond 这个取值，原因在于让下一次的更新跟上主服务器的时间，不要距离太远
//            this_sync_time = masterServerTime - sync_time_interval_in_milsecond-tx_time_out_in_milsecond;
//            return result;
//        }
//
//        /**
//         * 获取本地记录中的最大更新时间
//         * @return
//         */
//        protected abstract long getLocalMaxUpdateTime() ;
//
//        //获取主服务器的当前时间
//        protected abstract long getMasterServiceTime();
//
//        /**
//         * 表数据是否需要删除操作，不会删除，则可以减少去同步被删的数据
//         */
//        private boolean isNeedDel = false;
//
//        public void setSync_time_interval_in_milsecond(int sync_time_interval_in_milsecond) {
//            this.sync_time_interval_in_milsecond = sync_time_interval_in_milsecond;
//        }
//
//        public void setTx_time_out_in_milsecond(int tx_time_out_in_milsecond) {
//            this.tx_time_out_in_milsecond = tx_time_out_in_milsecond;
//        }
//
//        public void setNeedDel(boolean needDel) {
//            isNeedDel = needDel;
//        }
//    }
//}
