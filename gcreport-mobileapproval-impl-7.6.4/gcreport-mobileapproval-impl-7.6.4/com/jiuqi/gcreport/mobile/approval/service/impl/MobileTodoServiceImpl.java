/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.mobile.approval.vo.MobileTodoParamInfo
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.todo.entity.TodoVO
 *  com.jiuqi.nr.workflow2.todo.entity.TodoTableData
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoType
 *  com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext
 *  com.jiuqi.nr.workflow2.todo.envimpl.PageInfo
 *  com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl
 *  com.jiuqi.nr.workflow2.todo.service.TodoQueryService
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.gcreport.mobile.approval.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.mobile.approval.service.MobileTodoService;
import com.jiuqi.gcreport.mobile.approval.vo.MobileTodoParamInfo;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.nr.workflow2.todo.entity.TodoTableData;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl;
import com.jiuqi.nr.workflow2.todo.service.TodoQueryService;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import java.util.Collections;
import java.util.HashMap;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileTodoServiceImpl
implements MobileTodoService {
    private static final Logger logger = LogFactory.getLogger(MobileTodoServiceImpl.class);
    @Autowired
    private TodoQueryService todoQueryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public TodoVO queryTodoDataInfo(MobileTodoParamInfo mobileTodoParamInfo) {
        try {
            this.checkParams(mobileTodoParamInfo);
            TodoTableDataContextImpl context = new TodoTableDataContextImpl();
            context.setTaskId(mobileTodoParamInfo.getTaskId());
            context.setPeriod(mobileTodoParamInfo.getPeriod());
            context.setWorkflowNode(mobileTodoParamInfo.getWorkflowNode());
            context.setRangeUnits(Collections.singletonList(mobileTodoParamInfo.getUnitId()));
            if (StringUtils.isEmpty((String)mobileTodoParamInfo.getTodoType())) {
                context.setTodoType(TodoType.UNCOMPLETED.title);
            } else {
                context.setTodoType(mobileTodoParamInfo.getTodoType());
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurrentPage(1);
            pageInfo.setPageSize(100);
            context.setPageInfo(pageInfo);
            context.setEntityCaliber(mobileTodoParamInfo.getOrgType());
            TodoTableDataInfoImpl todoTableDataInfo = this.todoQueryService.getTodoTableData((TodoTableDataContext)context);
            TaskDefine taskDefine = this.runTimeViewController.getTask(mobileTodoParamInfo.getTaskId());
            if (todoTableDataInfo == null) {
                logger.error("\u67e5\u8be2\u5f85\u529e\u4e8b\u9879\u6570\u636e\u5931\u8d25\uff0c\u672a\u67e5\u8be2\u5230\u6570\u636e\uff0c\u4efb\u52a1\u3010{}\u3011\uff0c\u65f6\u671f\u3010{}\u3011\uff0c\u8282\u70b9\u3010{}\u3011\uff0c\u5355\u4f4d\u3010{}\u3011\uff0c\u7c7b\u578b\u3010{}\u3011\uff0c\u64cd\u4f5c\u4eba\u3010{}\u3011", taskDefine == null ? mobileTodoParamInfo.getTaskId() : taskDefine.getTitle(), mobileTodoParamInfo.getPeriod(), mobileTodoParamInfo.getWorkflowNode(), mobileTodoParamInfo.getUnitId(), StringUtils.isEmpty((String)mobileTodoParamInfo.getTodoType()) ? "\u672a\u529e" : mobileTodoParamInfo.getTodoType(), mobileTodoParamInfo.getOperator());
                return new TodoVO();
            }
            return this.initTodoVO(mobileTodoParamInfo, todoTableDataInfo, taskDefine);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5f85\u529e\u4e8b\u9879\u6570\u636e\u5931\u8d25\uff0c", e);
            throw new RuntimeException("\u67e5\u8be2\u5f85\u529e\u4e8b\u9879\u6570\u636e\u5931\u8d25\uff0c", e);
        }
    }

    private void checkParams(MobileTodoParamInfo mobileTodoParamInfo) {
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getTaskId())) {
            throw new RuntimeException("\u4efb\u52a1ID\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getPeriod())) {
            throw new RuntimeException("\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getWorkflowNode())) {
            throw new RuntimeException("\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getUnitId())) {
            throw new RuntimeException("\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getTodoType())) {
            throw new RuntimeException("\u5f85\u529e\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getOrgType())) {
            throw new RuntimeException("\u673a\u6784\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)mobileTodoParamInfo.getOperator())) {
            throw new RuntimeException("\u64cd\u4f5c\u4eba\u4e0d\u80fd\u4e3a\u7a7a");
        }
    }

    private TodoVO initTodoVO(MobileTodoParamInfo mobileTodoParamInfo, TodoTableDataInfoImpl todoTableDataInfo, TaskDefine taskDefine) {
        TodoVO todoVO = new TodoVO();
        if (todoTableDataInfo.getTotalNum() > 1) {
            logger.error("\u67e5\u8be2\u5f85\u529e\u4e8b\u9879\u6570\u636e\u5931\u8d25\uff0c\u67e5\u8be2\u5230\u591a\u6761\u6570\u636e\uff0c\u4efb\u52a1\u3010{}\u3011\uff0c\u65f6\u671f\u3010{}\u3011\uff0c\u8282\u70b9\u3010{}\u3011\uff0c\u5355\u4f4d\u3010{}\u3011\uff0c\u7c7b\u578b\u3010{}\u3011\uff0c\u64cd\u4f5c\u4eba\u3010{}\u3011", taskDefine == null ? mobileTodoParamInfo.getTaskId() : taskDefine.getTitle(), mobileTodoParamInfo.getPeriod(), mobileTodoParamInfo.getWorkflowNode(), mobileTodoParamInfo.getUnitId(), StringUtils.isEmpty((String)mobileTodoParamInfo.getTodoType()) ? "\u672a\u529e" : mobileTodoParamInfo.getTodoType(), mobileTodoParamInfo.getOperator());
        }
        TodoTableData todoTableData = (TodoTableData)todoTableDataInfo.getTableData().get(0);
        TableDataShowText tableDataShowText = todoTableData.getTableDataShowText();
        TableDataActualParam tableDataActualParam = todoTableData.getTableDataActualParam();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("taskId", mobileTodoParamInfo.getTaskId());
        params.put("taskTitle", taskDefine == null ? mobileTodoParamInfo.getTaskId() : taskDefine.getTitle());
        FormSchemeDefine formSchemeDefine = this.funcExecuteService.queryFormScheme(mobileTodoParamInfo.getTaskId(), mobileTodoParamInfo.getPeriod());
        if (formSchemeDefine == null) {
            throw new RuntimeException("\u672a\u67e5\u8be2\u5230\u62a5\u8868\u65b9\u6848\uff0c\u4efb\u52a1ID\u3010" + mobileTodoParamInfo.getTaskId() + "\u3011\uff0c\u65f6\u671f\u3010" + mobileTodoParamInfo.getPeriod() + "\u3011");
        }
        params.put("formSchemeId", formSchemeDefine.getKey());
        params.put("formSchemeTitle", formSchemeDefine.getTitle());
        params.put("unitId", mobileTodoParamInfo.getUnitId());
        params.put("unitName", StringUtils.isEmpty((String)tableDataShowText.getUnit()) ? mobileTodoParamInfo.getUnitId() : tableDataShowText.getUnit());
        params.put("period", mobileTodoParamInfo.getPeriod());
        params.put("type", this.getWorkflowType(formSchemeDefine));
        params.put("operator", this.getUserName(mobileTodoParamInfo.getOperator()));
        params.put("reportId", tableDataActualParam.getWorkflowObject());
        params.put("reportName", tableDataShowText.getWorkflowObject());
        todoVO.setParams(params);
        return todoVO;
    }

    private String getUserName(String userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        UserDO userDOs = this.authUserClient.get(userDTO);
        return userDOs == null ? "" : userDOs.getName();
    }

    private String getWorkflowType(FormSchemeDefine formSchemeDefine) {
        TaskFlowsDefine taskFlowsDefine = formSchemeDefine.getFlowsSetting();
        WorkFlowType workFlowType = taskFlowsDefine.getWordFlowType();
        if (workFlowType == WorkFlowType.FORM) {
            return "form";
        }
        if (workFlowType == WorkFlowType.GROUP) {
            return "group";
        }
        if (workFlowType == WorkFlowType.ENTITY) {
            return "entity";
        }
        logger.error("\u672a\u77e5\u7684\u5de5\u4f5c\u6d41\u7c7b\u578b\uff0c\u65e0\u6cd5\u83b7\u53d6\u5de5\u4f5c\u6d41\u7c7b\u578b");
        return "";
    }
}

