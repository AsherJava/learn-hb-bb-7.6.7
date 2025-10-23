/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao
 *  com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupLinkDao
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceItem
 *  com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceParameter
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.topic.extend.report;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import com.jiuqi.nr.topic.extend.report.GuidHandler;
import com.jiuqi.nr.topic.extend.report.bean.ReportConfig;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.ITopicNavResourceContext;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceItem;
import com.jiuqi.nvwa.topicnavigator.adapter.resource.TopicNavResourceParameter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class TaskTreeService {
    private static final Logger logger = LoggerFactory.getLogger(TaskTreeService.class);
    @Autowired
    IRunTimeViewController runTimeViewController_task;
    @Autowired
    com.jiuqi.nr.definition.controller.IRunTimeViewController runTimeViewController;
    @Autowired
    RunTimeFormDefineDao formDao;
    @Autowired
    RunTimeFormGroupDefineDao formGroupDao;
    @Autowired
    RunTimeFormGroupLinkDao formGroupLinkDao;
    @Autowired
    DefinitionAuthorityProvider authorityProvider;
    @Autowired
    RunTimeAuthViewController authService;

    public List<TopicNavResourceItem> getRootList() {
        return this.getByTaskGroup(null);
    }

    public List<TopicNavResourceItem> getByParent(String parentId) {
        GuidHandler guidHandler = GuidHandler.getInstance(parentId);
        switch (guidHandler.getType()) {
            case TG: {
                return this.getByTaskGroup(guidHandler.getId());
            }
            case TK: {
                return this.getByTask(guidHandler.getId());
            }
            case RS: {
                return this.getByFormScheme(guidHandler.getId());
            }
            case RG: {
                return this.getByFormGroup(guidHandler.getId());
            }
        }
        return null;
    }

    public List<TopicNavResourceItem> searchForm(String searchText) {
        ArrayList<TopicNavResourceItem> res = new ArrayList<TopicNavResourceItem>();
        if (!StringUtils.hasText(searchText)) {
            return res;
        }
        List formDefines = this.formDao.search(searchText.toUpperCase());
        if (!CollectionUtils.isEmpty(formDefines)) {
            for (FormDefine form : formDefines) {
                if (!this.authorityProvider.canReadForm(form.getKey())) continue;
                res.add(this.buildItemByForm(form));
            }
        }
        return res;
    }

    public TopicNavResourceItem getResource(String formId) {
        FormDefine formDefine = this.formDao.getDefineByKey(formId);
        if (formDefine == null || !this.authorityProvider.canReadForm(formId)) {
            return null;
        }
        return this.buildItemByForm(formDefine);
    }

    public TopicNavResourceItem getResourceByName(String formName) {
        try {
            FormDefine formDefine = this.formDao.queryDefinesByCode(formName);
            if (formDefine == null || !this.authorityProvider.canReadForm(formDefine.getKey())) {
                return null;
            }
            return this.buildItemByForm(formDefine);
        }
        catch (Exception e) {
            logger.error("NR-\u4e3b\u9898\u5bfc\u822a\u6811-\u57fa\u7840\u8868formName::" + formName, e);
            return null;
        }
    }

    public String getFormGuidByName(String formName) {
        try {
            FormDefine formDefine = this.formDao.queryDefinesByCode(formName);
            if (formDefine == null || !this.authorityProvider.canReadForm(formDefine.getKey())) {
                return null;
            }
            return formDefine.getKey();
        }
        catch (Exception e) {
            logger.error("NR-\u4e3b\u9898\u5bfc\u822a\u6811-\u57fa\u7840\u8868formName::" + formName, e);
            return null;
        }
    }

    public List<String> getPaths(String formId) {
        FormDefine formDefine = this.formDao.getDefineByKey(formId);
        if (formDefine == null || !this.authorityProvider.canReadForm(formDefine.getKey())) {
            return null;
        }
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(formId);
        List RunTimeFormGroupLinks = this.formGroupLinkDao.getFormGroupLinksByFormId(formId);
        if (!CollectionUtils.isEmpty(RunTimeFormGroupLinks)) {
            RunTimeFormGroupLink runTimeFormGroupLink = (RunTimeFormGroupLink)RunTimeFormGroupLinks.get(0);
            this.buildFormGroupPath(runTimeFormGroupLink.getGroupKey(), paths);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formDefine.getFormScheme());
        paths.add(GuidHandler.build(formDefine.getFormScheme(), GuidHandler.TreeNodeType.RS));
        paths.add(GuidHandler.build(formScheme.getTaskKey(), GuidHandler.TreeNodeType.TK));
        List taskGroupDefines = this.runTimeViewController_task.listTaskGroupByTask(formScheme.getTaskKey());
        if (!CollectionUtils.isEmpty(taskGroupDefines)) {
            TaskGroupDefine taskGroup = (TaskGroupDefine)taskGroupDefines.get(0);
            this.buildTaskGroupPath(taskGroup.getKey(), paths);
        }
        return paths;
    }

    private void buildTaskGroupPath(String group, List<String> paths) {
        if (!StringUtils.hasText(group)) {
            return;
        }
        TaskGroupDefine taskGroup = this.runTimeViewController_task.getTaskGroup(group);
        if (taskGroup == null) {
            return;
        }
        paths.add(GuidHandler.build(group, GuidHandler.TreeNodeType.TG));
        this.buildTaskGroupPath(taskGroup.getParentKey(), paths);
    }

    private void buildFormGroupPath(String group, List<String> paths) {
        if (!StringUtils.hasText(group)) {
            return;
        }
        FormGroupDefine formGroup = this.formGroupDao.getDefineByKey(group);
        if (formGroup == null) {
            return;
        }
        paths.add(GuidHandler.build(group, GuidHandler.TreeNodeType.RG));
        this.buildFormGroupPath(formGroup.getParentKey(), paths);
    }

    private List<TopicNavResourceItem> getByFormGroup(String formGroup) {
        return this.getByFormScheme(formGroup);
    }

    private List<TopicNavResourceItem> getByFormScheme(String formScheme) {
        ArrayList<TopicNavResourceItem> res = new ArrayList<TopicNavResourceItem>();
        List formGroupDefines = this.authService.queryRootGroupsByFormScheme(formScheme);
        if (!CollectionUtils.isEmpty(formGroupDefines)) {
            for (FormGroupDefine fg : formGroupDefines) {
                res.add(this.buildItemByFormGroup(fg));
            }
        }
        try {
            List forms = this.authService.getAllFormsInGroup(formScheme);
            if (!CollectionUtils.isEmpty(forms)) {
                for (FormDefine form : forms) {
                    res.add(this.buildItemByForm(form));
                }
            }
        }
        catch (Exception e) {
            logger.error("NR-\u4e3b\u9898\u5bfc\u822a\u6811-\u57fa\u7840\u8868\u65b9\u6848formScheme::" + formScheme, e);
        }
        return res;
    }

    private List<TopicNavResourceItem> getByTask(String task) {
        ArrayList<TopicNavResourceItem> res = new ArrayList<TopicNavResourceItem>();
        try {
            List formSchemeDefines = this.authService.queryFormSchemeByTask(task);
            if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                for (FormSchemeDefine fs : formSchemeDefines) {
                    res.add(this.buildItemByFormScheme(fs));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private List<TopicNavResourceItem> getByTaskGroup(String taskGroup) {
        ArrayList<TopicNavResourceItem> res = new ArrayList<TopicNavResourceItem>();
        List taskGroupDefines = this.authService.getChildTaskGroups(taskGroup, false);
        List taskDefines = this.authService.getTaskDefinesFromGroup(taskGroup);
        if (!CollectionUtils.isEmpty(taskGroupDefines)) {
            for (TaskGroupDefine group : taskGroupDefines) {
                res.add(this.buildItemByTaskGroup(group));
            }
        }
        if (!CollectionUtils.isEmpty(taskDefines)) {
            for (TaskDefine task : taskDefines) {
                if (StringUtils.hasText(task.getDims())) continue;
                res.add(this.buildItemByTask(task));
            }
        }
        return res;
    }

    private TopicNavResourceItem buildItemByTaskGroup(TaskGroupDefine group) {
        TopicNavResourceItem item = new TopicNavResourceItem();
        item.setGuid(GuidHandler.build(group.getKey(), GuidHandler.TreeNodeType.TG));
        item.setTitle(group.getTitle());
        item.setName(group.getCode());
        item.setFolder(true);
        item.setIcon("nr-iconfont icon-16_SHU_A_NR_fenzu");
        return item;
    }

    private TopicNavResourceItem buildItemByTask(TaskDefine task) {
        TopicNavResourceItem item = new TopicNavResourceItem();
        item.setGuid(GuidHandler.build(task.getKey(), GuidHandler.TreeNodeType.TK));
        item.setTitle(task.getTitle());
        item.setName(task.getTaskCode());
        item.setFolder(true);
        item.setIcon("nr-iconfont icon-J_GJ_A_NR_renwu");
        return item;
    }

    private TopicNavResourceItem buildItemByFormScheme(FormSchemeDefine formScheme) {
        TopicNavResourceItem item = new TopicNavResourceItem();
        item.setGuid(GuidHandler.build(formScheme.getKey(), GuidHandler.TreeNodeType.RS));
        item.setTitle(formScheme.getTitle());
        item.setName(formScheme.getFormSchemeCode());
        item.setFolder(true);
        item.setIcon("nr-iconfont icon-J_GJ_A_NR_baobiaofangan");
        return item;
    }

    private TopicNavResourceItem buildItemByFormGroup(FormGroupDefine formGroup) {
        TopicNavResourceItem item = new TopicNavResourceItem();
        item.setGuid(GuidHandler.build(formGroup.getKey(), GuidHandler.TreeNodeType.RG));
        item.setTitle(formGroup.getTitle());
        item.setName(formGroup.getCode());
        item.setFolder(true);
        return item;
    }

    private TopicNavResourceItem buildItemByForm(FormDefine form) {
        TopicNavResourceItem item = new TopicNavResourceItem();
        item.setGuid(form.getKey());
        item.setTitle(form.getTitle());
        item.setName(form.getFormCode());
        item.setType("com.jiuqi.nr.topic.extend.report");
        item.setFolder(false);
        return item;
    }

    public JSONObject buildAppConfig(String reportId, TopicNavResourceParameter extraParameter, ITopicNavResourceContext context) {
        ReportConfig config = new ReportConfig();
        FormDefine formDefine = this.runTimeViewController.queryFormById(reportId);
        if (formDefine == null) {
            config.setHasReadAuth(false);
            config.setMsg("\u5bf9\u4e0d\u8d77\uff0c\u60a8\u6ca1\u6709\u8bbf\u95ee\u5f53\u524d\u62a5\u8868\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return new JSONObject((Object)config);
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formDefine.getFormScheme());
        if (formScheme == null) {
            config.setHasReadAuth(false);
            config.setMsg("\u5bf9\u4e0d\u8d77\uff0c\u60a8\u6ca1\u6709\u8bbf\u95ee\u5f53\u524d\u62a5\u8868\u6743\u9650\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return new JSONObject((Object)config);
        }
        config.setFormKey(reportId);
        config.setFormSchemeKey(formScheme.getKey());
        config.setTaskKey(formScheme.getTaskKey());
        JSONObject linkMsgObj = extraParameter.getLinkMsgObj();
        if (linkMsgObj == null) {
            config.setHasReadAuth(false);
            config.setMsg("\u5bf9\u4e0d\u8d77\uff0c\u5f53\u524d\u8282\u70b9\u7f3a\u5931\u5355\u4f4d\u65f6\u671f\u4fe1\u606f\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return new JSONObject((Object)config);
        }
        String org = this.getLinkMsgValue(linkMsgObj, "P_MD_ORG");
        if (!StringUtils.hasText(org)) {
            config.setHasReadAuth(false);
            config.setMsg("\u5bf9\u4e0d\u8d77\uff0c\u5f53\u524d\u8282\u70b9\u7f3a\u5931\u5355\u4f4d\u65f6\u671f\u4fe1\u606f\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01");
            return new JSONObject((Object)config);
        }
        config.setHasReadAuth(true);
        return new JSONObject((Object)config);
    }

    private String getLinkMsgValue(JSONObject linkMsgObj, String alias) {
        if (!linkMsgObj.has(alias)) {
            return null;
        }
        Object data = linkMsgObj.get(alias);
        if (data == null) {
            return null;
        }
        if (data instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)data;
            return jsonArray.get(0).toString();
        }
        if (data instanceof String) {
            return (String)data;
        }
        return null;
    }

    public boolean hasAuth(String formId) {
        return this.authorityProvider.canReadForm(formId);
    }
}

