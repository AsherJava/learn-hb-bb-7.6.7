/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeFmlCompileParamDao;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FmlTrackDataLinkFinder
implements IDataLinkFinder {
    private RuntimeFmlCompileParamDao runtimeFmlCompileParamDao;
    private IRunTimeViewController controller;
    private IEntityMetaService entityMetaService;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private Map<String, List<DataLinkDefine>> allLinkInFMScheme = new HashMap<String, List<DataLinkDefine>>();
    private final Map<String, Map<String, FieldDefine>> key_fieldMap = new HashMap<String, Map<String, FieldDefine>>();
    private final Map<String, Map<String, FieldDefine>> code_fieldMap = new HashMap<String, Map<String, FieldDefine>>();
    private List<ReportInfo> reportInfos = null;
    private final List<FormDefine> formDefines = new ArrayList<FormDefine>();
    private static final String GRID = "_grid";
    private final String formSchemeKey;
    private final Map<String, ReportInfo> reportInfoMp = new HashMap<String, ReportInfo>();
    private final Map<String, FormDefine> formMap = new HashMap<String, FormDefine>();
    private final Set<String> emptyReportName = new HashSet<String>();
    private final Map<String, Map<String, DataLinkColumn>> rep_gridPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private final Map<String, Map<String, DataLinkColumn>> rep_dataPosMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private final Map<String, Map<String, DataLinkColumn>> rep_linkCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private final Map<String, Map<String, DataLinkColumn>> rep_fieldCodeMap = new HashMap<String, Map<String, DataLinkColumn>>();
    private final Logger logger = LogFactory.getLogger(FmlTrackDataLinkFinder.class);

    public FmlTrackDataLinkFinder(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
        this.runtimeFmlCompileParamDao = (RuntimeFmlCompileParamDao)BeanUtil.getBean(RuntimeFmlCompileParamDao.class);
        this.controller = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        this.entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        this.allLinkInFMScheme = this.runtimeFmlCompileParamDao.getAllDataLinkInFormScheme(formSchemeKey);
        this.loadFieldParamByFormScheme(formSchemeKey);
        List<TaskLinkDefine> taskLinkDefines = this.controller.queryLinksByCurrentFormScheme(formSchemeKey);
        ArrayList<String> formSchemeKeys = new ArrayList<String>();
        formSchemeKeys.add(formSchemeKey);
        for (TaskLinkDefine taskLinkDefine : taskLinkDefines) {
            String relatedFormSchemeKey = taskLinkDefine.getRelatedFormSchemeKey();
            if (!StringUtils.isNotEmpty((String)relatedFormSchemeKey)) continue;
            formSchemeKeys.add(relatedFormSchemeKey);
        }
        this.formDefines.addAll(this.runtimeFmlCompileParamDao.queryFormInScheme(formSchemeKeys));
    }

    private void loadFieldParamByFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
        List<FieldDefine> allFieldInFmScheme = this.runtimeFmlCompileParamDao.getAllDataFieldInFormScheme(formSchemeKey);
        HashMap<String, FieldDefine> fieldMap = new HashMap<String, FieldDefine>();
        for (FieldDefine fieldDefine : allFieldInFmScheme) {
            fieldMap.put(fieldDefine.getKey(), fieldDefine);
        }
        this.key_fieldMap.put(formSchemeKey, fieldMap);
        List allFieldsInTable = new ArrayList();
        try {
            allFieldsInTable = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableModel.getID());
        }
        catch (Exception e) {
            this.logger.error(e.getMessage() + "\u5c01\u9762\u4ee3\u7801\u6307\u6807\u67e5\u8be2\u5f02\u5e38", (Throwable)e);
        }
        Map<Object, Object> fmdmFieldMap = new HashMap();
        fmdmFieldMap = allFieldsInTable.stream().collect(Collectors.toMap(UniversalFieldDefine::getCode, FieldDefine2 -> FieldDefine2));
        this.code_fieldMap.put(formSchemeKey, fmdmFieldMap);
    }

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        Map<String, DataLinkColumn> linkMap = isGridPosition ? this.rep_gridPosMap.get(reportInfo.getReportKey()) : this.rep_dataPosMap.get(reportInfo.getReportKey());
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
            try {
                linkColumn = (DataLinkColumn)dataLinkColumn.clone();
            }
            catch (CloneNotSupportedException e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return linkColumn;
    }

    private void initLinkMap(ReportInfo reportInfo) {
        HashMap gridPosMap = new HashMap();
        HashMap dataPosMap = new HashMap();
        HashMap linkCodeMap = new HashMap();
        HashMap fieldCodeMap = new HashMap();
        List<DataLinkDefine> links = this.allLinkInFMScheme.get(reportInfo.getReportKey());
        String curFormScheme = this.controller.queryFormById(reportInfo.getReportKey()).getFormScheme();
        if (links == null) {
            Map<String, List<DataLinkDefine>> map = this.runtimeFmlCompileParamDao.getAllDataLinkInFormScheme(curFormScheme);
            if (map != null) {
                this.allLinkInFMScheme.putAll(map);
            }
            if ((links = this.allLinkInFMScheme.get(reportInfo.getReportKey())) == null) {
                links = new ArrayList<DataLinkDefine>();
                this.allLinkInFMScheme.put(reportInfo.getReportKey(), links);
            }
            this.loadFieldParamByFormScheme(curFormScheme);
        }
        HashMap fmdmFieldMap = this.code_fieldMap.get(curFormScheme) != null ? this.code_fieldMap.get(curFormScheme) : new HashMap();
        HashMap fieldMap = this.key_fieldMap.get(curFormScheme) != null ? this.key_fieldMap.get(curFormScheme) : new HashMap();
        FormDefine formDefine = null;
        for (FormDefine obj : this.formDefines) {
            if (!obj.getKey().equals(reportInfo.getReportKey())) continue;
            formDefine = obj;
            break;
        }
        if (formDefine != null) {
            FormDefine finalFormDefine = formDefine;
            links.forEach(link -> {
                String linkExpression = link.getLinkExpression();
                try {
                    FieldDefine fieldDefine = null;
                    fieldDefine = finalFormDefine.getFormType() == FormType.FORM_TYPE_NEWFMDM ? (FieldDefine)fmdmFieldMap.get(linkExpression) : (FieldDefine)fieldMap.get(linkExpression);
                    if (fieldDefine != null) {
                        DataLinkColumn linkColumn = this.getDataLinkColumn(reportInfo, (DataLinkDefine)link, fieldDefine);
                        Position gridPosition = linkColumn.getGridPosition();
                        String gridPosKey = gridPosition.row() + "_" + gridPosition.col() + GRID;
                        gridPosMap.put(gridPosKey, linkColumn);
                        Position dataPosition = linkColumn.getDataPosition();
                        String dataPosKey = dataPosition.row() + "_" + dataPosition.col();
                        dataPosMap.put(dataPosKey, linkColumn);
                        String linkCode = linkColumn.getDataLinkCode();
                        linkCodeMap.put(linkCode, linkColumn);
                        fieldCodeMap.put(fieldDefine.getCode(), linkColumn);
                    }
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), (Throwable)e);
                }
            });
            this.rep_gridPosMap.put(reportInfo.getReportKey(), gridPosMap);
            this.rep_dataPosMap.put(reportInfo.getReportKey(), dataPosMap);
            this.rep_linkCodeMap.put(reportInfo.getReportKey(), linkCodeMap);
            this.rep_fieldCodeMap.put(reportInfo.getReportKey(), fieldCodeMap);
        }
    }

    private DataLinkColumn getDataLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink, FieldDefine field) {
        DataLinkColumn column = new DataLinkColumn(field);
        if (dataLink != null) {
            column.setDataLinkCode(dataLink.getUniqueCode());
            column.setGridPosition(new Position(dataLink.getPosX(), dataLink.getPosY()));
            column.setDataPosition(new Position(dataLink.getColNum(), dataLink.getRowNum()));
            column.setRegion(dataLink.getRegionKey());
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

    public DataLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        Map<String, DataLinkColumn> linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
        if (linkMap == null) {
            this.initLinkMap(reportInfo);
            linkMap = this.rep_linkCodeMap.get(reportInfo.getReportKey());
        }
        DataLinkColumn dataLinkColumn = linkMap != null ? linkMap.get(dataLinkCode) : null;
        DataLinkColumn linkColumn = null;
        if (dataLinkColumn != null) {
            try {
                linkColumn = (DataLinkColumn)dataLinkColumn.clone();
            }
            catch (CloneNotSupportedException e) {
                this.logger.error(e.getMessage(), (Throwable)e);
            }
        }
        return linkColumn;
    }

    public DataLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        try {
            if (reportInfo == null) {
                FieldDefine fieldDefine = this.code_fieldMap.get(this.formSchemeKey).get(fieldName);
                return new DataLinkColumn(fieldDefine);
            }
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
                for (FormDefine formDefine : this.formDefines) {
                    if (!formDefine.getFormCode().equals(reportName) && !formDefine.getTitle().equals(reportName)) continue;
                    form = formDefine;
                    break;
                }
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
            List<DataLinkDefine> allLinks = this.allLinkInFMScheme.get(form.getKey());
            String curFormScheme = this.controller.queryFormById(form.getKey()).getFormScheme();
            if (allLinks == null) {
                Map<String, List<DataLinkDefine>> map = this.runtimeFmlCompileParamDao.getAllDataLinkInFormScheme(curFormScheme);
                if (map != null) {
                    this.allLinkInFMScheme.putAll(map);
                }
                if ((allLinks = this.allLinkInFMScheme.get(form.getKey())) == null) {
                    allLinks = new ArrayList<DataLinkDefine>();
                    this.allLinkInFMScheme.put(form.getKey(), allLinks);
                }
                this.loadFieldParamByFormScheme(curFormScheme);
            }
            for (DataLinkDefine link : allLinks) {
                rowSet.add(link.getRowNum());
                colSet.add(link.getColNum());
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), (Throwable)e);
        }
        int[] rowArr = rowSet.stream().mapToInt(Integer::intValue).toArray();
        int[] colArr = colSet.stream().mapToInt(Integer::intValue).toArray();
        ReportInfo report = new ReportInfo(form.getKey(), form.getFormCode(), form.getTitle(), colArr, rowArr);
        this.reportInfoMp.put(form.getKey(), report);
        return report;
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        try {
            ReportInfo reportInfo;
            String periodModifierStr = "";
            String periodDim = "";
            String schemeKey = null;
            TaskLinkDefine linkDefine = this.controller.queryTaskLinkByCurrentFormSchemeAndNumber(this.formSchemeKey, linkAlias);
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
            PeriodType periodType = formScheme.getPeriodType();
            if (linkDefine != null) {
                schemeKey = linkDefine.getRelatedFormSchemeKey();
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModifierStr = linkDefine.getPeriodOffset();
                        break;
                    }
                    case PERIOD_TYPE_NEXT: {
                        periodModifierStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_PREVIOUS: {
                        periodModifierStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
            } else {
                FormSchemeDefine schemeDefine = this.controller.getFormschemeByCode(linkAlias);
                if (schemeDefine != null) {
                    schemeKey = schemeDefine.getKey();
                }
            }
            if ((reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey)) != null) {
                reportInfo.setPeriodModifierStr(periodModifierStr);
                reportInfo.setPeriodDim(periodDim);
            }
            return reportInfo;
        }
        catch (Exception e) {
            this.logger.error("\u83b7\u53d6\u62a5\u8868\u5931\u8d25:" + e.getMessage());
            return null;
        }
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        if (this.reportInfos == null) {
            this.reportInfos = new ArrayList<ReportInfo>();
            List<TaskLinkDefine> links = this.controller.queryLinksByCurrentFormScheme(this.formSchemeKey);
            FormSchemeDefine formScheme = this.controller.getFormScheme(this.formSchemeKey);
            PeriodType periodType = formScheme.getPeriodType();
            for (TaskLinkDefine linkDefine : links) {
                String schemeKey = linkDefine.getRelatedFormSchemeKey();
                String periodModifierStr = "";
                String periodDim = "";
                switch (linkDefine.getConfiguration()) {
                    case PERIOD_TYPE_OFFSET: {
                        periodModifierStr = linkDefine.getPeriodOffset();
                        break;
                    }
                    case PERIOD_TYPE_NEXT: {
                        periodModifierStr = "+1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_PREVIOUS: {
                        periodModifierStr = "-1" + (char)PeriodConsts.typeToCode((int)periodType.type());
                        break;
                    }
                    case PERIOD_TYPE_SPECIFIED: {
                        periodDim = linkDefine.getSpecified();
                        break;
                    }
                }
                ReportInfo reportInfo = this.findReportInfoByFormScheme(reportName, schemeKey);
                if (reportInfo == null) continue;
                reportInfo.setPeriodModifierStr(periodModifierStr);
                reportInfo.setPeriodDim(periodDim);
                this.findReportInfoByFormScheme(reportName, this.formSchemeKey);
                this.reportInfos.add(reportInfo);
            }
            return this.reportInfos;
        }
        return this.reportInfos;
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

