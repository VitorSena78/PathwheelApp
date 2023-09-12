package com.myapplication.api.endpoint;

import com.myapplication.api.response.AuthenticateResponse;

/**
 * Created by leonardo on 11/04/19.
 */

public interface AuthenticateUserListener {
    void onAuthenticateUser(AuthenticateResponse response);
}
