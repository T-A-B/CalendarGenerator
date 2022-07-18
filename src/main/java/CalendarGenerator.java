import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CalendarGenerator {
    public static void generateCalendar(ArrayList<String> p_inputLines) throws URISyntaxException, IOException {
        URL pathURL = CalendarGenerator.class.getClassLoader().getResource("CalendarTemplate.html");

        Path path = Paths.get(pathURL.toURI());
        ArrayList<String> templateArrayList = (ArrayList<String>) Files.readAllLines(path);
 //       System.out.println(templateArrayList);
        String startDayString = null;
        String switchString = "MONDAY";

        System.out.println(p_inputLines.get(0).split("  ")[1]);

        switch (switchString){
            case "SUNDAY":
                startDayString = "DATE_NUMBER_1";
                break;
            case "MONDAY":
                startDayString = "DATE_NUMBER_2";
                break;
            case "TUESDAY":
                startDayString = "DATE_NUMBER_3";
                break;
            case "WEDNESDAY":
                startDayString = "DATE_NUMBER_4";
                break;
            case "THURSDAY":
                startDayString = "DATE_NUMBER_5";
                break;
            case "FRIDAY":
                startDayString = "DATE_NUMBER_6";
                break;
            case "SATURDAY":
                startDayString = "DATE_NUMBER_7";
                break;

        }
        ArrayList<String> outputFileLines = new ArrayList<>();
        int dateNumberIndex = Integer.parseInt(startDayString.split("_")[2]);
        System.out.println("#" + dateNumberIndex);
        int dayCount = 1;
        int inputCopyIndex = 0;
        for(String s : templateArrayList){
           String line = s.replace(("{DATE_NUMBER_" + dateNumberIndex + "}"), Integer.toString(dayCount));
            if(!line.equals(s)){
                dateNumberIndex++;
                dayCount++;
            }
            if(s.contains("STAFF_ANALYST")&& (inputCopyIndex < p_inputLines.size())){
                line = p_inputLines.get(inputCopyIndex).split("\\s+")[2] + "<br>";
            }
            if(s.contains("STAFF_APROG")&& (inputCopyIndex < p_inputLines.size())){
                line = p_inputLines.get(inputCopyIndex).split("\\s+")[3] + "<br>";
            }
            if(s.contains("STAFF_BPROG") && (inputCopyIndex < p_inputLines.size())){
                line = p_inputLines.get(inputCopyIndex).split("\\s+")[4] + "<br>";
                inputCopyIndex++;
            }


            outputFileLines.add(line);

            if(inputCopyIndex == p_inputLines.size()){
                outputFileLines.add(" </div>");
                outputFileLines.add("    <div class=\"event-time\">");
                outputFileLines.add("8:00am to 5:00pm");
                outputFileLines.add("         </div>");
                outputFileLines.add(" </div>");
                outputFileLines.add("  </li>");
                outputFileLines.add("               </div>");
                outputFileLines.add("<!-- /. calendar -->");
                outputFileLines.add("</div>");
                outputFileLines.add("<!-- /. wrap -->");
                outputFileLines.add("</body>");
                outputFileLines.add("</html>");
                break;
            }

        }

        for(String s: p_inputLines){
            System.out.println(s);
            System.out.println(startDayString);
        }
        System.out.println("Output");
        File outputFile = new File("output.html");
        outputFile.createNewFile();
        FileWriter fw = new FileWriter(outputFile);

        for(String s : outputFileLines){
            System.out.println(s);
            fw.append(s).append("\n");
        }
        fw.close();

    }

    public static void main(String[] args) throws URISyntaxException, IOException {
            generateCalendar((ArrayList<String>) Files.readAllLines(Paths.get(CalendarGenerator.class.getClassLoader().getResource("sampleInput.txt").toURI())));
    }
}
