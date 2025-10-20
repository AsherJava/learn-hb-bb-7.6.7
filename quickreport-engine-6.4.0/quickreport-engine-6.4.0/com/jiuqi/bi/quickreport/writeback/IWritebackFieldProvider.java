/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.writeback.SearchBean;
import com.jiuqi.bi.quickreport.writeback.TableField;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.bi.quickreport.writeback.WritebackFolder;
import java.util.ArrayList;
import java.util.List;

public interface IWritebackFieldProvider {
    public List<TableField> getFields(String var1) throws WritebackException;

    public List<WritebackFolder> getFieldFolders(String var1) throws WritebackException;

    public List<TableField> getAllFields(String var1) throws WritebackException;

    public List<TableField> searchFields(String var1, SearchBean var2) throws WritebackException;

    default public List<WritebackFolder> getPaths(String tableName, String fieldName) throws WritebackException {
        return new ArrayList<WritebackFolder>();
    }
}

