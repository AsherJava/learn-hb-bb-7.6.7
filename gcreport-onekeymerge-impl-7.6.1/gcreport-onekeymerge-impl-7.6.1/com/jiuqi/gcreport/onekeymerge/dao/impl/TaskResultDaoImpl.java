/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.onekeymerge.dao.TaskResultDao;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

@Repository
public class TaskResultDaoImpl
extends GcDbSqlGenericDAO<GcTaskResultEO, String>
implements TaskResultDao {
    public TaskResultDaoImpl() {
        super(GcTaskResultEO.class);
    }

    @Override
    public List<GcTaskResultEO> getResultEOByCondition(GcActionParamsVO vo, int taskIndex) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcTaskResultEO.class, (String)" e ") + " \n from " + "GC_ONEKEYMERGETASK_4" + "  e  \n where e.taskId = '" + vo.getTaskId() + "' and  e.schemeId = '" + vo.getSchemeId() + "' and  e.orgType = '" + vo.getOrgType() + "' and  e.acctYear = '" + vo.getAcctYear() + "' and  e.taskIndex = '" + taskIndex + "' and  e.orgId = '" + vo.getOrgId() + "'";
        List gcTaskResultEOS = this.selectEntity(sql, new Object[0]);
        return gcTaskResultEOS;
    }

    @Override
    public String getResultEOByTaskCodeAndGroupId(String taskCode, String taskLogId) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcTaskResultEO.class, (String)" t ") + " \n  from " + "GC_ONEKEYMERGETASK_4" + "  t \n  where t.taskCode = ? and \n   t.GROUP_ID = ?  \n";
        List gcTaskResultEOS = this.selectEntity(sql, new Object[]{taskCode, taskLogId});
        if (CollectionUtils.isEmpty((Collection)gcTaskResultEOS)) {
            return "";
        }
        return ((GcTaskResultEO)((Object)gcTaskResultEOS.get(0))).getTaskData();
    }

    @Override
    public Map<String, GcTaskResultVO> getResultEOByGroupId(String taskLogId) {
        String sql = "select " + SqlUtils.getColumnsSqlByEntity(GcTaskResultEO.class, (String)" t ") + " \n  from " + "GC_ONEKEYMERGETASK_4" + "  t \n  where t.GROUP_ID = ?  \n";
        List gcTaskResultEOS = this.selectEntity(sql, new Object[]{taskLogId});
        return gcTaskResultEOS.stream().collect(Collectors.toMap(GcTaskResultEO::getTaskCode, o -> {
            GcTaskResultVO vo = new GcTaskResultVO();
            BeanUtils.copyProperties(o, vo);
            return vo;
        }));
    }
}

