/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

import com.jiuqi.nr.single.core.dbf.DbfException;
import com.jiuqi.nr.single.core.dbf.DbfField;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.nr.single.core.util.datatable.DataTable;
import java.util.List;

public interface IDbfTable {
    public long getFileSize();

    public int getDataRowCount();

    public int getDataRealRowCount();

    public void saveData() throws DbfException;

    public void dispose() throws DbfException;

    public void close() throws DbfException;

    public void closeFileStream() throws DbfException;

    public void getFileStream() throws DbfException;

    public void getWirteFileStream() throws DbfException;

    public void getDbfRecords() throws DbfException;

    public void loadDataRow(DataRow var1);

    public void loadDataRowByNames(DataRow var1, List<String> var2);

    public void loadDataRowByIndexs(DataRow var1, List<Integer> var2);

    public void clearDataRow(DataRow var1);

    public void clearDataRow(DataRow var1, boolean var2);

    public void clearAllDataRows();

    public void saveDataRow(DataRow var1);

    public DataRow getRecordByIndex(int var1);

    public void deleteRecordByIndex(int var1);

    public void setDbfReRecords() throws DbfException;

    public String getTableName();

    public void setFileName(String var1);

    public String getEncoding();

    public int getRecordLength();

    public int getFieldCount2();

    public int getRecordCount();

    public void setRecordCount(int var1);

    public boolean getIsFileOpened();

    public DataTable getTable();

    public DbfField[] geDbfFields();

    public boolean getNeedCheckCRC();

    public void setNeedCheckCRC(boolean var1);

    public void moveFirst();

    public boolean isHasLoadAllRec();

    public void setHasLoadAllRec(boolean var1);

    public int getFieldIndex(String var1);

    public List<Integer> getFieldIndexs(List<String> var1);
}

