/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NrTool {
    private static final Logger log = LoggerFactory.getLogger(NrTool.class);

    public static Set<String> getEntityTableNames(String formSchemeId) {
        try {
            String[] entityIds;
            HashSet<String> result = new HashSet<String>(8);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            FormSchemeDefine formScheme = runTimeViewController.getFormScheme(formSchemeId);
            if (null == formScheme.getDims()) {
                return result;
            }
            IEntityMetaService entityMetaService = (IEntityMetaService)SpringContextUtils.getBean(IEntityMetaService.class);
            for (String entityId : entityIds = formScheme.getDims().split(";")) {
                TableModelDefine tableDefine = entityMetaService.getTableModel(entityId);
                if (null == tableDefine) continue;
                result.add(tableDefine.getName());
            }
            return result;
        }
        catch (Exception e) {
            log.error("\u6839\u636e\u62a5\u8868\u65b9\u6848\u67e5\u8be2\u4e3b\u4f53\u8868\u51fa\u9519", e);
            return null;
        }
    }

    public static Set<String> getAllNumberFieldDefineNamesByTableName(String tableName) {
        List<ColumnModelDefine> fieldDefines = NrTool.queryAllColumnsInTable(tableName);
        return fieldDefines.stream().filter(item -> item.getColumnType() == ColumnModelType.BIGDECIMAL || item.getColumnType() == ColumnModelType.DOUBLE || item.getColumnType() == ColumnModelType.INTEGER).map(ColumnModelDefine::getName).collect(Collectors.toSet());
    }

    public static List<ColumnModelDefine> queryAllColumnsInTable(String tableName) {
        try {
            DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByName(tableName);
            return dataModelService.getColumnModelDefinesByTable(tableDefine.getID());
        }
        catch (Exception e) {
            log.error("\u6839\u636etableName\u67e5\u8be2\u5b57\u6bb5\u51fa\u9519", e);
            return new ArrayList<ColumnModelDefine>();
        }
    }

    public static Set<String> getAllFieldDefineNamesByTableName(String tableName) {
        List<ColumnModelDefine> fieldDefines = NrTool.queryAllColumnsInTable(tableName);
        return fieldDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toSet());
    }

    public static Set<FormDefine> getFormDefineByTableName(String schemeId, String tableName) {
        HashSet<FormDefine> result = new HashSet<FormDefine>(16);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            List formDefines = runTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
            if (CollectionUtils.isEmpty((Collection)formDefines)) {
                return null;
            }
            formDefines.stream().filter(Objects::nonNull).forEach(formDefine -> {
                List dataRegionDefines = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
                if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
                    return;
                }
                for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                    Set tableNameSet;
                    List tableDefines = dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()));
                    if (CollectionUtils.isEmpty((Collection)tableDefines) || !(tableNameSet = tableDefines.stream().map(tableDefine -> dataModelService.getTableModelDefineByCode(tableDefine.getCode()).getName()).collect(Collectors.toSet())).contains(tableName)) continue;
                    result.add((FormDefine)formDefine);
                }
            });
        }
        catch (Exception e) {
            log.error("\u6839\u636e\u7269\u7406\u8868\u67e5\u8be2\u5f15\u7528\u62a5\u8868\u51fa\u9519", e);
            return result;
        }
        return result;
    }

    public static Set<TableModelDefine> getTableDefineByFormCode(String schemeId, String formCode, DataRegionKind allowRegionKind) {
        HashSet<TableModelDefine> result = new HashSet<TableModelDefine>(16);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            FormDefine formDefine = runTimeViewController.queryFormByCodeInScheme(schemeId, formCode);
            if (null == formDefine) {
                return result;
            }
            List dataRegionDefines = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
                return result;
            }
            for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
                List deployInfos;
                if (null != allowRegionKind && !allowRegionKind.equals((Object)dataRegionDefine.getRegionKind()) || CollectionUtils.isEmpty((Collection)(deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()).toArray(new String[0]))))) continue;
                result.addAll(dataModelService.getTableModelDefinesByIds((Collection)deployInfos.stream().map(DataFieldDeployInfo::getTableModelKey).distinct().collect(Collectors.toList())));
            }
        }
        catch (Exception e) {
            log.error("\u6839\u636e\u5f15\u7528\u62a5\u8868\u67e5\u8be2\u7269\u7406\u8868\u51fa\u9519", e);
            return result;
        }
        return result;
    }

    public static String getDictCodeByTitle(String tableCode, String title, Map<String, Map<String, String>> tableName2MapDictTitle2CodeCache) {
        if (!tableName2MapDictTitle2CodeCache.containsKey(tableCode)) {
            tableName2MapDictTitle2CodeCache.put(tableCode, NrTool.dictTitle2CodeMap(tableCode));
        }
        return tableName2MapDictTitle2CodeCache.get(tableCode).get(title);
    }

    private static Map<String, String> dictTitle2CodeMap(String tableCode) {
        List baseData = GcBaseDataCenterTool.getInstance().queryBasedataItems(tableCode);
        if (null == baseData) {
            return new HashMap<String, String>(1);
        }
        return baseData.stream().collect(Collectors.toMap(GcBaseData::getTitle, GcBaseData::getCode, (v1, v2) -> v2));
    }

    public static AbstractData getZbValue(DimensionValueSet ds, String tableZbCode) throws Exception {
        FieldDefine fieldDefine = NrTool.queryFieldDefineByZbCode(tableZbCode);
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery();
        dataQuery.addColumn(fieldDefine);
        dataQuery.setMasterKeys(ds);
        IDataDefinitionRuntimeController runtimeCtrl = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(runtimeCtrl);
        context.setUseDnaSql(false);
        IDataTable dataTable = dataQuery.executeQuery(context);
        int rowCount = dataTable.getCount();
        if (rowCount > 0) {
            IDataRow dataRow = dataTable.getItem(0);
            return dataRow.getValue(fieldDefine);
        }
        return null;
    }

    public static FieldDefine queryFieldDefineByZbCode(String tableZbCode) {
        String regex;
        if (!(tableZbCode = tableZbCode.trim()).matches(regex = "(.+)\\[(.+)\\]")) {
            Object[] i18Args = new String[]{tableZbCode};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.util.nrtool.tablezberror", (Object[])i18Args));
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tableZbCode);
        String tableName = "";
        String zbCode = "";
        if (matcher.find()) {
            tableName = matcher.group(1).trim();
            zbCode = matcher.group(2).trim();
        }
        try {
            IDataDefinitionRuntimeController runtimeCtrl = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
            TableDefine tabldefine = runtimeCtrl.queryTableDefineByCode(tableName);
            FieldDefine fieldDefine = runtimeCtrl.queryFieldByCodeInTable(zbCode, tabldefine.getKey());
            if (fieldDefine == null) {
                fieldDefine = runtimeCtrl.queryFieldByCodeInTable(tableName + "_" + zbCode, tabldefine.getKey());
            }
            return fieldDefine;
        }
        catch (Exception e) {
            log.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5bf9\u5e94\u7684\u5b57\u6bb5\u5f02\u5e38\uff1a" + tableZbCode, e);
            Object[] i18Args = new String[]{tableZbCode};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.common.util.nrtool.zberror", (Object[])i18Args));
        }
    }

    public static DataLinkDefine queryDataLinkDefineByFieldKey(String formKey, String fieldKey) {
        RunTimeAuthViewController runTimeViewController = (RunTimeAuthViewController)SpringContextUtils.getBean(RunTimeAuthViewController.class);
        List dataLinkDefines = runTimeViewController.getLinksInFormByField(formKey, fieldKey);
        if (CollectionUtils.isEmpty((Collection)dataLinkDefines)) {
            return null;
        }
        return (DataLinkDefine)dataLinkDefines.get(0);
    }

    public static List<GcBaseData> listSubjectsByFormKeyAndTableCode(String formKey, String tableCode) throws Exception {
        IDataDefinitionRuntimeController runtimeCtrl = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        IEntityViewRunTimeController viewRunCtrl = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        TableDefine tableDefine = runtimeCtrl.queryTableDefineByCode(tableCode);
        FieldDefine define = runtimeCtrl.queryFieldByCodeInTable("SUBJECTCODE", tableDefine.getKey());
        List allRegionDefines = runTimeViewController.getAllRegionsInForm(formKey);
        if (allRegionDefines == null) {
            return CollectionUtils.newArrayList();
        }
        String selectViewKey = null;
        for (DataRegionDefine regionDefine : allRegionDefines) {
            List allLinkDefines;
            if (regionDefine.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE && !CollectionUtils.isEmpty((Collection)(allLinkDefines = runTimeViewController.getLinksInRegionByField(regionDefine.getKey(), define.getKey())))) continue;
        }
        if (StringUtils.isEmpty(selectViewKey)) {
            return CollectionUtils.newArrayList();
        }
        TableDefine subjectTableDefine = runtimeCtrl.queryTableDefineByCode("MD_GCSUBJECT");
        FieldDefine subjectCodeDefine = runtimeCtrl.queryFieldByCodeInTable("CODE", subjectTableDefine.getKey());
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        IEntityQuery entityQuery = dataAccessProvider.newEntityQuery();
        entityQuery.addColumn(subjectCodeDefine);
        return CollectionUtils.newArrayList();
    }

    public static String getOrgTypeBySchemeId(String schemeDefineKey) {
        EntityViewData masterEntityView = ((IJtableParamService)SpringContextUtils.getBean(IJtableParamService.class)).getDwEntity(schemeDefineKey);
        return masterEntityView.getTableName();
    }
}

