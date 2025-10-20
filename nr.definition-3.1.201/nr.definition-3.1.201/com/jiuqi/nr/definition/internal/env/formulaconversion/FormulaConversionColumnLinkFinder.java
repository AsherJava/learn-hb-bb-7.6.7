/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env.formulaconversion;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.DesignReportColumnLinkFinder;
import com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FormulaConversionColumnLinkFinder
implements IDataModelLinkFinder {
    private static final String GRID = "_grid";
    private final FormulaConversionContext context;
    private IDesignTimeViewController controller;
    private DataModelService dataModelService;
    private IDesignDataSchemeService designDataSchemeService;
    private String formSchemeKey;
    private Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private Map<String, DataRegionDefine> regionMp = new HashMap<String, DataRegionDefine>();
    private Map<String, FormDefine> formMap = new HashMap<String, FormDefine>();
    private Set<String> emptyReportName = new HashSet<String>();
    private Map<String, Map<String, DataModelLinkColumn>> rep_gridPosMap = new HashMap<String, Map<String, DataModelLinkColumn>>();
    private Map<String, Map<String, DataModelLinkColumn>> rep_dataPosMap = new HashMap<String, Map<String, DataModelLinkColumn>>();
    private Map<String, Map<String, DataModelLinkColumn>> rep_linkCodeMap = new HashMap<String, Map<String, DataModelLinkColumn>>();
    private Map<String, Map<String, DataModelLinkColumn>> rep_fieldCodeMap = new HashMap<String, Map<String, DataModelLinkColumn>>();
    private Map<String, ColumnModelDefine> fieldMap = new HashMap<String, ColumnModelDefine>();
    private Map<String, ColumnModelDefine> fmdmFieldMap;
    private Logger logger = LogFactory.getLogger(DesignReportColumnLinkFinder.class);
    private boolean useCache = false;
    private IEntityMetaService entityMetaService;

    public void setUseCache() {
        this.useCache = true;
    }

    public FormulaConversionColumnLinkFinder(IDesignTimeViewController controller, IDataDefinitionDesignTimeController npDesignTimeController, String formSchemeKey, FormulaConversionContext context) {
        this(controller, formSchemeKey, context);
    }

    public FormulaConversionColumnLinkFinder(IDesignTimeViewController controller, String formSchemeKey, FormulaConversionContext context) {
        this.controller = controller;
        this.formSchemeKey = formSchemeKey;
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.designDataSchemeService = BeanUtil.getBean(IDesignDataSchemeService.class);
        this.context = context;
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            if (this.useCache) {
                Map<String, DataModelLinkColumn> linkMap = null;
                linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                }
                StringBuffer key = new StringBuffer().append(rowIndex).append("_").append(colIndex);
                if (isGridPosition) {
                    key.append(GRID);
                }
                DataModelLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(key.toString()) : null;
                DataModelLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataModelLinkColumn)dataLinkColumn.clone();
                }
                return linkColumn;
            }
            DesignDataLinkDefine dataLink = isGridPosition ? this.controller.getDataLinkByFormAndPos(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.getDataLinkByFormAndColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.info("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            DesignFormDefine formDefine = this.controller.getForm(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return this.getFmdmDatalink(reportInfo, dataLink);
            }
            DesignDataField dataField = this.designDataSchemeService.getDataField(dataLink.getLinkExpression());
            if (dataField == null) {
                this.logger.info("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
            return this.getDataLinkColumn(reportInfo, dataLink, columnModel);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        try {
            if (this.useCache) {
                Map<String, DataModelLinkColumn> linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
                }
                DataModelLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(dataLinkCode) : null;
                DataModelLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataModelLinkColumn)dataLinkColumn.clone();
                }
                return linkColumn;
            }
            DesignDataLinkDefine dataLink = this.controller.getDataLinkByUniqueCode(reportInfo.getReportKey(), dataLinkCode);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.info("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            DesignFormDefine formDefine = this.controller.getForm(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return this.getFmdmDatalink(reportInfo, dataLink);
            }
            DesignDataField dataField = this.designDataSchemeService.getDataField(dataLink.getLinkExpression());
            if (dataField == null) {
                this.logger.info("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
            return this.getDataLinkColumn(reportInfo, dataLink, columnModel);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        DataModelLinkColumn column = null;
        try {
            List<DesignDataLinkDefine> links;
            DesignDataField dataField;
            if (reportInfo == null) {
                return this.getFmdmDatalink(fieldName);
            }
            if (this.useCache) {
                DesignFormDefine formDefine;
                Map<String, DataModelLinkColumn> linkMap = this.rep_fieldCodeMap.get(reportInfo.getReportKey());
                if (linkMap == null && (formDefine = this.controller.getForm(reportInfo.getReportKey())).getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                    List<DesignDataLinkDefine> links2 = this.controller.listDataLinkByFormAndFieldKey(reportInfo.getReportKey(), fieldName);
                    DataModelLinkColumn dataLinkColumn = !CollectionUtils.isEmpty(links2) ? this.getFmdmDatalink(reportInfo, links2.get(0)) : this.getFmdmDatalink(fieldName);
                    return dataLinkColumn;
                }
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = this.rep_fieldCodeMap.get(reportInfo.getReportKey());
                }
                DataModelLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(fieldName) : null;
                DataModelLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataModelLinkColumn)dataLinkColumn.clone();
                }
                return linkColumn;
            }
            DesignFormDefine formDefine = this.controller.getForm(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                List<DesignDataLinkDefine> links3 = this.controller.listDataLinkByFormAndFieldKey(reportInfo.getReportKey(), fieldName);
                DataModelLinkColumn dataLinkColumn = !CollectionUtils.isEmpty(links3) ? this.getFmdmDatalink(reportInfo, links3.get(0)) : this.getFmdmDatalink(fieldName);
                return dataLinkColumn;
            }
            List<DesignDataLinkDefine> linkDefines = this.controller.listDataLinkByForm(reportInfo.getReportKey());
            List fieldKeys = linkDefines.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
            List dataFields = this.designDataSchemeService.getDataFields(fieldKeys);
            Optional<DesignDataField> dataFieldOpt = dataFields.stream().filter(d -> d.getCode().equalsIgnoreCase(fieldName)).findFirst();
            if (dataFieldOpt.isPresent()) {
                dataField = dataFieldOpt.get();
            } else {
                DesignFormSchemeDefine formSchemeDefine = this.controller.getFormScheme(formDefine.getFormScheme());
                DesignTaskDefine taskDefine = this.controller.getTask(formSchemeDefine.getTaskKey());
                dataField = this.designDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(taskDefine.getDataScheme(), fieldName);
            }
            if (dataField != null && (links = this.controller.listDataLinkByFormAndFieldKey(reportInfo.getReportKey(), dataField.getKey())) != null && links.size() > 0) {
                ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
                column = this.getDataLinkColumn(reportInfo, links.get(0), columnModel);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return column;
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            String periodModiferStr = "";
            String periodDim = "";
            String schemeKey = null;
            DesignTaskLinkDefine linkDefine = this.controller.getTaskLinkByFormSchemeAndAlias(this.formSchemeKey, linkAlias);
            if (linkDefine != null) {
                schemeKey = linkDefine.getRelatedFormSchemeKey();
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModiferStr = linkDefine.getPeriodOffset();
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
            } else {
                DesignFormSchemeDefine schemeDefine = this.controller.getFormSchemeByCode(linkAlias);
                if (schemeDefine != null) {
                    schemeKey = schemeDefine.getKey();
                }
            }
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            return reportInfo;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
            return null;
        }
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        ArrayList<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
        List<DesignTaskLinkDefine> links = this.controller.listTaskLinkByFormScheme(this.formSchemeKey);
        for (TaskLinkDefine taskLinkDefine : links) {
            String schemeKey = taskLinkDefine.getRelatedFormSchemeKey();
            String periodModiferStr = "";
            String periodDim = "";
            switch (taskLinkDefine.getConfiguration()) {
                case PERIOD_TYPE_OFFSET: {
                    periodModiferStr = taskLinkDefine.getPeriodOffset();
                    break;
                }
                case PERIOD_TYPE_SPECIFIED: {
                    periodDim = taskLinkDefine.getSpecified();
                    break;
                }
            }
            ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
            if (reportInfo == null) continue;
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
            reportInfos.add(reportInfo);
        }
        return reportInfos;
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        return null;
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        return null;
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        return null;
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        return null;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return false;
    }

    private DataModelLinkColumn getFmdmDatalink(ReportInfo reportInfo, DataLinkDefine link) {
        TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), link.getLinkExpression());
        if (columnModelDefine != null) {
            return this.getDataLinkColumn(reportInfo, link, columnModelDefine);
        }
        return null;
    }

    private DataModelLinkColumn getFmdmDatalink(String fieldCode) throws JQException {
        ColumnModelDefine column;
        if (this.fmdmFieldMap == null) {
            this.initFmdmFieldMap();
        }
        return (column = this.fmdmFieldMap.get(fieldCode)) == null ? null : new DataModelLinkColumn(column);
    }

    private void initFmdmFieldMap() throws JQException {
        ConversionFormInfo conversionFormInfo;
        List<DesignFormDefine> formDefines = this.controller.listFormByFormSchemeAndType(this.formSchemeKey, FormType.FORM_TYPE_NEWFMDM);
        DesignFormSchemeDefine formSchemeDefine = this.controller.getFormScheme(this.formSchemeKey);
        DesignTaskDefine taskDefine = this.controller.getTask(formSchemeDefine.getTaskKey());
        if (formDefines.size() > 0 && (conversionFormInfo = this.context.getConversionFormInfoMap().get(formDefines.get(0).getKey())) != null) {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
            List fmdmColumns = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            Map<String, ColumnModelDefine> fmdmColumnMap = fmdmColumns.stream().collect(Collectors.toMap(IModelDefineItem::getID, ColumnModelDefine2 -> ColumnModelDefine2));
            List dataFields = this.designDataSchemeService.getAllDataField(taskDefine.getDataScheme());
            if (dataFields != null && dataFields.size() > 0) {
                dataFields.stream().forEach(d -> {
                    ColumnModelDefine cfr_ignored_0 = fmdmColumnMap.put(d.getKey(), new ReportColumnModelImpl((DataField)d));
                });
            }
            Map<String, ConversionFieldInfo> fieldInfoMap = conversionFormInfo.getFieldInfoMap();
            this.fmdmFieldMap = new HashMap<String, ColumnModelDefine>();
            for (ConversionFieldInfo value : fieldInfoMap.values()) {
                this.fmdmFieldMap.put(value.getOldCode(), fmdmColumnMap.get(value.getNewKey()));
            }
            return;
        }
        this.fmdmFieldMap = new HashMap<String, ColumnModelDefine>();
    }

    private String getDwEntityId() {
        DesignFormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        String dwEntityId = formScheme.getDw();
        if (StringUtils.isEmpty((String)dwEntityId)) {
            DesignTaskDefine taskDefine = this.controller.getTask(formScheme.getTaskKey());
            dwEntityId = taskDefine.getDw();
        }
        return dwEntityId;
    }

    private void initLinkMap(ReportInfo reportInfo) {
        HashMap<String, DataModelLinkColumn> gridPosMap = new HashMap<String, DataModelLinkColumn>();
        HashMap<String, DataModelLinkColumn> dataPosMap = new HashMap<String, DataModelLinkColumn>();
        HashMap<String, DataModelLinkColumn> linkCodeMap = new HashMap<String, DataModelLinkColumn>();
        HashMap<String, DataModelLinkColumn> fieldCodeMap = new HashMap<String, DataModelLinkColumn>();
        List<DesignDataLinkDefine> links = this.controller.listDataLinkByForm(reportInfo.getReportKey());
        Map<Object, Object> newCodeFieldInfoMap = new HashMap();
        DesignFormDefine formDefine = this.controller.getForm(reportInfo.getReportKey());
        ConversionFormInfo conversionFormInfo = this.context.getConversionFormInfoMap().get(reportInfo.getReportKey());
        if (conversionFormInfo != null) {
            Map<String, ConversionFieldInfo> fieldInfoMap = conversionFormInfo.getFieldInfoMap();
            newCodeFieldInfoMap = fieldInfoMap.values().stream().collect(Collectors.toMap(ConversionFieldInfo::getNewCode, ConversionFieldInfo2 -> ConversionFieldInfo2));
        }
        for (DesignDataLinkDefine link : links) {
            String linkExpression;
            String fieldKey = linkExpression = link.getLinkExpression();
            try {
                DesignDataTable dataTable;
                DesignDataField dataField;
                ColumnModelDefine columnModelDefine = this.fieldMap.get(fieldKey);
                if (columnModelDefine == null && (dataField = this.designDataSchemeService.getDataField(linkExpression)) != null && (dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey())).getDataTableType() == DataTableType.TABLE) {
                    DesignFormSchemeDefine formSchemeDefine = this.controller.getFormScheme(formDefine.getFormScheme());
                    DesignTaskDefine taskDefine = this.controller.getTask(formSchemeDefine.getTaskKey());
                    List allDataField = this.designDataSchemeService.getAllDataField(taskDefine.getDataScheme());
                    allDataField.stream().forEach(field -> this.fieldMap.put(field.getKey(), new ReportColumnModelImpl((DataField)field)));
                    columnModelDefine = this.fieldMap.get(linkExpression);
                }
                if (columnModelDefine == null) continue;
                DataModelLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, link, columnModelDefine);
                Position gridPosition = linkColumn.getGridPosition();
                StringBuffer gridPosKey = new StringBuffer().append(gridPosition.row()).append("_").append(gridPosition.col()).append(GRID);
                gridPosMap.put(gridPosKey.toString(), linkColumn);
                Position dataPosition = linkColumn.getDataPosition();
                StringBuffer dataPosKey = new StringBuffer().append(dataPosition.row()).append("_").append(dataPosition.col());
                dataPosMap.put(dataPosKey.toString(), linkColumn);
                String linkCode = linkColumn.getDataLinkCode();
                linkCodeMap.put(linkCode, linkColumn);
                String fieldCode = columnModelDefine.getCode();
                String oldCode = newCodeFieldInfoMap.get(fieldCode) == null ? fieldCode : ((ConversionFieldInfo)newCodeFieldInfoMap.get(fieldCode)).getOldCode();
                fieldCodeMap.put(oldCode, linkColumn);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }
        this.rep_gridPosMap.put(reportInfo.getReportKey(), gridPosMap);
        this.rep_dataPosMap.put(reportInfo.getReportKey(), dataPosMap);
        this.rep_linkCodeMap.put(reportInfo.getReportKey(), linkCodeMap);
        this.rep_fieldCodeMap.put(reportInfo.getReportKey(), fieldCodeMap);
    }

    private DataModelLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, ColumnModelDefine columnModelDefine) {
        DataModelLinkColumn column = new DataModelLinkColumn(columnModelDefine);
        if (dataLink != null) {
            column.setDataLinkCode(dataLink.getUniqueCode());
            column.setGridPosition(new Position(dataLink.getPosX(), dataLink.getPosY()));
            column.setDataPosition(new Position(dataLink.getColNum(), dataLink.getRowNum()));
            DataRegionDefine region = this.regionMp.get(dataLink.getRegionKey());
            if (region == null) {
                region = this.controller.getDataRegion(dataLink.getRegionKey());
                this.regionMp.put(dataLink.getRegionKey(), region);
            }
            if (region != null) {
                column.setRegion(region.getKey());
            }
        }
        column.setReportInfo(reportInfo);
        if (reportInfo.getPeriodModifier() != null) {
            column.setPeriodModifier(reportInfo.getPeriodModifier());
        }
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.setValue("DATATIME", (Object)reportInfo.getPeriodDim());
            column.setDimensionRestriction(dimension);
        }
        return column;
    }

    private ReportInfo findReportInfoByFormScheme(String reportName, String formSchemeKey) {
        try {
            String key = formSchemeKey + "_" + reportName;
            if (this.emptyReportName.contains(key)) {
                return null;
            }
            FormDefine form = this.formMap.get(key);
            if (form == null) {
                form = this.controller.getFormByFormSchemeAndCode(formSchemeKey, reportName);
            }
            if (form == null) {
                this.emptyReportName.add(key);
                return null;
            }
            this.formMap.put(key, form);
            return this.getReportInfo(form);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    private ReportInfo getReportInfo(FormDefine form) {
        if (this.reportInfoMp.containsKey(form.getKey())) {
            return this.reportInfoMp.get(form.getKey());
        }
        TreeSet<Integer> rowSet = new TreeSet<Integer>();
        TreeSet<Integer> colSet = new TreeSet<Integer>();
        try {
            List<DesignDataLinkDefine> allLinks = this.controller.listDataLinkByForm(form.getKey());
            for (DataLinkDefine dataLinkDefine : allLinks) {
                rowSet.add(dataLinkDefine.getRowNum());
                colSet.add(dataLinkDefine.getColNum());
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        int[] rowArr = rowSet.stream().mapToInt(Integer::intValue).toArray();
        int[] colArr = colSet.stream().mapToInt(Integer::intValue).toArray();
        ReportInfo reportInfo = new ReportInfo(form.getKey(), form.getFormCode(), form.getTitle(), colArr, rowArr);
        this.reportInfoMp.put(form.getKey(), reportInfo);
        return reportInfo;
    }
}

