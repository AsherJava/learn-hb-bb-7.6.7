/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 */
package com.jiuqi.nr.singlequery.multcheck.vo;

import com.jiuqi.nr.datacheck.dataanalyze.vo.CheckUnitState;
import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class SingleQueryErrorInfo
extends MCErrorDescription {
    private String orgStr;
    private String resourceStr;
    private String type;
    private String check;
    private String desErrorReason;
    private CheckUnitState state;
    private Map<String, String> dimStr;

    public CheckUnitState getState() {
        return this.state;
    }

    public void setState(CheckUnitState state) {
        this.state = state;
    }

    public String getDesErrorReason() {
        return this.desErrorReason;
    }

    public void setDesErrorReason(String desErrorReason) {
        this.desErrorReason = desErrorReason;
    }

    public SingleQueryErrorInfo() {
    }

    public SingleQueryErrorInfo(MCErrorDescription errorDescription) {
        BeanUtils.copyProperties(errorDescription, (Object)this);
    }

    public Map<String, String> getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(Map<String, String> dimStr) {
        this.dimStr = dimStr;
    }

    public String getOrgStr() {
        return this.orgStr;
    }

    public void setOrgStr(String orgStr) {
        this.orgStr = orgStr;
    }

    public String getResourceStr() {
        return this.resourceStr;
    }

    public void setResourceStr(String resourceStr) {
        this.resourceStr = resourceStr;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheck() {
        return this.check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
}

