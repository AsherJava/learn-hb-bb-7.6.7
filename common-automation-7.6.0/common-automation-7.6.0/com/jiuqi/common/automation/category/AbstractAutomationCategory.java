/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo
 *  com.jiuqi.nvwa.framework.automation.api.IAutomationCategory
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 */
package com.jiuqi.common.automation.category;

import com.jiuqi.common.automation.gather.ICommonAutomationGather;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo;
import com.jiuqi.nvwa.framework.automation.api.IAutomationCategory;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAutomationCategory
implements IAutomationCategory {
    @Autowired
    private ICommonAutomationGather commonAutomationGather;

    public abstract String categoryName();

    public List<AutomationInstance> getChildInstances(String pid) {
        String key = StringUtils.isEmpty((String)pid) || "root".equalsIgnoreCase(pid) ? this.categoryName() : pid;
        return this.commonAutomationGather.getAutomationInstanceList(this.categoryName()).stream().filter(t -> Objects.equals(t.getPid(), key)).collect(Collectors.toList());
    }

    public List<AutomationFolder> getChildFolders(String pid) {
        String key = StringUtils.isEmpty((String)pid) || "root".equalsIgnoreCase(pid) ? this.categoryName() : pid;
        return this.commonAutomationGather.getFolderList(this.categoryName()).stream().filter(t -> Objects.equals(t.getPid(), key)).collect(Collectors.toList());
    }

    public AutomationInstance getInstance(String guid) throws AutomationException {
        return this.commonAutomationGather.getAutomationInstanceList(this.categoryName()).stream().filter(t -> t.getGuid().equals(guid)).findFirst().orElse(null);
    }

    public AutomationFolder getFolder(String guid) {
        return this.commonAutomationGather.getFolderList(this.categoryName()).stream().filter(t -> t.getGuid().equals(guid)).findFirst().orElse(null);
    }

    public List<AutomationInstance> search(String keyword) {
        return this.commonAutomationGather.getAutomationInstanceList(this.categoryName()).stream().filter(t -> t.getName().toUpperCase().contains(keyword.toUpperCase()) || t.getTitle().toUpperCase().contains(keyword.toUpperCase())).collect(Collectors.toList());
    }

    public List<String> getInstanceGuidPathList(String guid) throws AutomationException {
        String pid = this.commonAutomationGather.getAutomationInstanceList(this.categoryName()).stream().filter(t -> t.getGuid().equals(guid)).findFirst().map(AutomationInstance::getPid).orElse(null);
        if (StringUtils.isEmpty((String)pid) || "root".equalsIgnoreCase(pid)) {
            return null;
        }
        ArrayList<String> pathList = new ArrayList<String>();
        AutomationFolder folder = this.getFolder(pid);
        while (Objects.nonNull(folder)) {
            pathList.add(folder.getGuid());
            folder = this.getFolder(folder.getPid());
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public List<String> getInstancePathList(String guid) throws AutomationException {
        String pid = this.commonAutomationGather.getAutomationInstanceList(this.categoryName()).stream().filter(t -> t.getGuid().equals(guid)).findFirst().map(AutomationInstance::getPid).orElse(null);
        if (StringUtils.isEmpty((String)pid) || "root".equalsIgnoreCase(pid)) {
            return null;
        }
        ArrayList<String> pathList = new ArrayList<String>();
        AutomationFolder folder = this.getFolder(pid);
        while (Objects.nonNull(folder)) {
            pathList.add(folder.getTitle());
            folder = this.getFolder(folder.getPid());
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public AutomationInstanceAppInfo getInstanceAppInfo(String guid) throws AutomationException {
        return null;
    }

    public AutomationTransferResourceInfo getInstanceTransferResourceInfo(String guid) throws AutomationException {
        return null;
    }
}

