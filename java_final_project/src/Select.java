import java.util.List;

public class Select {

    private MyTable myTable;

    public Select(MyTable table) {
        this.myTable = table;
    }

    public void mySelect(String sqlStatement) {
        List<String> columnTitles = SQLSyntaxParser.getColumnTitlesFromSelect(sqlStatement);

        List<Integer> columnIndexes = myTable.getColumnIndexes(columnTitles);

        for (List<Object> row : myTable.getData()) {
            for (Integer index : columnIndexes) {
                System.out.print(row.get(index) + " ");
            }
            System.out.println();
        }
    }
}
