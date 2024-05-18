package org.example;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Programs");
      BroadcastsManager broadcastsManager = new BroadcastsManager();
        broadcastsManager.readFromFile();
        broadcastsManager.allPrograms();
        broadcastsManager.programsNow();
        broadcastsManager.writeProgramsToSheet(broadcastsManager.getPrograms(),sheet);
        broadcastsManager.exportProgramsToFile(workbook);


    }
}