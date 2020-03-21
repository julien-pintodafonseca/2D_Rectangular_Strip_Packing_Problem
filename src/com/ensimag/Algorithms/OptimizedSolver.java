package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.CutOptimized;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.util.*;

/**
 * Created by solokwal on 2/20/20.
 */
public class OptimizedSolver {
    private FileIn fileIn;
    
    public OptimizedSolver(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    public void start() {
        // récupère la plaque à découper
        Plate plate = fileIn.getPlateTypes().get(0);
        //plateWithCoords est la plaque avec les coordonnées X et Y où on se place pour la prochaine découpe de pièce
        PieceWithCoords plateWithCoords = new PieceWithCoords(plate, 0, 0);
        //créé une Map contenants chaque type de pièce et leur nombre
        Map<Piece, Integer> pieces = fileIn.getPiecesMap();

        //lance l'algorithme
        CutOptimized result = this.cutCutCut(plateWithCoords, pieces);
        List<String> endResults = result.toString(0);
        endResults.addAll(result.toStringEnd(result.getPieces(), result.getLost()));

        //écrit le résultat dans le fichier de sortie
        System.out.println("OptimizedSolver Algorithm: entries.txt --> resultsOptimizedSolver.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsOptimizedSolver.txt", endResults);
        fileOut.writeFile();
    }

    private CutOptimized cutCutCut(PieceWithCoords plate, Map<Piece, Integer> pieces) {
        //itérateur pour parcourir toutes les pièces disponibles
        Iterator<Piece> it = fileIn.getPieceTypes().iterator();
        Piece p = new Piece(0,0);

        boolean toward = false; // booléen indiquant que la pièce tournée loge dans la plaque
        boolean enoughPlace = false; // booléen indiquant que la pièce non tournée loge dans la plaque
        boolean stop = false; // booléen indiquant l'arrêt de l'algorithme

        //Fin de l'algorithme si la plaque à sa hauteur ou largueur égale à 0
        if (plate.getH() == 0 || plate.getW() == 0) {
            stop = true;
        }

        //Boucle pour trouver une pièce qui rentre dans a plaque
        while (!enoughPlace && !toward && it.hasNext() && !stop) {
            p = it.next();
            enoughPlace = false;
            toward = false;
            //on regarde si la pièce non tournée rentre
            if (pieces.get(p) > 0 && p.getH() <= plate.getH() && p.getW() <= plate.getW()) {
                enoughPlace = true;
            }
            //on regarde si la pièce rentre quand on la tourne ( hauteur et largueur inversée)
            if (pieces.get(p) > 0 && p.getH() <= plate.getW() && p.getW() <= plate.getH() && p.getH() != p.getW()){
                toward = true;
            }
        }

        //Si il n'y a aucune pièce qui rentre non tournée ou tournée , c'est la fin de l'algorithme
        if (!enoughPlace && !toward) {
            return new CutOptimized(plate.getH()*plate.getW(), new ArrayList<>(), pieces);
        } else {
            pieces.put(p, pieces.get(p) - 1); // on supprime de la map pieces la pièce que l'on place
            //création de deux CutOptimized
            CutOptimized subPlateEnough; // pour quand on laisse pièce dans le bon sens
            CutOptimized subPlateToward; // pour quand on tourne la pièce

            //Si la pièce loge dans le bon sens et tournée, on cherche la meilleure solution dans les deux cas
            // et on retourne celle avec le moins de perte
            if (enoughPlace && toward) {
                subPlateEnough = new CutOptimized(bestSoluce(plate, p.getH(), p.getW(), pieces));
                subPlateToward = new CutOptimized(bestSoluce(plate, p.getW(), p.getH(), pieces));
                if (subPlateToward.getLost() > subPlateEnough.getLost()) {
                    return subPlateEnough;
                } else {
                    return subPlateToward;
                }
            } else if (enoughPlace) { // cherche la meilleure solution que dans le cas où la pièce garde sons sens
                subPlateEnough = new CutOptimized(bestSoluce(plate, p.getH(), p.getW(), pieces));
                return subPlateEnough;
            } else { //cherche la meilleure solution que dans le cas où pièce est tounée
                subPlateToward = new CutOptimized(bestSoluce(plate, p.getW(), p.getH(), pieces));
                return subPlateToward;
            }
        }
    }

    //algorithme qui effectue la meileure découpe
    // plate : la plaque que l'on veut découper
    //pH : la hauteur de a pièce que on découpe
    //pW : la largeur de la pièce que on découpe
    // pieces : la map contenant les pièces qu'il reste à placer
    private CutOptimized bestSoluce(PieceWithCoords plate, int pH, int pW, Map<Piece, Integer> pieces) {
        Map<Piece, Integer> piecesCopy = new HashMap<>(pieces);

        //Si avec cette découpé la hateur de la plaque est à 0
        //Une seule sous plaque est créée
        // On effectue la découpe, on donne les nouveaux X et Y où on se trouve après la découpe et retourne le résultat
        if (plate.getH()-pH == 0) {
            CutOptimized subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        }
        //Si avec cette découpe la largeur de la plaque est à 0
        //Une seule sous plaque est créée
        // On effectue la découpe, on donne les nouveaux X et Y où on se trouve après la découpe et retourne le résultat
        if (plate.getW()-pW == 0) {
            CutOptimized subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1a.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1a;
        }
        //Dans les autres cas on pourra créer deux sous plaque après avoir découper la pièce
        // Il a deux facons de découper les sous plaques
        //La première sous découpe
        CutOptimized subPlate1a;
        CutOptimized subPlate1b;
        if ((plate.getH()-pH)*pW >= plate.getH()*(plate.getW()-pW)) {
            subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate1a.getPieces()));
        } else {
            subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), subPlate1a.getPieces()));
        }

        //la deuxième sous découpe
        CutOptimized subPlate2a;
        CutOptimized subPlate2b;
        if ((plate.getH()-pH)*plate.getW() >= pH*(plate.getW()-pW)) {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate2a.getPieces()));
        } else {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), subPlate2a.getPieces()));
        }

        //On choisit la sous découpe qui renvoie le moins de chute
        //On fusionne les résultats des deux plaques de la sous découpe
        //et on renvoie le résultat
        if (subPlate1a.getLost()+subPlate1b.getLost() <= subPlate2a.getLost()+subPlate2b.getLost()) {
            subPlate1b.fusion(subPlate1a.convertToCut());
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        } else {
            subPlate2b.fusion(subPlate2a.convertToCut());
            subPlate2b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate2b;
        }
    }
}
