package com.example.gtr.fastapplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于方便将long类型的日期转换为String类型
 */

public class DateFormatter {

    public String ZhihuDailyDateFormat(long date){
        String sDate;
        Date d = new Date(date + 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        sDate = format.format(d);
        return sDate;
    }

    public String DoubanDateFormat(long date){
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        sDate = simpleDateFormat.format(d);
        return sDate;
    }
}
