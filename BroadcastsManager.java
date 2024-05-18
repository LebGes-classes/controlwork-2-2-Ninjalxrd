package org.example;


import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;


public class BroadcastsManager {
    List <Program> programs = new ArrayList<>();

    public Map<BroadcastsTime, List<Program>> readProgramsFromFile(String filename) {
        Map<BroadcastsTime, List<Program>> programsByTime = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentChannel = null;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")) {
                    currentChannel = line.substring(1);
                } else if (!line.isEmpty()) {
                    BroadcastsTime time = BroadcastsTime.parse(line);
                    String title = reader.readLine().trim();
                    Program program = new Program(currentChannel, time, title);
                    programsByTime.putIfAbsent(time, new ArrayList<>());
                    programsByTime.get(time).add(program);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return programsByTime;
    }
    public List<Program> getPrograms() {
        return programs;
    }
    public void readFromFile() {
        String filename = "C:\\Users\\User\\Desktop\\data.txt";
        Map<BroadcastsTime, List<Program>> programsByTime = readProgramsFromFile(filename);
        for (List<Program> programList : programsByTime.values()) {
            programs.addAll(programList);
        }
    }

    public void allPrograms() {
        Collections.sort(programs, Comparator.comparing(Program::getChannel).thenComparing(Program::getTime));
        for (Program program : programs) {
            System.out.println(program);
        }
    }

    public void programsNow () {
        BroadcastsTime now = BroadcastsTime.now();
        System.out.println("Programs now:");
        for (Program program : programs) {
            if (program.getTime().between(now, now.addMinutes(60))) {
                System.out.println(program);
            }
        }
    }
    public void programByName(String name) {
        System.out.println("Programs with name " + name + " :");
        for (Program program : programs) {
            if (program.getTitle().contains(name)) {
                System.out.println(program);
            }
        }
    }
    public void programByChannel (String channel) {
        System.out.println("Programs on channel \"" + channel + "\" now:");
        for (Program program : programs) {
            if (program.getChannel().equals(channel) && program.getTime().between(BroadcastsTime.now(), BroadcastsTime.now().addMinutes(60))) {
                System.out.println(program);
            }
        }
    }

    public void programByTime(BroadcastsTime start, BroadcastsTime end, String channel) {

        System.out.println("Programs on channel \"" + channel + "\" from " + start + " to " + end + ":");
        for (Program program : programs) {
            if (program.getChannel().equals(channel) && program.getTime().between(start, end)) {
                System.out.println(program);
            }
        }
    }
    public void writeProgramsToSheet(List<Program> programs, Sheet sheet) throws IOException {
        int rowNum = 1;
        for (Program program : programs) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(program.getChannel());
            row.createCell(1).setCellValue(program.getTime().toString());
            row.createCell(2).setCellValue(program.getTitle());
        }
    }
    public void exportProgramsToFile(Workbook workbook) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream("programs.xlsx")) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
