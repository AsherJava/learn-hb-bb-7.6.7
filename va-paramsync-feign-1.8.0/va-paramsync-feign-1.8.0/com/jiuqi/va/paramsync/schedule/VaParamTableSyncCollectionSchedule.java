/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  org.springframework.data.redis.core.HashOperations
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.paramsync.schedule;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.jiuqi.va.paramsync.domain.VaParamTableSyncDTO;
import com.jiuqi.va.paramsync.intf.VaParamTableSyncExtend;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:true}")
public class VaParamTableSyncCollectionSchedule
implements ApplicationListener<StorageSyncFinishedEvent> {
    private static Logger logger = LoggerFactory.getLogger(VaParamTableSyncCollectionSchedule.class);
    public static final String REGISTER_KEY = "VA_PARAM_SYNC_TABLE";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired(required=false)
    private List<VaParamTableSyncExtend> taskList;
    private static boolean started;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        if (!started) {
            started = true;
            this.register();
        }
    }

    @Scheduled(fixedDelay=60000L, initialDelay=60000L)
    public void execute() {
        if (started) {
            this.register();
        }
    }

    private void register() {
        try {
            Map<String, String> taskJsonMap = this.getTaskJsonMap();
            if (taskJsonMap == null || taskJsonMap.isEmpty()) {
                return;
            }
            HashOperations hopts = this.stringRedisTemplate.opsForHash();
            hopts.putAll((Object)REGISTER_KEY, taskJsonMap);
            long systime = System.currentTimeMillis();
            Map taskMap = hopts.entries((Object)REGISTER_KEY);
            VaParamTableSyncDTO param = null;
            for (Map.Entry entry : taskMap.entrySet()) {
                param = (VaParamTableSyncDTO)JSONUtil.parseObject((String)entry.getValue().toString(), VaParamTableSyncDTO.class);
                if (param.getTimestamp() + 300000L >= systime) continue;
                hopts.delete((Object)REGISTER_KEY, new Object[]{entry.getKey()});
            }
        }
        catch (Exception e) {
            logger.error("\u53c2\u6570\u540c\u6b65\u6ce8\u518cVaParamTableSyncDTO\u5f02\u5e38\uff1a", e);
        }
    }

    private Map<String, String> getTaskJsonMap() {
        if (this.taskList == null || this.taskList.isEmpty()) {
            return null;
        }
        long systime = System.currentTimeMillis();
        HashMap<String, String> rs = new HashMap<String, String>();
        List<VaParamTableSyncDTO> params = null;
        for (VaParamTableSyncExtend task : this.taskList) {
            params = task.getParams();
            if (params == null || params.isEmpty()) continue;
            for (VaParamTableSyncDTO param : params) {
                param.setTimestamp(systime);
                rs.put(param.getName(), JSONUtil.toJSONString((Object)param));
            }
        }
        return rs;
    }
}

