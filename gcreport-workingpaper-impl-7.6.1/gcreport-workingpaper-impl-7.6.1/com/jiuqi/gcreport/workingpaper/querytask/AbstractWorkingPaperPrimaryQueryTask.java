/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.common.util.DataFieldUtils
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.FormSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.common.util.DataFieldUtils;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.FormSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperPrimaryQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractWorkingPaperPrimaryQueryTask
extends AbstractWorkingPaperQueryTask {
    protected Map<String, List<WorkingPaperDxsItemDTO>> getPrimaryWorkingPaperOffsetItemDTOs(WorkingPaperQueryCondition condition, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap, List<WorkingPaperTableDataVO> workingPaperTableDataVOS) {
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        HashMap<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperDxsItemDTO>>();
        List<WorkingPaperDxsItemDTO> workingPaperOffsetItemDTOs = this.getWorkingPaperOffsetItemDTOs(condition);
        workingPaperTableDataVOS.stream().forEach(workingPaperTableDataVO -> {
            String primaryId = workingPaperTableDataVO.getPrimarySettingId();
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            List boundSubjects = primaryWorkpaperSettingVO.getBoundSubjects();
            List boundSubjectCodes = boundSubjects.stream().map(ConsolidatedSubjectVO::getCode).collect(Collectors.toList());
            Set boundSubjectAndChildrenCodes = subjectService.listAllChildrenCodesContainsSelf(boundSubjectCodes, this.getSystemId(condition.getSchemeID(), condition.getPeriodStr()));
            List currPrimaryTypeOffsetItems = workingPaperOffsetItemDTOs.stream().filter(offset -> boundSubjectAndChildrenCodes.contains(offset.getSubjectCode())).collect(Collectors.toList());
            primaryWorkingPaperOffsetItemDTOsMap.put(primaryId, currPrimaryTypeOffsetItems);
        });
        return primaryWorkingPaperOffsetItemDTOsMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimary(Map<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        primaryWorkingPaperOffsetItemDTOsMap.forEach((primaryId, offsetItemDatas) -> offsetItemDatas.forEach(orignOffsetItemData -> {
            String mergeKey = primaryId;
            if (mergeOffsetItemDatasByPrimaryMap.get(mergeKey) == null) {
                mergeOffsetItemDatasByPrimaryMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
            }
            WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasByPrimaryMap.get(mergeKey);
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            this.mergeOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
        }));
        return mergeOffsetItemDatasByPrimaryMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndOrg(WorkingPaperQueryCondition condition, Map<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap, Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrgMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndOrgMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        orgDirectChildsContainMergeOrgAndDiffOrgMap.forEach((orgCode, orgData) -> {
            List<String> listAllOrgByParentIdContainsSelf = GcOrgKindEnum.UNIONORG.equals((Object)orgData.getOrgKind()) && !orgCode.equals(condition.getOrgid()) ? this.listAllOrgByParentIdContainsSelf(condition, orgData.getCode()) : Arrays.asList(orgCode);
            primaryWorkingPaperOffsetItemDTOsMap.forEach((primaryId, primaryDxsItemDatas) -> {
                PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
                String mergeKey = primaryId + "_" + orgCode;
                if (mergeOffsetItemDatasByPrimaryAndOrgMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasByPrimaryAndOrgMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currPrimaryIdAndOrgDxsItems = primaryDxsItemDatas.stream().filter(primaryDxsItem -> {
                    String dxsOrgCode = primaryDxsItem.getOrgCode();
                    if (StringUtils.isEmpty((String)dxsOrgCode)) {
                        return false;
                    }
                    return listAllOrgByParentIdContainsSelf.contains(dxsOrgCode);
                }).collect(Collectors.toList());
                currPrimaryIdAndOrgDxsItems.forEach(primaryIdAndOrgDxsItem -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasByPrimaryAndOrgMap.get(mergeKey);
                    this.mergeOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperDxsItemDTO)primaryIdAndOrgDxsItem, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasByPrimaryAndOrgMap;
    }

    protected List<WorkingPaperDxsItemDTO> getOffsetItemDatasByPrimaryIdAndOrgCode(WorkingPaperQueryCondition condition, GcOrgCacheVO orgVO, List<WorkingPaperDxsItemDTO> primaryOffsetItemDatas) {
        if (CollectionUtils.isEmpty(primaryOffsetItemDatas)) {
            return Collections.emptyList();
        }
        String orgCode = orgVO.getCode();
        List<WorkingPaperDxsItemDTO> primaryIdAndOrgDxsItems = primaryOffsetItemDatas.stream().filter(primaryDxsItem -> {
            String dxsOrgCode = primaryDxsItem.getOrgCode();
            return orgCode.equals(dxsOrgCode);
        }).collect(Collectors.toList());
        return primaryIdAndOrgDxsItems;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndYwlx(Map<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap, Map<String, String> ywlxCode2TitleMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndYwlxMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        primaryWorkingPaperOffsetItemDTOsMap.forEach((primaryId, primaryDxsItemDatas) -> {
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            ywlxCode2TitleMap.forEach((ywlxCode, ywlxTitle) -> {
                String mergeKey = primaryId + "_" + ywlxCode;
                if (mergeOffsetItemDatasByPrimaryAndYwlxMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasByPrimaryAndYwlxMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currPrimaryAndYwlxDxsItems = primaryDxsItemDatas.stream().filter(primaryDxsItemData -> {
                    String dxsYwlxCode = ConverterUtils.getAsString((Object)primaryDxsItemData.getYwlxCode(), (String)"OTHER");
                    return ywlxCode.equals(dxsYwlxCode);
                }).collect(Collectors.toList());
                currPrimaryAndYwlxDxsItems.forEach(orignOffsetItemData -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasByPrimaryAndYwlxMap.get(mergeKey);
                    this.mergeOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasByPrimaryAndYwlxMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndElmMode(Map<String, List<WorkingPaperDxsItemDTO>> primaryWorkingPaperOffsetItemDTOsMap, Map<String, PrimaryWorkpaperSettingVO> primaryCode2DataMap, Map<String, String> elmModeCode2TitleMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasByPrimaryAndElmModeMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        primaryWorkingPaperOffsetItemDTOsMap.forEach((primaryId, primaryDxsItemDatas) -> {
            PrimaryWorkpaperSettingVO primaryWorkpaperSettingVO = (PrimaryWorkpaperSettingVO)primaryCode2DataMap.get(primaryId);
            elmModeCode2TitleMap.forEach((elmCode, elmTitle) -> {
                String mergeKey = primaryId + "_" + elmCode;
                if (mergeOffsetItemDatasByPrimaryAndElmModeMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasByPrimaryAndElmModeMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currPrimaryAndElmModeDxsItems = primaryDxsItemDatas.stream().filter(primaryDxsItemData -> {
                    String dxsElmModeCode = ConverterUtils.getAsString((Object)primaryDxsItemData.getElmMode(), (String)"OTHER");
                    return elmCode.equals(dxsElmModeCode);
                }).collect(Collectors.toList());
                currPrimaryAndElmModeDxsItems.forEach(orignOffsetItemData -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasByPrimaryAndElmModeMap.get(mergeKey);
                    this.mergeOffSetVchrItem(primaryWorkpaperSettingVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasByPrimaryAndElmModeMap;
    }

    protected List<WorkingPaperTableDataVO> buildBlankWorkingPaperTableDataByPrimary(Map<String, PrimaryWorkpaperSettingVO> primaryWorkpaperSettingVOMap, WorkingPaperQueryCondition condition) {
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        HashMap tableCodeFieldCode2FieldNameMap = new HashMap();
        HashMap tableCode2TableNameMap = new HashMap();
        Map<String, String> orientCode2Title = ((GcBaseDataService)SpringContextUtils.getBean(GcBaseDataService.class)).queryBasedataItems("MD_ORIENT").stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle));
        primaryWorkpaperSettingVOMap.values().stream().forEach(primaryWorkpaperSettingVO -> {
            FormSubjectVO formPrimary = primaryWorkpaperSettingVO.getFormSubject();
            if (formPrimary == null) {
                return;
            }
            String tableCode = formPrimary.getTableName();
            String fieldCode = formPrimary.getCode();
            String key = tableCode + "_" + fieldCode;
            if (tableCodeFieldCode2FieldNameMap.get(key) != null) {
                return;
            }
            try {
                DataTable dataTable = runtimeDataSchemeService.getDataTableByCode(tableCode);
                if (dataTable == null) {
                    return;
                }
                Map nrFieldKey2FieldDefineMapByNrTableName = DataFieldUtils.getNrFieldKey2FieldDefineMapByNrTableName((String)tableCode);
                List dataFields = runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey());
                dataFields.stream().forEach(dataField -> {
                    String dataFieldKey = dataField.getDataFieldKey();
                    if (StringUtils.isEmpty((String)dataFieldKey)) {
                        return;
                    }
                    FieldDefine fieldDefine = (FieldDefine)nrFieldKey2FieldDefineMapByNrTableName.get(dataFieldKey);
                    if (fieldDefine == null) {
                        return;
                    }
                    tableCodeFieldCode2FieldNameMap.putIfAbsent(tableCode + "_" + fieldDefine.getCode(), dataField.getFieldName());
                    tableCode2TableNameMap.putIfAbsent(tableCode, dataField.getTableName());
                });
                if (tableCodeFieldCode2FieldNameMap.get(key) == null) {
                    tableCodeFieldCode2FieldNameMap.put(key, "");
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOS = new ArrayList<WorkingPaperTableDataVO>();
        primaryWorkpaperSettingVOMap.values().stream().forEach(primaryWorkpaperSettingVO -> {
            WorkingPaperTableDataVO workingPaperTableDataVO = new WorkingPaperTableDataVO();
            workingPaperTableDataVO.setFormSubjectTitle(primaryWorkpaperSettingVO.getBoundZbTitle());
            workingPaperTableDataVO.setPrimarySettingId(primaryWorkpaperSettingVO.getId());
            workingPaperTableDataVO.setZbBoundIndexPath(primaryWorkpaperSettingVO.getBoundZbName());
            workingPaperTableDataVO.setKmorient((String)orientCode2Title.get(ConverterUtils.getAsString((Object)primaryWorkpaperSettingVO.getOrient(), (String)"-1")));
            workingPaperTableDataVO.setOrient(primaryWorkpaperSettingVO.getOrient());
            FormSubjectVO formPrimary = primaryWorkpaperSettingVO.getFormSubject();
            if (formPrimary != null) {
                String fieldCode = formPrimary.getCode();
                String tableCode = formPrimary.getTableName();
                String key = tableCode + "_" + fieldCode;
                String fieldName = (String)tableCodeFieldCode2FieldNameMap.get(key);
                String tableName = (String)tableCode2TableNameMap.get(tableCode);
                if (!StringUtils.isEmpty((String)fieldName)) {
                    workingPaperTableDataVO.setZbtable(tableName);
                    workingPaperTableDataVO.setZbfield(fieldName);
                }
            }
            workingPaperTableDataVO.setZbvalue(new HashMap(16));
            workingPaperTableDataVO.setZbvalueStr(new HashMap(16));
            workingPaperTableDataVOS.add(workingPaperTableDataVO);
        });
        return workingPaperTableDataVOS;
    }

    protected Map<String, PrimaryWorkpaperSettingVO> getPrimaryWorkpaperSettingVOS(WorkingPaperQueryCondition condition) {
        PrimaryWorkpaperService primaryWorkpaperService = (PrimaryWorkpaperService)SpringContextUtils.getBean(PrimaryWorkpaperService.class);
        List primaryWorkpaperSettingVOS = primaryWorkpaperService.listPrimarySettingDatas(condition.getPrimaryTableType());
        if (primaryWorkpaperSettingVOS == null) {
            return Collections.emptyMap();
        }
        Map primaryCode2DataMap = primaryWorkpaperSettingVOS.stream().collect(Collectors.toMap(PrimaryWorkpaperSettingVO::getId, vo -> vo, (v1, v2) -> v1, LinkedHashMap::new));
        return primaryCode2DataMap;
    }

    protected List<WorkingPaperPrimaryQmsItemDTO> getWorkingPaperQmsDTOs(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap) {
        Map<String, Map<String, BigDecimal>> zbTableFieldName2OrgZbValueMap = this.getNrData(condition, workingPaperTableDataVOS, directChildOrgCode2DataMap);
        ArrayList<WorkingPaperPrimaryQmsItemDTO> workingPaperQmsDTOS = new ArrayList<WorkingPaperPrimaryQmsItemDTO>();
        workingPaperTableDataVOS.stream().forEach(vo -> {
            String tableFieldName = vo.getZbtable() + "_" + vo.getZbfield();
            Map orgId2ZbValueMap = (Map)zbTableFieldName2OrgZbValueMap.get(tableFieldName);
            if (org.springframework.util.CollectionUtils.isEmpty(orgId2ZbValueMap)) {
                return;
            }
            orgId2ZbValueMap.forEach((orgId, zbFeildValue) -> {
                WorkingPaperPrimaryQmsItemDTO workingPaperQmsDTO = new WorkingPaperPrimaryQmsItemDTO();
                workingPaperQmsDTO.setPrimaryId(vo.getPrimarySettingId());
                workingPaperQmsDTO.setOrgCode((String)orgId);
                workingPaperQmsDTO.setZbValue((BigDecimal)zbFeildValue);
                workingPaperQmsDTOS.add(workingPaperQmsDTO);
            });
        });
        return workingPaperQmsDTOS;
    }

    protected Map<String, WorkingPaperPrimaryQmsItemDTO> mergeWorkingPaperQmsDTOsByPrimaryAndOrg(List<WorkingPaperPrimaryQmsItemDTO> workingPaperQmsDTOs) {
        HashMap<String, WorkingPaperPrimaryQmsItemDTO> mergeWorkingPaperQmsDTOsByPrimaryAndOrgMap = new HashMap<String, WorkingPaperPrimaryQmsItemDTO>();
        workingPaperQmsDTOs.forEach(workingPaperQmsItemDTO -> {
            String primaryId = workingPaperQmsItemDTO.getPrimaryId();
            String orgCode = workingPaperQmsItemDTO.getOrgCode();
            String mergeKey = primaryId + "_" + orgCode;
            if (mergeWorkingPaperQmsDTOsByPrimaryAndOrgMap.get(mergeKey) == null) {
                mergeWorkingPaperQmsDTOsByPrimaryAndOrgMap.put(mergeKey, WorkingPaperPrimaryQmsItemDTO.empty());
            }
            WorkingPaperPrimaryQmsItemDTO mergeWorkingPaperQmsItemDTO = (WorkingPaperPrimaryQmsItemDTO)mergeWorkingPaperQmsDTOsByPrimaryAndOrgMap.get(mergeKey);
            mergeWorkingPaperQmsItemDTO.setZbValue(NumberUtils.add((BigDecimal)mergeWorkingPaperQmsItemDTO.getZbValue(), (BigDecimal[])new BigDecimal[]{workingPaperQmsItemDTO.getZbValue()}));
        });
        return mergeWorkingPaperQmsDTOsByPrimaryAndOrgMap;
    }

    protected Map<String, Map<String, BigDecimal>> getNrData(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap) {
        Set<String> directChildOrgCodes = directChildOrgCode2DataMap.keySet();
        HashMap<String, Map<String, BigDecimal>> zbTableFieldName2OrgZbValueMap = new HashMap<String, Map<String, BigDecimal>>();
        Set entityTableNames = NrTool.getEntityTableNames((String)condition.getSchemeID());
        Map<String, List<String>> zbTableName2FieldNamesMap = this.getZbTableName2FieldNamesMap(workingPaperTableDataVOS);
        DataModelService modelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        zbTableName2FieldNamesMap.forEach((zbTableName, zbFieldNames) -> {
            StringBuilder querySql = new StringBuilder();
            ArrayList<String> params = new ArrayList<String>();
            querySql.append("select ").append("MDCODE").append(" as ").append("MDCODE");
            for (String fieldName : zbFieldNames) {
                querySql.append(",").append(fieldName).append(" as ").append(fieldName);
            }
            querySql.append(" from ").append((String)zbTableName);
            querySql.append(" where ").append("DATATIME").append("=? ");
            params.add(condition.getPeriodStr());
            if (DimensionUtils.isExistAdjust((String)condition.getTaskID())) {
                querySql.append(" and ADJUST=").append("'").append(condition.getSelectAdjustCode()).append("'");
            }
            if (entityTableNames.contains("MD_CURRENCY")) {
                querySql.append(" and ").append("MD_CURRENCY").append("=? ");
                params.add(condition.getCurrencyCode());
            }
            querySql.append(" and  ((1=2)");
            directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                String orgTypeId = StringUtils.isEmpty((String)orgData.getOrgTypeId()) ? condition.getOrg_type() : orgData.getOrgTypeId();
                querySql.append(" or (").append("MDCODE").append("='").append((String)orgCode).append("' and ").append("MD_GCORGTYPE").append("='").append(orgTypeId).append("')");
            });
            querySql.append(")");
            List orgZbRowDatas = EntNativeSqlDefaultDao.getInstance().selectMap(querySql.toString(), params);
            for (Map orgZbRowData : orgZbRowDatas) {
                String orgID = ConverterUtils.getAsString(orgZbRowData.get("MDCODE"), (String)"");
                if (!directChildOrgCodes.contains(orgID)) continue;
                orgZbRowData.forEach((zbFieldName, zbFieldValue) -> {
                    if (zbFieldName.equalsIgnoreCase("MDCODE")) {
                        return;
                    }
                    String zbTableFieldNamekey = zbTableName + "_" + zbFieldName;
                    if (zbTableFieldName2OrgZbValueMap.get(zbTableFieldNamekey) == null) {
                        zbTableFieldName2OrgZbValueMap.put(zbTableFieldNamekey, new HashMap());
                    }
                    Map orgId2ZbValueMap = (Map)zbTableFieldName2OrgZbValueMap.get(zbTableFieldNamekey);
                    BigDecimal fieldValue = NumberUtils.add((BigDecimal)ConverterUtils.getAsBigDecimal(orgId2ZbValueMap.get(orgID), (BigDecimal)BigDecimal.ZERO), (BigDecimal[])new BigDecimal[]{ConverterUtils.getAsBigDecimal((Object)zbFieldValue, (BigDecimal)BigDecimal.ZERO)});
                    orgId2ZbValueMap.put(orgID, fieldValue);
                });
            }
        });
        return zbTableFieldName2OrgZbValueMap;
    }
}

