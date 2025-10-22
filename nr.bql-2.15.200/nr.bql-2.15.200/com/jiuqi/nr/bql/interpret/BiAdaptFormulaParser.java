/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.model.RelationInfo
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.adhoc.model.RelationInfo;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.bql.interpret.BiAdapTransResult;
import com.jiuqi.nr.bql.interpret.BiAdaptContext;
import com.jiuqi.nr.bql.interpret.BiAdaptDimNode;
import com.jiuqi.nr.bql.interpret.BiAdaptFunctionProvider;
import com.jiuqi.nr.bql.interpret.BiAdaptNode;
import com.jiuqi.nr.bql.interpret.BiAdaptNodeProvider;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.bql.interpret.BiAdaptTable;
import com.jiuqi.nr.bql.interpret.BiAdaptVariableManager;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BiAdaptFormulaParser
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BiAdaptFormulaParser.class);
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private ReportFormulaParser formulaParser;
    private BiAdaptVariableManager variableManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.formulaParser = ReportFormulaParser.getInstance();
        this.formulaParser.unregisterFunctionProvider((IFunctionProvider)ReportFunctionProvider.GLOBAL_PROVIDER);
        this.variableManager = new BiAdaptVariableManager();
        this.formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)this.variableManager);
        this.formulaParser.registerFunctionProvider((IFunctionProvider)new BiAdaptFunctionProvider());
        this.formulaParser.setJQReportMode(true);
        this.formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new BiAdaptNodeProvider());
    }

    public boolean checkFormula(BiAdaptParam param, String formula) throws ParseException {
        BiAdaptContext context = this.createContext(param);
        IExpression exp = this.formulaParser.parseEval(formula, (IContext)context);
        return exp != null;
    }

    public int getDataType(BiAdaptParam param, String formula) throws SyntaxException {
        BiAdaptContext context = this.createContext(param);
        IExpression exp = this.formulaParser.parseEval(formula, (IContext)context);
        int dataType = exp.getType((IContext)context);
        if (dataType == 0) {
            FunctionNode funcNode;
            IASTNode root = exp.getChild(0);
            if (root instanceof IfThenElse) {
                return root.getChild(1).getType((IContext)context);
            }
            if (root instanceof FunctionNode && (funcNode = (FunctionNode)root).getDefine().name().equals("IfThen")) {
                return root.getChild(1).getType((IContext)context);
            }
        }
        return dataType;
    }

    public boolean needBizkeyOrder(BiAdaptParam param) throws ParseException {
        BiAdaptContext context = this.createContext(param);
        HashSet<String> dimTableSet = new HashSet<String>();
        HashMap<String, BiAdaptTable> floatTables = new HashMap<String, BiAdaptTable>();
        for (String selectedField : param.getSelectedFields()) {
            BiAdaptNode dataFieldNode;
            BiAdaptTable table;
            String fieldName;
            String[] strs = selectedField.split("\\.");
            String tableName = strs[0];
            ASTNode fieldNode = context.findNode(tableName, fieldName = strs[1]);
            if (fieldNode instanceof BiAdaptDimNode) {
                BiAdaptDimNode dimFieldNode = (BiAdaptDimNode)fieldNode;
                dimTableSet.add(dimFieldNode.getTableCode());
                continue;
            }
            if (!(fieldNode instanceof BiAdaptNode) || !(table = (dataFieldNode = (BiAdaptNode)fieldNode).getTable()).isFloat() || table.isRepeatCode()) continue;
            floatTables.put(table.getCode(), table);
        }
        for (BiAdaptTable table : floatTables.values()) {
            TableInfo tableInfo = table.getTableNode().getTable();
            boolean isFullKey = true;
            for (String keyField : table.getInnerDimFields()) {
                String fieldName = table.getCode() + "." + keyField;
                if (param.getSelectedFields().contains(fieldName)) continue;
                for (RelationInfo relation : tableInfo.getRelations()) {
                    if (!relation.getFieldMaps().containsKey(keyField) || dimTableSet.contains(relation.getTargetTable())) continue;
                    isFullKey = false;
                }
            }
            if (!isFullKey) continue;
            return false;
        }
        return true;
    }

    public Set<IFunction> getAllFunctions() {
        return this.formulaParser.allFunctions();
    }

    public List<Variable> getAllVariables() {
        return this.variableManager.getAllVars();
    }

    public BiAdapTransResult transFormulaToBiSyntax(BiAdaptParam param, String nrFormula) throws ParseException, InterpretException {
        BiAdaptContext context = this.createContext(param);
        return this.interpret(nrFormula, context);
    }

    public List<BiAdapTransResult> transFormulasToBiSyntax(BiAdaptParam param, List<String> nrFormulas) throws ParseException, InterpretException {
        BiAdaptContext context = this.createContext(param);
        ArrayList<BiAdapTransResult> results = new ArrayList<BiAdapTransResult>();
        for (String nrFormula : nrFormulas) {
            results.add(this.interpret(nrFormula, context));
        }
        return results;
    }

    private BiAdapTransResult interpret(String nrFormula, BiAdaptContext context) throws ParseException, InterpretException {
        IExpression exp = this.formulaParser.parseEval(nrFormula, (IContext)context);
        boolean hasFloat = false;
        for (IASTNode node : exp) {
            BiAdaptNode dataFieldNode;
            BiAdaptTable table;
            if (!(node instanceof BiAdaptNode) || !(table = (dataFieldNode = (BiAdaptNode)node).getTable()).isFloat() || dataFieldNode.isStat()) continue;
            hasFloat = true;
            break;
        }
        BiAdapTransResult result = new BiAdapTransResult();
        result.setHasFloatField(hasFloat);
        result.setBiFormula(exp.interpret((IContext)context, Language.FORMULA, null));
        return result;
    }

    private BiAdaptContext createContext(BiAdaptParam param) {
        BiAdaptContext context = new BiAdaptContext();
        context.setDataSchemeService(this.dataSchemeService);
        context.setEntityMetaService(this.entityMetaService);
        context.setFormulaParser(this.formulaParser);
        context.setPeriodEngineService(this.periodEngineService);
        context.setParam(param);
        context.setLogger(logger);
        context.setExecutorContext(new ExecutorContext(this.dataDefinitionRuntimeController));
        context.init();
        return context;
    }
}

