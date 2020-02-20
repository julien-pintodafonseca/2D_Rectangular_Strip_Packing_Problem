package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;

import java.util.Map;

/**
 * Created by pintodaj on 2/20/20.
 */
public class BL {
    private FileIn fileIn;

    public BL(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        Map<Plate, Integer> plates = fileIn.getPlates();
        Map<Rectangle, Integer> pieces = fileIn.getPieces();

        for (Plate p : plates.keySet()) {
            for (int i=0; i<plates.get(p); i++) {
                System.out.println(i);
                System.out.println(plates.get(p));
                System.out.println("+++ NOUVELLE PLAQUE ["+p.getH()+"/"+p.getW()+"]+++");
                BLForOnePlate(p, pieces);
                plates.put(p, plates.get(p)-1);
            }
        }
    }

    private void BLForOnePlate(Plate plate, Map<Rectangle, Integer> pieces) {
        int lineH = 0;
        int lineW = 0;
        boolean newLine = true;
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placer toutes les pièces
        int nbPieces; // nombre de pièces à découper dans la ligne actuelle

        while (plate.getHRest() > 0 && !end) {
            end = true;
            for (Rectangle p : pieces.keySet()) {
                if (p.getH() <= plate.getHRest() && p.getW() <= plate.getLineWRest(lineW) && pieces.get(p) > 0) {
                    end = false;
                    if (newLine) {
                        newLine = false;
                        plate.setHRest(plate.getHRest() - p.getH());
                        lineW += p.getW();
                        pieces.put(p, pieces.get(p)-1);
                        System.out.println("Nouvelle ligne, une pièce a été placée ! ["+p.getH()+"/"+p.getW()+"]");
                    }
                    nbPieces = plate.getLineWRest(lineW) / p.getW();
                    if (nbPieces > 0) {
                        if (pieces.get(p) - nbPieces > 0) {
                            // On place toutes les pièces que l'on peut placer (nbPieces)
                            System.out.println(nbPieces+" ["+p.getH()+"/"+p.getW()+"] ont été découpées" +
                                    "(toutes les pièces pouvant etre placées sur la ligne).");
                            lineW += nbPieces * p.getW();
                            pieces.put(p, pieces.get(p) - nbPieces);
                        } else {
                            // On place toutes les pièces qu'il nous reste à placer (pieces.get(p))
                            System.out.println(pieces.get(p)+" ["+p.getH()+"/"+p.getW()+"] ont été découpées (toutes les pièces restantes).");
                            lineW += pieces.get(p) * p.getW();
                            pieces.put(p, 0);
                        }
                    }
                }
            }
            if (!end) {
                newLine = true;
                lineW = 0;
                lineH = plate.getH() - plate.getHRest();
            }
        }

    }
}
