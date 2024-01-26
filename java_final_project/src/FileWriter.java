import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class FileWriter {

    public static void writeTableToFile(MyTable table, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filename))) {
            writer.write(table.getName());
            writer.newLine();
            
            List<String> columnTitles = table.getColumnTitles();
            for (String title : columnTitles) {
                writer.write(title + "\t");
            }
            writer.newLine();

            List<List<Object>> data = table.getData();
            for (List<Object> row : data) {
                for (Object cell : row) {
                    writer.write((cell == null ? "" : cell.toString()) + "\t");
                }
                writer.newLine();
            }
        }
    }
}
