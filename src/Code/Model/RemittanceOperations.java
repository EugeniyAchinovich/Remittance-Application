package Code.Model;

import Code.Exception.RemittanceException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemittanceOperations {
    static final Pattern ACCOUNT_PATTERN = Pattern.compile("\\b(\\w{5}-\\w{5})\\b");
    static final Pattern AMOUNT_PATTERN = Pattern.compile("^-?\\d+$");
    static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    static final String MSG_FILE_SUCCESS = "File executed successfully: ";
    static final String MSG_FILES_SUCCESS = "File executed successfully: ";
    static final String MSG_ERROR_NO_DIRECTORY = "Error: directory empty or not exist";
    static final String MSG_ERROR_FILE_EXEC = "Error while trying to execute file: ";
    static final String MSG_LOG_ACCEPT = "Успешно";
    static final String MSG_LOG_DENY = "Отклонено";
    static final String MSG_LOG_WRONG_ACCOUNT = "неверные данные аккаунта";

    public void parseAll(String sourceDirectoryPath, String targetDirectoryPath) {
        File sourceDirectory = new File(sourceDirectoryPath);
        File[] files = sourceDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                String accountContent = getFileContentByPattern(file, ACCOUNT_PATTERN);
                String amountContent = getFileContentByPattern(file, AMOUNT_PATTERN);

                String targetFilePath = targetDirectoryPath + File.separator + file.getName();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath))) {
                    writer.write(accountContent);
                    writer.write(amountContent);
                }
                catch (IOException e) {
                    System.err.println(file.getName() + ": " + e.getMessage());
                }
                System.out.println(MSG_FILE_SUCCESS + targetFilePath);
            }

            System.out.println(MSG_FILES_SUCCESS);
        }
        else
            System.err.println(MSG_ERROR_NO_DIRECTORY);
    }

    private static String getFileContentByPattern(File file, Pattern pattern) {
        StringBuilder parsedContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find())
                    parsedContent.append(matcher.group()).append("\n");
            }
        }
        catch (IOException e) {
            System.err.println(MSG_ERROR_FILE_EXEC + file.getName() + ": " + e.getMessage());
        }

        return parsedContent.toString();
    }

    public ArrayList<Account> fileToList(String filePath) {
        ArrayList<Account> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                String accountNumber = line.split(" ")[0];
                int accountBalance = Integer.parseInt(line.split(" ")[1]);

                line = reader.readLine();

                accounts.add(new Account(accountNumber, accountBalance));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    public void listToFile(ArrayList<Account> accounts, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Account account : accounts) {
                writer.write(account.getAccountNumber() + " " + account.getAccountBalance());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToLog(String logFilePath, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(message);
            writer.newLine();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readFromLog(String logFilePath) {
        StringBuilder logData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line = reader.readLine();
            while (line != null) {
                logData.append(line).append("\n");
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return logData.toString();
    }

    public int findAccountIndexByNumber(ArrayList<Account> accounts, String accountNumber) {
        for (int i = 0; i < accounts.size(); i++)
            if (accounts.get(i).getAccountNumber().equals(accountNumber))
                return i;
        return -1;
    }

    public void submitOperationsFromFiles(String archiveDirectoryPath, String accountsFilePath, String logFilePath) {
        ArrayList<Account> accounts = fileToList(accountsFilePath);

        String accountNumberFrom = "";
        String accountNumberTo = "";
        int amount = 0;

        File folder = new File(archiveDirectoryPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line = reader.readLine();
                        while (line != null) {
                            accountNumberFrom = line;
                            line = reader.readLine();
                            accountNumberTo = line;
                            line = reader.readLine();
                            if (line != null)
                                amount = Integer.parseInt(line);
                            line = reader.readLine();
                        }
                        System.out.println("File " + file.getName() + " has been read.");
                    }
                    catch (IOException e) {
                        System.out.println("Error reading " + file.getName() + ": " + e.getMessage());
                    }
                    int indexFrom = findAccountIndexByNumber(accounts, accountNumberFrom);
                    int indexTo = findAccountIndexByNumber(accounts, accountNumberTo);

                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
                    String formattedDate = dateFormat.format(currentDate);

                    String logMessage =
                            formattedDate
                            + " | файл: " + file.getName()
                            + "\t | перевод c " + accountNumberFrom + " на " + accountNumberTo
                            + "\t | сумма: " + amount;

                    try {
                        if (indexFrom > 0 && indexTo > 0) {
                            accounts.get(indexFrom).changeAccountBalance(amount, false);
                            accounts.get(indexTo).changeAccountBalance(amount, true);
                            logMessage += "\t | " + MSG_LOG_ACCEPT;
                        }
                        else
                            logMessage += "\t | " + MSG_LOG_DENY + ": " + MSG_LOG_WRONG_ACCOUNT;
                    }
                    catch (RemittanceException e) {
                        logMessage += "\t | " + MSG_LOG_DENY + ": " + e.getMessage();
                    }

                    writeToLog(logFilePath, logMessage);
                }
            }
        }
        else {
            System.out.println(MSG_ERROR_NO_DIRECTORY);
        }
        writeToLog(logFilePath, "");
        listToFile(accounts, accountsFilePath);
    }
}


