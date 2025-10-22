/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalPostReportVO
 */
package com.jiuqi.gcreport.journalsingle.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.journalsingle.condition.JournalPostRuleCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalPostRuleDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalPostRuleEO;
import com.jiuqi.gcreport.journalsingle.vo.JournalPostReportVO;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class JournalPostRuleDaoImpl
extends GcDbSqlGenericDAO<JournalPostRuleEO, String>
implements IJournalPostRuleDao {
    public JournalPostRuleDaoImpl() {
        super(JournalPostRuleEO.class);
    }

    @Override
    public List<JournalPostRuleEO> queryListByCondition(JournalPostRuleCondition condi) {
        StringBuffer sql = new StringBuffer();
        LinkedList<String> args = new LinkedList<String>();
        args.add(condi.taskId);
        args.add(condi.schemeId);
        args.add(condi.formId);
        sql.append("select \n");
        sql.append(SqlUtils.getColumnsSqlByEntity(JournalPostRuleEO.class, (String)"t") + " \n");
        sql.append("from GC_JOURNALPOSTRULE  t \n");
        sql.append("where 1=1 and t.taskID = ? \n");
        sql.append("and t.schemeID = ? \n");
        sql.append("and t.formID = ? \n");
        if (condi.zbId != null) {
            sql.append("and t.zbID = ?D \n");
            args.add(condi.zbId);
        }
        sql.append("order by t.zbName \n");
        return this.selectEntity(sql.toString(), args.toArray());
    }

    @Override
    public void deleteByZbid(JournalPostReportVO ruleVo, String zbid) {
        StringBuffer sql = new StringBuffer();
        sql.append("delete from GC_JOURNALPOSTRULE   \n");
        sql.append("where 1=1 and taskID = ? \n");
        sql.append("and schemeID = ? \n");
        sql.append("and formID = ? \n");
        sql.append("and zbId = ? \n");
        this.execute(sql.toString(), new Object[]{ruleVo.getTaskId(), ruleVo.getSchemeId(), ruleVo.getFormId(), zbid});
    }

    @Override
    public void deleteRuleById(String id) {
        String sql = "delete from GC_JOURNALPOSTRULE  \nwhere id = ? \n";
        this.execute(sql, new Object[]{id});
    }
}

