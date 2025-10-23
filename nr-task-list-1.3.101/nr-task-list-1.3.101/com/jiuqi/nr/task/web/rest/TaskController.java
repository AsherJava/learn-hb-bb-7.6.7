/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.entity.component.tree.vo.TreeNode
 *  com.jiuqi.nr.entity.component.tree.vo.TreeParam
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  com.jiuqi.nr.task.api.common.Constants$ActionType
 *  com.jiuqi.nr.task.api.tree.TreeData
 *  com.jiuqi.nr.task.api.tree.UITreeNode
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
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
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.task.api.aop.TaskLog;
import com.jiuqi.nr.task.api.common.Constants;
import com.jiuqi.nr.task.api.tree.TreeData;
import com.jiuqi.nr.task.api.tree.UITreeNode;
import com.jiuqi.nr.task.dto.FormSchemeStatusDTO;
import com.jiuqi.nr.task.dto.TaskActionDTO;
import com.jiuqi.nr.task.dto.TaskGroupDTO;
import com.jiuqi.nr.task.dto.taskOrderMoveDTO;
import com.jiuqi.nr.task.exception.TaskException;
import com.jiuqi.nr.task.internal.action.service.ActionService;
import com.jiuqi.nr.task.internal.upgrade.TaskUpgradeService;
import com.jiuqi.nr.task.internal.upgrade.dto.UpgradeResult;
import com.jiuqi.nr.task.service.ITaskService;
import com.jiuqi.nr.task.tools.TaskTools;
import com.jiuqi.nr.task.util.tree.EntityTree;
import com.jiuqi.nr.task.web.param.UnitTreeDataParam;
import com.jiuqi.nr.task.web.vo.DwTaskOptionVO;
import com.jiuqi.nr.task.web.vo.TaskItemVO;
import com.jiuqi.nr.task.web.vo.TaskParamVO;
import com.jiuqi.nr.task.web.vo.TaskPublishInfo;
import com.jiuqi.nr.task.web.vo.TaskResourceSearchVO;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@ApiOperation(value="\u4efb\u52a1\u5b9a\u4e49")
@RequestMapping(value={"api/v1/task"})
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private ITaskService taskService;
    @Autowired
    private TaskTools taskTools;
    @Autowired
    private TaskUpgradeService taskUpgradeService;
    @Autowired
    private ActionService actionService;

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u65b9\u6848\u521d\u59cb\u5316\u4e00\u4e2a\u65b0\u4efb\u52a1")
    @GetMapping(value={"/create/{dataScheme}"})
    public TaskParamVO initNewTask(@PathVariable String dataScheme) throws JQException {
        TaskParamVO taskParam;
        try {
            taskParam = this.taskService.initTask(dataScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_INIT_FAILED, e.getMessage());
        }
        return taskParam;
    }

    @ApiOperation(value="\u65b0\u589e\u4efb\u52a1")
    @PostMapping(value={"/add"})
    @TaskLog(operation="\u65b0\u589e\u4efb\u52a1")
    public TaskItemVO add(@RequestBody TaskParamVO taskParam) throws JQException {
        try {
            return this.taskService.insertTask(this.taskTools.buildTaskDetailByTaskParam(taskParam));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_INSERT_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u5220\u9664\u4efb\u52a1")
    @GetMapping(value={"/delete/{taskID}"})
    @TaskLog(operation="\u5220\u9664\u4efb\u52a1")
    public String delete(@PathVariable String taskID) throws JQException {
        try {
            return this.taskService.deleteTask(taskID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_DELETE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u66f4\u65b0\u524d\u7684\u521d\u59cb\u5316")
    @GetMapping(value={"/get/{taskID}"})
    public TaskParamVO get(@PathVariable String taskID) throws JQException {
        TaskParamVO taskParam;
        try {
            taskParam = this.taskService.getTask(taskID);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_UPDATE_FAILED, e.getMessage());
        }
        return taskParam;
    }

    @ApiOperation(value="\u66f4\u65b0\u4efb\u52a1")
    @PostMapping(value={"/update"})
    @TaskLog(operation="\u66f4\u65b0\u4efb\u52a1")
    public TaskItemVO update(@RequestBody TaskParamVO taskParam) throws JQException {
        try {
            return this.taskService.updateTask(this.taskTools.buildTaskDetailByTaskParam(taskParam));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_UPDATE_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u5206\u7ec4ID\u67e5\u8be2\u4efb\u52a1\u5217\u8868")
    @GetMapping(value={"/list/group/{groupId}"})
    public List<TaskItemVO> list(@PathVariable String groupId) throws JQException {
        List<TaskItemVO> tasks;
        try {
            tasks = this.taskService.queryTask(groupId);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_QUERY_FAILED, e.getMessage());
        }
        return tasks;
    }

    @ApiOperation(value="\u8bbe\u7f6e\u4efb\u52a1\u5206\u7ec4")
    @PostMapping(value={"/taskGroupSetting/{taskID}"})
    @TaskLog(operation="\u79fb\u52a8\u4efb\u52a1\u5206\u7ec4")
    public void moveTaskToGroup(@PathVariable String taskID, @RequestBody List<String> groupKeys) throws JQException {
        try {
            this.taskService.changeTaskGroup(taskID, groupKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.SETTING_GROUP_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u4e0a\u4e0b\u79fb")
    @PostMapping(value={"/moveTask"})
    @TaskLog(operation="\u79fb\u52a8\u5206\u7ec4")
    public void moveTask(@RequestBody taskOrderMoveDTO moveDTO) throws JQException {
        try {
            this.taskService.moveTask(moveDTO);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.MOVE_TASK_FAILED, e.getMessage());
        }
    }

    @ApiOperation(value="\u5355\u4f4d\u6570\u636e\u9009\u62e9")
    @PostMapping(value={"/unit-tree/select"})
    public List<UITreeNode<TreeData>> treeDataSelect(@RequestBody UnitTreeDataParam param) throws JQException {
        try {
            return this.taskService.treeDataSelect(param.getTableName(), param.getParent());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TREE_DATA_ERROR, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5355\u4f4d\u6811\u5f62")
    @PostMapping(value={"/entity-tree/tree-init"})
    public List<EntityTree> entityTreeInit(@RequestBody TreeParam param) throws JQException {
        try {
            List<ITree<TreeNode>> iTrees = this.taskService.unitTreeInit(param);
            ArrayList<EntityTree> res = new ArrayList<EntityTree>();
            this.convertTree(iTrees, res);
            return res;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TREE_DATA_ERROR, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u4e0a\u4e0b\u79fb")
    @GetMapping(value={"/check/{taskKey}"})
    public boolean checkTaskStatus(@PathVariable String taskKey) throws JQException {
        return this.taskService.checkTask(taskKey);
    }

    @ApiOperation(value="\u6821\u9a8c\u4efb\u52a1\u6807\u8bc6")
    @PostMapping(value={"/checkCode"})
    public void checkTaskCode(@RequestBody TaskParamVO taskParam) throws JQException {
        try {
            this.taskService.checkTaskCode(taskParam.getKey(), taskParam.getCode());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TASK_CODE_ERROR, (Throwable)e);
        }
    }

    private void convertTree(List<ITree<TreeNode>> iTrees, List<EntityTree> res) {
        for (ITree<TreeNode> iTree : iTrees) {
            if (iTree == null) continue;
            EntityTree entityTree = new EntityTree(iTree);
            if (iTree.hasChildren()) {
                ArrayList<EntityTree> children = new ArrayList<EntityTree>();
                this.convertTree(iTree.getChildren(), children);
                entityTree.setChildren1(children);
            }
            res.add(entityTree);
        }
    }

    @PostMapping(value={"/entity-tree/children-node"})
    public List<EntityTree> unitTreeSelect(@RequestBody TreeParam param) throws JQException {
        try {
            List<ITree<TreeNode>> iTrees = this.taskService.unitTreeChildren(param);
            ArrayList<EntityTree> res = new ArrayList<EntityTree>();
            this.convertTree(iTrees, res);
            return res;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TREE_DATA_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/entity-tree/location-node"})
    public List<EntityTree> locationTreeNode(@RequestBody TreeParam param) throws JQException {
        try {
            List<ITree<TreeNode>> iTrees = this.taskService.locationTreeNode(param);
            ArrayList<EntityTree> res = new ArrayList<EntityTree>();
            this.convertTree(iTrees, res);
            return res;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)TaskException.TREE_DATA_ERROR, (Throwable)e);
        }
    }

    @ApiOperation(value="\u662f\u5426\u542f\u7528\u4efb\u52a1\u8bbe\u8ba12.0")
    @GetMapping(value={"/enable"})
    public Boolean enable() throws JQException {
        return true;
    }

    @ApiOperation(value="\u4efb\u52a1\u540d\u79f0\u91cd\u590d\u6821\u9a8c")
    @PostMapping(value={"/check-title"})
    public boolean checkTaskTitle(@RequestBody TaskParamVO taskParam) throws JQException {
        return this.taskService.checkTaskTitle(taskParam);
    }

    @ApiOperation(value="/\u83b7\u53d6\u7ef4\u5ea6\u4efb\u52a1\u9009\u9879")
    @GetMapping(value={"/wd-task-option/{taskKey}"})
    public DwTaskOptionVO queryWDTaskOption(@PathVariable String taskKey) throws JQException {
        try {
            return this.taskService.getDwTaskOption(taskKey);
        }
        catch (Exception e) {
            log.error("\u67e5\u8be2\u4efb\u52a1\u9009\u9879\u5931\u8d25", e);
            throw new JQException((ErrorEnum)TaskException.QUERY_TASK_OPTION_ERROR);
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u6a21\u7cca\u641c\u7d22(\u5e26\u62a5\u8868\u65b9\u6848)")
    @PostMapping(value={"/fuzzySearch"})
    public List<TaskResourceSearchVO> fuzzySearch(@RequestBody TaskGroupDTO taskGroupDTO) throws JQException {
        try {
            return this.taskService.fuzzySearchTask(taskGroupDTO.getTitle());
        }
        catch (Exception e) {
            log.error("\u4efb\u52a1\u641c\u7d22\u5931\u8d25", e);
            throw new JQException((ErrorEnum)TaskException.TASK_FUZZY_SEARCH_ERROR);
        }
    }

    @ApiOperation(value="/\u4fdd\u5b58\u7ef4\u5ea6\u4efb\u52a1\u9009\u9879")
    @PostMapping(value={"/save-wd-task-option"})
    @TaskLog(operation="\u4fdd\u5b58\u4efb\u52a1\u9009\u9879")
    public void saveDwTaskOption(@RequestBody @SFDecrypt DwTaskOptionVO dwTaskOptionVO) throws JQException {
        try {
            this.taskService.saveDwTaskOption(dwTaskOptionVO);
        }
        catch (Exception e) {
            log.error("\u4fdd\u5b58\u4efb\u52a1\u9009\u9879\u5931\u8d25", e);
            throw new JQException((ErrorEnum)TaskException.SAVE_TASK_OPTION_ERROR);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u7248\u672c")
    @GetMapping(value={"/version/{taskKey}"})
    public String getTaskVersion(@PathVariable String taskKey) {
        TaskParamVO task = this.taskService.getTask(taskKey);
        return task.getVersion();
    }

    @ApiOperation(value="\u4efb\u52a1\u5347\u7ea7")
    @GetMapping(value={"/upgrade/{taskKey}"})
    @TaskLog(operation="\u5347\u7ea7\u4efb\u52a1")
    public List<UpgradeResult> upgradeTask(@PathVariable String taskKey) {
        return this.taskUpgradeService.execute(taskKey);
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u72b6\u6001")
    @GetMapping(value={"/status/{taskKey}"})
    public FormSchemeStatusDTO queryStatus(@PathVariable String taskKey) {
        return this.taskService.queryTaskStatus(taskKey);
    }

    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u53d1\u5e03\u5931\u8d25\u539f\u56e0")
    @GetMapping(value={"/progress/{taskKey}"})
    public TaskPublishInfo queryProgress(@PathVariable String taskKey) {
        return this.taskService.queryTaskPublishInfo(taskKey);
    }

    @ApiOperation(value="\u5237\u65b0\u9009\u9879")
    @GetMapping(value={"/actions/refresh/{taskKey}"})
    public List<TaskActionDTO> refreshActions(@PathVariable String taskKey) {
        return this.actionService.listActions(Constants.ActionType.TASK, taskKey);
    }

    @ApiOperation(value="\u5bfc\u51fa\u4efb\u52a1\u53d1\u5e03\u5931\u8d25\u72b6\u6001")
    @PostMapping(value={"/publish/error/{taskKey}"})
    public void downloadError(@PathVariable String taskKey, HttpServletResponse response) throws JQException {
        this.taskService.exportPublishError(taskKey, response);
    }
}

