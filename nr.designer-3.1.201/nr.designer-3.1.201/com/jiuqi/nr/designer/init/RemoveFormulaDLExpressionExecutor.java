/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.data.engine.fml.var.ContextVariableManager
 *  com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.object.BatchSqlUpdate
 */
package com.jiuqi.nr.designer.init;

import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.engine.fml.var.ContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.dao.DesignFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormSchemeDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormulaSchemeDefineDao;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.service.DesignFormulaSchemeDefineService;
import com.jiuqi.nr.designer.init.FormulaDataUpUtils;
import com.jiuqi.nr.designer.init.FormulaRowMapper;
import com.jiuqi.nr.designer.init.FormulaUpgradeDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.util.StringUtils;

public class RemoveFormulaDLExpressionExecutor
implements CustomClassExecutor {
    private static final int MAX_EXPRESSION_LENGTH = 1500;
    private static final int MAX_THREAD_NUM = 5;
    private static final Logger logger = LoggerFactory.getLogger(RemoveFormulaDLExpressionExecutor.class);
    private final DesignFormulaSchemeDefineService designFormulaSchemeDefineService = (DesignFormulaSchemeDefineService)SpringUtil.getBean(DesignFormulaSchemeDefineService.class);
    private final DesignFormSchemeDefineDao designFormSchemeDefineDao = (DesignFormSchemeDefineDao)SpringUtil.getBean(DesignFormSchemeDefineDao.class);
    private final RunTimeFormulaSchemeDefineDao runTimeFormulaSchemeDefineDao = (RunTimeFormulaSchemeDefineDao)SpringUtil.getBean(RunTimeFormulaSchemeDefineDao.class);
    private final RunTimeFormSchemeDefineDao runTimeFormSchemeDefineDao = (RunTimeFormSchemeDefineDao)SpringUtil.getBean(RunTimeFormSchemeDefineDao.class);
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringUtil.getBean(IDataDefinitionRuntimeController.class);
    private final IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringUtil.getBean(IRunTimeViewController.class);
    private final IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringUtil.getBean(IEntityViewRunTimeController.class);
    private final JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringUtil.getBean(JdbcTemplate.class);
    private final FormulaDataUpUtils formulaDataUpUtils = (FormulaDataUpUtils)SpringUtil.getBean(FormulaDataUpUtils.class);

    public void execute(DataSource dataSource) throws Exception {
        long begin = System.currentTimeMillis();
        logger.info("\u5f00\u59cb\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u5347\u7ea7");
        String queryDesignSql = "select fl_key,fl_form_key, fl_scheme_key, fl_datalink_expression from nr_param_formula_des";
        List allFormulasDesign = this.jdbcTemplate.query(queryDesignSql, (RowMapper)new FormulaRowMapper());
        String queryRunSql = "select fl_key,fl_form_key, fl_scheme_key, fl_datalink_expression from nr_param_formula";
        List allFormulasRun = this.jdbcTemplate.query(queryRunSql, (RowMapper)new FormulaRowMapper());
        logger.info("\u8bbe\u8ba1\u671f\u53c2\u6570\u51c6\u5907");
        List allFormSchemeDefinesDes = this.designFormSchemeDefineDao.list();
        Map<String, List<DesignFormSchemeDefine>> desFormSchemeMapByTask = allFormSchemeDefinesDes.stream().collect(Collectors.groupingBy(FormSchemeDefine::getTaskKey));
        List allFormulaSchemeDefinesDes = this.designFormulaSchemeDefineService.queryAllFormulaSchemeDefine();
        Map<String, List<DesignFormulaSchemeDefine>> desFormulaSchemeMapByFormScheme = allFormulaSchemeDefinesDes.stream().collect(Collectors.groupingBy(FormulaSchemeDefine::getFormSchemeKey));
        Map<String, List<FormulaUpgradeDO>> desFormulasMapByFormulaScheme = allFormulasDesign.stream().collect(Collectors.groupingBy(FormulaUpgradeDO::getFormulaSchemeKey));
        HashMap desFormulasMapByTask = new HashMap();
        for (Map.Entry<String, List<DesignFormSchemeDefine>> entry : desFormSchemeMapByTask.entrySet()) {
            ArrayList list = new ArrayList();
            for (DesignFormSchemeDefine designFormSchemeDefine : entry.getValue()) {
                for (DesignFormulaSchemeDefine designFormulaSchemeDefine : desFormulaSchemeMapByFormScheme.get(designFormSchemeDefine.getKey())) {
                    list.addAll(desFormulasMapByFormulaScheme.get(designFormulaSchemeDefine.getKey()));
                }
            }
            desFormulasMapByTask.put(entry.getKey(), list);
        }
        logger.info("\u8fd0\u884c\u671f\u53c2\u6570\u51c6\u5907");
        List allFormSchemeDefinesRun = this.runTimeFormSchemeDefineDao.list();
        Map<String, List<FormSchemeDefine>> runFormSchemeMapByTask = allFormSchemeDefinesRun.stream().collect(Collectors.groupingBy(FormSchemeDefine::getTaskKey));
        List allFormulaSchemeDefinesRun = this.runTimeFormulaSchemeDefineDao.list();
        Map<String, List<FormulaSchemeDefine>> runFormulaSchemeMapByFormScheme = allFormulaSchemeDefinesRun.stream().collect(Collectors.groupingBy(FormulaSchemeDefine::getFormSchemeKey));
        Map<String, List<FormulaUpgradeDO>> runFormulasMapByFormulaScheme = allFormulasRun.stream().collect(Collectors.groupingBy(FormulaUpgradeDO::getFormulaSchemeKey));
        HashMap runFormulasMapByTask = new HashMap();
        for (Map.Entry<String, List<FormSchemeDefine>> entry : runFormSchemeMapByTask.entrySet()) {
            ArrayList list = new ArrayList();
            for (FormSchemeDefine formSchemeDefine : entry.getValue()) {
                for (FormulaSchemeDefine formulaSchemeDefine : runFormulaSchemeMapByFormScheme.get(formSchemeDefine.getKey())) {
                    list.addAll(runFormulasMapByFormulaScheme.get(formulaSchemeDefine.getKey()));
                }
            }
            runFormulasMapByTask.put(entry.getKey(), list);
        }
        logger.info("\u5f00\u59cb\u8bbe\u8ba1\u671f\u548c\u8fd0\u884c\u671f\u53c2\u6570\u591a\u7ebf\u7a0b\u5347\u7ea7");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (Map.Entry entry : desFormulasMapByTask.entrySet()) {
            this.multiThreadDes(executorService, (String)entry.getKey(), (List)entry.getValue(), dataSource);
        }
        for (Map.Entry entry : runFormulasMapByTask.entrySet()) {
            this.multiThreadRun(executorService, (String)entry.getKey(), (List)entry.getValue(), dataSource);
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) break;
            try {
                logger.info("\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u5347\u7ea7\u6b63\u5728\u8fdb\u884c......");
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                logger.error(interruptedException.getMessage(), interruptedException);
            }
        }
        logger.info("\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u5347\u7ea7\u6240\u6709\u4efb\u52a1\u5b50\u7ebf\u7a0b\u6267\u884c\u5b8c\u6bd5");
        logger.info("\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u5347\u7ea7\u5b8c\u6210");
        int count = allFormulasDesign.size() + allFormulasRun.size();
        long end = System.currentTimeMillis();
        long spend = end - begin;
        logger.info("\u5347\u7ea7\u3010" + count + "\u3011\u6761\u516c\u5f0f\u8bb0\u5f55\uff0c\u5171\u8017\u65f6\u3010" + spend + "\u3011\u6beb\u79d2");
    }

    private void designTimeUp(List<FormulaUpgradeDO> allFormulasDesign, BatchSqlUpdate bsuDesign) {
        Map<String, List<FormulaUpgradeDO>> formulasDesignByScheme = allFormulasDesign.stream().collect(Collectors.groupingBy(FormulaUpgradeDO::getFormulaSchemeKey));
        for (Map.Entry<String, List<FormulaUpgradeDO>> entry : formulasDesignByScheme.entrySet()) {
            try {
                this.formulaDataUpUtils.fillExpression(entry.getKey(), entry.getValue());
            }
            catch (Exception e) {
                logger.error("\u8bbe\u8ba1\u671f\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1aformulaSchemeKey\u3010" + entry.getKey() + "\u3011" + e.getMessage(), e);
            }
        }
        ArrayList<FormulaUpgradeDO> allFormulasDesignNew = new ArrayList<FormulaUpgradeDO>();
        for (List<FormulaUpgradeDO> value : formulasDesignByScheme.values()) {
            allFormulasDesignNew.addAll(value);
        }
        for (FormulaUpgradeDO formulaUpgradeDO : allFormulasDesignNew) {
            String expression = formulaUpgradeDO.getExpression();
            if (!StringUtils.hasText(expression)) continue;
            if (expression.length() < 1500) {
                bsuDesign.update(new Object[]{expression, "", formulaUpgradeDO.getFormulaKey()});
                continue;
            }
            bsuDesign.update(new Object[]{"", expression, formulaUpgradeDO.getFormulaKey()});
        }
        bsuDesign.flush();
    }

    private void runTimeUp(List<FormulaUpgradeDO> allFormulasRun, BatchSqlUpdate bsuRun) throws ParseException {
        Map<String, List<FormulaUpgradeDO>> formulasRunByScheme = allFormulasRun.stream().collect(Collectors.groupingBy(FormulaUpgradeDO::getFormulaSchemeKey));
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFormulaParser formulaParser = executorContext.getCache().getFormulaParser(true);
        formulaParser.unregisterDynamicNodeProvider((IDynamicNodeProvider)ExecutorContext.getPrioritycontextvariablemanager());
        formulaParser.unregisterDynamicNodeProvider((IDynamicNodeProvider)ExecutorContext.getContextvariablemanager());
        PriorityContextVariableManager priorityContextVariableManager = new PriorityContextVariableManager();
        this.formulaDataUpUtils.initPriority(priorityContextVariableManager);
        ContextVariableManager normalContextVariableManager = new ContextVariableManager();
        this.formulaDataUpUtils.initNor(normalContextVariableManager);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)normalContextVariableManager);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)priorityContextVariableManager);
        for (Map.Entry<String, List<FormulaUpgradeDO>> entry : formulasRunByScheme.entrySet()) {
            String formulaSchemeKey = entry.getKey();
            DataEngineConsts.FormulaShowType type = this.formulaDataUpUtils.getFormulaShowTypeByFormulaScheme(formulaSchemeKey);
            executorContext.setJQReportModel(type == DataEngineConsts.FormulaShowType.JQ);
            for (FormulaUpgradeDO formulaUpgradeDO : entry.getValue()) {
                try {
                    String formula;
                    FormDefine form = this.runTimeViewController.queryFormById(formulaUpgradeDO.getFormKey());
                    if (form != null) {
                        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, form.getFormScheme());
                        executorContext.setEnv((IFmlExecEnvironment)environment);
                        executorContext.setDefaultGroupName(form.getFormCode());
                    }
                    if (!StringUtils.hasText(formula = this.formulaDataUpUtils.transFromDataLinkExpressionRun(executorContext, formulaUpgradeDO, type, form))) continue;
                    if (formula.length() < 1500) {
                        bsuRun.update(new Object[]{formula, "", formulaUpgradeDO.getFormulaKey()});
                        continue;
                    }
                    bsuRun.update(new Object[]{"", formula, formulaUpgradeDO.getFormulaKey()});
                }
                catch (Exception e) {
                    logger.error("\u8fd0\u884c\u671f\u516c\u5f0f\u79fb\u9664\u4e2d\u95f4\u683c\u5f0f\u53c2\u6570\u89e3\u6790\u5931\u8d25\uff1aformulaKey\u3010" + formulaUpgradeDO.getFormulaKey() + "\u3011", e);
                }
            }
        }
        bsuRun.flush();
    }

    private void multiThreadDes(ExecutorService executorService, String taskKey, List<FormulaUpgradeDO> allFormulasDesByTask, DataSource dataSource) {
        executorService.execute(() -> {
            logger.info("\u8bbe\u8ba1\u671ftask\u7ebf\u7a0b\u3010{}\u3011\u542f\u52a8", (Object)taskKey);
            this.designTimeUp(allFormulasDesByTask, this.formulaDataUpUtils.getBsuDesign(dataSource));
        });
    }

    private void multiThreadRun(ExecutorService executorService, String taskKey, List<FormulaUpgradeDO> allFormulasRunByTask, DataSource dataSource) {
        executorService.execute(() -> {
            try {
                logger.info("\u8fd0\u884c\u671ftask\u7ebf\u7a0b\u3010{}\u3011\u542f\u52a8", (Object)taskKey);
                this.runTimeUp(allFormulasRunByTask, this.formulaDataUpUtils.getBsuRun(dataSource));
            }
            catch (ParseException e) {
                logger.error("\u8fd0\u884c\u671f\u53c2\u6570\u5347\u7ea7\u7ebf\u7a0b\u3010{}\u3011\u5f02\u5e38", (Object)taskKey);
            }
        });
    }
}

