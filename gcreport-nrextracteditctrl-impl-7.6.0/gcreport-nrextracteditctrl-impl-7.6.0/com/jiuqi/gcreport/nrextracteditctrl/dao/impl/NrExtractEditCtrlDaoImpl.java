/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nrextracteditctrl.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.nrextracteditctrl.dao.NrExtractEditCtrlDao;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NrExtractEditCtrlDaoImpl
implements NrExtractEditCtrlDao {
    private static final String FILED_STRING = " ID,TASKKEY,FORMSCHEMEKEY,UNITCODE,STOPFLAG, CREATETIME ";
    private static final String FILED_STRING_ALL = "CL.ID,CL.TASKKEY,CL.FORMSCHEMEKEY,CL.UNITCODE,CL.STOPFLAG, CL.CREATETIME,CLI.ID AS ITEMID,CLI.EDITCTRLCONFID,CLI.FORMKEY,CLI.LINKKEY";
    private static final String UPDATE_FILED_STRING = " SET TASKKEY = ?, FORMSCHEMEKEY = ?, UNITCODE = ?, STOPFLAG = ?, CREATETIME = ? ";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(NrExtractEditCtrlEO nrExtractEditCtrlEO) {
        Assert.isNotNull((Object)nrExtractEditCtrlEO, (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u4fdd\u5b58\u51fa\u9519\uff0c\u4fdd\u5b58\u5bf9\u8c61\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)nrExtractEditCtrlEO.getId(), (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u66f4\u65b0\u51fa\u9519\uff0c\u4fdd\u5b58\u5bf9\u8c61id\u4e3a\u7a7a", (Object[])new Object[0]);
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO  ").append("GC_NR_EXTRACT_EDIT_CTRL").append("\n");
        sql.append("(").append(FILED_STRING).append(")").append("\n");
        sql.append("VALUES(?,?,?,?,?,?)");
        Object[] args = new Object[]{nrExtractEditCtrlEO.getId(), nrExtractEditCtrlEO.getTaskKey(), nrExtractEditCtrlEO.getFormSchemeKey(), nrExtractEditCtrlEO.getUnitCode(), nrExtractEditCtrlEO.getStopFlag(), nrExtractEditCtrlEO.getCreateTime()};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void update(NrExtractEditCtrlEO nrExtractEditCtrlEO) {
        Assert.isNotNull((Object)nrExtractEditCtrlEO, (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u66f4\u65b0\u51fa\u9519\uff0c\u66f4\u65b0\u5bf9\u8c61\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)nrExtractEditCtrlEO.getId(), (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u66f4\u65b0\u51fa\u9519\uff0c\u66f4\u65b0\u5bf9\u8c61id\u4e3a\u7a7a", (Object[])new Object[0]);
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE ").append("GC_NR_EXTRACT_EDIT_CTRL").append("\n");
        sql.append(UPDATE_FILED_STRING);
        sql.append(" WHERE ID = ?");
        Object[] args = new Object[]{nrExtractEditCtrlEO.getTaskKey(), nrExtractEditCtrlEO.getFormSchemeKey(), nrExtractEditCtrlEO.getUnitCode(), nrExtractEditCtrlEO.getStopFlag(), nrExtractEditCtrlEO.getCreateTime(), nrExtractEditCtrlEO.getId()};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM  GC_NR_EXTRACT_EDIT_CTRL WHERE ID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public void stop(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_NR_EXTRACT_EDIT_CTRL").append(" SET STOPFLAG = 1 WHERE ID = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{id});
    }

    @Override
    public void start(String id) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append("GC_NR_EXTRACT_EDIT_CTRL").append(" SET STOPFLAG = 0 WHERE ID = ?");
        this.jdbcTemplate.update(sql.toString(), new Object[]{id});
    }

    @Override
    public List<NrExtractEditCtrlEO> queryAll() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  A.ID,A.TASKKEY,A.FORMSCHEMEKEY,A.UNITCODE,A.STOPFLAG,A.CREATETIME,A.ID AS ITEMID,A.EDITCTRLCONFID,A.FORMKEY,A.LINKKEY,b.DL_FIELD_KEY,b.DL_REGION_KEY,b.DF_CODE,b.DF_TITLE");
        sql.append("  FROM ( SELECT ").append(FILED_STRING_ALL);
        sql.append("          FROM GC_NR_EXTRACT_EDIT_CTRL CL");
        sql.append("          LEFT JOIN GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM CLI ON CLI.EDITCTRLCONFID = CL.ID");
        sql.append("         ORDER BY CL.CREATETIME) A");
        sql.append(" LEFT JOIN (SELECT T_DATALINK.DL_KEY,T_DATALINK.DL_FIELD_KEY,T_DATALINK.DL_REGION_KEY,T_FIELD_DES.DF_CODE, T_FIELD_DES.DF_TITLE");
        sql.append("              FROM NR_PARAM_DATALINK_DES T_DATALINK");
        sql.append("              LEFT JOIN NR_DATASCHEME_FIELD_DES T_FIELD_DES ON T_FIELD_DES.DF_KEY = T_DATALINK.DL_FIELD_KEY) B");
        sql.append(" ON A.LINKKEY = B.DL_KEY");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.nrExtractEditCtrlEOMappingAll(rs));
    }

    @Override
    public List<NrExtractEditCtrlEO> queryByTaskIdAndSchemeKey(String taskId, String schemeId) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT ").append(FILED_STRING_ALL).append("\n");
        sql.append("    FROM ").append("GC_NR_EXTRACT_EDIT_CTRL").append(" CL \n");
        sql.append("    LEFT JOIN ").append("GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM").append(" CLI ON CLI.EDITCTRLCONFID = CL.ID \n");
        sql.append("   WHERE CL.TASKKEY = ? AND CL.FORMSCHEMEKEY = ?  AND CL.STOPFLAG = 0\n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.nrExtractEditCtrlEOMapping(rs), new Object[]{taskId, schemeId});
    }

    private NrExtractEditCtrlEO nrExtractEditCtrlEOMapping(ResultSet rs) throws SQLException {
        NrExtractEditCtrlEO nrExtractEditCtrlEO = new NrExtractEditCtrlEO();
        nrExtractEditCtrlEO.setId(rs.getString(1));
        nrExtractEditCtrlEO.setTaskKey(rs.getString(2));
        nrExtractEditCtrlEO.setFormSchemeKey(rs.getString(3));
        nrExtractEditCtrlEO.setUnitCode(rs.getString(4));
        nrExtractEditCtrlEO.setStopFlag(rs.getInt(5));
        nrExtractEditCtrlEO.setCreateTime(rs.getDate(6));
        nrExtractEditCtrlEO.setItemId(rs.getString(7));
        nrExtractEditCtrlEO.setEditCtrlConfId(rs.getString(8));
        nrExtractEditCtrlEO.setFormKey(rs.getString(9));
        nrExtractEditCtrlEO.setLinkKey(rs.getString(10));
        return nrExtractEditCtrlEO;
    }

    private NrExtractEditCtrlEO nrExtractEditCtrlEOMappingAll(ResultSet rs) throws SQLException {
        NrExtractEditCtrlEO nrExtractEditCtrlEO = new NrExtractEditCtrlEO();
        nrExtractEditCtrlEO.setId(rs.getString(1));
        nrExtractEditCtrlEO.setTaskKey(rs.getString(2));
        nrExtractEditCtrlEO.setFormSchemeKey(rs.getString(3));
        nrExtractEditCtrlEO.setUnitCode(rs.getString(4));
        nrExtractEditCtrlEO.setStopFlag(rs.getInt(5));
        nrExtractEditCtrlEO.setCreateTime(rs.getDate(6));
        nrExtractEditCtrlEO.setItemId(rs.getString(7));
        nrExtractEditCtrlEO.setEditCtrlConfId(rs.getString(8));
        nrExtractEditCtrlEO.setFormKey(rs.getString(9));
        nrExtractEditCtrlEO.setLinkKey(rs.getString(10));
        nrExtractEditCtrlEO.setFieldKey(rs.getString(11));
        nrExtractEditCtrlEO.setRegionKey(rs.getString(12));
        nrExtractEditCtrlEO.setFieldCode(rs.getString(13));
        nrExtractEditCtrlEO.setFieldTitle(rs.getString(14));
        return nrExtractEditCtrlEO;
    }
}

