/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
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
import com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
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

public class DesignReportColumnLinkFinder
implements IDataModelLinkFinder {
    private static final String GRID = "_grid";
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
    private Map<String, String> formscheme_dataSchemeMap = new HashMap<String, String>();
    private Logger logger = LogFactory.getLogger(DesignReportColumnLinkFinder.class);
    private boolean useCache = false;
    private IEntityMetaService entityMetaService;

    public void setUseCache() {
        this.useCache = true;
    }

    public DesignReportColumnLinkFinder(IDesignTimeViewController controller, IDataDefinitionDesignTimeController npDesignTimeController, String formSchemeKey) {
        this(controller, formSchemeKey);
    }

    public DesignReportColumnLinkFinder(IDesignTimeViewController controller, String formSchemeKey) {
        this.controller = controller;
        this.formSchemeKey = formSchemeKey;
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.designDataSchemeService = BeanUtil.getBean(IDesignDataSchemeService.class);
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            if (this.useCache) {
                Map<String, DataModelLinkColumn> linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                }
                StringBuilder key = new StringBuilder().append(rowIndex).append("_").append(colIndex);
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
            DesignDataLinkDefine dataLink = isGridPosition ? this.controller.queryDataLinkDefine(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.queryDataLinkDefineByColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            return this.getDataModelLinkColumn(reportInfo, dataLink);
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
                if (linkMap == null) {
                    return null;
                }
                DataModelLinkColumn dataLinkColumn = linkMap.get(dataLinkCode);
                DataModelLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataModelLinkColumn)dataLinkColumn.clone();
                }
                return linkColumn;
            }
            DesignDataLinkDefine dataLink = this.controller.queryDataLinkDefineByUniquecode(reportInfo.getReportKey(), dataLinkCode);
            return this.getDataModelLinkColumn(reportInfo, dataLink);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    private DataModelLinkColumn getDataModelLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink) {
        if (dataLink == null || dataLink.getLinkExpression() == null || dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) {
            return null;
        }
        if (dataLink.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
            return this.getEntityFieldLink(reportInfo, dataLink);
        }
        DesignDataField dataField = this.designDataSchemeService.getDataField(dataLink.getLinkExpression());
        if (dataField == null) {
            this.logger.info("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
            return null;
        }
        ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
        return this.getDataLinkColumn(reportInfo, dataLink, columnModel);
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        try {
            Optional<DesignDataLinkDefine> linkDefine;
            if (reportInfo == null) {
                DataModelLinkColumn entityFieldLink = this.getEntityFieldLink(fieldName);
                if (entityFieldLink != null) {
                    return entityFieldLink;
                }
                String dataSchemeKey = this.getDataSchemeKey(this.formSchemeKey);
                DesignDataField dataField = this.designDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataSchemeKey, fieldName);
                if (dataField == null) {
                    return null;
                }
                ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
                return this.getDataLinkColumn(reportInfo, null, columnModel);
            }
            if (this.useCache) {
                Map<String, DataModelLinkColumn> linkMap = this.rep_fieldCodeMap.get(reportInfo.getReportKey());
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
            FormDefine formDefine = this.getFormDefine(reportInfo);
            List<DesignDataLinkDefine> allLinks = this.controller.getAllLinksInForm(reportInfo.getReportKey());
            List links = allLinks.stream().filter(l -> l.getType() != DataLinkType.DATA_LINK_TYPE_FORMULA).collect(Collectors.toList());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM && (linkDefine = links.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FMDM).filter(l -> l.getLinkExpression().equalsIgnoreCase(fieldName)).findFirst()).isPresent()) {
                return this.getEntityFieldLink(reportInfo, linkDefine.get());
            }
            Map<String, DesignDataLinkDefine> linkDefineMap = links.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD).collect(Collectors.toMap(DataLinkDefine::getLinkExpression, l -> l, (v1, v2) -> v1));
            List dataFields = this.designDataSchemeService.getDataFields(new ArrayList<String>(linkDefineMap.keySet()));
            Optional<DesignDataField> dataFieldOpt = dataFields.stream().filter(d -> d.getCode().equalsIgnoreCase(fieldName)).findFirst();
            if (dataFieldOpt.isPresent()) {
                DesignDataField dataField = dataFieldOpt.get();
                ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
                return this.getDataLinkColumn(reportInfo, linkDefineMap.get(dataField.getKey()), columnModel);
            }
            String dataSchemeKey = this.getDataSchemeKey(formDefine.getFormScheme());
            DesignDataField dataField = this.designDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataSchemeKey, fieldName);
            if (dataField != null) {
                ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
                return this.getDataLinkColumn(reportInfo, null, columnModel);
            }
            return this.getEntityFieldLink(formDefine.getFormScheme(), fieldName);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public DataModelLinkColumn findFMDMColumnByLinkAlias(ExecutorContext context, String fieldName, String linkAlias) {
        DesignTaskLinkDefine taskLinkDefine = this.controller.queryLinkByCurrentFormSchemeAndNum(this.formSchemeKey, linkAlias);
        DataModelLinkColumn entityFieldLink = this.getEntityFieldLink(taskLinkDefine.getRelatedFormSchemeKey(), fieldName);
        PeriodShiftInfo periodShiftInfo = this.getPeriodShiftInfo(linkAlias);
        if (entityFieldLink != null) {
            if (periodShiftInfo != null) {
                if (periodShiftInfo.periodDim != null) {
                    DimensionValueSet dimension = new DimensionValueSet();
                    dimension.setValue("DATATIME", (Object)periodShiftInfo.periodDim);
                    entityFieldLink.setDimensionRestriction(dimension);
                }
                if (periodShiftInfo.modifier != null) {
                    entityFieldLink.setPeriodModifier(periodShiftInfo.modifier);
                }
            }
            return entityFieldLink;
        }
        DesignTaskDefine taskDefine = StringUtils.isEmpty((String)taskLinkDefine.getRelatedTaskKey()) ? this.controller.queryTaskDefine(this.controller.queryFormSchemeDefine(taskLinkDefine.getRelatedFormSchemeKey()).getTaskKey()) : this.controller.queryTaskDefine(taskLinkDefine.getRelatedTaskKey());
        DesignDataField dataField = this.designDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(taskDefine.getDataScheme(), fieldName);
        if (dataField == null) {
            return null;
        }
        DesignFormDefine fmdmFormDefine = null;
        try {
            List<DesignFormDefine> designFormDefines = this.controller.queryFormsByTypeInScheme(taskLinkDefine.getRelatedFormSchemeKey(), FormType.FORM_TYPE_NEWFMDM);
            if (designFormDefines.size() > 0) {
                fmdmFormDefine = designFormDefines.get(0);
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u5173\u8054\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u5c5e\u6027\u5931\u8d25", (Throwable)e);
        }
        if (fmdmFormDefine == null) {
            return null;
        }
        List<DesignDataLinkDefine> allLinks = this.controller.getAllLinksInForm(fmdmFormDefine.getKey());
        Optional<DesignDataLinkDefine> linkDefine = allLinks.stream().filter(l -> l.getType() == DataLinkType.DATA_LINK_TYPE_FIELD || l.getType() == DataLinkType.DATA_LINK_TYPE_INFO).filter(l -> l.getLinkExpression().equalsIgnoreCase(dataField.getKey())).findFirst();
        if (linkDefine.isPresent()) {
            ReportInfo reportInfo = this.findReportInfo(linkAlias, fmdmFormDefine.getFormCode());
            ReportColumnModelImpl columnModel = new ReportColumnModelImpl((DataField)dataField);
            return this.getDataLinkColumn(reportInfo, linkDefine.get(), columnModel);
        }
        return null;
    }

    private String getDataSchemeKey(String formSchemeKey) {
        String dataScheme = this.formscheme_dataSchemeMap.get(formSchemeKey);
        if (StringUtils.isNotEmpty((String)dataScheme)) {
            return dataScheme;
        }
        DesignFormSchemeDefine designFormSchemeDefine = this.controller.queryFormSchemeDefine(formSchemeKey);
        DesignTaskDefine taskDefine = this.controller.queryTaskDefine(designFormSchemeDefine.getTaskKey());
        this.fillFormscheme_dataSchemeMap(formSchemeKey, taskDefine.getDataScheme());
        return taskDefine.getDataScheme();
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            ReportInfo reportInfo;
            String periodModiferStr = "";
            String periodDim = "";
            String schemeKey = null;
            DesignTaskLinkDefine linkDefine = this.controller.queryLinkByCurrentFormSchemeAndNum(this.formSchemeKey, linkAlias);
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
                DesignFormSchemeDefine schemeDefine = this.controller.getFormschemeByCode(linkAlias);
                if (schemeDefine != null) {
                    schemeKey = schemeDefine.getKey();
                }
            }
            if ((reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey)) == null) {
                return null;
            }
            reportInfo.setPeriodModifierStr(periodModiferStr);
            reportInfo.setPeriodDim(periodDim);
            return reportInfo;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u62a5\u8868\u5931\u8d25:" + e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        ArrayList<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
        List<DesignTaskLinkDefine> links = this.controller.queryLinksByCurrentFormScheme(this.formSchemeKey);
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

    private DataModelLinkColumn getEntityFieldLink(ReportInfo reportInfo, DataLinkDefine link) {
        FormDefine formDefine = this.getFormDefine(reportInfo);
        TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId(formDefine.getFormScheme()));
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), link.getLinkExpression());
        if (columnModelDefine != null) {
            return this.getDataLinkColumn(reportInfo, link, columnModelDefine);
        }
        return null;
    }

    private FormDefine getFormDefine(ReportInfo reportInfo) {
        FormDefine formDefine = this.formMap.get(reportInfo.getReportKey());
        if (formDefine == null) {
            formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
            this.formMap.put(reportInfo.getReportKey(), formDefine);
        }
        return formDefine;
    }

    private DataModelLinkColumn getEntityFieldLink(String fieldCode) {
        return this.getEntityFieldLink(this.formSchemeKey, fieldCode);
    }

    private DataModelLinkColumn getEntityFieldLink(String schemeKey, String fieldCode) {
        TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId(schemeKey));
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldCode);
        if (columnModelDefine != null) {
            return new DataModelLinkColumn(columnModelDefine);
        }
        return null;
    }

    private String getDwEntityId() {
        return this.getDwEntityId(this.formSchemeKey);
    }

    private String getDwEntityId(String schemKey) {
        DesignFormSchemeDefine formScheme = this.controller.queryFormSchemeDefine(schemKey);
        String dwEntityId = formScheme.getDw();
        if (StringUtils.isEmpty((String)dwEntityId)) {
            DesignTaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
            this.fillFormscheme_dataSchemeMap(schemKey, taskDefine.getDataScheme());
            dwEntityId = taskDefine.getDw();
        }
        return dwEntityId;
    }

    private void fillFormscheme_dataSchemeMap(String formSchemeKey, String dataSchemeKey) {
        this.formscheme_dataSchemeMap.put(formSchemeKey, dataSchemeKey);
    }

    private void initLinkMap(ReportInfo reportInfo) {
        HashMap gridPosMap = new HashMap();
        HashMap dataPosMap = new HashMap();
        HashMap linkCodeMap = new HashMap();
        HashMap fieldCodeMap = new HashMap();
        HashMap fieldMap = new HashMap();
        List<DesignDataLinkDefine> links = this.controller.getAllLinksInForm(reportInfo.getReportKey()).stream().filter(l -> l.getType() != DataLinkType.DATA_LINK_TYPE_FORMULA).collect(Collectors.toList());
        FormDefine formDefine = this.formMap.get(reportInfo.getReportKey());
        if (formDefine == null) {
            formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
            this.formMap.put(reportInfo.getReportKey(), formDefine);
        }
        String schemKey = formDefine.getFormScheme();
        links.forEach(link -> {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)fieldMap.get(link.getLinkExpression());
            if (columnModelDefine == null) {
                if (link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                    TableModelDefine dwTableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
                    List dwColumns = this.dataModelService.getColumnModelDefinesByTable(dwTableModel.getID());
                    dwColumns.forEach(c -> fieldMap.put(c.getCode(), c));
                    columnModelDefine = (ColumnModelDefine)fieldMap.get(link.getLinkExpression());
                } else if (link.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                    String linkExpression = link.getLinkExpression();
                    try {
                        DesignDataField dataField = this.designDataSchemeService.getDataField(linkExpression);
                        DesignDataTable dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
                        if (dataTable.getDataTableType() == DataTableType.TABLE) {
                            String dataSchemeKey = this.getDataSchemeKey(schemKey);
                            List allDataField = this.designDataSchemeService.getAllDataField(dataSchemeKey);
                            allDataField.forEach(field -> {
                                ColumnModelDefine cfr_ignored_0 = fieldMap.put(field.getKey(), new ReportColumnModelImpl((DataField)field));
                            });
                            columnModelDefine = (ColumnModelDefine)fieldMap.get(linkExpression);
                        }
                    }
                    catch (Exception e) {
                        this.logger.error(e.getMessage(), (Throwable)e);
                    }
                }
            }
            if (columnModelDefine != null) {
                String fieldCode = columnModelDefine.getCode();
                DataModelLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, (DataLinkDefine)link, columnModelDefine);
                Position gridPosition = linkColumn.getGridPosition();
                String gridPosKey = gridPosition.row() + "_" + gridPosition.col() + GRID;
                gridPosMap.put(gridPosKey, linkColumn);
                Position dataPosition = linkColumn.getDataPosition();
                String dataPosKey = dataPosition.row() + "_" + dataPosition.col();
                dataPosMap.put(dataPosKey, linkColumn);
                String linkCode = linkColumn.getDataLinkCode();
                linkCodeMap.put(linkCode, linkColumn);
                fieldCodeMap.put(fieldCode, linkColumn);
            }
        });
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
                region = this.controller.queryDataRegionDefine(dataLink.getRegionKey());
                this.regionMp.put(dataLink.getRegionKey(), region);
            }
            if (region != null) {
                column.setRegion(region.getKey());
            }
        }
        column.setReportInfo(reportInfo);
        if (reportInfo != null) {
            if (reportInfo.getPeriodModifier() != null) {
                column.setPeriodModifier(reportInfo.getPeriodModifier());
            }
            if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.setValue("DATATIME", (Object)reportInfo.getPeriodDim());
                column.setDimensionRestriction(dimension);
            }
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
                form = this.controller.querySoftFormDefineByCodeInFormScheme(formSchemeKey, reportName);
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
            List<DesignDataLinkDefine> allLinks = this.controller.getAllLinksInForm(form.getKey());
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

    private PeriodShiftInfo getPeriodShiftInfo(String linkAlias) {
        PeriodShiftInfo info = new PeriodShiftInfo();
        try {
            String periodDim = "";
            DesignTaskLinkDefine linkDefine = this.controller.queryLinkByCurrentFormSchemeAndNum(this.formSchemeKey, linkAlias);
            DesignFormSchemeDefine formScheme = this.controller.queryFormSchemeDefine(this.formSchemeKey);
            PeriodModifier periodModifier = null;
            PeriodType periodType = formScheme.getPeriodType();
            if (linkDefine != null) {
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModifier = PeriodModifier.parse((String)linkDefine.getPeriodOffset());
                        break;
                    }
                    case PERIOD_TYPE_NEXT: {
                        periodModifier = PeriodModifier.parse((String)("+1" + (char)PeriodConsts.typeToCode((int)periodType.type())));
                        break;
                    }
                    case PERIOD_TYPE_PREVIOUS: {
                        periodModifier = PeriodModifier.parse((String)("-1" + (char)PeriodConsts.typeToCode((int)periodType.type())));
                        break;
                    }
                    case PERIOD_TYPE_PREYEAR: {
                        periodModifier = PeriodModifier.parse((String)"-1N");
                        periodModifier.union(PeriodModifier.parse((String)"N"));
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
            }
            info.modifier = periodModifier;
            info.periodDim = periodDim;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
        }
        return info;
    }

    class PeriodShiftInfo {
        PeriodModifier modifier;
        String periodDim;

        PeriodShiftInfo() {
        }
    }
}

