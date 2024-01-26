import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLSyntaxParser {

    public static Integer isValidSQLSyntax(String sqlStatement) {

        String insertPattern = "^(?i)\\s*INSERT\\s+INTO\\s+\\w+\\s*\\(\\s*(\\w+\\s*(,\\s*\\w+\\s*)*)\\)\\s*VALUES\\s*\\(\\s*([^)]+?)\\s*\\)\\s*;?$";
        String createTablePattern = "^(?i)\\s*CREATE\\s+TABLE\\s+.*$";
        String deletePattern = "^(?i)\\s*DELETE\\s+FROM\\s+.*$";
        String selectPattern = "^(?i)\\s*SELECT\\s+.*$";
        String updatePattern = "^(?i)\\s*UPDATE\\s+\\w+\\s+SET\\s+\\w+\\s*=\\s*'[^']+'\\s*;?$";

        Pattern insertRegex = Pattern.compile(insertPattern);
        Pattern createTableRegex = Pattern.compile(createTablePattern);
        Pattern deleteRegex = Pattern.compile(deletePattern);
        Pattern selectRegex = Pattern.compile(selectPattern);
        Pattern updateRegex = Pattern.compile(updatePattern);

        Matcher insertMatcher = insertRegex.matcher(sqlStatement);
        Matcher createTableMatcher = createTableRegex.matcher(sqlStatement);
        Matcher deleteMatcher = deleteRegex.matcher(sqlStatement);
        Matcher selectMatcher = selectRegex.matcher(sqlStatement);
        Matcher updateMatcher = updateRegex.matcher(sqlStatement);

        if (createTableMatcher.matches()) {
            return 1;
        } else if (insertMatcher.matches()) {
            return 2;
        } else if (deleteMatcher.matches()) {
            return 3;
        } else if (selectMatcher.matches()) {
            return 4;
        } else if (updateMatcher.matches()) {
            return 5;
        } else {
            return 0;
        }
    }

    public static Object[] parseInsertStatement(String sqlInsertStatement) {
        Pattern tableNamePattern = Pattern.compile("INSERT INTO ([a-zA-Z_]+)");
        Pattern columnsPattern = Pattern.compile("\\(([^)]+)\\)");
        Pattern valuesPattern = Pattern.compile("VALUES \\(([^)]+)\\)");

        Matcher tableNameMatcher = tableNamePattern.matcher(sqlInsertStatement);
        Matcher columnsMatcher = columnsPattern.matcher(sqlInsertStatement);
        Matcher valuesMatcher = valuesPattern.matcher(sqlInsertStatement);

        if (tableNameMatcher.find() && columnsMatcher.find() && valuesMatcher.find()) {
            String tableName = tableNameMatcher.group(1);

            String[] columnTitlesArray = columnsMatcher.group(1).split(",");
            List<String> columnTitles = new ArrayList<>();
            for (String columnTitle : columnTitlesArray) {
                columnTitles.add(columnTitle.trim());
            }

            // Extract values
            String[] valuesArray = valuesMatcher.group(1).split(",");
            List<String> values = new ArrayList<>();
            for (String value : valuesArray) {
                String trimmedValue = value.trim();

                if (trimmedValue.startsWith("'") && trimmedValue.endsWith("'")) {
                    trimmedValue = trimmedValue.substring(1, trimmedValue.length() - 1);
                }
                values.add(trimmedValue);
            }

            return new Object[] { tableName, columnTitles, values };

        } else {
            return null;
        }
    }

    public static Object[] parseCreateTableStatement(String sqlCreateTableStatement) {
        Pattern tableNamePattern = Pattern.compile("CREATE TABLE ([a-zA-Z_]+)");
        Pattern columnsPattern = Pattern.compile("\\(([^)]+)\\)");

        Matcher tableNameMatcher = tableNamePattern.matcher(sqlCreateTableStatement);
        Matcher columnsMatcher = columnsPattern.matcher(sqlCreateTableStatement);

        if (tableNameMatcher.find() && columnsMatcher.find()) {
            // Extract table name
            String tableName = tableNameMatcher.group(1);

            // Extract column definitions
            String columnDefinitions = columnsMatcher.group(1);
            String[] columnsArray = columnDefinitions.split(",");

            // Store column names in a list
            List<String> columnList = new ArrayList<>();
            for (String column : columnsArray) {
                String columnName = column.trim().split("\\s+")[0];
                columnList.add(columnName);
            }

            return new Object[] { tableName, columnList };
        } else {
            return null;
        }
    }

    public static List<String> getColumnTitlesFromSelect(String sqlStatement) {
        List<String> columnTitles = new ArrayList<>();
        String selectPattern = "^(?i)\\s*SELECT\\s+(.*)$";
        Pattern pattern = Pattern.compile(selectPattern);
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            String columnsPart = matcher.group(1);
            String[] columns = columnsPart.split("\\s*,\\s*");
            for (String column : columns) {
                columnTitles.add(column.trim());
            }
        }

        return columnTitles;
    }

    public static String[] parseUpdateStatement(String sqlStatement) {
        String updatePattern = "^(?i)\\s*UPDATE\\s+\\w+\\s+SET\\s+(\\w+)='([^']+)';?.*$";
        Pattern updateRegex = Pattern.compile(updatePattern);
        Matcher updateMatcher = updateRegex.matcher(sqlStatement);

        if (updateMatcher.matches()) {
            String columnName = updateMatcher.group(1);
            String value = updateMatcher.group(2);
            return new String[] { columnName, value };
        } else {
            return new String[]{"Invalid Statement"};
        }
    }
    
    public static String parseDeleteStatement(String sqlStatement) {
        String deletePattern = "^(?i)\\s*DELETE\\s+FROM\\s+(\\w+).*";
        Pattern pattern = Pattern.compile(deletePattern);
        Matcher matcher = pattern.matcher(sqlStatement);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

}
