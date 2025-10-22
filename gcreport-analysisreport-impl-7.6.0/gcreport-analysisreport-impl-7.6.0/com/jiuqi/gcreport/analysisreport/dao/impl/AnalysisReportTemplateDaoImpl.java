/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.analysisreport.dao.impl;

import com.jiuqi.gcreport.analysisreport.authority.AnalysisReportTemplateAuthorityProvider;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateDao;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class AnalysisReportTemplateDaoImpl
extends GcDbSqlGenericDAO<AnalysisReportEO, String>
implements AnalysisReportTemplateDao {
    @Autowired
    private AnalysisReportTemplateAuthorityProvider authorityProvider;

    public AnalysisReportTemplateDaoImpl() {
        super(AnalysisReportEO.class);
    }

    public int add(AnalysisReportEO analysisReportEO) {
        int add = super.add((BaseEntity)analysisReportEO);
        this.authorityProvider.grantAllPrivileges(analysisReportEO.getId());
        return add;
    }

    public int update(AnalysisReportEO analysisReportEO) {
        int update = super.update((DefaultTableEntity)analysisReportEO);
        this.authorityProvider.grantAllPrivileges(analysisReportEO.getId());
        return update;
    }

    @Override
    public List<AnalysisReportEO> queryAuthItemsByParentId(boolean isAuth, String parentId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + "   t  \n  where  t.parentId = ? order by t.sortorder \n";
        List analysisReportEOS = this.selectEntity(sql, new Object[]{parentId});
        List<AnalysisReportEO> canReadAnalysisReportEOs = analysisReportEOS.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        return canReadAnalysisReportEOs;
    }

    @Override
    public List<AnalysisReportEO> queryAuthItemsByParentIdContainSelf(boolean isAuth, String parentId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + "   t  \n  where  t.parentId = ? or t.id = ? order by t.sortorder \n";
        List analysisReportEOS = this.selectEntity(sql, new Object[]{parentId, parentId});
        List<AnalysisReportEO> canReadAnalysisReportEOs = analysisReportEOS.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        return canReadAnalysisReportEOs;
    }

    @Override
    public AnalysisReportEO queryAuthPreviousItemById(boolean isAuth, String currentNodeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + "   t  \n  where  parentid = (select parentid from " + "GC_ANALYSISREPORT" + " where id = ?) \n     and sortorder < (select sortorder from " + "GC_ANALYSISREPORT" + " where id = ?) \n order by sortorder desc ";
        List previousNodes = this.selectEntityByPaging(sql, 0, 1, new Object[]{currentNodeId, currentNodeId});
        if (CollectionUtils.isEmpty(previousNodes)) {
            return null;
        }
        List canReadAnalysisReportEOs = previousNodes.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(canReadAnalysisReportEOs)) {
            return null;
        }
        return (AnalysisReportEO)((Object)canReadAnalysisReportEOs.get(0));
    }

    @Override
    public AnalysisReportEO queryAuthNextItemById(boolean isAuth, String currentNodeId) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + "   t  \n  where  parentid = (select parentid from " + "GC_ANALYSISREPORT" + " where id = ?) \n     and sortorder > (select sortorder from " + "GC_ANALYSISREPORT" + " where id = ?) \n order by sortorder asc ";
        List nextNodes = this.selectEntityByPaging(sql, 0, 1, new Object[]{currentNodeId, currentNodeId});
        if (CollectionUtils.isEmpty(nextNodes)) {
            return null;
        }
        List canReadAnalysisReportEOs = nextNodes.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(canReadAnalysisReportEOs)) {
            return null;
        }
        return (AnalysisReportEO)((Object)canReadAnalysisReportEOs.get(0));
    }

    @Override
    public List<AnalysisReportEO> queryAuthItemsByIds(boolean isAuth, List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + " t  \n  where " + SqlUtils.getConditionOfMulStrUseOr(ids, (String)"t.id");
        List analysisReportEOS = this.selectEntity(sql, new Object[0]);
        List<AnalysisReportEO> canReadAnalysisReportEOs = analysisReportEOS.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        canReadAnalysisReportEOs.sort(Comparator.comparingInt(o -> ids.indexOf(o.getId())));
        return canReadAnalysisReportEOs;
    }

    @Override
    public List<AnalysisReportEO> queryAuthAll(boolean isAuth) {
        List analysisReportEOS = this.loadAll();
        if (analysisReportEOS == null) {
            return Collections.emptyList();
        }
        analysisReportEOS.sort(Comparator.comparing(AnalysisReportEO::getSortOrder));
        List<AnalysisReportEO> canReadAnalysisReportEOs = analysisReportEOS.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        return canReadAnalysisReportEOs;
    }

    @Override
    public List<AnalysisReportEO> queryByTitle(boolean isAuth, String title) {
        String sql = "  select " + SqlUtils.getColumnsSqlByEntity(AnalysisReportEO.class, (String)"t") + "  from  " + "GC_ANALYSISREPORT" + "   t  \n  where  t.title = ? order by t.sortorder \n";
        List analysisReportEOS = this.selectEntity(sql, new Object[]{title});
        List<AnalysisReportEO> canReadAnalysisReportEOs = analysisReportEOS.stream().filter(analysisReportEO -> isAuth ? this.authorityProvider.canRead(analysisReportEO.getId()) : true).collect(Collectors.toList());
        return canReadAnalysisReportEOs;
    }

    @Override
    public List<Map<String, Object>> queryAnalysisReportLeafTemplates() {
        String sql = "  select id, t.title   from GC_ANALYSISREPORT t \n  where t.nodetype = 'item'  order by t.sortorder \n";
        List list = this.selectMap(sql, new Object[0]);
        return list;
    }
}

