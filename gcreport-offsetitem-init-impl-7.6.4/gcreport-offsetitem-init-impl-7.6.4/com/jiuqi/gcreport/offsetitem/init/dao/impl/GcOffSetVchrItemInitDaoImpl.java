/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.gcreport.offsetitem.init.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffsetElmModeEnum;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.utils.CalcLogUtil;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class GcOffSetVchrItemInitDaoImpl
extends GcDbSqlGenericDAO<GcOffSetVchrItemInitEO, String>
implements GcOffSetVchrItemInitDao {
    @Autowired
    private GcOffsetVchrQueryImpl offsetVchrQuery;
    private static final String SQL_QUERY_INIT_OFFSETVCHRITEM = "\tselect %s from GC_OFFSETVCHRITEM_INIT  e \n %s \n";

    public GcOffSetVchrItemInitDaoImpl() {
        super(GcOffSetVchrItemInitEO.class);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteByMrecid(Collection<String> mrecids, Integer acctYear, String currencyCode) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"mrecid");
        StringBuffer sql = new StringBuffer(64);
        sql.append("delete from ").append("GC_OFFSETVCHRITEM_INIT").append(" \n");
        sql.append("where ").append(tempTableCondition.getCondition());
        if (null != acctYear) {
            sql.append(" and acctYear = ").append(acctYear).append("\n");
        }
        if (!StringUtils.isEmpty((String)currencyCode)) {
            sql.append(" and offSetCurr = '").append(currencyCode).append("'\n");
        }
        try {
            this.execute(sql.toString());
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Pagination<Map<String, Object>> queryInitOffsetEntry(OffsetItemInitQueryParamsVO queryParamsVO) {
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        HashSet<String> mRecids = new HashSet<String>();
        queryParamsVO.setTaskId(null);
        int totalCount = this.queryMrecids(queryParamsVO, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setContent(new ArrayList());
            return page;
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)" record.mrecid");
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.MRECID,record.ORIENT,record.gcBusinessTypeCode,record.ruleId,record.UNITID,record.OPPUNITID,record.SUBJECTCODE,record.OFFSET_CREDIT as offsetCredit,record.OFFSET_DEBIT as offsetDEBIT,record.DIFFC as DIFFC,record.DIFFD as DIFFD,record.MEMO,record.OFFSETSRCTYPE as SRCTYPE,record.DISABLEFLAG as DISABLEFLAG,record.SRCOFFSETGROUPID as SRCOFFSETGROUPID, record.EFFECTTYPE as EFFECTTYPE, record.FETCHSETGROUPID as FETCHSETGROUPID,record.VCHRCODE,record.ELMMODE");
        for (String code : queryParamsVO.getOtherShowColumns()) {
            selectFields.append(",record.").append(code);
        }
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM_INIT").append(" record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        sql.append("order by record.mrecid desc, record.sortOrder \n");
        try {
            List datas = this.selectMap(sql.toString(), new Object[0]);
            page.setTotalElements(Integer.valueOf(totalCount));
            page.setContent(datas);
            Pagination pagination = page;
            return pagination;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Map<String, Object>> queryInitOffsetPartFieldEntry(OffsetItemInitQueryParamsVO queryParamsVO) {
        HashSet<String> mRecids = new HashSet<String>();
        this.queryMrecids(queryParamsVO, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            return Collections.emptyList();
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)" record.mrecid");
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID, record.MRECID, record.SRCOFFSETGROUPID, record.CREATETIME, record.MODIFYTIME");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM_INIT").append(" record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        try {
            List list = this.selectMap(sql.toString(), new Object[0]);
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
    public int queryMrecids(OffsetItemInitQueryParamsVO queryParamsVO, Set<String> mRecids) {
        if (null == mRecids) {
            mRecids = new HashSet<String>();
        }
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetVchrQuery.getQueryOrgPeriod(queryParamsVO.getPeriodStr()));
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String orgTable = orgService.getCurrOrgType().getTable();
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer head = new StringBuffer(32);
        StringBuffer whereSql = new StringBuffer(32);
        Date date = yp.formatYP().getEndDate();
        int orgCodeLen = orgService.getOrgCodeLength();
        if (!StringUtils.isEmpty((String)queryParamsVO.getOrgId())) {
            GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
            if (null == org || org.getParentStr() == null) {
                return 0;
            }
            if (org.getOrgKind() == GcOrgKindEnum.UNIONORG || !queryParamsVO.getAllowQuerySingleUnit().booleanValue()) {
                String parentGuids = org.getParentStr();
                String gcParentStr = org.getGcParentStr();
                this.initMergeUnitCondition(head, whereSql, params, parentGuids, gcParentStr, date, orgCodeLen);
            } else {
                this.initSingleUnitCondition(head, whereSql, params, org.getCode(), date);
            }
        } else {
            whereSql.append("where 1=1 ");
            this.offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
        }
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryParamsDTO);
        this.offsetVchrQuery.initUnitCondition(queryParamsDTO, whereSql, orgService);
        this.initPeriodCondition(queryParamsVO, params, whereSql);
        this.offsetVchrQuery.initOtherCondition(whereSql, queryParamsDTO);
        if (!CollectionUtils.isEmpty((Collection)queryParamsDTO.getSrcOffsetGroupIds())) {
            TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds((Collection)queryParamsDTO.getSrcOffsetGroupIds(), (String)"srcOffsetGroupId");
            whereSql.append(" and ").append(tempTableCondition.getCondition());
            queryParamsDTO.getTempGroupIdList().add(tempTableCondition.getTempGroupId());
        }
        StringBuffer sql = new StringBuffer(512);
        sql.append("select record.mrecid from ").append("GC_OFFSETVCHRITEM_INIT").append(" record\n");
        sql.append("join ").append(orgTable).append(" bfUnitTable on (record.unitid = bfUnitTable.code)\n");
        sql.append("join ").append(orgTable).append(" dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        sql.append(whereSql).append("\n");
        sql.append("group by record.mrecid \n");
        sql.append("order by record.mrecid desc \n");
        int begin = -1;
        int range = -1;
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            range = pageNum * pageSize;
        }
        try {
            List rs = this.selectFirstListByPaging(String.class, sql.toString(), begin, range, params.toArray());
            mRecids.addAll(rs);
            String countSql = String.format("select count(*) from ( %1$s ) t", sql);
            int n = this.count(countSql, params);
            return n;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    private void initPeriodCondition(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO, List<Object> params, StringBuffer whereSql) {
        whereSql.append(" and record.systemId=? ");
        params.add(offsetItemInitQueryParamsVO.getSystemId());
        whereSql.append(" and record.acctYear=? ");
        params.add(offsetItemInitQueryParamsVO.getAcctYear());
        String currentcy = offsetItemInitQueryParamsVO.getCurrencyUpperCase();
        whereSql.append(" and record.offsetCurr=? \n");
        params.add(currentcy);
    }

    private void initMergeUnitCondition(StringBuffer head, StringBuffer whereSql, List<Object> params, String parentGuids, String gcParentStr, Date date, int orgCodeLen) {
        int len = gcParentStr.length();
        params.add(gcParentStr + "%");
        params.add(gcParentStr + "%");
        whereSql.append(" where (substr(bfUnitTable.gcparents, 1, ").append(len + orgCodeLen + 1).append(" ) <> substr(dfUnitTable.gcparents, 1, ").append(len + orgCodeLen + 1).append(")\n");
        whereSql.append(" and bfUnitTable.gcparents like ?\n");
        whereSql.append(" and dfUnitTable.gcparents like ?)\n");
        this.offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
    }

    private void initSingleUnitCondition(StringBuffer head, StringBuffer whereSql, List<Object> params, String singleUnitId, Date date) {
        params.add(singleUnitId);
        params.add(singleUnitId);
        whereSql.append(" where (bfUnitTable.code=?\n");
        whereSql.append(" or dfUnitTable.code=?)\n");
        this.offsetVchrQuery.initValidtimeCondition(head, whereSql, params, date);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> getMrecidsBySameSrcId(Collection<String> mrecids) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"i.mrecid");
        String sql = " select distinct o.mrecid AS ID from GC_OFFSETVCHRITEM_INIT o\n where o.srcid in(\n   select i.srcid from GC_OFFSETVCHRITEM_INIT  i\n   where " + tempTableCondition.getCondition() + " and i.srcid is not null\n )\n";
        try {
            List rs = this.selectFirstList(String.class, sql, new Object[0]);
            HashSet<String> hashSet = new HashSet<String>(rs);
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
    public Map<String, Integer> countEveryBusinessType(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        HashMap<String, Integer> businessTypeCode2Count = new HashMap<String, Integer>();
        YearPeriodObject yp = new YearPeriodObject(null, this.offsetVchrQuery.getQueryOrgPeriod(offsetItemInitQueryParamsVO.getPeriodStr()));
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)offsetItemInitQueryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO organization = orgService.getOrgByID(offsetItemInitQueryParamsVO.getOrgId());
        if (null == organization || organization.getParents() == null) {
            return businessTypeCode2Count;
        }
        String parentGuids = organization.getParentStr();
        String gcParentStr = organization.getGcParentStr();
        String orgTable = orgService.getCurrOrgType().getTable();
        ArrayList<Object> params = new ArrayList<Object>();
        StringBuffer head = new StringBuffer(32);
        StringBuffer whereSql = new StringBuffer(32);
        Date date = yp.formatYP().getEndDate();
        if (organization.getOrgKind() == GcOrgKindEnum.UNIONORG || !offsetItemInitQueryParamsVO.getAllowQuerySingleUnit().booleanValue()) {
            int orgCodeLen = orgService.getOrgCodeLength();
            this.initMergeUnitCondition(head, whereSql, params, parentGuids, gcParentStr, date, orgCodeLen);
        } else {
            this.initSingleUnitCondition(head, whereSql, params, organization.getCode(), date);
        }
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(offsetItemInitQueryParamsVO, queryParamsDTO);
        this.offsetVchrQuery.initUnitCondition(queryParamsDTO, whereSql, orgService);
        this.initPeriodCondition(offsetItemInitQueryParamsVO, params, whereSql);
        this.offsetVchrQuery.initOtherCondition(whereSql, queryParamsDTO);
        List rs = null;
        String sql = "select record.gcbusinesstypecode AS CODE,count(1) AS NUM from( select record.gcbusinesstypecode,count(1) from GC_OFFSETVCHRITEM_INIT  record\n join " + orgTable + "  bfUnitTable on (record.unitid = bfUnitTable.code)\n join " + orgTable + "  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n" + whereSql + " group by record.mrecid,record.gcbusinesstypecode\n)  record group by record.gcbusinesstypecode\n";
        try {
            rs = this.selectMap(sql, params.toArray());
            int count = rs.size();
            if (count < 1) {
                HashMap<String, Integer> hashMap = businessTypeCode2Count;
                return hashMap;
            }
            rs.forEach(v -> {
                String businessTypeCode = String.valueOf(v.get("CODE"));
                Integer oneGroupCount = null == v.get("NUM") ? 0 : ConverterUtils.getAsInteger(v.get("NUM"));
                businessTypeCode2Count.put(businessTypeCode, oneGroupCount);
            });
            HashMap<String, Integer> hashMap = businessTypeCode2Count;
            return hashMap;
        }
        finally {
            queryParamsDTO.getTempGroupIdList().forEach(IdTemporaryTableUtils::deteteByGroupId);
        }
    }

    @Override
    public List<GcOffSetVchrItemInitEO> findByMrecidOrderBySortOrder(String mrecid) {
        String querySql = "\tselect %s from %s  e \n where e.mrecid = ? order by e.sortorder ";
        String sql = String.format(querySql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM_INIT", (String)"e"), "GC_OFFSETVCHRITEM_INIT");
        List itemInitEOs = this.selectEntity(sql, new Object[]{mrecid});
        return itemInitEOs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteBySrcOffsetGroupIds(List<String> srcOffsetGroupIds, int acctYear, String currencyCode, int offSetSrcType, String systemId) {
        if (srcOffsetGroupIds == null || srcOffsetGroupIds.size() <= 0) {
            return;
        }
        CalcLogUtil.getInstance().log(this.getClass(), "deleteBySrcOffsetGroupIds", new Object[]{srcOffsetGroupIds, acctYear, offSetSrcType});
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcOffsetGroupIds, (String)"srcOffsetGroupId");
        String sql = "delete from GC_OFFSETVCHRITEM_INIT  \n  where \n" + tempTableCondition.getCondition() + "and acctyear = ? \nand systemId = ? \n";
        if (!StringUtils.isEmpty((String)currencyCode)) {
            sql = sql + "and offSetCurr = '" + currencyCode + "'\n";
        }
        if (OffSetSrcTypeEnum.INVEST_OFFSET_ITEM_INIT.getSrcTypeValue() == offSetSrcType || OffSetSrcTypeEnum.FAIRVALUE_ADJUST_ITEM_INIT.getSrcTypeValue() == offSetSrcType || OffSetSrcTypeEnum.MODIFIED_INPUT.getSrcTypeValue() == offSetSrcType) {
            sql = sql + "and offSetSrcType = '" + offSetSrcType + "'\n";
        }
        try {
            this.execute(sql, new Object[]{acctYear, systemId});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffSetVchrItemInitEO> getInvestmentOffsetItemByMrecids(List<String> mrecids) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"v.mrecid");
        String sql = "select %s from GC_OFFSETVCHRITEM_INIT v \n    left join GC_UNIONRULE  r  \n      on v.ruleid = r.id  \n   where " + tempTableCondition.getCondition() + "     and  r.ruletype in ('" + RuleTypeEnum.DIRECT_INVESTMENT + "','" + RuleTypeEnum.INDIRECT_INVESTMENT + "','" + RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT + "') \n";
        try {
            List list = this.selectEntity(String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM_INIT", (String)"v")), new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public List<GcOffSetVchrItemInitEO> findByInvestment(GcOffsetItemQueryCondi condi, List<DimensionVO> otherDimension) {
        StringBuffer filedSql = new StringBuffer();
        for (DimensionVO vo : otherDimension) {
            filedSql.append(" t.").append(vo.getCode()).append(" as ").append(vo.getCode()).append(",");
        }
        String sql = "select " + filedSql + SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemInitEO.class, (String)"t") + " from " + "GC_OFFSETVCHRITEM_INIT" + "  t \n    left join GC_UNIONRULE  r  \n      on t.ruleid = r.id  \nwhere t.acctyear = ? and t.offSetCurr=? \n";
        if (condi.isFairValue) {
            sql = sql + "     and  r.ruletype = '" + RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT + "' \n";
            if (!StringUtils.isEmpty((String)condi.assetTitle)) {
                sql = sql + " and t.assetTitle = '" + condi.assetTitle + "'\n";
            }
        } else {
            sql = sql + "     and  r.ruletype in ('" + RuleTypeEnum.DIRECT_INVESTMENT + "','" + RuleTypeEnum.INDIRECT_INVESTMENT + "') \n";
        }
        if (!StringUtils.isEmpty((String)condi.systemId)) {
            sql = sql + "and t.systemId='" + condi.systemId + "'\n";
        }
        sql = sql + " and  t.srcOffsetGroupId = ? \norder by t.createtime,t.sortorder \n";
        return this.selectEntity(sql, new Object[]{condi.acctYear, condi.getOffSetCurr(), condi.srcOffsetGroupId});
    }

    @Override
    public void deleteByInvestment(String investUnit, String investedUnit, int acctYear) {
        if (StringUtils.isEmpty((String)investUnit) || StringUtils.isEmpty((String)investedUnit)) {
            return;
        }
        String sql = "delete from GC_OFFSETVCHRITEM_INIT \nwhere id in ( \nselect id from (  \n         select e.id from GC_OFFSETVCHRITEM_INIT  e \n         left join GC_UNIONRULE  r  \n         on e.ruleid = r.id  \n         where e.acctyear = ? \n         and  r.ruletype in ('" + RuleTypeEnum.DIRECT_INVESTMENT + "','" + RuleTypeEnum.INDIRECT_INVESTMENT + "','" + RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT + "') \n         and  e.unitid in ('" + investUnit + "','" + investedUnit + "') \n         and  e.oppunitid in ('" + investUnit + "','" + investedUnit + "') \n      ) t \n )\n";
        this.execute(sql, Arrays.asList(acctYear));
    }

    @Override
    public Pagination<GcOffSetVchrItemInitEO> queryOffsetingEntryEO(OffsetItemInitQueryParamsVO queryParamsVO) {
        Pagination page = new Pagination();
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.queryMrecids(queryParamsVO, mRecids);
        if (CollectionUtils.isEmpty(mRecids)) {
            page.setTotalElements(Integer.valueOf(0));
            page.setContent(new ArrayList());
            return page;
        }
        page.setTotalElements(Integer.valueOf(totalCount));
        page.setContent(this.queryOffsetingEntryEO(mRecids));
        page.setPageSize(Integer.valueOf(queryParamsVO.getPageSize()));
        page.setCurrentPage(Integer.valueOf(queryParamsVO.getPageNum()));
        return page;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<GcOffSetVchrItemInitEO> queryOffsetingEntryEO(Collection<String> mRecids) {
        if (CollectionUtils.isEmpty(mRecids)) {
            return new ArrayList<GcOffSetVchrItemInitEO>();
        }
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM_INIT", (String)"record");
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mRecids, (String)" record.mrecid");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM_INIT").append("  record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        sql.append("and record.elmmode <> " + OffsetElmModeEnum.MANAGE_INPUT_ITEM.getValue() + " \n");
        sql.append("order by record.mrecid desc,record.orient desc\n");
        try {
            List list = this.selectEntity(sql.toString(), new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public int deleteCarryOver(int year) {
        String sql = "delete from GC_OFFSETVCHRITEM_INIT  \n where acctYear=?\n  and offSetSrcType in(" + OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue() + "," + OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue() + ")\n";
        return this.execute(sql, new Object[]{year});
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> getSrcOffsetGroupIdsByMrecid(Collection<String> mrecids, String taskId, Integer acctYear, Integer acctPeriod, String orgTypeId, String currencyCode) {
        HashSet<String> srcOffsetGroupIdSet = new HashSet<String>();
        mrecids.remove(null);
        if (CollectionUtils.isEmpty(mrecids)) {
            return srcOffsetGroupIdSet;
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"e.mrecid");
        StringBuffer whereSql = new StringBuffer(128);
        whereSql.append("where ").append(tempTableCondition.getCondition());
        if (null != taskId) {
            whereSql.append(" and e.taskId = '").append(taskId).append("'\n");
        }
        if (null != acctYear) {
            whereSql.append(" and e.acctYear = ").append(acctYear).append("\n");
        }
        if (null != acctPeriod) {
            whereSql.append(" and e.acctPeriod = ").append(acctPeriod).append("\n");
        }
        if (!StringUtils.isEmpty((String)currencyCode)) {
            whereSql.append(" and e.offSetCurr = '").append(currencyCode).append("'\n");
        }
        String sql = String.format(SQL_QUERY_INIT_OFFSETVCHRITEM, "e.srcOffsetGroupId", whereSql);
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
    public Collection<String> getMrecidsBySrcOffsetGroupId(Collection<String> srcOffsetGroupId, String taskId, Integer acctYear, Integer acctPeriod, String orgTypeId, String unitId, String currencyCode) {
        HashSet<String> mrecidSet = new HashSet<String>();
        srcOffsetGroupId.remove(null);
        if (CollectionUtils.isEmpty(srcOffsetGroupId)) {
            return mrecidSet;
        }
        Assert.isNotNull((Object)acctYear);
        StringBuffer whereSql = new StringBuffer(64);
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcOffsetGroupId, (String)"e.srcOffsetGroupId");
        whereSql.append("where ").append(tempTableCondition.getCondition());
        if (null != taskId) {
            whereSql.append(" and e.taskId = '").append(taskId).append("'\n");
        }
        if (null != acctYear) {
            whereSql.append(" and e.acctYear = ").append(acctYear).append("\n");
        }
        if (!StringUtils.isEmpty((String)currencyCode)) {
            whereSql.append(" and e.offSetCurr = '").append(currencyCode).append("'\n");
        }
        if (null != orgTypeId) {
            whereSql.append(" and e.md_gcorgtype = '").append(orgTypeId).append("'\n");
        }
        if (null != unitId) {
            whereSql.append(" and (e.unitId = '").append(unitId).append("' or e.oppunitId = '").append(unitId).append("')\n");
        }
        try {
            String sql = String.format(SQL_QUERY_INIT_OFFSETVCHRITEM, "e.mrecid", whereSql);
            List rs = this.selectFirstList(String.class, sql, new Object[0]);
            mrecidSet.addAll(rs);
            HashSet<String> hashSet = mrecidSet;
            return hashSet;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public boolean hasOffsetRecordByUnitAndRuleType(String srcOffsetGroupId, Integer acctYear, boolean isTz) {
        String sql = "select count(v.id) from GC_OFFSETVCHRITEM_INIT  v \n    left join GC_UNIONRULE  r  \n      on v.ruleid = r.id  \n    where 1=1 and v.acctyear = ?  \n";
        sql = isTz ? sql + "     and  r.ruletype in ('" + RuleTypeEnum.DIRECT_INVESTMENT + "','" + RuleTypeEnum.INDIRECT_INVESTMENT + "') \n" : sql + " and  r.ruletype = '" + RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT + "' \n";
        sql = sql + " and  v.srcoffsetgroupId=? ";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM_INIT", (String)"v"));
        return (Integer)this.selectFirst(Integer.class, sql, new Object[]{acctYear, srcOffsetGroupId}) > 0;
    }

    @Override
    public void changeAssetTitle(String unitId, String oppUnitId, Integer acctYear, String oldAssetTitle, String newAssetTitle) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("update ").append("GC_OFFSETVCHRITEM_INIT  t \n");
        sql.append(" set  assetTitle = ? \n");
        sql.append(" where t.acctyear = ?  \n");
        sql.append(" and  t.unitid in ('" + unitId + "','" + oppUnitId + "')  \n");
        sql.append(" and  t.oppunitid in ('" + unitId + "','" + oppUnitId + "')  \n");
        sql.append(" and t.assetTitle = ?  \n");
        this.execute(sql.toString(), new Object[]{acctYear, newAssetTitle, oldAssetTitle});
    }

    @Override
    public int deleteCarryOverByMergeCodes(int year, String systemId, List<GcOrgCacheVO> mergeOrgs, String periodStr, String orgType) {
        YearPeriodDO yearPeriodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        Date date = yearPeriodUtil.getEndDate();
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yearPeriodUtil);
        int orgCodeLength = orgService.getOrgCodeLength();
        ArrayList<Serializable> paramList = new ArrayList<Serializable>();
        paramList.addAll(Arrays.asList(date, date, date, date, year, systemId));
        String sql = " delete from GC_OFFSETVCHRITEM_INIT \n  where id in (select offs.id as id from ( select e.id from GC_OFFSETVCHRITEM_INIT  e \n   join (select org.code as unitCode,org.parents as pars, org.gcparents as gcparents, org.validTime as valid,org.invalidTime as invalid from " + orgType + "  org )   unit on e.unitid = unit.unitCode and unit.valid <= ? and unit.invalid >= ? \n   join (select org1.code as oppunitCode,org1.parents as opppars,org1.gcparents as oppgcparents, org1.validTime as oppvalid,org1.invalidTime as oppinvalid from " + orgType + "  org1)  oppunit on e.oppunitid = oppunit.oppunitCode and oppunit.oppvalid <= ? and oppunit.oppinvalid >= ? \n  where e.acctYear = ? \n  and e.offSetSrcType in(" + OffSetSrcTypeEnum.CARRY_OVER.getSrcTypeValue() + "," + OffSetSrcTypeEnum.CARRY_OVER_FAIRVALUE.getSrcTypeValue() + ")\n  and e.systemId=? \n  and (";
        for (int i = 0; i < mergeOrgs.size(); ++i) {
            String mergeParents = mergeOrgs.get(i).getParentStr();
            int len = mergeOrgs.get(i).getGcParentStr().length();
            if (i != 0) {
                sql = sql + " or ";
            }
            sql = sql + " (";
            sql = sql + "unit.pars like '" + mergeParents + "%' \n";
            sql = sql + " and oppunit.opppars like '" + mergeParents + "%' \n";
            sql = sql + " and substr(unit.gcparents, 1, " + (len + orgCodeLength + 1) + ") <> substr(oppunit.oppgcparents, 1," + (len + orgCodeLength + 1) + ")";
            sql = sql + ") \n";
        }
        sql = sql + ") \n   )  offs) \n";
        return this.execute(sql, paramList);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateOffsetInitDisabledFlag(List<String> mrecids, boolean isDisabled) {
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(mrecids, (String)"offset.mrecid");
        StringBuffer sql = new StringBuffer(128);
        sql.append("update ").append("GC_OFFSETVCHRITEM_INIT  offset\n");
        sql.append(" set disableFlag=? \n");
        sql.append(" where  \n");
        sql.append(tempTableCondition.getCondition()).append("\n");
        try {
            this.execute(sql.toString(), new Object[]{isDisabled ? 1 : 0});
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }

    @Override
    public void deleteOffsetOfFvchBill(String investUnit, String investedUnit, Integer acctYear) {
        if (StringUtils.isEmpty((String)investUnit) || StringUtils.isEmpty((String)investedUnit)) {
            return;
        }
        String sql = "delete from GC_OFFSETVCHRITEM_INIT \nwhere id in ( \nselect id from (  \n         select e.id from GC_OFFSETVCHRITEM_INIT  e \n         left join GC_UNIONRULE  r  \n         on e.ruleid = r.id  \n         where r.inittypeflag = ? and e.acctyear = ? \n         and  r.ruletype  = '" + RuleTypeEnum.PUBLIC_VALUE_ADJUSTMENT.getCode() + "'\n         and  e.unitid in ('" + investUnit + "','" + investedUnit + "') \n         and  e.oppunitid in ('" + investUnit + "','" + investedUnit + "') \n      ) t \n )\n";
        this.execute(sql, Arrays.asList(1, acctYear));
    }

    @Override
    public List<GcOffSetVchrItemInitEO> listBySystemId(String systemId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_OFFSETVCHRITEM_INIT", (String)"t") + " from " + "GC_OFFSETVCHRITEM_INIT" + "  t \n  where t.systemId = ? \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    public Serializable save(GcOffSetVchrItemInitEO entity) throws DataAccessException {
        this.beforeInterceptor(entity, Calendar.getInstance().getTime());
        return super.save((DefaultTableEntity)entity);
    }

    public int update(GcOffSetVchrItemInitEO entity) {
        this.beforeInterceptor(entity, Calendar.getInstance().getTime());
        return super.update((DefaultTableEntity)entity);
    }

    public int updateSelective(GcOffSetVchrItemInitEO entity) {
        this.beforeInterceptor(entity, Calendar.getInstance().getTime());
        return super.updateSelective((BaseEntity)entity);
    }

    public int[] updateBatch(List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOS) throws DataAccessException {
        Date time = Calendar.getInstance().getTime();
        gcOffSetVchrItemInitEOS.stream().forEach(entity -> this.beforeInterceptor((GcOffSetVchrItemInitEO)((Object)entity), time));
        return super.updateBatch(gcOffSetVchrItemInitEOS);
    }

    public int add(GcOffSetVchrItemInitEO entity) {
        this.beforeInterceptor(entity, Calendar.getInstance().getTime());
        return super.add((BaseEntity)entity);
    }

    public int[] addBatch(List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOS) throws DataAccessException {
        Date time = Calendar.getInstance().getTime();
        gcOffSetVchrItemInitEOS.stream().forEach(entity -> this.beforeInterceptor((GcOffSetVchrItemInitEO)((Object)entity), time));
        return super.addBatch(gcOffSetVchrItemInitEOS);
    }

    public int addSelective(GcOffSetVchrItemInitEO entity) {
        this.beforeInterceptor(entity, Calendar.getInstance().getTime());
        return super.addSelective((BaseEntity)entity);
    }

    private void beforeInterceptor(GcOffSetVchrItemInitEO entity, Date time) {
        entity.setModifyTime(time);
        if (StringUtils.isEmpty((String)entity.getSrcOffsetGroupId())) {
            entity.setSrcOffsetGroupId(entity.getmRecid());
        }
    }
}

