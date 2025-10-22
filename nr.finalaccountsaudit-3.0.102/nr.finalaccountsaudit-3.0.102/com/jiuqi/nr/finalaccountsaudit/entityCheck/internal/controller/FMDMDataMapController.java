/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.fmdm.IFMDMData
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.CheckConfigurationContent;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckVersionObjectInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller.FieldContext;
import com.jiuqi.nr.fmdm.IFMDMData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FMDMDataMapController {
    private Map<String, IFMDMData> entityMaps = new HashMap<String, IFMDMData>();
    private Map<String, IFMDMData> dwmcMaps = new HashMap<String, IFMDMData>();
    private Map<String, IFMDMData> bizKeyMaps = new HashMap<String, IFMDMData>();
    private Map<String, IFMDMData> CodeMaps = new HashMap<String, IFMDMData>();
    private Map<String, IFMDMData> expValueToFmdmData = new HashMap<String, IFMDMData>();
    private Map<String, String> codeToExpValues = new HashMap<String, String>();
    private int mergerAndBalance = 0;

    public List<IEntityRow> queryEntityRowsByScop(EntityCheckVersionObjectInfo versionObjectInfo, String scop) {
        List currentAllEntity = null;
        IEntityRow currentEntity = null;
        if (StringUtils.isEmpty((String)scop)) {
            currentAllEntity = versionObjectInfo.getEntityTable().getAllRows();
        } else {
            currentEntity = versionObjectInfo.getEntityTable().findByEntityKey(scop);
            if (null == currentEntity) {
                currentAllEntity = versionObjectInfo.getEntityTable().getAllRows();
            } else {
                currentAllEntity = versionObjectInfo.getEntityTable().getAllChildRows(scop);
                if (null != currentAllEntity) {
                    currentAllEntity.add(currentEntity);
                }
            }
        }
        return currentAllEntity;
    }

    public FMDMDataMapController(EntityCheckVersionObjectInfo versionObjectInfo, FieldContext fieldContext, CheckConfigurationContent configurationContent) {
        String bllxValue;
        IFMDMData fmdMData;
        List<IEntityRow> entityRows = this.queryEntityRowsByScop(versionObjectInfo, null);
        for (IEntityRow entityRow : entityRows) {
            fmdMData = versionObjectInfo.getFmdmDataMap().get(entityRow.getEntityKeyData());
            if (fmdMData == null || fieldContext.getDefBBLXField() == null || !StringUtils.isNotEmpty(bllxValue = fmdMData.getValue(fieldContext.getDefBBLXField().getCode()) == null ? null : fmdMData.getValue(fieldContext.getDefBBLXField().getCode()).getAsString()) || !configurationContent.getFilterValue().contains(bllxValue)) continue;
            ++this.mergerAndBalance;
        }
        for (IEntityRow entityRow : versionObjectInfo.getEntityTable().getAllRows()) {
            Map<String, Object[]> existMap;
            Object[] existArray;
            TaskLinkMatchingType matchType;
            fmdMData = versionObjectInfo.getFmdmDataMap().get(entityRow.getEntityKeyData());
            if (fmdMData == null) continue;
            if (fieldContext.getDefBBLXField() != null) {
                String string = bllxValue = fmdMData.getValue(fieldContext.getDefBBLXField().getCode()) == null ? null : fmdMData.getValue(fieldContext.getDefBBLXField().getCode()).getAsString();
                if (StringUtils.isNotEmpty((String)bllxValue) && configurationContent.getFilterValue().contains(bllxValue)) continue;
            }
            this.entityMaps.put(entityRow.getAsString("CODE"), fmdMData);
            String name = entityRow.getTitle();
            if (null != name && !"".equals(name)) {
                this.dwmcMaps.put(name, fmdMData);
            }
            if (fieldContext.getMasterBizKey() != null) {
                String code;
                String string = code = fmdMData.getValue(fieldContext.getMasterBizKey().getCode()) == null ? null : fmdMData.getValue(fieldContext.getMasterBizKey().getCode()).getAsString();
                if (StringUtils.isNotEmpty(code)) {
                    this.bizKeyMaps.put(code, fmdMData);
                }
            }
            if (fieldContext.getMasterCode() != null) {
                String orgcode;
                String string = orgcode = fmdMData.getValue(fieldContext.getMasterCode().getCode()) == null ? null : fmdMData.getValue(fieldContext.getMasterCode().getCode()).getAsString();
                if (StringUtils.isNotEmpty(orgcode)) {
                    this.CodeMaps.put(orgcode, fmdMData);
                }
            }
            if ((matchType = TaskLinkMatchingType.forValue((int)configurationContent.getMatchingInfo().getMatchingType())) != TaskLinkMatchingType.FORM_TYPE_EXPRESSION || (existArray = (existMap = versionObjectInfo.getExpressionFormulaMap()).get(entityRow.getAsString("CODE"))) == null || existArray[0] == null) continue;
            this.codeToExpValues.put(entityRow.getAsString("CODE"), String.valueOf(existArray[0]));
            this.expValueToFmdmData.put(String.valueOf(existArray[0]), fmdMData);
        }
    }

    public Map<String, IFMDMData> getEntityMaps() {
        return this.entityMaps;
    }

    public Map<String, IFMDMData> getDwmcMaps() {
        return this.dwmcMaps;
    }

    public Map<String, IFMDMData> getBizKeyMaps() {
        return this.bizKeyMaps;
    }

    public Map<String, IFMDMData> getCodeMaps() {
        return this.CodeMaps;
    }

    public Map<String, IFMDMData> getExpValueToFmdmData() {
        return this.expValueToFmdmData;
    }

    public Map<String, String> getCodeToExpValues() {
        return this.codeToExpValues;
    }

    public int getMergerAndBalance() {
        return this.mergerAndBalance;
    }

    public Map<String, IFMDMData> getMapByMatchingType(TaskLinkMatchingType matchType) {
        Map<String, IFMDMData> result = new HashMap<String, IFMDMData>();
        switch (matchType) {
            case MATCHING_TYPE_CODE: {
                result = this.CodeMaps;
                break;
            }
            case MATCHING_TYPE_TITLE: {
                result = this.dwmcMaps;
                break;
            }
            case FORM_TYPE_EXPRESSION: {
                result = this.expValueToFmdmData;
                break;
            }
            case MATCHING_TYPE_PRIMARYKEY: {
                result = this.bizKeyMaps;
            }
        }
        return result;
    }
}

