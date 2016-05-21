package com.natasha.examples.models;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractDbModel {
    public abstract void crateOrUpdate(Connection conn, DbTable table) throws SQLException;
}
