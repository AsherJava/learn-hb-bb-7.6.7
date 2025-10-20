/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.menu.MenuDO
 *  com.jiuqi.va.domain.task.MenuRegisterTask
 *  com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent
 *  org.springframework.data.redis.core.HashOperations
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.shiro.config.schedule;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.menu.MenuDO;
import com.jiuqi.va.domain.task.MenuRegisterTask;
import com.jiuqi.va.mapper.runner.StorageSyncFinishedEvent;
import com.jiuqi.va.shiro.config.schedule.VaPermissionInfo;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

@Component
@ConditionalOnExpression(value="${spring.redis.enabled:true} && ${jiuqi.nvwa.restapi.permissions:false}")
public class VaPermissionCollectionSchedule
implements ApplicationListener<StorageSyncFinishedEvent> {
    public static final String REGISTER_KEY = "VA_PERMISSION_COLLECTION";
    private static Logger logger = LoggerFactory.getLogger(VaPermissionCollectionSchedule.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired(required=false)
    private List<MenuRegisterTask> taskList;
    private List<VaPermissionInfo> infos;
    private static boolean started;

    @Override
    public void onApplicationEvent(StorageSyncFinishedEvent event) {
        if (!started) {
            started = true;
            this.register();
        }
    }

    @Scheduled(fixedDelay=300000L, initialDelay=60000L)
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
            VaPermissionInfo info = null;
            for (Map.Entry entry : taskMap.entrySet()) {
                info = (VaPermissionInfo)JSONUtil.parseObject((String)entry.getValue().toString(), VaPermissionInfo.class);
                if (info.getTimestamp() + 900000L >= systime) continue;
                hopts.delete((Object)REGISTER_KEY, new Object[]{entry.getKey()});
            }
        }
        catch (Exception e) {
            logger.error("\u6743\u9650\u6807\u8bc6\u6536\u96c6\u5e38\uff1a", e);
        }
    }

    private Map<String, String> getTaskJsonMap() {
        if (this.taskList == null || this.taskList.isEmpty()) {
            return null;
        }
        if (this.infos == null) {
            this.infos = new ArrayList<VaPermissionInfo>();
            HashMap<String, String> nameRefs = new HashMap<String, String>();
            List menus = null;
            for (MenuRegisterTask task : this.taskList) {
                menus = task.getMenus();
                if (menus == null || menus.isEmpty()) continue;
                for (MenuDO menu : menus) {
                    nameRefs.put(menu.getName(), menu.getTitle());
                    if (!StringUtils.hasText(menu.getPerms())) continue;
                    VaPermissionInfo info = new VaPermissionInfo();
                    info.setName(menu.getName());
                    info.setTitle(menu.getTitle());
                    info.setParent((String)nameRefs.get(menu.getParentname()));
                    info.setPerms(menu.getPerms());
                    this.infos.add(info);
                }
            }
        }
        long systime = System.currentTimeMillis();
        HashMap<String, String> rs = new HashMap<String, String>();
        for (VaPermissionInfo info : this.infos) {
            info.setTimestamp(systime);
            rs.put(info.getName(), JSONUtil.toJSONString((Object)info));
        }
        return rs;
    }
}

