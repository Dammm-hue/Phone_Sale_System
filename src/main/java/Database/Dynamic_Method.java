package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dynamic_Method {

    // -------------------------
    // 1) SELECT (Dynamic)
    // -------------------------
    public static List<Map<String, Object>> select(
            String table,
            List<String> columns,              // null = *
            String whereColumn,                // null = no where
            Object whereValue,
            String orderBy,                    // null = no order
            Integer limit                      // null = no limit
    ) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            con = DB_Connection.getConnection();

            // 1) Columns
            String colPart = "*";
            if (columns != null && !columns.isEmpty()) {
                colPart = String.join(",", columns);
            }

            // 2) Base SQL
            StringBuilder sql = new StringBuilder("SELECT " + colPart + " FROM " + table);

            // 3) WHERE
            if (whereColumn != null) {
                sql.append(" WHERE ").append(whereColumn).append("=?");
            }

            // 4) ORDER BY
            if (orderBy != null) {
                sql.append(" ORDER BY ").append(orderBy);
            }

            // 5) LIMIT
            if (limit != null) {
                sql.append(" LIMIT ").append(limit);
            }

            ps = con.prepareStatement(sql.toString());

            // Set params
            if (whereColumn != null) {
                ps.setObject(1, whereValue);
            }

            rs = ps.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= colCount; i++) {
                    String colName = meta.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    row.put(colName, value);
                }

                resultList.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }
            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }

        return resultList;
    }

    // -------------------------
    // 1) INSERT (Dynamic)
    // -------------------------
    public static int insert(String table, Map<String, Object> data) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DB_Connection.getConnection();

            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (String col : data.keySet()) {
                columns.append(col).append(",");
                values.append("?,");
            }

            columns.deleteCharAt(columns.length() - 1);
            values.deleteCharAt(values.length() - 1);

            String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")";
            ps = con.prepareStatement(sql);

            int index = 1;
            for (Object val : data.values()) {
                ps.setObject(index++, val);
            }

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }

    // -------------------------
    // 2) UPDATE (Dynamic)
    // -------------------------
    public static int update(String table, Map<String, Object> data, String whereColumn, Object whereValue) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DB_Connection.getConnection();

            StringBuilder setPart = new StringBuilder();

            for (String col : data.keySet()) {
                setPart.append(col).append("=?,");
            }
            setPart.deleteCharAt(setPart.length() - 1);

//            String sql = "UPDATE " + table + " SET " + setPart + " WHERE " + whereColumn + "=?";

            //Base SQL
            StringBuilder sql = new StringBuilder("UPDATE " + table + " SET " + setPart);

            boolean check = false;

            if (whereColumn != null && whereValue != null) {
                sql.append(" WHERE ").append(whereColumn).append("=?");
                check = true;
            }

            ps = con.prepareStatement(sql.toString());

            int index = 1;
            for (Object val : data.values()) {
                ps.setObject(index++, val);
            }

            if (check) {
                ps.setObject(index, whereValue);
            }

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }

    // -------------------------
    // 3) DELETE (Dynamic)
    // -------------------------
    public static int delete(String table, String whereColumn, Object whereValue) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DB_Connection.getConnection();

//            String sql = "DELETE FROM " + table + " WHERE " + whereColumn + "=?";

            //Base SQL
            StringBuilder sql = new StringBuilder("DELETE FROM " + table);

            boolean check = false;

            if (whereColumn != null && whereValue != null) {
                sql.append(" WHERE ").append(whereColumn).append("=?");
                check = true;
            }

            ps = con.prepareStatement(sql.toString());

            if (check) {
                ps.setObject(1, whereValue);
            }

            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (Exception ignored) {
            }
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {
            }
        }
    }
}
