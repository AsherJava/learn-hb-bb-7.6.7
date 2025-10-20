/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.test.eo;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.math.BigDecimal;

@DBTable(name="GC_TEST_DEFINE", inStorage=true, title="GC\u6269\u5c55\u6570\u636e\u5efa\u6a21\u9a8c\u8bc1")
public class GcTestDefineEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    @DBColumn(length=50, nameInDB="CODE", dbType=DBColumn.DBType.NVarchar)
    private String code;
    @DBColumn(length=100, nameInDB="NAME", dbType=DBColumn.DBType.NVarchar)
    private String name;
    @DBColumn(length=300, nameInDB="T_STRING", dbType=DBColumn.DBType.NVarchar)
    private String t_string;
    @DBColumn(precision=19, scale=6, nameInDB="T_NUMBER", dbType=DBColumn.DBType.Numeric)
    private BigDecimal t_number;
    @DBColumn(length=300, nameInDB="REMARK", dbType=DBColumn.DBType.NVarchar)
    private String remark;
    @DBColumn(nameInDB="T_DATA", dbType=DBColumn.DBType.Text)
    private String data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getT_string() {
        return this.t_string;
    }

    public void setT_string(String t_string) {
        this.t_string = t_string;
    }

    public BigDecimal getT_number() {
        return this.t_number;
    }

    public void setT_number(BigDecimal t_number) {
        this.t_number = t_number;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

