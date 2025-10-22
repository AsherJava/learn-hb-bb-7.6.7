/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.KeyOrderTempTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TwoKeyTempTable;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class TempResource
implements Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TempResource.class);
    protected List<Closeable> closeable = new ArrayList<Closeable>();
    protected Set<String> tables = new HashSet<String>();
    protected final Map<String, TempAssistantTable> tempAssistantTables = new HashMap<String, TempAssistantTable>();
    protected IConnectionProvider connectionProvider;

    public void setConnectionProvider(IConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void close() throws IOException {
        for (Closeable close : this.closeable) {
            try {
                close.close();
            }
            catch (Exception e) {
                LOGGER.warn("\u8d44\u6e90\u672a\u6b63\u5e38\u5173\u95ed", e);
            }
        }
        this.closeable.clear();
        if (!CollectionUtils.isEmpty(this.tempAssistantTables)) {
            if (CollectionUtils.isEmpty(this.tables)) {
                this.tempAssistantTables.clear();
            } else {
                Iterator<Map.Entry<String, TempAssistantTable>> iterator = this.tempAssistantTables.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, TempAssistantTable> entry = iterator.next();
                    if (this.tables.contains(entry.getKey())) continue;
                    iterator.remove();
                }
            }
        }
    }

    public TempAssistantTable getTempAssistantTable(IDatabase database, String keyName, List<?> keyValue, int dimDataType) {
        if (!TempAssistantTable.supoort(dimDataType)) {
            return null;
        }
        TempAssistantTable tempAssistantTable = this.tempAssistantTables.get(keyName);
        if (keyValue != null && tempAssistantTable == null && keyValue.size() >= DataEngineUtil.getMaxInSize(database)) {
            try {
                tempAssistantTable = new TempAssistantTable(this.connectionProvider, keyValue, dimDataType);
                this.closeable.add(tempAssistantTable);
                tempAssistantTable.createTempTable();
                tempAssistantTable.insertIntoTempTable();
                this.tempAssistantTables.put(keyName, tempAssistantTable);
            }
            catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return tempAssistantTable;
    }

    public TwoKeyTempTable getTwoKeyTempTable(String cacheKey, Map<String, List<String>> keyValues) {
        TempAssistantTable tempAssistantTable = this.tempAssistantTables.get(cacheKey);
        if (tempAssistantTable instanceof TwoKeyTempTable) {
            return (TwoKeyTempTable)tempAssistantTable;
        }
        try {
            TwoKeyTempTable tempTable = new TwoKeyTempTable(this.connectionProvider, keyValues);
            this.closeable.add(tempTable);
            tempTable.createTempTable();
            tempTable.insertIntoTempTable();
            this.tempAssistantTables.put(cacheKey, tempTable);
            return tempTable;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void addTempAssistantTable(String keyName, TempAssistantTable tempAssistantTable) {
        this.tempAssistantTables.put(keyName, tempAssistantTable);
        this.tables.add(keyName);
    }

    public void removeTempAssistantTable(String keyName) {
        this.tempAssistantTables.remove(keyName);
    }

    public Map<String, TempAssistantTable> getTempAssistantTables() {
        return this.tempAssistantTables;
    }

    public TempAssistantTable getTempAssistantTable(String key) {
        return this.tempAssistantTables.get(key);
    }

    public void dropTempTable(String tempAssistantTable) throws IOException {
        TempAssistantTable table = this.tempAssistantTables.remove(tempAssistantTable);
        if (table != null) {
            table.close();
        }
    }

    public KeyOrderTempTable getKeyOrderTempTable(IDatabase database, String keyName, List<?> values, int dimDataType) {
        KeyOrderTempTable keyOrderTempTable = null;
        TempAssistantTable tempAssistantTable = this.tempAssistantTables.get(keyName);
        if (tempAssistantTable instanceof KeyOrderTempTable) {
            return (KeyOrderTempTable)tempAssistantTable;
        }
        if (values != null && tempAssistantTable == null) {
            try {
                keyOrderTempTable = new KeyOrderTempTable(this.connectionProvider, values, dimDataType);
                this.closeable.add(keyOrderTempTable);
                keyOrderTempTable.createTempTable();
                keyOrderTempTable.insertIntoTempTable();
                this.tempAssistantTables.put(keyName, keyOrderTempTable);
            }
            catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return keyOrderTempTable;
    }
}

