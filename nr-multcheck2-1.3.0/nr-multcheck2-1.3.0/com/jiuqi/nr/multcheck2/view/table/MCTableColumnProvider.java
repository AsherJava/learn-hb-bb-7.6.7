/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.resourceview.table.DefaultTableColumnProvider
 *  com.jiuqi.nvwa.resourceview.table.TableColumnAlign
 *  com.jiuqi.nvwa.resourceview.table.TableColumnDefine
 *  com.jiuqi.nvwa.resourceview.table.TableUtil
 */
package com.jiuqi.nr.multcheck2.view.table;

import com.jiuqi.nvwa.resourceview.table.DefaultTableColumnProvider;
import com.jiuqi.nvwa.resourceview.table.TableColumnAlign;
import com.jiuqi.nvwa.resourceview.table.TableColumnDefine;
import com.jiuqi.nvwa.resourceview.table.TableUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MCTableColumnProvider
extends DefaultTableColumnProvider {
    public List<TableColumnDefine> getTableColumns() {
        ArrayList<TableColumnDefine> columnDefines = new ArrayList<TableColumnDefine>();
        TableColumnDefine titleColumnDefine = TableUtil.buildTitleColumn();
        titleColumnDefine.setWidth(200);
        columnDefines.add(titleColumnDefine);
        TableColumnDefine codeColumnDefine = TableUtil.buildNameColumn();
        codeColumnDefine.setWidth(150);
        columnDefines.add(codeColumnDefine);
        TableColumnDefine schemeColumnDefine = new TableColumnDefine("formSchemeTitle", "\u62a5\u8868\u65b9\u6848");
        schemeColumnDefine.setWidth(200);
        columnDefines.add(schemeColumnDefine);
        TableColumnDefine itemColumnDefine = new TableColumnDefine("sitem", "\u5ba1\u6838\u9879");
        itemColumnDefine.setWidth(60);
        itemColumnDefine.setAlign(TableColumnAlign.CENTER);
        columnDefines.add(itemColumnDefine);
        TableColumnDefine typeColumnDefine = new TableColumnDefine("stype", "\u5e94\u7528\u573a\u666f");
        typeColumnDefine.setWidth(75);
        typeColumnDefine.setAlign(TableColumnAlign.LEFT);
        columnDefines.add(typeColumnDefine);
        TableColumnDefine unitDefine = new TableColumnDefine("org", "\u5355\u4f4d\u8303\u56f4");
        unitDefine.setMinWidth(180);
        unitDefine.setMaxWidth(300);
        columnDefines.add(unitDefine);
        TableColumnDefine actionColumnDefine = TableUtil.buildActionColumn();
        actionColumnDefine.setMinWidth(300);
        columnDefines.add(actionColumnDefine);
        return columnDefines;
    }
}

