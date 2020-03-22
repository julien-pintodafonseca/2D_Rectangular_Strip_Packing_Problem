package com.ensimag.Algorithms;

import com.ensimag.Files.FileIn;
import com.ensimag.Files.FileOut;
import com.ensimag.Models.CutOptimized;
import com.ensimag.Models.PieceWithCoords;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;

import java.util.*;

/**
 * Class OptimizedSolver
 * @author Groupe6
 */
public class OptimizedSolver {
    private FileIn fileIn;

    /**
     * Constructeur
     * @param _fileIn : fichier à lire
     */
    public OptimizedSolver(FileIn _fileIn) {
        this.fileIn = _fileIn;
    }

    /**
     * Méthode permettant l'éxecution de l'algorithme
     */
    public void start() {
        // récupère la plaque à découper
        Plate plate = fileIn.getPlateTypes().get(0);
        //plateWithCoords est la plaque avec ses coordonnées 0 et 0 dans laquelle on va découper
        PieceWithCoords plateWithCoords = new PieceWithCoords(plate, 0, 0);
        //créé une Map contenants chaque type de pièce et leur quantité
        Map<Piece, Integer> pieces = fileIn.getPiecesMap();

        //lance l'algorithme
        CutOptimized result = this.cutCutCut(plateWithCoords, pieces);
        //ce qui doit être écrit dans le fichier de sortie : endResults
        List<String> endResults = result.toString(0); // écrit la découpe de la plaque
        endResults.addAll(result.toStringEnd(result.getPieces(), result.getLost()));//écrit les pertes et les pièces restantes

        //écrit le résultat dans le fichier de sortie et état de l'algorithme dans la console
        System.out.println("OptimizedSolver Algorithm: entries.txt --> resultsOptimizedSolver.txt | State: Success!");
        FileOut fileOut = new FileOut("resultsOptimizedSolver.txt", endResults);
        fileOut.writeFile();
    }


    /**
     * Fonction qui effectue l'algorithme de découpe de la plaque
     * @param plate : la plaque à découper avec ses coordonnées ( peut être une sous plaque de la grande plaque à découper)
     * @param pieces : liste des pièces qu'il reste à découper
     * @return la meilleure découpe de la plaque + la liste des pièces à découper (Objet CutOptimized)
     */
    private CutOptimized cutCutCut(PieceWithCoords plate, Map<Piece, Integer> pieces) {
        //itérateur pour parcourir toutes les pièces disponibles
        Iterator<Piece> it = fileIn.getPieceTypes().iterator();
        //initialisation de la pièce que on traite
        Piece p = new Piece(0,0);

        boolean enoughPlace = false; // indique que la pièce loge dans la plaque
        boolean stop = false; // indique l'arrêt de l'algorithme

        //Fin de l'algorithme si la plaque à sa hauteur ou largueur égale à 0
        if (plate.getH() == 0 || plate.getW() == 0) {
            stop = true;
        }

        //Boucle pour trouver une pièce qui rentre dans la plaque
        while (!enoughPlace && it.hasNext() && !stop) {
            p = it.next();
            enoughPlace = false;
            //on regarde si la pièce rentre
            if (pieces.get(p) > 0 && p.getH() <= plate.getH() && p.getW() <= plate.getW()) {
                enoughPlace = true;
            }
        }

        //S'il n'y a aucune pièce qui loge, c'est la fin de l'algorithme
        if (!enoughPlace) {
            return new CutOptimized(plate.getH()*plate.getW(), new ArrayList<>(), pieces);
        } else {
            pieces.put(p, pieces.get(p) - 1); // on supprime de la map pieces la pièce que l'on place
            // on cherche et retourne la meilleure solution sous la forme d'un plan de coupe CutOptimized
            return new CutOptimized(bestSoluce(plate, p.getH(), p.getW(), pieces));
        }
    }


    /**
     * Fonction qui effectue l'algorithme qui détermine la meileure découpe
     * @param plate : la plaque que l'on veut découper
     * @param pH : la hauteur de a pièce que on découpe
     * @param pW : la largeur de la pièce que on découpe
     * @param pieces : la map contenant les pièces qu'il reste à placer
     * @return la meilleure découpe + les pièces restantes à découper
     */
    private CutOptimized bestSoluce(PieceWithCoords plate, int pH, int pW, Map<Piece, Integer> pieces) {
        Map<Piece, Integer> piecesCopy = new HashMap<>(pieces);

        // Si avec cette découpe la hateur de la plaque est à 0
        // Alors le reste de la plaque une fois la pièce enlevée forme une nouvelle plaque
        // On effectue la découpe de la nouvelle plaque, on donne les nouveaux X et Y de la nouvelle plaque et on retourne le résultat
        if (plate.getH()-pH == 0) {
            CutOptimized subPlate1b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH(), plate.getW()-pW, plate.getX() + pW, plate.getY()), pieces));
            subPlate1b.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1b;
        }
        // Si avec cette découpe la largeur de la plaque est à 0
        // Alors le reste de la plaque une fois la pièce enlevée forme une nouvelle plaque
        // On effectue la découpe de la nouvelle plaque, on donne les nouveaux X et Y de la nouvelle plaque et on retourne le résultat
        if (plate.getW()-pW == 0) {
            CutOptimized subPlate1a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, pW, plate.getX(),plate.getY() + pH), pieces));
            subPlate1a.addInfo(new PieceWithCoords(pH, pW, plate.getX(), plate.getY()));
            return subPlate1a;
        }
        // Dans les autres cas on pourra créer deux sous plaque après avoir découper la pièce
        // Il a deux facons de découper les sous plaques
        // La première sous découpe
        // ________________________________
        // |		       |		       |
        // |subPlate1a	   |		       |
        // |_______________| subPlate1b	   |
        // |		       |		       |
        // |piece		   |		       |
        // |_______________|_______________|
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
        // ________________________
        // |			           |
        // |	subPlate2a	       |
        // |_______________________|
        // |	   |		       |
        // |piece  |subPlate2b	   |
        // |_______|_______________|
        CutOptimized subPlate2a;
        CutOptimized subPlate2b;
        if ((plate.getH()-pH)*plate.getW() >= pH*(plate.getW()-pW)) {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), subPlate2a.getPieces()));
        } else {
            subPlate2a = new CutOptimized(this.cutCutCut(new PieceWithCoords(pH, plate.getW()-pW, plate.getX() + pW, plate.getY()), piecesCopy));
            subPlate2b = new CutOptimized(this.cutCutCut(new PieceWithCoords(plate.getH()-pH, plate.getW(), plate.getX(), plate.getY() + pH), subPlate2a.getPieces()));
        }

        // On choisit la sous découpe qui renvoie le moins de chute
        // On fusionne les résultats des deux plaques de la sous découpe choisie
        // et on renvoie le résultat
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
