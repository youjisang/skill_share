package com.immymemine.kevin.skillshare.model.user;

import java.security.acl.Group;
import java.util.List;

/**
 * Created by quf93 on 2017-11-29.
 */

public class User
{
    private List<SubscribeClass> subscribeClass;

    private List<Discussion> discussion;

    private String registrationId;

    private String nickname;

    private String password;

    private List<Project> project;

    private List<Following> following;

    private List<Followers> followers;

    private String imageUrl;

    private String email;

        public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;

    private String name;

    private List<String> followingSkills;

    private List<Group> group;

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

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
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

    public List<SubscribeClass> getSubscribeClass() {
        return subscribeClass;
    }

    public void setSubscribeClass(List<SubscribeClass> subscribeClass) {
        this.subscribeClass = subscribeClass;
    }

    public List<Discussion> getDiscussion() {
        return discussion;
    }

    public void setDiscussion(List<Discussion> discussion) {
        this.discussion = discussion;
    }

    public List<Project> getProject() {
        return project;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public List<Followers> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Followers> followers) {
        this.followers = followers;
    }

    public List<String> getFollowingSkills() {
        return followingSkills;
    }

    public void setFollowingSkills(List<String> followingSkills) {
        this.followingSkills = followingSkills;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subsribeClass = "+subscribeClass+", discussion = "+discussion+", registrationId = "+registrationId+", nickname = "+nickname+", password = "+password+", project = "+project+", following = "+following+", followers = "+followers+", imageUrl = "+imageUrl+", email = "+email+", userId = "+_id+", name = "+name+", followingSkills = "+followingSkills+", group = "+group+"]";
    }
}
