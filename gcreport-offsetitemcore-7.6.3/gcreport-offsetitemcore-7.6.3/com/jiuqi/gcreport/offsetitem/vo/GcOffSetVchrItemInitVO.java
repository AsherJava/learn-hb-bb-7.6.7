/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.HashMap;
import java.util.Map;

public class GcOffSetVchrItemInitVO {
    private GcOrgCacheVO unitVo;
    private String unitCode;
    private GcOrgCacheVO oppUnitVo;
    private String oppUnitCode;
    private String subjectCode;
    private BaseDataVO subjectVo;
    private Boolean isTZ;
    private Map<String, Object> ruleItem;
    private String offSetDebit;
    private String offSetCredit;
    private GcOrgCacheVO investUnitVo;
    private GcOrgCacheVO investedUnitVo;
    private BaseDataVO areaVo;
    private BaseDataVO ywbkVo;
    private BaseDataVO gcywlxVo;
    private String systemId;
    private Map<String, Object> unSysFields = new HashMap<String, Object>();
    private String effectType;

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public GcOrgCacheVO getUnitVo() {
        return this.unitVo;
    }

    public void setUnitVo(GcOrgCacheVO unitVo) {
        this.unitVo = unitVo;
    }

    public GcOrgCacheVO getOppUnitVo() {
        return this.oppUnitVo;
    }

    public void setOppUnitVo(GcOrgCacheVO oppUnitVo) {
        this.oppUnitVo = oppUnitVo;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public BaseDataVO getSubjectVo() {
        return this.subjectVo;
    }

    public void setSubjectVo(BaseDataVO subjectVo) {
        this.subjectVo = subjectVo;
    }

    public Boolean getIsTZ() {
        return this.isTZ;
    }

    public void setIsTZ(Boolean isTZ) {
        this.isTZ = isTZ;
    }

    public String getOffSetDebit() {
        return this.offSetDebit;
    }

    public void setOffSetDebit(String offSetDebit) {
        this.offSetDebit = offSetDebit;
    }

    public String getOffSetCredit() {
        return this.offSetCredit;
    }

    public void setOffSetCredit(String offSetCredit) {
        this.offSetCredit = offSetCredit;
    }

    public BaseDataVO getAreaVo() {
        return this.areaVo;
    }

    public void setAreaVo(BaseDataVO areaVo) {
        this.areaVo = areaVo;
    }

    public BaseDataVO getYwbkVo() {
        return this.ywbkVo;
    }

    public void setYwbkVo(BaseDataVO ywbkVo) {
        this.ywbkVo = ywbkVo;
    }

    public BaseDataVO getGcywlxVo() {
        return this.gcywlxVo;
    }

    public void setGcywlxVo(BaseDataVO gcywlxVo) {
        this.gcywlxVo = gcywlxVo;
    }

    public void setUnSysFields(Map<String, Object> unSysFields) {
        this.unSysFields = unSysFields;
    }

    public Map<String, Object> getUnSysFields() {
        return this.unSysFields;
    }

    public void addUnSysFieldValue(String field, Object value) {
        if (this.unSysFields == null) {
            this.unSysFields = new HashMap<String, Object>();
        }
        this.unSysFields.put(field, value);
    }

    public Object getUnSysFieldValue(String field) {
        if (this.unSysFields == null) {
            return null;
        }
        return this.unSysFields.get(field);
    }

    public Map<String, Object> getRuleItem() {
        if (null == this.ruleItem) {
            this.ruleItem = new HashMap<String, Object>();
        }
        return this.ruleItem;
    }

    public void setRuleItem(Map<String, Object> ruleItem) {
        this.ruleItem = ruleItem;
    }

    public GcOrgCacheVO getInvestUnitVo() {
        return this.investUnitVo;
    }

    public void setInvestUnitVo(GcOrgCacheVO investUnitVo) {
        this.investUnitVo = investUnitVo;
    }

    public GcOrgCacheVO getInvestedUnitVo() {
        return this.investedUnitVo;
    }

    public void setInvestedUnitVo(GcOrgCacheVO investedUnitVo) {
        this.investedUnitVo = investedUnitVo;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
}

