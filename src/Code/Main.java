package Code;

import Code.Model.RemittanceOperations;

import java.util.Scanner;

public class Main {
    static final String PATH_DIR_INPUT = "src\\Source\\Input";
    static final String PATH_DIR_ARCHIVE = "src\\Source\\Archive";
    static final String PATH_ACCOUNTS = "src\\Source\\Accounts.txt";
    static final String PATH_LOG = "src\\Source\\Log.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RemittanceOperations remittanceOperations = new RemittanceOperations();
        String input;

        do {
            System.out.print("""
                    "\u001B[32m"
                    Выберите действие:\s
                    1 - вызов операции парсинга файлов перевода из input
                    2 - вызов операции вывода списка всех переводов из файла-отчёта
                    Для выхода из программы введите любое другое значение
                    Ввод:\s""");

            input = scanner.nextLine();

            System.out.print("\u001B[0m");

            if (input.equals("1")) {
                remittanceOperations.parseAll(PATH_DIR_INPUT, PATH_DIR_ARCHIVE);
                remittanceOperations.submitOperationsFromFiles(PATH_DIR_ARCHIVE, PATH_ACCOUNTS, PATH_LOG);
            }
            if (input.equals("2")) {
                System.out.println("\u001B[33m" + remittanceOperations.readFromLog(PATH_LOG));
            }
        }
        while (input.equals("1") || input.equals("2"));
    }
}