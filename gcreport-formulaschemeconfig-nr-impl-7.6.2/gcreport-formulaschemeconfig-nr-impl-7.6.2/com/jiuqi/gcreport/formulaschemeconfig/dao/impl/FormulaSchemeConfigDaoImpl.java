/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.xlib.runtime.Assert
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.BeanPropertyRowMapper
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.gcreport.formulaschemeconfig.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.formulaschemeconfig.dao.FormulaSchemeConfigDao;
import com.jiuqi.gcreport.formulaschemeconfig.entity.FormulaSchemeConfigEO;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.xlib.runtime.Assert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FormulaSchemeConfigDaoImpl
implements FormulaSchemeConfigDao {
    @Autowired
    @Lazy
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String FILED_STRING = " ID,TASKID,SCHEMEID,BILLID,ENTITYID,CATEGORY,ORGID,ASSISTDIM,BBLX,FETCHSCHEMEID,FETCHAFTERSCHEMEID,CONVERTAFTERSCHEMEID,CONVERTSYSTEMSCHEMEID,POSTINGSCHEMEID,COMPLETEMERGEID,SPLITSCHEMEID,UNSACTDEEXTLAYENUMSAPERID,SAMECTRLEXTAFTERSCHEMEID,CREATOR,CREATETIME,UPDATOR,UPDATETIME,SORTORDER ";

    private String getAllFieldsSQL() {
        return SqlUtils.getNewColumnsSqlByEntity(FormulaSchemeConfigEO.class, (String)"formulaScheme");
    }

    @Override
    public List<FormulaSchemeConfigEO> getAllFormulaSchemeConfigs(String schemeId, String entityId) {
        entityId = this.getEntityIdByScheme(schemeId, entityId);
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n  and formulaScheme.category = ?\n order by formulaScheme.sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{schemeId, entityId, "reportFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> queryAllByTaskId(String taskId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.taskId = ? \n  and formulaScheme.category = ?\n order by formulaScheme.sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{taskId, "reportFetch"});
    }

    @Override
    public void deleteStrategySchemeConfig(String schemeId, String entityId) {
        String sql = "\tdelete from GC_FORMULASCHEMECONFIG \n  where schemeId = ? \n  and entityId = ? \n  and bblx <> '-'  \n  and category = ?\n";
        this.jdbcTemplate.update(sql, new Object[]{schemeId, entityId, "reportFetch"});
    }

    @Override
    public void deleteSelectSchemeConfig(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"id");
        String sql = "\tdelete from GC_FORMULASCHEMECONFIG  \n  where " + inSql + " \n  and category = ?\n";
        this.jdbcTemplate.update(sql, new Object[]{"reportFetch"});
    }

    @Override
    public void addBatch(List<FormulaSchemeConfigEO> formulaSchemeConfigEOS) {
        String sql = "  insert into  GC_FORMULASCHEMECONFIG \n (  ID,TASKID,SCHEMEID,BILLID,ENTITYID,CATEGORY,ORGID,ASSISTDIM,BBLX,FETCHSCHEMEID,FETCHAFTERSCHEMEID,CONVERTAFTERSCHEMEID,CONVERTSYSTEMSCHEMEID,POSTINGSCHEMEID,COMPLETEMERGEID,SPLITSCHEMEID,UNSACTDEEXTLAYENUMSAPERID,SAMECTRLEXTAFTERSCHEMEID,CREATOR,CREATETIME,UPDATOR,UPDATETIME,SORTORDER )\n values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
        ArrayList<Object[]> argsList = new ArrayList<Object[]>();
        for (FormulaSchemeConfigEO eo : formulaSchemeConfigEOS) {
            if (StringUtils.isEmpty((String)eo.getId())) {
                eo.setId(UUIDUtils.newHalfGUIDStr());
            }
            Object[] args = new Object[]{eo.getId(), eo.getTaskId(), eo.getSchemeId(), "-", eo.getEntityId(), "reportFetch", eo.getOrgId(), eo.getAssistDim(), eo.getBblx(), eo.getFetchSchemeId(), eo.getFetchAfterSchemeId(), eo.getConvertAfterSchemeId(), eo.getConvertSystemSchemeId(), eo.getPostingSchemeId(), eo.getCompleteMergeId(), eo.getSplitSchemeId(), eo.getUnSaCtDeExtLaYeNumSaPerId(), eo.getSameCtrlExtAfterSchemeId(), eo.getCreator(), eo.getCreateTime(), eo.getUpdator(), eo.getUpdateTime(), eo.getSortOrder()};
            argsList.add(args);
        }
        this.jdbcTemplate.batchUpdate(sql, argsList);
    }

    @Override
    public FormulaSchemeConfigEO getShowTableByOrgIdOrBblx(String orgId, String entityId, String assistDim, String schemeId, String bblx) {
        entityId = this.getEntityIdByScheme(schemeId, entityId);
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.orgId = ? \n  and formulaScheme.assistDim = ? \n  and formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n  and formulaScheme.bblx = ?\n  and formulaScheme.category = ?\n";
        List formulaSchemeConfigEOList = this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{orgId, assistDim, schemeId, entityId, bblx, "reportFetch"});
        if (!CollectionUtils.isEmpty((Collection)formulaSchemeConfigEOList)) {
            return (FormulaSchemeConfigEO)formulaSchemeConfigEOList.get(0);
        }
        return null;
    }

    private String getEntityIdByScheme(String schemeId, String entityId) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(schemeId);
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", schemeId));
        TaskOrgLinkListStream taskOrgLinkListStream = ((IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class)).listTaskOrgLinkStreamByTask(formScheme.getTaskKey());
        List taskOrgLinkList = taskOrgLinkListStream.getList();
        if (taskOrgLinkList != null && taskOrgLinkList.size() == 1) {
            entityId = ((TaskOrgLinkDefine)taskOrgLinkList.get(0)).getEntity();
        }
        return entityId;
    }

    @Override
    public FormulaSchemeConfigEO getShowTableById(String id) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.id =? \n  and formulaScheme.category = ?\n";
        List formulaSchemeConfigEOList = this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{id, "reportFetch"});
        if (!CollectionUtils.isEmpty((Collection)formulaSchemeConfigEOList)) {
            return (FormulaSchemeConfigEO)formulaSchemeConfigEOList.get(0);
        }
        return null;
    }

    @Override
    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIdOrBblx(String orgId, String entityId, String assistDim, String schemeId, String bblx) {
        entityId = this.getEntityIdByScheme(entityId, schemeId);
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.orgId = ? \n  and formulaScheme.assistDim like '" + assistDim + "%' \n  and formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n   and formulaScheme.bblx = ? \n  order by formulaScheme.sortOrder asc   and formulaScheme.category = ?\n";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{orgId, schemeId, entityId, bblx, "reportFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByParents(List<String> parentIds, String assistDim, String schemeId, String entityId) {
        List formulaSchemeConfigEOS;
        if (CollectionUtils.isEmpty(parentIds)) {
            return new ArrayList<FormulaSchemeConfigEO>();
        }
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        ArrayList<String> param = new ArrayList<String>();
        for (String parentId : parentIds) {
            stringJoiner.add("?");
            param.add(parentId);
        }
        String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.orgId in " + stringJoiner + " \n  and formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n  and formulaScheme.bblx <> '-'  \n  and formulaScheme.category = ?\n";
        param.add(schemeId);
        param.add(entityId);
        param.add("reportFetch");
        if (!StringUtils.isEmpty((String)assistDim)) {
            sql = sql + "  and formulaScheme.assistDim like ?  \n";
            param.add(assistDim + "%");
        }
        if (!CollectionUtils.isEmpty((Collection)(formulaSchemeConfigEOS = this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), param.toArray())))) {
            formulaSchemeConfigEOS.sort(Comparator.comparingDouble(FormulaSchemeConfigEO::getSortOrder));
        }
        return formulaSchemeConfigEOS;
    }

    @Override
    public List<FormulaSchemeConfigEO> getStrategyTabSchemeConfig(String schemeId, String entityId) {
        entityId = this.getEntityIdByScheme(schemeId, entityId);
        String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where formulaScheme.bblx <> '-'  \n  and formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n  and formulaScheme.category = ?\n order by formulaScheme.sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{schemeId, entityId, "reportFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> queryAllBySchemeId(String schemeId) {
        String sql = "  select formulaScheme.ASSISTDIM  ASSISTDIM \n  from GC_FORMULASCHEMECONFIG  formulaScheme \n  where formulaScheme.schemeId = ? \n  and formulaScheme.category = ?\n order by formulaScheme.sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{schemeId, "reportFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> getFormulaSchemeConfigsByOrgIds(List<String> parentIds, String schemeId, String entityId) {
        entityId = this.getEntityIdByScheme(schemeId, entityId);
        String inSql = SqlUtils.getConditionOfIdsUseOr(parentIds, (String)"formulaScheme.orgId");
        String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where " + inSql + " \n  and formulaScheme.schemeId = ? \n  and formulaScheme.entityId = ? \n  and formulaScheme.category = ?\n";
        sql = sql + " order by sortOrder asc ";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{schemeId, entityId, "reportFetch"});
    }

    @Override
    public void deleteFormulaSchemeConfigByUniqueIndex(String orgId, String assistDim, String schemeId, String bblx) {
        String sql = "\tdelete from GC_FORMULASCHEMECONFIG   \n  where orgId = ? \n  and assistDim = ? \n  and schemeId = ? \n  and bblx = ? \n  and category = ?\n";
        this.jdbcTemplate.update(sql, new Object[]{orgId, assistDim, schemeId, bblx, "reportFetch"});
    }

    @Override
    public int getFormulaSchemeConfigByFetchSchemeId(String fetchSchemeId) {
        String sql = "  SELECT count(1) \n  FROM GC_FORMULASCHEMECONFIG   \n WHERE FETCHSCHEMEID = ? \n  AND CATEGORY = ?\n";
        return (Integer)this.jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{fetchSchemeId, "reportFetch"});
    }

    @Override
    public List<FormulaSchemeConfigEO> listFormulaSchemeConfigById(List<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"formulaScheme.id");
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_FORMULASCHEMECONFIG" + "  formulaScheme \n  where " + inSql + " \n  and formulaScheme.category = ?\n";
        return this.jdbcTemplate.query(sql, (RowMapper)new BeanPropertyRowMapper(FormulaSchemeConfigEO.class), new Object[]{"reportFetch"});
    }

    @Override
    public List<String> selectEntityIdByTask(String taskKey) {
        String sql = "SELECT ENTITYID FROM GC_FORMULASCHEMECONFIG WHERE CATEGORY  = 'reportFetch' AND TASKID = ? GROUP BY ENTITYID ";
        return (List)this.jdbcTemplate.query(sql, (ResultSetExtractor)new ResultSetExtractor<List<String>>(){

            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<String> entityIdList = new ArrayList<String>();
                while (rs.next()) {
                    entityIdList.add(rs.getString(1));
                }
                return entityIdList;
            }
        }, new Object[]{taskKey});
    }

    @Override
    public int batchUpdateEntityId(String taskKey, String oldEntityId, String entityId) {
        String updateEntityIdSql = " UPDATE GC_FORMULASCHEMECONFIG SET ENTITYID = ? WHERE CATEGORY  = 'reportFetch' AND TASKID = ? AND ENTITYID = ?";
        return this.jdbcTemplate.update(updateEntityIdSql, new Object[]{entityId, taskKey, oldEntityId});
    }
}

