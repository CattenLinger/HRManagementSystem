package database;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by catten on 15/7/3.
 */
public interface SQLConnector {
    Statement getStatement();
    Connection getConnection();
}
