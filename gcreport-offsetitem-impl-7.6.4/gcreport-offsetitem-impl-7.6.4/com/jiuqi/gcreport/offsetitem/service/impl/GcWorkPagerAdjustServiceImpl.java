/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.sql.da.RecordSet
 *  com.jiuqi.np.sql.da.RecordSetField
 *  com.jiuqi.np.sql.da.RecordSetFieldContainer
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.GcWorkPagerAdjustService;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.sql.da.RecordSet;
import com.jiuqi.np.sql.da.RecordSetField;
import com.jiuqi.np.sql.da.RecordSetFieldContainer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcWorkPagerAdjustServiceImpl
implements GcWorkPagerAdjustService {
    @Autowired
    private ConsolidatedTaskService taskService;

    @Override
    public List<GcOffSetVchrItemAdjustEO> queryGzOffsetEntry(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        Date date = yp.formatYP().getEndDate();
        Assert.isNotNull((Object)queryParamsVO.getOrgId());
        GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
        String orgTable = orgService.getCurrOrgType().getTable();
        if (null == org || org.getParentStr() == null) {
            return new ArrayList<GcOffSetVchrItemAdjustEO>();
        }
        String parentGuids = org.getParentStr();
        Set taskIds = this.taskService.getRelateTaskIdsByTaskId(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        int len = org.getParentStr().length() + orgService.getOrgCodeLength() + 1;
        StringBuffer selectFields = new StringBuffer(32);
        ArrayList<Serializable> paramList = new ArrayList<Serializable>();
        selectFields.append(SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)"record"));
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.unitId = bfUnitTable.code and bfUnitTable.validTime <? and bfUnitTable.invalidTime >= ? )\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppUnitId = dfUnitTable.code and dfUnitTable.validTime <? and dfUnitTable.invalidTime >= ?)\n");
        paramList.addAll(Arrays.asList(date, date, date, date));
        sql.append("where (\n");
        sql.append("substr(bfUnitTable.gcparents, 1,?) <> substr(dfUnitTable.gcparents, 1, ?) \n");
        sql.append("and bfUnitTable.parents like ? and dfUnitTable.parents like ?\n");
        paramList.addAll(Arrays.asList(len, len, parentGuids + "%", parentGuids + "%"));
        sql.append("and record.md_gcOrgType in ('NONE', ?)\n");
        sql.append(" )\n");
        sql.append("and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)taskIds, (String)"record.taskid")).append("\n");
        sql.append("and record.DATATIME = ?\n");
        sql.append("and record.offsetCurr = ?\n");
        sql.append("and (record.disableFlag <> ?  or record.disableFlag is null )\n");
        sql.append("order by record.mRecid \n");
        String orgTypeId = queryParamsVO.getOrgType();
        String currencyCode = queryParamsVO.getCurrencyUpperCase();
        paramList.addAll(Arrays.asList(orgTypeId, queryParamsVO.getPeriodStr(), currencyCode, 1));
        EntNativeSqlDefaultDao adjustDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
        return adjustDao.selectEntity(sql.toString(), paramList);
    }

    @Override
    public List<Map<String, Object>> queryDxOffsetEntry(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String orgTable = orgService.getCurrOrgType().getTable();
        Date date = yp.formatYP().getEndDate();
        int orgCodelen = orgService.getOrgCodeLength();
        Assert.isNotNull((Object)queryParamsVO.getOrgId());
        GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
        if (null == org || org.getParentStr() == null) {
            return new ArrayList<Map<String, Object>>();
        }
        Set taskIds = this.taskService.getRelateTaskIdsByTaskId(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        String parentGuids = org.getParentStr();
        int len = org.getGcParentStr().length() + orgCodelen + 1;
        String currency = queryParamsVO.getCurrencyUpperCase();
        StringBuffer selectFields = new StringBuffer(32);
        ArrayList<Serializable> paramList = new ArrayList<Serializable>();
        selectFields.append("substr(bfUnitTable.parents," + (parentGuids.length() + 2) + "," + orgCodelen + ") as UNITID,record.SUBJECTCODE,sum(record.offset_DEBIT-record.offset_Credit) as OFFSETDEBIT,(case when record.OFFSETSRCTYPE>=20 then 20 else 0 end) as OFFSETSRCTYPE ");
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.unitId = bfUnitTable.code and bfUnitTable.validTime <=? and bfUnitTable.invalidTime >= ? )\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppUnitId = dfUnitTable.code and dfUnitTable.validTime <=? and dfUnitTable.invalidTime >= ?)\n");
        paramList.addAll(Arrays.asList(date, date, date, date));
        sql.append("where (\n");
        sql.append("substr(bfUnitTable.gcparents, 1,?) <> substr(dfUnitTable.gcparents, 1, ?) \n");
        sql.append("and bfUnitTable.parents like ? and dfUnitTable.parents like ?\n");
        paramList.addAll(Arrays.asList(len, len, parentGuids + "%", parentGuids + "%"));
        sql.append("and record.md_gcOrgType in ('NONE', ?)\n");
        sql.append(" )\n");
        sql.append("and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)taskIds, (String)"record.taskid")).append("\n");
        sql.append("and record.DATATIME = ?\n");
        sql.append("and record.offsetCurr = ?\n");
        sql.append("and (record.disableFlag <> ?  or record.disableFlag is null) \n");
        sql.append(" group by substr(bfUnitTable.parents," + (parentGuids.length() + 2) + "," + orgCodelen + "),record.SUBJECTCODE,(case when record.OFFSETSRCTYPE>=20 then 20 else 0 end) \n");
        String orgTypeId = queryParamsVO.getOrgType();
        String currencyCode = queryParamsVO.getCurrencyUpperCase();
        paramList.addAll(Arrays.asList(orgTypeId, queryParamsVO.getPeriodStr(), currencyCode, 1));
        EntNativeSqlDefaultDao adjustDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
        List rs = adjustDao.selectMap(sql.toString(), paramList);
        rs.forEach(v -> {
            v.put("OFFSETDEBIT", String.valueOf(v.get("OFFSETDEBIT")));
            v.put("OFFSETCREDIT", "0");
            v.put("ORIENT", OrientEnum.D.getValue());
        });
        return rs;
    }

    @Override
    public List<Map<String, Object>> queryDetailsOffsetEntry(QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String orgTable = orgService.getCurrOrgType().getTable();
        Date date = yp.formatYP().getEndDate();
        int orgCodelen = orgService.getOrgCodeLength();
        Assert.isNotNull((Object)queryParamsVO.getOrgId());
        GcOrgCacheVO org = orgService.getOrgByCode(queryParamsVO.getOrgId());
        if (null == org || org.getParentStr() == null) {
            return new ArrayList<Map<String, Object>>();
        }
        Set taskIds = this.taskService.getRelateTaskIdsByTaskId(queryParamsVO.getTaskId(), queryParamsVO.getPeriodStr());
        String parentGuids = org.getParentStr();
        int len = org.getParentStr().length() + orgCodelen + 1;
        ArrayList<Serializable> paramList = new ArrayList<Serializable>();
        String currency = queryParamsVO.getCurrencyUpperCase();
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append("record.ID,record.MRECID,record.ELMMODE,record.ORIENT,record.gcBusinessTypeCode,record.ruleId,record.UNITID,record.OPPUNITID,record.SUBJECTCODE,record.offset_Credit as offsetCredit,record.offset_DEBIT as offsetDEBIT,record.DIFFC as DIFFC,record.DIFFD as DIFFD,record.MEMO,record.OFFSETSRCTYPE as SRCTYPE,record.SRCOFFSETGROUPID");
        HashSet otherShowColumns = new HashSet();
        otherShowColumns.addAll(queryParamsVO.getOtherShowColumns());
        for (String code : otherShowColumns) {
            selectFields.append(",record.").append(code);
        }
        StringBuffer sql = new StringBuffer(512);
        sql.append("select ").append(selectFields).append(" from ").append("GC_OFFSETVCHRITEM").append("  record\n");
        sql.append("left join ").append(orgTable).append("  bfUnitTable on (record.unitId = bfUnitTable.code and bfUnitTable.validTime <=? and bfUnitTable.invalidTime >= ? )\n");
        sql.append("left join ").append(orgTable).append("  dfUnitTable on (record.oppUnitId = dfUnitTable.code and dfUnitTable.validTime <=? and dfUnitTable.invalidTime >= ?)\n");
        paramList.addAll(Arrays.asList(date, date, date, date));
        sql.append("where (\n");
        sql.append("substr(bfUnitTable.gcparents, 1,?) <> substr(dfUnitTable.gcparents, 1, ?) \n");
        sql.append("and bfUnitTable.parents like ? and dfUnitTable.parents like ?\n");
        paramList.addAll(Arrays.asList(len, len, parentGuids + "%", parentGuids + "%"));
        sql.append("and record.md_gcOrgType in ('NONE', ?)\n");
        sql.append(" )\n");
        sql.append("and ").append(SqlUtils.getConditionOfMulStrUseOr((Collection)taskIds, (String)"record.taskid")).append("\n");
        sql.append("and record.DATATIME = ?\n");
        sql.append("and record.offsetCurr = ?\n");
        sql.append("and (record.disableFlag <> ?  or record.disableFlag is null )\n");
        String orgTypeId = queryParamsVO.getOrgType();
        String currencyCode = queryParamsVO.getCurrencyUpperCase();
        paramList.addAll(Arrays.asList(orgTypeId, queryParamsVO.getPeriodStr(), currencyCode, 1));
        EntNativeSqlDefaultDao adjustDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
        List rs = adjustDao.selectMap(sql.toString(), paramList);
        return rs;
    }

    private Map<String, Object> getObject(RecordSet recordSet) {
        Double diffd;
        Double diffc;
        HashMap<String, Object> result = new HashMap<String, Object>();
        RecordSetFieldContainer res = recordSet.getFields();
        for (RecordSetField re : res) {
            result.put(re.getName().toUpperCase(), re.getObject());
        }
        Integer orient = (Integer)result.get("ORIENT");
        if (null != orient) {
            if (orient == OrientEnum.C.getValue()) {
                Double credit = (Double)result.get("OFFSETCREDIT");
                result.put("OFFSETDEBIT", "");
                result.put("OFFSETCREDIT", NumberUtils.doubleToString((Double)credit));
            } else {
                Double debit = (Double)result.get("OFFSETDEBIT");
                result.put("OFFSETDEBIT", NumberUtils.doubleToString((Double)debit));
                result.put("OFFSETCREDIT", "");
            }
        }
        if (null == (diffc = (Double)result.get("DIFFC"))) {
            diffc = 0.0;
        }
        if (null == (diffd = (Double)result.get("DIFFD"))) {
            diffd = 0.0;
        }
        result.put("DIFF", NumberUtils.doubleToString((double)(diffc + diffd)));
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> querySplitSrcOffsetEntry(List<String> srcIds) {
        if (CollectionUtils.isEmpty(srcIds)) {
            return new HashMap<String, Object>();
        }
        TempTableCondition tempTableCondition = SqlUtils.getConditionOfIds(srcIds, (String)" record.ID");
        StringBuffer selectFields = new StringBuffer(32);
        selectFields.append(SqlUtils.getColumnsSqlByEntity(GcOffSetVchrItemAdjustEO.class, (String)"record"));
        StringBuffer sql = new StringBuffer(512);
        sql.append("select record.ID,record.OFFSETSRCTYPE from ").append("GC_OFFSETVCHRITEM").append(" record\n");
        sql.append("where ").append(tempTableCondition.getCondition()).append("\n");
        EntNativeSqlDefaultDao adjustDao = EntNativeSqlDefaultDao.newInstance((String)"GC_OFFSETVCHRITEM", GcOffSetVchrItemAdjustEO.class);
        try {
            List rs = adjustDao.selectMap(sql.toString(), new Object[0]);
            HashMap<String, Object> offsetSrcTypeMap = new HashMap<String, Object>();
            rs.forEach(v -> offsetSrcTypeMap.put(String.valueOf(v.get("ID")), String.valueOf(v.get("OFFSETSRCTYPE"))));
            HashMap<String, Object> hashMap = offsetSrcTypeMap;
            return hashMap;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)tempTableCondition.getTempGroupId());
        }
    }
}

