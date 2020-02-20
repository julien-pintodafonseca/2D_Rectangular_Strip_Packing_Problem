package com.ensimag.Models;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by solokwal on 2/20/20.
 */

public class BLAdvanced {

    public Decoupe BLAdvanced(Plaque plaque, Map<Rectangle, Integer> pieces) {
        Iterator<Rectangle> it = pieces.keySet().iterator();
        Rectangle p = new Rectangle(0,0);
        boolean toward = false;
        boolean enoughPlace=false;

        while (!enoughPlace && it.hasNext()) {
            p = it.next();
            if (p.getH() <= plaque.getH() && p.getW() <= plaque.getW() && pieces.get(p) > 0) {
                enoughPlace=true;
            }else if(p.getH() <= plaque.getW() && p.getW() <= plaque.getH() && pieces.get(p) > 0){
                enoughPlace=true;
                toward = true;
            }
        }

        if(!enoughPlace){
            return new Decoupe(plaque.getH()*plaque.getW(), "");
        }else{
            pieces.put(p, pieces.get(p) - 1);
            if(toward) {
                Decoupe subPlate1 = new Decoupe((new Plate(plaque.getH()-p.getW(), p.getH())).BLAdvanced(pieces));
                Decoupe subPlate2 = new Decoupe((new Plate(plaque.getH(), plaque.getW()-p.getH())).BLAdvanced(pieces));
                Decoupe subPlate3 = new Decoupe((new Plate(plaque.getH()-p.getW(), plaque.getW())).BLAdvanced(pieces));
                Decoupe subPlate4 = new Decoupe((new Plate(p.getW(), plaque.getW()-p.getH())).BLAdvanced(pieces));
                if(subPlate1.getLost()+subPlate2.getLost()> subPlate1.getLost()+subPlate2.getLost()) {
                    subPlate1.fusion(subPlate2);
                    return subPlate1;
                }
                else {
                    subPlate3.fusion(subPlate4);
                    return subPlate3;
                }
            }else {
                Decoupe subPlate1 = new Decoupe((new Plate(plaque.getH()-p.getH(), p.getW())).BLAdvanced(pieces));
                Decoupe subPlate2 = new Decoupe((new Plate(plaque.getH(), plaque.getW()-p.getW())).BLAdvanced(pieces));
                Decoupe subPlate3 = new Decoupe((new Plate(plaque.getH()-p.getH(), plaque.getW())).BLAdvanced(pieces));
                Decoupe subPlate4 = new Decoupe((new Plate(p.getH(), plaque.getW()-p.getW())).BLAdvanced(pieces));
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
