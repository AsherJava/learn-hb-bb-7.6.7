/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  nr.midstore.core.definition.common.MidstoreLib
 *  nr.midstore.core.definition.db.MidstoreException
 *  nr.midstore.core.definition.dto.MidstoreBaseDataDTO
 *  nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO
 *  nr.midstore.core.definition.dto.MidstoreDimensionDTO
 *  nr.midstore.core.definition.dto.MidstoreFieldDTO
 *  nr.midstore.core.definition.dto.MidstoreOrgDataDTO
 *  nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO
 *  nr.midstore.core.definition.dto.MidstorePlanTaskDTO
 *  nr.midstore.core.definition.dto.MidstoreSchemeDTO
 *  nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO
 *  nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO
 *  nr.midstore.core.definition.service.IMidstoreBaseDataFieldService
 *  nr.midstore.core.definition.service.IMidstoreBaseDataService
 *  nr.midstore.core.definition.service.IMidstoreFieldService
 *  nr.midstore.core.definition.service.IMidstoreOrgDataFieldService
 *  nr.midstore.core.definition.service.IMidstoreOrgDataService
 *  nr.midstore.core.definition.service.IMidstoreSchemeGroupService
 *  nr.midstore.core.definition.service.IMidstoreSchemeInfoService
 *  nr.midstore.core.definition.service.IMidstoreSchemeService
 *  nr.midstore.core.job.service.IMidstorePlanRegService
 *  nr.midstore.core.util.IMidstoreDimensionService
 */
