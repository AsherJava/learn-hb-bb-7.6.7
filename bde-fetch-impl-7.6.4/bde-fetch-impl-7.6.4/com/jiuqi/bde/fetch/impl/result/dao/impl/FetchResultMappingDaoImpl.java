/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 */
package com.jiuqi.bde.fetch.impl.result.dao.impl;

import com.jiuqi.bde.fetch.impl.result.dao.FetchResultMappingDao;
import com.jiuqi.bde.fetch.impl.result.entity.FetchResultMappingEO;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class FetchResultMappingDaoImpl
extends BaseDataCenterDaoImpl
implements FetchResultMappingDao {
    @Override
    public List<Integer> getRouteNumber() {
        String sql = "SELECT ROUTENUM FROM BDE_RESULT_MAPPING WHERE ROUTESTATUS = 1 ORDER BY ROUTENUM";
        return (List)this.getJdbcTemplate().query(sql, rs -> {
            ArrayList<Integer> routeList = new ArrayList<Integer>();
            if (rs.next()) {
                routeList.add(rs.getInt(1));
            }
            return routeList;
        });
    }

    @Override
    public FetchResultMappingEO getMappingEOByRouteNum(Integer routeNum) {
        String sql = "SELECT ROUTENUM, ROUTESTATUS FROM BDE_RESULT_MAPPING WHERE ROUTENUM = ? ";
        return (FetchResultMappingEO)this.getJdbcTemplate().query(sql, rs -> {
            if (rs.next()) {
                FetchResultMappingEO fetchResultMappingEO = new FetchResultMappingEO();
                fetchResultMappingEO.setRouteNum(rs.getInt(1));
                fetchResultMappingEO.setRouteStatus(rs.getInt(2));
                return fetchResultMappingEO;
            }
            return null;
        }, new Object[]{routeNum});
    }

    @Override
    public int changeRouteStart(Integer routeNum) {
        String sql = "UPDATE BDE_RESULT_MAPPING SET ROUTESTATUS=1 WHERE ROUTENUM = ? ";
        return this.getJdbcTemplate().update(sql, new Object[]{routeNum});
    }

    @Override
    public int changeRouteLock(Integer routeNum) {
        String sql = "UPDATE BDE_RESULT_MAPPING SET ROUTESTATUS=0 WHERE ROUTENUM = ? ";
        return this.getJdbcTemplate().update(sql, new Object[]{routeNum});
    }

    @Override
    public int changeRouteStop(Integer routeNum) {
        String sql = "UPDATE BDE_RESULT_MAPPING SET ROUTESTATUS=-1 WHERE ROUTENUM = ? ";
        return this.getJdbcTemplate().update(sql, new Object[]{routeNum});
    }

    @Override
    public int updateRouteStatus(FetchResultMappingEO fetchResultMappingEO) {
        String sql = "UPDATE BDE_RESULT_MAPPING SET ROUTESTATUS= ? WHERE ROUTENUM = ? ";
        return this.getJdbcTemplate().update(sql, new Object[]{fetchResultMappingEO.getRouteStatus().getStatus(), fetchResultMappingEO.getRouteNum()});
    }

    @Override
    public void insertFetchResultMapping(List<FetchResultMappingEO> fetchResultMappingEOList) {
        String sql = "INSERT INTO BDE_RESULT_MAPPING (ROUTENUM, ROUTESTATUS) VALUES ( ?, ? )";
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
        for (FetchResultMappingEO fetchResultMappingEO : fetchResultMappingEOList) {
            Object[] args = new Object[]{fetchResultMappingEO.getRouteNum(), fetchResultMappingEO.getRouteStatus().getStatus()};
            batchArgs.add(args);
        }
        this.getJdbcTemplate().batchUpdate(sql, batchArgs);
    }
}

