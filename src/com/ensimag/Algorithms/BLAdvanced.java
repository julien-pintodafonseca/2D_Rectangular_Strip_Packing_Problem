package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Models.Decoupe;
import com.ensimag.Models.Plate;
import com.ensimag.Models.PlateWithCoords;
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
        PlateWithCoords plate2 = new PlateWithCoords(plate, 0, 0);
        this.pieces = fileIn.getPieces();
        Decoupe result = this.cutCutCut(plate2);
        System.out.println(result.toString());
    }

    private Decoupe cutCutCut(PlateWithCoords plate) {
        Iterator<Rectangle> it = this.pieces.keySet().iterator();
        Rectangle p = new Rectangle(0,0);
        boolean toward = false;
        boolean enoughPlace=false;

        while (!enoughPlace && it.hasNext()) {
            p = it.next();
            if (p.getH() <=plate.getH() && p.getW() <= plate.getW() && pieces.get(p) > 0) {
                enoughPlace=true;
            }else if(p.getH() <= plate.getW() && p.getW() <= plate.getH() && pieces.get(p) > 0){
                enoughPlace=true;
                toward = true;
            }
        }

        if(!enoughPlace){
            return new Decoupe(plate.getH()*plate.getW(), "");
        }else{
            this.pieces.put(p, this.pieces.get(p) - 1);
            if(toward) {
                Decoupe subPlate1 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH()-p.getW(), p.getH(),plate.getX(), plate.getY() + p.getW())));
                Decoupe subPlate2 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH(), plate.getW()-p.getH(), plate.getX() + p.getH(), plate.getY())));
                Decoupe subPlate3 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH()-p.getW(), plate.getW(), plate.getX(), plate.getY() + p.getW())));
                Decoupe subPlate4 = new Decoupe(this.cutCutCut(new PlateWithCoords(p.getW(), plate.getW()-p.getH(), plate.getX() + p.getH(), plate.getY())));
                if(subPlate1.getLost()+subPlate2.getLost() < subPlate3.getLost()+subPlate4.getLost()) {
                    subPlate1.fusion(subPlate2);
                    System.out.println(" dr" + subPlate1.getInfo());
                    subPlate1.setInfo("Piece H=" + p.getH() + " / W=" + p.getW() + " / Position X=" + plate.getX() + " / Y=" + plate.getY() + "\n" + subPlate1.getInfo());
                    return subPlate1;
                }
                else {
                    subPlate3.fusion(subPlate4);
                    System.out.println(" dr" + subPlate3.getInfo());
                    subPlate3.setInfo("Piece H=" + p.getW() + " / W=" + p.getH() +  " / Position X=" + plate.getX() + " / Y=" + plate.getY() + "\n" + subPlate3.getInfo());
                    return subPlate3;
                }
            }else {
                Decoupe subPlate1 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH()-p.getH(), p.getW(),plate.getX(), plate.getY() + p.getH())));
                Decoupe subPlate2 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH(), plate.getW()-p.getW(), plate.getX() + p.getW(), plate.getY())));
                Decoupe subPlate3 = new Decoupe(this.cutCutCut(new PlateWithCoords(plate.getH()-p.getH(), plate.getW(), plate.getX(), plate.getY() + p.getH())));
                Decoupe subPlate4 = new Decoupe(this.cutCutCut(new PlateWithCoords(p.getH(), plate.getW()-p.getW(), plate.getX() + p.getW(), plate.getY())));
                if(subPlate1.getLost()+subPlate2.getLost() < subPlate3.getLost()+subPlate4.getLost()) {
                    subPlate1.fusion(subPlate2);
                    subPlate1.setInfo("Piece H=" + p.getH() + " / W=" + p.getW() +  " / Position X=" + plate.getX() + " / Y=" + plate.getY() + "\n" + subPlate1.getInfo());
                    return subPlate1;
                }
                else {
                    subPlate3.fusion(subPlate4);
                    subPlate3.setInfo("Piece H=" + p.getH() + " / W=" + p.getW() +  " / Position X=" + plate.getX() + " / Y=" + plate.getY() + "\n" + subPlate3.getInfo());
                    return subPlate3;
                }
            }
        }
    }
}
