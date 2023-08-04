import java.util.Scanner;

public class Main
{
  public static void main(String[] args)
  {
    int secret_number = (int) Math.round(Math.random()*MAXIMUM_VALUE);
    int guessed_number = -1;
    int attempts = 0;
    boolean won = false;

    Scanner scanner = new Scanner(System.in);
    while(!won && attempts < MAXIMUM_ATTEMPT)
    {
      System.out.print("Proposition : ");
      guessed_number = scanner.nextInt();

      if(guessed_number > secret_number)
        System.out.println("Lower");
      if(guessed_number < secret_number)
        System.out.println("Greater");

      won = guessed_number == secret_number;

      attempts++;
    }

    if(won)
      System.out.println("You won in " + attempts + " attempts.");
    else
    {
      System.out.println("You lost.");
      System.out.println("The solution was " + secret_number + ".");
    }
  }

  private static final int MAXIMUM_VALUE = 50000;
  private static final int MAXIMUM_ATTEMPT = 10;
}