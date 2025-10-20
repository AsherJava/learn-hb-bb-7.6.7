/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.subject;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.subject.BoundSubjectDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.BoundSubjectEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BoundSubjectDaoImpl
extends GcDbSqlGenericDAO<BoundSubjectEO, String>
implements BoundSubjectDao {
    public BoundSubjectDaoImpl() {
        super(BoundSubjectEO.class);
    }

    @Override
    public BoundSubjectEO getBoundSubjectByConsSubject(String boundCode, String consCode, String systemId) {
        String columnSql = SqlUtils.getColumnsSqlByEntity(BoundSubjectEO.class, (String)"bound");
        String sql = "  select " + columnSql + " from " + "GC_BOUNDSUBJECT" + "  bound \n where bound.code=? and bound.conssubjectcode=? and bound.systemId=?";
        List boundSubjectList = this.selectEntity(sql, new Object[]{boundCode, consCode, systemId});
        return boundSubjectList.size() != 0 ? (BoundSubjectEO)((Object)boundSubjectList.get(0)) : null;
    }

    @Override
    public List<BoundSubjectEO> getBoundSubjectListByConsSubject(String consCode, String systemId) {
        String columnSql = SqlUtils.getColumnsSqlByEntity(BoundSubjectEO.class, (String)"bound");
        String sql = " select " + columnSql + " from " + "GC_BOUNDSUBJECT" + "  bound \n where bound.conssubjectcode=? \n and bound.systemId=? \n";
        return this.selectEntity(sql, new Object[]{consCode, systemId});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveBoundSubjects(List<BoundSubjectEO> boundSubjectList) {
        if (CollectionUtils.isEmpty(boundSubjectList)) {
            return;
        }
        Set consSubjectCodes = boundSubjectList.stream().map(BoundSubjectEO::getConsSubjectCode).collect(Collectors.toSet());
        this.deleteBoundByConsSubjectCode(new ArrayList<String>(consSubjectCodes), boundSubjectList.get(0).getSystemId());
        boundSubjectList.forEach(eo -> eo.setId(UUIDOrderUtils.newUUIDStr()));
        this.saveAll(boundSubjectList);
    }

    @Override
    public void deleteBoundByConsSubjectCode(List<String> consSubjectCodes, String systemId) {
        String sql = " delete  from GC_BOUNDSUBJECT   \n where systemId=?  \n and " + SqlUtils.getConditionOfIdsUseOr(consSubjectCodes, (String)" consSubjectCode");
        this.execute(sql, new Object[]{systemId});
    }

    @Override
    public void deleteBoundByConsSubjectId(List<String> subjectCodes, String systemId) {
        StringBuffer sql = new StringBuffer();
        sql.append("  delete    from GC_BOUNDSUBJECT  \nwhere ");
        sql.append(SqlUtils.getConditionOfIdsUseOr(subjectCodes, (String)"conssubjectcode"));
        sql.append(" and systemId = ?");
        this.execute(sql.toString(), new Object[]{systemId});
    }

    @Override
    public String getBoundSubjectStr(String consCode, String systemId) {
        List<BoundSubjectEO> boundSubjectList = this.getBoundSubjectListByConsSubject(consCode, systemId);
        StringBuffer sb = new StringBuffer();
        boundSubjectList.forEach(subject -> sb.append(subject.getCode()).append(","));
        return sb.toString();
    }

    @Override
    public List<BoundSubjectEO> getBoundSubjectListBySystemId(String systemId) {
        String columnSql = SqlUtils.getColumnsSqlByEntity(BoundSubjectEO.class, (String)"bound");
        String sql = " select " + columnSql + " from " + "GC_BOUNDSUBJECT" + "  bound \n where bound.systemId=? \n";
        return this.selectEntity(sql, new Object[]{systemId});
    }

    @Override
    public Map<String, List<String>> getConsToBoundCodeMapBySystemId(String systemId) {
        List<BoundSubjectEO> boundSubjects = this.getBoundSubjectListBySystemId(systemId);
        HashMap<String, List<String>> boundSubjectMap = new HashMap<String, List<String>>();
        boundSubjects.forEach(eo -> {
            ArrayList<String> boundList = (ArrayList<String>)boundSubjectMap.get(eo.getConsSubjectCode());
            if (boundList == null) {
                boundList = new ArrayList<String>();
            }
            boundList.add(eo.getCode());
            boundSubjectMap.put(eo.getConsSubjectCode(), boundList);
        });
        return boundSubjectMap;
    }
}

