package com.immymemine.kevin.skillshare.model.user;

import java.security.acl.Group;

/**
 * Created by quf93 on 2017-11-29.
 */

public class User
{
    private SubscribeClass[] subscribeClass;

    private Discussion[] discussion;

    private String registrationId;

    private String nickname;

    private String password;

    private Project[] project;

    private Following[] following;

    private Followers[] followers;

    private String pictureUrl;

    private String email;

    public SubscribeClass[] getSubscribeClass() {
        return subscribeClass;
    }

    public void setSubscribeClass(SubscribeClass[] subscribeClass) {
        this.subscribeClass = subscribeClass;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;

    private String name;

    private String[] followingSkills;

    private Group[] group;

    public SubscribeClass[] getSubsribeClass ()
    {
        return subscribeClass;
    }

    public void setSubsribeClass (SubscribeClass[] subscribeClass)
    {
        this.subscribeClass = subscribeClass;
    }

    public Discussion[] getDiscussion ()
    {
        return discussion;
    }

    public void setDiscussion (Discussion[] discussion)
    {
        this.discussion = discussion;
    }

    public String getRegistrationId ()
    {
        return registrationId;
    }

    public void setRegistrationId (String registrationId)
    {
        this.registrationId = registrationId;
    }

    public String getNickname ()
    {
        return nickname;
    }

    public void setNickname (String nickname)
    {
        this.nickname = nickname;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public Project[] getProject ()
    {
        return project;
    }

    public void setProject (Project[] project)
    {
        this.project = project;
    }

    public Following[] getFollowing ()
    {
        return following;
    }

    public void setFollowing (Following[] following)
    {
        this.following = following;
    }

    public Followers[] getFollowers ()
    {
        return followers;
    }

    public void setFollowers (Followers[] followers)
    {
        this.followers = followers;
    }

    public String getPictureUrl ()
    {
        return pictureUrl;
    }

    public void setPictureUrl (String pictureUrl)
    {
        this.pictureUrl = pictureUrl;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String[] getFollowingSkills ()
    {
        return followingSkills;
    }

    public void setFollowingSkills (String[] followingSkills)
    {
        this.followingSkills = followingSkills;
    }

    public Group[] getGroup ()
    {
        return group;
    }

    public void setGroup (Group[] group)
    {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subsribeClass = "+subscribeClass+", discussion = "+discussion+", registrationId = "+registrationId+", nickname = "+nickname+", password = "+password+", project = "+project+", following = "+following+", followers = "+followers+", pictureUrl = "+pictureUrl+", email = "+email+", userId = "+_id+", name = "+name+", followingSkills = "+followingSkills+", group = "+group+"]";
    }
}
