/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.quickreport.model.QuickReport
 *  com.jiuqi.bi.quickreport.model.WorksheetModel
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.common.itree.ITree
 *  com.jiuqi.nr.definition.common.DesignFormulaDTO
 *  com.jiuqi.nr.definition.common.FormulaConditionDTO
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.dao.DesignFormulaDefineDao
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.web.ResourceTreeController
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.quickreport.service.QuickReportModelService
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.designer.web.rest;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.model.WorksheetModel;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.definition.common.DesignFormulaDTO;
import com.jiuqi.nr.definition.common.FormulaConditionDTO;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.internal.controller.FormulaDesignTimeController;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.dao.DesignFormulaDefineDao;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.Grid2DataSeralizeToGeGe;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.formcopy.IDesignFormCopyService;
import com.jiuqi.nr.designer.helper.SaveFormWithFormulaHelper;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.planpublish.service.TaskPlanPublishExternalService;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.service.impl.QuoteFormByOtherTaskServiceImpl;
import com.jiuqi.nr.designer.util.JQExceptionWrapper;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSaveWithFormulaProgressVO;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.facade.FormulaUpdateRecordVO;
import com.jiuqi.nr.designer.web.rest.resultBean.SaveFormResult;
import com.jiuqi.nr.designer.web.rest.vo.ChangeFormOrder;
import com.jiuqi.nr.designer.web.rest.vo.FormSaveObject;
import com.jiuqi.nr.designer.web.rest.vo.MoveFormVO;
import com.jiuqi.nr.designer.web.rest.vo.OrderVO;
import com.jiuqi.nr.designer.web.rest.vo.RequestAllFormStyle;
import com.jiuqi.nr.designer.web.rest.vo.TaskSchemeGroupTreeNode;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.web.ResourceTreeController;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.quickreport.service.QuickReportModelService;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class FormController {
    private static final Logger log = LoggerFactory.getLogger(FormController.class);
    @Autowired
    private TaskPlanPublishExternalService taskPlanPublishExternalService;
    @Autowired
    private StepSaveService stepSaveService;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private IDesignTimeViewController designTimeViewService;
    @Autowired
    private TaskDesignerService taskDesignerService;
    @Autowired
    private QuoteFormByOtherTaskServiceImpl quoteFormByOtherTaskServiceImpl;
    @Autowired
    private LanguageTypeService languageService;
    @Autowired
    private IDesignFormCopyService iDesignFormCopyService;
    @Autowired
    private ResourceTreeController resuorceTreeController;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private FormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private SaveFormWithFormulaHelper saveFormWithFormulaHelper;
    @Autowired
    private DesignFormulaDefineDao formulaDao;
    @Autowired
    private QuickReportModelService quickReportModelService;

    @ApiOperation(value="\u4fdd\u5b58\u65b0\u5efa\u7684\u62a5\u8868")
    @PostMapping(value={"stepSaveFormObject"})
    public SaveFormResult stepSaveFormObject(@RequestBody FormSaveObject formSaveObject) throws JQException {
        String logTitle = "\u64cd\u4f5c\u62a5\u8868";
        String formCode = "\u672a\u77e5";
        String formTitle = "\u672a\u77e5";
        FormObj formObjFin = formSaveObject.getFormData();
        boolean taskCanEdit = false;
        try {
            formCode = formObjFin.getCode();
            formTitle = formObjFin.getTitle();
            logTitle = formObjFin.isIsDeleted() ? "\u5220\u9664\u62a5\u8868" : (formObjFin.isIsNew() ? "\u65b0\u589e\u62a5\u8868" : "\u4fee\u6539\u62a5\u8868");
            try {
                taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(formObjFin.getTaskId());
            }
            catch (Exception e) {
                throw JQExceptionWrapper.wrapper(e);
            }
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            this.stepSaveService.stepSaveForm(formObjFin);
            SaveFormResult saveFormResult = new SaveFormResult();
            saveFormResult.setOwnerLevelAndId(this.serveCodeService.getServeCode());
            List regionDefines = this.nrDesignTimeController.getAllRegionsInForm(formObjFin.getID());
            if (regionDefines != null && regionDefines.size() == 1) {
                saveFormResult.setDefaultSimpleRegionKey(((DesignDataRegionDefine)regionDefines.get(0)).getKey());
            }
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            return saveFormResult;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle + "|" + e.getMessage(), NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_010, (Throwable)e);
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u8bbe\u8ba1\u4fdd\u5b58\u6309\u94ae-\u4fdd\u5b58\u62a5\u8868\u548c\u516c\u5f0f")
    @PostMapping(value={"stepSaveFormAndFormulas"})
    public String stepSaveAndFormulas(@RequestBody FormSaveObject formSaveObject) throws JQException {
        String logTitle = "\u4fee\u6539\u62a5\u8868";
        String formTitle = "\u672a\u77e5";
        String formCode = "\u672a\u77e5";
        try {
            Map<Integer, Grid2Data> gridMap = formSaveObject.getGridMap();
            String activiSchemeId = formSaveObject.getActivedSchemeId();
            FormObj formObjFin = formSaveObject.getFormData();
            formTitle = formObjFin.getTitle();
            formCode = formObjFin.getCode();
            List<Object> formulaUpdateRecordVOS = new ArrayList();
            boolean taskCanEdit = this.taskPlanPublishExternalService.taskCanEdit(formObjFin.getTaskId());
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            if (formSaveObject.isIfUpdateFormula()) {
                String formKey = formObjFin.getID();
                String progressId = formSaveObject.getProgressId();
                ProgressItem progressItem = new ProgressItem();
                progressItem.setProgressId(progressId);
                progressItem.addStepTitle("\u89e3\u6790\u516c\u5f0f");
                progressItem.addStepTitle("\u4fdd\u5b58\u62a5\u8868");
                progressItem.addStepTitle("\u66f4\u65b0\u516c\u5f0f");
                List allFormulaScheme = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(activiSchemeId);
                List<String> formulaSchemeKeys = allFormulaScheme.stream().filter(o -> o.getFormulaSchemeType() == FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT).map(IBaseMetaItem::getKey).collect(Collectors.toList());
                try {
                    Map<String, List<DesignFormulaDTO>> schemeFormulaDTOListMap = this.saveFormWithFormulaHelper.saveFormWithFormula(formKey, formulaSchemeKeys, formSaveObject, formObjFin, progressItem);
                    progressItem.setCurrentProgess(33);
                    progressItem.setMessage("\u6b63\u5728\u89e3\u6790\u516c\u5f0f\uff0c\u5c06\u94fe\u63a5\u683c\u5f0f\u8f6c\u6362\u6210\u5750\u6807\u683c\u5f0f......");
                    this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
                    for (Map.Entry<String, List<DesignFormulaDTO>> entry : schemeFormulaDTOListMap.entrySet()) {
                        this.formulaDesignTimeController.fillExpression(entry.getKey(), entry.getValue());
                    }
                    ArrayList<DesignFormulaDefine> needUpdateFormula = new ArrayList<DesignFormulaDefine>();
                    ArrayList<DesignFormulaDTO> designFormulaDTOList = new ArrayList<DesignFormulaDTO>();
                    ArrayList<FormulaConditionDTO> formulaConditions = new ArrayList<FormulaConditionDTO>();
                    for (List<DesignFormulaDTO> formulaDTOS : schemeFormulaDTOListMap.values()) {
                        for (DesignFormulaDTO formulaDTO : formulaDTOS) {
                            if (formulaDTO.isSuccess()) {
                                needUpdateFormula.add(formulaDTO.getDesignFormulaDefine());
                            }
                            formulaConditions.addAll(formulaDTO.getConditions());
                        }
                        designFormulaDTOList.addAll(formulaDTOS);
                    }
                    progressItem.setCurrentProgess(66);
                    progressItem.setMessage("\u516c\u5f0f\u89e3\u6790\u5b8c\u6210\uff0c\u6b63\u5728\u4fdd\u5b58\u516c\u5f0f......");
                    this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
                    if (needUpdateFormula.size() > 0) {
                        this.formulaDao.update((Object[])needUpdateFormula.toArray(new DesignFormulaDefine[0]));
                    }
                    this.updateFormulaConditions(formulaConditions);
                    formulaUpdateRecordVOS = this.saveFormWithFormulaHelper.getFormulaUpdateRecordList(designFormulaDTOList, activiSchemeId);
                    progressItem.setCurrentProgess(100);
                    this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
                    progressItem.nextStep();
                    progressItem.setCurrentProgess(100);
                    progressItem.setMessage("\u4fdd\u5b58\u62a5\u8868\u5e76\u66f4\u65b0\u516c\u5f0f\u5df2\u5b8c\u6210\uff0c\u70b9\u51fb\"\u67e5\u770b\u8be6\u60c5\"\u53ef\u67e5\u770b\u516c\u5f0f\u66f4\u65b0\u8bb0\u5f55");
                    progressItem.setFinished(true);
                    this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                    progressItem.setMessage("\u62a5\u8868\u4fdd\u5b58\u5e76\u6279\u91cf\u66f4\u65b0\u516c\u5f0f\u5185\u5bb9\u5f02\u5e38");
                    progressItem.setFailed(true);
                    progressItem.setFinished(true);
                    this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
                }
            } else {
                this.stepSaveService.stepSaveForm(formObjFin);
                this.stepSaveService.syncGridData(formObjFin.getID(), formObjFin.getLanguageType(), formSaveObject.getSyncActions());
                if (null != formSaveObject.getFormulaObjs() && formSaveObject.getFormulaObjs().size() != 0) {
                    this.taskDesignerService.saveFormulas((FormulaObj[])formSaveObject.getFormulaObjs().stream().toArray(FormulaObj[]::new));
                }
            }
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_INFO);
            String returnValue = "{formId:" + formObjFin.getID() + ",taskId:" + formObjFin.getTaskId() + ",ownGroupId:" + formObjFin.getOwnGroupId() + ",activedSchemeId:" + activiSchemeId + ",NextLanguageType:" + formObjFin.getNextLanguageType() + "}";
            String res = this.stepSaveService.getFormPropWhenSave(returnValue);
            res = "{\"formulaUpdateRecordList\":" + JacksonUtils.objectToJson(formulaUpdateRecordVOS) + "," + res.substring(1);
            return res;
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_010, e.getMessage());
        }
    }

    @ApiOperation(value="\u4efb\u52a1\u8bbe\u8ba1\u4fdd\u5b58\u6309\u94ae-\u4fdd\u5b58\u62a5\u8868\u548c\u516c\u5f0f")
    @PostMapping(value={"stepSaveFormAndFormulas_rsa"})
    public String stepSaveAndFormulasRsa(@RequestBody @SFDecrypt FormSaveObject formSaveObject) throws JQException {
        return this.stepSaveAndFormulas(formSaveObject);
    }

    private void updateFormulaConditions(List<FormulaConditionDTO> formulaConditions) {
        List updateList = formulaConditions.stream().filter(FormulaConditionDTO::isNeedUpdate).map(FormulaConditionDTO::getFormulaCondition).collect(Collectors.toList());
        this.formulaDesignTimeController.updateFormulaConditions(updateList);
    }

    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u540c\u65f6\u66f4\u65b0\u516c\u5f0f\u5185\u5bb9\u8fdb\u5ea6\u67e5\u8be2")
    @GetMapping(value={"get-save-form-formulas-progress/{id}"})
    public FormSaveWithFormulaProgressVO getProgress(@PathVariable String id) {
        ProgressItem progress = this.progressCacheService.getProgress(id);
        return new FormSaveWithFormulaProgressVO(progress);
    }

    @ApiOperation(value="\u5bfc\u51fa\u62a5\u8868\u4fdd\u5b58\u65f6\u7684\u516c\u5f0f\u66f4\u65b0\u8bb0\u5f55")
    @PostMapping(value={"export-formula-update-record"})
    public void exportFormulaUpdateRecord(@RequestBody List<FormulaUpdateRecordVO> record, HttpServletResponse response) throws IOException, JQException {
        ServletOutputStream outputStream = null;
        String fileName = "\u516c\u5f0f\u66f4\u65b0\u8bb0\u5f55";
        fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
        fileName = "attachment;filename=" + fileName + "." + XSSFWorkbookType.XLSX.getExtension();
        response.setHeader("Content-disposition", fileName);
        response.setContentType("application/octet-stream");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(50);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setLocked(true);
        String[] label = new String[]{"\u516c\u5f0f\u65b9\u6848", "\u62a5\u8868\u540d\u79f0", "\u516c\u5f0f\u7f16\u53f7", "\u66f4\u65b0\u524d", "\u66f4\u65b0\u540e", "\u66f4\u65b0\u7ed3\u679c"};
        int columnNum = label.length;
        XSSFRow row1 = sheet.createRow(0);
        for (int n = 0; n < columnNum; ++n) {
            XSSFCell cell1 = row1.createCell(n);
            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(label[n]);
            cell1.setCellStyle(style);
        }
        XSSFCellStyle failStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(IndexedColors.RED.getIndex());
        failStyle.setFont(font);
        if (!record.isEmpty()) {
            for (int i = 0; i < record.size(); ++i) {
                String formulaSchemeTitle = record.get(i).getFormulaSchemeTitle();
                String formulaCode = record.get(i).getFormulaCode();
                String formulaOld = record.get(i).getFormulaOld();
                String formulaNew = record.get(i).getFormulaNew();
                String updateResult = record.get(i).getUpdateResult();
                String formTitle = record.get(i).getFormTitle();
                String[] arr = new String[]{formulaSchemeTitle, formTitle, formulaCode, formulaOld, formulaNew, updateResult};
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0; j < columnNum; ++j) {
                    XSSFCell cell = row.createCell(j, CellType.STRING);
                    cell.setCellValue(arr[j]);
                    if (record.get(i).isSuccess()) continue;
                    cell.setCellStyle(failStyle);
                }
            }
        }
        try {
            outputStream = response.getOutputStream();
            workbook.write((OutputStream)outputStream);
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_206, (Throwable)e);
        }
        finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
    }

    @GetMapping(value={"deleteForm/{formKey}"})
    @ApiOperation(value="\u5220\u9664\u62a5\u8868")
    public void deleteForm(@PathVariable(value="formKey") String formKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868";
        String formTitle = "\u672a\u77e5";
        String formCode = "\u672a\u77e5";
        boolean taskCanEdit = false;
        try {
            DesignFormDefine designFormDefine = this.designTimeViewService.queryFormById(formKey);
            formTitle = designFormDefine.getTitle();
            formCode = designFormDefine.getFormCode();
            try {
                taskCanEdit = this.taskPlanPublishExternalService.formCanEdit(formKey);
            }
            catch (Exception e) {
                throw JQExceptionWrapper.wrapper(e);
            }
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            boolean sameServeCode = this.serveCodeService.isSameServeCode(designFormDefine.getOwnerLevelAndId());
            if (!sameServeCode) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_173);
            }
            this.iDesignFormCopyService.deleteCopyFormInfo(formKey);
            this.stepSaveService.delForm(formKey, true);
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_122, (Throwable)e);
        }
    }

    @GetMapping(value={"deleteForm/without_data/{formKey}"})
    @ApiOperation(value="\u5220\u9664\u62a5\u8868")
    public void deleteFormWithout(@PathVariable(value="formKey") String formKey) throws JQException {
        String logTitle = "\u5220\u9664\u62a5\u8868";
        String formTitle = "\u672a\u77e5";
        String formCode = "\u672a\u77e5";
        try {
            DesignFormDefine designFormDefine = this.designTimeViewService.queryFormById(formKey);
            formTitle = designFormDefine.getTitle();
            formCode = designFormDefine.getFormCode();
            boolean taskCanEdit = this.taskPlanPublishExternalService.formCanEdit(formKey);
            if (!taskCanEdit) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_030);
            }
            boolean sameServeCode = this.serveCodeService.isSameServeCode(designFormDefine.getOwnerLevelAndId());
            if (!sameServeCode) {
                throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_173);
            }
            this.stepSaveService.delForm(formKey, false);
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (JQException e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_122, (Throwable)e);
        }
    }

    @GetMapping(value={"delFormWhenDelGroup/{formKey}"})
    @ApiOperation(value="\u5220\u9664\u62a5\u8868\u5206\u7ec4\u65f6\u5220\u9664\u62a5\u8868")
    public void delFormWhenDelGroup(@PathVariable(value="formKey") String formKey) throws JQException {
        String logTitle = "\u5220\u9664\u9664\u5206\u7ec4\u65f6\u5220\u9664\u62a5\u8868";
        String formTitle = "\u672a\u77e5";
        String formCode = "\u672a\u77e5";
        try {
            DesignFormDefine designFormDefine = this.designTimeViewService.queryFormById(formKey);
            formTitle = designFormDefine.getTitle();
            formCode = designFormDefine.getFormCode();
            this.stepSaveService.delFormWhenDelFormGroup(formKey, true);
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_INFO);
        }
        catch (Exception e) {
            NrDesignLogHelper.log(logTitle, formCode + "|" + formTitle, NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_153, (Throwable)e);
        }
    }

    @GetMapping(value={"form-by-key-and-language"})
    @ApiOperation(value="\u6839\u636e\u8bed\u8a00\u83b7\u53d6\u8868\u6837")
    public String getFormByKeyAndType(String key, int type) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
            objectMapper.registerModule((Module)module);
            FormObj formObj = this.stepSaveService.setFormObjProperty(key, type);
            return objectMapper.writeValueAsString((Object)formObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_166, (Throwable)e);
        }
    }

    @PostMapping(value={"changeFormOrder"})
    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u7684order")
    public Map<String, String> changeFormOrder(@RequestBody ChangeFormOrder changeFormOrder) throws JQException {
        try {
            String formKey = changeFormOrder.getFormKey();
            String order = changeFormOrder.getOrder();
            String groupKey = changeFormOrder.getGroupKey();
            this.stepSaveService.stepChangeFormOrder(formKey, order, groupKey);
            List links = this.nrDesignTimeController.getFormGroupLinksByFormId(formKey);
            Map<String, String> orderBygroup = links.stream().collect(Collectors.toMap(t -> t.getGroupKey(), t -> t.getFormOrder(), (oldValue, newValue) -> newValue));
            return orderBygroup;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_154, (Throwable)e);
        }
    }

    @PostMapping(value={"changeFormOrders"})
    @ApiOperation(value="\u4fdd\u5b58\u62a5\u8868\u7684order")
    public void changeFormOrders(@RequestBody OrderVO formOrder) throws JQException {
        try {
            String groupKey = formOrder.getGroupKey();
            String[] formKeys = formOrder.getKeys();
            for (int i = 0; i < formKeys.length; ++i) {
                this.stepSaveService.stepChangeFormOrder(formKeys[i], formOrder.getOrders() != null && formOrder.getOrders().length > i ? formOrder.getOrders()[i] : OrderGenerator.newOrder(), groupKey);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_154, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u8bed\u8a00\u83b7\u53d6\u62a5\u8868\u7684\u6240\u6709\u8868\u6837")
    @PostMapping(value={"requestAllFormStyle"})
    @RequiresPermissions(value={"nr:task_form:design"})
    public String requestAllFormStyle(@RequestBody RequestAllFormStyle requestAllFormStyle) throws JQException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap<Integer, Grid2Data> gridMap = new HashMap<Integer, Grid2Data>();
            SimpleModule module = new SimpleModule();
            module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
            objectMapper.registerModule((Module)module);
            int languageType = requestAllFormStyle.getType();
            String formKey = requestAllFormStyle.getKey();
            byte[] data = this.nrDesignTimeController.getReportDataFromForm(formKey, languageType);
            if (data == null && languageType == 2) {
                String defaultLang = this.languageService.queryDefaultLanguage();
                data = this.nrDesignTimeController.getReportDataFromForm(formKey, Integer.parseInt(defaultLang));
            }
            Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])data);
            gridMap.put(languageType, grid2Data);
            return objectMapper.writeValueAsString(gridMap);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_167, (Throwable)e);
        }
    }

    @ApiOperation(value="\u79fb\u52a8\u62a5\u8868")
    @RequestMapping(value={"move_form"}, method={RequestMethod.POST})
    public void moveForm(@RequestBody MoveFormVO moveFormVo) throws JQException {
        this.taskDesignerService.moveForm(moveFormVo);
    }

    @ApiOperation(value="\u67e5\u8be2\u8868\u5355\u6240\u5c5e\u6240\u6709\u5206\u7ec4")
    @RequestMapping(value={"getOwngroupsbyform/{formKey}"}, method={RequestMethod.GET})
    public List<String> getOwnGroupsByForm(@PathVariable String formKey) throws JQException {
        List designFormGroupLinks = this.nrDesignTimeController.getFormGroupLinksByFormId(formKey);
        List<String> formGroupKeys = designFormGroupLinks.stream().map(e -> e.getGroupKey()).collect(Collectors.toList());
        return formGroupKeys;
    }

    @ApiOperation(value="\u66f4\u65b0\u8868\u5355\u548c\u5206\u7ec4\u7684\u5173\u8054\u5173\u7cfb")
    @RequestMapping(value={"updateFormGroupLink"}, method={RequestMethod.POST})
    public void updateFormGroupLink(@RequestBody String mess) throws Exception {
        JSONObject json = new JSONObject(mess);
        String sourceFormKey = (String)json.get("sourceFormKey");
        String ownGroupKey = (String)json.get("OwnGroupKey");
        this.quoteFormByOtherTaskServiceImpl.updateFormGroupLink(sourceFormKey, ownGroupKey);
    }

    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848key\u83b7\u53d6\u5f53\u524d\u65b9\u6848\u4e0b\u7684\u6240\u6709\u8868\u5355")
    @RequestMapping(value={"{schemeKey}/getFormsByScheme"}, method={RequestMethod.GET})
    public List<ITree<TaskSchemeGroupTreeNode>> getFormsByTask(@PathVariable String schemeKey) throws JQException {
        try {
            return this.quoteFormByOtherTaskServiceImpl.getFormGroupTree(schemeKey);
        }
        catch (JQException e1) {
            throw e1;
        }
        catch (Exception e2) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_000);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u8868\u6811\u8282\u70b9")
    @RequestMapping(value={"query-analysis-treenode"}, method={RequestMethod.POST})
    public List<ResourceTreeNode> queryAnalysis(@RequestBody(required=false) String parent) throws JQException {
        ArrayList<ResourceTreeNode> result = new ArrayList<ResourceTreeNode>();
        if (parent == null) {
            parent = "";
        }
        try {
            List nodes = this.resuorceTreeController.getChildren(parent, false, false);
            for (ResourceTreeNode node : nodes) {
                if (!node.getType().equals("com.jiuqi.nvwa.quickreport.business") && !node.getType().equals("com.jiuqi.nvwa.dataanalyze")) continue;
                result.add(node);
            }
            return result;
        }
        catch (DataAnalyzeResourceException e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_205);
        }
    }

    @ApiOperation(value="\u67e5\u8be2\u5206\u6790\u8868\u539f\u59cb\u8868\u6837")
    @RequestMapping(value={"query-analysis-style/{analysisKey}"})
    public String queryAnalysisStyle(@PathVariable String analysisKey) throws JQException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSeralizeToGeGe());
        objectMapper.registerModule((Module)module);
        FormObj formObj = new FormObj();
        Grid2Data grid2Data = new Grid2Data();
        try {
            QuickReport quickReportByGuidOrId = this.quickReportModelService.getQuickReportByGuidOrId(analysisKey);
            List worksheets = quickReportByGuidOrId.getWorksheets();
            WorksheetModel worksheetModel = (WorksheetModel)worksheets.get(0);
            GridData griddata = worksheetModel.getGriddata();
            GridDataTransform.gridDataToGrid2Data((GridData)griddata, (Grid2Data)grid2Data);
            GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
            formObj.setFormStyle(grid2Data);
            return objectMapper.writeValueAsString((Object)formObj);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_207);
        }
    }
}

