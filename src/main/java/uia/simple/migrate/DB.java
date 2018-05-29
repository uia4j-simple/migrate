package uia.simple.migrate;

import java.sql.SQLException;

import uia.utils.dao.Database;
import uia.utils.dao.hana.Hana;
import uia.utils.dao.ora.Oracle;
import uia.utils.dao.pg.PostgreSQL;

public class DB {

    public static Database connect(String key) throws SQLException {
        String type = System.getProperty(String.format("db.%s.type", key));
        String host = System.getProperty(String.format("db.%s.host", key));
        String port = System.getProperty(String.format("db.%s.port", key));
        String schema = System.getProperty(String.format("db.%s.schema", key));
        String service = System.getProperty(String.format("db.%s.service", key));
        String user = System.getProperty(String.format("db.%s.user", key));
        String password = System.getProperty(String.format("db.%s.password", key));

        if ("HANA".equalsIgnoreCase(type)) {
            return hana(host, port, schema, service, user, password);
        }

        if ("ORA".equalsIgnoreCase(type)) {
            return ora(host, port, schema, service, user, password);
        }

        if ("PG".equalsIgnoreCase(type)) {
            return pg(host, port, schema, service, user, password);
        }

        return null;
    }

    private static Database hana(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new Hana(host, port, service, user, password);
    }

    private static Database ora(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new Oracle(host, port, service, user, password);
    }

    private static Database pg(String host, String port, String schema, String service, String user, String password) throws SQLException {
        return new PostgreSQL(host, port, service, user, password);
    }
}
