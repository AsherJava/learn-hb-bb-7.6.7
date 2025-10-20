/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.dao.impl;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultUnitVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.impl.dao.DataCheckResultDAO;
import com.jiuqi.gcreport.efdcdatacheck.impl.entity.EfdcCheckResultEO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class DataCheckResultDAOImp
extends GcDbSqlGenericDAO<EfdcCheckResultEO, String>
implements DataCheckResultDAO {
    public DataCheckResultDAOImp() {
        super(EfdcCheckResultEO.class);
    }

    @Override
    public void deleteBeforeCreateTime(Date createTime) {
        String sql = "  delete from GC_EFDCCHECKRESULT   where createTime<? \n";
        this.execute(sql, new Object[]{createTime});
    }

    @Override
    public void deleteByAsynTaskId(String asynTaskId) {
        String sql = "  delete from GC_EFDCCHECKRESULT   where asynTaskId=? \n";
        this.execute(sql, new Object[]{asynTaskId});
    }

    @Override
    public List<EfdcCheckResultEO> queryResultByAsynTaskId(GcBatchEfdcQueryParam efdcQueryParam) {
        String orgId = efdcQueryParam.getOrgId();
        int firstResult = efdcQueryParam.getPageSize() * efdcQueryParam.getPageNum();
        int maxResults = efdcQueryParam.getPageSize();
        String sql = "  select %s \n  from GC_EFDCCHECKRESULT  d \n   where d.asynTaskId=? \n";
        if (maxResults != -1 && !StringUtils.isEmpty((String)orgId)) {
            sql = sql + "and d.orgId=? ";
        }
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_EFDCCHECKRESULT", (String)"d"));
        String asynTaskID = efdcQueryParam.getAsynTaskID();
        if (maxResults == -1) {
            return this.selectEntity(sql, new Object[]{asynTaskID});
        }
        return this.selectEntityByPaging(sql, firstResult, maxResults, new Object[]{asynTaskID, orgId});
    }

    @Override
    public List<EfdcCheckResultUnitVO> queryUnitsByAsynTaskId(String asynTaskId) {
        String sql = "  select d.orgId AS ORGID,count(1) AS ORGCOUNT \n  from GC_EFDCCHECKRESULT  d \n   where d.asynTaskId=? \n   group by d.orgId \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByTableDefine((String)"GC_EFDCCHECKRESULT", (String)"d"));
        ArrayList<EfdcCheckResultUnitVO> checkResultUnitVOs = new ArrayList<EfdcCheckResultUnitVO>();
        List datas = this.selectMap(sql, new Object[]{asynTaskId});
        for (Map d : datas) {
            EfdcCheckResultUnitVO resultUnitVO = new EfdcCheckResultUnitVO();
            resultUnitVO.setKey((String)d.get("ORGID"));
            resultUnitVO.setCount(ConverterUtils.getAsIntValue(d.get("ORGCOUNT")));
            checkResultUnitVOs.add(resultUnitVO);
        }
        return checkResultUnitVOs;
    }

    @Override
    public Map<String, Integer> queryCountGroupByOrgId(String asynTaskID) {
        String sql = "  select count(1) as totalCount,d.orgId,d.formkey \n  from GC_EFDCCHECKRESULT  d \n   where d.asynTaskId=? \n   group by d.orgId,d.formkey \n";
        HashMap<String, Integer> orgId2Count = new HashMap<String, Integer>();
        List datas = this.selectMap(sql, new Object[]{asynTaskID});
        for (Map d : datas) {
            int count = ConverterUtils.getAsIntValue(d.get("TOTALCOUNT"));
            String orgId = String.valueOf(d.get("ORGID"));
            String formKey = String.valueOf(d.get("FORMKEY"));
            orgId2Count.put(orgId + formKey, count);
        }
        return orgId2Count;
    }

    @Override
    public Map<String, Integer> queryCountGroupByOrgIdAndCurrency(String asynTaskID) {
        String sql = "  select count(1) as totalCount,d.orgId,d.formkey, d.currency \n  from GC_EFDCCHECKRESULT  d \n   where d.asynTaskId=? \n   group by d.orgId,d.formkey, d.currency\n";
        HashMap<String, Integer> orgId2Count = new HashMap<String, Integer>();
        List datas = this.selectMap(sql, new Object[]{asynTaskID});
        for (Map d : datas) {
            int count = ConverterUtils.getAsIntValue(d.get("TOTALCOUNT"));
            String orgId = String.valueOf(d.get("ORGID"));
            String formKey = String.valueOf(d.get("FORMKEY"));
            String currency = String.valueOf(d.get("CURRENCY"));
            orgId2Count.put(orgId + currency + formKey, count);
        }
        return orgId2Count;
    }
}

