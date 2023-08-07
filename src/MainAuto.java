import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.javafx.scene.traversal.SubSceneTraversalEngine;
import org.apache.poi.hssf.usermodel.*;

public class MainAuto
{
  public static void main(String[] args)
  throws InterruptedException, IOException
  {
    //creating an instance of HSSFWorkbook class
    HSSFWorkbook workbook = new HSSFWorkbook();
    //invoking creatSheet() method and passing the name of the sheet to be created
    HSSFSheet sheet = workbook.createSheet("StaticSheet");
    HSSFRow rowhead0 = sheet.createRow((short)0);
    rowhead0.createCell(0).setCellValue("Each cycle had " + GAME_CYCLES + " iterations");

    HSSFRow rowhead1 = sheet.createRow((short)1);
    int i = 1;
    for(int current_attempt_column = MINIMUM_ATTEMPT; current_attempt_column <= MAXIMUM_ATTEMPT; current_attempt_column+=STEP_ATTEMPT)
    {
      rowhead1.createCell(i).setCellValue(current_attempt_column);
      i++;
    }

    int row_number = 2;
    for(int current_maximum_value = STEP_VALUE; current_maximum_value <= MAXIMUM_VALUE; current_maximum_value+= STEP_VALUE)
    {
      HSSFRow rowhead = sheet.createRow((short)(row_number));
      row_number++;

      int column_number = 1;
      for(int current_maximum_attempts = MINIMUM_ATTEMPT; current_maximum_attempts <= MAXIMUM_ATTEMPT; current_maximum_attempts+=STEP_ATTEMPT)
      {
        int solved_ammount = 0;
        for(int game_cycle = 0; game_cycle < GAME_CYCLES; game_cycle++)
        {
          int secret_number = (int) Math.round(Math.random()*current_maximum_value);
          if(resolveGame(secret_number, current_maximum_attempts, STEP_VALUE, current_maximum_value, false))
            solved_ammount++;
        }
        rowhead.createCell(0).setCellValue(current_maximum_value);
        rowhead.createCell(column_number).setCellValue(solved_ammount);
        column_number++;
      }
    }

    OutputStream fileOut = new FileOutputStream("AttemptsStatistics.xls");
    workbook.write(fileOut);
    fileOut.close();
    workbook.close();
    System.out.println("Excel File has been created successfully.");
  }

  private static final int MAXIMUM_VALUE = 1000000;
  private static final int STEP_VALUE = 1000;
  private static final int MAXIMUM_ATTEMPT = 20;
  private static final int MINIMUM_ATTEMPT = 10;
  private static final int STEP_ATTEMPT = 1;

  private static final int GAME_CYCLES = 100;

  public static boolean resolveGame(int secret_number, int maximum_attempt, int minimum_value, int maximum_value, boolean showOutput)
  {
    int guessed_number = -1;
    int attempts = 0;
    boolean won = false;

    int auto_min = minimum_value;
    int auto_max = maximum_value;

    while(!won && attempts < maximum_attempt)
    {
      guessed_number = auto_min + ((auto_max-auto_min)/2);
      if(showOutput) System.out.println("Proposition : " + guessed_number);

      if(guessed_number > secret_number)
        auto_max = guessed_number;
      if(guessed_number < secret_number)
        auto_min = guessed_number;

      won = guessed_number == secret_number;

      attempts++;
    }

    if(showOutput)
    {
      if(won)
        System.out.println("The computer won in " + attempts + " attempts.");
      else
        System.out.println("The computer used his "+ attempts + " attempts and lost.");

      System.out.println("The ammounts of possible answers is " + (maximum_value-minimum_value) + ".");
      System.out.println("The last guessed answer was " + guessed_number + ". And the secret number was " + secret_number + ".");
      Double separation_percantage = (Math.abs((double) guessed_number - (double) secret_number)*100.0d)/( (double) maximum_value- (double) minimum_value);
      System.out.println("The separation percentage is " + String.format("%.5f", separation_percantage) + "%.");
    }

    return won;
  }
}