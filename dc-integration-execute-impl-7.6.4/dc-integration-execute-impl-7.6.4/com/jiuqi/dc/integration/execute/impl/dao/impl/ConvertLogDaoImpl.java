/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl
 *  com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor
 *  com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO
 *  com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.integration.execute.impl.dao.impl;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.dc.base.common.jdbc.dao.impl.BaseDataCenterDaoImpl;
import com.jiuqi.dc.base.common.jdbc.extractor.IntegerResultSetExtractor;
import com.jiuqi.dc.base.common.jdbc.extractor.StringResultSetExtractor;
import com.jiuqi.dc.integration.execute.client.vo.ConvertLogVO;
import com.jiuqi.dc.integration.execute.impl.dao.ConvertLogDao;
import com.jiuqi.dc.integration.execute.impl.domain.ConvertLogDO;
import com.jiuqi.dc.mappingscheme.impl.common.ModelTypeEnum;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ConvertLogDaoImpl
extends BaseDataCenterDaoImpl
implements ConvertLogDao {
    @Override
    public void insert(ConvertLogDO log) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append("(ID, DATASCHEMECODE, SCHEMETYPE, DATANAME, USERNAME, EXECUTESTATE, CREATETIME, EXECUTEPARAMS) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{log.getId(), log.getDataSchemeCode(), log.getSchemeType(), log.getDataName(), log.getUserName(), log.getExecuteState(), log.getCreateTime(), log.getExecuteParams()});
    }

    @Override
    public void batchInsert(List<ConvertLogDO> logs) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append("(ID, DATASCHEMECODE, SCHEMETYPE, DATANAME, USERNAME, EXECUTESTATE, CREATETIME, EXECUTEPARAMS) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        ArrayList<Object[]> params = new ArrayList<Object[]>();
        for (ConvertLogDO log : logs) {
            params.add(new Object[]{log.getId(), log.getDataSchemeCode(), log.getSchemeType(), log.getDataName(), log.getUserName(), log.getExecuteState(), log.getCreateTime(), log.getExecuteParams()});
        }
        OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), params);
    }

    @Override
    public List<ConvertLogVO> queryWithTaskLog(String dataSchemeCode, int page, int pageSize) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT C.ID, C.RUNNERID, C.DATASCHEMECODE, C.SCHEMETYPE, C.DATANAME, \n");
        sql.append("       C.USERNAME, C.EXECUTESTATE, C.CREATETIME, T.CREATETIME AS STARTTIME, T.ENDTIME AS ENDTIME, C.MESSAGE \n");
        sql.append("  FROM ").append("DC_LOG_DATACONVERT").append(" C \n");
        sql.append("  LEFT JOIN ").append("GC_LOG_TASKINFO").append(" T ON C.RUNNERID = T.ID \n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND C.SCHEMETYPE IN ('##BIZDATA##', '##BASEDATA##')");
        sql.append("   AND C.DATASCHEMECODE = ?  \n");
        sql.append(" ORDER BY C.CREATETIME DESC");
        String querySql = sql.toString();
        querySql = querySql.replace("##BIZDATA##", ModelTypeEnum.BIZDATA.getCode());
        querySql = querySql.replace("##BASEDATA##", ModelTypeEnum.BASEDATA.getCode());
        GcBizJdbcTemplate outJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        return outJdbcTemplate.query(outJdbcTemplate.getIDbSqlHandler().getPageSql(querySql, page, pageSize), (RowMapper)new BeanPropertyRowMapper(ConvertLogVO.class), new Object[]{dataSchemeCode});
    }

    @Override
    public int queryCount(String dataSchemeCode) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(1) \n");
        sql.append("  FROM ").append("DC_LOG_DATACONVERT").append(" C \n");
        sql.append("  LEFT JOIN ").append("GC_LOG_TASKINFO").append(" T ON C.RUNNERID = T.ID\n");
        sql.append(" WHERE 1 = 1 \n");
        sql.append("   AND C.SCHEMETYPE IN ('##BIZDATA##', '##BASEDATA##') \n");
        sql.append("   AND C.DATASCHEMECODE = ?  \n");
        String querySql = sql.toString();
        querySql = querySql.replace("##BIZDATA##", ModelTypeEnum.BIZDATA.getCode());
        querySql = querySql.replace("##BASEDATA##", ModelTypeEnum.BASEDATA.getCode());
        return (Integer)OuterDataSourceUtils.getJdbcTemplate().query(querySql, (ResultSetExtractor)new IntegerResultSetExtractor(), new Object[]{dataSchemeCode});
    }

    @Override
    public ExecuteStateVO getExecuteById(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) TOTAL, ");
        sql.append("       SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.FAILURE.getState()).append(" THEN 1 ELSE 0 END) AS FAILURE, ");
        sql.append("       SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.SUCCESS.getState()).append(" THEN 1 ELSE 0 END) AS SUCCESS, ");
        sql.append("       SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.UNEXECUTE.getState()).append(" THEN 1 ELSE 0 END) AS UNEXECUTE, ");
        sql.append("       SUM(CASE WHEN ITEM.EXECUTESTATE = ").append(DataHandleState.EXECUTING.getState()).append(" THEN 1 ELSE 0 END) AS EXECUTING ");
        sql.append("  FROM ").append("DC_LOG_DATACONVERT").append(" C \n");
        sql.append("  JOIN ").append("GC_LOG_TASKINFO").append(" ITEM ON ITEM.RUNNERID = C.RUNNERID \n");
        sql.append(" WHERE C.ID = ? \n");
        return (ExecuteStateVO)OuterDataSourceUtils.getJdbcTemplate().query(sql.toString(), rs -> {
            if (rs.next()) {
                ExecuteStateVO executeStateVO = new ExecuteStateVO();
                executeStateVO.setTotal(Integer.valueOf(rs.getInt(1)));
                executeStateVO.setFailed(Integer.valueOf(rs.getInt(2)));
                executeStateVO.setSuccess(Integer.valueOf(rs.getInt(3)));
                executeStateVO.setUnExecute(Integer.valueOf(rs.getInt(4)));
                executeStateVO.setExecuting(Integer.valueOf(rs.getInt(5)));
                return executeStateVO;
            }
            return null;
        }, new Object[]{id});
    }

    @Override
    public void updateExecuteById(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append("   SET EXECUTESTATE = ").append(DataHandleState.SUCCESS.getState()).append(" \n");
        sql.append(" WHERE ID = ? \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{id});
    }

    @Override
    public ConvertLogVO getConvertLogById(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SCHEMETYPE FROM ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append(" WHERE ID = ?");
        String schemeType = (String)this.query(sql.toString(), (ResultSetExtractor)new StringResultSetExtractor(), new Object[]{id});
        sql = new StringBuilder();
        sql.append("SELECT C.ID, C.DATASCHEMECODE, C.SCHEMETYPE, C.DATANAME, \n");
        sql.append("       C.USERNAME, C.EXECUTESTATE, C.CREATETIME, T.CREATETIME AS STARTTIME, T.ENDTIME AS ENDTIME, C.RUNNERID, C.MESSAGE \n");
        sql.append("  FROM ").append("DC_LOG_DATACONVERT").append(" C \n");
        sql.append("  LEFT JOIN ").append("GC_LOG_TASKINFO").append(" T ON C.RUNNERID = T.ID \n");
        sql.append("  WHERE 1 = 1 \n");
        sql.append("    AND C.ID = ? \n");
        sql.append("    AND C.SCHEMETYPE IN ('##BIZDATA##', '##BASEDATA##') \n");
        sql.append(" ORDER BY STARTTIME DESC");
        String querySql = sql.toString();
        querySql = querySql.replace("##BIZDATA##", ModelTypeEnum.BIZDATA.getCode());
        querySql = querySql.replace("##BASEDATA##", ModelTypeEnum.BASEDATA.getCode());
        return (ConvertLogVO)OuterDataSourceUtils.getJdbcTemplate().query(querySql, rs -> {
            if (rs.next()) {
                ConvertLogVO convertLogVO = new ConvertLogVO();
                convertLogVO.setId(rs.getString(1));
                convertLogVO.setDataSchemeCode(rs.getString(2));
                convertLogVO.setSchemeType(rs.getString(3));
                convertLogVO.setDataName(rs.getString(4));
                convertLogVO.setUserName(rs.getString(5));
                convertLogVO.setExecuteState(rs.getInt(6));
                convertLogVO.setCreateTime((Date)rs.getTimestamp(7));
                convertLogVO.setStartTime((Date)rs.getTimestamp(8));
                convertLogVO.setEndTime((Date)rs.getTimestamp(9));
                convertLogVO.setRunnerId(rs.getString(10));
                convertLogVO.setMessage(rs.getString(11));
                return convertLogVO;
            }
            return null;
        }, new Object[]{id});
    }

    @Override
    public int batchDeleteById(List<String> deleteIdList) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append("DC_LOG_DATACONVERT").append(" WHERE ID = ?");
        ArrayList<Object[]> params = new ArrayList<Object[]>();
        for (String id : deleteIdList) {
            params.add(new Object[]{id});
        }
        return OuterDataSourceUtils.getJdbcTemplate().batchUpdate(sql.toString(), params).length;
    }

    @Override
    public void updateRunnerIdById(String id, String runnerId) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append("   SET RUNNERID = ?");
        sql.append(" WHERE ID = ? \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{runnerId, id});
    }

    @Override
    public void updateMessageAndState(String id, String message, int state) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("DC_LOG_DATACONVERT").append(" \n");
        sql.append("   SET MESSAGE = ?, EXECUTESTATE = ? \n");
        sql.append(" WHERE ID = ? \n");
        OuterDataSourceUtils.getJdbcTemplate().update(sql.toString(), new Object[]{message, state, id});
    }
}

