/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.var.ContextVariableManagerProvider;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorContext
implements IContext {
    private static final Logger logger = LoggerFactory.getLogger(ExecutorContext.class);
    private boolean useDnaSql;
    private IDataDefinitionRuntimeController runtimeController;
    private DefinitionsCache cache;
    private boolean recordDataChange;
    public static final ContextVariableManagerProvider contextVariableManagerProvider = new ContextVariableManagerProvider();
    private boolean JQReportModel = true;
    private FormulaShowInfo formulaShowInfo;
    private IFmlExecEnvironment env;
    private String defaultGroupName;
    private DimensionValueSet varDimensionValueSet;
    private IPeriodAdapter periodProvider;
    private boolean designTimeData;
    private VariableManager variableManager;
    protected String periodView;
    protected String orgEntityId;
    protected String currentEntityId;
    protected String unitDimension = null;
    protected boolean autoDataMasking = false;

    public ExecutorContext(IDataDefinitionRuntimeController runtimeController) {
        this.runtimeController = runtimeController;
    }

    public boolean isUseDnaSql() {
        return this.useDnaSql;
    }

    public void setUseDnaSql(boolean useDnaSql) {
        this.useDnaSql = useDnaSql;
    }

    public IDataDefinitionRuntimeController getRuntimeController() {
        return this.runtimeController;
    }

    public DefinitionsCache getCache() throws ParseException {
        if (this.cache == null) {
            this.cache = new DefinitionsCache(this);
        }
        return this.cache;
    }

    public void setDesignTimeData(boolean designTimeData, IDataDefinitionDesignTimeController designTimeController) throws ParseException {
        this.designTimeData = designTimeData;
        if (designTimeData && designTimeController != null) {
            if (this.cache == null) {
                this.cache = new DefinitionsCache(this);
                this.cache.setDesignTimeController(designTimeController);
            } else {
                this.cache = new DefinitionsCache(this, this.cache, designTimeController);
            }
        } else {
            this.cache = this.cache == null ? new DefinitionsCache(this) : new DefinitionsCache(this, this.cache, null);
        }
    }

    public boolean isRecordDataChange() {
        return this.recordDataChange;
    }

    public void setRecordDataChange(boolean recordDataChange) {
        this.recordDataChange = recordDataChange;
    }

    @Deprecated
    public IDataLinkFinder getDataLinkFinder() {
        return this.env == null ? null : this.env.getDataLinkFinder();
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        return this.env == null ? null : this.env.getDataModelLinkFinder();
    }

    public boolean isJQReportModel() {
        return this.JQReportModel;
    }

    public void setJQReportModel(boolean jQReportModel) {
        this.JQReportModel = jQReportModel;
    }

    public FormulaShowInfo getFormulaShowInfo() {
        if (this.formulaShowInfo == null) {
            DataEngineConsts.FormulaShowType formulaShowType = this.isJQReportModel() ? DataEngineConsts.FormulaShowType.JQ : DataEngineConsts.FormulaShowType.EXCEL;
            this.formulaShowInfo = new FormulaShowInfo(formulaShowType);
        }
        return this.formulaShowInfo;
    }

    public void setFormulaShowInfo(FormulaShowInfo formulaShowInfo) {
        this.formulaShowInfo = formulaShowInfo;
    }

    public VariableManager getVariableManager() {
        if (this.env != null) {
            return this.env.getVariableManager();
        }
        if (this.variableManager == null) {
            try {
                this.variableManager = new VariableManager();
                this.registerDynamicDataProvider(this.variableManager);
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
        return this.variableManager;
    }

    public List<Variable> getAllVars() {
        VariableManager variableManager;
        ArrayList<Variable> allVars = new ArrayList<Variable>();
        IReportDynamicNodeProvider normalContextVariableManager = contextVariableManagerProvider.getNormalContextVariableManager();
        if (normalContextVariableManager != null && normalContextVariableManager instanceof VariableManagerBase) {
            allVars.addAll(((VariableManagerBase)normalContextVariableManager).getAllVars());
        }
        if ((variableManager = this.getVariableManager()) != null) {
            allVars.addAll(variableManager.getAllVars());
        }
        return allVars;
    }

    public IPeriodAdapter getPeriodAdapter() {
        if (this.periodProvider == null) {
            if (this.env != null) {
                try {
                    this.periodProvider = this.env.getPeriodAdapter(this);
                }
                catch (Exception e) {
                    this.periodProvider = new DefaultPeriodAdapter();
                }
            } else {
                this.periodProvider = new DefaultPeriodAdapter();
            }
        }
        return this.periodProvider;
    }

    public boolean isStandardPeriod(int periodType) {
        return periodType != PeriodType.CUSTOM.type() && this.getPeriodType() != PeriodType.WEEK.type() && periodType != PeriodType.MONTH.type();
    }

    public String getUnitDimension() {
        if (this.unitDimension == null && this.env != null) {
            this.unitDimension = this.env.getUnitDimesion(this);
        }
        return this.unitDimension;
    }

    public IFmlExecEnvironment getEnv() {
        return this.env;
    }

    public void setEnv(IFmlExecEnvironment env) {
        this.env = env;
        try {
            if (env.getDataNodeFinders() != null) {
                for (IReportDynamicNodeProvider dataNodeProvider : env.getDataNodeFinders()) {
                    this.registerDynamicDataProvider(dataNodeProvider);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public int getPeriodType() {
        return this.env == null ? -1 : this.env.getPeriodType();
    }

    public void registerFunctionProvider(IFunctionProvider functionProvider) throws ParseException {
        ReportFormulaParser parser = this.getCache().getFormulaParser(true);
        parser.registerFunctionProvider(functionProvider);
        parser = this.getCache().getFormulaParser(false);
        parser.registerFunctionProvider(functionProvider);
    }

    public void registerDynamicDataProvider(IReportDynamicNodeProvider dataProvider) throws ParseException {
        ReportFormulaParser parser = this.getCache().getFormulaParser(true);
        parser.registerDynamicNodeProvider(dataProvider);
        parser = this.getCache().getFormulaParser(false);
        parser.registerDynamicNodeProvider(dataProvider);
    }

    public String getDefaultGroupName() {
        return this.defaultGroupName;
    }

    public void setDefaultGroupName(String defaultGroupName) {
        this.defaultGroupName = defaultGroupName;
    }

    public DimensionValueSet getVarDimensionValueSet() {
        return this.varDimensionValueSet;
    }

    public void setVarDimensionValueSet(DimensionValueSet varDimensionValueSet) {
        this.varDimensionValueSet = varDimensionValueSet;
    }

    public static IReportDynamicNodeProvider getContextvariablemanager() {
        return contextVariableManagerProvider.getNormalContextVariableManager();
    }

    public static IReportDynamicNodeProvider getPrioritycontextvariablemanager() {
        return contextVariableManagerProvider.getPriorityContextVariableManager();
    }

    public boolean isDesignTimeData() {
        return this.designTimeData;
    }

    public void setPeriodProvider(IPeriodAdapter periodProvider) {
        this.periodProvider = periodProvider;
    }

    public String getPeriodView() {
        EntityViewDefine periodEntityView;
        if (StringUtils.isEmpty((String)this.periodView) && this.env != null && (periodEntityView = this.env.getEntityViewDefine(this, "DATATIME")) != null) {
            this.periodView = periodEntityView.getEntityId();
        }
        return this.periodView;
    }

    public void setPeriodView(String periodView) {
        this.periodView = periodView;
    }

    public String getOrgEntityId() {
        return this.orgEntityId;
    }

    public void setOrgEntityId(String orgEntityId) {
        this.orgEntityId = orgEntityId;
    }

    public String getCurrentEntityId() {
        return this.currentEntityId;
    }

    public void setCurrentEntityId(String currentEntityId) {
        this.currentEntityId = currentEntityId;
    }

    public void setCache(DefinitionsCache cache) {
        this.cache = cache;
    }

    public boolean isAutoDataMasking() {
        return this.autoDataMasking;
    }

    public void setAutoDataMasking(boolean autoDataMasking) {
        this.autoDataMasking = autoDataMasking;
    }
}

