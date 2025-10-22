/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 *  com.jiuqi.util.StringUtils
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.para.upload.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import nr.single.para.compare.bean.ParaCompareOption;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareDataEnumItemDTO;
import nr.single.para.compare.definition.CompareDataFMDMFieldDTO;
import nr.single.para.compare.definition.CompareDataFieldDTO;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaFormDTO;
import nr.single.para.compare.definition.CompareDataFormulaSchemeDTO;
import nr.single.para.compare.definition.CompareDataPrintItemDTO;
import nr.single.para.compare.definition.CompareDataPrintSchemeDTO;
import nr.single.para.compare.definition.CompareDataTaskLinkDTO;
import nr.single.para.compare.definition.CompareInfoDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumItemService;
import nr.single.para.compare.definition.ISingleCompareDataEnumService;
import nr.single.para.compare.definition.ISingleCompareDataFMDMFieldService;
import nr.single.para.compare.definition.ISingleCompareDataFieldService;
import nr.single.para.compare.definition.ISingleCompareDataFormService;
import nr.single.para.compare.definition.ISingleCompareDataFormulaFormService;
import nr.single.para.compare.definition.ISingleCompareDataFormulaScemeService;
import nr.single.para.compare.definition.ISingleCompareDataPrintItemService;
import nr.single.para.compare.definition.ISingleCompareDataPrintScemeService;
import nr.single.para.compare.definition.ISingleCompareDataTaskLinkService;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.common.CompareChangeType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.compare.internal.defintion.CompareDataFieldDO;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDataMappingDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.FormulaFormMappingDTO;
import nr.single.para.upload.domain.FormulaSchemeMappingDTO;
import nr.single.para.upload.domain.PrintSchemeMappingDTO;
import nr.single.para.upload.domain.TaskLinkMappingDTO;
import nr.single.para.upload.domain.UploadFileOptionDTO;
import nr.single.para.upload.domain.UploadResultInfoDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileAnalysisService;
import nr.single.para.upload.service.IParamQueryService;
import nr.single.para.upload.vo.RepeatAndEmptyInfos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class FileAnalysisServiceImpl
implements IFileAnalysisService {
    private static final Logger log = LoggerFactory.getLogger(FileAnalysisServiceImpl.class);
    public static final Integer SINGLE = 0;
    public static final Integer NET = 1;
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmFieldService;
    @Autowired
    private ISingleCompareDataEnumService dataEnumService;
    @Autowired
    private ISingleCompareDataEnumItemService dataEnumItemService;
    @Autowired
    private ISingleCompareDataFormService dataFormService;
    @Autowired
    private ISingleCompareDataFieldService dataFieldService;
    @Autowired
    private ISingleCompareDataTaskLinkService taskLinkService;
    @Autowired
    private ISingleCompareDataPrintItemService printItemService;
    @Autowired
    private ISingleCompareDataFormulaScemeService formulaSchemService;
    @Autowired
    private ISingleCompareDataFormulaFormService formulaFormService;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private DesignDataLinkDefineService designDataLinkDefineService;
    @Autowired
    private ISingleCompareDataPrintScemeService printScemeService;
    @Autowired
    private IParamQueryService paramQueryService;
    @Autowired
    private ISingleCompareInfoService infoService;

    @Override
    public List<FMDMMappingDTO> listFMDMResult(String importKey) {
        CompareDataFMDMFieldDTO fmdmFieldDTO = new CompareDataFMDMFieldDTO();
        fmdmFieldDTO.setInfoKey(importKey);
        List<CompareDataFMDMFieldDTO> list = this.fmdmFieldService.list(fmdmFieldDTO);
        return this.transformFMDMMappingDTO(list);
    }

    @Override
    public List<EnumDefineMappingDTO> listEnumDefineResult(String importKey) {
        CompareDataEnumDTO dataEnumDTO = new CompareDataEnumDTO();
        dataEnumDTO.setInfoKey(importKey);
        List<CompareDataEnumDTO> list = this.dataEnumService.list(dataEnumDTO);
        return this.transfromEnumDefine(list);
    }

    @Override
    public List<EnumDefineMappingDTO> listEnumDefineChangeResult(String importKey) {
        CompareDataEnumDTO dataEnumDTO = new CompareDataEnumDTO();
        dataEnumDTO.setInfoKey(importKey);
        List<CompareDataEnumDTO> list = this.dataEnumService.list(dataEnumDTO);
        ArrayList<CompareDataEnumDTO> collect2 = new ArrayList<CompareDataEnumDTO>();
        for (CompareDataEnumDTO enumCompare : list) {
            if (enumCompare.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME && enumCompare.getItemChangeType() != CompareChangeType.CHANGE_NOSAME) continue;
            collect2.add(enumCompare);
        }
        return this.transfromEnumDefine(collect2);
    }

    @Override
    public EnumDataDTO listEnumDataResult(String importKey, EnumDataDTO enumDataResultDTO) {
        CompareDataEnumItemDTO enumItemDTO = new CompareDataEnumItemDTO();
        enumItemDTO.setInfoKey(importKey);
        enumItemDTO.setEnumCompareKey(enumDataResultDTO.getEnumCompareKey());
        List<CompareDataEnumItemDTO> list = this.dataEnumItemService.list(enumItemDTO);
        List<EnumDataMappingDTO> mappingDTOS = this.transformEnumDataDTO(list);
        enumDataResultDTO.setMappingList(mappingDTOS);
        return enumDataResultDTO;
    }

    @Override
    public List<FormMappingDTO> listFormResult(String importKey) {
        CompareDataFormDTO dataFormDTO = new CompareDataFormDTO();
        dataFormDTO.setInfoKey(importKey);
        List<CompareDataFormDTO> list = this.dataFormService.list(dataFormDTO);
        return this.transfromFormMappingDTO(list);
    }

    @Override
    public List<FormMappingDTO> listFormChangeResult(String importKey, int changeType) {
        CompareDataFormDTO dataFormDTO = new CompareDataFormDTO();
        dataFormDTO.setInfoKey(importKey);
        List<FormMappingDTO> formDefines = this.listFormResult(importKey);
        List collect2 = formDefines.stream().filter(e -> e.getCompareContent() != CompareChangeType.CHANGE_FORMFIELDSAME).collect(Collectors.toList());
        ArrayList<FormMappingDTO> list2 = new ArrayList<FormMappingDTO>();
        if (changeType == 0) {
            list2.addAll(collect2);
        } else {
            HashSet<String> fieldOrFormChanges = new HashSet<String>();
            for (FormMappingDTO formDto : collect2) {
                fieldOrFormChanges.add(formDto.getKey());
            }
            List<FormMappingDTO> collect1 = formDefines.stream().filter(e -> e.getCoverType() != CompareUpdateType.UPDATE_UNOVER || e.getCoverType() != CompareUpdateType.UPDATE_IGNORE).collect(Collectors.toList());
            RepeatAndEmptyInfos zbEmptyInfo = this.getZBEmptyInfo(importKey, null, true, collect1);
            List<String> emptyInfos = zbEmptyInfo.getEmptyInfos();
            List<String> repeatInfos = zbEmptyInfo.getRepeatInfos();
            HashSet<String> zbNewFormKeys = new HashSet<String>();
            HashMap<String, CompareDataFieldDTO> saveFieldMaps = new HashMap<String, CompareDataFieldDTO>();
            CompareDataFieldDTO fieldDTO = new CompareDataFieldDTO();
            fieldDTO.setInfoKey(importKey);
            List<CompareDataFieldDTO> list = this.dataFieldService.list(fieldDTO);
            for (CompareDataFieldDTO field : list) {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)field.getNetKey())) {
                    if (saveFieldMaps.containsKey(field.getNetKey())) {
                        CompareDataFieldDTO oldField = (CompareDataFieldDTO)saveFieldMaps.get(field.getNetKey());
                        if (!zbNewFormKeys.contains(oldField.getFormCompareKey())) {
                            zbNewFormKeys.add(oldField.getFormCompareKey());
                        }
                        if (zbNewFormKeys.contains(field.getFormCompareKey())) continue;
                        zbNewFormKeys.add(field.getFormCompareKey());
                        continue;
                    }
                    saveFieldMaps.put(field.getNetKey(), field);
                }
                if (field.getChangeType() == CompareChangeType.CHANGE_FLAGTITLESAME && field.getUpdateType() == CompareUpdateType.UPDATA_USENET || zbNewFormKeys.contains(field.getFormCompareKey())) continue;
                zbNewFormKeys.add(field.getFormCompareKey());
            }
            for (FormMappingDTO form : collect1) {
                if (!emptyInfos.isEmpty() && emptyInfos.contains(form.getKey())) {
                    list2.add(form);
                    continue;
                }
                if (!repeatInfos.isEmpty() && repeatInfos.contains(form.getKey())) {
                    list2.add(form);
                    continue;
                }
                if (!zbNewFormKeys.isEmpty() && zbNewFormKeys.contains(form.getKey())) {
                    list2.add(form);
                    continue;
                }
                if (fieldOrFormChanges.isEmpty() || !fieldOrFormChanges.contains(form.getKey())) continue;
                list2.add(form);
            }
        }
        return list2;
    }

    @Override
    public List<ZBMappingDTO> listZBResult(String importKey, String formCompareKey, Integer singleRegionId, boolean needOwnTable) {
        CompareDataFieldDTO dataFieldDTO = new CompareDataFieldDTO();
        dataFieldDTO.setInfoKey(importKey);
        dataFieldDTO.setFormCompareKey(formCompareKey);
        if (singleRegionId != null) {
            dataFieldDTO.setSingleFloatingId(singleRegionId);
        }
        List<CompareDataFieldDTO> list = this.dataFieldService.list(dataFieldDTO);
        return this.transformZBMappingDTO(list, needOwnTable);
    }

    @Override
    public List<ZBMappingDTO> listAllZBResultByUpdateType(String importKey, CompareUpdateType type) {
        List<ZBMappingDTO> zbMappingDTOS = this.listAllZBResultByUpdateType2(importKey, type);
        zbMappingDTOS = zbMappingDTOS.stream().filter(zbMappingDTO -> zbMappingDTO.getRegionIndex() < 0).collect(Collectors.toList());
        return zbMappingDTOS;
    }

    private List<ZBMappingDTO> listAllZBResultByUpdateType2(String importKey, CompareUpdateType type) {
        CompareDataFieldDTO fieldDTO = new CompareDataFieldDTO();
        fieldDTO.setInfoKey(importKey);
        fieldDTO.setUpdateType(type);
        List<CompareDataFieldDTO> list = this.dataFieldService.list(fieldDTO);
        List<ZBMappingDTO> zbMappingDTOS = this.transformZBMappingDTO(list, false);
        return zbMappingDTOS;
    }

    @Override
    public List<ZBMappingDTO> listZBInfo(String importKey, ZBMappingDTO formMappingDTO) {
        List dataFields;
        List allLinksInForm;
        ArrayList<ZBMappingDTO> result = new ArrayList<ZBMappingDTO>();
        CompareDataFieldDTO dataFieldDTO = new CompareDataFieldDTO();
        dataFieldDTO.setInfoKey(importKey);
        dataFieldDTO.setFormCompareKey(formMappingDTO.getFormCompareKey());
        List<CompareDataFieldDTO> list = this.dataFieldService.list(dataFieldDTO);
        CompareDataFormDTO dataFormDTO = new CompareDataFormDTO();
        dataFormDTO.setInfoKey(importKey);
        dataFormDTO.setKey(formMappingDTO.getFormCompareKey());
        List<CompareDataFormDTO> formList = this.dataFormService.list(dataFormDTO);
        if (CollectionUtils.isEmpty(formList)) {
            return null;
        }
        try {
            Map<Object, Object> dataLinkDefines;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)formList.get(0).getNetKey())) {
                allLinksInForm = this.designDataLinkDefineService.getAllLinksInForm(formList.get(0).getNetKey());
                dataLinkDefines = allLinksInForm.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<DataLinkDefine>(Comparator.comparing(DataLinkDefine::getLinkExpression))), ArrayList::new)).stream().collect(Collectors.toMap(DataLinkDefine::getLinkExpression, DataLinkDefine2 -> DataLinkDefine2));
                dataFields = this.dataSchemeService.getDataFields(new ArrayList<Object>(dataLinkDefines.keySet()));
            } else {
                allLinksInForm = new ArrayList();
                dataFields = new ArrayList();
                dataLinkDefines = new HashMap();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("\u83b7\u53d6\u8868\u5355\u4e0b\u6240\u6709\u6307\u6807\u5931\u8d25\uff01");
        }
        List collect = list.stream().filter(o -> !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)o.getNetKey())).collect(Collectors.toList());
        Map<String, CompareDataFieldDTO> compareFiled = collect.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<CompareDataFieldDTO>(Comparator.comparing(CompareDataDO::getNetKey))), ArrayList::new)).stream().collect(Collectors.toMap(CompareDataDO::getNetKey, CompareDataFieldDTO2 -> CompareDataFieldDTO2));
        Map<String, DesignDataField> formFieldCollect = dataFields.stream().collect(Collectors.toMap(Basic::getKey, DataField2 -> DataField2));
        Set<String> compare = compareFiled.keySet();
        Set<String> form = formFieldCollect.keySet();
        HashSet<String> difference = new HashSet<String>();
        difference.addAll(form);
        difference.removeAll(compare);
        HashMap<String, DesignDataTable> tableDic = new HashMap<String, DesignDataTable>();
        for (String fieldKey : difference) {
            ZBMappingDTO temp = new ZBMappingDTO();
            DesignDataField dataField = formFieldCollect.get(fieldKey);
            List links = allLinksInForm.stream().filter(o -> org.apache.commons.lang3.StringUtils.equals((CharSequence)o.getLinkExpression(), (CharSequence)fieldKey)).collect(Collectors.toList());
            temp.setNetKey(fieldKey);
            DesignDataTable table = null;
            if (tableDic.containsKey(dataField.getDataTableKey())) {
                table = (DesignDataTable)tableDic.get(dataField.getDataTableKey());
            } else {
                table = this.dataSchemeService.getDataTable(dataField.getDataTableKey());
                tableDic.put(dataField.getDataTableKey(), table);
            }
            if (table != null) {
                temp.setOwnTableCode(table.getCode());
                temp.setOwnTableKey(table.getKey());
                temp.setOwnTableTitle(table.getTitle());
            }
            for (DataLinkDefine dataLinkDefine : links) {
                temp.setNetPosY(dataLinkDefine.getPosY());
                temp.setNetPosX(dataLinkDefine.getPosX());
                temp.setNetCode(dataField.getCode());
                temp.setNetTitle(dataField.getTitle());
                result.add(temp);
            }
        }
        return result;
    }

    @Override
    public List<TaskLinkMappingDTO> listTaskLinkResult(String importKey) {
        CompareDataTaskLinkDTO taskLinkDTO = new CompareDataTaskLinkDTO();
        taskLinkDTO.setInfoKey(importKey);
        List<CompareDataTaskLinkDTO> list = this.taskLinkService.list(taskLinkDTO);
        return this.transformTaskLinkDTO(list);
    }

    @Override
    public List<PrintSchemeMappingDTO> listPrintSchemeResult(String importKey, PrintSchemeMappingDTO printSchemeMappingDTO) {
        CompareDataPrintItemDTO printItemDTO = new CompareDataPrintItemDTO();
        printItemDTO.setInfoKey(importKey);
        printItemDTO.setSinglePrintScheme(printSchemeMappingDTO.getSchemeKey());
        List<CompareDataPrintItemDTO> list = this.printItemService.list(printItemDTO);
        return this.transformPrintSchemeDTO(list);
    }

    @Override
    public UploadResultInfoDTO queryUploadInfo(String importKey) {
        return null;
    }

    @Override
    public UploadResultInfoDTO queryUploadResult(String importKey) {
        return null;
    }

    @Override
    public Map<Integer, String> getStyleByTypeAndFormComapreKey(String importKey, String formCompareKey) {
        CompareDataFormDTO dataFormDTO = new CompareDataFormDTO();
        dataFormDTO.setInfoKey(importKey);
        List<CompareDataFormDTO> list = this.dataFormService.list(dataFormDTO);
        Map<String, CompareDataFormDTO> collect = list.stream().collect(Collectors.toMap(CompareDataDO::getKey, CompareDataFormDTO2 -> CompareDataFormDTO2));
        CompareDataFormDTO dataForm = collect.get(formCompareKey);
        HashMap<Integer, String> result = new HashMap<Integer, String>();
        if (dataForm != null) {
            result.put(SINGLE, this.styleChangeFormat(dataForm.getSingleData()));
            result.put(NET, this.styleChangeFormat(dataForm.getNetData()));
        }
        return result;
    }

    @Override
    public List<String> getAllSinglePrintScheme(String importKey) {
        CompareDataPrintSchemeDTO printSchemeDTO = new CompareDataPrintSchemeDTO();
        printSchemeDTO.setInfoKey(importKey);
        List<CompareDataPrintSchemeDTO> list = this.printScemeService.list(printSchemeDTO);
        return list.stream().map(CompareDataDO::getSingleTitle).distinct().collect(Collectors.toList());
    }

    @Override
    public RepeatAndEmptyInfos getEnumItemEmptyInfo(String importKey, boolean isFilter, String singleTaskYear, List<EnumDefineMappingDTO> enumDefineMappingDTOS) {
        ArrayList<String> repeatResult = new ArrayList<String>();
        ArrayList<String> emptyResult = new ArrayList<String>();
        List<EnumDefineMappingDTO> collect = enumDefineMappingDTOS;
        if (isFilter) {
            collect = enumDefineMappingDTOS.stream().filter(e -> e.getCoverType() != CompareUpdateType.UPDATE_IGNORE && e.getCoverType() != CompareUpdateType.UPDATE_UNOVER).collect(Collectors.toList());
        }
        block0: for (EnumDefineMappingDTO enumDefine : collect) {
            List<String> enumItemRepeatInfos;
            EnumDataDTO enumDataDTO = new EnumDataDTO();
            enumDataDTO.setEnumCompareKey(enumDefine.getKey());
            EnumDataDTO enumDataDTO1 = this.listEnumDataResult(importKey, enumDataDTO);
            enumDataDTO1.setEnumCompareKey(enumDefine.getNetCode());
            if (enumDefine.getCoverType() != CompareUpdateType.UPDATE_NEW && !CollectionUtils.isEmpty(enumItemRepeatInfos = this.paramQueryService.getEnumItemRepeatInfos(enumDataDTO1, singleTaskYear))) {
                repeatResult.add(enumDefine.getKey());
            }
            for (EnumDataMappingDTO enumItem : enumDataDTO1.getMappingList()) {
                CompareUpdateType updateType = enumItem.getCoverType();
                if (updateType != CompareUpdateType.UPDATE_RECODE && updateType != CompareUpdateType.UPDATE_NEW || !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)enumItem.getNetCode()) && !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)enumItem.getNetTitle())) continue;
                emptyResult.add(enumDefine.getKey());
                continue block0;
            }
        }
        RepeatAndEmptyInfos result = new RepeatAndEmptyInfos();
        result.setRepeatInfos(repeatResult);
        result.setEmptyInfos(emptyResult);
        return result;
    }

    @Override
    public RepeatAndEmptyInfos getZBEmptyInfo(String importKey, String dataSchemeKey, boolean isFilter, List<FormMappingDTO> formMappingDTOS) {
        ArrayList<String> repeatResult = new ArrayList<String>();
        ArrayList<String> emptyResult = new ArrayList<String>();
        List<FormMappingDTO> collect = formMappingDTOS;
        if (isFilter) {
            collect = formMappingDTOS.stream().filter(e -> e.getCoverType() != CompareUpdateType.UPDATE_IGNORE && e.getCoverType() != CompareUpdateType.UPDATE_UNOVER).collect(Collectors.toList());
        }
        List<ZBMappingDTO> list = this.listAllZBResultByUpdateType(importKey, CompareUpdateType.UPDATE_NEW);
        ArrayList<DesignDataField> allDataField = new ArrayList();
        if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
            allDataField = this.dataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
        }
        block0: for (FormMappingDTO formDefine : collect) {
            List<ZBMappingDTO> zbMappingDTOS = this.listZBResult(importKey, formDefine.getKey(), null, false);
            List<ZBMappingDTO> hardRegion = zbMappingDTOS.stream().filter(e -> e.getRegionIndex() < 0).collect(Collectors.toList());
            List floatRegion = zbMappingDTOS.stream().filter(e -> e.getRegionIndex() > 0).collect(Collectors.toList());
            Map<Integer, List<ZBMappingDTO>> collect1 = floatRegion.stream().collect(Collectors.groupingBy(ZBMappingDTO::getRegionIndex));
            List<String> ZBRepeatInfos = this.paramQueryService.getZBRepeatInfos(importKey, dataSchemeKey, hardRegion, list, allDataField);
            ArrayList<String> ZDRepeatInfos = new ArrayList<String>();
            if (!collect1.isEmpty()) {
                for (Integer key : collect1.keySet()) {
                    List<String> temp = this.paramQueryService.getZBRepeatInfos(importKey, dataSchemeKey, collect1.get(key), null, null);
                    ZDRepeatInfos.addAll(temp);
                }
            }
            if (!CollectionUtils.isEmpty(ZBRepeatInfos) || !CollectionUtils.isEmpty(ZDRepeatInfos)) {
                repeatResult.add(formDefine.getKey());
            }
            for (ZBMappingDTO ZBDefine : zbMappingDTOS) {
                CompareUpdateType updateType = ZBDefine.getCoverType();
                if (updateType != CompareUpdateType.UPDATE_RECODE && updateType != CompareUpdateType.UPDATE_NEW && updateType != CompareUpdateType.UPDATE_APPOINT || !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)ZBDefine.getNetCode()) && !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)ZBDefine.getNetTitle())) continue;
                emptyResult.add(formDefine.getKey());
                continue block0;
            }
        }
        RepeatAndEmptyInfos result = new RepeatAndEmptyInfos();
        result.setRepeatInfos(repeatResult);
        result.setEmptyInfos(emptyResult);
        return result;
    }

    @Override
    public List<String> getEmptyInfo(String importKey, String singleTaskYear) {
        List<EnumDefineMappingDTO> enumDefines = this.listEnumDefineResult(importKey);
        List<EnumDefineMappingDTO> collect = enumDefines.stream().filter(e -> e.getCoverType() != CompareUpdateType.UPDATE_UNOVER || e.getCoverType() != CompareUpdateType.UPDATE_IGNORE).collect(Collectors.toList());
        List<FormMappingDTO> formDefines = this.listFormResult(importKey);
        RepeatAndEmptyInfos enumItemEmptyInfo = this.getEnumItemEmptyInfo(importKey, true, singleTaskYear, collect);
        List<String> emptyInfos1 = enumItemEmptyInfo.getEmptyInfos();
        List<String> repeatInfos1 = enumItemEmptyInfo.getRepeatInfos();
        emptyInfos1.removeAll(repeatInfos1);
        emptyInfos1.addAll(repeatInfos1);
        List<FormMappingDTO> collect1 = formDefines.stream().filter(e -> e.getCoverType() != CompareUpdateType.UPDATE_UNOVER || e.getCoverType() != CompareUpdateType.UPDATE_IGNORE).collect(Collectors.toList());
        RepeatAndEmptyInfos zbEmptyInfo = this.getZBEmptyInfo(importKey, null, true, collect1);
        List<String> emptyInfos = zbEmptyInfo.getEmptyInfos();
        List<String> repeatInfos = zbEmptyInfo.getRepeatInfos();
        emptyInfos.removeAll(repeatInfos);
        emptyInfos.addAll(repeatInfos);
        List<String> fmdmEmptyInfo = this.getFMDMEmptyInfo(importKey);
        fmdmEmptyInfo.addAll(emptyInfos);
        fmdmEmptyInfo.addAll(emptyInfos1);
        return fmdmEmptyInfo;
    }

    private List<String> getFMDMEmptyInfo(String importKey) {
        ArrayList<String> result = new ArrayList<String>();
        List<FMDMMappingDTO> fmdmMappingDTOS = this.listFMDMResult(importKey);
        for (FMDMMappingDTO fmdmMappingDTO : fmdmMappingDTOS) {
            CompareUpdateType updateType = fmdmMappingDTO.getUpdateType();
            if (updateType != CompareUpdateType.UPDATE_RECODE && updateType != CompareUpdateType.UPDATE_NEW || !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)fmdmMappingDTO.getNetCode()) && !org.apache.commons.lang3.StringUtils.isEmpty((CharSequence)fmdmMappingDTO.getNetTitle())) continue;
            result.add(fmdmMappingDTO.getKey());
        }
        return result;
    }

    private List<EnumDefineMappingDTO> transfromEnumDefine(List<CompareDataEnumDTO> list) {
        ArrayList<EnumDefineMappingDTO> result = new ArrayList<EnumDefineMappingDTO>();
        for (CompareDataEnumDTO compareData : list) {
            EnumDefineMappingDTO enumMappingDTO = new EnumDefineMappingDTO();
            enumMappingDTO.setKey(compareData.getKey());
            enumMappingDTO.setMatchKey(compareData.getMatchKey());
            enumMappingDTO.setNetKey(compareData.getNetKey());
            enumMappingDTO.setNetCode(compareData.getNetCode());
            enumMappingDTO.setNetTitle(compareData.getNetTitle());
            enumMappingDTO.setNetCodeLen(compareData.getNetCodeLen());
            enumMappingDTO.setSingleCode(compareData.getSingleCode());
            enumMappingDTO.setSingleTitle(compareData.getSingleTitle());
            enumMappingDTO.setSingleCodeLen(compareData.getSingleCodeLen());
            enumMappingDTO.setCompareContent(compareData.getChangeType());
            enumMappingDTO.setCoverType(compareData.getUpdateType());
            enumMappingDTO.setEnumItemCompareType(compareData.getCompareType());
            enumMappingDTO.setEnumItemChangeType(compareData.getItemChangeType());
            result.add(enumMappingDTO);
        }
        return result;
    }

    private List<FMDMMappingDTO> transformFMDMMappingDTO(List<CompareDataFMDMFieldDTO> list) {
        ArrayList<FMDMMappingDTO> result = new ArrayList<FMDMMappingDTO>();
        for (CompareDataFMDMFieldDTO compareData : list) {
            FMDMMappingDTO fmdmMappingDTO = new FMDMMappingDTO();
            BeanUtils.copyProperties(compareData, fmdmMappingDTO);
            result.add(fmdmMappingDTO);
        }
        return result;
    }

    private List<EnumDataMappingDTO> transformEnumDataDTO(List<CompareDataEnumItemDTO> list) {
        ArrayList<EnumDataMappingDTO> result = new ArrayList<EnumDataMappingDTO>();
        for (CompareDataEnumItemDTO enumItemDTO : list) {
            EnumDataMappingDTO MappingDTO = new EnumDataMappingDTO();
            MappingDTO.setKey(enumItemDTO.getKey());
            MappingDTO.setNetKey(enumItemDTO.getNetKey());
            MappingDTO.setMatchKey(enumItemDTO.getMatchKey());
            MappingDTO.setNetCode(enumItemDTO.getNetCode());
            MappingDTO.setNetTitle(enumItemDTO.getNetTitle());
            MappingDTO.setSingleCode(enumItemDTO.getSingleCode());
            MappingDTO.setSingleTitle(enumItemDTO.getSingleTitle());
            MappingDTO.setCompareContent(enumItemDTO.getChangeType());
            MappingDTO.setCoverType(enumItemDTO.getUpdateType());
            result.add(MappingDTO);
        }
        return result;
    }

    private List<FormMappingDTO> transfromFormMappingDTO(List<CompareDataFormDTO> list) {
        ArrayList<FormMappingDTO> result = new ArrayList<FormMappingDTO>();
        for (CompareDataFormDTO formDTO : list) {
            FormMappingDTO mappingDTO = new FormMappingDTO();
            mappingDTO.setKey(formDTO.getKey());
            mappingDTO.setNetKey(formDTO.getNetKey());
            mappingDTO.setMatchKey(formDTO.getMatchKey());
            mappingDTO.setNetCode(formDTO.getNetCode());
            mappingDTO.setNetTitle(formDTO.getNetTitle());
            mappingDTO.setSingleCode(formDTO.getSingleCode());
            mappingDTO.setSingleTitle(formDTO.getSingleTitle());
            mappingDTO.setCompareContent(formDTO.getChangeType());
            mappingDTO.setCoverType(formDTO.getUpdateType());
            mappingDTO.setCompareType(formDTO.getCompareType());
            mappingDTO.setIniFieldMap(formDTO.isIniFieldMap());
            result.add(mappingDTO);
        }
        return result;
    }

    private List<ZBMappingDTO> transformZBMappingDTO(List<CompareDataFieldDTO> list, boolean needOwnTable) {
        ArrayList<ZBMappingDTO> result = new ArrayList<ZBMappingDTO>();
        Map<Object, Object> collect = null;
        collect = needOwnTable ? list.stream().collect(Collectors.groupingBy(CompareDataFieldDO::getSingleFloatingIndex)) : new HashMap();
        for (CompareDataFieldDTO compare : list) {
            ZBMappingDTO mappingDTO;
            block3: {
                List compareDataFieldDTOS;
                block5: {
                    DesignDataTable dataTable;
                    block4: {
                        mappingDTO = new ZBMappingDTO();
                        mappingDTO.setKey(compare.getKey());
                        mappingDTO.setNetKey(compare.getNetKey());
                        mappingDTO.setMatchKey(compare.getMatchKey());
                        mappingDTO.setNetCode(compare.getNetCode());
                        mappingDTO.setNetTitle(compare.getNetTitle());
                        mappingDTO.setSingleCode(compare.getSingleCode());
                        mappingDTO.setSingleTitle(compare.getSingleTitle());
                        mappingDTO.setCompareContent(compare.getChangeType());
                        mappingDTO.setCoverType(compare.getUpdateType());
                        mappingDTO.setSingleFloatingId(compare.getSingleFloatingId());
                        mappingDTO.setRegionIndex(compare.getSingleFloatingIndex());
                        mappingDTO.setSinglePosX(compare.getSinglePosX());
                        mappingDTO.setSinglePosY(compare.getSinglePosY());
                        mappingDTO.setNetPosX(compare.getNetPosX());
                        mappingDTO.setNetPosY(compare.getNetPosY());
                        mappingDTO.setNetMatchTitle(compare.getNetMatchTitle());
                        mappingDTO.setSingleMatchTitle(compare.getSingleMatchTitle());
                        mappingDTO.setOwnTableKey(compare.getNetTableKey());
                        mappingDTO.setFormCompareKey(compare.getFormCompareKey());
                        if (!needOwnTable) break block3;
                        if (!org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)compare.getNetTableKey())) break block4;
                        DesignDataTable dataTable2 = this.dataSchemeService.getDataTable(compare.getNetTableKey());
                        if (dataTable2 == null) break block3;
                        mappingDTO.setOwnTableCode(dataTable2.getCode());
                        mappingDTO.setOwnTableTitle(dataTable2.getTitle());
                        break block3;
                    }
                    if (!org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)compare.getNetKey())) break block5;
                    DesignDataField dataField = this.dataSchemeService.getDataField(compare.getNetKey());
                    if (dataField == null || (dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey())) == null) break block3;
                    mappingDTO.setOwnTableCode(dataTable.getCode());
                    mappingDTO.setOwnTableTitle(dataTable.getTitle());
                    break block3;
                }
                if (compare.getSingleFloatingIndex() > 0 && compare.getUpdateType() == CompareUpdateType.UPDATE_NEW && !CollectionUtils.isEmpty(compareDataFieldDTOS = (List)collect.get(compare.getSingleFloatingIndex()))) {
                    for (CompareDataFieldDTO fieldDTO : compareDataFieldDTOS) {
                        DesignDataTable dataTable;
                        DesignDataField dataField;
                        if (!org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)fieldDTO.getNetKey()) || (dataField = this.dataSchemeService.getDataField(fieldDTO.getNetKey())) == null || (dataTable = this.dataSchemeService.getDataTable(dataField.getDataTableKey())) == null) continue;
                        mappingDTO.setOwnTableCode(dataTable.getCode());
                        mappingDTO.setOwnTableTitle(dataTable.getTitle());
                        break;
                    }
                }
            }
            result.add(mappingDTO);
        }
        return result;
    }

    private List<TaskLinkMappingDTO> transformTaskLinkDTO(List<CompareDataTaskLinkDTO> list) {
        ArrayList<TaskLinkMappingDTO> result = new ArrayList<TaskLinkMappingDTO>();
        for (CompareDataTaskLinkDTO compare : list) {
            TaskLinkMappingDTO mappingDTO = new TaskLinkMappingDTO();
            mappingDTO.setKey(compare.getKey());
            mappingDTO.setSingleCode(compare.getSingleCode());
            mappingDTO.setSingleTitle(compare.getSingleTitle());
            mappingDTO.setMatchKey(compare.getMatchKey());
            mappingDTO.setNetTask(compare.getNetTaskKey());
            mappingDTO.setNetFormScheme(compare.getNetFormSchemeKey());
            mappingDTO.setYear(compare.getSingleTaskYear());
            mappingDTO.setTaskType(compare.getSingleTaskType());
            mappingDTO.setCurrentFormula(compare.getSingleCurrentExp());
            mappingDTO.setLinkTaskFormula(compare.getSingleLinkExp());
            mappingDTO.setUpdateType(compare.getUpdateType());
            mappingDTO.setChangeType(compare.getChangeType());
            result.add(mappingDTO);
        }
        return result;
    }

    private List<PrintSchemeMappingDTO> transformPrintSchemeDTO(List<CompareDataPrintItemDTO> list) {
        ArrayList<PrintSchemeMappingDTO> result = new ArrayList<PrintSchemeMappingDTO>();
        for (CompareDataPrintItemDTO compare : list) {
            PrintSchemeMappingDTO mappingDTO = new PrintSchemeMappingDTO();
            mappingDTO.setKey(compare.getKey());
            mappingDTO.setNetKey(compare.getNetKey());
            mappingDTO.setMatchKey(compare.getMatchKey());
            mappingDTO.setSingleFormCode(compare.getSingleFormCode());
            mappingDTO.setSingleFormTitle(compare.getSingleFormTitile());
            mappingDTO.setNetFormCode(compare.getNetFormCode());
            mappingDTO.setNetFormTitle(compare.getNetFormTitle());
            mappingDTO.setCompareContent(compare.getChangeType());
            mappingDTO.setCoverType(compare.getUpdateType());
            result.add(mappingDTO);
        }
        return result;
    }

    private String styleChangeFormat(String style) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        Grid2Data result = null;
        if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)style)) {
            byte[] styleData = Convert.base64ToBytes((CharSequence)style);
            result = Grid2Data.bytesToGrid((byte[])styleData);
        }
        try {
            return mapper.writeValueAsString(result);
        }
        catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private List<FormulaSchemeMappingDTO> transFormulaSchemeschemeDTO(List<CompareDataFormulaSchemeDTO> list) {
        ArrayList<FormulaSchemeMappingDTO> result = new ArrayList<FormulaSchemeMappingDTO>();
        for (CompareDataFormulaSchemeDTO compare : list) {
            FormulaSchemeMappingDTO mappingDTO = new FormulaSchemeMappingDTO();
            mappingDTO.setKey(compare.getKey());
            mappingDTO.setNetKey(compare.getNetKey());
            mappingDTO.setMatchKey(compare.getMatchKey());
            mappingDTO.setSingleTitle(compare.getSingleTitle());
            mappingDTO.setNetTitle(compare.getNetTitle());
            mappingDTO.setCompareContent(compare.getChangeType());
            mappingDTO.setCoverType(compare.getUpdateType());
            result.add(mappingDTO);
        }
        return result;
    }

    @Override
    public List<FormulaSchemeMappingDTO> listFormulaSchemeResult(String importKey) {
        CompareDataFormulaSchemeDTO formulaItem = new CompareDataFormulaSchemeDTO();
        formulaItem.setInfoKey(importKey);
        List<CompareDataFormulaSchemeDTO> list = this.formulaSchemService.list(formulaItem);
        return this.transFormulaSchemeschemeDTO(list);
    }

    private List<FormulaFormMappingDTO> transFormulaFormschemeDTO(List<CompareDataFormulaFormDTO> list) {
        ArrayList<FormulaFormMappingDTO> result = new ArrayList<FormulaFormMappingDTO>();
        for (CompareDataFormulaFormDTO compare : list) {
            FormulaFormMappingDTO mappingDTO = new FormulaFormMappingDTO();
            mappingDTO.setKey(compare.getKey());
            mappingDTO.setNetKey(compare.getNetKey());
            mappingDTO.setMatchKey(compare.getMatchKey());
            mappingDTO.setSingleCode(compare.getMatchKey());
            mappingDTO.setSingleTitle(compare.getSingleTitle());
            mappingDTO.setNetCode(compare.getNetCode());
            mappingDTO.setNetTitle(compare.getNetTitle());
            mappingDTO.setCompareContent(compare.getChangeType());
            mappingDTO.setCoverType(compare.getUpdateType());
            result.add(mappingDTO);
        }
        return result;
    }

    @Override
    public List<FormulaFormMappingDTO> listFormulaFormResult(String importKey) {
        CompareDataFormulaFormDTO formulaForm = new CompareDataFormulaFormDTO();
        formulaForm.setInfoKey(importKey);
        List<CompareDataFormulaFormDTO> list = this.formulaFormService.list(formulaForm);
        return this.transFormulaFormschemeDTO(list);
    }

    @Override
    public UploadFileOptionDTO getImportOption(String importKey) throws SingleCompareException {
        UploadFileOptionDTO option = new UploadFileOptionDTO();
        ParaCompareOption option2 = null;
        CompareInfoDTO info = this.infoService.getByKey(importKey);
        if (info != null) {
            try {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty((CharSequence)info.getOptionData())) {
                    ObjectMapper mapper = new ObjectMapper();
                    option2 = (ParaCompareOption)mapper.readValue(info.getOptionData(), ParaCompareOption.class);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if (option2 != null) {
            option.setCoverParam(option2.isOverWriteAll());
            option.setImportBaseParam(option2.isUploadBaseParam());
            option.setImportFormula(option2.isUploadFormula());
            option.setImportPrint(option2.isUploadPrint());
            option.setImportQuery(option2.isUploadQuery());
        } else {
            option.setCoverParam(false);
            option.setImportBaseParam(true);
            option.setImportFormula(true);
            option.setImportPrint(true);
            option.setImportQuery(true);
        }
        return option;
    }
}

