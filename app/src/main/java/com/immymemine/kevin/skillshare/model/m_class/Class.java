package com.immymemine.kevin.skillshare.model.m_class;

import java.util.List;

/**
 * Created by quf93 on 2017-12-02.
 */

public class Class {
    String _id;
    String title;
    String imageUrl;
    String tutorName;
    String totalDuration;

    List<Discussion> discussions;
    Lessons lessons;
    About about;
}
