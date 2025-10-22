/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.internal.dao.DesignTaskGroupLinkDao;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.definition.internal.service.AbstractParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DesignTaskGroupLinkDefineService
extends AbstractParamService {
    @Autowired
    private DesignTaskGroupLinkDao designTaskGroupLinkDao;
    public static final String ALL_TASK_TITLE_KEY = "00000000-0000-0000-0000-000000000000";

    public void deleteLink(DesignTaskGroupLink define) throws Exception {
        this.designTaskGroupLinkDao.deleteLink(define);
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(define.getGroupKey()));
    }

    public List<DesignTaskGroupLink> getGroupLinkByTaskKey(String taskKey) {
        return this.designTaskGroupLinkDao.getGroupLinkByTaskKey(taskKey);
    }

    public List<DesignTaskGroupLink> getGroupLinkByGroupKey(String groupKey) {
        return this.designTaskGroupLinkDao.getGroupLinkByGroupKey(groupKey);
    }

    public List<DesignTaskGroupLink> getAllGroupLink() {
        return this.designTaskGroupLinkDao.getAllGroupLink();
    }

    public void deleteLinkByGroup(String groupKey) throws Exception {
        this.designTaskGroupLinkDao.deleteLinkByGroup(groupKey);
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.DELETE, Collections.singletonList(groupKey));
    }

    public void deleteLinkByTask(String taskKey) throws Exception {
        List<DesignTaskGroupLink> link = this.designTaskGroupLinkDao.getGroupLinkByTaskKey(taskKey);
        this.designTaskGroupLinkDao.deleteLinkByTask(taskKey);
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.DELETE, link.stream().map(DesignTaskGroupLink::getGroupKey).collect(Collectors.toList()));
    }

    public void insertLinks(DesignTaskGroupLink[] designTaskGroupLinks) throws Exception {
        if (designTaskGroupLinks == null || designTaskGroupLinks.length == 0) {
            return;
        }
        ArrayList<String> insertGroupKey = new ArrayList<String>();
        for (DesignTaskGroupLink designTaskGroupLink : designTaskGroupLinks) {
            if (!StringUtils.hasText(designTaskGroupLink.getGroupKey()) && ALL_TASK_TITLE_KEY.equals(designTaskGroupLink.getGroupKey())) continue;
            this.designTaskGroupLinkDao.insert(designTaskGroupLink);
            insertGroupKey.add(designTaskGroupLink.getGroupKey());
        }
        if (insertGroupKey.size() > 0) {
            this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.ADD, insertGroupKey);
        }
    }

    public void updateTaskGroupLink(DesignTaskGroupLink[] designTaskGroupLinks) throws Exception {
        for (DesignTaskGroupLink designTaskGroupLink : designTaskGroupLinks) {
            this.designTaskGroupLinkDao.update(designTaskGroupLink, new String[]{"groupKey", "taskKey"}, new String[]{designTaskGroupLink.getGroupKey(), designTaskGroupLink.getTaskKey()});
        }
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.UPDATE, Arrays.stream(designTaskGroupLinks).map(DesignTaskGroupLink::getGroupKey).collect(Collectors.toList()));
    }

    public void deleteTaskGroupLink(DesignTaskGroupLink[] designTaskGroupLinks) throws Exception {
        for (DesignTaskGroupLink designTaskGroupLink : designTaskGroupLinks) {
            this.designTaskGroupLinkDao.deleteLink(designTaskGroupLink);
        }
        this.onTaskGroupLinkChanged(ParamChangeEvent.ChangeType.DELETE, Arrays.stream(designTaskGroupLinks).map(DesignTaskGroupLink::getGroupKey).collect(Collectors.toList()));
    }
}

