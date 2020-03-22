package com.ensimag.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Plan de découpe pour l'algorithme OptimizedSolver
 * @author Groupe6
 */
public class CutOptimized extends Cut {
    private Map<Piece, Integer> pieces;

    /**
     * Constructeur de CutOptimized
     * @param clone : objet CutOptimized à cloner
     */
    public CutOptimized(CutOptimized clone) {
        super(clone);
        this.pieces = new HashMap<>(clone.getPieces());
    }

    /**
     * Constructeur de CutOptimized
     * @param _lost : valeur des pertes
     * @param _info : liste de PieceWithCoords (pièces découpées avec leurs coordonnées sur la plaque)
     * @param _pieces : map contenant les pièces restantes (=les pièces non découpées)
     */
    public CutOptimized(int _lost, List<PieceWithCoords> _info, Map<Piece, Integer> _pieces) {
        super(_lost, _info);
        this.pieces = new HashMap<>(_pieces);
    }

    /**
     * Getter de la map contenant les pièces restantes (=les pièces non découpées)
     * @return la map contenant les pièces restantes (=les pièces non découpées)
     */
    public Map<Piece, Integer> getPieces() {
        return this.pieces;
    }

    /**
     * Permet de convertir l'objet CutOptimized en objet Cut
     * @return l'objet converti, de type Cut
     */
    public Cut convertToCut() {
        return new Cut(this.getLost(), this.getInfo());
    }
}
