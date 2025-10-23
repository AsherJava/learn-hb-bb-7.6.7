/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationCategory
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFolder
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo
 *  com.jiuqi.nvwa.framework.automation.api.IAutomationCategory
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 */
package com.jiuqi.nr.dafafill.automation;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.dafafill.entity.DataFillDefinition;
import com.jiuqi.nr.dafafill.entity.DataFillGroup;
import com.jiuqi.nr.dafafill.service.IDataFillDefinitionService;
import com.jiuqi.nr.dafafill.service.IDataFillGroupService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationCategory;
import com.jiuqi.nvwa.framework.automation.api.AutomationFolder;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstanceAppInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationTransferResourceInfo;
import com.jiuqi.nvwa.framework.automation.api.IAutomationCategory;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationCategory(name="nvwa-datafill", title="\u81ea\u5b9a\u4e49\u5f55\u5165", path="/\u81ea\u5b9a\u4e49\u5f55\u5165")
public class DataFillAutomationCategory
implements IAutomationCategory {
    private static final String DATAFILL_PREFIX_NR = "com.jiuqi.nr.datafill";
    private static final List<AutomationInstance> AUTOMATION_INSTANCES = new ArrayList<AutomationInstance>();
    private static final String ROOT = "00000000000000000000000000000000";
    @Autowired
    private IDataFillGroupService dataFillGroupService;
    @Autowired
    private IDataFillDefinitionService dataFillDefinitionService;

    public List<AutomationInstance> getChildInstances(String pid) throws AutomationException {
        if (StringUtils.isEmpty((String)pid)) {
            pid = ROOT;
        }
        List<DataFillGroup> groups = this.dataFillGroupService.findByParentId(pid);
        List<DataFillDefinition> definitions = this.dataFillDefinitionService.findByParentId(pid);
        return definitions.stream().map(DataFillAutomationCategory::definition2AutomationInfo).collect(Collectors.toList());
    }

    public List<AutomationFolder> getChildFolders(String pid) throws AutomationException {
        if (StringUtils.isEmpty((String)pid)) {
            pid = ROOT;
        }
        List<DataFillGroup> groups = this.dataFillGroupService.findByParentId(pid);
        return groups.stream().map(DataFillAutomationCategory::group2AutomationFolder).collect(Collectors.toList());
    }

    public AutomationInstance getInstance(String guid) throws AutomationException {
        return DataFillAutomationCategory.definition2AutomationInfo(this.dataFillDefinitionService.findById(guid));
    }

    private static AutomationInstance definition2AutomationInfo(DataFillDefinition leaf) {
        AutomationInstance info = new AutomationInstance();
        info.setGuid(leaf.getId());
        info.setName(leaf.getId());
        info.setTitle(leaf.getTitle());
        info.setPid(leaf.getParentId());
        info.setType(DATAFILL_PREFIX_NR);
        return info;
    }

    private static AutomationFolder group2AutomationFolder(DataFillGroup node) {
        AutomationFolder info = new AutomationFolder();
        info.setGuid(node.getId());
        info.setPid(node.getParentId());
        info.setTitle(node.getTitle());
        return info;
    }

    public AutomationFolder getFolder(String guid) throws AutomationException {
        DataFillGroup groups = this.dataFillGroupService.findById(guid);
        return DataFillAutomationCategory.group2AutomationFolder(groups);
    }

    public List<AutomationInstance> search(String keyword) throws AutomationException {
        return this.dataFillDefinitionService.fuzzySearch(keyword).stream().map(DataFillAutomationCategory::definition2AutomationInfo).collect(Collectors.toList());
    }

    public List<String> getInstanceGuidPathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        DataFillDefinition dataFillDefinition = this.dataFillDefinitionService.findById(guid);
        pathList.add(dataFillDefinition.getId());
        String parent = dataFillDefinition.getParentId();
        while (!parent.equals(ROOT)) {
            DataFillGroup dataFillGroup = this.dataFillGroupService.findById(parent);
            pathList.add(dataFillGroup.getId());
            parent = dataFillGroup.getParentId();
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public List<String> getInstancePathList(String guid) throws AutomationException {
        ArrayList<String> pathList = new ArrayList<String>();
        DataFillDefinition dataFillDefinition = this.dataFillDefinitionService.findById(guid);
        pathList.add(dataFillDefinition.getTitle());
        String parent = dataFillDefinition.getParentId();
        while (!parent.equals(ROOT)) {
            DataFillGroup dataFillGroup = this.dataFillGroupService.findById(parent);
            pathList.add(dataFillGroup.getTitle());
            parent = dataFillGroup.getParentId();
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

