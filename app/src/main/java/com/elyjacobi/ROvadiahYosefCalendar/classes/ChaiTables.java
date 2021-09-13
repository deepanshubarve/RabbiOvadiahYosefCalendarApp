package com.elyjacobi.ROvadiahYosefCalendar.classes;

import com.kosherjava.zmanim.hebrewcalendar.JewishCalendar;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ChaiTables {

    private static final int ROWS_UNDER_MONTHS = 31;
    private final JewishCalendar jewishCalendar;
    private final File externalFilesDir;
    private List<String[]> actualVSunriseTable;

    public ChaiTables(File externalFilesDir, JewishCalendar jewishCalendar) throws Exception {
        this.jewishCalendar = jewishCalendar;
        this.externalFilesDir = externalFilesDir;

        if (visibleSunriseFileExists()) {
            File file = new File(externalFilesDir, "visibleSunriseTable" + jewishCalendar.getJewishYear() +".csv");
            CSVReader visibleSunriseReader = new CSVReader(new FileReader(file));

            List<String[]> visibleSunriseCSVBody = visibleSunriseReader.readAll();
            actualVSunriseTable = formatToTheActualTable(visibleSunriseCSVBody);
            visibleSunriseReader.close();
        }
//        CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
//        writer.writeAll(csvBody);
//        writer.flush();
//        writer.close();
    }

    public List<String[]> formatToTheActualTable(List<String[]> csvBody) throws Exception {
        int startingRow = 0;
        int lastRow = 0;
        ListIterator<String[]> listIterator = csvBody.listIterator();
        while (listIterator.hasNext()) {
            if (Arrays.toString(listIterator.next()).contains("Tishrey")) {
                startingRow = listIterator.nextIndex()-1;
                lastRow = startingRow + ROWS_UNDER_MONTHS;
            }
            listIterator.previous();
            listIterator.next();
        }
        if (startingRow == 0 && lastRow == 0) {
            throw new Exception("Table not recognized!");
        } else {
            return csvBody.subList(startingRow, lastRow);
        }
    }

    public String getVisibleSunrise() {
        int currentHebrewMonth = jewishCalendar.getJewishMonth();

        currentHebrewMonth -= 6;
        if (currentHebrewMonth < 1) {
            if (jewishCalendar.isJewishLeapYear()){
                currentHebrewMonth += 13;
            } else {
                currentHebrewMonth += 12;
            }
        }
        return actualVSunriseTable.get(jewishCalendar.getJewishDayOfMonth())[currentHebrewMonth];
    }

    public String getVisibleSunrise(JewishCalendar jCal) {
        int currentHebrewMonth = jCal.getJewishMonth();

        currentHebrewMonth -= 6;
        if (currentHebrewMonth < 1) {
            if (jCal.isJewishLeapYear()){
                currentHebrewMonth += 13;
            } else {
                currentHebrewMonth += 12;
            }
        }
        return actualVSunriseTable.get(jCal.getJewishDayOfMonth())[currentHebrewMonth];
    }

    public boolean visibleSunriseFileExists() {
        File sunriseCSV = new File(externalFilesDir, "visibleSunriseTable" + jewishCalendar.getJewishYear() + ".csv");
        return sunriseCSV.isFile();
    }

//Not needed for now, using android's shared preferences to save year of the table
//  public String getYearOfTable() throws Exception {
//        ListIterator<String[]> listIterator = visibleSunriseCSVBody.listIterator();
//        String year = null;
//        while (listIterator.hasNext()) {
//            String s = Arrays.toString(listIterator.next());
//            String[] arrayOfString = s.split("[^0-9]");
//            for (String s2: arrayOfString) {
//                if (s2.matches("[0-9]{4}")) {
//                    year=s2;
//                }
//            }
//            listIterator.previous();
//            listIterator.next();
//        }
//        if (year == null) {
//            throw new Exception();
//        } else {
//            return year;
//        }
//    }
}
