/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.common.base.util.UUIDUtils;
import java.util.Objects;

public class VchrMasterDim {
    private Integer acctYear;
    private Integer acctPeriod;
    private String unitCode;
    private Integer count;
    private Integer batchNum;
    private String groupId = UUIDUtils.newUUIDStr();
    private String dataSchemeCode;

    public VchrMasterDim() {
    }

    public VchrMasterDim(Integer acctYear, Integer acctPeriod, String unitCode, Integer count) {
        this.acctYear = acctYear;
        this.acctPeriod = acctPeriod;
        this.unitCode = unitCode;
        this.count = count;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public String toString() {
        if (Objects.isNull(this.getBatchNum())) {
            return String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u671f\u95f4\u3010%3$s\u3011", this.getUnitCode(), this.getAcctYear(), this.getAcctPeriod());
        }
        return String.format("\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u5e74\u5ea6\u3010%2$d\u3011\u671f\u95f4\u3010%3$s\u3011\u6279\u6b21\u3010%4$d\u3011", this.getUnitCode(), this.getAcctYear(), this.getAcctPeriod(), this.getBatchNum());
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }
}

