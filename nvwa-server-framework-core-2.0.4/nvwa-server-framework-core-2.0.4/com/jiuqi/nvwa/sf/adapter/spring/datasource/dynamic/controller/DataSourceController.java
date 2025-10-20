/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.model.Result
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.controller;

import com.jiuqi.np.core.model.Result;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.DataSourceInfoTreeNodeDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto.NvwaDataSourceInfoDto;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDataSourceInfoFilterService;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import com.jiuqi.nvwa.sf.adapter.spring.util.DataSourceInfoTreeNodeComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/datasource"})
public class DataSourceController {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);
    private final IDynamicDataSourceManager dynamicDataSourceManager;
    private final DataSourceInfoTreeNodeComparator comparator = new DataSourceInfoTreeNodeComparator();
    private final IDataSourceInfoFilterService dataSourceInfoFilterService;

    public DataSourceController(@Autowired IDynamicDataSourceManager dynamicDataSourceManager, @Autowired IDataSourceInfoFilterService dataSourceInfoFilterService) {
        this.dynamicDataSourceManager = dynamicDataSourceManager;
        this.dataSourceInfoFilterService = dataSourceInfoFilterService;
    }

    @GetMapping(value={"/list"})
    public Result<List<NvwaDataSourceInfoDto>> listDataSources() throws Exception {
        List<NvwaDataSourceInfoDto> allDataSources = this.dynamicDataSourceManager.listAllDataSourceInfos();
        try {
            return Result.succeed(this.dataSourceInfoFilterService.doFilter(allDataSources));
        }
        catch (Exception e) {
            logger.error("\u8bf7\u6c42\u6570\u636e\u6e90\u5217\u8868\u5931\u8d25: " + e.getMessage(), e);
            return Result.failed((String)("\u8bf7\u6c42\u6570\u636e\u6e90\u5217\u8868\u5931\u8d25: " + e.getMessage()));
        }
    }

    @GetMapping(value={"/tree"})
    public Result<DataSourceInfoTreeNodeDto> listDataSourcesTree() {
        try {
            return Result.succeed((Object)this.buildTree());
        }
        catch (Exception e) {
            logger.error("\u8bf7\u6c42\u6570\u636e\u6e90\u6811\u5931\u8d25: " + e.getMessage(), e);
            return Result.failed((String)("\u8bf7\u6c42\u6570\u636e\u6e90\u6811\u5931\u8d25: " + e.getMessage()));
        }
    }

    private DataSourceInfoTreeNodeDto buildTree() throws Exception {
        DataSourceInfoTreeNodeDto systemDataSourceRootNode = new DataSourceInfoTreeNodeDto("system_datasource_group_root", "system_datasource_group", "\u7cfb\u7edf\u6570\u636e\u6e90");
        DataSourceInfoTreeNodeDto extDataSourceRootNode = new DataSourceInfoTreeNodeDto("ext_datasource_group_root", "ext_datasource_group", "\u5916\u90e8\u6570\u636e\u6e90");
        List<NvwaDataSourceInfoDto> dataSourceInfos = this.dataSourceInfoFilterService.doFilter(this.dynamicDataSourceManager.listAllDataSourceInfos());
        ArrayList<DataSourceInfoTreeNodeDto> systemDataSourceInfos = new ArrayList<DataSourceInfoTreeNodeDto>();
        dataSourceInfos.stream().map(DataSourceInfoTreeNodeDto::fromInfoDto).forEach(node -> {
            NvwaDataSourceGroup group = node.getGroup();
            if (group == NvwaDataSourceGroup.SYSTEM_DATASOURCE_GROUP) {
                systemDataSourceInfos.add((DataSourceInfoTreeNodeDto)node);
            } else {
                this.insertDataSourceInfoToTree((DataSourceInfoTreeNodeDto)node, extDataSourceRootNode);
            }
        });
        systemDataSourceRootNode.setChildren(systemDataSourceInfos);
        this.sortTree(systemDataSourceRootNode);
        this.sortTree(extDataSourceRootNode);
        DataSourceInfoTreeNodeDto rootNode = new DataSourceInfoTreeNodeDto("root", "root", "\u6570\u636e\u6e90");
        ArrayList<DataSourceInfoTreeNodeDto> rootChildren = new ArrayList<DataSourceInfoTreeNodeDto>();
        if (extDataSourceRootNode.getChildren() != null) {
            rootChildren.add(extDataSourceRootNode);
        }
        rootChildren.add(systemDataSourceRootNode);
        rootNode.setChildren(rootChildren);
        return rootNode;
    }

    private void insertDataSourceInfoToTree(DataSourceInfoTreeNodeDto node, DataSourceInfoTreeNodeDto rootNode) {
        NvwaDataSourceGroup group = node.getGroup();
        String groupId = group.getDsGroupGuid();
        if (rootNode.getChildren() == null) {
            rootNode.setChildren(new ArrayList<DataSourceInfoTreeNodeDto>());
        }
        if (rootNode.getChildren().stream().anyMatch(child -> child.getKey().equals(groupId))) {
            DataSourceInfoTreeNodeDto targetNode = rootNode.getChildren().stream().filter(child -> child.getKey().equals(groupId)).findFirst().orElseThrow(IllegalArgumentException::new);
            targetNode.getChildren().add(node);
        } else {
            DataSourceInfoTreeNodeDto groupNode = new DataSourceInfoTreeNodeDto(groupId, groupId, group.getDsGroupTitle());
            groupNode.setChildren(new ArrayList<DataSourceInfoTreeNodeDto>(Collections.singletonList(node)));
            rootNode.getChildren().add(groupNode);
        }
    }

    private void sortTree(DataSourceInfoTreeNodeDto root) {
        List<DataSourceInfoTreeNodeDto> children = root.getChildren();
        if (children != null) {
            children.sort(this.comparator);
            children.forEach(this::sortTree);
        }
    }
}

