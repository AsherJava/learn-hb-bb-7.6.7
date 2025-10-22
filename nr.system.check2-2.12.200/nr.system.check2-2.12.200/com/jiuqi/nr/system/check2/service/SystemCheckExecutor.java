/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.CheckOptionType
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckAction
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResource
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup
 */
package com.jiuqi.nr.system.check2.service;

import com.jiuqi.nr.common.systemcheck.CheckOptionType;
import com.jiuqi.nr.common.systemcheck.spi.ICheckAction;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResource;
import com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup;
import com.jiuqi.nr.system.check2.exception.ExecutorResultEnum;
import com.jiuqi.nr.system.check2.vo.ExecutorResult;
import com.jiuqi.nr.system.check2.vo.ResourceGroupVO;
import com.jiuqi.nr.system.check2.vo.ResourceVO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class SystemCheckExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SystemCheckExecutor.class);
    public static final String EXECUTOR_SUCCESS = "\u6267\u884c\u6210\u529f";
    private List<ICheckResourceGroup> allCheckResourceGroups;
    private Map<String, ICheckResourceGroup> checkResourceGroupMaps;
    private List<ICheckResource> allCheckResources;
    private Map<String, ICheckResource> checkResourceMaps;

    @Autowired(required=false)
    private void setCheckResourceGroupList(List<ICheckResourceGroup> checkResourceGroups) {
        if (checkResourceGroups != null) {
            this.allCheckResourceGroups = checkResourceGroups;
            this.allCheckResourceGroups.sort(Comparator.comparing(ICheckResourceGroup::getOrder, Comparator.nullsLast(Double::compareTo)));
            this.checkResourceGroupMaps = checkResourceGroups.stream().collect(Collectors.toMap(ICheckResourceGroup::getKey, a -> a, (k1, k2) -> k2));
        }
    }

    @Autowired(required=false)
    private void setCheckResourceList(List<ICheckResource> checkResources) {
        if (checkResources != null) {
            this.allCheckResources = checkResources;
            this.allCheckResources.sort(Comparator.comparing(ICheckResource::getOrder, Comparator.nullsLast(Double::compareTo)));
            this.checkResourceMaps = checkResources.stream().collect(Collectors.toMap(ICheckResource::getKey, a -> a, (k1, k2) -> k2));
        }
    }

    public List<ResourceGroupVO> getResourcesGroup() {
        ArrayList<ResourceGroupVO> result = new ArrayList<ResourceGroupVO>();
        if (!CollectionUtils.isEmpty(this.allCheckResources)) {
            HashMap checkResourceMapsForGroup = new HashMap();
            this.allCheckResources.forEach(a -> {
                if (StringUtils.hasLength(a.getGroupKey())) {
                    String groupKey = a.getGroupKey();
                    boolean hasGroup = this.checkResourceGroupMaps.containsKey(groupKey);
                    ResourceVO resourceVO = new ResourceVO((ICheckResource)a);
                    if (hasGroup) {
                        checkResourceMapsForGroup.computeIfAbsent(groupKey, key -> new ArrayList()).add(resourceVO);
                    } else {
                        checkResourceMapsForGroup.computeIfAbsent("group-0000-other_group", key -> new ArrayList()).add(resourceVO);
                    }
                }
            });
            for (ICheckResourceGroup checkResourceGroup : this.allCheckResourceGroups) {
                List resources = (List)checkResourceMapsForGroup.get(checkResourceGroup.getKey());
                ResourceGroupVO resourceGroupVO = new ResourceGroupVO(checkResourceGroup);
                resourceGroupVO.setResources(resources);
                result.add(resourceGroupVO);
            }
        }
        return result;
    }

    public List<ResourceVO> getResourceVOS() {
        ArrayList<ResourceVO> result = new ArrayList<ResourceVO>();
        for (ICheckResource checkResource : this.allCheckResources) {
            ResourceVO resourceVO = new ResourceVO(checkResource);
            result.add(resourceVO);
        }
        return result;
    }

    public ExecutorResult doExecutor(String userId, String resourceKey) {
        ExecutorResult executorResult = null;
        if (!StringUtils.hasLength(resourceKey)) {
            executorResult = new ExecutorResult(resourceKey, false, ExecutorResultEnum.EXECUTOR_RESOURCE_EMPTY.getMessage());
            logger.error("");
            return executorResult;
        }
        ICheckResource checkResource = this.checkResourceMaps.get(resourceKey);
        if (checkResource == null) {
            executorResult = new ExecutorResult(resourceKey, false, ExecutorResultEnum.EXECUTOR_RESOURCE_EMPTY.getMessage());
            return executorResult;
        }
        boolean hasAuth = checkResource.checkAuth(userId);
        if (!hasAuth) {
            executorResult = new ExecutorResult(resourceKey, false, ExecutorResultEnum.EXECUTOR_AUTH_ERROR.getMessage() + " " + userId);
            return executorResult;
        }
        ICheckAction checkOption = checkResource.getCheckOption();
        Map optionResult = null;
        CheckOptionType optionType = checkOption.getOptionType();
        try {
            switch (optionType) {
                case EVENT: {
                    optionResult = checkOption.option();
                    executorResult = new ExecutorResult(resourceKey, true, EXECUTOR_SUCCESS, optionResult);
                    break;
                }
                case MODAL: 
                case PAGE: {
                    optionResult = checkOption.getPageMessage();
                    executorResult = new ExecutorResult(resourceKey, true, EXECUTOR_SUCCESS, optionResult);
                    break;
                }
                default: {
                    executorResult = new ExecutorResult(resourceKey, false, ExecutorResultEnum.EXECUTOR_AUTH_ERROR.getMessage() + " " + userId);
                    break;
                }
            }
        }
        catch (Exception e) {
            executorResult = new ExecutorResult(resourceKey, false, e.getMessage());
        }
        return executorResult;
    }

    public List<ResourceVO> doSearch(String groupKey, String keyword) {
        ArrayList<ResourceVO> result = new ArrayList<ResourceVO>();
        List<Object> checkResources = new ArrayList();
        checkResources = !StringUtils.hasLength(groupKey) ? this.allCheckResources : this.allCheckResources.stream().filter(a -> a.getGroupKey().equals(groupKey)).collect(Collectors.toList());
        for (ICheckResource checkResource : checkResources) {
            if (!checkResource.getTitle().contains(keyword) && !checkResource.getTagMessages().contains(keyword)) continue;
            ResourceVO resourceVO = new ResourceVO(checkResource);
            result.add(resourceVO);
        }
        return result;
    }
}

