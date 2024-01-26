import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFromFile {

    public static MyTable readTableFromFile(String filePath) throws IOException {
        MyTable table = new MyTable();
        BufferedReader br = new BufferedReader(new java.io.FileReader(filePath));

        String tableName = br.readLine();
        if (tableName == null || tableName.trim().isEmpty()) {
            br.close();
            return null;
        }
        table.setName(tableName);

        String columnTitleLine = br.readLine();
        if (columnTitleLine != null) {
            String[] titles = columnTitleLine.split("\t");
            for (String title : titles) {
                table.addColumn(title);
            }
        }

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] values = line.split("\t");
                List<Object> row = new ArrayList<>();
                for (String value : values) {
                    row.add(value);
                }
                table.addRow(row);
            }
        }
        br.close();

        return table;
    }
}
