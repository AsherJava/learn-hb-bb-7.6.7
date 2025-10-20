/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

public class GcConsolidatedSubjectVo
extends DefaultTableEntity {
    private String code;
    private String title;
    private String sortOrder;
    private String parentCode;
    private String systemId;
    private Boolean consolidationFlag;
    private Integer consolidationType;
    private Integer orient;
    private Integer attri;
    private String boundIndexPath;
    private String boundSubject;
    private String formula;
    private Date createtime;
    private String createuser;
    private Date modifiedtime;
    private String modifieduser;

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getAttri() {
        return this.attri;
    }

    public void setAttri(Integer attri) {
        this.attri = attri;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Boolean getConsolidationFlag() {
        return this.consolidationFlag;
    }

    public void setConsolidationFlag(Boolean consolidationFlag) {
        this.consolidationFlag = consolidationFlag;
    }

    public Integer getConsolidationType() {
        return this.consolidationType;
    }

    public void setConsolidationType(Integer consolidationType) {
        this.consolidationType = consolidationType;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public String getBoundIndexPath() {
        return this.boundIndexPath;
    }

    public void setBoundIndexPath(String boundIndexPath) {
        this.boundIndexPath = boundIndexPath;
    }

    public String getBoundSubject() {
        return this.boundSubject;
    }

    public void setBoundSubject(String boundSubject) {
        this.boundSubject = boundSubject;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return this.createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getModifiedtime() {
        return this.modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public String getModifieduser() {
        return this.modifieduser;
    }

    public void setModifieduser(String modifieduser) {
        this.modifieduser = modifieduser;
    }
}

