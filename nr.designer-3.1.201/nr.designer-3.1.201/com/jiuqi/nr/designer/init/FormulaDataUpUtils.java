/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.internal.log.Log
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.nr.data.engine.fml.var.AbstractContextVar
 *  com.jiuqi.nr.data.engine.fml.var.ContextVariableManager
 *  com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIOD
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODSTR
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_TIME
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_YEAR
 *  com.jiuqi.nr.data.engine.fml.var.VarDWDM
 *  com.jiuqi.nr.data.engine.fml.var.VarDWKJ
 *  com.jiuqi.nr.data.engine.fml.var.VarDWMC
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_SRC_TQRQ
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITCODE
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITTITLE
 *  com.jiuqi.nr.data.engine.fml.var.VarSYS_YEAR
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_DESCRIPTION
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_GUID
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_NAME
 *  com.jiuqi.nr.data.engine.fml.var.VarUSER_TITLE
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService
 *  org.springframework.jdbc.object.BatchSqlUpdate
 */
package com.jiuqi.nr.designer.init;

import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.internal.log.Log;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.data.engine.fml.var.ContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIOD;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODSTR;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIME;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarDWDM;
import com.jiuqi.nr.data.engine.fml.var.VarDWKJ;
import com.jiuqi.nr.data.engine.fml.var.VarDWMC;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_SRC_TQRQ;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITCODE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITTITLE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_DESCRIPTION;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_GUID;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_NAME;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_TITLE;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.env.DesignReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormulaVariableDefineService;
import com.jiuqi.nr.designer.init.FormulaUpgradeDO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Service;

@Service
public class FormulaDataUpUtils {
    private static final Logger logger = LoggerFactory.getLogger(FormulaDataUpUtils.class);
    private final NRDesignTimeController nrDesignTimeController = (NRDesignTimeController)SpringUtil.getBean(NRDesignTimeController.class);
    private final IDesignTimeViewController designTimeViewController = (IDesignTimeViewController)SpringUtil.getBean(IDesignTimeViewController.class);
    private final DesignFormulaSchemeDefineService designFormulaSchemeDefineService = (DesignFormulaSchemeDefineService)SpringUtil.getBean(DesignFormulaSchemeDefineService.class);
    private final DesignFormulaVariableDefineService designFormulaVariableDefineService = (DesignFormulaVariableDefineService)SpringUtil.getBean(DesignFormulaVariableDefineService.class);
    private final IDataDefinitionDesignTimeController dataDefinitionDesignTimeController = (IDataDefinitionDesignTimeController)SpringUtil.getBean(IDataDefinitionDesignTimeController.class);
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringUtil.getBean(IDataDefinitionRuntimeController.class);

    BatchSqlUpdate getBsuDesign(DataSource dataSource) {
        BatchSqlUpdate bsuDesign = new BatchSqlUpdate(dataSource, "update nr_param_formula_des set fl_expression=?, fl_large_expression=? where fl_key=?");
        bsuDesign.setTypes(new int[]{-9, -16, -9});
        return bsuDesign;
    }

    BatchSqlUpdate getBsuRun(DataSource dataSource) {
        BatchSqlUpdate bsuRun = new BatchSqlUpdate(dataSource, "update nr_param_formula set fl_expression=?, fl_large_expression=? where fl_key=?");
        bsuRun.setTypes(new int[]{-9, -16, -9});
        return bsuRun;
    }

