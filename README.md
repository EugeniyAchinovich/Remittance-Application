# Remittance application

## Description
The program is a console-based Java application that provides a simple implementation for handling transfers between virtual bank accounts.

The account data is stored in a separate file, while information about upcoming transfers is located in files within a separate directory.

The program allows the user to choose specific functions by entering parameters into the console.

During execution, the program interacts with files by creating, reading, writing, and editing them.

## Installation and Launch
To run the application, the project needs to be downloaded to the device and opened in Intellij Idea IDE. Then, the main file of the program needs to be launched.

## User Guide
Upon launch, the program immediately expects the user to input parameters into the console, displaying possible functions beforehand:

If the user enters "1", the program retrieves necessary data from the files in the input directory and writes them to the files in the archive directory. It then performs fund transfers between virtual accounts based on the received data. During operation, the program displays the success status of each file operation on the screen. After the processes are complete, the account data file and the report file are updated.

If the user enters "2", the program reads the data from the report file (log) and displays all the information on the screen.

If the user enters any other value, the program exits.

Input parameters in the program are looped and repeated until the user inputs "1" or "2".

## Project Architecture
The project architecture is divided into layers, primarily "Code" and "Source," containing program code and files, respectively.

The "Code" directory is divided into "Model" and "Exception" layers.

"Model" contains the bank account class and the class for banking operations and files.

"Exception" contains custom exception classes that may occur under specific conditions of fund transfers between accounts.

"Main" is the main file of the program, describing the user interaction logic and the invocation of specific functions.

#### Dependencies UML Diagram:
![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/a1b4982f-dc23-4462-82ca-4129cb36a6d2)


The "Source" directory contains the account data file "Accounts.txt" and the report file "Log.txt". It also includes two directories, "Input" and "Archive". "Input" contains files created by the user with information about transfers. The input files contain lines with account numbers between which the transfer should occur, as well as the transfer amount. The files may also contain other lines. "Archive" contains files created after parsing the files from the "Input" directory and only includes the necessary three lines: the account number from which funds are debited, the account number to which funds are credited, and the transfer amount.

## Program Screenshots
The account list follows the format:

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/8373bbe7-d6c9-4d8c-bb2d-84f6e02a0f46)

Example of input data in the payment file in the "Input" directory:

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/b783cf60-70dc-4278-b622-3c619ec80c3c)

Example of the output payment file after parsing in the "Archive" directory:

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/b783cf60-70dc-4278-b622-3c619ec80c3c)

Selection of the parsing function in the console when entering "1":

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/5d4fac5c-1582-4194-af56-9c4dbc4e2907)

Selection of the report file display function in the console when entering "2":

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/715d318d-4e0e-4404-9b37-c3b3b1d79cde)

Example of the generated report file:

![image](https://github.com/EugeniyAchinovich/Remittance-Application/assets/75760235/ab41db6f-81f1-4852-b093-9c4c01409316)
