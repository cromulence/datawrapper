package net.cromulence.datawrapper.postgres;

import net.cromulence.datawrapper.AbstractStringToStringDataStoreConnector;
import net.cromulence.datawrapper.DataWrapperException;
import net.cromulence.datawrapper.DataWrapperRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresDataStoreConnector extends AbstractStringToStringDataStoreConnector {

    private static final Logger LOG = LoggerFactory.getLogger(PostgresDataStoreConnector.class);
    private static final String COL_KEY   = "kvpkey";
    private static final String COL_VALUE = "kvpvalue";

    private static final int FIRST = 1;

    private String host;
    private int    port;
    private String path;
    private String username;
    private String password;
    private String schemaName;
    private String tableName;

    private Connection connection;

    public PostgresDataStoreConnector(String host, int port, String path, String username, String password, String schemaName, String tableName) throws DataWrapperException{
        this.host = host;
        this.port = port;
        this.path = path;
        this.username = username;
        this.password = password;
        this.schemaName = schemaName;
        this.tableName = tableName;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No PG Driver found", e);
        }

        Connection conn;
        try {
            conn = getConnection();

            if(!tableExists(conn)) {
                createKvpTable(conn);
            }
        } catch (Exception e) {
            throw new DataWrapperException("Unable to connect to database", e);
        }
    }

    public PostgresDataStoreConnector(URI databaseUri, String schemaName, String tableName) throws DataWrapperException{
        this(databaseUri.getHost(), databaseUri.getPort(), databaseUri.getPath(), databaseUri.getUserInfo().split(":")[0], databaseUri.getUserInfo().split(":")[1], schemaName, tableName);
    }

    private Connection getConnection() throws URISyntaxException, SQLException {
        return getConnection(host, port, path, username, password);
    }

    private synchronized Connection getConnection(String host, int port, String path, String username, String password) throws URISyntaxException, SQLException {
        if(connection != null && connection.isValid(100)) {
            return connection;
        }

        String dbUrl = "jdbc:postgresql://" + host + ':' + port + path + "?sslmode=require";
        this.connection = DriverManager.getConnection(dbUrl, username, password);
        return connection;
    }

    private boolean tableExists(Connection conn) throws SQLException {

        String sql = "" +
                "SELECT EXISTS(" +
                "	    SELECT * " +
                "	    FROM information_schema.tables" +
                "	    WHERE " +
                "	      table_schema = '%s' AND" +
                "	      table_name = '%s'" +
                "	)";


        ResultSet resultSet = conn.createStatement().executeQuery(String.format(sql,  schemaName, tableName));

        if(resultSet.next()) {
            return resultSet.getBoolean(FIRST);
        }

        return false;
    }

    private void createKvpTable(Connection conn) throws SQLException {

        String sql = "" +
                "CREATE TABLE %s.%s" +
                "(" +
                "   \"%s\" character varying(64) UNIQUE NOT NULL PRIMARY KEY," +
                "   \"%s\" character varying(256)" +
                ")";

        int executeUpdate = conn.createStatement().executeUpdate(String.format(sql, schemaName, tableName, COL_KEY, COL_VALUE));

        System.out.println(executeUpdate);
    }

    @Override
    public void remove(String name) {

        final String sql = "" +
                "DELETE FROM %s.%s where %s = '%s'";

        final String query = String.format(sql, schemaName, tableName, COL_KEY, name);

        try {
            int executeUpdate = getConnection().createStatement().executeUpdate(query);
        } catch (Exception e) {
            throw new DataWrapperRuntimeException("Unable to get " + name, e);
        }
    }

    @Override
    public void doPut(String name, String value) {

        final String sqlInsert = "" +
                "INSERT INTO %s.%s VALUES('%s','%s')";

        final String sqlUpdate = "" +
                "UPDATE %s.%s set %s = '%s' WHERE (%s = '%s')";

        final String insertQuery = String.format(sqlInsert, schemaName, tableName, name, value);

        final String updateQuery = String.format(sqlUpdate, schemaName, tableName, COL_VALUE, value, COL_KEY, name);

        try {
            int updateRows = getConnection().createStatement().executeUpdate(updateQuery);

            if(updateRows == 0) {
                int insertRows = getConnection().createStatement().executeUpdate(insertQuery);
            }
        } catch (Exception e) {
            throw new DataWrapperRuntimeException("Unable to put " + name, e);
        }
    }

    @Override
    public String doGet(String name) {

        final String sql = "" +
                "SELECT %s FROM %s.%s WHERE %s = '%s'";

        final String query = String.format(sql, COL_VALUE, schemaName, tableName, COL_KEY, name);

        try {
            final ResultSet result = getConnection().createStatement().executeQuery(query);

            if(result.next()) {
                return result.getString(1);
            }

            return null;

        } catch (Exception e) {
            throw new DataWrapperRuntimeException("Unable to get " + name, e);
        }
    }
}
