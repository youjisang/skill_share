package com.immymemine.kevin.skillshare.utility;

/**
 * Created by quf93 on 2017-12-11.
 */

public class TimeUtil {

    public static String calculateTime(String timeString) {
        long time = System.currentTimeMillis() - Long.parseLong(timeString);

        if(time < 60000) // 1분 이내면
            return "Just now";
        else if(time >= 60000 && time < 120000) // 1분 이상 2분 미만
            return "1 minute ago";
        else if(time >= 120000 && time < 3600000) // 2분 이상 1시간 미만
            return (time / 60000) + " minutes ago";
        else if(time >= 3600000 && time < 7200000) // 1시간 이상 2시간 미만
            return "An hour ago";
        else if(time >= 7200000 && time < 86400000) // 2시간 이상 24시간 미만
            return (time / 3600000) + " hours ago";
        else if(time >= 86400000 && time < 172800000) // 1일 이상 2일 미만
            return "A day ago";
        else if(time >= 172800000 && time < 604800000) // 2일 이상 7일 미만
            return (time / 86400000) + " days ago";
        else if(time >= 604800000 && time < 1209600000) // 1주 이상 2주 미만
            return "Last week";
        else if(time >= 1209600000 && time < 2419200000L) // 2주 이상 1달 미만
            return (time / 604800000) + " weeks ago";
        else if(time >= 2419200000L && time < 4838400000L) // 1달 이상 2달 미만
            return "A month ago";
        else if(time >= 4838400000L && time < 29030400000L) // 2달 이상 1년 미만
            return (time / 2419200000L) + " months ago";
        else if(time >= 29030400000L && time < 58060800000L) // 1년 이상 2년 미만
            return "Last year";
        else if(time >= 58060800000L) // 2년 이상
            return (time / 29030400000L) + " years ago";

        return "time error";
    }

    public static String calculateVideoTime(String timeString) {
        long time = Long.parseLong(timeString);

        if(time >= 60000 && time < 3600000) // 1분 이상 1시간 미만
            return (time / 60000) + "m";
        else if(time >= 3600000) {// 1시간 이상
            int hour = (int) (time / 3600000);
            time = time - (hour * 3600000);
            int minute = (int) (time / 60000);
            return hour + "h " + minute + "m";
        }

        return "time error";
    }

    public static String calculateVideoTimeByColon(String timeString) {
        long time = Long.parseLong(timeString);

        int minutes = (int)(time / 60000);
        int seconds = (int)((time % 60000) / 1000);

        return minutes + ":" + seconds;
    }
}
