/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish
 *  io.swagger.annotations.Api
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.planpublish.entity.TaskPlanPublish;
import com.jiuqi.nr.designer.service.DDLDeployService;
import io.swagger.annotations.Api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class DDLController {
    @Autowired
    private DDLDeployService ddlDeployService;
    private static final String ERROR_CODE = "DDL_ERROR";

    @GetMapping(value={"/ddl/enable"})
    public Boolean enableDdl() {
        return this.ddlDeployService.isEnableDDL();
    }

    @GetMapping(value={"/ddl/preDeploy/{taskKey}"})
    public List<String> preDeploy(@PathVariable(value="taskKey") String taskKey) throws JQException {
        try {
            return this.ddlDeployService.preDeploy(taskKey);
        }
        catch (Exception e) {
            throw new JQException(this.getError(e));
        }
    }

    @GetMapping(value={"/ddl/deploy/{taskKey}"})
    public void deploy(@PathVariable(value="taskKey") String taskKey) throws JQException {
        try {
            this.ddlDeployService.deploy(taskKey);
        }
        catch (Exception e) {
            throw new JQException(this.getError(e));
        }
    }

    @GetMapping(value={"/ddl/status/query/{taskKey}"})
    public TaskPlanPublish statusQuery(@PathVariable(value="taskKey") String taskKey) throws JQException {
        try {
            return this.ddlDeployService.getTaskPlanByTask(taskKey);
        }
        catch (Exception e) {
            throw new JQException(this.getError(e));
        }
    }

    private ErrorEnum getError(final Exception e) {
        return new ErrorEnum(){

            public String getCode() {
                return DDLController.ERROR_CODE;
            }

            public String getMessage() {
                return e.getMessage();
            }
        };
    }

    @GetMapping(value={"/ddl/sql/exist/{taskKey}"})
    public Boolean sqlExist(@PathVariable(value="taskKey") String taskKey) throws JQException {
        try {
            return this.ddlDeployService.canCancel(taskKey);
        }
        catch (Exception e) {
            throw new JQException(this.getError(e));
        }
    }

    @GetMapping(value={"/ddl/deploy/cancel/{taskKey}"})
    public void cancelDeploy(@PathVariable(value="taskKey") String taskKey) throws JQException {
        try {
            this.ddlDeployService.cancelDeploy(taskKey);
        }
        catch (Exception e) {
            throw new JQException(this.getError(e));
        }
    }
}

