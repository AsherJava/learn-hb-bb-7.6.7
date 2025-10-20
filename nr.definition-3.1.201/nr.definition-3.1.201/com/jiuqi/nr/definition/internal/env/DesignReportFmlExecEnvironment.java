/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.DesignReportColumnLinkFinder;
import com.jiuqi.nr.definition.internal.env.DesignReportDataLinkFinder;
import com.jiuqi.nr.definition.internal.env.ExecuteFormulaExpression;
import com.jiuqi.nr.definition.internal.env.ItemNodeProvider;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.env.ReportFunContext;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DesignReportFmlExecEnvironment
extends ReportFmlExecEnvironment {
    private static final Logger logger = LoggerFactory.getLogger(DesignReportFmlExecEnvironment.class);
    private IDataLinkFinder datalinkFinder;
    private IPeriodAdapter periodAdapter;
    private int periodType = -1;
    private IDesignTimeViewController controller;
    private IDataDefinitionDesignTimeController designTimeController;
    private Map<String, EntityViewDefine> entityViewDefines;
    private String unitDimension;
    private String formSchemeKey;
    private boolean useCache = false;
    private FormSchemeDefine formSchemeDefine = null;
    private static final int ZLX = 0;
    private static final int SZLX = 0;
    private static final int ZFCLX = 1;
    private static final int GSLX = 1;
    private IDataModelLinkFinder dataModelLinkFinder;

    public DesignReportFmlExecEnvironment(IDesignTimeViewController controller, IDataDefinitionDesignTimeController runtimeController, String formSchemeKey, List<FormulaVariDefine> formulaVariables) {
        super(null, null, null, formSchemeKey);
        this.controller = controller;
        this.designTimeController = runtimeController;
        this.formSchemeKey = formSchemeKey;
        this.setVariableManager(formulaVariables);
    }

    private void setVariableManager(List<FormulaVariDefine> formulaVariables) {
        if (formulaVariables == null) {
            return;
        }
        this.variableManager = this.getVariableManager();
        for (FormulaVariDefine formulaVar : formulaVariables) {
            if (formulaVar.getType() == 0) {
                if (formulaVar.getValueType() == 0) {
                    this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), 3, (Object)formulaVar.getInitValue()));
                    continue;
                }
                if (formulaVar.getValueType() == 1) {
                    this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), 6, (Object)formulaVar.getInitValue()));
                    continue;
                }
                this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), 2, (Object)formulaVar.getInitValue()));
                continue;
            }
            if (formulaVar.getType() == 1) {
                ExecuteFormulaExpression executeFormulaExpression = new ExecuteFormulaExpression(this.designTimeController);
                String formulaExpression = formulaVar.getInitValue();
                int formulaType = executeFormulaExpression.executeFormula(this, formulaExpression);
                this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), formulaType, (Object)formulaExpression));
                continue;
            }
            this.variableManager.add(new Variable(formulaVar.getCode(), formulaVar.getTitle(), 0, (Object)new ArrayData(0, new ArrayList<String>(Arrays.asList(StringUtils.split((String)formulaVar.getInitValue(), (String)","))))));
        }
    }

    @Override
    public IDataLinkFinder getDataLinkFinder() {
        if (this.datalinkFinder == null) {
            DesignReportDataLinkFinder designReportDataLinkFinder = new DesignReportDataLinkFinder(this.controller, this.designTimeController, this.formSchemeKey);
            if (this.useCache) {
                designReportDataLinkFinder.setUseCache();
            }
            this.datalinkFinder = designReportDataLinkFinder;
        }
        return this.datalinkFinder;
    }

    @Override
    public IDataModelLinkFinder getDataModelLinkFinder() {
        if (this.dataModelLinkFinder == null) {
            DesignReportColumnLinkFinder designReportDataLinkFinder = new DesignReportColumnLinkFinder(this.controller, this.designTimeController, this.formSchemeKey);
            if (this.useCache) {
                designReportDataLinkFinder.setUseCache();
            }
            this.dataModelLinkFinder = designReportDataLinkFinder;
        }
        return this.dataModelLinkFinder;
    }

    @Override
    public IPeriodAdapter getPeriodAdapter(ExecutorContext context) {
        if (this.periodAdapter == null) {
            EntityViewDefine periodEntityViewDefine = this.getEntityViewDefine(context, "DATATIME");
            PeriodEngineService periodService = BeanUtil.getBean(PeriodEngineService.class);
            this.periodAdapter = periodService.getPeriodAdapter().getPeriodProvider(periodEntityViewDefine.getEntityId());
        }
        return this.periodAdapter;
    }

    @Override
    public int getPeriodType() {
        if (this.periodType < 0) {
            try {
                FormSchemeDefine formScheme = this.getFormSchemeDefine();
                if (formScheme != null) {
                    PeriodType formSchemePeriodType = formScheme.getPeriodType();
                    if (formSchemePeriodType == FormSchemeExtendPropsDefaultValue.PERIOD_TYPE_EXTEND_VALUE) {
                        DesignTaskDefine designTaskDefine = this.controller.queryTaskDefine(formScheme.getTaskKey());
                        this.periodType = designTaskDefine.getPeriodType().type();
                    } else {
                        this.periodType = formSchemePeriodType.type();
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return this.periodType;
    }

    @Override
    public FormSchemeDefine getFormSchemeDefine() {
        if (this.formSchemeDefine == null) {
            this.formSchemeDefine = new DesignFormSchemeDefineGetterImpl(this.controller.queryFormSchemeDefine(this.formSchemeKey));
        }
        return this.formSchemeDefine;
    }

    @Override
    public TaskDefine getTaskDefine() {
        FormSchemeDefine formScheme = this.getFormSchemeDefine();
        if (formScheme != null) {
            return this.controller.queryTaskDefine(formScheme.getTaskKey());
        }
        return null;
    }

    @Override
    public EntityViewDefine getEntityViewDefine(ExecutorContext context, String dimensionName) {
        if (this.entityViewDefines == null) {
            this.initEntityViewDefines(context);
        }
        return this.entityViewDefines.get(dimensionName);
    }

    private void initEntityViewDefines(ExecutorContext context) {
        try {
            this.entityViewDefines = new HashMap<String, EntityViewDefine>();
            FormSchemeDefine formScheme = this.getFormSchemeDefine();
            String masterViews = this.controller.getFormSchemeEntity(formScheme.getKey());
            if (formScheme != null && StringUtils.isNotEmpty((String)masterViews)) {
                String[] idArray;
                for (String entityViewKey : idArray = masterViews.split(";")) {
                    if (entityViewKey == null) continue;
                    String dimName = context.getCache().getDataDefinitionsCache().getDimensionName(entityViewKey);
                    if (this.unitDimension == null) {
                        this.unitDimension = dimName;
                    }
                    RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                    entityViewDefine.setEntityId(entityViewKey);
                    this.entityViewDefines.put(dimName, (EntityViewDefine)entityViewDefine);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public IContext getExternalContext() {
        return new ReportFunContext();
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public List<IReportDynamicNodeProvider> getDataNodeFinders() {
        ArrayList<IReportDynamicNodeProvider> dataNodeFinders = new ArrayList<IReportDynamicNodeProvider>();
        dataNodeFinders.add((IReportDynamicNodeProvider)this.getVariableManager());
        dataNodeFinders.add(new ItemNodeProvider());
        return dataNodeFinders;
    }

    @Override
    public String getUnitDimesion(ExecutorContext context) {
        if (this.unitDimension == null) {
            this.initEntityViewDefines(context);
        }
        return this.unitDimension;
    }

    public void setUseCache() {
        this.useCache = true;
    }
}

