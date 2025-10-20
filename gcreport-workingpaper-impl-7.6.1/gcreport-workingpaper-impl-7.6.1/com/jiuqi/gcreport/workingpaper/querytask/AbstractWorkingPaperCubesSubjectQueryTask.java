/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.common.FinancialCubesSrcTypeEnum
 *  com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.dimension.service.DimensionService
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.common.FinancialCubesSrcTypeEnum;
import com.jiuqi.common.financialcubes.util.FinancialCubesCommonUtil;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.dimension.service.DimensionService;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperDxsTypeEnum;
import com.jiuqi.gcreport.workingpaper.enums.WorkingPaperQmsTypeEnum;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperSubjectQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableHeaderVO;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public abstract class AbstractWorkingPaperCubesSubjectQueryTask
extends AbstractWorkingPaperQueryTask {
    protected List<WorkingPaperTableDataVO> buildBlankWorkingPaperTableDataBySubjects(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap, Map<String, List<WorkingPaperSubjectQmsItemDTO>> subjectCode2QmsDatasMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2DxsDatasMap) {
        Collection conditionSubjectVOs;
        ConsolidatedSubjectService subjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        ArrayList<WorkingPaperTableDataVO> workingPaperTableDataVOS = new ArrayList<WorkingPaperTableDataVO>();
        List baseSubjectCodes = condition.getBaseSubjectCodes();
        if (CollectionUtils.isEmpty((Collection)baseSubjectCodes)) {
            conditionSubjectVOs = subjectCode2DataMap.values();
        } else {
            HashSet conditionSubjectCodes = new HashSet();
            baseSubjectCodes.stream().filter(Objects::nonNull).forEach(subjectCode -> {
                Set subjectCodes = (Set)subjectCode2ChildSubjectCodesContainsSelfMap.get(subjectCode);
                conditionSubjectCodes.addAll(subjectCodes);
            });
            conditionSubjectVOs = subjectCode2DataMap.values().stream().filter(vo -> conditionSubjectCodes.contains(vo.getCode())).collect(Collectors.toList());
        }
        Map<String, String> orientCode2Title = ((GcBaseDataService)SpringContextUtils.getBean(GcBaseDataService.class)).queryBasedataItems("MD_ORIENT").stream().collect(Collectors.toMap(GcBaseData::getCode, GcBaseData::getTitle));
        conditionSubjectVOs.forEach(row -> {
            Map assistName2LabelMap;
            WorkingPaperTableDataVO vo = new WorkingPaperTableDataVO();
            vo.setKmid(row.getId());
            vo.setKmcode(row.getCode());
            vo.setKmname(row.getTitle());
            vo.setOrder(ConverterUtils.getAsString((Object)row.getOrdinal(), (String)"0"));
            vo.setKmorient((String)orientCode2Title.get(ConverterUtils.getAsString((Object)row.getOrient(), (String)"-1")));
            vo.setOrient(row.getOrient());
            if (row.getParentCode() != null) {
                ConsolidatedSubjectVO vo1 = (ConsolidatedSubjectVO)subjectCode2DataMap.get(row.getParentCode());
                vo.setParentid(vo1 == null ? null : vo1.getCode());
                vo.setZbBoundIndexPath(row.getBoundIndexPath());
                if (null != row.getBoundIndexPath() && !"".equals(row.getBoundIndexPath())) {
                    ConsolidatedSubjectEO eo = new ConsolidatedSubjectEO();
                    eo.setBoundIndexPath(row.getBoundIndexPath());
                    FieldDefine fieldDefine = subjectService.getFieldDefineBySubject(eo);
                    if (fieldDefine != null) {
                        vo.setZbcode(fieldDefine.getCode());
                        vo.setZbname(fieldDefine.getTitle());
                        List deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                        vo.setZbtable(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
                        vo.setZbfield(((DataFieldDeployInfo)deployInfos.get(0)).getFieldName());
                    }
                }
            }
            vo.setZbvalue(new HashMap(16));
            vo.setZbvalueStr(new HashMap(16));
            Set childContainSelfSubjectCodes = (Set)subjectCode2ChildSubjectCodesContainsSelfMap.get(row.getCode());
            if (!CollectionUtils.isEmpty((Collection)childContainSelfSubjectCodes) && childContainSelfSubjectCodes.size() > 1) {
                vo.setLeafNodeFlag(false);
            }
            if (org.springframework.util.CollectionUtils.isEmpty(assistName2LabelMap = condition.getAssistName2Labels())) {
                workingPaperTableDataVOS.add(vo);
                return;
            }
            List workingPaperDxsItemDTOS = (List)subjectCode2DxsDatasMap.get(vo.getKmcode());
            List workingPaperSubjectQmsItemDTOS = (List)subjectCode2QmsDatasMap.get(vo.getKmcode());
            if (CollectionUtils.isEmpty((Collection)workingPaperDxsItemDTOS) && CollectionUtils.isEmpty((Collection)workingPaperSubjectQmsItemDTOS)) {
                workingPaperTableDataVOS.add(vo);
                return;
            }
            LinkedHashMap<String, Map> assistTableRowKey2AssistName2CodeMap = new LinkedHashMap<String, Map>();
            LinkedHashMap assistTableRowKey2AssistName2ShowTitleMap = new LinkedHashMap();
            workingPaperSubjectQmsItemDTOS.stream().forEach(workingPaperSubjectQmsItemDTO -> {
                StringBuilder assistTableRowKeyBuilder = new StringBuilder();
                Map<Object, Object> assistName2CodeMap = workingPaperSubjectQmsItemDTO.getAssistName2CodeMap() == null ? Collections.emptyMap() : workingPaperSubjectQmsItemDTO.getAssistName2CodeMap();
                Map<Object, Object> assistName2TitleMap = workingPaperSubjectQmsItemDTO.getAssistName2ShowTitleMap() == null ? Collections.emptyMap() : workingPaperSubjectQmsItemDTO.getAssistName2ShowTitleMap();
                assistName2LabelMap.forEach((assistName, assistTitle) -> {
                    String assistValue = ConverterUtils.getAsString(assistName2CodeMap.get(assistName), (String)"");
                    assistTableRowKeyBuilder.append(assistValue).append("_");
                });
                String key = assistTableRowKeyBuilder.toString();
                assistTableRowKey2AssistName2CodeMap.putIfAbsent(key, assistName2CodeMap);
                assistTableRowKey2AssistName2ShowTitleMap.putIfAbsent(key, assistName2TitleMap);
            });
            workingPaperDxsItemDTOS.stream().forEach(workingPaperDxsItemDTO -> {
                StringBuilder assistTableRowKeyBuilder = new StringBuilder();
                Map<Object, Object> assistName2CodeMap = workingPaperDxsItemDTO.getAssistName2CodeMap() == null ? Collections.emptyMap() : workingPaperDxsItemDTO.getAssistName2CodeMap();
                Map<Object, Object> assistName2TitleMap = workingPaperDxsItemDTO.getAssistName2ShowTitleMap() == null ? Collections.emptyMap() : workingPaperDxsItemDTO.getAssistName2ShowTitleMap();
                assistName2LabelMap.forEach((assistName, assistTitle) -> {
                    String assistValue = ConverterUtils.getAsString(assistName2CodeMap.get(assistName), (String)"");
                    assistTableRowKeyBuilder.append(assistValue).append("_");
                });
                String key = assistTableRowKeyBuilder.toString();
                assistTableRowKey2AssistName2CodeMap.putIfAbsent(key, assistName2CodeMap);
                assistTableRowKey2AssistName2ShowTitleMap.putIfAbsent(key, assistName2TitleMap);
            });
            if (org.springframework.util.CollectionUtils.isEmpty(assistTableRowKey2AssistName2CodeMap)) {
                return;
            }
            assistTableRowKey2AssistName2CodeMap.forEach((key, assistName2CodeMap) -> {
                Map assistName2ShowTitleMap = (Map)assistTableRowKey2AssistName2ShowTitleMap.get(key);
                WorkingPaperTableDataVO assitWorkingPaperTableDataVO = (WorkingPaperTableDataVO)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)vo), WorkingPaperTableDataVO.class);
                assistName2LabelMap.forEach((assistName, assistTitle) -> {
                    String assistCode = ConverterUtils.getAsString(assistName2CodeMap.get(assistName), (String)"");
                    String assistShowTitle = ConverterUtils.getAsString(assistName2ShowTitleMap.get(assistName), (String)"");
                    assitWorkingPaperTableDataVO.getZbvalueStr().put(assistName, ConverterUtils.getAsString((Object)assistCode, (String)""));
                    assitWorkingPaperTableDataVO.getZbvalueStr().put(assistName + "_SHOWVALUE", ConverterUtils.getAsString((Object)assistShowTitle, (String)""));
                });
                workingPaperTableDataVOS.add(assitWorkingPaperTableDataVO);
            });
        });
        return workingPaperTableDataVOS;
    }

    protected List<WorkingPaperDxsItemDTO> getWorkingPaperDxsItemDTOs(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, List<Map<String, Object>> cubesItems) {
        if (CollectionUtils.isEmpty(cubesItems)) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> dxsItems = this.getDxsItems(cubesItems);
        ArrayList<WorkingPaperDxsItemDTO> dxsCubesItems = new ArrayList<WorkingPaperDxsItemDTO>(dxsItems.size());
        dxsItems.stream().forEach(dxsItem -> {
            WorkingPaperDxsItemDTO sumOffSetVchrItemDTO = this.buildWorkingPaperDxsItem(condition, subjectCode2DataMap, (Map<String, Object>)dxsItem);
            String orgId = StringUtils.isEmpty((String)sumOffSetVchrItemDTO.getOrgCode()) ? condition.getOrgid() : sumOffSetVchrItemDTO.getOrgCode();
            sumOffSetVchrItemDTO.setOrgCode(orgId);
            dxsCubesItems.add(sumOffSetVchrItemDTO);
        });
        return dxsCubesItems;
    }

    protected List<WorkingPaperSubjectQmsItemDTO> getWorkingPaperQmsItemDTOs(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, List<Map<String, Object>> cubesItems) {
        if (CollectionUtils.isEmpty(cubesItems)) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> qmsItems = this.getQmsItems(cubesItems);
        ArrayList<WorkingPaperSubjectQmsItemDTO> qmsItemDTOS = new ArrayList<WorkingPaperSubjectQmsItemDTO>(qmsItems.size());
        qmsItems.stream().forEach(qmsItemDTO -> {
            WorkingPaperSubjectQmsItemDTO qmsItem = this.buildWorkingPaperQmsItem(condition, subjectCode2DataMap, (Map<String, Object>)qmsItemDTO);
            String orgId = StringUtils.isEmpty((String)qmsItem.getOrgCode()) ? condition.getOrgid() : qmsItem.getOrgCode();
            qmsItem.setOrgCode(orgId);
            qmsItemDTOS.add(qmsItem);
        });
        return qmsItemDTOS;
    }

    private List<Map<String, Object>> getQmsItems(List<Map<String, Object>> offsetList) {
        List<String> qmsSrcTypeCodes = this.getQmsSrcTypeCodes();
        return offsetList.stream().filter(offsetFieldMap -> {
            String srcType = ConverterUtils.getAsString(offsetFieldMap.get("MD_AUDITTRAIL"), (String)"");
            return qmsSrcTypeCodes.contains(srcType);
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> getDxsItems(List<Map<String, Object>> offsetList) {
        return offsetList.stream().filter(offsetFieldMap -> {
            String srcType = ConverterUtils.getAsString(offsetFieldMap.get("MD_AUDITTRAIL"), (String)"");
            return srcType.startsWith("2");
        }).collect(Collectors.toList());
    }

    protected WorkingPaperSubjectQmsItemDTO buildWorkingPaperQmsItem(WorkingPaperQueryCondition queryCondition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, Object> qmsItem) {
        Object orient;
        WorkingPaperSubjectQmsItemDTO dto = new WorkingPaperSubjectQmsItemDTO();
        dto.setOrgCode(ConverterUtils.getAsString((Object)qmsItem.get("MDCODE")));
        dto.setSubjectCode(ConverterUtils.getAsString((Object)qmsItem.get("SUBJECTCODE")));
        dto.setSubjectOrient(OrientEnum.valueOf((Integer)ConverterUtils.getAsInteger((Object)qmsItem.get("SUBJECTORIENT"), (Integer)OrientEnum.D.getValue())));
        ConsolidatedSubjectVO consolidatedSubjectVO = subjectCode2DataMap.get(dto.getSubjectCode());
        BigDecimal qms = ConverterUtils.getAsBigDecimal((Object)qmsItem.get("CF"));
        if (consolidatedSubjectVO != null) {
            Integer consolidationType = consolidatedSubjectVO.getConsolidationType();
            Integer attri = consolidatedSubjectVO.getAttri();
            orient = consolidatedSubjectVO.getOrient();
            if (!CSConst.CONSOLIDATION_BALANCE.equals(consolidationType) && Integer.valueOf(SubjectAttributeEnum.PROFITLOSS.getValue()).equals(attri)) {
                qms = OrientEnum.D.getValue().equals(orient) ? ConverterUtils.getAsBigDecimal((Object)qmsItem.get("DEBITSUM")) : ConverterUtils.getAsBigDecimal((Object)qmsItem.get("CREDITSUM"));
            }
        }
        dto.setZbValue(qms);
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        orient = otherColumnKeys.iterator();
        while (orient.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, qmsItem.get(columnKey = (String)orient.next()) == null ? "" : String.valueOf(qmsItem.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        LinkedHashMap<String, String> assistName2CodeMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> assistName2ShowTitleMap = new LinkedHashMap<String, String>();
        queryCondition.getAssistName2Labels().forEach((assistName, assistTitle) -> {
            String assistValue = ConverterUtils.getAsString(qmsItem.get(assistName), (String)"");
            String assistShowTitle = ConverterUtils.getAsString(qmsItem.get(assistName + "SHOWVALUE"), (String)assistValue);
            if ("#".equals(assistValue)) {
                assistValue = "";
            }
            assistName2CodeMap.put((String)assistName, assistValue);
            assistName2ShowTitleMap.put((String)assistName, assistShowTitle);
        });
        dto.setAssistName2CodeMap(assistName2CodeMap);
        dto.setAssistName2ShowTitleMap(assistName2ShowTitleMap);
        return dto;
    }

    protected WorkingPaperDxsItemDTO buildWorkingPaperDxsItem(WorkingPaperQueryCondition queryCondition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, Object> dxsItem) {
        WorkingPaperDxsItemDTO dto = new WorkingPaperDxsItemDTO();
        dto.setOrgCode(ConverterUtils.getAsString((Object)dxsItem.get("MDCODE")));
        dto.setSubjectCode(ConverterUtils.getAsString((Object)dxsItem.get("SUBJECTCODE")));
        dto.setSubjectOrient(OrientEnum.valueOf((Integer)ConverterUtils.getAsInteger((Object)dxsItem.get("SUBJECTORIENT"), (Integer)OrientEnum.D.getValue())));
        String srcType = ConverterUtils.getAsString((Object)dxsItem.get("MD_AUDITTRAIL"), (String)"");
        if (srcType.startsWith("2")) {
            String ywlxCode = srcType.replaceFirst("2", "");
            dto.setYwlxCode(ywlxCode);
        } else if (srcType.startsWith("1")) {
            String elmMode = srcType.replaceFirst("1", "");
            dto.setElmMode(elmMode);
        }
        dto.setUnitCode(ConverterUtils.getAsString((Object)dxsItem.get("UNITCODE")));
        dto.setOppUnitCode(ConverterUtils.getAsString((Object)dxsItem.get("OPPUNITCODE")));
        dto.setDebit(ConverterUtils.getAsBigDecimal((Object)dxsItem.get("DEBIT")));
        dto.setCredit(ConverterUtils.getAsBigDecimal((Object)dxsItem.get("CREDIT")));
        dto.setOffSetDebit(ConverterUtils.getAsBigDecimal((Object)dxsItem.get("DEBITSUM")));
        dto.setOffSetCredit(ConverterUtils.getAsBigDecimal((Object)dxsItem.get("CREDITSUM")));
        HashMap<String, String> otherColumnValueMap = new HashMap<String, String>();
        List otherColumnKeys = queryCondition.getOtherShowColumnKeys();
        Iterator iterator = otherColumnKeys.iterator();
        while (iterator.hasNext()) {
            String columnKey;
            otherColumnValueMap.put(columnKey, dxsItem.get(columnKey = (String)iterator.next()) == null ? "" : String.valueOf(dxsItem.get(columnKey)));
        }
        dto.setOtherColumnsValueMap(otherColumnValueMap);
        LinkedHashMap<String, String> assistName2CodeMap = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> assistName2ShowTitleMap = new LinkedHashMap<String, String>();
        queryCondition.getAssistName2Labels().forEach((assistName, assistTitle) -> {
            String assistValue = ConverterUtils.getAsString(dxsItem.get(assistName), (String)"");
            String assistShowTitle = ConverterUtils.getAsString(dxsItem.get(assistName + "SHOWVALUE"), (String)assistValue);
            if ("#".equals(assistValue)) {
                assistValue = "";
            }
            assistName2CodeMap.put((String)assistName, assistValue);
            assistName2ShowTitleMap.put((String)assistName, assistShowTitle);
        });
        dto.setAssistName2CodeMap(assistName2CodeMap);
        dto.setAssistName2ShowTitleMap(assistName2ShowTitleMap);
        return dto;
    }

    public List<Map<String, Object>> queryCubesOffSetItems(WorkingPaperQueryCondition condition, Map<String, GcOrgCacheVO> orgDirectChildsContainSelfOrgMap, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap) {
        String financialCubesDimTableName = FinancialCubesCommonUtil.getFinancialCubesDimTableName((FinancialCubesPeriodTypeEnum)FinancialCubesPeriodTypeEnum.getByCode((String)condition.getPeriodTypeChar()));
        DimensionService dimensionService = (DimensionService)SpringContextUtils.getBean(DimensionService.class);
        Map<String, String> dimensionCode2TableNameMap = dimensionService.findDimFieldsVOByTableName("GC_FINCUBES_DIM").stream().collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName));
        Map assistName2Labels = condition.getAssistName2Labels();
        StringBuilder assistTableJoinSqlBuilder = new StringBuilder();
        StringBuilder assistFields = new StringBuilder();
        StringBuilder assistShowTitleFields = new StringBuilder();
        StringBuilder assistFieldsGroupBy = new StringBuilder();
        StringBuilder assistShowTitleFieldsGroupBy = new StringBuilder();
        assistName2Labels.forEach((assistName, assistTitle) -> {
            String docTableName = (String)dimensionCode2TableNameMap.get(assistName);
            String fd_assistNameShowName = null;
            if (StringUtils.isEmpty((String)docTableName)) {
                fd_assistNameShowName = "T." + assistName;
            } else {
                fd_assistNameShowName = docTableName + ".NAME";
                assistTableJoinSqlBuilder.append(" LEFT JOIN ").append(docTableName).append(" ").append(docTableName).append(" ON T.").append((String)assistName).append(" = ").append(docTableName).append(".CODE ").append(" \n ");
            }
            assistFields.append(",T.").append((String)assistName).append(" AS ").append((String)assistName);
            assistShowTitleFields.append(", ").append(fd_assistNameShowName).append(" AS ").append((String)assistName).append("SHOWVALUE");
            assistFieldsGroupBy.append(",T.").append((String)assistName);
            assistShowTitleFieldsGroupBy.append(",").append(fd_assistNameShowName);
        });
        HashSet<String> subjectCodes = new HashSet<String>();
        List subjectCodeList = condition.getBaseSubjectCodes().stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());
        ConsolidatedSubjectClient subjectClient = (ConsolidatedSubjectClient)SpringContextUtils.getBean(ConsolidatedSubjectClient.class);
        String systemId = this.getSystemId(condition.getSchemeID(), condition.getPeriodStr());
        for (String subjectCode : subjectCodeList) {
            if (subjectCodes.contains(subjectCode)) continue;
            List subjectVOs = subjectClient.listAllChildrenSubjects(systemId, subjectCode);
            subjectCodes.add(subjectCode);
            for (ConsolidatedSubjectVO subjectVO : subjectVOs) {
                subjectCodes.add(subjectVO.getCode());
            }
        }
        String subjectFilterSQL = subjectCodes.isEmpty() ? "" : " and " + SqlUtils.getConditionOfMulStrUseOr(subjectCodes, (String)"T.SUBJECTCODE");
        Collection<GcOrgCacheVO> orgCacheVOS = orgDirectChildsContainSelfOrgMap.values();
        ArrayList<String> orgCodesCondition = new ArrayList<String>();
        for (GcOrgCacheVO orgCacheVO : orgCacheVOS) {
            String diffUnitId = orgCacheVO.getDiffUnitId();
            if (!StringUtils.isEmpty((String)diffUnitId)) {
                orgCodesCondition.add(diffUnitId);
            }
            orgCodesCondition.add(orgCacheVO.getCode());
        }
        String unitFilterSQL = " and " + SqlUtils.getConditionOfMulStrUseOr(orgCodesCondition, (String)"T.MDCODE");
        List<String> qmsSrcTypeCodes = this.getQmsSrcTypeCodes();
        String srcTypeFilterSQL = " and (" + SqlUtils.getConditionOfMulStrUseOr(qmsSrcTypeCodes, (String)"T.MD_AUDITTRAIL") + " or T.MD_AUDITTRAIL like '2%') ";
        String sql = "SELECT T.DATATIME, T.MDCODE, T.UNITCODE, T.OPPUNITCODE, T.SUBJECTCODE, T.MD_AUDITTRAIL " + assistFields + assistShowTitleFields + ", SUM(T.DEBIT) AS DEBIT, SUM(T.CREDIT) AS CREDIT, SUM(T.CREDITSUM) AS CREDITSUM, SUM(T.DEBITSUM) AS DEBITSUM ,SUM(T.CF) AS CF FROM " + financialCubesDimTableName + "  T " + assistTableJoinSqlBuilder + " WHERE T.DATATIME=? and  T.MD_GCORGTYPE in (?,'NONE') and T.MD_CURRENCY=? " + unitFilterSQL + subjectFilterSQL + srcTypeFilterSQL + " GROUP BY T.DATATIME,T.MD_GCORGTYPE, T.MD_CURRENCY, T.MDCODE, T.UNITCODE, T.OPPUNITCODE, T.SUBJECTCODE, T.MD_AUDITTRAIL" + assistFieldsGroupBy + assistShowTitleFieldsGroupBy;
        GcBizJdbcTemplate gcBizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate((String)"jiuqi.gcreport.mddreadonly.datasource");
        List result = gcBizJdbcTemplate.queryForList(sql, new Object[]{condition.getPeriodStr(), condition.getOrg_type(), condition.getCurrencyCode()});
        if (!CollectionUtils.isEmpty((Collection)result)) {
            result.stream().forEach(item -> {
                String subjectCode = ConverterUtils.getAsString(item.get("SUBJECTCODE"));
                if (StringUtils.isEmpty((String)subjectCode)) {
                    return;
                }
                ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
                if (consolidatedSubjectVO == null) {
                    return;
                }
                Integer subjectOrient = consolidatedSubjectVO.getOrient();
                item.put("SUBJECTORIENT", subjectOrient);
            });
        }
        return result;
    }

    private List<String> getQmsSrcTypeCodes() {
        List<String> srcTypeCodes = Arrays.asList(FinancialCubesSrcTypeEnum.ACCOUNTING_DATA.getCode(), FinancialCubesSrcTypeEnum.ADJUST_VCHR_MANUAL.getCode(), FinancialCubesSrcTypeEnum.BALANCE_RECLASSIFY.getCode(), FinancialCubesSrcTypeEnum.EXPIRE_DATE_RECLASSIFY.getCode(), FinancialCubesSrcTypeEnum.DEDUCTION_RECLASSIFY.getCode());
        return srcTypeCodes;
    }

    private List<String> getDxsSrcTypeCodes() {
        List<String> srcTypeCodes = Arrays.asList(FinancialCubesSrcTypeEnum.OFF_DEALINGS.getCode(), FinancialCubesSrcTypeEnum.OFF_TRADE.getCode(), FinancialCubesSrcTypeEnum.OFF_CF.getCode(), FinancialCubesSrcTypeEnum.OFF_INDIRECT_INVESTMENTS.getCode(), FinancialCubesSrcTypeEnum.OFF_DIRECT_INVESTMENTS.getCode(), FinancialCubesSrcTypeEnum.OFF_FAIR_VALUE.getCode(), FinancialCubesSrcTypeEnum.OFF_FIXED_ASSET.getCode(), FinancialCubesSrcTypeEnum.OFF_INTANGIBLE_ASSET.getCode(), FinancialCubesSrcTypeEnum.OFF_BALANCE_RECLASSIFY.getCode(), FinancialCubesSrcTypeEnum.OFF_DEDUCTION_RECLASSIFY.getCode(), FinancialCubesSrcTypeEnum.OFF_RESTORATION.getCode(), FinancialCubesSrcTypeEnum.OFF_DEFERRED_INCOME_TAX.getCode(), FinancialCubesSrcTypeEnum.OFF_LOSS.getCode());
        return srcTypeCodes;
    }

    @Override
    protected Map<String, GcOrgCacheVO> getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(WorkingPaperQueryCondition condtion) {
        if (StringUtils.isEmpty((String)condtion.getOrgid())) {
            return CollectionUtils.newHashMap();
        }
        Map<String, GcOrgCacheVO> directOrgCode2DataContainMergeOrgAndDiffOrgMap = super.getDirectOrgCode2DataContainMergeOrgAndDiffOrgMap(condtion);
        if (directOrgCode2DataContainMergeOrgAndDiffOrgMap.get(condtion.getOrgid()) == null) {
            YearPeriodObject yp = new YearPeriodObject(null, condtion.getPeriodStr());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)condtion.getOrg_type(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
            GcOrgCacheVO mergeOrg = tool.getOrgByCode(condtion.getOrgid());
            directOrgCode2DataContainMergeOrgAndDiffOrgMap.putIfAbsent(condtion.getOrgid(), mergeOrg);
        }
        return directOrgCode2DataContainMergeOrgAndDiffOrgMap;
    }

    @Override
    protected List<WorkingPaperTableDataVO> rebuildWorkPaperResultVos(WorkingPaperQueryCondition condition, WorkingPaperQmsTypeEnum qmsTypeEnum, WorkingPaperDxsTypeEnum dxsTypeEnum, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap, List<WorkingPaperTableDataVO> workingPaperTableDataVOS) {
        if (condition.getIsFilterZero().booleanValue()) {
            List<WorkingPaperTableHeaderVO> headerVOs = this.getHeaderVO(condition, qmsTypeEnum, dxsTypeEnum);
            HashSet amtProps = new HashSet();
            headerVOs.stream().forEach(headerVO -> {
                List childHeaderVOs = headerVO.getChildren();
                if (CollectionUtils.isEmpty((Collection)childHeaderVOs)) {
                    if (headerVO.getProp().startsWith("DXS_") || headerVO.getProp().startsWith("QMS_")) {
                        amtProps.add(headerVO.getProp());
                    }
                } else {
                    childHeaderVOs.stream().forEach(childHeaderVO -> {
                        if (childHeaderVO.getProp().startsWith("DXS_") || childHeaderVO.getProp().startsWith("QMS_")) {
                            amtProps.add(childHeaderVO.getProp());
                        }
                    });
                }
            });
            workingPaperTableDataVOS = workingPaperTableDataVOS.stream().filter(dataVO -> {
                Map zbvalueStrMap = dataVO.getZbvalueStr();
                AtomicBoolean notAllZeroValue = new AtomicBoolean(false);
                amtProps.forEach(amtProp -> {
                    if (notAllZeroValue.get()) {
                        return;
                    }
                    BigDecimal amtValue = ConverterUtils.getAsBigDecimal(zbvalueStrMap.get(amtProp), (BigDecimal)BigDecimal.ZERO);
                    if (BigDecimal.ZERO.compareTo(amtValue) != 0) {
                        notAllZeroValue.set(true);
                    }
                });
                return notAllZeroValue.get();
            }).collect(Collectors.toList());
        }
        return workingPaperTableDataVOS;
    }

    protected WorkingPaperDxsItemDTO mergeDxsAmountItem(WorkingPaperTableDataVO dataVO, List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOS) {
        WorkingPaperDxsItemDTO mergeAmountItem = WorkingPaperDxsItemDTO.empty();
        if (CollectionUtils.isEmpty(workingPaperDxsItemDTOS)) {
            return mergeAmountItem;
        }
        workingPaperDxsItemDTOS.stream().forEach(offsetItemData -> {
            BigDecimal credit;
            BigDecimal debit;
            BigDecimal offSetCredit;
            BigDecimal offSetDebit;
            if (dataVO.getOrient().equals(offsetItemData.getSubjectOrient().getValue())) {
                offSetDebit = NumberUtils.add((BigDecimal)mergeAmountItem.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
                offSetCredit = NumberUtils.add((BigDecimal)mergeAmountItem.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
                debit = NumberUtils.add((BigDecimal)mergeAmountItem.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
                credit = NumberUtils.add((BigDecimal)mergeAmountItem.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
            } else {
                offSetDebit = NumberUtils.add((BigDecimal)mergeAmountItem.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{NumberUtils.mul((BigDecimal)offsetItemData.getOffSetDebit(), (double)-1.0)});
                offSetCredit = NumberUtils.add((BigDecimal)mergeAmountItem.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{NumberUtils.mul((BigDecimal)offsetItemData.getOffSetCredit(), (double)-1.0)});
                debit = NumberUtils.add((BigDecimal)mergeAmountItem.getDebit(), (BigDecimal[])new BigDecimal[]{NumberUtils.mul((BigDecimal)offsetItemData.getDebit(), (double)-1.0)});
                credit = NumberUtils.add((BigDecimal)mergeAmountItem.getCredit(), (BigDecimal[])new BigDecimal[]{NumberUtils.mul((BigDecimal)offsetItemData.getCredit(), (double)-1.0)});
            }
            mergeAmountItem.setOffSetDebit(offSetDebit);
            mergeAmountItem.setOffSetCredit(offSetCredit);
            mergeAmountItem.setCredit(credit);
            mergeAmountItem.setDebit(debit);
        });
        return mergeAmountItem;
    }

    protected WorkingPaperSubjectQmsItemDTO mergeQmsAmountItem(WorkingPaperTableDataVO dataVO, List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsItemDTOS) {
        WorkingPaperSubjectQmsItemDTO mergeAmountItem = WorkingPaperSubjectQmsItemDTO.empty();
        if (CollectionUtils.isEmpty(workingPaperQmsItemDTOS)) {
            return mergeAmountItem;
        }
        workingPaperQmsItemDTOS.stream().forEach(qmsItemDTO -> {
            BigDecimal qms = NumberUtils.add((BigDecimal)mergeAmountItem.getZbValue(), (BigDecimal[])new BigDecimal[]{NumberUtils.mul((BigDecimal)qmsItemDTO.getZbValue(), (double)dataVO.getOrient().intValue())});
            mergeAmountItem.setZbValue(qms);
        });
        return mergeAmountItem;
    }

    protected List<WorkingPaperSubjectQmsItemDTO> filterQmsBySubjectAndAssists(Map<String, String> assistName2DataValueMap, WorkingPaperTableDataVO dataVO, List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsItemsBySubjectCode) {
        if (CollectionUtils.isEmpty(workingPaperQmsItemsBySubjectCode)) {
            return Collections.emptyList();
        }
        List<WorkingPaperSubjectQmsItemDTO> dtos = workingPaperQmsItemsBySubjectCode.stream().filter(workingPaperDxsItemDTO -> {
            Map<String, String> assistColumnsValueMap = workingPaperDxsItemDTO.getAssistName2CodeMap();
            if (org.springframework.util.CollectionUtils.isEmpty(assistColumnsValueMap)) {
                return true;
            }
            Optional<String> existNotEqualsOptional = assistName2DataValueMap.keySet().stream().filter(assistName -> {
                String dxsItemAssistValue;
                String dataAssistDataValue = ConverterUtils.getAsString(assistName2DataValueMap.get(assistName), (String)"");
                return !dataAssistDataValue.equals(dxsItemAssistValue = ConverterUtils.getAsString(assistColumnsValueMap.get(assistName), (String)""));
            }).findFirst();
            return !existNotEqualsOptional.isPresent();
        }).collect(Collectors.toList());
        return dtos;
    }

    protected List<WorkingPaperDxsItemDTO> filterDxsBySubjectAndAssists(Map<String, String> assistName2DataValueMap, WorkingPaperTableDataVO dataVO, List<WorkingPaperDxsItemDTO> workingPaperDxsItemDTOsBySubjectCode) {
        if (CollectionUtils.isEmpty(workingPaperDxsItemDTOsBySubjectCode)) {
            return Collections.emptyList();
        }
        List<WorkingPaperDxsItemDTO> dtos = workingPaperDxsItemDTOsBySubjectCode.stream().filter(workingPaperDxsItemDTO -> {
            Map<String, String> assistColumnsValueMap = workingPaperDxsItemDTO.getAssistName2CodeMap();
            if (org.springframework.util.CollectionUtils.isEmpty(assistColumnsValueMap)) {
                return true;
            }
            Optional<String> existNotEqualsOptional = assistName2DataValueMap.keySet().stream().filter(assistName -> {
                String dxsItemAssistValue;
                String dataAssistDataValue = ConverterUtils.getAsString(assistName2DataValueMap.get(assistName), (String)"");
                return !dataAssistDataValue.equals(dxsItemAssistValue = ConverterUtils.getAsString(assistColumnsValueMap.get(assistName), (String)""));
            }).findFirst();
            return !existNotEqualsOptional.isPresent();
        }).collect(Collectors.toList());
        return dtos;
    }

    private List<WorkingPaperTableDataVO> buildWorkingPaperToListFromTree(List<WorkingPaperTableDataVO> workingPaperTree) {
        ArrayList<WorkingPaperTableDataVO> sortedAndOrderWorkingPaperTableDataVOs = new ArrayList<WorkingPaperTableDataVO>();
        workingPaperTree.forEach(o -> {
            this.treeToList((WorkingPaperTableDataVO)o, (List<WorkingPaperTableDataVO>)sortedAndOrderWorkingPaperTableDataVOs);
            o.setChildren(null);
        });
        return sortedAndOrderWorkingPaperTableDataVOs;
    }

    private void treeToList(WorkingPaperTableDataVO node, List<WorkingPaperTableDataVO> list) {
        if (list == null) {
            list = new ArrayList<WorkingPaperTableDataVO>();
        }
        list.add(node);
        if (!CollectionUtils.isEmpty((Collection)node.getChildren())) {
            node.getChildren().sort(new Comparator<WorkingPaperTableDataVO>(){

                @Override
                public int compare(WorkingPaperTableDataVO o1, WorkingPaperTableDataVO o2) {
                    Double order1 = ConverterUtils.getAsDouble((Object)o1.getOrder(), (Double)0.0);
                    Double order2 = ConverterUtils.getAsDouble((Object)o2.getOrder(), (Double)0.0);
                    return order1.compareTo(order2);
                }
            });
            for (int i = 0; i < node.getChildren().size(); ++i) {
                WorkingPaperTableDataVO node_ = (WorkingPaperTableDataVO)node.getChildren().get(i);
                this.treeToList(node_, list);
                node_.setChildren(null);
            }
        }
        node.setChildren(null);
    }

    private List<WorkingPaperTableDataVO> buildWorkingPaperToTree(List<WorkingPaperTableDataVO> workingPaperTableDataVOs, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap) {
        Map parentWorking = workingPaperTableDataVOs.stream().collect(Collectors.toMap(WorkingPaperTableDataVO::getKmcode, baseDate -> baseDate, (e1, e2) -> e1, LinkedHashMap::new));
        ArrayList<WorkingPaperTableDataVO> tree = new ArrayList<WorkingPaperTableDataVO>();
        for (WorkingPaperTableDataVO voNode : workingPaperTableDataVOs) {
            WorkingPaperTableDataVO pobj = this.getParentWorking(parentWorking, voNode.getParentid(), subjectCode2DataMap);
            if (pobj != null) {
                pobj.getChildren().add(parentWorking.get(voNode.getKmcode()));
                continue;
            }
            tree.add((WorkingPaperTableDataVO)parentWorking.get(voNode.getKmcode()));
        }
        return tree;
    }

    private WorkingPaperTableDataVO getParentWorking(Map<String, WorkingPaperTableDataVO> parentWorkingPaperVO, String parentId, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap) {
        if (parentId == null) {
            return null;
        }
        ConsolidatedSubjectVO subjectVO = subjectCode2DataMap.get(parentId);
        if (subjectVO == null) {
            return null;
        }
        WorkingPaperTableDataVO vo = parentWorkingPaperVO.get(parentId);
        if (vo != null) {
            return vo;
        }
        vo = this.getParentWorking(parentWorkingPaperVO, subjectVO.getParentCode(), subjectCode2DataMap);
        return vo;
    }

    protected Map<String, List<WorkingPaperDxsItemDTO>> getSubjectWorkingPaperDxsDTOs(Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap, List<WorkingPaperDxsItemDTO> workingPaperOffsetItemDTOs) {
        HashMap<String, List<WorkingPaperDxsItemDTO>> subjectWorkingPaperOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperDxsItemDTO>>();
        subjectCode2ChildSubjectCodesContainsSelfMap.keySet().forEach(subjectCode -> {
            Set subjectCode2ChildSubjectCodesContainsSelf = (Set)subjectCode2ChildSubjectCodesContainsSelfMap.get(subjectCode);
            List currSubjectOffsetItems = workingPaperOffsetItemDTOs.stream().filter(offset -> subjectCode2ChildSubjectCodesContainsSelf.contains(offset.getSubjectCode())).collect(Collectors.toList());
            subjectWorkingPaperOffsetItemDTOsMap.put((String)subjectCode, currSubjectOffsetItems);
        });
        return subjectWorkingPaperOffsetItemDTOsMap;
    }

    protected Map<String, List<WorkingPaperSubjectQmsItemDTO>> getSubjectWorkingPaperQmsDTOs(Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap, List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsDTOs) {
        HashMap<String, List<WorkingPaperSubjectQmsItemDTO>> subjectWorkingPaperQmsDTOsMap = new HashMap<String, List<WorkingPaperSubjectQmsItemDTO>>();
        subjectCode2ChildSubjectCodesContainsSelfMap.keySet().forEach(subjectCode -> {
            Set subjectCode2ChildSubjectCodesContainsSelf = (Set)subjectCode2ChildSubjectCodesContainsSelfMap.get(subjectCode);
            List currSubjectQmsItems = workingPaperQmsDTOs.stream().filter(offset -> subjectCode2ChildSubjectCodesContainsSelf.contains(offset.getSubjectCode())).collect(Collectors.toList());
            subjectWorkingPaperQmsDTOsMap.put((String)subjectCode, currSubjectQmsItems);
        });
        return subjectWorkingPaperQmsDTOsMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndAssistAndOrg(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap, Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrgMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndOrgMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        orgDirectChildsContainMergeOrgAndDiffOrgMap.forEach((orgCode, orgData) -> {
            List<String> listAllOrgByParentIdContainsSelf = GcOrgKindEnum.UNIONORG.equals((Object)orgData.getOrgKind()) && !orgCode.equals(condition.getOrgid()) ? this.listAllOrgByParentIdContainsSelf(condition, orgData.getCode()) : Arrays.asList(orgCode);
            subjectCode2OffsetItemDatasMap.forEach((subjectCode, subjectOffsetItemDatas) -> {
                ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
                String mergeKey = subjectCode + "_" + orgCode;
                if (mergeOffsetItemDatasBySubjectAndOrgMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasBySubjectAndOrgMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currSubjectAndOrgDxsItems = subjectOffsetItemDatas.stream().filter(subjectDxsItem -> {
                    String dxsOrgCode = subjectDxsItem.getOrgCode();
                    if (StringUtils.isEmpty((String)dxsOrgCode)) {
                        return false;
                    }
                    return listAllOrgByParentIdContainsSelf.contains(dxsOrgCode);
                }).collect(Collectors.toList());
                currSubjectAndOrgDxsItems.forEach(orignOffsetItemData -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndOrgMap.get(mergeKey);
                    this.mergeOffSetVchrItem((WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasBySubjectAndOrgMap;
    }

    protected void mergeOffSetVchrItem(WorkingPaperDxsItemDTO offsetItemData, WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO) {
        BigDecimal offSetDebit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetDebit()});
        BigDecimal offSetCredit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getOffSetCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getOffSetCredit()});
        BigDecimal debit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDebit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDebit()});
        BigDecimal credit = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getCredit(), (BigDecimal[])new BigDecimal[]{offsetItemData.getCredit()});
        BigDecimal diffd = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffd(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffd()});
        BigDecimal diffc = NumberUtils.add((BigDecimal)mergeWorkingPaperOffsetItemDTO.getDiffc(), (BigDecimal[])new BigDecimal[]{offsetItemData.getDiffc()});
        mergeWorkingPaperOffsetItemDTO.setOffSetDebit(offSetDebit);
        mergeWorkingPaperOffsetItemDTO.setOffSetCredit(offSetCredit);
        mergeWorkingPaperOffsetItemDTO.setCredit(credit);
        mergeWorkingPaperOffsetItemDTO.setDebit(debit);
        mergeWorkingPaperOffsetItemDTO.setDiffd(diffd);
        mergeWorkingPaperOffsetItemDTO.setDiffc(diffc);
    }

    protected Map<String, Map<String, BigDecimal>> getNrData(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap) {
        Set<String> directChildOrgCodes = directChildOrgCode2DataMap.keySet();
        HashMap<String, Map<String, BigDecimal>> zbTableFieldName2OrgZbValueMap = new HashMap<String, Map<String, BigDecimal>>();
        Set entityTableNames = NrTool.getEntityTableNames((String)condition.getSchemeID());
        Map<String, List<String>> zbTableName2FieldNamesMap = this.getZbTableName2FieldNamesMap(workingPaperTableDataVOS);
        zbTableName2FieldNamesMap.forEach((zbTableName, zbFieldNames) -> {
            StringBuffer querySql = new StringBuffer(128);
            querySql.append("select zb.").append("MDCODE").append(" AS ").append("MDCODE");
            for (String fieldName : zbFieldNames) {
                querySql.append(", zb.").append(fieldName).append(" as ").append(fieldName);
            }
            querySql.append(" from ").append((String)zbTableName).append("  zb\n ");
            querySql.append(" where 1=1\n");
            ArrayList<String> params = new ArrayList<String>();
            querySql.append(" and zb.").append("DATATIME").append("=? ");
            params.add(condition.getPeriodStr());
            if (DimensionUtils.isExistAdjust((String)condition.getTaskID())) {
                querySql.append(" and zb.ADJUST=").append("'").append(condition.getSelectAdjustCode()).append("'");
            }
            if (entityTableNames.contains("MD_CURRENCY")) {
                querySql.append(" and zb.").append("MD_CURRENCY").append("=? ");
                params.add(condition.getCurrencyCode());
            }
            querySql.append(" and  ((1=2)");
            directChildOrgCode2DataMap.forEach((orgCode, orgData) -> {
                String orgTypeId = StringUtils.isEmpty((String)orgData.getOrgTypeId()) ? condition.getOrg_type() : orgData.getOrgTypeId();
                querySql.append(" or (zb.").append("MDCODE").append("='").append((String)orgCode).append("' and zb.").append("MD_GCORGTYPE").append("='").append(orgTypeId).append("')");
            });
            querySql.append(")");
            List orgZbRowDatas = EntNativeSqlDefaultDao.getInstance().selectMap(querySql.toString(), params);
            for (Map orgZbRowData : orgZbRowDatas) {
                String orgID = StringUtils.toViewString(orgZbRowData.get("MDCODE"));
                if (orgID == null || !directChildOrgCodes.contains(orgID)) continue;
                orgZbRowData.forEach((zbFieldName, zbFieldValue) -> {
                    if ("MDCODE".equals(zbFieldName) || "MD_GCORGTYPE".equals(zbFieldName)) {
                        return;
                    }
                    HashMap<String, BigDecimal> orgId2ZbValueMap = (HashMap<String, BigDecimal>)zbTableFieldName2OrgZbValueMap.get(zbTableName + "_" + zbFieldName);
                    if (orgId2ZbValueMap == null) {
                        orgId2ZbValueMap = new HashMap<String, BigDecimal>();
                        zbTableFieldName2OrgZbValueMap.put(zbTableName + "_" + zbFieldName, orgId2ZbValueMap);
                    }
                    BigDecimal fieldValue = ConverterUtils.getAsBigDecimal((Object)zbFieldValue, (BigDecimal)BigDecimal.ZERO);
                    orgId2ZbValueMap.put(orgID, fieldValue);
                });
            }
        });
        return zbTableFieldName2OrgZbValueMap;
    }
}

