/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.INumberData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 */
package com.jiuqi.nr.data.logic.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.INumberData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicMappingException;
import com.jiuqi.nr.data.logic.facade.extend.IAllCheckCacheCtrl;
import com.jiuqi.nr.data.logic.facade.extend.ICheckErrorFilter;
import com.jiuqi.nr.data.logic.facade.param.input.ActionEnum;
import com.jiuqi.nr.data.logic.facade.param.input.CheckMax;
import com.jiuqi.nr.data.logic.facade.param.output.CheckErrorObj;
import com.jiuqi.nr.data.logic.facade.param.output.CheckRecordData;
import com.jiuqi.nr.data.logic.internal.entity.FmlCheckResultEntity;
import com.jiuqi.nr.data.logic.internal.helper.CheckDataCollector;
import com.jiuqi.nr.data.logic.internal.obj.CheckData;
import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveInfo;
import com.jiuqi.nr.data.logic.internal.obj.FormulaNodeSaveObj;
import com.jiuqi.nr.data.logic.internal.obj.NodeInfoQueryCache;
import com.jiuqi.nr.data.logic.internal.service.impl.AllCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.service.impl.BatchCheckResultSaver;
import com.jiuqi.nr.data.logic.internal.util.BeanHelper;
import com.jiuqi.nr.data.logic.internal.util.CheckResultUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityDataLoader;
import com.jiuqi.nr.data.logic.monitor.base.BatchParallelBaseMonitor;
import com.jiuqi.nr.data.logic.spi.ICheckErrHandler;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class CheckMonitor
extends BatchParallelBaseMonitor {
    private static final Logger logger = LoggerFactory.getLogger(CheckMonitor.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private DimensionValueSet checkDim;
    private List<DimensionValueSet> onlyDimCartesian;
    private final List<String> formSchemeEntityNames = new ArrayList<String>();
    private String dwDimensionName;
    private String periodDimensionName;
    private List<String> dimDimensionName;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private ActionEnum action;
    private String actionID;
    private boolean record;
    private Map<String, CheckRecordData> checkRecordData = new HashMap<String, CheckRecordData>();
    private final List<FmlCheckResultEntity> checkResultEntities = new ArrayList<FmlCheckResultEntity>();
    private IAllCheckCacheCtrl allCheckCacheCtrl;
    private String executeId;
    private CheckMax checkMax = new CheckMax();
    private double startProgress = 0.01;
    private double endProgress = 0.99;
    private DecimalFormat decimalFormat;
    private String cacheKey;
    private boolean canceled = false;
    private EntityDataLoader dwEntityDataLoader;
    private NodeInfoQueryCache nodeInfoQueryCache;
    private static final int BATCH_CHECK_COMMIT_ROW_SIZE = 10000;
    private boolean removeUTF0Char;
    private List<ICheckErrHandler> checkErrHandlers;
    private ICheckErrorFilter checkErrorFilter;
    private BeanHelper beanHelper;
    private ExecutorContext executorContext;
    private BigDecimal allowableError;

    public CheckMonitor(BeanHelper beanHelper) {
        super(null);
        this.init(null, beanHelper);
    }

    public CheckMonitor(BatchParallelExeTask task, BeanHelper beanHelper) {
        super(task);
        this.init(null, beanHelper);
    }

    public CheckMonitor(BatchParallelExeTask task, AsyncTaskMonitor asyncTaskMonitor, BeanHelper beanHelper) {
        super(task);
        this.init(asyncTaskMonitor, beanHelper);
    }

    private void init(AsyncTaskMonitor asyncTaskMonitor, BeanHelper beanHelper) {
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.beanHelper = beanHelper;
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setGroupingSize(3);
        this.decimalFormat.setGroupingUsed(true);
    }

    public void onProgress(double progress) {
        if (this.task != null && this.task.isFinished()) {
            return;
        }
        super.onProgress(progress);
        if (this.asyncTaskMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            this.asyncTaskMonitor.progressAndMessage(currProgress, "\u5ba1\u6838\u6267\u884c\u4e2d...");
        }
    }

    @Override
    public void error(FormulaCheckEventImpl event) {
        super.error(event);
        if (this.filter(event)) {
            logger.debug("\u5ba1\u6838\u9519\u8bef\u88ab\u8fc7\u6ee4-{}", (Object)event);
            return;
        }
        CheckData executeCheckData = this.getExecuteCheckData();
        if (ActionEnum.BATCH_CHECK == this.action) {
            this.writeError(event);
            if (this.checkResultEntities.size() >= 10000) {
                this.checkDataHandle();
                this.checkResultEntities.clear();
            }
        } else if (null == executeCheckData) {
            this.writeError(event);
        } else {
            AtomicInteger errorCount = executeCheckData.getErrorCount();
            if (this.checkMax.getMax() < 0 || errorCount.incrementAndGet() <= this.checkMax.getMax()) {
                this.writeError(event);
            }
        }
    }

    private boolean filter(FormulaCheckEventImpl event) {
        if (this.allowableError == null || this.allowableError.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        AbstractData differenceValue = event.getDifferenceValue();
        if (differenceValue instanceof INumberData) {
            try {
                BigDecimal asCurrency = differenceValue.getAsCurrency();
                return asCurrency.abs().compareTo(this.allowableError) <= 0;
            }
            catch (Exception e) {
                logger.error("{}\u8bef\u5dee\u5224\u65ad\u5f02\u5e38:{}", event, e.getMessage(), e);
            }
        }
        return false;
    }

    private void writeError(FormulaCheckEventImpl event) {
        DimensionValueSet rowKey = event.getRowkey();
        for (DimensionValueSet valueSet : DimensionUtil.fillLostDim(rowKey, this.onlyDimCartesian)) {
            FmlCheckResultEntity result = this.buildResult(event, valueSet);
            if (this.checkErrorFilter != null && !this.checkErrorFilter.filter(result)) continue;
            this.checkResultEntities.add(result);
            this.recordError(result);
        }
    }

    private void recordError(FmlCheckResultEntity result) {
        if (this.record) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            for (Map.Entry<String, String> entry : result.getDimMap().entrySet()) {
                String name = entry.getKey();
                if (name.equals("MDCODE")) {
                    name = this.dwDimensionName;
                } else if (name.equals("PERIOD")) {
                    name = this.periodDimensionName;
                }
                String value = entry.getValue();
                dimensionValueSet.setValue(name, (Object)value);
            }
            String key = CheckResultUtil.buildCheckRecordMapKey(result.getFormKey(), dimensionValueSet);
            if (this.checkRecordData.containsKey(key)) {
                CheckRecordData recordData = this.checkRecordData.get(key);
                recordData.setStatus(2);
                int formulaCheckType = result.getFormulaCheckType();
                int count = 0;
                if (recordData.getCheckTypeCount().containsKey(formulaCheckType)) {
                    count = recordData.getCheckTypeCount().get(formulaCheckType);
                }
                recordData.getCheckTypeCount().put(formulaCheckType, ++count);
            }
        }
    }

    private FmlCheckResultEntity buildResult(FormulaCheckEventImpl event, DimensionValueSet dimensionValueSet) {
        String dwCode;
        Object entityOrder;
        FmlCheckResultEntity result = new FmlCheckResultEntity();
        Formula formulaObj = event.getFormulaObj();
        String formKey = formulaObj.getFormKey();
        if (formKey == null) {
            formKey = "00000000-0000-0000-0000-000000000000";
        }
        String formulaKey = formulaObj.getId();
        String parsedExpressionKey = event.getParsedExpresionKey();
        int globRow = event.getWildcardRow();
        int globCol = event.getWildcardCol();
        result.setActionID(this.actionID);
        DimensionValueSet masterKey = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String name = dimensionValueSet.getName(i);
            if (!this.formSchemeEntityNames.contains(name)) continue;
            Object dimValue = dimensionValueSet.getValue(i);
            masterKey.setValue(name, dimValue);
            String dimColName = name;
            if (name.equals(this.dwDimensionName)) {
                dimColName = "MDCODE";
            } else if (name.equals(this.periodDimensionName)) {
                dimColName = "PERIOD";
            }
            result.getDimMap().put(dimColName, dimValue.toString());
        }
        result.setMasterKey(masterKey);
        result.setFormulaSchemeKey(this.formulaSchemeKey);
        result.setFormKey(formKey);
        result.setFormulaKey(formulaKey);
        result.setFormulaExpressionKey(parsedExpressionKey);
        result.setFormulaCheckType(formulaObj.getChecktype());
        result.setGlobCol(globCol);
        result.setGlobRow(globRow);
        FormulaNodeSaveInfo formulaNodeSaveInfo = CheckResultUtil.getNodes(this.decimalFormat, event, dimensionValueSet, this.getNodeInfoQueryCache());
        List<FormulaNodeSaveObj> nodes = formulaNodeSaveInfo.getFmlNodeSaveObjs();
        String nodesStr = "";
        if (!nodes.isEmpty()) {
            try {
                nodesStr = mapper.writeValueAsString(nodes);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw new LogicMappingException(ExceptionEnum.FORMULA_NODE_EXC.getCode());
            }
        }
        this.setLRD(event, formulaNodeSaveInfo, result);
        result.setErrorDesc(nodesStr);
        if (ActionEnum.BATCH_CHECK == this.action && this.dwEntityDataLoader != null && (entityOrder = this.dwEntityDataLoader.getRowByEntityDataKey(dwCode = dimensionValueSet.getValue(this.dwDimensionName).toString()).getEntityOrder()) instanceof BigDecimal) {
            result.setUnitOrder((BigDecimal)entityOrder);
        }
        result.setFormOrder(CheckResultUtil.getFormOrder(formKey));
        result.setFormulaOrder(CheckResultUtil.getFormulaOrder(formulaKey));
        result.setDimStr(CheckResultUtil.getDimStr(dimensionValueSet, this.formSchemeEntityNames));
        Map<String, DimensionValue> dimensionSet = DimensionUtil.getDimensionSet(dimensionValueSet);
        result.setRecId(CheckResultUtil.buildRECID(this.formulaSchemeKey, formKey, formulaKey, globRow, globCol, dimensionSet));
        if (this.checkErrorFilter == null || this.checkErrorFilter.filter(result)) {
            this.customHandleError(dimensionValueSet, masterKey, result, nodes);
        }
        return result;
    }

    private void setLRD(FormulaCheckEventImpl event, FormulaNodeSaveInfo formulaNodeSaveInfo, FmlCheckResultEntity result) {
        boolean containMaskData = formulaNodeSaveInfo.isContainMaskData();
        AbstractData leftValue = event.getLeftValue();
        AbstractData rightValue = event.getRightValue();
        AbstractData differenceValue = event.getDifferenceValue();
        if (containMaskData) {
            result.setLeft(leftValue == null ? "" : "****");
            result.setRight(rightValue == null ? "" : "****");
            result.setBalance(differenceValue == null ? "" : "****");
        } else if (this.removeUTF0Char) {
            result.setLeft(CheckResultUtil.removeUTF0Char(CheckResultUtil.valueFormat(this.decimalFormat, leftValue)));
            result.setRight(CheckResultUtil.removeUTF0Char(CheckResultUtil.valueFormat(this.decimalFormat, rightValue)));
            result.setBalance(CheckResultUtil.removeUTF0Char(CheckResultUtil.valueFormat(this.decimalFormat, differenceValue)));
        } else {
            result.setLeft(CheckResultUtil.valueFormat(this.decimalFormat, leftValue));
            result.setRight(CheckResultUtil.valueFormat(this.decimalFormat, rightValue));
            result.setBalance(CheckResultUtil.valueFormat(this.decimalFormat, differenceValue));
        }
    }

    private void customHandleError(DimensionValueSet rowKey, DimensionValueSet masterKey, FmlCheckResultEntity result, List<FormulaNodeSaveObj> nodes) {
        if (!CollectionUtils.isEmpty(this.checkErrHandlers)) {
            CheckErrorObj checkErrorObj = new CheckErrorObj();
            checkErrorObj.setMasterKey(new DimensionCombinationBuilder(masterKey).getCombination());
            checkErrorObj.setRowKey(new DimensionCombinationBuilder(rowKey).getCombination());
            checkErrorObj.setRecid(result.getRecId());
            checkErrorObj.setFormulaSchemeKey(result.getFormulaSchemeKey());
            checkErrorObj.setFormKey(result.getFormKey());
            checkErrorObj.setFormulaKey(result.getFormulaKey());
            checkErrorObj.setFormulaExpressionKey(result.getFormulaExpressionKey());
            checkErrorObj.setFormulaCheckType(result.getFormulaCheckType());
            checkErrorObj.setGlobeRow(result.getGlobRow());
            checkErrorObj.setGlobeCol(result.getGlobCol());
            checkErrorObj.setLeft(result.getLeft());
            checkErrorObj.setRight(result.getRight());
            checkErrorObj.setBalance(result.getBalance());
            checkErrorObj.setNodes(nodes);
            for (ICheckErrHandler checkErrHandler : this.checkErrHandlers) {
                checkErrHandler.handle(checkErrorObj);
            }
        }
    }

    @Override
    public void finish() {
        if (!this.finished) {
            try {
                super.finish();
                this.checkDataHandle();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void checkDataHandle() {
        if (!CollectionUtils.isEmpty(this.checkResultEntities)) {
            CheckData executeCheckData;
            if (ActionEnum.ALL_CHECK == this.action) {
                boolean cache;
                boolean bl = cache = this.allCheckCacheCtrl != null && this.allCheckCacheCtrl.cache(this.checkResultEntities.size());
                if (cache) {
                    this.getAllCheckResultSaver().cacheSave(this.checkResultEntities, this.formSchemeKey, this.cacheKey, this.checkDim, this.actionID);
                } else {
                    this.getAllCheckResultSaver().dbSave(this.checkResultEntities, this.formSchemeKey, this.cacheKey);
                }
            } else if (ActionEnum.BATCH_CHECK == this.action) {
                this.getBatchCheckResultSaver().dbSave(this.checkResultEntities, this.formSchemeKey, this.actionID);
            } else if (ActionEnum.CHECK == this.action && null != (executeCheckData = this.getExecuteCheckData())) {
                executeCheckData.getCheckResultEntities().addAll(this.checkResultEntities);
            }
        }
    }

    public boolean isCancel() {
        if (this.asyncTaskMonitor != null) {
            return this.asyncTaskMonitor.isCancel();
        }
        CheckData executeCheckData = this.getExecuteCheckData();
        if (null == executeCheckData) {
            return false;
        }
        if (this.canceled) {
            return true;
        }
        AtomicInteger errorCount = executeCheckData.getErrorCount();
        this.canceled = this.checkMax.getMax() >= 0 && errorCount.get() > this.checkMax.getMax();
        return this.canceled;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public List<FmlCheckResultEntity> getCheckResultEntities() {
        return this.checkResultEntities;
    }

    public Map<String, CheckRecordData> getCheckRecordData() {
        return this.checkRecordData;
    }

    public void setCheckRecordData(Map<String, CheckRecordData> checkRecordData) {
        this.checkRecordData = checkRecordData;
    }

    public void setCheckDim(DimensionValueSet checkDim) {
        this.checkDim = checkDim;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public void setOnlyDimCartesian(List<DimensionValueSet> onlyDimCartesian) {
        this.onlyDimCartesian = onlyDimCartesian;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setAllCheckCacheCtrl(IAllCheckCacheCtrl allCheckCacheCtrl) {
        this.allCheckCacheCtrl = allCheckCacheCtrl;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    private AllCheckResultSaver getAllCheckResultSaver() {
        return this.beanHelper.getAllCheckResultSaver();
    }

    private BatchCheckResultSaver getBatchCheckResultSaver() {
        return this.beanHelper.getBatchCheckResultSaver();
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setDwDimensionName(String dwDimensionName) {
        this.dwDimensionName = dwDimensionName;
        this.formSchemeEntityNames.add(dwDimensionName);
    }

    public void setPeriodDimensionName(String periodDimensionName) {
        this.periodDimensionName = periodDimensionName;
        this.formSchemeEntityNames.add(periodDimensionName);
    }

    public void setDimDimensionName(List<String> dimDimensionName) {
        this.dimDimensionName = dimDimensionName;
        this.formSchemeEntityNames.addAll(dimDimensionName);
    }

    public String getDwDimensionName() {
        return this.dwDimensionName;
    }

    public String getPeriodDimensionName() {
        return this.periodDimensionName;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }

    public void setCheckMax(CheckMax checkMax) {
        this.checkMax = checkMax;
    }

    private CheckData getExecuteCheckData() {
        return CheckDataCollector.getInstance().get(this.executeId);
    }

    public void setDwEntityDataLoader(EntityDataLoader dwEntityDataLoader) {
        this.dwEntityDataLoader = dwEntityDataLoader;
    }

    private NodeInfoQueryCache getNodeInfoQueryCache() {
        if (this.nodeInfoQueryCache == null) {
            this.nodeInfoQueryCache = new NodeInfoQueryCache(this.executorContext, this.formSchemeEntityNames, this.beanHelper);
        }
        return this.nodeInfoQueryCache;
    }

    public void setDatabase(IDatabase database) {
        this.removeUTF0Char = database != null && (database.isDatabase("polardb") || database.isDatabase("POSTGRESQL"));
    }

    public void setCheckErrorFilter(ICheckErrorFilter checkErrorFilter) {
        this.checkErrorFilter = checkErrorFilter;
    }

    public void setCheckErrHandlers(List<ICheckErrHandler> checkErrHandlers) {
        this.checkErrHandlers = checkErrHandlers;
    }

    public void setAllowableError(BigDecimal allowableError) {
        if (allowableError == null) {
            return;
        }
        this.allowableError = allowableError.abs();
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }
}

