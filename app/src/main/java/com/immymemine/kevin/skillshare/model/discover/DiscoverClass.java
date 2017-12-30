package com.immymemine.kevin.skillshare.model.discover;

import com.immymemine.kevin.skillshare.model.home.Class;

import java.util.List;
import java.util.Map;

/**
 * Created by quf93 on 2017-12-22.
 */

public class DiscoverClass {
    String _id;
    String title;
    String imageUrl;
    String duration;
    String reviewPercent;
    String subscriberCount;
    String tutorId;
    String tutorName;
    String tutorImageUrl;
    String followersCount;
    List<Class> featuredClasses;
    Map<String, List<Class>> classes;

    public String getTutorImageUrl() {
        return tutorImageUrl;
    }

    public Map<String, List<Class>> getClasses() {
        return classes;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDuration() {
        return duration;
    }

    public String getReviewPercent() {
        return reviewPercent;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getTutorName() {
        return tutorName;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public List<Class> getFeaturedClasses() {
        return featuredClasses;
    }
}
