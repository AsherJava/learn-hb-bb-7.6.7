/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.offsetitem.dao.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.base.template.EntNativeSqlTemplate;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl;
import com.jiuqi.gcreport.offsetitem.dao.impl.OffsetQuerySqlBuilder;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.GcBusinessTypeQueryRuleEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.monitor.IOffsetCoreMonitor;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class GcOffSetItemAdjustCoreDaoImpl
extends GcDbSqlGenericDAO<GcOffSetVchrItemAdjustEO, String>
implements GcOffSetItemAdjustCoreDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GcOffsetVchrQueryImpl offsetVchrQuery;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Lazy
    @Autowired(required=false)
    private List<IOffsetCoreMonitor> offsetCoreMonitors;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    private static DecimalFormat df = new DecimalFormat("0000");
    private static final String INPUT_DATA_LOCK_TABLE_NAME = "GC_INPUTDATALOCK";
    public static final String ORG_TEMPORARY_TABLENAME = "GC_ORGTEMPORARY_AM";
    private static final String SQL_QUERY_OFFSETVCHRITEM = "select %s from GC_OFFSETVCHRITEM  e \n %s \n";

    public GcOffSetItemAdjustCoreDaoImpl() {
        super(GcOffSetVchrItemAdjustEO.class);
    }

    @Override
    public Serializable save(GcOffSetVchrItemAdjustEO entity) throws DataAccessException {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> saveAll(List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOS) throws DataAccessException {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public int update(GcOffSetVchrItemAdjustEO object) {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public void updateAll(List<GcOffSetVchrItemAdjustEO> gcOffSetVchrItemAdjustEOS) throws DataAccessException {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public int addSelective(GcOffSetVchrItemAdjustEO object) {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public int updateSelective(GcOffSetVchrItemAdjustEO object) {
        throw new RuntimeException("\u5f53\u524d\u65b9\u6cd5\u5df2\u5e9f\u5f03\uff0c\u8bf7\u4f7f\u7528\u66ff\u4ee3\u65b9\u6cd5");
    }

    @Override
    public int add(GcOffSetVchrItemAdjustEO eo) {
        this.monitorExecute(monitor -> monitor.beforeSave(Arrays.asList(eo)));
        this.setUpdateUserInfo(eo);
        int add = super.add((BaseEntity)eo);
        this.monitorExecute(monitor -> monitor.afterSave(Arrays.asList(eo)));
        return add;
    }

    @Override
    public int[] addBatch(List<GcOffSetVchrItemAdjustEO> eoList) throws DataAccessException {
        eoList.forEach(item -> this.setUpdateUserInfo((GcOffSetVchrItemAdjustEO)((Object)item)));
        this.monitorExecute(monitor -> monitor.beforeSave(eoList));
        int[] addBatch = super.addBatch(eoList);
        this.monitorExecute(monitor -> monitor.afterSave(eoList));
        return addBatch;
    }

    private void setUpdateUserInfo(GcOffSetVchrItemAdjustEO entity) {
        entity.addFieldValue("MD_GCADJTYPE", "BEFOREADJ");
        entity.setModifyTime(new Date(System.currentTimeMillis()));
        if (NpContextHolder.getContext() != null && !StringUtils.isEmpty((String)NpContextHolder.getContext().getUserName())) {
            entity.setCreateUser(NpContextHolder.getContext().getUserName());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteByMrecId(Collection<String> mrecIds) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecIds, (String)"mrecid");
        String sql = "select id from GC_OFFSETVCHRITEM  \n  where " + tempTableCondition.getCondition();
        try {
            List idList = this.selectFirstList(String.class, sql, new Object[0]);
            this.deleteByIdCollection(idList);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    private StringBuffer whereSql(GcTaskBaseArguments baseArguments) {
        StringBuffer whereSql = new StringBuffer(64);
        if (baseArguments == null) {
            return whereSql;
        }
        if (null != baseArguments.getAcctYear()) {
            whereSql.append(" and substr(dataTime,1,4) = '").append(baseArguments.getAcctYear()).append("'\n");
        }
        if (null != baseArguments.getAcctPeriod() && null != baseArguments.getAcctYear()) {
            whereSql.append(" and substr(dataTime,6,4) = '").append(df.format(baseArguments.getAcctPeriod())).append("'\n");
        }
        if (!StringUtils.isEmpty((String)baseArguments.getCurrency())) {
            whereSql.append(" and offSetCurr = '").append(baseArguments.getCurrency()).append("'\n");
        }
        return whereSql;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteByOffsetGroupIds(Collection<String> srcOffsetGroupIds, GcTaskBaseArguments baseArguments) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcOffsetGroupIds, (String)"srcOffsetGroupId");
        String sql = "select id from GC_OFFSETVCHRITEM  \n  where " + tempTableCondition.getCondition() + this.whereSql(baseArguments);
        try {
            List idList = this.selectFirstList(String.class, sql, new Object[0]);
            this.deleteByIdCollection(idList);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> srcOffsetGroupIds, Integer offSetSrcType, GcTaskBaseArguments baseArguments) {
        if (srcOffsetGroupIds == null || srcOffsetGroupIds.size() <= 0) {
            return;
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcOffsetGroupIds, (String)"srcOffsetGroupId");
        String sql = "select id from GC_OFFSETVCHRITEM  \n  where " + tempTableCondition.getCondition() + this.whereSql(baseArguments);
        if (baseArguments.getOrgType() != null) {
            sql = sql + "and md_gcorgtype = '" + baseArguments.getOrgType() + "'\n";
        }
        if (offSetSrcType != null) {
            sql = sql + "and offSetSrcType = '" + offSetSrcType + "'\n";
        }
        try {
            List idList = this.selectFirstList(String.class, sql, new Object[0]);
            this.deleteByIdCollection(idList);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public void deleteByLockId(String lockId) {
        String sql = "  select id from GC_OFFSETVCHRITEM  \n   where srcOffsetGroupId in \n         (select l.offsetGroupId from GC_INPUTDATALOCK  l \n           where l.lockId = ?)\n";
        List idList = this.selectFirstList(String.class, sql, new Object[]{lockId});
        this.deleteByIdCollection(idList);
    }

    @Override
    public void deleteByLockId(String lockId, List<String> currencyList) {
        String sql = "  select id from GC_OFFSETVCHRITEM  \n   where srcOffsetGroupId in \n         (select l.offsetGroupId from GC_INPUTDATALOCK  l \n           where l.lockId = ?)\n  and " + SqlUtils.getConditionOfMulStrUseOr(currencyList, (String)"offSetCurr");
        List idList = this.selectFirstList(String.class, sql, new Object[]{lockId});
        this.deleteByIdCollection(idList);
    }

    @Override
    public void deleteDataByOffsetSrcType(GcTaskBaseArguments arguments, String srcType) {
        String acctPeriodSql = " and t.dataTime like '%".concat(arguments.getAcctYear() + "_" + df.format(arguments.getAcctPeriod())).concat("'\n");
        String sql = "select id from GC_OFFSETVCHRITEM  \n   where mrecid in ( \n    select v.mrecid from (       select t.mrecid from GC_OFFSETVCHRITEM  t \n        where t.taskId = ? \n           " + acctPeriodSql + "          and t.md_gcorgtype = ? \n          and t.offSetCurr = ? \n          and t.offSetSrcType = " + srcType + " \n          and t.unitId in ( \n         select o.code from " + "MD_ORG_MANAGEMENT" + "  o \n          where o.parents like ?) \nunion \n       select m.mrecid from " + "GC_OFFSETVCHRITEM" + "  m \n        where m.taskId = ? \n           " + acctPeriodSql + "          and m.md_gcorgtype = ? \n          and m.offSetCurr = ? \n          and m.offSetSrcType = " + srcType + " \n          and m.OPPUNITID in ( \n         select o.code from " + "MD_ORG_MANAGEMENT" + "  o \n          where o.parents like ?) \n) v )  \n";
        YearPeriodObject yp = new YearPeriodObject(null, arguments.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)GCOrgTypeEnum.MANAGEMENT.getCode(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO org = orgService.getOrgByCode(arguments.getOrgId());
        ArrayList<String> paramList = new ArrayList<String>(Arrays.asList(arguments.getTaskId(), GCOrgTypeEnum.NONE.getCode(), arguments.getCurrency(), org.getParentStr() + "%", arguments.getTaskId(), GCOrgTypeEnum.NONE.getCode(), arguments.getCurrency(), org.getParentStr() + "%"));
        List idList = this.selectFirstList(String.class, sql, new Object[]{paramList});
        this.deleteByIdCollection(idList);
    }

    @Override
    public void updateMemoById(String id, String memo) {
        String sql = " update GC_OFFSETVCHRITEM  t \n    set memo = ? \n  where t.id = ? \n";
        this.execute(sql, new Object[]{memo, id});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateDisabledFlag(List<String> mrecids, boolean isDisabled) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"t.mrecid");
        StringBuffer sql = new StringBuffer(128);
        sql.append("update ").append("GC_OFFSETVCHRITEM  t\n");
        sql.append(" set disableFlag=? \n");
        sql.append(" ,modifyTime=? \n");
        sql.append(" where  \n");
        sql.append(tempTableCondition.getCondition()).append("\n");
        try {
            this.execute(sql.toString(), new Object[]{isDisabled ? 1 : 0, new Date()});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public void updateOffsetGroupId(String oldSrcGroupId, String newSrcGroupId, Integer elmMode, GcTaskBaseArguments baseArguments) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("update ").append("GC_OFFSETVCHRITEM  t\n");
        sql.append(" set srcOffsetGroupId=? \n");
        sql.append(" where t.srcOffsetGroupId=? \n");
        sql.append(this.whereSql(baseArguments)).append(" \n");
        if (elmMode != null) {
            sql.append(" and t.elmmode=").append(elmMode).append(" \n");
        }
        this.execute(sql.toString(), new Object[]{newSrcGroupId, oldSrcGroupId});
    }

    @Override
    public PaginationDto<Map<String, Object>> listWithFullGroup(QueryParamsDTO queryParamsDTO) {
        PaginationDto<Map<String, Object>> page = new PaginationDto<Map<String, Object>>(null, 0, queryParamsDTO.getPageNum(), queryParamsDTO.getPageSize());
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.fillMrecids(queryParamsDTO, null, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setContent(new ArrayList());
            return page;
        }
        List<Map<String, Object>> datas = this.listWithFullGroupByMrecids(queryParamsDTO, mRecids);
        page.setTotalElements(totalCount);
        page.setContent(datas);
        return page;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> listWithFullGroupByMrecids(QueryParamsDTO queryParamsDTO, Set<String> mRecids) {
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.MRECID,record.ELMMODE,record.GCBUSINESSTYPECODE,record.RULEID,record.UNITID,record.OPPUNITID,record.SUBJECTCODE,record.SUBJECTORIENT,record.OFFSET_CREDIT as OFFSETCREDIT,record.OFFSET_DEBIT as OFFSETDEBIT,record.DIFFC as DIFFC,record.DIFFD as DIFFD,record.OFFSETSRCTYPE as SRCTYPE,record.MEMO,record.SRCOFFSETGROUPID,record.DISABLEFLAG,record.FETCHSETGROUPID,record.MD_GCORGTYPE,record.SUBJECTORIENT,record.CHKSTATE,record.CHKSTATECHANGE");
        for (String code : queryParamsDTO.getOtherShowColumns()) {
            selectFields.append(",record.").append(code);
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)" record.mrecid");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        sql.append("order by record.mrecid desc, record.id \n");
        try {
            List datas = this.selectMap(sql.toString(), new Object[0]);
            datas.forEach(item -> this.offsetVchrQuery.convert((Map<String, Object>)item));
            List list = datas;
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> listWithFullGroupBySrcOffsetGroupIdsAndSystemId(QueryParamsDTO queryParamsDTO, Set<String> offsetGroupIds) {
        List<String> ruleIds;
        ArrayList<String> args = new ArrayList<String>();
        StringBuffer sql = new StringBuffer(512);
        sql.append("select distinct record.MRECID from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        TempTableCondition offsetGroupIdCondition = SqlUtils.getConditionOfIds(offsetGroupIds, (String)" record.srcOffsetGroupId");
        sql.append("where ").append(offsetGroupIdCondition.getCondition()).append("\n");
        queryParamsDTO.getTempGroupIdList().add(offsetGroupIdCondition.getTempGroupId());
        sql.append(" and record.SYSTEMID = ? \n");
        args.add(queryParamsDTO.getSystemId());
        sql.append(" and record.md_gcorgtype in('" + GCOrgTypeEnum.NONE.getCode() + "', ? ) \n");
        args.add(queryParamsDTO.getOrgType());
        if (!StringUtils.isEmpty((String)queryParamsDTO.getPeriodStr())) {
            sql.append(" and record.datatime = ? \n");
            args.add(queryParamsDTO.getPeriodStr());
        }
        if (!CollectionUtils.isEmpty(ruleIds = queryParamsDTO.getRules())) {
            TempTableCondition ruleIdCondition = SqlUtils.getConditionOfIds(ruleIds, (String)"record.RULEID");
            queryParamsDTO.getTempGroupIdList().add(ruleIdCondition.getTempGroupId());
            sql.append(" and ").append(ruleIdCondition.getCondition());
        }
        if (DimensionUtils.isExistAdjust((String)queryParamsDTO.getTaskId())) {
            sql.append(" and record.ADJUST = ? \n");
            args.add(queryParamsDTO.getSelectAdjustCode());
        }
        List unitOrOppUnitIds = Stream.concat(queryParamsDTO.getUnitIdList() != null ? queryParamsDTO.getUnitIdList().stream() : Stream.empty(), queryParamsDTO.getOppUnitIdList() != null ? queryParamsDTO.getOppUnitIdList().stream() : Stream.empty()).distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(queryParamsDTO.getUnitIdList())) {
            TempTableCondition unitIdCondition = SqlUtils.getConditionOfIds(unitOrOppUnitIds, (String)"record.UNITID");
            queryParamsDTO.getTempGroupIdList().add(unitIdCondition.getTempGroupId());
            sql.append(" and ").append(unitIdCondition.getCondition());
        }
        if (!CollectionUtils.isEmpty(queryParamsDTO.getOppUnitIdList())) {
            TempTableCondition oppUnitIdCondition = SqlUtils.getConditionOfIds(unitOrOppUnitIds, (String)"record.OPPUNITID");
            queryParamsDTO.getTempGroupIdList().add(oppUnitIdCondition.getTempGroupId());
            sql.append(" and ").append(oppUnitIdCondition.getCondition());
        }
        sql.append("order by record.mrecid desc\n");
        List datas = this.selectMap(sql.toString(), args.toArray());
        if (CollectionUtils.isEmpty((Collection)datas)) {
            return Collections.emptyList();
        }
        List mrecids = datas.stream().map(data -> ConverterUtils.getAsString(data.get("MRECID"), (String)"")).collect(Collectors.toList());
        StringBuffer sql2 = new StringBuffer(512);
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.MRECID,record.ELMMODE,record.gcBusinessTypeCode,record.ruleId,record.UNITID,record.OPPUNITID,record.SUBJECTCODE,record.SUBJECTORIENT,record.offset_Credit as offsetCredit,record.offset_DEBIT as offsetDEBIT,record.DIFFC as DIFFC,record.DIFFD as DIFFD,record.MEMO,record.OFFSETSRCTYPE as SRCTYPE,record.SRCOFFSETGROUPID,record.DISABLEFLAG,record.FETCHSETGROUPID");
        for (String code : queryParamsDTO.getOtherShowColumns()) {
            selectFields.append(",record.").append(code);
        }
        sql2.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        TempTableCondition mrecidCondition = SqlUtils.getConditionOfIds(mrecids, (String)" record.mrecid");
        queryParamsDTO.getTempGroupIdList().add(mrecidCondition.getTempGroupId());
        sql2.append("where ").append(mrecidCondition.getCondition()).append("\n");
        sql2.append(" order by record.mrecid desc \n");
        try {
            List mrecidDatas = this.selectMap(sql2.toString(), new Object[0]);
            if (CollectionUtils.isEmpty((Collection)mrecidDatas)) {
                List<Map<String, Object>> list = Collections.emptyList();
                return list;
            }
            mrecidDatas.forEach(item -> this.offsetVchrQuery.convert((Map<String, Object>)item));
            List list = mrecidDatas;
            return list;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    @Override
    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO queryParamsDTO) {
        PaginationDto<GcOffSetVchrItemAdjustEO> page = new PaginationDto<GcOffSetVchrItemAdjustEO>();
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.fillMrecids(queryParamsDTO, null, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setTotalElements(0);
            page.setContent(new ArrayList());
            return page;
        }
        page.setTotalElements(totalCount);
        page.setContent(this.listWithFullGroupByMrecids(mRecids));
        page.setPageSize(queryParamsDTO.getPageSize());
        page.setCurrentPage(queryParamsDTO.getPageNum());
        return page;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithFullGroupByMrecids(Collection<String> mRecids) {
        if (CollectionUtils.isEmpty(mRecids)) {
            return new ArrayList<GcOffSetVchrItemAdjustEO>();
        }
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM", (String)"record");
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)" record.MRECID");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        sql.append("order by record.MRECID desc \n");
        try {
            List list = this.selectEntity(sql.toString(), new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO queryParamsDTO) {
        OffsetQuerySqlBuilder builder = OffsetQuerySqlBuilder.builder(queryParamsDTO);
        if (!Boolean.TRUE.equals(queryParamsDTO.isQueryAllColumns())) {
            HashSet<String> queryColumns = new HashSet<String>();
            queryColumns.add("OFFSET_CREDIT");
            queryColumns.add("OFFSET_DEBIT");
            queryColumns.add("SUBJECTCODE");
            queryColumns.add("OFFSETSRCTYPE");
            queryColumns.addAll(queryParamsDTO.getOtherShowColumns());
            builder.setColumns(new ArrayList<String>(queryColumns));
        }
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = builder.buildOrderByClauseFromExternal(" record.mrecid desc \n").buildQuerySql(params);
        try {
            List list = this.selectEntity(sql, params.toArray());
            return list;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    private void initMergeUnitCondition(QueryParamsDTO queryParamsDto, StringBuffer head, StringBuffer whereSql, List<Object> params, String parentGuids, Date date, int orgCodeLength, String gcParentStr) {
        String orgTypeId = queryParamsDto.getOrgType();
        String emptyUUID = "NONE";
        if (Boolean.TRUE.equals(queryParamsDto.getArbitrarilyMerge())) {
            whereSql.append("where substr(bfUnitTable.parents, 1, ").append(queryParamsDto.getOrgComSupLength()).append(" ) <> substr(dfUnitTable.parents, 1, ").append(queryParamsDto.getOrgComSupLength()).append(")\n");
            params.add(orgTypeId);
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?) \n");
        } else {
            int len = gcParentStr.length();
            whereSql.append("where (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLength + 1).append(")\n");
            whereSql.append("and bfUnitTable.gcparents like ?\n");
            whereSql.append("and dfUnitTable.gcparents like ?\n");
            params.add(gcParentStr + "%");
            params.add(gcParentStr + "%");
            whereSql.append(" and record.md_gcorgtype in('" + emptyUUID + "',?)) \n");
            params.add(orgTypeId);
            this.offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        if (queryParamsDto.isDelete()) {
            ConsolidatedTaskVO consolidatedTaskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(queryParamsDto.getTaskId(), queryParamsDto.getPeriodStr());
            if (consolidatedTaskVO == null) {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsDto.getTaskId());
                throw new BusinessRuntimeException("\u4efb\u52a1\u3010 " + (taskDefine == null ? queryParamsDto.getTaskId() : taskDefine.getTitle()) + "\u3011\u672a\u8bbe\u7f6e\u5408\u5e76\u4f53\u7cfb");
            }
            if (!this.consolidatedTaskService.isCorporate(queryParamsDto.getTaskId(), queryParamsDto.getPeriodStr(), queryParamsDto.getOrgType()) || CollectionUtils.isEmpty((Collection)consolidatedTaskVO.getManageCalcUnitCodes()) || !consolidatedTaskVO.getManageCalcUnitCodes().contains(queryParamsDto.getOrgId())) {
                whereSql.append(" and record.md_gcorgtype <> '").append(emptyUUID).append("'\n");
            }
        }
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listByWhere(String[] columnNamesInDB, Object[] values) {
        StringBuffer conditionSql = new StringBuffer(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(SqlUtils.getConditionOfObject((Object)values[i], (String)(" e." + columnNamesInDB[i]))).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        whereSql = whereSql + " order by e.mrecid desc, id";
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM", (String)"e");
        String sql = String.format(SQL_QUERY_OFFSETVCHRITEM, selectFields, whereSql);
        return this.selectEntity(sql, new Object[0]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, String> getMrecid2FetchSetIdByMrecids(Set<String> mRecids) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)"e.mrecid");
        String whereSql = "where " + tempTableCondition.getCondition();
        try {
            String sql = String.format(SQL_QUERY_OFFSETVCHRITEM, "e.mrecid as MRECID,e.fetchSetGroupId as FETCHSETGROUPID", whereSql);
            List result = this.selectMap(sql, new Object[0]);
            Map<String, String> map = result.stream().collect(Collectors.toMap(data -> (String)data.get("MRECID"), data -> Optional.ofNullable((String)data.get("FETCHSETGROUPID")).orElse(""), (e1, e2) -> e1));
            return map;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public Collection<String> listMrecidsByRuleId(String ruleId, Set<String> subjectCode, QueryParamsDTO queryParamsDTO) {
        HashSet<String> mrecids = new HashSet<String>();
        TempTableCondition tempTableCondition = null;
        if (!CollectionUtils.isEmpty(subjectCode)) {
            tempTableCondition = SqlUtils.getConditionOfMulStr(subjectCode, (String)"e.subjectCode");
        }
        String sql = "select e.mrecid from GC_OFFSETVCHRITEM  e \n where \n   e.DATATIME = ? \n and e.systemId = ?  and e.ruleid = ? ";
        sql = sql + " and  e.MD_GCORGTYPE in ('" + queryParamsDTO.getOrgType() + "', '" + "NONE" + "')";
        if (!Objects.isNull(tempTableCondition)) {
            sql = sql + " and " + tempTableCondition.getCondition();
        }
        if (!StringUtils.isEmpty((String)queryParamsDTO.getCurrency())) {
            sql = sql + " and e.offsetcurr = '" + queryParamsDTO.getCurrency() + "' \n";
        }
        List re = this.selectFirstList(String.class, sql, new Object[]{queryParamsDTO.getPeriodStr(), queryParamsDTO.getSystemId(), ruleId});
        mrecids.addAll(re);
        return mrecids;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> listOffsetGroupIdsByMrecid(Collection<String> mrecids) {
        HashSet<String> srcOffsetGroupIdSet = new HashSet<String>();
        mrecids.remove(null);
        if (CollectionUtils.isEmpty(mrecids)) {
            return srcOffsetGroupIdSet;
        }
        StringBuffer whereSql = new StringBuffer(128);
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"e.mrecid");
        whereSql.append("where ").append(tempTableCondition.getCondition());
        String sql = String.format(SQL_QUERY_OFFSETVCHRITEM, "e.srcOffsetGroupId", whereSql);
        try {
            List rs = this.selectFirstList(String.class, sql, new Object[0]);
            srcOffsetGroupIdSet.addAll(rs);
            srcOffsetGroupIdSet.remove(null);
            HashSet<String> hashSet = srcOffsetGroupIdSet;
            return hashSet;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> listOffsetGroupIdsById(Collection<String> ids) {
        HashSet<String> srcOffsetGroupIdSet = new HashSet<String>();
        ids.remove(null);
        if (CollectionUtils.isEmpty(ids)) {
            return srcOffsetGroupIdSet;
        }
        StringBuffer whereSql = new StringBuffer(128);
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(ids, (String)"e.id");
        whereSql.append("where ").append(tempTableCondition.getCondition());
        String sql = String.format(SQL_QUERY_OFFSETVCHRITEM, "e.srcOffsetGroupId", whereSql);
        try {
            List rs = this.selectFirstList(String.class, sql, new Object[0]);
            srcOffsetGroupIdSet.addAll(rs);
            srcOffsetGroupIdSet.remove(null);
            HashSet<String> hashSet = srcOffsetGroupIdSet;
            return hashSet;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    private StringBuffer whereSqlAlias(GcTaskBaseArguments gcTaskBaseArguments) {
        StringBuffer whereSql = new StringBuffer(64);
        if (null != gcTaskBaseArguments.getAcctYear()) {
            whereSql.append(" and substr(e.dataTime,1,4) = '").append(gcTaskBaseArguments.getAcctYear()).append("'\n");
        }
        if (null != gcTaskBaseArguments.getAcctPeriod() && null != gcTaskBaseArguments.getAcctYear()) {
            whereSql.append(" and substr(e.dataTime,6,4) = '").append(df.format(gcTaskBaseArguments.getAcctPeriod())).append("'\n");
        }
        if (!StringUtils.isEmpty((String)gcTaskBaseArguments.getCurrency())) {
            whereSql.append(" and e.offSetCurr = '").append(gcTaskBaseArguments.getCurrency()).append("'\n");
        }
        return whereSql;
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String lockId) {
        String sql = " select e.id from GC_OFFSETVCHRITEM  e\n   inner join  \nGC_INPUTDATALOCK l on e.srcOffsetGroupId=l.offsetGroupId \n  where l.lockid=? \n";
        return this.selectEntity(sql, new Object[]{lockId});
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listIdsByLockId(String lockId, List<String> currencyList) {
        String sql = " select e.id from GC_OFFSETVCHRITEM  e\n   inner join  \nGC_INPUTDATALOCK l on e.srcOffsetGroupId=l.offsetGroupId \n  where l.lockid=? \n  and " + SqlUtils.getConditionOfMulStrUseOr(currencyList, (String)"e.offSetCurr");
        return this.selectEntity(sql, new Object[]{lockId});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> listExistsSubjectCodes(String systemId, Set<String> keySet) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(keySet, (String)"SUBJECTCODE");
        String sql = "SELECT SUBJECTCODE FROM GC_OFFSETVCHRITEM \n WHERE SYSTEMID= ? AND " + tempTableCondition.getCondition() + "\n  GROUP BY SUBJECTCODE";
        try {
            List list = this.selectFirstList(String.class, sql, new Object[]{systemId});
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int fillMrecids(QueryParamsDTO queryParamsDTO, Set<String> srcOffsetGroupIdResults, Set<String> mrecidSet) {
        String sql;
        if (null == mrecidSet) {
            mrecidSet = new HashSet<String>();
        }
        ArrayList<Object> params = new ArrayList<Object>();
        String queryFields = "record.mrecid";
        if (null != srcOffsetGroupIdResults) {
            queryFields = "record.mrecid,record.elmMode,record.srcOffsetGroupId ";
        }
        if (StringUtils.isEmpty((String)(sql = this.getFillMrecidSql(queryParamsDTO, queryFields, params)))) {
            return 0;
        }
        String countSql = String.format("select count(*) from ( %1$s )  t", sql);
        try {
            int count = this.count(countSql, params);
            if (count < 1) {
                int n = 0;
                return n;
            }
            int begin = -1;
            int range = -1;
            int pageNum = queryParamsDTO.getPageNum();
            int pageSize = queryParamsDTO.getPageSize();
            if (pageNum > 0 && pageSize > 0) {
                begin = (pageNum - 1) * pageSize;
                range = pageNum * pageSize;
            }
            List rs = this.selectMapByPaging(sql, begin, range, params);
            for (Map d : rs) {
                String value;
                int elmMode;
                mrecidSet.add(String.valueOf(d.get("MRECID")));
                if (d.get("ELMMODE") == null || d.get("SRCOFFSETGROUPID") == null || (elmMode = ((Integer)d.get("ELMMODE")).intValue()) == OffsetElmModeEnum.WRITE_OFF_ITEM.getValue() || null == (value = String.valueOf(d.get("SRCOFFSETGROUPID"))) || srcOffsetGroupIdResults == null) continue;
                srcOffsetGroupIdResults.add(value);
            }
            int n = count;
            return n;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    @Override
    public String getFillMrecidSql(QueryParamsDTO queryParamsDTO, String queryFields, List<Object> params) {
        String sql = OffsetQuerySqlBuilder.builder(queryParamsDTO).buildQueryFieldsFromExternal(queryFields).buildGroupByClauseFromExternal(queryFields).buildOrderByClauseFromExternal(" record.mrecid desc \n").buildQuerySql(params);
        sql = this.processingSqlByGcBusinessTypeQueryRule(sql, queryFields, queryParamsDTO);
        return sql.toString();
    }

    private String processingSqlByGcBusinessTypeQueryRule(String sql, String queryFields, QueryParamsDTO queryParamsDto) {
        String operator;
        if (CollectionUtils.isEmpty(queryParamsDto.getGcBusinessTypeCodes())) {
            return sql;
        }
        GcBusinessTypeQueryRuleEnum enumByCode = GcBusinessTypeQueryRuleEnum.getEnumByCode(queryParamsDto.getGcBusinessTypeQueryRule());
        if (enumByCode == null) {
            return sql;
        }
        switch (enumByCode) {
            case ALL: {
                return sql;
            }
            case EQ: {
                operator = " = ";
                break;
            }
            case ACROSS: {
                operator = " <> ";
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u672a\u8bc6\u522b\u7684\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u67e5\u8be2\u89c4\u5219\uff1a" + (Object)((Object)enumByCode));
            }
        }
        StringBuffer processingSql = new StringBuffer();
        processingSql.append("select ").append(queryFields).append(" from ").append("GC_OFFSETVCHRITEM").append(" record \n");
        processingSql.append(" where mrecid in (select mrecid  from ").append("GC_OFFSETVCHRITEM").append(" t \n");
        processingSql.append("                   where t.mrecid in ( select mrecid  from (").append(sql).append(") temp )      \n");
        processingSql.append("                   group by t.mrecid     \n");
        processingSql.append("                   having max(gcbusinesstypecode)").append(operator).append("min(gcbusinesstypecode) \n");
        processingSql.append("                  )        \n");
        processingSql.append(" group by ").append(queryFields).append("\n");
        processingSql.append(" order by record.mrecid desc\n");
        return processingSql.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> sumOffsetValueGroupBy(QueryParamsDTO queryParamsDTO, String selectFieldSql, String groupFieldSql) {
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = OffsetQuerySqlBuilder.builder(queryParamsDTO).buildQueryFieldsFromExternal(selectFieldSql).buildGroupByClauseFromExternal(groupFieldSql).buildQuerySql(params);
        try {
            List list = this.selectMap(sql, params.toArray());
            return list;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    @Override
    public String mergeUnitRangeSql(QueryParamsDTO queryParamsDTO, List<Object> params) {
        StringBuffer whereSql = new StringBuffer();
        whereSql.append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        String orgTable = StringUtils.isEmpty((String)queryParamsDTO.getOrgType()) ? "MD_ORG_CORPORATE" : queryParamsDTO.getOrgType();
        whereSql.append(" join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
        whereSql.append(" join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        StringBuffer head = new StringBuffer();
        this.getOffsetEntryWhereSql(queryParamsDTO, params, head, whereSql);
        return whereSql.toString();
    }

    private boolean getOffsetEntryWhereSql(QueryParamsDTO queryParamsDto, List<Object> params, StringBuffer head, StringBuffer whereSql) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsDto.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsDto.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        if (Boolean.TRUE.equals(queryParamsDto.getArbitrarilyMerge())) {
            this.initMergeUnitCondition(queryParamsDto, head, whereSql, params, null, date, orgService.getOrgCodeLength(), null);
        } else if (!StringUtils.isEmpty((String)queryParamsDto.getOrgId())) {
            GcOrgCacheVO org = orgService.getOrgByCode(queryParamsDto.getOrgId());
            if (null == org || org.getParentStr() == null) {
                return false;
            }
            String parentGuids = org.getParentStr();
            String gcParentStr = org.getGcParentStr();
            this.initMergeUnitCondition(queryParamsDto, head, whereSql, params, parentGuids, date, orgService.getOrgCodeLength(), gcParentStr);
        } else {
            whereSql.append("where 1=1 ");
            this.offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        this.offsetVchrQuery.initUnitCondition(queryParamsDto, whereSql, orgService);
        this.offsetVchrQuery.initPeriodCondition(queryParamsDto, params, whereSql);
        this.offsetVchrQuery.initOtherCondition(whereSql, queryParamsDto);
        whereSql.append(" and (record.disableFlag<>? or record.disableFlag is null )\n");
        params.add(1);
        return true;
    }

    private void deleteByIdCollection(Collection<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.monitorExecute(monitor -> monitor.beforeDelete(ids));
        String dataBase = null;
        try {
            dataBase = ((EntNativeSqlTemplate)this.getEntSqlTemplate()).getDatabase().getName();
        }
        catch (Exception e) {
            this.logger.error("deleteByIdCollection\u65b9\u6cd5\u4e2d\u83b7\u53d6\u6570\u636e\u5e93\u7c7b\u578b\u65f6\u62a5\u9519", e);
        }
        this.deleteByIdTemporaryMode(ids);
        this.monitorExecute(monitor -> monitor.afterDelete(ids));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deleteByIdTemporaryMode(Collection<String> ids) {
        TempTableCondition conditionOfIds = SqlUtils.getConditionOfIds(ids, (String)"ID");
        String sql = " DELETE FROM GC_OFFSETVCHRITEM where " + conditionOfIds.getCondition();
        try {
            this.execute(sql);
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)conditionOfIds.getTempGroupId());
        }
    }

    void monitorExecute(Consumer<IOffsetCoreMonitor> function) {
        if (CollectionUtils.isEmpty(this.offsetCoreMonitors)) {
            return;
        }
        for (IOffsetCoreMonitor offsetCoreMonitor : this.offsetCoreMonitors) {
            try {
                function.accept(offsetCoreMonitor);
            }
            catch (Exception e) {
                this.logger.error("\u62b5\u9500\u5206\u5f55\u76d1\u542c\u5668\u3010" + offsetCoreMonitor.monitorName() + "\u3011\u6267\u884c\u5f02\u5e38:", e);
            }
        }
    }
}

