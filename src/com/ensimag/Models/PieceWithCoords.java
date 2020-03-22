package com.ensimag.Models;

/**
 * Class PieceWithCoords
 * @author Groupe6
 */
public class PieceWithCoords extends Piece {
    /**
     * Coordonnée x
     */
    private int x;
    /**
     * Coordonnée y
     */
    private int y;

    /**
     * Contructeur de PieceWithCoords
     * @param _piece : objet pièce
     * @param _x : coordonnée x de la pièce
     * @param _y : coordonnée y de la pièce
     */
    public PieceWithCoords(Piece _piece, int _x, int _y) {
        super(_piece.getH(), _piece.getW());
        this.x = _x;
        this.y = _y;
    }

    /**
     * Construtueur de PieceWithCoords
     * @param _plate : objet plaque
     * @param _x : coordonnée x de la plaque
     * @param _y : coordonnée y de la plaque
     */
    public PieceWithCoords(Plate _plate, int _x, int _y) {
        super(_plate.getH(), _plate.getW());
        this.x = _x;
        this.y = _y;
    }

    /**
     * Constructeur de PieceWithCoords
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
     * @return l'attribut x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter de la coordonnée y
     * @return l'attribut y
     */
    public int getY() {
        return y;
    }

    /**
     * Setter de la coordonnée y
     * @param _y : nouvelle valeur de l'attribut y
     */
    public void setY(int _y) {
        this.y = _y;
    }
}
