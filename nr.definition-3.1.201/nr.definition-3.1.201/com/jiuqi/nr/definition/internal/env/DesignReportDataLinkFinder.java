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
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.definition.internal.env;

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
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
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
import org.springframework.util.CollectionUtils;

public class DesignReportDataLinkFinder
implements IDataLinkFinder {
    private static final String GRID = "_grid";
    private IDesignTimeViewController controller;
    private IDataDefinitionDesignTimeController npDesignTimeController;
    private DataModelService dataModelService;
    private IDesignDataSchemeService designDataSchemeService;
    private String formSchemeKey;
    private Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private Map<String, DataRegionDefine> regionMp = new HashMap<String, DataRegionDefine>();
    private Map<String, FormDefine> formMap = new HashMap<String, FormDefine>();
    private Set<String> emptyReportName = new HashSet<String>();
    private Map<String, Map<String, DataLinkColumn>> rep_gridPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_dataPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_linkCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Map<String, Map<String, DataLinkColumn>> rep_fieldCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private Logger logger = LogFactory.getLogger(DesignReportDataLinkFinder.class);
    private boolean useCache = false;
    private IEntityMetaService entityMetaService;

    public void setUseCache() {
        this.useCache = true;
    }

    public DesignReportDataLinkFinder(IDesignTimeViewController controller, IDataDefinitionDesignTimeController npDesignTimeController, String formSchemeKey) {
        this.controller = controller;
        this.npDesignTimeController = npDesignTimeController;
        this.formSchemeKey = formSchemeKey;
        this.entityMetaService = BeanUtil.getBean(IEntityMetaService.class);
        this.dataModelService = BeanUtil.getBean(DataModelService.class);
        this.designDataSchemeService = BeanUtil.getBean(IDesignDataSchemeService.class);
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
            DesignDataLinkDefine dataLink = isGridPosition ? this.controller.queryDataLinkDefine(reportInfo.getReportKey(), colIndex, rowIndex) : this.controller.queryDataLinkDefineByColRow(reportInfo.getReportKey(), colIndex, rowIndex);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.info("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            DesignFormDefine formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return this.getFmdmDatalink(reportInfo, dataLink);
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

    private DataLinkColumn getFmdmDatalink(ReportInfo reportInfo, DataLinkDefine link) throws JQException {
        TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), link.getLinkExpression());
        if (columnModelDefine != null) {
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(columnModelDefine.getID());
            return this.getDataLinkColumn(reportInfo, link, (FieldDefine)fieldDefine);
        }
        return null;
    }

    private DataLinkColumn getFmdmDatalink(String fieldCode) throws JQException {
        TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModel.getID(), fieldCode);
        if (columnModelDefine != null) {
            DesignFieldDefine fieldDefine = this.npDesignTimeController.queryFieldDefine(columnModelDefine.getID());
            return new DataLinkColumn((FieldDefine)fieldDefine);
        }
        return null;
    }

    private String getDwEntityId() {
        DesignFormSchemeDefine formScheme = this.controller.queryFormSchemeDefine(this.formSchemeKey);
        String dwEntityId = formScheme.getDw();
        if (StringUtils.isEmpty((String)dwEntityId)) {
            DesignTaskDefine taskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
            dwEntityId = taskDefine.getDw();
        }
        return dwEntityId;
    }

    private void initLinkMap(ReportInfo reportInfo) throws Exception {
        HashMap gridPosMap = new HashMap();
        HashMap dataPosMap = new HashMap();
        HashMap linkCodeMap = new HashMap();
        HashMap fieldCodeMap = new HashMap();
        HashMap fieldMap = new HashMap();
        List<DesignDataLinkDefine> links = this.controller.getAllLinksInForm(reportInfo.getReportKey());
        DesignFormDefine formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
        if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(this.getDwEntityId());
            links.stream().filter(link -> link.getType() == DataLinkType.DATA_LINK_TYPE_FMDM).forEach(link -> {
                DesignFieldDefine fieldDefine = (DesignFieldDefine)fieldMap.get(link.getLinkExpression());
                if (fieldDefine == null) {
                    try {
                        List entityFields = this.npDesignTimeController.getAllFieldsInTable(tableModel.getID());
                        entityFields.stream().forEach(e -> fieldMap.put(e.getCode(), e));
                    }
                    catch (JQException entityFields) {
                        // empty catch block
                    }
                }
                if ((fieldDefine = (DesignFieldDefine)fieldMap.get(link.getLinkExpression())) != null) {
                    String fieldCode = fieldDefine.getCode();
                    DataLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, (DataLinkDefine)link, (FieldDefine)fieldDefine);
                    Position gridPosition = linkColumn.getGridPosition();
                    StringBuffer gridPosKey = new StringBuffer().append(gridPosition.row()).append("_").append(gridPosition.col()).append(GRID);
                    gridPosMap.put(gridPosKey.toString(), linkColumn);
                    Position dataPosition = linkColumn.getDataPosition();
                    StringBuffer dataPosKey = new StringBuffer().append(dataPosition.row()).append("_").append(dataPosition.col());
                    dataPosMap.put(dataPosKey.toString(), linkColumn);
                    String linkCode = linkColumn.getDataLinkCode();
                    linkCodeMap.put(linkCode, linkColumn);
                    fieldCodeMap.put(fieldCode, linkColumn);
                }
            });
        } else {
            links.forEach(link -> {
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
                    if (fieldDefine != null) {
                        DataLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, (DataLinkDefine)link, (FieldDefine)fieldDefine);
                        Position gridPosition = linkColumn.getGridPosition();
                        StringBuffer gridPosKey = new StringBuffer().append(gridPosition.row()).append("_").append(gridPosition.col()).append(GRID);
                        gridPosMap.put(gridPosKey.toString(), linkColumn);
                        Position dataPosition = linkColumn.getDataPosition();
                        String dataPosKey = dataPosition.row() + "_" + dataPosition.col();
                        dataPosMap.put(dataPosKey, linkColumn);
                        String linkCode = linkColumn.getDataLinkCode();
                        linkCodeMap.put(linkCode, linkColumn);
                        fieldCodeMap.put(fieldDefine.getCode(), linkColumn);
                    }
                }
                catch (JQException e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            });
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
            DesignDataLinkDefine dataLink = this.controller.queryDataLinkDefineByUniquecode(reportInfo.getReportKey(), dataLinkCode);
            if (dataLink == null) {
                return null;
            }
            if (dataLink.getLinkExpression() == null) {
                this.logger.info("\u5355\u5143\u683c\u672a\u5173\u8054\u6307\u6807");
                return null;
            }
            DesignFormDefine formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                return this.getFmdmDatalink(reportInfo, dataLink);
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

    private DataLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, FieldDefine field) {
        DataLinkColumn column = new DataLinkColumn(field);
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

    public DataLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        DataLinkColumn column = null;
        try {
            if (reportInfo == null) {
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
            DesignFormDefine formDefine = this.controller.querySoftFormDefine(reportInfo.getReportKey());
            if (formDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM) {
                List<DesignDataLinkDefine> links = this.controller.getLinksInFormByField(reportInfo.getReportKey(), fieldName);
                DataLinkColumn dataLinkColumn = !CollectionUtils.isEmpty(links) ? this.getFmdmDatalink(reportInfo, links.get(0)) : this.getFmdmDatalink(fieldName);
                return dataLinkColumn;
            }
            List<DesignFieldDefine> fields = this.controller.getAllFieldsByLinksInForm(reportInfo.getReportKey());
            if (fields.size() == 0) {
                return column;
            }
            Optional<DesignFieldDefine> optional = fields.stream().filter(f -> f.getCode().equalsIgnoreCase(fieldName)).findFirst();
            if (optional.isPresent()) {
                DesignFieldDefine fieldDefine = optional.get();
                List<DesignDataLinkDefine> links = this.controller.getLinksInFormByField(reportInfo.getReportKey(), fieldDefine.getKey());
                if (links != null && links.size() > 0) {
                    column = this.getDataLinkColumn(reportInfo, links.get(0), (FieldDefine)fieldDefine);
                }
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

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataLinkColumn dataLinkColumn) {
        return null;
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        return false;
    }
}

