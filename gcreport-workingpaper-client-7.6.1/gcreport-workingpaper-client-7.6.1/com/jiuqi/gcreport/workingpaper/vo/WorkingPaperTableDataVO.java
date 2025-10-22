/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.workingpaper.serializer.WorkingPaperTableDataVOZbValueSerializer;
import com.jiuqi.gcreport.workingpaper.serializer.WorkingPaperTableDataVOZbValueStrSerializer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkingPaperTableDataVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String systemid;
    private String kmid;
    private String kmcode;
    private String kmname;
    private String formSubjectTitle;
    private String kmorient;
    private Integer orient;
    private String parentid;
    private String parents;
    private String zbBoundIndexPath;
    private String zbcode;
    private String zbname;
    private String zbtable;
    private String zbfield;
    @JsonSerialize(using=WorkingPaperTableDataVOZbValueSerializer.class)
    private Map<String, BigDecimal> zbvalue = new HashMap<String, BigDecimal>();
    @JsonSerialize(using=WorkingPaperTableDataVOZbValueStrSerializer.class)
    private Map<String, String> zbvalueStr = new HashMap<String, String>();
    private String gzmc;
    private String fetchConfigTitle;
    private String order;
    private String ruleid;
    private String fetchSetGroupId;
    private String mercid;
    private List<WorkingPaperTableDataVO> children;
    private String offsetSrcType;
    private String primarySettingId;
    private boolean leafNodeFlag = true;

    public List<WorkingPaperTableDataVO> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<WorkingPaperTableDataVO>();
        }
        return this.children;
    }

    public void setChildren(List<WorkingPaperTableDataVO> children) {
        this.children = children;
    }

    public String getMercid() {
        return this.mercid;
    }

    public void setMercid(String mercid) {
        this.mercid = mercid;
    }

    public String getRuleid() {
        return this.ruleid;
    }

    public void setRuleid(String ruleid) {
        this.ruleid = ruleid;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getGzmc() {
        return this.gzmc;
    }

    public void setGzmc(String gzmc) {
        this.gzmc = gzmc;
    }

    public String getFetchConfigTitle() {
        return this.fetchConfigTitle;
    }

    public void setFetchConfigTitle(String fetchConfigTitle) {
        this.fetchConfigTitle = fetchConfigTitle;
    }

    public Map<String, String> getZbvalueStr() {
        if (this.zbvalueStr == null) {
            this.zbvalueStr = new HashMap<String, String>();
        }
        return this.zbvalueStr;
    }

    public void setZbvalueStr(Map<String, String> zbvalueStr) {
        this.zbvalueStr = zbvalueStr;
    }

    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getParents() {
        return this.parents;
    }

    public void setParents(String parents) {
        this.parents = parents;
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

    public String getKmorient() {
        return this.kmorient;
    }

    public void setKmorient(String kmorient) {
        this.kmorient = kmorient;
    }

    public String getZbcode() {
        return this.zbcode;
    }

    public void setZbcode(String zbcode) {
        this.zbcode = zbcode;
    }

    public String getZbname() {
        return this.zbname;
    }

    public void setZbname(String zbname) {
        this.zbname = zbname;
    }

    public String getZbtable() {
        return this.zbtable;
    }

    public void setZbtable(String zbtable) {
        this.zbtable = zbtable;
    }

    public String getZbfield() {
        return this.zbfield;
    }

    public void setZbfield(String zbfield) {
        this.zbfield = zbfield;
    }

    public Map<String, BigDecimal> getZbvalue() {
        if (this.zbvalue == null) {
            this.zbvalue = new HashMap<String, BigDecimal>();
        }
        return this.zbvalue;
    }

    public void setZbvalue(Map<String, BigDecimal> zbvalue) {
        this.zbvalue = zbvalue;
    }

    public String getZbBoundIndexPath() {
        return this.zbBoundIndexPath;
    }

    public void setZbBoundIndexPath(String zbBoundIndexPath) {
        this.zbBoundIndexPath = zbBoundIndexPath;
    }

    public String getOffsetSrcType() {
        return this.offsetSrcType;
    }

    public void setOffsetSrcType(String offsetSrcType) {
        this.offsetSrcType = offsetSrcType;
    }

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getFormSubjectTitle() {
        return this.formSubjectTitle;
    }

    public void setFormSubjectTitle(String formSubjectTitle) {
        this.formSubjectTitle = formSubjectTitle;
    }

    public String getPrimarySettingId() {
        return this.primarySettingId;
    }

    public void setPrimarySettingId(String primarySettingId) {
        this.primarySettingId = primarySettingId;
    }

    public String getSystemid() {
        return this.systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public boolean isLeafNodeFlag() {
        return this.leafNodeFlag;
    }

    public void setLeafNodeFlag(boolean leafNodeFlag) {
        this.leafNodeFlag = leafNodeFlag;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }
}

