package com.example.leetcode.basicJava;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;


import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class SeqGen {
    /**

     * 生成递增序列号-线程安全

     * @java version 8

     * @author cosmozhu

     * @mail zhuchao1103@gmail.com

     * @site https://www.cosmozhu.fun

     */

    private final static AtomicInteger ai = new AtomicInteger(0);

    /**

     * 生成格式为yyyyMMddHHmmssxxxxx数字型序列号

     * @param scale 1-10之间的整数

     * @return 序列号

     */

    public static String getSeq(int scale) {
        if (scale < 0 || scale > 10) {
            throw new IllegalArgumentException("scale应为[1-10]之间的整数。");

        }

        LocalDateTime now = LocalDateTime.now();

        StringBuilder dateTime = new StringBuilder(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

        ai.compareAndSet(Integer.MAX_VALUE, 0);

        int incrementAndGet = ai.incrementAndGet();

        StringBuilder str = new StringBuilder(String.format("%0"+scale+"d", incrementAndGet));

        return dateTime.append(str.substring(str.length()-scale, str.length())).toString();

    }

    public static void main(String[] args) {
        for(int i=0;i<4;i++) {
            System.out.println(SeqGen.getSeq(1));

        }

    }

    private static int totalCount = 0;
    private int customerID;
    public SeqGen(){
        ++totalCount;
        customerID = totalCount;
        System.out.println("增加一个");
    }
    public String getCustomerID() {
        DecimalFormat decimalFormat = new DecimalFormat("00000000");
        return decimalFormat.format(customerID);
    }

    @Test
    public void te() {
        SeqGen c1 = new SeqGen();
        System.out.println(c1.getCustomerID());

    }

//    public static string CreateID(string tableName, string fieldName, string beginChar)
//    {
//        SqlParameter[] parameters = {
//                new SqlParameter("@TableName", SqlDbType.VarChar, 20),
//                new SqlParameter("@FieldName", SqlDbType.VarChar, 20),
//                new SqlParameter("@BeginChar", SqlDbType.VarChar,10)};
//        parameters[0].Value = tableName;
//        parameters[1].Value = fieldName;
//        parameters[2].Value = beginChar;
//        return DbHelperSQL.GetSingleByPro("proGetCustomID", parameters).ToString();
//    }

}
