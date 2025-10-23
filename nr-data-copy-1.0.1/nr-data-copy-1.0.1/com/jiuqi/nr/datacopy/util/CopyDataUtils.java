/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.datacopy.util;

import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datacopy.param.CopyDataTableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyDataUtils {
    private static final Logger logger = LoggerFactory.getLogger(CopyDataUtils.class);

    public static List<CopyDataTableDefine> getCopyDataTableDefineList(String formKey) {
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        ArrayList<CopyDataTableDefine> copyDataTableDefines = new ArrayList<CopyDataTableDefine>();
        List allRegionsInForm = runtimeView.getAllRegionsInForm(formKey);
        try {
            for (DataRegionDefine region : allRegionsInForm) {
                FieldDefine orderFieldDefine;
                HashMap<String, Set> tableFieldMap = new HashMap<String, Set>();
                boolean isFixed = region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
                List allLinks = runtimeView.getAllLinksInRegion(region.getKey());
                if (region.getInputOrderFieldKey() != null && (orderFieldDefine = dataDefinitionRuntimeController.queryFieldDefine(region.getInputOrderFieldKey())) != null) {
                    orderFieldDefine.getOwnerTableKey();
                    HashSet<String> fieldKeySet = new HashSet<String>();
                    fieldKeySet.add(orderFieldDefine.getKey());
                    tableFieldMap.put(orderFieldDefine.getOwnerTableKey(), fieldKeySet);
                }
                for (DataLinkDefine linkDefine : allLinks) {
                    FieldDefine fieldDefine;
                    if (linkDefine.getPosX() < region.getRegionLeft() || linkDefine.getPosY() < region.getRegionTop() || linkDefine.getPosX() > region.getRegionRight() || linkDefine.getPosY() > region.getRegionBottom() || linkDefine.getLinkExpression() == null || linkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD && linkDefine.getType() != DataLinkType.DATA_LINK_TYPE_INFO || (fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())) == null) continue;
                    tableFieldMap.computeIfAbsent(fieldDefine.getOwnerTableKey(), k -> new HashSet()).add(fieldDefine.getKey());
                }
                for (String tableKey : tableFieldMap.keySet()) {
                    Set fieldKeySet = (Set)tableFieldMap.get(tableKey);
                    TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(tableKey);
                    CopyDataTableDefine copyDataTableDefine = new CopyDataTableDefine((DataTable)tableDefine, isFixed);
                    List fieldList = !isFixed ? dataDefinitionRuntimeController.getAllFieldsInTable(tableKey) : dataDefinitionRuntimeController.queryFieldDefines((Collection)fieldKeySet);
                    copyDataTableDefine.setFormKey(formKey);
                    copyDataTableDefine.setRegionKey(region.getKey());
                    copyDataTableDefine.setFilterCondition(region.getFilterCondition());
                    ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
                    for (FieldDefine fieldDefine : fieldList) {
                        dataFieldList.add((DataField)fieldDefine);
                    }
                    copyDataTableDefine.setCopyDataFields(dataFieldList);
                    copyDataTableDefines.add(copyDataTableDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u8868\u5355".concat(formKey).concat("\u6570\u636e\u590d\u5236\u5b58\u50a8\u8868\u5931\u8d25"), e);
        }
        return copyDataTableDefines;
    }

    public static CopyDataTableDefine getCopyDataTableDefine(String tableCode, boolean isFixed, Collection<String> values) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        ArrayList<DataField> targetFields = new ArrayList<DataField>();
        DataTable targetTable = runtimeDataSchemeService.getDataTableByCode(tableCode);
        CopyDataTableDefine targetTableDefine = new CopyDataTableDefine(targetTable, isFixed);
        List dataFieldByTableCode = runtimeDataSchemeService.getDataFieldByTableCode(tableCode);
        Map<String, DataField> code2Field = dataFieldByTableCode.stream().collect(Collectors.toMap(Basic::getCode, a -> a));
        for (String targetFieldCode : values) {
            DataField dataField = code2Field.get(targetFieldCode);
            if (dataField == null) {
                logger.error("\u672a\u627e\u5230\u6620\u5c04\u6307\u6807{}\uff01", (Object)targetFieldCode);
                return null;
            }
            targetFields.add(dataField);
        }
        targetTableDefine.setCopyDataFields(targetFields);
        CopyDataUtils.setCopyDataTableDefine(targetTableDefine);
        return targetTableDefine;
    }

    public static void setCopyDataTableDefine(CopyDataTableDefine tableDefine) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        ArrayList<DataField> classifyFields = new ArrayList<DataField>();
        List<String> dimIds = CopyDataUtils.getBizFields(tableDefine.getTableDefine());
        DataField periodField = null;
        DataField orderField = null;
        DataField unitField = null;
        DataField bizOrderField = null;
        List dataFieldByTableCode = runtimeDataSchemeService.getDataFieldByTableCode(tableDefine.getTableDefine().getCode());
        for (DataField fieldDefine : dataFieldByTableCode) {
            String fieldKey = fieldDefine.getKey();
            FieldValueType valueType = fieldDefine.getValueType();
            if (valueType == FieldValueType.FIELD_VALUE_INPUT_ORDER) {
                orderField = fieldDefine;
                continue;
            }
            if (valueType == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) {
                bizOrderField = fieldDefine;
                continue;
            }
            if (!dimIds.contains(fieldKey)) continue;
            String code = fieldDefine.getCode();
            if (code.equals("MDCODE")) {
                unitField = fieldDefine;
                continue;
            }
            if (code.equals("DATATIME")) {
                periodField = fieldDefine;
                continue;
            }
            if (fieldDefine.getDataFieldKind() != DataFieldKind.PUBLIC_FIELD_DIM) continue;
            classifyFields.add(fieldDefine);
        }
        tableDefine.setUnitField(unitField);
        tableDefine.setPeriodField(periodField);
        tableDefine.setOrderField(orderField);
        tableDefine.setBizOrderField(bizOrderField);
        tableDefine.setPublicDimFields(classifyFields);
    }

    private static List<String> getBizFields(DataTable tableDefine) {
        String[] bizKey = tableDefine.getBizKeys();
        if (bizKey == null || bizKey.length == 0) {
            return new ArrayList<String>();
        }
        return Arrays.asList(bizKey);
    }

    private static Set<String> getUnClassifyFields(DataTable tableDefine) {
        HashSet<String> gatherKeys = new HashSet<String>();
        String[] gatherFieldKey = tableDefine.getGatherFieldKeys();
        if (gatherFieldKey == null || gatherFieldKey.length == 0) {
            return gatherKeys;
        }
        gatherKeys.addAll(Arrays.asList(gatherFieldKey));
        return gatherKeys;
    }
}

