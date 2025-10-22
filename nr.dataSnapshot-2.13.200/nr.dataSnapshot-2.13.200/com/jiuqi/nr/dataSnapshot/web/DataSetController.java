/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataSnapshot.web;

import com.jiuqi.nr.dataSnapshot.param.FormInfo;
import com.jiuqi.nr.dataSnapshot.param.FormSchemeInfo;
import com.jiuqi.nr.dataSnapshot.param.RegionInfo;
import com.jiuqi.nr.dataSnapshot.service.IDataSetService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/dataSnapshot/actions/dataSet"})
@Api(tags={"\u5feb\u7167\u6570\u636e\u96c6\u64cd\u4f5c"})
public class DataSetController {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataSetService dataSetService;

    @GetMapping(value={"/taskTitle/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u6807\u9898")
    public String getTaskTitle(@PathVariable String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        return taskDefine != null ? taskDefine.getTitle() : null;
    }

    @GetMapping(value={"/get-formScheme"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848")
    public List<FormSchemeInfo> getformSchemes(String taskKey) {
        return this.dataSetService.getformSchemes(taskKey);
    }

    @GetMapping(value={"/get-form"})
    @ApiOperation(value="\u83b7\u53d6\u8868\u5355")
    public List<FormInfo> getForms(String formSchemeKey) {
        return this.dataSetService.getFormInfo(formSchemeKey);
    }

    @GetMapping(value={"/get-region"})
    @ApiOperation(value="\u83b7\u53d6\u533a\u57df")
    public List<RegionInfo> getRegions(String formKey) {
        return this.dataSetService.getRegionInfo(formKey);
    }
}

