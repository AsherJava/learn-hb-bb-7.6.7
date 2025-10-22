/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.log.LogModuleEnum
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  io.netty.util.internal.StringUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.fieldselect.service.impl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.log.LogModuleEnum;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormData;
import com.jiuqi.nr.fieldselect.define.FieldSelectFormFilter;
import com.jiuqi.nr.fieldselect.define.FormData;
import com.jiuqi.nr.fieldselect.define.FormGroupData;
import com.jiuqi.nr.fieldselect.service.IFieldSelectFormFilterService;
import com.jiuqi.nr.fieldselect.service.IFieldSelectorExtend;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectCache;
import com.jiuqi.nr.fieldselect.service.impl.FieldSelectLinksData;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.service.QueryEntityUtil;
import com.jiuqi.nr.query.service.impl.QueryHelper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;

public class FieldSelectHelper {
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private IRunTimeViewController viewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    ExecutorContext executorContext;
    IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
    IDataAssist dataAssist;
    private static final String FS_ISRELATED = "isrelated";
    private static final String FS_PERIODTYPE = "periodtype";
    RunTimeAuthViewController RunAuthviewController;
    IDataDefinitionDesignTimeController designTimeController;
    IDesignTimeViewController designTimeViewController;
    IEntityViewRunTimeController entityViewRunTimeController;
    private QueryEntityUtil queryEntityUtil;
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private IEntityMetaService entityMetaService;
    private IPeriodEntityAdapter periodEntityAdapter;
    private DataModelService dataModelService;
    private static final Logger logger = LoggerFactory.getLogger(FieldSelectHelper.class);

