/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.table.ITableColumnProvider
 *  com.jiuqi.nvwa.resourceview.table.TableColumnDefine
 *  com.jiuqi.nvwa.resourceview.table.TableUtil
 */
package com.jiuqi.nr.batch.summary.web.app;

import com.jiuqi.nvwa.resourceview.table.ITableColumnProvider;
import com.jiuqi.nvwa.resourceview.table.TableColumnDefine;
import com.jiuqi.nvwa.resourceview.table.TableUtil;
import java.util.ArrayList;
import java.util.List;

public class SummarySchemeShareTableColumnProvider
implements ITableColumnProvider {
    public List<TableColumnDefine> getTableColumns() {
        ArrayList<TableColumnDefine> columnDefines = new ArrayList<TableColumnDefine>();
        TableColumnDefine titleColumn = TableUtil.buildTitleColumn();
        columnDefines.add(titleColumn);
        TableColumnDefine nameColumn = TableUtil.buildNameColumn();
        nameColumn.setWidth(250);
        nameColumn.setTitle("\u63a5\u6536\u4eba");
        columnDefines.add(nameColumn);
        TableColumnDefine modifyTimeColumn = TableUtil.buildModifyTimeColumn();
        modifyTimeColumn.setWidth(250);
        modifyTimeColumn.setTitle("\u5171\u4eab\u65f6\u95f4");
        columnDefines.add(modifyTimeColumn);
        TableColumnDefine actionColumn = TableUtil.buildActionColumn();
        columnDefines.add(actionColumn);
        actionColumn.setWidth(300);
        return columnDefines;
    }
}

