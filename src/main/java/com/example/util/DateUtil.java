package com.example.util;


import org.junit.Test;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * @@{一般有基本的格式，eg：2022-03-06 11:07:25}
     * @hh;HH------------HH是24小时制，hh是12小时制 mm与m等，它们的区别为是否有前导零：H,m,s表示非零开始，HH,mm,ss表示从零开始。
     * 比如凌晨1点2分，HH:mm显示为01:02，H:m显示为1:2。
     * 和oracle是不一样的。
     */

    public static String localDateTime2Str(LocalDateTime localDateTime) {
        String s = null;
        if (localDateTime != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD_HH_MM_SS);
            s = df.format(localDateTime);
        }
        return s;
    }

    public static String localDate2Str(LocalDate localDate) {
        String s = null;
        if (localDate != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(DateUtil.YYYY_MM_DD);
            s = df.format(localDate);
        }
        return s;
    }

    public static LocalDateTime str2LocalDateTime(String s) {
        if (!StringUtil.isEmpty(s)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
            LocalDateTime time = LocalDateTime.parse(s, fmt);
            return time;
        }
        return LocalDateTime.now();
    }

    public static LocalDate str2LocalDate(String s) {
        if (!StringUtil.isEmpty(s)) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern(YYYY_MM_DD);
            LocalDate date = LocalDate.parse(s, fmt);
            return date;
        }
        return LocalDate.now();
    }

    /**
     * time to the long
     *
     * @param localDateTime
     * @return
     */
    public static Long getLongTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            LocalDateTime.now()
                    .atZone(ZoneId.systemDefault())
                    .toInstant().toEpochMilli();
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LONG 2 正常的时间。
     *
     * @param l
     * @return
     */
    public static LocalDateTime long2Time(long l) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault());
    }

    /**
     *2个时间戳相差秒数
     *
     * @param s
     * @param e
     * @return
     */
    public long gapSec(long s, long e) {
        //获取两个时间相差的分钟
        return (e-s)/1000;
    }

//    public static long hoursGap(LocalDateTime pre, LocalDateTime current) {
//
//    }

    public static void main(String[] args) {
//        LocalDateTime now = LocalDateTime.now();
//        String s = localDateTime2Str(now);
//        System.out.println(s);

        LocalDate date = LocalDate.now();
        String s1 = localDate2Str(date);
        System.out.println(s1);
    }

    @Test
    public void min() {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("res", "jy11,jy12");
        }};
        HashMap<String, String> hashMap2 = new HashMap<String, String>() {{
            put("res", "jy11,jy13");
        }};
        List<HashMap<String, String>> result = Arrays.asList(hashMap, hashMap2);
        List<String> list = new ArrayList<>();
        String res = result.stream().map(m -> m.get("res")).collect(Collectors.joining(","));
        Stream<String> stream = Arrays.asList(res.split(","))
                .stream();
        Stream<String> distinct = stream.distinct();
        list = distinct.collect(Collectors.toList());
        System.out.println(list.toString());
    }
}
