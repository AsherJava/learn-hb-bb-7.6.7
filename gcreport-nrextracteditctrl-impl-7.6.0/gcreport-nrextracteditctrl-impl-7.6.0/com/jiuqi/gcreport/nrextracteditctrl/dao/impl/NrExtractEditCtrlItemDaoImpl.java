/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.nrextracteditctrl.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.gcreport.nrextracteditctrl.dao.NrExtractEditCtrlItemDao;
import com.jiuqi.gcreport.nrextracteditctrl.entity.NrExtractEditCtrlItemEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NrExtractEditCtrlItemDaoImpl
implements NrExtractEditCtrlItemDao {
    private static final String FILED_STRING = " ID,EDITCTRLCONFID,FORMKEY,LINKKEY";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(NrExtractEditCtrlItemEO extractEditCtrlItemEO) {
        Assert.isNotNull((Object)extractEditCtrlItemEO, (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u5b50\u8868\u4fdd\u5b58\u51fa\u9519\uff0c\u4fdd\u5b58\u5bf9\u8c61\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)extractEditCtrlItemEO.getId(), (String)"\u8d22\u52a1\u62a5\u8868\u6743\u9650\u5b50\u8868\u66f4\u65b0\u51fa\u9519\uff0c\u4fdd\u5b58\u5bf9\u8c61id\u4e3a\u7a7a", (Object[])new Object[0]);
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO  ").append("GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM").append("\n");
        sql.append("(").append(FILED_STRING).append(")").append("\n");
        sql.append("VALUES(?,?,?,?)");
        Object[] args = new Object[]{extractEditCtrlItemEO.getId(), extractEditCtrlItemEO.getEditCtrlConfId(), extractEditCtrlItemEO.getFormKey(), extractEditCtrlItemEO.getLinkKey()};
        this.jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void delete(String editCtrlConfId) {
        String sql = "DELETE FROM  GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM WHERE EDITCTRLCONFID = ? \n";
        this.jdbcTemplate.update(sql, new Object[]{editCtrlConfId});
    }

    @Override
    public List<NrExtractEditCtrlItemEO> queryByEditCtrlConfId(String editCtrlConfId) {
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT ").append(FILED_STRING).append("\n");
        sql.append("    FROM ").append("GC_NR_EXTRACT_EDIT_CTRL_FORM_ITEM").append(" T \n");
        sql.append("   WHERE T.EDITCTRLCONFID = ?\n");
        sql.append("   ORDER BY FORMKEY,LINKKEY\n");
        return this.jdbcTemplate.query(sql.toString(), (rs, row) -> this.nrExtractEditCtrlItemEOMapping(rs), new Object[]{editCtrlConfId});
    }

    private NrExtractEditCtrlItemEO nrExtractEditCtrlItemEOMapping(ResultSet rs) throws SQLException {
        NrExtractEditCtrlItemEO nrExtractEditCtrlItemEO = new NrExtractEditCtrlItemEO();
        nrExtractEditCtrlItemEO.setId(rs.getString(1));
        nrExtractEditCtrlItemEO.setEditCtrlConfId(rs.getString(2));
        nrExtractEditCtrlItemEO.setFormKey(rs.getString(3));
        nrExtractEditCtrlItemEO.setLinkKey(rs.getString(4));
        return nrExtractEditCtrlItemEO;
    }
}

