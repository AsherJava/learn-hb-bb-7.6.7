/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlChgOrgDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.vo.ChangeOrgCondition;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlChgOrgDaoImpl
extends GcDbSqlGenericDAO<SameCtrlChgOrgEO, String>
implements SameCtrlChgOrgDao {
    public SameCtrlChgOrgDaoImpl() {
        super(SameCtrlChgOrgEO.class);
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgs(String orgVersionId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLCHGORG", (String)"s") + " \n  from " + "GC_SAMECTRLCHGORG" + "  s \n  where s.orgVersionId = ? \n";
        return this.selectEntity(sql, new Object[]{orgVersionId});
    }

    @Override
    public Set<String> listVirtualCodeByVirtualParentCode(String virtualParentCode, Date fromDate, Date toDate) {
        String sql = "  select t.virtualCode from GC_SAMECTRLCHGORG  t \n  where t.virtualParentCode = ? \n  and t.disposalDate >= ? and t.disposalDate <= ?  \n";
        List rs = this.selectFirstList(String.class, sql, new Object[]{virtualParentCode, fromDate, toDate});
        HashSet<String> result = new HashSet<String>(16);
        result.addAll(rs);
        return result;
    }

    @Override
    public Set<String> listChangedCodeByChangedParentCode(String changedParentCode, Date fromDate, Date toDate) {
        String sql = "  select t.changedCode from GC_SAMECTRLCHGORG  t \n  where t.changedParentCode = ? \n  and t.changedOrgType = 2 \n  and t.disposalDate >= ? and t.disposalDate <= ?  \n";
        List rs = this.selectFirstList(String.class, sql, new Object[]{changedParentCode, fromDate, toDate});
        HashSet<String> result = new HashSet<String>(16);
        result.addAll(rs);
        return result;
    }

    @Override
    public Set<String> listVirtualCodeBySameParentCode(String sameParentCode, Date fromDate, Date toDate) {
        String sql = "  select t.virtualCode from GC_SAMECTRLCHGORG  t \n  where t.sameParentCode = ? \n  and t.disposalDate >= ? and t.disposalDate <= ?  ";
        List rs = this.selectFirstList(String.class, sql, new Object[]{sameParentCode, fromDate, toDate});
        HashSet<String> result = new HashSet<String>(16);
        result.addAll(rs);
        return result;
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getColumnsSqlByEntity(SameCtrlChgOrgEO.class, (String)"t");
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposalDate(ChangeOrgCondition condition, Date toDate) {
        List eos;
        ArrayList<Object> paramList = new ArrayList<Object>();
        String whereSql = this.getWhereSql(condition, paramList);
        String sql = "  select " + this.getAllFieldsSQL() + "  from " + "GC_SAMECTRLCHGORG" + "  t \n" + whereSql + " \n and  t.changeDate <= ?\n";
        paramList.add(toDate);
        if (!condition.getAdministrator().booleanValue()) {
            sql = sql + " and t.deleteFlag = ?\n";
            paramList.add(0);
        }
        if (CollectionUtils.isEmpty((Collection)(eos = this.selectEntity(sql = sql + " order by t.changeDate desc\n", paramList)))) {
            return Collections.EMPTY_LIST;
        }
        return eos;
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByDisposeOrg(ChangeOrgCondition condition, Date fromDate, Date toDate) {
        YearPeriodObject yp = new YearPeriodObject(null, condition.getPeriodStr());
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = noAuthzOrgTool.getOrgByID(condition.getOrgCode());
        String parentStr = orgCacheVO.getParentStr();
        String sql = " select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLCHGORG" + "  t \n join " + condition.getOrgType() + "  sameParentOrg on (t.changedCode = sameParentOrg.code)\n join " + condition.getOrgType() + "   virtualParentOrg on (t.virtualParentCode = virtualParentOrg.code)\n where  sameParentOrg.validtime<? and sameParentOrg.invalidtime>=? \n and virtualParentOrg.validtime<? and virtualParentOrg.invalidtime>=? \n and virtualParentOrg.parents like '" + parentStr + "%' \n and (sameParentOrg.parents not like '" + parentStr + "%' \n  or sameParentOrg.parents = '" + parentStr + "') and t.disposalDate > ? and t.disposalDate <= ?\n and t.deleteFlag = ?\n";
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(null, condition.getPeriodStr());
        Date date = yearPeriodUtil.formatYP().getEndDate();
        ArrayList<Serializable> paramList = new ArrayList<Serializable>(Arrays.asList(date, date, date, date, fromDate, toDate, 0));
        List eos = this.selectEntity(sql, paramList);
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return Collections.EMPTY_LIST;
        }
        return eos;
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgByMRecid(String mRecid) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLCHGORG", (String)"s") + " \n  from " + "GC_SAMECTRLCHGORG" + "  s \n  where s.mRecid = ? \n  and s.deleteFlag = 0     \n";
        return this.selectEntity(sql, new Object[]{mRecid});
    }

    @Override
    public void updateManageChangedOrg(ChangeOrgCondition condition) {
        String sql = " update GC_SAMECTRLCHGORG  \nset changedCode = ?, changeDate = ?, disposalDate = ?, virtualCode = ?, virtualCodeType = ?, virtualParentCode = ?, correspondVirtualCode = ?  where id = ? ";
        this.execute(sql, new Object[]{condition.getSameCtrlChgOrgVO().getChangedCode(), condition.getSameCtrlChgOrgVO().getChangeDate(), condition.getSameCtrlChgOrgVO().getDisposalDate(), condition.getSameCtrlChgOrgVO().getVirtualCode(), condition.getSameCtrlChgOrgVO().getVirtualCodeType(), condition.getSameCtrlChgOrgVO().getVirtualParentCode(), condition.getSameCtrlChgOrgVO().getCorrespondVirtualCode(), condition.getSameCtrlChgOrgVO().getId()});
    }

    @Override
    public void deleteById(String id) {
        String sql = " update GC_SAMECTRLCHGORG  \n set deleteFlag = 1 \n where id = ? ";
        this.execute(sql, new Object[]{id});
    }

    @Override
    public void deleteByMRecid(List<String> mRecids) {
        String sql = " update GC_SAMECTRLCHGORG  \n set deleteFlag = 1 \n where mRecid = ? ";
        List param = mRecids.stream().map(m -> Arrays.asList(m)).collect(Collectors.toList());
        this.executeBatch(sql, param);
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgByParents(ChangeOrgCondition condition) {
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(condition.getSchemeId(), condition.getPeriodStr());
        Date date = yearPeriodUtil.formatYP().getEndDate();
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodUtil);
        StringBuilder sqlBuilder = new StringBuilder(512);
        sqlBuilder.append(" select ").append(this.getAllFieldsSQL()).append(" from ").append("GC_SAMECTRLCHGORG").append("  t \n");
        sqlBuilder.append(" join ").append(condition.getOrgType()).append("  changeOrg on t.changedCode = changeOrg.code \n");
        sqlBuilder.append(" join ").append(condition.getOrgType()).append("  virtualOrg on t.virtualCode = virtualOrg.code \n");
        sqlBuilder.append(" where changeOrg.validtime<? and changeOrg.invalidtime>=? \n");
        sqlBuilder.append(" and virtualOrg.validtime<? and virtualOrg.invalidtime>=? \n");
        sqlBuilder.append(" and t.mRecid is not null  \n");
        sqlBuilder.append(" and t.deleteFlag = 0  \n");
        List gcOrgCacheVOList = gcOrgCenterService.listAllOrgByParentIdContainsSelf(condition.getOrgCode());
        List gcOrgCacheCodeList = gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(gcOrgCacheCodeList)) {
            sqlBuilder.append("and ( \n");
            sqlBuilder.append(SqlUtils.getConditionOfIdsUseOr(gcOrgCacheCodeList, (String)"t.virtualCode"));
            sqlBuilder.append("or  \n");
            sqlBuilder.append(SqlUtils.getConditionOfIdsUseOr(gcOrgCacheCodeList, (String)"t.changedCode"));
            sqlBuilder.append(")  \n");
        }
        ArrayList<Date> paramList = new ArrayList<Date>(Arrays.asList(date, date, date, date));
        if (condition.getPeriodDate() != null) {
            sqlBuilder.append(" and t.changeDate <= ?   ");
            paramList.add(condition.getPeriodDate());
        }
        sqlBuilder.append(" order by t.mRecid desc, t.createTime  \n");
        List eos = this.selectEntity(sqlBuilder.toString(), paramList);
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return new ArrayList<SameCtrlChgOrgEO>();
        }
        return eos;
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChangedCodeByParam(ChangeOrgCondition condition) {
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(condition.getSchemeId(), condition.getPeriodStr());
        Date date = yearPeriodUtil.formatYP().getEndDate();
        GcOrgCenterService gcOrgCenterService = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodUtil);
        StringBuilder sqlBuilder = new StringBuilder(512);
        sqlBuilder.append(" select ").append(this.getAllFieldsSQL()).append(" from ").append("GC_SAMECTRLCHGORG").append("  t \n");
        sqlBuilder.append(" join ").append(condition.getOrgType()).append("  changeOrg on t.changedCode = changeOrg.code \n");
        sqlBuilder.append(" where changeOrg.validtime<? and changeOrg.invalidtime>=? \n");
        sqlBuilder.append(" and t.mRecid is not null  \n");
        sqlBuilder.append(" and t.deleteFlag = 0  \n");
        List gcOrgCacheVOList = gcOrgCenterService.listAllOrgByParentIdContainsSelf(condition.getOrgCode());
        List gcOrgCacheCodeList = gcOrgCacheVOList.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(gcOrgCacheCodeList)) {
            sqlBuilder.append("and \n");
            sqlBuilder.append(SqlUtils.getConditionOfIdsUseOr(gcOrgCacheCodeList, (String)"t.changedCode"));
            sqlBuilder.append("  \n");
        }
        sqlBuilder.append("and (t.virtualCode is null or t.virtualCode = '')");
        ArrayList<Date> paramList = new ArrayList<Date>(Arrays.asList(date, date));
        if (condition.getPeriodDate() != null) {
            sqlBuilder.append(" and t.changeDate <= ?   ");
            paramList.add(condition.getPeriodDate());
        }
        sqlBuilder.append(" order by t.mRecid desc, t.createTime  \n");
        List eos = this.selectEntity(sqlBuilder.toString(), paramList);
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return new ArrayList<SameCtrlChgOrgEO>();
        }
        return eos;
    }

    @Override
    public void disEnable(List<String> mRecids) {
        String sql = " update GC_SAMECTRLCHGORG \n set disableFlag = 1  \n where mRecid = ? \n";
        List param = mRecids.stream().map(m -> Arrays.asList(m)).collect(Collectors.toList());
        this.executeBatch(sql, param);
    }

    @Override
    public void enable(List<String> mRecids) {
        String sql = " update GC_SAMECTRLCHGORG \n set disableFlag = 0  \n where mRecid = ? \n";
        List param = mRecids.stream().map(m -> Arrays.asList(m)).collect(Collectors.toList());
        this.executeBatch(sql, param);
    }

    private String getWhereSql(ChangeOrgCondition condition, List<Object> paramList) {
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(null, condition.getPeriodStr());
        Date date = yearPeriodUtil.formatYP().getEndDate();
        GcOrgCenterService noAuthzOrgTool = GcOrgPublicTool.getInstance((String)condition.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodUtil);
        GcOrgCacheVO orgCacheVO = noAuthzOrgTool.getOrgByID(condition.getOrgCode());
        String parentStr = orgCacheVO.getParentStr();
        StringBuffer sqlBuilder = new StringBuffer(512);
        sqlBuilder.append(" left join ").append(condition.getOrgType()).append("  changeOrg on (t.changedCode = changeOrg.code\n");
        sqlBuilder.append(" and changeOrg.validtime<? and changeOrg.invalidtime>=?) \n");
        sqlBuilder.append("join ").append(condition.getOrgType()).append("  virtualParentOrg on (t.virtualParentCode = virtualParentOrg.code\n");
        sqlBuilder.append(" and virtualParentOrg.validtime<? and virtualParentOrg.invalidtime>=?) \n");
        paramList.addAll(Arrays.asList(date, date, date, date));
        if (!condition.getAllChildren().booleanValue()) {
            sqlBuilder.append("join ").append(condition.getOrgType()).append("  sameParentOrg on (t.sameParentCode = sameParentOrg.code\n");
            sqlBuilder.append(" and sameParentOrg.validtime<? and  sameParentOrg.invalidtime>=?) \n");
            paramList.addAll(Arrays.asList(date, date));
        }
        sqlBuilder.append("where (");
        sqlBuilder.append("changeOrg.parents like '").append(parentStr).append("%' \n");
        sqlBuilder.append(" or virtualParentOrg.parents like '").append(parentStr).append("%' \n");
        sqlBuilder.append(") \n");
        if (!condition.getAllChildren().booleanValue()) {
            sqlBuilder.append(" and (sameParentOrg.parents not like '").append(parentStr).append("%' \n");
            sqlBuilder.append(" or sameParentOrg.parents = '").append(parentStr).append("'");
            sqlBuilder.append(" or t.changedOrgType <> 1");
            sqlBuilder.append(")");
        }
        return sqlBuilder.toString();
    }

    @Override
    public SameCtrlChgOrgEO listSameCtrlChgOrgsByChangeCodeAndDate(String changeOrgCode, Date changeDate) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_SAMECTRLCHGORG" + "  t \n  where t.changedCode=? \n    and t.changeDate = ? \n";
        List results = this.selectEntity(sql, new Object[]{changeOrgCode, changeDate});
        if (CollectionUtils.isEmpty((Collection)results)) {
            return null;
        }
        return (SameCtrlChgOrgEO)((Object)results.get(0));
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByChangedAndDisposeDate(String changeOrgCode, Date changedDate, Date disposeDate) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_SAMECTRLCHGORG" + "  t \n  where t.changedCode=? \n and ((t.disposalDate >= ? and t.disposalDate < ?)\n or (t.changeDate >= ? and t.changeDate < ?))\n";
        Calendar changedCalendar = Calendar.getInstance();
        changedCalendar.setTime(changedDate);
        Calendar disposeCalendar = Calendar.getInstance();
        disposeCalendar.setTime(disposeDate);
        int disposeCalendarYear = disposeCalendar.get(1);
        int disposeCalendarMonth = disposeCalendar.get(2);
        int changedCalendarYear = changedCalendar.get(1);
        int changedCalendarMonth = changedCalendar.get(2);
        List results = this.selectEntity(sql, new Object[]{changeOrgCode, DateUtils.firstDateOf((int)disposeCalendar.get(1), (int)(disposeCalendar.get(2) + 1)), DateUtils.firstDateOf((int)(disposeCalendarMonth == 12 ? ++disposeCalendarYear : disposeCalendarYear), (int)(disposeCalendarMonth == 12 ? 1 : disposeCalendarMonth + 2)), DateUtils.firstDateOf((int)changedCalendar.get(1), (int)(changedCalendar.get(2) + 1)), DateUtils.firstDateOf((int)(changedCalendarMonth == 12 ? ++changedCalendarYear : changedCalendarYear), (int)(changedCalendarMonth == 12 ? 1 : changedCalendarMonth + 2))});
        if (CollectionUtils.isEmpty((Collection)results)) {
            return Collections.emptyList();
        }
        return results;
    }

    @Override
    public List<SameCtrlChgOrgEO> listSameCtrlChgOrgsByIds(List<String> ids) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_SAMECTRLCHGORG" + "  t \n  where " + SqlUtils.getConditionOfIdsUseOr(ids, (String)"t.id") + " \n ";
        List results = this.selectEntity(sql, new Object[0]);
        if (CollectionUtils.isEmpty((Collection)results)) {
            return Collections.emptyList();
        }
        return results;
    }

    @Override
    public List<String> listVirtualCodesByChangeCode(String changeOrgCode) {
        String sql = "  select t.virtualCode as virtualCode    from GC_SAMECTRLCHGORG  t \n  where t.changedCode=? \n";
        List results = this.selectEntity(sql, new Object[]{changeOrgCode});
        if (CollectionUtils.isEmpty((Collection)results)) {
            return Collections.emptyList();
        }
        return results.stream().map(SameCtrlChgOrgEO::getVirtualCode).collect(Collectors.toList());
    }

    @Override
    public List<SameCtrlChgOrgEO> listChgOrgsByDisposOrg(String disposOrg, Date fromDate, Date toDate) {
        return this.queryByDisposalDate("t.virtualParentCode='" + disposOrg + "'\n", fromDate, toDate);
    }

    @Override
    public List<SameCtrlChgOrgEO> listChgOrgsByAcquisitionOrg(String acquisitionOrg, Date fromDate, Date toDate) {
        return this.queryByDisposalDate("t.changedParentCode='" + acquisitionOrg + "'\n", fromDate, toDate);
    }

    @Override
    public List<SameCtrlChgOrgEO> listChgOrgsBySameParentOrg(String sameParentOrg, Date fromDate, Date toDate) {
        return this.queryByDisposalDate("t.sameParentCode='" + sameParentOrg + "'\n", fromDate, toDate);
    }

    @Override
    public List<SameCtrlChgOrgEO> listChgOrgsChangeOrg(String virtualCode, Date fromDate, Date toDate) {
        return this.queryByDisposalDate("t.virtualCode='" + virtualCode + "'\n", fromDate, toDate);
    }

    private List<SameCtrlChgOrgEO> queryByDisposalDate(String whereSql, Date fromDate, Date toDate) {
        String sql = "  select " + this.getAllFieldsSQL() + "  from " + "GC_SAMECTRLCHGORG" + "  t \n where t.changeDate >= ? and t.changeDate <= ?\n and t.deleteFlag = ?\n and %1$s \n order by t.changeDate desc\n";
        List eos = this.selectEntity(String.format(sql, whereSql), new Object[]{fromDate, toDate, 0});
        if (CollectionUtils.isEmpty((Collection)eos)) {
            return Collections.EMPTY_LIST;
        }
        return eos;
    }
}

