package com.ensimag.Files;

import com.ensimag.Models.CutPlate;
import com.ensimag.Models.Plate;
import com.ensimag.Models.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FileCheck {
    private String fileName;
    private ArrayList<CutPlate> cutPlate;

    public FileCheck(String _fileName) {
        this.fileName = _fileName;
        this.cutPlate = new ArrayList<>();
    }

    public boolean hasNextPlate() {
        return true;
    }

    public void nextPlate() {

    }

    public int getLost() {
        return 1;
    }

    public void loadEntries() {
        try {
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
                    pieces.put(new Plate(h, w), nbPieces);
                }
                //System.out.println(st);
                line++;
            }
        } catch (Exception e) {
            System.out.println("There is a problem with EntriesFile");
            e.printStackTrace();
        }
    }
}
