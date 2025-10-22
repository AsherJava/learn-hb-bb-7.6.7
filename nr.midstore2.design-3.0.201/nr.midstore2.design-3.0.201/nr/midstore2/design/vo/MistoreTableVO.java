/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package nr.midstore2.design.vo;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import nr.midstore2.design.domain.CommonParamDTO;

public class MistoreTableVO
extends CommonParamDTO {
    private String groupKey;
    private DataTableType tableType;

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public DataTableType getTableType() {
        return this.tableType;
    }

    public void setTableType(DataTableType tableType) {
        this.tableType = tableType;
    }
}

