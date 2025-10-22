/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.upload.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import nr.single.para.compare.definition.ISingleCompareDataTaskLinkService;
import nr.single.para.compare.definition.ISingleCompareInfoService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.exception.SingleCompareException;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.upload.domain.BaseCompareDTO;
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
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUpdateServiceImpl
implements IFileUpdateService {
    private static final Logger log = LoggerFactory.getLogger(FileUpdateServiceImpl.class);
    @Autowired
    private ISingleCompareDataFMDMFieldService fmdmFieldService;
    @Autowired
    private ISingleCompareDataEnumService enumService;
    @Autowired
    private ISingleCompareDataEnumItemService enumItemService;
    @Autowired
    private ISingleCompareDataFormService formService;
    @Autowired
    private ISingleCompareDataFieldService fieldService;
    @Autowired
    private ISingleCompareDataTaskLinkService taskLinkService;
    @Autowired
    private ISingleCompareDataPrintItemService printItemService;
    @Autowired
    private ISingleCompareDataFormulaScemeService formulaSchemeService;
    @Autowired
    private ISingleCompareDataFormulaFormService formulaFormService;
    @Autowired
    private ISingleCompareInfoService infoService;

    @Override
    public void saveFMDMResult(String importKey, List<FMDMMappingDTO> fmdmMapping) throws SingleCompareException {
        if (fmdmMapping.size() > 0) {
            CompareDataFMDMFieldDTO fmdmFieldDTO = new CompareDataFMDMFieldDTO();
            fmdmFieldDTO.setInfoKey(importKey);
            List<CompareDataFMDMFieldDTO> list = this.fmdmFieldService.list(fmdmFieldDTO);
            Map<String, FMDMMappingDTO> collect = fmdmMapping.stream().collect(Collectors.toMap(CompareDataDO::getKey, FMDMMappingDTO2 -> FMDMMappingDTO2));
            for (CompareDataFMDMFieldDTO compare : list) {
                FMDMMappingDTO fmdmMappingDTO = collect.get(compare.getKey());
                if (fmdmMappingDTO == null) continue;
                BeanUtils.copyProperties(fmdmMappingDTO, compare);
            }
            this.fmdmFieldService.batchUpdate(list);
        }
    }

    @Override
    public void saveEnumDefineResult(String importKey, List<EnumDefineMappingDTO> enumDefineMapping) throws SingleCompareException {
        if (enumDefineMapping.size() > 0) {
            CompareDataEnumDTO enumDTO = new CompareDataEnumDTO();
            enumDTO.setInfoKey(importKey);
            List<CompareDataEnumDTO> list = this.enumService.list(enumDTO);
            Map<String, EnumDefineMappingDTO> collect = enumDefineMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, EnumDefineMappingDTO2 -> EnumDefineMappingDTO2));
            for (CompareDataEnumDTO compare : list) {
                EnumDefineMappingDTO enumDefineMappingDTO = collect.get(compare.getKey());
                if (enumDefineMappingDTO == null) continue;
                compare.setNetKey(enumDefineMappingDTO.getNetKey());
                compare.setNetCode(enumDefineMappingDTO.getNetCode());
                compare.setNetTitle(enumDefineMappingDTO.getNetTitle());
                compare.setUpdateType(enumDefineMappingDTO.getCoverType());
                compare.setCompareType(enumDefineMappingDTO.getEnumItemCompareType());
                compare.setItemChangeType(enumDefineMappingDTO.getEnumItemChangeType());
            }
            this.enumService.batchUpdate(list);
        }
    }

    @Override
    public void saveEnumDataResult(String importKey, EnumDataDTO enumDataDTO) throws SingleCompareException {
        if (enumDataDTO.getMappingList().size() > 0) {
            CompareDataEnumItemDTO enumItemDTO = new CompareDataEnumItemDTO();
            enumItemDTO.setInfoKey(importKey);
            enumItemDTO.setEnumCompareKey(enumDataDTO.getEnumCompareKey());
            List<CompareDataEnumItemDTO> list = this.enumItemService.list(enumItemDTO);
            Map<String, EnumDataMappingDTO> collect = enumDataDTO.getMappingList().stream().collect(Collectors.toMap(BaseCompareDTO::getKey, EnumDataMappingDTO2 -> EnumDataMappingDTO2));
            for (CompareDataEnumItemDTO compare : list) {
                EnumDataMappingDTO enumDataMappingDTO = collect.get(compare.getKey());
                if (enumDataMappingDTO == null) continue;
                compare.setNetCode(enumDataMappingDTO.getNetCode());
                compare.setNetTitle(enumDataMappingDTO.getNetTitle());
                compare.setUpdateType(enumDataMappingDTO.getCoverType());
            }
            this.enumItemService.batchUpdate(list);
        }
    }

    @Override
    public void saveFormResult(String importKey, List<FormMappingDTO> formMapping) throws SingleCompareException {
        if (formMapping.size() > 0) {
            CompareDataFormDTO formDTO = new CompareDataFormDTO();
            formDTO.setInfoKey(importKey);
            List<CompareDataFormDTO> list = this.formService.list(formDTO);
            Map<String, FormMappingDTO> collect = formMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, FormMappingDTO2 -> FormMappingDTO2));
            for (CompareDataFormDTO compare : list) {
                FormMappingDTO formMappingDTO = collect.get(compare.getKey());
                if (formMappingDTO == null) continue;
                compare.setUpdateType(formMappingDTO.getCoverType());
                compare.setCompareType(formMappingDTO.getCompareType());
            }
            this.formService.batchUpdate(list);
        }
    }

    @Override
    public void saveZBResult(String importKey, List<ZBMappingDTO> zbMapping) throws SingleCompareException {
        if (zbMapping.size() > 0) {
            CompareDataFieldDTO fieldDTO = new CompareDataFieldDTO();
            fieldDTO.setInfoKey(importKey);
            fieldDTO.setFormCompareKey(zbMapping.get(0).getFormCompareKey());
            List<CompareDataFieldDTO> list = this.fieldService.list(fieldDTO);
            Map<String, ZBMappingDTO> collect = zbMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, ZBMappingDTO2 -> ZBMappingDTO2));
            for (CompareDataFieldDTO compare : list) {
                ZBMappingDTO zbMappingDTO = collect.get(compare.getKey());
                if (zbMappingDTO == null) continue;
                compare.setNetMatchTitle(zbMappingDTO.getNetMatchTitle());
                compare.setNetCode(zbMappingDTO.getNetCode());
                compare.setNetTitle(zbMappingDTO.getNetTitle());
                compare.setUpdateType(zbMappingDTO.getCoverType());
                compare.setNetKey(zbMappingDTO.getNetKey());
                compare.setNetTableKey(zbMappingDTO.getOwnTableKey());
            }
            this.fieldService.batchUpdate(list);
        }
    }

    @Override
    public void saveTaskResult(String importKey, List<TaskLinkMappingDTO> taskLinkMapping) throws SingleCompareException {
        if (taskLinkMapping.size() > 0) {
            CompareDataTaskLinkDTO taskLinkDTO = new CompareDataTaskLinkDTO();
            taskLinkDTO.setInfoKey(importKey);
            List<CompareDataTaskLinkDTO> list = this.taskLinkService.list(taskLinkDTO);
            Map<String, TaskLinkMappingDTO> collect = taskLinkMapping.stream().collect(Collectors.toMap(TaskLinkMappingDTO::getKey, TaskLinkMappingDTO2 -> TaskLinkMappingDTO2));
            for (CompareDataTaskLinkDTO compare : list) {
                TaskLinkMappingDTO mappingDTO = collect.get(compare.getKey());
                if (mappingDTO == null) continue;
                compare.setNetTaskKey(mappingDTO.getNetTask());
                compare.setNetFormSchemeKey(mappingDTO.getNetFormScheme());
                compare.setNetCurrentExp(mappingDTO.getCurrentFormula());
                compare.setNetLinkExp(mappingDTO.getLinkTaskFormula());
                if (mappingDTO.getUpdateType() == null) continue;
                compare.setUpdateType(mappingDTO.getUpdateType());
            }
            this.taskLinkService.batchUpdate(list);
        }
    }

    @Override
    public void savePrintScheme(String importKey, List<PrintSchemeMappingDTO> printSchemeMapping) throws SingleCompareException {
        if (printSchemeMapping.size() > 0) {
            CompareDataPrintItemDTO printItemDTO = new CompareDataPrintItemDTO();
            printItemDTO.setInfoKey(importKey);
            printItemDTO.setSinglePrintScheme(printSchemeMapping.get(0).getSchemeKey());
            List<CompareDataPrintItemDTO> list = this.printItemService.list(printItemDTO);
            Map<String, PrintSchemeMappingDTO> collect = printSchemeMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, PrintSchemeMappingDTO2 -> PrintSchemeMappingDTO2));
            for (CompareDataPrintItemDTO compare : list) {
                PrintSchemeMappingDTO mappingDTO = collect.get(compare.getKey());
                if (mappingDTO == null) continue;
                compare.setUpdateType(mappingDTO.getCoverType());
            }
            this.printItemService.batchUpdate(list);
        }
    }

    @Override
    public void saveFormulaScheme(String importKey, List<FormulaSchemeMappingDTO> formulaSchemeMapping) throws SingleCompareException {
        if (formulaSchemeMapping.size() > 0) {
            CompareDataFormulaSchemeDTO schemQueryParam = new CompareDataFormulaSchemeDTO();
            schemQueryParam.setInfoKey(importKey);
            List<CompareDataFormulaSchemeDTO> list = this.formulaSchemeService.list(schemQueryParam);
            ArrayList<CompareDataFormulaSchemeDTO> updateList = new ArrayList<CompareDataFormulaSchemeDTO>();
            Map<String, FormulaSchemeMappingDTO> collect = formulaSchemeMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, FormulaSchemeMappingDTO2 -> FormulaSchemeMappingDTO2));
            for (CompareDataFormulaSchemeDTO compare : list) {
                FormulaSchemeMappingDTO mappingDTO = collect.get(compare.getKey());
                if (mappingDTO == null) continue;
                compare.setUpdateType(mappingDTO.getCoverType());
                compare.setNetTitle(mappingDTO.getNetTitle());
                updateList.add(compare);
            }
            if (!updateList.isEmpty()) {
                this.formulaSchemeService.batchUpdate(updateList);
            }
        }
    }

    @Override
    public void saveFormulaForm(String importKey, List<FormulaFormMappingDTO> formulaFormMapping) throws SingleCompareException {
        if (formulaFormMapping.size() > 0) {
            CompareDataFormulaFormDTO schemQueryParam = new CompareDataFormulaFormDTO();
            schemQueryParam.setInfoKey(importKey);
            List<CompareDataFormulaFormDTO> list = this.formulaFormService.list(schemQueryParam);
            ArrayList<CompareDataFormulaFormDTO> updateList = new ArrayList<CompareDataFormulaFormDTO>();
            Map<String, FormulaFormMappingDTO> collect = formulaFormMapping.stream().collect(Collectors.toMap(BaseCompareDTO::getKey, formulaFormMappingDTO -> formulaFormMappingDTO));
            for (CompareDataFormulaFormDTO compare : list) {
                FormulaFormMappingDTO mappingDTO = collect.get(compare.getKey());
                if (mappingDTO == null) continue;
                compare.setUpdateType(mappingDTO.getCoverType());
                updateList.add(compare);
            }
            if (!updateList.isEmpty()) {
                this.formulaFormService.batchUpdate(updateList);
            }
        }
    }

    @Override
    public void saveImportOption(String importKey, UploadFileOptionDTO option) throws SingleCompareException {
        CompareInfoDTO info = this.infoService.getByKey(importKey);
        if (info != null) {
            ParaCompareOption option2 = null;
            try {
                if (StringUtils.isNotEmpty((String)info.getOptionData())) {
                    ObjectMapper mapper = new ObjectMapper();
                    option2 = (ParaCompareOption)mapper.readValue(info.getOptionData(), ParaCompareOption.class);
                } else {
                    option2 = new ParaCompareOption();
                    option2.setCorpEntityId("");
                    option2.setDateEntityId("");
                    option2.setDimEntityIds("");
                    option2.setEnumCompareType(CompareContextType.CONTEXT_TITLE);
                    option2.setFieldContainForm(true);
                    option2.setOverWriteAll(false);
                    option2.setUploadBaseParam(true);
                    option2.setUploadFormula(true);
                    option2.setUploadPrint(true);
                    option2.setUploadQuery(true);
                    option2.setUseFormLevel(true);
                }
                option2.setOverWriteAll(option.getCoverParam());
                option2.setUploadBaseParam(option.getImportBaseParam());
                option2.setUploadFormula(option.getImportFormula());
                option2.setUploadPrint(option.getImportPrint());
                option2.setUploadQuery(option.getImportQuery());
                ObjectMapper mapper2 = new ObjectMapper();
                info.setOptionData(mapper2.writeValueAsString((Object)option2));
                this.infoService.update(info);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new SingleCompareException(e.getMessage(), e);
            }
        }
    }
}