    public FieldSelectHelper() {
        this.executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        this.dataAssist = this.dataAccessProvider.newDataAssist(this.executorContext);
        this.RunAuthviewController = (RunTimeAuthViewController)BeanUtil.getBean(RunTimeAuthViewController.class);
        this.designTimeController = (IDataDefinitionDesignTimeController)BeanUtil.getBean(IDataDefinitionDesignTimeController.class);
        this.designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(IDesignTimeViewController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
        this.queryEntityUtil = (QueryEntityUtil)BeanUtil.getBean(QueryEntityUtil.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
        this.iRuntimeDataSchemeService = (IRuntimeDataSchemeService)BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
    }

    public FieldSelectHelper(String taskId, String formSchemeKey) {
        this.designTimeController = (IDataDefinitionDesignTimeController)BeanUtil.getBean(IDataDefinitionDesignTimeController.class);
        this.designTimeViewController = (IDesignTimeViewController)BeanUtil.getBean(DesignTimeViewController.class);
        this.entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    }

    private Grid2Data getFieldsFormGridByEntity() {
        String[] rowTitles = new String[]{"\u6807\u8bc6", "\u540d\u79f0", "\u6570\u636e\u7c7b\u578b", "\u679a\u4e3e\u5b57\u5178"};
        Grid2Data gridData = new Grid2Data();
        int defaultColumn = 5;
        gridData.setColumnCount(defaultColumn);
        gridData.setRowCount(2);
        gridData.setRowHeight(1, 35);
        for (int i = 1; i < defaultColumn; ++i) {
            GridCellData headerCell = gridData.getGridCellData(i, 0);
            String title = rowTitles[i - 1];
            headerCell.setSilverHead(true);
            headerCell.setBottomBorderStyle(1);
            headerCell.setRightBorderStyle(1);
            headerCell.setSelectable(false);
            headerCell.setPersistenceData("fontSize", String.valueOf(12));
            headerCell.setForeGroundColor(0);
            headerCell.setShowText(title);
            headerCell.setHorzAlign(3);
            if (i == 1) {
                gridData.setColumnWidth(i, 150);
                continue;
            }
            if (i == 2) {
                gridData.setColumnWidth(i, 150);
                continue;
            }
            gridData.setColumnWidth(i, 150);
        }
        gridData.setColumnHidden(0, true);
        return gridData;
    }

    private String getFieldTypeName(FieldType type) {
        String name = "";
        switch (type.getValue()) {
            case 0: {
                name = "\u901a\u7528\u7c7b\u578b";
                break;
            }
            case 1: {
                name = "\u6d6e\u70b9\u7c7b\u578b";
                break;
            }
            case 2: {
                name = "\u5b57\u7b26\u7c7b\u578b";
                break;
            }
            case 3: {
                name = "\u6574\u6570\u7c7b\u578b";
                break;
            }
            case 4: {
                name = "\u903b\u8f91/\u5e03\u5c14\u7c7b\u578b";
                break;
            }
            case 5: {
                name = "\u65e5\u671f\u7c7b\u578b";
                break;
            }
            case 6: {
                name = "\u65f6\u95f4\u65e5\u671f\u578b";
                break;
            }
            case 7: {
                name = "UUID\u7c7b\u578b";
                break;
            }
            case 8: {
                name = "\u6570\u503c\u578b";
                break;
            }
            case 9: {
                name = "\u65f6\u95f4\u6233";
                break;
            }
            case 16: {
                name = "\u6587\u672c\u7c7b\u578b";
                break;
            }
            case 19: {
                name = "\u65f6\u95f4\u7c7b\u578b";
                break;
            }
            case 23: {
                name = "\u679a\u4e3e\u7c7b\u578b";
                break;
            }
            default: {
                name = "\u672a\u77e5\u7c7b\u578b";
            }
        }
        return name;
    }

    private String getEnumTitle(String referFieldKey) {
        FieldDefine relationField = null;
        try {
            relationField = this.dataDefinitionRuntimeController.queryFieldDefine(referFieldKey);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (relationField == null) {
            return "";
        }
        if (relationField.getEntityKey() != null) {
            this.getEnumTitle(relationField.getEntityKey());
        } else {
            try {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(relationField.getEntityKey());
                return iEntityDefine.getTitle();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return "";
    }

    public String getGroupAndFroms(List<IFieldSelectorExtend> fieldSelectExtend, FieldSelectFormFilter fieldSelectFormFilter) {
        try {
            List<Object> formGroups = new ArrayList();
            try {
                Iterator<IFieldSelectorExtend> iterator;
                if (fieldSelectExtend != null && fieldSelectExtend.size() > 0 && (iterator = fieldSelectExtend.iterator()).hasNext()) {
                    IFieldSelectorExtend obj = iterator.next();
                    formGroups = obj.getGroupAndForms(fieldSelectFormFilter.getSchemeKey());
                }
            }
            catch (BeansException ex) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u52a0\u8f7d\u811a\u672c\u5904\u7406\u5668Bean\u5931\u8d25", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
                logger.error("\u52a0\u8f7d\u811a\u672c\u5904\u7406\u5668Bean\u5931\u8d25\uff01", ex);
            }
            catch (Exception ex) {
                LogHelper.info((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u83b7\u53d6\u81ea\u5b9a\u4e49\u62a5\u8868\u5217\u8868\u5f02\u5e38", (String)("\u5f02\u5e38\u539f\u56e0\uff1a" + ex));
            }
            IFieldSelectFormFilterService fieldSelectFormFilterService = (IFieldSelectFormFilterService)BeanUtil.getBean(IFieldSelectFormFilterService.class);
            if (formGroups == null || formGroups.size() == 0) {
                String schemeID = fieldSelectFormFilter.getSchemeKey();
                List groups = this.RunAuthviewController.getAllFormGroupsInFormScheme(schemeID);
                if (groups == null) {
                    return "";
                }
                for (FormGroupDefine group : groups) {
                    FormGroupData fgData = new FormGroupData();
                    fgData.setId(group.getKey());
                    fgData.setTitle(group.getTitle());
                    List forms = this.RunAuthviewController.getAllFormsInGroup(group.getKey());
                    if (forms == null) continue;
                    ArrayList<FormData> formDatas = new ArrayList<FormData>();
                    for (FormDefine form : forms) {
                        boolean fmForm;
                        boolean bl = fmForm = FormType.FORM_TYPE_NEWFMDM.equals((Object)form.getFormType()) || FormType.FORM_TYPE_FMDM.equals((Object)form.getFormType());
                        if (!Boolean.valueOf(fieldSelectFormFilter.getDisplayFMDM()).booleanValue() && fmForm || form.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT || fieldSelectFormFilterService != null && fieldSelectFormFilterService.filterForm(fieldSelectFormFilter.getFilterCondition(), form.getKey())) continue;
                        FormData fdata = new FormData();
                        fdata.setId(form.getKey());
                        fdata.setTitle(form.getTitle());
                        fdata.setCode(form.getFormCode());
                        fdata.setSerialNum(form.getSerialNumber());
                        formDatas.add(fdata);
                    }
                    if (formDatas.size() == 0) continue;
                    fgData.setForms(formDatas);
                    formGroups.add(fgData);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(formGroups);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "";
        }
    }

    public String getDesignGroupAndFroms(String schemeKey) {
        try {
            ArrayList<FormGroupData> formGroups = new ArrayList<FormGroupData>();
            String schemeID = schemeKey;
            List groups = this.designTimeViewController.queryAllGroupsByFormScheme(schemeID);
            for (FormGroupDefine group : groups) {
                FormGroupData fgData = new FormGroupData();
                fgData.setId(group.getKey());
                fgData.setTitle(group.getTitle());
                List forms = this.designTimeViewController.getAllFormsInGroup(group.getKey(), true);
                ArrayList<FormData> formDatas = new ArrayList<FormData>();
                for (FormDefine form : forms) {
                    if (form.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) continue;
                    FormData fdata = new FormData();
                    fdata.setId(form.getKey());
                    fdata.setTitle(form.getTitle());
                    fdata.setCode(form.getFormCode());
                    fdata.setSerialNum(form.getSerialNumber());
                    formDatas.add(fdata);
                }
                if (formDatas.size() == 0) continue;
                fgData.setForms(formDatas);
                formGroups.add(fgData);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(formGroups);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return "";
        }
    }

    public Grid2Data getFieldsGrid(String formid) {
        try {
            Grid2Data gridData = this.getFieldsFormGrid();
            if (formid != null) {
                FormDefine queryForm = this.viewController.queryFormById(formid);
                List allRegionsInForms = this.viewController.getAllRegionsInForm(formid);
                if (allRegionsInForms != null && allRegionsInForms.size() > 0) {
                    for (DataRegionDefine allRegionsInForm : allRegionsInForms) {
                        DataRegionDefine region = this.viewController.queryDataRegionDefine(allRegionsInForm.getKey());
                        if (region == null) {
                            return null;
                        }
                        List listFieldDefine = null;
                        listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.viewController.getFieldKeysInRegion(region.getKey()));
                        if (listFieldDefine == null) continue;
                        int size = listFieldDefine.size();
                        gridData.insertRows(2, size - 1, 1);
                        for (int i = 0; i < size; ++i) {
                            FieldDefine field = (FieldDefine)listFieldDefine.get(i);
                            JSONObject data = new JSONObject();
                            data.put("RegionKey", (Object)formid);
                            data.put("FieldKey", (Object)field.getKey());
                            data.put("TableKey", (Object)field.getOwnerTableKey());
                            data.put("FieldCode", (Object)field.getCode());
                            data.put("FieldTitle", (Object)field.getTitle());
                            GridCellData dataCell = this.getCell(1, i + 1, gridData, String.valueOf(i + 1));
                            dataCell.setDataExFromString(data.toString());
                            dataCell = this.getCell(2, i + 1, gridData, field.getCode());
                            dataCell.setDataExFromString(data.toString());
                            dataCell = this.getCell(3, i + 1, gridData, field.getTitle());
                            dataCell.setDataExFromString(data.toString());
                            dataCell.setHorzAlign(1);
                            dataCell = this.getCell(4, i + 1, gridData, queryForm.getTitle());
                            dataCell.setHorzAlign(1);
                            dataCell.setDataExFromString(data.toString());
                        }
                    }
                }
            }
            return gridData;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public boolean checkFieldAuth(String fieldKey) {
        return true;
    }

    public boolean checkFieldWriteAuth(String fieldKey) {
        return true;
    }

    public Grid2Data getChildGridByTable(String keyId, String tableTitle) {
        try {
            Grid2Data gridData = this.getFieldsFormGrid();
            List fields = this.designTimeController.queryFieldDefines(new String[]{keyId});
            if (fields != null) {
                int size = fields.size();
                gridData.insertRows(2, size - 1, 1);
                for (int i = 0; i < size; ++i) {
                    FieldDefine field = (FieldDefine)fields.get(i);
                    DataTable tableModel = this.iRuntimeDataSchemeService.getDataTable(field.getOwnerTableKey());
                    JSONObject data = new JSONObject();
                    data.put("TableKey", (Object)field.getOwnerTableKey());
                    data.put("RegionKey", (Object)keyId);
                    data.put("FieldKey", (Object)field.getKey());
                    data.put("FieldCode", (Object)field.getCode());
                    data.put("FieldTitle", (Object)field.getTitle());
                    data.put("FieldType", (Object)field.getType().name());
                    data.put("GatherType", (Object)field.getGatherType().name());
                    data.put("TableName", (Object)this.dataDefinitionRuntimeController.queryTableDefine(tableModel.getCode()));
                    GridCellData dataCell = this.getCell(1, i + 1, gridData, String.valueOf(i + 1));
                    dataCell.setDataExFromString(data.toString());
                    dataCell = this.getCell(2, i + 1, gridData, field.getCode());
                    dataCell.setDataExFromString(data.toString());
                    dataCell = this.getCell(3, i + 1, gridData, field.getTitle());
                    dataCell.setDataExFromString(data.toString());
                    dataCell.setHorzAlign(1);
                    dataCell = this.getCell(4, i + 1, gridData, tableTitle);
                    dataCell.setHorzAlign(1);
                    dataCell.setDataExFromString(data.toString());
                }
            }
            return gridData;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    private Grid2Data getFieldsFormGrid() {
        String[] rowTitles = new String[]{"\u5e8f\u53f7", "\u4ee3\u7801", "\u540d\u79f0", "\u6240\u5c5e\u5206\u7ec4"};
        Grid2Data gridData = new Grid2Data();
        int defaultColumn = 5;
        gridData.setColumnCount(defaultColumn);
        gridData.setRowCount(2);
        for (int i = 1; i < defaultColumn; ++i) {
            GridCellData headerCell = gridData.getGridCellData(i, 0);
            String title = rowTitles[i - 1];
            headerCell.setSilverHead(true);
            headerCell.setBottomBorderStyle(1);
            headerCell.setRightBorderStyle(1);
            headerCell.setSelectable(false);
            headerCell.setPersistenceData("fontSize", String.valueOf(12));
            headerCell.setForeGroundColor(0);
            headerCell.setShowText(title);
            if (i < 3) {
                headerCell.setHorzAlign(3);
            } else {
                headerCell.setHorzAlign(1);
            }
            if (i == 1) {
                gridData.setColumnWidth(i, 50);
                continue;
            }
            if (i == 2) {
                gridData.setColumnWidth(i, 100);
                continue;
            }
            gridData.setColumnAutoWidth(i, true);
        }
        gridData.setColumnHidden(0, true);
        return gridData;
    }

    private FieldType columnTypeToFieldType(ColumnModelType columnModelType) {
        switch (columnModelType) {
            case BLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case DOUBLE: {
                return FieldType.FIELD_TYPE_FLOAT;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case UUID: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case BIGDECIMAL: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case CLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case DATETIME: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
            case ATTACHMENT: {
                return FieldType.FIELD_TYPE_FILE;
            }
        }
        return FieldType.FIELD_TYPE_GENERAL;
    }

    public FieldSelectFormData getFormData(String formSchemeKey, String formKey, Boolean isUseRelateFieldType) {
        FieldSelectFormData formData = new FieldSelectFormData();
        try {
            formData.setFormKey(formKey);
            List allRegionsInForms = this.RunAuthviewController.getAllRegionsInForm(formKey);
            ArrayList<String> regions = new ArrayList<String>();
            LinkedHashMap<String, List<FieldSelectLinksData>> regionLinks = new LinkedHashMap<String, List<FieldSelectLinksData>>();
            Grid2Data griddata = this.getGridData(formKey);
            FormDefine formDefine = this.viewController.queryFormById(formKey);
            for (DataRegionDefine region : allRegionsInForms) {
                String regionKey = region.getKey();
                if (regions.contains(regionKey)) continue;
                regions.add(regionKey);
                List dataLinks = this.RunAuthviewController.getAllLinksInRegion(regionKey);
                ArrayList<FieldSelectLinksData> links = new ArrayList<FieldSelectLinksData>();
                for (DataLinkDefine link : dataLinks) {
                    FieldDefine fieldDefineObj;
                    FieldSelectLinksData linkData;
                    block20: {
                        linkData = new FieldSelectLinksData();
                        linkData.setRegionKind(region.getRegionKind());
                        linkData.setOwnerRegion(regionKey);
                        linkData.setCol(link.getPosX());
                        linkData.setRow(link.getPosY());
                        linkData.setLinkExpression(link.getLinkExpression());
                        linkData.setKey(link.getKey());
                        fieldDefineObj = null;
                        if (FormType.FORM_TYPE_NEWFMDM.equals((Object)formDefine.getFormType())) {
                            try {
                                IEntityModel entityModel = this.entityMetaService.getEntityModel(formDefine.getMasterEntitiesKey().split(";")[0]);
                                IEntityAttribute entityAttribute = entityModel.getAttribute(link.getLinkExpression());
                                if (entityAttribute == null) {
                                    fieldDefineObj = this.dataDefinitionRuntimeController.queryFieldDefine(link.getLinkExpression());
                                    break block20;
                                }
                                fieldDefineObj = this.dataModelService.getColumnModelDefineByID(entityAttribute.getID());
                            }
                            catch (Exception e) {
                                logger.error(e.getMessage());
                            }
                        } else {
                            try {
                                if (formDefine.getMasterEntitiesKey().contains("@BASE") && link.getLinkExpression().contains("[")) {
                                    int first = link.getLinkExpression().indexOf("[");
                                    int second = link.getLinkExpression().indexOf("]");
                                    String code = link.getLinkExpression().substring(first + 1, second);
                                    IEntityModel entityModel = this.entityMetaService.getEntityModel(formDefine.getMasterEntitiesKey().split(";")[0]);
                                    IEntityAttribute entityAttribute = entityModel.getAttribute(code);
                                    fieldDefineObj = this.dataDefinitionRuntimeController.queryFieldDefine(entityAttribute.getID());
                                } else {
                                    fieldDefineObj = this.dataDefinitionRuntimeController.queryFieldDefine(link.getLinkExpression());
                                }
                            }
                            catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }
                    String title = link.getTitle();
                    if (fieldDefineObj instanceof ColumnModelDefine) {
                        ColumnModelDefine columnModelDefine = (ColumnModelDefine)fieldDefineObj;
                        FieldType fieldType = this.columnTypeToFieldType(columnModelDefine.getColumnType());
                        linkData.setDataType(fieldType);
                        linkData.setGatherType(FieldGatherType.FIELD_GATHER_NONE);
                        linkData.setFieldCode(columnModelDefine.getCode());
                        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(columnModelDefine.getTableID());
                        String tableName = tableModelDefine.getName();
                        linkData.setTableName(tableName);
                        linkData.setDataSheet(tableName);
                        linkData.setTableKey(tableModelDefine.getID());
                        title = columnModelDefine.getTitle();
                    } else if (fieldDefineObj instanceof FieldDefine) {
                        EntityViewDefine entityView;
                        FieldDefine fieldDefine = fieldDefineObj;
                        linkData.setDataType(fieldDefine.getType());
                        if (isUseRelateFieldType.booleanValue() && fieldDefine.getEntityKey() != null && (entityView = this.getRelationEnum(fieldDefine.getEntityKey())) != null) {
                            linkData.setDataType(FieldType.FIELD_TYPE_UUID);
                        }
                        linkData.setGatherType(fieldDefine.getGatherType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        if (!StringUtil.isNullOrEmpty((String)fieldDefine.getTitle())) {
                            title = fieldDefine.getTitle();
                        }
                        DataTable tableModel = this.iRuntimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
                        String tableName = tableModel.getCode();
                        linkData.setTableName(tableName);
                        linkData.setDataSheet(tableName);
                        linkData.setTableKey(tableModel.getKey());
                        linkData.setFieldCode(fieldDefine.getCode());
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setGatherType(FieldGatherType.FIELD_GATHER_NONE);
                        linkData.setFieldCode("\u672a\u77e5");
                    }
                    if (StringUtil.isNullOrEmpty((String)title)) {
                        title = "\u672a\u77e5";
                    }
                    linkData.setFieldTitle(title);
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getCol(), linkData.getRow());
                    if (cellData == null) continue;
                    cellData.setShowText(linkData.getFieldCode());
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
                regionLinks.put(regionKey, links);
            }
            formData.setLinks(regionLinks);
            formData.setRegions(regions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return formData;
    }

    private EntityViewDefine getRelationEnum(String referFieldKey) {
        IEntityDefine entityDefine = null;
        try {
            entityDefine = this.entityMetaService.queryEntity(referFieldKey);
        }
        catch (Exception e) {
            logger.error("\u83b7\u53d6\u6307\u6807\u5173\u8054\u7684\u679a\u4e3e\u7c7b\u578b\u9519\u8bef: " + e.getMessage(), e);
        }
        if (entityDefine == null) {
            return null;
        }
        return this.entityViewRunTimeController.buildEntityView(referFieldKey);
    }

    public FieldSelectFormData getDesignFormData(String formSchemeKey, String formKey) {
        FieldSelectFormData formData = new FieldSelectFormData();
        try {
            formData.setFormKey(formKey);
            List allRegionsInForms = this.designTimeViewController.getAllRegionsInForm(formKey);
            ArrayList<String> regions = new ArrayList<String>();
            LinkedHashMap<String, List<FieldSelectLinksData>> regionLinks = new LinkedHashMap<String, List<FieldSelectLinksData>>();
            Grid2Data griddata = this.getGridData(formKey);
            for (DataRegionDefine region : allRegionsInForms) {
                String regionKey = region.getKey();
                if (regions.contains(regionKey)) continue;
                regions.add(regionKey);
                List dataLinks = this.designTimeViewController.getAllLinksInRegion(regionKey);
                ArrayList<FieldSelectLinksData> links = new ArrayList<FieldSelectLinksData>();
                for (DataLinkDefine link : dataLinks) {
                    FieldSelectLinksData linkData = new FieldSelectLinksData();
                    linkData.setCol(link.getPosX());
                    linkData.setRow(link.getPosY());
                    linkData.setLinkExpression(link.getLinkExpression());
                    DesignFieldDefine fieldDefine = null;
                    try {
                        fieldDefine = this.designTimeController.queryFieldDefine(link.getLinkExpression());
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    if (fieldDefine != null) {
                        linkData.setDataType(fieldDefine.getType());
                        linkData.setGatherType(fieldDefine.getGatherType());
                        linkData.setFieldCode(fieldDefine.getCode());
                        linkData.setFieldTitle(fieldDefine.getTitle());
                    } else {
                        linkData.setDataType(FieldType.FIELD_TYPE_STRING);
                        linkData.setGatherType(FieldGatherType.FIELD_GATHER_NONE);
                        linkData.setFieldCode("\u672a\u77e5");
                        linkData.setFieldTitle("\u672a\u77e5");
                    }
                    links.add(linkData);
                    GridCellData cellData = griddata.getGridCellData(linkData.getCol(), linkData.getRow());
                    if (cellData == null) continue;
                    cellData.setShowText(linkData.getFieldCode());
                    cellData.setHorzAlign(3);
                    cellData.setForeGroundColor(255);
                }
                regionLinks.put(regionKey, links);
            }
            formData.setLinks(regionLinks);
            formData.setRegions(regions);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule((Module)module);
            String result = mapper.writeValueAsString((Object)griddata);
            formData.setGridData(result.getBytes());
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return formData;
    }

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formData = this.RunAuthviewController.getReportDataFromForm(formKey);
        if (formData != null) {
            return Grid2Data.bytesToGrid((byte[])formData.getData());
        }
        Grid2Data griddata = new Grid2Data();
        griddata.setRowCount(10);
        griddata.setColumnCount(10);
        return griddata;
    }

    public Map<String, Object> getSearchItems(String regionKey, String searchWords) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            DataRegionDefine region = this.viewController.queryDataRegionDefine(regionKey);
            if (region == null) {
                return null;
            }
            List listFieldDefine = null;
            listFieldDefine = this.dataDefinitionRuntimeController.queryFieldDefinesInRange((Collection)this.viewController.getFieldKeysInRegion(regionKey));
            if (listFieldDefine != null) {
                for (FieldDefine field : listFieldDefine) {
                    if (!field.getTitle().contains(searchWords) && !field.getCode().contains(searchWords)) continue;
                    JSONObject data = new JSONObject();
                    data.put("RegionKey", (Object)regionKey);
                    data.put("FieldKey", (Object)field.getKey());
                    data.put("FieldCode", (Object)field.getCode());
                    data.put("FieldTitle", (Object)field.getTitle());
                    result.put(field.getKey().toString(), data);
                }
            }
            return result;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    private GridCellData getCell(int col, int row, Grid2Data gridData, String title) {
        GridCellData dataCell = gridData.getGridCellData(col, row);
        dataCell.setSelectable(false);
        dataCell.setBottomBorderStyle(1);
        dataCell.setRightBorderStyle(1);
        dataCell.setHorzAlign(3);
        dataCell.setShowText(title);
        return dataCell;
    }

    public List<JSONObject> getMasterEntity(String formSchemeKey) throws Exception {
        String[] keys;
        ArrayList<JSONObject> result = new ArrayList<JSONObject>();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        String masterKeys = formScheme.getMasterEntitiesKey();
        if (masterKeys == null) {
            return null;
        }
        for (String key : keys = masterKeys.split(";")) {
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
            TableModelDefine tableDefine = this.queryEntityUtil.getEntityTablelDefineByView(key);
            if (entityView == null) continue;
            boolean periodView = this.periodEntityAdapter.isPeriodEntity(key);
            if (periodView) {
                // empty if block
            }
            JSONObject entityObject = new JSONObject();
            entityObject.put("code", (Object)entityView.getEntityId());
            entityObject.put("title", (Object)tableDefine.getTitle());
            entityObject.put("tablekind", (Object)this.queryEntityUtil.getEntityTablelKindByView(key));
            entityObject.put(FS_PERIODTYPE, (Object)formScheme.getPeriodType());
            IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
            if (entityTable.getMaxDepth() > 1) {
                entityObject.put("istree", (Object)"true");
            } else {
                entityObject.put("istree", (Object)"false");
            }
            result.add(entityObject);
        }
        return result;
    }

    public List<JSONObject> getDesignMasterEntity(String formSchemeKey) throws Exception {
        String[] keys;
        ArrayList<JSONObject> result = new ArrayList<JSONObject>();
        DesignFormSchemeDefine formScheme = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
        String masterKeys = this.designTimeViewController.getFormSchemeEntity(formSchemeKey);
        if (masterKeys == null) {
            return null;
        }
        for (String key : keys = masterKeys.split(";")) {
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
            TableModelDefine tableDefine = this.queryEntityUtil.getEntityTablelDefineByView(key);
            if (entityView == null) continue;
            JSONObject entityObject = new JSONObject();
            entityObject.put("code", (Object)entityView.getEntityId());
            entityObject.put("title", (Object)tableDefine.getTitle());
            entityObject.put("tablekind", (Object)this.queryEntityUtil.getEntityTablelKindByView(key));
            entityObject.put(FS_PERIODTYPE, (Object)formScheme.getPeriodType());
            IEntityTable entityTable = QueryHelper.getEntityTable(entityView);
            if (entityTable.getMaxDepth() > 1) {
                entityObject.put("istree", (Object)"true");
            } else {
                entityObject.put("istree", (Object)"false");
            }
            result.add(entityObject);
        }
        return result;
    }

    public Map<String, String> checkQueryRelation(Map<String, String> params) {
        try {
            String curScheme = params.get("curScheme");
            boolean isCurDimEmpty = StringUtil.isNullOrEmpty((String)params.get("curMasterDims"));
            String masterKeys = this.viewController.getFormSchemeEntity(curScheme);
            FormSchemeDefine formScheme = this.viewController.getFormScheme(curScheme);
            if (masterKeys == null) {
                return null;
            }
            String[] keys = masterKeys.split(";");
            DimensionSet c = new DimensionSet();
            if (!isCurDimEmpty) {
                for (String ekey : params.get("masterdimensions").split(",")) {
                    if (ekey.isEmpty()) continue;
                    c.addDimension(ekey);
                }
            }
            ENameSet nameSet = new ENameSet();
            boolean isMoreDim = keys.length > c.size();
            boolean isRelated = false;
            for (String key : keys) {
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
                String name = this.dataAssist.getDimensionName(entityView);
                if (!isRelated) {
                    isRelated = c.contains(name);
                }
                if (!isMoreDim && isRelated) break;
                if (name == null) continue;
                nameSet.add(name);
            }
            LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
            result.put("curMasterDims", "1");
            result.put(FS_ISRELATED, String.valueOf(isRelated || isCurDimEmpty));
            result.put("masterdimensions", isMoreDim ? nameSet.toString() : params.get("masterdimensions"));
            result.put("masterkeys", isMoreDim ? masterKeys.toString() : params.get("masterkeys"));
            if (isCurDimEmpty) {
                result.put(FS_PERIODTYPE, String.valueOf(formScheme.getPeriodType()));
            } else {
                result.put(FS_PERIODTYPE, this.getPeriodType(PeriodType.valueOf((String)params.get(FS_PERIODTYPE).toString()), formScheme.getPeriodType()));
            }
            return result;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    private String getPeriodType(PeriodType p1, PeriodType p2) {
        return p1.compareTo((Enum)p2) > 0 ? p1.toString() : p2.toString();
    }

    private String getTableName(String fieldCode) {
        try {
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
            List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
            DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
            if (deployInfoByDataFieldKeys.size() > 0) {
                deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
            }
            if (field.getEntityKey() != null) {
                return this.getTableName(field.getEntityKey());
            }
            return deployInfoByColumnKey.getTableName();
        }
        catch (Exception e) {
            return null;
        }
    }

    private FieldDefine getReferField(String fieldCode) {
        try {
            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
            if (field.getEntityKey() != null) {
                return this.dataDefinitionRuntimeController.queryFieldDefine(field.getEntityKey());
            }
            return field;
        }
        catch (Exception e) {
            logger.error("\u901a\u8fc7\u4e3b\u952e\u6307\u6807\u83b7\u53d6\u5bf9\u5e94\u8868\u540d\u51fa\u9519:\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private String getMasterField(String fieldKey) throws JQException {
        FieldDefine masterField = null;
        try {
            masterField = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_006, (Throwable)e);
        }
        if (masterField == null) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_006, (Object)masterField);
        }
        String masterKey = masterField.getEntityKey();
        if (masterKey == null) {
            return fieldKey;
        }
        return this.getMasterField(masterKey);
    }

    public List<Map<String, Object>> getMasterViewFieldsDesignTime(Map<String, Object> curCompareRelation, boolean isCurDimEmpty) {
        try {
            boolean isChanged = false;
            FieldSelectCache curCache = new FieldSelectCache();
            ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            DimensionSet cur = new DimensionSet();
            if (!isCurDimEmpty) {
                curCache = FieldSelectCache.parse((Map)curCompareRelation.get("master"));
                for (String string : curCache.getMasterDimensions().split(",")) {
                    if (StringUtils.isEmpty((String)string)) continue;
                    cur.addDimension(string.trim());
                }
            }
            for (String fieldKey : curCompareRelation.keySet()) {
                if (fieldKey.equals(FS_ISRELATED) || fieldKey.equals(FS_PERIODTYPE) || fieldKey.equals("isFirst") || fieldKey.equals("master")) continue;
                LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
                if (isCurDimEmpty && resultList.size() > 0) {
                    for (String ekey : curCache.getMasterDimensions().split(",")) {
                        if (StringUtils.isEmpty((String)ekey)) continue;
                        cur.addDimension(ekey.trim());
                    }
                }
                curCache = curCache.clone();
                FormSchemeDefine formSchemeDefine = this.getFormSchemeByField(fieldKey);
                if (formSchemeDefine != null) {
                    curCache.setSchemeKey(formSchemeDefine.getKey());
                    curCache.setTaskKey(formSchemeDefine.getTaskKey());
                }
                String masterKeys = null;
                String tableKey = curCompareRelation.get(fieldKey).toString();
                TableDefine thisTable = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
                masterKeys = thisTable.getBizKeyFieldsStr();
                if (StringUtils.isEmpty((String)masterKeys)) {
                    return null;
                }
                String[] keys = masterKeys.split(";");
                int keyLen = keys.length;
                ENameSet nameSet = new ENameSet();
                boolean isMoreDim = keyLen > cur.size();
                boolean isEque = keyLen == cur.size();
                boolean isRelated = false;
                LinkedHashMap<String, TableDefine> masterTableNameSet = new LinkedHashMap<String, TableDefine>();
                LinkedHashMap<String, Integer> masterOrder = new LinkedHashMap<String, Integer>();
                LinkedHashMap<String, String> tableKinds = new LinkedHashMap<String, String>();
                LinkedHashMap<String, TableDefine> masterDimNameSet = new LinkedHashMap<String, TableDefine>();
                String masterEntityKey = "";
                int i = 0;
                int order = 0;
                while (i < keyLen) {
                    String key = keys[i];
                    String refkey = null;
                    try {
                        refkey = this.getMasterField(key);
                    }
                    catch (JQException e) {
                        logger.error(e.getMessage(), e);
                        return null;
                    }
                    FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(refkey);
                    EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(fieldDefine.getEntityKey());
                    if (fieldDefine.getEntityKey() == null) {
                        --order;
                    } else {
                        masterEntityKey = masterEntityKey + entityView.getEntityId() + ";";
                        String name = this.dataAssist.getDimensionName(this.dataDefinitionRuntimeController.queryFieldDefine(key));
                        TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                        tableKinds.put(fieldDefine.getKey(), tableDefine.getKind().name());
                        if (!isRelated && tableDefine.getKind() != TableKind.TABLE_KIND_ENTITY_PERIOD) {
                            isRelated = cur.contains(name);
                        }
                        masterDimNameSet.put(name, tableDefine);
                        TableModelDefine tableModel = this.entityMetaService.getTableModel(tableDefine.getKey());
                        masterTableNameSet.put(tableModel.getName(), tableDefine);
                        masterOrder.put(name, order);
                        if ((isMoreDim || !isRelated) && name != null) {
                            nameSet.add(name);
                        }
                    }
                    ++i;
                    ++order;
                }
                result = new LinkedHashMap();
                result.put(FS_ISRELATED, String.valueOf(isRelated || isCurDimEmpty));
                String periodType = String.valueOf(PeriodType.DAY);
                result.put(FS_PERIODTYPE, periodType);
                if (isMoreDim || isEque) {
                    boolean isTableKeyMore;
                    LinkedHashMap<String, Object> bizFields = new LinkedHashMap<String, Object>();
                    String bizFieldKeys = "";
                    String[] bizKeys = thisTable.getBizKeyFieldsID();
                    boolean bl = isCurDimEmpty ? true : (isTableKeyMore = bizKeys.length > curCache.getBizFieldKeys().split(";").length);
                    if (isTableKeyMore || isMoreDim) {
                        int index = masterEntityKey.split(",").length;
                        for (String bizKey : bizKeys) {
                            LinkedHashMap<String, Object> bizField = new LinkedHashMap<String, Object>();
                            FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(bizKey);
                            if (field.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) continue;
                            bizFieldKeys = StringUtil.isNullOrEmpty((String)bizFieldKeys) ? field.getKey() : bizFieldKeys + ";" + field.getKey().toString();
                            String tableName = this.getTableName(bizKey);
                            String dimName = this.dataAssist.getDimensionName(field);
                            boolean isMaster = masterDimNameSet.containsKey(dimName) || masterTableNameSet.containsKey(tableName);
                            bizField.put("RegionKey", tableKey);
                            bizField.put("FieldKey", field.getKey());
                            bizField.put("FieldCode", field.getCode());
                            bizField.put("TableName", tableName);
                            bizField.put("TableKind", tableKinds.get(this.getMasterField(field.getKey())));
                            String title = field.getTitle();
                            if (isMaster) {
                                TableDefine table = (TableDefine)masterDimNameSet.get(dimName);
                                title = table.getTitle();
                                bizField.put("Order", masterOrder.get(dimName));
                            } else {
                                bizField.put("Order", index);
                                ++index;
                            }
                            bizField.put("FieldTitle", title);
                            bizField.put("IsMaster", isMaster);
                            bizFields.put(field.getKey(), bizField);
                        }
                    }
                    if ((isEque && isTableKeyMore || isMoreDim) && (isRelated || isCurDimEmpty)) {
                        curCache.setMasterDimensions(isEque ? cur.toString() : nameSet.toString());
                        curCache.setMasterKeys(masterEntityKey);
                        curCache.setBizFieldKeys(bizFieldKeys);
                        curCache.setBizFields(bizFields);
                        curCache.setPeriodType(periodType);
                        List taskDefines = this.viewController.getAllTaskDefines();
                        curCache.setRelatedTasks(taskDefines);
                        result.put(tableKey, curCache);
                        isChanged = true;
                    } else {
                        FieldSelectCache cache = new FieldSelectCache();
                        cache.setFieldCount(0);
                        result.put(tableKey, curCache);
                    }
                } else {
                    FieldSelectCache cache = new FieldSelectCache();
                    cache.setFieldCount(0);
                    result.put(tableKey, curCache);
                }
                result.put("changed", isChanged);
                resultList.add(result);
            }
            return resultList;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public Map<String, Object> getMasterViewFields(boolean isFirst, Map<String, Object> params, boolean isCurDimEmpty) {
        try {
            String schemeKey = params.get("schemeKey").toString();
            String regionKey = params.get("regionKey").toString();
            String fieldKey = params.containsKey("fieldKey") ? params.get("fieldKey").toString() : "";
            Map<String, Object> curCompareRelation = null;
            if (!isFirst) {
                curCompareRelation = params;
            }
            String formKey = params.get("formKey").toString();
            boolean isChanged = false;
            String schemeID = schemeKey;
            FormDefine formDefine = this.viewController.queryFormById(formKey);
            String masterKeys = "";
            if (StringUtils.isEmpty((String)masterKeys)) {
                masterKeys = formDefine.getMasterEntitiesKey();
            }
            try {
                FormSchemeDefine formScheme1 = this.viewController.getFormScheme(schemeID);
                masterKeys = formScheme1.getMasterEntitiesKey();
                if (StringUtils.isEmpty((String)masterKeys)) {
                    TaskDefine taskDefine = this.viewController.queryTaskDefine(formScheme1.getTaskKey());
                    masterKeys = taskDefine.getMasterEntitiesKey();
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            if (StringUtils.isEmpty((String)masterKeys)) {
                masterKeys = formDefine.getMasterEntitiesKey();
            }
            FormSchemeDefine formScheme = this.viewController.getFormScheme(schemeID);
            if (masterKeys == null) {
                return null;
            }
            String[] keys = masterKeys.split(";");
            DimensionSet c = new DimensionSet();
            FieldSelectCache curCache = new FieldSelectCache();
            if (!isCurDimEmpty) {
                for (String key : curCompareRelation.keySet()) {
                    if (key.equals(FS_ISRELATED) || key.equals(FS_PERIODTYPE)) continue;
                    curCache = FieldSelectCache.parse((Map)curCompareRelation.get(key));
                    break;
                }
                for (String ekey : curCache.getMasterDimensions().split(",")) {
                    if (ekey.isEmpty()) continue;
                    c.addDimension(ekey.trim());
                }
            }
            int keyLen = keys.length;
            ENameSet nameSet = new ENameSet();
            boolean isMoreDim = keyLen > c.size();
            boolean isEque = keyLen == c.size();
            boolean isRelated = false;
            LinkedHashMap<String, Object> masterTableNameSet = new LinkedHashMap<String, Object>();
            LinkedHashMap<String, Integer> masterOrder = new LinkedHashMap<String, Integer>();
            LinkedHashMap<String, Object> masterDimNameSet = new LinkedHashMap<String, Object>();
            for (int i = 0; i < keyLen; ++i) {
                String key = keys[i];
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
                if (entityView == null) {
                    // empty if block
                }
                String name = this.dataAssist.getDimensionName(entityView);
                boolean periodView = this.periodEntityAdapter.isPeriodEntity(key);
                if (!isRelated && !periodView) {
                    isRelated = c.contains(name);
                }
                if (periodView) {
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityView.getEntityId());
                    masterDimNameSet.put(name, periodEntity);
                    masterTableNameSet.put(periodEntity.getCode(), periodEntity);
                } else {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityView.getEntityId());
                    masterDimNameSet.put(name, entityDefine);
                    masterTableNameSet.put(entityDefine.getCode(), entityDefine);
                }
                masterOrder.put(name, i);
                if (!isMoreDim && isRelated || name == null) continue;
                nameSet.add(name);
            }
            LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
            result.put(FS_ISRELATED, String.valueOf(isRelated || isCurDimEmpty));
            String periodType = String.valueOf(formScheme.getPeriodType());
            if (!isCurDimEmpty) {
                periodType = this.getPeriodType(PeriodType.valueOf((String)curCompareRelation.get(FS_PERIODTYPE).toString()), formScheme.getPeriodType());
            }
            result.put(FS_PERIODTYPE, periodType);
            if (isMoreDim || isEque) {
                boolean isTableKeyMore;
                LinkedHashMap<String, Object> bizFields = new LinkedHashMap<String, Object>();
                String bizFieldKeys = "";
                DataRegionDefine region = this.viewController.queryDataRegionDefine(regionKey);
                boolean isFixed = region.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
                List tableDefine = this.dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)this.viewController.getFieldKeysInRegion(regionKey));
                FieldDefine selectField = StringUtil.isNullOrEmpty((String)fieldKey) ? null : this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
                String[] bizKeys = ((TableDefine)tableDefine.get(0)).getBizKeyFieldsID();
                for (TableDefine table : tableDefine) {
                    if (selectField != null) {
                        if (!table.getKey().equals(selectField.getOwnerTableKey())) continue;
                        bizKeys = table.getBizKeyFieldsID();
                        break;
                    }
                    if (table.getKind().equals((Object)TableKind.TABLE_KIND_ENTITY)) continue;
                    bizKeys = table.getBizKeyFieldsID();
                    break;
                }
                boolean bl = isCurDimEmpty ? true : (isTableKeyMore = bizKeys.length > curCache.getBizFieldKeys().split(";").length);
                if (isTableKeyMore || isMoreDim) {
                    int index = keyLen;
                    for (String bizKey : bizKeys) {
                        LinkedHashMap<String, Object> bizField = new LinkedHashMap<String, Object>();
                        FieldDefine tablefield = this.dataDefinitionRuntimeController.queryFieldDefine(bizKey);
                        if (tablefield.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) continue;
                        FieldDefine field = tablefield;
                        bizFieldKeys = StringUtil.isNullOrEmpty((String)bizFieldKeys) ? field.getKey().toString() : bizFieldKeys + ";" + field.getKey().toString();
                        String key = field.getKey();
                        List deployInfoByDataFieldKeys = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{key});
                        DataFieldDeployInfoDO deployInfoByColumnKey = new DataFieldDeployInfoDO();
                        if (deployInfoByDataFieldKeys.size() > 0) {
                            deployInfoByColumnKey = (DataFieldDeployInfo)deployInfoByDataFieldKeys.get(0);
                        }
                        String tableName = deployInfoByColumnKey.getTableName();
                        String dimName = this.dataAssist.getDimensionName(field);
                        boolean isMaster = masterDimNameSet.containsKey(dimName) || masterTableNameSet.containsKey(tableName);
                        bizField.put("RegionKey", regionKey);
                        bizField.put("FieldKey", tablefield.getKey());
                        bizField.put("FieldCode", tablefield.getCode());
                        bizField.put("TableName", tableName);
                        bizField.put("DataSheet", tableName);
                        bizField.put("TableKey", field.getOwnerTableKey());
                        bizField.put("DimName", dimName);
                        String title = tablefield.getTitle();
                        if (isMaster) {
                            Object entity = masterDimNameSet.get(dimName);
                            if (entity != null) {
                                if (entity instanceof IPeriodEntity) {
                                    title = ((IPeriodEntity)entity).getTitle();
                                } else if (entity instanceof IEntityDefine) {
                                    title = ((IEntityDefine)entity).getTitle();
                                }
                            }
                            bizField.put("Order", masterOrder.get(dimName));
                        } else {
                            bizField.put("Order", FieldSelectHelper.indexOfArr(bizKeys, bizKey));
                        }
                        bizField.put("FieldTitle", title);
                        bizField.put("IsMaster", isMaster);
                        bizField.put("regionKind", DataRegionKind.DATA_REGION_SIMPLE);
                        bizField.put("isMergeSameCell", false);
                        bizFields.put(tablefield.getKey().toString(), bizField);
                    }
                }
                if ((isEque && isTableKeyMore || isMoreDim) && (isRelated || isCurDimEmpty)) {
                    curCache.setMasterDimensions(nameSet.toString());
                    curCache.setMasterKeys(masterKeys);
                    curCache.setBizFieldKeys(bizFieldKeys);
                    curCache.setSchemeKey(schemeKey);
                    curCache.setBizFields(bizFields);
                    curCache.setPeriodType(periodType);
                    List taskDefines = this.viewController.getAllTaskDefines();
                    curCache.setRelatedTasks(taskDefines);
                    result.put(regionKey, curCache);
                    isChanged = true;
                } else {
                    FieldSelectCache cache = new FieldSelectCache();
                    cache.setFieldCount(0);
                    result.put(regionKey, curCache);
                }
            } else {
                FieldSelectCache cache = new FieldSelectCache();
                cache.setFieldCount(0);
                result.put(regionKey, curCache);
            }
            result.put("changed", isChanged);
            if (!StringUtil.isNullOrEmpty((String)formScheme.getTaskKey())) {
                result.put("taskdefstartperiod", formScheme.getFromPeriod());
                result.put("taskdefendperiod", formScheme.getToPeriod());
            }
            return result;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public FieldDefine getReferentField(String fieldCode) throws Exception {
        FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(fieldCode);
        if (field == null) {
            return null;
        }
        return field;
    }

    public Map<String, Object> getDesignMasterViewFields(Map<String, Object> curCompareRelation, String schemeKey, String regionKey, boolean isCurDimEmpty) {
        try {
            boolean isChanged = false;
            String schemeID = schemeKey;
            String masterKeys = this.designTimeViewController.getFormSchemeEntity(schemeID);
            DesignFormSchemeDefine formScheme = this.designTimeViewController.queryFormSchemeDefine(schemeID);
            String dw = formScheme.getDw();
            if (masterKeys == null) {
                return null;
            }
            String[] keys = masterKeys.split(";");
            DimensionSet c = new DimensionSet();
            FieldSelectCache curCache = new FieldSelectCache();
            if (!isCurDimEmpty) {
                for (String key : curCompareRelation.keySet()) {
                    if (key.equals(FS_ISRELATED) || key.equals(FS_PERIODTYPE)) continue;
                    curCache = FieldSelectCache.parse((Map)curCompareRelation.get(key));
                    break;
                }
                for (String ekey : curCache.getMasterDimensions().split(",")) {
                    if (ekey.isEmpty()) continue;
                    c.addDimension(ekey.trim());
                }
            }
            int keyLen = keys.length;
            ENameSet nameSet = new ENameSet();
            boolean isMoreDim = keyLen > c.size();
            boolean isEque = keyLen == c.size();
            boolean isRelated = false;
            LinkedHashMap<String, TableModelDefine> masterTableNameSet = new LinkedHashMap<String, TableModelDefine>();
            LinkedHashMap<String, Integer> masterOrder = new LinkedHashMap<String, Integer>();
            for (int i = 0; i < keyLen; ++i) {
                String key = keys[i];
                EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(key);
                String name = this.dataAssist.getDimensionName(entityView);
                TableModelDefine tableModel = this.entityMetaService.getTableModel(dw);
                isRelated = c.contains(name);
                masterTableNameSet.put(tableModel.getName(), tableModel);
                masterOrder.put(tableModel.getName(), i);
                if (!isMoreDim && isRelated || name == null) continue;
                nameSet.add(name);
            }
            LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
            result.put(FS_ISRELATED, String.valueOf(isRelated || isCurDimEmpty));
            String periodType = String.valueOf(formScheme.getPeriodType());
            if (!isCurDimEmpty) {
                periodType = this.getPeriodType(PeriodType.valueOf((String)curCompareRelation.get(FS_PERIODTYPE).toString()), formScheme.getPeriodType());
            }
            result.put(FS_PERIODTYPE, periodType);
            if (isMoreDim || isEque) {
                boolean isTableKeyMore;
                LinkedHashMap<String, Object> bizFields = new LinkedHashMap<String, Object>();
                String bizFieldKeys = "";
                List fieldDefines = this.designTimeViewController.getAllFieldsByLinksInRegion(regionKey);
                ArrayList<String> fieldDefinesStr = new ArrayList<String>();
                for (DesignFieldDefine fieldDefine : fieldDefines) {
                    fieldDefinesStr.add(fieldDefine.getKey());
                }
                List tableDefine = this.dataDefinitionRuntimeController.queryTableDefinesByFields(fieldDefinesStr);
                String[] bizKeys = ((TableDefine)tableDefine.get(0)).getBizKeyFieldsID();
                boolean bl = isCurDimEmpty ? true : (isTableKeyMore = bizKeys.length > curCache.getBizFieldKeys().split(";").length);
                if (isTableKeyMore || isMoreDim) {
                    int index = keyLen;
                    for (String bizKey : bizKeys) {
                        LinkedHashMap<String, Object> bizField = new LinkedHashMap<String, Object>();
                        DesignFieldDefine field = this.designTimeController.queryFieldDefine(bizKey);
                        if (field.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) continue;
                        bizFieldKeys = StringUtil.isNullOrEmpty((String)bizFieldKeys) ? field.getKey().toString() : bizFieldKeys + ";" + field.getKey().toString();
                        String tableName = this.getTableName(bizKey);
                        boolean isMaster = masterTableNameSet.keySet().contains(tableName);
                        bizField.put("RegionKey", regionKey);
                        bizField.put("FieldKey", field.getKey());
                        bizField.put("FieldCode", field.getCode());
                        String title = field.getTitle();
                        if (isMaster) {
                            TableModelDefine table = (TableModelDefine)masterTableNameSet.get(tableName);
                            title = table.getTitle();
                            bizField.put("Order", masterOrder.get(tableName));
                        } else {
                            bizField.put("Order", index);
                            ++index;
                        }
                        bizField.put("FieldTitle", title);
                        bizField.put("IsMaster", isMaster);
                        bizFields.put(field.getKey().toString(), bizField);
                    }
                }
                if ((isEque && isTableKeyMore || isMoreDim) && (isRelated || isCurDimEmpty)) {
                    curCache.setMasterDimensions(nameSet.toString());
                    curCache.setMasterKeys(masterKeys);
                    curCache.setBizFieldKeys(bizFieldKeys);
                    curCache.setSchemeKey(schemeKey);
                    curCache.setBizFields(bizFields);
                    curCache.setPeriodType(periodType);
                    List taskDefines = this.designTimeViewController.getAllTaskDefines();
                    curCache.setRelatedDesignTasks(taskDefines);
                    result.put(regionKey, curCache);
                    isChanged = true;
                } else {
                    FieldSelectCache cache = new FieldSelectCache();
                    cache.setFieldCount(0);
                    result.put(regionKey, curCache);
                }
            } else {
                FieldSelectCache cache = new FieldSelectCache();
                cache.setFieldCount(0);
                result.put(regionKey, curCache);
            }
            result.put("changed", isChanged);
            return result;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    public FormSchemeDefine getFormSchemeByField(String fieldKey) {
        try {
            List schemes = this.viewController.queryFormSchemeByField(fieldKey);
            if (schemes != null && schemes.size() > 0) {
                return (FormSchemeDefine)schemes.get(0);
            }
        }
        catch (Exception ex) {
            LogHelper.error((String)LogModuleEnum.NRQUERY.getTitle(), (String)"\u901a\u8fc7\u6307\u6807\u83b7\u53d6\u65b9\u6848\u5f02\u5e38", (String)("\u5f02\u5e38\u4fe1\u606f\uff1a" + ex.getMessage()));
        }
        return null;
    }

    public TaskDefine getTaskDefineByField(String fieldKey) {
        FormSchemeDefine formScheme = this.getFormSchemeByField(fieldKey);
        TaskDefine task = null;
        if (formScheme != null) {
            task = this.viewController.queryTaskDefine(formScheme.getTaskKey());
        }
        return task;
    }

    public Map<String, Object> checkEnumField(List<String> fields) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            if (!CollectionUtils.isEmpty(fields)) {
                for (String field : fields) {
                    DataFieldDeployInfo deployInfoByColumnKey = this.iRuntimeDataSchemeService.getDeployInfoByColumnKey(field);
                    String tableModelKey = deployInfoByColumnKey.getTableModelKey();
                    String tableName = this.getTableName(field);
                    Boolean enumField = this.isEnumField(tableModelKey);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>(2);
                    hashMap.put("isEnumField", enumField);
                    hashMap.put("tableName", tableName);
                    hashMap.put("fieldTableName", deployInfoByColumnKey.getTableName());
                    map.put(field, hashMap);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return map;
    }

    private boolean isEnumField(String tableModelKey) {
        try {
            TableDefine tableDefine = this.dataDefinitionRuntimeController.queryTableDefine(tableModelKey);
            if (tableDefine.getKind() == TableKind.TABLE_KIND_DICTIONARY) {
                return true;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public static int indexOfArr(String[] arr, String value2) {
        if (arr.length < 0) {
            return 0;
        }
        for (int i = 0; i < arr.length; ++i) {
            if (!arr[i].equals(value2)) continue;
            return i;
        }
        return 0;
    }
}

