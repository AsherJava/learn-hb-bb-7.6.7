/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj
 *  com.jiuqi.nr.definition.formulamapping.facade.MappingObj
 *  com.jiuqi.nr.definition.formulamapping.facade.TargetAndSourceObj
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.formulamapping.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj;
import com.jiuqi.nr.definition.formulamapping.facade.MappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.TargetAndSourceObj;
import com.jiuqi.nr.formulamapping.exception.NrFormulaMappingErrorEnum;
import com.jiuqi.nr.formulamapping.service.FormulaMappingSchemeService;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"api/v1/formula/mapping/"})
public class FormulaMappingSchemeController {
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IFormulaRunTimeController runtimeFormulaController;
    @Autowired
    private FormulaMappingSchemeService formulaMappingSchemeService;

    @ApiOperation(value="\u67e5\u8be2\u8fd0\u884c\u671f\u7684\u5efa\u6a21\u4efb\u52a1")
    @GetMapping(value={"query/taskDefines"})
    public List<MappingObj> queryTaskDefines() {
        ArrayList<MappingObj> list = new ArrayList<MappingObj>();
        List allTaskDefines = this.runtimeViewController.getAllTaskDefinesByType(TaskType.TASK_TYPE_DEFAULT);
        if (allTaskDefines != null) {
            allTaskDefines.stream().forEach(item -> this.setMappingObj(list, (IMetaItem)item));
        }
        return list;
    }

    @ApiOperation(value="\u67e5\u8be2\u8fd0\u884c\u671f\u7684\u5efa\u6a21\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848")
    @GetMapping(value={"query/formSchemeByTask"})
    public List<MappingObj> queryFormSchemeByTaskKey(@RequestParam String taskKey) throws JQException {
        ArrayList<MappingObj> list = new ArrayList<MappingObj>();
        try {
            List queryFormSchemeByTask = this.runtimeViewController.queryFormSchemeByTask(taskKey);
            if (queryFormSchemeByTask != null && !queryFormSchemeByTask.isEmpty()) {
                queryFormSchemeByTask.stream().forEach(item -> this.setMappingObj(list, (IMetaItem)item));
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrFormulaMappingErrorEnum.NRFORMULAMAPPING_EXCEPTION_000, (Throwable)e);
        }
        return list;
    }

    @ApiOperation(value="\u67e5\u8be2\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u65b9\u6848")
    @GetMapping(value={"query/formulaScheme"})
    public List<MappingObj> queryFormSchemeByFormShemeKey(@RequestParam String formSchemeKey) throws JQException {
        ArrayList<MappingObj> list = new ArrayList<MappingObj>();
        List formulaSchemeDefines = this.runtimeFormulaController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        List collect = formulaSchemeDefines.stream().filter(item -> item.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT).collect(Collectors.toList());
        collect.stream().forEach(item -> this.setMappingObj(list, (IMetaItem)item));
        return list;
    }

    private void setMappingObj(List<MappingObj> list, IMetaItem item) {
        MappingObj o = new MappingObj();
        o.setKey(item.getKey());
        o.setTitle(item.getTitle());
        list.add(o);
    }

    @ApiOperation(value="\u67e5\u8be2\u8fd0\u884c\u671f\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u4efb\u52a1key\u548c\u62a5\u8868\u65b9\u6848key")
    @GetMapping(value={"query/taskAndFormSchemeKeys"})
    public TargetAndSourceObj getTaskAndFormSchemeObj(@RequestParam String key) {
        TargetAndSourceObj targetAndSourceObj = new TargetAndSourceObj();
        FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey = this.formulaMappingSchemeService.queryFormulaMappingSchemeObjsByKey(key);
        if (queryFormulaMappingSchemeObjsByKey != null) {
            FormSchemeAndTaskKeysObj targetTaskAndFormSchemeObj = this.formulaMappingSchemeService.getTaskAndFormSchemeObj(queryFormulaMappingSchemeObjsByKey.getTargetFSKey());
            targetAndSourceObj.setTargetFormSchemeKey(targetTaskAndFormSchemeObj.getFormSchemeKey());
            targetAndSourceObj.setTargetTaskKey(targetTaskAndFormSchemeObj.getTaskKey());
            FormSchemeAndTaskKeysObj sourceTaskAndFormSchemeObj = this.formulaMappingSchemeService.getTaskAndFormSchemeObj(queryFormulaMappingSchemeObjsByKey.getSourceFSKey());
            targetAndSourceObj.setSourceFormSchemeKey(sourceTaskAndFormSchemeObj.getFormSchemeKey());
            targetAndSourceObj.setSourceTaskKey(sourceTaskAndFormSchemeObj.getTaskKey());
        }
        return targetAndSourceObj;
    }

    @ApiOperation(value="\u67e5\u8be2\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e")
    @GetMapping(value={"query/formula_mapping_scheme"})
    public List<FormulaMappingSchemeObj> queryFormulaMappingScheme() throws JQException {
        return this.formulaMappingSchemeService.queryFormulaMappingSchemeObjs();
    }

    @ApiOperation(value="\u65b0\u589e\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"insert/formula_mapping_scheme"})
    public void insertFormulaMappingScheme(@RequestBody FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws JQException {
        this.formulaMappingSchemeService.insertFormulaMappingSchemeDefine(formulaMappingSchemeDefine);
    }

    @ApiOperation(value="\u4fee\u6539\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"update/formula_mapping_scheme"})
    public void updateFormulaMappingScheme(@RequestBody FormulaMappingSchemeDefine formulaMappingSchemeDefine) throws JQException {
        this.formulaMappingSchemeService.updateFormulaMappingSchemeDefine(formulaMappingSchemeDefine);
    }

    @ApiOperation(value="\u5220\u9664\u516c\u5f0f\u65b9\u6848\u6620\u5c04\u6570\u636e")
    @PostMapping(value={"delete/formula_mapping_scheme"})
    public void deleteFormulaMappingScheme(@RequestBody String[] keys) throws JQException {
        this.formulaMappingSchemeService.deletsFormulaMappingSchemeDefine(keys);
    }
}

