import java.util.List;
import java.util.ArrayList;

public class Insert {

    public static void insertData(String sqlStatement, MyTable table) {

        Object[] parsedData = SQLSyntaxParser.parseInsertStatement(sqlStatement);
        if (parsedData == null) {
            System.out.println("Invalid SQL syntax.");
            return;
        }

        String tableName = (String) parsedData[0];

        @SuppressWarnings("unchecked")
        List<String> givenColumnTitles = (List<String>) parsedData[1];
        List<String> actualColumnTitles = table.getColumnTitles();

        @SuppressWarnings("unchecked")
        List<Object> values = (List<Object>) parsedData[2];

        if (!table.getName().equals(tableName)) {
            System.out.println("Table name does not match.");
            return;
        }

        if (givenColumnTitles.size() != actualColumnTitles.size() || values.size() != actualColumnTitles.size()) {
            System.out.println("The number of provided columns or values does not match the actual table.");
            return;
        }

        List<Object> orderedValues = new ArrayList<>();
        for (String columnTitle : actualColumnTitles) {
            orderedValues.add(null);
        }

        for (int i = 0; i < givenColumnTitles.size(); i++) {
            String givenColumnTitle = givenColumnTitles.get(i);
            int columnIndex = actualColumnTitles.indexOf(givenColumnTitle);
            if (columnIndex != -1) { // column title found in the table
                orderedValues.set(columnIndex, values.get(i));
            }
        }

        table.addRow(orderedValues);
    }
}
