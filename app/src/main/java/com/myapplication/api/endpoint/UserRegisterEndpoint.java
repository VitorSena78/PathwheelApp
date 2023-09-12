package com.myapplication.api.endpoint;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.myapplication.api.request.RegisterUserRequest;
import com.myapplication.api.response.Response;

/**
 * Created by João Vitor on 30/05/23.
 */

public class UserRegisterEndpoint extends PathwheelApiClient{
    private RegisterUserListener registerUserListener;

    public void register(RegisterUserRequest registerUserRequest, RegisterUserListener registerUserListener) {
        this.registerUserListener = registerUserListener;
        new RegisterSampleAsyncTask().execute(new Gson().toJson(registerUserRequest));
    }

    private class RegisterSampleAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return doPost("/v1/user/register", params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return new Gson().toJson(new Response(500, e.getMessage()));
            }
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                Response response = new Gson().fromJson(s, Response.class);
                registerUserListener.onRegisterUserListener(response);
            } catch (Exception e) {
                e.printStackTrace();
                registerUserListener.onRegisterUserListener(new Response(500, e.getMessage()));
            }
        }
    }
}
