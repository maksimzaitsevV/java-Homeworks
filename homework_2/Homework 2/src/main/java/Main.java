import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        var in = new Scanner(System.in);
        System.out.println("Задача 1-5 ");
        var task = in.nextInt();

        switch (task) {
            case 1 -> homework_2.Task1.main(args);
            case 2 -> homework_2.Task2.main(args);
            case 3 -> homework_2.Task3.Solution.main(args);
            case 4 -> homework_2.Task4.main(args);
            case 5 -> homework_2.Task5.DownloadFile.main(args);
            default -> System.out.println("Мимо");
        }

        in.close();
    }
}