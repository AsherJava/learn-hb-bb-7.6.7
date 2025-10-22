/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.collector.FieldExecInfo
 *  com.jiuqi.np.dataengine.collector.FmlExecuteCollectConfig
 *  com.jiuqi.np.dataengine.collector.FmlExecuteCollector
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.DetailCollectMonitor
 *  com.jiuqi.np.dataengine.log.LogRow
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CalculateParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICalculateService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeFormulaController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.collector.FieldExecInfo;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollectConfig;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.DetailCollectMonitor;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CalculateParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICalculateService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.monitor.LogicProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.FmlDebugNode;
import com.jiuqi.nr.dataentry.paramInfo.FmlMonitorAndDebugParam;
import com.jiuqi.nr.dataentry.service.IFmlMonitorAndDebugService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeFormulaController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FmlMonitorAndDebugServiceImpl
implements IFmlMonitorAndDebugService {
    public static String FmlMonitoringName = "ASYNCTASK_FMLMONITOR";
    private static final Logger logger = LoggerFactory.getLogger(FmlMonitorAndDebugServiceImpl.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Resource
    private IRunTimeFormulaController runTimeFormulaController;
    @Resource
    private IFormulaRunTimeController formulaRuntimeController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private ICalculateService calculateService;
    @Autowired
    private ICheckService checkService;
    private String GlOBALMD = "GlOBAL_MARKDOWN_FILE";
    private String EXECUTEPROGRAM = "EXECUTE_PROGRAM_TXT_FILE";
    private String SQLCSV = "SQL_CSV_FILE";
    private String ERRORCSV = "ERROR_CSV_FILE";

    /*
     * WARNING - void declaration
     */
    @Override
    public void runFmlMonitor(FmlMonitorAndDebugParam fmlMonitorAndDebugParam, AsyncTaskMonitor asyncTaskMonitor) {
        String[] dimsTitleList;
        QueryContext queryContext;
        FmlExecuteCollectConfigObj config = new FmlExecuteCollectConfigObj();
        config.setFocusZbExps(fmlMonitorAndDebugParam.getZbList());
        FmlExecuteCollector fmlExecuteCollector = new FmlExecuteCollector((FmlExecuteCollectConfig)config);
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.createExecutorContext(fmlMonitorAndDebugParam.getContext().getFormSchemeKey());
        FmlDebugMonitor fmlDebugMonitor = new FmlDebugMonitor();
        try {
            queryContext = new QueryContext(executorContext, null);
            fmlExecuteCollector.init(queryContext);
            fmlDebugMonitor.setCollector(fmlExecuteCollector);
            FmlExeMonitor fmlExeMonitor = new FmlExeMonitor(fmlDebugMonitor, asyncTaskMonitor, 0.0, "", 0.9);
            Mode form_formula_mode = Mode.FORM;
            ArrayList<String> rangeKeys = new ArrayList<String>();
            Map<String, List<String>> formulas = fmlMonitorAndDebugParam.getForm_formula();
            if (!formulas.isEmpty()) {
                Set<Map.Entry<String, List<String>>> entrySet = formulas.entrySet();
                Map.Entry<String, List<String>> firstEntry = entrySet.iterator().next();
                if (firstEntry.getValue() == null || firstEntry.getValue().isEmpty()) {
                    rangeKeys.addAll(formulas.keySet());
                } else {
                    form_formula_mode = Mode.FORMULA;
                    for (List<String> value : formulas.values()) {
                        rangeKeys.addAll(value);
                    }
                }
            }
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(fmlMonitorAndDebugParam.getContext().getDimensionSet(), fmlMonitorAndDebugParam.getContext().getFormSchemeKey());
            if (fmlMonitorAndDebugParam.getFmlType().equals("check")) {
                CheckParam checkParam = new CheckParam();
                checkParam.setMode(form_formula_mode);
                checkParam.setRangeKeys(rangeKeys);
                checkParam.setVariableMap(new HashMap());
                checkParam.setDimensionCollection(dimensionCollection);
                checkParam.setFormulaSchemeKey(fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey());
                checkParam.setActionId(asyncTaskMonitor.getTaskId());
                this.checkService.batchCheck(checkParam, (IFmlMonitor)fmlExeMonitor);
            } else {
                CalculateParam calculateParam = new CalculateParam();
                calculateParam.setMode(form_formula_mode);
                calculateParam.setRangeKeys(rangeKeys);
                calculateParam.setVariableMap(new HashMap());
                calculateParam.setDimensionCollection(dimensionCollection);
                calculateParam.setFormulaSchemeKey(fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey());
                this.calculateService.batchCalculate(calculateParam, (IFmlMonitor)fmlExeMonitor);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        StringBuilder globalMDString = new StringBuilder();
        globalMDString.append("# \u516c\u5f0f\u76d1\u63a7\u62a5\u544a\n\n");
        globalMDString.append("## 1\u3001\u6267\u884c\u8ba1\u5212\u6982\u8ff0\n");
        globalMDString.append("* \u6267\u884c\u4fe1\u606f\n");
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(fmlMonitorAndDebugParam.getContext().getTaskKey());
        globalMDString.append("  * \u4efb\u52a1\uff1a" + taskDefine.getTitle() + "\n");
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(fmlMonitorAndDebugParam.getContext().getDimensionSet(), fmlMonitorAndDebugParam.getContext().getFormSchemeKey());
        DimensionCombination dimensionCombination = (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0);
        for (String dimTitle : dimsTitleList = this.getDimsTitle(fmlMonitorAndDebugParam.getContext().getTaskKey(), dimensionCombination).split("\uff1b")) {
            if (dimTitle.trim().isEmpty()) continue;
            globalMDString.append("  * " + dimTitle.trim() + "\n");
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRuntimeController.queryFormulaSchemeDefine(fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey());
        globalMDString.append("  * \u516c\u5f0f\u65b9\u6848\uff1a" + formulaSchemeDefine.getTitle() + "\n");
        if (fmlMonitorAndDebugParam.getFmlType().equals("check")) {
            globalMDString.append("  * \u76d1\u63a7\u7c7b\u578b\uff1a\u5ba1\u6838\n");
        } else {
            globalMDString.append("  * \u76d1\u63a7\u7c7b\u578b\uff1a\u8fd0\u7b97\n");
        }
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formatDate = dateFormat.format(date);
        String[] dateStrings = formatDate.split("T");
        String[] ymd = dateStrings[0].split("-");
        globalMDString.append("  * \u76d1\u63a7\u65f6\u95f4\uff1a" + ymd[0] + "\u5e74" + ymd[1] + "\u6708" + ymd[2] + "\u65e5" + dateStrings[1] + "\n");
        globalMDString.append("* \u6267\u884c\u7684\u516c\u5f0f\u6570\uff1a" + fmlExecuteCollector.getGlobalInfo().getFormulaCount() + "\n");
        globalMDString.append("* \u6267\u884c\u57df\u7684\u4e2a\u6570\uff1a" + fmlExecuteCollector.getGlobalInfo().getExecRegionCount() + "\n");
        globalMDString.append("* \u5305\u542b\u7684\u9884\u53d6\u6570\u516c\u5f0f\n");
        List advanceExpList = fmlExecuteCollector.getGlobalInfo().getAdvanceExpressions();
        int advanceIndex = 1;
        for (IParsedExpression iParsedExpression : advanceExpList) {
            if (advanceIndex > 10) break;
            globalMDString.append("  * ").append(iParsedExpression.getSource().getFormula()).append("\n");
            ++advanceIndex;
        }
        if (advanceExpList.size() == 0) {
            globalMDString.append("  * \u65e0 \n");
        }
        if (advanceExpList.size() > 10) {
            globalMDString.append("  * \u66f4\u591a\u9884\u53d6\u6570\u516c\u5f0f\u8bf7\u67e5\u770b\u9644\u4ef6\uff1a\u6267\u884c\u8ba1\u5212 \n");
        }
        globalMDString.append("\n*\u8be6\u7ec6\u7684\u516c\u5f0f\u5185\u5bb9\u548c\u6267\u884c\u57df\u67e5\u770b\u9644\u4ef6\uff1a\u6267\u884c\u8ba1\u5212*\n\n");
        globalMDString.append("***\n\n\n## 2\u3001\u8fd0\u884c\u8fc7\u7a0b\u7edf\u8ba1\n");
        globalMDString.append("* \u516c\u5f0f\u6267\u884c\u603b\u8017\u65f6\uff1a").append(fmlExecuteCollector.getGlobalInfo().getTotalCost() + "\u6beb\u79d2").append("\n");
        List formulaCost = fmlExecuteCollector.getNodeCostCollector().getExpCostTopN(10);
        globalMDString.append("* \u516c\u5f0f\u8017\u65f6TOP10\n");
        globalMDString.append("  |\u5e8f\u53f7|\u516c\u5f0f\u7f16\u53f7|\u516c\u5f0f|\u62a5\u8868|\u603b\u8017\u65f6\uff08\u6beb\u79d2\uff09|\u6267\u884c\u6b21\u6570|\u5e73\u5747\u8017\u65f6\uff08\u6beb\u79d2\uff09|\n  |------|------|------|------|------|------|------|\n");
        int formulaCostIndex = 1;
        FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        for (CostCalculator calculator : formulaCost) {
            Object formulaText = "";
            String formula_form = "";
            String formulaCode = "";
            try {
                IParsedExpression parsedExpression = this.formulaRuntimeController.getParsedExpression(fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey(), calculator.getType());
                formula_form = parsedExpression.getSource().getReportName();
                formulaCode = parsedExpression.getSource().getCode();
                formulaText = parsedExpression.getFormula(queryContext, jqStyleShowInfo);
            }
            catch (InterpretException e) {
                throw new RuntimeException(e);
            }
            long avgCost = calculator.getAvgCost() > 1L ? calculator.getAvgCost() : 0L;
            globalMDString.append("  |" + formulaCostIndex + "|" + formulaCode + "|" + (String)formulaText + "|" + formula_form + "|" + calculator.getTotalCost() + "|" + calculator.getTimes() + "|" + avgCost + "|\n");
            ++formulaCostIndex;
        }
        List funcCost = fmlExecuteCollector.getNodeCostCollector().getFuncCostTopN(10);
        globalMDString.append("* \u51fd\u6570\u8017\u65f6TOP10\n");
        globalMDString.append("  |\u5e8f\u53f7|\u51fd\u6570\u7c7b\u578b|\u603b\u8017\u65f6\uff08\u6beb\u79d2\uff09|\u6267\u884c\u6b21\u6570|\u5e73\u5747\u8017\u65f6\uff08\u6beb\u79d2\uff09|\n  |------|------|------|------|------|\n");
        int funcCostIndex = 1;
        for (CostCalculator calculator : funcCost) {
            long avgCost = calculator.getAvgCost() > 1L ? calculator.getAvgCost() : 0L;
            globalMDString.append("  |" + funcCostIndex + "|" + calculator.getType() + "|" + calculator.getTotalCost() + "|" + calculator.getTimes() + "|" + avgCost + "|\n");
            ++funcCostIndex;
        }
        globalMDString.append("* sql\u6267\u884c\u8017\u65f6TOP10\n");
        List logRows = fmlExecuteCollector.getLogRowCollector().getLogRows();
        Collections.sort(logRows, new Comparator<LogRow>(){

            @Override
            public int compare(LogRow o1, LogRow o2) {
                if (o1.getExeCost() > o2.getExeCost()) {
                    return -1;
                }
                if (o1.getExeCost() < o2.getExeCost()) {
                    return 1;
                }
                return 0;
            }
        });
        globalMDString.append("  |\u5e8f\u53f7|\u6267\u884c\u8017\u65f6\uff08\u6beb\u79d2\uff09|\u64cd\u4f5c\u8bb0\u5f55|sql\u6587\u672c|\n  |------|------|------|------|\n");
        int showNum = 1;
        for (LogRow logRow : logRows) {
            if (showNum > 10) break;
            globalMDString.append("  |" + showNum + "|" + logRow.getExeCost() + "|" + logRow.getRowCount() + "\u884c\uff0c" + logRow.getColCount() + "\u5217|" + logRow.getSql() + "|\n");
            ++showNum;
        }
        globalMDString.append("\n*\u66f4\u591asql\u67e5\u770bcsv\u9644\u4ef6*\n");
        globalMDString.append("* \u64cd\u4f5c\u7684\u8bb0\u5f55\u6570\uff08\u884c\u6570\uff09\n");
        globalMDString.append("  * \u67e5\u8be2\uff1a" + fmlExecuteCollector.getGlobalInfo().getQueryRecordCount() + "\n");
        globalMDString.append("  * \u66f4\u65b0\uff1a" + fmlExecuteCollector.getGlobalInfo().getUpdateRecordCount() + "\n");
        globalMDString.append("  * \u63d2\u5165\uff1a" + fmlExecuteCollector.getGlobalInfo().getInsertRecordCount() + "\n");
        LinkedHashMap recordTop = fmlExecuteCollector.getLogRowCollector().getTableSizeTopN(10);
        ArrayList<String> recordTableNames = new ArrayList<String>();
        for (String string : recordTop.keySet()) {
            recordTableNames.add(string + "@@@" + recordTop.get(string));
        }
        Collections.sort(recordTableNames, new Comparator<String>(){

            @Override
            public int compare(String o1, String o2) {
                int o1Nums = Integer.parseInt(o1.split("@@@")[1]);
                int o2Nums = Integer.parseInt(o2.split("@@@")[1]);
                return o2Nums - o1Nums;
            }
        });
        globalMDString.append("* \u6570\u636e\u8868\u67e5\u8be2\u8bb0\u5f55\u6570TOP10\n");
        for (String string : recordTableNames) {
            String tableName = string.split("@@@")[0];
            int recordNum = Integer.parseInt(string.split("@@@")[1]);
            globalMDString.append("  * \u8868\u540d\uff1a" + tableName + "\uff0c\u8bb0\u5f55\u6570\uff1a" + recordNum + "\n");
        }
        globalMDString.append("***\n\n## 3\u3001\u5173\u6ce8\u6307\u6807\n\n");
        if (fmlExecuteCollector.getFocusInfoCollector() != null) {
            List fieldExecInfos = fmlExecuteCollector.getFocusInfoCollector().getFieldExecInfos();
            for (Object fieldExecInfo : fieldExecInfos) {
                globalMDString.append("* \u6307\u6807\uff1a[" + fieldExecInfo.getDataNode().getQueryField().getTableName() + "]" + fieldExecInfo.getDataNode().getQueryField().getFieldCode() + "\n");
                globalMDString.append("  * \u6d89\u53ca\u516c\u5f0f\n");
                for (Object parsedExpression : fieldExecInfo.getLinkedExpressions()) {
                    try {
                        globalMDString.append("    * [" + parsedExpression.getSource().getReportName() + "]" + parsedExpression.getSource().getCode() + "\uff1a" + parsedExpression.getFormula(queryContext, jqStyleShowInfo) + "\n");
                    }
                    catch (InterpretException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (fieldExecInfo.getLinkedExpressions().size() == 0) {
                    globalMDString.append("    * \u65e0 \n");
                }
                globalMDString.append("  * \u6d89\u53casql\n");
                for (LogRow logRow : fieldExecInfo.getSqlLogs()) {
                    globalMDString.append("    * " + logRow.getSql() + "\n");
                }
                if (fieldExecInfo.getSqlLogs().size() != 0) continue;
                globalMDString.append("    * \u65e0 \n");
            }
        }
        globalMDString.append("\n***\n\n## 4\u3001\u6267\u884c\u5f02\u5e38\n");
        globalMDString.append("### 1\uff09\u6267\u884c\u9519\u8bef\n");
        int errorSize = fmlExecuteCollector.getErrorMessages().size();
        globalMDString.append("* \u6267\u884c\u9519\u8bef\u7684\u516c\u5f0f\u603b\u6570\uff1a" + errorSize + "\n");
        if (errorSize > 0) {
            globalMDString.append("* \u9519\u8bef\u4fe1\u606f\n");
            boolean bl = true;
            for (String string : fmlExecuteCollector.getErrorMessages()) {
                void var32_53;
                if (var32_53 > 10) break;
                globalMDString.append("  * " + string + "\n");
                ++var32_53;
            }
            if (errorSize > 10) {
                globalMDString.append(" *\u66f4\u591a\u6267\u884c\u9519\u8bef\u67e5\u770bcsv\u9644\u4ef6*\n\n\n\n");
            }
        }
        globalMDString.append("### 2\uff09\u6267\u884c\u8b66\u544a\n");
        globalMDString.append("* **\u91cd\u590d\u8d4b\u503c\u7684\u6307\u6807**\n");
        List list = fmlExecuteCollector.getWanningCollector().getDuplicateAssignNodes();
        for (FieldExecInfo fieldExecInfo : list) {
            globalMDString.append("  * [" + fieldExecInfo.getDataNode().getQueryField().getTableName() + "]" + fieldExecInfo.getDataNode().getQueryField().getFieldCode() + "\n");
        }
        if (list.size() == 0) {
            globalMDString.append("  * \u65e0 \n");
        }
        globalMDString.append("* **\u53d7\u5faa\u73af\u4f9d\u8d56\u7684\u5f71\u54cd\uff0c\u9700\u8981\u9010\u6761\u6267\u884c\u7684\u516c\u5f0f**\n");
        List oneByOneExpressions = fmlExecuteCollector.getWanningCollector().getOneByOneExpressions();
        try {
            for (Object parsedExpression : oneByOneExpressions) {
                globalMDString.append("  * [" + parsedExpression.getSource().getReportName() + "]" + parsedExpression.getSource().getCode() + "\uff1a" + parsedExpression.getFormula(queryContext, jqStyleShowInfo) + "\n");
            }
        }
        catch (InterpretException e) {
            throw new RuntimeException(e);
        }
        if (oneByOneExpressions.size() == 0) {
            globalMDString.append("  * \u65e0 \n");
        }
        globalMDString.append("* **\u5b58\u5728\u5faa\u73af\u4f9d\u8d56\u7684\u516c\u5f0f**\n");
        List cycleExpressions = fmlExecuteCollector.getWanningCollector().getCycleExpressions();
        try {
            for (CalcExpression calcExpression : cycleExpressions) {
                globalMDString.append("  * [" + calcExpression.getSource().getReportName() + "]" + calcExpression.getSource().getCode() + "\uff1a" + calcExpression.getFormula(queryContext, jqStyleShowInfo) + "\n");
            }
        }
        catch (InterpretException e) {
            throw new RuntimeException(e);
        }
        if (cycleExpressions.size() == 0) {
            globalMDString.append("  * \u65e0 \n");
        }
        globalMDString.append("\n***");
        result.put(this.GlOBALMD, globalMDString.toString());
        result.put(this.EXECUTEPROGRAM, fmlDebugMonitor.getFmlExecuteProgram());
        result.put(this.SQLCSV, fmlExecuteCollector.getLogRowCollector().getLogRows());
        result.put(this.ERRORCSV, fmlExecuteCollector.getErrorMessages());
        asyncTaskMonitor.finish("success", (Object)JsonUtil.objectToJson(result));
    }

    @Override
    public String previewFmlMonitorResult(String asyncTaskId) {
        String jsonString = (String)this.asyncTaskManager.queryDetail(asyncTaskId);
        HashMap result = new HashMap();
        try {
            result = (HashMap)JsonUtil.toObject((String)jsonString, HashMap.class);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String previewMessage = (String)result.get(this.GlOBALMD);
        return previewMessage;
    }

    @Override
    public List<ExportData> exportFmlMonitorResult(String asyncTaskId) {
        ArrayList<ExportData> exportDataList = new ArrayList<ExportData>();
        String jsonString = (String)this.asyncTaskManager.queryDetail(asyncTaskId);
        HashMap result = new HashMap();
        try {
            result = (HashMap)JsonUtil.toObject((String)jsonString, HashMap.class);
            String previewMessage = (String)result.get(this.GlOBALMD);
            ByteArrayOutputStream globalMD_os = new ByteArrayOutputStream(0xA00000);
            globalMD_os.write(previewMessage.getBytes(StandardCharsets.UTF_8));
            byte[] globalMD_byteArray = globalMD_os.toByteArray();
            ExportData globalMD = new ExportData("\u516c\u5f0f\u76d1\u63a7\u62a5\u544a.md", globalMD_byteArray);
            exportDataList.add(globalMD);
            List exeProgramMessage = (List)result.get(this.EXECUTEPROGRAM);
            StringBuilder programBuilder = new StringBuilder();
            for (String string : exeProgramMessage) {
                programBuilder.append(string).append("\n");
            }
            ByteArrayOutputStream exeProgram_os = new ByteArrayOutputStream(0xA00000);
            exeProgram_os.write(programBuilder.toString().getBytes(StandardCharsets.UTF_8));
            byte[] exeProgram_byteArray = exeProgram_os.toByteArray();
            ExportData exeProgram = new ExportData("\u6267\u884c\u8ba1\u5212.txt", exeProgram_byteArray);
            exportDataList.add(exeProgram);
            List logRows = (List)result.get(this.SQLCSV);
            XSSFWorkbook sql_workbook = new XSSFWorkbook();
            XSSFSheet sql_sheet = sql_workbook.createSheet("SQL\u7edf\u8ba1");
            sql_sheet.setColumnWidth(0, 2048);
            sql_sheet.setColumnWidth(1, 3072);
            sql_sheet.setColumnWidth(2, 3840);
            sql_sheet.setColumnWidth(3, 3072);
            sql_sheet.setColumnWidth(4, 3072);
            sql_sheet.setColumnWidth(5, 3072);
            sql_sheet.setColumnWidth(6, 3072);
            sql_sheet.setColumnWidth(7, 25600);
            XSSFRow sql_row0 = sql_sheet.createRow(0);
            sql_row0.createCell(0).setCellValue("\u5e8f\u53f7");
            sql_row0.createCell(1).setCellValue("\u64cd\u4f5c\u7c7b\u578b");
            sql_row0.createCell(2).setCellValue("\u7269\u7406\u8868\u540d");
            sql_row0.createCell(3).setCellValue("\u6267\u884c\u8017\u65f6\uff08\u6beb\u79d2\uff09");
            sql_row0.createCell(4).setCellValue("\u603b\u8017\u65f6\uff08\u6beb\u79d2\uff09");
            sql_row0.createCell(5).setCellValue("\u64cd\u4f5c\u8bb0\u5f55\u884c\u6570");
            sql_row0.createCell(6).setCellValue("\u64cd\u4f5c\u8bb0\u5f55\u5217\u6570");
            sql_row0.createCell(7).setCellValue("SQL\u6587\u672c");
            int sql_index = 1;
            for (Map logRow : logRows) {
                XSSFRow rowOfIndex = sql_sheet.createRow(sql_index);
                rowOfIndex.createCell(0).setCellValue(sql_index);
                rowOfIndex.createCell(1).setCellValue(logRow.get("type").toString());
                rowOfIndex.createCell(2).setCellValue(logRow.get("tableName").toString());
                rowOfIndex.createCell(3).setCellValue(logRow.get("exeCost").toString());
                rowOfIndex.createCell(4).setCellValue(logRow.get("totalCost").toString());
                rowOfIndex.createCell(5).setCellValue(logRow.get("rowCount").toString());
                rowOfIndex.createCell(6).setCellValue(logRow.get("colCount").toString());
                rowOfIndex.createCell(7).setCellValue(logRow.get("sql").toString());
                ++sql_index;
            }
            ByteArrayOutputStream sql_os = new ByteArrayOutputStream(0xA00000);
            sql_workbook.write(sql_os);
            byte[] sql_byteArray = sql_os.toByteArray();
            ExportData sqlCsv = new ExportData("SQL\u7edf\u8ba1.csv", sql_byteArray);
            exportDataList.add(sqlCsv);
            List errorMessage = (List)result.get(this.ERRORCSV);
            XSSFWorkbook err_workbook = new XSSFWorkbook();
            XSSFSheet err_sheet = err_workbook.createSheet("\u9519\u8bef\u4fe1\u606f");
            err_sheet.setColumnWidth(0, 2048);
            err_sheet.setColumnWidth(1, 19200);
            XSSFRow err_row0 = err_sheet.createRow(0);
            err_row0.createCell(0).setCellValue("\u5e8f\u53f7");
            err_row0.createCell(1).setCellValue("\u9519\u8bef\u4fe1\u606f");
            int err_index = 1;
            for (String string : errorMessage) {
                XSSFRow rowOfIndex = err_sheet.createRow(err_index);
                rowOfIndex.createCell(0).setCellValue(err_index);
                rowOfIndex.createCell(1).setCellValue(string);
                ++err_index;
            }
            ByteArrayOutputStream err_os = new ByteArrayOutputStream(0xA00000);
            err_workbook.write(err_os);
            byte[] err_byteArray = err_os.toByteArray();
            ExportData errCsv = new ExportData("\u9519\u8bef\u4fe1\u606f.csv", err_byteArray);
            exportDataList.add(errCsv);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return exportDataList;
    }

    @Override
    public List<FormulaData> queryFmlList(FmlMonitorAndDebugParam fmlMonitorAndDebugParam) {
        FormulaData formulaData;
        List parsedExpressions;
        ArrayList<FormulaData> result = new ArrayList<FormulaData>();
        DataLinkDefine dataLinkDefine = this.iRunTimeViewController.queryDataLinkDefine(fmlMonitorAndDebugParam.getDataLinkKey());
        String dataLinkCode = dataLinkDefine.getUniqueCode();
        if (fmlMonitorAndDebugParam.getFmlType().equals("check")) {
            parsedExpressions = this.formulaRuntimeController.getParsedExpressionByDataLink(dataLinkCode, fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey(), null, DataEngineConsts.FormulaType.CHECK);
            for (IParsedExpression iParsedExpression : parsedExpressions) {
                formulaData = new FormulaData();
                formulaData.setKey(iParsedExpression.getKey());
                formulaData.setCode(iParsedExpression.getSource().getCode());
                formulaData.setFormula(iParsedExpression.getSource().getFormula());
                result.add(formulaData);
            }
        }
        if (fmlMonitorAndDebugParam.getFmlType().equals("calculate")) {
            parsedExpressions = this.formulaRuntimeController.getParsedExpressionByDataLink(dataLinkCode, fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey(), null, DataEngineConsts.FormulaType.CALCULATE);
            for (IParsedExpression iParsedExpression : parsedExpressions) {
                if (!dataLinkCode.equals(iParsedExpression.getAssignNode().getDataLink().getDataLinkCode())) continue;
                formulaData = new FormulaData();
                formulaData.setKey(iParsedExpression.getKey());
                formulaData.setCode(iParsedExpression.getSource().getCode());
                formulaData.setFormula(iParsedExpression.getSource().getFormula());
                result.add(formulaData);
            }
        }
        return result;
    }

    @Override
    public FmlDebugNode queryFmlValue(FmlMonitorAndDebugParam fmlMonitorAndDebugParam) {
        IParsedExpression iParsedExpression = this.runTimeFormulaController.getExpressionBySchemeAndExpression(fmlMonitorAndDebugParam.getContext().getFormulaSchemeKey(), fmlMonitorAndDebugParam.getExpressionKey());
        ArrayList<IASTNode> nodes = new ArrayList<IASTNode>();
        for (IASTNode node : iParsedExpression.getRealExpression()) {
            nodes.add(node);
        }
        IASTNode formula = (IASTNode)nodes.get(0);
        FmlDebugNode fmlDebugNode = new FmlDebugNode(false);
        String formulaCode = iParsedExpression.getSource().getCode();
        String formulaText = iParsedExpression.getSource().getFormula();
        String formulaMeaning = iParsedExpression.getSource().getMeanning();
        IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.createExecutorContext(fmlMonitorAndDebugParam.getContext().getFormSchemeKey());
        executorContext.setDefaultGroupName(this.iRunTimeViewController.queryFormById(iParsedExpression.getFormKey()).getFormCode());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)fmlMonitorAndDebugParam.getContext());
        if (!fmlMonitorAndDebugParam.getRowDimMap().isEmpty()) {
            for (String rowDim : fmlMonitorAndDebugParam.getRowDimMap().keySet()) {
                dimensionValueSet.setValue(rowDim, (Object)fmlMonitorAndDebugParam.getRowDimMap().get(rowDim));
            }
        }
        DetailCollectMonitor monitor = new DetailCollectMonitor();
        try {
            if (fmlMonitorAndDebugParam.getFmlType().equals("calculate")) {
                Object result = expressionEvaluator.evalValueWithDetail((IASTNode)iParsedExpression.getAssignNode(), executorContext, dimensionValueSet, monitor);
                fmlDebugNode.setText("\u503c\uff1a" + result + " \n\u516c\u5f0f\u7f16\u53f7\uff1a" + formulaCode + "\n\u516c\u5f0f\uff1a" + formulaText + " \n\u516c\u5f0f\u8bf4\u660e\uff1a" + formulaMeaning + "\n ");
                if (formula instanceof IfThenElse) {
                    this.buildChildrenNode(formula, fmlDebugNode, expressionEvaluator, executorContext, dimensionValueSet);
                } else {
                    this.buildChildrenNode(formula.getChild(1), fmlDebugNode, expressionEvaluator, executorContext, dimensionValueSet);
                }
            } else {
                Object resultObject = expressionEvaluator.evalValueWithDetail(formula, executorContext, dimensionValueSet, monitor);
                if (resultObject == null) {
                    fmlDebugNode.setExpandHolderPosition(0);
                    fmlDebugNode.setText("\u503c\uff1a  \n\u516c\u5f0f\u7f16\u53f7\uff1a" + formulaCode + "\n\u516c\u5f0f\uff1a" + formulaText + " \n\u516c\u5f0f\u8bf4\u660e\uff1a" + formulaMeaning + "\n ");
                    return fmlDebugNode;
                }
                boolean result = (Boolean)resultObject;
                StringBuilder text = new StringBuilder("\u503c\uff1a" + result + " \n\u516c\u5f0f\u7f16\u53f7\uff1a" + formulaCode + "\n\u516c\u5f0f\uff1a" + formulaText + " \n\u516c\u5f0f\u8bf4\u660e\uff1a" + formulaMeaning + "\n");
                if (StringUtils.isNotEmpty((String)monitor.toString())) {
                    text.append("\u516c\u5f0f\u8c03\u8bd5\u4fe1\u606f\uff1a" + monitor.toString());
                    fmlDebugNode.setData(true);
                }
                fmlDebugNode.setText(text.toString());
                this.buildChildrenNode(formula, fmlDebugNode, expressionEvaluator, executorContext, dimensionValueSet);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fmlDebugNode;
    }

    @Override
    public String getDimsTitle(String task, DimensionCombination dimensionCombination) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(task);
        boolean isCustom = taskDefine.getPeriodType() == PeriodType.CUSTOM;
        String[] masters = taskDefine.getMasterEntitiesKey().split(";");
        StringBuilder builder = new StringBuilder();
        try {
            for (String master : masters) {
                boolean periodView = this.periodEntityAdapter.isPeriodEntity(master);
                EntityViewDefine entityViewDefine = this.entityViewController.buildEntityView(master);
                if (entityViewDefine == null) continue;
                IDataAssist dataAssist = this.dataAccessProvider.newDataAssist(new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionController));
                String dimName = dataAssist.getDimensionName(entityViewDefine);
                if (periodView) {
                    IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityViewDefine.getEntityId());
                    if (isCustom) {
                        IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(entityViewDefine.getEntityId());
                        String periodTitle = periodProvider.getPeriodTitle(dimensionCombination.getValue(dimName).toString());
                        builder.append(" " + periodEntity.getTitle() + "\uff1a" + periodTitle + "\uff1b  ");
                        continue;
                    }
                    String periodTitle = this.periodEngineService.getPeriodAdapter().getPeriodProvider(entityViewDefine.getEntityId()).getPeriodTitle(dimensionCombination.getValue(dimName).toString());
                    builder.append("  \u65f6\u671f\uff1a" + periodTitle + "\uff1b   ");
                    continue;
                }
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityViewDefine.getEntityId());
                ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
                IEntityQuery query = this.entityDataService.newEntityQuery();
                query.sorted(true);
                query.setEntityView(entityViewDefine);
                query.setIgnoreViewFilter(true);
                query.setAuthorityOperations(AuthorityType.Read);
                IEntityTable entityTable = query.executeReader((IContext)context);
                IEntityRow row = entityTable.findByEntityKey(dimensionCombination.getValue(dimName).toString());
                builder.append("  " + entityDefine.getTitle() + "\uff1a" + row.getTitle() + "\uff1b   ");
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return builder.toString();
    }

    private com.jiuqi.np.dataengine.executors.ExecutorContext createExecutorContext(String formSchemeKey) {
        com.jiuqi.np.dataengine.executors.ExecutorContext context = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.iRunTimeViewController, this.dataDefinitionController, this.entityViewController, formSchemeKey, true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    private void buildChildrenNode(IASTNode node, FmlDebugNode fmlDebugNode, IExpressionEvaluator expressionEvaluator, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, DimensionValueSet dimensionValueSet) {
        if (node instanceof IfThenElse) {
            IASTNode ifNode = node.getChild(0);
            IASTNode thenNode = node.getChild(1);
            IASTNode elseNode = node.getChild(2);
            DetailCollectMonitor monitor = new DetailCollectMonitor();
            try {
                boolean result = (Boolean)expressionEvaluator.evalValueWithDetail(ifNode, executorContext, dimensionValueSet, monitor);
                FmlDebugNode ifChild = new FmlDebugNode(false);
                ifChild.setText("\u503c\uff1a" + result + " \n\u516c\u5f0f\uff1a" + this.parseFormulaNode(ifNode) + "\n ");
                fmlDebugNode.getChildren().add(ifChild);
                this.buildChildrenNode(ifNode, ifChild, expressionEvaluator, executorContext, dimensionValueSet);
                FmlDebugNode thenChild = new FmlDebugNode(true);
                fmlDebugNode.getChildren().add(thenChild);
                thenChild.setText(" \n\u516c\u5f0f\uff1a" + this.parseFormulaNode(thenNode) + "\n ");
                FmlDebugNode elseChild = new FmlDebugNode(true);
                fmlDebugNode.getChildren().add(elseChild);
                elseChild.setText(" \n\u516c\u5f0f\uff1a" + this.parseFormulaNode(elseNode) + "\n ");
                if (result) {
                    thenChild.setExpandHolderPosition(1);
                    if (thenNode instanceof Equal) {
                        this.buildChildrenNode(thenNode.getChild(1), thenChild, expressionEvaluator, executorContext, dimensionValueSet);
                    }
                    this.buildChildrenNode(thenNode, thenChild, expressionEvaluator, executorContext, dimensionValueSet);
                }
                elseChild.setExpandHolderPosition(1);
                if (elseNode instanceof Equal) {
                    this.buildChildrenNode(elseNode.getChild(1), elseChild, expressionEvaluator, executorContext, dimensionValueSet);
                }
                this.buildChildrenNode(elseNode, elseChild, expressionEvaluator, executorContext, dimensionValueSet);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (node.getNodeType().equals((Object)ASTNodeType.FUNCTION) || node.getNodeType().equals((Object)ASTNodeType.OPERATOR)) {
            FmlDebugNode child = new FmlDebugNode(false);
            fmlDebugNode.getChildren().add(child);
            StringBuilder text = new StringBuilder();
            DetailCollectMonitor monitor = new DetailCollectMonitor();
            try {
                Object result = expressionEvaluator.evalValueWithDetail(node, executorContext, dimensionValueSet, monitor);
                text.append("\u503c\uff1a ");
                if (result != null) {
                    text.append(result);
                }
                text.append(" \n ");
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            text.append("\u516c\u5f0f\u7c7b\u578b\uff1a ");
            if (StringUtils.isNotEmpty((String)String.valueOf(node.getToken()))) {
                text.append(node.getToken() + " \n ");
            } else {
                text.append(node.toString().substring(1, node.toString().length() - 1).split(",")[0] + " \n ");
            }
            text.append("\u516c\u5f0f\uff1a " + this.parseFormulaNode(node));
            if (node instanceof FunctionNode) {
                text.append(" \n\u516c\u5f0f\u542b\u4e49\uff1a" + ((FunctionNode)node).getDefine().title());
            }
            if (StringUtils.isNotEmpty((String)monitor.toString())) {
                child.setData(true);
                text.append(" \n\u516c\u5f0f\u8c03\u8bd5\u4fe1\u606f\uff1a ");
                for (String message : monitor.getMessages()) {
                    text.append(" \n " + message);
                }
            }
            child.setText(text.toString());
            int nums = node.childrenSize();
            for (int i = 0; i < nums; ++i) {
                this.buildChildrenNode(node.getChild(i), child, expressionEvaluator, executorContext, dimensionValueSet);
            }
        } else if (node instanceof DataNode) {
            FmlDebugNode child = new FmlDebugNode(true);
            fmlDebugNode.getChildren().add(child);
            child.setText("  \n\u503c\uff1a" + node.toString() + "  \n ");
        } else {
            FmlDebugNode child = new FmlDebugNode(true);
            fmlDebugNode.getChildren().add(child);
            DetailCollectMonitor monitor = new DetailCollectMonitor();
            try {
                Object result = expressionEvaluator.evalValueWithDetail(node, executorContext, dimensionValueSet, monitor);
                if (result == null) {
                    child.setText("\u503c\uff1a \n\u516c\u5f0f\uff1a" + node.toString() + "  \n ");
                }
                child.setText("\u503c\uff1a" + result + " \n\u516c\u5f0f\uff1a" + node.toString() + " \n ");
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String parseFormulaNode(IASTNode node) {
        if (node.childrenSize() < 2) {
            return node.toString();
        }
        StringBuilder sb = new StringBuilder();
        String nodeExp = node.toString().substring(1, node.toString().length() - 1);
        String[] nodeList = nodeExp.split(",");
        String operator = nodeList[0];
        if (node instanceof FunctionNode) {
            sb.append(((FunctionNode)node).getDefine().name()).append("(");
            for (int i = 0; i < node.childrenSize(); ++i) {
                sb.append(this.parseFormulaNode(node.getChild(i))).append(",");
            }
            sb.append(")");
            return sb.toString();
        }
        if (node.childrenSize() == 2) {
            sb.append(this.parseFormulaNode(node.getChild(0))).append(" ").append(operator).append(" ").append(this.parseFormulaNode(node.getChild(1)));
        }
        if (operator.equals("IF-THEN-ELSE")) {
            sb.append("IF ").append(this.parseFormulaNode(node.getChild(0))).append(" THEN ").append(this.parseFormulaNode(node.getChild(1))).append(" ELSE ").append(this.parseFormulaNode(node.getChild(2)));
        }
        return sb.toString();
    }

    public class FmlExeMonitor
    extends LogicProgressMonitor {
        private AbstractMonitor abstractMonitor;

        public FmlExeMonitor(AbstractMonitor abstractMonitor, AsyncTaskMonitor progressMonitor, double progressStart, String progressMessage, double coefficient) {
            super(progressMonitor, progressStart, progressMessage, coefficient);
            this.abstractMonitor = abstractMonitor;
        }

        public AbstractMonitor getFmlEngineMonitor() {
            return this.abstractMonitor;
        }
    }

    public class FmlDebugMonitor
    extends AbstractMonitor {
        private List<String> FmlExecuteProgram = new ArrayList<String>();

        public List<String> getFmlExecuteProgram() {
            return this.FmlExecuteProgram;
        }

        public void message(String msg, Object sender) {
            String message = this.getLogMessage(msg);
            this.FmlExecuteProgram.add(message);
        }

        public void debug(String msg, DataEngineConsts.DebugLogType type) {
            String message = this.getLogMessage(msg);
            this.FmlExecuteProgram.add(message);
        }

        public void exception(Exception e) {
            this.FmlExecuteProgram.add(e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                this.FmlExecuteProgram.add(stackTraceElement.toString());
            }
        }

        public void finish() {
            super.finish();
        }

        private String getLogMessage(String msg) {
            String message = msg;
            if (this.runType != null) {
                message = this.runType.getTitle() + " - " + msg;
            }
            return message;
        }
    }

    public class FmlExecuteCollectConfigObj
    extends FmlExecuteCollectConfig {
        private List<String> focusZbExps = new ArrayList<String>();

        public List<String> getFocusZbExps() {
            return this.focusZbExps;
        }

        public void setFocusZbExps(List<String> focusZbExps) {
            this.focusZbExps = focusZbExps;
        }
    }
}

