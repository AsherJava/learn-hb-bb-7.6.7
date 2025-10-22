/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.internal.entity.DimensionEO
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum
 *  com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  org.springframework.dao.DataAccessException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.internal.entity.DimensionEO;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.financialcheckImpl.offset.dataquery.dto.RelationToMergeArgDTO;
import com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.dao.OffsetRelatedItemDao;
import com.jiuqi.gcreport.financialcheckcore.check.enums.CheckStateEnum;
import com.jiuqi.gcreport.financialcheckcore.offset.entity.GcOffsetRelatedItemEO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OffsetRelatedItemDaoImpl
extends GcDbSqlGenericDAO<GcOffsetRelatedItemEO, String>
implements OffsetRelatedItemDao {
    @Autowired
    private DimensionService dimensionService;

    public OffsetRelatedItemDaoImpl() {
        super(GcOffsetRelatedItemEO.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchDeleteByOffsetGroupId(Collection<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        groupIds.remove(null);
        groupIds.remove("");
        String tempGroupId = "";
        try {
            String sql = "\tdelete from GC_OFFSETRELATEDITEM   where 1=1 ";
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(groupIds, (String)"OFFSETGROUPID", (boolean)false);
            sql = sql + "and " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            EntNativeSqlDefaultDao.getInstance().execute(sql);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchClearOffsetGroupId(Collection<String> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        groupIds.remove(null);
        groupIds.remove("");
        String tempGroupId = "";
        try {
            String sql = "\tupdate GC_OFFSETRELATEDITEM set offsetGroupId = null, RTOFFSETCANDEL = ?  where 1=1 ";
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(groupIds, (String)"OFFSETGROUPID", (boolean)false);
            sql = sql + "and " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{0});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> filterByUnChecked(Collection<String> originOffsetGroupIds) {
        if (CollectionUtils.isEmpty(originOffsetGroupIds)) {
            return Collections.EMPTY_SET;
        }
        String tempGroupId = "";
        try {
            Set<String> offsetGroupIds;
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(originOffsetGroupIds, (String)"OFFSETGROUPID", (boolean)false);
            String sql = "\tselect offsetGroupId from GC_OFFSETRELATEDITEM   where checkState='UNCHECKED' and " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            List rs = this.selectFirstList(String.class, sql, new Object[0]);
            Set<String> set = offsetGroupIds = rs.stream().collect(Collectors.toSet());
            return set;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffsetRelatedItemEO> listByRelatedId(Collection<String> relatedItemId) {
        if (CollectionUtils.isEmpty(relatedItemId)) {
            return Collections.EMPTY_LIST;
        }
        String tempGroupId = "";
        try {
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(relatedItemId, (String)"relatedItemId", (boolean)false);
            String sql = "\tselect " + SqlUtils.getColumnsSqlByEntity(GcOffsetRelatedItemEO.class, (String)"e") + " from " + "GC_OFFSETRELATEDITEM" + " e  where 1=1 and  " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffsetRelatedItemEO> listById(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }
        String tempGroupId = "";
        try {
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(ids, (String)"id", (boolean)false);
            String sql = "\tselect " + SqlUtils.getColumnsSqlByEntity(GcOffsetRelatedItemEO.class, (String)"e") + " from " + "GC_OFFSETRELATEDITEM" + " e  where " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffsetRelatedItemEO> listByOffsetGroupId(Collection<String> groupIdSet) {
        if (CollectionUtils.isEmpty(groupIdSet)) {
            return Collections.EMPTY_LIST;
        }
        groupIdSet.remove(null);
        groupIdSet.remove("");
        String tempGroupId = "";
        try {
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(groupIdSet, (String)"offsetGroupId", (boolean)false);
            String sql = "\tselect " + SqlUtils.getColumnsSqlByEntity(GcOffsetRelatedItemEO.class, (String)"e") + " from " + "GC_OFFSETRELATEDITEM" + " e where " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int[] mergeOffsetGroupId(List<GcOffsetRelatedItemEO> items) throws DataAccessException {
        if (CollectionUtils.isEmpty(items)) {
            return new int[0];
        }
        ArrayList<GcOffsetRelatedItemEO> needInsertItems = new ArrayList<GcOffsetRelatedItemEO>();
        for (GcOffsetRelatedItemEO item : items) {
            String sql = "\tupdate GC_OFFSETRELATEDITEM set  offsetGroupId=? , RTOFFSETCANDEL = ? , unionruleid = ? where relatedItemId=? and dataTime=? and systemId=?";
            int num = EntNativeSqlDefaultDao.getInstance().execute(sql, new Object[]{item.getOffsetGroupId(), item.getRtOffsetCanDel(), item.getUnionRuleId(), item.getRelatedItemId(), item.getDataTime(), item.getSystemId()});
            if (num < 1) {
                needInsertItems.add(item);
            }
            if (num <= 1) continue;
            throw new BusinessRuntimeException("GC_RELATEDITEMOFFSETREL\u8868\u6570\u636e\u5f02\u5e38\u3002\u9700\u4fee\u590d\u6570\u636e\u5efa\u7acb\u552f\u4e00\u7d22\u5f15");
        }
        return super.addBatch(needInsertItems);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public long updateItemRuleInfo(List<GcOffsetRelatedItemEO> items) throws DataAccessException {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(items)) {
            return recordTimestamp;
        }
        StringBuilder sqlBuilder = new StringBuilder("  update ");
        sqlBuilder.append("GC_OFFSETRELATEDITEM").append(" item set \n").append(" unionRuleId=? ,\n").append(" recordTimestamp=?\n").append(" where id = ? and recordTimestamp = ?");
        List param = items.stream().map(v -> {
            ArrayList<String> objects = new ArrayList<String>(Arrays.asList(v.getUnionRuleId()));
            objects.add((String)((Object)Long.valueOf(recordTimestamp)));
            objects.add(v.getId());
            objects.add((String)((Object)v.getRecordTimestamp()));
            return objects;
        }).collect(Collectors.toList());
        this.executeBatch(sqlBuilder.toString(), param);
        return recordTimestamp;
    }

    @Override
    public long updateOffsetRelatedItemInfo(List<GcOffsetRelatedItemEO> items) {
        long recordTimestamp = Instant.now().toEpochMilli();
        if (CollectionUtils.isEmpty(items)) {
            return recordTimestamp;
        }
        List dimensions = this.dimensionService.findDimFieldsByTableName("GC_OFFSETRELATEDITEM");
        List<String> dimensionCodes = dimensions.stream().map(DimensionEO::getCode).collect(Collectors.toList());
        StringBuilder sqlBuilder = new StringBuilder("  update ");
        sqlBuilder.append("GC_OFFSETRELATEDITEM").append(" item set \n").append(" RELATEDITEMID=?,\n").append(" CHECKSTATE=?,\n").append(" UNIONRULEID=?,\n").append(" SYSTEMID=?,\n").append(" DATATIME=?,\n").append(" GCUNITID=?,\n").append(" GCOPPUNITID=?,\n").append(" GCSUBJECTCODE=?,\n").append(" MATCHINGINFORMATION=?,\n").append(" SUBJECTCODE=?,\n").append(" UNITID=?,\n").append(" OPPUNITID=?,\n").append(" ORIGINALCURR=?,\n").append(" CREDITORIG=?,\n").append(" DEBITORIG=?,\n").append(" CREDIT=?,\n").append(" DEBIT=?,\n").append(" MEMO=?,\n").append(" SRCTIMESTAMP=?,\n").append(" CHECKID=?,\n").append(" CHECKMODE=?,\n").append(" CHECKTYPE=?,\n").append(" CHECKYEAR=?,\n").append(" CHECKPERIOD=?,\n").append(" CHECKRULEID=?,\n").append(" CHECKTIME=?,\n").append(" CHECKER=?,\n").append(" CHKCURR=?,\n").append(" CHKAMTD=?,\n").append(" CHKAMTC=?,\n").append(" CREDITCONVERSIONVALUE=?,\n").append(" DEBITCONVERSIONVALUE=?,\n").append(" CONVERSIONCURR=?,\n").append(" AMT=?,\n").append(" CURRENCY=?,\n").append(" CONVERSIONRATE=?,\n");
        if (!org.springframework.util.CollectionUtils.isEmpty(dimensionCodes)) {
            dimensionCodes.forEach(dim -> sqlBuilder.append((String)dim).append("=?, \n"));
        }
        sqlBuilder.append(" recordTimestamp=? \n").append(" where item.id = ? \n");
        List param = items.stream().map(v -> {
            ArrayList<Serializable> objects = new ArrayList<Serializable>(Arrays.asList(v.getRelatedItemId(), v.getCheckState(), v.getUnionRuleId(), v.getSystemId(), v.getDataTime(), v.getGcUnitId(), v.getGcOppUnitId(), v.getGcSubjectCode(), v.getMatchingInformation(), v.getSubjectCode(), v.getUnitId(), v.getOppUnitId(), v.getOriginalCurr(), v.getCreditOrig(), v.getDebitOrig(), v.getCredit(), v.getDebit(), v.getMemo(), v.getSrcTimestamp(), v.getCheckId(), v.getCheckMode(), v.getCheckType(), v.getCheckYear(), v.getCheckPeriod(), v.getCheckRuleId(), v.getCheckTime(), v.getChecker(), v.getChkCurr(), v.getChkAmtD(), v.getChkAmtC(), v.getCreditConversionValue(), v.getDebitConversionValue(), v.getConversionCurr(), v.getAmt(), v.getCurrency(), v.getConversionRate()));
            if (!org.springframework.util.CollectionUtils.isEmpty(dimensionCodes)) {
                dimensionCodes.forEach(dim -> objects.add((Serializable)v.getFieldValue(dim)));
            }
            objects.add(Long.valueOf(recordTimestamp));
            objects.add((Serializable)((Object)v.getId()));
            return objects;
        }).collect(Collectors.toList());
        this.executeBatch(sqlBuilder.toString(), param);
        return recordTimestamp;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchDeleteByRelatedItemId(Collection<String> itemIds) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return;
        }
        itemIds.remove(null);
        itemIds.remove("");
        String tempGroupId = "";
        try {
            String sql = "\tdelete from GC_OFFSETRELATEDITEM   where 1=1 ";
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(itemIds, (String)"RELATEDITEMID", (boolean)false);
            sql = sql + "and " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            EntNativeSqlDefaultDao.getInstance().execute(sql);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffsetRelatedItemEO> listByOffsetCondition(RelationToMergeArgDTO queryCondition) {
        List vchrBalanceEOS;
        String orgType = queryCondition.getOrgType();
        String chkState = queryCondition.isChecked() ? CheckStateEnum.CHECKED.name() : CheckStateEnum.UNCHECKED.name();
        String sqlTemplate = "  select %2$s \n    from GC_OFFSETRELATEDITEM  vb \n    join " + orgType + "  unit on vb.gcUnitId = unit.code and unit.validTime <= ? and unit.invalidTime >= ? \n    join " + orgType + "  oppunit on vb.gcOppUnitId = oppunit.code and oppunit.validTime <= ? and oppunit.invalidTime >= ? \n   where unit.gcparents like ?  \n     and oppunit.gcparents like ? \n     and substr(to_char(unit.gcparents), %1$s, %3$s) <> substr(to_char(oppunit.gcparents), %1$s, %3$s) \n     and vb.dataTime = ? \n     and vb.systemId = ? \n     and vb.checkState = ? \n     and vb.CONVERSIONCURR = ? \n     and (vb. offsetGroupId is null) \n";
        YearPeriodObject yp = new YearPeriodObject(null, queryCondition.getPeriodScheme());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO gcOrg = tool.getOrgByCode(queryCondition.getOrgCode());
        String mergeOrgParents = gcOrg.getGcParentStr();
        int mergeOrgChildBeginIndex = gcOrg.getGcParentStr().length() + 2;
        String sql = String.format(sqlTemplate, mergeOrgChildBeginIndex, SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETRELATEDITEM", (String)"vb"), tool.getOrgCodeLength());
        String tempGroupId = "";
        Set<String> boundSubjects = queryCondition.getBoundSubjects();
        Date versionDate = yp.formatYP().getEndDate();
        try {
            if (!CollectionUtils.isEmpty(boundSubjects)) {
                TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(boundSubjects, (String)"vb.subjectCode", (boolean)false);
                sql = sql + "and " + conditionOfMulStr.getCondition();
                tempGroupId = conditionOfMulStr.getTempGroupId();
            }
            vchrBalanceEOS = this.selectEntity(sql, new Object[]{versionDate, versionDate, versionDate, versionDate, mergeOrgParents + "%", mergeOrgParents + "%", queryCondition.getPeriodScheme(), queryCondition.getSystemId(), chkState, queryCondition.getCurrency()});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
        return vchrBalanceEOS;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> listOffsetDataRelatedIds(Collection<String> itemIds, String systemId, String currency, String periodStr) {
        if (CollectionUtils.isEmpty(itemIds)) {
            return Collections.EMPTY_SET;
        }
        String tempGroupId = "";
        try {
            TempTableCondition conditionOfMulStr = SqlUtils.getConditionOfMulStr(itemIds, (String)"relatedItemId", (boolean)false);
            String sql = "\tselect  relatedItemId from GC_OFFSETRELATEDITEM e  where 1=1 and SYSTEMID = ? and DATATIME = ? and CONVERSIONCURR = ? and OFFSETGROUPID is not null and  " + conditionOfMulStr.getCondition();
            tempGroupId = conditionOfMulStr.getTempGroupId();
            List ids = this.selectFirstList(String.class, sql, new Object[]{systemId, periodStr, currency});
            HashSet<String> hashSet = new HashSet<String>(ids);
            return hashSet;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempGroupId);
        }
    }
}

