package com.ensimag.Files;

import java.util.ArrayList;
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
}
