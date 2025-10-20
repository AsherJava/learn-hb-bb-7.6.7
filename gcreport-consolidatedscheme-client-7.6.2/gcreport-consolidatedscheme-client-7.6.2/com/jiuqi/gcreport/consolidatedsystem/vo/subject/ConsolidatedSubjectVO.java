/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.subject;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ConsolidatedSubjectVO
implements Serializable {
    private String id;
    private String code;
    private String title;
    private BigDecimal ordinal;
    private String parentCode;
    private String systemId;
    private Boolean consolidationFlag = true;
    private Integer consolidationType;
    private Integer orient;
    private Integer attri;
    private String boundIndex;
    private String boundIndexPath;
    private List<BaseDataVO> boundSubject = new ArrayList<BaseDataVO>();
    private Boolean leafFlag;
    private String formula;
    private Date createtime;
    private String createuser;
    private Date modifiedtime;
    private String modifieduser;
    private Boolean globalAdd;
    private String asstype;
    private String asstypeTitles;
    private Map<String, String> multilingualNames;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public Integer getAttri() {
        return this.attri;
    }

    public void setAttri(Integer attri) {
        this.attri = attri;
    }

    public String getBoundIndex() {
        return this.boundIndex;
    }

    public void setBoundIndex(String boundIndex) {
        this.boundIndex = boundIndex;
    }

    public String getBoundIndexPath() {
        return this.boundIndexPath;
    }

    public void setBoundIndexPath(String boundIndexPath) {
        this.boundIndexPath = boundIndexPath;
    }

    public List<BaseDataVO> getBoundSubject() {
        return this.boundSubject;
    }

    public void setBoundSubject(List<BaseDataVO> boundSubject) {
        this.boundSubject = boundSubject;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
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

    public Boolean getGlobalAdd() {
        return this.globalAdd;
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

    public String getAsstypeTitles() {
        return this.asstypeTitles;
    }

    public void setAsstypeTitles(String asstypeTitles) {
        this.asstypeTitles = asstypeTitles;
    }

    public Map<String, String> getMultilingualNames() {
        return this.multilingualNames;
    }

    public void setMultilingualNames(Map<String, String> multilingualNames) {
        this.multilingualNames = multilingualNames;
    }
}

