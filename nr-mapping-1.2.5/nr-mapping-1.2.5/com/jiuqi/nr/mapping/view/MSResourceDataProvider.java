/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 */
package com.jiuqi.nr.mapping.view;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.DesignTaskGroupLink;
import com.jiuqi.nr.mapping.bean.MappingScheme;
import com.jiuqi.nr.mapping.service.MappingSchemeService;
import com.jiuqi.nr.mapping.view.util.MappingViewUtil;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MSResourceDataProvider
implements IResourceDataProvider {
    protected final Logger logger = LoggerFactory.getLogger(MSResourceDataProvider.class);
    @Autowired
    private MappingSchemeService mappingservice;
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private RunTimeAuthViewController runTime;

    public List<ResourceGroup> getRootGroupsForTree() {
        ArrayList<ResourceGroup> groups = new ArrayList<ResourceGroup>();
        List<DesignTaskGroupDefine> allGroup = this.designTime.getAllTaskGroup();
        allGroup = this.sortByChina(allGroup);
        List allTask = this.runTime.getAllTaskDefines();
        allTask = allTask.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        HashSet hasGroupTask = new HashSet();
        for (DesignTaskGroupDefine group : allGroup) {
            List links;
            if (!StringUtils.hasText(group.getParentKey())) {
                groups.add(MappingViewUtil.convertDesignTaskGroup(group));
            }
            if (CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(group.getKey()))) continue;
            hasGroupTask.addAll(links.stream().map(l -> l.getTaskKey()).collect(Collectors.toSet()));
        }
        for (TaskDefine task : allTask) {
            if (hasGroupTask.contains(task.getKey())) continue;
            groups.add(MappingViewUtil.convertTaskDefine(task, null));
        }
        return groups;
    }

    public List<ResourceData> getRootResourceDatasForTree() {
        return Collections.emptyList();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public List<ResourceGroup> getGroupsForTree(String parentId) {
        ArrayList<ResourceGroup> groups = new ArrayList<ResourceGroup>();
        if (parentId.indexOf("G@") > -1) {
            List links;
            String groupId = parentId.replace("G@", "");
            List childGroups = this.designTime.getChildTaskGroups(groupId, false);
            if (!CollectionUtils.isEmpty(childGroups)) {
                groups.addAll(childGroups.stream().map(MappingViewUtil::convertDesignTaskGroup).collect(Collectors.toList()));
            }
            if (CollectionUtils.isEmpty(links = this.designTime.getGroupLinkByGroupKey(groupId))) return groups;
            HashSet<String> tasks = new HashSet<String>();
            for (DesignTaskGroupLink l : links) {
                try {
                    TaskDefine task = this.runTime.queryTaskDefine(l.getTaskKey());
                    if (task == null || !tasks.add(l.getTaskKey())) continue;
                    groups.add(MappingViewUtil.convertTaskDefine(task, groupId));
                }
                catch (Exception e) {
                    this.logger.error("\u83b7\u53d6\u4efb\u52a1\u5206\u7ec4\u4e0b\u7684\u4efb\u52a1\u5931\u8d25\uff01" + l.getTaskKey(), e);
                }
            }
            return groups;
        } else {
            if (parentId.indexOf("T@") <= -1) return groups;
            try {
                String taskId = parentId.replace("T@", "");
                List formSchemes = this.runTime.queryFormSchemeByTask(taskId);
                if (CollectionUtils.isEmpty(formSchemes)) return groups;
                groups.addAll(formSchemes.stream().map(MappingViewUtil::convertFormScheme).collect(Collectors.toList()));
                return groups;
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return groups;
    }

    public List<ResourceData> getResourceDatasForTree(String groupId) {
        return Collections.emptyList();
    }

    public boolean hasChildRsourceNode(ResourceGroup groupNode) {
        return groupNode.getId().indexOf("F@") <= -1;
    }

    public List<ResourceGroup> getRootGroupsForTable() {
        return Collections.emptyList();
    }

    public List<ResourceGroup> getGroupsForTable(String parentId) {
        return Collections.emptyList();
    }

    public List<ResourceData> getRootResourceDatasForTable() {
        return Collections.emptyList();
    }

    public List<ResourceData> getResourceDatasForTable(String groupId) {
        if (groupId.indexOf("T@") > -1) {
            String taskId = groupId.replace("T@", "");
            List<MappingScheme> mappings = this.mappingservice.getMappingByTask(taskId);
            if (!CollectionUtils.isEmpty(mappings)) {
                return mappings.stream().map(map -> MappingViewUtil.convertMappingScheme(map, this.runTime)).collect(Collectors.toList());
            }
        } else if (groupId.indexOf("F@") > -1) {
            String formId = groupId.replace("F@", "");
            try {
                List<MappingScheme> mappings;
                FormSchemeDefine form = this.runTime.getFormScheme(formId);
                if (form != null && !CollectionUtils.isEmpty(mappings = this.mappingservice.getMappingByTaskForm(form.getTaskKey(), formId))) {
                    return mappings.stream().map(m -> MappingViewUtil.convertMappingScheme(m, this.runTime)).collect(Collectors.toList());
                }
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
            }
        }
        return Collections.emptyList();
    }

    public ResourceGroup getGroup(String groupId) {
        return null;
    }

    public ResourceData getResourceData(String resourceId) {
        MappingScheme mapping = this.mappingservice.getByKey(resourceId);
        if (mapping != null) {
            return MappingViewUtil.convertMappingScheme(mapping, this.runTime);
        }
        return null;
    }

    public boolean hasResourceRootGroup() {
        return true;
    }

    private List<DesignTaskGroupDefine> sortByChina(List<DesignTaskGroupDefine> list) {
        return list.stream().sorted((o1, o2) -> {
            Collator comparator = Collator.getInstance(Locale.CHINA);
            return comparator.compare(o1.getTitle(), o2.getTitle());
        }).collect(Collectors.toList());
    }
}

