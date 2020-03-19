package com.ensimag.Models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cut2 extends Cut {
    private Map<Rectangle, Integer> pieces;

    public Cut2(Cut2 clone) {
        super(clone);
        this.pieces = new HashMap<>(clone.getPieces());
    }

    public Cut2(int _lost, PieceWithCoords _info, Map<Rectangle, Integer> _pieces) {
        super(_lost, _info);
        this.pieces = new HashMap<>(_pieces);
    }

    public Cut2(int _lost, List<PieceWithCoords> _info, Map<Rectangle, Integer> _pieces) {
        super(_lost, _info);
        this.pieces = new HashMap<>(_pieces);
    }

    public Map<Rectangle, Integer> getPieces() {
        return this.pieces;
    }

    public Cut convertToCut() {
        return new Cut(this.getLost(), this.getInfo());
    }

}
