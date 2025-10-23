/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.vo.CustomNodeData;
import com.jiuqi.nr.summary.vo.NodeType;

public class FieldNodeData
extends CustomNodeData {
    private String zbCode;
    private String zbTitle;
    private SummaryZb summaryZb;
    private DataFieldGatherType gatherType;

    public FieldNodeData() {
        super(NodeType.DATA_FIELD);
    }

    public FieldNodeData(DataField dataField) {
        super(NodeType.DATA_FIELD);
        this.summaryZb = new SummaryZb();
        this.summaryZb.setName(dataField.getCode());
        this.summaryZb.setTitle(dataField.getTitle());
        this.summaryZb.setDataType(dataField.getDataFieldType());
        this.summaryZb.setPrecision(dataField.getPrecision() == null ? -1 : dataField.getPrecision());
        this.summaryZb.setDecimal(dataField.getDecimal() == null ? -1 : dataField.getDecimal());
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public SummaryZb getSummaryZb() {
        return this.summaryZb;
    }

    public void setSummaryZb(SummaryZb summaryZb) {
        this.summaryZb = summaryZb;
    }

    public DataFieldGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(DataFieldGatherType gatherType) {
        this.gatherType = gatherType;
    }
}

