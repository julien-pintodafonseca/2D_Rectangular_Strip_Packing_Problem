package com.ensimag.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutOptimized extends Cut {
    private Map<Piece, Integer> pieces;

    /**
     * Constructeur
     * @param clone : objet CutOptimized à cloner
     */
    public CutOptimized(CutOptimized clone) {
        super(clone);
        this.pieces = new HashMap<>(clone.getPieces());
    }

    /**
     * Constructeur
     * @param _lost : les pertes
     * @param _info : liste de pièces placées avec leurs coordonnée sur la plaque
     * @param _pieces : map de pièces restantes
     */
    public CutOptimized(int _lost, List<PieceWithCoords> _info, Map<Piece, Integer> _pieces) {
        super(_lost, _info);
        this.pieces = new HashMap<>(_pieces);
    }

    /**
     * Getter
     * @return l'attribut pieces
     */
    public Map<Piece, Integer> getPieces() {
        return this.pieces;
    }

    /**
     * Convertie l'objet CutOptimized en objet Cut
     * @return l'objet convertie en Cut
     */
    public Cut convertToCut() {
        return new Cut(this.getLost(), this.getInfo());
    }
}
