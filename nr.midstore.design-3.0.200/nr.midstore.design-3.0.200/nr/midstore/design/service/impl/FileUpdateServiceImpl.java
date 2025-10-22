/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  nr.midstore.core.definition.bean.MidstoreResultObject
 *  nr.midstore.core.definition.common.ExcutePeriodType
 *  nr.midstore.core.definition.common.MidstoreLib
 *  nr.midstore.core.definition.common.PublishStateType
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
 *  nr.midstore.core.internal.definition.MidstoreBaseDataDO
 *  nr.midstore.core.internal.definition.MidstoreBaseDataFieldDO
 *  nr.midstore.core.internal.definition.MidstoreFieldDO
 *  nr.midstore.core.internal.definition.MidstoreOrgDataDO
 *  nr.midstore.core.internal.definition.MidstoreOrgDataFieldDO
 *  nr.midstore.core.job.service.IMidstorePlanRegService
 *  nr.midstore.core.param.service.IMistoreExchangeTaskService
 */
package nr.midstore.design.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.common.ExcutePeriodType;
import nr.midstore.core.definition.common.MidstoreLib;
import nr.midstore.core.definition.common.PublishStateType;
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
import nr.midstore.core.internal.definition.MidstoreBaseDataDO;
import nr.midstore.core.internal.definition.MidstoreBaseDataFieldDO;
import nr.midstore.core.internal.definition.MidstoreFieldDO;
import nr.midstore.core.internal.definition.MidstoreOrgDataDO;
import nr.midstore.core.internal.definition.MidstoreOrgDataFieldDO;
import nr.midstore.core.job.service.IMidstorePlanRegService;
import nr.midstore.core.param.service.IMistoreExchangeTaskService;
import nr.midstore.design.domain.ExchangeSchemeDTO;
import nr.midstore.design.domain.SchemeBaseDTO;
import nr.midstore.design.domain.SchemeBaseDataDTO;
import nr.midstore.design.domain.SchemeBaseDataFieldDTO;
import nr.midstore.design.domain.SchemeFieldDTO;
import nr.midstore.design.domain.SchemeGroupDTO;
import nr.midstore.design.domain.SchemeInfoDTO;
import nr.midstore.design.domain.SchemeOrgDataFieldDTO;
import nr.midstore.design.domain.SchemeOrgDataItemDTO;
import nr.midstore.design.service.IFileUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUpdateServiceImpl
implements IFileUpdateService {
    private static final Logger log = LoggerFactory.getLogger(FileUpdateServiceImpl.class);
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
    private IMidstorePlanRegService planRegService;
    @Autowired
    private IMistoreExchangeTaskService exchangeTaskService;

    @Override
    public MidstoreResultObject saveMidstoreScheme(SchemeBaseDTO schemeDTO) throws MidstoreException {
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        boolean isNew = true;
        if (StringUtils.isNotEmpty((String)schemeDTO.getKey())) {
            midstoreDataDTO.setKey(schemeDTO.getKey());
            List list2 = this.schemeService.list(midstoreDataDTO);
            if (list2 != null && list2.size() > 0) {
                isNew = false;
            }
        } else if (StringUtils.isEmpty((String)schemeDTO.getCode())) {
            throw new MidstoreException("\u4ea4\u6362\u65b9\u6848\u7684\u6807\u8bc6\u4e3a\u7a7a");
        }
        midstoreDataDTO.setCode(schemeDTO.getCode());
        midstoreDataDTO.setTitle(schemeDTO.getTitle());
        midstoreDataDTO.setGroupKey(schemeDTO.getGroupKey());
        midstoreDataDTO.setOrder(OrderGenerator.newOrder());
        if (isNew) {
            if (StringUtils.isNotEmpty((String)schemeDTO.getCode())) {
                MidstoreSchemeDTO queryParam = new MidstoreSchemeDTO();
                queryParam.setCode(schemeDTO.getCode());
                List list2 = this.schemeService.list(queryParam);
                if (list2 != null && list2.size() > 0) {
                    return new MidstoreResultObject(false, "\u5df2\u5b58\u5728\u76f8\u540c\u6807\u8bc6\uff0c" + schemeDTO.getCode());
                }
            }
            this.schemeService.add(midstoreDataDTO);
            this.addDefaultSchemeInfo(midstoreDataDTO.getKey());
            return new MidstoreResultObject(true, "\u6210\u529f\u65b0\u589e\u4ea4\u6362\u65b9\u6848", midstoreDataDTO.getKey());
        }
        this.schemeService.update(midstoreDataDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u66f4\u65b0\u4ea4\u6362\u65b9\u6848", midstoreDataDTO.getKey());
    }

    @Override
    public MidstoreResultObject saveMidstoreScheme(ExchangeSchemeDTO schemeDTO) throws MidstoreException {
        MidstoreSchemeDTO scheme;
        MidstoreSchemeDTO midstoreDataDTO = new MidstoreSchemeDTO();
        boolean isNew = true;
        if (StringUtils.isNotEmpty((String)schemeDTO.getKey()) && (scheme = this.schemeService.getByKey(schemeDTO.getKey())) != null) {
            MidstoreSchemeDTO queryParam;
            if (StringUtils.isNotEmpty((String)scheme.getCode()) && !scheme.getCode().equalsIgnoreCase(schemeDTO.getCode())) {
                queryParam = new MidstoreSchemeDTO();
                queryParam.setCode(schemeDTO.getCode());
                List list3 = this.schemeService.list(queryParam);
                if (list3 != null && list3.size() > 0) {
                    return new MidstoreResultObject(false, "\u5df2\u5b58\u5728\u76f8\u540c\u6807\u8bc6\uff0c" + schemeDTO.getCode());
                }
            } else if (StringUtils.isNotEmpty((String)schemeDTO.getTablePrefix()) && !schemeDTO.getTablePrefix().equalsIgnoreCase(scheme.getTablePrefix())) {
                queryParam = new MidstoreSchemeDTO();
                queryParam.setTablePrefix(schemeDTO.getTablePrefix());
                List list3 = this.schemeService.list(queryParam);
                if (list3 != null && list3.size() > 0) {
                    return new MidstoreResultObject(false, "\u5df2\u5b58\u5728\u76f8\u540c\u4e2d\u95f4\u5e93\u8868\u524d\u7f00\uff0c" + schemeDTO.getTablePrefix());
                }
            }
            isNew = false;
        }
        midstoreDataDTO.setKey(schemeDTO.getKey());
        midstoreDataDTO.setCode(schemeDTO.getCode());
        midstoreDataDTO.setTitle(schemeDTO.getTitle());
        midstoreDataDTO.setGroupKey(schemeDTO.getGroupKey());
        midstoreDataDTO.setConfigKey(schemeDTO.getConfigKey());
        midstoreDataDTO.setDataBaseLink(schemeDTO.getDataBaseLink());
        midstoreDataDTO.setTablePrefix(schemeDTO.getTablePrefix());
        midstoreDataDTO.setStorageInfo(schemeDTO.getStorageInfo());
        midstoreDataDTO.setStorageMode(schemeDTO.getStorageMode());
        midstoreDataDTO.setTaskKey(schemeDTO.getTaskKey());
        midstoreDataDTO.setDesc(schemeDTO.getDesc());
        midstoreDataDTO.setExchangeMode(schemeDTO.getExchangeMode());
        midstoreDataDTO.setOrder(OrderGenerator.newOrder());
        midstoreDataDTO.setSceneCode(schemeDTO.getSceneCode());
        if (schemeDTO.getDimensionSet() != null) {
            MidstoreDimensionDTO mDim = new MidstoreDimensionDTO();
            mDim.setDimensionSet(schemeDTO.getDimensionSet());
            midstoreDataDTO.setDimensions(MidstoreLib.objectToJson((Object)mDim));
        } else {
            midstoreDataDTO.setDimensions(null);
        }
        if (isNew) {
            MidstoreSchemeDTO queryParam = new MidstoreSchemeDTO();
            queryParam.setCode(schemeDTO.getCode());
            List list3 = this.schemeService.list(queryParam);
            if (list3 != null && list3.size() > 0) {
                return new MidstoreResultObject(false, "\u5df2\u5b58\u5728\u76f8\u540c\u6807\u8bc6\uff0c" + schemeDTO.getCode());
            }
            this.schemeService.add(midstoreDataDTO);
            this.addDefaultSchemeInfo(midstoreDataDTO.getKey());
            return new MidstoreResultObject(true, "\u6210\u529f\u65b0\u589e\u4ea4\u6362\u65b9\u6848", midstoreDataDTO.getKey());
        }
        this.schemeService.update(midstoreDataDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u66f4\u65b0\u4ea4\u6362\u65b9\u6848", midstoreDataDTO.getKey());
    }

    @Override
    public MidstoreResultObject addDefaultSchemeInfo(String schemeKey) throws MidstoreException {
        MidstoreSchemeInfoDTO schemeInfo = null;
        schemeInfo = new MidstoreSchemeInfoDTO();
        schemeInfo.setSchemeKey(schemeKey);
        schemeInfo.setAllOrgData(true);
        schemeInfo.setAllOrgField(true);
        schemeInfo.setAutoClean(false);
        schemeInfo.setUseUpdateOrg(true);
        schemeInfo.setUsePlanTask(false);
        schemeInfo.setDeleteEmpty(false);
        schemeInfo.setCleanMonth(Integer.valueOf(6));
        schemeInfo.setPublishState(PublishStateType.PUBLISHSTATE_NONE);
        schemeInfo.setExcutePeriodType(ExcutePeriodType.EXCUTEPERIOD_CURRENT);
        this.schemeInfoService.add(schemeInfo);
        return new MidstoreResultObject(true, "\u6210\u529f\u65b0\u589e\u9ed8\u8ba4\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f", schemeInfo.getKey());
    }

    @Override
    public MidstoreResultObject deleteMidstoreScheme(String schemeKey) throws MidstoreException {
        MidstoreSchemeDTO scheme = this.schemeService.getByKey(schemeKey);
        if (scheme != null) {
            MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoService.getBySchemeKey(schemeKey);
            if (schemeInfo != null) {
                if (StringUtils.isNotEmpty((String)schemeInfo.getExcutePlanKey()) || StringUtils.isNotEmpty((String)schemeInfo.getCleanPlanKey())) {
                    try {
                        this.planRegService.deletePlanTaskByMidstoreScheme(schemeKey);
                    }
                    catch (Exception ex) {
                        log.error("\u5220\u9664\u8ba1\u5212\u4efb\u52a1\u51fa\u73b0\u5f02\u5e38" + ex.getMessage(), ex);
                    }
                }
                if (schemeInfo.getPublishState() != PublishStateType.PUBLISHSTATE_NONE && StringUtils.isNotEmpty((String)schemeInfo.getExchangeTaskKey())) {
                    try {
                        this.exchangeTaskService.deleteExchangeTask(scheme);
                    }
                    catch (Exception ex) {
                        log.error("\u5220\u9664\u4e2d\u95f4\u5e93\u51fa\u9519" + ex.getMessage(), ex);
                    }
                }
            }
            MidstoreSchemeDTO deleteParam = new MidstoreSchemeDTO();
            deleteParam.setKey(schemeKey);
            this.schemeService.delete(deleteParam);
            return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u4ea4\u6362\u65b9\u6848", schemeKey);
        }
        return new MidstoreResultObject(false, "\u4ea4\u6362\u65b9\u6848\u4e0d\u5b58\u5728", schemeKey);
    }

    @Override
    public MidstoreResultObject saveSchemeGroup(SchemeGroupDTO groupDTO) throws MidstoreException {
        MidstoreSchemeGroupDTO midstoreDataDTO = new MidstoreSchemeGroupDTO();
        boolean isNew = true;
        if (StringUtils.isNotEmpty((String)groupDTO.getKey())) {
            midstoreDataDTO.setKey(groupDTO.getKey());
            List list2 = this.groupService.list(midstoreDataDTO);
            if (list2 != null && list2.size() > 0) {
                isNew = false;
            }
        }
        midstoreDataDTO.setTitle(groupDTO.getTitle());
        midstoreDataDTO.setParent(groupDTO.getParent());
        midstoreDataDTO.setDesc(groupDTO.getDesc());
        midstoreDataDTO.setOrder(OrderGenerator.newOrder());
        if (isNew) {
            this.groupService.add(midstoreDataDTO);
            return new MidstoreResultObject(true, "\u6210\u529f\u65b0\u589e\u4ea4\u6362\u65b9\u6848\u5206\u7ec4", midstoreDataDTO.getKey());
        }
        this.groupService.update(midstoreDataDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u5206\u7ec4", midstoreDataDTO.getKey());
    }

    @Override
    public MidstoreResultObject deleteMidstoreSchemeGroup(String groupKey) throws MidstoreException {
        MidstoreSchemeGroupDTO midstoreDataDTO = new MidstoreSchemeGroupDTO();
        midstoreDataDTO.setKey(groupKey);
        this.groupService.delete(midstoreDataDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u4ea4\u6362\u65b9\u6848\u5206\u7ec4", midstoreDataDTO.getKey());
    }

    @Override
    public MidstoreResultObject saveSchemeInfo(SchemeInfoDTO schemeInfoDTO) throws MidstoreException {
        MidstoreResultObject result = null;
        MidstoreSchemeInfoDTO midstoreDataDTO = new MidstoreSchemeInfoDTO();
        boolean isNew = true;
        if (StringUtils.isNotEmpty((String)schemeInfoDTO.getKey())) {
            midstoreDataDTO.setKey(schemeInfoDTO.getKey());
            List list2 = this.schemeInfoService.list(midstoreDataDTO);
            if (list2 != null && list2.size() > 0) {
                isNew = false;
            }
        }
        midstoreDataDTO.setKey(schemeInfoDTO.getKey());
        midstoreDataDTO.setAllOrgData(schemeInfoDTO.isAllOrgData());
        midstoreDataDTO.setAllOrgField(schemeInfoDTO.isAllOrgField());
        midstoreDataDTO.setAutoClean(schemeInfoDTO.isAutoClean());
        midstoreDataDTO.setUseUpdateOrg(schemeInfoDTO.isUseUpdateOrg());
        midstoreDataDTO.setUsePlanTask(schemeInfoDTO.isUsePlanTask());
        midstoreDataDTO.setDeleteEmpty(schemeInfoDTO.isDeleteEmpty());
        midstoreDataDTO.setExcutePeriodType(schemeInfoDTO.getExcutePeriodType());
        midstoreDataDTO.setExcutePeriod(schemeInfoDTO.getExcutePeriod());
        midstoreDataDTO.setExcutePlanKey(schemeInfoDTO.getExcutePlanKey());
        midstoreDataDTO.setExcutePlanInfo(schemeInfoDTO.getExcutePlanInfo());
        midstoreDataDTO.setCleanMonth(schemeInfoDTO.getCleanMonth());
        midstoreDataDTO.setCleanPlanKey(schemeInfoDTO.getCleanPlanKey());
        midstoreDataDTO.setCleanPlanInfo(schemeInfoDTO.getCleanPlanInfo());
        midstoreDataDTO.setPublishState(schemeInfoDTO.getPublishState());
        midstoreDataDTO.setExchangeTaskKey(schemeInfoDTO.getExchangeTaskKey());
        midstoreDataDTO.setDocumentKey(schemeInfoDTO.getDocumentKey());
        midstoreDataDTO.setSchemeKey(schemeInfoDTO.getSchemeKey());
        midstoreDataDTO.setOrder(OrderGenerator.newOrder());
        if (midstoreDataDTO.getExcutePeriodType() == null) {
            midstoreDataDTO.setExcutePeriodType(ExcutePeriodType.EXCUTEPERIOD_CURRENT);
        }
        if (isNew) {
            this.schemeInfoService.add(midstoreDataDTO);
            result = new MidstoreResultObject(true, "\u6210\u529f\u65b0\u589e\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f", midstoreDataDTO.getKey());
        } else {
            this.schemeInfoService.update(midstoreDataDTO);
            result = new MidstoreResultObject(true, "\u6210\u529f\u66f4\u65b0\u4ea4\u6362\u65b9\u6848\u6269\u5c55\u4fe1\u606f", midstoreDataDTO.getKey());
        }
        if (midstoreDataDTO.isUsePlanTask()) {
            this.planRegService.regPlanTaskByMidstoreScheme(schemeInfoDTO.getSchemeKey());
        } else {
            this.planRegService.stopPlanTaskByMidstoreScheme(schemeInfoDTO.getSchemeKey());
        }
        return result;
    }

    @Override
    public MidstoreResultObject saveSchemeOrgDataItems(String schemeKey, List<SchemeOrgDataItemDTO> orgDataItems) throws MidstoreException {
        ArrayList<MidstoreOrgDataDTO> addList = new ArrayList<MidstoreOrgDataDTO>();
        ArrayList<MidstoreOrgDataDTO> updateList = new ArrayList<MidstoreOrgDataDTO>();
        if (orgDataItems.size() > 0) {
            MidstoreOrgDataDTO schemeDTO = new MidstoreOrgDataDTO();
            schemeDTO.setSchemeKey(schemeKey);
            List list = this.orgDataService.list(schemeDTO);
            Map<String, MidstoreOrgDataDTO> orgDataDic = list.stream().collect(Collectors.toMap(MidstoreOrgDataDO::getKey, MidstoreOrgDataDTO2 -> MidstoreOrgDataDTO2));
            Map<String, MidstoreOrgDataDTO> orgDataCodeDic = list.stream().collect(Collectors.toMap(MidstoreOrgDataDO::getCode, MidstoreOrgDataDTO2 -> MidstoreOrgDataDTO2));
            for (SchemeOrgDataItemDTO orgItem : orgDataItems) {
                MidstoreOrgDataDTO orgData = null;
                if (StringUtils.isNotEmpty((String)orgItem.getKey()) && orgDataDic.containsKey(orgItem.getKey())) {
                    orgData = orgDataDic.get(orgItem.getKey());
                    updateList.add(orgData);
                } else if (StringUtils.isNotEmpty((String)orgItem.getCode()) && orgDataCodeDic.containsKey(orgItem.getCode())) {
                    orgData = orgDataCodeDic.get(orgItem.getCode());
                    updateList.add(orgData);
                } else {
                    orgData = new MidstoreOrgDataDTO();
                    if (StringUtils.isNotEmpty((String)orgItem.getKey())) {
                        orgData.setKey(orgItem.getKey());
                    } else {
                        orgData.setKey(UUID.randomUUID().toString());
                    }
                    orgData.setSchemeKey(schemeKey);
                    addList.add(orgData);
                }
                orgData.setCode(orgItem.getCode());
                orgData.setOrgCode(orgItem.getOrgCode());
                orgData.setParentCode(orgItem.getParentCode());
                orgData.setTitle(orgItem.getTitle());
            }
            if (addList.size() > 0) {
                this.orgDataService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.orgDataService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784\u6570\u636e\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemeOrgDataFields(String schemeKey, List<SchemeOrgDataFieldDTO> orgDataFields) throws MidstoreException {
        ArrayList<MidstoreOrgDataFieldDTO> addList = new ArrayList<MidstoreOrgDataFieldDTO>();
        ArrayList<MidstoreOrgDataFieldDTO> updateList = new ArrayList<MidstoreOrgDataFieldDTO>();
        ArrayList<String> delList = new ArrayList<String>();
        if (orgDataFields.size() > 0) {
            MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoService.getBySchemeKey(schemeKey);
            if (schemeInfo.isAllOrgField()) {
                schemeInfo.setAllOrgField(false);
                this.schemeInfoService.update(schemeInfo);
            }
            MidstoreOrgDataFieldDTO schemeDTO = new MidstoreOrgDataFieldDTO();
            schemeDTO.setSchemeKey(schemeKey);
            List list = this.orgDataFieldService.list(schemeDTO);
            Map<String, MidstoreOrgDataFieldDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreOrgDataFieldDO::getKey, MidstoreOrgDataFieldDTO2 -> MidstoreOrgDataFieldDTO2));
            Map<String, MidstoreOrgDataFieldDTO> orgFieldCodeDic = list.stream().collect(Collectors.toMap(MidstoreOrgDataFieldDO::getCode, MidstoreOrgDataFieldDTO2 -> MidstoreOrgDataFieldDTO2));
            HashMap<String, MidstoreOrgDataFieldDTO> updateOrAddDic = new HashMap<String, MidstoreOrgDataFieldDTO>();
            for (SchemeOrgDataFieldDTO fieldItem : orgDataFields) {
                MidstoreOrgDataFieldDTO fieldData = null;
                if (StringUtils.isNotEmpty((String)fieldItem.getKey()) && orgFieldDic.containsKey(fieldItem.getKey())) {
                    fieldData = orgFieldDic.get(fieldItem.getKey());
                    updateList.add(fieldData);
                } else if (StringUtils.isNotEmpty((String)fieldItem.getCode()) && orgFieldCodeDic.containsKey(fieldItem.getCode())) {
                    fieldData = orgFieldCodeDic.get(fieldItem.getCode());
                    updateList.add(fieldData);
                } else {
                    fieldData = new MidstoreOrgDataFieldDTO();
                    if (StringUtils.isNotEmpty((String)fieldItem.getKey())) {
                        fieldData.setKey(fieldItem.getKey());
                    } else {
                        fieldData.setKey(UUID.randomUUID().toString());
                    }
                    fieldData.setSchemeKey(schemeKey);
                    addList.add(fieldData);
                }
                fieldData.setCode(fieldItem.getCode());
                fieldData.setSrcFieldKey(fieldItem.getSrcFieldKey());
                fieldData.setSrcOrgDataKey(fieldItem.getSrcOrgDataKey());
                fieldData.setTitle(fieldItem.getTitle());
                updateOrAddDic.put(fieldData.getKey(), fieldData);
            }
            for (MidstoreOrgDataFieldDTO oldField : list) {
                if (updateOrAddDic.containsKey(oldField.getKey())) continue;
                delList.add(oldField.getKey());
            }
            if (addList.size() > 0) {
                this.orgDataFieldService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.orgDataFieldService.batchUpdate(updateList);
            }
            if (delList.size() > 0) {
                this.orgDataFieldService.batchDeleteByKeys(delList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemeBaseDatas(String schemeKey, List<SchemeBaseDataDTO> baseDatas) throws MidstoreException {
        ArrayList<MidstoreBaseDataDTO> addList = new ArrayList<MidstoreBaseDataDTO>();
        ArrayList<MidstoreBaseDataDTO> updateList = new ArrayList<MidstoreBaseDataDTO>();
        if (baseDatas.size() > 0) {
            MidstoreBaseDataDTO schemeDTO = new MidstoreBaseDataDTO();
            schemeDTO.setSchemeKey(schemeKey);
            List list = this.baseDataService.list(schemeDTO);
            Map<String, MidstoreBaseDataDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreBaseDataDO::getKey, MidstoreBaseDataDTO2 -> MidstoreBaseDataDTO2));
            Map<String, MidstoreBaseDataDTO> orgFieldCodeDic = list.stream().collect(Collectors.toMap(MidstoreBaseDataDO::getCode, MidstoreBaseDataDTO2 -> MidstoreBaseDataDTO2));
            for (SchemeBaseDataDTO item : baseDatas) {
                MidstoreBaseDataDTO data = null;
                if (StringUtils.isNotEmpty((String)item.getKey()) && orgFieldDic.containsKey(item.getKey())) {
                    data = orgFieldDic.get(item.getKey());
                    updateList.add(data);
                } else if (StringUtils.isNotEmpty((String)item.getCode()) && orgFieldCodeDic.containsKey(item.getCode())) {
                    data = orgFieldCodeDic.get(item.getCode());
                    updateList.add(data);
                } else {
                    data = new MidstoreBaseDataDTO();
                    if (StringUtils.isNotEmpty((String)item.getKey())) {
                        data.setKey(item.getKey());
                    } else {
                        data.setKey(UUID.randomUUID().toString());
                    }
                    data.setSchemeKey(schemeKey);
                    addList.add(data);
                }
                data.setCode(item.getCode());
                data.setRemark(item.getRemark());
                data.setSrcBaseDataKey(item.getSrcBaseDataKey());
                data.setTitle(item.getTitle());
            }
            if (addList.size() > 0) {
                this.baseDataService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.baseDataService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u57fa\u7840\u6570\u636e\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u57fa\u7840\u6570\u636e\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemeBaseDataFields(String schemeKey, List<SchemeBaseDataFieldDTO> baseDataFileds) throws MidstoreException {
        ArrayList<MidstoreBaseDataFieldDTO> addList = new ArrayList<MidstoreBaseDataFieldDTO>();
        ArrayList<MidstoreBaseDataFieldDTO> updateList = new ArrayList<MidstoreBaseDataFieldDTO>();
        if (baseDataFileds.size() > 0) {
            MidstoreBaseDataFieldDTO schemeDTO = new MidstoreBaseDataFieldDTO();
            schemeDTO.setSchemeKey(schemeKey);
            List list = this.baseDataFieldService.list(schemeDTO);
            Map<String, MidstoreBaseDataFieldDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreBaseDataFieldDO::getKey, MidstoreBaseDataFieldDTO2 -> MidstoreBaseDataFieldDTO2));
            HashMap<String, MidstoreBaseDataFieldDTO> orgFieldCodeDic = new HashMap<String, MidstoreBaseDataFieldDTO>();
            for (MidstoreBaseDataFieldDTO fieldDto : list) {
                orgFieldCodeDic.put(fieldDto.getSrcBaseDataKey() + "_" + fieldDto.getCode(), fieldDto);
            }
            for (SchemeBaseDataFieldDTO item : baseDataFileds) {
                MidstoreBaseDataFieldDTO data = null;
                if (StringUtils.isNotEmpty((String)item.getKey()) && orgFieldDic.containsKey(item.getKey())) {
                    data = orgFieldDic.get(item.getKey());
                    updateList.add(data);
                } else if (StringUtils.isNotEmpty((String)item.getCode()) && orgFieldCodeDic.containsKey(item.getSrcBaseDataKey() + "_" + item.getCode())) {
                    data = (MidstoreBaseDataFieldDTO)orgFieldCodeDic.get(item.getSrcBaseDataKey() + "_" + item.getCode());
                    updateList.add(data);
                } else {
                    data = new MidstoreBaseDataFieldDTO();
                    if (StringUtils.isNotEmpty((String)item.getKey())) {
                        data.setKey(item.getKey());
                    } else {
                        data.setKey(UUID.randomUUID().toString());
                    }
                    data.setSchemeKey(schemeKey);
                    addList.add(data);
                }
                data.setCode(item.getCode());
                data.setSrcFieldKey(item.getSrcFieldKey());
                data.setBaseDataKey(item.getBaseDataKey());
                data.setSrcBaseDataKey(item.getSrcBaseDataKey());
                data.setTitle(item.getTitle());
            }
            if (addList.size() > 0) {
                this.baseDataFieldService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.baseDataFieldService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemeBaseDataFields(String schemeKey, String baseDataKey, List<SchemeBaseDataFieldDTO> baseDataFileds) throws MidstoreException {
        ArrayList<MidstoreBaseDataFieldDTO> addList = new ArrayList<MidstoreBaseDataFieldDTO>();
        ArrayList<MidstoreBaseDataFieldDTO> updateList = new ArrayList<MidstoreBaseDataFieldDTO>();
        if (baseDataFileds.size() > 0) {
            MidstoreBaseDataFieldDTO schemeDTO = new MidstoreBaseDataFieldDTO();
            schemeDTO.setSchemeKey(schemeKey);
            schemeDTO.setBaseDataKey(baseDataKey);
            List list = this.baseDataFieldService.list(schemeDTO);
            Map<String, MidstoreBaseDataFieldDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreBaseDataFieldDO::getKey, MidstoreBaseDataFieldDTO2 -> MidstoreBaseDataFieldDTO2));
            HashMap<String, MidstoreBaseDataFieldDTO> orgFieldCodeDic = new HashMap<String, MidstoreBaseDataFieldDTO>();
            for (MidstoreBaseDataFieldDTO fieldDto : list) {
                orgFieldCodeDic.put(fieldDto.getSrcBaseDataKey() + "_" + fieldDto.getCode(), fieldDto);
            }
            for (SchemeBaseDataFieldDTO item : baseDataFileds) {
                MidstoreBaseDataFieldDTO data = null;
                if (StringUtils.isNotEmpty((String)item.getKey()) && orgFieldDic.containsKey(item.getKey())) {
                    data = orgFieldDic.get(item.getKey());
                    updateList.add(data);
                } else if (StringUtils.isNotEmpty((String)item.getCode()) && orgFieldCodeDic.containsKey(item.getSrcBaseDataKey() + "_" + item.getCode())) {
                    data = (MidstoreBaseDataFieldDTO)orgFieldCodeDic.get(item.getSrcBaseDataKey() + "_" + item.getCode());
                    updateList.add(data);
                } else {
                    data = new MidstoreBaseDataFieldDTO();
                    if (StringUtils.isNotEmpty((String)item.getKey())) {
                        data.setKey(item.getKey());
                    } else {
                        data.setKey(UUID.randomUUID().toString());
                    }
                    data.setSchemeKey(schemeKey);
                    addList.add(data);
                }
                data.setCode(item.getCode());
                data.setSrcFieldKey(item.getSrcFieldKey());
                data.setBaseDataKey(item.getBaseDataKey());
                data.setSrcBaseDataKey(item.getSrcBaseDataKey());
                data.setTitle(item.getTitle());
            }
            if (addList.size() > 0) {
                this.baseDataFieldService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.baseDataFieldService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u57fa\u7840\u6570\u636e\u5b57\u6bb5\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemFields(String schemeKey, List<SchemeFieldDTO> schemeFileds) throws MidstoreException {
        ArrayList<MidstoreFieldDTO> addList = new ArrayList<MidstoreFieldDTO>();
        ArrayList<MidstoreFieldDTO> updateList = new ArrayList<MidstoreFieldDTO>();
        if (schemeFileds.size() > 0) {
            MidstoreFieldDTO schemeDTO = new MidstoreFieldDTO();
            schemeDTO.setSchemeKey(schemeKey);
            List list = this.fieldService.list(schemeDTO);
            Map<String, MidstoreFieldDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreFieldDO::getKey, MidstoreFieldDTO2 -> MidstoreFieldDTO2));
            HashMap<String, MidstoreFieldDTO> orgFieldCodeDic = new HashMap<String, MidstoreFieldDTO>();
            for (MidstoreFieldDTO fieldDto : list) {
                orgFieldCodeDic.put(fieldDto.getSrcTableKey() + "_" + fieldDto.getCode(), fieldDto);
            }
            for (SchemeFieldDTO item : schemeFileds) {
                MidstoreFieldDTO data = null;
                if (StringUtils.isNotEmpty((String)item.getKey()) && orgFieldDic.containsKey(item.getKey())) {
                    data = orgFieldDic.get(item.getKey());
                    updateList.add(data);
                } else if (StringUtils.isNotEmpty((String)item.getCode()) && orgFieldCodeDic.containsKey(item.getSrcTableKey() + "_" + item.getCode())) {
                    data = (MidstoreFieldDTO)orgFieldCodeDic.get(item.getSrcTableKey() + "_" + item.getCode());
                    updateList.add(data);
                } else {
                    data = new MidstoreFieldDTO();
                    if (StringUtils.isNotEmpty((String)item.getKey())) {
                        data.setKey(item.getKey());
                    } else {
                        data.setKey(UUID.randomUUID().toString());
                    }
                    data.setSchemeKey(schemeKey);
                    addList.add(data);
                }
                data.setCode(item.getCode());
                data.setSrcFieldKey(item.getSrcFieldKey());
                data.setSrcTableKey(item.getSrcTableKey());
                data.setRemark(item.getRemark());
                data.setTitle(item.getTitle());
                data.setEncrypted(item.isEncrypted());
            }
            if (addList.size() > 0) {
                this.fieldService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.fieldService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u6307\u6807\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u6307\u6807\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject saveSchemFields(String schemeKey, String srcTableKey, List<SchemeFieldDTO> schemeFileds) throws MidstoreException {
        ArrayList<MidstoreFieldDTO> addList = new ArrayList<MidstoreFieldDTO>();
        ArrayList<MidstoreFieldDTO> updateList = new ArrayList<MidstoreFieldDTO>();
        if (schemeFileds.size() > 0) {
            MidstoreFieldDTO schemeDTO = new MidstoreFieldDTO();
            schemeDTO.setSchemeKey(schemeKey);
            schemeDTO.setSrcTableKey(srcTableKey);
            List list = this.fieldService.list(schemeDTO);
            Map<String, MidstoreFieldDTO> orgFieldDic = list.stream().collect(Collectors.toMap(MidstoreFieldDO::getKey, MidstoreFieldDTO2 -> MidstoreFieldDTO2));
            HashMap<String, MidstoreFieldDTO> orgFieldCodeDic = new HashMap<String, MidstoreFieldDTO>();
            for (MidstoreFieldDTO fieldDto : list) {
                orgFieldCodeDic.put(fieldDto.getSrcTableKey() + "_" + fieldDto.getCode(), fieldDto);
            }
            for (SchemeFieldDTO item : schemeFileds) {
                MidstoreFieldDTO data = null;
                if (StringUtils.isNotEmpty((String)item.getKey()) && orgFieldDic.containsKey(item.getKey())) {
                    data = orgFieldDic.get(item.getKey());
                    updateList.add(data);
                } else if (StringUtils.isNotEmpty((String)item.getCode()) && orgFieldCodeDic.containsKey(item.getSrcTableKey() + "_" + item.getCode())) {
                    data = (MidstoreFieldDTO)orgFieldCodeDic.get(item.getSrcTableKey() + "_" + item.getCode());
                    updateList.add(data);
                } else {
                    data = new MidstoreFieldDTO();
                    if (StringUtils.isNotEmpty((String)item.getKey())) {
                        data.setKey(item.getKey());
                    } else {
                        data.setKey(UUID.randomUUID().toString());
                    }
                    data.setSchemeKey(schemeKey);
                    addList.add(data);
                }
                data.setCode(item.getCode());
                data.setSrcFieldKey(item.getSrcFieldKey());
                data.setSrcTableKey(srcTableKey);
                data.setRemark(item.getRemark());
                data.setTitle(item.getTitle());
                data.setEncrypted(item.isEncrypted());
            }
            if (addList.size() > 0) {
                this.fieldService.batchAdd(addList);
            }
            if (updateList.size() > 0) {
                this.fieldService.batchUpdate(updateList);
            }
        }
        return new MidstoreResultObject(addList.size() > 0 || updateList.size() > 0, "\u65b0\u589e\u6307\u6807\u4e2a\u6570\uff1a" + String.valueOf(addList.size()) + ",\u66f4\u65b0\u6307\u6807\u4e2a\u6570\uff1a" + String.valueOf(updateList.size()));
    }

    @Override
    public MidstoreResultObject deleteOrgDataItemsByScheme(String schemeKey) throws MidstoreException {
        MidstoreOrgDataDTO schemeDTO = new MidstoreOrgDataDTO();
        schemeDTO.setSchemeKey(schemeKey);
        this.orgDataService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u4ea4\u6362\u65b9\u6848\u7684\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u6570\u636e", schemeKey);
    }

    @Override
    public MidstoreResultObject deleteOrgDataItemsByKeys(List<String> orgDataKeys) throws MidstoreException {
        this.orgDataService.batchDeleteByKeys(orgDataKeys);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u7ec4\u7ec7\u673a\u6784\u6570\u636e");
    }

    @Override
    public MidstoreResultObject deleteOrgDataFieldsByScheme(String schemeKey) throws MidstoreException {
        MidstoreOrgDataFieldDTO schemeDTO = new MidstoreOrgDataFieldDTO();
        schemeDTO.setSchemeKey(schemeKey);
        this.orgDataFieldService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u4ea4\u6362\u65b9\u6848\u7684\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5", schemeKey);
    }

    @Override
    public MidstoreResultObject deleteOrgDataFieldsByKeys(List<String> orgDataFieldKeys) throws MidstoreException {
        this.orgDataFieldService.batchDeleteByKeys(orgDataFieldKeys);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u7684\u7ec4\u7ec7\u673a\u6784\u5b57\u6bb5");
    }

    @Override
    public MidstoreResultObject deleteBaseDatasByScheme(String schemeKey) throws MidstoreException {
        MidstoreBaseDataDTO schemeDTO = new MidstoreBaseDataDTO();
        schemeDTO.setSchemeKey(schemeKey);
        this.baseDataService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e", schemeKey);
    }

    @Override
    public MidstoreResultObject deleteBaseDatasByKeys(List<String> baseDataKeys) throws MidstoreException {
        this.baseDataService.batchDeleteByKeys(baseDataKeys);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u7684\u57fa\u7840\u6570\u636e");
    }

    @Override
    public MidstoreResultObject deleteBaseDataFieldsByScheme(String schemeKey) throws MidstoreException {
        MidstoreBaseDataFieldDTO schemeDTO = new MidstoreBaseDataFieldDTO();
        schemeDTO.setSchemeKey(schemeKey);
        this.baseDataFieldService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u4ea4\u6362\u65b9\u6848\u7684\u57fa\u7840\u6570\u636e\u5b57\u6bb5", schemeKey);
    }

    @Override
    public MidstoreResultObject deleteBaseDataFieldsBySchemeAndBaseData(String schemeKey, String baseDataKey) throws MidstoreException {
        MidstoreBaseDataFieldDTO schemeDTO = new MidstoreBaseDataFieldDTO();
        schemeDTO.setSchemeKey(schemeKey);
        schemeDTO.setBaseDataKey(baseDataKey);
        this.baseDataFieldService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u57fa\u7840\u6570\u636e\u7684\u5b57\u6bb5", baseDataKey);
    }

    @Override
    public MidstoreResultObject deleteBaseDataFieldsByKeys(List<String> baseDataFieldKeys) throws MidstoreException {
        this.baseDataFieldService.batchDeleteByKeys(baseDataFieldKeys);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u7684\u57fa\u7840\u6570\u636e\u5b57\u6bb5");
    }

    @Override
    public MidstoreResultObject deleteFieldsByScheme(String schemeKey) throws MidstoreException {
        MidstoreFieldDTO schemeDTO = new MidstoreFieldDTO();
        schemeDTO.setSchemeKey(schemeKey);
        this.fieldService.delete(schemeDTO);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u4ea4\u6362\u65b9\u6848\u7684\u6307\u6807", schemeKey);
    }

    @Override
    public MidstoreResultObject deleteFieldsByKeys(List<String> fieldKeys) throws MidstoreException {
        this.fieldService.batchDeleteByKeys(fieldKeys);
        return new MidstoreResultObject(true, "\u6210\u529f\u5220\u9664\u6307\u5b9a\u7684\u6307\u6807");
    }

    @Override
    public MidstoreResultObject savePlanTask(MidstorePlanTaskDTO planTask) throws MidstoreException {
        boolean isNew = false;
        MidstoreResultObject result = new MidstoreResultObject();
        if (StringUtils.isEmpty((String)planTask.getId())) {
            isNew = true;
            planTask.setId(UUID.randomUUID().toString());
        } else {
            boolean bl = isNew = !this.planRegService.existPlanTask(planTask.getId());
        }
        if (isNew) {
            this.planRegService.insertPlanTask(planTask);
        } else {
            this.planRegService.updatePlanTask(planTask);
        }
        result.setResultKey(planTask.getId());
        result.setSuccess(true);
        return result;
    }

    @Override
    public MidstoreResultObject updateSchemeUsePlanTask(String schemeKey, boolean usePlanTask) throws MidstoreException {
        MidstoreResultObject result = new MidstoreResultObject();
        MidstoreSchemeInfoDTO schemeInfo = this.schemeInfoService.getBySchemeKey(schemeKey);
        if (schemeInfo == null) {
            this.addDefaultSchemeInfo(schemeKey);
            schemeInfo = this.schemeInfoService.getBySchemeKey(schemeKey);
        }
        if (schemeInfo.isUsePlanTask() != usePlanTask) {
            schemeInfo.setUsePlanTask(usePlanTask);
            this.schemeInfoService.update(schemeInfo);
            if (schemeInfo.isUsePlanTask()) {
                this.planRegService.regPlanTaskByMidstoreScheme(schemeKey);
            } else {
                this.planRegService.stopPlanTaskByMidstoreScheme(schemeKey);
            }
        }
        result.setSuccess(true);
        return result;
    }
}

