/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 */
package com.jiuqi.gcreport.multicriteria.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.dao.GcMultiCriteriaDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMultiCriteriaEO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class GcMultiCriteriaDaoImpl
extends GcDbSqlGenericDAO<GcMultiCriteriaEO, String>
implements GcMultiCriteriaDao {
    public GcMultiCriteriaDaoImpl() {
        super(GcMultiCriteriaEO.class);
    }

    @Override
    public List<GcMultiCriteriaEO> querySubjectMapping(GcMultiCriteriaConditionVO condition) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULTICRITERIA", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_MULTICRITERIA" + "  t \n");
        sql.append(" where t.taskId=?\n");
        sql.append(" and t.schemeId=?\n");
        sql.append(" and (");
        condition.setZbs(condition.getZbs().stream().filter(zb -> !StringUtils.isEmpty((String)zb)).collect(Collectors.toList()));
        for (int i = 0; i < condition.getZbs().size(); ++i) {
            String zbCode = (String)condition.getZbs().get(i);
            sql.append("t.beforeZbCodes like '%").append(zbCode).append("%' or ");
            sql.append("t.afterZbCodes like '%").append(zbCode).append("%' ");
            if (i == condition.getZbs().size() - 1) continue;
            sql.append(" or \n");
        }
        sql.append(")\n");
        return this.selectEntity(sql.toString(), new Object[]{condition.getTaskId(), condition.getSchemeId()});
    }

    @Override
    public void deleteSubjectMapping(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"ID");
        String sql = "   delete from GC_MULTICRITERIA   \n   where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public List<GcMultiCriteriaEO> querySubjectMappingByIds(Set<String> mcids) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULTICRITERIA", (String)"t");
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcids, (String)"t.ID");
        StringBuilder sql = new StringBuilder();
        sql.append("  select " + allFieldsSQL + " from " + "GC_MULTICRITERIA" + "  t \n");
        sql.append(" where \n");
        sql.append(inSql);
        return this.selectEntity(sql.toString(), new Object[0]);
    }
}

