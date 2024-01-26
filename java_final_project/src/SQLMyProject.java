import java.io.IOException;
import java.util.Scanner;

public class SQLMyProject {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyTable newTable = new MyTable();
        try {
            newTable = ReadFromFile.readTableFromFile("/Users/juliasiger/Desktop/java_final_project/Database.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.print("Enter a string (or 'X' to exit): ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("X")) {
                break;
            }

            int action = SQLSyntaxParser.isValidSQLSyntax(userInput);

            if (newTable==null && action == 1) {
                System.out.println("create table");
                newTable = CreateTable.myCreateTable(userInput);
                System.out.println(newTable.toString());
            } else if (newTable!=null) {
                switch (action) {
                    case 1: // Create Table
                        System.out.println("Table already exists.");
                        break;
                    case 2: // Insert
                        Insert.insertData(userInput, newTable);
                        // System.out.println(newTable.toString());
                        System.out.println("Insert executed.");
                        break;
                    case 3: // Delete
                        Delete.executeDeleteStatement(userInput, newTable);
                        // System.out.println(newTable.toString());
                        System.out.println("Delete executed.");
                        break;
                    case 4: // Select
                        System.out.println("Select executed.");
                        Select newSelect = new Select(newTable);
                        newSelect.mySelect(userInput);
                        break;
                    case 5: // Update
                        Update.executeUpdate(userInput, newTable);
                        // System.out.println(newTable.toString());
                        break;
                    case 0:
                        System.out.println("No matching SQL syntax found.");
                        break;
                    default:
                        System.out.println("Input error.");
                }
            } else if (newTable==null && action != 1) {
                System.out.println("Table doesn't exist yet so you cannot perform operations on it.");
                break;
            }

            try {
                FileWriter.writeTableToFile(newTable, "Database.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        scanner.close();
    }
}
