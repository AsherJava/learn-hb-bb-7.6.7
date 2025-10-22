/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.workingpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_ORGFILTER_FORMULA", inStorage=true, title="\u4efb\u610f\u5408\u5e76\u5355\u4f4d\u7b5b\u9009\u5173\u8054\u81ea\u5b9a\u4e49\u516c\u5f0f\u8868")
public class ArbitrarilyMergeOrgFilterCustomFormulaSettingEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_ORGFILTER_FORMULA";
    @DBColumn(nameInDB="DATAID", title="\u5173\u8054\u4e3b\u952eid", dbType=DBColumn.DBType.Varchar)
    private String dataId;
    @DBColumn(nameInDB="OPTIONNAME", title="\u9009\u9879\u540d\u79f0", dbType=DBColumn.DBType.Varchar)
    private String optionName;
    @DBColumn(nameInDB="OPTIONNAME", title="\u9009\u9879\u5907\u6ce8", dbType=DBColumn.DBType.Varchar)
    private String optionDes;
    @DBColumn(nameInDB="FORMULA", title="\u81ea\u5b9a\u4e49\u516c\u5f0f", dbType=DBColumn.DBType.Text)
    private String formula;
    @DBColumn(nameInDB="ORDERNUM", title="\u6392\u5e8f\u53f7", dbType=DBColumn.DBType.Numeric)
    private Double orderNum;

    public String getDataId() {
        return this.dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getOptionName() {
        return this.optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionDes() {
        return this.optionDes;
    }

    public void setOptionDes(String optionDes) {
        this.optionDes = optionDes;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Double orderNum) {
        this.orderNum = orderNum;
    }
}

