package com.immymemine.kevin.skillshare.network.api;

import com.immymemine.kevin.skillshare.model.discover.DiscoverClass;
import com.immymemine.kevin.skillshare.model.group.Group;
import com.immymemine.kevin.skillshare.model.home.Class;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by quf93 on 2017-12-05.
 */

public interface HomeService {
    @GET("home")
    Observable<List<Map<String, List<Class>>>> getHomeClasses(@Query("types") List<String> types);

    @GET("group")
    Observable<Map<String, List<Group>>> getGroups();

    @GET("discover")
    Observable<DiscoverClass> getDiscoverClass();
}