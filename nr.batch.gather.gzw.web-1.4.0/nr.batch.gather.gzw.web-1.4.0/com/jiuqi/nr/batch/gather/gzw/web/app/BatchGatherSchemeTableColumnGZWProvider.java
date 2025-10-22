/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.table.ITableColumnProvider
 *  com.jiuqi.nvwa.resourceview.table.TableColumnDefine
 *  com.jiuqi.nvwa.resourceview.table.TableUtil
 */
package com.jiuqi.nr.batch.gather.gzw.web.app;

import com.jiuqi.nvwa.resourceview.table.ITableColumnProvider;
import com.jiuqi.nvwa.resourceview.table.TableColumnDefine;
import com.jiuqi.nvwa.resourceview.table.TableUtil;
import java.util.ArrayList;
import java.util.List;

public class BatchGatherSchemeTableColumnGZWProvider
implements ITableColumnProvider {
    public List<TableColumnDefine> getTableColumns() {
        ArrayList<TableColumnDefine> columnDefines = new ArrayList<TableColumnDefine>();
        TableColumnDefine titleColumn = TableUtil.buildTitleColumn();
        columnDefines.add(titleColumn);
        TableColumnDefine nameColumn = TableUtil.buildNameColumn();
        nameColumn.setWidth(250);
        columnDefines.add(nameColumn);
        TableColumnDefine modifyTimeColumn = TableUtil.buildModifyTimeColumn();
        modifyTimeColumn.setWidth(250);
        columnDefines.add(modifyTimeColumn);
        TableColumnDefine actionColumn = TableUtil.buildActionColumn();
        columnDefines.add(actionColumn);
        actionColumn.setWidth(300);
        return columnDefines;
    }
}

