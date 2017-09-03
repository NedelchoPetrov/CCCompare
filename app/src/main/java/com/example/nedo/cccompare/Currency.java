package com.example.nedo.cccompare;

/**
 * Created by Nedo on 26/08/2017.
 */

public class Currency {

    //TODO: Maybe change attributes visibility to private.
    public String name;
    public String icon;
    public boolean priceUp;
    public double currentPrice;

    public Currency(String name, String icon, boolean priceUp, double currentPrice){
        this.name = name;
        this.icon = icon;
        this.priceUp = priceUp;
        this.currentPrice = currentPrice;
    }
}
