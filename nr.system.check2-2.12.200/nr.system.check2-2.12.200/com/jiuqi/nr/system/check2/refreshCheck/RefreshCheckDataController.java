/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check2.refreshCheck;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.system.check2.exception.ExecutorResultEnum;
import com.jiuqi.nr.system.check2.vo.TaskObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system_check2"})
@Api(tags={"\u53c2\u6570\u68c0\u67e5\uff0c\u5237\u65b0\u51fa\u9519\u8bf4\u660e"})
public class RefreshCheckDataController {
    @Autowired
    private IRunTimeViewController iRunTimeViewService;

    @ApiOperation(value="\u5237\u65b0\u51fa\u9519\u8bf4\u660e\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    @GetMapping(value={"/refers_data/get_run_task"})
    public List<TaskObj> getAllRunTimeTasks() throws JQException {
        try {
            List allTaskDefines = this.iRunTimeViewService.getAllTaskDefines();
            ArrayList<TaskObj> taskObjs = new ArrayList<TaskObj>();
            for (TaskDefine taskDefine : allTaskDefines) {
                TaskObj taskObj = new TaskObj(taskDefine.getKey(), taskDefine.getTitle());
                taskObjs.add(taskObj);
            }
            return taskObjs;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ExecutorResultEnum.GET_ALL_TASK_ERROR, (Throwable)e);
        }
    }
}

