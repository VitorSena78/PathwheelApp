package com.myapplication.api.endpoint;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import com.myapplication.api.request.RegisterSampleRequest;
import com.myapplication.api.response.Response;

public class PavementSampleEndpoint extends PathwheelApiClient {

    private RegisterPavementSampleListener registerPavementSampleListener;

    public void register(RegisterSampleRequest request, RegisterPavementSampleListener registerPavementSampleListener) {
        this.registerPavementSampleListener = registerPavementSampleListener;
        Log.i("PavementSampleEndpoint", "register: "+request);
        new RegisterSampleAsyncTask().execute(new Gson().toJson(request));

    }

    private class RegisterSampleAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return doPost("/v1/pavement-sample/register", params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return new Gson().toJson(new Response(500, e.getMessage()));
            }
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                Response response = new Gson().fromJson(s, Response.class);
                registerPavementSampleListener.onRegisterPavementSampleListener(response);
            } catch (Exception e) {
                e.printStackTrace();
                registerPavementSampleListener.onRegisterPavementSampleListener(new Response(500, e.getMessage()));
            }
        }
    }
}
