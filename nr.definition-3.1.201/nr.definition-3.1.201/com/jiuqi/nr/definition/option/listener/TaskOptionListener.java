/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.listener;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.option.TaskOptionService;
import com.jiuqi.nr.definition.option.dao.TaskOptionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TaskOptionListener
implements ApplicationListener<TaskChangeEvent> {
    private static final Logger logger = LoggerFactory.getLogger(TaskOptionListener.class);
    @Autowired
    private TaskOptionDao taskOptionDao;
    @Autowired
    private TaskOptionService taskOptionService;

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        if (ParamChangeEvent.ChangeType.DELETE == event.getType()) {
            for (int i = 0; i < event.getTasks().size(); ++i) {
                if (event.getTasks().get(i) == null) continue;
                String taskKey = event.getTasks().get(i).getKey();
                this.taskOptionDao.resetOptions(taskKey);
                this.taskOptionService.clear(taskKey);
            }
        }
    }
}

