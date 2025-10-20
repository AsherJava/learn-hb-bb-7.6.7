/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult
 *  com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.inputdata.check.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckAbleResult;
import com.jiuqi.gcreport.inputdata.check.InputDataCheckCondition;
import com.jiuqi.gcreport.inputdata.check.dao.InputDataCheckDao;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckColumnDataUtils;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckComparatorUtil;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckSQLUtils;
import com.jiuqi.gcreport.inputdata.check.utils.InputDataCheckUploadUtil;
import com.jiuqi.gcreport.inputdata.inputdata.dao.InputDataDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.inputdata.enums.InputDataCheckStateEnum;
import com.jiuqi.gcreport.inputdata.inputdata.service.TemplateEntDaoCacheService;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.np.core.context.NpContextHolder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class InputDataCheckDaoImpl
implements InputDataCheckDao {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    @Autowired
    private TemplateEntDaoCacheService templateEntDaoCacheService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private InputDataDao inputDataDao;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Pagination<Map<String, Object>> checkTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        Assert.notNull((Object)inputDataCheckCondition.getTaskId(), GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        int pageNum = inputDataCheckCondition.getPageNum();
        int pageSize = inputDataCheckCondition.getPageSize();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        StringBuilder sql = new StringBuilder(512);
        sql.append("SELECT record.CHECKGROUPID from ").append(tableName);
        sql.append(" record \n");
        ArrayList<Object> params = new ArrayList<Object>();
        String joinSql = InputDataCheckSQLUtils.getQueryCheckTabDataSql(inputDataCheckCondition, params, InputDataCheckStateEnum.CHECK, systemId);
        sql.append(joinSql);
        InputDataCheckSQLUtils.initOtherCondition(sql, inputDataCheckCondition.getFilterCondition(), systemId, true);
        sql.append(" group by CHECKGROUPID \n");
        List inputDataCheckGroupIds = dao.selectMap(sql.toString(), params);
        if (inputDataCheckGroupIds.isEmpty()) {
            return page;
        }
        ArrayList<String> checkGroupIds = new ArrayList<String>();
        for (Map data : inputDataCheckGroupIds) {
            if (Objects.isNull(data.get("CHECKGROUPID"))) continue;
            String checkGroupId = (String)data.get("CHECKGROUPID");
            checkGroupIds.add(checkGroupId);
        }
        ArrayList<String> otherShowAndGatherColumns = new ArrayList<String>();
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getCheckGatherColumns());
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getOtherShowColumns());
        StringBuffer checkTableDataSql = new StringBuffer("Select ");
        String columnSelectSQL = SqlUtils.getColumnsSql((String[])this.getSelectColumn(InputDataCheckColumnDataUtils.selectCheckTabColumns, otherShowAndGatherColumns), (String)"record");
        checkTableDataSql.append(columnSelectSQL);
        checkTableDataSql.append(" from ").append(tableName).append(" record \n");
        checkTableDataSql.append(joinSql);
        TempTableCondition conditionOfIds = null;
        if (!CollectionUtils.isEmpty(checkGroupIds)) {
            conditionOfIds = SqlUtils.getConditionOfIds(checkGroupIds, (String)" and record.CHECKGROUPID");
            checkTableDataSql.append(conditionOfIds.getCondition());
        }
        checkTableDataSql.append(" order by record.CHECKTIME desc");
        int totalCount = dao.count(checkTableDataSql.toString(), params);
        if (totalCount < 1) {
            return page;
        }
        page.setTotalElements(Integer.valueOf(totalCount));
        ArrayList<InputDataEO> inputDatas = new ArrayList();
        try {
            inputDatas = dao.selectEntityByPaging(checkTableDataSql.toString(), (pageNum - 1) * pageSize, pageNum * pageSize, params);
        }
        finally {
            if (!Objects.isNull(conditionOfIds)) {
                IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
            }
        }
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        List<Map<String, Object>> unSortedRecords = InputDataCheckColumnDataUtils.getCheckTableDataByColumns(inputDatas, InputDataCheckColumnDataUtils.selectCheckTabColumns, showDictCode, inputDataCheckCondition, true);
        List<Map<String, Object>> dataSortCheckDataItems = this.getDataSortCheckGroupId(checkGroupIds, unSortedRecords);
        List<Map<String, Object>> returnDatas = InputDataCheckComparatorUtil.setRowSpanAndSort(dataSortCheckDataItems, inputDataCheckCondition.getFilterCondition());
        page.setContent(returnDatas);
        page.setPageSize(Integer.valueOf(pageSize));
        page.setCurrentPage(Integer.valueOf(pageNum));
        return page;
    }

    @Override
    public Pagination<Map<String, Object>> unCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        Assert.notNull((Object)inputDataCheckCondition.getTaskId(), GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<String> otherShowAndGatherColumns = new ArrayList<String>();
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getCheckGatherColumns());
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getOtherShowColumns());
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder checkTableDataSql = this.getUnCheckDataSql(InputDataCheckColumnDataUtils.selectUnCheckTabColumns, otherShowAndGatherColumns, tableName, inputDataCheckCondition, params, true, InputDataCheckStateEnum.NOTCHECK, systemId);
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        return this.getCheckInputData(dao, checkTableDataSql.toString(), params, inputDataCheckCondition, showDictCode, InputDataCheckColumnDataUtils.selectUnCheckTabColumns);
    }

    @Override
    public Pagination<Map<String, Object>> allCheckTabDatas(InputDataCheckCondition inputDataCheckCondition) {
        Assert.notNull((Object)inputDataCheckCondition.getTaskId(), GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<String> otherShowAndGatherColumns = new ArrayList<String>();
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getCheckGatherColumns());
        otherShowAndGatherColumns.addAll(inputDataCheckCondition.getOtherShowColumns());
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder checkTableDataSql = this.getUnCheckDataSql(InputDataCheckColumnDataUtils.selectAllCheckTabColumns, otherShowAndGatherColumns, tableName, inputDataCheckCondition, params, true, null, systemId);
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        return this.getCheckInputData(dao, checkTableDataSql.toString(), params, inputDataCheckCondition, showDictCode, InputDataCheckColumnDataUtils.selectAllCheckTabColumns);
    }

    @Override
    public Map<String, Object> manualCheck(InputDataCheckCondition inputDataCheckCondition) {
        String taskId = inputDataCheckCondition.getTaskId();
        Assert.notNull((Object)taskId, GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(taskId);
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        List recordDataIds = inputDataCheckCondition.getRecordDataIds();
        if (CollectionUtils.isEmpty((Collection)recordDataIds)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.manualitemnonemsg"));
        }
        List<InputDataEO> inputDatas = this.inputDataDao.queryByIds(recordDataIds, tableName);
        if (CollectionUtils.isEmpty(inputDatas)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.manualqueryitemnonemsg"));
        }
        HashSet<String> unitAndOppUnitCode = new HashSet<String>();
        HashSet<String> ruleIds = new HashSet<String>();
        for (InputDataEO inputData : inputDatas) {
            ruleIds.add(inputData.getUnionRuleId());
            unitAndOppUnitCode.add(inputData.getUnitId());
            unitAndOppUnitCode.add(inputData.getOppUnitId());
        }
        if (ruleIds.size() >= 2) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.manualrulemsg"));
        }
        if (unitAndOppUnitCode.size() > 2) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.itemsizemsg"));
        }
        String[] orgs = unitAndOppUnitCode.toArray(new String[2]);
        InputDataCheckAbleResult inputDataCheckAbleResult = new InputDataCheckUploadUtil().checkUpload(inputDataCheckCondition, orgs[0], orgs[1]);
        if (Boolean.FALSE.equals(inputDataCheckAbleResult.getWriteAble())) {
            throw new BusinessRuntimeException(inputDataCheckAbleResult.getMsg());
        }
        String[] selectColumns = new String[]{"ORGCODE", "OPPUNITID", "CHECKGROUPID", "CHECKTYPE", "UNIONRULEID", "CHECKAMT", "DC", "ID", "SUBJECTCODE", "UNCHECKAMT", "OFFSETSTATE", "AMT", "MEMO", "DATATIME"};
        boolean showDictCode = false;
        ConsolidatedOptionVO consolidatedOptionVO = this.optionService.getOptionData(systemId);
        if (consolidatedOptionVO != null) {
            showDictCode = "1".equals(consolidatedOptionVO.getShowDictCode());
        }
        Map<String, Object> retureData = this.getManualCheckInfo(inputDatas, inputDataCheckCondition.getOtherShowColumns());
        BigDecimal totaCheckDebit = BigDecimal.valueOf((Double)retureData.get("totalCheckDebit"));
        BigDecimal totaCheckCredit = BigDecimal.valueOf((Double)retureData.get("totalCheckCredit"));
        ArrayList<InputDataEO> checkDebitItems = new ArrayList<InputDataEO>();
        ArrayList<InputDataEO> checkCreditItems = new ArrayList<InputDataEO>();
        for (InputDataEO inputData : inputDatas) {
            inputData.setCheckAmt(inputData.getUnCheckAmt());
            inputData.addFieldValue("CHECKAMT", inputData.getUnCheckAmt());
            if (inputData.getDc().equals(OrientEnum.D.getValue())) {
                checkDebitItems.add(inputData);
                continue;
            }
            checkCreditItems.add(inputData);
        }
        int manualCheckAmtType = inputDataCheckCondition.getManualCheckAmtType();
        OrientEnum orientEnum = totaCheckDebit.compareTo(totaCheckCredit) > 0 ? OrientEnum.D : OrientEnum.C;
        BigDecimal checkDiffAmt = totaCheckDebit.subtract(totaCheckCredit).abs();
        ArrayList<InputDataEO> returnCheckData = new ArrayList<InputDataEO>();
        if (checkDiffAmt.doubleValue() != 0.0 && !CollectionUtils.isEmpty(checkCreditItems) && !CollectionUtils.isEmpty(checkDebitItems)) {
            switch (manualCheckAmtType) {
                case 1: {
                    InputDataEO inputData;
                    if (OrientEnum.D.equals((Object)orientEnum)) {
                        inputData = (InputDataEO)((Object)checkCreditItems.get(0));
                        inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).add(checkDiffAmt).doubleValue());
                        inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                        break;
                    }
                    inputData = (InputDataEO)((Object)checkDebitItems.get(0));
                    inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).add(checkDiffAmt).doubleValue());
                    inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                    break;
                }
                case 2: {
                    InputDataEO inputData;
                    if (OrientEnum.D.equals((Object)orientEnum)) {
                        inputData = (InputDataEO)((Object)checkDebitItems.get(0));
                        inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).subtract(checkDiffAmt).doubleValue());
                        inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                        break;
                    }
                    inputData = (InputDataEO)((Object)checkCreditItems.get(0));
                    inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).subtract(checkDiffAmt).doubleValue());
                    inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                    break;
                }
                case 3: {
                    InputDataEO inputData;
                    if (totaCheckDebit.compareTo(totaCheckCredit) > 0) {
                        inputData = (InputDataEO)((Object)checkCreditItems.get(0));
                        inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).add(checkDiffAmt).doubleValue());
                        inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                        break;
                    }
                    inputData = (InputDataEO)((Object)checkCreditItems.get(0));
                    inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).subtract(checkDiffAmt).doubleValue());
                    inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                    break;
                }
                case 4: {
                    InputDataEO inputData;
                    if (totaCheckDebit.compareTo(totaCheckCredit) > 0) {
                        inputData = (InputDataEO)((Object)checkDebitItems.get(0));
                        inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).subtract(checkDiffAmt).doubleValue());
                        inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                        break;
                    }
                    inputData = (InputDataEO)((Object)checkDebitItems.get(0));
                    inputData.setCheckAmt(BigDecimal.valueOf(inputData.getUnCheckAmt()).add(checkDiffAmt).doubleValue());
                    inputData.addFieldValue("CHECKAMT", inputData.getCheckAmt());
                }
            }
            returnCheckData.addAll(checkCreditItems);
            returnCheckData.addAll(checkDebitItems);
        } else {
            returnCheckData.addAll(inputDatas);
        }
        List<Map<String, Object>> data = InputDataCheckColumnDataUtils.getCheckTableDataByColumns(returnCheckData, selectColumns, showDictCode, inputDataCheckCondition, false);
        retureData.put("recordData", data);
        return retureData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<InputDataEO> queryInputDataByCheckGroupIds(Collection<String> ids, String tableName) {
        List inputDatas;
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<InputDataEO>();
        }
        String selectFields = SqlUtils.getNewColumnsSqlByTableDefine((String)tableName, (String)"e");
        TempTableCondition condition = SqlUtils.getNewConditionOfIds(ids, (String)"e.CHECKGROUPID");
        try {
            String sql = String.format(" select %1$s  from %2$s e  where %3$s  ", selectFields, tableName, condition.getCondition());
            EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
            inputDatas = dao.selectEntity(sql, new Object[0]);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        }
        if (CollectionUtils.isEmpty((Collection)inputDatas)) {
            return new ArrayList<InputDataEO>();
        }
        return inputDatas;
    }

    @Override
    public void updateInputDataCheckInfos(List<InputDataEO> inputDatas, String tableName) {
        String sql = " update " + tableName + " t \n set CHECKGROUPID=?, \n CHECKSTATE=?,CHECKAMT=?, \n CHECKTYPE=?,CHECKTIME=?,CHECKUSER=?, MEMO=? \n   where t." + "BIZKEYORDER" + " = ? \n";
        List param = inputDatas.stream().map(inputItem -> {
            Object key = inputItem.getFields().get("BIZKEYORDER");
            if (key == null) {
                return null;
            }
            ArrayList<Serializable> argValues = new ArrayList<Serializable>(Arrays.asList(inputItem.getCheckGroupId(), inputItem.getCheckState(), inputItem.getCheckAmt(), inputItem.getCheckType(), inputItem.getCheckTime(), inputItem.getCheckUser(), inputItem.getMemo()));
            argValues.add((Serializable)key);
            return argValues;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.executeBatch(sql, param);
    }

    @Override
    public void cancelLockedCheck(String lockId, String tableName) {
        String sql = "  update " + tableName + "  i \n     set CHECKGROUPID=null,CHECKSTATE='0',CHECKAMT=0,         CHECKTYPE=null,CHECKTIME=?,CHECKUSER=? \n   where i.CHECKGROUPID in \n         (select l.CHECKGROUPID from " + "GC_INPUTDATALOCK" + "  l \n  where l.lockId = ?) \n  and i.CHECKGROUPID is not null ";
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        dao.execute(sql, new Object[]{new Date(), NpContextHolder.getContext().getUserName(), lockId});
    }

    @Override
    public List<InputDataEO> listUnCheckData(InputDataCheckCondition inputDataCheckCondition) {
        Assert.notNull((Object)inputDataCheckCondition.getTaskId(), GcI18nUtil.getMessage((String)"gc.inputdata.check.taskisnullmsg"));
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(inputDataCheckCondition.getTaskId());
        String systemId = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(inputDataCheckCondition.getTaskId(), inputDataCheckCondition.getDataTime());
        if (StringUtils.isEmpty((String)systemId)) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.check.nonesystemid"));
        }
        EntNativeSqlDefaultDao<InputDataEO> dao = this.templateEntDaoCacheService.getTemplateEntDao(tableName, InputDataEO.class);
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuilder checkTableDataSql = this.getUnCheckDataSql(null, null, tableName, inputDataCheckCondition, params, false, InputDataCheckStateEnum.NOTCHECK, systemId);
        List inputDatas = dao.selectEntity(checkTableDataSql.toString(), params);
        return inputDatas;
    }

    private StringBuilder getUnCheckDataSql(String[] selectColumns, List<String> otherShowColumns, String tableName, InputDataCheckCondition inputDataCheckCondition, List<Object> params, boolean isOrderBy, InputDataCheckStateEnum inputDataCheckStateEnum, String systemId) {
        StringBuilder checkTableDataSql = new StringBuilder("Select ");
        String columnSelectSQL = null == selectColumns ? SqlUtils.getColumnsSqlByTableDefine((String)tableName, (String)"record") : SqlUtils.getColumnsSql((String[])this.getSelectColumn(selectColumns, otherShowColumns), (String)"record");
        checkTableDataSql.append(columnSelectSQL);
        checkTableDataSql.append(" from ").append(tableName).append(" record \n");
        String joinSql = InputDataCheckSQLUtils.getQueryCheckTabDataSql(inputDataCheckCondition, params, inputDataCheckStateEnum, systemId);
        checkTableDataSql.append(joinSql);
        InputDataCheckSQLUtils.initOtherCondition(checkTableDataSql, inputDataCheckCondition.getFilterCondition(), systemId, false);
        if (isOrderBy) {
            checkTableDataSql.append(" order by record.unionRuleId,(CASE WHEN record.MDCODE>record.oppunitid THEN concat(record.MDCODE, record.oppunitid) ELSE concat(record.oppunitid, record.MDCODE) END),record.DC desc");
        }
        return checkTableDataSql;
    }

    private List<Map<String, Object>> getDataSortCheckGroupId(List<String> checkGroupIds, List<Map<String, Object>> unSortedRecords) {
        HashMap<String, List> dataGroupByCheckId = new HashMap<String, List>();
        for (Map<String, Object> record : unSortedRecords) {
            String tempMrecid = (String)record.get("CHECKGROUPID");
            List datas = dataGroupByCheckId.computeIfAbsent(tempMrecid, v -> new ArrayList());
            datas.add(record);
        }
        ArrayList<Map<String, Object>> dataSortCheckGroupId = new ArrayList<Map<String, Object>>();
        for (String checkGroupId : checkGroupIds) {
            if (CollectionUtils.isEmpty((Collection)((Collection)dataGroupByCheckId.get(checkGroupId)))) continue;
            dataSortCheckGroupId.addAll((Collection)dataGroupByCheckId.get(checkGroupId));
        }
        return dataSortCheckGroupId;
    }

    private Map<String, Object> getManualCheckInfo(List<InputDataEO> inputDatas, List<String> columns) {
        HashMap<String, Object> returnData = new HashMap<String, Object>();
        BigDecimal totalCheckDebit = BigDecimal.valueOf(0L);
        BigDecimal totalCheckCredit = BigDecimal.valueOf(0L);
        ArrayList<String> ids = new ArrayList<String>();
        ArrayList recordDatas = new ArrayList();
        for (InputDataEO inputData : inputDatas) {
            BigDecimal unCheckAmt;
            HashMap<String, Object> recordData = new HashMap<String, Object>();
            ids.add(inputData.getId());
            Integer dc = inputData.getDc();
            if (null == dc) {
                dc = OrientEnum.D.getValue();
            }
            BigDecimal bigDecimal = unCheckAmt = Objects.isNull(inputData.getUnCheckAmt()) ? BigDecimal.ZERO : BigDecimal.valueOf(inputData.getUnCheckAmt());
            if (dc == OrientEnum.D.getValue()) {
                totalCheckDebit = totalCheckDebit.add(unCheckAmt);
            } else {
                totalCheckCredit = totalCheckCredit.add(unCheckAmt);
            }
            for (String column : columns) {
                recordData.put(column, inputData.getFieldValue(column));
            }
            recordDatas.add(recordData);
        }
        BigDecimal checkAmt = totalCheckCredit;
        if (totalCheckDebit.doubleValue() > totalCheckCredit.doubleValue()) {
            checkAmt = totalCheckDebit;
        }
        returnData.put("totalCheckDebit", totalCheckDebit.doubleValue());
        returnData.put("totalCheckCredit", totalCheckCredit.doubleValue());
        returnData.put("checkAmt", checkAmt.doubleValue());
        returnData.put("recordIds", ids);
        return returnData;
    }

    private String[] getSelectColumn(String[] selectColumns, List<String> otherShowColumns) {
        Set<String> selectCodes = Arrays.stream(selectColumns).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(otherShowColumns)) {
            selectCodes.addAll(otherShowColumns);
        }
        return selectCodes.toArray(new String[0]);
    }

    private Pagination<Map<String, Object>> getCheckInputData(EntNativeSqlDefaultDao<InputDataEO> dao, String sql, List<Object> params, InputDataCheckCondition inputDataCheckCondition, boolean showDictCode, String[] selectColumns) {
        int pageNum = inputDataCheckCondition.getPageNum();
        int pageSize = inputDataCheckCondition.getPageSize();
        int totalElements = 0;
        List<Object> inputDataItems = new ArrayList();
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        if (!this.isCheckDiffAmtFilter(inputDataCheckCondition.getFilterCondition())) {
            totalElements = dao.count(sql, params);
            if (totalElements < 1) {
                return page;
            }
            List inputDatas = dao.selectEntityByPaging(sql.toString(), (pageNum - 1) * pageSize, pageNum * pageSize, params);
            List<Map<String, Object>> data = InputDataCheckColumnDataUtils.getCheckTableDataByColumns(inputDatas, selectColumns, showDictCode, inputDataCheckCondition, false);
            inputDataItems = InputDataCheckComparatorUtil.setRowSpanAndSort(data, inputDataCheckCondition.getFilterCondition());
        } else {
            List totalData = dao.selectEntity(sql.toString(), params);
            if (totalData.size() < 1) {
                return page;
            }
            List<Map<String, Object>> allData = InputDataCheckColumnDataUtils.getCheckTableDataByColumns(totalData, selectColumns, showDictCode, inputDataCheckCondition, false);
            List<Map<String, Object>> totalCountData = InputDataCheckComparatorUtil.setRowSpanAndSort(allData, inputDataCheckCondition.getFilterCondition());
            if (totalCountData.size() < 1) {
                return page;
            }
            totalElements = totalCountData.size();
            inputDataItems = totalCountData;
            if (inputDataCheckCondition.getPageNum() != -1) {
                inputDataItems = totalCountData.stream().skip((inputDataCheckCondition.getPageNum() - 1) * inputDataCheckCondition.getPageSize()).limit(inputDataCheckCondition.getPageSize().intValue()).collect(Collectors.toList());
            }
        }
        page.setTotalElements(Integer.valueOf(totalElements));
        page.setContent(inputDataItems);
        page.setPageSize(Integer.valueOf(pageSize));
        page.setCurrentPage(Integer.valueOf(pageNum));
        return page;
    }

    private boolean isCheckDiffAmtFilter(Map<String, Object> filterCondition) {
        if (Objects.isNull(filterCondition) || filterCondition.isEmpty() || !filterCondition.keySet().contains("checkDiffAmtInterval")) {
            return false;
        }
        List checkDiffAmtFilterCondition = (List)filterCondition.get("checkDiffAmtInterval");
        if (CollectionUtils.isEmpty((Collection)checkDiffAmtFilterCondition)) {
            return false;
        }
        List values = checkDiffAmtFilterCondition.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(values);
    }
}

