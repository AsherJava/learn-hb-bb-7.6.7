/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao.impl;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckcore.check.dto.BalanceCondition;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.check.enums.ReconciliationModeEnum;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.ReltxSqlUtils;
import com.jiuqi.gcreport.financialcheckcore.utils.PeriodUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class GcRelatedItemDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemEO>
implements GcRelatedItemDao {
    @Autowired
    private DimensionService dimensionService;
    private static final String UPDATECHECKINFOSQL = "  update GC_RELATED_ITEM item \n     set chkCurr=?,chkState=?,checkId=?,checkTime=?,         checkYear=?,checkPeriod=?,checker=?,checkType=?,         chkAmtD=?,chkAmtc=?,recordTimestamp=?, CHECKMODE=? \n   where item.id = ? \n       and item.chkState = ? \n       and item.recordTimestamp = ? \n";
    private static final String QUERYVCHRITEMBYIDSSQL = "  select %s \n    from GC_RELATED_ITEM  item \n   where %s \n";
    private static final String UPDATEGCNUMBERSQL = "  update GC_RELATED_ITEM item \n     set GCNUMBER = 'SystemDefault', recordTimestamp = ?   where item.SRCITEMID =  ?  and item.recordTimestamp = ? ";
    private static final String UPDATECHECKSCHEMEINFOSQL = "  update GC_RELATED_ITEM item \n     set checkAttribute = ?, checkRuleId=?,checkProject=?,checkProjectDirection=?,businessRole=?,unitCombine=?,         recordTimestamp=? \n   where item.id = ? \n       and item.recordTimestamp = ? \n";
    private static final String UPDATECHECKINFOANDSCHEMEINFOSQL = "  update GC_RELATED_ITEM item \n     set checkAttribute = ?, checkRuleId=?,checkProject=?,checkProjectDirection=?,businessRole=?,unitCombine=?,\t\t\tchkCurr=?,chkState=?,checkId=?,checkTime=?,         checkYear=?,checkPeriod=?,checker=?,checkType=?,         chkAmtD=?,chkAmtc=?,recordTimestamp=?, CHECKMODE=? \n   where item.id = ? \n       and item.chkState = ? \n       and item.recordTimestamp = ? \n";

    public GcRelatedItemDaoImpl() {
        super(GcRelatedItemEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATED_ITEM");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> findByCheckIds(Collection<String> checkIds) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(checkIds, "item.checkId");
        try {
            String sql = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n    from " + "GC_RELATED_ITEM" + "  item \n   where " + tempTableCondition.getCondition() + " \n";
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public List<GcRelatedItemEO> findByCheckIdAndCheckPeriod(String checkId, Integer checkPeriod) {
        String sql = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n    from " + "GC_RELATED_ITEM" + "  item \n   where item.checkId = ? and CHECKPERIOD >= ?  \n";
        return this.selectEntity(sql, new Object[]{checkId, checkPeriod});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> findByCheckSchemeIds(Collection<String> checkSchemeIds) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(checkSchemeIds, "item.checkRuleId");
        try {
            String sql = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n    from " + "GC_RELATED_ITEM" + "  item \n   where " + tempTableCondition.getCondition() + " \n";
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public void deleteByIds(List<String> ids) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "id");
        try {
            String sql = "  delete from GC_RELATED_ITEM   where " + tempTableCondition.getCondition() + " \n";
            this.execute(sql);
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public long updateCheckInfo(List<GcRelatedItemEO> vchrItems, String dbCheckState) {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(vchrItems)) {
            return recordTimestamp;
        }
        List param = vchrItems.stream().map(v -> Arrays.asList(v.getChkCurr(), v.getChkState(), v.getCheckId(), v.getCheckTime(), v.getCheckYear(), v.getCheckPeriod(), v.getChecker(), v.getCheckType(), v.getChkAmtD(), v.getChkAmtC(), recordTimestamp, v.getCheckMode(), v.getId(), dbCheckState, v.getRecordTimestamp())).collect(Collectors.toList());
        this.executeBatch(UPDATECHECKINFOSQL, param);
        return recordTimestamp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public Set<String> countByIdAndRecordTimestamp(Collection<String> ids, long recordTimestamp) {
        String sql = "  select i.id  ID from GC_RELATED_ITEM  i \n   where i.recordTimestamp = ? \n     and %s \n";
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "i.id");
        try {
            Set<String> matchIds;
            List rs = this.selectFirstList(String.class, String.format("  select i.id  ID from GC_RELATED_ITEM  i \n   where i.recordTimestamp = ? \n     and %s \n", tempTableCondition.getCondition()), new Object[]{recordTimestamp});
            Set<String> set = matchIds = rs.stream().collect(Collectors.toSet());
            return set;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> queryByIds(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(ids, "item.id");
        try {
            String sql = String.format(QUERYVCHRITEMBYIDSSQL, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), tempTableCondition.getCondition());
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public String queryLastUpdateTime(int year) {
        String sqlTemplate = "select MAX(UPDATETIME) from GC_RELATED_ITEM where ACCTYEAR = ?";
        return (String)this.selectFirst(String.class, sqlTemplate, new Object[]{year});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> queryUncheckedItemByUnitsAndYear(Set<String> units, List<String> oppUnitIds, int year) {
        String sqlTemplate = "  select %s \n    from GC_RELATED_ITEM  item \n   where %s \n     and item.chkState = '" + (Object)((Object)CheckStateEnum.UNCHECKED) + "' \n     and item.acctYear = ? \n";
        TempTableCondition unitCondition = ReltxSqlUtils.getConditionOfMulStr(units, "item.UNITID");
        TempTableCondition localUnitCondition = ReltxSqlUtils.getConditionOfMulStr(oppUnitIds, "item.OPPUNITID");
        TempTableCondition oppUnitCondition = ReltxSqlUtils.getConditionOfMulStr(units, "item.OPPUNITID");
        TempTableCondition localOppUnitCondition = ReltxSqlUtils.getConditionOfMulStr(oppUnitIds, "item.UNITID");
        try {
            String localUnitSql = String.format(sqlTemplate, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), unitCondition.getCondition() + " and " + localUnitCondition.getCondition());
            String oppUnitSql = String.format(sqlTemplate, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), oppUnitCondition.getCondition() + " and " + localOppUnitCondition.getCondition());
            String sql = localUnitSql + "union" + oppUnitSql;
            List list = this.selectEntity(sql, new Object[]{year, year});
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(unitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(oppUnitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(localUnitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(localOppUnitCondition.getTempGroupId());
        }
    }

    @Override
    public long deleteGcNumber(List<GcRelatedItemEO> items) {
        long recordTimestamp = Instant.now().toEpochMilli();
        List param = items.stream().map(v -> Arrays.asList(recordTimestamp, v.getSrcItemId(), v.getRecordTimestamp())).collect(Collectors.toList());
        this.executeBatch(UPDATEGCNUMBERSQL, param);
        return recordTimestamp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> queryByOffsetCondition(BalanceCondition queryCondition) {
        List vchrBalanceEOS;
        String orgType = queryCondition.getOrgType();
        boolean isItemWay = ReconciliationModeEnum.ITEM.getCode().equals(queryCondition.getCheckWay());
        int month = PeriodUtils.getMonth(queryCondition.getAcctYear(), queryCondition.getPeriodType(), queryCondition.getAcctPeriod());
        String chkState = queryCondition.isChecked() ? CheckStateEnum.CHECKED.name() : CheckStateEnum.UNCHECKED.name();
        String sqlTemplate = "  select %2$s \n    from GC_RELATED_ITEM  vb \n    join " + orgType + "  unit on vb.unitId = unit.code and unit.validTime <= ? and unit.invalidTime >= ? \n    join " + orgType + "  oppunit on vb.oppUnitId = oppunit.code and oppunit.validTime <= ? and oppunit.invalidTime >= ? \n   where unit.gcparents like ?  \n     and oppunit.gcparents like ? \n     and substr(unit.gcparents, %1$s, %3$s) <> substr(oppunit.gcparents, %1$s, %3$s) \n     and vb.acctYear = ? and vb.acctPeriod " + (isItemWay ? "<=" : "=") + " ?\n     and vb.chkState = '" + chkState + "' \n";
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(queryCondition.getMergeOrg());
        String mergeOrgParents = gcOrg.getGcParentStr();
        int mergeOrgChildBeginIndex = gcOrg.getGcParentStr().length() + 2;
        String sql = String.format(sqlTemplate, mergeOrgChildBeginIndex, SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"vb"), tool.getOrgCodeLength());
        String tempGroupId = "";
        Set<String> boundSubjects = queryCondition.getBoundSubjects();
        Date versionDate = yp.formatYP().getEndDate();
        try {
            if (!CollectionUtils.isEmpty(boundSubjects)) {
                TempTableCondition conditionOfMulStr = ReltxSqlUtils.getConditionOfMulStr(boundSubjects, "vb.subjectCode");
                sql = sql + "and " + conditionOfMulStr.getCondition();
                tempGroupId = conditionOfMulStr.getTempGroupId();
            }
            vchrBalanceEOS = this.selectEntity(sql, new Object[]{versionDate, versionDate, versionDate, versionDate, mergeOrgParents + "%", mergeOrgParents + "%", queryCondition.getAcctYear(), month});
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempGroupId);
        }
        return vchrBalanceEOS;
    }

    @Override
    public List<GcRelatedItemEO> queryNeedCancelClbrItems(String batchId) {
        String sqlTemplate = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n from " + "GC_RELATED_ITEM" + " item \n where 1=1  and exists(select 1 from  GC_CLBR_VOUCHERITEM_TEMP  temp where temp.batchId = ?  and temp.oppUnitId = item.oppUnitId  and temp.gcNumber = item.gcNumber  and temp.billCode = item.billCode  ) ";
        return this.selectEntity(sqlTemplate, new Object[]{batchId});
    }

    @Override
    public List<GcRelatedItemEO> listExistRelatedItemsByBatchId(String batchId, boolean isItemWay) {
        String joinCondi = isItemWay ? " and temp.SRCITEMASSID = item.SRCITEMASSID  and temp.UNITID = item.UNITID  and temp.RULECHANGEHANDLERFLAG = item.RULECHANGEHANDLERFLAG " : " and temp.ITEMID = item.srcItemId ";
        String sqlTemplate = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n from " + "GC_RELATED_ITEM" + " item \n where 1=1  and exists(select 1 from " + "GC_RELATEDITEM_TEMPORARY" + " temp where temp.batchId = ? " + joinCondi + " ) ";
        String condition = "";
        sqlTemplate = String.format(sqlTemplate, condition);
        return this.selectEntity(sqlTemplate, new Object[]{batchId});
    }

    @Override
    public long updateCheckSchemeInfo(List<GcRelatedItemEO> items) {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(items)) {
            return recordTimestamp;
        }
        List param = items.stream().map(v -> Arrays.asList(v.getCheckAttribute(), v.getCheckRuleId(), v.getCheckProject(), v.getCheckProjectDirection(), v.getBusinessRole(), v.getUnitCombine(), recordTimestamp, v.getId(), v.getRecordTimestamp())).collect(Collectors.toList());
        this.executeBatch(UPDATECHECKSCHEMEINFOSQL, param);
        return recordTimestamp;
    }

    @Override
    public List<GcRelatedItemEO> listNoSchemeIdByAcctYear(Integer acctYear) {
        String columnsSql = SqlUtils.getColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"");
        String sql = " select " + columnsSql + " from " + "GC_RELATED_ITEM" + " where CHECKRULEID is null and ACCTYEAR = ? ";
        return this.selectEntity(sql, new Object[]{acctYear});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> listBySchemeIdAndCondition(String schemeId, Integer year, Integer period, List<String> subjects) {
        TempTableCondition tempTableCondition = ReltxSqlUtils.getConditionOfMulStr(subjects, "item.SUBJECTCODE");
        try {
            String sql = "  select " + SqlUtils.getNewColumnsSqlByEntity(GcRelatedItemEO.class, (String)"item") + " \n    from " + "GC_RELATED_ITEM" + "  item \n   where " + tempTableCondition.getCondition() + " \n and CHECKRULEID = ? and ACCTYEAR = ? and ACCTPERIOD = ?\n";
            List list = this.selectEntity(sql, new Object[]{schemeId, year, period});
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public long updateItemBaseInfo(List<GcRelatedItemEO> items) {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(items)) {
            return recordTimestamp;
        }
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_RELATED_ITEM");
        List<String> dimensionCodes = dimensions.stream().map(DimensionEO::getCode).collect(Collectors.toList());
        StringBuilder sqlBuilder = new StringBuilder("  update ");
        sqlBuilder.append("GC_RELATED_ITEM").append(" item set \n").append(" ACCTPERIOD=?,\n").append(" ACCTYEAR=?,\n").append(" BILLCODE=?,\n").append(" CREATEDATE=?,\n").append(" CREDIT=?,\n").append(" DEBIT=?,\n").append(" CREDITORIG=?,\n").append(" DEBITORIG=?,\n").append(" DIGEST=?,\n").append(" GCNUMBER=?,\n").append(" ITEMORDER=?,\n").append(" OPPUNITID=?,\n").append(" MEMO=?,\n").append(" ORIGINALCURR=?,\n").append(" SUBJECTCODE=?,\n").append(" UNITID=?,\n").append(" UPDATETIME=?,\n").append(" VCHRNUM=?,\n").append(" VCHRSOURCETYPE=?,\n").append(" VCHRTYPE=?,\n").append(" CURRENCY=?,\n").append(" REALGCNUMBER=?,\n").append(" SRCVCHRID=?,\n").append(" CFITEMCODE=?,\n").append(" VCHRID=?,\n").append(" RULECHANGEHANDLERFLAG=?,\n").append(" RECLASSIFYSUBJCODE=?,\n");
        if (!CollectionUtils.isEmpty(dimensionCodes)) {
            dimensionCodes.forEach(dim -> sqlBuilder.append((String)dim).append("=?, \n"));
        }
        sqlBuilder.append(" recordTimestamp=? \n").append(" where item.id = ? \n").append(" and item.recordTimestamp = ? \n ");
        List param = items.stream().map(v -> {
            ArrayList<Serializable> objects = new ArrayList<Serializable>(Arrays.asList(v.getAcctPeriod(), v.getAcctYear(), v.getBillCode(), v.getCreateDate(), v.getCredit(), v.getDebit(), v.getCreditOrig(), v.getDebitOrig(), v.getDigest(), v.getGcNumber(), v.getItemOrder(), v.getOppUnitId(), v.getMemo(), v.getOriginalCurr(), v.getSubjectCode(), v.getUnitId(), v.getUpdateTime(), v.getVchrNum(), v.getVchrSourceType(), v.getVchrType(), v.getCurrency(), v.getRealGcNumber(), v.getSrcVchrId(), v.getCfItemCode(), v.getVchrId(), v.getRuleChangeHandlerFlag(), v.getReclassifySubjCode()));
            if (!CollectionUtils.isEmpty(dimensionCodes)) {
                dimensionCodes.forEach(dim -> objects.add((Serializable)v.getFieldValue((String)dim)));
            }
            objects.add(Long.valueOf(recordTimestamp));
            objects.add((Serializable)((Object)v.getId()));
            objects.add(v.getRecordTimestamp());
            return objects;
        }).collect(Collectors.toList());
        this.executeBatch(sqlBuilder.toString(), param);
        return recordTimestamp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @OuterTransaction
    public List<GcRelatedItemEO> queryUncheckedItemByUnitAndDataTime(Set<String> units, List<String> oppUnitIds, String dataTime, String checkWay, Set<String> localSchemes) {
        boolean isItemWay = ReconciliationModeEnum.ITEM.getCode().equals(checkWay);
        Integer year = ConverterUtils.getAsInteger((Object)dataTime.substring(0, 4));
        Integer period = ConverterUtils.getAsInteger((Object)dataTime.substring(7));
        String sqlTemplate = "  select %s \n    from GC_RELATED_ITEM  item \n   where %s \n     and item.chkState = '" + (Object)((Object)CheckStateEnum.UNCHECKED) + "' \n and %s  \n and item.acctYear = ?  %s \n";
        TempTableCondition unitCondition = ReltxSqlUtils.getConditionOfMulStr(units, "item.UNITID");
        TempTableCondition localUnitCondition = ReltxSqlUtils.getConditionOfMulStr(oppUnitIds, "item.OPPUNITID");
        TempTableCondition oppUnitCondition = ReltxSqlUtils.getConditionOfMulStr(units, "item.OPPUNITID");
        TempTableCondition localOppUnitCondition = ReltxSqlUtils.getConditionOfMulStr(oppUnitIds, "item.UNITID");
        TempTableCondition schemeCondition = ReltxSqlUtils.getConditionOfMulStr(localSchemes, "item.CHECKRULEID");
        String periodCondition = isItemWay ? "" : " and ACCTPERIOD = " + period;
        try {
            String localUnitSql = String.format(sqlTemplate, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), unitCondition.getCondition() + " and " + localUnitCondition.getCondition(), schemeCondition.getCondition(), periodCondition);
            String oppUnitSql = String.format(sqlTemplate, SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), oppUnitCondition.getCondition() + " and " + localOppUnitCondition.getCondition(), schemeCondition.getCondition(), periodCondition);
            String sql = localUnitSql + "union" + oppUnitSql;
            List list = this.selectEntity(sql, new Object[]{year, year});
            return list;
        }
        finally {
            ReltxSqlUtils.deteteByGroupId(unitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(oppUnitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(localUnitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(localOppUnitCondition.getTempGroupId());
            ReltxSqlUtils.deteteByGroupId(schemeCondition.getTempGroupId());
        }
    }

    @Override
    public long updateCheckInfoAndScheme(List<GcRelatedItemEO> items, String dbCheckState) {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(items)) {
            return recordTimestamp;
        }
        List param = items.stream().map(v -> Arrays.asList(v.getCheckAttribute(), v.getCheckRuleId(), v.getCheckProject(), v.getCheckProjectDirection(), v.getBusinessRole(), v.getUnitCombine(), v.getChkCurr(), v.getChkState(), v.getCheckId(), v.getCheckTime(), v.getCheckYear(), v.getCheckPeriod(), v.getChecker(), v.getCheckType(), v.getChkAmtD(), v.getChkAmtC(), recordTimestamp, v.getCheckMode(), v.getId(), dbCheckState, v.getRecordTimestamp())).collect(Collectors.toList());
        this.executeBatch(UPDATECHECKINFOANDSCHEMEINFOSQL, param);
        return recordTimestamp;
    }

    @Override
    public List<GcRelatedItemEO> queryByGcNumberAndUnit(String gcNumber, String unitId, String oppUnitId, Integer acctYear) {
        String sqlTemplate = "  select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n   and item.UNITID = ? \n   and item.OPPUNITID = ? \n   and item.ACCTYEAR = ? \n\tunion   select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n   and item.UNITID = ? \n   and item.OPPUNITID = ? \n   and item.ACCTYEAR = ? \n";
        String selectSql = String.format("  select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n   and item.UNITID = ? \n   and item.OPPUNITID = ? \n   and item.ACCTYEAR = ? \n\tunion   select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n   and item.UNITID = ? \n   and item.OPPUNITID = ? \n   and item.ACCTYEAR = ? \n", SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"), SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"));
        return this.selectEntity(selectSql, new Object[]{gcNumber, unitId, oppUnitId, acctYear, gcNumber, oppUnitId, unitId, acctYear});
    }

    @Override
    public List<GcRelatedItemEO> queryByGcNumber(String gcNumber) {
        String sqlTemplate = "  select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n";
        String selectSql = String.format("  select %s \n    from GC_RELATED_ITEM  item \n   where item.gcNumber = ? \n", SqlUtils.getNewColumnsSqlByTableDefine((String)"GC_RELATED_ITEM", (String)"item"));
        return this.selectEntity(selectSql, new Object[]{gcNumber});
    }
}

