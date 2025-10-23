/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.JQRMappingCache;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.log.XmlDataImportLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransImportContext {
    private List<DimInfo> dimInfos;
    private TaskDefine taskDefine;
    private String mappingSchemeKey;
    private JQRMappingCache jqrMappingCache = new JQRMappingCache();
    public String formSchemeKey;
    public List<TransOrgInfo> orgDataList;
    private XmlDataImportLog importLog;

    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<TransOrgInfo> getOrgDataList() {
        return this.orgDataList;
    }

    public void setOrgDataList(List<TransOrgInfo> orgDataList) {
        this.orgDataList = orgDataList;
    }

    public void addOrgData(TransOrgInfo orgData) {
        if (this.orgDataList == null) {
            this.orgDataList = new ArrayList<TransOrgInfo>();
        }
        this.orgDataList.add(orgData);
    }

    public List<DimInfo> getDimInfos() {
        return this.dimInfos;
    }

    public void setDimInfos(List<DimInfo> dimInfos) {
        this.dimInfos = dimInfos;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public XmlDataImportLog getImportLog() {
        return this.importLog;
    }

    public void setImportLog(XmlDataImportLog importLog) {
        this.importLog = importLog;
    }

    public void setLogUnitInfo(String unitInfo) {
        this.importLog.setCurUnitInfo(unitInfo);
    }

    public String getLogUnitInfo() {
        return this.importLog.getCurUnitInfo();
    }

    public void addDetailInfoToLog(String detailLog) {
        this.importLog.addDetailLog(detailLog);
    }

    public void addErrorInfoToLog(String errorLog) {
        this.importLog.addErrorLog(errorLog);
    }

    public JQRMappingCache getJqrMappingCache() {
        return this.jqrMappingCache;
    }

    public void setJqrMappingCache(JQRMappingCache jqrMappingCache) {
        this.jqrMappingCache = jqrMappingCache;
    }

    public String getTableNameMapping() {
        return this.jqrMappingCache.getTableNameMapping();
    }

    public void setTableNameMapping(String tableNameMapping) {
        this.jqrMappingCache.setTableNameMapping(tableNameMapping);
    }

    public String getSolutionNameMapping() {
        return this.jqrMappingCache.getSolutionCodeMapping();
    }

    public void setSolutionNameMapping(String solutionNameMapping) {
        this.jqrMappingCache.setSolutionCodeMapping(solutionNameMapping);
    }

    public Map<String, String> getUnitMappingMap() {
        return this.jqrMappingCache.getOrgMappingMap();
    }

    public void setUnitMappingMap(Map<String, String> mappingMap) {
        this.jqrMappingCache.setOrgMappingMap(mappingMap);
    }

    public Map<String, Map<String, String>> getBaseDataMappingMap() {
        return this.jqrMappingCache.getBaseDataMappingMap();
    }

    public void setBaseDataMappingMap(Map<String, Map<String, String>> baseDataMappingMap) {
        this.jqrMappingCache.setBaseDataMappingMap(baseDataMappingMap);
    }

    public Map<String, String> getPeriodMappingMap() {
        return this.jqrMappingCache.getPeriodMappingMap();
    }

    public void setPeriodMappingMap(Map<String, String> periodMappingMap) {
        this.jqrMappingCache.setPeriodMappingMap(periodMappingMap);
    }

    public Map<String, String> getZbMappingMap() {
        return this.jqrMappingCache.getZbMappingMap();
    }

    public void setZbMappingMap(Map<String, String> zbMappingMap) {
        this.jqrMappingCache.setZbMappingMap(zbMappingMap);
    }

    public DimensionValueSet getDimensionValueSet() {
        DimensionValueSet valueSet = new DimensionValueSet();
        for (DimInfo dimInfo : this.dimInfos) {
            String name;
            if ("DATATIME".equals(dimInfo.getName())) {
                valueSet.setValue(dimInfo.getName(), (Object)dimInfo.getValue());
                continue;
            }
            String string = name = dimInfo.getEntityId().endsWith("@BASE") ? dimInfo.getEntityId().substring(0, dimInfo.getEntityId().length() - "@BASE".length()) : "MD_ORG";
            if (dimInfo.getValues().size() > 0) {
                valueSet.setValue(name, dimInfo.getValues());
                continue;
            }
            valueSet.setValue(name, (Object)dimInfo.getValue());
        }
        return valueSet;
    }
}

