/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.mapping2.service.MappingTransferService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.query.service.impl.QueryDimensionHelper
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONObject
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.migration.transferdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.mapping2.service.MappingTransferService;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.ExportParam;
import com.jiuqi.nr.migration.transferdata.bean.ImportJQRDTO;
import com.jiuqi.nr.migration.transferdata.bean.TransOrgInfo;
import com.jiuqi.nr.migration.transferdata.bean.TransferExportDTO;
import com.jiuqi.nr.migration.transferdata.common.TransferUtils;
import com.jiuqi.nr.migration.transferdata.dbservice.BatchQueryDataUtil;
import com.jiuqi.nr.migration.transferdata.dbservice.service.IQueryDataService;
import com.jiuqi.nr.migration.transferdata.service.IDataTransExportService;
import com.jiuqi.nr.migration.transferdata.service.IDataTransImportService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.query.service.impl.QueryDimensionHelper;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value={"/nr-transfer-jqr/jqr-data"})
public class DataTransController {
    @Autowired
    private RuntimeViewController runTimeViewController;
    @Autowired
    private IRuntimeFormService iRuntimeFormService;
    @Autowired
    private IQueryDataService queryDataExecutorImpl;
    @Autowired
    protected FieldRelationFactory fieldRelationFactory;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private QueryDimensionHelper dimensionHelper;
    @Autowired
    private MappingTransferService mappingTransferService;
    @Autowired
    private IDataTransImportService dataTransImportService;
    @Autowired
    private IDataTransExportService dataTransExportService;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;

    @GetMapping(value={"/queryFormulaCheckDes"})
    public void getCheckResults() {
        String taskKey = "af09df5f-00d0-486a-b10d-3a791d87f5f8";
        String formKey = "acdbd95b-4b5a-4d3a-b647-715f2b196216";
        String formSchemeKey = "27b25956-51cb-4e73-9dec-02c20e9bbe4c";
        String formulaSchemeKey = "a69bd7eb-e26d-4949-aad8-8003fe02bb1e";
        String contextEntityId = "MD_ORG_T1@ORG";
        String formGroupKey = "a1a2bd91-fcae-4531-80aa-0aa54180ed94";
        ArrayList<DimInfo> dims = new ArrayList<DimInfo>();
        dims.add(new DimInfo("DATATIME", "N", "2025N0001"));
        dims.add(new DimInfo("MD_ORG", contextEntityId, "QG"));
        DimensionCollection dimensionValueSets = TransferUtils.buildDimensionCollection(dims);
        CheckDesQueryParam param = new CheckDesQueryParam();
        List<String> formulaSchemeKeys = Arrays.asList(formulaSchemeKey);
        List<String> formKeys = Arrays.asList(formKey);
        param.setFormKey(formKeys);
        param.setFormSchemeKey(formSchemeKey);
        param.setDimensionCollection(dimensionValueSets);
        param.setFormulaSchemeKey(formulaSchemeKeys);
        param.setFormulaKey(null);
        List checkDesObjs = this.checkErrorDescriptionService.queryFormulaCheckDes(param);
        for (CheckDesObj checkDesObj : checkDesObjs) {
            System.out.println(checkDesObj.getFloatId());
        }
    }

