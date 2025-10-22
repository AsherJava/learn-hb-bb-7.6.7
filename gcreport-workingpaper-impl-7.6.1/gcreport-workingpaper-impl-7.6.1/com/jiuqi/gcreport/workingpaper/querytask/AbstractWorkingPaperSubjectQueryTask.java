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
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition
 *  com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 */
package com.jiuqi.gcreport.workingpaper.querytask;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.workingpaper.querytask.AbstractWorkingPaperQueryTask;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperDxsItemDTO;
import com.jiuqi.gcreport.workingpaper.querytask.dto.WorkingPaperSubjectQmsItemDTO;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperQueryCondition;
import com.jiuqi.gcreport.workingpaper.vo.WorkingPaperTableDataVO;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractWorkingPaperSubjectQueryTask
extends AbstractWorkingPaperQueryTask {
    protected List<WorkingPaperTableDataVO> buildBlankWorkingPaperTableDataBySubjects(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap) {
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
            try {
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
                workingPaperTableDataVOS.add(vo);
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
        List<WorkingPaperTableDataVO> workingPaperTree = this.buildWorkingPaperToTree(workingPaperTableDataVOS, subjectCode2DataMap);
        List<WorkingPaperTableDataVO> sortedAndOrderWorkingPaperTableDataVOs = this.buildWorkingPaperToListFromTree(workingPaperTree);
        return sortedAndOrderWorkingPaperTableDataVOs;
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

    protected Map<String, List<WorkingPaperDxsItemDTO>> getSubjectWorkingPaperOffsetItemDTOs(WorkingPaperQueryCondition condition, Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap, List<WorkingPaperTableDataVO> workingPaperTableDataVOS) {
        HashMap<String, List<WorkingPaperDxsItemDTO>> subjectWorkingPaperOffsetItemDTOsMap = new HashMap<String, List<WorkingPaperDxsItemDTO>>();
        List<WorkingPaperDxsItemDTO> workingPaperOffsetItemDTOs = this.getWorkingPaperOffsetItemDTOs(condition);
        Map<String, List<WorkingPaperDxsItemDTO>> offsetItemDTOsMap = workingPaperOffsetItemDTOs.stream().collect(Collectors.groupingBy(WorkingPaperDxsItemDTO::getSubjectCode));
        for (WorkingPaperTableDataVO workingPaperTableDataVO : workingPaperTableDataVOS) {
            String subjectCode = workingPaperTableDataVO.getKmcode();
            Set<String> subjectCode2ChildSubjectCodesContainsSelf = subjectCode2ChildSubjectCodesContainsSelfMap.get(subjectCode);
            if (subjectCode2ChildSubjectCodesContainsSelf == null) continue;
            ArrayList<WorkingPaperDxsItemDTO> currSubjectOffsetItems = new ArrayList<WorkingPaperDxsItemDTO>();
            for (String code : subjectCode2ChildSubjectCodesContainsSelf) {
                List<WorkingPaperDxsItemDTO> items = offsetItemDTOsMap.get(code);
                if (items == null) continue;
                currSubjectOffsetItems.addAll(items);
            }
            subjectWorkingPaperOffsetItemDTOsMap.put(subjectCode, currSubjectOffsetItems);
        }
        return subjectWorkingPaperOffsetItemDTOsMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubject(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        subjectCode2OffsetItemDatasMap.forEach((subjectCode, orignOffsetItemDatas) -> {
            WorkingPaperDxsItemDTO workingPaperDxsItemDTO = WorkingPaperDxsItemDTO.empty();
            workingPaperDxsItemDTO.setSubjectCode((String)subjectCode);
            mergeOffsetItemDatasBySubjectMap.put((String)subjectCode, workingPaperDxsItemDTO);
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
            orignOffsetItemDatas.stream().forEach(orignOffsetItemData -> {
                WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectMap.get(subjectCode);
                this.mergeOffSetVchrItem(consolidatedSubjectVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
            });
        });
        return mergeOffsetItemDatasBySubjectMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndOrg(WorkingPaperQueryCondition condition, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap, Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrgMap) {
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
                    this.mergeOffSetVchrItem(consolidatedSubjectVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasBySubjectAndOrgMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndYwlx(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap, Map<String, String> ywlxCode2TitleMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndYwlxMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        subjectCode2OffsetItemDatasMap.forEach((subjectCode, subjectOffsetItemDatas) -> {
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
            ywlxCode2TitleMap.forEach((ywlxCode, ywlxTitle) -> {
                String mergeKey = subjectCode + "_" + ywlxCode;
                if (mergeOffsetItemDatasBySubjectAndYwlxMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasBySubjectAndYwlxMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currSubjectAndYwlxDxsItems = subjectOffsetItemDatas.stream().filter(subjectDxsItem -> {
                    String dxsYwlxCode = ConverterUtils.getAsString((Object)subjectDxsItem.getYwlxCode(), (String)"OTHER");
                    return ywlxCode.equals(dxsYwlxCode);
                }).collect(Collectors.toList());
                currSubjectAndYwlxDxsItems.forEach(orignOffsetItemData -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndYwlxMap.get(mergeKey);
                    this.mergeOffSetVchrItem(consolidatedSubjectVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasBySubjectAndYwlxMap;
    }

    protected Map<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndElmMode(Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, List<WorkingPaperDxsItemDTO>> subjectCode2OffsetItemDatasMap, Map<String, String> offSetElmModeCode2TitleMap) {
        HashMap<String, WorkingPaperDxsItemDTO> mergeOffsetItemDatasBySubjectAndElmModeMap = new HashMap<String, WorkingPaperDxsItemDTO>();
        subjectCode2OffsetItemDatasMap.forEach((subjectCode, subjectOffsetItemDatas) -> {
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
            offSetElmModeCode2TitleMap.forEach((elmCode, elmTitle) -> {
                String mergeKey = subjectCode + "_" + elmCode;
                if (mergeOffsetItemDatasBySubjectAndElmModeMap.get(mergeKey) == null) {
                    mergeOffsetItemDatasBySubjectAndElmModeMap.put(mergeKey, WorkingPaperDxsItemDTO.empty());
                }
                List<WorkingPaperDxsItemDTO> currSubjectAndElmModeDxsItems = subjectOffsetItemDatas.stream().filter(subjectDxsItem -> {
                    String dxsElmMode = ConverterUtils.getAsString((Object)subjectDxsItem.getElmMode(), (String)"OTHER");
                    return elmCode.equals(dxsElmMode);
                }).collect(Collectors.toList());
                currSubjectAndElmModeDxsItems.forEach(orignOffsetItemData -> {
                    WorkingPaperDxsItemDTO mergeWorkingPaperOffsetItemDTO = (WorkingPaperDxsItemDTO)mergeOffsetItemDatasBySubjectAndElmModeMap.get(mergeKey);
                    this.mergeOffSetVchrItem(consolidatedSubjectVO, (WorkingPaperDxsItemDTO)orignOffsetItemData, mergeWorkingPaperOffsetItemDTO);
                });
            });
        });
        return mergeOffsetItemDatasBySubjectAndElmModeMap;
    }

    protected Map<String, WorkingPaperSubjectQmsItemDTO> mergeWorkingPaperQmsDTOsBySubjectAndOrg(WorkingPaperQueryCondition condition, List<WorkingPaperSubjectQmsItemDTO> workingPaperQmsDTOs, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap, Map<String, GcOrgCacheVO> orgDirectChildsContainMergeOrgAndDiffOrgMap, Map<String, Set<String>> subjectCode2ChildSubjectCodesContainsSelfMap) {
        int initialCapacity = subjectCode2DataMap.size() * orgDirectChildsContainMergeOrgAndDiffOrgMap.size();
        HashMap mergeQmsDTOsBySubjectAndLeafOrgMap = new HashMap(initialCapacity);
        workingPaperQmsDTOs.forEach(workingPaperQmsDTO -> {
            String subjectCode = ConverterUtils.getAsString((Object)workingPaperQmsDTO.getSubjectCode(), (String)"");
            String orgCode = ConverterUtils.getAsString((Object)workingPaperQmsDTO.getOrgCode(), (String)"");
            String mergeKey = subjectCode + "_" + orgCode;
            if (mergeQmsDTOsBySubjectAndLeafOrgMap.get(mergeKey) == null) {
                mergeQmsDTOsBySubjectAndLeafOrgMap.put(mergeKey, new HashSet());
            }
            ((Set)mergeQmsDTOsBySubjectAndLeafOrgMap.get(mergeKey)).add(workingPaperQmsDTO);
        });
        HashMap<String, WorkingPaperSubjectQmsItemDTO> mergeQmsDTOsBySubjectAndOrgMap = new HashMap<String, WorkingPaperSubjectQmsItemDTO>(initialCapacity);
        orgDirectChildsContainMergeOrgAndDiffOrgMap.forEach((orgCode, orgData) -> {
            List<String> listAllOrgByParentIdContainsSelf = GcOrgKindEnum.UNIONORG.equals((Object)orgData.getOrgKind()) && !orgCode.equals(condition.getOrgid()) ? this.listAllOrgByParentIdContainsSelf(condition, orgData.getCode()) : Arrays.asList(orgCode);
            subjectCode2DataMap.forEach((subjectCode, subjectData) -> {
                String mergeKey = subjectCode + "_" + orgCode;
                HashSet listAllOrgByParentIdContainsSelfQmsDTOs = new HashSet();
                listAllOrgByParentIdContainsSelf.forEach(leafOrgCode -> {
                    String leafMergeKey = subjectCode + "_" + leafOrgCode;
                    Set workingPaperSubjectQmsItemDTOS = (Set)mergeQmsDTOsBySubjectAndLeafOrgMap.get(leafMergeKey);
                    if (CollectionUtils.isEmpty((Collection)workingPaperSubjectQmsItemDTOS)) {
                        return;
                    }
                    listAllOrgByParentIdContainsSelfQmsDTOs.addAll(workingPaperSubjectQmsItemDTOS);
                });
                if (mergeQmsDTOsBySubjectAndOrgMap.get(mergeKey) == null) {
                    WorkingPaperSubjectQmsItemDTO subjectQmsItemDTO = WorkingPaperSubjectQmsItemDTO.empty();
                    subjectQmsItemDTO.setOrgCode((String)orgCode);
                    subjectQmsItemDTO.setSubjectCode((String)subjectCode);
                    mergeQmsDTOsBySubjectAndOrgMap.put(mergeKey, subjectQmsItemDTO);
                }
                if (CollectionUtils.isEmpty(listAllOrgByParentIdContainsSelfQmsDTOs)) {
                    return;
                }
                ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(subjectCode);
                if (consolidatedSubjectVO == null) {
                    return;
                }
                listAllOrgByParentIdContainsSelfQmsDTOs.forEach(currSubjectAndOrgQmsItem -> {
                    WorkingPaperSubjectQmsItemDTO mergeWorkingPaperQmsItemDTO = (WorkingPaperSubjectQmsItemDTO)mergeQmsDTOsBySubjectAndOrgMap.get(mergeKey);
                    mergeWorkingPaperQmsItemDTO.setZbValue(NumberUtils.add((BigDecimal)currSubjectAndOrgQmsItem.getZbValue(), (BigDecimal[])new BigDecimal[]{mergeWorkingPaperQmsItemDTO.getZbValue()}));
                });
            });
        });
        return mergeQmsDTOsBySubjectAndOrgMap;
    }

    protected List<WorkingPaperSubjectQmsItemDTO> getWorkingPaperQmsDTOs(WorkingPaperQueryCondition condition, List<WorkingPaperTableDataVO> workingPaperTableDataVOS, Map<String, GcOrgCacheVO> directChildOrgCode2DataMap, Map<String, ConsolidatedSubjectVO> subjectCode2DataMap) {
        Map<String, Map<String, BigDecimal>> zbTableFieldName2OrgZbValueMap = this.getNrData(condition, workingPaperTableDataVOS, directChildOrgCode2DataMap);
        ArrayList<WorkingPaperSubjectQmsItemDTO> workingPaperQmsDTOS = new ArrayList<WorkingPaperSubjectQmsItemDTO>();
        workingPaperTableDataVOS.stream().forEach(vo -> {
            ConsolidatedSubjectVO consolidatedSubjectVO = (ConsolidatedSubjectVO)subjectCode2DataMap.get(vo.getKmcode());
            if (consolidatedSubjectVO == null) {
                return;
            }
            String tableFieldName = vo.getZbtable() + "_" + vo.getZbfield();
            Map orgId2ZbValueMap = (Map)zbTableFieldName2OrgZbValueMap.get(tableFieldName);
            if (org.springframework.util.CollectionUtils.isEmpty(orgId2ZbValueMap)) {
                return;
            }
            orgId2ZbValueMap.forEach((orgId, zbFeildValue) -> {
                WorkingPaperSubjectQmsItemDTO workingPaperQmsDTO = new WorkingPaperSubjectQmsItemDTO();
                workingPaperQmsDTO.setSubjectCode(vo.getKmcode());
                workingPaperQmsDTO.setOrgCode((String)orgId);
                workingPaperQmsDTO.setZbValue((BigDecimal)zbFeildValue);
                workingPaperQmsDTO.setSubjectOrient(OrientEnum.valueOf((Integer)consolidatedSubjectVO.getOrient()));
                workingPaperQmsDTOS.add(workingPaperQmsDTO);
            });
        });
        return workingPaperQmsDTOS;
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

