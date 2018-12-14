package com.nbxuanma.spsclient.utils;

import android.os.Bundle;

/**
 * Created by Airon on 2018/10/7.
 */

public class MyEventBus {

    public int tag;
    public int num;
    public int type;
    public double price;
    public String str;
    public Bundle bundle;
    public int position;

    public MyEventBus(int tag) {
        this.tag = tag;
    }

    public MyEventBus(int tag, int num) {
        this.tag = tag;
        this.num = num;
    }

    public MyEventBus(int tag, int num, int position) {
        this.tag = tag;
        this.num = num;
        this.position = position;
    }

    public MyEventBus(int tag, int num, int position, String str) {
        this.tag = tag;
        this.num = num;
        this.position = position;
        this.str = str;
    }

    public MyEventBus(int tag, String str) {
        this.tag = tag;
        this.str = str;
    }

    public MyEventBus(int tag, int type, double price) {
        this.tag = tag;
        this.type = type;
        this.price = price;
    }

    public MyEventBus(int tag, Bundle bundle) {
        this.tag = tag;
        this.bundle = bundle;
    }

}
