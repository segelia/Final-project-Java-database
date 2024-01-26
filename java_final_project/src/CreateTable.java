import java.util.List;

public class CreateTable {

    public static MyTable myCreateTable(String sqlStatement) {

        Object[] tableDetails = SQLSyntaxParser.parseCreateTableStatement(sqlStatement);

        if (tableDetails == null) {
            throw new IllegalArgumentException("Invalid CREATE TABLE statement.");
        }

        String tableName = (String) tableDetails[0];

        @SuppressWarnings("unchecked")
        List<String> columnNames = (List<String>) tableDetails[1];

        MyTable myTable = new MyTable();

        myTable.setName(tableName);

        for (String column : columnNames) {
            myTable.addColumn(column);
        }

        return myTable;
    }
}
