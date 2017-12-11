package com.immymemine.kevin.skillshare.utility;

/**
 * Created by quf93 on 2017-12-11.
 */

public class TimeUtil {

    public static String calculateTime(long time) {

        if(time < 60000) // 1분 이내면
            return "now";
        else if(time >= 60000 && time < 120000)
            return "1 minute ago";
        else if(time >= 120000 && time < 3600000) // 2분 이상 1시간 미만
            return (time / 60000) + " minutes ago";
        else if(time >= 86400000 && time < 172800000) // 1시간 이상 2시간 미만
            return "An hour ago";
        else if(time >= 172800000 && time < 2073600000) // 2시간 이상 24시간 미만
            return (time / 86400000) + " hours ago";
        else if(time >= 2073600000 && time < 4147200000L) // 1일 이상 2일 미만
            return "A day ago";
        else if(time >= 4147200000L && time < 14515200000L) // 2일 이상 7일 미만
            return (time / 2073600000) + " days ago";
        else if(time >= 14515200000L && time < 29030400000L) // 1주 이상 2주 미만
            return "Last week";
        else if(time >= 29030400000L && time < 58060800000L) // 2주 이상 1달 미만
            return (time / 14515200000L) + " weeks ago";
        else if(time >= 58060800000L && time < 116121600000L) // 1달 이상 2달 미만
            return "A month ago";
        else if(time >= 116121600000L && time < 696729600000L) // 2달 이상 1년 미만
            return (time / 58060800000L) + " months ago";
        else if(time >= 696729600000L && time < 1393459200000L) // 1년 이상 2년 미만
            return "Last year";
        else if(time >= 1393459200000L) // 2년 이상
            return (time / 696729600000L) + " years ago";

        return "no time";
    }
}
