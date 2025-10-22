/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.examine.web;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.examine.facade.DataClearParamObj;
import com.jiuqi.nr.examine.service.IDataSchemeDataClearService;
import com.jiuqi.nr.examine.web.bean.DataCleanParamInfo;
import com.jiuqi.nr.examine.web.bean.DataSchemeInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/paramcheck/"})
@Api(tags={"\u53c2\u6570\u68c0\u67e5"})
public class CleanDataController {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IDataSchemeDataClearService dataClearService;

    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u6570\u636e\u65b9\u6848\u53ca\u5176\u5bf9\u5e94\u4efb\u52a1", httpMethod="POST")
    @RequestMapping(value={"all_ds_task"}, method={RequestMethod.POST})
    public DataCleanParamInfo getAllData() {
        List dataSchemes = this.runtimeDataSchemeService.getAllDataScheme();
        List taskDefines = this.runTimeViewController.getAllTaskDefines();
        List<DataSchemeInfo> dataSchemeInfos = this.getParams(dataSchemes, taskDefines);
        boolean isAdmin = this.systemIdentityService.isAdmin();
        return new DataCleanParamInfo(dataSchemeInfos, isAdmin);
    }

    private List<DataSchemeInfo> getParams(List<DataScheme> dataSchemes, List<TaskDefine> taskDefines) {
        ArrayList<DataSchemeInfo> schemeInfos = new ArrayList<DataSchemeInfo>();
        Map<String, List<TaskDefine>> taskdefineMap = taskDefines.stream().collect(Collectors.groupingBy(TaskDefine::getDataScheme));
        for (DataScheme dataScheme : dataSchemes) {
            if (null != taskdefineMap.get(dataScheme.getKey())) {
                schemeInfos.add(new DataSchemeInfo(dataScheme, taskdefineMap.get(dataScheme.getKey())));
                continue;
            }
            schemeInfos.add(new DataSchemeInfo(dataScheme, new ArrayList<TaskDefine>()));
        }
        return schemeInfos;
    }

    @ApiOperation(value="\u6e05\u9664\u6570\u636e", httpMethod="POST")
    @RequestMapping(value={"doclear"}, method={RequestMethod.POST})
    public boolean cleanData(@RequestBody DataClearParamObj clearParamObj) {
        boolean isClean = true;
        try {
            this.dataClearService.clearDataSchemeData(clearParamObj);
        }
        catch (Exception e) {
            isClean = false;
        }
        return isClean;
    }
}

