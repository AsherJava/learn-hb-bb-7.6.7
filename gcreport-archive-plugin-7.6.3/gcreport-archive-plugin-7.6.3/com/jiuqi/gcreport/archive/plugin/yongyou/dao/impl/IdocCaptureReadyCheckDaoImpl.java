/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl;

import com.jiuqi.gcreport.archive.plugin.yongyou.dao.IdocCaptureReadyCheckDao;
import com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl.YongYouArichiveBaseDaoImpl;
import com.jiuqi.gcreport.archive.plugin.yongyou.entity.IdocCaptureReadyCheckEo;
import java.util.ArrayList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IdocCaptureReadyCheckDaoImpl
extends YongYouArichiveBaseDaoImpl
implements IdocCaptureReadyCheckDao {
    @Override
    public void save(IdocCaptureReadyCheckEo idocCaptureReadyCheckEo) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO %s \n");
        sql.append("       (ID,ORGCODE,BBORGCODE,ORGNAME, ACCOUNTYEAR, ACCOUNTMONTH, PERIOD, PERIODINDEX, ARCHIVETYPE, READYSTATUS, ERRORMSG, TS) \n");
        sql.append("VALUES (? , ?, ?,?,?, ?, ?, ?, ?, ?, ?, ?) \n");
        Object[] objectArray = new Object[1];
        objectArray[0] = "IDOC_CAPTURE_READY_CHECK";
        String sqlResult = String.format(sql.toString(), objectArray);
        ArrayList<Object> queryParams = new ArrayList<Object>();
        queryParams.add(idocCaptureReadyCheckEo.getId());
        queryParams.add(idocCaptureReadyCheckEo.getOrgCode());
        queryParams.add(idocCaptureReadyCheckEo.getBbOrgCode());
        queryParams.add(idocCaptureReadyCheckEo.getOrgName());
        queryParams.add(idocCaptureReadyCheckEo.getAccountYear());
        queryParams.add(idocCaptureReadyCheckEo.getAccountMonth());
        queryParams.add(idocCaptureReadyCheckEo.getPeriod());
        queryParams.add(idocCaptureReadyCheckEo.getPeriodIndex());
        queryParams.add(idocCaptureReadyCheckEo.getArchiveType());
        queryParams.add(idocCaptureReadyCheckEo.getReadyStatus());
        queryParams.add(idocCaptureReadyCheckEo.getErrorMsg());
        queryParams.add(idocCaptureReadyCheckEo.getTs());
        jdbcTemplate.update(sqlResult.toString(), queryParams.toArray());
    }

    @Override
    public IdocCaptureReadyCheckEo get(String prepareInfoId) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORGCODE, BBORGCODE, ORGNAME, ACCOUNTYEAR, ACCOUNTMONTH, PERIOD, PERIODINDEX, ARCHIVETYPE, READYSTATUS, ERRORMSG, TS  FROM %s \n");
        sql.append(" WHERE ID = ? \n");
        String sqlResult = String.format(sql.toString(), "IDOC_CAPTURE_READY_CHECK");
        return (IdocCaptureReadyCheckEo)((Object)jdbcTemplate.queryForObject(sqlResult.toString(), new Object[]{prepareInfoId}, (rs, rowNum) -> {
            IdocCaptureReadyCheckEo eo = new IdocCaptureReadyCheckEo();
            eo.setOrgCode(rs.getString("ORGCODE"));
            eo.setBbOrgCode(rs.getString("BBORGCODE"));
            eo.setOrgName(rs.getString("ORGNAME"));
            eo.setAccountYear(rs.getString("ACCOUNTYEAR"));
            eo.setAccountMonth(rs.getString("ACCOUNTMONTH"));
            eo.setPeriod(rs.getString("PERIOD"));
            eo.setPeriodIndex(rs.getInt("PERIODINDEX"));
            eo.setArchiveType(rs.getString("ARCHIVETYPE"));
            eo.setReadyStatus(rs.getString("READYSTATUS"));
            eo.setErrorMsg(rs.getString("ERRORMSG"));
            eo.setTs(rs.getString("TS"));
            return eo;
        }));
    }

    @Override
    public void update(IdocCaptureReadyCheckEo idocCaptureReadyCheckEo) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate();
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE %s \n");
        sql.append("   SET ORGCODE = ?, BBORGCODE = ?, ORGNAME = ?, ACCOUNTYEAR = ?, ACCOUNTMONTH = ?, PERIOD = ?, PERIODINDEX = ?, \n");
        sql.append("       ARCHIVETYPE = ?, READYSTATUS = ?, ERRORMSG = ?, TS = ?  \n");
        sql.append(" WHERE ID = ? \n");
        String sqlResult = String.format(sql.toString(), "IDOC_CAPTURE_READY_CHECK");
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(idocCaptureReadyCheckEo.getOrgCode());
        params.add(idocCaptureReadyCheckEo.getBbOrgCode());
        params.add(idocCaptureReadyCheckEo.getOrgName());
        params.add(idocCaptureReadyCheckEo.getAccountYear());
        params.add(idocCaptureReadyCheckEo.getAccountMonth());
        params.add(idocCaptureReadyCheckEo.getPeriod());
        params.add(idocCaptureReadyCheckEo.getPeriodIndex());
        params.add(idocCaptureReadyCheckEo.getArchiveType());
        params.add(idocCaptureReadyCheckEo.getReadyStatus());
        params.add(idocCaptureReadyCheckEo.getErrorMsg());
        params.add(idocCaptureReadyCheckEo.getTs());
        params.add(idocCaptureReadyCheckEo.getId());
        jdbcTemplate.update(sqlResult.toString(), params.toArray());
    }
}

