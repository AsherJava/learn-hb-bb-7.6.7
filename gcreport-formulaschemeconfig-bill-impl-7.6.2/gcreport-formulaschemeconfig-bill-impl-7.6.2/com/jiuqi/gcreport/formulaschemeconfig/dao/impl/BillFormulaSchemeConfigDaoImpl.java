/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.formulaschemeconfig.dao.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dao.BillFormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class BillFormulaSchemeConfigDaoImpl
implements BillFormulaSchemeConfigDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String FILED_STRING = " ID,TASKID,SCHEMEID,BILLID,ENTITYID,CATEGORY,ORGID,ASSISTDIM,BBLX,FETCHSCHEMEID,FETCHAFTERSCHEMEID,CONVERTAFTERSCHEMEID,CONVERTSYSTEMSCHEMEID,POSTINGSCHEMEID,COMPLETEMERGEID,SPLITSCHEMEID,UNSACTDEEXTLAYENUMSAPERID,SAMECTRLEXTAFTERSCHEMEID,CREATOR,CREATETIME,UPDATOR,UPDATETIME,SORTORDER ";

    private String getAllFieldsSQL() {
        return SqlUtils.getNewColumnsSqlByEntity(FormulaSchemeConfigEO.class, (String)"formulaScheme");
    }

    @Override
    public List<FormulaSchemeConfigEO> getAllFormulaSchemeConfigs(String billId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.billId = ? \n  and formulaScheme.category = ?\n order by formulaScheme.sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{billId, "billFetch"});
    }

    @Override
    public void deleteStrategySchemeConfig(String billId) {
        String sql = "\tdelete from GC_FORMULASCHEMECONFIG \n  where billId = ? \n  and bblx = 'strategySetting'  \n  and category = ?\n";
        this.jdbcTemplate.update(sql, new Object[]{billId, "billFetch"});
    }

    @Override
    public void addBatch(List<FormulaSchemeConfigEO> formulaSchemeConfigEOS) {
        String sql = "  insert into  GC_FORMULASCHEMECONFIG \n (  ID,TASKID,SCHEMEID,BILLID,ENTITYID,CATEGORY,ORGID,ASSISTDIM,BBLX,FETCHSCHEMEID,FETCHAFTERSCHEMEID,CONVERTAFTERSCHEMEID,CONVERTSYSTEMSCHEMEID,POSTINGSCHEMEID,COMPLETEMERGEID,SPLITSCHEMEID,UNSACTDEEXTLAYENUMSAPERID,SAMECTRLEXTAFTERSCHEMEID,CREATOR,CREATETIME,UPDATOR,UPDATETIME,SORTORDER )\n values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FormulaSchemeConfigEO eo : formulaSchemeConfigEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), "-", "-", eo.getBillId(), "#", "billFetch", eo.getOrgId(), eo.getAssistDim(), eo.getBblx(), eo.getFetchSchemeId(), eo.getFetchAfterSchemeId(), eo.getConvertAfterSchemeId(), eo.getConvertSystemSchemeId(), eo.getPostingSchemeId(), eo.getCompleteMergeId(), eo.getSplitSchemeId(), eo.getUnSaCtDeExtLaYeNumSaPerId(), eo.getSameCtrlExtAfterSchemeId(), eo.getCreator(), eo.getCreateTime(), eo.getUpdator(), eo.getUpdateTime(), eo.getSortOrder()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public void deleteSelectSchemeConfig(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"id");
        String sql = "\tdelete from GC_FORMULASCHEMECONFIG  \n  where " + inSql + " \n  and category = ?\n";
        this.jdbcTemplate.update(sql, new Object[]{"billFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> listFormulaSchemeConfigById(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"formulaScheme.id");
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where " + inSql + " \n  and formulaScheme.category = ?\n";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{"billFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIds(List<String> parentIds, String billId) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(parentIds, (String)"formulaScheme.orgId");
        String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where " + inSql + " \n  and formulaScheme.billId = ? \n  and formulaScheme.category = ?\n";
        sql = sql + " order by sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{billId, "billFetch"});
    }

    @Override
    public List<String> listByBillSettingType(String billSettingType) {
        Assert.isNotEmpty((String)billSettingType);
        String LIST_BY_SETTINGTYPE_SQL = "SELECT ID FROM BDE_BILLEXTRACT_DEFINE WHERE 1 = 1 AND BILLSETTINGTYPE = ? ORDER BY ORDINAL DESC";
        return (List)this.jdbcTemplate.query("SELECT ID FROM BDE_BILLEXTRACT_DEFINE WHERE 1 = 1 AND BILLSETTINGTYPE = ? ORDER BY ORDINAL DESC", (ResultSetExtractor)new ResultSetExtractor<List<String>>(){

            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<String> ids = new ArrayList<String>();
                while (rs.next()) {
                    ids.add(rs.getString("ID"));
                }
                return ids;
            }
        }, new Object[]{billSettingType});
    }
}

