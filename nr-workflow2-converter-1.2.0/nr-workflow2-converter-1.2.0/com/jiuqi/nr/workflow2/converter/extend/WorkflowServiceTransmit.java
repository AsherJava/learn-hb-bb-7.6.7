/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.nr.authz.bean.UserTreeNode
 *  com.jiuqi.nr.authz.service.IRoleMgrService
 *  com.jiuqi.nr.authz.service.IUserTreeService
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkflowConfigService
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsTransmit
 *  com.jiuqi.nr.workflow2.settings.vo.source.CustomWorkflowDefine
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.converter.extend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.nr.authz.bean.UserTreeNode;
import com.jiuqi.nr.authz.service.IRoleMgrService;
import com.jiuqi.nr.authz.service.IUserTreeService;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowGroup;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowTreeNode;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.custom.service.CustomWorkflowConfigService;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.workflow2.settings.extend.WorkflowSettingsTransmit;
import com.jiuqi.nr.workflow2.settings.vo.source.CustomWorkflowDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceTransmit
implements WorkflowSettingsTransmit {
    @Resource
    private CustomWorkFolwService customWorkFolwService;
    @Resource
    private CustomWorkflowConfigService customWorkflowConfigService;
    @Resource
    private IRoleMgrService roleMgrService;
    @Resource
    private IUserTreeService userTreeService;

    public List<CustomWorkflowDefine> getCustomConfigSource(String taskId) {
        List customWorkflow = this.customWorkflowConfigService.queryPublishedWorkflowByTaskKey(taskId);
        return this.buildCustomWorkflowDefine(customWorkflow);
    }

    public List<String> convertUserRoleList(List<String> userList, List<String> roleList) {
        String userRole;
        String name;
        Optional first;
        ArrayList<String> userRoleList = new ArrayList<String>();
        for (String user : userList) {
            List userInfo = this.userTreeService.getUsersByIds(Collections.singletonList(user));
            first = userInfo.stream().findFirst();
            if (!first.isPresent()) continue;
            name = ((UserTreeNode)((ITree)first.get()).getData()).getTitle();
            userRole = user + ":" + name + ":" + 0;
            userRoleList.add(userRole);
        }
        for (String role : roleList) {
            List roleInfo = this.roleMgrService.getRolesByIds(Collections.singletonList(role));
            first = roleInfo.stream().findFirst();
            if (!first.isPresent()) continue;
            name = ((Role)first.get()).getTitle();
            userRole = role + ":" + name + ":" + 1;
            userRoleList.add(userRole);
        }
        return userRoleList;
    }

    public List<Role> getRoleSource() {
        return this.roleMgrService.getAllRoles();
    }

    private List<CustomWorkflowDefine> buildCustomWorkflowDefine(List<WorkFlowTreeNode> customWorkflow) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CustomWorkflowDefine> defineSource = new ArrayList<CustomWorkflowDefine>();
        for (WorkFlowTreeNode node : customWorkflow) {
            try {
                String objStr = mapper.writeValueAsString(node.getData());
                CustomWorkflowDefine define = new CustomWorkflowDefine();
                if (node.isGroup()) {
                    WorkFlowGroup workFlowGroup = (WorkFlowGroup)mapper.readValue(objStr, WorkFlowGroup.class);
                    define.setKey(workFlowGroup.getId());
                    define.setTitle(workFlowGroup.getTitle());
                    define.setIsLeaf(false);
                    List defineNode = this.customWorkFolwService.getWorkFlowDefineTNodeByGroupID(workFlowGroup.getId());
                    define.setChildren(this.buildCustomWorkflowDefine(defineNode));
                } else {
                    WorkFlowDefine workFlowDefine = (WorkFlowDefine)mapper.readValue(objStr, WorkFlowDefine.class);
                    define.setKey(workFlowDefine.getId());
                    define.setTitle(workFlowDefine.getTitle());
                    define.setIsLeaf(true);
                    define.setChildren(null);
                }
                defineSource.add(define);
            }
            catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return defineSource;
    }
}

