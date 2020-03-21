package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.Cut;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BL2in1 {
    private FileIn fileIn; //fichier d'entrée=entries.txt
    private List<String> results; //ce qui doit être écrit dans le fichier de sortie


    /**
     * Constructeur
     * @param _fileIn : fichier à lire
     */
    public BL2in1(FileIn _fileIn) {
        this.fileIn = _fileIn;
        this.results = new ArrayList<>();
    }

    

    /**
     * Méthode permettant l'éxecution de l'algorithme
     * @param type : booléen permettant de savoir si on éxécute l'algo de la tache 1(type=false) ou la tache 2(type=true)
     */
    public void start(boolean type) {
        Map<Plate, Integer> plates = fileIn.getPlatesMap(); //Map des différentes plaques et leur quantité
        Map<Piece, Integer> pieces = fileIn.getPiecesMap(); //Map des différentes pièces et leur quantité
        Cut resultBLForOnePlate; //Contient le résultat de l'algorithme pour une plaque : les pièces découpés, leur position dans la plaque et les pertes
        int lost = 0; //pertes totales de toutes les plaques
        boolean end = false; // indique si pour un même type de plaque on peut encore poser des pièces sur d'autre plaque de ce type

        int plateNumber = 0; //Numéro de la plaque découpé
        //Pour chaque plaque :
        for (Plate pType : fileIn.getPlateTypes()) {
            int nbPlatesAtStart = plates.get(pType);
            for (int i=0; i<nbPlatesAtStart; i++) {
                Plate plate = new Plate(pType.getH(), pType.getW()); // on créé un plaque du même type sur laquelle exécuter l'algorithme

                if (!end) {
                    resultBLForOnePlate = BLForOnePlate(plate, pieces, type); //on découpe la plaque
                    if (resultBLForOnePlate.getInfo().size() == 0) { //si on n'a pu découper aucune pièce dans cette plaque
                        end = true; // on ne découpera plus aucune plaque de ce type
                    }
                } else {
                    resultBLForOnePlate = new Cut();
                }

                results.addAll(resultBLForOnePlate.toString(plateNumber)); // écrit la découpe de la plaque
                lost += resultBLForOnePlate.getLost(); // incrémente les pertes
                plateNumber++; //passe à plaque suivante
            }
            end = false;
        }
        results.addAll((new Cut()).toStringEnd(pieces, lost)); // écrit les pièces qui n'ont pas été utilisées et les pertes

        //écriture dans le fichier de sortie et affichage de l'état de l'algorithme sur la console
        FileOut fileOut;
        if (type) {
            System.out.println("BLAdvanced Algorithm : entries.txt --> resultsBLAdvanced.txt | State: Success!");
            fileOut = new FileOut("resultsBLAdvanced.txt", results);
        } else {
            System.out.println("BL Algorithm : entries.txt --> resultsBL.txt | State: Success!");
            fileOut = new FileOut("resultsBL.txt", results);
        }
        fileOut.writeFile();
    }


    /**
     * Fonction qui effectue l'algorithme BL pour une seule plaque
     * @param plate : la plaque à découper
     * @param pieces : map des pièces restants à découper
     * @param type : booléen permettant de savoir si on éxécute l'algorithme de la tâche 1(type=false) ou la tâche 2(type=true)
     * @return : la plaque découpée
     */
    private Cut BLForOnePlate(Plate plate, Map<Piece, Integer> pieces, boolean type) {
        boolean newLine = true; // permet de savoir si on doit commencer une nouvelle ligne
        boolean end = false; // vrai si l'on ne peut plus placer de pièces ou que l'on a placé toutes les pièces
        Cut piecesDecoupes = new Cut(); //résultat : valeur que l'on retourne

        int lineH = 0; // hauteur de la ligne courante
        int lineW = plate.getW(); // largeur restante de la ligne courante
        int LS = 0; //hauteur de la ligne courante

        while (plate.getHRest() > 0 && !end) {
            end = true;
            Iterator it = fileIn.getPieceTypes().iterator();
            Piece p;
            while (it.hasNext()) {
                p = (Piece) it.next();
                if (pieces.get(p) == 0) {
                    it.remove();
                } else {
                    if (newLine) {
                        if (p.getH() <= plate.getHRest() && p.getW() <= plate.getW()) {
                            newLine = false;
                            end = false;
                            //add piece
                            pieces.put(p, pieces.get(p) - 1);
                            piecesDecoupes.addInfo(new PieceWithCoords(p, 0, LS));
                            plate.setHRest(plate.getHRest() - p.getH());
                            lineH = p.getH();
                            lineW = plate.getW() - p.getW();
                        }
                    }
                    if (!newLine) {
                        //on ajoute dans la ligne des pièces tant que l'on peut
                        while (p.getH() <= lineH && p.getW() <= lineW && pieces.get(p) > 0) {
                            //add piece
                            pieces.put(p, pieces.get(p) - 1);
                            piecesDecoupes.addInfo(new PieceWithCoords(p, plate.getW() - lineW, LS));
                            if (type) {//Si on éxécuté l'algo de la tâche 2
                                //On essaye de positionner des pièces au dessus de celle mise sur la ligne
                                // tant que on ne dépasse pas la hauteur de la ligne avec la fonction stacking
                                piecesDecoupes.fusion(
                                        stacking(new PieceWithCoords(lineH - p.getH(), p.getW(),
                                                plate.getW() - lineW, LS + p.getH()), pieces)
                                );
                            } else {
                                piecesDecoupes.addLost((lineH - p.getH()) * p.getW());
                            }

                            lineW -= p.getW();
                        }
                    }
                }
            }
            //Quand on ne peut plus placer de pièces sur la ligne
            // On change de ligne
            if (!end) {
                piecesDecoupes.addLost(lineW*lineH);
                newLine = true;
                LS = plate.getH() - plate.getHRest();
            }
        }
        piecesDecoupes.addLost(plate.getHRest()*plate.getW());
        // Si les pertes de la plaque sont égales à sont aires
        //Alors la plaque n'a pas été découpé donc elle n'a pas de perte
        if (piecesDecoupes.getLost() == plate.getW()*plate.getH()) {
            piecesDecoupes.addLost(-piecesDecoupes.getLost());
        }
        return piecesDecoupes;
    }


    /**
     * Fonction pour positionner des pièces au dessus d'une pièce
     * @param subPlate : la sous plaque contennat l'aire disponible au dessus de la pièce sur laquelle on apelle la méthode stacking
     * @param pieces :  map des pièces restantes à découper
     * @return  : la sous plaque découpée
     */
    private Cut stacking(PieceWithCoords subPlate, Map<Piece, Integer> pieces) {
        Cut piecesDecoupes = new Cut();
        //Si on a pas d'aire disponible
        //Alors on ne peut  rien découper dedans
        if (subPlate.getH() == 0) {
            return piecesDecoupes;
        } else { // Sinon on positionne toutes les pièces que l'on peut
            for (Piece p : fileIn.getPieceTypes()) {
                while (subPlate.getH() >= p.getH() && subPlate.getW() >= p.getW() && pieces.get(p) > 0) {
                    piecesDecoupes.addInfo(new PieceWithCoords(p.getH(), p.getW(), subPlate.getX(), subPlate.getY()));
                    pieces.put(p, pieces.get(p) - 1);
                    piecesDecoupes.addLost((subPlate.getW() - p.getW()) * p.getH());
                    subPlate.setH(subPlate.getH() - p.getH());
                    subPlate.setY(subPlate.getY() + p.getH());
                }
            }
            piecesDecoupes.addLost(subPlate.getW()*subPlate.getH());
            return piecesDecoupes;
        }
    }
}
