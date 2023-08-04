import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    for(int j = 1; j <= MAXIMUM_ATTEMPT; j++)
    {
      rowhead1.createCell(j).setCellValue(j);
    }

    int column_number = 2;
    for(int current_maximum_value = STEP_VALUE; current_maximum_value <= MAXIMUM_VALUE; current_maximum_value = current_maximum_value + STEP_VALUE)
    {
      HSSFRow rowhead = sheet.createRow((short)(column_number));
      column_number++;
      for(int current_maximum_attempts = 1; current_maximum_attempts <= MAXIMUM_ATTEMPT; current_maximum_attempts++)
      {
        int solved_ammount = 0;
        for(int game_cycle = 0; game_cycle < GAME_CYCLES; game_cycle++)
        {
          int secret_number = (int) Math.round(Math.random()*current_maximum_value);
          if(resolveGame(secret_number, current_maximum_attempts, current_maximum_value))
            solved_ammount++;
        }
        rowhead.createCell(0).setCellValue(current_maximum_value);
        rowhead.createCell(current_maximum_attempts).setCellValue(solved_ammount);
      }
    }

    OutputStream fileOut = new FileOutputStream("AttemptsStatistics.xls");
    workbook.write(fileOut);
    fileOut.close();
    workbook.close();
    System.out.println("Excel File has been created successfully.");
  }

  private static final int MAXIMUM_VALUE = 100;
  private static final int STEP_VALUE = 10;
  private static final int MAXIMUM_ATTEMPT = 10;

  private static final int GAME_CYCLES = 1000;

  private static boolean resolveGame(int secret_number, int maximum_attempt, int maximum_value)
  {
    int guessed_number = -1;
    int attempts = 0;
    boolean won = false;

    int auto_min = 0;
    int auto_max = maximum_value;

    //System.out.println("The secret number is " + secret_number);
    while(!won && attempts < maximum_attempt)
    {
      guessed_number = auto_min + ((auto_max-auto_min)/2);
      //System.out.println("Proposition : " + guessed_number);

      if(guessed_number > secret_number)
        auto_max = guessed_number;
      if(guessed_number < secret_number)
        auto_min = guessed_number;

      won = guessed_number == secret_number;

      attempts++;
    }

    /*
    if(won)
      System.out.println("The computer won in " + attempts + " attempts.");
    else
      System.out.println("The computer lost.");
     */

    return won;
  }
}