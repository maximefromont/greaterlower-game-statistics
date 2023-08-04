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
    for(int maximum_value = STEP_VALUE; maximum_value <= MAXIMUM_VALUE; maximum_value = maximum_value + STEP_VALUE)
    {
      HSSFRow rowhead = sheet.createRow((short)(column_number));
      column_number++;
      for(int attempts = 1; attempts <= MAXIMUM_ATTEMPT; attempts++)
      {
        int solved_ammount = 0;
        for(int game_cycle = 0; game_cycle < GAME_CYCLES; game_cycle++)
        {
          int secret_number = (int) Math.round(Math.random()*MAXIMUM_VALUE);
          if(resolveGame(secret_number))
            solved_ammount++;
        }
        rowhead.createCell(0).setCellValue(maximum_value);
        rowhead.createCell(attempts).setCellValue(solved_ammount);
      }
    }

    OutputStream fileOut = new FileOutputStream("AttemptsStatistics.xls");
    workbook.write(fileOut);
    fileOut.close();
    workbook.close();
    System.out.println("Excel File has been created successfully.");
  }

  private static final int MAXIMUM_VALUE = 50000;
  private static final int STEP_VALUE = 100;
  private static final int MAXIMUM_ATTEMPT = 10;

  private static final int GAME_CYCLES = 100;

  private static boolean resolveGame(int secret_number)
  {
    int guessed_number = -1;
    int attempts = 0;
    boolean won = false;

    int auto_min = 0;
    int auto_max = MAXIMUM_VALUE;

    //System.out.println("The secret number is " + secret_number);
    while(!won && attempts < MAXIMUM_ATTEMPT)
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