    String transFromDataLinkExpression(ExecutorContext context, Map<String, String> formMap, FormulaUpgradeDO formulaUpgradeDO, DataEngineConsts.FormulaShowType type) {
        String formKey = formulaUpgradeDO.getFormKey();
        String formCode = null;
        if (formKey != null) {
            DesignFormDefine formDefine;
            if (formMap != null) {
                formCode = formMap.get(formKey);
                if (formCode == null && (formDefine = this.nrDesignTimeController.queryFormById(formKey)) != null) {
                    formCode = formDefine.getFormCode();
                    formMap.put(formKey, formCode);
                }
            } else {
                formDefine = this.nrDesignTimeController.queryFormById(formKey);
                if (formDefine != null) {
                    formCode = formDefine.getFormCode();
                }
            }
        }
        String expression = "";
        if (!StringUtils.isEmpty((String)formulaUpgradeDO.getDLExpression())) {
            try {
                expression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)formulaUpgradeDO.getDLExpression(), (String)formCode, (DataEngineConsts.FormulaShowType)type);
            }
            catch (Exception e) {
                logger.error("\u8bbe\u8ba1\u671f\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1aformulaKey\u3010" + formulaUpgradeDO.getFormulaKey() + "\u3011", e);
            }
        }
        return expression;
    }

    String transFromDataLinkExpressionRun(ExecutorContext context, FormulaUpgradeDO formulaUpgradeDO, DataEngineConsts.FormulaShowType type, FormDefine formDefine) {
        String formCode = null;
        if (formDefine != null) {
            formCode = formDefine.getFormCode();
        }
        String expression = "";
        if (!StringUtils.isEmpty((String)formulaUpgradeDO.getDLExpression())) {
            try {
                expression = DataEngineFormulaParser.transFormulaStyle((ExecutorContext)context, (String)formulaUpgradeDO.getDLExpression(), (String)formCode, (DataEngineConsts.FormulaShowType)type);
            }
            catch (Exception e) {
                logger.error("\u8fd0\u884c\u671f\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1aformulaKey\u3010" + formulaUpgradeDO.getFormulaKey() + "\u3011", e);
            }
        }
        return expression;
    }

    void fillExpression(String formulaSchemeKey, List<FormulaUpgradeDO> formulaUpgradeDOS) throws ParseException {
        if (formulaUpgradeDOS == null || formulaUpgradeDOS.size() == 0) {
            return;
        }
        HashMap<String, String> formMap = new HashMap<String, String>();
        ExecutorContext context = this.initContext(formulaSchemeKey);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeKey);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        for (FormulaUpgradeDO formulaUpgradeDO : formulaUpgradeDOS) {
            String expression = this.transFromDataLinkExpression(context, formMap, formulaUpgradeDO, type);
            if (StringUtils.isEmpty((String)expression)) continue;
            formulaUpgradeDO.setExpression(expression);
        }
    }

    ExecutorContext initContext(String formulaSchemeKey) throws ParseException {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFormulaParser formulaParser = context.getCache().getFormulaParser(true);
        formulaParser.unregisterDynamicNodeProvider((IDynamicNodeProvider)ExecutorContext.getPrioritycontextvariablemanager());
        formulaParser.unregisterDynamicNodeProvider((IDynamicNodeProvider)ExecutorContext.getContextvariablemanager());
        PriorityContextVariableManager priorityContextVariableManager = new PriorityContextVariableManager();
        this.initPriority(priorityContextVariableManager);
        ContextVariableManager normalContextVariableManager = new ContextVariableManager();
        this.initNor(normalContextVariableManager);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)normalContextVariableManager);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)priorityContextVariableManager);
        context.setDesignTimeData(true, this.dataDefinitionDesignTimeController);
        DataEngineConsts.FormulaShowType type = this.getFormulaShowTypeByFormulaScheme(formulaSchemeKey);
        context.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
        DesignFormulaSchemeDefine formulaSchemeDefine = this.queryFormulaSchemeDefine(formulaSchemeKey);
        List formulaVariables = this.designFormulaVariableDefineService.queryAllFormulaVariable(formulaSchemeDefine.getFormSchemeKey());
        DesignReportFmlExecEnvironment env = new DesignReportFmlExecEnvironment(this.designTimeViewController, this.dataDefinitionDesignTimeController, formulaSchemeDefine.getFormSchemeKey(), formulaVariables);
        env.setUseCache();
        context.setEnv((IFmlExecEnvironment)env);
        return context;
    }

    DataEngineConsts.FormulaShowType getFormulaShowTypeByFormulaScheme(String schemeKey) {
        DesignFormulaSchemeDefine formulaSchemeDefine = this.queryFormulaSchemeDefine(schemeKey);
        DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formulaSchemeDefine.getFormSchemeKey());
        DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataEngineConsts.FormulaShowType type = DataEngineConsts.FormulaShowType.JQ;
        if (taskDefine.getFormulaSyntaxStyle() == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL) {
            type = DataEngineConsts.FormulaShowType.EXCEL;
        }
        return type;
    }

    DesignFormulaSchemeDefine queryFormulaSchemeDefine(String formulaSchemeKey) {
        DesignFormulaSchemeDefine define = null;
        try {
            define = this.designFormulaSchemeDefineService.queryFormulaSchemeDefine(formulaSchemeKey);
        }
        catch (Exception e) {
            Log.error((Exception)e);
        }
        return define;
    }

    void initPriority(PriorityContextVariableManager priorityContextVariableManager) {
        priorityContextVariableManager.add((Variable)new VarCUR_YEAR());
        priorityContextVariableManager.add((Variable)new VarSYS_YEAR());
        priorityContextVariableManager.add((Variable)new VarCUR_PERIOD());
        priorityContextVariableManager.add((Variable)new VarCUR_TIME());
        priorityContextVariableManager.add((Variable)new VarCUR_PERIODSTR());
        priorityContextVariableManager.add((Variable)new VarSQ());
        priorityContextVariableManager.add((Variable)new VarUSER_GUID());
        priorityContextVariableManager.add((Variable)new VarUSER_NAME());
        priorityContextVariableManager.add((Variable)new VarUSER_TITLE());
        priorityContextVariableManager.add((Variable)new VarUSER_DESCRIPTION());
        priorityContextVariableManager.add((Variable)new VarSYS_UNITCODE());
        priorityContextVariableManager.add((Variable)new VarSYS_UNITTITLE());
        priorityContextVariableManager.add((Variable)new VarSYS_SRC_TQRQ());
    }

    void initNor(ContextVariableManager normalContextVariableManager) {
        normalContextVariableManager.add((Variable)new VarCUR_YEAR());
        normalContextVariableManager.add((Variable)new VarSYS_YEAR());
        normalContextVariableManager.add((Variable)new VarCUR_PERIOD());
        normalContextVariableManager.add((Variable)new VarCUR_TIME());
        normalContextVariableManager.add((Variable)new VarCUR_PERIODSTR());
        normalContextVariableManager.add((Variable)new VarSQ());
        normalContextVariableManager.add((Variable)new VarUSER_GUID());
        normalContextVariableManager.add((Variable)new VarUSER_NAME());
        normalContextVariableManager.add((Variable)new VarUSER_TITLE());
        normalContextVariableManager.add((Variable)new VarUSER_DESCRIPTION());
        normalContextVariableManager.add((Variable)new VarSYS_UNITCODE());
        normalContextVariableManager.add((Variable)new VarSYS_UNITTITLE());
        normalContextVariableManager.add((Variable)new VarDWDM());
        normalContextVariableManager.add((Variable)new VarDWKJ());
        normalContextVariableManager.add((Variable)new VarDWMC());
        normalContextVariableManager.add((Variable)new VarSYS_SRC_TQRQ());
    }

    private class VarSQ
    extends AbstractContextVar {
        private static final long serialVersionUID = 4865474095837209758L;

        public VarSQ() {
            super("SQ", "\u5f53\u524d\u65f6\u671f\u539f\u59cb\u7f16\u7801", 6);
        }

        public Object getVarValue(IContext context) {
            return null;
        }

        public void setVarValue(Object value) {
        }
    }
}

