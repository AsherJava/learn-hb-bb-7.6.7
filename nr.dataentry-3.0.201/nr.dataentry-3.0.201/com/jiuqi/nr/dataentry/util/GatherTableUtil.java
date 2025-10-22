/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.common.DefinitionHelper
 *  com.jiuqi.nr.data.engine.gather.GatherTableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionHelper;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.dataentry.bean.NodeCheckFieldMessage;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatherTableUtil {
    private static final Logger logger = LoggerFactory.getLogger(GatherTableUtil.class);
    private static final String CONDITION_ALL = "ALL";

    public static List<GatherTableDefine> getGatherTables(String formKey, Map<String, HashMap<String, NodeCheckFieldMessage>> nodeCheckfieldMap, Map<String, HashSet<String>> tableCache, HashSet<String> warnTables) {
        IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        ArrayList<GatherTableDefine> gatherTableDefineList = new ArrayList<GatherTableDefine>();
        FormDefine formDefine = runtimeView.queryFormById(formKey);
        if (!formDefine.getIsGather()) {
            StringBuilder logInfo = new StringBuilder();
            logInfo.append("\u8868\u5355").append(formDefine.getTitle()).append("\u3010").append(formDefine.getFormCode()).append("\u3011\u6c47\u603b\u7c7b\u578b\u4e3a\u4e0d\u6c47\u603b\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
            logger.info(logInfo.toString());
            return gatherTableDefineList;
        }
        try {
            List allRegionsInForm = runtimeView.getAllRegionsInForm(formKey);
            for (DataRegionDefine region : allRegionsInForm) {
                FieldDefine orderFieldDefine;
                HashMap tableFieldMap = new HashMap();
                boolean isFixed = region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
                List allLinks = runtimeView.getAllLinksInRegion(region.getKey());
                if (region.getInputOrderFieldKey() != null && (orderFieldDefine = dataDefinitionRuntimeController.queryFieldDefine(region.getInputOrderFieldKey())) != null) {
                    Set<String> fieldKeySet;
                    if (tableFieldMap.containsKey(orderFieldDefine.getOwnerTableKey())) {
                        fieldKeySet = (Set)tableFieldMap.get(orderFieldDefine.getOwnerTableKey());
                        fieldKeySet.add(orderFieldDefine.getKey());
                    } else {
                        fieldKeySet = new HashSet<String>();
                        fieldKeySet.add(orderFieldDefine.getKey());
                        tableFieldMap.put(orderFieldDefine.getOwnerTableKey(), fieldKeySet);
                    }
                }
                for (DataLinkDefine linkDefine : allLinks) {
                    Set<String> fieldKeySet;
                    FieldDefine fieldDefine;
                    if (linkDefine.getPosX() < region.getRegionLeft() || linkDefine.getPosY() < region.getRegionTop() || linkDefine.getPosX() > region.getRegionRight() || linkDefine.getPosY() > region.getRegionBottom() || linkDefine.getLinkExpression() == null || linkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldDefine = dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())) == null) continue;
                    if (nodeCheckfieldMap != null) {
                        NodeCheckFieldMessage nodeCheckFieldMessage = new NodeCheckFieldMessage();
                        nodeCheckFieldMessage.setDataLinkKey(linkDefine.getKey());
                        nodeCheckFieldMessage.setFieldKey(fieldDefine.getKey());
                        nodeCheckFieldMessage.setFormKey(formKey);
                        nodeCheckFieldMessage.setFormTitle(formDefine.getTitle());
                        nodeCheckFieldMessage.setFormOrder(formDefine.getOrder());
                        nodeCheckFieldMessage.setRegionKey(region.getKey());
                        HashMap<String, NodeCheckFieldMessage> regionCache = nodeCheckfieldMap.get(region.getKey());
                        if (regionCache == null) {
                            regionCache = new HashMap();
                            nodeCheckfieldMap.put(region.getKey(), regionCache);
                        }
                        regionCache.put(fieldDefine.getKey(), nodeCheckFieldMessage);
                    }
                    if (tableFieldMap.containsKey(fieldDefine.getOwnerTableKey())) {
                        fieldKeySet = (Set)tableFieldMap.get(fieldDefine.getOwnerTableKey());
                        fieldKeySet.add(fieldDefine.getKey());
                        continue;
                    }
                    fieldKeySet = new HashSet<String>();
                    fieldKeySet.add(fieldDefine.getKey());
                    tableFieldMap.put(fieldDefine.getOwnerTableKey(), fieldKeySet);
                }
                for (String tablekey : tableFieldMap.keySet()) {
                    if (isFixed || nodeCheckfieldMap != null) {
                        Object fieldKey2;
                        Set fieldKeySet = (Set)tableFieldMap.get(tablekey);
                        TableDefine tableDefine = dataDefinitionRuntimeController.queryTableDefine(tablekey);
                        GatherTableDefine gatherTableDefine = new GatherTableDefine((DataTable)tableDefine, isFixed);
                        ArrayList<FieldDefine> fieldList = new ArrayList<FieldDefine>();
                        for (Object fieldKey2 : fieldKeySet) {
                            fieldList.add(dataDefinitionRuntimeController.queryFieldDefine((String)fieldKey2));
                        }
                        gatherTableDefine.setFilterCondition(region.getFilterCondition());
                        ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
                        fieldKey2 = fieldList.iterator();
                        while (fieldKey2.hasNext()) {
                            FieldDefine fieldDefine = (FieldDefine)fieldKey2.next();
                            dataFieldList.add((DataField)fieldDefine);
                        }
                        gatherTableDefine.setGatherFields(dataFieldList);
                        gatherTableDefine.setRegionKey(region.getKey());
                        gatherTableDefine.setFormId(formKey);
                        gatherTableDefineList.add(gatherTableDefine);
                        continue;
                    }
                    IRuntimeDataSchemeService dataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
                    DataTable dataTable = dataSchemeService.getDataTable(tablekey);
                    List deployInfoByDataTableKey = dataSchemeService.getDeployInfoByDataTableKey(tablekey);
                    String tableName = ((DataFieldDeployInfo)deployInfoByDataTableKey.get(0)).getTableName();
                    String rowFilter = StringUtils.isEmpty((String)region.getFilterCondition()) ? CONDITION_ALL : region.getFilterCondition().toUpperCase();
                    HashSet<Object> filterSet = new HashSet<String>();
                    if (tableCache.containsKey(tablekey)) {
                        filterSet = tableCache.get(tablekey);
                        if (rowFilter.equals(CONDITION_ALL)) {
                            if (!filterSet.contains(CONDITION_ALL) && filterSet.size() > 0) {
                                warnTables.add(tableName);
                            }
                        } else if (filterSet.contains(CONDITION_ALL) || filterSet.contains(rowFilter)) {
                            warnTables.add(tableName);
                        }
                    } else {
                        filterSet.add(rowFilter);
                        tableCache.put(tablekey, filterSet);
                    }
                    GatherTableDefine gatherTableDefine = new GatherTableDefine(dataTable, isFixed);
                    List allFields = dataDefinitionRuntimeController.getAllFieldsInTable(tablekey);
                    ArrayList<DataField> dataFieldList = new ArrayList<DataField>();
                    for (FieldDefine allField : allFields) {
                        dataFieldList.add(DefinitionHelper.toDataField((FieldDefine)allField));
                    }
                    gatherTableDefine.setGatherFields(dataFieldList);
                    gatherTableDefine.setFilterCondition(region.getFilterCondition());
                    gatherTableDefine.setRegionKey(region.getKey());
                    gatherTableDefine.setFormId(formKey);
                    gatherTableDefineList.add(gatherTableDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u8868\u5355".concat(formKey).concat("\u6c47\u603b\u5b58\u50a8\u8868\u5931\u8d25"), e);
        }
        return gatherTableDefineList;
    }
}

