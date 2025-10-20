/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskProcessDao;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskProcessEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MergeTaskProcessDaoImpl
extends AbstractEntDbSqlGenericDAO<MergeTaskProcessEO>
implements MergeTaskProcessDao {
    public MergeTaskProcessDaoImpl() {
        super(MergeTaskProcessEO.class);
    }

    @Override
    public void updateProcess(Long finishedCount, Double process, String id) {
        String sql = "update GC_MERGEPROCESS\nset PROCESS           = PROCESS + ?,\n    FINISHEDTASKCOUNT = FINISHEDTASKCOUNT + ?\nwhere ID = ?";
        this.execute(sql, new Object[]{process, finishedCount, id});
    }

    @Override
    public List<MergeTaskProcessEO> getTaskRecord(GcActionParamsVO param, String dims) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MERGEPROCESS", (String)"t");
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(allFieldsSQL);
        sql.append(" from ").append("GC_MERGEPROCESS").append(" t ");
        sql.append(" where ");
        sql.append(" t.NRTASKID = '").append(param.getTaskId()).append("'");
        sql.append(" and t.DATATIME = '").append(param.getPeriodStr()).append("'");
        sql.append(" and t.DIMS = '").append(dims).append("'");
        sql.append(" order by t.CREATETIME desc ");
        return this.selectEntityByPaging(sql.toString(), 0, 3, new Object[0]);
    }
}

