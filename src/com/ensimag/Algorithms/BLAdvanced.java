package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Models.Decoupe;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by solokwal on 2/20/20.
 */
public class BLAdvanced {
    private FileIn fileIn;
    private Map<Rectangle, Integer> pieces;

    public BLAdvanced(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Plate plate = fileIn.getPlates().keySet().iterator().next();
        this.pieces = fileIn.getPieces();
        this.cutCutCut(plate);
    }

    public Decoupe cutCutCut(Plate Plate) {
        Iterator<Rectangle> it = this.pieces.keySet().iterator();
        Rectangle p = new Rectangle(0,0);
        boolean toward = false;
        boolean enoughPlace=false;

        while (!enoughPlace && it.hasNext()) {
            p = it.next();
            if (p.getH() <= Plate.getH() && p.getW() <= Plate.getW() && pieces.get(p) > 0) {
                enoughPlace=true;
            }else if(p.getH() <= Plate.getW() && p.getW() <= Plate.getH() && pieces.get(p) > 0){
                enoughPlace=true;
                toward = true;
            }
        }

        if(!enoughPlace){
            return new Decoupe(Plate.getH()*Plate.getW(), "");
        }else{
            this.pieces.put(p, this.pieces.get(p) - 1);
            if(toward) {
                Decoupe subPlate1 = new Decoupe(this.cutCutCut(new Plate(Plate.getH()-p.getW(), p.getH())));
                Decoupe subPlate2 = new Decoupe(this.cutCutCut(new Plate(Plate.getH(), Plate.getW()-p.getH())));
                Decoupe subPlate3 = new Decoupe(this.cutCutCut(new Plate(Plate.getH()-p.getW(), Plate.getW())));
                Decoupe subPlate4 = new Decoupe(this.cutCutCut(new Plate(p.getW(), Plate.getW()-p.getH())));
                if(subPlate1.getLost()+subPlate2.getLost()> subPlate1.getLost()+subPlate2.getLost()) {
                    subPlate1.fusion(subPlate2);
                    return subPlate1;
                }
                else {
                    subPlate3.fusion(subPlate4);
                    return subPlate3;
                }
            }else {
                Decoupe subPlate1 = new Decoupe(this.cutCutCut(new Plate(Plate.getH()-p.getH(), p.getW())));
                Decoupe subPlate2 = new Decoupe(this.cutCutCut(new Plate(Plate.getH(), Plate.getW()-p.getW())));
                Decoupe subPlate3 = new Decoupe(this.cutCutCut(new Plate(Plate.getH()-p.getH(), Plate.getW())));
                Decoupe subPlate4 = new Decoupe(this.cutCutCut(new Plate(p.getH(), Plate.getW()-p.getW())));
                if(subPlate1.getLost()+subPlate2.getLost()> subPlate1.getLost()+subPlate2.getLost()) {
                    subPlate1.fusion(subPlate2);
                    subPlate1.setInfo("Piece H=" + p.getH() + " / W=" + p.getW() + " / " + subPlate1.getInfo());
                    return subPlate1;
                }
                else {
                    subPlate3.fusion(subPlate4);
                    return subPlate3;
                }
            }
        }
    }
}
