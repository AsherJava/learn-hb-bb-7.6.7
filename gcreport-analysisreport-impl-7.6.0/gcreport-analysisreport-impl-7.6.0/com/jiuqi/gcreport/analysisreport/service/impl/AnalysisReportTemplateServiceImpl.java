/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportRefTemplateType
 *  com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.nr.analysisreport.facade.DimensionConfigObj
 *  com.jiuqi.nr.analysisreport.internal.AnalysisTemp
 *  com.jiuqi.nr.analysisreport.service.SaveAnalysis
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.analysisreport.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.analysisreport.common.AnalysisReportTemplateConsts;
import com.jiuqi.gcreport.analysisreport.converter.AnalysisReportTemplateConverter;
import com.jiuqi.gcreport.analysisreport.converter.AnalysisReportTemplateRefOrgConverter;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateDao;
import com.jiuqi.gcreport.analysisreport.dao.AnalysisReportTemplateRefOrgDao;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.dto.AnalysisTempAndRefOrgDTO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportEO;
import com.jiuqi.gcreport.analysisreport.entity.AnalysisReportRefOrgEO;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportRefTemplateType;
import com.jiuqi.gcreport.analysisreport.enums.AnalysisReportVersionState;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportTemplateService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.nr.analysisreport.facade.DimensionConfigObj;
import com.jiuqi.nr.analysisreport.internal.AnalysisTemp;
import com.jiuqi.nr.analysisreport.service.SaveAnalysis;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
public class AnalysisReportTemplateServiceImpl
implements AnalysisReportTemplateService {
    @Autowired
    private AnalysisReportTemplateDao templateDao;
    @Autowired
    private AnalysisReportTemplateRefOrgDao templateItemDao;
    @Autowired
    private SaveAnalysis saveAnalysis;

    @Override
    public List<AnalysisReportRefOrgDTO> queryRefOrgsByTemplateId(String gcTemplateId) {
        List<AnalysisReportRefOrgEO> analysisReportItemEOS = this.templateItemDao.queryItemsByMrecid(gcTemplateId);
        List<AnalysisReportRefOrgDTO> reportRefOrgDTOS = analysisReportItemEOS.stream().map(eo -> AnalysisReportTemplateRefOrgConverter.convertEO2DTO(eo)).collect(Collectors.toList());
        return reportRefOrgDTOS;
    }

    @Override
    public AnalysisReportEO queryTemplateByTemplateId(String gcTemplateId) {
        AnalysisReportEO analysisReportEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)gcTemplateId));
        return analysisReportEO;
    }

    @Override
    public List<AnalysisTempAndRefOrgDTO> queryAnalysisTempsByTemplateId(String gcTemplateId) {
        AnalysisReportEO analysisReportEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)gcTemplateId));
        List<AnalysisTempAndRefOrgDTO> analysisTemps = this.queryAnalysisTempsByTemplateId(analysisReportEO);
        return analysisTemps;
    }

    @Override
    public List<AnalysisTempAndRefOrgDTO> queryAnalysisTempsByTemplateId(AnalysisReportEO analysisReportEO) {
        if (analysisReportEO == null) {
            return Collections.emptyList();
        }
        if (!"item".equalsIgnoreCase(analysisReportEO.getNodeType())) {
            return Collections.emptyList();
        }
        String refIdsStr = analysisReportEO.getRefIds();
        if (StringUtils.isEmpty((String)refIdsStr)) {
            return Collections.emptyList();
        }
        List refIds = StringUtils.split((String)refIdsStr, (String)",");
        String refTemplateType = analysisReportEO.getRefTemplateType();
        AnalysisReportRefTemplateType reportRefTemplateType = AnalysisReportRefTemplateType.getEnumByCode((String)refTemplateType);
        List<AnalysisTempAndRefOrgDTO> refAnalysisTemps = this.getAnalysisTempsByRefIds(analysisReportEO.getId(), reportRefTemplateType, refIds);
        return refAnalysisTemps;
    }

    @Override
    public List<AnalysisTempAndRefOrgDTO> getAnalysisTempsByRefIds(String gcTemplateId, AnalysisReportRefTemplateType refTemplateType, List<String> refTemplateIds) {
        if (refTemplateType == null) {
            throw new BusinessRuntimeException("\u5173\u8054\u6a21\u677f\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (CollectionUtils.isEmpty(refTemplateIds)) {
            return Collections.emptyList();
        }
        ArrayList<AnalysisTempAndRefOrgDTO> refAnalysisTemps = new ArrayList<AnalysisTempAndRefOrgDTO>();
        List<AnalysisReportRefOrgDTO> analysisReportRefOrgDTOS = this.queryRefOrgsByTemplateId(gcTemplateId);
        Map refTempateId2OrgEOMap = analysisReportRefOrgDTOS.stream().collect(Collectors.toMap(AnalysisReportRefOrgDTO::getTemplateId, Function.identity(), (k1, k2) -> k1));
        switch (refTemplateType) {
            case GC: {
                List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByIds(false, refTemplateIds);
                if (CollectionUtils.isEmpty(analysisReportEOS)) {
                    return Collections.emptyList();
                }
                analysisReportEOS.stream().forEach(analysisReportEO -> {
                    AnalysisReportRefOrgDTO refOrgDTO = (AnalysisReportRefOrgDTO)refTempateId2OrgEOMap.get(analysisReportEO.getId());
                    if (AnalysisReportRefTemplateType.GC.getCode().equals(analysisReportEO.getRefTemplateType())) {
                        throw new BusinessRuntimeException("\u5173\u8054\u521b\u5efa\u591a\u7ae0\u8282\u6a21\u677f\u7c7b\u578b\u65f6\uff0c\u6240\u5173\u8054\u7684\u6a21\u677f\u4e0d\u5141\u8bb8\u5d4c\u5957\u591a\u7ae0\u8282\u7c7b\u578b\u7684\u6a21\u677f");
                    }
                    if ("item".equals(analysisReportEO.getNodeType())) {
                        List<AnalysisTemp> analysisTemps = this.queryRefNrTemplatesByGcItemTemplateId((AnalysisReportEO)((Object)analysisReportEO));
                        List analysisTempAndRefOrgDTOS = analysisTemps.stream().map(analysisTemp -> new AnalysisTempAndRefOrgDTO(analysisTemp, refOrgDTO)).collect(Collectors.toList());
                        refAnalysisTemps.addAll(analysisTempAndRefOrgDTOS);
                    } else if ("group".equals(analysisReportEO.getNodeType())) {
                        List<AnalysisReportEO> itemTemplates = this.templateDao.queryAuthItemsByParentId(false, analysisReportEO.getId());
                        if (CollectionUtils.isEmpty(itemTemplates)) {
                            return;
                        }
                        Optional<AnalysisReportEO> isExistsGroupOptional = itemTemplates.stream().filter(itemTemplate -> "group".equals(itemTemplate.getNodeType())).findAny();
                        if (isExistsGroupOptional.isPresent()) {
                            throw new BusinessRuntimeException("\u5173\u8054\u521b\u5efa\u591a\u7ae0\u8282\u6a21\u677f\u7c7b\u578b\u65f6\uff0c\u6240\u5173\u8054\u7684\u6a21\u677f\u4e3a\u5206\u7ec4\u7c7b\u578b\u4e0d\u5141\u8bb8\u518d\u5d4c\u5957\u5173\u8054\u6a21\u677f\u5206\u7ec4\uff0c\u53ea\u5141\u8bb8\u5173\u8054\u6700\u4e0b\u7ea7\u5206\u7ec4\u8282\u70b9");
                        }
                        itemTemplates.stream().forEach(itemTemplate -> {
                            if (AnalysisReportRefTemplateType.GC.getCode().equals(itemTemplate.getRefTemplateType())) {
                                throw new BusinessRuntimeException("\u5173\u8054\u521b\u5efa\u591a\u7ae0\u8282\u6a21\u677f\u7c7b\u578b\u65f6\uff0c\u6240\u5173\u8054\u7684\u6a21\u677f\u4e0d\u5141\u8bb8\u5d4c\u5957\u591a\u7ae0\u8282\u7c7b\u578b\u7684\u6a21\u677f");
                            }
                            List<AnalysisTemp> analysisTemps = this.queryRefNrTemplatesByGcItemTemplateId((AnalysisReportEO)((Object)((Object)itemTemplate)));
                            List analysisTempAndRefOrgDTOS = analysisTemps.stream().map(analysisTemp -> new AnalysisTempAndRefOrgDTO(analysisTemp, refOrgDTO)).collect(Collectors.toList());
                            refAnalysisTemps.addAll(analysisTempAndRefOrgDTOS);
                        });
                    }
                });
                break;
            }
            case NR: {
                refTemplateIds.stream().forEach(nrRefTemplateId -> {
                    try {
                        AnalysisReportRefOrgDTO refOrgDTO = (AnalysisReportRefOrgDTO)refTempateId2OrgEOMap.get(nrRefTemplateId);
                        List<AnalysisTemp> analysisTemps = this.queryRefNrTemplatesByNrTemplateId((String)nrRefTemplateId);
                        List analysisTempAndRefOrgDTOS = analysisTemps.stream().map(analysisTemp -> new AnalysisTempAndRefOrgDTO(analysisTemp, refOrgDTO)).collect(Collectors.toList());
                        refAnalysisTemps.addAll(analysisTempAndRefOrgDTOS);
                    }
                    catch (Exception e) {
                        throw new BusinessRuntimeException((Throwable)e);
                    }
                });
                break;
            }
        }
        return refAnalysisTemps;
    }

    private List<AnalysisTemp> queryRefNrTemplatesByGcItemTemplateId(AnalysisReportEO analysisReportEO) {
        if (!"item".equals(analysisReportEO.getNodeType()) || !AnalysisReportRefTemplateType.NR.getCode().equals(analysisReportEO.getRefTemplateType())) {
            return Collections.emptyList();
        }
        String nrRefTemplateIdsStr = analysisReportEO.getRefIds();
        if (StringUtils.isEmpty((String)nrRefTemplateIdsStr)) {
            return Collections.emptyList();
        }
        List nrRefTemplateIds = StringUtils.split((String)nrRefTemplateIdsStr, (String)",");
        if (CollectionUtils.isEmpty(nrRefTemplateIds)) {
            return Collections.emptyList();
        }
        ArrayList<AnalysisTemp> refAnalysisTemps = new ArrayList<AnalysisTemp>();
        nrRefTemplateIds.stream().forEach(nrRefTemplateId -> {
            try {
                List<AnalysisTemp> analysisTemps = this.queryRefNrTemplatesByNrTemplateId((String)nrRefTemplateId);
                refAnalysisTemps.addAll(analysisTemps);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException((Throwable)e);
            }
        });
        return refAnalysisTemps;
    }

    private List<AnalysisTemp> queryRefNrTemplatesByNrTemplateId(String nrRefTemplateId) throws Exception {
        List analysisTemps;
        ArrayList<AnalysisTemp> refAnalysisTemps = new ArrayList<AnalysisTemp>();
        List groupAnalysisTemps = this.saveAnalysis.getTempListByGroupKey(nrRefTemplateId);
        if (!CollectionUtils.isEmpty(groupAnalysisTemps)) {
            refAnalysisTemps.addAll(groupAnalysisTemps);
        }
        if (!CollectionUtils.isEmpty(analysisTemps = this.saveAnalysis.getListByKeys(Arrays.asList(nrRefTemplateId)))) {
            refAnalysisTemps.addAll(analysisTemps);
        }
        return refAnalysisTemps;
    }

    @Override
    public Object queryRefAnalysisReportTree() {
        List analysisTree;
        try {
            analysisTree = this.saveAnalysis.getGroupAndReportTree();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return analysisTree;
    }

    @Override
    public List<AnalysisReportDTO> queryItemsByParentId(String parentId) {
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByParentId(true, parentId);
        if (analysisReportEOS == null) {
            return Collections.emptyList();
        }
        List<AnalysisReportDTO> analysisReportDTOS = analysisReportEOS.stream().map(analysisReportEO -> AnalysisReportTemplateConverter.convertEO2DTO(analysisReportEO)).collect(Collectors.toList());
        return analysisReportDTOS;
    }

    @Override
    public List<AnalysisReportDTO> queryItemsByParentIdContainSelf(String parentId) {
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByParentIdContainSelf(true, parentId);
        if (analysisReportEOS == null) {
            return Collections.emptyList();
        }
        List<AnalysisReportDTO> analysisReportDTOS = analysisReportEOS.stream().filter(eo -> !"root".equals(eo.getNodeType())).map(analysisReportEO -> AnalysisReportTemplateConverter.convertEO2DTO(analysisReportEO)).collect(Collectors.toList());
        return analysisReportDTOS;
    }

    @Override
    public AnalysisReportDTO queryAnalysisReportTree(boolean isAuth) {
        AnalysisReportTemplateServiceImpl reportGroupService = (AnalysisReportTemplateServiceImpl)SpringContextUtils.getBean(AnalysisReportTemplateServiceImpl.class);
        reportGroupService.initRoot();
        List<AnalysisReportDTO> analysisReportDTOS = this.listAnalysisReport(isAuth);
        List<AnalysisReportDTO> groupTreeDTOs = this.buildTree(analysisReportDTOS);
        AnalysisReportDTO groupTreeDTO = groupTreeDTOs.get(0);
        return groupTreeDTO;
    }

    @Override
    public List<Map<String, Object>> queryAnalysisReportLeafTemplates() {
        List<Map<String, Object>> datas = this.templateDao.queryAnalysisReportLeafTemplates();
        return datas;
    }

    @Override
    public boolean upAnalysisReport(String id) {
        AnalysisReportEO currentNodeEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)id));
        if (currentNodeEO == null) {
            return true;
        }
        AnalysisReportEO previousNode = this.templateDao.queryAuthPreviousItemById(true, id);
        if (previousNode == null) {
            return true;
        }
        Long currentSortOrder = currentNodeEO.getSortOrder();
        Long previousSortOrder = previousNode.getSortOrder();
        currentNodeEO.setSortOrder(previousSortOrder);
        previousNode.setSortOrder(currentSortOrder);
        this.templateDao.updateBatch(Arrays.asList(currentNodeEO, previousNode));
        return true;
    }

    @Override
    public boolean downAnalysisReport(String id) {
        AnalysisReportEO currentNodeEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)id));
        if (currentNodeEO == null) {
            return true;
        }
        AnalysisReportEO nextNode = this.templateDao.queryAuthNextItemById(true, id);
        if (nextNode == null) {
            return true;
        }
        Long currentSortOrder = currentNodeEO.getSortOrder();
        Long nextSortOrder = nextNode.getSortOrder();
        currentNodeEO.setSortOrder(nextSortOrder);
        nextNode.setSortOrder(currentSortOrder);
        this.templateDao.updateBatch(Arrays.asList(currentNodeEO, nextNode));
        return true;
    }

    @Override
    public boolean deleteAnalysisReport(Set<String> ids) {
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByIds(false, new ArrayList<String>(ids));
        if (CollectionUtils.isEmpty(analysisReportEOS)) {
            return true;
        }
        Map<String, List<AnalysisReportEO>> map = analysisReportEOS.stream().collect(Collectors.groupingBy(AnalysisReportEO::getNodeType));
        List<AnalysisReportEO> items = map.get("item");
        if (!CollectionUtils.isEmpty(items)) {
            this.templateDao.deleteBatch(items);
        }
        ArrayList<AnalysisReportEO> parentAndChildEOs = new ArrayList<AnalysisReportEO>();
        List<AnalysisReportEO> parentEOs = map.get("group");
        this.fetchItemsByParent(parentEOs, parentAndChildEOs);
        if (!CollectionUtils.isEmpty(parentAndChildEOs)) {
            this.templateDao.deleteBatch(parentAndChildEOs);
        }
        return true;
    }

    public void fetchItemsByParent(List<AnalysisReportEO> parentEOs, List<AnalysisReportEO> parentAndChildEOs) {
        if (CollectionUtils.isEmpty(parentEOs)) {
            return;
        }
        parentEOs.stream().forEach(parentEO -> {
            parentAndChildEOs.add((AnalysisReportEO)((Object)parentEO));
            this.recursionFetchItemsByParent(parentEO.getId(), parentAndChildEOs);
        });
    }

    public void recursionFetchItemsByParent(String parentId, List<AnalysisReportEO> parentAndChildEOs) {
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByParentId(false, parentId);
        if (CollectionUtils.isEmpty(analysisReportEOS)) {
            return;
        }
        analysisReportEOS.forEach(analysisReportEO -> {
            if ("item".equals(analysisReportEO.getNodeType())) {
                parentAndChildEOs.add((AnalysisReportEO)((Object)analysisReportEO));
                return;
            }
            if ("group".equals(analysisReportEO.getNodeType())) {
                parentAndChildEOs.add((AnalysisReportEO)((Object)analysisReportEO));
                this.recursionFetchItemsByParent(analysisReportEO.getId(), parentAndChildEOs);
                return;
            }
        });
    }

    @Override
    public List<AnalysisReportDTO> listAnalysisReport(boolean isAuth) {
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthAll(isAuth);
        if (CollectionUtils.isEmpty(analysisReportEOS)) {
            return Collections.emptyList();
        }
        List<AnalysisReportDTO> groupDTOS = analysisReportEOS.stream().map(eo -> AnalysisReportTemplateConverter.convertEO2DTO(eo)).collect(Collectors.toList());
        return groupDTOS;
    }

    @Override
    public boolean saveAnalysisReport(AnalysisReportDTO analysisReportDTO) throws Exception {
        List analysisTemps;
        String title = analysisReportDTO.getTitle();
        Objects.requireNonNull(title, "\u6807\u9898\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        List<AnalysisReportEO> sameTitleAnalysisReportEOs = this.templateDao.queryByTitle(false, title);
        AnalysisReportEO sameTitleAnalysisReportEO = null;
        if (!CollectionUtils.isEmpty(sameTitleAnalysisReportEOs)) {
            sameTitleAnalysisReportEO = sameTitleAnalysisReportEOs.get(0);
        }
        if (sameTitleAnalysisReportEO != null && !sameTitleAnalysisReportEO.getId().equals(analysisReportDTO.getId())) {
            throw new BusinessRuntimeException("\u5df2\u7ecf\u5b58\u5728\u6807\u9898[" + title + "]\uff0c\u4e0d\u5141\u8bb8\u91cd\u590d\u6dfb\u52a0");
        }
        AnalysisReportEO analysisReportEO = AnalysisReportTemplateConverter.convertDTO2EO(analysisReportDTO);
        if ("group".equals(analysisReportEO.getNodeType())) {
            if (ObjectUtils.isEmpty(analysisReportEO.getId())) {
                analysisReportEO.setId(UUIDUtils.newUUIDStr());
                analysisReportEO.setSortOrder(DateUtils.now().getTime());
                this.templateDao.add((BaseEntity)analysisReportEO);
            } else {
                this.templateDao.update((BaseEntity)analysisReportEO);
            }
            return true;
        }
        AnalysisReportVersionState versionState = AnalysisReportVersionState.getEnumByCode((String)analysisReportDTO.getVersionState());
        if (AnalysisReportVersionState.REAL_TIME.equals((Object)versionState) || AnalysisReportVersionState.LATEST_SAVED.equals((Object)versionState)) {
            this.checkTemplateDims(analysisReportDTO);
        }
        List refIds = analysisReportDTO.getRefIds() == null ? Collections.emptyList() : analysisReportDTO.getRefIds();
        HashMap templateId2TitleMap = new HashMap();
        List<AnalysisReportEO> analysisReportEOS = this.templateDao.queryAuthItemsByIds(false, refIds);
        analysisReportEOS.stream().forEach(eo -> templateId2TitleMap.put(eo.getId(), eo.getTitle()));
        List nrTemplateIds = refIds.stream().filter(refId -> templateId2TitleMap.get(refId) == null).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nrTemplateIds) && !CollectionUtils.isEmpty(analysisTemps = this.saveAnalysis.getListByKeys(nrTemplateIds))) {
            analysisTemps.stream().forEach(analysisTemp -> templateId2TitleMap.put(analysisTemp.getKey(), analysisTemp.getTitle()));
        }
        String refTitles = refIds.stream().map(refId -> ConverterUtils.getAsString(templateId2TitleMap.get(refId), (String)"")).collect(Collectors.joining(",", "", ""));
        analysisReportDTO.setRefTitles(refTitles);
        analysisReportEO.setRefTitles(refTitles);
        if (ObjectUtils.isEmpty(analysisReportEO.getId())) {
            analysisReportEO.setId(UUIDUtils.newUUIDStr());
            analysisReportEO.setSortOrder(DateUtils.now().getTime());
            this.templateDao.add((BaseEntity)analysisReportEO);
        } else {
            this.templateDao.update((BaseEntity)analysisReportEO);
        }
        this.templateItemDao.deleteItemsByMrecid(analysisReportEO.getId());
        ArrayList refOrgDTOs = analysisReportDTO.getRefOrgs();
        if (refOrgDTOs == null) {
            refOrgDTOs = new ArrayList();
        }
        Map templateId2RefOrgDTOMap = refOrgDTOs.stream().collect(Collectors.toMap(AnalysisReportRefOrgDTO::getTemplateId, Function.identity(), (k1, k2) -> k1));
        List analysisReportRefOrgEOS = refIds.stream().map(refId -> {
            AnalysisReportRefOrgDTO refOrgDTO = (AnalysisReportRefOrgDTO)templateId2RefOrgDTOMap.get(refId);
            if (refOrgDTO == null) {
                refOrgDTO = new AnalysisReportRefOrgDTO();
                refOrgDTO.setTemplateId(refId);
            }
            if (StringUtils.isEmpty((String)refOrgDTO.getId())) {
                refOrgDTO.setId(UUIDUtils.newUUIDStr());
            }
            if (StringUtils.isEmpty((String)refOrgDTO.getMrecid())) {
                refOrgDTO.setMrecid(analysisReportEO.getId());
            }
            refOrgDTO.setTemplateTitle((String)templateId2TitleMap.get(refOrgDTO.getTemplateId()));
            refOrgDTO.setSortOrder(ConverterUtils.getAsLong((Object)refIds.indexOf(refOrgDTO.getTemplateId()), (Long)0L));
            AnalysisReportRefOrgEO analysisReportRefOrgEO = AnalysisReportTemplateRefOrgConverter.convertDTO2EO(refOrgDTO);
            return analysisReportRefOrgEO;
        }).collect(Collectors.toList());
        this.templateItemDao.addBatch(analysisReportRefOrgEOS);
        return true;
    }

    private void checkTemplateDims(AnalysisReportDTO analysisReportDTO) {
        List refIds = analysisReportDTO.getRefIds();
        if (!ObjectUtils.isEmpty(refIds) && "item".equals(analysisReportDTO.getNodeType())) {
            AnalysisReportRefTemplateType refTemplateType = AnalysisReportRefTemplateType.getEnumByCode((String)analysisReportDTO.getRefTemplateType());
            List<AnalysisTempAndRefOrgDTO> analysisTempsByRefIds = this.getAnalysisTempsByRefIds(analysisReportDTO.getId(), refTemplateType, refIds);
            String firstRefDimsGroupKey = "";
            String firstAnalysisTempTitle = "";
            for (int i = 0; i < analysisTempsByRefIds.size(); ++i) {
                AnalysisTempAndRefOrgDTO analysisTempAndRefOrgDTO = analysisTempsByRefIds.get(i);
                AnalysisTemp analysisTemp = analysisTempAndRefOrgDTO.getAnalysisTemp();
                DimensionConfigObj dimension = analysisTemp.getDimension();
                List srcDims = dimension.getSrcDims();
                StringBuilder currentRefDimsGroupKeyBuilder = new StringBuilder();
                srcDims.stream().sorted(Comparator.comparing(o -> String.valueOf(o.getViewKey()))).forEach(srcDim -> currentRefDimsGroupKeyBuilder.append(srcDim.getViewKey()).append("_").append(srcDim.getType()));
                String currentRefDimsGroupKey = currentRefDimsGroupKeyBuilder.toString();
                if (i == 0) {
                    firstRefDimsGroupKey = currentRefDimsGroupKey;
                    firstAnalysisTempTitle = analysisTemp.getTitle();
                    continue;
                }
                if (firstRefDimsGroupKey.equals(currentRefDimsGroupKey)) continue;
                throw new BusinessRuntimeException("\u5173\u8054\u6a21\u677f[" + analysisTemp.getTitle() + "]\u4e0e\u6a21\u677f[" + firstAnalysisTempTitle + "]\u7684\u7ef4\u5ea6\u8bbe\u7f6e\u4e0d\u4e00\u81f4\u3002");
            }
        }
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public AnalysisReportDTO initRoot() {
        AnalysisReportEO rootEO = (AnalysisReportEO)this.templateDao.get((Serializable)((Object)AnalysisReportTemplateConsts.ROOT_PARENT_ID));
        if (rootEO == null) {
            rootEO = new AnalysisReportEO();
            rootEO.setRefTemplateType(AnalysisReportRefTemplateType.GC.getCode());
            rootEO.setId(AnalysisReportTemplateConsts.ROOT_PARENT_ID);
            rootEO.setParentId(null);
            rootEO.setTitle("\u5206\u6790\u62a5\u544a");
            rootEO.setSortOrder(0L);
            rootEO.setLeafFlag(0);
            rootEO.setStartFlag(1);
            rootEO.setCreator("system");
            rootEO.setNodeType("root");
            rootEO.setCreateTime(new Date());
            rootEO.setUpdateTime(new Date());
            this.templateDao.save(rootEO);
        }
        AnalysisReportDTO analysisReportDTO = AnalysisReportTemplateConverter.convertEO2DTO(rootEO);
        return analysisReportDTO;
    }

    public List<AnalysisReportDTO> buildTree(List<AnalysisReportDTO> list) {
        ArrayList<AnalysisReportDTO> roots = new ArrayList<AnalysisReportDTO>();
        for (AnalysisReportDTO node : list) {
            if (node.getParentId() != null) continue;
            roots.add(node);
        }
        for (AnalysisReportDTO root : roots) {
            this.addChildNodes(root, list);
        }
        return roots;
    }

    public void addChildNodes(AnalysisReportDTO parent, List<AnalysisReportDTO> list) {
        for (AnalysisReportDTO node : list) {
            if (node.getParentId() == null || !node.getParentId().equals(parent.getId())) continue;
            parent.getChildren().add(node);
            parent.setNotLeafFlag(Integer.valueOf(1));
            this.addChildNodes(node, list);
        }
    }
}

