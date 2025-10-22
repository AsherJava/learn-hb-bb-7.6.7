/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.common.UploadAllFormSumInfo
 *  com.jiuqi.nr.bpm.common.UploadRecordDetail
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecordDetail;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataentry.asynctask.ITaskTreeNode;
import com.jiuqi.nr.dataentry.asynctask.TaskTreeObj;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import com.jiuqi.nr.dataentry.bean.FormsQueryResult;
import com.jiuqi.nr.dataentry.bean.QueryUploadStateInfo;
import com.jiuqi.nr.dataentry.bean.TreeDataSoureParam;
import com.jiuqi.nr.dataentry.internal.overview.OverviewVirtualNodeAndCustomServiceImpl;
import com.jiuqi.nr.dataentry.internal.overview.OverviewVirtualNodeServiceImpl;
import com.jiuqi.nr.dataentry.internal.overview.UploadOverviewConverter;
import com.jiuqi.nr.dataentry.paramInfo.ActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryUpload;
import com.jiuqi.nr.dataentry.paramInfo.BatchQueryWorkFlowType;
import com.jiuqi.nr.dataentry.paramInfo.ChangeEntitys;
import com.jiuqi.nr.dataentry.paramInfo.ExportExcelState;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.dataentry.paramInfo.UploadActionInfo;
import com.jiuqi.nr.dataentry.paramInfo.UploadSumInfo;
import com.jiuqi.nr.dataentry.web.BatchUploadStateController;
import com.jiuqi.nr.dataentry.web.BatchUploadSumService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags={"\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001"})
public class BatchUploadStateController2 {
    @Autowired
    private BatchUploadStateController batchUploadStateController;
    @Autowired
    private OverviewVirtualNodeServiceImpl overviewVirtualNodeService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private BatchUploadSumService batchUploadSumService;
    @Autowired
    private UploadOverviewConverter uploadOverviewConverter;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    private OverviewVirtualNodeAndCustomServiceImpl overviewVirtualNodeAndCustomServiceImpl;

