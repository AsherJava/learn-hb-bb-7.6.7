/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.vo;

import com.jiuqi.nr.workflow2.instance.entity.InstanceTableData;
import com.jiuqi.nr.workflow2.instance.vo.InstanceTableDataVO;
import java.util.List;

public class InstanceTableDataVOImpl
implements InstanceTableDataVO {
    private List<InstanceTableData> tableData;
    private int count;

    @Override
    public List<InstanceTableData> getTableData() {
        return this.tableData;
    }

    public void setTableData(List<InstanceTableData> tableData) {
        this.tableData = tableData;
    }

    @Override
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

