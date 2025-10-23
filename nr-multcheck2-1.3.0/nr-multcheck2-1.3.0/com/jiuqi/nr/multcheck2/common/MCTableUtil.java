/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.multcheck2.common;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.multcheck2.common.MCColumnDefine;
import com.jiuqi.nr.multcheck2.common.MCTableDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;

public class MCTableUtil {
    public static void convertDesignTableModelDefine(MCTableDefine mcd, DesignTableModelDefine dtd, DataScheme dataScheme, String table) {
        dtd.setCode(table);
        dtd.setName(table);
        dtd.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011" + mcd.getTitle());
        dtd.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011" + mcd.getTitle());
        dtd.setType(TableModelType.DEFAULT);
        dtd.setKind(TableModelKind.DEFAULT);
        dtd.setOwner("NR");
        dtd.setSupportNrdb(true);
        dtd.setStorageName(dataScheme.getBizCode());
    }

    public static void convertDesignColumnModelDefine(MCColumnDefine mcd, DesignColumnModelDefine dcd, String tableId, double index) {
        dcd.setTableID(tableId);
        dcd.setOrder(index);
        dcd.setName(mcd.getName());
        dcd.setCode(mcd.getName());
        dcd.setColumnType(mcd.getType());
        if (mcd.getType() == ColumnModelType.CLOB) {
            return;
        }
        dcd.setPrecision(mcd.getPrecision());
        dcd.setNullAble(mcd.isNullAble());
    }
}

