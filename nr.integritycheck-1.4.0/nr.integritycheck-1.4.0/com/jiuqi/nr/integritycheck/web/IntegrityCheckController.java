/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nvwa.authority.vo.ResultObject
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.integritycheck.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.asynctask.IntegrityCheckAsyncTaskExecutor;
import com.jiuqi.nr.integritycheck.common.AddTagParam;
import com.jiuqi.nr.integritycheck.common.CheckErrDesContext;
import com.jiuqi.nr.integritycheck.common.ErrorDesContext;
import com.jiuqi.nr.integritycheck.common.ErrorDesInfo;
import com.jiuqi.nr.integritycheck.common.ErrorDesShowInfo;
import com.jiuqi.nr.integritycheck.common.ErrorFormUnitInfo;
import com.jiuqi.nr.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.integritycheck.common.PageTableICRInfo;
import com.jiuqi.nr.integritycheck.common.QueryICRContext;
import com.jiuqi.nr.integritycheck.common.QueryICRParam;
import com.jiuqi.nr.integritycheck.common.ResultInfo;
import com.jiuqi.nr.integritycheck.common.TableICRInfo;
import com.jiuqi.nr.integritycheck.service.IIntegrityCheckService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nvwa.authority.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"\u8868\u5b8c\u6574\u6027\u68c0\u67e5"})
@RequestMapping(value={"/api/v2/finalIntegrityCheck/integritycheck"})
@RestController
public class IntegrityCheckController {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckController.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IIntegrityCheckService integrityCheckService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;

