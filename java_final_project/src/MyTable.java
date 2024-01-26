import java.util.ArrayList;
import java.util.List;

public class MyTable {
    private List<String> columnTitles;
    private List<List<Object>> data;
    private String name;

    public MyTable() {
        columnTitles = new ArrayList<>();
        data = new ArrayList<>();
        name = "MyTable";
    }

    public void addColumn(String columnName) {
        columnTitles.add(columnName);
        for (List<Object> row : data) {
            row.add(null);
        }
    }

    public void addRow(List<Object> rowData) {
        if (rowData.size() != columnTitles.size()) {
            throw new IllegalArgumentException("Row data size does not match the number of columns.");
        }
        data.add(rowData);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnTitles.size();
    }

    public List<String> getColumnTitles() {
        return columnTitles;
    }

    public List<Object> getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= data.size()) {
            throw new IndexOutOfBoundsException("Row index is out of bounds.");
        }
        return data.get(rowIndex);
    }

    public String getName() {
        return name;
    }

    public void setName(String userInput) {
        name = userInput;
    }

    public void clearTable() {
        data.clear();
    }

    public List<Integer> getColumnIndexes(List<String> columnNames) {
        List<Integer> indexes = new ArrayList<>();
        for (String columnName : columnNames) {
            int index = columnTitles.indexOf(columnName);
            if (index != -1) {
                indexes.add(index);
            }
        }
        return indexes;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public boolean updateColumn(String columnName, Object newValue) {
        int columnIndex = columnTitles.indexOf(columnName);
        if (columnIndex == -1) {
            // column name doesnt exist in the table
            return false;
        }

        for (List<Object> row : data) {
            row.set(columnIndex, newValue);
        }

        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Table Name: ").append(name).append("\n");

        stringBuilder.append("Columns: ").append(columnTitles).append("\n");

        stringBuilder.append("Data:\n");
        for (List<Object> row : data) {
            stringBuilder.append(row).append("\n");
        }

        return stringBuilder.toString();
    }

}
