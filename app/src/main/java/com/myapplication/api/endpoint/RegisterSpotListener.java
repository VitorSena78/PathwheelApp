package com.myapplication.api.endpoint;

import com.myapplication.api.response.RegisterSpotResponse;

public interface RegisterSpotListener {
    void onRegisterSpot(RegisterSpotResponse response);
}
