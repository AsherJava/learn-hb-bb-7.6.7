/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.journalsingle.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.journalsingle.dao.IJournalRelateSchemeDao;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSubjectDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalRelateSchemeEO;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JournalSubjectDaoImpl
extends GcDbSqlGenericDAO<JournalSubjectEO, String>
implements IJournalSubjectDao {
    @Autowired
    private IJournalRelateSchemeDao relateSchemeDao;

    public JournalSubjectDaoImpl() {
        super(JournalSubjectEO.class);
    }

    @Override
    public Serializable insertSubject(JournalSubjectEO journalSubjectEO) {
        if (journalSubjectEO.getId() == null) {
            journalSubjectEO.setId(UUID.randomUUID().toString());
        }
        if (StringUtils.isNull((String)journalSubjectEO.getParents())) {
            String parents = this.generateNewParents(journalSubjectEO);
            if (StringUtils.isNull((String)parents)) {
                return null;
            }
            journalSubjectEO.setParents(parents);
        }
        if (journalSubjectEO.getNeedShow() == null) {
            journalSubjectEO.setNeedShow(1);
        }
        return this.save(journalSubjectEO);
    }

    @Override
    public Integer deleteSubject(String id) {
        JournalSubjectEO journalSubjectEO = (JournalSubjectEO)this.get((Serializable)((Object)id));
        if (null == journalSubjectEO) {
            return 0;
        }
        String sql = "delete from GC_JOURNAL_SUBJECT   \nwhere parents like ? \n";
        return this.execute(sql, new Object[]{journalSubjectEO.getParents() + "%"});
    }

    @Override
    public Integer deleteAllSubjects(String jRelateSchemeId) {
        String sql = "delete from GC_JOURNAL_SUBJECT   \nwhere jRelateSchemeId=? \n";
        return this.execute(sql, new Object[]{jRelateSchemeId});
    }

    @Override
    public JournalSubjectEO getPreNodeByParentIdAndOrder(String parentId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.parentId=? \nand t.sortOrder<? \norder by t.sortOrder desc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        List subjectEOS = this.selectEntityByPaging(sql, 0, 1, new Object[]{parentId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)subjectEOS)) {
            return null;
        }
        return (JournalSubjectEO)((Object)subjectEOS.get(0));
    }

    @Override
    public JournalSubjectEO getNextNodeByParentIdAndOrder(String parentId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.parentId=? \nand t.sortOrder>? \norder by t.sortOrder asc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        List subjectEOS = this.selectEntityByPaging(sql, 0, 1, new Object[]{parentId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)subjectEOS)) {
            return null;
        }
        return (JournalSubjectEO)((Object)subjectEOS.get(0));
    }

    @Override
    public List<JournalSubjectEO> listDirectChildSubjects(String parentId, int pageNum, int pageSize) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.parentId=? \norder by t.sortOrder \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        if (pageNum <= 0 || pageSize <= 0) {
            return this.selectEntity(sql, new Object[]{parentId});
        }
        return this.selectEntityByPaging(sql, (pageNum - 1) * pageSize, pageNum * pageSize, new Object[]{parentId});
    }

    @Override
    public List<JournalSubjectEO> listAllChildSubjects(String parentId, int pageNum, int pageSize) {
        JournalSubjectEO subjectEO = (JournalSubjectEO)this.get((Serializable)((Object)parentId));
        StringBuffer sql = new StringBuffer();
        sql.append("select ").append(SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t")).append(" \n");
        sql.append("from GC_JOURNAL_SUBJECT  t \n");
        sql.append("where 1=1 \n");
        sql.append(" and t.parents like '").append(null != subjectEO ? subjectEO.getParents() : parentId).append("%' \n");
        if (pageNum <= 0 || pageSize <= 0) {
            return this.selectEntity(sql.toString(), new Object[0]);
        }
        return this.selectEntityByPaging(sql.toString(), (pageNum - 1) * pageSize, pageNum * pageSize, new Object[0]);
    }

    @Override
    public int countDirectChildSubjects(String parentId) {
        String sql = "select count(1) \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.parentId=? \n";
        return this.count(sql, new Object[]{parentId});
    }

    @Override
    public int countAllChildSubjects(String parentId) {
        JournalSubjectEO subjectEO = (JournalSubjectEO)this.get((Serializable)((Object)parentId));
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) from GC_JOURNAL_SUBJECT  t \n");
        sql.append("where 1=1 \n");
        sql.append(" and t.parents like '").append(null != subjectEO ? subjectEO.getParents() : parentId).append("_%%' \n");
        return this.count(sql.toString(), new Object[0]);
    }

    @Override
    public List<JournalSubjectEO> listAllSubjects(String jRelateSchemeId) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.jRelateSchemeId=? \norder by t.sortOrder\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        return this.selectEntity(sql, new Object[]{jRelateSchemeId});
    }

    @Override
    public String getSubjectTitleByCode(String jRelateSchemeId, String subjectCode) {
        String sql = "select title AS TITLE \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.jRelateSchemeId=? \nand t.code=? \n";
        List datas = this.selectFirstList(String.class, sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t")), new Object[]{jRelateSchemeId, subjectCode});
        if (datas.isEmpty() || datas.get(0) == null) {
            return "";
        }
        return (String)datas.get(0);
    }

    @Override
    public JournalSubjectEO getSubjectEOByCode(String jRelateSchemeId, String subjectCode) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere t.jRelateSchemeId=?\nand t.code=? \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        List subjectEOS = this.selectEntity(sql, new Object[]{jRelateSchemeId, subjectCode});
        return CollectionUtils.isEmpty((Collection)subjectEOS) ? null : (JournalSubjectEO)((Object)subjectEOS.get(0));
    }

    @Override
    public JournalSubjectEO getSubjectEOByZbCode(String zbCode) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere (t.BEFOREZBCODE = ? or t.AFTERZBCODE=?) \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        List subjectEOS = this.selectEntity(sql, new Object[]{zbCode, zbCode});
        return CollectionUtils.isEmpty((Collection)subjectEOS) ? null : (JournalSubjectEO)((Object)subjectEOS.get(0));
    }

    @Override
    public Integer deleteSubjectBySchemeIdAndSubjectCode(String jRelateSchemeId, Set<String> subjectCode) {
        String sql = "delete from GC_JOURNAL_SUBJECT   \n where jRelateSchemeId=? \n   and " + SqlUtils.getConditionOfIdsUseOr(subjectCode, (String)"code");
        return this.execute(sql, new Object[]{jRelateSchemeId});
    }

    @Override
    public JournalSubjectEO getSubjectEOByZbId(String zbId) {
        String sql = "select %1$s \nfrom GC_JOURNAL_SUBJECT  t \nwhere (t.BEFOREZBID = ? or t.AFTERZBID= ?) \n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(JournalSubjectEO.class, (String)"t"));
        List subjectEOS = this.selectEntity(sql, new Object[]{zbId});
        return CollectionUtils.isEmpty((Collection)subjectEOS) ? null : (JournalSubjectEO)((Object)subjectEOS.get(0));
    }

    @Override
    public String generateNewParents(JournalSubjectEO journalSubjectEO) {
        JournalSubjectEO parentSubjectEO = (JournalSubjectEO)this.get((Serializable)((Object)journalSubjectEO.getParentId()));
        String parents = null;
        if (null == parentSubjectEO) {
            JournalRelateSchemeEO relateSchemeEO = (JournalRelateSchemeEO)this.relateSchemeDao.get((Serializable)((Object)journalSubjectEO.getParentId()));
            if (null != relateSchemeEO) {
                parents = relateSchemeEO.getId() + "/" + journalSubjectEO.getId();
            }
        } else {
            parents = parentSubjectEO.getParents() + "/" + journalSubjectEO.getId();
        }
        return parents;
    }
}

