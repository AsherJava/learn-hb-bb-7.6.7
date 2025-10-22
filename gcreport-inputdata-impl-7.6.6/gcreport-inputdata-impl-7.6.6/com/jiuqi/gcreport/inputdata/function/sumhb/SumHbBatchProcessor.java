/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.function.sumhb;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dao.SumhbTempDao;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.RegionInputBuilder;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.RegionInputItem;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParamBuilder;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbRegion;
import com.jiuqi.gcreport.inputdata.function.sumhb.entity.SumHbTempEO;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.InputQuery;
import com.jiuqi.gcreport.inputdata.function.sumhb.query.InputQueryFactory;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbService;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class SumHbBatchProcessor {
    private final QueryContext queryContext;
    String funcName;
    private final List<FunctionNode> functions;
    private final IRunTimeViewController runTimeViewController;
    private final IRuntimeDataSchemeService runtimeDataSchemeService;
    private final InputDataNameProvider inputDataNameProvider;
    private final Logger logger;
    private final SumhbTempDao sumhbTempDao;
    private final ConsolidatedTaskService consolidatedTaskService;
    private final TaskDefine taskDefine;
    private List<ColumnModelDefine> allFields;
    private List<String> calFieldNames;
    private List<String> noCalcFieldNames;
    private Map<String, Integer> fieldIndexMapping;
    private String inputTableName;

    public static SumHbBatchProcessor newInstance(QueryContext queryContext, String funcName, List<FunctionNode> functions) {
        return new SumHbBatchProcessor(queryContext, funcName, functions);
    }

    private SumHbBatchProcessor(QueryContext queryContext, String funcName, List<FunctionNode> functions) {
        this.queryContext = queryContext;
        this.funcName = funcName;
        this.functions = functions;
        this.runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        this.inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        this.consolidatedTaskService = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.logger = LoggerFactory.getLogger(SumHbBatchProcessor.class);
        this.sumhbTempDao = SumhbTempDao.newInstance();
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)queryContext.getExeContext().getEnv();
        try {
            this.taskDefine = env.getTaskDefine();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.functioncontexterrormsg"), (Throwable)e);
        }
    }

    public void batchCalc() {
        try {
            List<Map<String, String>> allDims = this.getAllDims();
            if (CollectionUtils.isEmpty(allDims)) {
                return;
            }
            Map<String, SumhbRegion> sumhbRegions = this.getReginSumhbs();
            if (CollectionUtils.isEmpty(sumhbRegions)) {
                return;
            }
            this.initSubjectCodes(sumhbRegions);
            this.initFieldNames(sumhbRegions);
            allDims.forEach(dims -> {
                try {
                    this.batchCalcSingleDims((Map<String, String>)dims, sumhbRegions);
                }
                catch (Exception e) {
                    LogHelper.error((String)"SUMHB", (String)"SUMHB\u67d0\u7ef4\u5ea6\u6279\u5904\u7406\u5f02\u5e38", (String)(dims + e.getMessage()));
                    this.logger.error("SUMHB\u67d0\u7ef4\u5ea6\u6279\u5904\u7406\u5f02\u5e38\u3002\u7ef4\u5ea6\uff1a" + dims, e);
                }
            });
        }
        catch (Exception e) {
            LogHelper.error((String)"SUMHB", (String)"SUMHB\u6279\u5904\u7406\u5f02\u5e38", (String)e.getMessage());
            this.logger.error("SUMHB\u6279\u5904\u7406\u5931\u8d25\u3002", e);
        }
        finally {
            this.sumhbTempDao.clear();
        }
    }

    private List<Map<String, String>> getAllDims() throws Exception {
        DimensionValueSet masterKeys = new DimensionValueSet(this.queryContext.getMasterKeys());
        for (int index = 0; index < masterKeys.size(); ++index) {
            if (!(masterKeys.getValue(index) instanceof Iterable)) continue;
            masterKeys.setValue(masterKeys.getName(index), (Object)String.join((CharSequence)";", (Iterable)masterKeys.getValue(index)));
        }
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)this.queryContext.getExeContext().getEnv();
        Map<String, String> allDims = InputDataConver.getDimFieldValueMap(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)masterKeys), env.getTaskDefine().getKey());
        ArrayList<Map<String, String>> re = new ArrayList<Map<String, String>>();
        Arrays.stream(allDims.get("MDCODE").split(";")).forEach(orgValue -> Arrays.stream(((String)allDims.get("MD_CURRENCY")).split(";")).forEach(currencyCode -> {
            HashMap<String, String> dims = new HashMap<String, String>(allDims);
            dims.put("MDCODE", (String)orgValue);
            dims.put("MD_CURRENCY", (String)currencyCode);
            re.add(dims);
        }));
        return re;
    }

    private Map<String, SumhbRegion> getReginSumhbs() {
        this.inputTableName = this.inputDataNameProvider.getTableNameByTaskId(this.taskDefine.getKey());
        HashMap<String, SumhbRegion> sumhbRegions = new HashMap<String, SumhbRegion>(this.functions.size() * 2);
        SumhbParamBuilder sumhbParamBuilder = new SumhbParamBuilder(this.queryContext, this.funcName, this.inputTableName);
        for (FunctionNode functionNode : this.functions) {
            SumhbParam sumhbParam;
            try {
                sumhbParam = sumhbParamBuilder.build(functionNode.getParameters());
            }
            catch (Exception e) {
                continue;
            }
            if (sumhbParam == null) continue;
            SumhbRegion sumhbRegion = sumhbRegions.computeIfAbsent(sumhbParam.getReginId(), regionId -> new SumhbRegion(sumhbParam));
            sumhbRegion.addSumhbParam(sumhbParam);
        }
        return sumhbRegions;
    }

    private void initSubjectCodes(Map<String, SumhbRegion> sumhbRegions) {
        String subjectFieldDefinedId;
        ArrayList<SumHbTempEO> subjectTemps = new ArrayList<SumHbTempEO>();
        ArrayList<SumHbTempEO> subjectSumTemps = new ArrayList<SumHbTempEO>();
        try {
            String tableKey = this.inputDataNameProvider.getDataTableKeyByTaskId(this.taskDefine.getKey());
            DataField field = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(tableKey, "SUBJECTOBJ");
            subjectFieldDefinedId = field.getKey();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.findgcinoutdatasubjectmsg"), (Throwable)e);
        }
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(this.taskDefine.getKey(), (String)this.queryContext.getCurrentMasterKey().getValue("DATATIME"));
        for (SumhbRegion sumhbRegion : sumhbRegions.values()) {
            List entityRows;
            IEntityTable entityTable;
            List allLinkDefines = this.runTimeViewController.getLinksInRegionByField(sumhbRegion.getRegionId(), subjectFieldDefinedId);
            if (CollectionUtils.isEmpty(allLinkDefines) || allLinkDefines.size() > 1) {
                this.logger.error("\u533a\u57df{}}\u6709\u591a\u4e2a\u5355\u5143\u683c\u5173\u8054\u4e86\u79d1\u76ee\u5b57\u6bb5\uff0c\u65e0\u6cd5\u8fdb\u884c\u6279\u91cfsumhb\u8fd0\u7b97\u3002", (Object)sumhbRegion.getRegionId());
                continue;
            }
            EntityViewDefine viewDefine = this.runTimeViewController.getViewByLinkDefineKey(((DataLinkDefine)allLinkDefines.get(0)).getKey());
            try {
                IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
                IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
                IEntityQuery entityQuery = entityDataService.newEntityQuery();
                entityQuery.setIsolateCondition(systemId);
                entityQuery.setEntityView(viewDefine);
                entityQuery.setMasterKeys(this.queryContext.getCurrentMasterKey());
                ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
                executorContext.setPeriodView(this.taskDefine.getDateTime());
                entityTable = entityQuery.executeReader((IContext)executorContext);
            }
            catch (Exception e) {
                this.logger.error("\u533a\u57df\u201d" + sumhbRegion.getRegionId() + "\u201c\u4f9d\u636e\u5408\u5e76\u79d1\u76ee\u8fc7\u6ee4\u6761\u4ef6\u67e5\u8be2\u79d1\u76ee\u6570\u636e\u5931\u8d25\u3002", e);
                continue;
            }
            if (Objects.isNull(entityTable) || CollectionUtils.isEmpty(entityRows = entityTable.getAllRows())) continue;
            boolean hasSumFilter = sumhbRegion.hasSumFilter();
            boolean hasSumNoFilter = sumhbRegion.hasSumNoFilter();
            for (IEntityRow iEntityRow : entityRows) {
                SumHbTempEO sumHbTempEO = new SumHbTempEO();
                sumHbTempEO.setRegionId(sumhbRegion.getRegionId());
                sumHbTempEO.setSubjectCode(iEntityRow.getCode());
                if (hasSumFilter) {
                    subjectTemps.add(sumHbTempEO);
                }
                if (!hasSumNoFilter) continue;
                subjectSumTemps.add(sumHbTempEO);
            }
        }
        this.sumhbTempDao.insert(subjectTemps);
        this.sumhbTempDao.insertSum(subjectSumTemps);
    }

    private void initFieldNames(Map<String, SumhbRegion> sumhbRegions) {
        this.calFieldNames = this.getAllCalcFieldNames(sumhbRegions);
        this.noCalcFieldNames = this.getNoCalcFieldName(sumhbRegions, this.calFieldNames);
        if (!this.noCalcFieldNames.contains("SUBJECTCODE")) {
            this.noCalcFieldNames.add("SUBJECTCODE");
        }
        ArrayList<String> allFieldNames = new ArrayList<String>();
        allFieldNames.addAll(this.calFieldNames);
        allFieldNames.addAll(this.noCalcFieldNames);
        this.allFields = this.getAllFields(allFieldNames);
        this.fieldIndexMapping = new HashMap<String, Integer>();
        for (int index = 0; index < allFieldNames.size(); ++index) {
            this.fieldIndexMapping.put((String)allFieldNames.get(index), index);
        }
    }

    private List<String> getAllCalcFieldNames(Map<String, SumhbRegion> sumhbRegions) {
        return sumhbRegions.values().stream().flatMap(sumhbRegion -> sumhbRegion.getSumhbParams().stream()).map(sumhbParam -> sumhbParam.getFieldDefine().getName()).distinct().collect(Collectors.toList());
    }

    private List<String> getNoCalcFieldName(Map<String, SumhbRegion> sumhbRegions, Collection<String> calcFieldNames) {
        List<String> allFieldNames = sumhbRegions.values().stream().flatMap(sumhbRegion -> sumhbRegion.getAllFields().stream()).map(DataFieldDeployInfo::getFieldName).distinct().collect(Collectors.toList());
        allFieldNames.removeAll(calcFieldNames);
        return allFieldNames;
    }

    private List<ColumnModelDefine> getAllFields(List<String> fieldNames) {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByName(this.inputTableName);
        List columnModelDefines = dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        Map<String, ColumnModelDefine> columnModelDefineMap = columnModelDefines.stream().collect(Collectors.toMap(ColumnModelDefine::getName, columnModelDefine -> columnModelDefine));
        return fieldNames.stream().map(columnModelDefineMap::get).collect(Collectors.toList());
    }

    private void batchCalcSingleDims(Map<String, String> dims, Map<String, SumhbRegion> sumhbRegions) {
        InputQuery inputQuery = InputQueryFactory.createQuery(this.queryContext, this.inputTableName, this.calFieldNames, this.noCalcFieldNames, dims, this.sumhbTempDao);
        List<Map<String, Object>> inputItems = Collections.unmodifiableList(inputQuery.query());
        Map<String, RegionInputItem> regionInputItems = RegionInputBuilder.build(inputItems, this.allFields);
        sumhbRegions.values().forEach(sumhbRegion -> {
            try {
                this.calcRegion(dims, regionInputItems, (SumhbRegion)sumhbRegion);
            }
            catch (Exception e) {
                LogHelper.error((String)"SUMHB", (String)"SUMHB\u67d0\u533a\u57df\u6279\u5904\u7406\u5f02\u5e38", (String)(sumhbRegion.getRegionId() + e.getMessage()));
                this.logger.error("SUMHB\u533a\u57df\u3010" + sumhbRegion.getRegionId() + "\u3011\u6279\u5904\u7406\u5f02\u5e38\u3002", e);
            }
        });
    }

    private void calcRegion(Map<String, String> dims, Map<String, RegionInputItem> regionInputItems, SumhbRegion sumhbRegion) {
        RegionInputItem regionInputItem = regionInputItems.computeIfAbsent(sumhbRegion.getRegionId(), key -> new RegionInputItem(sumhbRegion.getRegionId(), this.allFields));
        String regionFilter = sumhbRegion.getFilter();
        try (IDataSetExprEvaluator dataSetExprEvaluator = this.getRegionExpreEvaluator();){
            List<DataRow> detailItems = this.doFilter(regionInputItem.getDetailRows(), regionFilter, dataSetExprEvaluator);
            sumhbRegion.getSumhbParams().forEach(sumhbParam -> {
                List<DataRow> functionFilterItems = ObjectUtils.isEmpty(sumhbParam.getFilter()) ? regionInputItem.getSumRows() : this.doFilter(detailItems, sumhbParam.getCompiledFilter(), dataSetExprEvaluator);
                double sumRe = this.sum(functionFilterItems, sumhbParam.getFieldDefine());
                String orgCode = (String)dims.get("MDCODE");
                String currencyCode = (String)dims.get("MD_CURRENCY");
                String cacheKey = SumHbService.getCacheKey(orgCode, currencyCode, sumhbParam);
                this.queryContext.getCache().put(cacheKey, sumRe);
            });
        }
        catch (IOException e) {
            this.logger.error("\u6570\u636e\u96c6\u8868\u8fbe\u5f0f\u53d6\u6570\u6267\u884c\u5668\u8fde\u63a5\u5173\u95ed\u5931\u8d25", e);
        }
    }

    private IDataSetExprEvaluator getRegionExpreEvaluator() {
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringContextUtils.getBean(IDataAccessProvider.class);
        Metadata metaData = new Metadata();
        for (ColumnModelDefine columnModelDefine : this.allFields) {
            metaData.addColumn(new Column(columnModelDefine.getCode(), columnModelDefine.getColumnType().getValue()));
        }
        MemoryDataSet dataSet = new MemoryDataSet(null, metaData);
        return dataAccessProvider.newDataSetExprEvaluator((DataSet)dataSet);
    }

    private List<DataRow> doFilter(List<DataRow> inputItems, String filter, IDataSetExprEvaluator dataSetExprEvaluator) {
        if (ObjectUtils.isEmpty(filter) || CollectionUtils.isEmpty(inputItems)) {
            return inputItems;
        }
        try {
            dataSetExprEvaluator.prepare(this.queryContext.getExeContext(), new DimensionValueSet(), filter);
        }
        catch (Exception e) {
            Object[] args = new String[]{filter};
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.regionfilterparseexceptionmsg", (Object[])args));
        }
        return inputItems.stream().filter(inputItem -> {
            try {
                return dataSetExprEvaluator.judge(inputItem);
            }
            catch (Exception e) {
                Object[] args = new String[]{filter};
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.functionexecutionexceptionmsg", (Object[])args), e);
            }
        }).collect(Collectors.toList());
    }

    private double sum(List<DataRow> funcInputItems, ColumnModelDefine columnModelDefine) {
        double sum = 0.0;
        if (CollectionUtils.isEmpty(funcInputItems)) {
            return sum;
        }
        for (DataRow inputItem : funcInputItems) {
            double value = this.getDoubleValue(inputItem, columnModelDefine.getName());
            sum += value;
        }
        return BigDecimal.valueOf(sum).setScale(columnModelDefine.getDecimal(), RoundingMode.HALF_UP).doubleValue();
    }

    private double getDoubleValue(DataRow inputItem, String fieldName) {
        double value;
        if (this.fieldIndexMapping.get(fieldName) == null) {
            return 0.0;
        }
        int index = this.fieldIndexMapping.get(fieldName);
        try {
            value = Double.parseDouble(String.valueOf(inputItem.getDouble(index)));
        }
        catch (Exception e) {
            value = 0.0;
        }
        return value;
    }
}