package nr.midstore.design.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import nr.midstore.core.definition.common.MidstoreLib;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.definition.dto.MidstoreBaseDataDTO;
import nr.midstore.core.definition.dto.MidstoreBaseDataFieldDTO;
import nr.midstore.core.definition.dto.MidstoreDimensionDTO;
import nr.midstore.core.definition.dto.MidstoreFieldDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataDTO;
import nr.midstore.core.definition.dto.MidstoreOrgDataFieldDTO;
import nr.midstore.core.definition.dto.MidstorePlanTaskDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeGroupDTO;
import nr.midstore.core.definition.dto.MidstoreSchemeInfoDTO;
import nr.midstore.core.definition.service.IMidstoreBaseDataFieldService;
import nr.midstore.core.definition.service.IMidstoreBaseDataService;
import nr.midstore.core.definition.service.IMidstoreFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataFieldService;
import nr.midstore.core.definition.service.IMidstoreOrgDataService;
import nr.midstore.core.definition.service.IMidstoreSchemeGroupService;
import nr.midstore.core.definition.service.IMidstoreSchemeInfoService;
import nr.midstore.core.definition.service.IMidstoreSchemeService;
import nr.midstore.core.job.service.IMidstorePlanRegService;
import nr.midstore.core.util.IMidstoreDimensionService;
import nr.midstore.design.domain.CommonParamDTO;
import nr.midstore.design.domain.ExchangeSchemeDTO;
import nr.midstore.design.domain.SchemeBaseDTO;
import nr.midstore.design.domain.SchemeBaseDataDTO;
import nr.midstore.design.domain.SchemeBaseDataFieldDTO;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.domain.SchemeGroupDTO;
import nr.midstore.design.domain.SchemeInfoDTO;
import nr.midstore.design.domain.SchemeOrgDataFieldDTO;
import nr.midstore.design.domain.SchemeOrgDataItemDTO;
import nr.midstore.design.service.IFileReadService;
import nr.midstore.design.vo.ExchangeSchemeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileReadServiceImpl
implements IFileReadService {
    private static final Logger logger = LoggerFactory.getLogger(FileReadServiceImpl.class);
    @Autowired
    private IMidstoreSchemeGroupService groupService;
    @Autowired
    private IMidstoreSchemeService schemeService;
    @Autowired
    private IMidstoreSchemeInfoService schemeInfoService;
    @Autowired
    private IMidstoreOrgDataService orgDataService;
    @Autowired
    private IMidstoreOrgDataFieldService orgDataFieldService;
    @Autowired
    private IMidstoreBaseDataService baseDataService;
    @Autowired
    private IMidstoreBaseDataFieldService baseDataFieldService;
    @Autowired
    private IMidstoreFieldService fieldService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IMidstorePlanRegService planService;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;
    @Autowired
    private IMidstorePlanRegService planRegService;
    @Autowired
    private IMidstoreDimensionService dimensionService;

    @Override
    public List<SchemeGroupDTO> listAllSchemeGroups() {
        MidstoreSchemeGroupDTO midstoreDataDTO = new MidstoreSchemeGroupDTO();
        return this.transSchemeGroupsFromDTO(this.groupService.list(midstoreDataDTO));
    }

    @Override
    public List<SchemeGroupDTO> listRootSchemeGroups() {
        ArrayList<SchemeGroupDTO> list2 = new ArrayList<SchemeGroupDTO>();
        MidstoreSchemeGroupDTO midstoreDataDTO = new MidstoreSchemeGroupDTO();
        List<SchemeGroupDTO> list = this.transSchemeGroupsFromDTO(this.groupService.list(midstoreDataDTO));
        for (SchemeGroupDTO data : list) {
            if (!StringUtils.isEmpty((String)data.getParent())) continue;
            list2.add(data);
        }
        return list2;
    }

    @Override
    public List<SchemeGroupDTO> listSchemeGroupsByParent(String parentGroupKey) {
        MidstoreSchemeGroupDTO midstoreDataDTO = new MidstoreSchemeGroupDTO();
        midstoreDataDTO.setParent(parentGroupKey);
        return this.transSchemeGroupsFromDTO(this.groupService.list(midstoreDataDTO));
    }

    @Override
    public List<SchemeBaseDTO> listAllMidstoreSchemes() {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        return this.transSchemeBaseFromDTO(this.schemeService.list(midstoreDataDTO));
    }

    @Override
    public List<SchemeBaseDTO> listMidstoreSchemesInGroup(String groupKey) {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        midstoreDataDTO.setGroupKey(groupKey);
        return this.transSchemeBaseFromDTO(this.schemeService.list(midstoreDataDTO));
    }

    @Override
    public List<ExchangeSchemeDTO> listSchemesInGroup(String groupKey) {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        midstoreDataDTO.setGroupKey(groupKey);
        return this.transSchemeFromDTO(this.schemeService.list(midstoreDataDTO));
    }

    @Override
    public List<ExchangeSchemeVO> listSchemeVOsInGroup(String groupKey) {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        midstoreDataDTO.setGroupKey(groupKey);
        return this.transSchemeVOFromDTO(this.schemeService.list(midstoreDataDTO));
    }

    @Override
    public ExchangeSchemeDTO listMidstoreSchemeByKey(String schemeKey) {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        midstoreDataDTO.setKey(schemeKey);
        List<ExchangeSchemeDTO> list2 = this.transSchemeFromDTO(this.schemeService.list(midstoreDataDTO));
        ExchangeSchemeDTO result = null;
        if (list2 != null && list2.size() > 0) {
            result = list2.get(0);
            SchemeInfoDTO schemeInfoDTO = this.getMidstoreSchemeInfo(schemeKey);
        }
        return result;
    }

    @Override
    public List<ExchangeSchemeDTO> listMidstoreSchemeByKeys(List<String> schemeKeys) {
        List<ExchangeSchemeDTO> list2 = this.transSchemeFromDTO(this.schemeService.list(schemeKeys));
        return list2;
    }

    @Override
    public List<ExchangeSchemeDTO> listMidstoreSchemeBytablePrefix(String tablePrefix) {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        midstoreDataDTO.setTablePrefix(tablePrefix);
        List<ExchangeSchemeDTO> list2 = this.transSchemeFromDTO(this.schemeService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public SchemeInfoDTO getMidstoreSchemeInfo(String schemeKey) {
        MidstoreSchemeInfoDTO midstoreDataDTO = new MidstoreSchemeInfoDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List list = this.schemeInfoService.list(midstoreDataDTO);
        List<SchemeInfoDTO> list2 = this.transSchemeInfoFromDTO(this.schemeInfoService.list(midstoreDataDTO));
        SchemeInfoDTO result = null;
        if (list2 != null && list2.size() > 0) {
            result = list2.get(0);
            MidstoreSchemeInfoDTO schemeInfo = (MidstoreSchemeInfoDTO)list.get(0);
            MidstoreSchemeDTO scheme = this.schemeService.getByKey(schemeKey);
            try {
                TaskDefine task = this.runTimeViewController.queryTaskDefine(scheme.getTaskKey());
                if (task != null) {
                    String curPeriod = this.dimensionService.getCurPeriodByDate(task.getDateTime());
                    String priorPeriod = this.dimensionService.getPriorPeriodByDate(task.getDateTime());
                    String curPeriodTitle = this.dimensionService.getPeriodTitle(task.getDateTime(), curPeriod);
                    String priorPeriodTitle = this.dimensionService.getPeriodTitle(task.getDateTime(), priorPeriod);
                    result.getValues().put("curPeriod", curPeriod);
                    result.getValues().put("curPeriodTitle", curPeriodTitle);
                    result.getValues().put("priorPeriod", priorPeriod);
                    result.getValues().put("priorPeriodTitle", priorPeriodTitle);
                }
                if (schemeInfo.isUsePlanTask()) {
                    this.planService.regPlanTaskByMidstoreScheme(scheme, schemeInfo);
                }
            }
            catch (Exception e) {
                logger.error("\u6ce8\u518c\u8ba1\u5212\u4efb\u52a1\u5931\u8d25" + e.getMessage(), e);
            }
        }
        return result;
    }

    @Override
    public List<SchemeOrgDataItemDTO> listMidstoreOrgDataByScheme(String schemeKey) {
        MidstoreOrgDataDTO midstoreDataDTO = new MidstoreOrgDataDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List<SchemeOrgDataItemDTO> list2 = this.transSchemeOrgDataItemFromDTO(this.orgDataService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public List<SchemeOrgDataFieldDTO> listMidstoreOrgDataFieldByScheme(String schemeKey) {
        MidstoreOrgDataFieldDTO midstoreDataDTO = new MidstoreOrgDataFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List<SchemeOrgDataFieldDTO> list2 = this.transSchemeOrgDataFieldFromDTO(this.orgDataFieldService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public List<SchemeBaseDataDTO> listMidstoreBaseDataByScheme(String schemeKey) {
        MidstoreBaseDataDTO midstoreDataDTO = new MidstoreBaseDataDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List<SchemeBaseDataDTO> list2 = this.transSchemeBaseDataFromDTO(this.baseDataService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public List<SchemeBaseDataFieldDTO> listMidstoreBaseDataFieldByScheme(String schemeKey) {
        MidstoreBaseDataFieldDTO midstoreDataDTO = new MidstoreBaseDataFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List<SchemeBaseDataFieldDTO> list2 = this.transSchemeBaseDataFieldFromDTO(this.baseDataFieldService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public List<SchemeBaseDataFieldDTO> listMidstoreBaseDataFieldBySchemeAndBaseData(String schemeKey, String baseDataKey) {
        MidstoreBaseDataFieldDTO midstoreDataDTO = new MidstoreBaseDataFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        midstoreDataDTO.setBaseDataKey(baseDataKey);
        List<SchemeBaseDataFieldDTO> list2 = this.transSchemeBaseDataFieldFromDTO(this.baseDataFieldService.list(midstoreDataDTO));
        return list2;
    }

    @Override
    public List<CommonParamDTO> listMidstoreDataTableBySheme(String schemeKey) {
        MidstoreFieldDTO midstoreDataDTO = new MidstoreFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List list = this.fieldService.list(midstoreDataDTO);
        List<CommonParamDTO> list2 = new ArrayList<CommonParamDTO>();
        HashSet<String> dataTableKeys = new HashSet<String>();
        for (MidstoreFieldDTO field : list) {
            if (StringUtils.isEmpty((String)field.getSrcTableKey()) || dataTableKeys.contains(field.getSrcTableKey())) continue;
            DataTable dataTable = this.dataSchemeSevice.getDataTable(field.getSrcTableKey());
            CommonParamDTO tableDto = new CommonParamDTO();
            tableDto.setKey(field.getSrcTableKey());
            if (dataTable != null) {
                tableDto.setCode(dataTable.getCode());
                tableDto.setTitle(dataTable.getTitle());
            } else {
                tableDto.setCode("UnknowCode");
                tableDto.setTitle("\u672a\u77e5\u6307\u6807\u8868");
            }
            dataTableKeys.add(field.getSrcTableKey());
            list2.add(tableDto);
        }
        if (list2.size() > 0) {
            list2 = list2.stream().sorted(Comparator.comparing(CommonParamDTO::getCode)).collect(Collectors.toList());
        }
        return list2;
    }

    @Override
    public List<SchemeFieldDTO> listMidstoreFieldBySheme(String schemeKey) {
        List fieldList;
        MidstoreFieldDTO midstoreDataDTO = new MidstoreFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        List<SchemeFieldDTO> list2 = this.transSchemFieldFromDTO(this.fieldService.list(midstoreDataDTO));
        HashMap tableFieldMap = new HashMap();
        for (SchemeFieldDTO field : list2) {
            fieldList = null;
            if (tableFieldMap.containsKey(field.getSrcTableKey())) {
                fieldList = (List)tableFieldMap.get(field.getSrcTableKey());
            } else {
                fieldList = new ArrayList();
                tableFieldMap.put(field.getSrcTableKey(), fieldList);
            }
            fieldList.add(field);
        }
        for (String tableKey : tableFieldMap.keySet()) {
            if (StringUtils.isEmpty((String)tableKey)) continue;
            fieldList = (List)tableFieldMap.get(tableKey);
            DataTable table = this.dataSchemeSevice.getDataTable(tableKey);
            if (table == null) continue;
            HashSet<String> bizKeyList = new HashSet<String>();
            String[] bizKeys = table.getBizKeys();
            for (String fieldID : bizKeys) {
                bizKeyList.add(fieldID);
            }
            for (SchemeFieldDTO field : fieldList) {
                if (bizKeyList.contains(field.getSrcFieldKey())) {
                    field.getValues().put("isBizKeyField", true);
                    continue;
                }
                field.getValues().put("isBizKeyField", false);
            }
        }
        return list2;
    }

    @Override
    public List<SchemeFieldDTO> listMidstoreFieldByShemeAndTable(String schemeKey, String srcTableKey) {
        MidstoreFieldDTO midstoreDataDTO = new MidstoreFieldDTO();
        midstoreDataDTO.setSchemeKey(schemeKey);
        midstoreDataDTO.setSrcTableKey(srcTableKey);
        List<SchemeFieldDTO> list2 = this.transSchemFieldFromDTO(this.fieldService.list(midstoreDataDTO));
        DataTable table = this.dataSchemeSevice.getDataTable(srcTableKey);
        if (table != null) {
            HashSet<String> bizKeyList = new HashSet<String>();
            String[] bizKeys = table.getBizKeys();
            for (String fieldID : bizKeys) {
                bizKeyList.add(fieldID);
            }
            for (SchemeFieldDTO field : list2) {
                if (bizKeyList.contains(field.getSrcFieldKey())) {
                    field.getValues().put("isBizKeyField", true);
                    continue;
                }
                field.getValues().put("isBizKeyField", false);
            }
        }
        return list2;
    }

    private List<SchemeGroupDTO> transSchemeGroupsFromDTO(List<MidstoreSchemeGroupDTO> list) {
        ArrayList<SchemeGroupDTO> newList = new ArrayList<SchemeGroupDTO>();
        for (MidstoreSchemeGroupDTO dto : list) {
            SchemeGroupDTO data = new SchemeGroupDTO();
            data.setKey(dto.getKey());
            data.setTitle(dto.getTitle());
            data.setParent(dto.getParent());
            data.setDesc(dto.getDesc());
            newList.add(data);
        }
        return newList;
    }

    private List<SchemeBaseDTO> transSchemeBaseFromDTO(List<MidstoreSchemeDTO> list) {
        ArrayList<SchemeBaseDTO> newList = new ArrayList<SchemeBaseDTO>();
        for (MidstoreSchemeDTO dto : list) {
            SchemeBaseDTO data = new SchemeBaseDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setTitle(dto.getTitle());
            data.setGroupKey(dto.getGroupKey());
            newList.add(data);
        }
        return newList;
    }

    private List<ExchangeSchemeDTO> transSchemeFromDTO(List<MidstoreSchemeDTO> list) {
        ArrayList<ExchangeSchemeDTO> newList = new ArrayList<ExchangeSchemeDTO>();
        for (MidstoreSchemeDTO dto : list) {
            ExchangeSchemeDTO data = new ExchangeSchemeDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setTitle(dto.getTitle());
            data.setGroupKey(dto.getGroupKey());
            data.setConfigKey(dto.getConfigKey());
            data.setDataBaseLink(dto.getDataBaseLink());
            data.setTablePrefix(dto.getTablePrefix());
            data.setStorageInfo(dto.getStorageInfo());
            data.setStorageMode(dto.getStorageMode());
            data.setTaskKey(dto.getTaskKey());
            data.setDesc(dto.getDesc());
            data.setSceneCode(dto.getSceneCode());
            data.setExchangeMode(dto.getExchangeMode());
            try {
                MidstoreDimensionDTO mDim = null;
                if (StringUtils.isNotEmpty((String)dto.getDimensions())) {
                    mDim = (MidstoreDimensionDTO)MidstoreLib.toObject((String)dto.getDimensions(), MidstoreDimensionDTO.class);
                }
                if (mDim != null) {
                    data.setDimensionSet(mDim.getDimensionSet());
                } else {
                    data.setDimensionSet(null);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            newList.add(data);
        }
        return newList;
    }

    private List<ExchangeSchemeVO> transSchemeVOFromDTO(List<MidstoreSchemeDTO> list) {
        ArrayList<ExchangeSchemeVO> newList = new ArrayList<ExchangeSchemeVO>();
        for (MidstoreSchemeDTO dto : list) {
            TaskDefine task;
            ExchangeSchemeVO data = new ExchangeSchemeVO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setTitle(dto.getTitle());
            data.setGroupKey(dto.getGroupKey());
            data.setConfigKey(dto.getConfigKey());
            data.setDataBaseLink(dto.getDataBaseLink());
            data.setTablePrefix(dto.getTablePrefix());
            data.setStorageInfo(dto.getStorageInfo());
            data.setStorageMode(dto.getStorageMode());
            data.setTaskKey(dto.getTaskKey());
            data.setDesc(dto.getDesc());
            data.setSceneCode(dto.getSceneCode());
            data.setExchangeMode(dto.getExchangeMode());
            try {
                MidstoreDimensionDTO mDim = null;
                if (StringUtils.isNotEmpty((String)dto.getDimensions())) {
                    mDim = (MidstoreDimensionDTO)MidstoreLib.toObject((String)dto.getDimensions(), MidstoreDimensionDTO.class);
                }
                if (mDim != null) {
                    data.setDimensionSet(mDim.getDimensionSet());
                } else {
                    data.setDimensionSet(null);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoService.getBySchemeKey(dto.getKey());
            if (schemeInfo != null) {
                data.setPublishState(schemeInfo.getPublishState());
            }
            if (StringUtils.isNotEmpty((String)dto.getTaskKey()) && (task = this.runTimeViewController.queryTaskDefine(dto.getTaskKey())) != null) {
                data.setTaskCode(task.getTaskCode());
                data.setTaskTitle(task.getTitle());
            }
            newList.add(data);
        }
        return newList;
    }

    private List<SchemeInfoDTO> transSchemeInfoFromDTO(List<MidstoreSchemeInfoDTO> list) {
        ArrayList<SchemeInfoDTO> newList = new ArrayList<SchemeInfoDTO>();
        for (MidstoreSchemeInfoDTO dto : list) {
            SchemeInfoDTO data = new SchemeInfoDTO();
            data.setKey(dto.getKey());
            data.setAllOrgData(dto.isAllOrgData());
            data.setAllOrgField(dto.isAllOrgField());
            data.setAutoClean(dto.isAutoClean());
            data.setUseUpdateOrg(dto.isUseUpdateOrg());
            data.setUsePlanTask(dto.isUsePlanTask());
            data.setDeleteEmpty(dto.isDeleteEmpty());
            data.setExcutePeriodType(dto.getExcutePeriodType());
            data.setExcutePeriod(dto.getExcutePeriod());
            data.setExcutePlanKey(dto.getExcutePlanKey());
            data.setExcutePlanInfo(dto.getExcutePlanInfo());
            data.setCleanMonth(dto.getCleanMonth());
            data.setCleanPlanKey(dto.getCleanPlanKey());
            data.setCleanPlanInfo(dto.getCleanPlanInfo());
            data.setPublishState(dto.getPublishState());
            data.setExchangeTaskKey(dto.getExchangeTaskKey());
            data.setDocumentKey(dto.getDocumentKey());
            data.setSchemeKey(dto.getSchemeKey());
            newList.add(data);
        }
        return newList;
    }

    private List<SchemeOrgDataItemDTO> transSchemeOrgDataItemFromDTO(List<MidstoreOrgDataDTO> list) {
        List<SchemeOrgDataItemDTO> newList = new ArrayList<SchemeOrgDataItemDTO>();
        for (MidstoreOrgDataDTO dto : list) {
            SchemeOrgDataItemDTO data = new SchemeOrgDataItemDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setOrgCode(dto.getOrgCode());
            data.setParentCode(dto.getParentCode());
            data.setTitle(dto.getTitle());
            data.setSchemeKey(dto.getSchemeKey());
            newList.add(data);
        }
        if (newList.size() > 0) {
            newList = newList.stream().sorted(Comparator.comparing(SchemeOrgDataItemDTO::getCode)).collect(Collectors.toList());
        }
        return newList;
    }

    private List<SchemeOrgDataFieldDTO> transSchemeOrgDataFieldFromDTO(List<MidstoreOrgDataFieldDTO> list) {
        List<SchemeOrgDataFieldDTO> newList = new ArrayList<SchemeOrgDataFieldDTO>();
        for (MidstoreOrgDataFieldDTO dto : list) {
            SchemeOrgDataFieldDTO data = new SchemeOrgDataFieldDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setSrcFieldKey(dto.getSrcFieldKey());
            data.setSrcOrgDataKey(dto.getSrcOrgDataKey());
            data.setTitle(dto.getTitle());
            data.setSchemeKey(dto.getSchemeKey());
            newList.add(data);
        }
        if (newList.size() > 0) {
            newList = newList.stream().sorted(Comparator.comparing(SchemeOrgDataFieldDTO::getCode)).collect(Collectors.toList());
        }
        return newList;
    }

    private List<SchemeBaseDataDTO> transSchemeBaseDataFromDTO(List<MidstoreBaseDataDTO> list) {
        List<SchemeBaseDataDTO> newList = new ArrayList<SchemeBaseDataDTO>();
        for (MidstoreBaseDataDTO dto : list) {
            SchemeBaseDataDTO data = new SchemeBaseDataDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setRemark(dto.getRemark());
            data.setSrcBaseDataKey(dto.getSrcBaseDataKey());
            data.setTitle(dto.getTitle());
            data.setSchemeKey(dto.getSchemeKey());
            BaseDataDefineDO baseDataDefine = this.queryBaseDatadefine(dto.getCode());
            if (baseDataDefine != null) {
                if (baseDataDefine.getStructtype() == 0) {
                    data.getValues().put("Structtype", "\u5217\u8868");
                } else if (baseDataDefine.getStructtype() == 1) {
                    data.getValues().put("Structtype", "\u5206\u7ec4\u5217\u8868");
                } else if (baseDataDefine.getStructtype() == 2) {
                    data.getValues().put("Structtype", "\u6811\u5f62");
                } else if (baseDataDefine.getStructtype() == 3) {
                    data.getValues().put("Structtype", "\u7ea7\u6b21\u6811");
                }
                if (StringUtils.isEmpty((String)baseDataDefine.getLevelcode())) {
                    data.getValues().put("LevelCode", "");
                    data.getValues().put("LevelLength", "");
                } else {
                    String levelCode = baseDataDefine.getLevelcode();
                    if (levelCode.contains("#")) {
                        int id = levelCode.indexOf("#");
                        String levelCode2 = levelCode.substring(0, id);
                        String levellength = levelCode.substring(id + 1, levelCode.length());
                        data.getValues().put("LevelCode", levelCode2);
                        data.getValues().put("LevelLength", levellength);
                    } else {
                        data.getValues().put("LevelCode", levelCode);
                        data.getValues().put("LevelLength", "");
                    }
                }
            }
            newList.add(data);
        }
        if (newList.size() > 0) {
            newList = newList.stream().sorted(Comparator.comparing(SchemeBaseDataDTO::getCode)).collect(Collectors.toList());
        }
        return newList;
    }

    private List<SchemeBaseDataFieldDTO> transSchemeBaseDataFieldFromDTO(List<MidstoreBaseDataFieldDTO> list) {
        List<SchemeBaseDataFieldDTO> newList = new ArrayList<SchemeBaseDataFieldDTO>();
        for (MidstoreBaseDataFieldDTO dto : list) {
            SchemeBaseDataFieldDTO data = new SchemeBaseDataFieldDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setSrcFieldKey(dto.getSrcFieldKey());
            data.setBaseDataKey(dto.getBaseDataKey());
            data.setSrcBaseDataKey(dto.getSrcBaseDataKey());
            data.setTitle(dto.getTitle());
            data.setSchemeKey(dto.getSchemeKey());
            newList.add(data);
        }
        if (newList.size() > 0) {
            newList = newList.stream().sorted(Comparator.comparing(SchemeBaseDataFieldDTO::getCode)).collect(Collectors.toList());
        }
        return newList;
    }

    private List<SchemeFieldDTO> transSchemFieldFromDTO(List<MidstoreFieldDTO> list) {
        List<SchemeFieldDTO> newList = new ArrayList<SchemeFieldDTO>();
        for (MidstoreFieldDTO dto : list) {
            DataField field;
            SchemeFieldDTO data = new SchemeFieldDTO();
            data.setKey(dto.getKey());
            data.setCode(dto.getCode());
            data.setSrcFieldKey(dto.getSrcFieldKey());
            data.setSrcTableKey(dto.getSrcTableKey());
            data.setRemark(dto.getRemark());
            data.setTitle(dto.getTitle());
            data.setSchemeKey(dto.getSchemeKey());
            data.setEncrypted(dto.isEncrypted());
            if (StringUtils.isNotEmpty((String)dto.getSrcFieldKey()) && (field = this.dataSchemeSevice.getDataField(dto.getSrcFieldKey())) != null) {
                data.getValues().put("DataFieldType", field.getDataFieldType());
                data.getValues().put("Precision", field.getPrecision());
                data.getValues().put("Decimal", field.getDecimal());
                DataTable dataTable = this.dataSchemeSevice.getDataTable(field.getDataTableKey());
                if (dataTable != null) {
                    data.getValues().put("DataTable", dataTable.getCode());
                } else {
                    data.getValues().put("DataTable", "");
                }
            }
            newList.add(data);
        }
        if (newList.size() > 0) {
            newList = newList.stream().sorted(Comparator.comparing(SchemeFieldDTO::getCode)).collect(Collectors.toList());
        }
        return newList;
    }

    private BaseDataDefineDO queryBaseDatadefine(String baseName) {
        BaseDataDefineDTO param = new BaseDataDefineDTO();
        param.setName(baseName);
        if (NpContextHolder.getContext() != null) {
            param.setTenantName(NpContextHolder.getContext().getTenant());
        }
        BaseDataDefineDO baseDefine = this.baseDataDefineClient.get(param);
        return baseDefine;
    }

    @Override
    public MidstorePlanTaskDTO getMidstorePlanTask(String planTaskKey) throws MidstoreException {
        return this.planRegService.queryPlanTask(planTaskKey);
    }

    @Override
    public boolean existPlanTask(String planTaskKey) {
        return this.planRegService.existPlanTask(planTaskKey);
    }

    @Override
    public String getMidstorePlanTaskGroup() {
        return this.planRegService.getMidstorePlanTaskGroup();
    }

    @Override
    public String getPlanTaskLogDetail(String planTaskKey) throws MidstoreException {
        return this.planRegService.getPlanTaskLogDetail(planTaskKey);
    }
}

