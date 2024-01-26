public class Delete {

    public static void executeDeleteStatement(String sqlStatement, MyTable myTable) {
        String tableName = SQLSyntaxParser.parseDeleteStatement(sqlStatement);

        if (tableName == null) {
            System.out.println("Invalid DELETE statement.");
            return;
        }

        // System.out.println("existing table = " + myTable.getName());
        // System.out.println("table = " + tableName);

        if(tableName.equals(myTable.getName())) {
            myTable.clearTable();
            System.out.println("Deleting from table: " + tableName);
        } else {
            System.out.println("Error: Table '" + tableName + "' does not exist.");
            return;
        }
    }
}
