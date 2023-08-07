import java.util.Scanner;

public class TestMain
{
  public static void main(String[] args)
  {
    int minimum_value = 1;
    int maximum_value = 10000000;
    int maximum_attempts = 20;
    int secret_number = (int) Math.round(Math.random()*maximum_value);
    MainAuto.resolveGame(secret_number, maximum_attempts, minimum_value, maximum_value, true);
  }
}