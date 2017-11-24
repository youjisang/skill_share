package com.immymemine.kevin.skillshare.sampleModel;

/**
 * Created by JisangYou on 2017-11-24.
 */

public class ProjectSeeAllModel {

    String seeAllProjectImage;
    String heartNum;
    String seeAllContentName;
    String seeAllAuthor;


    public ProjectSeeAllModel(){

    }

    public ProjectSeeAllModel(String seeAllProjectImage, String heartNum, String seeAllContentName, String seeAllAuthor) {
        this.seeAllProjectImage = seeAllProjectImage;
        this.heartNum = heartNum;
        this.seeAllContentName = seeAllContentName;
        this.seeAllAuthor = seeAllAuthor;
    }
}
