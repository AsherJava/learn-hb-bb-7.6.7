/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.task.api.aop.TaskLog
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.formula.web.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.formula.dto.TaskDataDTO;
import com.jiuqi.nr.formula.exception.FormulaResourceErrorEnum;
import com.jiuqi.nr.formula.service.ITaskLinkService;
import com.jiuqi.nr.formula.web.vo.PeriodTypeVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkEntityQueryVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkSettingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkVO;
import com.jiuqi.nr.task.api.aop.TaskLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v2/nr-formula/task-link"})
@Api(value="\u4efb\u52a1\u5173\u8054\u914d\u7f6e")
public class TaskLinkController {
    @Autowired
    private ITaskLinkService taskLinkService;

    @GetMapping(value={"/list/{formScheme}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u5173\u8054\u914d\u7f6e")
    public TaskLinkSettingVO getTaskLinkSetting(@PathVariable String formScheme) throws JQException {
        try {
            return this.taskLinkService.getTaskLinkSetting(formScheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_01, (Throwable)e);
        }
    }

    @GetMapping(value={"/task-list"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u5217\u8868")
    public List<TaskDataDTO> getTaskList() throws JQException {
        try {
            return this.taskLinkService.getTaskList();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_17, (Throwable)e);
        }
    }

    @GetMapping(value={"/query/entity/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u4ee5\u53ca\u60c5\u666f\u4fe1\u606f")
    public TaskLinkEntityQueryVO queryRelatedTaskEntities(@PathVariable String taskKey) throws JQException {
        try {
            return this.taskLinkService.queryRelatedTaskEntities(taskKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_16, (Throwable)e);
        }
    }

    @PostMapping(value={"/save"})
    @ApiOperation(value="\u4fdd\u5b58\u6570\u636e")
    @TaskLog(operation="\u4fdd\u5b58\u5173\u8054\u4efb\u52a1")
    public void saveTaskLinks(@RequestBody List<TaskLinkVO> datas) throws JQException {
        try {
            this.taskLinkService.saveTaskLinks(datas);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)FormulaResourceErrorEnum.FORMULA_RESOURCE_01, (Throwable)e);
        }
    }

    @GetMapping(value={"/getPeriodType/{schemeKey}"})
    public PeriodTypeVO getPeriodType(@PathVariable String schemeKey) {
        return this.taskLinkService.getPeriodType(schemeKey);
    }
}

