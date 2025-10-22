/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.xlib.utils.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.query.dataset.impl;

import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.query.block.QueryBlockDefine;
import com.jiuqi.nr.query.block.QuerySelectField;
import com.jiuqi.nr.query.dao.IQueryBlockDefineDao;
import com.jiuqi.nr.query.dao.IQueryModalDefineDao;
import com.jiuqi.nr.query.dataset.DSFieldAdapter;
import com.jiuqi.nr.query.dataset.DSHierarchyAdapter;
import com.jiuqi.nr.query.dataset.DSParameterAdapter;
import com.jiuqi.nr.query.dataset.QueryDSField;
import com.jiuqi.nr.query.dataset.QueryDSModel;
import com.jiuqi.nr.query.dataset.QueryDefineDataSetUtil;
import com.jiuqi.nr.query.dataset.impl.NrQueryDSContext;
import com.jiuqi.nr.query.dataset.parse.DataSetDimensionNodeFinder;
import com.jiuqi.nr.query.querymodal.QueryModalDefine;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NrQueryDSModelBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IQueryBlockDefineDao blockDao;
    @Autowired
    private IQueryModalDefineDao modelDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DSFieldAdapter fieldAdapter;
    @Autowired
    private DSHierarchyAdapter hierarchyAdapter;
    @Autowired
    private DSParameterAdapter parameterAdapter;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;

    public void buildModel(QueryDSModel dsModel) throws Exception {
        List fields = dsModel.getCommonFields();
        fields.clear();
        String modelId = dsModel.getBlockId();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        QueryModalDefine modelDefine = this.modelDao.getQueryModalDefineById(modelId);
        if (modelDefine != null) {
            List<QueryBlockDefine> blocks = this.blockDao.GetQueryBlockDefinesByModelId(modelId);
            QueryBlockDefine block = blocks.get(0);
            this.buildDSModelByBlock(dsModel, executorContext, block, true);
        } else {
            QueryBlockDefine block = this.blockDao.GetQueryBlockDefineById(dsModel.getBlockId());
            this.buildDSModelByBlock(dsModel, executorContext, block, true);
        }
    }

    private QueryDSModel buildDSModelByBlock(QueryDSModel model, ExecutorContext executorContext, QueryBlockDefine block, boolean initTimeDim) throws ParseException, Exception {
        String periodType;
        String blockInfoClob = block.getBlockInfoBlob();
        if (StringUtils.hasText((String)blockInfoClob)) {
            block.setBlockInfoStr(blockInfoClob);
        }
        if ((periodType = block.getPeriodTypeInCache()) != null) {
            model.setTimeGranularity(QueryDefineDataSetUtil.adaptTimeGranularity(periodType));
        }
        NrQueryDSContext qContext = new NrQueryDSContext(executorContext);
        qContext.setJdbcTemplate(this.jdbcTemplate);
        List<QuerySelectField> selectedFields = QueryDefineDataSetUtil.getSelectFieldsInBlock(block);
        this.addDSFields(model, qContext, selectedFields, initTimeDim);
        for (DSField dsField : model.getCommonFields()) {
            QueryDSField queryDSField = (QueryDSField)dsField;
            if (queryDSField.getEntityTableName() == null || !queryDSField.getName().equals(queryDSField.getParentFieldName())) continue;
            DSHierarchy hierarchy = this.hierarchyAdapter.getHierarchy(qContext, queryDSField);
            model.getHiers().add(hierarchy);
        }
        model.getParameterModels().addAll(this.parameterAdapter.getParameterModel(executorContext, model, block, "QueryDataSet"));
        return model;
    }

    private void addDSFields(QueryDSModel model, NrQueryDSContext context, List<QuerySelectField> selectedFields, boolean initTimeDim) throws ParseException, Exception {
        if (selectedFields == null || selectedFields.size() == 0) {
            return;
        }
        List commonFields = model.getCommonFields();
        TableModelRunInfo tableRunInfo = null;
        Map<String, TableModelRunInfo> allTableInfos = context.getAllTableInfos();
        ExecutorContext executorContext = context.getExecutorContext();
        DataModelDefinitionsCache dataDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
        for (QuerySelectField sf : selectedFields) {
            FieldDefine field;
            if (model.getFormSchemeKey() == null && sf.getFormSchemeId() != null) {
                model.setFormSchemeKey(sf.getFormSchemeId());
                ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.iEntityViewRunTimeController, model.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)env);
            }
            if ((field = this.dataDefinitionRuntimeController.queryFieldDefine(sf.getCode().toString())) == null) continue;
            ColumnModelDefine columnModel = dataDefinitionsCache.getColumnModel(field);
            TableModelDefine table = dataDefinitionsCache.findTable(columnModel.getTableID());
            TableModelRunInfo tableInfo = dataDefinitionsCache.getTableInfo(table.getName());
            if (tableRunInfo == null && tableInfo.getTableModelDefine().getType() == TableModelType.DATA) {
                tableRunInfo = tableInfo;
            }
            allTableInfos.put(tableInfo.getTableModelDefine().getName(), tableInfo);
        }
        ReportFormulaParser formulaParser = executorContext.getCache().getFormulaParser(true);
        formulaParser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new DataSetDimensionNodeFinder());
        QueryContext qContext = new QueryContext(executorContext, null);
        HashMap<String, QueryDSField> dsFieldMap = new HashMap<String, QueryDSField>();
        QueryDSField timeKeyField = null;
        int expIndex = 0;
        for (QuerySelectField sf : selectedFields) {
            try {
                String dsFieldTitle = sf.getTitle();
                QueryDSField dsField = null;
                if (sf.getCustom()) {
                    TableModelRunInfo fieldTableInfo;
                    String customExpression = sf.getCustomValue();
                    if (sf.getTableName() != null && (fieldTableInfo = dataDefinitionsCache.getTableInfo(sf.getTableName())).getTableModelDefine().getType() == TableModelType.DATA) {
                        tableRunInfo = fieldTableInfo;
                    }
                    if ((dsField = this.fieldAdapter.getDsFieldByExpression(context, tableRunInfo, dataDefinitionsCache, formulaParser, qContext, dsFieldTitle, dsField, customExpression)).getEntityTableName() == null) {
                        String expName = "EXP" + expIndex;
                        dsField.setName(expName);
                        dsField.setMessageAlias(expName);
                        dsField.setKeyField(expName);
                        dsField.setNameField(expName);
                        ++expIndex;
                    }
                } else {
                    FieldDefine field = this.dataDefinitionRuntimeController.queryFieldDefine(sf.getCode().toString());
                    if (field != null) {
                        ColumnModelDefine columnModel = dataDefinitionsCache.getColumnModel(field);
                        dsField = this.fieldAdapter.getDSField(context, columnModel, dsFieldTitle);
                        if (field.getValueType() == FieldValueType.FIELD_VALUE_PERIOD_VALUE) {
                            TimeGranularity timeKeyGranularity = model.getTimeGranularity();
                            this.fieldAdapter.adaptTimeDim(dsField, timeKeyGranularity, true);
                            model.setTimeKeyIndex(commonFields.size());
                            timeKeyField = dsField;
                        }
                    }
                    if (dsField == null) {
                        throw new SyntaxException(sf.getCode().toString() + "\u6ca1\u627e\u5230\u6307\u6807");
                    }
                }
                if (dsField == null) continue;
                dsFieldMap.put(dsField.getName(), dsField);
                commonFields.add(dsField);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (timeKeyField != null) {
            this.addTimeDimFields(model, commonFields, timeKeyField);
        }
    }

    private void addTimeDimFields(QueryDSModel model, List<DSField> commonFields, DSField timeKeyField) throws TimeCalcException {
        List<Integer> timeDims = model.getTimeDims();
        switch (timeKeyField.getTimegranularity()) {
            case DAY: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.DAY);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.XUN);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.MONTH);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.QUARTER);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.HALFYEAR);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
                break;
            }
            case XUN: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.XUN);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.MONTH);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.QUARTER);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.HALFYEAR);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
                break;
            }
            case MONTH: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.MONTH);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.QUARTER);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.HALFYEAR);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
                break;
            }
            case QUARTER: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.QUARTER);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.HALFYEAR);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
                break;
            }
            case HALFYEAR: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.HALFYEAR);
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
                break;
            }
            case YEAR: {
                this.addTimeDimField(timeDims, commonFields, TimeGranularity.YEAR);
            }
        }
    }

    private void addTimeDimField(List<Integer> timeDimIndexes, List<DSField> commonFields, TimeGranularity timeGranularity) throws TimeCalcException {
        QueryDSField dsField = new QueryDSField();
        this.fieldAdapter.adaptTimeDim(dsField, timeGranularity, false);
        String dsFieldName = timeGranularity.name();
        dsField.setName(dsFieldName);
        dsField.setValType(6);
        dsField.setFieldType(FieldType.TIME_DIM);
        dsField.setAggregation(AggregationType.MIN);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setTitle(timeGranularity.title());
        dsField.setMessageAlias(dsFieldName);
        dsField.setKeyField(dsFieldName);
        dsField.setNameField(dsFieldName);
        timeDimIndexes.add(commonFields.size());
        commonFields.add(dsField);
    }
}

