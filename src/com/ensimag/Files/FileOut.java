package com.ensimag.Files;

import java.io.FileWriter;
import java.util.List;

/**
 * Created by pintodaj on 2/19/20.
 */
public class FileOut {
    private String fileName;
    private List<String> endResults;

    public FileOut(String _fileName, List<String> _endResults) {
        this.fileName = _fileName;
        this.endResults = _endResults;
    }

    public void writeFile() {
        try {
            FileWriter writer = new FileWriter(fileName);
            for(String str: endResults) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch(Exception e) {
            System.out.println("There is a problem in writing the files");
        }
    }


}


