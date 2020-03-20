package com.ensimag.Models;

/**
 * Created by solokwal on 2/20/20.
 */
public class PieceWithCoords extends Piece {
    private int x;
    private int y;

    public PieceWithCoords(Piece _piece, int _x, int _y) {
        super(_piece.getH(), _piece.getW());
        this.x = _x;
        this.y = _y;
    }

    public PieceWithCoords(Plate _plate, int _x, int _y) {
        super(_plate.getH(), _plate.getW());
        this.x = _x;
        this.y = _y;
    }

    public PieceWithCoords(int _h, int _w, int _x, int _y) {
        super(_h, _w);
        this.x = _x;
        this.y = _y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int _y) {
        this.y = _y;
    }
}
