package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pintodaj on 2/20/20.
 */
public class BL {
    private FileIn fileIn;
    private List<String> results;

    public BL(FileIn _fileIn) {
        this.fileIn = _fileIn;
        this.results = new ArrayList<>();
    }

    public void start() {
        Map<Plate, Integer> plates = fileIn.getPlates();
        Map<Rectangle, Integer> pieces = fileIn.getPieces();
        int resultBLForOnePlate;
        Integer chutes = 0;

        int plateNumber = 0;
        for (Plate pType : plates.keySet()) {
            int nbPlatesAtStart = plates.get(pType);
            for (int i=0; i<nbPlatesAtStart; i++) {
                Plate plate = new Plate(pType.getH(), pType.getW());

                results.add("Plaque "+plateNumber+" :");
                resultBLForOnePlate = BLForOnePlate(plate, pieces);
                if (resultBLForOnePlate != -1) {
                    plates.put(pType, plates.get(pType) - 1);
                    chutes += resultBLForOnePlate;
                } else {
                    results.add("Pas utilisée.");
                }

                plateNumber++;
            }
        }

        results.add("Pièces restantes à couper :");
        String piecesRestantes = "";
        for (Rectangle p : pieces.keySet()) {
            for (int i=0; i<pieces.get(p); i++) {
                piecesRestantes += p.getH()+" "+p.getW()+", ";
            }
        }
        if (!piecesRestantes.equals("")) {
            results.add(piecesRestantes);
        } else {
            results.add("Aucune.");
        }

        results.add("Chutes:");
        if (chutes != 0) {
            results.add(chutes.toString());
        } else {
            results.add("Aucune.");
        }

        System.out.println("BL Algorithm: entries.txt --> resultsBL.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsBL.txt", results);
        fileOut.writeFile();
    }

    private int BLForOnePlate(Plate plate, Map<Rectangle, Integer> pieces) {
        int lineH = 0;
        int lineW = 0;
        boolean plateIsUsed = false; // vrai si la plaque a été utilisé (même partiellement), faux sinon
        boolean newLine = true;
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placer toutes les pièces
        int nbPieces; // nombre de pièces à découper dans la ligne actuelle
        String piecesDecoupes = "";
        int chutes = getSize(plate);

        while (plate.getHRest() > 0 && !end) {
            end = true;
            for (Rectangle p : pieces.keySet()) {
                if (p.getH() <= plate.getHRest() && p.getW() <= plate.getLineWRest(lineW) && pieces.get(p) > 0) {
                    end = false;
                    if (newLine) {
                        if (!plateIsUsed) {
                            plateIsUsed = true;
                            //System.out.println();
                            //System.out.println("+++ NOUVELLE PLAQUE ["+plate.getH()+"/"+plate.getW()+"]+++");
                        }
                        newLine = false;
                        plate.setHRest(plate.getHRest() - p.getH());
                        piecesDecoupes += lineW+" "+p.getH()+" "+p.getW()+", ";
                        chutes -= getSize(p);
                        lineW += p.getW();
                        pieces.put(p, pieces.get(p)-1);
                        //System.out.println("Nouvelle ligne, une pièce a été placée ! ["+p.getH()+"/"+p.getW()+"]");
                        results.add("LS="+lineH);
                    }
                    nbPieces = plate.getLineWRest(lineW) / p.getW();
                    if (nbPieces > 0) {
                        if (pieces.get(p) - nbPieces > 0) {
                            // On place toutes les pièces que l'on peut placer (nbPieces)
                            //System.out.println(nbPieces+" ["+p.getH()+"/"+p.getW()+"] ont été découpées " +
                                    //"(toutes les pièces pouvant etre placées sur la ligne).");
                            for (int i=0; i<nbPieces; i++) {
                                piecesDecoupes += lineW + " " + p.getH() + " " + p.getW() + ", ";
                                chutes -= getSize(p);
                                lineW += p.getW();
                            }
                            pieces.put(p, pieces.get(p) - nbPieces);
                        } else {
                            // On place toutes les pièces qu'il nous reste à placer (pieces.get(p))
                            //System.out.println(pieces.get(p)+" ["+p.getH()+"/"+p.getW()+"] ont été découpées (toutes les pièces restantes).");
                            for (int i=0; i<pieces.get(p); i++) {
                                piecesDecoupes += lineW + " " + p.getH() + " " + p.getW() + ", ";
                                chutes -= getSize(p);
                                lineW += p.getW();
                            }
                            pieces.put(p, 0);
                        }
                    }
                }
            }
            if (!piecesDecoupes.equals("")) {
                results.add(piecesDecoupes);
            }
            piecesDecoupes = "";
            if (!end) {
                newLine = true;
                lineW = 0;
                lineH = plate.getH() - plate.getHRest();
            }
        }

        return plateIsUsed ? chutes : -1;
    }

    private int getSize(Rectangle o) {
        return o.getH()*o.getW();
    }
}
