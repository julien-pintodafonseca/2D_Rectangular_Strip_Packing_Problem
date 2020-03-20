package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 */
public class Plate extends Rectangle {
    private int hRest;

    public Plate(int _h, int _w) {
        super(_h, _w);
        this.hRest = _h;
    }

    public int getHRest() {
        return this.hRest;
    }

    public void setHRest(int _hRest) {
        this.hRest = _hRest;
    }
}
