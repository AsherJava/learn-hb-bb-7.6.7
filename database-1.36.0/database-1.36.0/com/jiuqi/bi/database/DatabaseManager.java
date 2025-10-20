/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database;

import com.jiuqi.bi.database.DefaultDatabase;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.desc.IDatabaseDescriptor;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.database.syntax.ISyntaxInterpretor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public class DatabaseManager {
    private static final DatabaseManager instance = new DatabaseManager();
    private Map<String, IDatabase> dbMap = new HashMap<String, IDatabase>();
    private final IDatabase defaultDatabase = new DefaultDatabase();

    private DatabaseManager() {
        this.autoRegist();
    }

    private void autoRegist() {
        try {
            ServiceLoader<IDatabase> loader = ServiceLoader.load(IDatabase.class);
            Iterator<IDatabase> iterator = loader.iterator();
            while (true) {
                try {
                    while (iterator.hasNext()) {
                        IDatabase database = iterator.next();
                        this.regDatabase(database);
                    }
                }
                catch (Throwable t) {
                    t.printStackTrace();
                    continue;
                }
                break;
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static final DatabaseManager getInstance() {
        return instance;
    }

    public Iterator<IDatabase> getAllDatabase() {
        ArrayList<IDatabase> vals = new ArrayList<IDatabase>(this.dbMap.values());
        Collections.sort(vals, new Comparator<IDatabase>(){

            @Override
            public int compare(IDatabase o1, IDatabase o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return vals.iterator();
    }

    public IDatabase getDefaultDatabase() {
        return this.defaultDatabase;
    }

    public void regDatabase(IDatabase database) {
        if (database == null || database.getName() == null) {
            throw new NullPointerException();
        }
        this.dbMap.put(database.getName().toUpperCase(), database);
    }

    public IDatabase findDatabaseByName(String databaseName) {
        if (databaseName == null) {
            throw new NullPointerException();
        }
        IDatabase database = this.dbMap.get(databaseName.toUpperCase());
        return database == null ? this.getDefaultDatabase() : database;
    }

    public IDatabase findDatabaseByURL(String url) {
        for (IDatabase database : this.dbMap.values()) {
            if (!database.match(url)) continue;
            return database;
        }
        return this.getDefaultDatabase();
    }

    public IDatabase findDatabaseByConnection(Connection conn) throws SQLException {
        SQLException lastException = null;
        for (IDatabase database : this.dbMap.values()) {
            try {
                if (!database.match(conn)) continue;
                return database;
            }
            catch (SQLException e) {
                lastException = e;
            }
        }
        if (lastException != null) {
            throw lastException;
        }
        return this.getDefaultDatabase();
    }

    public ISQLMetadata createMetadata(Connection conn) throws SQLException {
        IDatabase database = this.findDatabaseByConnection(conn);
        return database.createMetadata(conn);
    }

    public IDatabaseDescriptor getDescriptor(Connection conn) throws SQLException {
        IDatabase database = this.findDatabaseByConnection(conn);
        return database.getDescriptor();
    }

    public IDatabaseDescriptor getDescriptorByURL(String url) {
        IDatabase database = this.findDatabaseByURL(url);
        return database.getDescriptor();
    }

    public IDatabaseDescriptor getDescriptorByName(String databaseName) {
        IDatabase database = this.findDatabaseByName(databaseName);
        return database.getDescriptor();
    }

    public ISQLInterpretor getSQLInterpretor(Connection conn) throws SQLException, SQLInterpretException {
        IDatabase database = this.findDatabaseByConnection(conn);
        return database.createSQLInterpretor(conn);
    }

    public ISyntaxInterpretor getSyntaxInterpretor(Connection conn) throws SQLException {
        IDatabase database = this.findDatabaseByConnection(conn);
        return database.getSyntaxInterpretor();
    }

    public ISyntaxInterpretor getSyntaxInterpretorByURL(String url) {
        IDatabase database = this.findDatabaseByURL(url);
        return database.getSyntaxInterpretor();
    }

    public ISyntaxInterpretor getSyntaxInterpretorByName(String databaseName) {
        IDatabase database = this.findDatabaseByName(databaseName);
        return database.getSyntaxInterpretor();
    }
}

