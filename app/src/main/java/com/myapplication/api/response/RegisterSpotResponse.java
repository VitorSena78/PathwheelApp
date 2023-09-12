package com.myapplication.api.response;

import com.myapplication.api.model.Spot;

public class RegisterSpotResponse extends Response {
    private Spot spot;

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    @Override
    public String toString() {
        return "RegisterSpotResponse [spot=" + spot + "]";
    }

}