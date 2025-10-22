/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.task;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskEO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class ConsolidatedTaskDaoImpl
extends GcDbSqlGenericDAO<ConsolidatedTaskEO, String>
implements ConsolidatedTaskDao {
    public ConsolidatedTaskDaoImpl() {
        super(ConsolidatedTaskEO.class);
    }

    @Override
    public List<ConsolidatedTaskEO> getTaskByTaskKey(String taskKey) {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedTaskEO.class, (String)"scheme");
        String sql = "  select " + columnSQL + "    from " + "GC_CONSTASK" + "  scheme \n where scheme.TASKKEY = ? or scheme.MANAGETASKKEYS like ? \n order by scheme.SORTORDER asc \n";
        List consolidatedTaskEOs = this.selectEntity(sql, new Object[]{taskKey, "%" + taskKey + "%"});
        if (consolidatedTaskEOs == null) {
            return Collections.emptyList();
        }
        return consolidatedTaskEOs;
    }

    @Override
    public List<ConsolidatedTaskEO> getTaskByTaskKeyAndPeriodStr(String taskKey, String periodStr) {
        if (StringUtils.isEmpty((String)periodStr)) {
            throw new BusinessRuntimeException("\u65f6\u671f\u6761\u4ef6\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        List<ConsolidatedTaskEO> consolidatedTaskEOS = this.getTaskByTaskKey(taskKey);
        TaskDefine taskDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryTaskDefine(taskKey);
        YearPeriodDO yearPeriodDO = YearPeriodUtil.transformByDataSchemeKey((String)taskDefine.getDataScheme(), (String)periodStr);
        Date startPeriod = yearPeriodDO.getBeginDate();
        Date endPeriod = yearPeriodDO.getEndDate();
        if (startPeriod == null || endPeriod == null) {
            return Collections.emptyList();
        }
        return consolidatedTaskEOS.stream().filter(item -> {
            Date toEndDate;
            Date fromStartDate;
            if (!StringUtils.isEmpty((String)item.getFromPeriod()) && startPeriod.compareTo(fromStartDate = YearPeriodUtil.transformByDataSchemeKey((String)taskDefine.getDataScheme(), (String)item.getFromPeriod()).getBeginDate()) < 0) {
                return false;
            }
            return StringUtils.isEmpty((String)item.getToPeriod()) || endPeriod.compareTo(toEndDate = YearPeriodUtil.transformByDataSchemeKey((String)taskDefine.getDataScheme(), (String)item.getToPeriod()).getEndDate()) <= 0;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteTasksBySystem(String systemId) {
        String sql = "  delete     from GC_CONSTASK   \n     where SYSTEMID = ?";
        this.execute(sql, new Object[]{systemId});
    }

    @Override
    public List<ConsolidatedTaskEO> getAllBoundTasks() {
        String columnSQL = SqlUtils.getColumnsSqlByEntity(ConsolidatedTaskEO.class, (String)"scheme");
        String sql = "  select " + columnSQL + "    from " + "GC_CONSTASK" + "  scheme \n    left join " + "GC_CONSSYSTEM" + " s on s.ID = scheme.SYSTEMID\n order by s.DATASORT, scheme.SORTORDER \n";
        return this.selectEntity(sql, new Object[0]);
    }

    @Override
    public ConsolidatedTaskEO getPreNodeBySystemIdAndOrder(String systemId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_CONSTASK  t \nwhere t.systemId=? \nand t.sortOrder<? \norder by t.sortOrder desc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(ConsolidatedTaskEO.class, (String)"t"));
        List subjectEOS = this.selectEntityByPaging(sql, 0, 1, new Object[]{systemId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)subjectEOS)) {
            return null;
        }
        return (ConsolidatedTaskEO)((Object)subjectEOS.get(0));
    }

    @Override
    public ConsolidatedTaskEO getNextNodeBySystemIdAndOrder(String systemId, String sortOrder) {
        String sql = "select %1$s \nfrom GC_CONSTASK  t \nwhere t.systemId=? \nand t.sortOrder>? \norder by t.sortOrder asc\n";
        sql = String.format(sql, SqlUtils.getColumnsSqlByEntity(ConsolidatedTaskEO.class, (String)"t"));
        List subjectEOS = this.selectEntityByPaging(sql, 0, 1, new Object[]{systemId, sortOrder});
        if (CollectionUtils.isEmpty((Collection)subjectEOS)) {
            return null;
        }
        return (ConsolidatedTaskEO)((Object)subjectEOS.get(0));
    }
}

