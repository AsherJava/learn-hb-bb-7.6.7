/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package nr.single.para.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import nr.single.para.common.NrSingleErrorEnum;
import nr.single.para.compare.definition.common.CompareStatusType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.upload.domain.CreateParamDTO;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDataMappingDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.FormulaFormMappingDTO;
import nr.single.para.upload.domain.FormulaSchemeMappingDTO;
import nr.single.para.upload.domain.ParamImportInfoDTO;
import nr.single.para.upload.domain.PrintSchemeMappingDTO;
import nr.single.para.upload.domain.SingleCompareResult;
import nr.single.para.upload.domain.TaskLinkMappingDTO;
import nr.single.para.upload.domain.UploadFileDTO;
import nr.single.para.upload.domain.UploadFileOptionDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileAnalysisService;
import nr.single.para.upload.service.IFileExecuteService;
import nr.single.para.upload.service.IFileUpdateService;
import nr.single.para.upload.vo.CreateParamVO;
import nr.single.para.upload.vo.FixRegionCompareVO;
import nr.single.para.upload.vo.FloatRegionCompareVO;
import nr.single.para.upload.vo.FormSchemeVO;
import nr.single.para.upload.vo.QueryPrintSchemeCompareVO;
import nr.single.para.upload.vo.SingleCompareVO;
import nr.single.para.upload.vo.UploadFileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@JQRestController
@RequestMapping(value={"/single/upload"})
@Api(tags={"JIO\u53c2\u6570\u5bfc\u5165"})
public class UploadParamController {
    private static final Logger log = LoggerFactory.getLogger(UploadParamController.class);
    @Autowired
    private IFileExecuteService fileExecuteService;
    @Autowired
    private IFileAnalysisService analysisService;
    @Autowired
    private IFileUpdateService updateService;

