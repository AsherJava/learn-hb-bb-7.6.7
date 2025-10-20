/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.entity.subject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ConsolidatedSubjectEO {
    public static final String TABLENAME = "MD_GCSUBJECT";
    private String id;
    private Long recver;
    private String code;
    private String objectCode;
    private String title;
    private BigDecimal ordinal;
    private String parentCode;
    private String parents;
    private String systemId;
    private Boolean consolidationFlag;
    private Integer consolidationType;
    private Integer orient;
    private Integer attri;
    private String boundIndexPath;
    private String formula;
    private Date createtime;
    private String createuser;
    private Boolean globalAdd;
    private String asstype;
    private Map<String, String> multilingualNames;
    private Boolean leafFlag;

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

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
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

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public String getObjectCode() {
        return this.objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public Boolean getGlobalAdd() {
        return this.globalAdd != null && this.globalAdd != false;
    }

    public void setGlobalAdd(Boolean globalAdd) {
        this.globalAdd = globalAdd;
    }

    public String getAsstype() {
        return this.asstype;
    }

    public void setAsstype(String asstype) {
        this.asstype = asstype;
    }

    public Map<String, String> getMultilingualNames() {
        return this.multilingualNames;
    }

    public void setMultilingualNames(Map<String, String> multilingualNames) {
        this.multilingualNames = multilingualNames;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }
}

