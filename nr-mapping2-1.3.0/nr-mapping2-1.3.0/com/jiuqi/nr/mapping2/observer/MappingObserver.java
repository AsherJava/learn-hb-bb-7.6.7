/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.event.TaskChangeEvent
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  org.json.JSONObject
 */
package com.jiuqi.nr.mapping2.observer;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MappingObserver
implements ApplicationListener<TaskChangeEvent> {
    protected final Logger logger = LoggerFactory.getLogger(MappingObserver.class);
    @Autowired
    IRunTimeViewController dRunTime;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        List tasks = event.getTasks();
        for (TaskDefine taskDefine : tasks) {
            if (taskDefine == null) {
                return;
            }
            List allSchemes = this.mappingSchemeService.getAllSchemes();
            for (MappingScheme scheme : allSchemes) {
                String taskKey;
                JSONObject jsonObject;
                String nrTask;
                String extParam = scheme.getExtParam();
                if (!StringUtils.hasText(extParam) || !extParam.contains("NrTask") || !StringUtils.hasText(nrTask = (jsonObject = new JSONObject(extParam)).getString("NrTask")) || !nrTask.contains("taskKey") || !StringUtils.hasText(taskKey = (jsonObject = new JSONObject(nrTask)).getString("taskKey")) || !taskKey.equals(taskDefine.getKey())) continue;
                try {
                    Set<String> orgCodeSet = this.getOrgCodeSet(taskDefine);
                    if (orgCodeSet.contains(scheme.getOrgName())) continue;
                    if (orgCodeSet.size() > 1) {
                        this.logger.error("\u6620\u5c04\u65b9\u6848code=" + scheme.getCode() + "::title=" + scheme.getTitle() + "::\u6620\u5c04\u65b9\u6848\u5173\u8054\u7684\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49\u548c\u591a\u53e3\u5f84\u4efb\u52a1\u3010" + taskDefine.getTaskCode() + "_" + taskDefine.getTitle() + "\u3011\u4e0d\u4e00\u81f4\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u4fee\u590d");
                        continue;
                    }
                    scheme.setOrgName((String)orgCodeSet.stream().findFirst().get());
                    this.mappingSchemeService.modifyScheme(scheme);
                }
                catch (JQException e) {
                    this.logger.error("\u4fee\u590d\u6620\u5c04\u65b9\u6848code=" + scheme.getCode() + "::title=" + scheme.getTitle() + "::\u6620\u5c04\u65b9\u6848\u5173\u8054\u7684\u7ec4\u7ec7\u673a\u6784\u5b9a\u4e49\u5f02\u5e38\uff1b\u4efb\u52a1\u3010" + taskDefine.getTaskCode() + "_" + taskDefine.getTitle() + "\u3011\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u4fee\u590d", e);
                }
            }
        }
    }

    private Set<String> getOrgCodeSet(TaskDefine taskDefine) throws JQException {
        HashSet<String> orgLinks = new HashSet<String>();
        List orgLinkDefs = this.dRunTime.listTaskOrgLinkByTask(taskDefine.getKey());
        if (!CollectionUtils.isEmpty(orgLinkDefs) && orgLinkDefs.size() > 1) {
            for (TaskOrgLinkDefine orgLinkDef : orgLinkDefs) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(orgLinkDef.getEntity());
                if (entityDefine == null) continue;
                orgLinks.add(entityDefine.getCode());
            }
        } else {
            orgLinks.add(this.entityMetaService.queryEntity(taskDefine.getDw()).getCode());
        }
        return orgLinks;
    }
}

