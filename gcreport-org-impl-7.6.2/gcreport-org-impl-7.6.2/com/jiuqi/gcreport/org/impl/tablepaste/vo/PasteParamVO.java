/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.tablepaste.vo;

import com.jiuqi.gcreport.org.impl.tablepaste.vo.PasteDataVO;
import java.util.List;
import java.util.Map;

public class PasteParamVO {
    private List<Map<String, PasteDataVO>> rows;

    public List<Map<String, PasteDataVO>> getRows() {
        return this.rows;
    }

    public void setRows(List<Map<String, PasteDataVO>> rows) {
        this.rows = rows;
    }
}

