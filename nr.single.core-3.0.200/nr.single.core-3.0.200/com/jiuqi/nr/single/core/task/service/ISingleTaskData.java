/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.nr.single.core.task.service;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.model.SingleAttachInfo;
import com.jiuqi.nr.single.core.task.model.SingleFieldInfo;
import com.jiuqi.nr.single.core.task.model.SinglePeriodDataItem;
import com.jiuqi.nr.single.core.task.model.SingleTaskInfo;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataHandler;
import com.jiuqi.nr.single.core.task.service.ISingleTableDataWriter;
import com.jiuqi.nr.single.core.task.service.ISingleTaskParamReader;
import java.util.List;

public interface ISingleTaskData {
    public void zipToSingleFile(String var1) throws SingleTaskException;

    public void createDBf(String var1, List<SingleFieldInfo> var2) throws SingleTaskException;

    public ISingleTableDataWriter createTableWriter(String var1, List<String> var2) throws SingleTaskException;

    public ISingleTableDataWriter createTableWriter(String var1, int var2, List<String> var3) throws SingleTaskException;

    public ISingleTableDataWriter createDbfWriter(String var1, List<String> var2) throws SingleTaskException;

    public boolean readTableData(String var1, ISingleTableDataHandler var2) throws SingleTaskException;

    public boolean readTableData(String var1, int var2, ISingleTableDataHandler var3) throws SingleTaskException;

    public boolean readDbfData(String var1, ISingleTableDataHandler var2) throws SingleTaskException;

    public List<SinglePeriodDataItem> readEntityList(String var1) throws SingleTaskException;

    public String getTextFieldValue(DataRow var1, int var2) throws SingleTaskException;

    public SingleAttachInfo getDocFieldValue(DataRow var1, int var2, boolean var3) throws SingleTaskException;

    public SingleAttachInfo getDocTableValue(String var1, String var2, boolean var3) throws SingleTaskException;

    public String getTaskDir();

    public String getTaskParaDir();

    public String getTaskDataDir();

    public String getTaskTempDir();

    public String getTaskDataDocDir();

    public String getTaskDataTextDir();

    public SingleTaskInfo getTaskInfo();

    public ISingleTaskParamReader getParamReader() throws SingleTaskException;

    public void close();
}

