package com.immymemine.kevin.skillshare.utility.eventbusLibrary;


/**
 * Created by JisangYou on 2018-02-06.
 */

public class BusProvider {
    private static final com.squareup.otto.Bus BUS = new com.squareup.otto.Bus();

    public static com.squareup.otto.Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

}
