/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.GCAdjTypeEnum
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.dto.Pagination
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition
 *  com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum
 *  com.jiuqi.gcreport.journalsingle.vo.JournalAdjustedFiguresVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO
 *  com.jiuqi.gcreport.nr.impl.service.JournalFunctionService
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.UniversalFieldDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl
 *  com.jiuqi.nr.efdc.service.IEFDCConfigService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.journalsingle.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.GCAdjTypeEnum;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.dto.Pagination;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.journalsingle.condition.JournalDetailCondition;
import com.jiuqi.gcreport.journalsingle.condition.JournalSinglePostCondition;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum;
import com.jiuqi.gcreport.journalsingle.service.IJournalSinglePostService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSchemeService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import com.jiuqi.gcreport.journalsingle.utils.GcNpUtil;
import com.jiuqi.gcreport.journalsingle.vo.JournalAdjustedFiguresVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSingleVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalWorkPaperDataVO;
import com.jiuqi.gcreport.nr.impl.service.JournalFunctionService;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.UniversalFieldDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.internal.pojo.QueryObjectImpl;
import com.jiuqi.nr.efdc.service.IEFDCConfigService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalSinglePostServiceImpl
implements IJournalSinglePostService,
JournalFunctionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IJournalSingleSubjectService singleSubjectService;
    @Autowired
    private IJournalSingleSchemeService singleSchemeService;
    @Autowired
    private IJournalSingleService journalSingleService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private RunTimeAuthViewController iRunTimeViewController;
    @Autowired
    private IBatchCalculateService batchOperationService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEFDCConfigService efdcConfigServiceImpl;

    @Override
    public String postData(JournalSinglePostCondition postCondition) {
        this.checkActionValid(postCondition);
        List<JournalSubjectEO> allJournalSubjectEOS = this.listJournalPostSubjects(postCondition);
        if (allJournalSubjectEOS.isEmpty()) {
            return GcI18nUtil.getMessage((String)"gc.journal.no.adjust.item");
        }
        Map<String, BigDecimal> beforeFullZbCode2Value = this.beforeFullZbCode2Value(postCondition, allJournalSubjectEOS);
        Map<String, BigDecimal> afterFullZbCode2AdjustValue = this.afterFullZbCode2AdjustValue(postCondition, allJournalSubjectEOS);
        Map<String, String> afterFullZbCode2beforeFullZbCode = this.afterFullZbCode2beforeFullZbCode(allJournalSubjectEOS);
        String result = this.processCurrUnitFormData(postCondition, beforeFullZbCode2Value, afterFullZbCode2AdjustValue, afterFullZbCode2beforeFullZbCode);
        if (!this.batchCalculateForm(postCondition)) {
            this.logger.error("\u5355\u4f4d: [{}] ,\u8fc7\u8d26\u81ea\u52a8\u8fd0\u7b97\u5931\u8d25\u3002", (Object)postCondition.getInputUnitId());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.journal.unit.post.error", (Object[])new Object[]{postCondition.getInputUnitId()}));
        }
        return result;
    }

    private boolean batchCalculateForm(JournalSinglePostCondition postCondition) {
        FormulaSchemeDefine soluctionByDimensions;
        HashMap<String, String> dimensionParam = postCondition.getDimensionParam();
        if (dimensionParam == null || dimensionParam.size() == 0) {
            dimensionParam = new HashMap<String, String>();
            dimensionParam.put("MD_GCORGTYPE", postCondition.getOrgTypeId());
            dimensionParam.put("MD_CURRENCY", postCondition.getCurrencyId());
            dimensionParam.put("DATATIME", postCondition.getPeriodStr());
            dimensionParam.put("ADJUST", postCondition.getSelectAdjustCode());
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(postCondition.getSchemeId());
        QueryObjectImpl queryObjectImpl = new QueryObjectImpl(postCondition.getTaskId(), postCondition.getSchemeId(), postCondition.getInputUnitId());
        List dimEntityList = this.jtableParamService.getDimEntityList(postCondition.getSchemeId());
        HashMap<String, String> dimMap = new HashMap<String, String>();
        for (EntityViewData entityInfo : dimEntityList) {
            String dimensionValue = (String)dimensionParam.get(entityInfo.getDimensionName());
            dimMap.put(entityInfo.getTableName(), dimensionValue);
        }
        try {
            soluctionByDimensions = this.efdcConfigServiceImpl.getSoluctionByDimensions(queryObjectImpl, dimMap, dwEntity.getKey());
        }
        catch (Exception e) {
            this.logger.error("\u5355\u6237\u8c03\u6574\u83b7\u53d6\u8fc7\u8d26\u540e\u8fd0\u7b97\u65b9\u6848\u5931\u8d25\uff1a" + e.getMessage(), e);
            return true;
        }
        if (soluctionByDimensions == null) {
            return true;
        }
        String postingSchemeId = soluctionByDimensions.getKey();
        if (StringUtils.isEmpty((String)postingSchemeId)) {
            return true;
        }
        BatchCalculateInfo batchCalculateInfo = this.buidBatchCalculateInfo(postCondition, postingSchemeId);
        try {
            this.batchOperationService.batchCalculateForm(batchCalculateInfo);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    private BatchCalculateInfo buidBatchCalculateInfo(JournalSinglePostCondition condition, String formulaSchemeKeys) {
        Map<String, DimensionValue> dimensionSetMap = GcNpUtil.getInstance().buildDimensionMap(condition.getTaskId(), condition.getCurrencyId(), condition.getPeriodStr(), condition.getOrgTypeId(), condition.getInputUnitId(), condition.getSelectAdjustCode());
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setDimensionSet(dimensionSetMap);
        batchCalculateInfo.setTaskKey(condition.getTaskId());
        batchCalculateInfo.setFormSchemeKey(condition.getSchemeId());
        batchCalculateInfo.setFormulaSchemeKey(formulaSchemeKeys);
        return batchCalculateInfo;
    }

    public boolean callPostFunc(QueryContext queryContext) {
        JournalSinglePostCondition condition = this.getJournalSinglePostCondition(queryContext);
        long beginTime = System.currentTimeMillis();
        String msg = this.postData(condition);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        return StringUtils.isNull((String)msg);
    }

    public boolean callPostFunc(QueryContext queryContext, String afterZbTableName) {
        JournalSinglePostCondition condition = this.getJournalSinglePostCondition(queryContext);
        this.postData(condition, afterZbTableName);
        return true;
    }

    private void postData(JournalSinglePostCondition condition, String afterZbTableName) {
        this.checkActionValid(condition);
        List<JournalSubjectEO> allJournalSubjectEOS = this.listJournalPostSubjects(condition);
        if (allJournalSubjectEOS.isEmpty()) {
            return;
        }
        Map<String, BigDecimal> beforeFullZbCode2Value = this.beforeFullZbCode2Value(condition, allJournalSubjectEOS);
        Map<String, BigDecimal> afterFullZbCode2AdjustValue = this.afterFullZbCode2AdjustValue(condition, allJournalSubjectEOS);
        Map<String, String> afterFullZbCode2beforeFullZbCode = this.afterFullZbCode2beforeFullZbCode(allJournalSubjectEOS);
        try {
            DataTable tableDefine = this.runtimeDataSchemeService.getDataTableByCode(afterZbTableName);
            this.post(tableDefine, condition, beforeFullZbCode2Value, afterFullZbCode2AdjustValue, afterFullZbCode2beforeFullZbCode);
            this.journalSingleService.batchUpdatePostFlag(condition.getTaskId(), condition.getSchemeId(), condition.getPeriodStr(), condition.getInputUnitId(), condition.getOrgTypeId(), afterZbTableName);
        }
        catch (Exception e) {
            this.logger.error("\u4e2d\u65ad\u5b58\u50a8\u8868{}\u6570\u636e\u5355\u8868\u8fc7\u8d26\uff0c\u8be6\u60c5:{}", (Object)afterZbTableName, (Object)e.getMessage());
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.journal.table.post.error", (Object[])new Object[]{afterZbTableName}) + e.getMessage(), (Throwable)e);
        }
    }

    private String processCurrUnitFormData(JournalSinglePostCondition condition, Map<String, BigDecimal> beforeFullZbCode2Value, Map<String, BigDecimal> afterFullZbCode2AdjustValue, Map<String, String> afterFullZbCode2beforeFullZbCode) {
        StringBuffer retInfo = new StringBuffer(128);
        Set<DataTable> tableDefines = this.getTaskAllTableDefines(condition, retInfo);
        if (CollectionUtils.isEmpty(tableDefines)) {
            return retInfo.toString();
        }
        tableDefines.stream().filter(Objects::nonNull).forEach(tableDefine -> {
            try {
                this.post((DataTable)tableDefine, condition, beforeFullZbCode2Value, afterFullZbCode2AdjustValue, afterFullZbCode2beforeFullZbCode);
            }
            catch (Exception e) {
                e.printStackTrace();
                this.logger.error("\u4e2d\u65ad\u5b58\u50a8\u8868{}\u6570\u636e\u8fc7\u8d26\uff0c\u8be6\u60c5:{}", (Object)tableDefine.getCode(), (Object)e.getMessage());
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.journal.table.post.error", (Object[])new Object[]{tableDefine.getCode()}) + e.getMessage(), (Throwable)e);
            }
        });
        this.journalSingleService.batchUpdatePostFlag(condition.getTaskId(), condition.getSchemeId(), condition.getPeriodStr(), condition.getInputUnitId(), condition.getOrgTypeId());
        return retInfo.toString();
    }

    private void post(DataTable tableDefine, JournalSinglePostCondition condition, Map<String, BigDecimal> beforeFullZbCode2Value, Map<String, BigDecimal> afterFullZbCode2AdjustValue, Map<String, String> afterFullZbCode2beforeFullZbCode) {
        List fieldDefines;
        try {
            fieldDefines = this.dataDefinitionRuntimeController.getAllFieldsInTable(tableDefine.getKey());
            Collections.sort(fieldDefines, Comparator.comparing(UniversalFieldDefine::getCode));
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        this.mergePostData(tableDefine, fieldDefines, condition, beforeFullZbCode2Value, afterFullZbCode2AdjustValue, afterFullZbCode2beforeFullZbCode);
    }

    @Transactional(rollbackFor={Exception.class})
    private void mergePostData(DataTable tableDefine, List<FieldDefine> fieldDefines, JournalSinglePostCondition condition, Map<String, BigDecimal> beforeFullZbCode2Value, Map<String, BigDecimal> afterFullZbCode2AdjustValue, Map<String, String> afterFullZbCode2beforeFullZbCode) {
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(tableDefine.getCode());
        String tableKey = tableModel.getID();
        StringBuilder insertFieldColumnValues = new StringBuilder();
        StringBuilder insertFieldColumns = new StringBuilder();
        StringBuilder updateFieldSql = new StringBuilder();
        ArrayList<Double> values = new ArrayList<Double>();
        for (FieldDefine fieldDefine : fieldDefines) {
            String afterFullTableName;
            String beforeFullTableName;
            if (!this.isNumber(fieldDefine) || null == (beforeFullTableName = afterFullZbCode2beforeFullZbCode.get(afterFullTableName = tableDefine.getCode() + "[" + fieldDefine.getCode().toUpperCase() + "]"))) continue;
            beforeFullTableName = this.getDbTableAndColumnName(beforeFullTableName);
            ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableKey, fieldDefine.getCode());
            if (columnModelDefine == null) continue;
            double value = this.getWriteValue(beforeFullTableName, afterFullTableName, beforeFullZbCode2Value, afterFullZbCode2AdjustValue);
            String fieldName = columnModelDefine.getName();
            insertFieldColumns.append(fieldName).append(",");
            insertFieldColumnValues.append("?,");
            updateFieldSql.append(fieldName).append("=?,");
            values.add(value);
        }
        if (insertFieldColumns.length() < 1) {
            return;
        }
        this.logger.debug("\u5f00\u59cb\u5b58\u50a8\u8868{}\u6570\u636e\u8fc7\u8d26", (Object)tableModel.getName());
        int updateCount = this.updateData(tableModel, condition, updateFieldSql, values);
        if (updateCount > 0) {
            return;
        }
        this.insertData(tableModel, insertFieldColumnValues, insertFieldColumns, values, condition);
    }

    private String getDbTableAndColumnName(String beforeFullTableName) {
        String regex = "(.+)\\[(.+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(beforeFullTableName);
        if (matcher.find()) {
            String nrTableName = matcher.group(1).trim();
            String columnCode = matcher.group(2).trim();
            try {
                DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(nrTableName);
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(dataTable.getCode());
                String tableName = tableModelDefine.getName();
                ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), columnCode);
                if (columnModelDefine == null) {
                    List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
                    for (ColumnModelDefine modelDefine : columnModelDefines) {
                        if (!columnCode.equals(modelDefine.getName())) continue;
                        columnModelDefine = modelDefine;
                        break;
                    }
                }
                if (columnModelDefine != null) {
                    return tableName + "[" + columnModelDefine.getName().toUpperCase() + "]";
                }
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5f02\u5e38\uff1a{}", (Object)beforeFullTableName, (Object)e);
                throw new BusinessRuntimeException("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5f02\u5e38\uff1a" + beforeFullTableName, (Throwable)e);
            }
        }
        return beforeFullTableName;
    }

    private JournalSinglePostCondition getJournalSinglePostCondition(QueryContext queryContext) {
        ExecutorContext exeContext = queryContext.getExeContext();
        ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)exeContext.getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        DimensionValueSet ds = queryContext.getCurrentMasterKey();
        String orgTypeId = (String)ds.getValue("MD_GCORGTYPE");
        String orgId = (String)ds.getValue("MD_ORG");
        String currencyId = (String)ds.getValue("MD_CURRENCY");
        String periodString = String.valueOf(ds.getValue("DATATIME"));
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodString);
        JournalSinglePostCondition condition = new JournalSinglePostCondition();
        try {
            String taskId = env.getTaskDefine().getKey();
            condition.setTaskId(taskId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        condition.setInputUnitId(orgId);
        condition.setAcctYear(yearPeriodUtil.getYear());
        condition.setAcctPeriod(yearPeriodUtil.getPeriod());
        condition.setOrgTypeId(orgTypeId);
        condition.setCurrencyId(currencyId);
        condition.setSchemeId(formSchemeKey);
        condition.setPeriodStr(periodString);
        String adjustCode = ConverterUtils.getAsString((Object)ds.getValue("ADJUST"), (String)"0");
        condition.setSelectAdjustCode(adjustCode);
        return condition;
    }

    private int updateData(TableModelDefine tableDefine, JournalSinglePostCondition condition, StringBuilder updateFieldSql, List values) {
        updateFieldSql.setLength(updateFieldSql.length() - 1);
        ArrayList params = new ArrayList();
        params.addAll(values);
        String updateSql = "update " + tableDefine.getName() + " t set " + updateFieldSql + this.whereSql(condition, params);
        Object[] args = params.toArray(params.toArray(new Object[0]));
        int updateCount = EntNativeSqlDefaultDao.getInstance().execute(updateSql, args);
        if (updateCount > 1) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.journal.post.match.more.error") + updateSql);
        }
        if (updateCount == 1) {
            this.logger.debug(String.format("\u5b8c\u6210\u5b58\u50a8\u8868%s\u6570\u636e\u8fc7\u8d26\uff0c\u5b9a\u4f4d\u5230\u6570\u636e\u884c\u7ef4\u5ea6\u4fe1\u606f\uff0c\u4fee\u6539\u6570\u636e\u3002%s:%s", tableDefine.getName(), updateSql, StringUtils.join((Object[])args, (String)",")));
            return updateCount;
        }
        return updateCount;
    }

    private void insertData(TableModelDefine tableDefine, StringBuilder insertFieldColumnValues, StringBuilder insertFieldColumns, List values, JournalSinglePostCondition condition) {
        ArrayList params = new ArrayList();
        params.addAll(values);
        this.appendDimColumnInsertSql(condition, insertFieldColumns, insertFieldColumnValues, params);
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ").append(tableDefine.getName()).append("(").append((CharSequence)insertFieldColumns).append(")");
        insertSql.append(" values(").append((CharSequence)insertFieldColumnValues).append(")");
        Object[] args = params.toArray(params.toArray(new Object[0]));
        EntNativeSqlDefaultDao.getInstance().execute(insertSql.toString(), args);
        this.logger.debug(String.format("\u5b8c\u6210\u5b58\u50a8\u8868%s\u6570\u636e\u8fc7\u8d26\uff0c\u672a\u5b9a\u4f4d\u5230\u6570\u636e\u884c\u7ef4\u5ea6\u4fe1\u606f\uff0c\u65b0\u589e\u6570\u636e\u3002%s", tableDefine.getName(), StringUtils.join((Object[])args, (String)",")));
    }

    private double getWriteValue(String beforeFullTableName, String afterFullTableName, Map<String, BigDecimal> beforeFullZbCode2Value, Map<String, BigDecimal> afterFullZbCode2AdjustValue) {
        BigDecimal adjustValue;
        BigDecimal beforeValue = beforeFullZbCode2Value.get(beforeFullTableName);
        if (null == beforeValue) {
            beforeValue = BigDecimal.ZERO;
        }
        if (null == (adjustValue = afterFullZbCode2AdjustValue.get(afterFullTableName))) {
            return beforeValue.doubleValue();
        }
        return beforeValue.add(adjustValue).doubleValue();
    }

    private void appendDimColumnInsertSql(JournalSinglePostCondition condition, StringBuilder insertFieldColumns, StringBuilder insertFieldColumnValues, List params) {
        insertFieldColumns.append("MDCODE,DATATIME,MD_CURRENCY,MD_GCORGTYPE");
        insertFieldColumnValues.append("?,?,?,?");
        params.add(condition.getInputUnitId());
        params.add(condition.getPeriodStr());
        params.add(condition.getCurrencyId());
        params.add(condition.getOrgTypeId());
        if (DimensionUtils.isExisAdjType((String)condition.getTaskId())) {
            insertFieldColumns.append(", MD_GCADJTYPE");
            insertFieldColumnValues.append(",?");
            params.add(GCAdjTypeEnum.BEFOREADJ.getCode());
        }
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            insertFieldColumns.append(", ").append("ADJUST");
            insertFieldColumnValues.append(",?");
            params.add(condition.getSelectAdjustCode());
        }
    }

    private String whereSql(JournalSinglePostCondition condition, List params) {
        StringBuilder whereSql = new StringBuilder(128);
        whereSql.append(" where t.MDCODE = ? \n");
        whereSql.append("   and t.DATATIME = ? \n");
        whereSql.append("   and t.MD_CURRENCY = ? \n");
        whereSql.append("   and t.MD_GCORGTYPE = ? \n");
        params.add(condition.getInputUnitId());
        params.add(condition.getPeriodStr());
        params.add(condition.getCurrencyId());
        params.add(condition.getOrgTypeId());
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            whereSql.append("   and t.ADJUST = ? \n");
            params.add(condition.getSelectAdjustCode());
        }
        return whereSql.toString();
    }

    private boolean isNumber(FieldDefine fieldDefine) {
        if (null == fieldDefine) {
            return false;
        }
        switch (fieldDefine.getType()) {
            case FIELD_TYPE_INTEGER: 
            case FIELD_TYPE_FLOAT: 
            case FIELD_TYPE_DECIMAL: {
                return true;
            }
        }
        return false;
    }

    private Map<String, String> afterFullZbCode2beforeFullZbCode(List<JournalSubjectEO> journalSubjectEOS) {
        HashMap<String, String> afterFullZbCode2beforeFullZbCode = new HashMap<String, String>();
        for (JournalSubjectEO subject : journalSubjectEOS) {
            if (StringUtils.isNull((String)subject.getBeforeZbCode()) || StringUtils.isNull((String)subject.getAfterZbCode())) continue;
            afterFullZbCode2beforeFullZbCode.put(subject.getAfterZbCode(), subject.getBeforeZbCode());
        }
        return afterFullZbCode2beforeFullZbCode;
    }

    private List<JournalSubjectEO> listJournalPostSubjects(JournalSinglePostCondition condition) {
        String relateSchemeId = this.singleSchemeService.getRelateSchemeId(condition.getTaskId(), condition.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        if (null == relateSchemeId) {
            return new ArrayList<JournalSubjectEO>();
        }
        return this.singleSubjectService.listAllSubjects(relateSchemeId);
    }

    private Map<String, BigDecimal> beforeFullZbCode2Value(JournalSinglePostCondition condition, List<JournalSubjectEO> journalSubjectEOS) {
        Map<String, Set<String>> tableName2ZbCodesMap = this.tableName2ZbCodesMap(journalSubjectEOS, true);
        Map<String, BigDecimal> beforeFullZbCode2Value = this.getZbCode2Value(condition, tableName2ZbCodesMap);
        return beforeFullZbCode2Value;
    }

    private Map<String, BigDecimal> afterFullZbCode2Value(JournalSinglePostCondition condition, List<JournalSubjectEO> journalSubjectEOS) {
        Map<String, Set<String>> tableName2ZbCodesMap = this.tableName2ZbCodesMap(journalSubjectEOS, false);
        Map<String, BigDecimal> afterFullZbCode2Value = this.getZbCode2Value(condition, tableName2ZbCodesMap);
        return afterFullZbCode2Value;
    }

    private Map<String, BigDecimal> getZbCode2Value(JournalSinglePostCondition condition, Map<String, Set<String>> tableName2ZbCodesMap) {
        HashMap<String, BigDecimal> zbCode2Value = new HashMap<String, BigDecimal>(256);
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)condition.getOrgTypeId(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO inputUnitOrg = orgTool.getOrgByCode(condition.getInputUnitId());
        String fullSql = "select %1$s\n from %2$s t\nwhere  t.MDCODE = '" + condition.getInputUnitId() + "'\n   and t." + "DATATIME" + " = '" + condition.getPeriodStr() + "'\n   and t." + "MD_CURRENCY" + " = '" + condition.getCurrencyId() + "'\n   and t." + "MD_GCORGTYPE" + " in ('" + inputUnitOrg.getOrgTypeId() + "')\n";
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            fullSql = fullSql + "and t.ADJUST = '" + condition.getSelectAdjustCode() + "'\n";
        }
        for (Map.Entry<String, Set<String>> table2ZbCodeEntry : tableName2ZbCodesMap.entrySet()) {
            String tableName = table2ZbCodeEntry.getKey();
            String fieldSql = this.getFieldSql(table2ZbCodeEntry.getValue(), "t");
            String sql = String.format(fullSql, fieldSql, tableName);
            List zbDataList = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
            zbDataList.stream().forEach(oneRowData -> {
                for (Map.Entry zbCode2ZbValueEntry : oneRowData.entrySet()) {
                    String zbCode = (String)zbCode2ZbValueEntry.getKey();
                    Object zbValue = zbCode2ZbValueEntry.getValue();
                    if (null == zbValue) continue;
                    zbCode2Value.put(tableName + "[" + zbCode + "]", new BigDecimal(NumberUtils.parseDouble(zbValue)));
                }
            });
        }
        return zbCode2Value;
    }

    private Map<String, BigDecimal> subjectCode2AdjustValue(JournalSinglePostCondition condition, List<JournalSubjectEO> journalSubjectEOS) {
        Map<String, Integer> subjectCode2Orient = this.subjectCode2Orient(journalSubjectEOS);
        HashMap<String, BigDecimal> subjectCode2AdjustValue = new HashMap<String, BigDecimal>();
        List<JournalSingleEO> journalSingleEOS = this.journalSingleService.listJournalSingleByDims(condition.getTaskId(), condition.getSchemeId(), condition.getPeriodStr(), condition.getInputUnitId(), condition.getOrgTypeId(), condition.getSelectAdjustCode());
        for (JournalSingleEO journalSingleEO : journalSingleEOS) {
            Integer subjectDc = subjectCode2Orient.get(journalSingleEO.getSubjectCode());
            if (journalSingleEO.getDc() == null || null == subjectDc) continue;
            Double debit = journalSingleEO.getDebitCNY() == null ? 0.0 : journalSingleEO.getDebitCNY();
            Double credit = journalSingleEO.getCreditCNY() == null ? 0.0 : journalSingleEO.getCreditCNY();
            if (OrientEnum.D.getValue().intValue() == subjectDc.intValue()) {
                MapUtils.add(subjectCode2AdjustValue, (Object)journalSingleEO.getSubjectCode(), (BigDecimal)new BigDecimal(debit - credit));
                continue;
            }
            MapUtils.sub(subjectCode2AdjustValue, (Object)journalSingleEO.getSubjectCode(), (BigDecimal)new BigDecimal(debit - credit));
        }
        return subjectCode2AdjustValue;
    }

    private Map<String, BigDecimal> afterFullZbCode2AdjustValue(JournalSinglePostCondition condition, List<JournalSubjectEO> journalSubjectEOS) {
        Map<String, BigDecimal> subjectCode2AdjustValue = this.subjectCode2AdjustValue(condition, journalSubjectEOS);
        Map<String, List<JournalSubjectEO>> parentId2SubjectEOs = journalSubjectEOS.stream().collect(Collectors.groupingBy(JournalSubjectEO::getParentId));
        HashMap<String, BigDecimal> afterFullZbCode2AdjustValue = new HashMap<String, BigDecimal>(256);
        for (JournalSubjectEO journalSubjectEO : journalSubjectEOS) {
            if (StringUtils.isNull((String)journalSubjectEO.getBeforeZbCode()) || StringUtils.isNull((String)journalSubjectEO.getAfterZbCode())) continue;
            String afterFullZbCode = journalSubjectEO.getAfterZbCode();
            MapUtils.add(afterFullZbCode2AdjustValue, (Object)afterFullZbCode, (BigDecimal)subjectCode2AdjustValue.get(journalSubjectEO.getCode()));
            BigDecimal sumChildAdjustValue = this.sumChildAdjustValue(journalSubjectEO, parentId2SubjectEOs, subjectCode2AdjustValue);
            MapUtils.add(afterFullZbCode2AdjustValue, (Object)afterFullZbCode, (BigDecimal)sumChildAdjustValue);
        }
        return afterFullZbCode2AdjustValue;
    }

    private BigDecimal sumChildAdjustValue(JournalSubjectEO currSubject, Map<String, List<JournalSubjectEO>> parentId2SubjectEOs, Map<String, BigDecimal> subjectCode2AdjustValue) {
        BigDecimal result = BigDecimal.ZERO;
        if (!parentId2SubjectEOs.containsKey(currSubject.getId())) {
            return result;
        }
        List<JournalSubjectEO> childSubjectEOS = parentId2SubjectEOs.get(currSubject.getId());
        for (JournalSubjectEO childSubjectEO : childSubjectEOS) {
            BigDecimal adjustValue = subjectCode2AdjustValue.get(childSubjectEO.getCode());
            if (childSubjectEO.getOrient() == null) continue;
            if (childSubjectEO.getOrient().equals(currSubject.getOrient())) {
                result = result.add(null == adjustValue ? BigDecimal.ZERO : adjustValue);
                result = result.add(this.sumChildAdjustValue(childSubjectEO, parentId2SubjectEOs, subjectCode2AdjustValue));
                continue;
            }
            result = result.subtract(null == adjustValue ? BigDecimal.ZERO : adjustValue);
            result = result.subtract(this.sumChildAdjustValue(childSubjectEO, parentId2SubjectEOs, subjectCode2AdjustValue));
        }
        return result;
    }

    private String getFieldSql(Set<String> zbCodeSet, String tableAlias) {
        if (zbCodeSet.isEmpty()) {
            return "";
        }
        StringBuilder fieldStr = new StringBuilder(256);
        for (String zbCode : zbCodeSet) {
            fieldStr.append("t.").append(zbCode).append(",");
        }
        fieldStr.setLength(fieldStr.length() - 1);
        return fieldStr.toString();
    }

    private Map<String, Set<String>> tableName2ZbCodesMap(List<JournalSubjectEO> journalSubjectEOS, boolean isBeforeZb) {
        String regex = "(.+)\\[(.+)\\]";
        Pattern pattern = Pattern.compile(regex);
        HashMap<String, Set<String>> tableName2ZbCodesMap = new HashMap<String, Set<String>>(16);
        for (JournalSubjectEO subjectEO : journalSubjectEOS) {
            String zbCode;
            Matcher matcher;
            if (StringUtils.isNull((String)subjectEO.getBeforeZbCode()) || StringUtils.isNull((String)subjectEO.getAfterZbCode()) || !(matcher = pattern.matcher(zbCode = isBeforeZb ? subjectEO.getBeforeZbCode() : subjectEO.getAfterZbCode())).find()) continue;
            String nrTableName = matcher.group(1).trim();
            String columnCode = matcher.group(2).trim();
            try {
                ColumnModelDefine columnModelDefine;
                DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(nrTableName);
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(dataTable.getCode());
                String tableName = tableModelDefine.getName();
                if (!tableName2ZbCodesMap.containsKey(tableName)) {
                    tableName2ZbCodesMap.put(tableName, new HashSet(64));
                }
                if ((columnModelDefine = this.dataModelService.getColumnModelDefineByCode(tableModelDefine.getID(), columnCode)) == null) {
                    List columnModelDefines = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
                    for (ColumnModelDefine modelDefine : columnModelDefines) {
                        if (!columnCode.equals(modelDefine.getName())) continue;
                        columnModelDefine = modelDefine;
                        break;
                    }
                }
                if (columnModelDefine == null) continue;
                ((Set)tableName2ZbCodesMap.get(tableName)).add(columnModelDefine.getName());
            }
            catch (Exception e) {
                this.logger.error("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5f02\u5e38\uff1a{}", (Object)zbCode, (Object)e);
                throw new BusinessRuntimeException("\u83b7\u53d6\u6307\u6807\u4ee3\u7801\u5f02\u5e38\uff1a" + zbCode, (Throwable)e);
            }
        }
        return tableName2ZbCodesMap;
    }

    private Map<String, Integer> subjectCode2Orient(List<JournalSubjectEO> journalSubjectEOS) {
        return journalSubjectEOS.stream().collect(Collectors.toMap(JournalSubjectEO::getCode, JournalSubjectEO::getOrient));
    }

    private void checkActionValid(JournalSinglePostCondition condition) {
        Objects.requireNonNull(condition.getInputUnitId(), GcI18nUtil.getMessage((String)"gc.journal.post.unit.null.error"));
        Objects.requireNonNull(condition.getOrgTypeId(), GcI18nUtil.getMessage((String)"gc.journal.post.unit.type.null.error"));
        Objects.requireNonNull(condition.getPeriodStr(), GcI18nUtil.getMessage((String)"gc.journal.post.period.null.error"));
        Objects.requireNonNull(condition.getTaskId(), GcI18nUtil.getMessage((String)"gc.journal.post.task.null.error"));
        Objects.requireNonNull(condition.getSchemeId(), GcI18nUtil.getMessage((String)"gc.journal.post.scheme.null.error"));
    }

    private Set<DataTable> getTaskAllTableDefines(JournalSinglePostCondition condition, StringBuffer retInfo) {
        List formDefines;
        try {
            formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(condition.getSchemeId());
            this.filterLockedForm(formDefines, condition, retInfo);
        }
        catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
        if (CollectionUtils.isEmpty((Collection)formDefines)) {
            return null;
        }
        TreeSet<DataTable> tableDefineSet = new TreeSet<DataTable>((o1, o2) -> o1.getCode().compareToIgnoreCase(o2.getCode()));
        formDefines.stream().filter(Objects::nonNull).forEach(formDefine -> {
            List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
                return;
            }
            dataRegionDefines.stream().filter(Objects::nonNull).filter(dataRegionDefine -> dataRegionDefine != null && dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).forEach(dataRegionDefine -> {
                List dataFields = this.runtimeDataSchemeService.getDataFields(this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey()));
                if (CollectionUtils.isEmpty((Collection)dataFields)) {
                    return;
                }
                List tableDefines = dataFields.stream().map(DataField::getDataTableKey).distinct().map(dataTableKey -> this.runtimeDataSchemeService.getDataTable(dataTableKey)).collect(Collectors.toList());
                tableDefines.stream().filter(Objects::nonNull).forEach(tableDefine -> {
                    boolean isAllowPost;
                    switch (tableDefine.getKind()) {
                        case TABLE_KIND_DICTIONARY: 
                        case TABLE_KIND_ENTITY_PERIOD: 
                        case TABLE_KIND_MEASUREMENT_UNIT: 
                        case TABLE_KIND_SYSTEM: 
                        case TABLE_KIND_ENTITY: {
                            isAllowPost = false;
                            break;
                        }
                        default: {
                            isAllowPost = true;
                        }
                    }
                    if (!isAllowPost) {
                        return;
                    }
                    tableDefineSet.add((DataTable)tableDefine);
                });
            });
        });
        if (CollectionUtils.isEmpty(tableDefineSet)) {
            return null;
        }
        return tableDefineSet;
    }

    private void filterLockedForm(List<FormDefine> formDefines, JournalSinglePostCondition condition, StringBuffer retInfo) {
        List formIds = formDefines.stream().map(formDefine -> formDefine.getKey()).collect(Collectors.toList());
        DimensionParamsVO queryParamsVO = new DimensionParamsVO();
        BeanUtils.copyProperties(condition, queryParamsVO);
        queryParamsVO.setOrgId(condition.getInputUnitId());
        List readWriteAccessDescs = FormUploadStateTool.getInstance().writeable(queryParamsVO, formIds);
        StringBuffer readOnlyFormTitles = new StringBuffer(128);
        ArrayList<FormDefine> removedFormDefines = new ArrayList<FormDefine>();
        for (int i = 0; i < formDefines.size(); ++i) {
            FormDefine formDefine2 = formDefines.get(i);
            ReadWriteAccessDesc readWriteAccessDesc = (ReadWriteAccessDesc)readWriteAccessDescs.get(i);
            if (readWriteAccessDesc.getAble().booleanValue()) continue;
            readOnlyFormTitles.append(formDefine2.getTitle()).append(",");
            removedFormDefines.add(formDefine2);
        }
        formDefines.removeAll(removedFormDefines);
        retInfo.append(GcI18nUtil.getMessage((String)"gc.journal.post.table.lock.error") + readOnlyFormTitles);
    }

    @Override
    public List<JournalWorkPaperDataVO> getJournalWorkPaperData(JournalDetailCondition condi) {
        JournalSinglePostCondition postCondition = new JournalSinglePostCondition();
        BeanUtils.copyProperties(condi, postCondition);
        List<JournalSubjectEO> journalSubjectEos = this.listJournalPostSubjects(postCondition);
        journalSubjectEos.sort(Comparator.comparing(JournalSubjectEO::getCode));
        List adjustTypeVOS = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO("MD_ADJUSTTYPE");
        Map<String, BigDecimal> adjustValueMap = this.getAdjustDataMap(journalSubjectEos, condi, adjustTypeVOS);
        Map<String, BigDecimal> beforeFullZbCode2Value = this.beforeFullZbCode2Value(postCondition, journalSubjectEos);
        Map<String, BigDecimal> afterFullZbCode2AdjustValue = this.afterFullZbCode2Value(postCondition, journalSubjectEos);
        List<Object> workPaperDataVOS = this.assemblyWokrPaperData(journalSubjectEos, adjustTypeVOS, adjustValueMap, beforeFullZbCode2Value, afterFullZbCode2AdjustValue, condi.getSchemeId());
        if (!condi.isShowEmptyRow()) {
            workPaperDataVOS = workPaperDataVOS.stream().filter(notNullVo -> notNullVo.getParentId() == null || !notNullVo.getAfterCalcValue().startsWith("0") || !notNullVo.getAfterZbValue().startsWith("0")).collect(Collectors.toList());
        }
        if (condi.getBaseSubjectProp() != null && condi.getBaseSubjectProp().size() != 0) {
            List subjectCodeList = condi.getBaseSubjectProp().stream().map(vo -> vo.getCode()).collect(Collectors.toList());
            workPaperDataVOS = workPaperDataVOS.stream().filter(vo -> subjectCodeList.contains(vo.getSubjectCode())).collect(Collectors.toList());
        }
        if (condi.isPaginationDisplay) {
            return workPaperDataVOS;
        }
        Map<String, JournalSubjectEO> subjectDataMap = journalSubjectEos.stream().collect(Collectors.toMap(DefaultTableEntity::getId, eo -> eo));
        return this.getWorkingTreeData(workPaperDataVOS, subjectDataMap);
    }

    private Map<String, BigDecimal> getAdjustDataMap(List<JournalSubjectEO> journalSubjectEos, JournalDetailCondition condi, List<BaseDataVO> adjustTypeVOS) {
        Map<String, JournalSubjectEO> subjectDcMap = journalSubjectEos.stream().collect(Collectors.toMap(JournalSubjectEO::getCode, eo -> eo));
        List<JournalSingleEO> journalSingleEOList = this.journalSingleService.queryByPageCondition(condi);
        HashMap<String, BigDecimal> adjustValueMap = new HashMap<String, BigDecimal>();
        for (int i = 0; i < journalSingleEOList.size(); ++i) {
            JournalSingleEO journalSingleEO = journalSingleEOList.get(i);
            JournalSubjectEO subjectEO = subjectDcMap.get(journalSingleEO.getSubjectCode());
            if (journalSingleEO.getDc() == null || StringUtils.isEmpty((String)journalSingleEO.getAdjustTypeCode()) || subjectEO == null) continue;
            for (int j = 0; j < adjustTypeVOS.size(); ++j) {
                BaseDataVO baseDataVo = adjustTypeVOS.get(j);
                if (!journalSingleEO.getAdjustTypeCode().equals(baseDataVo.getCode())) continue;
                Double debitCNY = journalSingleEO.getDebitCNY() == null ? 0.0 : journalSingleEO.getDebitCNY();
                Double creditCNY = journalSingleEO.getCreditCNY() == null ? 0.0 : journalSingleEO.getCreditCNY();
                BigDecimal dxs = journalSingleEO.getDc().intValue() == subjectEO.getOrient().intValue() ? new BigDecimal(debitCNY + creditCNY) : new BigDecimal((debitCNY - creditCNY) * (double)subjectEO.getOrient().intValue());
                MapUtils.add(adjustValueMap, (Object)(journalSingleEO.getSubjectCode() + baseDataVo.getCode()), (BigDecimal)dxs);
            }
        }
        return adjustValueMap;
    }

    private List<JournalWorkPaperDataVO> assemblyWokrPaperData(List<JournalSubjectEO> journalSubjectEos, List<BaseDataVO> adjustTypeVOS, Map<String, BigDecimal> adjustValueMap, Map<String, BigDecimal> beforeFullZbCode2Value, Map<String, BigDecimal> afterFullZbCode2AdjustValue, String schemeId) {
        ArrayList<JournalWorkPaperDataVO> workPaperDataVOS = new ArrayList<JournalWorkPaperDataVO>();
        Map<String, JournalSubjectEO> subjectMap = journalSubjectEos.stream().collect(Collectors.toMap(DefaultTableEntity::getId, eo -> eo));
        HashMap<String, Map<String, DesignDataLinkDefine>> dataLinkCacheMap = new HashMap<String, Map<String, DesignDataLinkDefine>>();
        Map<String, String> zbId2FormIdMap = this.getZbId2FormIdMap(schemeId);
        for (JournalSubjectEO eo2 : journalSubjectEos) {
            JournalWorkPaperDataVO workPaperDataVO = new JournalWorkPaperDataVO();
            workPaperDataVO.setSubjectCode(eo2.getCode());
            workPaperDataVO.setSubjectTitle(eo2.getTitle());
            workPaperDataVO.setBeforZbId(eo2.getBeforeZbId());
            JournalSubjectEO parentSubjectEo = subjectMap.get(eo2.getParentId());
            if (parentSubjectEo != null) {
                workPaperDataVO.setParentId(eo2.getParentId());
                BigDecimal beforeZbValue = beforeFullZbCode2Value.get(eo2.getBeforeZbCode()) == null ? new BigDecimal(0) : beforeFullZbCode2Value.get(eo2.getBeforeZbCode());
                workPaperDataVO.setBeforeZbValue(this.formatBigDecialToStr(beforeZbValue));
                String beforeFormId = zbId2FormIdMap.get(eo2.getBeforeZbId());
                workPaperDataVO.setBeforeFormId(beforeFormId);
                DesignDataLinkDefine dataLinkDefine = this.findDataLinkDefine(beforeFormId, eo2.getBeforeZbId(), dataLinkCacheMap);
                if (dataLinkDefine != null) {
                    String colAndRowLinkNum = "[" + dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum() + "]";
                    workPaperDataVO.setColAndRowLinkNum(colAndRowLinkNum);
                }
                BigDecimal afterZbValue = afterFullZbCode2AdjustValue.get(eo2.getAfterZbCode()) == null ? new BigDecimal(0) : afterFullZbCode2AdjustValue.get(eo2.getAfterZbCode());
                workPaperDataVO.setAfterZbValue(this.formatBigDecialToStr(afterZbValue));
                BigDecimal afterCalcValue = new BigDecimal(0);
                HashMap<String, BigDecimal> tzValueMap = new HashMap<String, BigDecimal>();
                for (BaseDataVO baseDataVo : adjustTypeVOS) {
                    BigDecimal tzValue = adjustValueMap.get(eo2.getCode() + baseDataVo.getCode());
                    if (tzValue == null) {
                        tzValue = new BigDecimal(0);
                    }
                    tzValueMap.put(baseDataVo.getCode(), tzValue);
                    afterCalcValue = afterCalcValue.add(tzValue);
                }
                tzValueMap.put("totalValue", afterCalcValue);
                workPaperDataVO.setTzValue(tzValueMap);
                afterCalcValue = afterCalcValue.add(beforeZbValue);
                workPaperDataVO.setAfterCalcValue(this.formatBigDecialToStr(afterCalcValue));
            }
            workPaperDataVOS.add(workPaperDataVO);
        }
        this.sumSubjectCodeByLevel(workPaperDataVOS, journalSubjectEos);
        return workPaperDataVOS;
    }

    public void sumSubjectCodeByLevel(List<JournalWorkPaperDataVO> datas, List<JournalSubjectEO> journalSubjectEos) {
        Set parentSubjectIdSet = journalSubjectEos.stream().map(eo -> eo.getParentId()).collect(Collectors.toSet());
        Map<String, JournalSubjectEO> subjectCode2EOMap = journalSubjectEos.stream().collect(Collectors.toMap(JournalSubjectEO::getCode, eo -> eo));
        for (JournalWorkPaperDataVO vo : datas) {
            String srcSubjectCode = vo.getSubjectCode();
            JournalSubjectEO journalSubjectEO = subjectCode2EOMap.get(srcSubjectCode);
            if (journalSubjectEO == null || StringUtils.isEmpty((String)vo.getParentId()) || !parentSubjectIdSet.contains(journalSubjectEO.getId())) continue;
            for (JournalWorkPaperDataVO subdatasVo : datas) {
                JournalSubjectEO journalEO = subjectCode2EOMap.get(subdatasVo.getSubjectCode());
                if (!subdatasVo.getSubjectCode().startsWith(srcSubjectCode) || parentSubjectIdSet.contains(journalEO.getId())) continue;
                for (Map.Entry tzValueSum : vo.getTzValue().entrySet()) {
                    BigDecimal tzValue = (BigDecimal)tzValueSum.getValue();
                    tzValue = journalSubjectEO.getOrient().equals(journalEO.getOrient()) ? tzValue.add((BigDecimal)subdatasVo.getTzValue().get(tzValueSum.getKey())) : tzValue.subtract((BigDecimal)subdatasVo.getTzValue().get(tzValueSum.getKey()));
                    vo.getTzValue().put(tzValueSum.getKey(), tzValue);
                }
                BigDecimal srcBeforeValue = new BigDecimal(vo.getBeforeZbValue().replace(",", ""));
                BigDecimal totalValue = (BigDecimal)vo.getTzValue().get("totalValue");
                srcBeforeValue = srcBeforeValue.add(totalValue);
                vo.setAfterCalcValue(this.formatBigDecialToStr(srcBeforeValue));
            }
        }
    }

    private List<JournalWorkPaperDataVO> getWorkingTreeData(List<JournalWorkPaperDataVO> datas, Map<String, JournalSubjectEO> subjectVOHashMap) {
        Map<String, JournalWorkPaperDataVO> parentWorking = datas.stream().collect(Collectors.toMap(JournalWorkPaperDataVO::getSubjectCode, baseData -> baseData));
        ArrayList<JournalWorkPaperDataVO> tree = new ArrayList<JournalWorkPaperDataVO>();
        for (JournalWorkPaperDataVO node1 : datas) {
            JournalWorkPaperDataVO pobj = this.getParentWorking(parentWorking, node1.getParentId(), subjectVOHashMap);
            if (pobj != null) {
                pobj.getChildren().add(parentWorking.get(node1.getSubjectCode()));
                continue;
            }
            tree.add(parentWorking.get(node1.getSubjectCode()));
        }
        return tree;
    }

    private JournalWorkPaperDataVO getParentWorking(Map<String, JournalWorkPaperDataVO> parentWorking, String parentId, Map<String, JournalSubjectEO> subjectVOHashMap) {
        if (parentId == null) {
            return null;
        }
        JournalSubjectEO subjectVO = subjectVOHashMap.get(parentId);
        if (subjectVO == null) {
            return null;
        }
        JournalWorkPaperDataVO vo = parentWorking.get(subjectVO.getCode());
        if (vo != null) {
            return vo;
        }
        vo = this.getParentWorking(parentWorking, subjectVO.getParentId(), subjectVOHashMap);
        return vo;
    }

    private String getFormIdByZbCode(String schemeId, String zbCode) {
        if (StringUtils.isEmpty((String)zbCode)) {
            return "";
        }
        String formId = "";
        try {
            String formCode = "";
            String regex = "(.+)\\[(.+)\\]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(zbCode);
            if (matcher.find()) {
                formCode = matcher.group(1).trim();
                FormDefine formDefine = this.iRunTimeViewController.queryFormByCodeInScheme(schemeId, formCode);
                if (formDefine == null) {
                    return formId;
                }
                formId = formDefine.getKey();
            }
        }
        catch (Exception e) {
            this.logger.error("\u6839\u636e\u62a5\u8868\u65b9\u6848[" + schemeId + "],\u6307\u6807\u4ee3\u7801[" + zbCode + "] \u83b7\u53d6\u62a5\u8868Id\u5931\u8d25", e);
        }
        return formId;
    }

    private Map<String, String> getZbId2FormIdMap(String schemeId) {
        List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(schemeId);
        HashMap<String, String> zbId2FormIdMap = new HashMap<String, String>();
        if (CollectionUtils.isEmpty((Collection)formDefines)) {
            return zbId2FormIdMap;
        }
        formDefines.stream().filter(Objects::nonNull).forEach(formDefine -> {
            List dataLinkDefines = this.runTimeViewController.getAllLinksInForm(formDefine.getKey());
            zbId2FormIdMap.putAll(dataLinkDefines.stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, formKey -> formDefine.getKey(), (e1, e2) -> e1)));
        });
        return zbId2FormIdMap;
    }

    private DesignDataLinkDefine findDataLinkDefine(String formKey, String fieldKey, Map<String, Map<String, DesignDataLinkDefine>> cacheMap) {
        if (StringUtils.isEmpty((String)formKey) || StringUtils.isEmpty((String)fieldKey)) {
            return null;
        }
        Map<String, DesignDataLinkDefine> zbId2LinkDefine = cacheMap.get(formKey);
        if (null == zbId2LinkDefine) {
            IDesignTimeViewController runTimeViewController = (IDesignTimeViewController)SpringContextUtils.getBean(IDesignTimeViewController.class);
            List dataRegionDefines = runTimeViewController.getAllRegionsInForm(formKey);
            if (dataRegionDefines.size() < 1) {
                return null;
            }
            String regionKey = ((DesignDataRegionDefine)dataRegionDefines.get(0)).getKey();
            zbId2LinkDefine = runTimeViewController.getAllLinksInRegion(regionKey).stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, DesignDataLinkDefine2 -> DesignDataLinkDefine2));
            cacheMap.put(formKey, zbId2LinkDefine);
        }
        return zbId2LinkDefine.get(fieldKey);
    }

    private String formatBigDecialToStr(BigDecimal zbValue) {
        if (zbValue == null) {
            zbValue = new BigDecimal(0);
        }
        DecimalFormat df = new DecimalFormat("###,##0.00");
        return df.format(zbValue);
    }

    @Override
    public List<JournalSingleVO> getPenerationData(JournalDetailCondition condi) {
        List<JournalSingleEO> journalSingleEOList = this.journalSingleService.queryByPageCondition(condi);
        String relateSchemeId = this.singleSchemeService.getRelateSchemeId(condi.getTaskId(), condi.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        List dimensionVOS = this.dimensionService.findDimFieldsByTableName("GC_JOURNAL_SINGLE");
        List<Object> detailList = new ArrayList<JournalSingleVO>();
        for (JournalSingleEO eo : journalSingleEOList) {
            JournalSingleVO vo2 = this.journalSingleService.convertEO2VO(eo, relateSchemeId, dimensionVOS);
            detailList.add(vo2);
        }
        Set<String> subjectCodeSet = this.getCurrSubjectAndChildrenSet(relateSchemeId, condi.getSubjectCode());
        Map journalVOGroupMap = detailList.stream().collect(Collectors.groupingBy(JournalSingleVO::getmRecid, LinkedHashMap::new, Collectors.toList()));
        detailList = new ArrayList();
        for (List journalVOList : journalVOGroupMap.values()) {
            boolean isChildren = false;
            for (JournalSingleVO journalVO : journalVOList) {
                if (!subjectCodeSet.contains(journalVO.getSubjectCode())) continue;
                isChildren = true;
                break;
            }
            if (!isChildren) continue;
            detailList.addAll(journalVOList);
        }
        if ("2".equals(condi.getCurrShowTypeValue())) {
            detailList = detailList.stream().filter(vo -> subjectCodeSet.contains(vo.getSubjectCode()) && (condi.getAdjustTypeCode().equals(vo.getAdjustTypeCode()) || StringUtils.isEmpty((String)condi.adjustTypeCode))).collect(Collectors.toList());
        }
        return detailList;
    }

    @Override
    public JournalAdjustedFiguresVO getAdjustedfigure(JournalSinglePostCondition condi, String zbCode) {
        JournalSubjectEO subjectEOByZbCode = this.singleSubjectService.getSubjectEOByZbCode(zbCode);
        JournalAdjustedFiguresVO vo = new JournalAdjustedFiguresVO();
        if (subjectEOByZbCode == null) {
            return vo;
        }
        List<JournalSubjectEO> allSubjectEOs = this.singleSubjectService.listAllSubjects(subjectEOByZbCode.getjRelateSchemeId());
        ArrayList<JournalSubjectEO> subjectEOs = new ArrayList<JournalSubjectEO>();
        subjectEOs.add(subjectEOByZbCode);
        Map<String, BigDecimal> beforeFullZbCode2Value = this.beforeFullZbCode2Value(condi, subjectEOs);
        Map<String, BigDecimal> afterFullZbCode2Value = this.afterFullZbCode2Value(condi, subjectEOs);
        BigDecimal beforeBigDecimal = beforeFullZbCode2Value.get(subjectEOByZbCode.getBeforeZbCode());
        BigDecimal afterBigDecimal = afterFullZbCode2Value.get(subjectEOByZbCode.getAfterZbCode());
        vo.setBeforNumber(Double.valueOf(beforeBigDecimal == null ? 0.0 : beforeBigDecimal.doubleValue()));
        vo.setAfterNumber(Double.valueOf(afterBigDecimal == null ? 0.0 : afterBigDecimal.doubleValue()));
        Map<String, BigDecimal> subjectCode2AdjustValue = this.subjectCode2AdjustValue(condi, allSubjectEOs);
        Map<String, List<JournalSubjectEO>> parentId2SubjectEOs = allSubjectEOs.stream().collect(Collectors.groupingBy(JournalSubjectEO::getParentId));
        BigDecimal adjustBigDecimal = subjectCode2AdjustValue.get(subjectEOByZbCode.getCode());
        BigDecimal sumChildAdjustValue = this.sumChildAdjustValue(subjectEOByZbCode, parentId2SubjectEOs, subjectCode2AdjustValue);
        if (adjustBigDecimal == null) {
            adjustBigDecimal = BigDecimal.ZERO;
        }
        if (sumChildAdjustValue == null) {
            sumChildAdjustValue = BigDecimal.ZERO;
        }
        vo.setAdjustedNumber(Double.valueOf(adjustBigDecimal.add(sumChildAdjustValue).doubleValue()));
        vo.setBeforZbId(subjectEOByZbCode.getBeforeZbId());
        return vo;
    }

    private Set<String> getCurrSubjectAndChildrenSet(String relateSchemeId, String subjectCode) {
        Set<String> subjectCodeSet = new HashSet<String>();
        if (StringUtils.isEmpty((String)subjectCode)) {
            return subjectCodeSet;
        }
        subjectCodeSet.add(subjectCode);
        JournalSubjectEO parentSubjectEo = this.singleSubjectService.getSubjectEOByCode(relateSchemeId, subjectCode);
        if (parentSubjectEo == null) {
            return subjectCodeSet;
        }
        Pagination<JournalSubjectVO> subjectListPage = this.singleSubjectService.listChildSubjectsOrSelf(parentSubjectEo.getId(), true, -1, -1);
        List subjectVOList = subjectListPage.getContent();
        if (subjectVOList.isEmpty()) {
            return subjectCodeSet;
        }
        subjectCodeSet = subjectVOList.stream().map(vo -> vo.getCode()).collect(Collectors.toSet());
        return subjectCodeSet;
    }
}

