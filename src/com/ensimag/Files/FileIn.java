package com.ensimag.Files;

import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;
import com.ensimag.Sorts.SortByArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Class FileIn (fichier d'entrée)
 * @author Groupe6
 */
public class FileIn {
    /**
     * Nom du fichier d'entrée à lire
     */
    private String fileName;
    /**
     * Map contenant les différents types de plaques ainsi que leur quantité
     */
    private Map<Plate, Integer> plates;
    /**
     * Map contenant les différents types de pièces ainsi que leur quantité
     */
    private Map<Piece, Integer> pieces;

    /**
     * Constructeur de FileIn
     * @param _fileName : nom du fichier d'entrée à lire
     */
    public FileIn(String _fileName) {
        this.fileName = _fileName;
        this.plates = new HashMap<>();
        this.pieces = new HashMap<>();
        this.loadEntries();
    }

    /**
     * Getter de la map contenant les différents types de plaques ainsi que leur quantité
     * @return l'attribut places sous forme de HashMap
     */
    public Map<Plate, Integer> getPlatesMap() {
        return new HashMap<>(plates);
    }

    /**
     * Permet d'obtenir une liste contenant toutes les plaques, triée en fonction de leur aire
     * @return une liste contenant toutes les plaques, triée en fonction de leur aire
     */
    public List<Plate> getPlatesList() {
        List<Plate> platesList = new ArrayList<>();
        for (Plate p : plates.keySet()) {
            for (int i=0; i<plates.get(p); i++) {
                platesList.add(p);
            }
        }
        platesList.sort(new SortByArea());
        return platesList;
    }

    /**
     * Permet d'obtenir une liste contenant tous les types de plaques, triée en fonction de leur aire
     * @return liste contenant tous les types de plaques, triée en fonction de leur aire
     */
    public List<Plate> getPlateTypes() {
        List<Plate> platesList = new ArrayList<>(plates.keySet());
        platesList.sort(new SortByArea());
        return platesList;
    }

    /**
     * Getter de la map contenant les différents types de pièces ainsi que leur quantité
     * @return l'attribut pieces
     */
    public Map<Piece, Integer> getPiecesMap() {
        return new HashMap<>(pieces);
    }

    /**
     * Permet d'obtenir une liste contenant tous les types de pièces, triée en fonction de leur aire
     * @return liste contenant tous les types de pièces, triée en fonction de leur aire
     */
    public List<Piece> getPieceTypes() {
        List<Piece> piecesList = new ArrayList<>(pieces.keySet());
        piecesList.sort(new SortByArea());
        return piecesList;
    }

    /**
     *  Permet de lire et de charger les données du fichier (plaques et pièces) dans les attributs correspondants
     */
    private void loadEntries() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            int line = 0;
            String st;
            int nbTypePlates = 0, nbTypePieces = 0;
            while ((st = br.readLine()) != null) {
                StringTokenizer tk = new StringTokenizer(st);
                if (line == 0) {
                    nbTypePlates = Integer.parseInt(tk.nextToken());
                    nbTypePieces = Integer.parseInt(tk.nextToken());
                } else if (line <= nbTypePlates) {
                    int h = Integer.parseInt(tk.nextToken());
                    int w = Integer.parseInt(tk.nextToken());
                    int nbPlates = Integer.parseInt(tk.nextToken());
                    plates.put(new Plate(h, w), nbPlates);
                } else if (line <= nbTypePlates+nbTypePieces){
                    int h = Integer.parseInt(tk.nextToken());
                    int w = Integer.parseInt(tk.nextToken());
                    int nbPieces = Integer.parseInt(tk.nextToken());
                    pieces.put(new Piece(h, w), nbPieces);
                }
                //System.out.println(st);
                line++;
            }
        } catch (Exception e) {
            System.out.println("There is a problem when reading FileIn");
            e.printStackTrace();
        }
    }
}
