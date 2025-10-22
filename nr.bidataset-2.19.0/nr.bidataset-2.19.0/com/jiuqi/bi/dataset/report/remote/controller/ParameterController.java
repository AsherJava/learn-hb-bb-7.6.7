/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.bi.dataset.report.remote.controller;

import com.jiuqi.bi.dataset.report.builder.ParameterBuilder;
import com.jiuqi.bi.dataset.report.builder.ParameterSelectorTreeBuilder;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ParameterInfoVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ParameterSelectorNodeVo;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.server.util.ParameterConvertor;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/report/dataset"})
public class ParameterController {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @GetMapping(value={"/parameter/selector/{taskKey}"})
    public List<ParameterSelectorNodeVo> getParamSelectorTree(@PathVariable String taskKey) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        return new ParameterSelectorTreeBuilder(taskDefine, this.entityMetaService, this.runTimeViewController, this.runtimeDataSchemeService).build();
    }

    @PostMapping(value={"/parameter/generate"})
    public String getParameter(@RequestBody ParameterInfoVo parameterInfoVo) throws Exception {
        ParameterModel parameterModel = new ParameterBuilder(parameterInfoVo).build();
        JSONObject json = ParameterConvertor.toJson(null, (ParameterModel)parameterModel, (boolean)false);
        return json.toString();
    }
}

