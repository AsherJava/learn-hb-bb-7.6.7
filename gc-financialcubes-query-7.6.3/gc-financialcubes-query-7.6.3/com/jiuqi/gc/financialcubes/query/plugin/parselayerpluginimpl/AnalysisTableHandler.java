/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.bi.dataset.storage.DataSetStorageManager
 *  com.jiuqi.bi.quickreport.engine.context.ReportContext
 *  com.jiuqi.bi.quickreport.engine.context.ReportDataSetProvider
 *  com.jiuqi.bi.quickreport.engine.parser.IDataSetModelProvider
 *  com.jiuqi.bi.quickreport.engine.parser.IReportExpression
 *  com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException
 *  com.jiuqi.bi.quickreport.engine.parser.ReportParser
 *  com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode
 *  com.jiuqi.bi.quickreport.model.CellMap
 *  com.jiuqi.bi.quickreport.model.DataSetInfo
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Like
 *  com.jiuqi.bi.syntax.operator.Minus
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.tuples.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  com.jiuqi.nvwa.quickreport.web.query.QueryUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gc.financialcubes.query.plugin.parselayerpluginimpl;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.DataSetStorageManager;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.context.ReportDataSetProvider;
import com.jiuqi.bi.quickreport.engine.parser.IDataSetModelProvider;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.ReportParser;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.quickreport.model.CellMap;
import com.jiuqi.bi.quickreport.model.DataSetInfo;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Like;
import com.jiuqi.bi.syntax.operator.Minus;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.tuples.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financialcubes.query.dto.AnalysePenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.enums.PenetrationType;
import com.jiuqi.gc.financialcubes.query.plugin.ParseLayerPenetrationPlugin;
import com.jiuqi.gc.financialcubes.query.redisservice.AnalyseTablePenetrationMap;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.common.NvwaQuickReportException;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import com.jiuqi.nvwa.quickreport.web.query.QueryUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisTableHandler
implements ParseLayerPenetrationPlugin {
    @Autowired
    private QuickReportModelService quickReportModelService;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private AnalyseTablePenetrationMap analyseTablePenetrationMap;

    @Override
    public PenetrationType getType() {
        return PenetrationType.ANALYSIS_TABLE;
    }

    @Override
    public Map<String, String> handle(String linkMsg, PenetrationContextInfo context) {
        Pair<String, String> extractAnalyseInfo = this.extractAnalyseInfo(linkMsg);
        String position = (String)extractAnalyseInfo.get_1();
        QuickReport quickReportByGuidOrId = this.getQuickReport((String)extractAnalyseInfo.get_0());
        String primarySheetName = quickReportByGuidOrId.getPrimarySheetName();
        String formulaOriginalValue = this.getFormulaValue(primarySheetName, quickReportByGuidOrId, position);
        String filterConditionOriginalValue = this.getFilterConditionValue(primarySheetName, quickReportByGuidOrId, position);
        ReportParser reportParser = this.getParser(quickReportByGuidOrId);
        List<IASTNode> formulaValueAfterParse = this.getFormulaValueAfterParse(reportParser, formulaOriginalValue);
        List<IASTNode> formulaFilterConditionAfterParse = this.getFormulaFilterConditionAfterParse(reportParser, filterConditionOriginalValue);
        DataSetInfo dataSetInfo = (DataSetInfo)quickReportByGuidOrId.getRefDataSets().get(0);
        AnalysePenetrationContextInfo analyseContext = new AnalysePenetrationContextInfo();
        analyseContext.setAnalyseTableId(dataSetInfo.getId());
        analyseContext.setAnalyseTableType(dataSetInfo.getType());
        this.setAnalyseSchemeInfo(context, analyseContext);
        Map<String, String> conditionMap = this.extractConditionsFromFormulas(formulaValueAfterParse);
        conditionMap.putAll(this.extractConditionsFromFormulas(formulaFilterConditionAfterParse));
        return this.analysePreProcess(analyseContext, linkMsg, conditionMap);
    }

    private QuickReport getQuickReport(String idOrGuid) {
        try {
            return this.quickReportModelService.getQuickReportByGuidOrId(idOrGuid);
        }
        catch (NvwaQuickReportException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
    }

    private Map<String, String> extractConditionsFromFormulas(List<IASTNode> formulas) {
        HashMap<String, String> conditionMap = new HashMap<String, String>();
        for (IASTNode formula : formulas) {
            this.addConditionToMap(formula, conditionMap);
        }
        return conditionMap;
    }

    private void addConditionToMap(IASTNode formula, Map<String, String> conditionMap) {
        if (formula instanceof DataSetFunctionNode) {
            this.processDataSetFunctionNode((DataSetFunctionNode)formula, conditionMap);
        } else if (formula instanceof Like) {
            this.processLikeNode((Like)formula, conditionMap);
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u6b64\u516c\u5f0f: " + formula.getClass().getSimpleName());
        }
    }

    private void processDataSetFunctionNode(DataSetFunctionNode ds, Map<String, String> conditionMap) {
        IASTNode iastNode = (IASTNode)ds.getParameters().get(2);
        this.processNodeRecursively(iastNode, conditionMap);
    }

    private void processNodeRecursively(IASTNode node, Map<String, String> conditionMap) {
        if (node instanceof Like) {
            this.processLikeNode((Like)node, conditionMap);
        } else if (node instanceof Or || node instanceof And) {
            this.processNodeRecursively(node.getChild(0), conditionMap);
            this.processNodeRecursively(node.getChild(1), conditionMap);
        } else {
            throw new BusinessRuntimeException("\u6682\u4e0d\u652f\u6301\u6b64\u64cd\u4f5c\u7b26: " + node.getClass().getSimpleName());
        }
    }

    private void processLikeNode(Like like, Map<String, String> conditionMap) {
        String condition = this.formatFormulaCondition(StringUtils.toViewString((Object)like.getChild(0)));
        String condValue = StringUtils.toViewString((Object)like.getChild(1)).replace("'", "").replace("\"", "");
        this.updateConditionMap(condition, condValue, conditionMap);
    }

    private void updateConditionMap(String condition, String condValue, Map<String, String> conditionMap) {
        if (conditionMap.containsKey(condition) && !conditionMap.get(condition).equals(condValue)) {
            condValue = conditionMap.get(condition) + "," + condValue;
        }
        conditionMap.put(condition, condValue);
    }

    public String formatFormulaCondition(String input) {
        String condition = input.contains(".") ? input.substring(input.indexOf(46) + 1) : input;
        int lastUnderscoreIndex = condition.lastIndexOf(95);
        if (lastUnderscoreIndex != -1) {
            condition = condition.substring(0, lastUnderscoreIndex) + '.' + condition.substring(lastUnderscoreIndex + 1);
        }
        return condition;
    }

    private Map<String, String> analysePreProcess(AnalysePenetrationContextInfo analyseContext, String linkMsg, Map<String, String> conditionMap) {
        Map linkmsgMap = JSONUtil.parseMap((String)linkMsg);
        HashMap<String, String> msgMap = new HashMap<String, String>();
        this.processMap(analyseContext, linkmsgMap, msgMap);
        this.processMap(analyseContext, conditionMap, msgMap);
        return msgMap;
    }

    private void setAnalyseSchemeInfo(PenetrationContextInfo context, AnalysePenetrationContextInfo analyseContext) {
        DataSetStorageManager dsMgr = DataSetStorageManager.getInstance();
        List fields = null;
        try {
            DSModel dsModel = dsMgr.findModel(analyseContext.getAnalyseTableId(), analyseContext.getAnalyseTableType());
            fields = dsModel.getFields();
        }
        catch (DataSetStorageException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (fields != null) {
            for (DSField field : fields) {
                int lastIndex;
                String key = field.getName();
                FieldType fieldType = field.getFieldType();
                if (fieldType != FieldType.MEASURE || (lastIndex = key.lastIndexOf(95)) == -1) continue;
                String subKey = key.substring(0, lastIndex);
                context.setDataSchemeTableCode(subKey);
                context.setDataSchemeKey(this.iRuntimeDataSchemeService.getDataTableByCode(subKey).getDataSchemeKey());
                break;
            }
        }
    }

    private Pair<String, String> extractAnalyseInfo(String linkMsg) {
        Map linkmsgMap = JSONUtil.parseMap((String)linkMsg);
        String reportName = "";
        String cellInfo = "";
        for (Map.Entry entry : linkmsgMap.entrySet()) {
            String key = (String)entry.getKey();
            Object value = entry.getValue();
            if (key.equals("_reportName")) {
                reportName = value.toString();
                continue;
            }
            if (!key.equals("_rawCell")) continue;
            if (value instanceof Map) {
                Map cellMap = (Map)value;
                int col = (Integer)cellMap.get("col");
                int row = (Integer)cellMap.get("row");
                cellInfo = this.convertColToExcelColumn(col) + row;
                continue;
            }
            throw new BusinessRuntimeException("\u7c7b\u578b\u8f6c\u6362\u9519\u8bef: " + value);
        }
        return new Pair((Object)reportName, (Object)cellInfo);
    }

    private String convertColToExcelColumn(int col) {
        StringBuilder column = new StringBuilder();
        while (col > 0) {
            column.insert(0, (char)(65 + --col % 26));
            col /= 26;
        }
        return column.toString();
    }

    private String getFormulaValue(String primarySheetName, QuickReport quickReportByGuidOrId, String position) {
        List worksheets = quickReportByGuidOrId.getWorksheets();
        return worksheets.stream().filter(worksheet -> worksheet.getName().equals(primarySheetName)).flatMap(worksheet -> worksheet.getCellMaps().stream()).filter(cellMap -> cellMap.getPosition().toString().equals(position)).map(CellMap::getValue).findFirst().orElse("");
    }

    private String getFilterConditionValue(String primarySheetName, QuickReport quickReportByGuidOrId, String position) {
        List worksheets = quickReportByGuidOrId.getWorksheets();
        WorksheetModel worksheet = worksheets.stream().filter(w -> w.getName().equals(primarySheetName)).findFirst().orElse(null);
        if (worksheet == null) {
            return "";
        }
        List cellMaps = worksheet.getCellMaps();
        Optional<CellMap> cellMap = cellMaps.stream().filter(cm -> cm.getPosition().toString().equals(position)).findFirst();
        return cellMap.map(CellMap::getFilter).orElse("");
    }

    private ReportParser getParser(QuickReport quickReportByGuidOrId) {
        IParameterEnv iParameterEnv = null;
        try {
            iParameterEnv = QueryUtil.buildParameterEnv((QuickReport)quickReportByGuidOrId, (Option)new Option());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        ReportContext reportContext = new ReportContext(quickReportByGuidOrId);
        reportContext.setUserID(NpContextHolder.getContext().getUserId());
        reportContext.setParamEnv(iParameterEnv);
        reportContext.setDataSetProvider((IDataSetModelProvider)new ReportDataSetProvider(quickReportByGuidOrId));
        return new ReportParser((IContext)reportContext);
    }

    private List<IASTNode> getFormulaValueAfterParse(ReportParser reportParser, String formulaOriginalValue) {
        ArrayList<IASTNode> nodeList = new ArrayList<IASTNode>();
        try {
            IReportExpression parseResult = reportParser.parseEval(formulaOriginalValue);
            IASTNode rootNode = parseResult.getRootNode();
            this.traverseAndAddNodes(rootNode, nodeList);
        }
        catch (ReportExpressionException e) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u89e3\u6790\u5931\u8d25");
        }
        return nodeList;
    }

    private List<IASTNode> getFormulaFilterConditionAfterParse(ReportParser reportParser, String formulaOriginalValue) {
        ArrayList<IASTNode> nodeList = new ArrayList<IASTNode>();
        if (formulaOriginalValue == null || formulaOriginalValue.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            IReportExpression parseResult = reportParser.parseCond(formulaOriginalValue);
            IASTNode rootNode = parseResult.getRootNode();
            this.traverseFilterConditionNodes(rootNode, nodeList);
        }
        catch (ReportExpressionException e) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u89e3\u6790\u5931\u8d25");
        }
        return nodeList;
    }

    private void traverseFilterConditionNodes(IASTNode node, List<IASTNode> nodeList) {
        if (node instanceof Like) {
            nodeList.add(node);
        } else if (node instanceof And || node instanceof Or) {
            IASTNode leftChild = node.getChild(0);
            IASTNode rightChild = node.getChild(1);
            this.traverseFilterConditionNodes(leftChild, nodeList);
            this.traverseFilterConditionNodes(rightChild, nodeList);
        }
    }

    private void traverseAndAddNodes(IASTNode node, List<IASTNode> nodeList) {
        if (node instanceof DataSetFunctionNode) {
            nodeList.add(node);
        } else if (node instanceof Plus || node instanceof Minus) {
            IASTNode leftChild = node.getChild(0);
            IASTNode rightChild = node.getChild(1);
            this.traverseAndAddNodes(leftChild, nodeList);
            this.traverseAndAddNodes(rightChild, nodeList);
        }
    }

    private void processMap(AnalysePenetrationContextInfo analyseContext, Map<String, ?> inputMap, Map<String, String> outputMap) {
        Map<String, String> cacheMapValue = this.analyseTablePenetrationMap.getAnalyseMap(analyseContext);
        for (Map.Entry<String, ?> entry : inputMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.equals("_reportName") || key.equals("_rawCell") || key.equals("measureCode")) continue;
            if (key.split("\\.").length == 3) {
                key = key.substring(key.indexOf(46) + 1);
            }
            if (key.contains(".") && cacheMapValue.containsKey(key.split("\\.")[1])) {
                key = cacheMapValue.get(key.split("\\.")[1]);
            }
            if (value instanceof ArrayList) {
                ArrayList valueList = (ArrayList)value;
                value = valueList.size() == 1 ? valueList.get(0) : (valueList.size() > 1 ? String.join((CharSequence)",", (CharSequence[])valueList.stream().map(Object::toString).toArray(String[]::new)) : "");
            } else if (!(value instanceof String)) {
                throw new BusinessRuntimeException("\u7c7b\u578b\u8f6c\u6362\u9519\u8bef" + value);
            }
            outputMap.put(key, StringUtils.toViewString(value).replace("'", "").replace("\"", ""));
        }
    }
}

