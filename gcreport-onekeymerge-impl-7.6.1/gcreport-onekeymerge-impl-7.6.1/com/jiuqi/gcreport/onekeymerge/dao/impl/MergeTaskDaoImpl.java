/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.onekeymerge.dao.MergeTaskDao;
import com.jiuqi.gcreport.onekeymerge.entity.MergeTaskEO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class MergeTaskDaoImpl
extends AbstractEntDbSqlGenericDAO<MergeTaskEO>
implements MergeTaskDao {
    public MergeTaskDaoImpl() {
        super(MergeTaskEO.class);
    }

    @Override
    public List<MergeTaskEO> listExecutableTask(String taskTreeGroupId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MERGETASK", (String)"t");
        String sql = "select " + allFieldsSQL + " from " + "GC_MERGETASK" + " t\n where t.TASKSTATE = 'WAITTING' and t.GROUP_ID = '%1$s' and not exists(select 1\n                 from " + "GC_MERGETASK" + " a\n                where a.TASKSTATE in ('WAITTING', 'EXECUTING') and t.ID = a.AFTERTASKID) order by ordinal desc";
        return this.selectEntity(String.format(sql, taskTreeGroupId, taskTreeGroupId, taskTreeGroupId), new Object[0]);
    }

    @Override
    public int countByGroupId(String taskTreeGroupId) {
        String sql = "select count(*) from GC_MERGETASK where GROUP_ID='%1$s'";
        return this.count(String.format(sql, taskTreeGroupId), new Object[0]);
    }

    @Override
    public int updateTaskStateByIds(List<String> ids, String taskState) {
        TempTableCondition condition = SqlUtils.getConditionOfIds(ids, (String)"t.ID");
        String sql = "update GC_MERGETASK t set t.TASKSTATE = '%1$s' , t.FINISHTIME = ? where " + condition.getCondition();
        IdTemporaryTableUtils.deteteByGroupId((String)condition.getTempGroupId());
        return this.execute(String.format(sql, taskState), new Object[]{new Date()});
    }

    @Override
    public List<MergeTaskEO> listByIds(Set<String> ids) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MERGETASK", (String)"t");
        TempTableCondition condition = SqlUtils.getConditionOfIds(ids, (String)"t.ID");
        String sql = "select " + allFieldsSQL + " from " + "GC_MERGETASK" + " t  where " + condition.getCondition();
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public List<MergeTaskEO> listExecuting5ErrorByGroupId(String groupId) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MERGETASK", (String)"t");
        String sql = "select " + allFieldsSQL + " from " + "GC_MERGETASK" + " t  where (t.TASKSTATE = 'EXECUTING' or t.TASKSTATE = 'ERROR') and t.GROUP_ID='%1$s' ";
        return this.selectEntity(String.format(sql, groupId), new Object[0]);
    }

    @Override
    public List<Map<String, Object>> countState(String groupId) {
        String sql = "select  TASKCODE ,TASKSTATE, count(0) COUNT  from GC_MERGETASK where  GROUP_ID= ?  group by TASKCODE, TASKSTATE";
        return this.selectMap(sql, new Object[]{groupId});
    }

    @Override
    public int updateStateForWaiting(String groupId) {
        String sql = "update GC_MERGETASK set TASKSTATE = ? , FINISHTIME = ? where GROUP_ID = ? and TASKSTATE = 'WAITTING'";
        return this.execute(sql, new Object[]{TaskStateEnum.STOP.getCode(), new Date(), groupId});
    }
}

