/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultCheckTableBean {
    private String s_key;
    private String s_name;
    private int s_order;
    private List<String> s_dw;
    private List<MultCheckTableDataItem> multCheckTableDataItem;
    private String s_taskkey;
    private String s_formschemekey;

    public String getS_key() {
        return this.s_key;
    }

    public void setS_key(String s_key) {
        this.s_key = s_key;
    }

    public String getS_name() {
        return this.s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public int getS_order() {
        return this.s_order;
    }

    public void setS_order(int s_order) {
        this.s_order = s_order;
    }

    public List<String> getS_dw() {
        return this.s_dw;
    }

    public void setS_dw(List<String> s_dw) {
        this.s_dw = s_dw;
    }

    public List<MultCheckTableDataItem> getMultCheckTableDataItem() {
        return this.multCheckTableDataItem;
    }

    public void setMultCheckTableDataItem(List<MultCheckTableDataItem> multCheckTableDataItem) {
        this.multCheckTableDataItem = multCheckTableDataItem;
    }

    public String getS_taskkey() {
        return this.s_taskkey;
    }

    public void setS_taskkey(String s_taskkey) {
        this.s_taskkey = s_taskkey;
    }

    public String getS_formschemekey() {
        return this.s_formschemekey;
    }

    public void setS_formschemekey(String s_formschemekey) {
        this.s_formschemekey = s_formschemekey;
    }

    public class ZBInfo
    implements Serializable {
        private String formKey;
        private String fieldkey;
        private String dataLinkKey;

        public String getFieldkey() {
            return this.fieldkey;
        }

        public void setFieldkey(String fieldkey) {
            this.fieldkey = fieldkey;
        }

        public String getDataLinkKey() {
            return this.dataLinkKey;
        }

        public void setDataLinkKey(String dataLinkKey) {
            this.dataLinkKey = dataLinkKey;
        }

        public String getFormKey() {
            return this.formKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }
    }

    public static class AuditScopeAttachment
    implements Serializable {
        private Set<ZBInfo> zbList;

        public void setZbList(Set<ZBInfo> zbList) {
            this.zbList = zbList;
        }

        public Set<ZBInfo> getZbList() {
            return this.zbList;
        }
    }

    public static class AuditScopeErrorDesc
    implements Serializable {
        private List<String> formulaSolution;
        private boolean impactReport;

        public boolean isImpactReport() {
            return this.impactReport;
        }

        public void setImpactReport(boolean impactReport) {
            this.impactReport = impactReport;
        }

        public List<String> getFormulaSolution() {
            return this.formulaSolution;
        }

        public void setFormulaSolution(List<String> formulaSolution) {
            this.formulaSolution = formulaSolution;
        }
    }

    public static class Association
    implements Serializable {
        private int configuration;
        private String specified;
        private String lastIssue;
        private String nextIssue;
        private String periodOffset;

        public int getConfiguration() {
            return this.configuration;
        }

        public void setConfiguration(int configuration) {
            this.configuration = configuration;
        }

        public String getSpecified() {
            return this.specified;
        }

        public void setSpecified(String specified) {
            this.specified = specified;
        }

        public String getLastIssue() {
            return this.lastIssue;
        }

        public void setLastIssue(String lastIssue) {
            this.lastIssue = lastIssue;
        }

        public String getNextIssue() {
            return this.nextIssue;
        }

        public void setNextIssue(String nextIssue) {
            this.nextIssue = nextIssue;
        }

        public String getPeriodOffset() {
            return this.periodOffset;
        }

        public void setPeriodOffset(String periodOffset) {
            this.periodOffset = periodOffset;
        }
    }

    public static class AuditScopeEntityCheck
    implements Serializable {
        private String taskKey;
        private String formSchemeKey;
        private String taskTitle;
        private String formSchemeTitle;
        private DimensionValue datatime;
        private Association association;

        public void setTaskKey(String taskKey) {
            this.taskKey = taskKey;
        }

        public String getTaskKey() {
            return this.taskKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public void setTaskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
        }

        public String getTaskTitle() {
            return this.taskTitle;
        }

        public void setFormSchemeTitle(String formSchemeTitle) {
            this.formSchemeTitle = formSchemeTitle;
        }

        public String getFormSchemeTitle() {
            return this.formSchemeTitle;
        }

        public void setDatatime(DimensionValue datatime) {
            this.datatime = datatime;
        }

        public DimensionValue getDatatime() {
            return this.datatime;
        }

        public Association getAssociation() {
            return this.association;
        }

        public void setAssociation(Association association) {
            this.association = association;
        }
    }

    public static class AuditScopeEnum
    implements Serializable {
        private Set<String> enumList;

        public void setEnumList(Set<String> enumList) {
            this.enumList = enumList;
        }

        public Set<String> getEnumList() {
            return this.enumList;
        }
    }

    public static class AuditScopeFormula
    implements Serializable {
        private Map<String, List<String>> formulaMap;

        public void setFormulaMap(Map<String, List<String>> formulaMap) {
            this.formulaMap = formulaMap;
        }

        public Map<String, List<String>> getFormulaMap() {
            return this.formulaMap;
        }
    }

    public static class AuditScopeNodeCheck
    implements Serializable {
        private boolean tierCheck;
        private int errorRange;
        private Set<FormInfo> formList;

        public void setFormList(Set<FormInfo> formList) {
            this.formList = formList;
        }

        public Set<FormInfo> getFormList() {
            return this.formList;
        }

        public boolean isTierCheck() {
            return this.tierCheck;
        }

        public void setTierCheck(boolean tierCheck) {
            this.tierCheck = tierCheck;
        }

        public int getErrorRange() {
            return this.errorRange;
        }

        public void setErrorRange(int errorRange) {
            this.errorRange = errorRange;
        }
    }

    public class FormInfo
    implements Serializable {
        private String groupKey;
        private String formKey;

        public void setGroupKey(String groupKey) {
            this.groupKey = groupKey;
        }

        public String getGroupKey() {
            return this.groupKey;
        }

        public void setFormKey(String formKey) {
            this.formKey = formKey;
        }

        public String getFormKey() {
            return this.formKey;
        }
    }

    public static class AuditScopeForm
    implements Serializable {
        private Set<FormInfo> formList;

        public void setFormList(Set<FormInfo> formList) {
            this.formList = formList;
        }

        public Set<FormInfo> getFormList() {
            return this.formList;
        }
    }

    public static class MultCheckTableDataItem<T> {
        private int index;
        private String key;
        private String multCheckKey;
        private String multCheckType;
        private String multCheckTypeName;
        private String multCheckName;
        private T auditScope;

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }

        public void setMultCheckName(String multCheckName) {
            this.multCheckName = multCheckName;
        }

        public String getMultCheckName() {
            return this.multCheckName;
        }

        public void setAuditScope(T auditScope) {
            this.auditScope = auditScope;
        }

        public T getAuditScope() {
            return this.auditScope;
        }

        public String getMultCheckType() {
            return this.multCheckType;
        }

        public void setMultCheckType(String multCheckType) {
            this.multCheckType = multCheckType;
        }

        public String getMultCheckKey() {
            return this.multCheckKey;
        }

        public void setMultCheckKey(String multCheckKey) {
            this.multCheckKey = multCheckKey;
        }

        public String getMultCheckTypeName() {
            return this.multCheckTypeName;
        }

        public void setMultCheckTypeName(String multCheckTypeName) {
            this.multCheckTypeName = multCheckTypeName;
        }
    }
}

