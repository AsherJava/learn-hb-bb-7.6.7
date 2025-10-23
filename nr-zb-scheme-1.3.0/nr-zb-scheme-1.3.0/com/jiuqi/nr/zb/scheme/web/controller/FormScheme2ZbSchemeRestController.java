/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobCacheProviderManager
 *  com.jiuqi.bi.core.jobs.bean.JobInstanceBean
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zb.scheme.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobCacheProviderManager;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.zb.scheme.exception.JQException;
import com.jiuqi.nr.zb.scheme.exception.ZbSchemeErrorEnum;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeTreeService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckIOService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckQueryService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckService;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbGenerateService;
import com.jiuqi.nr.zb.scheme.service.async.FormSchemeZbCheckAsyncExecutor;
import com.jiuqi.nr.zb.scheme.service.async.FormSchemeZbGenerateAsyncExecutor;
import com.jiuqi.nr.zb.scheme.web.vo.AsyncPublishInfo;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeNodeVO;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeSearchItem;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeTreeSearchParam;
import com.jiuqi.nr.zb.scheme.web.vo.FormSchemeVO;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbParam;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbResult;
import com.jiuqi.nr.zb.scheme.web.vo.PageVO;
import com.jiuqi.nr.zb.scheme.web.vo.ProgressInfo;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckExportParam;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckItemVO;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckParam;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckQueryParam;
import io.swagger.annotations.ApiOperation;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/zb_scheme/by_form_scheme/"})
public class FormScheme2ZbSchemeRestController {
    private static final Logger logger = LoggerFactory.getLogger(FormScheme2ZbSchemeRestController.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormSchemeZbCheckService formSchemePreCheckService;
    @Autowired
    private IFormSchemeTreeService formSchemeTreeService;
    @Autowired
    private IFormSchemeZbCheckQueryService formSchemeZbCheckQueryService;
    @Autowired
    private IFormSchemeZbGenerateService formSchemeZbGenerateService;
    @Autowired
    private IFormSchemeZbCheckIOService formSchemeZbCheckIOService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private FormSchemeZbCheckAsyncExecutor formSchemeZbCheckAsyncExecutor;
    @Autowired
    private FormSchemeZbGenerateAsyncExecutor formSchemeZbGenerateAsyncExecutor;

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848")
    @GetMapping(value={"query_task_form_scheme/{taskKey}"})
    public List<FormSchemeVO> queryTaskFormScheme(@PathVariable(name="taskKey") String taskKey) throws JQException {
        try {
            List designFormSchemes = this.designTimeViewController.listFormSchemeByTask(taskKey);
            if (CollectionUtils.isEmpty(designFormSchemes)) {
                return Collections.emptyList();
            }
            return designFormSchemes.stream().map(FormSchemeVO::new).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bf9\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6307\u6807\u6267\u884c\u9884\u68c0\u67e5")
    @PostMapping(value={"precheck"})
    public String checkFormSchemeZb(@RequestBody @Valid ZbCheckParam zbCheckParam) throws JQException {
        try {
            return this.formSchemePreCheckService.checkZb(zbCheckParam);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bf9\u62a5\u8868\u65b9\u6848\u4e0b\u7684\u6307\u6807\u6267\u884c\u9884\u68c0\u67e5\uff08\u5f02\u6b65\uff09")
    @PostMapping(value={"async_precheck"})
    public AsyncPublishInfo checkFormSchemeZbAsync(@RequestBody @Valid ZbCheckParam zbCheckParam) throws JQException {
        try {
            AsyncPublishInfo asyncPublishInfo = new AsyncPublishInfo();
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(new ObjectMapper().writeValueAsString((Object)zbCheckParam));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)this.formSchemeZbCheckAsyncExecutor);
            String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            asyncPublishInfo.setAsyncTaskId(asyncTaskId);
            asyncPublishInfo.setPrompt("\u5f00\u59cb\u68c0\u67e5");
            return asyncPublishInfo;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_ASYNC_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_ASYNC_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u6307\u6807\u65b9\u6848\u6307\u6807\u9884\u68c0\u67e5\uff08\u5f02\u6b65\uff09\u8fdb\u5ea6")
    @GetMapping(value={"async_precheck_progress/{asyncTaskId}"})
    public ProgressInfo zbCheckProgress(@PathVariable(name="asyncTaskId") String asyncTaskId) throws JQException {
        try {
            AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
            if (asyncTask.getState().equals((Object)TaskState.FINISHED)) {
                return new ProgressInfo(asyncTask);
            }
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(asyncTaskId)) {
                JobInstanceBean bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(asyncTaskId);
                return new ProgressInfo(asyncTask, bean.getPrompt());
            }
            return new ProgressInfo(asyncTask);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_ASYNC_PROGRESS_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_ASYNC_PROGRESS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848-\u62a5\u8868\u6811\u5f62\u7684\u6839\u8282\u70b9")
    @GetMapping(value={"query_form_scheme_tree/root/{formSchemeKey}"})
    public List<FormSchemeTreeNodeVO> getFormSchemeTreeRoot(@PathVariable(name="formSchemeKey") String formSchemeKey) throws JQException {
        try {
            return this.formSchemeTreeService.getRoot(formSchemeKey).stream().map(FormSchemeTreeNodeVO::valueOf).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u65b9\u6848-\u62a5\u8868\u6811\u5f62\u7684\u4e0b\u7ea7\u8282\u70b9")
    @GetMapping(value={"query_form_scheme_tree/children/{formGroupKey}"})
    public List<FormSchemeTreeNodeVO> getFormSchemeTreeChildren(@PathVariable(name="formGroupKey") String formGroupKey) throws JQException {
        try {
            return this.formSchemeTreeService.getChildren(formGroupKey).stream().map(FormSchemeTreeNodeVO::valueOf).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u62a5\u8868\u65b9\u6848-\u62a5\u8868\u6811\u5f62\u641c\u7d22")
    @PostMapping(value={"query_form_scheme_tree/search/{formSchemeKey}/{keyword}"})
    public List<FormSchemeTreeSearchItem> searchFormSchemeTree(@RequestBody @Valid FormSchemeTreeSearchParam param) throws JQException {
        try {
            return this.formSchemeTreeService.search(param.getFormSchemeKey(), param.getKeyword());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_TREE_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u6309\u7167\u7b5b\u9009\u6761\u4ef6\u83b7\u53d6\u6307\u6807\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"query_check_result_filter"})
    public PageVO<ZbCheckItemVO> filterZbCheckResult(@RequestBody @Valid ZbCheckQueryParam param) throws JQException {
        try {
            int currentPage = param.getCurrentPage();
            int pageSize = param.getPageSize();
            List<ZbCheckItemDTO> zbCheckItemDTOS = this.formSchemeZbCheckQueryService.filterByCond(param);
            PageVO<ZbCheckItemVO> pageVO = new PageVO<ZbCheckItemVO>(currentPage, pageSize, zbCheckItemDTOS.size());
            List itemVOS = this.doPageWhenSearch(zbCheckItemDTOS, pageSize, currentPage, pageVO::setCurrentPage).stream().map(ZbCheckItemVO::build).collect(Collectors.toList());
            pageVO.setData(itemVOS);
            return pageVO;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_QUERY_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_QUERY_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u6309\u7167\u5173\u952e\u5b57\u83b7\u53d6\u6307\u6807\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"query_check_result_search"})
    public PageVO<ZbCheckItemVO> searchZbCheckResult(@RequestBody @Valid ZbCheckQueryParam param) throws JQException {
        try {
            int currentPage = param.getCurrentPage();
            int pageSize = param.getPageSize();
            List<ZbCheckItemDTO> zbCheckItemDTOS = this.formSchemeZbCheckQueryService.search(param);
            PageVO<ZbCheckItemVO> pageVO = new PageVO<ZbCheckItemVO>(currentPage, pageSize, zbCheckItemDTOS.size());
            List itemVOS = this.doPage(zbCheckItemDTOS, pageSize, currentPage).stream().map(ZbCheckItemVO::build).collect(Collectors.toList());
            pageVO.setData(itemVOS);
            return pageVO;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_QUERY_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_QUERY_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807")
    @PostMapping(value={"generate_zb"})
    public GenerateZbResult generateFormSchemeZb(@RequestBody @Valid GenerateZbParam param) throws JQException {
        try {
            return this.formSchemeZbGenerateService.generateZbInfo(param);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_GENERATE_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_GENERATE_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807\uff08\u5f02\u6b65\uff09")
    @PostMapping(value={"async_generate_zb"})
    public AsyncPublishInfo generateFormSchemeZbAsync(@RequestBody @Valid GenerateZbParam generateZbParam) throws JQException {
        try {
            AsyncPublishInfo asyncPublishInfo = new AsyncPublishInfo();
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(new ObjectMapper().writeValueAsString((Object)generateZbParam));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)this.formSchemeZbGenerateAsyncExecutor);
            String asyncTaskId = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
            asyncPublishInfo.setAsyncTaskId(asyncTaskId);
            asyncPublishInfo.setPrompt("\u5f00\u59cb\u9006\u5411\u751f\u6210");
            return asyncPublishInfo;
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_ASYNC_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_ASYNC_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u9006\u5411\u751f\u6210\u6307\u6807\u4f53\u7cfb\u6307\u6807\uff08\u5f02\u6b65\uff09\u8fdb\u5ea6")
    @GetMapping(value={"async_generate_zb_progress/{checkKey}/{asyncTaskId}"})
    public ProgressInfo zbGenerateProgress(@PathVariable(name="checkKey") String checkKey, @PathVariable(name="asyncTaskId") String asyncTaskId) throws JQException {
        try {
            AsyncTask asyncTask = this.asyncTaskManager.queryTask(asyncTaskId);
            if (asyncTask.getState().equals((Object)TaskState.FINISHED)) {
                return new ProgressInfo(asyncTask, this.formSchemeZbGenerateService.getResult(checkKey), "\u6210\u529f");
            }
            if (JobCacheProviderManager.getInstance().getJobInstanceBeanCache().exist(asyncTaskId)) {
                JobInstanceBean bean = (JobInstanceBean)JobCacheProviderManager.getInstance().getJobInstanceBeanCache().get(asyncTaskId);
                return new ProgressInfo(asyncTask, bean.getPrompt());
            }
            return new ProgressInfo(asyncTask);
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_GENERATE_PROGRESS.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_GENERATE_PROGRESS, e.getMessage());
        }
    }

    @ApiOperation(value="\u5bfc\u51fa\u6307\u6807\u68c0\u67e5\u7ed3\u679c")
    @PostMapping(value={"export_precheck_result"})
    public void exportZbCheckResult(@RequestBody @Valid ZbCheckExportParam param, HttpServletResponse response) throws JQException {
        String fileName = "\u6307\u6807\u68c0\u67e5\u7ed3\u679c";
        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            this.formSchemeZbCheckIOService.exportExcel(param, workbook);
            fileName = URLEncoder.encode(fileName, "utf-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
            HtmlUtils.validateHeaderValue((String)fileName);
            response.setHeader("Content-disposition", fileName);
            response.setContentType("application/octet-stream");
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
            workbook.write((OutputStream)response.getOutputStream());
        }
        catch (Exception e) {
            logger.error(ZbSchemeErrorEnum.ZB_CHECK_EXPORT_ERROR.getMessage(), e);
            throw new JQException((ErrorEnum)ZbSchemeErrorEnum.ZB_CHECK_EXPORT_ERROR, e.getMessage());
        }
    }

    private <T> List<T> doPage(List<T> data, int pageSize, int currentPage) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        int dataSize = data.size();
        if (dataSize <= pageSize) {
            return data;
        }
        currentPage = Math.max(currentPage, 1);
        int start = (currentPage - 1) * pageSize;
        return data.subList(Math.min(start, dataSize), Math.min(start + pageSize, dataSize));
    }

    private <T> List<T> doPageWhenSearch(List<T> data, int pageSize, int currentPage, Consumer<Integer> pageStartConsumer) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        int dataSize = data.size();
        int start = ((currentPage = Math.max(currentPage, 1)) - 1) * pageSize;
        start = start >= dataSize ? 0 : start;
        pageStartConsumer.accept(start);
        int end = Math.min(start + pageSize, dataSize);
        return data.subList(start, end);
    }
}

