package com.ensimag.Files;

import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;
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
    private Map<Rectangle, Integer> pieces;

    public FileIn(String _fileName) {
        this.fileName = _fileName;
        this.plates = new HashMap<>();
        this.pieces = new HashMap<>();
        this.loadEntries();
    }

    public Map<Plate, Integer> getPlatesMap() {
        return new HashMap<>(plates);
    }

    public List<Plate> getPlatesList() {
        List<Plate> platesList = new ArrayList<>(plates.keySet());
        platesList.sort(new SortByArea());
        return platesList;
    }

    public Map<Rectangle, Integer> getPiecesMap() {
        return new HashMap<>(pieces);
    }

    public List<Rectangle> getPiecesList() {
        List<Rectangle> piecesList = new ArrayList<>(pieces.keySet());
        piecesList.sort(new SortByArea());
        return piecesList;
    }

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
                    pieces.put(new Rectangle(h, w), nbPieces);
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
