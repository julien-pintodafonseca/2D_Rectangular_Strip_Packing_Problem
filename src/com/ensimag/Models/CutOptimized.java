package com.ensimag.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CutOptimized extends Cut {
    private Map<Piece, Integer> pieces;

    public CutOptimized(CutOptimized clone) {
        super(clone);
        this.pieces = new HashMap<>(clone.getPieces());
    }

    public CutOptimized(int _lost, List<PieceWithCoords> _info, Map<Piece, Integer> _pieces) {
        super(_lost, _info);
        this.pieces = new HashMap<>(_pieces);
    }

    public Map<Piece, Integer> getPieces() {
        return this.pieces;
    }

    public Cut convertToCut() {
        return new Cut(this.getLost(), this.getInfo());
    }
}
