/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.writeback.SearchBean;
import com.jiuqi.bi.quickreport.writeback.TableInfo;
import com.jiuqi.bi.quickreport.writeback.TableModel;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFolder;
import java.util.ArrayList;
import java.util.List;

public interface IWritebackMetaDataProvider {
    public List<TableInfo> getTables(String var1) throws WritebackException;

    public List<WritebackFolder> getTableFolders(String var1) throws WritebackException;

    public TableModel getTableModel(String var1) throws WritebackException;

    public List<TableInfo> searchTables(SearchBean var1) throws WritebackException;

    default public List<WritebackFolder> getPaths(String tableName) throws WritebackException {
        return new ArrayList<WritebackFolder>();
    }
}

