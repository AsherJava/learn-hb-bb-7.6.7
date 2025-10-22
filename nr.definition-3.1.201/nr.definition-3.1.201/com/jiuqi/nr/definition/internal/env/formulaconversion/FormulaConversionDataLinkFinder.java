/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env.formulaconversion;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
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
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFieldInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo;
import com.jiuqi.nr.definition.internal.env.formulaconversion.FormulaConversionContext;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FormulaConversionDataLinkFinder
implements IDataLinkFinder {
    private static final String GRID = "_grid";
    private IDesignTimeViewController controller;
    private IDataDefinitionDesignTimeController npDesignTimeController;
    private String formSchemeKey;
    private Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private Map<String, DataRegionDefine> regionMp = new HashMap<String, DataRegionDefine>();
    private Map<String, FormDefine> formMap = new HashMap<String, FormDefine>();
    private Set<String> emptyReportName = new HashSet<String>();
    private Map<String, Map<String, DataLinkColumn>> rep_gridPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_dataPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_linkCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_fieldCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Logger logger = LogFactory.getLogger(FormulaConversionDataLinkFinder.class);
    private boolean useCache = false;
    private FormulaConversionContext context;
    private IEntityMetaService entityMetaService;
    private DataModelService dataModelService;
    private IDesignDataSchemeService designDataSchemeService;

    public FormulaConversionDataLinkFinder(IDesignTimeViewController controller, IDataDefinitionDesignTimeController npDesignTimeController, String formSchemeKey, FormulaConversionContext context) {
        this.controller = controller;
        this.npDesignTimeController = npDesignTimeController;
        this.formSchemeKey = formSchemeKey;
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.designDataSchemeService = BeanUtil.getBean(IDesignDataSchemeService.class);
        this.context = context;
    }

    public void setUseCache() {
        this.useCache = true;
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        try {
            if (this.useCache) {
                Map<String, DataLinkColumn> linkMap = null;
                linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
                }
                StringBuffer key = new StringBuffer().append(rowIndex).append("_").append(colIndex);
                if (isGridPosition) {
                    key.append(GRID);
                }
                DataLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(key.toString()) : null;
                DataLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataLinkColumn)dataLinkColumn.clone();
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
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(dataLink.getLinkExpression());
            if (fieldDefine == null) {
                this.logger.info("\u6839\u636e\u5355\u5143\u683c\u5173\u8054\u6307\u6807key:" + dataLink.getLinkExpression() + "\u6ca1\u6709\u627e\u5230\u6307\u6807\u5b9a\u4e49");
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, (FieldDefine)fieldDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    private void initLinkMap(ReportInfo reportInfo) {
        HashMap<String, DataLinkColumn> gridPosMap = new HashMap<String, DataLinkColumn>();
        HashMap<String, DataLinkColumn> dataPosMap = new HashMap<String, DataLinkColumn>();
        HashMap<String, DataLinkColumn> linkCodeMap = new HashMap<String, DataLinkColumn>();
        HashMap<String, DataLinkColumn> fieldCodeMap = new HashMap<String, DataLinkColumn>();
        HashMap<String, DesignFieldDefine> fieldMap = new HashMap<String, DesignFieldDefine>();
        List<DesignDataLinkDefine> links = this.controller.listDataLinkByForm(reportInfo.getReportKey());
        Map<Object, Object> newCodeFieldInfoMap = new HashMap();
        ConversionFormInfo conversionFormInfo = this.context.getConversionFormInfoMap().get(reportInfo.getReportKey());
        if (conversionFormInfo != null) {
            Map<String, ConversionFieldInfo> fieldInfoMap = conversionFormInfo.getFieldInfoMap();
            newCodeFieldInfoMap = fieldInfoMap.values().stream().collect(Collectors.toMap(ConversionFieldInfo::getNewCode, ConversionFieldInfo2 -> ConversionFieldInfo2));
        }
        for (DesignDataLinkDefine link : links) {
            String linkExpression;
            String fieldKey = linkExpression = link.getLinkExpression();
            try {
                DesignFieldDefine fieldDefine = (DesignFieldDefine)fieldMap.get(fieldKey);
                if (fieldDefine == null && (fieldDefine = this.npDesignTimeController.queryFieldDefine(fieldKey)) != null) {
                    List fieldsInTable = this.npDesignTimeController.getAllFieldsInTable(fieldDefine.getOwnerTableKey());
                    for (DesignFieldDefine field : fieldsInTable) {
                        fieldMap.put(field.getKey(), field);
                    }
                }
                if (fieldDefine == null) continue;
                DataLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, link, (FieldDefine)fieldDefine);
                Position gridPosition = linkColumn.getGridPosition();
                StringBuffer gridPosKey = new StringBuffer().append(gridPosition.row()).append("_").append(gridPosition.col()).append(GRID);
                gridPosMap.put(gridPosKey.toString(), linkColumn);
                Position dataPosition = linkColumn.getDataPosition();
                StringBuffer dataPosKey = new StringBuffer().append(dataPosition.row()).append("_").append(dataPosition.col());
                dataPosMap.put(dataPosKey.toString(), linkColumn);
                String linkCode = linkColumn.getDataLinkCode();
                linkCodeMap.put(linkCode, linkColumn);
                String fieldCode = fieldDefine.getCode();
                String oldCode = newCodeFieldInfoMap.get(fieldCode) == null ? fieldCode : ((ConversionFieldInfo)newCodeFieldInfoMap.get(fieldCode)).getOldCode();
                fieldCodeMap.put(oldCode, linkColumn);
            }
            catch (JQException e) {
                this.logger.error(e.getMessage(), (Throwable)e);
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

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        try {
            if (this.useCache) {
                Map<String, DataLinkColumn> linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
                }
                DataLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(dataLinkCode) : null;
                DataLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataLinkColumn)dataLinkColumn.clone();
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
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(dataLink.getLinkExpression());
            if (fieldDefine == null) {
                return null;
            }
            return this.getDataLinkColumn(reportInfo, dataLink, (FieldDefine)fieldDefine);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    public ReportInfo findReportInfo(String reportName) {
        return this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
    }

    private ReportInfo findReportInfoByFormScheme(String reportName, String formSchemeKey) {
        try {
            String key = formSchemeKey + "_" + reportName;
            if (this.emptyReportName.contains(key)) {
                return null;
            }
            FormDefine form = this.formMap.get(key);
            if (form == null) {
                form = this.controller.getFormByFormSchemeAndCode(reportName, formSchemeKey);
            }
            if (form == null) {
                this.emptyReportName.add(key);
                return null;
            }
            if (form != null) {
                this.formMap.put(key, form);
            }
            return this.getReportInfo(form);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
            return null;
        }
    }

    private DataLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, FieldDefine field) {
        DataLinkColumn column = new DataLinkColumn(field);
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
                column.setRegion(region.getKey().toString());
            }
        }
        column.setReportInfo(reportInfo);
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodModifierStr())) {
            column.setPeriodModifier(PeriodModifier.parse((String)reportInfo.getPeriodModifierStr()));
        }
        if (!StringUtils.isEmpty((String)reportInfo.getPeriodDim())) {
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.setValue("DATATIME", (Object)reportInfo.getPeriodDim());
            column.setDimensionRestriction(dimension);
        }
        return column;
    }

    private ReportInfo getReportInfo(FormDefine form) {
        if (this.reportInfoMp.containsKey(form.getKey().toString())) {
            return this.reportInfoMp.get(form.getKey().toString());
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
        this.reportInfoMp.put(form.getKey().toString(), reportInfo);
        return reportInfo;
    }

    private DataLinkColumn getFmdmDatalink(String fieldCode) throws JQException {
        TableModelDefine tableModel;
        ColumnModelDefine columnModelDefine;
        DesignFormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
        String dwViewKey = formScheme.getDw();
        DesignTaskDefine taskDefine = this.controller.getTask(formScheme.getTaskKey());
        if (StringUtils.isEmpty((String)dwViewKey)) {
            dwViewKey = taskDefine.getDw();
        }
        if ((columnModelDefine = this.dataModelService.getColumnModelDefineByCode((tableModel = this.entityMetaService.getTableModel(dwViewKey)).getID(), fieldCode)) != null) {
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(columnModelDefine.getID());
            return new DataLinkColumn((FieldDefine)fieldDefine);
        }
        DesignDataTable fmdmTable = this.designDataSchemeService.getFmDataTableBySchemeAndDimKey(taskDefine.getDataScheme(), dwViewKey);
        if (fmdmTable == null) {
            return null;
        }
        DesignDataField field = this.designDataSchemeService.getDataFieldByTableKeyAndCode(fmdmTable.getKey(), fieldCode);
        if (field != null) {
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(field.getKey());
            return new DataLinkColumn((FieldDefine)fieldDefine);
        }
        return null;
    }

    public DataLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        DataLinkColumn column = null;
        try {
            if (reportInfo == null) {
                return this.getFmdmDatalink(fieldName);
            }
            DesignFormDefine formDefine = this.controller.getForm(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return this.getFmdmDatalink(fieldName);
            }
            if (this.useCache) {
                Map<String, DataLinkColumn> linkMap = this.rep_fieldCodeMap.get(reportInfo.getReportKey());
                if (linkMap == null) {
                    this.initLinkMap(reportInfo);
                    linkMap = this.rep_fieldCodeMap.get(reportInfo.getReportKey());
                }
                DataLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(fieldName) : null;
                DataLinkColumn linkColumn = null;
                if (dataLinkColumn != null) {
                    linkColumn = (DataLinkColumn)dataLinkColumn.clone();
                }
                return linkColumn;
            }
            ConversionFormInfo conversionFormInfo = this.context.getConversionFormInfoMap().get(reportInfo.getReportKey());
            if (conversionFormInfo == null) {
                return null;
            }
            ConversionFieldInfo conversionFieldInfo = conversionFormInfo.getFieldInfoMap().get(fieldName);
            if (conversionFieldInfo == null) {
                return null;
            }
            List<DesignDataLinkDefine> links = this.controller.listDataLinkByFormAndFieldKey(reportInfo.getReportKey(), conversionFieldInfo.getNewKey());
            if (links != null && links.size() > 0) {
                return this.getDataLinkColumn(reportInfo, links.get(0), (FieldDefine)this.npDesignTimeController.queryFieldDefine(conversionFieldInfo.getNewKey()));
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        return column;
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
        ReportInfo reportInfo = null;
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
            reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
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

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataLinkColumn dataLinkColumn) {
        return null;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return false;
    }
}

