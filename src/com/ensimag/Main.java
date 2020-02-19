package com.ensimag;

import com.ensimag.Models.Plate;

public class Main {

    public static void main(String[] args) {
	    Plate myPlate = new Plate(10, 20);
        String entriesFileName = "entries.txt";
        myPlate.getFileEntries(entriesFileName);
    }
}
