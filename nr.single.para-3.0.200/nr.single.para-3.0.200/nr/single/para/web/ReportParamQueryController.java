/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO
 *  com.jiuqi.nr.definition.config.EnableTask2Config
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.DesignTimeViewController
 *  com.jiuqi.nr.single.core.util.SingleSecurityUtils
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.org.ZB
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package nr.single.para.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataSchemeDO;
import com.jiuqi.nr.definition.config.EnableTask2Config;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.DesignTimeViewController;
import com.jiuqi.nr.single.core.util.SingleSecurityUtils;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.org.ZB;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import nr.single.para.common.NrSingleErrorEnum;
import nr.single.para.common.ParamQueryError;
import nr.single.para.parain.util.IParaImportFileServcie;
import nr.single.para.upload.domain.CommonParamDTO;
import nr.single.para.upload.domain.CreateParamDTO;
import nr.single.para.upload.domain.DataLinkDTO;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDataMappingDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileAnalysisService;
import nr.single.para.upload.service.IParamQueryService;
import nr.single.para.upload.vo.DataSchemeVO;
import nr.single.para.upload.vo.DefaultCodeObject;
import nr.single.para.upload.vo.EnumMappingRepeatVO;
import nr.single.para.upload.vo.FMDMMappingRepeatVO;
import nr.single.para.upload.vo.FormSchemeVO;
import nr.single.para.upload.vo.RepeatAndDefaultCodeVO;
import nr.single.para.upload.vo.RepeatAndEmptyInfos;
import nr.single.para.upload.vo.TaskInfoVO;
import nr.single.para.upload.vo.TaskQueryVO;
import nr.single.para.upload.vo.ZBInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"/single/report/query"})
@Api(tags={"\u62a5\u8868\u53c2\u6570\u67e5\u8be2"})
public class ReportParamQueryController {
    private static final Logger logger = LoggerFactory.getLogger(ReportParamQueryController.class);
    @Autowired
    private IParamQueryService paramQueryService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private IFileAnalysisService fileAnalysisService;
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private IParaImportFileServcie fileServcie;
    @Autowired
    private EnableTask2Config taskConfig;
    @Autowired
    private IMappingSchemeService mapingSchemeService;

