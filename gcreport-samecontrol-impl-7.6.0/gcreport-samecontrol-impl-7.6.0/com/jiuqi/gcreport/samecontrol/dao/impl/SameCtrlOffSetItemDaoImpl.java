/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.offsetitem.dao.impl.GcOffsetVchrQueryImpl;
import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlOffSetItemDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlOffSetItemDaoImpl
extends GcDbSqlGenericDAO<SameCtrlOffSetItemEO, String>
implements SameCtrlOffSetItemDao {
    private static final String SQL_QUERY_SAMECTRLOFFSETITEM = "select %s from GC_SAMECTRLOFFSETITEM  e \n %s \n";
    @Autowired
    private GcOffsetVchrQueryImpl offsetVchrQuery;

    public SameCtrlOffSetItemDaoImpl() {
        super(SameCtrlOffSetItemEO.class);
    }

    @Override
    public void checkItemDTO(SameCtrlOffSetItemEO item) {
        Assert.isNotEmpty((String)item.getInputUnitCode(), (String)"\u5408\u5e76\u5355\u4f4d\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getUnitChangeYear(), (String)"\u5355\u4f4d\u53d8\u52a8\u5e74\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)item.getSameCtrlChgId(), (String)"\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u8868\u7684id\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)item.getSameCtrlSrcType(), (String)"\u540c\u63a7\u6765\u6e90\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isTrue((!"NONE".equals(item.getOrgType()) ? 1 : 0) != 0, (String)"\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3aNONE", (Object[])new Object[0]);
        Assert.isTrue((!"NONE".equals(item.getInputUnitCode()) ? 1 : 0) != 0, (String)"\u5408\u5e76\u5355\u4f4d\u4e0d\u80fd\u4e3aNONE", (Object[])new Object[0]);
    }

    @Override
    public List<SameCtrlOffSetItemEO> rewriteDisposeParentOffset(GcActionParamsVO paramsVO, String currMergeUnitCode) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where i.inputUnitCode = ? \n  and i.orgType = ? \n  and i.schemeId = ? \n  and i.defaultPeriod = ? \n  and i.sameCtrlSrcType in('endExtract', 'endInput','endInvest','endInvestInit')\n";
        return this.selectEntity(sql, new Object[]{currMergeUnitCode, paramsVO.getOrgType(), paramsVO.getSchemeId(), paramsVO.getPeriodStr()});
    }

    @Override
    public List<SameCtrlOffSetItemEO> rewriteDisposeSameParentUnitOffset(GcActionParamsVO paramsVO, String currMergeUnitCode) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where i.sameParentCode = ? \n  and i.orgType = ? \n  and i.schemeId = ? \n  and i.defaultPeriod = ? \n  and i.sameCtrlSrcType in('endExtract', 'endInput','endInvest','endInvestInit')\n";
        return this.selectEntity(sql, new Object[]{currMergeUnitCode, paramsVO.getOrgType(), paramsVO.getSchemeId(), paramsVO.getPeriodStr()});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listBeginInputUnitParentsOffset(String defaultPeriod, String orgType, GcOrgCacheVO currMergeOrgCacheVO) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i join %1s  r on(i.changedParentCode=r.code) \n  where r.parents like ? \n  and i.inputUnitCode=?\n  and i.orgType = ? \n  and r.validtime<? and r.invalidTime>=? \n  and i.defaultPeriod = ? \n  and i.sameCtrlSrcType in('" + SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode() + "')\n";
        sql = String.format(sql, orgType);
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(null, defaultPeriod);
        Date orgvalidate = yearPeriodUtil.formatYP().getEndDate();
        return this.selectEntity(sql, new Object[]{currMergeOrgCacheVO.getParentStr() + "%", currMergeOrgCacheVO.getCode(), orgType, orgvalidate, orgvalidate, defaultPeriod});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listBeginInputUnitParentsOffsetLimitYear(String defaultPeriod, String orgType, GcOrgCacheVO currMergeOrgCacheVO) {
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)defaultPeriod);
        int unitChangeYear = periodUtil.getYear();
        String sql = " select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i join %1s  r on(i.changedParentCode=r.code) \n  where r.parents like ? \n  and i.inputUnitCode=?\n  and i.orgType = ? \n  and r.validtime<? and r.invalidTime>=? \n  and i.defaultPeriod = ? \n  and i.unitChangeYear = ? \n  and i.sameCtrlSrcType in('" + SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode() + "')\n";
        sql = String.format(sql, orgType);
        Date orgvalidate = periodUtil.getEndDate();
        return this.selectEntity(sql, new Object[]{currMergeOrgCacheVO.getParentStr() + "%", currMergeOrgCacheVO.getCode(), orgType, orgvalidate, orgvalidate, defaultPeriod, unitChangeYear});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listBeginSameParentUnitOffset(String defaultPeriod, String orgType, String currMergeUnitCode) {
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where i.sameParentCode = ? \n  and i.orgType = ? \n  and i.defaultPeriod = ? \n  and i.sameCtrlSrcType in('" + SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode() + "')\n";
        return this.selectEntity(sql, new Object[]{currMergeUnitCode, orgType, defaultPeriod});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listBeginSameParentUnitOffsetLimitYear(String defaultPeriod, String orgType, String currMergeUnitCode) {
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)defaultPeriod);
        int unitChangeYear = periodUtil.getYear();
        String sql = "  select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + " \n  from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where i.sameParentCode like ? \n  and i.orgType = ? \n  and i.defaultPeriod = ? \n  and i.unitChangeYear = ? \n  and i.sameCtrlSrcType in('" + SameCtrlSrcTypeEnum.BEGIN_ASSET.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INVEST.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_INPUT.getCode() + "', '" + SameCtrlSrcTypeEnum.BEGIN_LAST_YEAR.getCode() + "')\n";
        return this.selectEntity(sql, new Object[]{currMergeUnitCode, orgType, defaultPeriod, unitChangeYear});
    }

    @Override
    public Pagination<SameCtrlOffSetItemEO> listOffsets(SameCtrlOffsetCond cond, List<String> srcTypeCodes, List<String> inputUnitCodes) {
        Pagination page = new Pagination(null, Integer.valueOf(0), Integer.valueOf(cond.getPageNum()), Integer.valueOf(cond.getPageSize()));
        HashSet<String> mRecids = new HashSet<String>();
        int totalCount = this.queryMrecidsByInputUnit(cond, mRecids, srcTypeCodes, inputUnitCodes);
        page.setTotalElements(Integer.valueOf(totalCount));
        page.setContent(this.listSameCtrlOffsets(mRecids));
        return page;
    }

    @Override
    public int queryMrecidsByInputUnit(SameCtrlOffsetCond cond, Set<String> mRecids, List<String> srcTypeCodes, List<String> inputUnitCodes) {
        List childUnitCodes = cond.getChangedUnitCodeChilds();
        String sql = "  select i.mrecid \n  from GC_SAMECTRLOFFSETITEM  i \n  where i.taskId = ? \n  and i.schemeId = ? \n  and i.defaultPeriod = ? \n  and i.offsetCurr = ? \n  and i.sameCtrlChgId = ? \n  and i.orgType = ? \n  and " + SqlUtils.getConditionOfIdsUseOr(srcTypeCodes, (String)"i.sameCtrlSrcType") + " \n  and " + SqlUtils.getConditionOfIdsUseOr(inputUnitCodes, (String)"i.inputUnitCode") + " \n  and((" + SqlUtils.getConditionOfIds((Collection)childUnitCodes, (String)"i.unitCode").getCondition() + ") or (" + SqlUtils.getConditionOfIds((Collection)childUnitCodes, (String)"i.oppUnitCode").getCondition() + ")) \n  group by i.mrecid \n";
        int begin = -1;
        int range = -1;
        if (cond.getPageNum() > 0 || cond.getPageSize() > 0) {
            begin = (cond.getPageNum() - 1) * cond.getPageSize();
            range = cond.getPageNum() * cond.getPageSize();
        }
        List rs = this.selectFirstListByPaging(String.class, sql, begin, range, new Object[]{cond.getTaskId(), cond.getSchemeId(), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getSameCtrlChgId(), cond.getOrgType()});
        mRecids.addAll(rs);
        String countSql = String.format("select count(*) from ( %1$s ) t", sql);
        return this.count(countSql, new Object[]{cond.getTaskId(), cond.getSchemeId(), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getSameCtrlChgId(), cond.getOrgType()});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listOffsetsByParams(SameCtrlOffsetCond cond, List<String> srcTypeCodes) {
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"record");
        String sql = "  select  \n" + selectFields + "  from " + "GC_SAMECTRLOFFSETITEM" + "  record \n  where record.taskId = ? \n  and record.defaultPeriod = ? \n  and record.offsetCurr = ? \n  and record.sameCtrlChgId = ? \n  and record.orgType = ? \n  and " + SqlUtils.getConditionOfIdsUseOr(srcTypeCodes, (String)"record.sameCtrlSrcType") + " \n";
        if (DimensionUtils.isExistAdjust((String)cond.getTaskId())) {
            sql = sql + " and record.adjust = '";
            sql = sql + cond.getSelectAdjustCode();
            sql = sql + "'";
        }
        sql = sql + "order by record.mrecid desc,record.orient desc,record.subjectCode asc  \n";
        return this.selectEntity(sql, new Object[]{cond.getTaskId(), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getSameCtrlChgId(), cond.getOrgType()});
    }

    @Override
    public void deleteByCondition(SameCtrlOffsetCond cond, List<String> sameCtrlSrcTypeList) {
        String periodStr = cond.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr) || StringUtils.isEmpty((String)cond.getMergeUnitCode()) || CollectionUtils.isEmpty(sameCtrlSrcTypeList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(sameCtrlSrcTypeList, (String)"sameCtrlSrcType");
        String sql = " delete from GC_SAMECTRLOFFSETITEM   \n where taskId =? \n and schemeId=? \n and defaultPeriod=? \n and inputUnitCode=? \n and sameCtrlChgId=? \n and " + inSql + "\n";
        this.execute(sql, new Object[]{cond.getTaskId(), cond.getSchemeId(), periodStr, cond.getMergeUnitCode(), cond.getSameCtrlChgId()});
    }

    @Override
    public void deleteByUnitAndSrcType(SameCtrlOffsetCond cond, List<String> sameCtrlSrcTypeList) {
        String periodStr = cond.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr) || StringUtils.isEmpty((String)cond.getMergeUnitCode()) || CollectionUtils.isEmpty(sameCtrlSrcTypeList)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(sameCtrlSrcTypeList, (String)"sameCtrlSrcType");
        String sql = " delete from GC_SAMECTRLOFFSETITEM   \n where taskId =? \n and schemeId=? \n and defaultPeriod=? \n and inputUnitCode=? \n and " + inSql + "\n";
        this.execute(sql, new Object[]{cond.getTaskId(), cond.getSchemeId(), periodStr, cond.getMergeUnitCode()});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listSameCtrlOffsets(Collection<String> mRecids) {
        if (CollectionUtils.isEmpty(mRecids)) {
            return new ArrayList<SameCtrlOffSetItemEO>();
        }
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"record");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_SAMECTRLOFFSETITEM").append("  record\n");
        sql.append("where ").append(SqlUtils.getConditionOfIdsUseOr(mRecids, (String)" record.mrecid")).append("\n");
        sql.append("order by record.mrecid desc,record.orient desc,record.subjectCode asc\n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public List<SameCtrlOffSetItemEO> listSameCtrlOffsetsOrderByMrecidAndId(Collection<String> mRecids) {
        if (CollectionUtils.isEmpty(mRecids)) {
            return new ArrayList<SameCtrlOffSetItemEO>();
        }
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"record");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_SAMECTRLOFFSETITEM").append("  record\n");
        sql.append("where ").append(SqlUtils.getConditionOfIdsUseOr(mRecids, (String)" record.mrecid")).append("\n");
        sql.append("order by record.mrecid desc, id  \n");
        return this.selectEntity(sql.toString(), new Object[0]);
    }

    @Override
    public Map<String, Integer> listOffsetCountBySameCtrlChgId(List<String> sameCtrlChgIds) {
        String sql = "  select i.sameCtrlChgId as sameCtrlChgId,count(1) as offsetCount \n  from GC_SAMECTRLOFFSETITEM  i \n  where " + SqlUtils.getConditionOfIdsUseOr(sameCtrlChgIds, (String)"i.sameCtrlChgId") + " group by i.sameCtrlChgId \n";
        List results = this.selectMap(sql, new Object[0]);
        HashMap<String, Integer> sameCtrlChgId2OffsetCount = new HashMap<String, Integer>();
        for (Map map : results) {
            sameCtrlChgId2OffsetCount.put((String)map.get("sameCtrlChgId"), (Integer)map.get("offsetCount"));
        }
        return sameCtrlChgId2OffsetCount;
    }

    @Override
    public void deleteOffsetEntrysByMrecid(List<String> mrecids) {
        String sql = "\tdelete from GC_SAMECTRLOFFSETITEM   \n  where " + SqlUtils.getConditionOfIdsUseOr(mrecids, (String)"mrecid");
        this.execute(sql);
    }

    @Override
    public void deleteOffsetEntrysBySrcOffsetGroupId(Set<String> srcOffsetGroupIds, List<String> periodStrs) {
        String sql = "\tdelete from GC_SAMECTRLOFFSETITEM   \n  where " + SqlUtils.getConditionOfIdsUseOr(srcOffsetGroupIds, (String)"srcOffsetGroupId") + " \n";
        if (!CollectionUtils.isEmpty(periodStrs)) {
            sql = sql + " and " + SqlUtils.getConditionOfIdsUseOr(periodStrs, (String)"defaultPeriod") + " \n";
        }
        this.execute(sql);
    }

    @Override
    public List<SameCtrlOffSetItemEO> queryInputAdjustment(String mrecid) {
        String sql = "\tselect " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + "\n from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where i.mrecid = ? \n";
        return this.selectEntity(sql, new Object[]{mrecid});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listOffsetEntrysBySrcOffsetGroupIds(List<String> srcOffsetGroupIds) {
        String sql = "select " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"i") + "\n from " + "GC_SAMECTRLOFFSETITEM" + "  i \n  where " + SqlUtils.getConditionOfIdsUseOr(srcOffsetGroupIds, (String)"i.srcOffsetGroupId") + "\n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<SameCtrlOffSetItemEO> listOffsetsBySameCtrlChgId(SameCtrlOffsetCond cond, List<String> sameCtrlChgIds, List<String> srcTypeCodes, List<String> inputUnitCodes) {
        Set<String> mRecids = this.queryMrecidsBySameCtrlChgId(cond, sameCtrlChgIds, srcTypeCodes, inputUnitCodes);
        return null;
    }

    @Override
    public void deleteByRuleAndSrcType(SameCtrlOffsetCond cond, List<String> ruleList, List<String> sameCtrlSrcTypeList) {
        String periodStr = cond.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr) || StringUtils.isEmpty((String)cond.getMergeUnitCode()) || CollectionUtils.isEmpty(sameCtrlSrcTypeList)) {
            return;
        }
        String sql = " delete from GC_SAMECTRLOFFSETITEM   \n where taskId =? \n and schemeId=? \n and defaultPeriod=? \n and inputUnitCode=? \n and " + SqlUtils.getConditionOfIdsUseOr(ruleList, (String)"ruleTitle") + "\n and " + SqlUtils.getConditionOfIdsUseOr(sameCtrlSrcTypeList, (String)"sameCtrlSrcType") + "\n";
        this.execute(sql, new Object[]{cond.getTaskId(), cond.getSchemeId(), periodStr, cond.getMergeUnitCode()});
    }

    @Override
    public List<SameCtrlOffSetItemEO> listOffsetsByRuleAndSrcType(SameCtrlOffsetCond cond, List<String> ruleList, List<String> sameCtrlSrcTypeList) {
        String sql = "\tselect " + SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"t") + "\n from " + "GC_SAMECTRLOFFSETITEM" + "  t \n where t.taskId =? \n and t.schemeId=? \n and t.defaultPeriod=? \n and t.inputUnitCode=?\n and " + SqlUtils.getConditionOfIdsUseOr(ruleList, (String)"t.ruleTitle") + "\n and " + SqlUtils.getConditionOfIdsUseOr(sameCtrlSrcTypeList, (String)"t.sameCtrlSrcType") + "\n";
        return this.selectEntity(sql, new Object[]{cond.getTaskId(), cond.getSchemeId(), cond.getPeriodStr(), cond.getMergeUnitCode()});
    }

    public Set<String> queryMrecidsBySameCtrlChgId(SameCtrlOffsetCond cond, List<String> sameCtrlChgIds, List<String> srcTypeCodes, List<String> inputUnitCodes) {
        YearPeriodObject yearPeriodUtil = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodUtil);
        GcOrgCacheVO org = orgService.getOrgByCode(cond.getMergeUnitCode());
        String parents = org.getParentStr();
        int len = org.getGcParentStr().length() + GcOrgPublicTool.getInstance().getOrgCodeLength() + 1;
        String sql = "  select i.mrecid \n  from GC_SAMECTRLOFFSETITEM i \n  join ?  bfUnitTable on (i.unitCode = bfUnitTable.code) \n  join ?  dfUnitTable on (i.oppUnitCode = dfUnitTable.code) \n  join MD_GCSUBJECT  subject on (i.subjectcode = subject.code) \n  join gc_samectrlchgorg sameCtrl on (i.sameCtrlChgId = sameCtrl.id) \n  where substr(bfUnitTable.gcparents, 1," + len + ") <> substr(dfUnitTable.gcparents, 1," + len + ") \n  and bfUnitTable.parents like ? \n  and dfUnitTable.parents like ? \n  and bfUnitTable.validtime<? and bfUnitTable.invalidTime>=? \n  and dfUnitTable.validtime<? and dfUnitTable.invalidTime>=? \n  and subject.systemId=? \n  and i.taskId = ? \n  and i.schemeId = ? \n  and i.defaultPeriod =  ?\n  and i.offsetCurr = ? \n  and i.orgType = ? \n  and " + SqlUtils.getConditionOfIdsUseOr(sameCtrlChgIds, (String)"i.sameCtrlChgId") + " \n  and " + SqlUtils.getConditionOfIdsUseOr(srcTypeCodes, (String)"i.sameCtrlSrcType") + " \n  and " + SqlUtils.getConditionOfIdsUseOr(inputUnitCodes, (String)"i.inputUnitCode") + " \n  and (subject.attri=" + SubjectAttributeEnum.CASH.getValue() + " or subject.attri=" + SubjectAttributeEnum.PROFITLOSS.getValue() + ")\n  and (i.unitCode = sameCtrl.changedCode or i.oppUnitCode=sameCtrl.changedCode) \n  and sameCtrl.  group by i.mrecid \n";
        String orgType = cond.getOrgType();
        Date date = yearPeriodUtil.formatYP().getEndDate();
        List rs = this.selectFirstList(String.class, sql, new Object[]{Arrays.asList(orgType, orgType, parents, parents, date, date, date, date, cond.getSystemId(), cond.getTaskId(), cond.getSchemeId(), cond.getPeriodStr(), cond.getCurrencyCode(), orgType)});
        HashSet<String> result = new HashSet<String>(16);
        result.addAll(rs);
        return result;
    }

    @Override
    public List<Map<String, Object>> sumOffsetsBySameSubjectCode(SameCtrlOffsetCond cond, List<String> sameCtrlChgIds, List<String> srcTypeCodes, List<String> inputUnitCodes) {
        Set<String> mRecids = this.queryMrecidsBySameCtrlChgId(cond, sameCtrlChgIds, srcTypeCodes, inputUnitCodes);
        return this.sumOffsetValueGroupBySubjectcode(cond, mRecids);
    }

    private List<Map<String, Object>> sumOffsetValueGroupBySubjectcode(SameCtrlOffsetCond cond, Set<String> mRecids) {
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String orgTable = orgService.getCurrOrgType().getTable();
        String selectFields = "record.subjectCode,record.sameCtrlChgId,sum(record.offSetDebit) as debitValue,sum(record.offSetCredit) as creditValue,count(1) as OFFSETCOUNT";
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_SAMECTRLOFFSETITEM").append("  record\n");
        sql.append("join ").append(orgTable).append("  bfUnitTable on (record.unitid = bfUnitTable.code)\n");
        sql.append("join ").append(orgTable).append("  dfUnitTable on (record.oppunitid = dfUnitTable.code)\n");
        sql.append("group by record.subjectCode,record.sameCtrlChgId\n");
        return this.selectMap(sql.toString(), new Object[0]);
    }

    @Override
    public List<SameCtrlOffSetItemEO> queryOffsetRecordsByWhere(String[] columnNamesInDB, Object[] values) {
        StringBuffer conditionSql = new StringBuffer(128);
        for (int i = 0; i < values.length; ++i) {
            conditionSql.append(SqlUtils.getConditionOfObject((Object)values[i], (String)(" e." + columnNamesInDB[i]))).append(" and");
        }
        String whereSql = "";
        if (conditionSql.length() > 0) {
            conditionSql.setLength(conditionSql.length() - 4);
            whereSql = " where " + conditionSql;
        }
        whereSql = whereSql + " order by e.mrecid desc";
        String selectFields = SqlUtils.getColumnsSqlByTableDefine((String)"GC_SAMECTRLOFFSETITEM", (String)"e");
        String sql = String.format(SQL_QUERY_SAMECTRLOFFSETITEM, selectFields, whereSql);
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public int listOffsetsByParams(SameCtrlOffsetCond cond, Set<String> mRecids) {
        String sql = "  select i.mrecid \n  from GC_SAMECTRLOFFSETITEM  i \n  where i.taskId = ? \n  and i.defaultPeriod = ? \n  and i.offsetCurr = ? \n  and i.orgType = ? \n  and i.sameCtrlChgId = ? \n";
        if (DimensionUtils.isExistAdjust((String)cond.getTaskId())) {
            sql = sql + " and i.adjust = '";
            sql = sql + cond.getSelectAdjustCode();
            sql = sql + "'";
        }
        sql = sql + "  group by i.mrecid \n";
        int begin = -1;
        int range = -1;
        if (cond.getPageNum() > 0 || cond.getPageSize() > 0) {
            begin = (cond.getPageNum() - 1) * cond.getPageSize();
            range = cond.getPageNum() * cond.getPageSize();
        }
        List rs = this.selectFirstListByPaging(String.class, sql, begin, range, new Object[]{cond.getTaskId(), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getOrgType(), cond.getSameCtrlChgId()});
        mRecids.addAll(rs);
        String countSql = String.format("select count(*) from ( %1$s ) t", sql);
        return this.count(countSql, new Object[]{cond.getTaskId(), cond.getPeriodStr(), cond.getCurrencyCode(), cond.getOrgType(), cond.getSameCtrlChgId()});
    }

    @Override
    public int deleteSameCtrlByCondition(SameCtrlOffsetCond cond, List<String> sameCtrlSrcTypeList) {
        String periodStr = cond.getPeriodStr();
        if (StringUtils.isEmpty((String)periodStr) || StringUtils.isEmpty((String)cond.getMergeUnitCode())) {
            return 0;
        }
        String sql = " delete from GC_SAMECTRLOFFSETITEM   \n where taskId =? \n and defaultPeriod=? \n and offsetCurr=?  \n and sameCtrlChgId=? \n and orgType =? \n";
        if (!CollectionUtils.isEmpty(sameCtrlSrcTypeList)) {
            String inSql = SqlUtils.getConditionOfIdsUseOr(sameCtrlSrcTypeList, (String)"sameCtrlSrcType");
            sql = sql + " and " + inSql + "\n";
        }
        if (DimensionUtils.isExistAdjust((String)cond.getTaskId())) {
            sql = sql + " and adjust = '";
            sql = sql + cond.getSelectAdjustCode();
            sql = sql + "'";
        }
        return this.execute(sql, new Object[]{cond.getTaskId(), periodStr, cond.getCurrencyCode(), cond.getSameCtrlChgId(), cond.getOrgType()});
    }

    @Override
    public int[] deleteByMRecid(List<String> mRecids) {
        String sql = " delete from GC_SAMECTRLOFFSETITEM   \n where mRecid =? \n";
        List param = mRecids.stream().map(m -> Arrays.asList(m)).collect(Collectors.toList());
        return this.executeBatch(sql, param);
    }

    @Override
    public int countBySameCtrlChgId(String sameCtrlChgId) {
        String sql = " select count(*) from GC_SAMECTRLOFFSETITEM   \n where sameCtrlChgId =? \n";
        return this.count(sql, new Object[]{sameCtrlChgId});
    }
}

