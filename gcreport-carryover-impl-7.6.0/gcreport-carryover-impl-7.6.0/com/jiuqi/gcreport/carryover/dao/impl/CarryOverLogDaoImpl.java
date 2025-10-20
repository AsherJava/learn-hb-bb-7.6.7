/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.carryover.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.carryover.dao.CarryOverLogDao;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class CarryOverLogDaoImpl
extends GcDbSqlGenericDAO<CarryOverLogEO, String>
implements CarryOverLogDao {
    public CarryOverLogDaoImpl() {
        super(CarryOverLogEO.class);
    }

    @Override
    public CarryOverLogEO queryGcCarryOverInfoEO(String taskId, String schemeId, Integer acctYear, String code) {
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(CarryOverLogEO.class, (String)"e") + "\n from " + "GC_CARRYOVER_LOG" + " e \n where e.taskId = ?\n and e.schemeId = ?\n and e.acctYear = ?\n";
        if (!StringUtils.isEmpty((String)code)) {
            sql = sql + " and e.typeCode = '" + code + "'\n";
        }
        sql = sql + " ORDER BY ENDTIME DESC\n";
        List carryOverLogEOS = this.selectEntity(sql, new Object[]{taskId, schemeId, acctYear});
        return carryOverLogEOS.size() > 0 ? (CarryOverLogEO)((Object)carryOverLogEOS.get(0)) : null;
    }

    @Override
    public List<Map<String, Object>> listLogInfoBySchemeId(String schemeId) {
        if (StringUtils.isEmpty((String)schemeId)) {
            return Collections.emptyList();
        }
        String sql = " select " + SqlUtils.getColumnsSqlByEntity(CarryOverLogEO.class, (String)"e") + "\n from " + "GC_CARRYOVER_LOG" + " e \n where e.carryOverSchemeId = ? \n order by e.endTime desc";
        return this.selectMap(sql, new Object[]{schemeId});
    }

    @Override
    public int countByGroupId(String taskGroupId) {
        String sql = "select count(*) from GC_CARRYOVER_LOG where GROUP_ID = ?";
        return this.count(sql, new Object[]{taskGroupId});
    }

    @Override
    public List<CarryOverLogEO> listByIds(Set<String> ids) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_CARRYOVER_LOG", (String)"t");
        TempTableCondition condition = SqlUtils.getConditionOfIds(ids, (String)"t.ID");
        String sql = "select " + allFieldsSQL + " from " + "GC_CARRYOVER_LOG" + " t where " + condition.getCondition();
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public Pagination<Map<String, Object>> listLogInfo(QueryParamsVO queryParamsVO, Map<String, Object> otherParams) {
        Pagination pagination = new Pagination(null, Integer.valueOf(0), Integer.valueOf(queryParamsVO.getPageNum()), Integer.valueOf(queryParamsVO.getPageSize()));
        ArrayList<String> params = new ArrayList<String>();
        String allField = SqlUtils.getColumnsSqlByEntity(CarryOverLogEO.class, (String)"e");
        StringBuilder sql = new StringBuilder();
        sql.append("select").append(allField).append("\n from GC_CARRYOVER_LOG e \n");
        sql.append("where \n");
        if (!StringUtils.isEmpty((String)queryParamsVO.getCarryOverSchemeId())) {
            sql.append("e.carryOverSchemeId = ? \n");
            params.add(queryParamsVO.getCarryOverSchemeId());
        }
        if (otherParams.containsKey("authOrg")) {
            List authOrg = (List)otherParams.get("authOrg");
            if (CollectionUtils.isEmpty((Collection)authOrg)) {
                return pagination;
            }
            String collect = authOrg.stream().map(str -> "e.UNITINFO like '" + str + "'").collect(Collectors.joining(" or "));
            sql.append("and (").append(collect).append(")\n");
        }
        sql.append("order by e.createTime desc, startTime desc");
        String countSql = String.format("select count(*) from ( %1$s )  t", sql.toString());
        int count = this.count(countSql, params);
        if (count < 1) {
            pagination.setContent(new ArrayList());
            return pagination;
        }
        int begin = -1;
        int end = -1;
        int pageNum = queryParamsVO.getPageNum();
        int pageSize = queryParamsVO.getPageSize();
        if (pageNum > 0 && pageSize > 0) {
            begin = (pageNum - 1) * pageSize;
            end = pageNum * pageSize;
        }
        List data = this.selectMapByPaging(sql.toString(), begin, end, params);
        pagination.setContent(data);
        pagination.setTotalElements(Integer.valueOf(count));
        return pagination;
    }
}

