/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.definition.option;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.option.DimGroupOptionService;
import com.jiuqi.nr.definition.option.TaskOptionService;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.definition.option.core.TaskOptionVO;
import com.jiuqi.nr.definition.option.dto.DimensionGroupDTO;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/definition/task-option"})
public class TaskOptionController {
    @Autowired
    private TaskOptionService taskOptionService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private DimGroupOptionService dimGroupOptionService;

    @GetMapping
    @RequiresPermissions(value={"nr:task:manage"})
    public TaskOptionVO getOptionDefines(@RequestParam String taskKey) {
        return this.taskOptionService.getOptionDefines(taskKey);
    }

    @GetMapping(value={"/dimension-group"})
    public DimensionGroupDTO getDimensionGroup(@RequestParam String taskKey) {
        return this.dimGroupOptionService.getDimensionGroup(taskKey);
    }

    @PostMapping
    @RequiresPermissions(value={"nr:task:manage"})
    public boolean setOptions(@RequestBody @SFDecrypt List<TaskOption> options) throws JQException {
        try {
            this.iTaskOptionController.setOptions(options);
            return true;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_068, (Throwable)e);
        }
    }
}

