package com.ensimag.Models;

import java.util.*;

public class CutChecker extends Piece {
    private int lost;
    /**
     * La Map a un Integer qui est la coordonnée X. A chaque X est associé une Map contenant la liste des pièces et
     * leur position Y qui sont à cette position X.
     */
    private Map<Integer, Map<Integer, Plate>> xPieces;
    /**
     * La Map a un Integer qui est la coordonnée Y. A chaque Y est associé une Map contenant la liste des pièces et
     * leur position X qui sont à cette position Y.
     */
    private Map<Integer, Map<Integer, Plate>> yPieces;
    /**
     * La liste de tous les X sur lesquels il y a une ou plusieurs pièce(s).
     */
    private List<Integer> xList;
    /**
     * La liste de tous les Y sur lesquels il y a une ou plusieurs pièce(s).
     */
    private List<Integer> yList;

    /**
     * Constructeur
     * @param _h
     * @param _w
     */
    public CutChecker(int _h, int _w) {
        super(_h, _w);
        this.xPieces = new HashMap<>();
        this.yPieces = new HashMap<>();
        this.xList = new ArrayList<>();
        this.yList = new ArrayList<>();
        this.lost = 0;
    }

    public Map<Integer, Map<Integer, Plate>> getxPieces() {
        return this.xPieces;
    }

    public Map<Integer, Map<Integer, Plate>> getyPieces() {
        return this.yPieces;
    }

    public List<Integer> getxList() {
        return this.xList;
    }

    public List<Integer> getyList() {
        return this.yList;
    }

    public int getLost() {
        return this.lost;
    }

    public void setLost(int _lost) {
        this.lost = _lost;
    }

    public void addLost(int yes) {
        this.lost += yes;
    }

    /**
     * Méthode qui permet d'ajouter une pièce découpée dans la plaque
     * @param piece : la pièce à ajouter
     */
    public void addPiece(PieceWithCoords piece) {
        // initialisation des sous map si nécessaire
        if (!this.xPieces.containsKey(piece.getX())) {
            this.xPieces.put(piece.getX(), new HashMap<>());
        }
        if (!this.yPieces.containsKey(piece.getY())) {
            this.yPieces.put(piece.getY(), new HashMap<>());
        }

        // On rajoute la pièce sur la map des X
        Map<Integer, Plate> subXPieces = this.xPieces.get(piece.getX());
        if (subXPieces.containsKey(piece.getY())) {
            System.out.println("Erreur deux pièces à la même position - CutChecker.java");
        }
        subXPieces.put(piece.getY(), new Plate(piece.getH(), piece.getW()));
        this.xPieces.put(piece.getX(), subXPieces);

        // On rajoute la pièce sur la map des Y
        Map<Integer, Plate> subYPieces = this.yPieces.get(piece.getY());
        if (subYPieces.containsKey(piece.getX())) {
            System.out.println("Erreur deux pièces à la même position - CutChecker.java");
        }
        subYPieces.put(piece.getX(), new Plate(piece.getH(), piece.getW()));
        this.yPieces.put(piece.getY(), subYPieces);

        // Ajouter le X de la pièce à la liste des X possibles
        if (!this.xList.contains(piece.getX())) {
            this.xList.add(piece.getX());
        }

        // Ajouter le Y de la pièce à la liste des Y possibles
        if (!this.yList.contains(piece.getY())) {
            this.yList.add(piece.getY());
        }
    }

