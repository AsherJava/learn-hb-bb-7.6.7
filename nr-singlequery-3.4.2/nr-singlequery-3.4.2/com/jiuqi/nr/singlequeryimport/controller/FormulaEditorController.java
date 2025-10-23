/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject
 *  io.swagger.annotations.Api
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.singlequeryimport.controller;

import com.jiuqi.nr.singlequeryimport.bean.FormSchemeItem;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.FormulaCheckParam;
import com.jiuqi.nr.singlequeryimport.bean.TaskItem;
import com.jiuqi.nr.singlequeryimport.intf.utils.ResultObject;
import com.jiuqi.nr.singlequeryimport.service.IFormulaEditorService;
import io.swagger.annotations.Api;
import java.util.List;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u51b3\u7b97\u67e5\u8be2\u516c\u5f0f\u7f16\u8f91\u5668"})
@RequestMapping(value={"/singleQuery/formulaEditor"})
public class FormulaEditorController {
    @Autowired
    private IFormulaEditorService formulaEditorService;

    @GetMapping(value={"/taskItems/{reportSchemeKey}"})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<TaskItem> getTaskItemList(@PathVariable String reportSchemeKey) {
        return this.formulaEditorService.getTaskItemList(reportSchemeKey);
    }

    @GetMapping(value={"/schemes/{taskItemKey}"})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public List<FormSchemeItem> getFormSchemeItemList(@PathVariable String taskItemKey) {
        return this.formulaEditorService.getFormSchemeItemList(taskItemKey);
    }

    @PostMapping(value={"/checkFormula"})
    @RequiresPermissions(value={"nr:singlequery:manage"})
    public ResultObject checkFormula(@RequestBody @Valid FormulaCheckParam formulaCheckParam) {
        try {
            this.formulaEditorService.checkFormula(formulaCheckParam);
        }
        catch (Exception e) {
            return ResultObject.error((String)e.getMessage());
        }
        return ResultObject.ok();
    }
}

