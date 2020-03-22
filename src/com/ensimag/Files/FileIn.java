package com.ensimag.Files;

import com.ensimag.Models.Plate;
import com.ensimag.Models.Piece;
import com.ensimag.Sorts.SortByArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created by pintodaj on 2/19/20.
 */
public class FileIn {
    private String fileName;
    private Map<Plate, Integer> plates;
    private Map<Piece, Integer> pieces;

    /**
     * Constructeur
     * @param _fileName : le nom du fichier à lire
     */
    public FileIn(String _fileName) {
        this.fileName = _fileName;
        this.plates = new HashMap<>();
        this.pieces = new HashMap<>();
        this.loadEntries();
    }

    /**
     * Getter de l'attribut plates sous forme de HashMap
     * @return l'attribut places sous forme de HashMap
     */
    public Map<Plate, Integer> getPlatesMap() {
        return new HashMap<>(plates);
    }

    /**
     * Getter d'une liste triée de plaque
     * @return une list de plaque
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
     * Getter de la liste triée des types de plaques
     * @return liste de plaque
     */
    public List<Plate> getPlateTypes() {
        List<Plate> platesList = new ArrayList<>(plates.keySet());
        platesList.sort(new SortByArea());
        return platesList;
    }

    /**
     * Getter
     * @return l'attribut pieces
     */
    public Map<Piece, Integer> getPiecesMap() {
        return new HashMap<>(pieces);
    }

    /**
     * Getter d'une liste de pièce triées par type
     * @return liste de pièce
     */
    public List<Piece> getPieceTypes() {
        List<Piece> piecesList = new ArrayList<>(pieces.keySet());
        piecesList.sort(new SortByArea());
        return piecesList;
    }

    /**
     *  Méthode qui charge les données du fichier dans les attributs correspondants de notre objet (plaques et pièces)
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
