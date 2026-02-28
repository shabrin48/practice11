package shomin.algoritm.practice.p11;

import com.sun.source.tree.IfTree;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class main2 {
    static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    static Scanner sc = new Scanner(System.in);
    static int rounds = 0;
    static int time = 0;
    static int user_count_letters = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        Random rand = new Random();
        boolean running = true;

        while (running) {
            int score = 0;
            boolean start_game = false;

            System.out.println("\n=== ГРА ЗАПОМ'ЯТАЙ ЛIТЕРИ У ПРАВИЛЬНІЙ ПОСЛIДОВНОСТI ===");
            System.out.println("--= Головне меню =--");
            System.out.println("1. Обрати рiвень складностi");
            System.out.println("2. Обрати свої налаштування");
            System.out.println("3. Вийти з гри");

            int choice;
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                }catch (InputMismatchException e){
                    System.out.println("Помилка: Введіть цифру!!!");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    chooseDifficulty();
                    start_game = true;
                    break;

                case 2:
                    chooseOwnDifficulty();
                    start_game = true;
                    break;

                case 3:
                    running = false;
                    System.out.println("Бувай!");
                    break;

                default:
                    System.out.println("Невiрний вибiр!");
            }

            if (start_game) {
                char[] secret_letters = new char[user_count_letters];

                System.out.println("Гра розпочалась 😈😈😈!!!");
                Thread.sleep(1000);

                for (int i = 1; i <= rounds; i++) {
                    System.out.println("\nРаунд " + i);
                    System.out.println("Запам'ятайте лiтери!!!");
                    System.out.println();

                    for (int j = 0; j < user_count_letters; j++) {
                        char finalLetter = alphabet.charAt(rand.nextInt(alphabet.length()));
                        secret_letters[j] = finalLetter;

                        for (int k = 0; k < 15; k++) {
                            char randomChar = alphabet.charAt(rand.nextInt(alphabet.length()));

                            System.out.print("\r");
                            for (int l = 0; l < j; l++) {
                                System.out.print(secret_letters[l] + " ");
                            }
                            System.out.print(randomChar + " ");
                            System.out.flush();
                            Thread.sleep(40);
                        }
                    }
                    System.out.print("\r");
                    for (char c : secret_letters) {
                        System.out.print(c + " ");
                    }
                    System.out.println();

                    Thread.sleep(time);

                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                    System.out.println("Введiть лiтери у правильнiй послiдовностi: ");
                    String user_input = sc.nextLine();
                    if (user_input.length() > 20) {
                        System.out.println("Занадто довгий ввід!");
                        continue;
                    }
                    String secret = new String(secret_letters);

                    if (user_input.equals(secret)) {
                        score++;
                        System.out.printf("Вiрно, у вас %d балiв\n", score);
                    } else {
                        System.out.print("\007");
                        System.out.printf("Не вiрно! Було: %s. У вас %d балiв\n", secret, score);
                    }
                }

                System.out.println("\n=== Пiдсумок ===");
                if (score >= rounds - 1) {
                    System.out.printf("Вiтаю, ви перемогли! Балiв: %d/%d\n", score, rounds);
                } else {
                    System.out.printf("Нажаль, ви програли. Балiв: %d/%d\n", score, rounds);
                }
            }
        }
    }

    public static void chooseDifficulty () throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("---Оберiть рiвень складностi---");
        System.out.println("1. Легка (2 раунди, 5 сек, 3 літери)");
        System.out.println("2. Середня (4 раунди, 3 сек, 5 літер)");
        System.out.println("3. Для кiтайцив (5 раундів, 1 сек, 6 літер)");
        int difficulty;
        while (true) {
            try {
                difficulty = sc.nextInt();
                sc.nextLine();
                if (difficulty < 1 || difficulty > 3) {
                    throw new IllegalArgumentException("Введіть цифру більшу за 0 і менше ніж 3");
                }
                break;
            }catch (InputMismatchException e){
                System.out.println("Помилка: Введіть цифру!!!");
                sc.nextLine();
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        if (difficulty == 1) {
            rounds = 2; time = 5000; user_count_letters = 3;
        } else if (difficulty == 2) {
            rounds = 4; time = 3000; user_count_letters = 5;
        } else {
            rounds = 5; time = 1500; user_count_letters = 6;
        }
    }

    public static void chooseOwnDifficulty () throws InterruptedException, IOException {
        while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            try {
                System.out.println("Оберiть кiлькiсть раундiв (3, 4 або 5): ");
                rounds = sc.nextInt();
                if (rounds < 3 || rounds > 5) {
                    throw new IllegalArgumentException("Помилка: Кiлькiсть раундiв 3, 4 або 5");
                }

                System.out.println("Оберiть час (2, 3 або 5 секунд): ");
                int user_time = sc.nextInt();
                if (user_time < 2 || user_time > 5) {
                    throw new IllegalArgumentException("Помилка: час 2, 3 або 5 секунд");
                }
                time = user_time * 1000;

                System.out.println("Оберiть кiлькiсть лiтер: ");
                user_count_letters = sc.nextInt();
                if (user_count_letters < 1) {
                    throw new IllegalArgumentException("Помилка: Не буває стільки букв");
                }else if (user_count_letters > 15) {
                    throw new IllegalArgumentException("Помилка: Забагато букв (максимум 15)");
                }

                sc.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.println("Помилка: Введіть цифри!!!");
                sc.nextLine();
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}