/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.system.check.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.system.check.common.SCErrorEnum;
import com.jiuqi.nr.system.check.model.request.DeleteDataObj;
import com.jiuqi.nr.system.check.model.response.TaskObj;
import com.jiuqi.nr.system.check.service.SCDeleteDataService;
import com.jiuqi.nr.system.check.service.SCTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/system-check"})
@Api(tags={"\u7cfb\u7edf\u68c0\u67e5"})
public class SCDeleteDataController {
    @Autowired
    private SCTaskService scTaskService;
    @Autowired
    private SCDeleteDataService scDeleteDataService;

    @GetMapping(value={"/all-task"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u8fd0\u884c\u671f\u4efb\u52a1")
    public List<TaskObj> getAllRunTimeTasks() throws JQException {
        try {
            return this.scTaskService.getAllRunTimeTasks();
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SCErrorEnum.SYSTEM_CHECK_EXCEPTION_001, (Throwable)e);
        }
    }

    @PostMapping(value={"/delete/form-dim"})
    @ApiOperation(value="\u6e05\u9664\u62a5\u8868\u6570\u636e")
    public void deleteData(@RequestBody DeleteDataObj deleteDataObj) throws JQException {
        try {
            this.scDeleteDataService.deleteData(deleteDataObj);
        }
        catch (JQException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SCErrorEnum.SYSTEM_CHECK_EXCEPTION_002, (Throwable)e);
        }
    }
}

