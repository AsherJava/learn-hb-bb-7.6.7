/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.GatherException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMemTableLoader
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.IQuerySqlUpdater
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.log.UnitReportLog
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.util.JsonUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.formtype.common.EntityUnitNatureGetter
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.service.IFormTypeApplyService
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.GatherException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMemTableLoader;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.log.UnitReportLog;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.util.JsonUtil;
import com.jiuqi.nr.data.engine.execption.DataGatherExecption;
import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import com.jiuqi.nr.data.engine.gather.CheckSqlItem;
import com.jiuqi.nr.data.engine.gather.DimGatherSqlGenerator;
import com.jiuqi.nr.data.engine.gather.FieldItem;
import com.jiuqi.nr.data.engine.gather.FloatTableGatherSetting;
import com.jiuqi.nr.data.engine.gather.GatherAssistantTable;
import com.jiuqi.nr.data.engine.gather.GatherCondition;
import com.jiuqi.nr.data.engine.gather.GatherDataTable;
import com.jiuqi.nr.data.engine.gather.GatherDirection;
import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.engine.gather.GatherEntityValue;
import com.jiuqi.nr.data.engine.gather.GatherEventHandler;
import com.jiuqi.nr.data.engine.gather.GatherSqlGenerater;
import com.jiuqi.nr.data.engine.gather.GatherTableDefine;
import com.jiuqi.nr.data.engine.gather.GatherTempTableHandler;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.NodeCheckResult;
import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nr.data.engine.gather.ShowItem;
import com.jiuqi.nr.data.engine.gather.SqlItem;
import com.jiuqi.nr.data.engine.gather.param.impl.CancelGatherInfo;
import com.jiuqi.nr.data.engine.gather.param.impl.GatherCancelVO;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.formtype.common.EntityUnitNatureGetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.lang.invoke.LambdaMetafactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

