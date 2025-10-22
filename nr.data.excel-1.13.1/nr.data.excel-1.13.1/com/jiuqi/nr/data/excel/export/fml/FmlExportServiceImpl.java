/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.ISheetNameProvider
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.office.excel2.label.ExcelLabel
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.logic.internal.util.FileFieldValueProcessor
 *  com.jiuqi.nr.datacrud.IQueryInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.impl.RegionRelationFactory
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.data.excel.export.fml;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.ISheetNameProvider;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.office.excel2.label.ExcelLabel;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.excel.config.ConfigProperties;
import com.jiuqi.nr.data.excel.export.fml.Fml;
import com.jiuqi.nr.data.excel.export.fml.FmlConditionMonitor;
import com.jiuqi.nr.data.excel.export.fml.FmlContext;
import com.jiuqi.nr.data.excel.export.fml.FmlDataModelLinkFinder;
import com.jiuqi.nr.data.excel.export.fml.FmlExecEnvironment;
import com.jiuqi.nr.data.excel.export.fml.FmlNode;
import com.jiuqi.nr.data.excel.export.fml.FmlPositionAdjustor;
import com.jiuqi.nr.data.excel.export.fml.IFmlExportService;
import com.jiuqi.nr.data.excel.export.fml.PreInterpretFml;
import com.jiuqi.nr.data.excel.export.fml.SheetNameProvider;
import com.jiuqi.nr.data.excel.export.grid.GridArea;
import com.jiuqi.nr.data.excel.export.grid.GridAreaInfo;
import com.jiuqi.nr.data.excel.obj.ExcelInfo;
import com.jiuqi.nr.data.excel.obj.ExportCache;
import com.jiuqi.nr.data.excel.obj.SheetInfo;
import com.jiuqi.nr.data.excel.param.BatchCSConditionMonitor;
import com.jiuqi.nr.data.excel.service.internal.IFormDataService;
import com.jiuqi.nr.data.logic.internal.util.FileFieldValueProcessor;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FmlExportServiceImpl
implements IFmlExportService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private RegionRelationFactory regionRelationFactory;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IFormDataService formDataService;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private ConfigProperties configProperties;
    private static final Logger logger = LoggerFactory.getLogger(FmlExportServiceImpl.class);
    private static final Pattern regex = Pattern.compile("[()\\[\\]&+-/%! \t]+");

    @Override
    public Map<String, String> getExcelFormulas(SheetInfo sheetInfo, ExportCache exportCache) {
        GridAreaInfo curSheetArea = sheetInfo.getCurSheetArea();
        ExcelInfo excelInfo = sheetInfo.getExcelInfo();
        if (excelInfo == null) {
            return Collections.emptyMap();
        }
        String formKey = sheetInfo.getFormKey();
        List<Fml> parsedFormulasByForm = exportCache.getParsedFormulasByForm(formKey);
        List<PreInterpretFml> filteredFormulas = this.filterFormulas(parsedFormulasByForm, sheetInfo, exportCache);
        if (CollectionUtils.isEmpty(filteredFormulas)) {
            return Collections.emptyMap();
        }
        FormDefine curForm = exportCache.getFormByKey(formKey);
        FmlContext fmlContext = new FmlContext(curSheetArea, sheetInfo, exportCache, this, this.runTimeViewController);
        FmlDataModelLinkFinder dataModelLinkFinder = new FmlDataModelLinkFinder(this.runTimeViewController, curForm.getKey(), null, fmlContext);
        QueryContext queryContext = this.getQueryContext(exportCache.getCurFormScheme(), curForm, (IDataModelLinkFinder)dataModelLinkFinder);
        SheetNameProvider sheetNameProvider = new SheetNameProvider(curForm.getFormCode(), sheetInfo.getSheetName(), excelInfo.getFormSheetInfo());
        HashMap<String, String> result = new HashMap<String, String>();
        for (PreInterpretFml fml : filteredFormulas) {
            if (!this.expFml(fml, sheetInfo, exportCache)) continue;
            IParsedExpression parsedExpression = fml.getExpression();
            IExpression realExpression = parsedExpression.getRealExpression();
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.EXCEL);
            formulaShowInfo.setSheetNameProvider((ISheetNameProvider)sheetNameProvider);
            dataModelLinkFinder.setFml(fml);
            boolean assignFix = fml.getAssignNode().getDataRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
            try {
                if (assignFix) {
                    formulaShowInfo.setAdjustors(Collections.singletonList(new FmlPositionAdjustor(fmlContext, fml)));
                    FmlExportServiceImpl.addExcelFml(sheetInfo, realExpression, queryContext, formulaShowInfo, result, excelInfo);
                    continue;
                }
                for (GridArea gridArea : curSheetArea.getGridAreaList()) {
                    if (!fml.getAssignNode().getRegionKey().equals(gridArea.getRegionKey())) continue;
                    for (int i = 0; i < gridArea.getRowCount(); ++i) {
                        int rowIndex = i + 1;
                        formulaShowInfo.setAdjustors(Collections.singletonList(new FmlPositionAdjustor(fml, rowIndex, gridArea, fmlContext)));
                        FmlExportServiceImpl.addExcelFml(sheetInfo, realExpression, queryContext, formulaShowInfo, result, excelInfo);
                    }
                }
            }
            catch (Exception e) {
                logger.debug("{}\u8f6cExcel\u516c\u5f0f\u5f02\u5e38:{}", parsedExpression.getSource().getFormula(), e.getMessage(), e);
            }
        }
        return result;
    }

    private static void addExcelFml(SheetInfo sheetInfo, IExpression realExpression, QueryContext queryContext, FormulaShowInfo formulaShowInfo, Map<String, String> result, ExcelInfo excelInfo) throws InterpretException {
        String formula;
        String assignedCell;
        String interpret = realExpression.interpret((IContext)queryContext, Language.EXCEL, (Object)formulaShowInfo);
        String[] splits = interpret.split("=");
        if (FmlExportServiceImpl.isIF(interpret)) {
            assignedCell = FmlExportServiceImpl.extractIFFmlAssignedCell(interpret);
            int assignedCellIndex1 = splits[0].lastIndexOf(assignedCell);
            int assignedCellIndex2 = splits[1].lastIndexOf(assignedCell);
            formula = splits[0].substring(0, assignedCellIndex1) + splits[1].substring(0, assignedCellIndex2) + splits[2];
        } else {
            assignedCell = splits[0].trim();
            formula = splits[1].trim();
        }
        String fmlCurSheetName = FmlExportServiceImpl.getFmlSheetNamePref(sheetInfo.getSheetName());
        if (assignedCell.contains(fmlCurSheetName)) {
            assignedCell = assignedCell.replace(fmlCurSheetName, "");
            result.put(assignedCell, formula);
        } else {
            fmlCurSheetName = FmlExportServiceImpl.getFmlSheetNamePref(excelInfo.getExcelName());
            if (assignedCell.contains(fmlCurSheetName)) {
                assignedCell = assignedCell.replace(fmlCurSheetName, "");
                formula = formula.replace(fmlCurSheetName, "");
                result.put(assignedCell, formula);
            }
        }
    }

    private static boolean isIF(String formula) {
        return formula != null && formula.startsWith("IF(") && formula.endsWith(")");
    }

    private static String extractIFFmlAssignedCell(String formula) {
        String falseCell;
        String content = formula.substring(3, formula.length() - 1).trim();
        String[] parts = content.split("\\s*,\\s*");
        if (parts.length != 3) {
            throw new IllegalArgumentException("IF\u51fd\u6570\u5e94\u5305\u542b\u4e09\u4e2a\u53c2\u6570");
        }
        String truePart = parts[1];
        String falsePart = parts[2];
        String trueCell = FmlExportServiceImpl.extractCellFromAssignment(truePart);
        if (!trueCell.equals(falseCell = FmlExportServiceImpl.extractCellFromAssignment(falsePart))) {
            throw new IllegalArgumentException("\u6761\u4ef6\u6210\u7acb\u548c\u4e0d\u6210\u7acb\u65f6\u8d4b\u503c\u7684\u5355\u5143\u683c\u4e0d\u540c");
        }
        return trueCell;
    }

    private static String extractCellFromAssignment(String assignment) {
        int equalsIndex = assignment.indexOf(61);
        if (equalsIndex == -1) {
            throw new IllegalArgumentException("\u53c2\u6570\u4e2d\u6ca1\u6709\u8d4b\u503c\u64cd\u4f5c: " + assignment);
        }
        return assignment.substring(0, equalsIndex).trim();
    }

    private List<PreInterpretFml> filterFormulas(List<Fml> parsedFormulasByForm, SheetInfo sheetInfo, ExportCache exportCache) {
        if (CollectionUtils.isEmpty(parsedFormulasByForm)) {
            return Collections.emptyList();
        }
        ArrayList<PreInterpretFml> preInterpretFmls = new ArrayList<PreInterpretFml>();
        HashMap conditionFmlMap = new HashMap();
        ArrayList preParseConditions = new ArrayList();
        parsedFormulasByForm.forEach(fml -> {
            IParsedExpression parsedExpression = this.formulaRunTimeController.getParsedExpression(sheetInfo.getExportOps().getFormulaSchemeKey(), fml.getExpressionKey());
            if (parsedExpression != null) {
                preInterpretFmls.add(new PreInterpretFml((Fml)fml, parsedExpression));
                List conditions = parsedExpression.getConditions();
                if (!CollectionUtils.isEmpty(conditions)) {
                    conditions.forEach(condition -> {
                        String conditionKey = condition.getKey();
                        if (conditionFmlMap.containsKey(conditionKey)) {
                            ((Set)conditionFmlMap.get(conditionKey)).add(fml.getExpressionKey());
                        } else {
                            preParseConditions.add(condition);
                            HashSet<String> fmlSet = new HashSet<String>();
                            fmlSet.add(fml.getExpressionKey());
                            conditionFmlMap.put(conditionKey, fmlSet);
                        }
                    });
                }
            }
        });
        if (CollectionUtils.isEmpty(preParseConditions)) {
            return preInterpretFmls;
        }
        FormulaCallBack formulaCallBack = new FormulaCallBack();
        FmlConditionMonitor fmlConditionMonitor = new FmlConditionMonitor();
        DimensionValueSet dimensionValueSet = sheetInfo.getDimensionCombination().toDimensionValueSet();
        formulaCallBack.getParsedExpressions().addAll(preParseConditions);
        IFormulaRunner formulaRunner = this.dataAccessProvider.newFormulaRunner(formulaCallBack);
        ExecutorContext executorContext = this.getExecutorContext(exportCache, dimensionValueSet);
        try {
            formulaRunner.prepareCheck(executorContext, null, null);
            formulaRunner.setMasterKeyValues(dimensionValueSet);
            formulaRunner.run((IMonitor)fmlConditionMonitor);
            Set<String> errorConditionKeys = fmlConditionMonitor.getErrorConditionKeys();
            if (!CollectionUtils.isEmpty(errorConditionKeys)) {
                HashSet removeFmlKeys = new HashSet();
                for (String errorConditionKey : errorConditionKeys) {
                    Set fmlKeys = (Set)conditionFmlMap.get(errorConditionKey);
                    if (CollectionUtils.isEmpty(fmlKeys)) continue;
                    removeFmlKeys.addAll(fmlKeys);
                }
                preInterpretFmls.removeIf(fml -> removeFmlKeys.contains(fml.getExpressionKey()));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return preInterpretFmls;
    }

    @NonNull
    private ExecutorContext getExecutorContext(ExportCache exportCache, DimensionValueSet dimensionValueSet) {
        String contextEntityId;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        List variables = DsContextHolder.getDsContext().getVariables();
        HashMap<String, Object> variableMap = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(variables)) {
            VariableManager variableManager = executorContext.getVariableManager();
            for (Variable variable : variables) {
                variableManager.add(variable);
                try {
                    variableMap.put(variable.getVarName(), variable.getVarValue(null));
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, exportCache.getCurFormScheme().getKey(), null, variableMap);
        FileFieldValueProcessor fileFieldValueProcessor = new FileFieldValueProcessor();
        environment.setFieldValueUpdateProcessor((IFieldValueUpdateProcessor)fileFieldValueProcessor);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (dimensionValueSet != null) {
            executorContext.setVarDimensionValueSet(dimensionValueSet);
        }
        if (StringUtils.isNotEmpty((String)(contextEntityId = DsContextHolder.getDsContext().getContextEntityId()))) {
            executorContext.setOrgEntityId(contextEntityId);
        }
        return executorContext;
    }

    private static String getFmlSheetNamePref(String sheetName) {
        if (!FmlExportServiceImpl.isValidSheetName(sheetName)) {
            return "'" + sheetName + "'!";
        }
        return sheetName + "!";
    }

    private static boolean isValidSheetName(String sheetName) {
        if (regex.matcher(sheetName).find()) {
            return false;
        }
        return !Character.isDigit(sheetName.charAt(0));
    }

    private boolean expFml(Fml fml, SheetInfo sheetInfo, ExportCache exportCache) {
        assert (sheetInfo.getExcelInfo() != null);
        List<String> expForms = sheetInfo.getExcelInfo().getFormKeys();
        boolean assignFix = fml.getAssignNode().getDataRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
        for (FmlNode node : fml.getNodes()) {
            boolean nodeFloat;
            if (!expForms.contains(node.getFormKey())) {
                return false;
            }
            boolean bl = nodeFloat = node.getDataRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST || node.getDataRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST;
            if (!assignFix || !nodeFloat) continue;
            RegionGradeInfo gradeInfo = sheetInfo.getRegionGradeInfo(node.getRegionKey(), this.regionRelationFactory);
            if (gradeInfo != null && gradeInfo.isGrade() && !CollectionUtils.isEmpty(gradeInfo.getGradeLinks())) {
                return false;
            }
            String nodeFormKey = node.getFormKey();
            GridAreaInfo gridAreaInfo = this.cacheGridAreaInfo(nodeFormKey, sheetInfo, exportCache);
            if (gridAreaInfo == null || gridAreaInfo.isSplitSheet()) {
                return false;
            }
            List<Integer> regionAreas = gridAreaInfo.getRegionAreaIndexMap().get(node.getRegionKey());
            if (CollectionUtils.isEmpty(regionAreas) || regionAreas.size() != 1) {
                return false;
            }
            GridArea gridArea = gridAreaInfo.getGridAreaList().get(regionAreas.get(0));
            if (gridArea.getRowSpan() == 1) continue;
            return false;
        }
        return true;
    }

    @Nullable
    GridAreaInfo cacheGridAreaInfo(String formKey, SheetInfo sheetInfo, ExportCache exportCache) {
        ExcelInfo excelInfo = sheetInfo.getExcelInfo();
        assert (excelInfo != null);
        Map<String, GridAreaInfo> formAreaInfo = excelInfo.getFormAreaInfo();
        if (formAreaInfo.containsKey(formKey)) {
            return formAreaInfo.get(formKey);
        }
        List<DataRegionDefine> sortedRegionsByForm = exportCache.getSortedRegionsByForm(formKey);
        if (CollectionUtils.isEmpty(sortedRegionsByForm)) {
            formAreaInfo.put(formKey, null);
            return null;
        }
        GridAreaInfo gridAreaInfo = new GridAreaInfo();
        gridAreaInfo.setFormKey(formKey);
        formAreaInfo.put(formKey, gridAreaInfo);
        SheetInfo cloneSheetInfo = this.getSheetInfo(formKey, sheetInfo);
        int moreRow = this.getLabelRowDeviation(cloneSheetInfo, exportCache);
        gridAreaInfo.setMoreRow(moreRow);
        int moreCol = 0;
        for (DataRegionDefine dataRegionDefine : sortedRegionsByForm) {
            int floatType;
            int rowSpan;
            boolean expTab;
            DataRegionKind regionKind = dataRegionDefine.getRegionKind();
            if (regionKind == DataRegionKind.DATA_REGION_SIMPLE) continue;
            RegionGradeInfo regionGradeInfo = excelInfo.getRegionGradeInfo(dataRegionDefine.getKey(), this.regionRelationFactory);
            List<RegionTabSettingDefine> tabsByRegion = this.getFilteredTabs(cloneSheetInfo, exportCache, dataRegionDefine);
            ArrayList<Integer> dataRowCountList = new ArrayList<Integer>();
            boolean bl = expTab = !CollectionUtils.isEmpty(tabsByRegion);
            if (!expTab) {
                IQueryInfo queryInfo = this.getQueryInfo(cloneSheetInfo, dataRegionDefine, null);
                int dataCount = this.dataQueryService.queryRegionDataCount(queryInfo, regionGradeInfo);
                dataRowCountList.add(dataCount);
            } else {
                for (RegionTabSettingDefine tab : tabsByRegion) {
                    ArrayList<RowFilter> tabFilters = new ArrayList<RowFilter>(1);
                    if (StringUtils.isNotEmpty((String)tab.getFilterCondition())) {
                        FormulaFilter formulaFilter = new FormulaFilter(tab.getFilterCondition());
                        tabFilters.add((RowFilter)formulaFilter);
                    }
                    IQueryInfo queryInfo = this.getQueryInfo(cloneSheetInfo, dataRegionDefine, tabFilters);
                    int dataCount = this.dataQueryService.queryRegionDataCount(queryInfo, regionGradeInfo);
                    dataRowCountList.add(dataCount);
                }
            }
            if (regionKind == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                int regionRight = dataRegionDefine.getRegionRight();
                int regionLeft = dataRegionDefine.getRegionLeft();
                rowSpan = regionRight - regionLeft + 1;
                floatType = 1;
            } else {
                int regionTop = dataRegionDefine.getRegionTop();
                int regionBottom = dataRegionDefine.getRegionBottom();
                rowSpan = regionBottom - regionTop + 1;
                floatType = 0;
            }
            int regionSplitCount = Math.max(tabsByRegion.size(), 1);
            for (int i = 0; i < regionSplitCount; ++i) {
                int right;
                int bottom;
                Integer rowCount;
                if (expTab) {
                    ++moreRow;
                }
                if ((rowCount = (Integer)dataRowCountList.get(i)) > this.getSheetFloatMax()) {
                    gridAreaInfo.setSplitSheet(true);
                    rowCount = this.getSheetFloatMax();
                }
                GridArea gridArea = new GridArea();
                gridArea.setRegionKey(dataRegionDefine.getKey());
                gridArea.setRowCount(rowCount);
                gridArea.setRowSpan(rowSpan);
                gridArea.setFloatType(floatType);
                gridArea.setOriginalRegion(dataRegionDefine.getRegionLeft(), dataRegionDefine.getRegionTop(), dataRegionDefine.getRegionRight(), dataRegionDefine.getRegionBottom());
                int rowAppend = Math.max(rowCount - 1, 0) * rowSpan;
                int top = dataRegionDefine.getRegionTop() + moreRow;
                int left = dataRegionDefine.getRegionLeft() + moreCol;
                if (regionKind == DataRegionKind.DATA_REGION_COLUMN_LIST) {
                    bottom = dataRegionDefine.getRegionBottom() + moreRow;
                    right = dataRegionDefine.getRegionRight() + moreCol + rowAppend;
                    moreCol += rowAppend;
                } else {
                    bottom = dataRegionDefine.getRegionBottom() + moreRow + rowAppend;
                    right = dataRegionDefine.getRegionRight() + moreCol;
                    moreRow += rowAppend;
                }
                gridArea.setRealRegion(left, top, right, bottom);
                gridAreaInfo.addGridArea(gridArea);
            }
        }
        return gridAreaInfo;
    }

    private int getSheetFloatMax() {
        if (this.configProperties == null) {
            return 100000;
        }
        return this.configProperties.getExpSheetFloatMax();
    }

    @NotNull
    private List<RegionTabSettingDefine> getFilteredTabs(SheetInfo sheetInfo, ExportCache exportCache, DataRegionDefine dataRegionDefine) {
        Map<String, List<String>> tabs = sheetInfo.getExportOps().getTabs();
        List<String> tabTitles = tabs.get(dataRegionDefine.getKey());
        List<RegionTabSettingDefine> tabsByRegionCopy = FmlExportServiceImpl.getSelectRegionTabs(dataRegionDefine, exportCache, tabTitles);
        ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>(tabsByRegionCopy);
        if (!tabsByRegion.isEmpty()) {
            DimensionValueSet dimensionValueSet = sheetInfo.getDimensionCombination().toDimensionValueSet();
            FormDefine queryFormById = this.runTimeViewController.queryFormById(sheetInfo.getFormKey());
            FormulaCallBack callback = new FormulaCallBack();
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = exportCache.getFmlExecEnv(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController);
            FileFieldValueProcessor fileFieldValueProcessor = new FileFieldValueProcessor();
            environment.setFieldValueUpdateProcessor((IFieldValueUpdateProcessor)fileFieldValueProcessor);
            context.setEnv((IFmlExecEnvironment)environment);
            context.setDefaultGroupName(queryFormById.getFormCode());
            for (RegionTabSettingDefine regionTabSettingDefine : tabsByRegion) {
                if (!StringUtils.isNotEmpty((String)regionTabSettingDefine.getDisplayCondition())) continue;
                String styleExpression = regionTabSettingDefine.getDisplayCondition();
                Formula formula = new Formula();
                formula.setId(regionTabSettingDefine.getId());
                formula.setFormula(styleExpression);
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formula.setFormKey(queryFormById.getKey());
                formula.setReportName(queryFormById.getFormCode());
                callback.getFormulas().add(formula);
            }
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callback);
            BatchCSConditionMonitor monitor = new BatchCSConditionMonitor();
            try {
                runner.prepareCheck(context, dimensionValueSet, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                Map<String, Set<DimensionValueSet>> result = monitor.getResult();
                Iterator iterator = tabsByRegion.iterator();
                while (iterator.hasNext()) {
                    RegionTabSettingDefine regionTabSettingDefine = (RegionTabSettingDefine)iterator.next();
                    if (!result.containsKey(regionTabSettingDefine.getId())) continue;
                    iterator.remove();
                }
            }
            catch (Exception e) {
                sheetInfo.stopWrite();
                logger.error("\u89e3\u6790\u516c\u5f0f\u5931\u8d25\uff1a{},{}", (Object)e.getMessage(), (Object)e);
            }
        }
        return tabsByRegion;
    }

    @Nullable
    private QueryContext getQueryContext(FormSchemeDefine curFormScheme, FormDefine curFormDefine, IDataModelLinkFinder dataModelLinkFinder) {
        FmlExecEnvironment environment = new FmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, curFormScheme.getKey());
        environment.setDataModelLinkFinder(dataModelLinkFinder);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setDefaultGroupName(curFormDefine.getFormCode());
        QueryContext queryContext = null;
        try {
            queryContext = new QueryContext(executorContext, null);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
        }
        return queryContext;
    }

    private IQueryInfo getQueryInfo(SheetInfo sheetInfo, DataRegionDefine dataRegionDefine, List<RowFilter> otherRowFilters) {
        Map<String, List<RowFilter>> rowFilter = sheetInfo.getExportOps().getRowFilter();
        QueryInfoBuilder queryInfoBuilder = new QueryInfoBuilder(dataRegionDefine.getKey(), sheetInfo.getDimensionCombination()).whereRegionFilter();
        queryInfoBuilder.setDesensitized(true);
        if (rowFilter.containsKey(dataRegionDefine.getKey())) {
            rowFilter.get(dataRegionDefine.getKey()).forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        if (!CollectionUtils.isEmpty(otherRowFilters)) {
            otherRowFilters.forEach(arg_0 -> ((QueryInfoBuilder)queryInfoBuilder).where(arg_0));
        }
        queryInfoBuilder.setFormulaSchemeKey(sheetInfo.getExportOps().getFormulaSchemeKey());
        return queryInfoBuilder.build();
    }

    private int getLabelRowDeviation(SheetInfo sheetInfo, ExportCache exportCache) {
        int labelRowDeviation = 0;
        Grid2Data formStyle = exportCache.getFormStyle(sheetInfo.getFormKey());
        List<ExcelLabel> excelLabels = this.formDataService.handleLabel(sheetInfo, formStyle, exportCache);
        if (CollectionUtils.isEmpty(excelLabels)) {
            return labelRowDeviation;
        }
        for (ExcelLabel excelLabel : excelLabels) {
            int rowTo;
            if (!excelLabel.isUpper() || (rowTo = excelLabel.getRowIndex() + excelLabel.getRowSpan()) <= labelRowDeviation) continue;
            labelRowDeviation = rowTo;
        }
        return labelRowDeviation;
    }

    private SheetInfo getSheetInfo(String formKey, SheetInfo sheetInfo) {
        SheetInfo result = new SheetInfo();
        result.setFormKey(formKey);
        result.setSheetName(sheetInfo.getSheetName());
        result.setOriginalSheetName(sheetInfo.getOriginalSheetName());
        result.setDimensionCollection(sheetInfo.getDimensionCollection());
        result.setDimensionCombination(sheetInfo.getDimensionCombination());
        result.setExportOps(sheetInfo.getExportOps());
        result.setExcelInfo(sheetInfo.getExcelInfo());
        result.setFormSchemeKey(sheetInfo.getFormSchemeKey());
        result.setTaskKey(sheetInfo.getTaskKey());
        result.setExcelPrintSetup(sheetInfo.getExcelPrintSetup());
        result.setFilters(sheetInfo.getFilters());
        result.setLinks(sheetInfo.getLinks());
        result.setStartAndEndList(sheetInfo.getStartAndEndList());
        result.setCustomCellStyles(sheetInfo.getCustomCellStyles());
        return result;
    }

    private static List<RegionTabSettingDefine> getSelectRegionTabs(DataRegionDefine dataRegionDefine, ExportCache exportCache, List<String> tabTitles) {
        List<RegionTabSettingDefine> allRegionTabs;
        ArrayList<RegionTabSettingDefine> tabsByRegion = new ArrayList<RegionTabSettingDefine>();
        if (!CollectionUtils.isEmpty(tabTitles) && !CollectionUtils.isEmpty(allRegionTabs = exportCache.getTabsByRegion(dataRegionDefine.getKey()))) {
            Map collect = allRegionTabs.stream().collect(Collectors.toMap(RegionTabSettingDefine::getTitle, Function.identity(), (o1, o2) -> o1));
            for (String tabTitle : tabTitles) {
                if (!collect.containsKey(tabTitle)) continue;
                tabsByRegion.add((RegionTabSettingDefine)collect.get(tabTitle));
            }
        }
        return tabsByRegion;
    }
}