    @JLoggable(value="\u6267\u884c\u8868\u5b8c\u6574\u6027\u68c0\u67e5")
    @PostMapping(value={"/integrity-check"})
    @ApiOperation(value="\u8868\u5b8c\u6574\u6027\u68c0\u67e5", notes="\u53d1\u8d77\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7684\u5f02\u6b65\u4efb\u52a1")
    @NRContextBuild
    public AsyncTaskInfo integrityCheck(@Valid @RequestBody IntegrityCheckInfo integrityCheckInfo) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(integrityCheckInfo.getTaskKey());
        npRealTimeTaskInfo.setFormSchemeKey(integrityCheckInfo.getFormSchemeKey());
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new IntegrityCheckAsyncTaskExecutor());
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)integrityCheckInfo)));
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo, "ASYNCTASK_TABLE_INTEGRITYCHECK");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"/query-ic-result"})
    @NRContextBuild
    public ResultInfo queryICResult(@Valid @RequestBody QueryICRContext queryICRContext) {
        try {
            return this.integrityCheckService.queryICResult(this.buildQueryICRParam(queryICRContext));
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u9519\u8bef", e);
            return null;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"/query-integritycheck-result"})
    @NRContextBuild
    public ResultInfo queryIntegritycheckResult(@Valid @RequestBody QueryICRContext queryICRContext) {
        HashMap<String, ErrorDesShowInfo> tableICDInfos = new HashMap<String, ErrorDesShowInfo>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(queryICRContext.getTaskKey());
        List<TableICRInfo> tableICRInfos = this.structureICRInfo(queryICRContext);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskDefine.getDw());
        String masterDimName = entityDefine.getDimensionName();
        for (TableICRInfo tableICRInfo : tableICRInfos) {
            String key = tableICRInfo.getDimNameValueMap().get(masterDimName) + tableICRInfo.getFormKey();
            ErrorDesShowInfo errorDesShowInfo = new ErrorDesShowInfo();
            errorDesShowInfo.setUpdater(tableICRInfo.getErrorDesInfo().getUpdater());
            errorDesShowInfo.setUpdateTime(tableICRInfo.getErrorDesInfo().getUpdateTime());
            errorDesShowInfo.setDescription(tableICRInfo.getErrorDesInfo().getDescription());
            tableICDInfos.put(key, errorDesShowInfo);
        }
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setTableICDInfos(tableICDInfos);
        return resultInfo;
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6bcf\u5f20\u8868\u51fa\u9519\u5355\u4f4d")
    @PostMapping(value={"/query-error-formunit"})
    @NRContextBuild
    public List<ErrorFormUnitInfo> queryErrorFormUnit(@Valid @RequestBody QueryICRContext queryICRContext) {
        try {
            return this.integrityCheckService.queryErrorFormUnit(this.buildQueryICRParam(queryICRContext));
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u9519\u8bef", e);
            return null;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u67e5\u8be2\u6bcf\u5f20\u8868\u51fa\u9519\u5355\u4f4d")
    @PostMapping(value={"/export-error-formunit"})
    @NRContextBuild
    public void exportErrorFormUnit(@Valid @RequestBody QueryICRContext queryICRContext, HttpServletResponse response) {
        try {
            String fileName = "\u8868\u5b8c\u6574\u6027\u68c0\u67e5.xlsx";
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName;
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            this.integrityCheckService.exportErrorFormUnit(this.buildQueryICRParam(queryICRContext), (OutputStream)response.getOutputStream());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private QueryICRParam buildQueryICRParam(QueryICRContext queryICRContext) {
        QueryICRParam param = new QueryICRParam();
        param.setBatchId(queryICRContext.getBatchId());
        param.setTaskKey(queryICRContext.getTaskKey());
        DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(queryICRContext.getDimensionSet(), queryICRContext.getFormSchemeKey());
        param.setDimensionCollection(dimensionCollection);
        param.setFormSchemeKey(queryICRContext.getFormSchemeKey());
        param.setFormKeys(queryICRContext.getFormKeys());
        param.setPageInfo(queryICRContext.getPageInfo());
        param.setNeedDiffTable(queryICRContext.isDiffTable());
        param.setNeedZeroTable(queryICRContext.isZeroTable());
        return param;
    }

    private List<TableICRInfo> structureICRInfo(QueryICRContext queryICRContext) {
        QueryICRParam queryICRParam = this.buildQueryICRParam(queryICRContext);
        PageTableICRInfo pageTableICRInfo = this.integrityCheckService.pageQueryCheckResult(queryICRParam);
        ArrayList<TableICRInfo> tableICRInfoFilter = new ArrayList<TableICRInfo>();
        for (TableICRInfo tableICRInfo : pageTableICRInfo.getTableICRInfos()) {
            if (null == tableICRInfo.getErrorDesInfo() || !StringUtils.isNotEmpty((String)tableICRInfo.getErrorDesInfo().getDescription())) continue;
            tableICRInfoFilter.add(tableICRInfo);
        }
        return tableICRInfoFilter;
    }

    @ResponseBody
    @ApiOperation(value="\u7f16\u8f91\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/edit-error-des"})
    @NRContextBuild
    public ErrorDesInfo editErrorDes(@Valid @RequestBody ErrorDesContext context) {
        try {
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormSchemeKey());
            return this.integrityCheckService.editErrorDes(context.getTaskKey(), (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0), context.getFormKeys().get(0), context.getDescription());
        }
        catch (Exception e) {
            logger.error("\u7f16\u8f91\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            return null;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u6279\u91cf\u7f16\u8f91\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/batchEdit-error-des"})
    @NRContextBuild
    public boolean batchEditErrorDes(@Valid @RequestBody ErrorDesContext context) {
        try {
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormSchemeKey());
            this.integrityCheckService.batchEditErrorDes(context.getTaskKey(), dimensionCollection, context.getFormKeys(), context.getDescription(), context.getBatchId());
            return true;
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u7f16\u8f91\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            return false;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u6821\u9a8c\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/check-error-des"})
    public String checkErrorDes(@Valid @RequestBody CheckErrDesContext context) {
        return this.integrityCheckService.checkErrorDes(context);
    }

    @ResponseBody
    @ApiOperation(value="\u5220\u9664\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e")
    @PostMapping(value={"/del-error-des"})
    @NRContextBuild
    public boolean delErrorDes(@Valid @RequestBody ErrorDesContext context) {
        try {
            DimensionCollection dimensionCollection = this.dimCollectionBuildUtil.buildDimensionCollection(context.getDimensionSet(), context.getFormSchemeKey());
            this.integrityCheckService.deleteErrorDes(context.getTaskKey(), (DimensionCombination)dimensionCollection.getDimensionCombinations().get(0), context.getFormKeys().get(0));
            return true;
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u51fa\u9519\u8bf4\u660e\u5931\u8d25", e);
            return false;
        }
    }

    @ResponseBody
    @ApiOperation(value="\u6dfb\u52a0\u6807\u8bb0")
    @PostMapping(value={"/add-tags"})
    @NRContextBuild
    public ResultObject addTags(@Valid @RequestBody AddTagParam param) {
        this.integrityCheckService.addTags(param);
        return new ResultObject();
    }

    @ResponseBody
    @ApiOperation(value="\u5bfc\u51fa\u8868\u5b8c\u6574\u6027\u5ba1\u6838\u7ed3\u679c")
    @PostMapping(value={"/export-check-result"})
    @NRContextBuild
    public void exportCheckResult(@Valid @RequestBody QueryICRContext queryICRContext, HttpServletResponse response) {
        try {
            String fileName = "\u8868\u5b8c\u6574\u6027\u68c0\u67e5.xlsx";
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName;
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            this.integrityCheckService.exportCheckResult(this.buildQueryICRParam(queryICRContext), (OutputStream)response.getOutputStream());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

