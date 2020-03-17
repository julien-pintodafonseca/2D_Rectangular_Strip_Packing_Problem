package com.ensimag.Models;

/**
 * Created by houdee on 2/17/20.
 */
public class Rectangle implements Comparable<Rectangle> {
    private int h; // height
    private int w; // width

    public Rectangle(int _h, int _w) {
        this.h = _h;
        this.w = _w;
    }

    public int getH() {
        return this.h;
    }

    public int getW() {
        return this.w;
    }

    @Override
    public int compareTo(Rectangle o) {
        return (o.getH()*o.getW()) - (this.h*this.w);
    }
}
