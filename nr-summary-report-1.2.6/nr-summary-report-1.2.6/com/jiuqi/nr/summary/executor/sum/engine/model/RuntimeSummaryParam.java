/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.option.treegroup.GroupInfo
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuqi.nr.summary.executor.sum.engine.model;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.treegroup.GroupInfo;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.summary.api.SummaryFormula;
import com.jiuqi.nr.summary.executor.sum.SumBeanSet;
import com.jiuqi.nr.summary.executor.sum.SumContext;
import com.jiuqi.nr.summary.executor.sum.SumParam;
import com.jiuqi.nr.summary.executor.sum.engine.model.RuntimeSummaryReport;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.model.soulution.CommitState;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SourceDimensionRange;
import com.jiuqi.nr.summary.model.soulution.SummarySolutionModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RuntimeSummaryParam {
    private SumParam paramDefine;
    private List<RuntimeSummaryReport> reports = new ArrayList<RuntimeSummaryReport>();
    private Map<String, SummaryReportModel> reportsByName = new HashMap<String, SummaryReportModel>();
    private Map<String, List<String>> mainDimMap = new HashMap<String, List<String>>();
    private DimensionValueSet solutionDimValues = new DimensionValueSet();
    private FormSchemeDefine srcFromScheme;
    private TaskDefine srcTaskDefine;
    private IEntityDefine targetMdEntity;
    private IEntityDefine sourceMdEntity;
    private IEntityAttribute sourceRefTargetField = null;
    private FormulaCallBack calcFormulaCallBack;
    private IEntityTable targetEntityTable;
    private boolean sumBySolution = false;

    public RuntimeSummaryParam(SumParam paramDefine) {
        this.paramDefine = paramDefine;
    }

    public void doInit(SumContext sumContext, boolean afterCalculate, String formSchemeKey) throws Exception {
        SummarySolutionModel summarySolutionModel = this.paramDefine.getSummarySolutionModel();
        this.targetMdEntity = sumContext.getBeanSet().entityMetaService.queryEntity(summarySolutionModel.getTargetDimension());
        this.srcFromScheme = sumContext.getBeanSet().runTimeViewController.getFormScheme(formSchemeKey);
        this.srcTaskDefine = sumContext.getBeanSet().runTimeViewController.queryTaskDefine(summarySolutionModel.getMainTask());
        this.sourceMdEntity = sumContext.getBeanSet().entityMetaService.queryEntity(this.srcTaskDefine.getDw());
        GroupInfo groupInfo = sumContext.getBeanSet().dimGroupOptionService.getGroupInfo(summarySolutionModel.getMainTask());
        if (groupInfo != null) {
            String dimFieldCode = groupInfo.getDimFieldCode();
            IEntityModel srcEntityModel = sumContext.getBeanSet().entityMetaService.getEntityModel(this.sourceMdEntity.getId());
            List refers = sumContext.getBeanSet().entityMetaService.getEntityRefer(this.sourceMdEntity.getId());
            for (IEntityRefer refer : refers) {
                if (!refer.getOwnField().equals(dimFieldCode) || !refer.getReferEntityId().equals(this.targetMdEntity.getId())) continue;
                this.sourceRefTargetField = srcEntityModel.getAttribute(dimFieldCode);
            }
        }
        this.initMainDimMap(sumContext, summarySolutionModel);
        this.initSolutionDimValues(sumContext, summarySolutionModel);
        this.initSummaryReport(sumContext, afterCalculate);
    }

    public Map<DimensionValueSet, DimensionValueSet> expandSummaryDims(SumContext sumContext) {
        HashMap<DimensionValueSet, DimensionValueSet> dimMap = new HashMap<DimensionValueSet, DimensionValueSet>();
        Map<String, String> refDimMap = this.getRefDimMap(sumContext);
        for (Map.Entry<String, List<String>> entry : this.mainDimMap.entrySet()) {
            DimensionValueSet dim = new DimensionValueSet(this.solutionDimValues);
            dim.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
            ArrayList dimList = new ArrayList();
            ExpressionUtils.expandDims((DimensionValueSet)dim, dimList);
            for (DimensionValueSet targetDimValues : dimList) {
                DimensionValueSet sourceDimValues = new DimensionValueSet(targetDimValues);
                targetDimValues.setValue(this.targetMdEntity.getDimensionName(), (Object)entry.getKey());
                if (!refDimMap.isEmpty()) {
                    IEntityRow targetEntityRow = this.targetEntityTable.findByEntityKey(entry.getKey());
                    refDimMap.forEach((dimName, fieldName) -> {
                        String dimValue = targetEntityRow.getAsString(fieldName);
                        targetDimValues.setValue(dimName, (Object)dimValue);
                    });
                }
                sourceDimValues.setValue(this.sourceMdEntity.getDimensionName(), entry.getValue());
                dimMap.put(targetDimValues, sourceDimValues);
            }
        }
        return dimMap;
    }

    private Map<String, String> getRefDimMap(SumContext sumContext) {
        HashMap<String, String> refDimMap = new HashMap<String, String>();
        List dataDimensions = sumContext.getBeanSet().dataSchemeService.getDataSchemeDimension(this.paramDefine.getSummaryDataScheme().getKey());
        for (DataDimension dataDimension : dataDimensions) {
            IEntityDefine dimEntity;
            IEntityModel entityModel;
            IEntityAttribute entityAttribute;
            if (!StringUtils.isNotEmpty((String)dataDimension.getDimAttribute()) || (entityAttribute = (entityModel = sumContext.getBeanSet().entityMetaService.getEntityModel((dimEntity = sumContext.getBeanSet().entityMetaService.queryEntity(dataDimension.getDimKey())).getId())).getAttribute(dataDimension.getDimAttribute())).isMultival()) continue;
            refDimMap.put(dimEntity.getDimensionName(), dataDimension.getDimAttribute());
        }
        return refDimMap;
    }

    private void initSummaryReport(SumContext sumContext, boolean afterCalculate) throws Exception, ParseException {
        ArrayList<Formula> allFormulas = new ArrayList<Formula>();
        List<SummaryReportModel> summaryReportModels = this.paramDefine.getSummaryReportModels();
        if (summaryReportModels == null || summaryReportModels.isEmpty()) {
            this.sumBySolution = true;
            summaryReportModels = sumContext.getBeanSet().summarySolutionService.getSummaryReportModelsBySolu(this.paramDefine.getSummarySolutionModel().getKey());
        }
        for (SummaryReportModel report : summaryReportModels) {
            List<SummaryFormula> formulas;
            if (sumContext.getMonitor().isCancel()) break;
            RuntimeSummaryReport rReport = new RuntimeSummaryReport(report);
            rReport.doInit(sumContext);
            this.reports.add(rReport);
            this.reportsByName.put(report.getName(), report);
            if (!afterCalculate || (formulas = sumContext.getBeanSet().summaryFormulaService.getSummaryFormulasByReport(report.getKey())) == null) continue;
            for (SummaryFormula summaryFormula : formulas) {
                Formula f = new Formula();
                f.setId(summaryFormula.getKey());
                f.setFormula(summaryFormula.getExpression());
                f.setReportName(report.getName());
                f.setFormKey(report.getKey());
                f.setOrder(OrderGenerator.newOrder());
                allFormulas.add(f);
            }
        }
        if (allFormulas.size() > 0) {
            List expressions = DataEngineFormulaParser.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)sumContext.getCalcExecutorContext(), allFormulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE);
            this.calcFormulaCallBack = new FormulaCallBack();
            this.calcFormulaCallBack.getParsedExpressions().addAll(expressions);
        }
    }

    private void initMainDimMap(SumContext sumContext, SummarySolutionModel summarySolutionModel) {
        this.targetEntityTable = this.getTargetEntityTable(sumContext, summarySolutionModel);
        List targetEntityRows = this.targetEntityTable.getAllRows();
        if (summarySolutionModel.getSourceDimensionRange() == SourceDimensionRange.SELF) {
            for (IEntityRow targetRow : targetEntityRows) {
                List<String> srcKeys = Collections.singletonList(targetRow.getEntityKeyData());
                if (this.sourceRefTargetField != null) {
                    srcKeys = this.getSubSrcKeys(sumContext, srcKeys, summarySolutionModel);
                }
                srcKeys = this.filterByCommitState(sumContext, srcKeys, summarySolutionModel);
                this.mainDimMap.put(targetRow.getEntityKeyData(), srcKeys);
            }
        } else if (summarySolutionModel.getSourceDimensionRange() == SourceDimensionRange.CONDITION || summarySolutionModel.getSourceDimensionRange() == SourceDimensionRange.LIST) {
            DimensionValueSet srcFullEntityDimValues = new DimensionValueSet();
            srcFullEntityDimValues.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
            IEntityTable fullEntityTable = this.getEntityTable(sumContext, this.sourceMdEntity.getId(), srcFullEntityDimValues, null, AuthorityType.None);
            if (this.sourceRefTargetField == null) {
                IEntityTable srcEntityTable = this.getSrcEntityTable(sumContext, summarySolutionModel);
                List srcEntityRows = srcEntityTable.getAllRows();
                for (IEntityRow targetRow : targetEntityRows) {
                    List<String> srcKeys = new ArrayList<String>();
                    List allChilds = fullEntityTable.getAllChildRows(targetRow.getEntityKeyData());
                    Set keySet = allChilds.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                    keySet.add(targetRow.getEntityKeyData());
                    for (IEntityRow srcRow : srcEntityRows) {
                        if (!keySet.contains(srcRow.getEntityKeyData())) continue;
                        srcKeys.add(srcRow.getEntityKeyData());
                    }
                    srcKeys = this.filterByCommitState(sumContext, srcKeys, summarySolutionModel);
                    this.mainDimMap.put(targetRow.getEntityKeyData(), srcKeys);
                }
            } else {
                IEntityTable srcSubEntityTable = this.getSrcSubEntityTable(sumContext, null, summarySolutionModel);
                List srcEntityRows = srcSubEntityTable.getAllRows();
                for (IEntityRow targetRow : targetEntityRows) {
                    List<String> srcKeys = new ArrayList<String>();
                    List allChilds = fullEntityTable.getAllChildRows(targetRow.getEntityKeyData());
                    Set keySet = allChilds.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toSet());
                    keySet.add(targetRow.getEntityKeyData());
                    for (IEntityRow srcRow : srcEntityRows) {
                        if (!keySet.contains(srcRow.getAsString(this.sourceRefTargetField.getCode()))) continue;
                        srcKeys.add(srcRow.getEntityKeyData());
                    }
                    srcKeys = this.filterByCommitState(sumContext, srcKeys, summarySolutionModel);
                    this.mainDimMap.put(targetRow.getEntityKeyData(), srcKeys);
                }
            }
        } else {
            for (IEntityRow targetRow : targetEntityRows) {
                List<String> srcKeys = new ArrayList<String>();
                ArrayList<IEntityRow> srcEntityRows = new ArrayList<IEntityRow>();
                IEntityTable srcEntityTable = this.getSrcEntityTable(sumContext, summarySolutionModel);
                switch (summarySolutionModel.getSourceDimensionRange()) {
                    case DIRECT: {
                        srcEntityRows.addAll(srcEntityTable.getChildRows(targetRow.getEntityKeyData()));
                        break;
                    }
                    case LEAF: {
                        List allChilds = srcEntityTable.getAllChildRows(targetRow.getEntityKeyData());
                        for (IEntityRow child : allChilds) {
                            if (srcEntityTable.getDirectChildCount(child.getEntityKeyData()) != 0) continue;
                            srcEntityRows.add(child);
                        }
                        break;
                    }
                    case ALL: {
                        srcEntityRows.add(targetRow);
                        srcEntityRows.addAll(srcEntityTable.getAllChildRows(targetRow.getEntityKeyData()));
                        break;
                    }
                }
                for (IEntityRow srcRow : srcEntityRows) {
                    srcKeys.add(srcRow.getEntityKeyData());
                }
                if (this.sourceRefTargetField != null) {
                    srcKeys = this.getSubSrcKeys(sumContext, srcKeys, summarySolutionModel);
                }
                srcKeys = this.filterByCommitState(sumContext, srcKeys, summarySolutionModel);
                this.mainDimMap.put(targetRow.getEntityKeyData(), srcKeys);
            }
        }
    }

    private List<String> filterByCommitState(SumContext sumContext, List<String> srckeys, SummarySolutionModel summarySolutionModel) {
        if (summarySolutionModel.getCommitState() == CommitState.COMMIT) {
            ArrayList<UploadState> uploadStates = new ArrayList<UploadState>(1);
            uploadStates.add(UploadState.UPLOADED);
            DimensionValueSet sourceMasterKeys = new DimensionValueSet();
            sourceMasterKeys.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
            String unitDim = sumContext.getParam().getSourceMdEntity().getDimensionName();
            sourceMasterKeys.setValue(unitDim, srckeys);
            List stateRecords = sumContext.getBeanSet().workflowService.getDataByActionCode(this.srcFromScheme, sourceMasterKeys, uploadStates);
            ArrayList<String> uploadUnits = new ArrayList<String>(stateRecords.size());
            for (UploadStateNew state : stateRecords) {
                DimensionValueSet dim = state.getEntities();
                uploadUnits.add(dim.getValue(unitDim).toString());
            }
            return uploadUnits;
        }
        return srckeys;
    }

    private void initSolutionDimValues(SumContext sumContext, SummarySolutionModel summarySolutionModel) {
        List<DimensionData> currentDims = this.paramDefine.getDimDataRange();
        List<DimensionData> solutionDims = summarySolutionModel.getDimDataRange();
        this.putToSolutionDimValues(sumContext, currentDims);
        this.putToSolutionDimValues(sumContext, solutionDims);
    }

    private void putToSolutionDimValues(SumContext sumContext, List<DimensionData> dims) {
        if (dims != null) {
            for (DimensionData dimensionData : dims) {
                String dimEntityId = dimensionData.getName();
                IEntityDefine dimEntity = sumContext.getBeanSet().entityMetaService.queryEntity(dimEntityId);
                String dimensionName = dimEntity.getDimensionName();
                if (this.solutionDimValues.hasValue(dimensionName)) continue;
                String values = dimensionData.getValues();
                if (values != null) {
                    String[] array = values.split(";");
                    this.solutionDimValues.setValue(dimensionName, Arrays.asList(array));
                    continue;
                }
                String dimAttribute = sumContext.getBeanSet().dataSchemeService.getDimAttributeByReportDim(this.paramDefine.getSummaryDataScheme().getKey(), dimEntityId);
                if (dimAttribute != null) continue;
                DimensionValueSet masterKeys = new DimensionValueSet();
                masterKeys.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
                IEntityTable dimEntitTable = this.getEntityTable(sumContext, dimEntityId, masterKeys, null, AuthorityType.None);
                ArrayList<String> dimKeys = new ArrayList<String>();
                for (IEntityRow entityRow : dimEntitTable.getAllRows()) {
                    dimKeys.add(entityRow.getEntityKeyData());
                }
                this.solutionDimValues.setValue(dimensionName, dimKeys);
            }
        }
    }

    private List<String> getSubSrcKeys(SumContext sumContext, List<String> orgKeys, SummarySolutionModel summarySolutionModel) {
        ArrayList<String> srcKeys = new ArrayList<String>();
        IEntityTable srcSubEntityTable = this.getSrcSubEntityTable(sumContext, orgKeys, summarySolutionModel);
        List srcEntityRows = srcSubEntityTable.getAllRows();
        srcEntityRows.forEach(row -> srcKeys.add(row.getEntityKeyData()));
        return srcKeys;
    }

    private IEntityTable getSrcSubEntityTable(SumContext sumContext, List<String> orgKeys, SummarySolutionModel summarySolutionModel) {
        String srcMdFilter = summarySolutionModel.getSourceDimensionFilter();
        DimensionValueSet srcEntityDimValues = new DimensionValueSet();
        srcEntityDimValues.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
        if (summarySolutionModel.getSourceDimensionValues() != null && summarySolutionModel.getSourceDimensionValues().size() > 0) {
            srcEntityDimValues.setValue(this.sourceMdEntity.getDimensionName(), summarySolutionModel.getSourceDimensionValues());
        }
        if (orgKeys != null && orgKeys.size() > 0) {
            StringBuilder filter = new StringBuilder();
            if (StringUtils.isNotEmpty((String)srcMdFilter)) {
                filter.append("(").append(srcMdFilter).append(") and ");
            }
            filter.append("(");
            filter.append("[").append(this.sourceRefTargetField.getCode()).append("]").append(" in {");
            orgKeys.forEach(o -> filter.append("'").append((String)o).append("',"));
            filter.setLength(filter.length() - 1);
            filter.append("}");
            filter.append(")");
            srcMdFilter = filter.toString();
        }
        IEntityTable srcEntityTable = this.getEntityTable(sumContext, this.sourceMdEntity.getId(), srcEntityDimValues, srcMdFilter, AuthorityType.None);
        return srcEntityTable;
    }

    private IEntityTable getSrcEntityTable(SumContext sumContext, SummarySolutionModel summarySolutionModel) {
        String srcMdFilter = null;
        DimensionValueSet srcEntityDimValues = new DimensionValueSet();
        srcEntityDimValues.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
        if (this.sourceRefTargetField == null) {
            srcMdFilter = summarySolutionModel.getSourceDimensionFilter();
            if (summarySolutionModel.getSourceDimensionValues() != null && summarySolutionModel.getSourceDimensionValues().size() > 0) {
                srcEntityDimValues.setValue(this.targetMdEntity.getDimensionName(), summarySolutionModel.getSourceDimensionValues());
            }
        }
        IEntityTable srcEntityTable = this.getEntityTable(sumContext, this.targetMdEntity.getId(), srcEntityDimValues, srcMdFilter, AuthorityType.None);
        return srcEntityTable;
    }

    private IEntityTable getTargetEntityTable(SumContext sumContext, SummarySolutionModel summarySolutionModel) {
        String targetMdFilter = summarySolutionModel.getTargetDimensionFilter();
        DimensionValueSet targetEntityDimValues = new DimensionValueSet();
        targetEntityDimValues.setValue("DATATIME", (Object)this.getPeriodWrapper().toString());
        List<String> targetDimensionValues = summarySolutionModel.getTargetDimensionValues();
        if (targetDimensionValues != null && targetDimensionValues.size() > 0) {
            targetEntityDimValues.setValue(this.targetMdEntity.getDimensionName(), targetDimensionValues);
        }
        IEntityTable targetEntityTable = this.getEntityTable(sumContext, this.targetMdEntity.getId(), targetEntityDimValues, targetMdFilter, AuthorityType.Read);
        return targetEntityTable;
    }

    public IEntityTable getEntityTable(SumContext sumContext, String entityId, DimensionValueSet masterKeys, String filter, AuthorityType authorityType) {
        try {
            SumBeanSet beanSet = sumContext.getBeanSet();
            IEntityQuery iEntityQuery = beanSet.entityDataService.newEntityQuery();
            iEntityQuery.setAuthorityOperations(authorityType);
            iEntityQuery.setMasterKeys(masterKeys);
            iEntityQuery.setEntityView(beanSet.entityViewRunTimeController.buildEntityView(entityId));
            iEntityQuery.setExpression(filter);
            ExecutorContext entityExecutorContext = new ExecutorContext(beanSet.dataDefinitionController);
            entityExecutorContext.setEnv(sumContext.getExeContext().getEnv());
            return iEntityQuery.executeFullBuild((IContext)entityExecutorContext);
        }
        catch (Exception e) {
            sumContext.getMonitor().exception(e);
            return null;
        }
    }

    public SummaryReportModel findReprotModel(SumBeanSet beanSet, String reportName) {
        SummaryReportModel report = this.reportsByName.get(reportName);
        if (report == null && beanSet != null) {
            List<SummaryReportModel> allReports = beanSet.summarySolutionService.getSummaryReportModelsBySolu(this.paramDefine.getSummarySolutionModel().getKey());
            allReports.stream().forEach(o -> this.reportsByName.put(o.getName(), (SummaryReportModel)o));
            report = this.reportsByName.get(reportName);
        }
        return report;
    }

    public FormulaCallBack getSpanFormulaCallBack(SumContext sumContext) throws ParseException {
        List<SummaryFormula> formulas = sumContext.getBeanSet().summaryFormulaService.getBJSummaryFormulasBySolution(this.paramDefine.getSummarySolutionModel().getKey());
        ArrayList<Formula> allFormulas = new ArrayList<Formula>();
        if (formulas != null) {
            for (SummaryFormula summaryFormula : formulas) {
                Formula f = new Formula();
                f.setId(summaryFormula.getKey());
                f.setFormula(summaryFormula.getExpression());
                f.setReportName("UNKNOWN");
                f.setFormKey("UNKNOWN");
                f.setOrder(OrderGenerator.newOrder());
                allFormulas.add(f);
            }
        }
        if (allFormulas.size() > 0) {
            List expressions = DataEngineFormulaParser.parseFormula((com.jiuqi.np.dataengine.executors.ExecutorContext)sumContext.getCalcExecutorContext(), allFormulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CALCULATE);
            FormulaCallBack spanFormulaCallBack = new FormulaCallBack();
            spanFormulaCallBack.getParsedExpressions().addAll(expressions);
            return spanFormulaCallBack;
        }
        return null;
    }

    public SumParam getParamDefine() {
        return this.paramDefine;
    }

    public PeriodWrapper getPeriodWrapper() {
        return this.paramDefine.getCurrPeriod();
    }

    public Map<String, List<String>> getMainDimMap() {
        return this.mainDimMap;
    }

    public DimensionValueSet getSolutionDimValues() {
        return this.solutionDimValues;
    }

    public String getSrcFormSchemeKey() {
        return this.srcFromScheme.getKey();
    }

    public IEntityDefine getTargetMdEntity() {
        return this.targetMdEntity;
    }

    public List<RuntimeSummaryReport> getReports() {
        return this.reports;
    }

    public FormulaCallBack getCalcFormulaCallBack() {
        return this.calcFormulaCallBack;
    }

    public void setCalcFormulaCallBack(FormulaCallBack calcFormulaCallBack) {
        this.calcFormulaCallBack = calcFormulaCallBack;
    }

    public IEntityDefine getSourceMdEntity() {
        return this.sourceMdEntity;
    }

    public boolean isSumBySolution() {
        return this.sumBySolution;
    }

    public IEntityAttribute getSourceRefTargetField() {
        return this.sourceRefTargetField;
    }

    public String toString() {
        return "RuntimeSummaryParam [paramDefine=" + this.paramDefine + ", reports=" + this.reports + ", mainDimMap=" + this.mainDimMap + ", solutionDimValues=" + this.solutionDimValues + ", srcFormSchemeKey=" + this.srcFromScheme.getKey() + ", targetMdEntity=" + this.targetMdEntity + ", calcFormulaCallBack=" + this.calcFormulaCallBack + "]";
    }
}

