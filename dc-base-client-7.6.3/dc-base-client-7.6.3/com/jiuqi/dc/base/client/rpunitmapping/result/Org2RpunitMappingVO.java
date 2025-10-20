/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.Excel
 *  com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn
 */
package com.jiuqi.dc.base.client.rpunitmapping.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataexport.excel.annotation.ExcelColumn;

@Excel(title="\u4e00\u672c\u8d26\u5355\u4f4d\u4e0e\u62a5\u8868\u5355\u4f4d\u6620\u5c04")
public class Org2RpunitMappingVO {
    public String id;
    public int acctYear;
    @ExcelColumn(index=0, title={"*\u4e00\u672c\u8d26\u5355\u4f4d\u7f16\u7801"})
    private String orgCode;
    @ExcelColumn(index=1, title={"\u4e00\u672c\u8d26\u5355\u4f4d\u540d\u79f0"})
    private String orgName;
    @ExcelColumn(index=2, title={"*\u7b2c1\u671f"})
    private String unitCode1;
    @ExcelColumn(index=3, title={"*\u7b2c2\u671f"})
    private String unitCode2;
    @ExcelColumn(index=4, title={"*\u7b2c3\u671f"})
    private String unitCode3;
    @ExcelColumn(index=5, title={"*\u7b2c4\u671f"})
    private String unitCode4;
    @ExcelColumn(index=6, title={"*\u7b2c5\u671f"})
    private String unitCode5;
    @ExcelColumn(index=7, title={"*\u7b2c6\u671f"})
    private String unitCode6;
    @ExcelColumn(index=8, title={"*\u7b2c7\u671f"})
    private String unitCode7;
    @ExcelColumn(index=9, title={"*\u7b2c8\u671f"})
    private String unitCode8;
    @ExcelColumn(index=10, title={"*\u7b2c9\u671f"})
    private String unitCode9;
    @ExcelColumn(index=11, title={"*\u7b2c10\u671f"})
    private String unitCode10;
    @ExcelColumn(index=12, title={"*\u7b2c11\u671f"})
    private String unitCode11;
    @ExcelColumn(index=13, title={"*\u7b2c12\u671f"})
    private String unitCode12;
    @JsonIgnore
    private boolean ignored;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUnitCode1() {
        return this.unitCode1;
    }

    public void setUnitCode1(String unitCode1) {
        this.unitCode1 = unitCode1;
    }

    public String getUnitCode2() {
        return this.unitCode2;
    }

    public void setUnitCode2(String unitCode2) {
        this.unitCode2 = unitCode2;
    }

    public String getUnitCode3() {
        return this.unitCode3;
    }

    public void setUnitCode3(String unitCode3) {
        this.unitCode3 = unitCode3;
    }

    public String getUnitCode4() {
        return this.unitCode4;
    }

    public void setUnitCode4(String unitCode4) {
        this.unitCode4 = unitCode4;
    }

    public String getUnitCode5() {
        return this.unitCode5;
    }

    public void setUnitCode5(String unitCode5) {
        this.unitCode5 = unitCode5;
    }

    public String getUnitCode6() {
        return this.unitCode6;
    }

    public void setUnitCode6(String unitCode6) {
        this.unitCode6 = unitCode6;
    }

    public String getUnitCode7() {
        return this.unitCode7;
    }

    public void setUnitCode7(String unitCode7) {
        this.unitCode7 = unitCode7;
    }

    public String getUnitCode8() {
        return this.unitCode8;
    }

    public void setUnitCode8(String unitCode8) {
        this.unitCode8 = unitCode8;
    }

    public String getUnitCode9() {
        return this.unitCode9;
    }

    public void setUnitCode9(String unitCode9) {
        this.unitCode9 = unitCode9;
    }

    public String getUnitCode10() {
        return this.unitCode10;
    }

    public void setUnitCode10(String unitCode10) {
        this.unitCode10 = unitCode10;
    }

    public String getUnitCode11() {
        return this.unitCode11;
    }

    public void setUnitCode11(String unitCode11) {
        this.unitCode11 = unitCode11;
    }

    public String getUnitCode12() {
        return this.unitCode12;
    }

    public void setUnitCode12(String unitCode12) {
        this.unitCode12 = unitCode12;
    }

    public boolean getIgnored() {
        return this.ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public String toString() {
        return "Org2RpunitMappingVO [id=" + this.id + ", acctYear=" + this.acctYear + ", orgCode=" + this.orgCode + ", orgName=" + this.orgName + ", unitCode1=" + this.unitCode1 + ", unitCode2=" + this.unitCode2 + ", unitCode3=" + this.unitCode3 + ", unitCode4=" + this.unitCode4 + ", unitCode5=" + this.unitCode5 + ", unitCode6=" + this.unitCode6 + ", unitCode7=" + this.unitCode7 + ", unitCode8=" + this.unitCode8 + ", unitCode9=" + this.unitCode9 + ", unitCode10=" + this.unitCode10 + ", unitCode11=" + this.unitCode11 + ", unitCode12=" + this.unitCode12 + ", ignored=" + this.ignored + "]";
    }
}

