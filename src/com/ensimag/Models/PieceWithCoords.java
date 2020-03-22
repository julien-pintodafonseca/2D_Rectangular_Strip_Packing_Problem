package com.ensimag.Models;

/**
 * Created by solokwal on 2/20/20.
 * Objet qui hérite de la classe Piece.
 * Une pièce avec des coordonnées
 */
public class PieceWithCoords extends Piece {
    private int x;
    private int y;

    /**
     * Contructeur
     * @param _piece : l'objet pièce
     * @param _x : coordonnée x de l'objet
     * @param _y : coordonnée y de l'objet
     */
    public PieceWithCoords(Piece _piece, int _x, int _y) {
        super(_piece.getH(), _piece.getW());
        this.x = _x;
        this.y = _y;
    }

    /**
     * Construtueur
     * @param _plate : l'objet plaque
     * @param _x : coordonnée x de la plaque
     * @param _y : coordonnée y de la plaque
     */
    public PieceWithCoords(Plate _plate, int _x, int _y) {
        super(_plate.getH(), _plate.getW());
        this.x = _x;
        this.y = _y;
    }

    /**
     * Constructeur
     * @param _h : hauteur e la pièce
     * @param _w : largeur de la pièce
     * @param _x : coordonnée x de la pièce
     * @param _y : coordonnée y de la pièce
     */
    public PieceWithCoords(int _h, int _w, int _x, int _y) {
        super(_h, _w);
        this.x = _x;
        this.y = _y;
    }

    /**
     * Getter de la coordonnée x
     * @return int la coordonnée x de l'objet
     */
    public int getX() {
        return x;
    }

    /**
     * Getter de la coordonnée y
     * @return int la coordonnée y de l'objet
     */
    public int getY() {
        return y;
    }

    /**
     * Setter de la coordonnée y
     * @param _y : la nouvelle valeur de la coordonnée y
     */
    public void setY(int _y) {
        this.y = _y;
    }
}