public class DataGatherImpl
implements IDataGather {
    private static final Logger logger = LoggerFactory.getLogger(DataGatherImpl.class);
    private String targetKey;
    private List<String> targetKeys;
    private GatherCondition condition;
    private GatherEntityFilterProvider provider;
    private IMonitor monitor;
    private DataField refUnitField;
    private IEntityAttribute minusField;
    private GatherEntityValue gatherEntityValue;
    private NotGatherEntityValue notGatherValues;
    private Integer maxLevel;
    private String gatherTempTable;
    private String notGatherTempTable;
    private boolean isMinus;
    private QueryParam queryParam;
    private IQuerySqlUpdater querySqlUpdater;
    private final Map<String, String> unitCache = new HashMap<String, String>();
    private HashSet<String> unpassedNodeCheck;
    private GatherEventHandler gatherEventHandler;
    private IRunTimeViewController viewController;
    private boolean isLeaf = false;
    private String executionId;
    private Map<String, String> gatherSingleDims = new LinkedHashMap<String, String>();
    private IEntityTable entityTable;
    private IEntityMetaService entityMetaService;
    private SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private IDimensionProvider dimensionProvider;
    private FileCalculateService fileCalculateService;
    private IMemTableLoader memTableLoader;
    private UnitReportLog unitReportLog;
    public static final String NO_SUMMARY_UNIT = "no_summary_unit";
    private final Function<DataField, ColumnModelDefine> dataFieldConvertCol = field -> {
        List deploy = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{field.getKey()});
        if (CollectionUtils.isEmpty(deploy)) {
            return null;
        }
        String columnKey = ((DataFieldDeployInfo)deploy.stream().findFirst().get()).getColumnModelKey();
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        return dataModelService.getColumnModelDefineByID(columnKey);
    };
    private final BigDecimal formatDecimal = new BigDecimal(10000);

    @Override
    public void setGatherCondition(GatherCondition condition) {
        this.condition = condition;
    }

    @Override
    public void setEntityFilterProvider(GatherEntityFilterProvider provider) {
        this.provider = provider;
    }

    @Override
    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void executeNodeGather(ExecutorContext executorContext, String targetKey) throws Exception {
        this.targetKey = targetKey;
        boolean isSuccess = this.initGatherCheck(executorContext, false);
        if (!isSuccess) {
            return;
        }
        if (this.gatherEventHandler != null) {
            this.gatherEventHandler.beforeNodeGather(targetKey, this.condition);
        }
        this.initSingleSelectGatherDims();
        this.condition.setGatherSingleDims(this.gatherSingleDims);
        this.initGatherEntitys(executorContext);
        if (this.isLeaf) {
            this.monitor.message("\u6c47\u603b\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u6c47\u603b\u3002", null);
            logger.info("\u6c47\u603b\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
            return;
        }
        if (this.gatherEntityValue.getIdValues().isEmpty()) {
            this.monitor.message("\u6c47\u603b\u7684\u6e90\u5355\u4f4d\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\uff0c\u5efa\u8bae\u67e5\u770b\u662f\u5426\u5df2\u542f\u7528\u4ec5\u6c47\u603b\u5df2\u4e0a\u62a5\u5355\u4f4d\u9009\u9879\u3002", null);
            logger.info("\u6c47\u603b\u7684\u6e90\u5355\u4f4d\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\uff0c\u5efa\u8bae\u67e5\u770b\u662f\u5426\u5df2\u542f\u7528\u4ec5\u6c47\u603b\u5df2\u4e0a\u62a5\u5355\u4f4d\u9009\u9879\u3002");
            throw new DataGatherExecption(NO_SUMMARY_UNIT);
        }
        this.condition.setGatherEntityValue(this.gatherEntityValue);
        this.getExecutionId();
        GatherSqlGenerater sqlGenerator = new GatherSqlGenerater(executorContext, this.queryParam.getConnection(), false, this.executionId, this.condition.getContainTargetKey(), this.condition.getTaskKey(), this.condition.getFormSchemeKey(), this.gatherSingleDims, this.memTableLoader, this.fileCalculateService);
        this.doNodeGather(executorContext, sqlGenerator);
    }

    @Override
    public void executeNodeGatherByDim(ExecutorContext executorContext, String targetKey) throws Exception {
        this.targetKey = targetKey;
        boolean isSuccess = this.initGatherCheck(executorContext, false);
        boolean dimCheck = this.dimCheckBeforeGather();
        if (!isSuccess && !dimCheck) {
            return;
        }
        if (this.gatherEventHandler != null) {
            this.gatherEventHandler.beforeNodeGather(targetKey, this.condition);
        }
        this.initGatherByDimEntities();
        if (this.gatherEntityValue.getIdValues().isEmpty()) {
            this.monitor.message("\u6c47\u603b\u7684\u6e90\u60c5\u666f\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\u3002", null);
            logger.info("\u6c47\u603b\u7684\u6e90\u60c5\u666f\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
            throw new DataGatherExecption(NO_SUMMARY_UNIT);
        }
        this.condition.setGatherEntityValue(this.gatherEntityValue);
        this.getExecutionId();
        DimGatherSqlGenerator sqlGenerator = new DimGatherSqlGenerator(executorContext, this.queryParam.getConnection(), false, this.executionId, this.condition.getContainTargetKey(), this.condition.getTaskKey(), this.condition.getFormSchemeKey(), this.gatherSingleDims, this.memTableLoader, this.fileCalculateService, this.condition.getGatherDimName(), targetKey);
        this.doNodeGather(executorContext, sqlGenerator);
    }

    private boolean dimCheckBeforeGather() {
        Object currentValue;
        int index;
        DimensionValueSet targetDimensions = this.condition.getTargetDimension();
        DimensionValueSet sourceDimensions = this.condition.getSourceDimensions();
        String gatherDimName = this.condition.getGatherDimName();
        if (targetDimensions != null && targetDimensions.size() > 0) {
            for (index = 0; index < targetDimensions.size(); ++index) {
                currentValue = targetDimensions.getValue(index);
                if (!(currentValue instanceof List)) continue;
                if (this.monitor != null) {
                    this.monitor.message("\u76ee\u6807\u5355\u4f4d\u60c5\u666f\u503c\u4e0d\u652f\u6301\u5217\u8868", null);
                    this.monitor.finish();
                }
                return false;
            }
        }
        if (sourceDimensions != null && sourceDimensions.size() > 0) {
            for (index = 0; index < sourceDimensions.size(); ++index) {
                currentValue = sourceDimensions.getValue(index);
                String name = sourceDimensions.getName(0);
                if (name.equals(gatherDimName) || !(currentValue instanceof List)) continue;
                if (this.monitor != null) {
                    this.monitor.message("\u6765\u6e90\u60c5\u666f\u4e0d\u652f\u6301\u60c5\u666f\u503c\uff08\u9664\u9700\u8981\u6c47\u603b\u7684\u60c5\u666f\u5916\uff09\u662f\u5217\u8868", null);
                    this.monitor.finish();
                }
                return false;
            }
        }
        return true;
    }

    private void initGatherByDimEntities() {
        DimensionValueSet targetDimension = this.condition.getTargetDimension();
        DimensionValueSet sourceDimensions = this.condition.getSourceDimensions();
        String gatherDimName = this.condition.getGatherDimName();
        this.gatherEntityValue = new GatherEntityValue();
        Object value = sourceDimensions.getValue(gatherDimName);
        List<String> gatherDims = new ArrayList<String>();
        if (value instanceof List) {
            gatherDims = (List)value;
        } else {
            gatherDims.add((String)value);
        }
        String aimDim = (String)targetDimension.getValue(gatherDimName);
        ArrayList<String> parentKeys = new ArrayList<String>();
        ArrayList<Integer> levelValues = new ArrayList<Integer>();
        for (int index = 0; index < gatherDims.size(); ++index) {
            parentKeys.add(aimDim);
            levelValues.add(1);
        }
        this.gatherEntityValue.setIdValues(gatherDims);
        this.gatherEntityValue.setPidValues(parentKeys);
        this.gatherEntityValue.setLevelValues(levelValues);
        this.isMinus = false;
        this.maxLevel = 1;
        if (this.monitor != null) {
            this.monitor.message("\u6309\u60c5\u666f\u6c47\u603b\u5355\u4f4d\u83b7\u53d6\u7ed3\u675f", null);
            this.monitor.onProgress(0.1);
        }
    }

    /*
     * Exception decompiling
     */
    private void doNodeGather(ExecutorContext executorContext, GatherSqlGenerater sqlGenerator) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 22[WHILELOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean isCancel(GatherTableDefine gatherTableDefine, CancelGatherInfo cancelGatherInfo, List<String> allPIds, Map<String, Set<String>> notGatherUnitByFormKey, GatherCancelVO gatherCancelVO) {
        if (this.monitor.isCancel() && !cancelGatherInfo.getDoneFormKey().contains(gatherTableDefine.getFormId())) {
            this.doDoneFormInfoLog(allPIds, notGatherUnitByFormKey, cancelGatherInfo.getDoneFormKey());
            gatherCancelVO.setParam(new GatherCancelVO.Param(cancelGatherInfo.getDoneFormKey().size()));
            this.monitor.canceled(JsonUtil.objectToJson((Object)gatherCancelVO), (Object)cancelGatherInfo);
            return true;
        }
        return false;
    }

    private void doDoneFormInfoLog(List<String> allPIds, Map<String, Set<String>> notGatherUnitByFormKey, Set<String> doneFormKey) {
        for (String formKey : doneFormKey) {
            Set<String> notGatherUnits = notGatherUnitByFormKey.get(formKey);
            for (String pid : allPIds) {
                if (notGatherUnits != null && notGatherUnits.contains(pid) || this.unitReportLog == null) continue;
                this.unitReportLog.addFormToUnit(pid, formKey);
            }
        }
    }

    private List<DimensionValueSet> getDimensions(DimensionValueSet targetDimension, List<String> allPIds) {
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        String dimensionName = this.getDimensionName(this.condition.getUnitView().getEntityId());
        for (String pId : allPIds) {
            DimensionValueSet dimensionValueSet = new DimensionValueSet(targetDimension);
            dimensionValueSet.setValue(dimensionName, (Object)pId);
            dimensionValueSet.setValue("DATATIME", (Object)this.condition.getPeriodCode());
            dimensionValueSets.add(dimensionValueSet);
        }
        return dimensionValueSets;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public NodeCheckResult executeNodeCheck(ExecutorContext executorContext, String targetKey) throws Exception {
        nrDbHelper = (NrdbHelper)BeanUtil.getBean(NrdbHelper.class);
        result = new NodeCheckResult();
        errorItems = new ArrayList<CheckErrorItem>();
        result.setErrorItems(errorItems);
        this.unpassedNodeCheck = new HashSet<E>();
        this.targetKey = targetKey;
        isSuccess = this.initGatherCheck(executorContext, true);
        if (!isSuccess) {
            return result;
        }
        connection = null;
        tempTable = null;
        notGatherTempTable = null;
        try {
            this.initSingleSelectGatherDims();
            this.initGatherEntitys(executorContext);
            if (this.isLeaf) {
                this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u8282\u70b9\u68c0\u67e5\u3002", null);
                DataGatherImpl.logger.info("\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u8282\u70b9\u68c0\u67e5\u3002");
                var10_10 = result;
                return var10_10;
            }
            connection = this.queryParam.getConnection();
            gatherTables = this.condition.getGatherTables();
            isEnableNrdb = nrDbHelper.isEnableNrdb();
            if (!isEnableNrdb) {
                tempTable = this.fillGatherTempTable();
                notGatherTempTable = this.fillNotGatherTempTable();
            }
            result.setCheckedNodeCount(this.getNodeCheckCount());
            perProcess = 0.8 / (double)gatherTables.size();
            currentProcess = 0.15;
            abstractMonitor = null;
            if (this.monitor != null) {
                this.monitor.onProgress(currentProcess);
                abstractMonitor = (AbstractMonitor)this.monitor;
                if (abstractMonitor != null) {
                    abstractMonitor.setStep(perProcess);
                }
            }
            cancelGatherInfo = new CancelGatherInfo(targetKey);
            cancelGatherInfo.setAllFormKey(gatherTables.stream().map((Function<GatherTableDefine, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getFormId(), (Lcom/jiuqi/nr/data/engine/gather/GatherTableDefine;)Ljava/lang/String;)()).collect(Collectors.toSet()));
            gatherCancelVO = new GatherCancelVO();
            gatherCancelVO.setCode("node_check_cancel");
            for (GatherTableDefine gatherTableDefine : gatherTables) {
                block29: {
                    if (this.monitor == null || !this.monitor.isCancel()) break block29;
                    gatherCancelVO.setParam(new GatherCancelVO.Param(cancelGatherInfo.getDoneFormKey().size()));
                    this.monitor.canceled(JsonUtil.objectToJson((Object)gatherCancelVO), new Object());
                    var21_26 = result;
                    ** try [egrp 3[TRYBLOCK] [1 : 437->450)] { 
lbl46:
                    // 1 sources

                    ** GOTO lbl-1000
                }
                cancelGatherInfo.getDoneFormKey().add(gatherTableDefine.getFormId());
                useSql = this.canUseSql(gatherTableDefine, connection, true);
                if (useSql) {
                    this.executeCheckTableBySql(executorContext, connection, gatherTableDefine, errorItems, isEnableNrdb);
                } else {
                    this.executeCheckTable(executorContext, gatherTableDefine, errorItems);
                }
                if (this.monitor == null || abstractMonitor == null) continue;
                abstractMonitor.step();
            }
            result.setUnpassedNodeCount(this.unpassedNodeCheck.size());
            var19_22 = result;
            ** try [egrp 5[TRYBLOCK] [2 : 583->596)] { 
lbl61:
            // 1 sources

            ** GOTO lbl-1000
        }
        catch (Exception e) {
            DataGatherImpl.logger.error(e.getMessage(), e);
            if (this.monitor != null) {
                this.monitor.error("\u8282\u70b9\u68c0\u67e5\u5931\u8d25", (Object)e);
                this.monitor.finish();
            }
            result.setUnpassedNodeCount(this.unpassedNodeCheck.size());
            var11_15 = result;
            return var11_15;
        }
lbl-1000:
        // 1 sources

        {
            GatherAssistantTable.releaseTempTable(tempTable);
            GatherAssistantTable.releaseTempTable(notGatherTempTable);
        }
lbl73:
        // 1 sources

        catch (Exception var22_27) {
            // empty catch block
        }
        if (this.monitor != null) {
            this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
            this.monitor.finish();
        }
        this.queryParam.closeConnection();
        return var21_26;
lbl-1000:
        // 1 sources

        {
            GatherAssistantTable.releaseTempTable(tempTable);
            GatherAssistantTable.releaseTempTable(notGatherTempTable);
        }
lbl83:
        // 1 sources

        catch (Exception var20_24) {
            // empty catch block
        }
        if (this.monitor != null) {
            this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
            this.monitor.finish();
        }
        this.queryParam.closeConnection();
        return var19_22;
        finally {
            try {
                GatherAssistantTable.releaseTempTable(tempTable);
                GatherAssistantTable.releaseTempTable(notGatherTempTable);
            }
            catch (Exception var12_17) {}
            if (this.monitor != null) {
                this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
                this.monitor.finish();
            }
            this.queryParam.closeConnection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public NodeCheckResult executeBatchNodeCheck(ExecutorContext executorContext, List<String> targetKeys) throws Exception {
        result = new NodeCheckResult();
        errorItems = new ArrayList<CheckErrorItem>();
        result.setErrorItems(errorItems);
        this.unpassedNodeCheck = new HashSet<E>();
        this.targetKeys = targetKeys;
        isSuccess = this.initGatherCheck(executorContext, true);
        if (!isSuccess) {
            return result;
        }
        connection = null;
        tempTable = null;
        try {
            block29: {
                gatherForms = this.getGatherForms();
                formKeys = this.getCanGatherForms();
                needCheckForm = this.needCheckForm(formKeys, gatherForms);
                this.initBatchGatherEntitys(executorContext);
                if (!this.isLeaf) break block29;
                this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u8282\u70b9\u68c0\u67e5\u3002", null);
                DataGatherImpl.logger.info("\u8282\u70b9\u68c0\u67e5\u7684\u5355\u4f4d\u662f\u53f6\u5b50\u5355\u4f4d\uff0c\u65e0\u6cd5\u8282\u70b9\u68c0\u67e5\u3002");
                var11_13 = result;
                ** try [egrp 1[TRYBLOCK] [0 : 119->127)] { 
lbl21:
                // 1 sources

                ** GOTO lbl-1000
            }
            connection = this.queryParam.getConnection();
            gatherTables = this.condition.getGatherTables();
            tempTable = this.fillGatherTempTable();
            result.setCheckedNodeCount(this.getNodeCheckCount());
            perProcess = 0.8 / (double)gatherTables.size();
            currentProcess = 0.15;
            abstractMonitor = null;
            if (this.monitor != null) {
                this.monitor.onProgress(currentProcess);
                abstractMonitor = (AbstractMonitor)this.monitor;
                if (abstractMonitor != null) {
                    abstractMonitor.setStep(perProcess);
                }
            }
            cancelGatherInfo = new CancelGatherInfo(this.targetKey);
            cancelGatherInfo.setAllFormKey(gatherTables.stream().map((Function<GatherTableDefine, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getFormId(), (Lcom/jiuqi/nr/data/engine/gather/GatherTableDefine;)Ljava/lang/String;)()).collect(Collectors.toSet()));
            for (GatherTableDefine gatherTableDefine : gatherTables) {
                block30: {
                    if (this.monitor == null || !this.monitor.isCancel()) break block30;
                    this.monitor.canceled("\u8282\u70b9\u68c0\u67e5\u53d6\u6d88\u5b8c\u6210\uff01", new Object());
                    var20_24 = result;
                    ** try [egrp 3[TRYBLOCK] [1 : 377->385)] { 
lbl42:
                    // 1 sources

                    ** GOTO lbl-1000
                }
                cancelGatherInfo.getDoneFormKey().add(gatherTableDefine.getTableDefine().getKey());
                if (needCheckForm && !formKeys.contains(gatherTableDefine.getFormId())) {
                    DataGatherImpl.logger.warn(String.format("\u62a5\u8868\u4e0d\u7b26\u5408\u6c47\u603b\u6761\u4ef6\uff0c\u4e0d\u8fdb\u884c\u6c47\u603b\uff01formKey:%s", new Object[]{gatherTableDefine.getFormId()}));
                    continue;
                }
                useSql = this.canUseSql(gatherTableDefine, connection, true);
                if (useSql) {
                    this.executeCheckTableBySql(executorContext, connection, gatherTableDefine, errorItems, false);
                } else {
                    this.executeCheckTable(executorContext, gatherTableDefine, errorItems);
                }
                if (this.monitor == null || abstractMonitor == null) continue;
                abstractMonitor.step();
            }
            result.setUnpassedNodeCount(this.unpassedNodeCheck.size());
            var18_20 = result;
            ** try [egrp 5[TRYBLOCK] [2 : 568->576)] { 
lbl60:
            // 1 sources

            ** GOTO lbl-1000
        }
        catch (Exception e) {
            DataGatherImpl.logger.error(e.getMessage(), e);
            if (this.monitor != null) {
                this.monitor.error("\u8282\u70b9\u68c0\u67e5\u5931\u8d25", (Object)e);
                this.monitor.finish();
            }
            result.setUnpassedNodeCount(this.unpassedNodeCheck.size());
            var9_10 = result;
            return var9_10;
        }
lbl-1000:
        // 1 sources

        {
            GatherAssistantTable.releaseTempTable(tempTable);
        }
lbl74:
        // 1 sources

        catch (Exception var12_15) {
            // empty catch block
        }
        if (this.monitor != null) {
            this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
            this.monitor.finish();
        }
        this.queryParam.closeConnection();
        return var11_13;
lbl-1000:
        // 1 sources

        {
            GatherAssistantTable.releaseTempTable(tempTable);
        }
lbl83:
        // 1 sources

        catch (Exception var21_25) {
            // empty catch block
        }
        if (this.monitor != null) {
            this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
            this.monitor.finish();
        }
        this.queryParam.closeConnection();
        return var20_24;
lbl-1000:
        // 1 sources

        {
            GatherAssistantTable.releaseTempTable(tempTable);
        }
lbl92:
        // 1 sources

        catch (Exception var19_22) {
            // empty catch block
        }
        if (this.monitor != null) {
            this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
            this.monitor.finish();
        }
        this.queryParam.closeConnection();
        return var18_20;
        finally {
            try {
                GatherAssistantTable.releaseTempTable(tempTable);
            }
            catch (Exception var10_12) {}
            if (this.monitor != null) {
                this.monitor.message("\u8282\u70b9\u68c0\u67e5\u7ed3\u675f", null);
                this.monitor.finish();
            }
            this.queryParam.closeConnection();
        }
    }

    private int getNodeCheckCount() {
        List<String> pidValues = this.gatherEntityValue.getPidValues();
        if (null == pidValues || pidValues.size() <= 0) {
            return 0;
        }
        HashSet<String> tempList = new HashSet<String>();
        for (String key : pidValues) {
            tempList.add(key);
        }
        return tempList.size();
    }

    private void executeCheckTableBySql(ExecutorContext executorContext, Connection connection, GatherTableDefine tableDefine, List<CheckErrorItem> errorItems, boolean isEnableNrdb) throws Exception {
        if (tableDefine.getTableDefine().getDataTableGatherType() == DataTableGatherType.NONE) {
            return;
        }
        GatherTableDefine gatherTableDefine = this.getGatherTableDefine(tableDefine, true, false);
        if (gatherTableDefine.getUnitField() == null || gatherTableDefine.getGatherFields().size() <= 0) {
            return;
        }
        GatherSqlGenerater sqlGenerater = new GatherSqlGenerater(executorContext, connection, true, this.executionId, this.condition.getContainTargetKey(), this.condition.getTaskKey(), this.condition.getFormSchemeKey(), this.gatherSingleDims, this.memTableLoader, this.fileCalculateService);
        Collections.sort(gatherTableDefine.getGatherFields(), new Comparator<DataField>(){

            @Override
            public int compare(DataField leftValue, DataField rightValue) {
                if (StringUtils.isEmpty((String)leftValue.getOrder()) || StringUtils.isEmpty((String)rightValue.getOrder())) {
                    return leftValue.getCode().compareTo(rightValue.getCode());
                }
                return leftValue.getOrder().compareTo(rightValue.getOrder());
            }
        });
        List<GatherDataTable> splitGatherTables = this.splitGatherTable(gatherTableDefine);
        for (GatherDataTable splitGatherTable : splitGatherTables) {
            if (isEnableNrdb) {
                sqlGenerater.executeNodeCheckForNrdb(splitGatherTable, this.gatherEntityValue, this.maxLevel, this.condition.getPeriodCode(), this.condition.getUnitView(), this.condition.getSourceDimensions(), errorItems, this.condition.getPrecisionValue(), this.unitCache, this.unpassedNodeCheck);
                continue;
            }
            String versionField = null;
            CheckSqlItem checkSqlItem = sqlGenerater.buildCheckTableSqls(splitGatherTable, this.gatherTempTable, this.notGatherTempTable, this.maxLevel, this.condition.getPeriodCode(), this.condition.getSourceDimensions(), this.condition.getTargetDimension(), this.querySqlUpdater, versionField);
            BigDecimal precisionValue = this.condition.getPrecisionValue() == null ? BigDecimal.valueOf(0.0) : this.condition.getPrecisionValue().abs();
            String periodCode = this.condition.getPeriodCode();
            List<SqlItem> executeSqls = checkSqlItem.getSqlItems();
            String regionKey = tableDefine.getRegionKey();
            for (SqlItem executeSql : executeSqls) {
                try {
                    sqlGenerater.printLoggerSQL(executeSql, "\u8282\u70b9\u68c0\u67e5", splitGatherTable.getTableModelDefine().getName());
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    int count = 0;
                    Object[] params = executeSql.getParamValues().toArray();
                    HashSet<String> bizKeyOrder = new HashSet<String>();
                    try (PreparedStatement leftPrep = connection.prepareStatement(executeSql.getExecutorSql());){
                        if (params.length > 0) {
                            for (int i = 0; i < params.length; ++i) {
                                GatherSqlGenerater.setValue(params[i], leftPrep, i);
                            }
                        }
                        leftPrep.setFetchSize(100);
                        try (ResultSet resultSet = leftPrep.executeQuery();){
                            stopWatch.stop();
                            logger.debug("\u8017\u65f6\uff1a".concat(String.valueOf(stopWatch.getTotalTimeSeconds())).concat("\u79d2"));
                            count = this.dealCheckResult(executorContext, errorItems, splitGatherTable, executeSql, precisionValue, periodCode, regionKey, resultSet, count, bizKeyOrder);
                        }
                    }
                    boolean fixed = splitGatherTable.getGatherTableDefine().isFixed();
                    if (count >= 1 && (!CollectionUtils.isNotEmpty(this.targetKeys) || count >= this.targetKeys.size()) && fixed) continue;
                    PreparedStatement rightPrep = connection.prepareStatement(executeSql.getExecutorSql().replace("left join", "right join"));
                    Throwable throwable = null;
                    try {
                        if (params.length > 0) {
                            for (int i = 0; i < params.length; ++i) {
                                GatherSqlGenerater.setValue(params[i], rightPrep, i);
                            }
                        }
                        rightPrep.setFetchSize(100);
                        ResultSet resultSet = rightPrep.executeQuery();
                        Throwable throwable2 = null;
                        try {
                            count = this.dealCheckResult(executorContext, errorItems, splitGatherTable, executeSql, precisionValue, periodCode, regionKey, resultSet, count, bizKeyOrder);
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (resultSet == null) continue;
                            if (throwable2 != null) {
                                try {
                                    resultSet.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            resultSet.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (rightPrep == null) continue;
                        if (throwable != null) {
                            try {
                                rightPrep.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        rightPrep.close();
                    }
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                    throw e;
                }
            }
        }
    }

    private String queryFieldName(DataField dataField) {
        if (Objects.isNull(dataField)) {
            return null;
        }
        List deployInfo = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataField.getKey()});
        if (CollectionUtils.isEmpty(deployInfo)) {
            throw new DataGatherExecption("\u672a\u67e5\u8be2\u5230\u6307\u6807\u540d\u79f0");
        }
        return ((DataFieldDeployInfo)deployInfo.stream().findFirst().get()).getFieldName();
    }

    private int dealCheckResult(ExecutorContext executorContext, List<CheckErrorItem> errorItems, GatherDataTable gatherDataTable, SqlItem executeSql, BigDecimal precisionValue, String periodCode, String regionKey, ResultSet resultSet, int count, Set<String> bizKeyOrder) throws Exception {
        GatherTableDefine gatherTableDefine = gatherDataTable.getGatherTableDefine();
        HashMap<String, String> fieldAndDimensionNames = new HashMap<String, String>();
        if (!gatherTableDefine.isFixed()) {
            DefinitionsCache cache = new DefinitionsCache(executorContext);
            TableModelRunInfo tableInfo = cache.getDataModelDefinitionsCache().getTableInfo(gatherDataTable.getTableModelDefine().getName());
            List<DataField> classifyFields = gatherTableDefine.getClassifyFields();
            if (classifyFields != null && !classifyFields.isEmpty()) {
                for (DataField classifyField : classifyFields) {
                    DataFieldDTO field = (DataFieldDTO)classifyField;
                    String dimensionName = null;
                    if (StringUtils.isNotEmpty((String)field.getEntityKey())) {
                        dimensionName = tableInfo.getDimensionName(field.getCode());
                    }
                    if (StringUtils.isEmpty(dimensionName)) {
                        fieldAndDimensionNames.put(classifyField.getCode(), classifyField.getCode());
                        continue;
                    }
                    fieldAndDimensionNames.put(classifyField.getCode(), dimensionName);
                }
            }
        }
        while (resultSet.next()) {
            String unitKey;
            ++count;
            DataField unitField = gatherTableDefine.getUnitField();
            Map<String, DataField> collect = gatherTableDefine.getGatherFields().stream().collect(Collectors.toMap(Basic::getKey, e -> e));
            Object unitValue = DataEngineConsts.formatData((ColumnModelDefine)this.dataFieldConvertCol.apply(unitField), (Object)resultSet.getObject(this.queryFieldName(unitField)), null);
            String string = unitKey = unitValue == null ? null : unitValue.toString();
            if (StringUtils.isEmpty((String)unitKey)) continue;
            DimensionValueSet dimensionValueSet = this.setDimensionValue(unitField, unitKey, periodCode);
            String bizOrderValue = "";
            if (!gatherTableDefine.isFixed()) {
                List<DataField> classifyFields;
                DataField bizOrderField = gatherTableDefine.getBizOrderField();
                if (bizOrderField != null) {
                    Object bizOrderData = DataEngineConsts.formatData((ColumnModelDefine)this.dataFieldConvertCol.apply(bizOrderField), (Object)resultSet.getObject(this.queryFieldName(bizOrderField)), null);
                    String string2 = bizOrderValue = bizOrderData == null ? null : bizOrderData.toString();
                    if (bizKeyOrder.contains(bizOrderValue)) continue;
                    bizKeyOrder.add(bizOrderValue);
                }
                if ((classifyFields = gatherTableDefine.getClassifyFields()) != null && !classifyFields.isEmpty()) {
                    for (DataField classifyField : classifyFields) {
                        Object dimValue = DataEngineConsts.formatData((ColumnModelDefine)this.dataFieldConvertCol.apply(classifyField), (Object)resultSet.getObject(this.queryFieldName(classifyField)), null);
                        String value = dimValue == null ? null : dimValue.toString();
                        String dimensionName = (String)fieldAndDimensionNames.get(classifyField.getCode());
                        dimensionValueSet.setValue(dimensionName, (Object)value);
                    }
                }
            }
            for (FieldItem gatherField : executeSql.getInnerfieldItems()) {
                BigDecimal minusValue;
                Integer decimal;
                int colIndex = gatherField.getColIndex();
                DataField dataField = collect.get(gatherField.getFieldKey());
                BigDecimal parentValue = resultSet.getBigDecimal(colIndex);
                parentValue = parentValue == null ? new BigDecimal(0.0) : parentValue;
                BigDecimal childValue = resultSet.getBigDecimal(colIndex + 1);
                BigDecimal bigDecimal = childValue = childValue == null ? new BigDecimal(0.0) : childValue;
                if (dataField != null && (decimal = dataField.getDecimal()) != null) {
                    parentValue = parentValue.setScale((int)decimal, 4);
                    childValue = childValue.setScale((int)decimal, 4);
                }
                if (parentValue.equals(childValue) || (minusValue = parentValue.subtract(childValue)).abs().compareTo(precisionValue) <= 0) continue;
                CheckErrorItem errorItem = this.getCheckErrorItem(unitField, unitKey, bizOrderValue, periodCode, gatherField, parentValue, childValue, minusValue, dimensionValueSet);
                errorItem.setRegionKey(regionKey);
                this.unpassedNodeCheck.add(unitKey);
                errorItems.add(errorItem);
            }
        }
        return count;
    }

    private CheckErrorItem getCheckErrorItem(DataField unitField, String unitKey, String bizOrderValue, String periodCode, FieldItem gatherField, BigDecimal parentValue, BigDecimal childValue, BigDecimal minusValue, DimensionValueSet dimensionValueSet) {
        CheckErrorItem errorItem = new CheckErrorItem();
        errorItem.setUnitKey(unitKey);
        errorItem.setDimension(this.setDimensionValue(unitField, unitKey, periodCode));
        errorItem.setPeriodCode(periodCode);
        errorItem.setTableKey(gatherField.getTableKey());
        errorItem.setFieldCode(gatherField.getFieldCode());
        errorItem.setParentValue(parentValue);
        errorItem.setChildValue(childValue);
        errorItem.setMinusValue(minusValue);
        errorItem.setFieldKey(gatherField.getFieldKey());
        errorItem.setFieldTitle(gatherField.getFieldTitle());
        errorItem.setBizOrder(bizOrderValue);
        errorItem.setRowKeys(dimensionValueSet);
        if (this.unitCache.containsKey(unitKey)) {
            errorItem.setUnitTitle(this.unitCache.get(unitKey));
        }
        return errorItem;
    }

    private DimensionValueSet setDimensionValue(DataField unitField, String unitKey, String periodCode) {
        String entityKey = unitField.getRefDataEntityKey();
        String dimName = this.entityMetaService.getDimensionName(entityKey);
        DimensionValueSet dimension = this.condition.getTargetDimension();
        DimensionValueSet newKey = new DimensionValueSet(dimension);
        newKey.setValue(dimName, (Object)unitKey);
        newKey.setValue("DATATIME", (Object)periodCode);
        return newKey;
    }

    private void executeCheckTable(ExecutorContext executorContext, GatherTableDefine gatherTableDefine, List<CheckErrorItem> errorItems) {
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void executeSelectGather(ExecutorContext executorContext, String targetKey, List<String> selectKeys) throws Exception {
        NrdbHelper nrDbHelper = (NrdbHelper)BeanUtil.getBean(NrdbHelper.class);
        this.targetKey = targetKey;
        boolean isSuccess = this.initGatherCheck(executorContext, false);
        if (!isSuccess) {
            return;
        }
        if (!this.condition.getContainTargetKey().booleanValue()) {
            selectKeys.remove(targetKey);
        }
        if (selectKeys.isEmpty()) {
            if (this.monitor != null) {
                this.monitor.message("\u6c47\u603b\u7684\u6e90\u5355\u4f4d\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\u3002", null);
                logger.info("\u6c47\u603b\u7684\u6e90\u5355\u4f4d\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
                this.monitor.finish();
            }
            return;
        }
        if (this.gatherEventHandler != null) {
            this.gatherEventHandler.beforeSelectGather(targetKey, this.condition, selectKeys);
        }
        this.initSingleSelectGatherDims();
        this.condition.setGatherSingleDims(this.gatherSingleDims);
        this.initSelectGatherEntitys(executorContext, targetKey, selectKeys);
        this.condition.setGatherEntityValue(this.gatherEntityValue);
        this.getExecutionId();
        GatherSqlGenerater sqlGenerator = new GatherSqlGenerater(executorContext, this.queryParam.getConnection(), false, this.executionId, this.condition.getContainTargetKey(), this.condition.getTaskKey(), this.condition.getFormSchemeKey(), this.gatherSingleDims, this.memTableLoader, this.fileCalculateService);
        this.doNodeGather(executorContext, sqlGenerator);
    }

    private Set<String> getCanGatherForms(ExecutorContext executorContext) throws Exception {
        Set<String> formKeys = Collections.emptySet();
        if (this.provider != null) {
            DimensionValueSet targetDimension = this.condition.getTargetDimension();
            DimensionValueSet queryDim = new DimensionValueSet(targetDimension);
            EntityViewDefine unitView = this.condition.getUnitView();
            if (unitView == null) {
                return Collections.emptySet();
            }
            IEntityRow minusRow = null;
            if (this.condition.getGatherDirection() == GatherDirection.GATHER_TO_MINUS) {
                IEntityRow targetRow;
                if (this.entityTable == null) {
                    this.entityTable = this.getEntityTable(executorContext, this.condition.getUnitView(), this.targetKey, this.condition.getPeriodCode());
                }
                if ((targetRow = this.entityTable.findByEntityKey(this.targetKey)) == null) {
                    throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
                }
                List childRows = this.entityTable.getChildRows(this.targetKey);
                IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
                EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(this.condition.getUnitView().getEntityId());
                for (IEntityRow childRow : childRows) {
                    if (!this.isMinus(entityFormGather, childRow)) continue;
                    minusRow = childRow;
                    break;
                }
            }
            queryDim.setValue(this.getDimensionName(unitView.getEntityId()), (Object)(minusRow == null ? this.targetKey : minusRow.getEntityKeyData()));
            queryDim.setValue("DATATIME", (Object)this.condition.getPeriodCode());
            formKeys = this.provider.getCanGatherForms(queryDim, this.condition.getFormSchemeKey());
        }
        return formKeys;
    }

    private Set<String> getCanGatherForms() {
        Set<String> formKeys = Collections.emptySet();
        if (this.provider != null) {
            DimensionValueSet targetDimension = this.condition.getTargetDimension();
            DimensionValueSet queryDim = new DimensionValueSet(targetDimension);
            EntityViewDefine unitView = this.condition.getUnitView();
            if (unitView == null) {
                return Collections.emptySet();
            }
            queryDim.setValue(this.getDimensionName(unitView.getEntityId()), (Object)this.targetKey);
            queryDim.setValue("DATATIME", (Object)this.condition.getPeriodCode());
            formKeys = this.provider.getCanGatherForms(queryDim, this.condition.getFormSchemeKey());
        }
        return formKeys;
    }

    public String getDimensionName(String entityId) {
        return this.entityMetaService.getDimensionName(entityId);
    }

    private boolean needCheckForm(Set<String> formKeys, Set<String> gatherForms) {
        gatherForms.retainAll(formKeys);
        return CollectionUtils.isNotEmpty(formKeys) && CollectionUtils.isNotEmpty(gatherForms);
    }

    private Set<String> getGatherForms() {
        return this.condition.getGatherTables().stream().map(e -> e.getFormId()).collect(Collectors.toSet());
    }

    private void initSelectGatherEntitys(ExecutorContext executorContext, String targetKey, List<String> selectKeys) throws Exception {
        IEntityTable entityTable = this.getEntityTable(executorContext, this.condition.getUnitView(), this.condition.getPeriodCode());
        IEntityRow targetRow = entityTable.findByEntityKey(targetKey);
        if (targetRow == null) {
            throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
        }
        int keyCount = selectKeys.size();
        ArrayList<String> parentKeys = new ArrayList<String>();
        ArrayList<Integer> levelValues = new ArrayList<Integer>();
        this.gatherEntityValue = new GatherEntityValue();
        for (int index = 0; index < keyCount; ++index) {
            parentKeys.add(targetKey);
            levelValues.add(1);
            this.setDimAttributeValues(targetRow);
        }
        this.gatherEntityValue.setIdValues(selectKeys);
        this.gatherEntityValue.setPidValues(parentKeys);
        this.gatherEntityValue.setLevelValues(levelValues);
        this.isMinus = false;
        this.maxLevel = 1;
        if (this.monitor != null) {
            this.monitor.message("\u6c47\u603b\u5355\u4f4d\u83b7\u53d6\u7ed3\u675f", null);
            this.monitor.onProgress(0.1);
        }
    }

    private boolean canUseSql(GatherTableDefine gatherTableDefine, Connection connection, boolean isNodeCheck) throws SQLException {
        IDatabase dataBase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        if (isNodeCheck) {
            return true;
        }
        return (!gatherTableDefine.isFixed() || !dataBase.getName().equals("HANA")) && StringUtils.isEmpty((String)gatherTableDefine.getBingingExpression()) && gatherTableDefine.getTableDefine().getGatherType() != TableGatherType.TABLE_GATHER_CUSTOM;
    }

    private void executeGatherTable(GatherTableDefine tableDefine, GatherSqlGenerater sqlGenerator) throws Exception {
        if (tableDefine.getTableDefine().getGatherType() == TableGatherType.TABLE_GATHER_NONE) {
            logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u6c47\u603b\u7c7b\u578b\u4e3a\u4e0d\u6c47\u603b\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            return;
        }
        GatherTableDefine gatherTableDefine = this.getGatherTableDefine(tableDefine, false, this.isMinus);
        if (gatherTableDefine.getUnitField() == null || gatherTableDefine.getGatherFields().size() <= 0) {
            if (gatherTableDefine.getUnitField() == null) {
                logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u6307\u6807\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            } else if (gatherTableDefine.getGatherFields().size() <= 0) {
                logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u83b7\u53d6\u4e0d\u5230\u53ef\u6c47\u603b\u6307\u6807\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            }
            return;
        }
        List<GatherDataTable> splitGatherTables = this.splitGatherTable(gatherTableDefine);
        if (gatherTableDefine.isFixed()) {
            for (GatherDataTable splitGatherTable : splitGatherTables) {
                sqlGenerator.executeFixedTableGather(splitGatherTable, this.gatherTempTable, this.notGatherTempTable, this.maxLevel, this.isMinus, this.condition.getPeriodCode(), this.condition.getSourceDimensions(), this.condition.getTargetDimension(), this.querySqlUpdater, null);
            }
        }
    }

    private boolean initGatherCheck(ExecutorContext executorContext, boolean nodeCheck) {
        try {
            this.checkGatherCondition(nodeCheck);
            this.getRefUnitField(executorContext);
            this.checkMinusField(executorContext);
            this.checkExecutorContext(executorContext, this.condition.getFormSchemeKey());
            List<GatherTableDefine> gatherTables = this.condition.getGatherTables();
            if (gatherTables == null || gatherTables.size() <= 0) {
                if (this.monitor != null) {
                    this.monitor.message("\u672a\u8bbe\u7f6e\u6c47\u603b\u7684\u5b58\u50a8\u8868\uff0c\u65e0\u6cd5\u6c47\u603b\u3002", null);
                    this.monitor.finish();
                }
                return false;
            }
            if (this.monitor != null) {
                this.monitor.message("\u6c47\u603b\u53c2\u6570\u68c0\u67e5\u7ed3\u675f", null);
                this.monitor.onProgress(0.05);
            }
            return true;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            if (this.monitor != null) {
                this.monitor.message(ex.getMessage(), null);
                this.monitor.finish();
            }
            return false;
        }
    }

    private void checkExecutorContext(ExecutorContext executorContext, String formSchemeKey) {
        if (executorContext.getEnv() == null && !StringUtils.isEmpty((String)formSchemeKey)) {
            executorContext.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.viewController, this.queryParam.getRuntimeController(), this.queryParam.getEntityViewRunTimeController(), formSchemeKey));
        }
    }

    private void checkMinusField(ExecutorContext executorContext) throws Exception {
        if (this.condition.getGatherDirection() == GatherDirection.GATHER_TO_GROUP) {
            return;
        }
        this.getMinusField(executorContext, this.condition.getUnitView());
    }

    private void getRefUnitField(ExecutorContext executorContext) throws Exception {
        EntityViewDefine unitView;
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
        TableModelDefine tableModel = entityMetaService.getTableModel((unitView = this.condition.getUnitView()).getEntityId());
        String bizField = tableModel.getBizKeys();
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(bizField.split(";")[0]);
        if (Objects.nonNull(deployInfo)) {
            this.refUnitField = this.runtimeDataSchemeService.getDataField(deployInfo.getDataFieldKey());
        }
    }

    private void executeGatherTableBySql(ExecutorContext executorContext, GatherTableDefine tableDefine, boolean isEnableNrDb, GatherSqlGenerater sqlGenerator) throws Exception {
        boolean listGather;
        FloatTableGatherSetting floatTableGatherSetting = tableDefine.getFloatTableGatherSetting();
        if (!(tableDefine.getTableDefine().getGatherType() != TableGatherType.TABLE_GATHER_NONE || floatTableGatherSetting != null && floatTableGatherSetting.isSingleFloatTableGather())) {
            logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u6c47\u603b\u7c7b\u578b\u4e3a\u4e0d\u6c47\u603b\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            return;
        }
        GatherTableDefine gatherTableDefine = this.getGatherTableDefine(tableDefine, false, this.isMinus);
        if (gatherTableDefine.getUnitField() == null || gatherTableDefine.getGatherFields().size() <= 0) {
            if (gatherTableDefine.getUnitField() == null) {
                logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u83b7\u53d6\u4e0d\u5230\u5355\u4f4d\u6307\u6807\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            } else if (gatherTableDefine.getGatherFields().size() <= 0) {
                logger.info("\u5b58\u50a8\u8868".concat(this.getRealTableName(tableDefine.getTableDefine().getCode())).concat("\u83b7\u53d6\u4e0d\u5230\u53ef\u6c47\u603b\u6307\u6807\uff0c\u65e0\u6cd5\u6c47\u603b\u3002"));
            }
            return;
        }
        List<GatherDataTable> splitGatherTables = this.splitGatherTable(gatherTableDefine);
        DataTableGatherType gatherType = gatherTableDefine.getTableDefine().getDataTableGatherType();
        boolean bl = listGather = gatherType == DataTableGatherType.LIST;
        if (floatTableGatherSetting != null) {
            listGather = floatTableGatherSetting.isListGather();
        }
        for (GatherDataTable gatherDataTable : splitGatherTables) {
            if (gatherTableDefine.isFixed()) {
                if (isEnableNrDb) {
                    sqlGenerator.executeFixedTableGatherForNrDb(gatherDataTable, this.gatherEntityValue, this.condition.getNotGatherEntityValue(), this.maxLevel, this.isMinus, this.condition.getPeriodCode(), this.condition.getSourceDimensions(), this.condition.getUnitView());
                    continue;
                }
                sqlGenerator.executeFixedTableGatherForSql(gatherDataTable, this.gatherTempTable, this.notGatherTempTable, this.maxLevel, this.isMinus, this.condition.getPeriodCode(), this.condition.getSourceDimensions(), this.condition.getTargetDimension(), this.querySqlUpdater, null);
                continue;
            }
            if (isEnableNrDb) {
                sqlGenerator.executeGatherTableByOrderFieldForNrdb(gatherDataTable, this.gatherEntityValue, this.condition.getNotGatherEntityValue(), this.maxLevel, this.isMinus, listGather, this.condition.getPeriodCode(), this.condition.getSourceDimensions(), this.condition.getUnitView(), this.condition.getBizKeyOrderMappings());
            } else {
                sqlGenerator.executeGatherTableByOrderField(gatherDataTable, this.gatherEntityValue, this.condition.getNotGatherEntityValue(), this.gatherTempTable, this.notGatherTempTable, this.maxLevel, this.isMinus, this.condition.getPeriodCode(), listGather, this.condition.getSourceDimensions(), this.condition.getTargetDimension(), this.querySqlUpdater, null, executorContext.getUnitDimension(), this.condition.getBizKeyOrderMappings());
            }
            if (!this.isMinus) continue;
            List<String> pidValues = this.gatherEntityValue.getPidValues();
            List<String> minusMD = pidValues.stream().distinct().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(minusMD)) {
                return;
            }
            sqlGenerator.clearMinusZeroData(gatherDataTable, this.condition.getPeriodCode(), this.condition.getTargetDimension(), minusMD);
        }
    }

    private List<GatherDataTable> splitGatherTable(GatherTableDefine gatherTableDefine) {
        DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
        ArrayList<GatherDataTable> gatherDataTables = new ArrayList<GatherDataTable>();
        ArrayList<DataField> dimFields = new ArrayList<DataField>();
        dimFields.add(gatherTableDefine.getUnitField());
        dimFields.add(gatherTableDefine.getPeriodField());
        if (gatherTableDefine.getOrderField() != null) {
            dimFields.add(gatherTableDefine.getOrderField());
        }
        if (gatherTableDefine.getBizOrderField() != null) {
            dimFields.add(gatherTableDefine.getBizOrderField());
        }
        dimFields.addAll(gatherTableDefine.getClassifyFields());
        dimFields.addAll(gatherTableDefine.getUnClassifyFields());
        List<DataField> fields = gatherTableDefine.getGatherFields();
        List dimRes = this.subDatabaseTableNamesProvider.getSubDatabaseTableColumns(this.condition.getDataSchemeKey(), dimFields.stream().map(Basic::getKey).collect(Collectors.toList()));
        Map<String, List<ColumnModelDefine>> dimGroup = dimRes.stream().collect(Collectors.groupingBy(ColumnModelDefine::getTableID));
        Map<String, DataField> dataFieldMap = fields.stream().collect(Collectors.toMap(Basic::getKey, a -> a));
        Map fieldKey2SubDatabaseColumns = this.subDatabaseTableNamesProvider.getSubDatabaseFieldKey2Columns(this.condition.getDataSchemeKey(), new ArrayList<String>(dataFieldMap.keySet()));
        ArrayList allGatherColumns = new ArrayList();
        HashMap columnModel2DataFiledKey = new HashMap();
        for (Map.Entry entry : fieldKey2SubDatabaseColumns.entrySet()) {
            allGatherColumns.addAll((Collection)entry.getValue());
            for (ColumnModelDefine columnModelDefine : (List)entry.getValue()) {
                columnModel2DataFiledKey.put(columnModelDefine, entry.getKey());
            }
        }
        Map<String, List<ColumnModelDefine>> groupMap = allGatherColumns.stream().collect(Collectors.groupingBy(ColumnModelDefine::getTableID));
        for (Map.Entry<String, List<ColumnModelDefine>> entry : groupMap.entrySet()) {
            GatherDataTable gatherDataTable = new GatherDataTable();
            HashMap<ColumnModelDefine, DataField> gatherColumns2DataField = new HashMap<ColumnModelDefine, DataField>();
            gatherDataTable.setGatherColumns2DataField(gatherColumns2DataField);
            TableModelDefine table = dataModelService.getTableModelDefineById(entry.getKey());
            List<ColumnModelDefine> dimColumns = dimGroup.get(entry.getKey());
            List<ColumnModelDefine> allColumn = entry.getValue();
            for (ColumnModelDefine columnModelDefine : allColumn) {
                String dataFieldKey = (String)columnModel2DataFiledKey.get(columnModelDefine);
                if (!StringUtils.isNotEmpty((String)dataFieldKey)) continue;
                gatherColumns2DataField.put(columnModelDefine, dataFieldMap.get(dataFieldKey));
            }
            gatherDataTable.setAllDimsColumns(dimColumns);
            gatherDataTable.setAllColumns(allColumn);
            gatherDataTable.setTableModelDefine(table);
            gatherDataTable.setGatherTableDefine(gatherTableDefine);
            gatherDataTables.add(gatherDataTable);
        }
        return gatherDataTables;
    }

    private GatherTableDefine getGatherTableDefine(GatherTableDefine gatherTableDefine, boolean isNodeCheck, boolean isMinus) throws Exception {
        DataFieldType type;
        DataFieldGatherType gatherType;
        boolean isList;
        DataTable tableDefine = gatherTableDefine.getTableDefine();
        FloatTableGatherSetting floatTableGatherSetting = gatherTableDefine.getFloatTableGatherSetting();
        List allFields = this.runtimeDataSchemeService.getDataFieldByTable(tableDefine.getKey());
        ArrayList<DataField> gatherFields = new ArrayList<DataField>();
        ArrayList<DataField> classifyFields = new ArrayList<DataField>();
        ArrayList<DataField> unClassifyFields = new ArrayList<DataField>();
        DataField periodField = null;
        DataField orderField = null;
        DataField unitField = null;
        DataField bizOrderField = null;
        List<String> dimIds = this.getBizFields(tableDefine);
        HashSet<String> unClassifyKeys = this.getUnClassifyFields(tableDefine);
        if (floatTableGatherSetting != null && CollectionUtils.isNotEmpty(floatTableGatherSetting.getUnClassifyFields())) {
            logger.debug("\u6d6e\u52a8\u8868\u6c47\u603b\u8bbe\u7f6e\u6c47\u603b\u5b57\u6bb5\u53c2\u6570");
            unClassifyKeys.addAll(floatTableGatherSetting.getUnClassifyFields());
        }
        HashSet<String> bizKeys = new HashSet<String>(dimIds);
        for (String dimId : dimIds) {
            DataField fieldDefine = this.runtimeDataSchemeService.getDataField(dimId);
            if (!fieldDefine.getCode().equals("MDCODE")) continue;
            unitField = fieldDefine;
            break;
        }
        for (DataField fieldDefine : allFields) {
            String fieldKey = fieldDefine.getKey();
            FieldValueType valueType = fieldDefine.getValueType();
            if (valueType == FieldValueType.FIELD_VALUE_INPUT_ORDER) {
                orderField = fieldDefine;
                bizKeys.add(fieldKey);
                continue;
            }
            if (valueType == FieldValueType.FIELD_VALUE_BIZKEY_ORDER) {
                bizOrderField = fieldDefine;
                bizKeys.add(fieldKey);
                continue;
            }
            if (dimIds.contains(fieldKey)) {
                String code = fieldDefine.getCode();
                if (code.equals("DATATIME")) {
                    periodField = fieldDefine;
                    continue;
                }
                if (fieldKey.equals(unitField.getKey())) continue;
                classifyFields.add(fieldDefine);
            }
            if (!unClassifyKeys.contains(fieldKey) || gatherTableDefine.isFixed() || dimIds.contains(fieldKey)) continue;
            unClassifyFields.add(fieldDefine);
        }
        boolean bl = isList = tableDefine.getDataTableGatherType() == DataTableGatherType.LIST;
        if (floatTableGatherSetting != null) {
            isList = floatTableGatherSetting.isListGather();
        }
        if (isNodeCheck) {
            for (DataField gatherField : gatherTableDefine.getGatherFields()) {
                gatherType = gatherField.getDataFieldGatherType();
                type = gatherField.getDataFieldType();
                if (!type.equals((Object)DataFieldType.INTEGER) && !type.equals((Object)DataFieldType.BIGDECIMAL) || gatherType == DataFieldGatherType.NONE && !isList || bizKeys.contains(gatherField.getKey()) || unClassifyKeys.contains(gatherField.getKey()) || gatherType == DataFieldGatherType.DISTINCT_COUNT) continue;
                gatherFields.add(gatherField);
            }
        } else {
            for (DataField gatherField : gatherTableDefine.getGatherFields()) {
                DataTableType dataTableType;
                gatherType = gatherField.getDataFieldGatherType();
                type = gatherField.getDataFieldType();
                if (floatTableGatherSetting != null && floatTableGatherSetting.isAllNumFieldsSum() ? DataFieldType.PICTURE == type || DataFieldType.FILE == type || bizKeys.contains(gatherField.getKey()) || unClassifyKeys.contains(gatherField.getKey()) || gatherType == DataFieldGatherType.DISTINCT_COUNT : ((dataTableType = tableDefine.getDataTableType()) == DataTableType.TABLE || dataTableType == DataTableType.MD_INFO ? gatherType == DataFieldGatherType.NONE && DataFieldType.PICTURE != type && DataFieldType.FILE != type || bizKeys.contains(gatherField.getKey()) || unClassifyKeys.contains(gatherField.getKey()) || gatherType == DataFieldGatherType.DISTINCT_COUNT : (isList && !isMinus ? bizKeys.contains(gatherField.getKey()) || unClassifyKeys.contains(gatherField.getKey()) || gatherType == DataFieldGatherType.DISTINCT_COUNT : gatherType == DataFieldGatherType.NONE && !isList || DataFieldType.PICTURE == type || DataFieldType.FILE == type || bizKeys.contains(gatherField.getKey()) || unClassifyKeys.contains(gatherField.getKey()) || gatherType == DataFieldGatherType.DISTINCT_COUNT))) continue;
                gatherFields.add(gatherField);
            }
        }
        gatherTableDefine.setUnitField(unitField);
        gatherTableDefine.setPeriodField(periodField);
        gatherTableDefine.setOrderField(orderField);
        gatherTableDefine.setBizOrderField(bizOrderField);
        gatherTableDefine.setClassifyFields(classifyFields);
        gatherTableDefine.setGatherFields(gatherFields);
        gatherTableDefine.setUnClassifyFields(unClassifyFields);
        return gatherTableDefine;
    }

    private List<String> getBizFields(DataTable tableDefine) {
        String[] bizKey = tableDefine.getBizKeys();
        if (bizKey == null || bizKey.length == 0) {
            return new ArrayList<String>();
        }
        return Arrays.asList(bizKey);
    }

    private HashSet<String> getUnClassifyFields(DataTable tableDefine) {
        HashSet<String> gatherKeys = new HashSet<String>();
        String[] gatherFieldKey = tableDefine.getGatherFieldKeys();
        if (gatherFieldKey == null || gatherFieldKey.length == 0) {
            return gatherKeys;
        }
        gatherKeys.addAll(Arrays.asList(gatherFieldKey));
        return gatherKeys;
    }

    public String getExecutionId() {
        if (StringUtils.isEmpty((String)this.executionId)) {
            this.executionId = UUID.randomUUID().toString().toUpperCase();
        }
        return this.executionId;
    }

    private ITempTable fillGatherTempTable() throws Exception {
        ITempTable tempTable = GatherAssistantTable.getGatherTempTable();
        if (tempTable == null) {
            throw new DataGatherExecption("\u6ca1\u6709\u83b7\u53d6\u5230\u6c47\u603b\u4e34\u65f6\u8868\uff01");
        }
        this.gatherTempTable = tempTable.getTableName();
        if (StringUtils.isEmpty((String)this.executionId)) {
            this.executionId = UUID.randomUUID().toString().toUpperCase();
        }
        tempTable.insertRecords(GatherAssistantTable.buildTempValues(this.gatherEntityValue, this.executionId));
        if (this.monitor != null) {
            this.monitor.message("\u6c47\u603b\u4e34\u65f6\u8868\u521d\u59cb\u5316\u7ed3\u675f", null);
            this.monitor.onProgress(0.1);
        }
        return tempTable;
    }

    private ITempTable fillNotGatherTempTable() throws Exception {
        ITempTable notGatherTable = null;
        this.notGatherValues = this.condition.getNotGatherEntityValue();
        if (this.notGatherValues == null) {
            logger.info("\u65e0\u8fc7\u6ee4\u5355\u4f4d\u6570\u636e");
            this.notGatherTempTable = null;
            return notGatherTable;
        }
        this.notGatherValues.parse();
        if (StringUtils.isEmpty((String)this.executionId)) {
            this.executionId = UUID.randomUUID().toString().toUpperCase();
        }
        if (CollectionUtils.isNotEmpty(this.notGatherValues.getIdValues())) {
            notGatherTable = GatherAssistantTable.getNotGatherTempTable();
            if (notGatherTable == null) {
                throw new DataGatherExecption("\u6ca1\u6709\u83b7\u53d6\u5230\u6c47\u603b\u4e34\u65f6\u8868\uff01");
            }
            this.notGatherTempTable = notGatherTable.getTableName();
            List<Object[]> values = GatherAssistantTable.buildNotTempValues(this.notGatherValues, this.executionId);
            notGatherTable.insertRecords(values);
        } else {
            this.notGatherTempTable = null;
        }
        if (this.monitor != null) {
            this.monitor.message("\u6c47\u603b\u5355\u4f4d\u8fc7\u6ee4\u7ed3\u675f", null);
            this.monitor.onProgress(0.15);
        }
        return notGatherTable;
    }

    private boolean checkNeedGather(NotGatherEntityValue notGatherValues) {
        notGatherValues.parse();
        HashSet<String> notGatherPid = new HashSet<String>(notGatherValues.getIdValues());
        HashSet<String> gatherPid = new HashSet<String>(this.gatherEntityValue.getPidValues());
        return notGatherPid.containsAll(gatherPid);
    }

    private void initSingleSelectGatherDims() {
        GatherTempTableHandler gatherTempTableHandler = (GatherTempTableHandler)BeanUtil.getBean(GatherTempTableHandler.class);
        Map<String, String> dimColsMap = gatherTempTableHandler.getSingleSelectDimCols(this.condition.getDataSchemeKey(), this.condition.getTaskKey());
        this.gatherSingleDims.putAll(dimColsMap);
    }

    private void initGatherEntitys(ExecutorContext executorContext) throws Exception {
        IEntityRow targetRow;
        if (this.entityTable == null) {
            this.entityTable = this.getEntityTable(executorContext, this.condition.getUnitView(), this.targetKey, this.condition.getPeriodCode());
        }
        if ((targetRow = this.entityTable.findByEntityKey(this.targetKey)) == null) {
            throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
        }
        List childRows = this.entityTable.getChildRows(this.targetKey);
        if (childRows.size() <= 0) {
            this.isLeaf = true;
        }
        this.unitCache.put(this.targetKey, targetRow.getTitle());
        this.gatherEntityValue = new GatherEntityValue();
        this.isMinus = this.condition.getGatherDirection() == GatherDirection.GATHER_TO_MINUS;
        this.maxLevel = this.fillGatherEntityValues(this.gatherEntityValue, targetRow, this.entityTable, 1, this.isMinus, this.condition.isRecursive(), this.unitCache);
        if (this.monitor != null) {
            this.monitor.message("\u6c47\u603b\u5355\u4f4d\u83b7\u53d6\u7ed3\u675f", null);
            this.monitor.onProgress(0.1);
        }
    }

    private void initBatchGatherEntitys(ExecutorContext executorContext) throws Exception {
        IEntityTable entityTable = this.getEntityTable(executorContext, this.condition.getUnitView(), this.condition.getPeriodCode());
        this.gatherEntityValue = new GatherEntityValue();
        for (String key : this.targetKeys) {
            IEntityRow targetRow = entityTable.findByEntityKey(key);
            if (targetRow == null) {
                throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
            }
            List childRows = entityTable.getChildRows(key);
            if (childRows.isEmpty()) {
                this.isLeaf = true;
            }
            this.unitCache.put(key, targetRow.getTitle());
            this.isMinus = this.condition.getGatherDirection() == GatherDirection.GATHER_TO_MINUS;
            this.maxLevel = this.fillGatherEntityValues(this.gatherEntityValue, targetRow, entityTable, 1, this.isMinus, this.condition.isRecursive(), this.unitCache);
        }
        if (this.monitor != null) {
            this.monitor.message("\u6c47\u603b\u5355\u4f4d\u83b7\u53d6\u7ed3\u675f", null);
            this.monitor.onProgress(0.1);
        }
    }

    private IEntityTable getEntityTable(ExecutorContext executorContext, EntityViewDefine unitView, String periodCode) throws Exception {
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(unitView);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(this.condition.getFormSchemeKey());
        executorContext.setPeriodView(formScheme.getDateTime());
        return entityQuery.executeReader((IContext)executorContext);
    }

    private IEntityTable getEntityTable(ExecutorContext executorContext, EntityViewDefine unitView, String targetKey, String periodCode) throws Exception {
        IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(unitView);
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (!StringUtils.isEmpty((String)periodCode)) {
            masterKeys.setValue("DATATIME", (Object)periodCode);
        }
        entityQuery.setMasterKeys(masterKeys);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        executorContext.setVarDimensionValueSet(masterKeys);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
        FormSchemeDefine formScheme = runTimeViewController.getFormScheme(this.condition.getFormSchemeKey());
        executorContext.setPeriodView(formScheme.getDateTime());
        TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
        ArrayList<String> target = new ArrayList<String>();
        target.add(targetKey);
        treeRangeQuery.setParentKey(target);
        return entityQuery.executeRangeBuild((IContext)executorContext, (RangeQuery)treeRangeQuery);
    }

    private int fillGatherEntityValues(GatherEntityValue gatherEntityValue, IEntityRow parentRow, IEntityTable entityTable, int level, boolean isMinus, boolean isRecursive, Map<String, String> unitCache) {
        Set<String> filterKeys;
        int resultLevel = level;
        String parentKey = parentRow.getEntityKeyData();
        List childRows = entityTable.getChildRows(parentKey);
        List<String> childKeys = childRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        Set<String> set = filterKeys = this.provider == null ? null : this.provider.getFilterChildren(childKeys);
        if (!isMinus) {
            for (IEntityRow iEntityRow : childRows) {
                String childKey = iEntityRow.getEntityKeyData();
                unitCache.put(childKey, iEntityRow.getTitle());
                if (filterKeys == null || filterKeys.contains(childKey)) {
                    this.addGatherEntityValue(gatherEntityValue, parentRow, level, iEntityRow, childKey, parentKey);
                }
                if (!isRecursive || entityTable.getChildRows(childKey).isEmpty()) continue;
                if (this.condition.isBZHZBGather()) {
                    int tempLevel;
                    IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
                    EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(this.condition.getUnitView().getEntityId());
                    if (!entityFormGather.getUnitNature(iEntityRow).equals((Object)UnitNature.BZHZB) || (tempLevel = this.fillGatherEntityValues(gatherEntityValue, iEntityRow, entityTable, level + 1, false, true, unitCache)) <= resultLevel) continue;
                    resultLevel = tempLevel;
                    continue;
                }
                int tempLevel = this.fillGatherEntityValues(gatherEntityValue, iEntityRow, entityTable, level + 1, false, true, unitCache);
                if (tempLevel <= resultLevel) continue;
                resultLevel = tempLevel;
            }
        } else {
            IEntityRow minusRow = null;
            IFormTypeApplyService formTypeApplyService = (IFormTypeApplyService)BeanUtil.getBean(IFormTypeApplyService.class);
            EntityUnitNatureGetter entityFormGather = formTypeApplyService.getEntityFormTypeGetter(this.condition.getUnitView().getEntityId());
            for (IEntityRow childRow : childRows) {
                if (!this.isMinus(entityFormGather, childRow)) continue;
                minusRow = childRow;
                break;
            }
            if (minusRow == null) {
                for (IEntityRow iEntityRow : childRows) {
                    int tempLevel;
                    String childKey = iEntityRow.getEntityKeyData();
                    unitCache.put(childKey, iEntityRow.getTitle());
                    if (!isRecursive || entityTable.getChildRows(childKey).isEmpty() || (tempLevel = this.fillGatherEntityValues(gatherEntityValue, iEntityRow, entityTable, level + 1, true, true, unitCache)) <= resultLevel) continue;
                    resultLevel = tempLevel;
                }
            } else {
                String minusKey = minusRow.getEntityKeyData();
                this.addMinusEntityValue(gatherEntityValue, level, parentKey, minusKey, minusRow, false);
                for (IEntityRow iEntityRow : childRows) {
                    int tempLevel;
                    String childKey = iEntityRow.getEntityKeyData();
                    unitCache.put(childKey, iEntityRow.getTitle());
                    if (StringUtils.equals((String)minusKey, (String)childKey)) continue;
                    if (filterKeys == null || filterKeys.contains(childKey)) {
                        this.addMinusEntityValue(gatherEntityValue, level, childKey, minusKey, minusRow, true);
                    }
                    if (!isRecursive || entityTable.getChildRows(childKey).isEmpty() || (tempLevel = this.fillGatherEntityValues(gatherEntityValue, iEntityRow, entityTable, level + 1, true, true, unitCache)) <= resultLevel) continue;
                    resultLevel = tempLevel;
                }
            }
        }
        return resultLevel;
    }

    private void addGatherEntityValue(GatherEntityValue gatherEntityValue, IEntityRow parentRow, int level, IEntityRow iEntityRow, String childKey, String parentKey) {
        gatherEntityValue.getIdValues().add(childKey);
        gatherEntityValue.getPidValues().add(parentKey);
        gatherEntityValue.getLevelValues().add(level);
        Object unitOrder = iEntityRow.getEntityOrder();
        BigDecimal bigNum = new BigDecimal(unitOrder.toString());
        bigNum = bigNum.multiply(this.formatDecimal).setScale(0, RoundingMode.HALF_UP);
        Long order = Long.parseLong(bigNum.toString());
        gatherEntityValue.getUnitOrders().add(order);
        this.setDimAttributeValues(iEntityRow, parentRow);
    }

    private void addMinusEntityValue(GatherEntityValue gatherEntityValue, int level, String id, String pid, IEntityRow entityRow, boolean isChild) {
        gatherEntityValue.getIdValues().add(id);
        gatherEntityValue.getPidValues().add(pid);
        gatherEntityValue.getMidValues().add(pid);
        if (isChild) {
            gatherEntityValue.getIsMinus().add(-1.0);
        } else {
            gatherEntityValue.getIsMinus().add(1.0);
        }
        gatherEntityValue.getLevelValues().add(level);
        Object unitOrder = entityRow.getEntityOrder();
        BigDecimal bigNum = new BigDecimal(unitOrder.toString());
        bigNum = bigNum.multiply(this.formatDecimal).setScale(0, RoundingMode.HALF_UP);
        Long order = Long.parseLong(bigNum.toString());
        gatherEntityValue.getUnitOrders().add(order);
        this.setDimAttributeValues(entityRow);
    }

    private void setDimAttributeValues(IEntityRow parentRow) {
        if (org.springframework.util.CollectionUtils.isEmpty(this.gatherSingleDims)) {
            return;
        }
        for (Map.Entry<String, String> entry : this.gatherSingleDims.entrySet()) {
            String gatherDim = entry.getKey();
            String attribute = entry.getValue();
            String dimValue = parentRow.getAsString(attribute);
            if (!this.gatherEntityValue.getDimValues().containsKey(gatherDim)) {
                this.gatherEntityValue.getDimValues().put(gatherDim, new ArrayList());
            }
            this.gatherEntityValue.getDimValues().get(gatherDim).add(dimValue);
        }
    }

    private void setDimAttributeValues(IEntityRow childRow, IEntityRow parentRow) {
        if (org.springframework.util.CollectionUtils.isEmpty(this.gatherSingleDims)) {
            return;
        }
        for (Map.Entry<String, String> entry : this.gatherSingleDims.entrySet()) {
            String gatherDim = entry.getKey();
            String attribute = entry.getValue();
            String dimPValue = parentRow.getAsString(attribute);
            String dimCValue = childRow.getAsString(attribute);
            if (!this.gatherEntityValue.getDimValues().containsKey(gatherDim)) {
                this.gatherEntityValue.getDimValues().put(gatherDim, new ArrayList());
            }
            this.gatherEntityValue.getDimValues().get(gatherDim).add(dimPValue);
            if (!this.gatherEntityValue.getDimValuesBySelf().containsKey(gatherDim)) {
                this.gatherEntityValue.getDimValuesBySelf().put(gatherDim, new ArrayList());
            }
            this.gatherEntityValue.getDimValuesBySelf().get(gatherDim).add(dimCValue);
        }
    }

    private boolean isMinus(EntityUnitNatureGetter gather, IEntityRow row) {
        UnitNature unitNature = gather.getUnitNature(row);
        if (null != unitNature) {
            return unitNature.equals((Object)UnitNature.JTCEB);
        }
        return false;
    }

    private void checkGatherCondition(boolean nodeCheck) throws GatherException {
        if (this.condition == null) {
            throw new GatherException("\u6c47\u603b\u8bbe\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty((String)this.targetKey) && CollectionUtils.isEmpty(this.targetKeys)) {
            throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (this.condition.getUnitView() == null) {
            throw new GatherException("\u6c47\u603b\u5355\u4f4d\u5b9e\u4f53\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (nodeCheck) {
            return;
        }
    }

    @Override
    public void setQueryParam(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public void setQuerySqlUpdater(IQuerySqlUpdater querySqlUpdater) {
        this.querySqlUpdater = querySqlUpdater;
    }

    @Override
    public void setGatherEventHandler(GatherEventHandler gatherEventHandler) {
        this.gatherEventHandler = gatherEventHandler;
    }

    public void setViewController(IRunTimeViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public GatherEntityMap getGatherEntityMap(String targetKey, boolean isMinus, boolean isRecursive, ExecutorContext executorContext, String formSchemeKey, EntityViewDefine unitView, String peridCode) throws Exception {
        IEntityRow targetRow;
        if (isMinus) {
            this.getMinusField(executorContext, unitView);
        }
        GatherEntityMap gatherEntityMap = new GatherEntityMap();
        this.checkExecutorContext(executorContext, formSchemeKey);
        if (this.entityTable == null) {
            this.entityTable = this.getEntityTable(executorContext, unitView, targetKey, peridCode);
        }
        if ((targetRow = this.entityTable.findByEntityKey(targetKey)) == null) {
            throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
        }
        List childRows = this.entityTable.getChildRows(targetKey);
        if (childRows.size() <= 0) {
            return gatherEntityMap;
        }
        GatherEntityValue gatherEntityValue = new GatherEntityValue();
        this.condition = new GatherCondition();
        this.condition.setUnitView(unitView);
        HashMap<String, String> unitCache = new HashMap<String, String>();
        int maxLevel = this.fillGatherEntityValues(gatherEntityValue, targetRow, this.entityTable, 1, isMinus, isRecursive, unitCache);
        List<String> parentValues = gatherEntityValue.getPidValues().stream().distinct().collect(Collectors.toList());
        gatherEntityMap.setMaxLevel(maxLevel);
        gatherEntityMap.setGatherEntitys(parentValues);
        Map<String, ShowItem> unitMap = this.getUnitMap(parentValues, this.entityTable);
        gatherEntityMap.setUnitCache(unitMap);
        return gatherEntityMap;
    }

    @Override
    public GatherEntityMap getGatherEntityMap(String targetKey, boolean isMinus, boolean isRecursive, ExecutorContext executorContext, EntityViewDefine unitView, IEntityTable entityTable) throws Exception {
        IEntityRow targetRow;
        if (isMinus) {
            this.getMinusField(executorContext, unitView);
        }
        GatherEntityMap gatherEntityMap = new GatherEntityMap();
        if (this.entityTable == null) {
            this.entityTable = entityTable;
        }
        if ((targetRow = entityTable.findByEntityKey(targetKey)) == null) {
            throw new GatherException("\u6c47\u603b\u76ee\u6807\u5355\u4f4d\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u6c47\u603b\u3002");
        }
        List childRows = entityTable.getChildRows(targetKey);
        if (childRows.size() <= 0) {
            return gatherEntityMap;
        }
        GatherEntityValue gatherEntityValue = new GatherEntityValue();
        this.condition = new GatherCondition();
        this.condition.setUnitView(unitView);
        HashMap<String, String> unitCache = new HashMap<String, String>();
        int maxLevel = this.fillGatherEntityValues(gatherEntityValue, targetRow, entityTable, 1, isMinus, isRecursive, unitCache);
        List<String> parentValues = gatherEntityValue.getPidValues().stream().distinct().collect(Collectors.toList());
        gatherEntityMap.setMaxLevel(maxLevel);
        gatherEntityMap.setGatherEntitys(parentValues);
        Map<String, ShowItem> unitMap = this.getUnitMap(parentValues, entityTable);
        gatherEntityMap.setUnitCache(unitMap);
        return gatherEntityMap;
    }

    private Map<String, ShowItem> getUnitMap(List<String> parentValues, IEntityTable entityTable) {
        HashMap<String, ShowItem> unitMap = new HashMap<String, ShowItem>();
        for (String parentValue : parentValues) {
            IEntityRow parentRow = entityTable.findByEntityKey(parentValue);
            if (parentRow == null) continue;
            ShowItem showItem = this.getShowItem(parentRow);
            unitMap.put(parentValue, showItem);
        }
        return unitMap;
    }

    private void getMinusField(ExecutorContext executorContext, EntityViewDefine unitView) throws Exception {
        IEntityModel entityModel;
        try {
            entityModel = this.entityMetaService.getEntityModel(unitView.getEntityId());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002", (Throwable)e);
        }
        IEntityAttribute bblxField = entityModel.getBblxField();
        if (bblxField == null) {
            throw new GatherException("\u672a\u627e\u5230\u5355\u4f4d\u5b9e\u4f53\u7684\u62a5\u8868\u7c7b\u578b\u6307\u6807\uff0c\u65e0\u6cd5\u8fdb\u884c\u5dee\u989d\u6c47\u603b\u3002");
        }
        this.minusField = bblxField;
    }

    private String getRealTableName(String tableName) {
        if (null == this.subDatabaseTableNamesProvider || StringUtils.isEmpty((String)this.condition.getTaskKey())) {
            return tableName;
        }
        return this.subDatabaseTableNamesProvider.getSubDatabaseTableName(this.condition.getTaskKey(), tableName);
    }

    private ShowItem getShowItem(IEntityRow targetRow) {
        ShowItem showItem = new ShowItem(targetRow.getCode(), targetRow.getTitle());
        showItem.setRowCaption(targetRow.getTitle());
        return showItem;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setSubDatabaseTableNamesProvider(SubDatabaseTableNamesProvider subDatabaseTableNamesProvider) {
        this.subDatabaseTableNamesProvider = subDatabaseTableNamesProvider;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setMemTableLoader(IMemTableLoader memTableLoader) {
        this.memTableLoader = memTableLoader;
    }

    public void setDimensionProvider(IDimensionProvider dimensionProvider) {
        this.dimensionProvider = dimensionProvider;
    }

    public void setFileCalculateService(FileCalculateService fileCalculateService) {
        this.fileCalculateService = fileCalculateService;
    }

    @Override
    public void setUnitReportLog(UnitReportLog unitReportLog) {
        this.unitReportLog = unitReportLog;
    }
}

