/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableColumnProvider
 *  com.jiuqi.nvwa.resourceview.table.TableColumnDefine
 *  com.jiuqi.nvwa.resourceview.table.TableUtil
 */
package com.jiuqi.nr.mapping.view.table;

import com.jiuqi.nvwa.resourceview.table.DefaultTableColumnProvider;
import com.jiuqi.nvwa.resourceview.table.TableColumnDefine;
import com.jiuqi.nvwa.resourceview.table.TableUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MSTableColumnProvider
extends DefaultTableColumnProvider {
    public List<TableColumnDefine> getTableColumns() {
        ArrayList<TableColumnDefine> columnDefines = new ArrayList<TableColumnDefine>();
        columnDefines.add(TableUtil.buildTitleColumn());
        columnDefines.add(TableUtil.buildNameColumn());
        TableColumnDefine task = new TableColumnDefine("task", "\u4efb\u52a1");
        task.setWidth(300);
        columnDefines.add(task);
        columnDefines.add(TableUtil.buildModifyTimeColumn());
        columnDefines.add(TableUtil.buildActionColumn());
        return columnDefines;
    }
}

