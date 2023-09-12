package com.myapplication.api.endpoint;

import com.myapplication.api.response.FetchSpotResponse;

/**
 * Created by leonardo on 24/07/19.
 */

public interface FetchSpotListener {
    void onFetchSpot(FetchSpotResponse response);
}
