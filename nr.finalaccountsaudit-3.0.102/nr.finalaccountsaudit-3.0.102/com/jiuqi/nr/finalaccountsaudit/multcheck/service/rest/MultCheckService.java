/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.service.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.finalaccountsaudit.common.AsynctaskPoolType;
import com.jiuqi.nr.finalaccountsaudit.enumcheck.common.EnumDataCheckResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.asynctask.MultCheckAsyncTaskExecutor;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.AnalysisResultInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityCheckPeriodTransform;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.EntityShortInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.LastCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.MultCheckResultItem;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.IMultCheckItemBase;
import com.jiuqi.nr.finalaccountsaudit.multcheck.controller.IMultCheckController;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u7efc\u5408\u5ba1\u6838"})
@RequestMapping(value={"FinalMultCheck/multcheck"})
public class MultCheckService {
    @Autowired
    IMultCheckController controller;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    IRunTimeViewController runTimeViewController;

    @RequestMapping(value={"/actions/oneKeyCheck"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u7efc\u5408\u5ba1\u6838\u9879\u4e00\u952e\u5ba1\u6838")
    @ResponseBody
    public AsyncTaskInfo oneKeyCheck(@Valid @RequestBody OneKeyCheckInfo oneKeyCheckInfo) throws Exception {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(oneKeyCheckInfo.getContext().getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(oneKeyCheckInfo.getContext().getFormSchemeKey());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)oneKeyCheckInfo));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new MultCheckAsyncTaskExecutor());
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, AsynctaskPoolType.ASYNCTASK_MULTCHECK.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @RequestMapping(value={"/actions/getCheckItemResults"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u5ba1\u6838\u7ed3\u679c")
    public List<MultCheckResultItem> getCheckItemResults(String asyncTaskId, String formSchemeKey) throws Exception {
        return this.controller.getCheckItemResults(asyncTaskId, formSchemeKey);
    }

    @RequestMapping(value={"/actions/lastCheckResults"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u6b21\u5ba1\u6838\u7ed3\u679c")
    public LastCheckInfo lastCheckResults(String formSchemeKey) throws Exception {
        return this.controller.lastCheckResults(formSchemeKey);
    }

    @RequestMapping(value={"/actions/getEnumCheckResults"}, method={RequestMethod.GET})
    @ApiOperation(value="\u67e5\u8be2\u679a\u4e3e\u5ba1\u6838\u7ed3\u679c")
    public EnumDataCheckResultInfo getEnumCheckResults(String asyncTaskId) throws Exception {
        return this.controller.getEnumCheckResults(asyncTaskId);
    }

    @RequestMapping(value={"/actions/multCheck"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6570\u636e\u5f55\u5165\uff1a\u83b7\u53d6\u7efc\u5408\u5ba1\u6838\u9879")
    @ResponseBody
    public List<MultCheckItem> multCheck(@Valid @RequestBody OneKeyCheckInfo oneKeyCheckInfo) throws Exception {
        return this.controller.queryAllItemList(oneKeyCheckInfo);
    }

    @RequestMapping(value={"/actions/queryformulaSchemes"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6240\u6709\u516c\u5f0f\u65b9\u6848")
    public List<FormulaSchemeDefine> queryformulaSchemes(String formSchemeKey) throws Exception {
        return this.controller.queryformulaSchemes(formSchemeKey);
    }

    @RequestMapping(value={"/actions/getRootNode"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5355\u4f4d\u6811\u6839\u8282\u70b9")
    @ResponseBody
    public EntityShortInfo getRootNode(@Valid @RequestBody JtableContext context) throws Exception {
        if (StringUtils.isEmpty((String)context.getFormSchemeKey())) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(((DimensionValue)context.getDimensionSet().get("DATATIME")).getValue(), context.getTaskKey());
            context.setFormSchemeKey(schemePeriodLinkDefine.getSchemeKey());
        }
        return this.controller.getRootNode(context);
    }

    @RequestMapping(value={"/actions/paramCheck"}, method={RequestMethod.GET})
    @ApiOperation(value="\u5ba1\u6838\u524d\u68c0\u9a8c\u4efb\u52a1\u662f\u5426\u53d1\u5e03")
    public boolean paramCheck(String formSchemeKey) throws Exception {
        return this.controller.paramCheck(formSchemeKey);
    }

    @RequestMapping(value={"/actions/getCheckSchemes"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u5ba1\u6838\u65b9\u6848")
    public List getCheckSchemes(String formSchemeKey) throws Exception {
        return this.controller.getCheckSchemes(formSchemeKey);
    }

    @RequestMapping(value={"/actions/getCheckSchemesByTaskAndPeriod"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u5ba1\u6838\u65b9\u6848")
    public List getCheckSchemesByTaskAndPeriod(@RequestBody Map<String, Object> params) throws Exception {
        String taskId = (String)params.get("taskId");
        String period = (String)params.get("period");
        if (StringUtils.isEmpty((String)taskId) || StringUtils.isEmpty((String)period) || period.length() != 9) {
            return null;
        }
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        if (schemePeriodLinkDefine == null || StringUtils.isEmpty((String)schemePeriodLinkDefine.getSchemeKey())) {
            return null;
        }
        return this.controller.getCheckSchemes(schemePeriodLinkDefine.getSchemeKey());
    }

    @RequestMapping(value={"/actions/getDataAnalysis"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5206\u6790\u8868")
    public List<IMultCheckItemBase> getDataAnalysis() throws Exception {
        return this.controller.getDataAnalysis("dataAnalysis");
    }

    @RequestMapping(value={"/actions/getAnalysisResults"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5206\u6790\u8868\u5ba1\u6838\u7ed3\u679c")
    public AnalysisResultInfo getDataAnalysis(String asyncTaskId) throws Exception {
        return this.controller.getAnalysisResults(asyncTaskId);
    }

    @RequestMapping(value={"/actions/periodTransform"}, method={RequestMethod.POST})
    @ApiOperation(value="\u6237\u6570\u6838\u5bf9\u65f6\u671f\u8f6c\u6362")
    @ResponseBody
    public String periodTransform(@Valid @RequestBody EntityCheckPeriodTransform entityCheckPeriodTransform) throws Exception {
        return this.controller.periodTransform(entityCheckPeriodTransform);
    }

    @RequestMapping(value={"/actions/getZbQuery"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5206\u6790\u8868")
    public List<IMultCheckItemBase> getZbQuery() throws Exception {
        return this.controller.getDataAnalysis("zbQueryTemplate");
    }

    @RequestMapping(value={"/actions/getZbQueryResults"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u5206\u6790\u8868\u5ba1\u6838\u7ed3\u679c")
    public AnalysisResultInfo getZbQueryResults(String asyncTaskId) throws Exception {
        return this.controller.getAnalysisResults(asyncTaskId);
    }
}

