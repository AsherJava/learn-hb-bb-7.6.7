/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeValidator
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package nr.single.para.upload.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.DataSchemeValidator;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nr.single.para.basedata.IBaseDataVerService;
import nr.single.para.compare.definition.CompareDataFormDTO;
import nr.single.para.compare.definition.ISingleCompareDataFormService;
import nr.single.para.compare.definition.common.CompareContextType;
import nr.single.para.compare.definition.common.CompareTableType;
import nr.single.para.compare.definition.common.CompareUpdateType;
import nr.single.para.compare.internal.defintion.CompareDataDO;
import nr.single.para.upload.domain.BaseCompareDTO;
import nr.single.para.upload.domain.CommonParamDTO;
import nr.single.para.upload.domain.DataLinkDTO;
import nr.single.para.upload.domain.EnumDataDTO;
import nr.single.para.upload.domain.EnumDataMappingDTO;
import nr.single.para.upload.domain.EnumDefineMappingDTO;
import nr.single.para.upload.domain.FMDMMappingDTO;
import nr.single.para.upload.domain.FormMappingDTO;
import nr.single.para.upload.domain.ZBMappingDTO;
import nr.single.para.upload.service.IFileAnalysisService;
import nr.single.para.upload.service.IParamQueryService;
import nr.single.para.upload.vo.DefaultCodeObject;
import nr.single.para.upload.vo.FormSchemeVO;
import nr.single.para.upload.vo.RepeatAndDefaultCodeVO;
import nr.single.para.upload.vo.TaskInfoVO;
import nr.single.para.upload.vo.TaskQueryVO;
import nr.single.para.upload.vo.ZBInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ParamQueryServiceImpl
implements IParamQueryService {
    private static final Logger log = LoggerFactory.getLogger(ParamQueryServiceImpl.class);
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataSchemeValidator dataSchemeValidator;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgCategoryClient orgCategoryClient;
    @Autowired
    private IFileAnalysisService fileAnalysisService;
    @Autowired
    private IBaseDataVerService baseVerService;
    @Autowired
    private ISingleCompareDataFormService formCompareService;

    @Override
    public List<TaskInfoVO> getTaskList(List<String> taskKeys) {
        ArrayList<TaskInfoVO> result = new ArrayList<TaskInfoVO>();
        List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
        for (DesignTaskDefine taskDefine : allTaskDefines) {
            if (!CollectionUtils.isEmpty(taskKeys) && !taskKeys.contains(taskDefine.getKey())) continue;
            TaskInfoVO taskInfo = new TaskInfoVO();
            taskInfo.setKey(taskDefine.getKey());
            taskInfo.setCode(taskDefine.getTaskCode());
            taskInfo.setTitle(taskDefine.getTitle());
            taskInfo.setDataSchemeKey(taskDefine.getDataScheme());
            result.add(taskInfo);
        }
        return result;
    }

    @Override
    public List<FormSchemeVO> listFormScheme(String taskKey) {
        ArrayList<FormSchemeVO> result = new ArrayList<FormSchemeVO>();
        List designFormSchemeDefines = null;
        try {
            designFormSchemeDefines = this.designTimeViewController.queryFormSchemeByTask(taskKey);
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u5f02\u5e38");
        }
        for (DesignFormSchemeDefine formSchemeDefine : designFormSchemeDefines) {
            FormSchemeVO commonParamDTO = new FormSchemeVO();
            commonParamDTO.setKey(formSchemeDefine.getKey());
            commonParamDTO.setCode(formSchemeDefine.getFormSchemeCode());
            commonParamDTO.setTitle(formSchemeDefine.getTitle());
            result.add(commonParamDTO);
            try {
                List periodLinks = this.designTimeViewController.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
                String minPeriod = "";
                String maxPeriod = "";
                for (DesignSchemePeriodLinkDefine link : periodLinks) {
                    commonParamDTO.getPeriodList().add(link.getPeriodKey());
                    if (StringUtils.isEmpty((String)minPeriod) || minPeriod.compareTo(link.getPeriodKey()) > 0) {
                        minPeriod = link.getPeriodKey();
                    }
                    if (!StringUtils.isEmpty((String)maxPeriod) && maxPeriod.compareTo(link.getPeriodKey()) >= 0) continue;
                    maxPeriod = link.getPeriodKey();
                }
                commonParamDTO.setFormPeriod(minPeriod);
                commonParamDTO.setToPeriod(maxPeriod);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException("\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5f02\u5e38" + e.getMessage());
            }
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listDataTable(String dataSchemeKey, Boolean tableType) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List dataTableByScheme = this.designDataSchemeService.getAllDataTable(dataSchemeKey);
        for (DesignDataTable dataTable : dataTableByScheme) {
            if (tableType != false ? dataTable.getDataTableType() != DataTableType.TABLE : dataTable.getDataTableType() != DataTableType.DETAIL) continue;
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(dataTable.getKey());
            commonParamDTO.setCode(dataTable.getCode());
            commonParamDTO.setTitle(dataTable.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listZBByTableCode(String tableCode) {
        String[] filter = new String[]{"MDCODE", "DATATIME", "BIZKEYORDER", "FLOATORDER"};
        List<String> list = Arrays.asList(filter);
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List dataFieldByTableCode = this.designDataSchemeService.getDataFieldByTableCode(tableCode);
        for (DesignDataField dataField : dataFieldByTableCode) {
            if (list.contains(dataField.getCode())) continue;
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(dataField.getKey());
            commonParamDTO.setCode(dataField.getCode());
            commonParamDTO.setTitle(dataField.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> getEnumList(String dataSchemeKey) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List<BaseDataDefineDO> list = this.getEnumDefineByDSKey(dataSchemeKey);
        for (BaseDataDefineDO baseDataDefineDO : list) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(baseDataDefineDO.getId().toString());
            commonParamDTO.setCode(baseDataDefineDO.getName());
            commonParamDTO.setTitle(baseDataDefineDO.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> getEnumListByPrefix(String enumPrefix) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List<BaseDataDefineDO> list = this.getEnumDefineByPerfix(enumPrefix);
        for (BaseDataDefineDO baseDataDefineDO : list) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(baseDataDefineDO.getId().toString());
            commonParamDTO.setCode(baseDataDefineDO.getName());
            commonParamDTO.setTitle(baseDataDefineDO.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listPeriod() {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        List iPeriodEntities = this.periodEngineService.getPeriodAdapter().getPeriodEntity();
        for (IPeriodEntity periodEntity : iPeriodEntities) {
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(periodEntity.getKey());
            commonParamDTO.setCode(periodEntity.getCode());
            commonParamDTO.setTitle(periodEntity.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<CommonParamDTO> listEntity(List<String> entityKeys) {
        ArrayList<CommonParamDTO> list = new ArrayList<CommonParamDTO>(entityKeys.size());
        for (String entityKey : entityKeys) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
            if (entityDefine == null) continue;
            CommonParamDTO paramDTO = new CommonParamDTO();
            paramDTO.setKey(entityDefine.getId());
            paramDTO.setCode(entityDefine.getCode());
            paramDTO.setTitle(entityDefine.getTitle());
            list.add(paramDTO);
        }
        return list;
    }

    @Override
    public List<BaseDataDO> listBaseData(String tableKey, String singleTaskYear) {
        if (StringUtils.isEmpty((String)tableKey)) {
            return new ArrayList<BaseDataDO>();
        }
        return this.getBaseDataByTable(tableKey, singleTaskYear);
    }

    @Override
    public List<ZB> queryZBInfo(ZBInfoVO infoVO) {
        OrgCategoryDO orgCategory = new OrgCategoryDO();
        orgCategory.setName(infoVO.getTableKey());
        PageVO list = this.orgCategoryClient.list(orgCategory);
        OrgCategoryDO orgCategoryDO = (OrgCategoryDO)list.getRows().get(0);
        List zbs = orgCategoryDO.getZbs();
        return zbs;
    }

    @Override
    public List<TaskQueryVO> queryAllTaskAndFormScheme() {
        List allTaskDefines = this.designTimeViewController.getAllTaskDefines();
        if (CollectionUtils.isEmpty(allTaskDefines)) {
            return null;
        }
        ArrayList<TaskQueryVO> list = new ArrayList<TaskQueryVO>(allTaskDefines.size());
        List designFormSchemeDefines = this.designTimeViewController.queryAllFormSchemeDefine();
        Map<String, List<DesignFormSchemeDefine>> taskToFormSchemeMap = designFormSchemeDefines.stream().collect(Collectors.groupingBy(FormSchemeDefine::getTaskKey));
        for (DesignTaskDefine taskDefine : allTaskDefines) {
            TaskQueryVO task = new TaskQueryVO();
            task.setTask(new CommonParamDTO(taskDefine.getKey(), taskDefine.getTaskCode(), taskDefine.getTitle()));
            List<DesignFormSchemeDefine> formSchemeDefines = taskToFormSchemeMap.get(taskDefine.getKey());
            if (!CollectionUtils.isEmpty(formSchemeDefines)) {
                for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    task.addChildren(new CommonParamDTO(formSchemeDefine.getKey(), formSchemeDefine.getFormSchemeCode(), formSchemeDefine.getTitle()));
                }
            }
            list.add(task);
        }
        return list;
    }

    @Override
    public void checkDataScheme(DesignDataScheme dataScheme) {
        this.dataSchemeValidator.checkDataScheme(dataScheme);
    }

    @Override
    public Map<String, String> queryOrgZbInfo(String orgCode) {
        IEntityModel entityModel;
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(orgCode);
        if (entityDefine == null) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u7684\u5b9e\u4f53\u5b9a\u4e49[%s]", orgCode));
        }
        try {
            entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u5b9e\u4f53[%s]\u7684\u6a21\u578b", orgCode));
        }
        List showFields = entityModel.getShowFields();
        Map<String, String> collect = showFields.stream().collect(Collectors.toMap(IModelDefineItem::getCode, ColumnModelDefine::getTitle));
        collect.remove("ORGCODE");
        collect.remove("PARENTCODE");
        collect.remove("CODE");
        return collect;
    }

    @Override
    public Map<String, String> queryZBsInForm(FMDMMappingDTO fmdmMappingDTO) {
        if (CompareTableType.TABLE_ORG == fmdmMappingDTO.getOwnerTableType()) {
            return this.queryOrgZbInfo(fmdmMappingDTO.getOwnerTableKey());
        }
        return this.designDataSchemeService.getDataFieldByTableCode(fmdmMappingDTO.getOwnerTableKey()).stream().collect(Collectors.toMap(Basic::getCode, Basic::getTitle));
    }

    @Override
    public Map<String, CommonParamDTO> queryZBsExInForm(FMDMMappingDTO fmdmMappingDTO) {
        HashMap<String, CommonParamDTO> result = new HashMap<String, CommonParamDTO>();
        if (CompareTableType.TABLE_ORG == fmdmMappingDTO.getOwnerTableType()) {
            Map<String, String> zBmap = this.queryOrgZbInfo(fmdmMappingDTO.getOwnerTableKey());
            for (String fieldCode : zBmap.keySet()) {
                CommonParamDTO newField = new CommonParamDTO();
                newField.setCode(fieldCode);
                newField.setTitle(zBmap.get(fieldCode));
                result.put(newField.getCode(), newField);
            }
        } else {
            List fields = this.designDataSchemeService.getDataFieldByTableCode(fmdmMappingDTO.getOwnerTableKey());
            for (DesignDataField field : fields) {
                CommonParamDTO newField = new CommonParamDTO();
                newField.setCode(field.getCode());
                newField.setTitle(field.getTitle());
                newField.setKey(field.getKey());
                result.put(field.getCode(), newField);
            }
        }
        return result;
    }

    @Override
    public Map<String, String> getMdInfoZbsInDataScheme(String dataSchemeKey) {
        String mdTableKey = this.designDataSchemeService.insertDataTableForMdInfo(dataSchemeKey);
        List fields = this.designDataSchemeService.getDataFieldByTable(mdTableKey);
        if (fields != null && !fields.isEmpty()) {
            return fields.stream().collect(Collectors.toMap(Basic::getCode, Basic::getTitle));
        }
        return new HashMap<String, String>();
    }

    @Override
    public Map<String, CommonParamDTO> getMdInfoZbsExInDataScheme(String dataSchemeKey) {
        String mdTableKey = this.designDataSchemeService.insertDataTableForMdInfo(dataSchemeKey);
        List fields = this.designDataSchemeService.getDataFieldByTable(mdTableKey);
        if (fields != null && !fields.isEmpty()) {
            HashMap<String, CommonParamDTO> result = new HashMap<String, CommonParamDTO>();
            for (DesignDataField field : fields) {
                CommonParamDTO newField = new CommonParamDTO();
                newField.setCode(field.getCode());
                newField.setTitle(field.getTitle());
                newField.setKey(field.getKey());
                result.put(field.getCode(), newField);
            }
            return result;
        }
        return new HashMap<String, CommonParamDTO>();
    }

    @Override
    public List<ZBMappingDTO> getOriginalZB(List<ZBMappingDTO> zbMappingDTOS) {
        for (ZBMappingDTO zbMappingDTO : zbMappingDTOS) {
            DesignDataTable dataTable;
            if (!StringUtils.isNotEmpty((String)zbMappingDTO.getMatchKey())) continue;
            String matchKey = zbMappingDTO.getMatchKey();
            if (StringUtils.isNotEmpty((String)matchKey)) {
                DesignDataField dataField = this.designDataSchemeService.getDataField(matchKey);
                if (dataField == null) continue;
                zbMappingDTO.setNetKey(dataField.getKey());
                zbMappingDTO.setNetCode(dataField.getCode());
                zbMappingDTO.setNetTitle(dataField.getTitle());
                DesignDataTable dataTable2 = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
                if (dataTable2 == null) continue;
                zbMappingDTO.setOwnTableCode(dataTable2.getCode());
                zbMappingDTO.setOwnTableTitle(dataTable2.getTitle());
                continue;
            }
            if (!StringUtils.isNotEmpty((String)zbMappingDTO.getOwnTableKey()) || (dataTable = this.designDataSchemeService.getDataTable(zbMappingDTO.getOwnTableKey())) == null) continue;
            zbMappingDTO.setOwnTableCode(dataTable.getCode());
            zbMappingDTO.setOwnTableTitle(dataTable.getTitle());
        }
        return zbMappingDTOS;
    }

    @Override
    public List<FMDMMappingDTO> getOriginalFMDM(List<FMDMMappingDTO> fmdmMappingDTOS) {
        List orgFMDM = fmdmMappingDTOS.stream().filter(fmdm -> fmdm.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue()).collect(Collectors.toList());
        String ownerTableKey = null;
        if (orgFMDM.size() > 0) {
            ownerTableKey = ((FMDMMappingDTO)orgFMDM.get(0)).getOwnerTableKey();
        }
        Map<String, IEntityAttribute> entities = null;
        if (StringUtils.isNotEmpty(ownerTableKey)) {
            List<IEntityAttribute> entitiesByOwnTable = this.getEntitiesByOwnTable(ownerTableKey);
            entities = entitiesByOwnTable.stream().collect(Collectors.toMap(IModelDefineItem::getID, IEntityAttribute2 -> IEntityAttribute2));
        }
        List fieldFMDM = fmdmMappingDTOS.stream().filter(fmdm -> fmdm.getOwnerTableType().getValue() != CompareTableType.TABLE_ORG.getValue()).collect(Collectors.toList());
        String ownerTableKey1 = null;
        if (fieldFMDM.size() > 0) {
            ownerTableKey1 = ((FMDMMappingDTO)fieldFMDM.get(0)).getOwnerTableKey();
        }
        Map<String, DesignDataField> fields = null;
        if (StringUtils.isNotEmpty(ownerTableKey1)) {
            List dataFieldByTable = this.designDataSchemeService.getDataFieldByTableCode(ownerTableKey1);
            fields = dataFieldByTable.stream().collect(Collectors.toMap(Basic::getKey, DesignDataField2 -> DesignDataField2));
        }
        for (FMDMMappingDTO fmdmMappingDTO : fmdmMappingDTOS) {
            DesignDataField dataField;
            IEntityAttribute attribute;
            if (fmdmMappingDTO.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue() && !CollectionUtils.isEmpty(entities) && (attribute = entities.get(fmdmMappingDTO.getMatchKey())) != null) {
                fmdmMappingDTO.setNetTitle(attribute.getTitle());
                fmdmMappingDTO.setNetCode(attribute.getCode());
                fmdmMappingDTO.setNetKey(attribute.getID());
            }
            if (fmdmMappingDTO.getOwnerTableType().getValue() != CompareTableType.TABLE_FIX.getValue() && fmdmMappingDTO.getOwnerTableType().getValue() != CompareTableType.TABLE_MDINFO.getValue() || CollectionUtils.isEmpty(fields) || (dataField = fields.get(fmdmMappingDTO.getMatchKey())) == null) continue;
            fmdmMappingDTO.setNetTitle(dataField.getTitle());
            fmdmMappingDTO.setNetCode(dataField.getCode());
            fmdmMappingDTO.setNetKey(dataField.getKey());
        }
        return fmdmMappingDTOS;
    }

    @Override
    public List<EnumDataMappingDTO> getOriginalEnumData(EnumDataDTO params, String singleTaskYeay) {
        if (StringUtils.isEmpty((String)params.getEnumCompareKey())) {
            return new ArrayList<EnumDataMappingDTO>();
        }
        List<BaseDataDO> datas = this.getBaseDataByTable(params.getEnumCompareKey(), singleTaskYeay);
        Map<String, BaseDataDO> collect = datas.stream().collect(Collectors.toMap(a -> a.getId().toString(), BaseDataDO2 -> BaseDataDO2));
        List<EnumDataMappingDTO> enumDataMappingDTOS = params.getMappingList();
        for (EnumDataMappingDTO enumDataMappingDTO : enumDataMappingDTOS) {
            BaseDataDO baseDataDO = collect.get(enumDataMappingDTO.getMatchKey());
            if (baseDataDO == null) continue;
            enumDataMappingDTO.setNetKey(baseDataDO.getId().toString());
            enumDataMappingDTO.setNetCode(baseDataDO.getCode());
            enumDataMappingDTO.setNetTitle(baseDataDO.getName());
        }
        return enumDataMappingDTOS;
    }

    @Override
    public List<EnumDefineMappingDTO> getOriginalEnumDefine(String dataSchemeKey, List<EnumDefineMappingDTO> params) {
        if (StringUtils.isEmpty((String)dataSchemeKey)) {
            return new ArrayList<EnumDefineMappingDTO>();
        }
        List<BaseDataDefineDO> enumDefines = this.getEnumDefineByDS(dataSchemeKey);
        Map<String, BaseDataDefineDO> collect = enumDefines.stream().collect(Collectors.toMap(a -> a.getId().toString(), BaseDataDefineDO2 -> BaseDataDefineDO2));
        for (EnumDefineMappingDTO enumDefineMappingDTO : params) {
            BaseDataDefineDO baseDataDefineDO = collect.get(enumDefineMappingDTO.getMatchKey());
            if (baseDataDefineDO == null) continue;
            enumDefineMappingDTO.setNetKey(baseDataDefineDO.getId().toString());
            enumDefineMappingDTO.setNetCode(baseDataDefineDO.getName());
            enumDefineMappingDTO.setNetTitle(baseDataDefineDO.getTitle());
        }
        return params;
    }

    @Override
    public List<FormMappingDTO> getOriginalForm(String formSchemeKey, List<FormMappingDTO> params) {
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return new ArrayList<FormMappingDTO>();
        }
        List formDefines = this.designTimeViewController.getAllFormDefinesInFormSchemeWithoutBinaryData(formSchemeKey);
        Map<String, DesignFormDefine> collect = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, DesignFormDefine2 -> DesignFormDefine2));
        for (FormMappingDTO formMappingDTO : params) {
            DesignFormDefine designFormDefine = collect.get(formMappingDTO.getMatchKey());
            if (designFormDefine == null) continue;
            formMappingDTO.setNetKey(designFormDefine.getKey());
            formMappingDTO.setNetCode(designFormDefine.getFormCode());
            formMappingDTO.setNetTitle(designFormDefine.getTitle());
        }
        return params;
    }

    private List<BaseDataDO> getBaseDataByTable(String tableKey, String singleTaskYear) {
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(tableKey);
        int year = Integer.parseInt(singleTaskYear);
        if (year > 1970 && year < 3000) {
            try {
                Date[] dates = this.baseVerService.getDateRegion(year);
                queryParam.setVersionDate(dates[0]);
            }
            catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(0));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO list = this.baseDataClient.list(queryParam);
        return list.getRows();
    }

    private List<IEntityAttribute> getEntitiesByOwnTable(String ownTableKey) {
        IEntityModel entityModel;
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(ownTableKey);
        if (entityDefine == null) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u7684\u5b9e\u4f53\u5b9a\u4e49[%s]", ownTableKey));
        }
        try {
            entityModel = this.entityMetaService.getEntityModel(entityDefine.getId());
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("\u627e\u4e0d\u5230\u5b9e\u4f53[%s]\u7684\u6a21\u578b", ownTableKey));
        }
        return entityModel.getShowFields();
    }

    private List<BaseDataDefineDO> getEnumDefineByDSKey(String dsKey) {
        if (StringUtils.isEmpty((String)dsKey)) {
            return new ArrayList<BaseDataDefineDO>();
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dsKey);
        return this.getEnumDefineByPerfix(dataScheme.getPrefix());
    }

    private List<BaseDataDefineDO> getEnumDefineByPerfix(String enumPrefix) {
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName("__default_tenant__");
        if (StringUtils.isEmpty((String)enumPrefix)) {
            baseDataDefineDTO.setSearchKey("MD_");
        } else {
            baseDataDefineDTO.setSearchKey("MD_" + enumPrefix + "_");
        }
        PageVO list = client.list(baseDataDefineDTO);
        return list.getRows();
    }

    private List<BaseDataDefineDO> getEnumDefineByDS(String dsKey) {
        if (StringUtils.isEmpty((String)dsKey)) {
            return new ArrayList<BaseDataDefineDO>();
        }
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dsKey);
        BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        baseDataDefineDTO.setTenantName("__default_tenant__");
        baseDataDefineDTO.setSearchKey("MD_" + dataScheme.getPrefix() + "_");
        PageVO list = client.list(baseDataDefineDTO);
        return list.getRows();
    }

    @Override
    public List<String> getZBRepeatInfos(String infoKey, String dataSchemeKey, List<ZBMappingDTO> params, List<ZBMappingDTO> list, List<DesignDataField> fields) {
        List<String> repeatKeys = new ArrayList<String>();
        if (CollectionUtils.isEmpty(params)) {
            return repeatKeys;
        }
        params = params.stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).collect(Collectors.toList());
        List newCollect = params.stream().filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
        List appiont = params.stream().filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_APPOINT).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(newCollect) && CollectionUtils.isEmpty(appiont)) {
            return repeatKeys;
        }
        CompareDataFormDTO qeuryFormParam = new CompareDataFormDTO();
        qeuryFormParam.setKey(params.get(0).getFormCompareKey());
        List<CompareDataFormDTO> formCompares = this.formCompareService.list(qeuryFormParam);
        if (!formCompares.isEmpty() && formCompares.get(0).getCompareType() == CompareContextType.CONTEXT_GLOBALCODE) {
            return repeatKeys;
        }
        Map<String, DesignDataField> collect = params.get(0).getRegionIndex() < 0 ? fields.stream().collect(Collectors.toMap(Basic::getCode, DesignDataField2 -> DesignDataField2)) : this.getNetField(dataSchemeKey, params, false);
        Map<String, Long> collect2 = null;
        if (CollectionUtils.isEmpty(list) && params.get(0).getRegionIndex() < 0) {
            list = this.fileAnalysisService.listAllZBResultByUpdateType(infoKey, CompareUpdateType.UPDATE_NEW);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.removeAll(newCollect);
        }
        Map<String, Long> collect1 = newCollect.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        if (!CollectionUtils.isEmpty(list)) {
            collect2 = list.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        }
        for (ZBMappingDTO zbMappingDTO : newCollect) {
            if (!CollectionUtils.isEmpty(collect) && collect.containsKey(zbMappingDTO.getNetCode())) {
                repeatKeys.add(zbMappingDTO.getKey());
            }
            if (!CollectionUtils.isEmpty(collect1) && collect1.get(zbMappingDTO.getNetCode()) >= 2L) {
                repeatKeys.add(zbMappingDTO.getKey());
            }
            if (CollectionUtils.isEmpty(collect2) || collect2.get(zbMappingDTO.getNetCode()) == null) continue;
            repeatKeys.add(zbMappingDTO.getKey());
        }
        Map<String, Long> appiontcollect = appiont.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        for (ZBMappingDTO zbMappingDTO : appiont) {
            if (CollectionUtils.isEmpty(appiontcollect) || appiontcollect.get(zbMappingDTO.getNetCode()) < 2L) continue;
            repeatKeys.add(zbMappingDTO.getKey());
        }
        repeatKeys = repeatKeys.stream().distinct().collect(Collectors.toList());
        return repeatKeys;
    }

    private Map<String, DesignDataField> getNetField(String dataSchemeKey, List<ZBMappingDTO> params, boolean isGetDefault) {
        Map<String, DesignDataField> collect = null;
        if (!StringUtils.isEmpty((String)dataSchemeKey) && params.size() > 0) {
            List allDataField = null;
            if (params.get(0).getRegionIndex() < 0) {
                allDataField = this.designDataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
            } else {
                String netKey = null;
                for (ZBMappingDTO zbMappingDTO : params) {
                    if (isGetDefault && StringUtils.isNotEmpty((String)zbMappingDTO.getMatchKey())) {
                        netKey = zbMappingDTO.getMatchKey();
                        break;
                    }
                    CompareUpdateType coverType = zbMappingDTO.getCoverType();
                    if (coverType == CompareUpdateType.UPDATE_IGNORE || coverType == CompareUpdateType.UPDATE_NEW || !StringUtils.isNotEmpty((String)zbMappingDTO.getMatchKey())) continue;
                    netKey = zbMappingDTO.getMatchKey();
                    break;
                }
                if (StringUtils.isNotEmpty(netKey)) {
                    DesignDataField dataField = this.designDataSchemeService.getDataField(netKey);
                    DesignDataTable dataTable = this.designDataSchemeService.getDataTable(dataField.getDataTableKey());
                    allDataField = this.designDataSchemeService.getDataFieldByTable(dataTable.getKey());
                }
            }
            if (allDataField != null && !CollectionUtils.isEmpty(allDataField)) {
                collect = allDataField.stream().collect(Collectors.toMap(Basic::getCode, DesignDataField2 -> DesignDataField2));
            }
        }
        return collect;
    }

    @Override
    public List<DefaultCodeObject> getZBDefaultCode(String formCode, String dataSchemeKey, List<ZBMappingDTO> params) {
        ArrayList<DefaultCodeObject> result = new ArrayList<DefaultCodeObject>();
        if (!CollectionUtils.isEmpty(params)) {
            Map<String, DesignDataField> netField = this.getNetField(dataSchemeKey, params, true);
            List newCollect = params.stream().filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
            Map<String, Long> collect = newCollect.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
            for (ZBMappingDTO zbMappingDTO : params) {
                collect.remove(zbMappingDTO.getNetCode());
            }
            List<DefaultCodeObject> defaultCode = this.createDefaultCode(formCode, params, netField, collect);
            result.addAll(defaultCode);
        }
        return result;
    }

    private List<DefaultCodeObject> createDefaultCode(String formCode, List<ZBMappingDTO> params, Map<String, DesignDataField> collect, Map<String, Long> collect1) {
        ArrayList<DefaultCodeObject> result = new ArrayList<DefaultCodeObject>();
        block0: for (ZBMappingDTO zbMappingDTO : params) {
            String oldNetCode = zbMappingDTO.getRegionIndex() < 0 ? formCode + "_" + zbMappingDTO.getSingleCode() : zbMappingDTO.getSingleCode();
            String newNetCode = oldNetCode;
            for (int i = 1; i != 0; ++i) {
                if ((CollectionUtils.isEmpty(collect) || !collect.containsKey(newNetCode)) && collect1.get(newNetCode) == null) {
                    collect1.put(newNetCode, 1L);
                    DefaultCodeObject object = new DefaultCodeObject();
                    object.setKey(zbMappingDTO.getKey());
                    object.setDefaultCode(newNetCode);
                    result.add(object);
                    continue block0;
                }
                newNetCode = oldNetCode.concat("_" + i);
            }
        }
        return result;
    }

    @Override
    public List<String> getFormRepeatInfos(String formSchemeKey, List<FormMappingDTO> params) {
        ArrayList<String> result = new ArrayList<String>();
        if (CollectionUtils.isEmpty(params)) {
            return result;
        }
        Map<String, DesignFormDefine> collect = null;
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            List formDefines = this.designTimeViewController.getAllFormDefinesInFormSchemeWithoutBinaryData(formSchemeKey);
            collect = formDefines.stream().collect(Collectors.toMap(FormDefine::getFormCode, DesignFormDefine2 -> DesignFormDefine2));
        }
        params = params.stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
        Map<String, Long> collect1 = params.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        for (FormMappingDTO formMappingDTO : params) {
            if (collect != null && !CollectionUtils.isEmpty(collect) && collect.containsKey(formMappingDTO.getNetCode())) {
                result.add(formMappingDTO.getKey());
            }
            if (collect1.get(formMappingDTO.getNetCode()) < 2L) continue;
            result.add(formMappingDTO.getKey());
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public RepeatAndDefaultCodeVO getFMDMRepeatInfos(String dataSchemeKey, List<FMDMMappingDTO> params, List<FMDMMappingDTO> params2) {
        RepeatAndDefaultCodeVO result = new RepeatAndDefaultCodeVO();
        ArrayList<String> repeatKeys = new ArrayList<String>();
        if (CollectionUtils.isEmpty(params)) {
            return result;
        }
        params = params.stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).collect(Collectors.toList());
        List orgFMDM = params.stream().filter(fmdm -> fmdm.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue()).collect(Collectors.toList());
        String ownerTableKey = null;
        Map<String, Long> collect = null;
        if (orgFMDM.size() > 0) {
            for (FMDMMappingDTO fmdmMappingDTO : orgFMDM) {
                if (!StringUtils.isNotEmpty((String)fmdmMappingDTO.getOwnerTableKey())) continue;
                ownerTableKey = fmdmMappingDTO.getOwnerTableKey();
                break;
            }
            collect = orgFMDM.stream().filter(a -> a.getUpdateType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.groupingBy(CompareDataDO::getNetCode, Collectors.counting()));
        }
        Map<String, IEntityAttribute> entities = null;
        if (StringUtils.isNotEmpty(ownerTableKey)) {
            List<IEntityAttribute> entitiesByOwnTable = this.getEntitiesByOwnTable(ownerTableKey);
            entities = entitiesByOwnTable.stream().collect(Collectors.toMap(IModelDefineItem::getCode, IEntityAttribute2 -> IEntityAttribute2));
        }
        List fieldFMDM = params.stream().filter(fmdm -> fmdm.getOwnerTableType().getValue() != CompareTableType.TABLE_ORG.getValue()).collect(Collectors.toList());
        Map<String, Long> collect1 = null;
        if (fieldFMDM.size() > 0) {
            collect1 = fieldFMDM.stream().filter(a -> a.getUpdateType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.groupingBy(CompareDataDO::getNetCode, Collectors.counting()));
        }
        Map<String, DesignDataField> fields = null;
        if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
            List dataFieldByTable = this.designDataSchemeService.getAllDataFieldByKind(dataSchemeKey, new DataFieldKind[]{DataFieldKind.FIELD_ZB});
            fields = dataFieldByTable.stream().collect(Collectors.toMap(Basic::getCode, DesignDataField2 -> DesignDataField2));
        }
        List newCollect = params.stream().filter(a -> a.getUpdateType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
        for (FMDMMappingDTO fmdmMappingDTO : newCollect) {
            if (fmdmMappingDTO.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue()) {
                if (!CollectionUtils.isEmpty(entities) && entities.get(fmdmMappingDTO.getNetCode()) != null) {
                    repeatKeys.add(fmdmMappingDTO.getKey());
                }
                if (!CollectionUtils.isEmpty(collect) && collect.get(fmdmMappingDTO.getNetCode()) >= 2L) {
                    repeatKeys.add(fmdmMappingDTO.getKey());
                }
            }
            if (fmdmMappingDTO.getOwnerTableType().getValue() != CompareTableType.TABLE_FIX.getValue() && fmdmMappingDTO.getOwnerTableType().getValue() != CompareTableType.TABLE_MDINFO.getValue()) continue;
            if (!CollectionUtils.isEmpty(fields) && fields.get(fmdmMappingDTO.getNetCode()) != null) {
                repeatKeys.add(fmdmMappingDTO.getKey());
            }
            if (CollectionUtils.isEmpty(collect1) || collect1.get(fmdmMappingDTO.getNetCode()) < 2L) continue;
            repeatKeys.add(fmdmMappingDTO.getKey());
        }
        List appiont = params.stream().filter(a -> a.getUpdateType() == CompareUpdateType.UPDATE_APPOINT).collect(Collectors.toList());
        Map<String, Long> appiontCollect = appiont.stream().collect(Collectors.groupingBy(CompareDataDO::getNetCode, Collectors.counting()));
        for (Object fmdmMappingDTO : appiont) {
            if (CollectionUtils.isEmpty(appiontCollect) || appiontCollect.get(((CompareDataDO)fmdmMappingDTO).getNetCode()) < 2L) continue;
            repeatKeys.add(((CompareDataDO)fmdmMappingDTO).getKey());
        }
        List<String> repeatList = repeatKeys.stream().distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(params2)) {
            for (FMDMMappingDTO fmdmMappingDTO : params2) {
                if (fmdmMappingDTO.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue()) {
                    collect.remove(fmdmMappingDTO.getNetCode());
                    continue;
                }
                collect1.remove(fmdmMappingDTO.getNetCode());
            }
            List<DefaultCodeObject> defaultCode = this.getDefaultFMDMCode(params2, entities, fields, collect, collect1);
            result.setDefaultCodeList(defaultCode);
            List collect2 = params2.stream().map(CompareDataDO::getKey).collect(Collectors.toList());
            repeatList.removeAll(collect2);
        }
        result.setRepeatKeyList(repeatList);
        return result;
    }

    private List<DefaultCodeObject> getDefaultFMDMCode(List<FMDMMappingDTO> params, Map<String, IEntityAttribute> entities, Map<String, DesignDataField> fields, Map<String, Long> collect, Map<String, Long> collect1) {
        ArrayList<DefaultCodeObject> result = new ArrayList<DefaultCodeObject>();
        block0: for (FMDMMappingDTO fmdmMappingDTO : params) {
            DefaultCodeObject object;
            int i;
            String oldNetCode;
            String newNetCode = oldNetCode = fmdmMappingDTO.getSingleCode();
            if (fmdmMappingDTO.getOwnerTableType().getValue() == CompareTableType.TABLE_ORG.getValue()) {
                for (i = 1; i != 0; ++i) {
                    if (!(entities != null && entities.containsKey(newNetCode) || collect.get(newNetCode) != null)) {
                        collect.put(newNetCode, 1L);
                        object = new DefaultCodeObject();
                        object.setKey(fmdmMappingDTO.getKey());
                        object.setDefaultCode(newNetCode);
                        result.add(object);
                        continue block0;
                    }
                    newNetCode = oldNetCode.concat("_" + i);
                }
                continue;
            }
            for (i = 1; i != 0; ++i) {
                if (!(fields != null && fields.containsKey(newNetCode) || collect1.get(newNetCode) != null)) {
                    collect1.put(newNetCode, 1L);
                    object = new DefaultCodeObject();
                    object.setKey(fmdmMappingDTO.getKey());
                    object.setDefaultCode(newNetCode);
                    result.add(object);
                    continue block0;
                }
                newNetCode = oldNetCode.concat("_" + i);
            }
        }
        return result;
    }

    @Override
    public RepeatAndDefaultCodeVO getEnumDefineRepeatInfos(String dataSchemeKey, String prefix, List<EnumDefineMappingDTO> params, List<EnumDefineMappingDTO> params2) {
        List<BaseDataDefineDO> list;
        RepeatAndDefaultCodeVO result = new RepeatAndDefaultCodeVO();
        ArrayList<String> repeatKeys = new ArrayList<String>();
        if (CollectionUtils.isEmpty(params)) {
            return result;
        }
        Map<String, BaseDataDefineDO> collect = null;
        if (StringUtils.isNotEmpty((String)prefix)) {
            list = this.getEnumDefineByPerfix(prefix);
            collect = list.stream().collect(Collectors.toMap(BaseDataDefineDO::getName, BaseDataDefineDO2 -> BaseDataDefineDO2));
        } else if (StringUtils.isNotEmpty((String)dataSchemeKey)) {
            list = this.getEnumDefineByDS(dataSchemeKey);
            collect = list.stream().collect(Collectors.toMap(BaseDataDefineDO::getName, BaseDataDefineDO2 -> BaseDataDefineDO2));
        }
        List newparams = params.stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
        Map<String, Long> collect1 = newparams.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        for (EnumDefineMappingDTO enumDefineMappingDTO : newparams) {
            if (!CollectionUtils.isEmpty(collect) && collect.get(enumDefineMappingDTO.getNetCode()) != null) {
                repeatKeys.add(enumDefineMappingDTO.getKey());
            }
            if (CollectionUtils.isEmpty(collect1) || collect1.get(enumDefineMappingDTO.getNetCode()) < 2L) continue;
            repeatKeys.add(enumDefineMappingDTO.getKey());
        }
        List appiont = params.stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_APPOINT).collect(Collectors.toList());
        Map<String, Long> appiontCollect = appiont.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        for (Object enumDefineMappingDTO : appiont) {
            if (CollectionUtils.isEmpty(appiontCollect) || appiontCollect.get(((BaseCompareDTO)enumDefineMappingDTO).getNetCode()) < 2L) continue;
            repeatKeys.add(((BaseCompareDTO)enumDefineMappingDTO).getKey());
        }
        List<String> repeatList = repeatKeys.stream().distinct().collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(params2)) {
            for (EnumDefineMappingDTO enumMappingDTO : params2) {
                collect1.remove(enumMappingDTO.getNetCode());
            }
            List<DefaultCodeObject> defaultCode = this.getDefaultEnumCode(prefix, params2, collect, collect1);
            result.setDefaultCodeList(defaultCode);
            List collect2 = params2.stream().map(BaseCompareDTO::getKey).collect(Collectors.toList());
            repeatList.removeAll(collect2);
        }
        result.setRepeatKeyList(repeatList);
        return result;
    }

    private List<DefaultCodeObject> getDefaultEnumCode(String prefix, List<EnumDefineMappingDTO> params, Map<String, BaseDataDefineDO> collect, Map<String, Long> collect1) {
        ArrayList<DefaultCodeObject> result = new ArrayList<DefaultCodeObject>();
        block0: for (EnumDefineMappingDTO enumMappingDTO : params) {
            String oldNetCode = !enumMappingDTO.getSingleCode().equals("BBLX") ? (StringUtils.isNotEmpty((String)prefix) ? "MD_" + prefix + "_" + enumMappingDTO.getSingleCode() : "MD_" + enumMappingDTO.getSingleCode()) : (StringUtils.isNotEmpty((String)prefix) ? "MD_BBLX_" + prefix : "MD_BBLX");
            String newNetCode = oldNetCode;
            for (int i = 1; i != 0; ++i) {
                if (!(collect != null && collect.containsKey(newNetCode) || collect1.get(newNetCode) != null)) {
                    collect1.put(newNetCode, 1L);
                    DefaultCodeObject object = new DefaultCodeObject();
                    object.setKey(enumMappingDTO.getKey());
                    object.setDefaultCode(newNetCode);
                    result.add(object);
                    continue block0;
                }
                newNetCode = oldNetCode.concat("_" + i);
            }
        }
        return result;
    }

    @Override
    public List<String> getEnumItemRepeatInfos(EnumDataDTO params, String singleTaskYear) {
        ArrayList<String> result = new ArrayList<String>();
        if (CollectionUtils.isEmpty(params.getMappingList())) {
            return result;
        }
        List mappingList = params.getMappingList().stream().filter(a -> StringUtils.isNotEmpty((String)a.getNetCode())).filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_NEW).collect(Collectors.toList());
        List appiont = params.getMappingList().stream().filter(a -> a.getCoverType() == CompareUpdateType.UPDATE_APPOINT).collect(Collectors.toList());
        Map<String, Long> collect1 = mappingList.stream().collect(Collectors.groupingBy(a -> a.getNetCode(), Collectors.counting()));
        if (CollectionUtils.isEmpty(mappingList) && CollectionUtils.isEmpty(appiont)) {
            return result;
        }
        Map<String, BaseDataDO> collect = null;
        if (StringUtils.isNotEmpty((String)params.getEnumCompareKey())) {
            List<BaseDataDO> datas = this.getBaseDataByTable(params.getEnumCompareKey(), singleTaskYear);
            collect = datas.stream().collect(Collectors.toMap(BaseDataDO::getCode, BaseDataDO2 -> BaseDataDO2));
        }
        for (EnumDataMappingDTO enumDataMappingDTO : mappingList) {
            if (!CollectionUtils.isEmpty(collect) && collect.get(enumDataMappingDTO.getNetCode()) != null) {
                result.add(enumDataMappingDTO.getKey());
            }
            if (collect1.get(enumDataMappingDTO.getNetCode()) < 2L) continue;
            result.add(enumDataMappingDTO.getKey());
        }
        Map<String, Long> appiontcollect = appiont.stream().collect(Collectors.groupingBy(BaseCompareDTO::getNetCode, Collectors.counting()));
        for (EnumDataMappingDTO enumDTO : appiont) {
            if (CollectionUtils.isEmpty(appiontcollect) || appiontcollect.get(enumDTO.getNetCode()) < 2L) continue;
            result.add(enumDTO.getKey());
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<CommonParamDTO> checkEnums(List<String> codes) {
        ArrayList<CommonParamDTO> result = new ArrayList<CommonParamDTO>();
        for (String code : codes) {
            BaseDataDefineDTO basedataDefineDTO = new BaseDataDefineDTO();
            basedataDefineDTO.setName(code);
            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(basedataDefineDTO);
            if (baseDataDefineDO == null) continue;
            CommonParamDTO commonParamDTO = new CommonParamDTO();
            commonParamDTO.setKey(baseDataDefineDO.getId().toString());
            commonParamDTO.setCode(baseDataDefineDO.getName());
            commonParamDTO.setTitle(baseDataDefineDO.getTitle());
            result.add(commonParamDTO);
        }
        return result;
    }

    @Override
    public List<DataLinkDTO> getDataLinkByForm(String formKey) {
        ArrayList<DataLinkDTO> list = new ArrayList<DataLinkDTO>();
        HashMap<String, DesignFieldDefine> fieldMap = new HashMap<String, DesignFieldDefine>();
        HashMap<String, DesignDataTable> tableMap = new HashMap<String, DesignDataTable>();
        try {
            ArrayList<String> tableIds = new ArrayList<String>();
            List fields = this.designTimeViewController.getAllFieldsByLinksInForm(formKey);
            for (Object field : fields) {
                fieldMap.put(field.getKey(), (DesignFieldDefine)field);
                tableIds.add(field.getOwnerTableKey());
            }
            if (!tableIds.isEmpty()) {
                List tables = this.designDataSchemeService.getDataTables(tableIds);
                for (DesignDataTable table : tables) {
                    tableMap.put(table.getKey(), table);
                }
            }
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
        }
        List links = this.designTimeViewController.getAllLinksInForm(formKey);
        for (DesignDataLinkDefine link : links) {
            DataLinkDTO zb = new DataLinkDTO();
            zb.setKey(link.getKey());
            zb.setCode(link.getUniqueCode());
            zb.setTitle(link.getTitle());
            zb.setFormKey(formKey);
            zb.setRegionKey(link.getRegionKey());
            zb.setPosX(link.getPosX());
            zb.setPosY(link.getPosY());
            zb.setColNum(link.getColNum());
            zb.setRowNum(link.getRowNum());
            list.add(zb);
            if (link.getType() == DataLinkType.DATA_LINK_TYPE_FIELD) {
                zb.setFieldKey(link.getLinkExpression());
                if (!fieldMap.containsKey(link.getLinkExpression())) continue;
                DesignFieldDefine field = (DesignFieldDefine)fieldMap.get(link.getLinkExpression());
                zb.setFieldName(field.getCode());
                zb.setFieldTitle(field.getTitle());
                if (!tableMap.containsKey(field.getOwnerTableKey())) continue;
                DesignDataTable table = (DesignDataTable)tableMap.get(field.getOwnerTableKey());
                zb.setOwnTableCode(table.getCode());
                zb.setOwnTableKey(table.getKey());
                zb.setOwnTableTitle(table.getTitle());
                continue;
            }
            if (link.getType() != DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
            zb.setExpression(link.getLinkExpression());
        }
        return list;
    }
}