    @PostMapping(value={"/entity"})
    @ApiOperation(value="\u67e5\u8be2\u5355\u4f4d\u5217\u8868")
    public List<CommonParamDTO> listEntity(@RequestBody List<String> entityKeys) throws JQException {
        List<CommonParamDTO> commonParamDTOS;
        try {
            commonParamDTOS = this.paramQueryService.listEntity(entityKeys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)ParamQueryError.ENTITY_QUERY, e.getMessage());
        }
        return commonParamDTOS;
    }

    @PostMapping(value={"/getAllTask"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u7cfb\u7edf\u6240\u6709\u4efb\u52a1")
    public List<TaskInfoVO> getTaskList(@RequestBody List<String> taskKey) {
        return this.paramQueryService.getTaskList(taskKey);
    }

    @GetMapping(value={"/getFormScheme/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u4efb\u52a1\u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848")
    public List<FormSchemeVO> getFormSchemeByTask(@PathVariable(value="taskKey") String taskKey) {
        return this.paramQueryService.listFormScheme(taskKey);
    }

    @GetMapping(value={"/getDataSchemePrefix/{dataSchemeKey}", "/getDataSchemePrefix/{dataSchemeKey}/{taskKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u5bf9\u5e94\u7684\u524d\u7f00")
    public DataSchemeVO getDataSchemePrefix(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @PathVariable(value="taskKey", required=false) String taskKey) {
        DataSchemeVO result = new DataSchemeVO();
        DesignDataScheme dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme != null) {
            if (dataScheme.getPrefix() != null) {
                result.setPrefix(dataScheme.getPrefix());
            } else {
                result.setPrefix("");
            }
        }
        List unit = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT);
        result.setMainDimensionKey(((DesignDataDimension)unit.get(0)).getDimKey());
        if (StringUtils.isNotEmpty((String)taskKey)) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
            String dw = designTaskDefine.getDw();
            String DW = dw.substring(0, dw.indexOf("@"));
            result.setMainDimensionKey(DW);
        }
        return result;
    }

    @GetMapping(value={"/getDataTable/{dataSchemeKey}/{tableType}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6570\u636e\u8868")
    public List<CommonParamDTO> getDataTable(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @PathVariable(value="tableType") Boolean tableType) {
        return this.paramQueryService.listDataTable(dataSchemeKey, tableType);
    }

    @GetMapping(value={"/getEnumList/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u5f53\u524d\u6570\u636e\u65b9\u6848\u4e0b\u7684\u679a\u4e3e")
    public List<CommonParamDTO> getEnumList(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getEnumList(dataSchemeKey);
    }

    @GetMapping(value={"/getEnumListByPrefix/{enumPrefix}"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u5b9a\u524d\u7f00\u7684\u679a\u4e3e")
    public List<CommonParamDTO> getEnumListByPrefix(@PathVariable(value="enumPrefix") String enumPrefix) {
        return this.paramQueryService.getEnumListByPrefix(enumPrefix);
    }

    @GetMapping(value={"/getEnumItemByEnumDefine/{tablekey}/{singleTaskYear}"})
    @ApiOperation(value="\u6839\u636e\u679a\u4e3e\u83b7\u53d6\u7f51\u62a5\u679a\u4e3e\u9879")
    public List<BaseDataDO> getEnumItemByEnumDefine(@PathVariable(value="tablekey") String tablekey, @PathVariable(value="singleTaskYear") String singleTaskYear) {
        return this.paramQueryService.listBaseData(tablekey, singleTaskYear);
    }

    @GetMapping(value={"/getZBByTable/{tableCode}"})
    @ApiOperation(value="\u6839\u636e\u6570\u636e\u8868code\u83b7\u53d6\u8868\u4e0b\u6307\u6807\u6216\u5b57\u6bb5")
    public List<CommonParamDTO> getZBByTable(@PathVariable(value="tableCode") String tableCode) {
        return this.paramQueryService.listZBByTableCode(tableCode);
    }

    @GetMapping(value={"/getPeriodEntities"})
    @ApiOperation(value="\u83b7\u53d6\u65f6\u671f")
    public List<CommonParamDTO> getPeriod() {
        return this.paramQueryService.listPeriod();
    }

    @PostMapping(value={"/getZB"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u8be6\u60c5")
    public List<ZB> getZbInfo(@RequestBody ZBInfoVO zbInfoVO) {
        return this.paramQueryService.queryZBInfo(zbInfoVO);
    }

    @PostMapping(value={"/getZbsInForm"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u5c5e\u8868\u4e0b\u7684\u6307\u6807\u6216\u679a\u4e3e")
    public Map<String, String> getZbsInForm(@RequestBody FMDMMappingDTO fmdmMappingDTO) {
        return this.paramQueryService.queryZBsInForm(fmdmMappingDTO);
    }

    @PostMapping(value={"/getZbsExInForm"})
    @ApiOperation(value="\u83b7\u53d6\u6240\u5c5e\u8868\u4e0b\u7684\u6307\u6807\u6216\u679a\u4e3e")
    public Map<String, CommonParamDTO> getZbsExInForm(@RequestBody FMDMMappingDTO fmdmMappingDTO) {
        return this.paramQueryService.queryZBsExInForm(fmdmMappingDTO);
    }

    @GetMapping(value={"/getMdInfoZbsInDataScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6307\u6807")
    public Map<String, String> getMdInfoZbsInDataScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getMdInfoZbsInDataScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getMdInfoZbsExInDataScheme/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4e0b\u7684\u6307\u6807")
    public Map<String, CommonParamDTO> getMdInfoZbsExInDataScheme(@PathVariable(value="dataSchemeKey") String dataSchemeKey) {
        return this.paramQueryService.getMdInfoZbsExInDataScheme(dataSchemeKey);
    }

    @GetMapping(value={"/getOrgZB/{entityCode}"})
    @ApiOperation(value="\u83b7\u53d6\u5b9e\u4f53\u7684\u6240\u6709\u5c5e\u6027")
    public Map<String, String> getOrgZB(@PathVariable String entityCode) {
        return this.paramQueryService.queryOrgZbInfo(entityCode);
    }

    @PostMapping(value={"/getAllTaskAndFormScheme"})
    @ApiOperation(value="\u83b7\u53d6\u7cfb\u7edf\u4e2d\u6240\u6709\u7684\u4efb\u52a1\u53ca\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848")
    public List<TaskQueryVO> getAllTaskAndFormScheme() {
        return this.paramQueryService.queryAllTaskAndFormScheme();
    }

    @GetMapping(value={"/getZBByKey/{netKey}"})
    @ApiOperation(value="\u6839\u636e\u7f51\u62a5\u6307\u6807key\u67e5\u8be2\u5bf9\u5e94\u6307\u6807\u4fe1\u606f")
    public DataField getZBByKey(@PathVariable(value="netKey") String netKey) {
        return this.dataSchemeService.getDataField(netKey);
    }

    @PostMapping(value={"/checkDataScheme/"})
    @ApiOperation(value="\u6821\u9a8c\u6570\u636e\u65b9\u6848\u76f8\u5173\u5c5e\u6027\u662f\u5426\u5408\u6cd5")
    public void checkDataScheme(@RequestBody DataSchemeVO dataSchemeVO) throws JQException {
        DesignDataSchemeDO dataScheme = new DesignDataSchemeDO();
        dataScheme.setCode(dataSchemeVO.getCode());
        dataScheme.setTitle(dataSchemeVO.getTitle());
        dataScheme.setPrefix(dataSchemeVO.getPrefix());
        try {
            this.paramQueryService.checkDataScheme((DesignDataScheme)dataScheme);
        }
        catch (SchemeDataException e) {
            throw new JQException((ErrorEnum)NrSingleErrorEnum.NRDESIGNER_EXCEPTION_016, e.getMessage());
        }
    }

    @PostMapping(value={"/getEnumItemEmptyInfo/{importKey}/{singleTaskYear}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u9879\u65b0\u589e\u60c5\u51b5\u4e0b\u7684\u7a7a\u4fe1\u606f")
    public RepeatAndEmptyInfos getEnumItemEmptyInfo(@PathVariable(value="importKey") String importKey, @PathVariable(value="singleTaskYear") String singleTaskYear, @RequestBody List<EnumDefineMappingDTO> enumDefineMappingDTO) {
        return this.fileAnalysisService.getEnumItemEmptyInfo(importKey, false, singleTaskYear, enumDefineMappingDTO);
    }

    @RequestMapping(value={"/getZBEmptyInfo/{importKey}", "/getZBEmptyInfo/{importKey}/{dataSchemeKey}"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u65b0\u589e\u60c5\u51b5\u4e0b\u7684\u7a7a\u4fe1\u606f")
    public RepeatAndEmptyInfos getZBEmptyInfo(@PathVariable(value="importKey") String importKey, @PathVariable(value="dataSchemeKey", required=false) String dataSchemeKey, @RequestBody List<FormMappingDTO> formMappingDTO) {
        return this.fileAnalysisService.getZBEmptyInfo(importKey, dataSchemeKey, false, formMappingDTO);
    }

    @GetMapping(value={"/getEmptyInfo/{importKey}/{singleTaskYear}"})
    @ApiOperation(value="\u83b7\u53d6\u65b0\u589e\u60c5\u51b5\u4e0b\u7684\u7a7a\u4fe1\u606f")
    public List<String> getEmptyInfo(@PathVariable(value="importKey") String importKey, @PathVariable(value="singleTaskYear") String singleTaskYear) {
        return this.fileAnalysisService.getEmptyInfo(importKey, singleTaskYear);
    }

    @PostMapping(value={"/getOriginalZBInfo"})
    @ApiOperation(value="\u83b7\u53d6\u539f\u59cb\u6307\u6807\u4fe1\u606f")
    public List<ZBMappingDTO> getOriginalZBInfo(@RequestBody List<ZBMappingDTO> zbMappingDTOS) {
        return this.paramQueryService.getOriginalZB(zbMappingDTOS);
    }

    @PostMapping(value={"/getOriginalFMDMInfo"})
    @ApiOperation(value="\u83b7\u53d6\u539f\u59cb\u5c01\u9762\u4ee3\u7801\u4fe1\u606f")
    public List<FMDMMappingDTO> getOriginalFMDMInfo(@RequestBody List<FMDMMappingDTO> fmdmMappingDTO) {
        return this.paramQueryService.getOriginalFMDM(fmdmMappingDTO);
    }

    @PostMapping(value={"/getOriginalEnumDataInfo/{singleTaskYear}"})
    @ApiOperation(value="\u83b7\u53d6\u539f\u59cb\u679a\u4e3e\u9879\u4fe1\u606f")
    public List<EnumDataMappingDTO> getOriginalEnumDataInfo(@PathVariable(value="singleTaskYear") String singleTaskYear, @RequestBody EnumDataDTO params) {
        return this.paramQueryService.getOriginalEnumData(params, singleTaskYear);
    }

    @PostMapping(value={"/getOriginalEnumDefineInfo/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u539f\u59cb\u679a\u4e3e\u5b9a\u4e49\u4fe1\u606f")
    public List<EnumDefineMappingDTO> getOriginalEnumDefineInfo(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @RequestBody List<EnumDefineMappingDTO> params) {
        return this.paramQueryService.getOriginalEnumDefine(dataSchemeKey, params);
    }

    @PostMapping(value={"/getOriginalFormInfo/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u539f\u59cb\u8868\u5355\u4fe1\u606f")
    public List<FormMappingDTO> getOriginalFormInfo(@PathVariable(value="formSchemeKey") String formSchemeKey, @RequestBody List<FormMappingDTO> params) {
        return this.paramQueryService.getOriginalForm(formSchemeKey, params);
    }

    @RequestMapping(value={"/getZBRepeatInfos/{infoKey}/{dataSchemeKey}", "/getZBRepeatInfos/{infoKey}"}, method={RequestMethod.POST})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u7f16\u7801\u91cd\u590d\u4fe1\u606f")
    public List<String> getZBRepeatInfos(@PathVariable(value="infoKey") String infoKey, @PathVariable(value="dataSchemeKey", required=false) String dataSchemeKey, @RequestBody List<ZBMappingDTO> repeatList) {
        List<Object> allDataField = new ArrayList<DesignDataField>();
        if (!CollectionUtils.isEmpty(repeatList) && repeatList.get(0).getRegionIndex() < 0 && StringUtils.isNotEmpty((String)dataSchemeKey)) {
            allDataField = this.dataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
        }
        return this.paramQueryService.getZBRepeatInfos(infoKey, dataSchemeKey, repeatList, null, allDataField);
    }

    @RequestMapping(value={"/getOriginalDataLinkByForm/{formKey}"}, method={RequestMethod.GET})
    @ApiOperation(value="\u83b7\u53d6\u6570\u636e\u8fde\u63a5")
    public List<DataLinkDTO> getDataLinkByForm(@PathVariable(value="formKey") String formKey) {
        return this.paramQueryService.getDataLinkByForm(formKey);
    }

    @PostMapping(value={"/getZBDefaultCode/{dataSchemeKey}/{formCode}"})
    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u9ed8\u8ba4\u7f16\u7801\u4fe1\u606f")
    public List<DefaultCodeObject> getZBDefaultCode(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @PathVariable(value="formCode") String formCode, @RequestBody List<ZBMappingDTO> defaultCodeList) {
        return this.paramQueryService.getZBDefaultCode(formCode, dataSchemeKey, defaultCodeList);
    }

    @PostMapping(value={"/getFormRepeatInfos/{formSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u8868\u5355\u7f16\u7801\u91cd\u590d\u4fe1\u606f")
    public List<String> getFormRepeatInfos(@PathVariable(value="formSchemeKey") String formSchemeKey, @RequestBody List<FormMappingDTO> params) {
        return this.paramQueryService.getFormRepeatInfos(formSchemeKey, params);
    }

    @PostMapping(value={"/getFMDMRepeatInfos"})
    @ApiOperation(value="\u83b7\u53d6\u5c01\u9762\u4ee3\u7801\u7f16\u7801\u91cd\u590d\u4fe1\u606f")
    public RepeatAndDefaultCodeVO getFMDMRepeatInfos(@RequestBody FMDMMappingRepeatVO param) {
        return this.paramQueryService.getFMDMRepeatInfos(param.getDataSchemeKey(), param.getRepeatList(), param.getDefaultCodeList());
    }

    @PostMapping(value={"/getEnumDefineRepeatInfos/{dataSchemeKey}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u5b9a\u4e49\u7f16\u7801\u91cd\u590d\u4fe1\u606f")
    public RepeatAndDefaultCodeVO getEnumDefineRepeatInfos(@PathVariable(value="dataSchemeKey") String dataSchemeKey, @RequestBody EnumMappingRepeatVO params) {
        return this.paramQueryService.getEnumDefineRepeatInfos(dataSchemeKey, params.getSchemePrefix(), params.getRepeatList(), params.getDefaultCodeList());
    }

    @PostMapping(value={"/getEnumItemRepeatInfos/{singleTaskYear}"})
    @ApiOperation(value="\u83b7\u53d6\u679a\u4e3e\u9879\u7f16\u7801\u91cd\u590d\u4fe1\u606f")
    public List<String> getEnumItemRepeatInfos(@PathVariable(value="singleTaskYear") String singleTaskYear, @RequestBody EnumDataDTO param) {
        return this.paramQueryService.getEnumItemRepeatInfos(param, singleTaskYear);
    }

    @GetMapping(value={"/getTaskInfo/{formSchemeKey}"})
    @ApiOperation(value="\u6839\u636e\u62a5\u8868\u65b9\u6848key\u83b7\u53d6\u4efb\u52a1\u76f8\u5173\u4fe1\u606f")
    public CommonParamDTO getTaskInfo(@PathVariable(value="formSchemeKey") String formSchemeKey) {
        CommonParamDTO result = new CommonParamDTO();
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            DesignFormSchemeDefine designFormSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey);
            if (designFormSchemeDefine == null) {
                return result;
            }
            DesignTaskDefine designTaskDefine = this.designTimeViewController.queryTaskDefine(designFormSchemeDefine.getTaskKey());
            result.setKey(designTaskDefine.getKey());
            result.setTitle(designTaskDefine.getTitle());
            result.setCode(designTaskDefine.getTaskCode());
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/downloadLogInfo/{fileKey}"})
    @ApiOperation(value="\u4e0b\u8f7d\u5bfc\u5165\u65e5\u5fd7")
    public void downloadLogInfo(HttpServletResponse response, @PathVariable(value="fileKey") String fileKey) {
        ServletOutputStream outputStream = null;
        try {
            String fileName = this.fileServcie.getFileInfo(fileKey).getName();
            outputStream = response.getOutputStream();
            fileName = URLEncoder.encode(fileName, "UTF-8").replace("\\+", "%20");
            fileName = "attachment;filename=" + fileName;
            fileName = SingleSecurityUtils.cleanHeaderValue((String)fileName);
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
            response.setHeader("Content-disposition", fileName);
            this.fileServcie.downFile(fileKey, (OutputStream)outputStream);
            outputStream.flush();
        }
        catch (IOException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
    }

    @PostMapping(value={"/checkFormSchemeTitle"})
    @ApiOperation(value="\u6821\u9a8c\u62a5\u8868\u65b9\u6848\u540d\u79f0\u662f\u5426\u91cd\u590d")
    public boolean checkFormSchemeTitle(@RequestBody CreateParamDTO param) {
        List collect;
        List<FormSchemeVO> commonParamDTOS = this.paramQueryService.listFormScheme(param.getTaskKey());
        return !CollectionUtils.isEmpty(commonParamDTOS) && !(collect = commonParamDTOS.stream().map(CommonParamDTO::getTitle).collect(Collectors.toList())).contains(param.getFormSchemeTitle());
    }

    @GetMapping(value={"/getEnableTaskConfig/{taskKey}"})
    @ApiOperation(value="\u6839\u636e\u4efb\u52a1key\u83b7\u53d6\u4efb\u52a1\u76f8\u5173\u4fe1\u606f")
    public CommonParamDTO getEnableTaskConfig(@PathVariable(value="taskKey") String taskKey) {
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(taskKey);
        CommonParamDTO result = new CommonParamDTO();
        if (taskDefine != null) {
            if ("2.0".equalsIgnoreCase(taskDefine.getVersion())) {
                result.setCode(" @nr/taskList");
            } else if ("1.0".equalsIgnoreCase(taskDefine.getVersion())) {
                result.setCode("@nr/formDesigner");
            } else if (StringUtils.isEmpty((String)taskDefine.getVersion())) {
                result.setTitle("\u4efb\u52a1\u7248\u672c\u4e3a\u7a7a");
            }
        } else {
            result.setTitle("\u4efb\u52a1\u4e0d\u5b58\u5728\u65e0\u6cd5\u83b7\u53d6\u7248\u672c\u4fe1\u606f");
        }
        return result;
    }

    @GetMapping(value={"/getMapSchemeByKey/{mapSchemeKey}"})
    @ApiOperation(value="\u6839\u636eKEY\u83b7\u53d6\u6620\u5c04\u65b9\u6848\u4fe1\u606f")
    public CommonParamDTO getMapSchemeByKey(@PathVariable(value="mapSchemeKey") String mapSchemeKey) {
        MappingScheme mappingScheme = this.mapingSchemeService.getSchemeByKey(mapSchemeKey);
        CommonParamDTO result = new CommonParamDTO();
        if (mappingScheme != null) {
            result.setKey(mapSchemeKey);
            result.setCode(mappingScheme.getCode());
            result.setTitle(mappingScheme.getTitle());
        }
        return result;
    }
}

