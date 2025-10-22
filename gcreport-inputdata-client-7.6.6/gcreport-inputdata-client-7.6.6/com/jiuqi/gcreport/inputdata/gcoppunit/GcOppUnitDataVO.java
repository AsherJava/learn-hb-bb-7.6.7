/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO
 *  com.jiuqi.gcreport.common.util.NumberUtils
 */
package com.jiuqi.gcreport.inputdata.gcoppunit;

import com.jiuqi.gcreport.common.elementtable.impl.ElementTableDataVO;
import com.jiuqi.gcreport.common.util.NumberUtils;
import java.math.BigDecimal;
import java.util.Map;

public class GcOppUnitDataVO
extends ElementTableDataVO {
    private String unitId;
    private String unitCode;
    private String unitName;
    private String oppUnitId;
    private String oppUnitCode;
    private String oppUnitName;
    private String kmid;
    private String kmcode;
    private String kmname;
    private BigDecimal amt;
    private String amtV;
    private String remark;
    private Map<String, Object> otherDataMap;

    public Map<String, Object> getOtherDataMap() {
        return this.otherDataMap;
    }

    public void setOtherDataMap(Map<String, Object> otherDataMap) {
        this.otherDataMap = otherDataMap;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getOppUnitName() {
        return this.oppUnitName;
    }

    public void setOppUnitName(String oppUnitName) {
        this.oppUnitName = oppUnitName;
    }

    public String getKmid() {
        return this.kmid;
    }

    public void setKmid(String kmid) {
        this.kmid = kmid;
    }

    public String getKmcode() {
        return this.kmcode;
    }

    public void setKmcode(String kmcode) {
        this.kmcode = kmcode;
    }

    public String getKmname() {
        return this.kmname;
    }

    public void setKmname(String kmname) {
        this.kmname = kmname;
    }

    public BigDecimal getAmt() {
        return this.amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
        this.amtV = NumberUtils.format((Number)amt);
    }

    public void setAmtV(String amtV) {
        this.amtV = amtV;
    }

    public String getAmtV() {
        return this.amtV;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

