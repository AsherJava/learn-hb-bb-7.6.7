/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.internal.update.taskgrouplinkupdate;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskGroupLinkOrderService {
    @Autowired
    private DesignTaskGroupLinkDao groupLinkDao;
    @Autowired
    private DesignTaskDefineService taskDefineService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String ORDER = "TL_ORDER";
    private static final String TASK = "TL_TASK_KEY";
    private static final String GROUP = "tl_group_key";
    private static final String TASK_ORDER = "TK_ORDER";
    private static final String SQL_TASKGROUP = "SELECT * FROM NR_PARAM_TASKGROUPLINK_DES";
    private static final String SQL_TASK = "SELECT * FROM NR_PARAM_TASK_DES WHERE TK_KEY = ?";
    private static final String SQL_UPDATE = "UPDATE NR_PARAM_TASKGROUPLINK_DES SET TL_ORDER = ? WHERE TL_GROUP_KEY = ? AND TL_TASK_KEY = ?";

    public void update() throws Exception {
        List allGroupLink = this.jdbcTemplate.queryForList(SQL_TASKGROUP);
        for (Map designTaskGroupLink : allGroupLink) {
            if (null != designTaskGroupLink.get(ORDER) && !StringUtils.isEmpty((String)designTaskGroupLink.get(ORDER).toString())) continue;
            List taskDefines = this.jdbcTemplate.queryForList(SQL_TASK, new Object[]{designTaskGroupLink.get(TASK).toString()});
            if (null != taskDefines && taskDefines.size() == 1) {
                designTaskGroupLink.put(ORDER, ((Map)taskDefines.get(0)).get(TASK_ORDER));
            } else {
                designTaskGroupLink.put(ORDER, OrderGenerator.newOrder());
            }
            this.jdbcTemplate.update(SQL_UPDATE, new Object[]{designTaskGroupLink.get(ORDER).toString(), designTaskGroupLink.get(GROUP), designTaskGroupLink.get(TASK)});
        }
    }
}

