/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.authorize.service.LicenceService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.authorize.service.LicenceService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.excel.importexcel.common.ExcelType;
import com.jiuqi.nr.designer.excel.importexcel.common.NrDesingerExcelErrorEnum;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.util.OssUploadUtil;
import com.jiuqi.nr.designer.web.rest.vo.ExcelExportVO;
import com.jiuqi.nr.designer.web.service.ExcelExportExecutor;
import com.jiuqi.nr.designer.web.service.ExcelExportService;
import com.jiuqi.nr.designer.web.service.ExcelImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"Excel\u53c2\u6570"})
public class ExcelController {
    @Autowired
    private ExcelImportService excelImportService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private LicenceService licenceService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @ApiOperation(value="Excel\u53c2\u6570\u5bfc\u5165")
    @RequestMapping(value={"excel/import"}, method={RequestMethod.POST})
    public Map<String, Object> importFormExcel(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws Exception {
        String authorizeConfig = this.licenceService.findAuthorizeConfig("com.jiuqi.nr.formDesigner", "com.jiuqi.nr.formDesigner.excel");
        if (!authorizeConfig.equals("true")) {
            throw new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_017);
        }
        String taskKey = request.getParameter("taskId");
        String taskTitle = this.designTimeViewController.queryTaskDefine(taskKey).getTitle();
        String logTitle = "EXCEL\u53c2\u6570\u5bfc\u5165";
        String formulaSchemeKey = request.getParameter("formulaSchemeId");
        boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(taskKey);
        if (!taskCanEdit) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
        }
        String schemeKey = request.getParameter("schemeId");
        String groupKey = request.getParameter("groupId");
        boolean isImportField = Boolean.valueOf(request.getParameter("isImportField"));
        ZipSecureFile.setMinInflateRatio(-1.0);
        Workbook workbook = null;
        try (InputStream inputStream = file.getInputStream();){
            workbook = WorkbookFactory.create(inputStream);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
            NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            HashMap<String, Object> rsErrorMap = new HashMap<String, Object>();
            rsErrorMap.put("fileError", new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_001).getErrorMessage());
            return rsErrorMap;
        }
        ExcelType excelType = null;
        if (workbook instanceof HSSFWorkbook) {
            logger.info("2003\u53ca\u4ee5\u4e0b");
            excelType = ExcelType.XLSHXXF;
        } else {
            logger.info("2007\u53ca\u4ee5\u4e0a");
            excelType = ExcelType.XLSXXSSF;
        }
        Map<String, Object> resMap = null;
        resMap = isImportField ? this.excelImportService.excelPrePrecess(workbook, excelType, groupKey, schemeKey, formulaSchemeKey, taskKey) : this.excelImportService.excelPrePrecessOnlyStyle(workbook, excelType, groupKey, schemeKey, formulaSchemeKey, taskKey);
        ArrayList formObjs = (ArrayList)resMap.get("successFormData");
        NrDesignLogHelper.log(logTitle, taskTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        return resMap;
    }

    @ApiOperation(value="\u6309\u8868\u6837\u5bfc\u51faExcel")
    @RequestMapping(value={"excel/export"}, method={RequestMethod.POST})
    public void exportEfdcWithFormStyle(@RequestBody ExcelExportVO excelExportObj, HttpServletResponse res) throws JQException {
        try {
            this.excelExportService.exportFormStyle(excelExportObj, res);
        }
        catch (Exception e) {
            NrDesignLogHelper.log("\u6309\u8868\u6837\u5bfc\u51faExcel", "\u5bfc\u51fa\u8868\u6837\u51fa\u73b0\u9519\u8bef", NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_182, (Throwable)e);
        }
    }

    @ApiOperation(value="\u5f02\u6b65\u5bfc\u51faexcel")
    @RequestMapping(value={"excel/export_async"}, method={RequestMethod.POST})
    public String exportParaPackage(@RequestBody ExcelExportVO excelExportObj) throws Exception {
        String taskKey = excelExportObj.getTaskKey();
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_171);
        }
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)excelExportObj));
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new ExcelExportExecutor());
        return this.asyncTaskManager.publishTask(info, "ASYNCTASK_EXCELEXPORT");
    }

    @ApiOperation(value="\u4e0b\u8f7d\u5bfc\u51fa\u7684excel")
    @RequestMapping(value={"excel/download"}, method={RequestMethod.POST})
    public void downloadBatchExport(@RequestBody ExcelExportVO excelExportObj, HttpServletResponse response) throws Exception {
        String downLoadKey = excelExportObj.getDownLoadKey();
        OssUploadUtil.fileDownLoad(downLoadKey, (OutputStream)response.getOutputStream());
    }
}

