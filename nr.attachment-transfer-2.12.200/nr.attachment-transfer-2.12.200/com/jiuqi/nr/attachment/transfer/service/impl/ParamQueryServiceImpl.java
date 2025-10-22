/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.configurations.service.MappingConfigService
 */
package com.jiuqi.nr.attachment.transfer.service.impl;

import com.jiuqi.nr.attachment.transfer.dto.TaskParamDTO;
import com.jiuqi.nr.attachment.transfer.dto.TransferTreeDTO;
import com.jiuqi.nr.attachment.transfer.service.IParamQueryService;
import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import java.util.ArrayList;
import java.util.List;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.configurations.service.MappingConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ParamQueryServiceImpl
implements IParamQueryService {
    private static final Logger log = LoggerFactory.getLogger(ParamQueryServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private MappingConfigService mappingConfigService;

    @Override
    public List<ITree<TransferTreeDTO>> getTask(String taskKey) {
        ArrayList<ITree<TransferTreeDTO>> root = new ArrayList<ITree<TransferTreeDTO>>();
        List rootGroup = this.runTimeViewController.listTaskGroupByParentGroup(null);
        boolean first = true;
        for (TaskGroupDefine group : rootGroup) {
            ITree groupNode = new ITree((INode)new TransferTreeDTO(group.getKey(), group.getCode(), group.getTitle()));
            List taskDefines = this.runTimeViewController.listTaskByTaskGroup(group.getKey());
            if (!CollectionUtils.isEmpty(taskDefines)) {
                for (TaskDefine task : taskDefines) {
                    ITree taskNode = new ITree((INode)new TransferTreeDTO(task.getKey(), task.getTaskCode(), task.getTitle()));
                    groupNode.appendChild(taskNode);
                    if (task.getKey().equals(taskKey)) {
                        groupNode.setExpanded(true);
                        taskNode.setSelected(true);
                        groupNode.setExpanded(true);
                    }
                    taskNode.setLeaf(true);
                }
            } else {
                groupNode.setLeaf(true);
            }
            if (first && !StringUtils.hasText(taskKey)) {
                groupNode.setExpanded(true);
                first = false;
            }
            groupNode.setDisabled(true);
            root.add((ITree<TransferTreeDTO>)groupNode);
        }
        List taskDefines = this.runTimeViewController.listTaskByTaskGroup(null);
        for (TaskDefine task : taskDefines) {
            ITree taskNode = new ITree((INode)new TransferTreeDTO(task.getKey(), task.getTaskCode(), task.getTitle()));
            taskNode.setLeaf(true);
            root.add((ITree<TransferTreeDTO>)taskNode);
        }
        return root;
    }

    @Override
    public List<ITree<TransferTreeDTO>> getMapping(String formScheme, String mapping) {
        List mappingInReport = this.mappingConfigService.getAllMappingInReport(formScheme);
        ArrayList<ITree<TransferTreeDTO>> trees = new ArrayList<ITree<TransferTreeDTO>>(mappingInReport.size());
        for (SingleConfigInfo info : mappingInReport) {
            TransferTreeDTO treeDTO = new TransferTreeDTO(info.getConfigKey(), info.getConfigKey(), info.getConfigName());
            ITree node = new ITree((INode)treeDTO);
            if (info.getConfigKey().equals(mapping)) {
                node.setSelected(true);
            }
            node.setLeaf(true);
            trees.add((ITree<TransferTreeDTO>)node);
        }
        return trees;
    }

    @Override
    public TaskParamDTO getTaskParam(String taskKey, String period) {
        TaskParamDTO taskParamDTO = new TaskParamDTO();
        taskParamDTO.setTaskKey(taskKey);
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        if (task == null) {
            throw new RuntimeException("\u4efb\u52a1\u4e0d\u5b58\u5728");
        }
        String dw = task.getDw();
        taskParamDTO.setDw(dw);
        if (!StringUtils.hasText(period)) {
            period = task.getFromPeriod();
        }
        taskParamDTO.setPeriod(period);
        SchemePeriodLinkDefine scheme = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, task.getKey());
        taskParamDTO.setFormScheme(scheme.getSchemeKey());
        return taskParamDTO;
    }
}

