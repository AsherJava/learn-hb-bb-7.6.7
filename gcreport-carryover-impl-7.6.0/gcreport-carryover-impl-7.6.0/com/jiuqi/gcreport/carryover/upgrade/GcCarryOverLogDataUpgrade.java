/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.temp.dto.Message
 *  com.jiuqi.gcreport.temp.dto.MessageTypeEnum
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.spire.ms.System.Collections.ArrayList
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.carryover.upgrade;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.temp.dto.Message;
import com.jiuqi.gcreport.temp.dto.MessageTypeEnum;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.spire.ms.System.Collections.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class GcCarryOverLogDataUpgrade
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        String sql1 = "SELECT * FROM GC_CARRYOVER_LOG";
        List list = jdbcTemplate.query("select id, startTime, info from GC_CARRYOVER_LOG", (rs, rowNum) -> {
            CarryOverLogEO eo = new CarryOverLogEO();
            eo.setId(rs.getString("id"));
            eo.setStartTime(rs.getDate("startTime"));
            eo.setInfo(rs.getString("info"));
            return eo;
        });
        ArrayList args = new ArrayList();
        for (CarryOverLogEO eo : list) {
            String info = eo.getInfo();
            ArrayList messages = new ArrayList();
            String[] logLines = info.split("\n");
            int order = 0;
            for (String line : logLines) {
                Message message = new Message();
                if (line.startsWith("[\u4fe1\u606f]") || line.startsWith("[INFO]")) {
                    message.setMsgType(MessageTypeEnum.INFO);
                } else if (line.startsWith("[\u8b66\u544a]") || line.startsWith("[WARN]")) {
                    message.setMsgType(MessageTypeEnum.WARN);
                } else if (line.startsWith("[\u9519\u8bef]") || line.startsWith("[ERROR")) {
                    message.setMsgType(MessageTypeEnum.ERROR);
                }
                message.setOrder(Integer.valueOf(order));
                message.setKey(UUID.randomUUID().toString());
                message.setMessage(new StringBuffer(line));
                messages.add(message);
                ++order;
            }
            Object[] arg = new Object[]{eo.getStartTime(), 1.0, TaskStateEnum.SUCCESS.getCode(), JsonUtils.writeValueAsString((Object)messages), eo.getId()};
            args.add(arg);
        }
        jdbcTemplate.batchUpdate("UPDATE GC_CARRYOVER_LOG set CREATETIME = ?, PROCESS = ?, TASKSTATE = ?, INFO = ? WHERE ID = ?", (List)args);
    }
}