    /**
     * Fonction qui regarde si la pièce a une pièce voisine à droite qui se superpose sur elle
     *          ___________
     *         | Voisine |
     *     ___|____     |
     *   |   |    |    |
     *  |   |____|____|
     * |________|
     * @param x : la position X de la pièce
     * @param y : la position Y de la pièce
     * @param piece : la pièce à vérifier
     * @return true si la pièce voisine à une pièce à droite qui se superpose false sinon
     */
    public boolean suivX(int x, int y, Plate piece) {
        int x_index = this.xList.indexOf(x);
        if (x_index == this.xList.size() - 1) { //Si c'est la dernière pièce du X Alors pas de pièce voisine à droite
            return true;
        } else {
            x_index += 1;
            int x_index_max;
            // x_index_max : le premier X qui existe dans xList tel que ce X soit supérieur ou égal à la coordonnée X de notre pièce + sa largeur
            if (this.xList.contains(x + piece.getW())) {
                x_index_max = this.xList.indexOf(x + piece.getW());
            } else {
                x_index_max = x_index;
                while (x_index_max < this.xList.size() && this.xList.get(x_index_max) < x + piece.getW()) {
                    x_index_max += 1;
                }
                if (x_index_max < this.xList.size()) {
                    x_index_max += 1;
                }
            }
            int y_index = this.yList.indexOf(y) + 1;
            int y_index_max;
            // y_index_max : le premier X qui existe dans xList tel que ce X soit supérieur ou égal à la coordonnée Y de notre pièce + sa hauteur
            if (this.yList.contains(y + piece.getH())) {
                y_index_max = this.yList.indexOf(y + piece.getH());
            } else {
                y_index_max = y_index;
                while (y_index_max < this.yList.size() && this.yList.get(y_index_max) < y + piece.getH()) {
                    y_index_max += 1;
                }
                if (y_index_max < this.yList.size()) {
                    y_index_max += 1;
                }
            }
            Map<Integer, Plate> subXPiecesY;
            // on fait varier x_index et y_index de manière à vérifier qu'il n'existe pas de pièce de coordonnées (xList.get(x_index), yList.get(y_index))
            // dans la surface de notre pièce
            while (x_index < x_index_max) {
                subXPiecesY = this.xPieces.get(this.xList.get(x_index));
                while (y_index < y_index_max) {
                    if (subXPiecesY.get(y_index) != null) {
                        return false;
                    }
                    y_index += 1;
                }
                x_index += 1;
                y_index = this.yList.indexOf(y) + 1;
            }
            return true;
        }
    }

    /**
     * Fonction qui permet d'avoir sur une ligne de coordonnée y la pièce suivant notre pièce située en x
     * @param x : coordonnée x de la pièce
     * @param y : coordonnée y de la pièce
     * @return la coordonnée X de la pièce suivant notre pièce de coordonnées (x,y) si elle existe sinon -1
     */
    public int nextX(int x, int y) {
        Map<Integer, Plate> subYPiecesX = this.yPieces.get(y);
        this.xList.indexOf(x);
        int indexNextX = this.xList.indexOf(x) + 1;
        int sizeXList = this.xList.size();
        while (indexNextX < sizeXList && !subYPiecesX.containsKey(this.xList.get(indexNextX))) {
            indexNextX ++;
        }
        if (indexNextX >= sizeXList) {
            return -1;
        } else {
            return this.xList.get(indexNextX);
        }
    }

    /**
     * Fonction qui permet d'avoir sur une ligne de coordonnée x la pièce suivant notre pièce située en y
     * @param x : coordonnée x de la pièce
     * @param y : coordonnée y de la pièce
     * @return la coordonnée Y de la pièce suivant notre pièce de coordonnées (x,y) si elle existe sinon -1
     */
    public int nextY(int x, int y) {
        Map<Integer, Plate> subXPiecesY = this.xPieces.get(x);
        this.yList.indexOf(y);
        int indexNextY = this.yList.indexOf(y) + 1;
        int sizeYList = this.yList.size();
        while (indexNextY < sizeYList && !subXPiecesY.containsKey(this.yList.get(indexNextY))) {
            indexNextY ++;
        }
        if (indexNextY >= sizeYList) {
            return -1;
        } else {
            return this.yList.get(indexNextY);
        }
    }

    /**
     * Méthode qui trie xList et yList dans l'ordre croissant
     */
    public void sort() {
        Collections.sort(this.xList);
        Collections.sort(this.yList);
    }
}