    @PostMapping(value={"/overview/forms-read"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868")
    @NRContextBuild
    public FormsQueryResult getFormsReadable(@Valid @RequestBody DataEntryContext context) {
        return this.batchUploadStateController.getFormsReadable(context);
    }

    @RequestMapping(value={"/api/batchUploadState"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    @RequiresPermissions(value={"nr:overview:query"})
    public List<UploadSumInfo> batchUploadState(@Valid @RequestBody BatchQueryUpload batchQueryUpload) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(batchQueryUpload.getTaskKey())) {
            return this.overviewVirtualNodeService.batchUploadState(batchQueryUpload);
        }
        return this.uploadOverviewConverter.batchUploadState(batchQueryUpload);
    }

    @RequestMapping(value={"/api/batchQueryState"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    public List<UploadAllFormSumInfo> batchQueryState(@Valid @RequestBody BatchQueryUpload batchQueryUpload) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(batchQueryUpload.getTaskKey())) {
            return this.batchUploadStateController.batchQueryState(batchQueryUpload);
        }
        return this.uploadOverviewConverter.batchQueryState(batchQueryUpload);
    }

    @RequestMapping(value={"/api/uploadState/period"}, method={RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    public String getPeriod(@ApiParam(name="period", value="\u65f6\u671f", required=true) @RequestParam(value="period") String period, @ApiParam(name="formSchemeKey", value="\u62a5\u8868\u65b9\u6848", required=true) @RequestParam(value="formSchemeKey") String formSchemeKey) {
        return this.batchUploadStateController.getPeriod(period, formSchemeKey);
    }

    @RequestMapping(value={"/api/allReportTaskTrees"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4")
    @NRContextBuild
    public List<ITree<TaskTreeObj>> getAllTaskGroupDataS() {
        return this.batchUploadStateController.getAllTaskGroupDataS();
    }

    @RequestMapping(value={"/api/queryTaskGroupTreeByKey"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4")
    @NRContextBuild
    public List<ITree<TaskTreeObj>> queryTaskGroupTreeByKey(@RequestBody TaskGroupParam taskGroupParam) {
        return this.batchUploadStateController.queryTaskByKey(taskGroupParam);
    }

    @RequestMapping(value={"/api/allTaskTree"}, method={RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u5206\u7ec4")
    @NRContextBuild
    public List<ITaskTreeNode> getAllTaskGroupDataTree() {
        return this.batchUploadStateController.getAllTaskGroup();
    }

    @RequestMapping(value={"/api/batchUploadAction"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u5386\u53f2")
    @NRContextBuild
    public List<UploadActionInfo> batchUploadActions(@Valid @RequestBody BatchQueryUpload batchQueryUpload) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(batchQueryUpload.getTaskKey())) {
            return this.batchUploadStateController.batchUploadActions(batchQueryUpload);
        }
        return this.uploadOverviewConverter.batchUploadActions(batchQueryUpload);
    }

    @RequestMapping(value={"/api/batchWorkFlow"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u52a8\u4f5c")
    @NRContextBuild
    @RequiresPermissions(value={"nr:overview:query"})
    public List<ActionInfo> batchWorkFlow(@Valid @RequestBody QueryUploadStateInfo queryUploadStateInfo) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(queryUploadStateInfo.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return this.batchUploadStateController.batchWorkFlow(queryUploadStateInfo);
        }
        return this.uploadOverviewConverter.batchWorkFlow(queryUploadStateInfo);
    }

    @RequestMapping(value={"/api/exportUploadState"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5bfc\u51fa\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    @RequiresPermissions(value={"nr:overview:export"})
    public void exportUploadState(@Valid @RequestBody(required=false) ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(exportExcelState.getTaskKey())) {
            this.batchUploadStateController.exportUploadState(exportExcelState, response, request);
        }
        this.uploadOverviewConverter.exportUploadState(exportExcelState, response, request);
    }

    @RequestMapping(value={"/api/exportAllFormState"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5bfc\u51fa\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    public void exportAllFormState(@Valid @RequestBody(required=false) ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws IOException {
        this.batchUploadStateController.exportAllFormState(exportExcelState, response, request);
    }

    @RequestMapping(value={"/api/queryWorkFlowType"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u52a8\u4f5c")
    @NRContextBuild
    public WorkFlowType queryWorkType(@Valid @RequestBody BatchQueryWorkFlowType batchQueryWorkFlowType) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(batchQueryWorkFlowType.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return this.batchUploadStateController.queryWorkType(batchQueryWorkFlowType);
        }
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        switch (workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                return WorkFlowType.ENTITY;
            }
            case FORM: {
                return WorkFlowType.FORM;
            }
            case FORM_GROUP: {
                return WorkFlowType.GROUP;
            }
        }
        return WorkFlowType.ENTITY;
    }

    @RequestMapping(value={"/api/exportUploadDetails"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u5bfc\u51fa\u4e0a\u62a5\u72b6\u6001")
    @NRContextBuild
    @RequiresPermissions(value={"nr:overview:query"})
    public void exportUploadState2(@Valid @RequestBody(required=false) ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(exportExcelState.getTaskKey())) {
            this.batchUploadStateController.exportUploadState2(exportExcelState, response, request);
        } else {
            this.uploadOverviewConverter.exportUploadState2(exportExcelState, response, request);
        }
    }

    @RequestMapping(value={"/api/queryUploadStateDetails"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001\u660e\u7ec6")
    @NRContextBuild
    public List<UploadRecordDetail> queryUploadState(@Valid @RequestBody(required=false) ExportExcelState exportExcelState, HttpServletResponse response, HttpServletRequest request) throws Exception {
        return this.batchUploadStateController.queryUploadState(exportExcelState, response, request);
    }

    @RequestMapping(value={"/api/workFlowActionTitle"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public Map<String, String> getWorkFlowActionTitle(@Valid @RequestBody BatchQueryUpload param) {
        return this.batchUploadStateController.getWorkFlowActionTitle(param);
    }

    @RequestMapping(value={"/api/treeDataSource"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6811\u5f62\u6570\u636e\u6e90\u6807\u8bc6")
    @NRContextBuild
    public String getTreeDataSource(@Valid @RequestBody TreeDataSoureParam treeDataSoureParam) {
        boolean enableTwoTree = this.workFlowDimensionBuilder.enableVirtualTree(treeDataSoureParam.getFormSchemeKey(), treeDataSoureParam.getPeriod());
        if (enableTwoTree) {
            return "def-virtual-tree-entity-row-scheme-v2";
        }
        return "def-tree-entity-row-scheme";
    }

    @RequestMapping(value={"/api/isTaskGroup"}, method={RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value="\u5224\u65ad\u662f\u4efb\u52a1\u5206\u7ec4\u8fd8\u662f\u4efb\u52a1")
    @NRContextBuild
    public boolean isTaskGroup(@RequestParam(value="selectKey") String selectKey) {
        return this.batchUploadStateController.isTaskGroup(selectKey);
    }

    @RequestMapping(value={"/api/queryUploadSumInfo"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u72b6\u6001-\u65b0\u7edf\u8ba1\u89c4\u5219")
    @NRContextBuild
    public List<UploadSumInfo> queryUploadSumInfo(@Valid @RequestBody BatchQueryUpload batchQueryUpload) {
        return this.overviewVirtualNodeAndCustomServiceImpl.batchUploadState(batchQueryUpload);
    }

    @RequestMapping(value={"/api/queryWorkFlowAction"}, method={RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u4e0a\u62a5\u52a8\u4f5c")
    @NRContextBuild
    public List<ActionInfo> queryWorkFlowAction(@Valid @RequestBody QueryUploadStateInfo queryUploadStateInfo) {
        return this.batchUploadSumService.batchWorkFlow(queryUploadStateInfo);
    }

    @RequestMapping(value={"/table/cols"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u8868\u683c\u5217")
    public List<ChangeEntitys> getTableCols() {
        return this.batchUploadStateController.getTableCols();
    }
}

