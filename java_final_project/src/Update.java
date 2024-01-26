public class Update {

    public static void executeUpdate(String sqlStatement, MyTable table) {
        String[] parsedStatement = SQLSyntaxParser.parseUpdateStatement(sqlStatement);

        if (parsedStatement.length != 2 || parsedStatement[0].equals("Invalid Statement")) {
            System.out.println("Invalid update statement.");
            return;
        }

        String columnName = parsedStatement[0];
        String newValue = parsedStatement[1];

        if (table.updateColumn(columnName, newValue)) {
            System.out.println("Update executed.");
        } else {
            System.out.println("Failed to execute update.");
        }
    }
}

