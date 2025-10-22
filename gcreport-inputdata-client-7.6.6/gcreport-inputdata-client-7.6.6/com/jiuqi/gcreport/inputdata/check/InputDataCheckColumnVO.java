/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.gcreport.inputdata.check;

import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class InputDataCheckColumnVO
extends DesignFieldDefineVO {
    private int minWidth;

    public InputDataCheckColumnVO() {
    }

    public InputDataCheckColumnVO(String key, String label, ColumnModelType columnModelType, int minWidth) {
        this.setKey(key);
        this.setLabel(label);
        this.setType(columnModelType);
        this.setMinWidth(minWidth);
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }
}

