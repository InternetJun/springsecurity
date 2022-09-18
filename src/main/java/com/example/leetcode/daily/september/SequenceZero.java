package com.example.leetcode.daily.september;

import com.example.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SequenceZero {

    /**
     * 01构成的字符串，找出如下的
     * 1）000000000000001
     * 1）00000000000000000
     * 2）00000000000000001
     * 2）11111111111111
     * <p>
     * <p>
     * 111100001
     * <p>
     * * @+：1~n
     * * @*：0~n
     * * @?：0或者1的数据
     * 01和00001的区别是什么呢？
     *
     *
     * </p>
     * <h1>暂停：故障+维修</h1>
     * 那1--》0的时间点呢？
     *
     * @param s
     * @return
     */
    public void getSuccessiveSequence(String s) {
        ArrayList<int[]> resultNormal = new ArrayList<>();
        ArrayList<int[]> resultBad = new ArrayList<>();
        String allOneRegex = "[1]+[0]";
        String allZeroRegex = "[0]+[1]";
        Pattern compileOne = Pattern.compile(allOneRegex);
        Pattern compileZero = Pattern.compile(allZeroRegex);
        Matcher matcher = compileOne.matcher(s);
        Matcher matcher1 = compileZero.matcher(s);
        while (matcher.find() ) {
            int start = matcher.start();
            int end = matcher.end()-1;

            System.out.printf("%s:%s-%s\n",matcher.group(), start, end);
        }

        while(matcher1.find()) {
            int start = matcher1.start();
            int end = matcher1.end()-1;
            System.out.printf("%s:%s-%s\n",matcher1.group(), start, end);
        }
    }

    public void transMap(Map<Long, Integer> map) {
        StringBuilder stringBuffer = new StringBuilder();
        map.values().forEach(stringBuffer::append);
        String status = stringBuffer.toString();
        List collect = new ArrayList(map.keySet());
        log.info("{}",status);
        log.info("{}",collect);


    }

    @Test
    public void init() {
        Map<Long, Integer> generate = generate();
        transMap(generate);

    }

    @Test
    public void testTime() {
        long[] longs = {1663509976699L, 1663510276698L, 1663510576699L, 1663509676699L};
        for (long aLong : longs) {
            System.out.println(DateUtil.long2Time(aLong));
        }

    }

    public Map<Long, Integer> generate() {
        Map<Long, Integer> map = new HashMap<>();
        Random random = new Random();
        // 每5分钟处理时间了。
        map.put(DateUtil.getLongTime(LocalDateTime.now().plusMinutes(-5)), random.nextInt(2));
        map.put(DateUtil.getLongTime(LocalDateTime.now().plusMinutes(-10)), random.nextInt(2));
        map.put(DateUtil.getLongTime(LocalDateTime.now().plusMinutes(-15)), random.nextInt(2));
        map.put(System.currentTimeMillis(), random.nextInt(2));
        return map;
    }

    @Test
    public void test() {
        String s = "11110100010001";
        getSuccessiveSequence(s);
        LocalDateTime now = LocalDateTime.now();
        long l = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("{}",l);
        long l1 = System.currentTimeMillis();
        log.info("{}",l1);

    }


}