    @PostMapping(value={"/matchNetTask"})
    @ApiOperation(value="\u6839\u636eJIO\u53c2\u6570\u6587\u4ef6\u5339\u914d\u7f51\u62a5\u4efb\u52a1")
    public UploadFileVO matchNetTask(@RequestParam(value="file") MultipartFile file) throws JQException {
        UploadFileVO result = new UploadFileVO();
        try {
            UploadFileDTO uploadFileDTO = this.fileExecuteService.uploadAndMatchFile(file.getOriginalFilename(), file.getBytes());
            result.setKey(uploadFileDTO.getKey());
            result.setMatchTask(uploadFileDTO.getMatchTask());
            result.setTaskInfos(uploadFileDTO.getTaskInfos());
            result.setPeriod(uploadFileDTO.getPeriod());
            result.setSingleTaskName(uploadFileDTO.getSingleTaskName());
            result.setParaCompareDataSchemeInfo(uploadFileDTO.getParaCompareDataSchemeInfo());
            result.setCustom(uploadFileDTO.getCustom());
            result.setTaskFindMode(uploadFileDTO.getTaskFindMode());
            result.setSingleTaskYear(uploadFileDTO.getSingleTaskYear());
            result.setSingleFromPeriod(uploadFileDTO.getSingleFromPeriod());
            result.setSingleToPeriod(uploadFileDTO.getSingleToPeriod());
            result.setNetTaskInfo(uploadFileDTO.getNetTaskInfo());
            result.setSingleTaskName(uploadFileDTO.getSingleTaskName());
            result.setSingleFileFlag(uploadFileDTO.getSingleFileFlag());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_004, e.getMessage());
        }
        return result;
    }

    @PostMapping(value={"/compareSingleAndNet"})
    @ApiOperation(value="JIO\u6587\u4ef6\u5bf9\u5e94\u5355\u673a\u7248\u4e0e\u7f51\u62a5\u7248\u8fdb\u884c\u5206\u6790\u6bd4\u8f83")
    public AsyncTaskInfo compare(@RequestBody CreateParamVO createParam) throws JQException {
        try {
            return this.fileExecuteService.analysisFile(createParam);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_005, (Throwable)e);
        }
    }

    @PostMapping(value={"/compareSingleAndNetEx"})
    @ApiOperation(value="JIO\u6587\u4ef6\u5bf9\u5e94\u5355\u673a\u7248\u53c2\u6570\u4e0e\u7f51\u62a5\u7248\u8fdb\u884c\u5206\u6790\u6bd4\u8f83")
    public SingleCompareResult compareEx(@RequestBody CreateParamVO createParam) throws JQException {
        try {
            return this.fileExecuteService.analysisFileEx(createParam);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_005, (Throwable)e);
        }
    }

    @PostMapping(value={"/doUploadJIO/{importKey}"})
    @ApiOperation(value="\u6267\u884c\u5bfc\u5165JIO\u6587\u4ef6")
    public AsyncTaskInfo doUploadJIO(@PathVariable(value="importKey") String importKey) throws JQException {
        try {
            return this.fileExecuteService.executeUpload(importKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_014, (Throwable)e);
        }
    }

    @PostMapping(value={"/getFormSchemeByTaskYear"})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1\u548c\u5e74\u5ea6\u83b7\u53d6\u62a5\u8868\u65b9\u6848")
    public List<FormSchemeVO> getFormSchemeByTaskYear(@RequestBody CreateParamDTO param) throws JQException {
        try {
            return this.fileExecuteService.getFormSchemeByTaskYear(param.getTaskKey(), param.getSingleTaskYear(), param.getSingleTaskName());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_019, (Throwable)e);
        }
    }

    @GetMapping(value={"/getFMDMCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5c01\u9762\u4ee3\u7801\u5bf9\u6bd4\u7ed3\u679c")
    public List<FMDMMappingDTO> getFMDMCompareResult(@PathVariable(value="importKey") String importKey) {
        return this.analysisService.listFMDMResult(importKey);
    }

    @PostMapping(value={"/updateFMDMCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u5c01\u9762\u4ee3\u7801\u5bf9\u6bd4\u7ed3\u679c")
    public void updateFMDMCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<FMDMMappingDTO> fmdmMappingDTOS) throws JQException {
        try {
            this.updateService.saveFMDMResult(importKey, fmdmMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_006, (Throwable)e);
        }
    }

    @GetMapping(value={"/getEnumCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u5b9a\u4e49\u6bd4\u8f83\u7ed3\u679c")
    public List<EnumDefineMappingDTO> getEnumCompareResult(@PathVariable(value="importKey") String importKey) {
        return this.analysisService.listEnumDefineResult(importKey);
    }

    @PostMapping(value={"/updateEnumCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u679a\u4e3e\u5b9a\u4e49\u6bd4\u8f83\u7ed3\u679c")
    public void updateEnumCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<EnumDefineMappingDTO> enumDefineMappingDTOS) throws JQException {
        try {
            this.updateService.saveEnumDefineResult(importKey, enumDefineMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_007, (Throwable)e);
        }
    }

    @GetMapping(value={"/getEnumItemCompareResult/{importKey}/{enumKey}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u9879\u6bd4\u8f83\u7ed3\u679c")
    public List<EnumDataMappingDTO> getEnumItemCompareResult(@PathVariable(value="importKey") String importKey, @PathVariable(value="enumKey") String enumKey) {
        EnumDataDTO enumData = new EnumDataDTO();
        enumData.setEnumCompareKey(enumKey);
        EnumDataDTO enumDataDTO = this.analysisService.listEnumDataResult(importKey, enumData);
        if (enumDataDTO != null) {
            return enumDataDTO.getMappingList();
        }
        return new ArrayList<EnumDataMappingDTO>();
    }

    @PostMapping(value={"/updateEnumItemCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u679a\u4e3e\u9879\u6bd4\u8f83\u7ed3\u679c")
    public void updateEnumItemCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<EnumDataMappingDTO> enumDataMappingDTOS) throws JQException {
        EnumDataDTO enumDataDTO = new EnumDataDTO();
        enumDataDTO.setMappingList(enumDataMappingDTOS);
        try {
            this.updateService.saveEnumDataResult(importKey, enumDataDTO);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_008, (Throwable)e);
        }
    }

    @GetMapping(value={"/getFormCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u5bf9\u6bd4\u7ed3\u679c")
    public List<FormMappingDTO> getFormCompareResult(@PathVariable String importKey) {
        return this.analysisService.listFormResult(importKey);
    }

    @GetMapping(value={"/getFormCompareChangeResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u62a5\u8868\u53d8\u5316\u5bf9\u6bd4\u7ed3\u679c")
    public List<FormMappingDTO> getFormCompareChangeResult(@PathVariable String importKey) {
        return this.analysisService.listFormChangeResult(importKey, 0);
    }

    @PostMapping(value={"/updateFormCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u62a5\u8868\u5bf9\u6bd4\u7ed3\u679c")
    public void updateFormCompareResult(@PathVariable String importKey, @RequestBody List<FormMappingDTO> formMappingDTOS) throws JQException {
        try {
            this.updateService.saveFormResult(importKey, formMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_009, (Throwable)e);
        }
    }

    @GetMapping(value={"/getZBCompareResult/{importKey}/{formKey}", "/getZBCompareResult/{importKey}/{formKey}/{singleRegionId}"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u5bf9\u6bd4\u7ed3\u679c")
    public List<ZBMappingDTO> getZBCompareResultByForm(@PathVariable(value="importKey") String importKey, @PathVariable(value="formKey") String formKey, @PathVariable(value="singleRegionId", required=false) Integer singleRegionId) {
        return this.analysisService.listZBResult(importKey, formKey, singleRegionId, true);
    }

    @GetMapping(value={"/getNetZBInfoResult/{importKey}/{formKey}"})
    @ApiOperation(value="\u83b7\u53d6\u7f51\u62a5\u6709\u4f46\u5355\u673a\u7248\u6ca1\u6709\u7684\u6307\u6807\u4fe1\u606f")
    public List<ZBMappingDTO> getNetZBInfoResult(@PathVariable(value="importKey") String importKey, @PathVariable(value="formKey") String formKey) {
        ZBMappingDTO zbMapping = new ZBMappingDTO();
        zbMapping.setFormCompareKey(formKey);
        return this.analysisService.listZBInfo(importKey, zbMapping);
    }

    @PostMapping(value={"/updateZBCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u6307\u6807\u5bf9\u6bd4\u7ed3\u679c")
    public void updateZBCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<ZBMappingDTO> zbMappingDTOS) throws JQException {
        try {
            this.updateService.saveZBResult(importKey, zbMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_010, (Throwable)e);
        }
    }

    @GetMapping(value={"/getTaskLinkCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5173\u8054\u4efb\u52a1\u5bf9\u6bd4\u7ed3\u679c")
    public List<TaskLinkMappingDTO> getTaskLinkCompareResult(@PathVariable(value="importKey") String importKey) {
        return this.analysisService.listTaskLinkResult(importKey);
    }

    @PostMapping(value={"/updateTaskLinkCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u5173\u8054\u4efb\u52a1\u5bf9\u6bd4\u7ed3\u679c")
    public void updateTaskLinkCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<TaskLinkMappingDTO> taskLinkMappingDTOS) throws JQException {
        try {
            this.updateService.saveTaskResult(importKey, taskLinkMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_012, (Throwable)e);
        }
    }

    @PostMapping(value={"/getPrintSchemeCompareResult"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u673a\u7248\u4e0b\u67d0\u6253\u5370\u65b9\u6848\u7684\u4e0e\u7f51\u62a5\u5bf9\u6bd4\u7ed3\u679c")
    public List<PrintSchemeMappingDTO> getPrintSchemeCompareResult(@RequestBody QueryPrintSchemeCompareVO queryVO) {
        PrintSchemeMappingDTO printSchemeMapping = new PrintSchemeMappingDTO();
        printSchemeMapping.setSchemeKey(queryVO.getSinglePrintSchemeKey());
        return this.analysisService.listPrintSchemeResult(queryVO.getImportKey(), printSchemeMapping);
    }

    @PostMapping(value={"/updatePrintSchemeCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u6253\u5370\u65b9\u6848\u5bf9\u6bd4\u7ed3\u679c")
    public void updatePrintSchemeCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<PrintSchemeMappingDTO> printSchemeMappingDTOS) throws JQException {
        try {
            this.updateService.savePrintScheme(importKey, printSchemeMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_013, (Throwable)e);
        }
    }

    @GetMapping(value={"/getFormulaSchemeCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u65b9\u6848\u5bf9\u6bd4\u7ed3\u679c")
    public List<FormulaSchemeMappingDTO> getFormulaSchemeCompareResult(@PathVariable(value="importKey") String importKey) {
        return this.analysisService.listFormulaSchemeResult(importKey);
    }

    @PostMapping(value={"/updateFormulaSchemeCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u516c\u5f0f\u65b9\u6848\u5bf9\u6bd4\u7ed3\u679c")
    public void updateFormulaSchemeCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<FormulaSchemeMappingDTO> formulaSchemeMappingDTOS) throws JQException {
        try {
            this.updateService.saveFormulaScheme(importKey, formulaSchemeMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_013, (Throwable)e);
        }
    }

    @GetMapping(value={"/getFormulaFormCompareResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u516c\u5f0f\u8868\u5355\u5bf9\u6bd4\u7ed3\u679c")
    public List<FormulaFormMappingDTO> getFormulaFormCompareResult(@PathVariable(value="importKey") String importKey) {
        return this.analysisService.listFormulaFormResult(importKey);
    }

    @PostMapping(value={"/updateFormulaFormCompareResult/{importKey}"})
    @ApiOperation(value="\u66f4\u65b0\u516c\u8868\u5355\u5bf9\u6bd4\u7ed3\u679c")
    public void updateFormulaFormCompareResult(@PathVariable(value="importKey") String importKey, @RequestBody List<FormulaFormMappingDTO> formulaFormMappingDTOS) throws JQException {
        try {
            this.updateService.saveFormulaForm(importKey, formulaFormMappingDTOS);
        }
        catch (SingleCompareException e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_013, (Throwable)e);
        }
    }

    @PostMapping(value={"/compareByType"})
    @ApiOperation(value="\u6309\u6570\u636e\u7c7b\u578b\u8fdb\u884c\u5355\u9879\u6bd4\u8f83")
    public boolean compareByType(@RequestBody SingleCompareVO singleComparevo) throws JQException {
        try {
            return this.fileExecuteService.singleAnalysis(singleComparevo);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_015, (Throwable)e);
        }
    }

    @PostMapping(value={"/floatRegionCompare"})
    @ApiOperation(value="\u6d6e\u52a8\u533a\u57df\u6307\u5b9a\u540e\u6309\u533a\u57df\u91cd\u65b0\u6bd4\u8f83")
    public void floatRegionCompare(@RequestBody FloatRegionCompareVO compareVO) throws JQException {
        try {
            this.fileExecuteService.floatRegionReCompare(compareVO);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_018, (Throwable)e);
        }
    }

    @PostMapping(value={"/fixRegionCompare"})
    @ApiOperation(value="\u56fa\u5b9a\u533a\u57df\u6307\u5b9a\u540e\u6309\u533a\u57df\u91cd\u65b0\u6bd4\u8f83")
    public List<ZBMappingDTO> fixtRegionCompare(@RequestBody FixRegionCompareVO compareVO) throws JQException {
        try {
            return this.fileExecuteService.fixRegionReCompare(compareVO);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_021, (Throwable)e);
        }
    }

    @GetMapping(value={"/doEnumCompare/{importKey}/{enumPrefix}"})
    @ApiOperation(value="\u679a\u4e3e\u6309\u7167\u6307\u5b9a\u524d\u7f00\u8fdb\u884c\u6bd4\u8f83")
    public void doEnumCompare(@PathVariable(value="importKey") String importKey, @PathVariable(value="enumPrefix") String enumPrefix) throws JQException {
        try {
            this.fileExecuteService.doEnumCompare(importKey, enumPrefix);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_021, (Throwable)e);
        }
    }

    @PostMapping(value={"/doPartEnumCompare/{importKey}/{enumPrefix}"})
    @ApiOperation(value="\u6307\u5b9a\u679a\u4e3e\u6309\u7167\u6307\u5b9a\u524d\u7f00\u8fdb\u884c\u6bd4\u8f83")
    public void doPartEnumCompare(@PathVariable(value="importKey") String importKey, @PathVariable(value="enumPrefix") String enumPrefix, @RequestBody List<String> enumCompareKeys) throws JQException {
        try {
            if (enumCompareKeys == null || enumCompareKeys.isEmpty()) {
                return;
            }
            this.fileExecuteService.doPartEnumCompare(importKey, enumPrefix, enumCompareKeys);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_021, (Throwable)e);
        }
    }

    @GetMapping(value={"/getEnumCompareChangeResult/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u53d8\u5316\u5bf9\u6bd4\u7ed3\u679c")
    public List<EnumDefineMappingDTO> getEnumCompareChangeResult(@PathVariable String importKey) {
        return this.analysisService.listEnumDefineChangeResult(importKey);
    }

    @GetMapping(value={"/getCurFormStyleByType/{importKey}/{formCompareKey}"})
    @ApiOperation(value="\u6839\u636e\u7c7b\u578b\u83b7\u53d6\u5f53\u524d\u62a5\u8868\u7684\u8868\u6837")
    public Map<Integer, String> getFormStyle(@PathVariable(value="importKey") String importKey, @PathVariable(value="formCompareKey") String formCompareKey) {
        return this.analysisService.getStyleByTypeAndFormComapreKey(importKey, formCompareKey);
    }

    @GetMapping(value={"/getAllSinglePrintScheme/{importKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5355\u673a\u7248\u4e0b\u6240\u6709\u6253\u5370\u65b9\u6848")
    public List<String> getgetAllSinglePrintScheme(@PathVariable String importKey) {
        return this.analysisService.getAllSinglePrintScheme(importKey);
    }

    @GetMapping(value={"/deleteCompareInfo/{importKey}"})
    @ApiOperation(value="\u5220\u9664\u6bd4\u8f83\u4fe1\u606f")
    public AsyncTaskInfo deleteCompareInfo(@PathVariable String importKey) throws JQException {
        try {
            return this.fileExecuteService.deleteCompareInfo(importKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_017, (Throwable)e);
        }
    }

    @PostMapping(value={"/deleteCompareInfos"})
    @ApiOperation(value="\u6279\u91cf\u5220\u9664\u6bd4\u8f83\u4fe1\u606f")
    public AsyncTaskInfo deleteCompareInfos(@RequestBody List<String> importKeys) throws JQException {
        try {
            return this.fileExecuteService.deleteCompareInfos(importKeys);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_017, (Throwable)e);
        }
    }

    @GetMapping(value={"/listAllCompareInfos"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u53c2\u6570\u5386\u53f2\u6bd4\u8f83\u4fe1\u606f")
    public List<ParamImportInfoDTO> listAllCompareInfos() throws JQException {
        try {
            return this.fileExecuteService.listAllCompareInfos();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_022, (Throwable)e);
        }
    }

    @GetMapping(value={"/listSuccessAndFailCompareInfos"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u6709\u6210\u529f\u6216\u5931\u8d25\u5386\u53f2\u6bd4\u8f83\u4fe1\u606f")
    public List<ParamImportInfoDTO> listSuccessAndFailCompareInfos() throws JQException {
        try {
            List<ParamImportInfoDTO> list = this.fileExecuteService.listAllCompareInfos();
            ArrayList<ParamImportInfoDTO> list2 = new ArrayList<ParamImportInfoDTO>();
            Instant lashMonth = Instant.now().minusMillis(TimeUnit.DAYS.toMillis(30L));
            Instant lashMonth2 = Instant.now().minusMillis(TimeUnit.DAYS.toMillis(40L));
            ArrayList<String> deleteImports = new ArrayList<String>();
            ArrayList<String> deleteImports2 = new ArrayList<String>();
            ArrayList<String> deleteImports3 = new ArrayList<String>();
            ArrayList<ParamImportInfoDTO> deleteList = new ArrayList<ParamImportInfoDTO>();
            for (ParamImportInfoDTO info : list) {
                if (info.getImportStatus() == CompareStatusType.SCHEME_IMPORTED || info.getImportStatus() == CompareStatusType.SCHEME_IMPORTFAIL) {
                    list2.add(info);
                    continue;
                }
                if (info.getUpdateTime() == null || !lashMonth.isAfter(info.getUpdateTime())) continue;
                if (info.getImportStatus() == CompareStatusType.SCHEME_DELETEING) {
                    if (!lashMonth2.isAfter(info.getUpdateTime())) continue;
                    deleteImports2.add(info.getKey());
                    continue;
                }
                if (info.getImportStatus() == CompareStatusType.SCHEME_DELETEED) {
                    deleteImports3.add(info.getKey());
                    continue;
                }
                deleteImports.add(info.getKey());
                deleteList.add(info);
            }
            if (!list2.isEmpty()) {
                list2 = list2.stream().sorted(Comparator.comparing(ParamImportInfoDTO::getUpdateTime).reversed()).collect(Collectors.toList());
            }
            if (!deleteImports.isEmpty()) {
                this.deleteCompareInfos(deleteImports);
            }
            if (!deleteImports2.isEmpty()) {
                this.deleteCompareInfos(deleteImports2);
            }
            if (!deleteImports3.isEmpty()) {
                this.deleteCompareInfos(deleteImports3);
            }
            return list2;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_022, (Throwable)e);
        }
    }

    @GetMapping(value={"/geteSingleEnumCodesInFMDM/{importKey}"})
    @ApiOperation(value="\u6839\u636e\u5bfc\u5165ID\u83b7\u53d6\u5c01\u9762\u4ee3\u7801\u6240\u7528\u7684\u5355\u673a\u7248\u679a\u4e3e\u6807\u8bc6")
    public List<String> getSingleEnumCodesInFMDM(@PathVariable(value="importKey") String importKey) throws JQException {
        try {
            return this.fileExecuteService.getSingleEnumCodeInFmdm(importKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_023, (Throwable)e);
        }
    }

    @GetMapping(value={"/getImportOption/{importKey}"})
    @ApiOperation(value="\u6839\u636e\u5bfc\u5165ID\u83b7\u53d6\u5bfc\u5165\u9009\u9879")
    public UploadFileOptionDTO getImportOption(@PathVariable(value="importKey") String importKey) throws JQException {
        try {
            return this.analysisService.getImportOption(importKey);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_023, (Throwable)e);
        }
    }

    @PostMapping(value={"/updateImportOption/{importKey}"})
    @ApiOperation(value="\u6839\u636e\u5bfc\u5165ID\u66f4\u65b0\u5bfc\u5165\u9009\u9879")
    public void updateImportOption(@PathVariable(value="importKey") String importKey, @RequestBody UploadFileOptionDTO option) throws JQException {
        try {
            this.updateService.saveImportOption(importKey, option);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESINGER_EXCEPTION_024, (Throwable)e);
        }
    }
}

