/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.BIDataSetFieldInfo
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.query.authority.AuthFailedException
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.ExprExecNetwork
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimHelper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.framework.parameter.ParameterCalculator
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.bi.dataset.report.query;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.model.AdvancedConfig;
import com.jiuqi.bi.dataset.report.model.PeriodChangeMode;
import com.jiuqi.bi.dataset.report.model.ReportDSField;
import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.UnitChangeMode;
import com.jiuqi.bi.dataset.report.query.ReportEvalMonitor;
import com.jiuqi.bi.dataset.report.query.ReportEvalResultSet;
import com.jiuqi.bi.dataset.report.query.ReportQueryContext;
import com.jiuqi.bi.dataset.report.query.network.EvalExprExecNetwork;
import com.jiuqi.bi.dataset.report.query.network.EvalExprItem;
import com.jiuqi.bi.dataset.report.query.network.EvalExprRegionCreator;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo;
import com.jiuqi.bi.dataset.report.tree.UnitDetailTreeService;
import com.jiuqi.bi.publicparam.datasource.caliberdim.NrCaliberDimDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.util.ParamUtils;
import com.jiuqi.bi.query.authority.AuthFailedException;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryExecutorProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimHelper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportQueryExecutor {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private UnitDetailTreeService unitDetailTreeService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private ReportDSModelBuilder modelBuilder;
    @Autowired(required=false)
    public DBQueryExecutorProvider dbQueryExecutorProvider;
    private static final String DEFAULT_PRARM_PREFIX = "__DEFAULT__";
    private static final String DEFAULT_PRARM_PERIOD = "__DEFAULT__PERIOD";

    public int runQuery(ReportDSModel dsModel, IDSContext dsContext, int pageSize, int currentPage, MemoryDataSet<BIDataSetFieldInfo> result) throws BIDataSetException {
        ReportQueryContext context = null;
        try (TempResource resource = new TempResource();){
            context = this.createContext(dsModel, dsContext, resource);
            if (context != null) {
                this.checkAuth(context, dsModel);
                int totoalCount = this.evalByExprExecNetwork(dsModel, context);
                context.getResultSet().toResult(result, pageSize, currentPage);
                this.setFixRefDims(dsModel, result);
                context.getMonitor().debug("\u62a5\u8868\u6570\u636e\u96c6[" + dsModel.getName() + "|" + dsModel.getTitle() + "]\u67e5\u8be2\u7ed3\u679c\uff1a\n" + result, null);
                int n = totoalCount;
                return n;
            }
        }
        catch (IOException e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
        finally {
            if (context != null) {
                context.getQueryParam().closeConnection();
                context.getMonitor().finish();
            }
        }
        return 0;
    }

    private void checkAuth(ReportQueryContext context, ReportDSModel dsModel) throws AuthFailedException {
        QueryFields queryFields = new QueryFields();
        HashSet<String> fieldKeys = new HashSet<String>();
        Set canReadFields = null;
        try {
            for (DSField dsField : dsModel.getFields()) {
                ReportDSField reportDSField = (ReportDSField)dsField;
                IExpression exp = reportDSField.getEvalExpression();
                if (exp == null) continue;
                ExpressionUtils.recursiveGetQueryFields((QueryContext)context, (IASTNode)exp, (QueryFields)queryFields, null, null);
            }
            for (QueryField queryField : queryFields) {
                String dataFieldKey;
                DataField dataField;
                DataFieldDeployInfo deployInfo = this.dataSchemeService.getDeployInfoByColumnKey(queryField.getUID());
                if (null == deployInfo || (dataField = this.dataSchemeService.getDataField(dataFieldKey = deployInfo.getDataFieldKey())).getDataFieldKind() != DataFieldKind.FIELD && dataField.getDataFieldKind() != DataFieldKind.FIELD_ZB) continue;
                fieldKeys.add(dataFieldKey);
            }
            canReadFields = this.definitionAuthorityProvider.canReadFields(new ArrayList(fieldKeys));
        }
        catch (Exception e) {
            context.getMonitor().exception(e);
        }
        if (canReadFields != null && canReadFields.size() < fieldKeys.size()) {
            fieldKeys.removeAll(canReadFields);
            HashMap noAuths = new HashMap();
            StringBuilder msg = new StringBuilder("\u5b58\u5728\u65e0\u8bbf\u95ee\u6743\u9650\u7684\u6307\u6807: ");
            msg.append("[");
            fieldKeys.forEach(fieldKey -> {
                DataField dataField = this.dataSchemeService.getDataField(fieldKey);
                DataTable dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey());
                noAuths.put(dataTable.getCode() + "." + dataField.getCode(), dataField.getTitle());
                msg.append(dataTable.getCode()).append(".").append(dataField.getCode()).append(",");
            });
            msg.setLength(msg.length() - 1);
            msg.append("]");
            throw new AuthFailedException(msg.toString(), null, noAuths);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PreviewResultVo preview(ReportDsModelDefine dsModelDefine, int pageSize, int currentPage) throws Exception {
        PreviewResultVo resultVo = new PreviewResultVo();
        ReportDSModel dsModel = new ReportDSModel();
        dsModel.setReportDsModelDefine(dsModelDefine);
        this.modelBuilder.buildModel(dsModel);
        ReportQueryContext context = null;
        try (TempResource resource = new TempResource();){
            context = this.createContext(dsModel, null, resource);
            if (context != null) {
                this.checkAuth(context, dsModel);
                this.evalByExprExecNetwork(dsModel, context);
                context.getResultSet().toPreviewResult(resultVo, pageSize, currentPage);
            }
        }
        finally {
            if (context != null) {
                context.getQueryParam().closeConnection();
                context.getMonitor().finish();
            }
        }
        return resultVo;
    }

    private void setFixRefDims(ReportDSModel dsModel, MemoryDataSet<BIDataSetFieldInfo> result) {
        for (int i = 0; i < dsModel.getFields().size(); ++i) {
            BIDataSetFieldInfo info;
            DSField dsField = (DSField)dsModel.getFields().get(i);
            ReportDSField reportDSField = (ReportDSField)dsField;
            if (!reportDSField.isFixZb() || (info = (BIDataSetFieldInfo)result.getMetadata().getColumn(i).getInfo()) == null) continue;
            info.getRefDimCols().clear();
            info.getRefDimCols().addAll(dsModel.getPublicDimFields());
        }
    }

    private int evalByExprExecNetwork(ReportDSModel dsModel, ReportQueryContext context) throws BIDataSetException {
        try {
            ReportEvalResultSet resultSet = new ReportEvalResultSet();
            context.setResultSet(resultSet);
            Object periodValue = context.getMasterKeys().getValue("DATATIME");
            ArrayList<String> periods = null;
            if (periodValue instanceof List) {
                List values = (List)periodValue;
                if (values.size() == 1) {
                    context.getMasterKeys().setValue("DATATIME", values.get(0));
                    context.setMasterKeys(context.getMasterKeys());
                    context.getExeContext().getVarDimensionValueSet().setValue("DATATIME", values.get(0));
                } else if (values.size() > 1) {
                    periods = new ArrayList<String>(values.size());
                    for (Object v : values) {
                        periods.add((String)v);
                    }
                }
            }
            if (StringUtils.isNotEmpty((String)dsModel.getReportDsModelDefine().getGatherSchemeCode())) {
                this.unitDetailTreeService.addGatherSchemeVariables(dsModel, context.getExeContext().getVariableManager());
            }
            this.evalToResultSet(dsModel, context, resultSet, periods);
            if (dsModel.isShowDetail()) {
                DimensionValueSet detailMarsterKeys = new DimensionValueSet(context.getMasterKeys());
                detailMarsterKeys.setValue(dsModel.getUnitEntityDefnie().getDimensionName(), context.getSortedUnitTree().getAllDetailUnits());
                ReportQueryContext newContext = this.cloneContext(context, detailMarsterKeys);
                newContext.getTempAssistantTables().clear();
                this.tryCreateTempTable(detailMarsterKeys, newContext);
                newContext.setResultSet(resultSet);
                this.unitDetailTreeService.clearGatherSchemeVariables(newContext.getExeContext().getVariableManager());
                this.evalToResultSet(dsModel, newContext, resultSet, periods);
            }
            resultSet.sort(dsModel.getSortFieldIndexes());
            return resultSet.size();
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }

    private void evalToResultSet(ReportDSModel dsModel, ReportQueryContext context, ReportEvalResultSet resultSet, List<String> periods) throws Exception, ExpressionException, SyntaxException, ParseException, ExecuteException {
        if (periods != null) {
            for (String period : periods) {
                DimensionValueSet masterKeys = new DimensionValueSet(context.getMasterKeys());
                masterKeys.setValue("DATATIME", (Object)period);
                ReportQueryContext newContext = this.cloneContext(context, masterKeys);
                newContext.setResultSet(resultSet);
                this.evalByExprExecNetwork(dsModel, newContext, resultSet);
            }
        } else {
            this.evalByExprExecNetwork(dsModel, context, resultSet);
        }
    }

    protected void evalByExprExecNetwork(ReportDSModel dsModel, ReportQueryContext context, ReportEvalResultSet resultSet) throws ExpressionException, SyntaxException, ParseException, ExecuteException, Exception {
        EvalExprRegionCreator regionCreator = new EvalExprRegionCreator(context, false, context.getQueryParam());
        EvalExprExecNetwork execNetwork = this.createEvalExecNetwork(dsModel, context, resultSet, regionCreator);
        execNetwork.initialize(context.getMonitor());
        execNetwork.checkRunTask(context.getMonitor());
    }

    private EvalExprExecNetwork createEvalExecNetwork(ReportDSModel dsModel, ReportQueryContext context, ReportEvalResultSet resultSet, EvalExprRegionCreator regionCreator) throws ExpressionException, SyntaxException, ParseException {
        EvalExprExecNetwork execNetwork = new EvalExprExecNetwork(context, regionCreator);
        this.arrangeEvalExpressions(context, dsModel, resultSet, execNetwork);
        IExpression conditionExp = context.getConditionExp();
        if (conditionExp != null) {
            EvalExprItem conditionExprItem = new EvalExprItem(conditionExp, conditionExp.getType((IContext)context));
            execNetwork.arrangeEvalExpression(conditionExp);
            resultSet.setCondition(conditionExprItem);
        }
        return execNetwork;
    }

    private void arrangeEvalExpressions(ReportQueryContext context, ReportDSModel dsModel, ReportEvalResultSet resultSet, ExprExecNetwork execNetwork) throws ExpressionException, SyntaxException {
        boolean resultSetInited = resultSet.getColumnCount() > 0;
        for (DSField dsField : dsModel.getFields()) {
            ReportDSField reportDSField = (ReportDSField)dsField;
            IExpression evalExpression = reportDSField.getEvalExpression();
            if (evalExpression == null) {
                evalExpression = new Expression(null, (IASTNode)new DataNode(null, reportDSField.getValType(), null));
            }
            execNetwork.arrangeEvalExpression(evalExpression);
            if (resultSetInited) continue;
            EvalExprItem evalExprItem = new EvalExprItem(evalExpression, reportDSField.getIndex(), reportDSField.getValType());
            resultSet.addEvalExprItem(evalExprItem);
        }
    }

    private String buildMasterKeys(DimensionValueSet masterKeys, ReportDSModel dsModel, IDSContext dsContext) throws Exception {
        if (dsContext == null) {
            return this.buildDefaultMasterKeys(masterKeys, dsModel);
        }
        String defaultPeriod = null;
        List<ParameterModel> parameterModels = this.getParaModels(dsModel);
        for (ParameterModel parameterModel : parameterModels) {
            AbstractParameterDataSourceModel dataSourceModel = parameterModel.getDatasource();
            List values = null;
            String paramName = parameterModel.getName().toUpperCase();
            if (!paramName.startsWith(DEFAULT_PRARM_PREFIX)) {
                values = dsContext.getEnhancedParameterEnv().getValueAsList(paramName);
            }
            if (values == null || values.isEmpty()) {
                ParameterCalculator calculator = new ParameterCalculator(NpContextHolder.getContext().getUserId(), parameterModels);
                ParameterResultset pResultset = calculator.getCandidateValue(parameterModel.getName());
                values = pResultset.getValueAsList();
            }
            if (dataSourceModel instanceof NrPeriodDataSourceModel) {
                NrPeriodDataSourceModel nrPeriodDataSourceModel = (NrPeriodDataSourceModel)dataSourceModel;
                PeriodType periodType = PeriodType.fromType((int)nrPeriodDataSourceModel.getPeriodType());
                if (values == null) continue;
                if (values.size() == 1) {
                    String period = (String)values.get(0);
                    defaultPeriod = period = TimeDimUtils.timeKeyToPeriod((String)period, (PeriodType)periodType);
                    masterKeys.setValue("DATATIME", (Object)period);
                    continue;
                }
                if (values.size() <= 1) continue;
                ArrayList<String> periods = new ArrayList<String>(values.size());
                if (parameterModel.getSelectMode() == ParameterSelectMode.RANGE && values.size() == 2) {
                    String startPeriod = TimeDimUtils.timeKeyToPeriod((String)((String)values.get(0)), (PeriodType)periodType);
                    String endPeriod = TimeDimUtils.timeKeyToPeriod((String)((String)values.get(1)), (PeriodType)periodType);
                    IPeriodProvider periodProvider = this.getPeriodProvider(nrPeriodDataSourceModel, periodType);
                    periods.addAll(TimeDimHelper.getPeiodStrList((IPeriodAdapter)periodProvider, (PeriodWrapper)new PeriodWrapper(startPeriod), (PeriodWrapper)new PeriodWrapper(endPeriod)));
                } else {
                    for (Object value : values) {
                        String period = TimeDimUtils.timeKeyToPeriod((String)((String)value), (PeriodType)periodType);
                        periods.add(period);
                    }
                }
                ParameterDataSourceContext pContext = new ParameterDataSourceContext(parameterModel, new ParameterCalculator(NpContextHolder.getContext().getUserId(), parameterModels));
                IParameterDataSourceProvider periodDataSourceProvider = ParameterDataSourceManager.getInstance().getFactory(dataSourceModel.getType()).create(dataSourceModel);
                ParameterResultset pResultset = periodDataSourceProvider.getDefaultValue(pContext);
                if (!pResultset.isEmpty()) {
                    defaultPeriod = TimeDimUtils.timeKeyToPeriod((String)((String)pResultset.get(pResultset.size() - 1).getValue()), (PeriodType)periodType);
                }
                if (defaultPeriod == null) {
                    Collections.sort(periods);
                    defaultPeriod = (String)periods.get(periods.size() - 1);
                }
                masterKeys.setValue("DATATIME", periods);
                continue;
            }
            if (dataSourceModel instanceof NrEntityDataSourceModel) {
                if (values == null || values.size() <= 0) continue;
                String entityId = ((NrEntityDataSourceModel)dataSourceModel).getEntityViewId();
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                String dimName = entityDefine.getDimensionName();
                if (dsModel.getGaterDimName() != null && dsModel.getGaterDimName().equals(entityId)) {
                    dimName = dsModel.getUnitEntityDefnie().getDimensionName();
                }
                masterKeys.setValue(dimName, (Object)values);
                continue;
            }
            if (!(dataSourceModel instanceof NrCaliberDimDataSourceModel) || values == null || values.size() <= 0) continue;
            String dimName = dsModel.getUnitEntityDefnie().getDimensionName();
            masterKeys.setValue(dimName, (Object)values);
        }
        return defaultPeriod;
    }

    private IPeriodProvider getPeriodProvider(NrPeriodDataSourceModel nrPeriodDataSourceModel, PeriodType periodType) {
        String periodEntityKey = nrPeriodDataSourceModel.getEntityViewId();
        if (StringUtils.isEmpty((String)periodEntityKey)) {
            periodEntityKey = String.valueOf((char)periodType.code());
        }
        return this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityKey);
    }

    private List<ParameterModel> getParaModels(ReportDSModel dsModel) {
        ArrayList<ParameterModel> parameterModels = new ArrayList<ParameterModel>(dsModel.getParameterModels());
        List dataDimensions = this.dataSchemeService.getDataSchemeDimension(dsModel.getTaskDefine().getDataScheme());
        for (DataDimension dim : dataDimensions) {
            ParameterModel parameterModel;
            String dimKey;
            String dimension = dimKey = dim.getDimKey();
            DimensionType dimensionType = dim.getDimensionType();
            if (StringUtils.isNotEmpty((String)dimKey)) {
                dimension = this.dimensionProvider.getDimensionNameByEntityId(dimKey);
            }
            if ((parameterModel = dsModel.getDimParamMap().get(dimension)) != null) continue;
            if (dimKey != null) {
                if (dimKey.equals("ADJUST")) {
                    throw new UnsupportedOperationException("\u62a5\u8868\u6570\u636e\u96c6\u6682\u4e0d\u652f\u6301\u8c03\u6574\u671f\u4e1a\u52a1");
                }
                if (dimensionType == DimensionType.PERIOD) {
                    parameterModel = ParamUtils.createPeriodParamByDimension(dsModel.getTaskDefine(), DEFAULT_PRARM_PERIOD, this.periodEngineService, dim, false);
                } else if (dimensionType == DimensionType.UNIT) {
                    parameterModel = ParamUtils.createUnitParamByDimension(dsModel, DEFAULT_PRARM_PREFIX + dimension, this.entityMetaService, dim);
                } else if (dimensionType == DimensionType.DIMENSION) {
                    IEntityModel entityModel;
                    IEntityAttribute entityAttribute;
                    boolean visiable = true;
                    if (StringUtils.isNotEmpty((String)dim.getDimAttribute()) && !(entityAttribute = (entityModel = this.entityMetaService.getEntityModel(dsModel.getUnitEntityDefnie().getId())).getAttribute(dim.getDimAttribute())).isMultival()) {
                        visiable = false;
                    }
                    if (visiable) {
                        parameterModel = ParamUtils.createEntityParamByDimension(dsModel.getTaskDefine(), DEFAULT_PRARM_PREFIX + dimension, this.entityMetaService, dim);
                    }
                }
            }
            if (parameterModel == null) continue;
            parameterModels.add(parameterModel);
        }
        return parameterModels;
    }

    private String buildDefaultMasterKeys(DimensionValueSet masterKeys, ReportDSModel dsModel) throws Exception {
        String defaultPeriod = null;
        List<ParameterModel> parameterModels = this.getParaModels(dsModel);
        for (ParameterModel parameterModel : parameterModels) {
            List values;
            AbstractParameterDataSourceModel dataSourceModel = parameterModel.getDatasource();
            ParameterDataSourceContext pContext = new ParameterDataSourceContext(parameterModel, new ParameterCalculator(NpContextHolder.getContext().getUserId(), parameterModels));
            String paramName = parameterModel.getName();
            ParameterResultset pResultset = pContext.getCalculator().getValue(paramName, true);
            if (paramName.startsWith(DEFAULT_PRARM_PREFIX) || pResultset.isEmpty()) {
                pResultset = pContext.getCalculator().getCandidateValue(paramName);
            }
            if ((values = pResultset.getValueAsList()) == null || values.size() <= 0) continue;
            if (dataSourceModel instanceof NrPeriodDataSourceModel) {
                NrPeriodDataSourceModel nrPeriodDataSourceModel = (NrPeriodDataSourceModel)dataSourceModel;
                PeriodType periodType = PeriodType.fromType((int)nrPeriodDataSourceModel.getPeriodType());
                ArrayList<String> periods = new ArrayList<String>(values.size());
                if (parameterModel.getSelectMode() == ParameterSelectMode.RANGE && values.size() == 2) {
                    String startPeriod = TimeDimUtils.timeKeyToPeriod((String)((String)values.get(0)), (PeriodType)periodType);
                    String endPeriod = TimeDimUtils.timeKeyToPeriod((String)((String)values.get(1)), (PeriodType)periodType);
                    IPeriodProvider periodProvider = this.getPeriodProvider(nrPeriodDataSourceModel, periodType);
                    periods.addAll(TimeDimHelper.getPeiodStrList((IPeriodAdapter)periodProvider, (PeriodWrapper)new PeriodWrapper(startPeriod), (PeriodWrapper)new PeriodWrapper(endPeriod)));
                } else {
                    for (Object value : values) {
                        String period = TimeDimUtils.timeKeyToPeriod((String)((String)value), (PeriodType)periodType);
                        periods.add(period);
                    }
                }
                Collections.sort(periods);
                defaultPeriod = (String)periods.get(periods.size() - 1);
                masterKeys.setValue("DATATIME", periods);
                continue;
            }
            if (dataSourceModel instanceof NrEntityDataSourceModel) {
                String entityId = ((NrEntityDataSourceModel)dataSourceModel).getEntityViewId();
                if (dsModel.getGaterDimName() != null && dsModel.getGaterDimName().equals(entityId)) {
                    masterKeys.setValue(dsModel.getUnitEntityDefnie().getDimensionName(), (Object)values);
                    continue;
                }
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
                String dimName = entityDefine.getDimensionName();
                masterKeys.setValue(dimName, (Object)values);
                continue;
            }
            if (!(dataSourceModel instanceof NrCaliberDimDataSourceModel)) continue;
            String dimName = dsModel.getUnitEntityDefnie().getDimensionName();
            masterKeys.setValue(dimName, (Object)values);
        }
        return defaultPeriod;
    }

    private ReportQueryContext createContext(ReportDSModel dsModel, IDSContext dsContext, TempResource resource) throws BIDataSetException {
        try {
            IExpression conditionExp;
            boolean filterd;
            DimensionValueSet masterKeys = new DimensionValueSet();
            String period = this.buildMasterKeys(masterKeys, dsModel, dsContext);
            this.resetMasterKeysByConfig(masterKeys, period, dsModel);
            ReportDsModelDefine reportDSDefine = dsModel.getReportDsModelDefine();
            String reportTaskKey = reportDSDefine.getReportTaskKey();
            com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = this.createExecutorContext(period, reportTaskKey);
            String filter = reportDSDefine.getFilter();
            ReportQueryContext context = new ReportQueryContext(executorContext, this.dataAccessProvider.getFormulaParam(), dsModel, (IMonitor)new ReportEvalMonitor());
            if (StringUtils.isNotEmpty((String)filter) && !(filterd = this.tryFilterByMainDim(executorContext, conditionExp = executorContext.getCache().getFormulaParser(true).parseEval(filter, (IContext)context), filter, masterKeys))) {
                context.setConditionExp(conditionExp);
            }
            context.setQueryExecutorProvider(this.dbQueryExecutorProvider);
            context.setEntityVeriosnPeriod(period);
            context.setMasterKeys(masterKeys);
            if (dsModel.needUnitTreeBuilder()) {
                context.setSortedUnitTree(this.unitDetailTreeService.createSortedUnitTree(context, dsModel));
            }
            context.setTempResource(resource);
            this.tryCreateTempTable(masterKeys, context);
            return context;
        }
        catch (Exception e) {
            throw new BIDataSetException(e.getMessage(), (Throwable)e);
        }
    }

    private void resetMasterKeysByConfig(DimensionValueSet masterKeys, String period, ReportDSModel dsModel) throws Exception {
        AdvancedConfig config = dsModel.getReportDsModelDefine().getConfig();
        if (config != null) {
            this.resetUnitKeys(masterKeys, period, dsModel, config);
            this.resetPeriods(masterKeys, period, dsModel, config);
        }
    }

    private void resetPeriods(DimensionValueSet masterKeys, String period, ReportDSModel dsModel, AdvancedConfig config) {
        ParameterModel periodParam = dsModel.getDimParamMap().get("DATATIME");
        if (periodParam == null || periodParam.getSelectMode() != ParameterSelectMode.SINGLE) {
            return;
        }
        if (PeriodChangeMode.RANGE == config.getPeriodChangeMode()) {
            String startOffset = config.getPeriodOffset();
            String[] strs = startOffset.split(",");
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dsModel.getTaskDefine().getDateTime());
            PeriodModifier pm = new PeriodModifier();
            for (String str : strs) {
                pm.union(PeriodModifier.parse((String)str));
            }
            PeriodWrapper stopPeriod = new PeriodWrapper(period);
            PeriodWrapper startPeriod = new PeriodWrapper(period);
            periodProvider.modify(startPeriod, pm);
            if (startPeriod.compareTo((Object)stopPeriod) > 0) {
                PeriodWrapper temp = startPeriod;
                startPeriod = stopPeriod;
                stopPeriod = temp;
            }
            List periods = TimeDimHelper.getPeiodStrList((IPeriodAdapter)periodProvider, (PeriodWrapper)startPeriod, (PeriodWrapper)stopPeriod);
            masterKeys.setValue("DATATIME", (Object)periods);
        }
    }

    private void resetUnitKeys(DimensionValueSet masterKeys, String period, ReportDSModel dsModel, AdvancedConfig config) throws Exception {
        ParameterModel unitParam = dsModel.getDimParamMap().get(dsModel.getUnitEntityDefnie().getDimensionName());
        if (unitParam == null || unitParam.getSelectMode() != ParameterSelectMode.SINGLE) {
            return;
        }
        UnitChangeMode unitChangeMode = config.getUnitChangeMode();
        if (UnitChangeMode.SELECTED != unitChangeMode) {
            DimensionValueSet entityDims = new DimensionValueSet();
            entityDims.setValue("DATATIME", (Object)period);
            IEntityTable unitEntityTable = this.getUnitEntityTable(entityDims, dsModel);
            String unitDimName = dsModel.getUnitEntityDefnie().getDimensionName();
            List unitKeys = (List)masterKeys.getValue(unitDimName);
            if (unitKeys.size() == 1) {
                if (UnitChangeMode.DIRECT_CHILD == unitChangeMode) {
                    List entityRows = unitEntityTable.getChildRows((String)unitKeys.get(0));
                    unitKeys = entityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    masterKeys.setValue(unitDimName, unitKeys);
                } else if (UnitChangeMode.ALL_CHILD == unitChangeMode) {
                    List entityRows = unitEntityTable.getAllChildRows((String)unitKeys.get(0));
                    unitKeys = entityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    masterKeys.setValue(unitDimName, unitKeys);
                } else if (UnitChangeMode.ALL_LEAF == unitChangeMode) {
                    List entityRows = unitEntityTable.getAllChildRows((String)unitKeys.get(0));
                    unitKeys = entityRows.stream().filter(o -> unitEntityTable.getDirectChildCount(o.getEntityKeyData()) == 0).map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    masterKeys.setValue(unitDimName, unitKeys);
                }
            }
        }
    }

    private void tryCreateTempTable(DimensionValueSet masterKeys, ReportQueryContext conetxt) {
        try {
            String unitDimension = conetxt.getExeContext().getUnitDimension();
            Object dimValue = masterKeys.getValue(unitDimension);
            conetxt.getTempAssistantTable(unitDimension, dimValue, 6);
        }
        catch (Exception e) {
            conetxt.getMonitor().exception(e);
        }
    }

    private ReportQueryContext cloneContext(ReportQueryContext context, DimensionValueSet masterKeys) throws Exception {
        ReportQueryContext newConetxt = new ReportQueryContext(context.getExeContext(), context.getQueryParam(), context.getDsModel(), context.getMonitor());
        newConetxt.setConditionExp(context.getConditionExp());
        newConetxt.setMasterKeys(masterKeys);
        newConetxt.setTempResource(context.getTempResource());
        newConetxt.setSortedUnitTree(context.getSortedUnitTree());
        newConetxt.setEntityVeriosnPeriod(context.getEntityVeriosnPeriod());
        return newConetxt;
    }

    private boolean tryFilterByMainDim(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, IExpression conditionExp, String filter, DimensionValueSet masterKeys) {
        return false;
    }

    private com.jiuqi.np.dataengine.executors.ExecutorContext createExecutorContext(String period, String reportTaskKey) throws Exception {
        String fromSchemeKey = null;
        SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, reportTaskKey);
        if (schemePeriodLink != null) {
            fromSchemeKey = schemePeriodLink.getSchemeKey();
        } else {
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(reportTaskKey);
            if (formSchemeDefines.size() > 0) {
                fromSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
            }
        }
        if (fromSchemeKey == null) {
            return null;
        }
        com.jiuqi.np.dataengine.executors.ExecutorContext executorContext = new com.jiuqi.np.dataengine.executors.ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, fromSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)env);
        executorContext.setAutoDataMasking(true);
        return executorContext;
    }

    public IEntityTable getUnitEntityTable(DimensionValueSet marsterKeys, ReportDSModel dsModel) throws Exception {
        ExecutorContext entityExecutorContext = new ExecutorContext(this.dataDefinitionController);
        entityExecutorContext.setPeriodView(dsModel.getTaskDefine().getDateTime());
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(marsterKeys);
        iEntityQuery.sorted(true);
        iEntityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(dsModel.getUnitEntityDefnie().getId()));
        return iEntityQuery.executeFullBuild((IContext)entityExecutorContext);
    }
}

