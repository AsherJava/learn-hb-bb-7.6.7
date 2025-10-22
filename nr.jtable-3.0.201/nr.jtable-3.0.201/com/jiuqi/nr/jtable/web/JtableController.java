/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.engine.result.PagingInfo
 *  com.jiuqi.bi.security.HtmlUtils
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentity.entity.IDataEntity
 *  com.jiuqi.nr.dataentity.entity.IDataEntityRow
 *  com.jiuqi.nr.dataentity.service.DataEntityService
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.fileupload.FileUploadParamInfo
 *  com.jiuqi.nr.fileupload.FilesUploadReturnInfo
 *  com.jiuqi.nr.fileupload.service.CheckUploadFileService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.DefaultGridOptions
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.nvwa.quickreport.api.query.Wrapper
 *  com.jiuqi.nvwa.quickreport.api.query.option.Option
 *  com.jiuqi.nvwa.quickreport.dto.ReportData
 *  com.jiuqi.nvwa.quickreport.web.query.GridController
 *  com.jiuqi.nvwa.quickreport.web.query.SheetGridData
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  io.swagger.annotations.ApiParam
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.Valid
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.http.HttpStatus
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.jtable.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.engine.result.PagingInfo;
import com.jiuqi.bi.security.HtmlUtils;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.dataentity.service.DataEntityService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fileupload.FileUploadParamInfo;
import com.jiuqi.nr.fileupload.FilesUploadReturnInfo;
import com.jiuqi.nr.fileupload.service.CheckUploadFileService;
import com.jiuqi.nr.jtable.annotation.ActionBeforeAfterExe;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.annotation.LevelAuthRead;
import com.jiuqi.nr.jtable.annotation.LevelAuthWrite;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.exception.QueryAnalFormException;
import com.jiuqi.nr.jtable.exception.SaveDataException;
import com.jiuqi.nr.jtable.params.base.CStyleFile;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaConditionFile;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.FromGridData;
import com.jiuqi.nr.jtable.params.base.IsNewAttachmentInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.LinkSimpleData;
import com.jiuqi.nr.jtable.params.base.RegionSimpleData;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.params.input.AnalysisFormulaInfo;
import com.jiuqi.nr.jtable.params.input.CardInputInit;
import com.jiuqi.nr.jtable.params.input.CellValueQueryInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByCodeInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import com.jiuqi.nr.jtable.params.input.FormulaQueryInfo;
import com.jiuqi.nr.jtable.params.input.LinkFileOptionInfo;
import com.jiuqi.nr.jtable.params.input.LinkImgOptionInfo;
import com.jiuqi.nr.jtable.params.input.MeasureQueryInfo;
import com.jiuqi.nr.jtable.params.input.PasteFormatDataInfo;
import com.jiuqi.nr.jtable.params.input.RegionDataCommitSet;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.input.ReportDataCommitSet;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.params.output.AnalyTableStyle;
import com.jiuqi.nr.jtable.params.output.CardInputInfo;
import com.jiuqi.nr.jtable.params.output.CellDataSet;
import com.jiuqi.nr.jtable.params.output.CrossTaskLocateInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormTables;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.params.output.MultiPeriodDataSet;
import com.jiuqi.nr.jtable.params.output.PasteFormatReturnInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataCount;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.params.output.RegionSingleDataSet;
import com.jiuqi.nr.jtable.params.output.ReportDataSet;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.params.output.SaveResult;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.jtable.quickGridUtil.GridDataTransform;
import com.jiuqi.nr.jtable.quickGridUtil.QuickGrid2DataSerialize;
import com.jiuqi.nr.jtable.quickGridUtil.QuickGridCellDataSerialize;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableFileService;
import com.jiuqi.nr.jtable.service.IJtableLocateService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.uniformity.annotation.JUniformity;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.DefaultGridOptions;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.nvwa.quickreport.api.query.Wrapper;
import com.jiuqi.nvwa.quickreport.api.query.option.Option;
import com.jiuqi.nvwa.quickreport.dto.ReportData;
import com.jiuqi.nvwa.quickreport.web.query.GridController;
import com.jiuqi.nvwa.quickreport.web.query.SheetGridData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/api/v1/jtable"})
@Api(value="/api/jtable", tags={"\u62a5\u8868\u7ec4\u4ef6rest\u63a5\u53e3"})
public class JtableController {
    private static final Logger logger = LoggerFactory.getLogger(JtableController.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableResourceService jtableResourceService;
    @Autowired
    private IJtableFileService fileService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IJtableContextService jtableContextService;
    @Autowired
    private IDataStateCheckService dataStateCheckService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private GridController gridController;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    private DataEntityService dataEntityService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private CheckUploadFileService checkUploadFileService;
    @Autowired
    private IJtableLocateService jtableLocateService;

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6jtable\u8868\u6837")
    @RequestMapping(value={"/initStyle"}, method={RequestMethod.GET})
    @NRContextBuild
    public void initStyle(@ApiParam(name="formKey", value="\u62a5\u8868key", required=true) @RequestParam(value="formKey") String formKey, @ApiParam(name="taskKey", value="\u4efb\u52a1key \uff08\u53ef\u4ee5\u4e0d\u4f20\u9012\uff0c\u4f20\u9012\u4e86\u5f00\u542f\u6d4f\u89c8\u5668\u7f13\u5b58\uff09", required=false) @RequestParam(value="taskKey", required=false) String taskKey, @ModelAttribute NRContext nrContext, HttpServletRequest request, HttpServletResponse response) {
        if (!this.checkCache(taskKey, request, response)) {
            ObjectMapper mapper = new ObjectMapper();
            String returnStr = "";
            Grid2Data grid2Data = this.jtableParamService.getGridData(formKey);
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
            module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
            mapper.registerModule((Module)module);
            try {
                returnStr = mapper.writeValueAsString((Object)grid2Data);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            try {
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream output = response.getOutputStream();
                output.write(returnStr.getBytes("UTF-8"));
                output.flush();
                output.close();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u95ee\u5377\u8868\u6837")
    @RequestMapping(value={"/style"}, method={RequestMethod.GET})
    @NRContextBuild
    public void init(@ApiParam(name="formKey", value="\u62a5\u8868key", required=true) @RequestParam(value="formKey") String formKey, @ApiParam(name="taskKey", value="\u4efb\u52a1key \uff08\u53ef\u4ee5\u4e0d\u4f20\u9012\uff0c\u4f20\u9012\u4e86\u5f00\u542f\u6d4f\u89c8\u5668\u7f13\u5b58\uff09", required=false) @RequestParam(value="taskKey", required=false) String taskKey, @ApiParam(name="param", value="param", required=false) @RequestParam(value="param", required=false) String param, @ModelAttribute NRContext nrContext, HttpServletRequest request, HttpServletResponse response) {
        try {
            param = URLDecoder.decode(param, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        String reportType = this.jtableParamService.getReportType(formKey);
        if (FormType.FORM_TYPE_INSERTANALYSIS.name().equals(reportType) || FormType.FORM_TYPE_SURVEY.name().equals(reportType) || !this.checkCache(taskKey, request, response)) {
            ObjectMapper mapper = new ObjectMapper();
            String returnStr = "";
            if (FormType.FORM_TYPE_SURVEY.name().equals(reportType)) {
                returnStr = this.jtableParamService.getSurveyData(formKey, taskKey, param);
            } else if (FormType.FORM_TYPE_INSERTANALYSIS.name().equals(reportType)) {
                response.setStatus(HttpStatus.OK.value());
                FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
                String formSchemeKey = formDefine.getFormScheme();
                String[] params = param.split(";");
                HashMap<String, String> paramMap = new HashMap<String, String>();
                for (String paramStr : params) {
                    String[] paramValues = paramStr.split(":");
                    if (paramValues.length != 2) continue;
                    paramMap.put(paramValues[0], paramValues[1]);
                }
                ReportData rd = new ReportData();
                rd.setGuid(formDefine.getExtensionProp("analysisGuid").toString());
                Option op = new Option();
                EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formSchemeKey);
                op.addParamValue("MD_ORG", paramMap.get(dwEntityInfo.getDimensionName()));
                EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formSchemeKey);
                op.addParamValue("MD_PERIOD", paramMap.get(periodEntityInfo.getDimensionName()));
                for (Map.Entry entry : paramMap.entrySet()) {
                    op.addParamValue("P_" + (String)entry.getKey(), entry.getValue());
                }
                Wrapper wp = new Wrapper();
                wp.setOption(op);
                wp.setReportData(rd);
                try {
                    GridData gridData = this.gridController.getPrimarySheetGridData(wp);
                    Grid2Data grid2Data = new Grid2Data();
                    grid2Data = GridDataTransform.gridDataToGrid2Data(gridData, grid2Data);
                    SimpleModule module = new SimpleModule();
                    module.addSerializer(Grid2Data.class, (JsonSerializer)new QuickGrid2DataSerialize(new DefaultGridOptions()));
                    module.addSerializer(GridCellData.class, (JsonSerializer)new QuickGridCellDataSerialize());
                    mapper.registerModule((Module)module);
                    try {
                        returnStr = mapper.writeValueAsString((Object)grid2Data);
                    }
                    catch (JsonProcessingException e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                }
                catch (Exception exception) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
                    throw new QueryAnalFormException(new String[]{formKey + "\u5206\u6790\u8868\u67e5\u8be2\u5931\u8d25"});
                }
            } else {
                Grid2Data grid2Data = this.jtableParamService.getGridData(formKey);
                SimpleModule module = new SimpleModule();
                module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
                module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
                mapper.registerModule((Module)module);
                try {
                    returnStr = mapper.writeValueAsString((Object)grid2Data);
                }
                catch (JsonProcessingException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            try {
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream output = response.getOutputStream();
                output.write(returnStr.getBytes("UTF-8"));
                output.flush();
                output.close();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u5206\u6790\u8868\u53c2\u6570wrapper")
    @PostMapping(value={"/getAnalyWrapper"})
    @NRContextBuild
    public Wrapper getAnalyWrapper(@Valid @RequestBody JtableContext jtableContext) {
        FormDefine formDefine = this.runTimeViewController.queryFormById(jtableContext.getFormKey());
        ReportData rd = new ReportData();
        rd.setGuid(formDefine.getExtensionProp("analysisGuid").toString());
        Option op = new Option();
        Map<String, DimensionValue> dimensionSet = jtableContext.getDimensionSet();
        EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
        op.addParamValue("MD_ORG", (Object)dimensionSet.get(dwEntityInfo.getDimensionName()).getValue());
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        op.addParamValue("MD_PERIOD", (Object)dimensionSet.get(periodEntityInfo.getDimensionName()).getValue());
        for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
            op.addParamValue("P_" + entry.getKey(), (Object)entry.getValue().getValue());
        }
        Wrapper wp = new Wrapper();
        wp.setOption(op);
        wp.setReportData(rd);
        return wp;
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u5206\u6790\u8868\u8868\u6837")
    @RequestMapping(value={"/initAnalyStyle"}, method={RequestMethod.GET})
    @NRContextBuild
    public void initAnalyStyle(@ApiParam(name="formKey", value="\u62a5\u8868key", required=true) @RequestParam(value="formKey") String formKey, @ApiParam(name="taskKey", value="\u4efb\u52a1key", required=false) @RequestParam(value="taskKey", required=false) String taskKey, @ApiParam(name="pageNum", value="\u9875\u6570", required=false) @RequestParam(value="pageNum", required=false) int pageNum, @RequestParam(value="param", required=false) String param, @ModelAttribute NRContext nrContext, HttpServletResponse response) {
        try {
            param = URLDecoder.decode(param, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String returnStr = "";
        response.setStatus(HttpStatus.OK.value());
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        String formSchemeKey = formDefine.getFormScheme();
        String[] params = param.split(";");
        HashMap<String, String> paramMap = new HashMap<String, String>();
        for (String paramStr : params) {
            String[] paramValues = paramStr.split(":");
            if (paramValues.length != 2) continue;
            paramMap.put(paramValues[0], paramValues[1]);
        }
        ReportData rd = new ReportData();
        rd.setGuid(formDefine.getExtensionProp("analysisGuid").toString());
        Option op = new Option();
        if (pageNum != 0) {
            op.setPageNum(pageNum);
        } else {
            op.setPageNum(1);
        }
        EntityViewData dwEntityInfo = this.jtableParamService.getDwEntity(formSchemeKey);
        op.addParamValue("MD_ORG", paramMap.get(dwEntityInfo.getDimensionName()));
        EntityViewData periodEntityInfo = this.jtableParamService.getDataTimeEntity(formSchemeKey);
        op.addParamValue("MD_PERIOD", paramMap.get(periodEntityInfo.getDimensionName()));
        for (Map.Entry entry : paramMap.entrySet()) {
            op.addParamValue("P_" + (String)entry.getKey(), entry.getValue());
        }
        Wrapper wp = new Wrapper();
        wp.setOption(op);
        wp.setReportData(rd);
        try {
            SheetGridData sheetGridData = this.gridController.getPagedPrimarySheetData(wp);
            PagingInfo pagingInfo = sheetGridData.getPagingInfo();
            JSONObject ext = sheetGridData.getExt();
            Grid2Data grid2Data = new Grid2Data();
            grid2Data = GridDataTransform.gridDataToGrid2Data(sheetGridData.getGridData(), grid2Data);
            AnalyTableStyle analyTableStyle = new AnalyTableStyle();
            analyTableStyle.setGridData(grid2Data);
            analyTableStyle.setPagingInfo(pagingInfo);
            if (ext != null) {
                analyTableStyle.setAnalyExt(ext.toString());
            }
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new QuickGrid2DataSerialize(new DefaultGridOptions()));
            module.addSerializer(GridCellData.class, (JsonSerializer)new QuickGridCellDataSerialize());
            mapper.registerModule((Module)module);
            try {
                returnStr = mapper.writeValueAsString((Object)analyTableStyle);
            }
            catch (JsonProcessingException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        catch (Exception exception) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
            throw new QueryAnalFormException(new String[]{formKey + "\u5206\u6790\u8868\u67e5\u8be2\u5931\u8d25"});
        }
        try {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(returnStr.getBytes("UTF-8"));
            servletOutputStream.flush();
            servletOutputStream.close();
        }
        catch (Exception exception) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u62a5\u8868\u7ec4\u4ef6\u521d\u59cb\u5316\u6570\u636e\uff08\u6620\u5c04\u4fe1\u606f\uff09")
    @RequestMapping(value={"/init"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ResponseBody
    @NRContextBuild
    public JtableData init(@Valid @RequestBody JtableContext jtableContext) {
        JtableData jtableData = this.jtableContextService.getReportFormData(jtableContext);
        if (jtableContext.getDecimal() != null && !jtableContext.getDecimal().equals("")) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(jtableContext.getFormKey());
            FromGridData fromGridData = (FromGridData)jtableData.getStructure();
            HashMap<String, LinkSimpleData> linkMap = new HashMap<String, LinkSimpleData>();
            ArrayList<String> fieldKeys = new ArrayList<String>();
            List fieldDefines = new ArrayList();
            List<RegionSimpleData> regionSimpleDataList = fromGridData.getRegions();
            for (RegionSimpleData regionSimpleData : regionSimpleDataList) {
                List<LinkSimpleData> linkSimpleDataList = regionSimpleData.getLinks();
                for (LinkSimpleData linkSimpleData : linkSimpleDataList) {
                    if (linkSimpleData.getZbtype() != LinkType.LINK_TYPE_DECIMAL.getValue()) continue;
                    linkMap.put(linkSimpleData.getId(), linkSimpleData);
                    fieldKeys.add(linkSimpleData.getZbid());
                }
            }
            if (!fieldKeys.isEmpty()) {
                try {
                    fieldDefines = this.iDataDefinitionRuntimeController.queryFieldDefines(fieldKeys);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            Map fieldMap = fieldDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, Function.identity()));
            for (String link : linkMap.keySet()) {
                LinkSimpleData linkData = (LinkSimpleData)linkMap.get(link);
                FieldDefine fieldDefine = (FieldDefine)fieldMap.get(linkData.getZbid());
                if (fieldDefine == null || (fieldDefine.getMeasureUnit() == null || fieldDefine.getMeasureUnit().indexOf("NotDimession") < 0) && (fieldDefine.getMeasureUnit() != null || formDefine.getMeasureUnit() != null && formDefine.getMeasureUnit().indexOf("NotDimession") < 0)) continue;
                String formatStr = linkData.getFormat();
                StringBuilder stringBuilder = new StringBuilder(formatStr);
                String decimalStr = jtableContext.getDecimal();
                if (jtableContext.getDecimal().length() < 2) {
                    decimalStr = "0" + decimalStr;
                }
                stringBuilder.replace(15, 17, decimalStr);
                linkData.setFormat(stringBuilder.toString());
            }
        }
        Map<String, String> formulaValues = this.jtableResourceService.analysisFormulaByGridScript(jtableContext);
        jtableData.setFormulaValues(formulaValues);
        ((FromGridData)jtableData.getStructure()).setCalcDataLinks(new ArrayList<String>());
        return jtableData;
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u52a8\u4f5c\u5217\u8868")
    @RequestMapping(value={"/actions"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public String actions(@Valid @RequestBody JtableContext jtableContext) {
        return "";
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u5361\u7247\u5f55\u5165\u533a\u57df\u6620\u5c04\u4fe1\u606f\uff0c\u6839\u636e\u504f\u79fb\u91cf\u83b7\u53d6\u884c\u6570\u636e")
    @RequestMapping(value={"/cardInput"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public CardInputInfo cardInputInit(@Valid @RequestBody CardInputInit cardInputInit) {
        return this.jtableResourceService.cardInputInit(cardInputInit);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u62a5\u8868\u5f55\u5165\u6570\u636e ")
    @RequestMapping(value={"/actions/query"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public ReportDataSet query(@Valid @RequestBody ReportDataQueryInfo reportDataQueryInfo) {
        return this.jtableResourceService.queryReportData(reportDataQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u8868\u683c\u5f80\u671f\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/queryMultiPeriodData"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public MultiPeriodDataSet queryMultiPeriodData(@Valid @RequestBody JtableContext jtableContext) {
        MultiPeriodDataSet multiPeriodDataSet = this.jtableResourceService.queryMultiPeriodData(jtableContext);
        return multiPeriodDataSet;
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u4fdd\u5b58\u62a5\u8868\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/save"}, method={RequestMethod.POST})
    @JLoggable(value="\u4fdd\u5b58\u6574\u8868\u6570\u636e")
    @LevelAuthWrite
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public SaveResult save(@Valid @RequestBody ReportDataCommitSet reportDataCommitSet) {
        FormData formData;
        SaveResult saveResult = null;
        NpContext npContext = NpContextHolder.getContext();
        String ctxInfoKey = "IS_NEW_ATTACHMENT";
        IsNewAttachmentInfo isNewAttachmentInfo = new IsNewAttachmentInfo();
        isNewAttachmentInfo.setNewAttachment(true);
        npContext.getDefaultExtension().put(ctxInfoKey, (Serializable)isNewAttachmentInfo);
        try {
            JtableContext jtableContext = reportDataCommitSet.getContext();
            formData = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
            saveResult = FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType()) ? this.jtableResourceService.saveFmdmReportData(reportDataCommitSet) : this.jtableResourceService.saveReportData(reportDataCommitSet);
        }
        catch (Exception e) {
            if (e instanceof SaveDataException) {
                SaveDataException saveDataException = (SaveDataException)((Object)e);
                saveResult = saveDataException.getSaveResult();
            }
            throw e;
        }
        if (saveResult.getErrors().isEmpty()) {
            String saveCheckMessage;
            String saveCalculateMessage;
            JtableContext context = reportDataCommitSet.getContext();
            formData = this.jtableParamService.getReport(context.getFormKey(), context.getFormSchemeKey());
            if (FormType.FORM_TYPE_NEWFMDM.name().equals(formData.getFormType()) && reportDataCommitSet.isUnitAdd()) {
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
                Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
                DimensionValue dwDimensionValue = dimensionSet.get(dwEntity.getDimensionName());
                String unitCode = saveResult.getUnitCode();
                if (unitCode != "" && unitCode != null) {
                    dwDimensionValue.setValue(unitCode);
                }
            }
            if ("1".equals(saveCalculateMessage = this.taskOptionController.getValue(context.getTaskKey(), "AUTOCALCULAT_AFTER_SAVE"))) {
                saveResult.setCalculateReturnInfo(this.jtableResourceService.calculateFormBetween(context));
            }
            if ("1".equals(saveCheckMessage = this.taskOptionController.getValue(context.getTaskKey(), "AUTOCHECK_AFTER_SAVE"))) {
                saveResult.setCheckReturnInfo(this.jtableResourceService.checkForm(context));
            }
        }
        return saveResult;
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u6e05\u9664\u62a5\u8868\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/clear"}, method={RequestMethod.POST})
    @JLoggable(value="\u6e05\u9664\u6574\u8868\u6570\u636e")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public ReturnInfo clear(@Valid @RequestBody ReportDataQueryInfo reportDataQueryInfo) {
        String saveCalculateMessage;
        ReturnInfo clearReportData = this.jtableResourceService.clearReportData(reportDataQueryInfo);
        if ("success".equals(clearReportData.getMessage()) && "1".equals(saveCalculateMessage = this.taskOptionController.getValue(reportDataQueryInfo.getContext().getTaskKey(), "AUTOCALCULAT_AFTER_CLEAR"))) {
            clearReportData.setMessage(this.jtableResourceService.calculateFormBetween(reportDataQueryInfo.getContext()).getMessage());
        }
        return clearReportData;
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u533a\u57df\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/region/query"}, method={RequestMethod.POST})
    @LevelAuthRead
    @ResponseBody
    @NRContextBuild
    public RegionDataSet regionQuery(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableResourceService.queryRegionData(regionQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u533a\u57df\u5355\u6761\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/region/querysingle"}, method={RequestMethod.POST})
    @ResponseBody
    public RegionSingleDataSet regionQuerySingle(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableResourceService.queryRegionSingleData(regionQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u533a\u57df\u5f55\u5165\u6570\u636e\u603b\u884c\u6570")
    @RequestMapping(value={"/actions/region/datacount"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public RegionDataCount regionDataCount(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableResourceService.regionDataCount(regionQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u4fdd\u5b58\u533a\u57df\u5f55\u5165\u6570\u636e")
    @RequestMapping(value={"/actions/region/save"}, method={RequestMethod.POST})
    @JLoggable(value="\u4fdd\u5b58\u533a\u57df\u6570\u636e")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public SaveResult regionSave(@Valid @RequestBody RegionDataCommitSet regionDataCommitSet) {
        return this.jtableResourceService.saveRegionData(regionDataCommitSet);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u5355\u5143\u683c\u5220\u9009\u503c\u5217\u8868")
    @RequestMapping(value={"/actions/cellvalues"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public CellDataSet cellvalues(@Valid @RequestBody CellValueQueryInfo cellValueQueryInfo) {
        return this.jtableResourceService.getCellValues(cellValueQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u6d6e\u52a8\u884c\u5355\u5143\u683c\u5b9a\u4f4d")
    @RequestMapping(value={"/actions/floatdata/locate"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public PagerInfo floatdataLocate(@Valid @RequestBody RegionQueryInfo regionQueryInfo) {
        return this.jtableResourceService.floatDataLocate(regionQueryInfo);
    }

    @Deprecated
    @ApiOperation(value="\u5220\u9664\u5c01\u9762\u4ee3\u7801")
    @PostMapping(value={"/deleteFMDM"})
    @NRContextBuild
    public SaveResult deleteFMDM(@Valid @RequestBody JtableContext context) {
        SaveResult saveResult = null;
        try {
            saveResult = this.jtableResourceService.deleteFMDM(context);
        }
        catch (Exception e) {
            if (e instanceof SaveDataException) {
                SaveDataException saveDataException = (SaveDataException)((Object)e);
                saveResult = saveDataException.getSaveResult();
            }
            throw e;
        }
        return saveResult;
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u7c98\u8d34\u6570\u636e\u683c\u5f0f\u5316")
    @RequestMapping(value={"/actions/pastedataformat"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public PasteFormatReturnInfo pastedataformat(@Valid @RequestBody PasteFormatDataInfo pasteFormatDataInfo) {
        return this.jtableResourceService.pasteFormatData(pasteFormatDataInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u524d\u7aef\u516c\u5f0f\u811a\u672c")
    @RequestMapping(value={"/calculateformula"}, method={RequestMethod.GET})
    @NRContextBuild
    public void calculateformula(@ApiParam(name="formKey", value="\u62a5\u8868key", required=true) @RequestParam(value="formKey") String formKey, @ApiParam(name="formulaSchemeKey", value="\u516c\u5f0f\u65b9\u6848key", required=true) @RequestParam(value="formulaSchemeKey") String formulaSchemeKey, @ApiParam(name="taskKey", value="\u4efb\u52a1key\uff08\u5e9f\u5f03\uff09", required=false) @RequestParam(value="taskKey", required=false) String taskKey, @RequestParam(value="formSchemeKey", required=true) String formSchemeKey, @ModelAttribute NRContext nrContext, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!this.checkFormulaCache(formulaSchemeKey, taskKey, request, response)) {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.OK.value());
            String js = this.jtableParamService.getCalculateFormulaJs(formulaSchemeKey, formKey);
            HashMap<String, String> formulaMeanings = this.jtableParamService.getFormulaMeanings(taskKey, formSchemeKey, formulaSchemeKey, formKey);
            ReturnInfo returnInfo = new ReturnInfo();
            returnInfo.setMessage(js);
            returnInfo.setFormulaMeansMap(formulaMeanings);
            try {
                String jsonReturn = mapper.writeValueAsString((Object)returnInfo);
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream output = response.getOutputStream();
                output.write(jsonReturn.getBytes("UTF-8"));
                output.flush();
                output.close();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u524d\u7aef\u516c\u5f0f\u9002\u7528\u6761\u4ef6\u811a\u672c")
    @RequestMapping(value={"/formulaConditions"}, method={RequestMethod.POST})
    @NRContextBuild
    public void formulaConditions(@RequestBody JtableContext jtableContext, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        FormulaConditionFile fcFile = this.jtableParamService.getFormulaConditionJs(jtableContext);
        try {
            String jsonReturn = mapper.writeValueAsString((Object)fcFile);
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream output = response.getOutputStream();
            output.write(jsonReturn.getBytes("UTF-8"));
            output.flush();
            output.close();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u524d\u7aef\u6761\u4ef6\u6837\u5f0f\u811a\u672c")
    @RequestMapping(value={"/styleFormula"}, method={RequestMethod.GET})
    @NRContextBuild
    public void styleformula(@ApiParam(name="formKey", value="\u62a5\u8868key", required=true) @RequestParam(value="formKey") String formKey, @ApiParam(name="taskKey", value="\u4efb\u52a1key", required=true) @RequestParam(value="taskKey") String taskKey, @ModelAttribute NRContext nrContext, HttpServletRequest request, HttpServletResponse response) {
        if (!this.checkStyleFormulaCache(formKey, request, response)) {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpStatus.OK.value());
            CStyleFile cStyleFile = this.jtableParamService.getStyleFormulaJs(taskKey, formKey);
            try {
                String jsonReturn = mapper.writeValueAsString((Object)cStyleFile);
                response.setContentType("application/json;charset=UTF-8");
                ServletOutputStream output = response.getOutputStream();
                output.write(jsonReturn.getBytes("UTF-8"));
                output.flush();
                output.close();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u6570\u636e\u8868\u548c\u6307\u6807")
    @RequestMapping(value={"/formfields"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public FormTables formfields(@Valid @RequestBody FieldQueryInfo fieldQueryInfo) {
        return this.jtableParamService.formfields(fieldQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u5355\u8868\u5ba1\u6838")
    @RequestMapping(value={"/actions/check"}, method={RequestMethod.POST})
    @JLoggable(value="\u5355\u8868\u5ba1\u6838")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public FormulaCheckReturnInfo check(@Valid @RequestBody JtableContext jtableContext) {
        return this.jtableResourceService.checkForm(jtableContext);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u4fdd\u5b58\u540e\u5355\u8868\u5ba1\u6838\uff0c\u9700\u8981\u5ba1\u6838\u8868\u95f4\u516c\u5f0f")
    @RequestMapping(value={"/actions/save-check"}, method={RequestMethod.POST})
    @JLoggable(value="\u4fdd\u5b58\u540e\u5355\u8868\u5ba1\u6838")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public FormulaCheckReturnInfo saveCheck(@Valid @RequestBody JtableContext jtableContext) {
        return this.jtableResourceService.checkFormBetween(jtableContext);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u5355\u8868\u8fd0\u7b97")
    @RequestMapping(value={"/actions/calculate"}, method={RequestMethod.POST})
    @JLoggable(value="\u5355\u8868\u8fd0\u7b97")
    @ActionBeforeAfterExe(value="calc")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public ReturnInfo calculate(@Valid @RequestBody JtableContext jtableContext) {
        return this.jtableResourceService.calculateForm(jtableContext);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u4fdd\u5b58\u540e\u5355\u8868\u8fd0\u7b97\uff0c\u9700\u8981\u8ba1\u7b97\u8868\u95f4\u516c\u5f0f")
    @RequestMapping(value={"/actions/save-calculate"}, method={RequestMethod.POST})
    @JLoggable(value="\u4fdd\u5b58\u540e\u5355\u8868\u8fd0\u7b97")
    @JUniformity
    @ResponseBody
    @NRContextBuild
    public ReturnInfo saveCalculate(@Valid @RequestBody JtableContext jtableContext) {
        return this.jtableResourceService.calculateFormBetween(jtableContext);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u67e5\u8be2\u516c\u5f0f\u5217\u8868")
    @RequestMapping(value={"/formulas"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public List<FormulaData> formulaList(@Valid @RequestBody FormulaQueryInfo formulaQueryInfo) {
        return this.jtableParamService.getFormulaList(formulaQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u67e5\u8be2\u516c\u5f0f\u5217\u8868\u957f\u5ea6")
    @RequestMapping(value={"/formulasize"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public HashMap<String, Integer> formulaListSize(@Valid @RequestBody FormulaQueryInfo formulaQueryInfo) {
        return this.jtableParamService.getFormulaListSize(formulaQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u89c6\u56fe\u6570\u636e")
    @RequestMapping(value={"/entitydatas"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public EntityReturnInfo queryEntityData(@Valid @RequestBody EntityQueryByViewInfo entityQueryInfo) {
        boolean isSummaryEntry;
        EntityReturnInfo returnInfo = new EntityReturnInfo();
        if (StringUtils.isEmpty((String)entityQueryInfo.getEntityViewKey())) {
            EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(entityQueryInfo.getContext().getFormSchemeKey());
            entityQueryByKeyInfo.setEntityViewKey(dwEntity.getKey());
            entityQueryByKeyInfo.setEntityKey(entityQueryInfo.getSearch());
            EntityByKeyReturnInfo entityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
            returnInfo.setMessage(entityDataByKey.getMessage());
            returnInfo.setCells(entityDataByKey.getCells());
            returnInfo.getEntitys().add(entityDataByKey.getEntity());
            return returnInfo;
        }
        if (entityQueryInfo.getDataLinkKey() != null) {
            return this.jtableEntityService.queryEntityData(entityQueryInfo);
        }
        Map<String, Object> variableMap = entityQueryInfo.getContext().getVariableMap();
        boolean bl = isSummaryEntry = variableMap != null && variableMap.containsKey("batchGatherSchemeCode");
        if (!isSummaryEntry && this.iFormSchemeService.existCurrencyAttributes(entityQueryInfo.getContext().getFormSchemeKey()).booleanValue() && entityQueryInfo.getEntityViewKey().contains("MD_CURRENCY")) {
            EntityReturnInfo entityReturnInfo = this.jtableEntityService.queryDimEntityData(entityQueryInfo);
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(entityQueryInfo.getContext().getDimensionSet());
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(entityQueryInfo.getContext().getFormSchemeKey());
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(entityQueryInfo.getContext().getFormSchemeKey());
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(formScheme.getDw());
            ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
            EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(entityQueryInfo.getContext().getFormSchemeKey());
            executorContext.setPeriodView(dataTimeEntity.getKey());
            try {
                IDataEntity iEntityTable = this.dataEntityService.getIEntityTable(entityView, executorContext, dimensionValueSet, entityQueryInfo.getContext().getFormSchemeKey());
                IDataEntityRow entityDataByKey = iEntityTable.findByEntityKey(String.valueOf(dimensionValueSet.getValue(targetEntityInfo.getDimensionName())));
                if (entityDataByKey.getRowList().size() > 0 && entityDataByKey.getRowList().get(0) != null && ((IEntityRow)entityDataByKey.getRowList().get(0)).getValue("CURRENCYID") != null) {
                    AbstractData abstractData = ((IEntityRow)entityDataByKey.getRowList().get(0)).getValue("CURRENCYID");
                    List<EntityData> entityDataList = entityReturnInfo.getEntitys();
                    for (EntityData entityData : entityDataList) {
                        if (!entityData.getCode().equals(abstractData.getAsString())) continue;
                        entityData.setBaseCurrency(true);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            return entityReturnInfo;
        }
        return this.jtableEntityService.queryDimEntityData(entityQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u89c6\u56fe\u6570\u636e")
    @RequestMapping(value={"/entitydatasByCode"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public EntityReturnInfo queryEntityDataByCode(@Valid @RequestBody EntityQueryByCodeInfo entityQueryInfo) {
        String tableCode = entityQueryInfo.getTableCode();
        TableData queryTableDefineByCode = this.jtableParamService.queryTableDefineByCode(tableCode);
        FieldData fieldByCodeInTable = this.jtableParamService.getFieldByCodeInTable(entityQueryInfo.getFieldCode(), queryTableDefineByCode.getTableKey());
        String fieldKey = fieldByCodeInTable.getFieldKey();
        HashSet<String> captionFields = new HashSet<String>();
        captionFields.add(fieldKey);
        entityQueryInfo.setCaptionFields(captionFields);
        EntityReturnInfo returnInfo = new EntityReturnInfo();
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setContext(entityQueryInfo.getContext());
        entityQueryByKeyInfo.setEntityViewKey(entityQueryInfo.getEntityViewKey());
        entityQueryByKeyInfo.setEntityKey(entityQueryInfo.getSearch());
        entityQueryByKeyInfo.setCaptionFields(captionFields);
        EntityByKeyReturnInfo queryEntityDataByKey = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
        if (queryEntityDataByKey.getEntity() != null) {
            ArrayList<EntityData> entitys = new ArrayList<EntityData>();
            entitys.add(queryEntityDataByKey.getEntity());
            returnInfo.setEntitys(entitys);
            return returnInfo;
        }
        return this.jtableEntityService.queryEntityData(entityQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u89c6\u56fe\u6570\u636e")
    @RequestMapping(value={"/entityDataByKeys"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public EntityByKeysReturnInfo queryEntityDatasByKeys(@Valid @RequestBody EntityQueryByKeysInfo entityQueryByKeysInfo) {
        return this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u89c6\u56fe\u6570\u636e")
    @RequestMapping(value={"/entityWayByCode"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public EntityByKeyReturnInfo queryEntityWay(@Valid @RequestBody EntityQueryByViewInfo entityQueryInfo) {
        EntityQueryByKeyInfo entityQueryByKeyInfo = new EntityQueryByKeyInfo();
        entityQueryByKeyInfo.setEntityKey(entityQueryInfo.getSearch());
        entityQueryByKeyInfo.setEntityViewKey(entityQueryInfo.getEntityViewKey());
        return this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u83b7\u53d6\u91cf\u7eb2\u6570\u636e")
    @RequestMapping(value={"/measureGroup"}, method={RequestMethod.POST})
    @RequiresPermissions(value={"nr:dataentry:dataentry"})
    @ResponseBody
    @NRContextBuild
    public List<MeasureData> queryMeasures(@Valid @RequestBody MeasureQueryInfo measureQueryInfo) {
        return this.jtableEntityService.queryMeasureData(measureQueryInfo);
    }

    @ApiOperation(value="\u5355\u5143\u683c\u83b7\u53d6\u9644\u4ef6\u6570\u636e")
    @RequestMapping(value={"/query"}, method={RequestMethod.POST})
    @NRContextBuild
    public List<FileInfo> getFileInfos(@Valid @RequestBody JtableContext jtableContext) {
        return this.fileService.getJableFiles(jtableContext);
    }

    @ApiOperation(value="\u5355\u5143\u683c\u83b7\u53d6\u5bc6\u7ea7\u9009\u9879")
    @RequestMapping(value={"/querySecret"}, method={RequestMethod.POST})
    @NRContextBuild
    public List<SecretLevelItem> getFileSecretItems(@Valid @RequestBody JtableContext jtableContext) {
        return this.fileService.getFileSecretItems(jtableContext);
    }

    @PostMapping(value={"/checkFileAndUpload"})
    public FilesUploadReturnInfo checkUploadFileInfo(@RequestParam(value="file") MultipartFile[] files, HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        String paramJson = request.getParameter("param");
        FileUploadParamInfo fileUploadParamInfo = null;
        if (StringUtils.isNotEmpty((String)paramJson)) {
            try {
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                fileUploadParamInfo = (FileUploadParamInfo)mapper.readValue(paramJson, FileUploadParamInfo.class);
            }
            catch (IOException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        } else {
            fileUploadParamInfo = new FileUploadParamInfo();
        }
        return this.checkUploadFileService.checkUploadFileInfo(files, fileUploadParamInfo.getSceneList(), fileUploadParamInfo.getAppName());
    }

    @PostMapping(value={"/getAllUploadSysConfig"})
    public Map<String, Object> getAllCheckInfo() {
        return this.checkUploadFileService.getAllCheckInfo();
    }

    @ApiOperation(value="\u5355\u5143\u683c\u9644\u4ef6\u4e0b\u8f7d(\u4e0b\u8f7d\u9644\u4ef6\u6a21\u677f)")
    @RequestMapping(value={"/actions/downloadfiles"}, method={RequestMethod.POST})
    @JLoggable(value="\u5355\u5143\u683c\u9644\u4ef6\u4e0b\u8f7d")
    @NRContextBuild
    public void downloadJtableFiles(@Valid @RequestBody LinkFileOptionInfo linkFileOptionInfo, HttpServletResponse response) {
        this.fileService.downloadJtableFiles(linkFileOptionInfo, response);
    }

    @Deprecated
    @ApiOperation(value="\u5355\u5143\u683c\u9644\u4ef6\u4fee\u6539\u5bc6\u7ea7")
    @RequestMapping(value={"/actions/updatefilessecret"}, method={RequestMethod.POST})
    @JLoggable(value="\u5355\u5143\u683c\u9644\u4ef6\u4fee\u6539\u5bc6\u7ea7")
    @JUniformity
    @NRContextBuild
    public ReturnInfo updateJtableFilesSecret(@Valid @RequestBody LinkFileOptionInfo linkFileOptionInfo) {
        return this.fileService.updateJtableFilesSecret(linkFileOptionInfo);
    }

    @ApiOperation(value="\u5355\u5143\u683c\u9644\u4ef6\u79fb\u9664(\u56fe\u7247\u6307\u6807\u5220\u9664)")
    @RequestMapping(value={"/actions/removefiles"}, method={RequestMethod.POST})
    @JLoggable(value="\u5355\u5143\u683c\u9644\u4ef6\u79fb\u9664")
    @JUniformity
    @NRContextBuild
    public ReturnInfo removeJtableFiles(@Valid @RequestBody LinkFileOptionInfo linkFileOptionInfo) {
        return this.fileService.removeJtableFiles(linkFileOptionInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u89e3\u6790\u516c\u5f0f\uff08\u7ed9\u524d\u53f0\u516c\u5f0fjs\u63d0\u4f9b\uff09")
    @RequestMapping(value={"/analysisFormula"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public Map<String, String> analysisFormula(@Valid @RequestBody AnalysisFormulaInfo analysisFormulaInfo) {
        return this.jtableResourceService.analysisFormula(analysisFormulaInfo);
    }

    @ApiOperation(value="\u9644\u4ef6\u9884\u89c8")
    @GetMapping(value={"/preview/{taskKey}/{fileKey}"})
    @NRContextBuild
    public void previewFile(@PathVariable String taskKey, @PathVariable String fileKey, @ModelAttribute NRContext nrContext, HttpServletResponse response) {
        this.jtableResourceService.previewFileByTaskKey(taskKey, fileKey, response);
    }

    @ApiOperation(value="\u6839\u636e\u6570\u636e\u65b9\u6848code\u9644\u4ef6\u9884\u89c8")
    @GetMapping(value={"/previewByDSCode/{dataSchemeCode}/{fileKey}"})
    @NRContextBuild
    public void previewFileByDataSchemeCode(@PathVariable String dataSchemeCode, @PathVariable String fileKey, @ModelAttribute NRContext nrContext, HttpServletResponse response) {
        this.jtableResourceService.previewFileByDataSchemeCode(dataSchemeCode, fileKey, response);
    }

    @ApiOperation(value="\u83b7\u53d6\u4e00\u4e2a\u5355\u5143\u683c\u4e0b\u7684\u56fe\u7247\u4fe1\u606f(\u56fe\u7247\u6307\u6807\u83b7\u53d6\u7f29\u7565\u56fe)")
    @PostMapping(value={"/query/img"})
    @NRContextBuild
    public Map<String, List<byte[]>> queryImgDatas(@Valid @RequestBody LinkImgOptionInfo linkImgOptionInfo) {
        return this.fileService.queryImgDatas(linkImgOptionInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u66f4\u65b0\u6570\u636e\u72b6\u6001")
    @RequestMapping(value={"/actions/update-data-status"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    @Deprecated
    public String updateDataStatus(@Valid @RequestBody JtableContext jtableContext) {
        return "";
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a\u6279\u91cf\u66f4\u65b0\u6570\u636e\u72b6\u6001")
    @RequestMapping(value={"/actions/update-all-data-status"}, method={RequestMethod.GET})
    @ResponseBody
    @NRContextBuild
    @Deprecated
    public String updateAllDataStatus(@ModelAttribute NRContext nrContext) throws Exception {
        return "";
    }

    @ApiOperation(value="\u83b7\u53d6\u94fe\u63a5")
    @PostMapping(value={"/linkDatas"})
    @NRContextBuild
    public Map<String, LinkData> getLinkData(@Valid @RequestBody List<String> linkKeys) {
        HashMap<String, LinkData> linkDatas = new HashMap<String, LinkData>();
        if (linkKeys != null && linkKeys.size() > 0) {
            for (String key : linkKeys) {
                LinkData link = this.jtableParamService.getLink(key);
                if (link == null) continue;
                linkDatas.put(link.getKey(), link);
            }
        }
        return linkDatas;
    }

    private boolean checkCache(String checkKey, HttpServletRequest request, HttpServletResponse response) {
        if (null != checkKey && !"".equals(checkKey)) {
            checkKey = HtmlUtils.cleanHeaderValue((String)checkKey);
            String ifModifiedSince = request.getHeader("If-None-Match");
            String language = request.getHeader("language");
            String updateDateString = this.jtableParamService.getDeployUpdate(checkKey);
            if (null == updateDateString || "".equals(updateDateString)) {
                return false;
            }
            updateDateString = updateDateString + "|" + language;
            response.setHeader("Cache-Control", "max-age=5");
            response.setHeader("Etag", updateDateString);
            if (updateDateString.equals(ifModifiedSince)) {
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                return true;
            }
        }
        return false;
    }

    private boolean checkFormulaCache(String formSchemeKey, String taskKey, HttpServletRequest request, HttpServletResponse response) {
        boolean keyIsNull;
        boolean bl = keyIsNull = null != formSchemeKey && !"".equals(formSchemeKey) && null != taskKey && !"".equals(taskKey);
        if (keyIsNull) {
            formSchemeKey = HtmlUtils.cleanHeaderValue((String)formSchemeKey);
            taskKey = HtmlUtils.cleanHeaderValue((String)taskKey);
            String formFxShow = this.taskOptionController.getValue(taskKey, "FORM_FX_SHOW");
            String modifiedStr = request.getHeader("If-None-Match");
            String language = request.getHeader("language");
            String updateStr = this.jtableParamService.getDeployUpdate(formSchemeKey);
            if (null == updateStr || "".equals(updateStr)) {
                return false;
            }
            updateStr = updateStr + "|" + language + "|" + formFxShow;
            response.setHeader("Cache-Control", "max-age=5");
            response.setHeader("Etag", updateStr);
            if (updateStr.equals(modifiedStr)) {
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                return true;
            }
        }
        return false;
    }

    private boolean checkStyleFormulaCache(String formKey, HttpServletRequest request, HttpServletResponse response) {
        boolean keyIsNull;
        if (StringUtils.isNotEmpty((String)formKey)) {
            formKey = HtmlUtils.cleanHeaderValue((String)formKey);
        }
        List formSchemeDefineList = new ArrayList();
        try {
            formSchemeDefineList = this.runTimeViewController.queryFormSchemeByForm(formKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (formSchemeDefineList.size() == 0) {
            return true;
        }
        FormSchemeDefine formScheme = (FormSchemeDefine)formSchemeDefineList.get(0);
        boolean bl = keyIsNull = null != formScheme.getKey() && !"".equals(formScheme.getKey()) && null != formScheme.getTaskKey() && !"".equals(formScheme.getTaskKey());
        if (keyIsNull) {
            String formFxShow = this.taskOptionController.getValue(formScheme.getTaskKey(), "FORM_FX_SHOW");
            String modifiedStr = request.getHeader("If-None-Match");
            String language = request.getHeader("language");
            String updateStr = this.jtableParamService.getDeployUpdate(formScheme.getTaskKey());
            if (null == updateStr || "".equals(updateStr)) {
                return false;
            }
            updateStr = updateStr + "|" + language + "|" + formFxShow;
            response.setHeader("Cache-Control", "max-age=5");
            response.setHeader("Etag", updateStr);
            if (updateStr.equals(modifiedStr)) {
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
                return true;
            }
        }
        return false;
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u67e5\u8be2\u516c\u5f0f")
    @RequestMapping(value={"/curFormula"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public String queryFormula(@Valid @RequestBody FormulaQueryInfo formulaQueryInfo) {
        return this.jtableParamService.getCurFormula(formulaQueryInfo);
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u95ee\u5377\u5361\u7247\u5f55\u5165\u8868\u6837")
    @RequestMapping(value={"/card-survery-style"}, method={RequestMethod.GET})
    @NRContextBuild
    public void initSurveryCardStyle(@RequestParam(value="formKey") String formKey, @RequestParam(value="taskKey", required=true) String taskKey, @RequestParam(value="formulaSchemeKey", required=true) String formulaSchemeKey, @RequestParam(value="regionKey", required=true) String regionKey, @RequestParam(value="param", required=true) String param, @ModelAttribute NRContext nrContext, HttpServletResponse response) {
        try {
            param = URLDecoder.decode(param, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        String returnStr = this.jtableParamService.getSurveyCardStyle(formKey, taskKey, formulaSchemeKey, regionKey, param);
        try {
            response.setContentType("application/json;charset=UTF-8");
            ServletOutputStream output = response.getOutputStream();
            output.write(returnStr.getBytes("UTF-8"));
            output.flush();
            output.close();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u83b7\u53d6\u95ee\u5377\u5361\u7247\u5f55\u5165\u53c2\u6570")
    @RequestMapping(value={"/card-survery-init"}, method={RequestMethod.GET})
    @NRContextBuild
    public JtableData initSurveryCardData(@RequestParam(value="formKey") String formKey, @RequestParam(value="taskKey", required=true) String taskKey, @RequestParam(value="formulaSchemeKey", required=true) String formulaSchemeKey, @RequestParam(value="regionKey", required=true) String regionKey, @RequestParam(value="param", required=true) String param, @ModelAttribute NRContext nrContext) {
        try {
            param = URLDecoder.decode(param, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        JtableData surveryCardData = this.jtableContextService.initSurveryCardData(formKey, taskKey, formulaSchemeKey, regionKey, param);
        return surveryCardData;
    }

    @ApiOperation(value="\u62a5\u8868\u7ec4\u4ef6\uff1a \u62a5\u8868\u8bfb\u6743\u9650")
    @RequestMapping(value={"/crossTaskLocateInfo"}, method={RequestMethod.POST})
    @ResponseBody
    @NRContextBuild
    public CrossTaskLocateInfo getCrossTaskLocateInfo(@Valid @RequestBody JtableContext context) throws Exception {
        return this.jtableLocateService.getCrossTaskLocateInfo(context);
    }
}

