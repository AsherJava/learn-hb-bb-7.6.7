/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.resourceview.query.IResourceDataProvider
 *  com.jiuqi.nvwa.resourceview.query.NodeType
 *  com.jiuqi.nvwa.resourceview.query.ResourceData
 *  com.jiuqi.nvwa.resourceview.query.ResourceGroup
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.multcheck2.view;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.common.GlobalType;
import com.jiuqi.nr.multcheck2.common.MultcheckUtil;
import com.jiuqi.nr.multcheck2.common.SchemeType;
import com.jiuqi.nr.multcheck2.service.IMCEnvService;
import com.jiuqi.nr.multcheck2.service.IMCSchemeService;
import com.jiuqi.nr.multcheck2.web.vo.MultcheckSchemeVO;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.resourceview.query.IResourceDataProvider;
import com.jiuqi.nvwa.resourceview.query.NodeType;
import com.jiuqi.nvwa.resourceview.query.ResourceData;
import com.jiuqi.nvwa.resourceview.query.ResourceGroup;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class MCResourceDataProvider
implements IResourceDataProvider {
    protected final Logger logger = LoggerFactory.getLogger(MCResourceDataProvider.class);
    @Autowired
    private IDesignTimeViewController designTime;
    @Autowired
    private RunTimeAuthViewController runTime;
    @Autowired
    private IMCSchemeService schemeService;
    @Autowired
    private IMCEnvService envService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    public List<ResourceGroup> getRootGroupsForTree() {
        ArrayList<ResourceGroup> groups = new ArrayList<ResourceGroup>();
        String type = MultcheckUtil.getType();
        List<TaskDefine> allTask = this.runTime.getAllTaskDefines();
        if (type.equals(GlobalType.EXIST.value())) {
            List<String> tasks = this.schemeService.getAllTask();
            if (CollectionUtils.isEmpty(tasks)) {
                return groups;
            }
            List<TaskDefine> schemeTask = allTask.stream().filter(e -> tasks.contains(e.getKey())).collect(Collectors.toList());
            this.envService.getNoDimTasks(schemeTask);
            for (TaskDefine taskDefine : schemeTask) {
                groups.add(MultcheckUtil.convertTaskDefine(taskDefine, null));
            }
            return groups;
        }
        List<DesignTaskGroupDefine> allGroup = this.designTime.getAllTaskGroup();
        allGroup = this.sortByChina(allGroup);
        allTask = allTask.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder).reversed()).collect(Collectors.toList());
        HashSet hasGroupTask = new HashSet();
        for (DesignTaskGroupDefine group : allGroup) {
            List taskDefines;
            if (!this.authorityProvider.canReadTaskGroup(group.getKey())) continue;
            if (!StringUtils.hasText(group.getParentKey())) {
                groups.add(MultcheckUtil.convertDesignTaskGroup((TaskGroupDefine)group));
            }
            if (CollectionUtils.isEmpty(taskDefines = this.runTime.getTaskDefinesFromGroup(group.getKey()))) continue;
            hasGroupTask.addAll(taskDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet()));
        }
        this.envService.getNoDimTasks(allTask);
        for (TaskDefine task : allTask) {
            if (hasGroupTask.contains(task.getKey())) continue;
            groups.add(MultcheckUtil.convertTaskDefine(task, null));
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
        if (parentId.contains("G@")) {
            List taskDefines;
            String groupId = parentId.replace("G@", "");
            List childGroups = this.runTime.getChildTaskGroups(groupId, false);
            if (!CollectionUtils.isEmpty(childGroups)) {
                groups.addAll(childGroups.stream().map(MultcheckUtil::convertDesignTaskGroup).collect(Collectors.toList()));
            }
            if (CollectionUtils.isEmpty(taskDefines = this.runTime.getTaskDefinesFromGroup(groupId))) return groups;
            this.envService.getNoDimTasks(taskDefines);
            for (TaskDefine task : taskDefines) {
                groups.add(MultcheckUtil.convertTaskDefine(task, groupId));
            }
            return groups;
        } else {
            if (!parentId.contains("T@")) return groups;
            try {
                String taskId = parentId.replace("T@", "");
                TaskDefine task = this.runTime.queryTaskDefine(taskId);
                List formSchemes = this.runTime.queryFormSchemeByTask(taskId);
                if (CollectionUtils.isEmpty(formSchemes)) return groups;
                groups.addAll(formSchemes.stream().map(f -> MultcheckUtil.convertFormScheme(f, task, this.periodEngineService, this.runTimeViewController)).collect(Collectors.toList()));
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
        return !groupNode.getId().contains("F@");
    }

    public List<ResourceGroup> getRootGroupsForTable() {
        return Collections.emptyList();
    }

    public List<ResourceGroup> getGroupsForTable(String parentId) {
        return Collections.emptyList();
    }

    public List<ResourceData> getResourceDatasForTable(String groupId) {
        ArrayList<ResourceData> resourceDatas = new ArrayList<ResourceData>();
        List<Object> schemes = new ArrayList();
        try {
            if (groupId.startsWith("F@")) {
                schemes = this.envService.getSchemeAndOrgCountByFormScheme(groupId.replaceFirst("F@", ""));
            } else if (groupId.startsWith("T@")) {
                schemes = this.envService.getSchemeAndOrgCountByTask(groupId.replaceFirst("T@", ""));
            }
            for (MultcheckSchemeVO scheme : schemes) {
                resourceDatas.add(this.getResourceData(scheme));
            }
            return resourceDatas;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    private ResourceData getResourceData(MultcheckSchemeVO scheme) throws Exception {
        ResourceData data = new ResourceData();
        data.setResourceType("com.jiuqi.nr.multcheck2");
        data.setResourceTypeTitle("\u5ba1\u6838\u65b9\u6848");
        data.setId(scheme.getKey());
        data.setName(scheme.getCode());
        data.setTitle(scheme.getTitle());
        data.setGroup(scheme.getFormScheme());
        data.setType(NodeType.NODE_DATA);
        if (scheme.getType().value() == SchemeType.COMMON.value()) {
            data.setIcon("icon nr-iconfont icon-16_DH_A_NR_piliangshenhe");
        } else {
            data.setIcon("icon nr-iconfont icon-16_DH_A_NR_piliangshangbao");
        }
        HashMap<String, String> customMap = new HashMap<String, String>();
        List<MultcheckItem> itemInfoList = this.schemeService.getItemInfoList(scheme.getKey());
        customMap.put("sitem", CollectionUtils.isEmpty(itemInfoList) ? "0" : String.valueOf(itemInfoList.size()));
        customMap.put("stype", scheme.getType().value() == 0 ? "\u72ec\u7acb\u529f\u80fd" : "\u901a\u7528");
        String orgType = null;
        switch (scheme.getOrgType()) {
            case SELECT: {
                orgType = "\u5df2\u9009\u62e9 | " + scheme.getOrgCount() + "\u5bb6\u5355\u4f4d";
                break;
            }
            case FORMULA: {
                orgType = "\u8868\u8fbe\u5f0f | " + scheme.getOrgFml();
                break;
            }
            default: {
                orgType = "\u5df2\u9009\u62e9 | \u5168\u90e8\u5355\u4f4d";
            }
        }
        customMap.put("org", orgType);
        customMap.put("formScheme", scheme.getFormScheme());
        customMap.put("formSchemeTitle", this.runTime.getFormScheme(scheme.getFormScheme()).getTitle());
        customMap.put("task", scheme.getTask());
        customMap.put("schemeType", String.valueOf(scheme.getType().value()));
        data.setCustomValues(customMap);
        return data;
    }

    public List<ResourceData> getRootResourceDatasForTable() {
        return Collections.emptyList();
    }

    public ResourceGroup getGroup(String groupId) {
        return null;
    }

    public ResourceData getResourceData(String resourceId) {
        MultcheckScheme scheme = this.schemeService.getSchemeByKey(resourceId);
        if (scheme != null) {
            MultcheckSchemeVO schemeVO = new MultcheckSchemeVO();
            BeanUtils.copyProperties(scheme, schemeVO);
            try {
                return this.getResourceData(schemeVO);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
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