    @PostMapping(value={"/old_export"})
    public void oldExportFile(@RequestBody String param, HttpServletResponse response) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ExportParam context = (ExportParam)objectMapper.readValue(param, ExportParam.class);
        this.dataTransExportService.downloadJQRDataPackage(context, response);
    }

    @PostMapping(value={"/export"})
    public void exportFile(@RequestBody String param, HttpServletResponse response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TransferExportDTO context = (TransferExportDTO)objectMapper.readValue(param, TransferExportDTO.class);
        this.dataTransExportService.executeSyncScheme(context, response);
    }

    @PostMapping(value={"/execute-scheme"})
    public void executeExportScheme(@RequestBody String key, HttpServletResponse response) throws Exception {
        this.dataTransExportService.executeSyncScheme(key, response);
    }

    @PostMapping(value={"/upload"})
    public ReturnObject uploadJQRDataPackage(@RequestParam(name="file") MultipartFile file) throws Exception {
        return this.dataTransImportService.upload(file);
    }

    @PostMapping(value={"/importFile"})
    public ReturnObject importJQRDataPackage(@RequestBody ImportJQRDTO vo) throws Exception {
        return this.dataTransImportService.importFile(vo);
    }

    @GetMapping(value={"/getMapping/{formSchemeId}"})
    public List<MappingScheme> getMappingScheme(@PathVariable String formSchemeId) {
        return this.mappingTransferService.getMappingSchemeByFormScheme(formSchemeId);
    }

    @GetMapping(value={"/getPeriod"})
    public String getPeriodObjByTask(String taskKey, String period) {
        try {
            if (StringUtils.hasLength(taskKey)) {
                SchemePeriodLinkDefine periodLinkDefine;
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
                JSONObject json = new JSONObject();
                if (!StringUtils.hasLength(period)) {
                    String toPeriod;
                    String fromPeriod = taskDefine.getFromPeriod();
                    if (!StringUtils.hasLength(fromPeriod)) {
                        fromPeriod = periodProvider.getPeriodCodeRegion()[0];
                    }
                    if (!StringUtils.hasLength(toPeriod = taskDefine.getToPeriod())) {
                        String[] periodCodeRegion = periodProvider.getPeriodCodeRegion();
                        toPeriod = periodCodeRegion[periodCodeRegion.length - 1];
                    }
                    json.put("begin", (Object)fromPeriod);
                    json.put("end", (Object)toPeriod);
                    if (taskDefine.getPeriodType() != PeriodType.CUSTOM) {
                        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskDefine.getPeriodType().type(), (int)taskDefine.getTaskPeriodOffset());
                        period = currentPeriod.toString();
                        json.put("curr", (Object)currentPeriod.toString());
                        json.put("currTitle", (Object)periodProvider.getPeriodTitle(currentPeriod));
                    } else {
                        IPeriodRow curPeriod = periodProvider.getCurPeriod();
                        period = curPeriod.getCode();
                        json.put("curr", (Object)curPeriod.getCode());
                        json.put("currTitle", (Object)curPeriod.getTitle());
                        String periodList = this.dimensionHelper.getPeriodListByMasterKey(taskDefine.getDateTime());
                        json.put("customArr", (Object)periodList);
                    }
                }
                if ((periodLinkDefine = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey)) != null) {
                    json.put("formSchemeId", (Object)periodLinkDefine.getSchemeKey());
                    if (StringUtils.hasLength(period)) {
                        json.put("currTitle", (Object)periodProvider.getPeriodTitle(period));
                    }
                } else {
                    List schemePeriodLinkDefines = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
                    if (!CollectionUtils.isEmpty(schemePeriodLinkDefines)) {
                        json.put("curr", (Object)((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey());
                        json.put("currTitle", (Object)periodProvider.getPeriodTitle(((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey()));
                        json.put("formSchemeId", (Object)((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getSchemeKey());
                    } else {
                        FormSchemeDefine formSchemeDefine = (FormSchemeDefine)this.runTimeViewController.queryFormSchemeByTask(taskKey).get(0);
                        json.put("formSchemeId", (Object)formSchemeDefine.getKey());
                    }
                }
                return json.toString();
            }
            return null;
        }
        catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value={"/testBatchQuery"})
    public void testBatchQueryData(@RequestBody ExportParam vo) {
        ArrayList<DimInfo> dimInfos = new ArrayList<DimInfo>();
        DimInfo dimInfoT = new DimInfo();
        dimInfoT.setName("DATATIME");
        dimInfoT.setEntityId(vo.getPeriodEntityId());
        dimInfoT.setValue(vo.getPeriodValue());
        dimInfos.add(dimInfoT);
        DimInfo dimInfoDW = new DimInfo();
        dimInfoDW.setName(vo.getDwEntityId().split("@")[0]);
        dimInfoDW.setEntityId(vo.getDwEntityId());
        List<String> orgCodes = vo.getOrgInfos().stream().map(TransOrgInfo::getCode).collect(Collectors.toList());
        dimInfoDW.setValues(orgCodes);
        dimInfos.add(dimInfoDW);
        BatchQueryDataUtil.testBatchQuery(vo.getTaskId(), vo.getFormSchemeId(), dimInfos, this.runTimeViewController, this.iRuntimeFormService, this.queryDataExecutorImpl, this.fieldRelationFactory);
    }
}

