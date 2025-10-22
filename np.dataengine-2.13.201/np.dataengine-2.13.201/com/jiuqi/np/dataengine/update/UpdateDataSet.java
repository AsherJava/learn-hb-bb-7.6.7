/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.update;

import com.jiuqi.np.dataengine.update.UpdateDataTable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UpdateDataSet
implements Serializable {
    private static final long serialVersionUID = 7956136121007489590L;
    private Map<String, UpdateDataTable> tables = new HashMap<String, UpdateDataTable>();
    private final boolean batchCommit;

    public UpdateDataSet() {
        this.batchCommit = true;
    }

    public UpdateDataSet(boolean batchCommit) {
        this.batchCommit = batchCommit;
    }

    public Map<String, UpdateDataTable> getTables() {
        return this.tables;
    }

    public UpdateDataTable getTable(String tableName) {
        UpdateDataTable table = this.tables.get(tableName);
        if (table == null) {
            table = new UpdateDataTable(tableName);
            this.tables.put(tableName, table);
        }
        return table;
    }

    public void reset() {
        this.tables.clear();
    }

    public byte[] serialize() {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            byte[] bytes = baos.toByteArray();
            return bytes;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static UpdateDataSet unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (UpdateDataSet)ois.readObject();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public boolean isBatchCommit() {
        return this.batchCommit;
    }

    public String toString() {
        if (this.tables.isEmpty()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        str.append("tables: \n");
        for (UpdateDataTable table : this.tables.values()) {
            str.append(table).append("\n");
        }
        return str.toString();
    }
}

