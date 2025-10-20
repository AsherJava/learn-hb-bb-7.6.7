/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo;

import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import java.io.Serializable;
import java.util.Date;

public class OrgVersionVO
implements Serializable {
    private static final long serialVersionUID = 5744396439495839912L;
    private String id;
    private String name;
    private String title;
    private Date validTime;
    private Date invalidTime;
    private String periodStr;
    private OrgTypeVO orgType;
    private int ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public OrgTypeVO getOrgType() {
        return this.orgType;
    }

    public void setOrgType(OrgTypeVO orgType) {
        this.orgType = orgType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

