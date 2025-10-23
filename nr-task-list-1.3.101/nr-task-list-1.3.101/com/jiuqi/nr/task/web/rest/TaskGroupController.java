/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.task.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.dto.TaskGroupDTO;
import com.jiuqi.nr.task.exception.TaskException;
import com.jiuqi.nr.task.service.ITaskGroupService;
import com.jiuqi.nr.task.web.vo.ResourceSearchVO;
import com.jiuqi.nr.task.web.vo.TaskGroupTreeNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/v1/taskGroup/"})
@Api(tags={"\u4efb\u52a1\u5206\u7ec4\u8d44\u6e90\u7ba1\u7406"})
public class TaskGroupController {
    public static final String TASK_GROUP_RESOURCE_URL = "/api/v1/taskGroup/";
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    @Autowired
    private ITaskGroupService taskGroupService;

    @ApiOperation(value="\u65b0\u589e\u4efb\u52a1\u5206\u7ec4")
    @PostMapping(value={"add"})
    @TaskLog(operation="\u6dfb\u52a0\u4efb\u52a1\u5206\u7ec4")
    public String add(@RequestBody TaskGroupDTO taskGroupDTO) throws JQException {
        try {
            return this.taskGroupService.insertTaskGroup(taskGroupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_28, e.getMessage());
        }
    }

    @ApiOperation(value="\u6821\u9a8c\u4efb\u52a1\u5206\u7ec4\u540d\u79f0")
    @PostMapping(value={"check-task-group-title"})
    public boolean checkTaskGroupTitle(@RequestBody TaskGroupDTO taskGroupDTO) throws JQException {
        return this.taskGroupService.checkTaskGroup(taskGroupDTO);
    }

    @ApiOperation(value="\u5220\u9664\u4efb\u52a1\u5206\u7ec4")
    @PostMapping(value={"delete/{key}"})
    @TaskLog(operation="\u5220\u9664\u4efb\u52a1\u5206\u7ec4")
    public void delete(@PathVariable(value="key") String key) throws JQException {
        try {
            this.taskGroupService.deleteTaskGroup(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_007, e.getMessage());
        }
    }

    @ApiOperation(value="\u4fee\u6539\u4efb\u52a1\u5206\u7ec4")
    @PostMapping(value={"update"})
    @TaskLog(operation="\u4fee\u6539\u4efb\u52a1\u5206\u7ec4")
    public void update(@RequestBody TaskGroupDTO taskGroupDTO) throws JQException {
        try {
            this.taskGroupService.updateTaskGroup(taskGroupDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_005, e.getMessage());
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u4efb\u52a1\u5206\u7ec4")
    @PostMapping(value={"move/{key}/{way}"})
    @TaskLog(operation="\u79fb\u52a8\u4efb\u52a1\u5206\u7ec4")
    public void moveGroup(@PathVariable String key, @PathVariable int way) throws JQException {
        try {
            this.taskGroupService.changeTaskGroupOrder(key, way);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_004, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4")
    @GetMapping(value={"get/{key}"})
    public TaskGroupDTO get(@PathVariable String key) throws JQException {
        try {
            return this.taskGroupService.queryTaskGroup(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_008, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5b50\u5206\u7ec4\u6811\u578b")
    @GetMapping(value={"listChild/{key}"})
    public List<UITreeNode<TaskGroupTreeNode>> listChildTaskGroupTree(@PathVariable String key) throws JQException {
        try {
            return this.taskGroupService.listChildTaskGroup(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_009, e.getMessage());
        }
    }

    @ApiOperation(value="\u6a21\u7cca\u641c\u7d22")
    @PostMapping(value={"fuzzySearch"})
    public List<ResourceSearchVO> fuzzySearchTaskGroup(@RequestBody TaskGroupDTO taskGroupDTO) throws JQException {
        try {
            return this.taskGroupService.fuzzySearchTaskGroup(taskGroupDTO.getTitle());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_006, e.getMessage());
        }
    }

    @ApiOperation(value="\u521d\u59cb\u5316\u4efb\u52a1\u5206\u7ec4\u6811\u578b")
    @GetMapping(value={"getRoot"})
    @RequiresPermissions(value={"nr:task_group:query"})
    public List<UITreeNode<TaskGroupTreeNode>> getRootGroupTree() throws JQException {
        try {
            return this.taskGroupService.getRootGroupTree();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_009, e.getMessage());
        }
    }

    @ApiOperation(value="\u5f02\u6b65\u5b9a\u4f4d\u4efb\u52a1\u5206\u7ec4")
    @GetMapping(value={"location/{key}"})
    public List<UITreeNode<TaskGroupTreeNode>> location(@PathVariable String key) throws JQException {
        try {
            return this.taskGroupService.locationTaskGroup(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_009, e.getMessage());
        }
    }

    @ApiOperation(value="\u5f02\u6b65\u5b9a\u4f4d\u4efb\u52a1\u7684\u5206\u7ec4")
    @GetMapping(value={"locationByTask/{key}"})
    public List<UITreeNode<TaskGroupTreeNode>> locationByTask(@PathVariable String key) throws JQException {
        try {
            return this.taskGroupService.locationByTask(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASKGROUP_ERROE_009, e.getMessage());
        }
    }
}

