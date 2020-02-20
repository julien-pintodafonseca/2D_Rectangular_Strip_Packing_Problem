package com.ensimag.Models;

/**
 * Created by solokwal on 2/20/20.
 */
public class PlateWithCoords extends Plate{
    private int x;
    private int y;

    public PlateWithCoords(Plate _plate,int _x, int _y) {
        super(_plate.getH(), _plate.getW());
        this.x = _x;
        this.y = _y;
    }
}
