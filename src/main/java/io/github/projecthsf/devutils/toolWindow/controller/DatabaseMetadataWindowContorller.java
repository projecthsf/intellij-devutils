package io.github.projecthsf.devutils.toolWindow.controller;

import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;

public class DatabaseMetadataWindowContorller extends JPanel {
    JComboBox<String> drivers = new JComboBox<>(new String[]{"MySQL", "MariaDB"});
    public DatabaseMetadataWindowContorller(@NotNull ToolWindow toolWindow) {
        setLayout(new BorderLayout());
        add(getForm(), BorderLayout.NORTH);

        String url = "jdbc:mariadb://vc-pp-hw-kyc-db-01.cdtytha5vt3v.eu-west-1.rds.amazonaws.com:3306/kyc";
        String username = "root";
        String password = "NnzcNTfxsLhaxg8J5Jpj";


        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.mariadb.jdbc.Driver");
            //Class.forName("com.mongodb.jdbc.MongoDriver");
            Connection connection = DriverManager.getConnection(url, username, password);

            DatabaseMetaData metaData = connection.getMetaData();
            String catalog = null; // Or specify a catalog name
            String schemaPattern = "%"; // Or specify a schema name pattern
            String tableNamePattern = "%"; // Or specify a table name pattern
            String[] types = {"TABLE"}; // To get only tables, or {"TABLE", "VIEW"} for tables and views

            /*
            ResultSet rs = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
            while (rs.next()) {
                System.out.println("===== rs: " + rs);
                String tableName = rs.getString("TABLE_NAME");
                System.out.println("Table Name: " + tableName);
                // You can also retrieve other information like schema, catalog, etc.
                //String schemaName = rs.getString("TABLE_SCHEM");
                //System.out.println("Schema Name: " + schemaName);
            }*/

            ResultSet columns = metaData.getColumns(null, null, "users", null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                int columnSize = columns.getInt("COLUMN_SIZE");
                String isNullable = columns.getString("IS_NULLABLE");

                System.out.println("Column Name: " + columnName +
                        ", Data Type: " + dataType +
                        ", Size: " + columnSize +
                        ", Nullable: " + isNullable);
            }

        } catch (SQLException sqlException) {
            System.out.println("===== error sqlExceptoin: " + sqlException.getMessage());
        } catch (Exception e) {
            System.out.println("MySQL error " + e.getMessage());
        }
    }

    private JPanel getForm() {
        return FormBuilder.createFormBuilder()
                .addLabeledComponent("Driver", drivers)
                .getPanel();
    }
}
