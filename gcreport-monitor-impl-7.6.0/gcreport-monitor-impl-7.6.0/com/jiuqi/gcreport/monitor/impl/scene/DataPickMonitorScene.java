/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.inputdata.util.InputDataNameProvider
 *  com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum
 *  com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorArgument
 *  com.jiuqi.gcreport.monitor.api.inf.MonitorScene
 *  com.jiuqi.gcreport.monitor.api.inf.RouterRedirect
 */
package com.jiuqi.gcreport.monitor.impl.scene;

import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.monitor.api.common.MonitorSceneEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorStateEnum;
import com.jiuqi.gcreport.monitor.api.inf.MonitorArgument;
import com.jiuqi.gcreport.monitor.api.inf.MonitorScene;
import com.jiuqi.gcreport.monitor.api.inf.RouterRedirect;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataPickMonitorScene
implements MonitorScene {
    @Autowired
    private InputDataNameProvider inputDataNameProvider;

    public MonitorSceneEnum getMonitorScene() {
        return MonitorSceneEnum.NODE_DATA_PICK;
    }

    public MonitorStateEnum getState(MonitorArgument argument) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return MonitorStateEnum.PICK_NOT_IS;
        }
        return MonitorStateEnum.PICK_IS;
    }

    public Map<String, MonitorStateEnum> getStates(MonitorArgument argument) {
        if (argument.getOrgIds().isEmpty()) {
            return new HashMap<String, MonitorStateEnum>(16);
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr((Collection)argument.getOrgIds(), (String)"item.MDCODE");
        String tableName = this.inputDataNameProvider.getTableNameByTaskId(argument.getTaskId());
        String sql = "select item.MDCODE from " + tableName + " item \n  where " + inSql + " \n  and item.schemeId = '" + argument.getSchemeId() + "'\n  and item.taskId = '" + argument.getTaskId() + "'\n  and item.acctYear = " + argument.getAcctYear() + "\n  and item.acctPeriod = " + argument.getAcctPeriod() + "\n";
        List rows = EntNativeSqlDefaultDao.getInstance().selectMap(sql, new Object[0]);
        Set orgIds = rows.stream().map(row -> row.get("MDCODE")).collect(Collectors.toSet());
        return argument.getOrgIds().stream().collect(Collectors.toMap(org1 -> org1, o -> {
            boolean contain = orgIds.contains(o);
            return contain ? MonitorStateEnum.PICK_IS : MonitorStateEnum.PICK_NOT_IS;
        }));
    }

    public RouterRedirect getURL(MonitorArgument argument) {
        return null;
    }

    public int getOrder() {
        return 0;
    }
}

