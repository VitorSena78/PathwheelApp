package com.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.myapplication.R;
import com.myapplication.api.endpoint.AuthenticateUserListener;
import com.myapplication.api.endpoint.UserAuthenticateEndpoint;
import com.myapplication.api.request.AuthenticateRequest;
import com.myapplication.api.response.AuthenticateResponse;
import com.myapplication.service.PathwheelPreferences;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_MILIS = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_splash);

        setContentView(R.layout.activity_splash);

        
        if(PathwheelPreferences.getLastKnownLocation(getApplicationContext()) == null) {
            //PathwheelMemory.setLastKnownLocation(getApplicationContext(), new LatLng(-12.9016241,-38.4198075));
            PathwheelPreferences.setLastKnownLocation(getApplicationContext(), new LatLng(-13.002433, -38.451021));
        }

        AuthenticateRequest authenticateRequest = PathwheelPreferences.getAuthenticateRequest(getApplicationContext());

        if(authenticateRequest == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }, SPLASH_MILIS);
        }
        else {
            final UserAuthenticateEndpoint userAuthenticateEndpoint = new UserAuthenticateEndpoint();
            userAuthenticateEndpoint.authenticate(authenticateRequest, new AuthenticateUserListener() {
                @Override
                public void onAuthenticateUser(AuthenticateResponse response) {
                    if (response.getCode() == 200) {
                        PathwheelPreferences.setUser(getApplicationContext(),response.getUser());
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            });
        }

    }
}
