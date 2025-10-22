/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface IAccountColumnModelFinder {
    public boolean isAccountTable(ExecutorContext var1, String var2) throws Exception;

    public String getAccountTableName(String var1);

    public TableModelDefine getAccountTableModelByTableKey(ExecutorContext var1, String var2) throws Exception;

    public TableModelDefine getAccountHiTableModelByTableKey(ExecutorContext var1, String var2) throws Exception;

    public TableModelDefine getAccountRpTableModelByTableKey(ExecutorContext var1, String var2) throws Exception;

    public boolean isAccountFiled(ExecutorContext var1, String var2) throws Exception;

    public boolean isAccountColumn(ExecutorContext var1, String var2) throws Exception;

    public boolean isAccountVersionFiled(ExecutorContext var1, String var2) throws Exception;

    public boolean isAccountVersionColumn(ExecutorContext var1, String var2) throws Exception;

    public ColumnModelDefine findAccountColumnModelDefine(ExecutorContext var1, String var2) throws Exception;

    public ColumnModelDefine findAccountHiColumnModelDefine(ExecutorContext var1, String var2) throws Exception;

    public ColumnModelDefine findAccountRpColumnModelDefine(ExecutorContext var1, String var2) throws Exception;

    public List<ColumnModelDefine> getAllAccountColumnModelsByTableKey(ExecutorContext var1, String var2) throws Exception;

    public List<ColumnModelDefine> getAllAccountHiColumnModelsByTableKey(ExecutorContext var1, String var2) throws Exception;

    public List<ColumnModelDefine> getAllAccountRpColumnModelsByTableKey(ExecutorContext var1, String var2) throws Exception;

    public List<ColumnModelDefine> getAllBizkColumnByColumnId(ExecutorContext var1, String var2);

    public List<FieldDefine> getAllFieldByColumnKeys(List<String> var1);

    public boolean isBNWDColumn(String var1);

    public boolean ifTrackHistory(String var1);
}

