/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.nr.conditionalstyle.controller.IConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.ConditionalStyle;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.jtable.params.base.CStyleFile;
import com.jiuqi.nr.jtable.params.base.Cell;
import com.jiuqi.nr.jtable.params.base.CellExpression;
import com.jiuqi.nr.jtable.params.base.CellStyle;
import com.jiuqi.nr.jtable.params.base.TargetStyle;
import com.jiuqi.nr.jtable.service.ICSRunTimeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class CSRunTimeServiceImpl
implements ICSRunTimeService,
ApplicationListener<DeployFinishedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(CSRunTimeServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IConditionalStyleController conditionalStyleController;
    private int formulaStyleCodeNum = 1000;
    private final NedisCache csFileCache;
    private final String StyleFormula = "StyleFormula";
    private final IdMutexProvider idMutexProvider;
    private final String formulaStyleCache = "this._sfc";

    public CSRunTimeServiceImpl(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManger = cacheProvider.getCacheManager();
        this.csFileCache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(IParsedExpression.class).concat("_csFile"));
        this.idMutexProvider = new IdMutexProvider();
    }

    @Override
    public CStyleFile getStyleFormulasInForm(String taskKey, String formKey) {
        if (formKey == null) {
            throw new IllegalArgumentException("'formKey' must not be null.");
        }
        String formSchemeKey = "";
        try {
            List formSchemeDefines = this.viewController.queryFormSchemeByForm(formKey);
            formSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String cacheKey = "StyleFormula" + formSchemeKey;
        String fileName = formKey;
        Cache.ValueWrapper valueWrapper = this.csFileCache.hGet(cacheKey, (Object)fileName);
        CStyleFile cStyleFile = valueWrapper != null ? (CStyleFile)valueWrapper.get() : this.getCsFileByForm(formSchemeKey, formKey);
        return cStyleFile;
    }

    private CStyleFile getCsFileByForm(String formSchemeKey, String formKey) {
        return (CStyleFile)RuntimeDefinitionCache.synchronizedRun((Object)this.idMutexProvider.getMutex(formSchemeKey + formKey), () -> {
            String fileName;
            String cacheKey = "StyleFormula" + formSchemeKey;
            Cache.ValueWrapper valueWrapper = this.csFileCache.hGet(cacheKey, (Object)(fileName = formKey));
            if (valueWrapper != null) {
                CStyleFile cStyleFile = (CStyleFile)valueWrapper.get();
                return cStyleFile;
            }
            List conditionalStyleList = this.conditionalStyleController.getAllCSInForm(formKey);
            FormDefine formDefine = this.viewController.queryFormById(formKey);
            if (formDefine != null && conditionalStyleList != null && conditionalStyleList.size() != 0) {
                CStyleFile cStyleFile = this.getStyleJsFormulasInForm(formKey, conditionalStyleList);
                this.csFileCache.hSet(cacheKey, (Object)fileName, (Object)cStyleFile);
                return cStyleFile;
            }
            this.csFileCache.hSet(cacheKey, (Object)fileName, null);
            return null;
        });
    }

    private CStyleFile getStyleJsFormulasInForm(String formKey, List<ConditionalStyle> conditionalStyleList) {
        CStyleFile cStyleFile = new CStyleFile();
        List<StyleFormula> styleFormulas = this.parseConditionalStyle(conditionalStyleList);
        if (styleFormulas == null) {
            return null;
        }
        HashMap<String, QueryStyleByCell> queryStyleByCells = this.parseConditionalStyleByCell(conditionalStyleList);
        int formulaIndex = 10000;
        FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        StringBuilder styleJS = new StringBuilder();
        for (StyleFormula styleFormula : styleFormulas) {
            try {
                QueryContext queryContext = new QueryContext(styleFormula.getExecutorContext(), null);
                this.getCheckFormulaScript(styleFormula, formKey, queryContext, cStyleFile, formulaIndex, jqStyleShowInfo, styleJS, queryStyleByCells);
                ++formulaIndex;
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        cStyleFile.setStyleFormulaJS(styleJS.toString());
        return cStyleFile;
    }

    HashMap<String, QueryStyleByCell> parseConditionalStyleByCell(List<ConditionalStyle> conditionalStyleList) {
        HashMap<String, QueryStyleByCell> map = new HashMap<String, QueryStyleByCell>();
        for (ConditionalStyle conditionalStyle : conditionalStyleList) {
            QueryStyleByCell queryStyleByCell;
            String mapKey = String.valueOf(conditionalStyle.getPosX()) + "_" + String.valueOf(conditionalStyle.getPosY());
            if (map.containsKey(mapKey)) {
                queryStyleByCell = map.get(mapKey);
                queryStyleByCell.AddCS(conditionalStyle);
                continue;
            }
            queryStyleByCell = new QueryStyleByCell(conditionalStyle.getPosX(), conditionalStyle.getPosY());
            queryStyleByCell.AddCS(conditionalStyle);
            map.put(mapKey, queryStyleByCell);
        }
        return map;
    }

    List<StyleFormula> parseConditionalStyle(List<ConditionalStyle> conditionalStyleList) {
        HashMap<String, StyleFormula> map = new HashMap<String, StyleFormula>();
        if (conditionalStyleList == null || conditionalStyleList.size() == 0) {
            return null;
        }
        HashMap<String, Cell> CellMap = new HashMap<String, Cell>();
        for (ConditionalStyle conditionalStyle : conditionalStyleList) {
            StyleFormula styleFormula;
            Cell cell;
            String mapKey = conditionalStyle.getStyleExpression() + conditionalStyle.getFontColor() + conditionalStyle.getForeGroundColor() + conditionalStyle.getBold() + conditionalStyle.getItalic() + conditionalStyle.getReadOnly();
            String cellKey = String.valueOf(conditionalStyle.getPosX()) + "_" + String.valueOf(conditionalStyle.getPosY());
            if (CellMap.containsKey(cellKey)) {
                cell = (Cell)CellMap.get(cellKey);
            } else {
                cell = new Cell(conditionalStyle.getPosX(), conditionalStyle.getPosY());
                CellMap.put(cellKey, cell);
            }
            if (map.containsKey(mapKey)) {
                styleFormula = (StyleFormula)map.get(mapKey);
                this.styleFormulaAddCell(conditionalStyle, cell, styleFormula);
                continue;
            }
            styleFormula = new StyleFormula(conditionalStyle.getStyleExpression(), conditionalStyle.getFormKey());
            this.styleFormulaAddCell(conditionalStyle, cell, styleFormula);
            map.put(mapKey, styleFormula);
        }
        return new ArrayList<StyleFormula>(map.values());
    }

    private void styleFormulaAddCell(ConditionalStyle conditionalStyle, Cell cell, StyleFormula styleFormula) {
        styleFormula.addCells(cell);
        CellStyle cellStyle = new CellStyle();
        String showText = null;
        if (conditionalStyle.getHorizontalBar().booleanValue()) {
            showText = "-";
        }
        cellStyle.setStyle(conditionalStyle.getFontColor(), conditionalStyle.getForeGroundColor(), conditionalStyle.getBold(), conditionalStyle.getItalic(), conditionalStyle.getReadOnly(), conditionalStyle.getStrikeThrough(), showText);
        styleFormula.setCellStyle(cellStyle);
    }

    private void getCheckFormulaScript(StyleFormula styleFormula, String formKey, QueryContext queryContext, CStyleFile cStyleFile, int formulaIndex, FormulaShowInfo jqStyleShowInfo, StringBuilder scriptBuilder, HashMap<String, QueryStyleByCell> queryStyleByCells) {
        try {
            QueryFields queryFields;
            FormulaSyntaxStyle syntaxStyle = styleFormula.getFormulaSyntaxStyle();
            IParsedExpression expression = styleFormula.getParsedExpression();
            boolean supportJs = expression.supportJs();
            int resultType = expression.getRealExpression().getType((IContext)queryContext);
            if (resultType == -1) {
                return;
            }
            ScriptInfo scriptInfo = new ScriptInfo();
            String varName = String.format("CSfml_%s", formulaIndex);
            scriptInfo.setVarName(varName);
            scriptInfo.setCalc(true);
            scriptInfo.setFormulaKey(expression.getSource().getId());
            scriptInfo.setExcelSyntax(syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL);
            if (syntaxStyle == null || syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION) {
                scriptInfo.setFormulaText(expression.getFormula(queryContext, jqStyleShowInfo));
            }
            if ((queryFields = expression.getQueryFields()).getCount() == 0) {
                for (Cell cell : styleFormula.getCells()) {
                    Iterator cellExpression = new CellExpression();
                    ((CellExpression)((Object)cellExpression)).setVarName(varName);
                    ((CellExpression)((Object)cellExpression)).setCellStyle(styleFormula.cellStyle);
                    String queryStyleByCellMapKey = String.valueOf(cell.getX()) + "_" + String.valueOf(cell.getY());
                    QueryStyleByCell thisQueryStyleByCell = queryStyleByCells.get(queryStyleByCellMapKey);
                    ((CellExpression)((Object)cellExpression)).setOrder(thisQueryStyleByCell.getCS().get(styleFormula.getFormulaExpression()).getOrder());
                    cell.AddCellExpression((CellExpression)((Object)cellExpression));
                    cStyleFile.AddPeriodCell(cell);
                }
            }
            supportJs = true;
            scriptInfo.setAutoCalc(false);
            scriptInfo.setDecription(expression.getSource().getMeanning());
            boolean flag = true;
            LinkedHashSet<String> otherlink = new LinkedHashSet<String>();
            for (IASTNode node : expression.getRealExpression()) {
                String dataLinkCode;
                String datalink;
                DynamicDataNode dynamicDataNode;
                if (!(node instanceof DynamicDataNode) || !queryFields.hasField((dynamicDataNode = (DynamicDataNode)node).getQueryField())) continue;
                DataModelLinkColumn dataModelLinkColumn = dynamicDataNode.getDataModelLink();
                if (dataModelLinkColumn == null || StringUtils.isEmpty((String)dataModelLinkColumn.getDataLinkCode())) {
                    logger.error("\u516c\u5f0f\uff1a" + expression.getFormula(queryContext, jqStyleShowInfo) + "\u89e3\u6790\u51fa\u9519");
                    continue;
                }
                if (!dataModelLinkColumn.getReportInfo().getReportKey().equals(formKey)) {
                    cStyleFile.AddOtherFormDataLink(dynamicDataNode.getQueryField().getTableName() + "[" + dynamicDataNode.getQueryField().getFieldName() + "]");
                    datalink = this.viewController.queryDataLinkDefineByUniquecode(dataModelLinkColumn.getReportInfo().getReportKey(), dataModelLinkColumn.getDataLinkCode()).getKey();
                    dataLinkCode = dataModelLinkColumn.getDataLinkCode();
                    this.AddDataLink(cStyleFile, datalink, dataLinkCode, varName, styleFormula, queryStyleByCells, dynamicDataNode.getQueryField());
                    otherlink.add(datalink);
                    continue;
                }
                datalink = this.viewController.queryDataLinkDefineByUniquecode(formKey, dataModelLinkColumn.getDataLinkCode()).getKey();
                dataLinkCode = dataModelLinkColumn.getDataLinkCode();
                this.AddDataLink(cStyleFile, datalink, dataLinkCode, varName, styleFormula, queryStyleByCells, dynamicDataNode.getQueryField());
                flag = false;
            }
            if (flag) {
                for (String datalink : otherlink) {
                    cStyleFile.addOtherDataLink(datalink);
                }
            }
            String scriptText = expression.generateJs(queryContext, scriptInfo);
            scriptBuilder.append(scriptText);
            this.addCheckExpressionToScript(scriptBuilder, varName);
        }
        catch (Exception e) {
            logger.error(styleFormula.getFormulaExpression() + "\uff1a\u6761\u4ef6\u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u6761\u4ef6\u6837\u5f0f");
        }
    }

    private void AddDataLink(CStyleFile cStyleFile, String datalink, String dataLinkCode, String expressionVarName, StyleFormula styleFormula, HashMap<String, QueryStyleByCell> queryStyleByCells, QueryField queryField) {
        TargetStyle targetStyle = new TargetStyle(dataLinkCode, queryField.getFieldCode());
        for (Cell cell : styleFormula.getCells()) {
            CellExpression cellExpression = new CellExpression();
            cellExpression.setVarName(expressionVarName);
            cellExpression.setCellStyle(styleFormula.cellStyle);
            String queryStyleByCellMapKey = String.valueOf(cell.getX()) + "_" + String.valueOf(cell.getY());
            QueryStyleByCell thisQueryStyleByCell = queryStyleByCells.get(queryStyleByCellMapKey);
            cellExpression.setOrder(thisQueryStyleByCell.getCS().get(styleFormula.getFormulaExpression()).getOrder());
            cell.AddCellExpression(cellExpression);
        }
        targetStyle.setCell(styleFormula.getCells());
        cStyleFile.AddTargetByDataLink(datalink, targetStyle);
    }

    private void addCheckExpressionToScript(StringBuilder scriptBuilder, String varName) {
        scriptBuilder.append("this._sfc");
        scriptBuilder.append("._ace(");
        scriptBuilder.append("\"" + varName + "\",");
        scriptBuilder.append(varName + ");");
    }

    @Override
    public void onApplicationEvent(DeployFinishedEvent event) {
        for (String formSchemeKey : event.getDeployParams().getFormScheme().getDesignTimeKeys()) {
            String cacheKey = "StyleFormula" + formSchemeKey;
            this.csFileCache.evict(cacheKey);
        }
    }

    class QueryStyleByCell {
        private int x;
        private int y;
        private HashMap<String, ConditionalStyle> conditionalStyleList;

        public QueryStyleByCell(int x, int y) {
            this.x = x;
            this.y = y;
            this.conditionalStyleList = new HashMap();
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public void AddCS(ConditionalStyle conditionalStyle) {
            this.conditionalStyleList.put(conditionalStyle.getStyleExpression(), conditionalStyle);
        }

        public HashMap<String, ConditionalStyle> getCS() {
            return this.conditionalStyleList;
        }
    }

    class StyleFormula {
        private String formula;
        private Set<Cell> cells = new LinkedHashSet<Cell>();
        private IParsedExpression parsedExpression;
        private FormSchemeDefine formSchemeDefine;
        private ExecutorContext executorContext;
        private CellStyle cellStyle;

        public FormulaSyntaxStyle getFormulaSyntaxStyle() throws ParseException {
            TaskDefine task = CSRunTimeServiceImpl.this.viewController.queryTaskDefine(this.formSchemeDefine.getTaskKey());
            if (task == null) {
                throw new ParseException("task not found.  ".concat(this.formSchemeDefine.getTaskKey()));
            }
            FormulaSyntaxStyle formulaSyntaxStyle = task.getFormulaSyntaxStyle();
            if (formulaSyntaxStyle == null) {
                throw new ParseException("syntax style not found. ");
            }
            return formulaSyntaxStyle;
        }

        public ExecutorContext getExecutorContext() {
            return this.executorContext;
        }

        public StyleFormula(String formulaString, String formKey) {
            List expressions;
            this.formula = formulaString;
            Formula formulaObject = new Formula();
            formulaObject.setId(UUIDUtils.getKey());
            formulaObject.setChecktype(Integer.valueOf(DataEngineConsts.FormulaType.CHECK.getValue()));
            formulaObject.setFormula(this.formula);
            formulaObject.setFormKey(formKey);
            FormDefine form = CSRunTimeServiceImpl.this.runTimeViewController.queryFormById(formKey);
            formulaObject.setReportName(form.getFormCode());
            formulaObject.setCode(form.getFormCode() + String.valueOf(CSRunTimeServiceImpl.this.formulaStyleCodeNum++));
            formulaObject.setMeanning("");
            try {
                this.formSchemeDefine = (FormSchemeDefine)CSRunTimeServiceImpl.this.runTimeViewController.queryFormSchemeByForm(formKey).get(0);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            this.executorContext = this.createExecutorContext(this.formSchemeDefine.getKey());
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            formulas.add(formulaObject);
            try {
                expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)this.executorContext, formulas, (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK);
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
            this.parsedExpression = !expressions.isEmpty() ? (IParsedExpression)expressions.get(0) : null;
        }

        public String getFormulaExpression() {
            return this.formula;
        }

        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }

        public CellStyle getCellStyle() {
            return this.cellStyle;
        }

        public void addCells(Cell cell) {
            this.cells.add(cell);
        }

        public Set<Cell> getCells() {
            return this.cells;
        }

        public IParsedExpression getParsedExpression() {
            return this.parsedExpression;
        }

        private ExecutorContext createExecutorContext(String formSchemeKey) {
            ExecutorContext context = new ExecutorContext(CSRunTimeServiceImpl.this.dataDefinitionController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(CSRunTimeServiceImpl.this.viewController, CSRunTimeServiceImpl.this.dataDefinitionController, CSRunTimeServiceImpl.this.entityViewController, formSchemeKey, true);
            context.setEnv((IFmlExecEnvironment)environment);
            return context;
        }
    }
}

