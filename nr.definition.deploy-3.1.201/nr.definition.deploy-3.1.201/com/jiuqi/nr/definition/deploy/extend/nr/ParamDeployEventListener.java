/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.event.ParamChangeEvent$ChangeType
 *  com.jiuqi.nr.definition.event.TaskChangeEvent
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.ViewDeployService
 *  com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao
 */
package com.jiuqi.nr.definition.deploy.extend.nr;

import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.ViewDeployService;
import com.jiuqi.nr.definition.planpublish.dao.TaskPlanPublishDao;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ParamDeployEventListener
implements IParamDeployFinishListener,
ApplicationListener<TaskChangeEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamDeployEventListener.class);
    @Autowired
    private ViewDeployService deployService;
    @Autowired
    private TaskPlanPublishDao taskPlanPublishDao;

    @Override
    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.notifyObserver(define, warningConsumer, progressConsumer);
    }

    @Override
    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.notifyObserver(define, warningConsumer, progressConsumer);
    }

    @Override
    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.notifyObserver(define, warningConsumer, progressConsumer);
    }

    @Deprecated
    private void notifyObserver(FormSchemeDefine formScheme, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\uff08\u517c\u5bb9\uff09");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u6267\u884c\u62a5\u8868\u65b9\u6848[{}\uff1a{}]\u7684\u53d1\u5e03\u540e\u517c\u5bb9\u4e8b\u4ef6\u5f00\u59cb", (Object)formScheme.getTitle(), (Object)formScheme.getKey());
        try {
            Map observerResult = this.deployService.notifyObserver(new Object[]{formScheme.getTaskKey()});
            List warnings = (List)observerResult.get(false);
            if (!CollectionUtils.isEmpty(warnings)) {
                for (String warning : warnings) {
                    if (!StringUtils.hasText(warning)) continue;
                    warningConsumer.accept(warning);
                }
            }
        }
        catch (Exception e) {
            warningConsumer.accept("\u672a\u77e5\u5f02\u5e38");
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u6267\u884c\u62a5\u8868\u65b9\u6848[{}\uff1a{}]\u7684\u53d1\u5e03\u540e\u517c\u5bb9\u4e8b\u4ef6\u5f02\u5e38", formScheme.getTitle(), formScheme.getKey(), e);
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u6267\u884c\u62a5\u8868\u65b9\u6848[{}\uff1a{}]\u7684\u53d1\u5e03\u540e\u517c\u5bb9\u4e8b\u4ef6\u5b8c\u6210", (Object)formScheme.getTitle(), (Object)formScheme.getKey());
    }

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        if (ParamChangeEvent.ChangeType.DELETE == event.getType()) {
            List tasks = event.getTasks();
            for (TaskDefine task : tasks) {
                this.taskPlanPublishDao.deleteByTaskKey(task.getKey());
            }
        }
    }
}

