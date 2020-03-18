package com.ensimag.Utils;

import com.ensimag.Files.FileCheck;
import com.ensimag.Models.CutPlate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Checker {
    private FileCheck fileCheck;
    private CutPlate plate;
    private Map<Integer, Object> xPieces;
    private Map<Integer, Object> yPieces;

    public Checker(FileCheck _fileCheck) {
        this.fileCheck = _fileCheck;
        this.xPieces = new HashMap<>();
        this.yPieces = new HashMap<>();
        this.plate = new CutPlate();
    }

    public void start() {
        boolean checked = true;
        while (this.fileCheck.hasNextPlate() && checked) {
            this.plate = this.fileCheck.nextPlate();
            if (!this.checkX() || !this.checkY()) {
                checked = false;
            }
        }
        if (checked) {
            System.out.println("All is ok !");
        } else {
            System.out.println("NOK !");
        }
    }

    public boolean checkX() {
        return true;
    }

    public boolean checkY() {
        return true;
    }
}
