/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO
 *  com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum
 *  com.jiuqi.gcreport.clbr.enums.ClbrVchrControlEnum
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.apache.commons.collections4.CollectionUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.clbr.dao.ClbrSchemeDao;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeEO;
import com.jiuqi.gcreport.clbr.entity.ClbrSchemeInfoEO;
import com.jiuqi.gcreport.clbr.enums.ClbrFlowControlEnum;
import com.jiuqi.gcreport.clbr.enums.ClbrVchrControlEnum;
import com.jiuqi.gcreport.clbr.executor.model.ClbrSchemeExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrSchemeService;
import com.jiuqi.gcreport.clbr.util.ClbrUtils;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClbrSchemeServiceImpl
implements ClbrSchemeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClbrSchemeServiceImpl.class);
    @Autowired
    private ClbrSchemeDao clbrSchemeDao;
    @Autowired
    private GcBaseDataService gcBaseDataService;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ClbrSchemeVO save(ClbrSchemeVO clbrSchemeVO) {
        String repeatScheme;
        this.checkParams(clbrSchemeVO);
        ClbrSchemeDTO clbrSchemeDTO = new ClbrSchemeDTO();
        BeanUtils.copyProperties(clbrSchemeVO, clbrSchemeDTO);
        List clbrSchemeEOList = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        Optional<ClbrSchemeEO> sameNameScheme = clbrSchemeEOList.stream().filter(clbrSchemeEO -> clbrSchemeEO.getTitle().equals(clbrSchemeVO.getTitle())).findAny();
        Optional<ClbrSchemeEO> parentNode = clbrSchemeEOList.stream().filter(clbrSchemeEO -> clbrSchemeEO.getId().equals(clbrSchemeVO.getParentId())).findAny();
        List<ClbrSchemeEO> leafNode = clbrSchemeEOList.stream().filter(clbrSchemeEO -> new Integer(1).equals(clbrSchemeEO.getLeafFlag())).collect(Collectors.toList());
        if (sameNameScheme.isPresent()) {
            throw new BusinessRuntimeException("\u65b9\u6848\u540d\u79f0\u91cd\u590d");
        }
        if (!parentNode.isPresent()) {
            throw new BusinessRuntimeException("\u5206\u7ec4\u5df2\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        if (Boolean.TRUE.equals(clbrSchemeVO.getLeafFlag()) && StringUtils.isNotBlank((CharSequence)(repeatScheme = this.verifyOnly(leafNode, clbrSchemeDTO)))) {
            throw new BusinessRuntimeException("\u6b64\u65b9\u6848\u4e0e[" + repeatScheme + "]\u65b9\u6848\u91cd\u590d");
        }
        ClbrSchemeEO clbrSchemeEO2 = this.initBeforeSave(clbrSchemeDTO);
        this.clbrSchemeDao.add((BaseEntity)clbrSchemeEO2);
        clbrSchemeVO.setId(clbrSchemeEO2.getId());
        return clbrSchemeVO;
    }

    private String verifyOnly(List<ClbrSchemeEO> clbrSchemeEOList, ClbrSchemeDTO clbrSchemeDTO) {
        for (ClbrSchemeEO clbrSchemeEO : clbrSchemeEOList) {
            ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)clbrSchemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
            ArrayList<String> infoClbrTypes = new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getClbrTypes().split(",")));
            List<String> infoRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getRelations().split(","))));
            List<String> infoOppRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getOppRelations().split(","))));
            infoClbrTypes.retainAll(new ArrayList<String>(Arrays.asList(clbrSchemeDTO.getClbrTypes().split(","))));
            infoRelations.retainAll(RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeDTO.getRelations().split(",")))));
            infoOppRelations.retainAll(RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeDTO.getOppRelations().split(",")))));
            if (!CollectionUtils.isNotEmpty(infoClbrTypes) || !CollectionUtils.isNotEmpty(infoRelations) || !CollectionUtils.isNotEmpty(infoOppRelations)) continue;
            return clbrSchemeEO.getTitle();
        }
        return null;
    }

    private ClbrSchemeEO initBeforeSave(ClbrSchemeDTO clbrSchemeDTO) {
        ClbrSchemeEO clbrSchemeEO = new ClbrSchemeEO();
        if (Boolean.TRUE.equals(clbrSchemeDTO.getLeafFlag())) {
            List<String> relations = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(clbrSchemeDTO.getRelations().split(","))));
            List<String> oppRelations = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(clbrSchemeDTO.getOppRelations().split(","))));
            ClbrSchemeInfoEO clbrSchemeInfoEO = new ClbrSchemeInfoEO();
            clbrSchemeInfoEO.setClbrTypes(clbrSchemeDTO.getClbrTypes());
            clbrSchemeInfoEO.setRelations(String.join((CharSequence)",", relations));
            clbrSchemeInfoEO.setOppRelations(String.join((CharSequence)",", oppRelations));
            clbrSchemeInfoEO.setOppClbrTypes(clbrSchemeDTO.getOppClbrTypes());
            clbrSchemeEO.setFlowControlType(clbrSchemeDTO.getFlowControlType());
            clbrSchemeEO.setVchrControlType(clbrSchemeDTO.getVchrControlType());
            clbrSchemeEO.setClbrInfo(JSONUtil.toJSONString((Object)clbrSchemeInfoEO));
        }
        clbrSchemeEO.setId(UUIDUtils.newUUIDStr());
        clbrSchemeEO.setCreateTime(new Date());
        clbrSchemeEO.setParentId(clbrSchemeDTO.getParentId());
        clbrSchemeEO.setLeafFlag(clbrSchemeDTO.getLeafFlag() != false ? 1 : 0);
        clbrSchemeEO.setTitle(clbrSchemeDTO.getTitle());
        clbrSchemeEO.setSchemeDesc(clbrSchemeDTO.getSchemeDesc());
        return clbrSchemeEO;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean edit(ClbrSchemeVO clbrSchemeVO) {
        Optional<ClbrSchemeEO> sameNameScheme;
        if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getId())) {
            LOGGER.warn("\u4e1a\u52a1\u5f02\u5e38\uff1a\u65b9\u6848\u914d\u7f6eID\u4e3a\u7a7a,\u53c2\u6570={}", (Object)clbrSchemeVO);
            throw new BusinessRuntimeException("\u4e1a\u52a1\u5f02\u5e38\uff1a\u65b9\u6848\u914d\u7f6eID\u4e3a\u7a7a");
        }
        ClbrSchemeEO clbrSchemeEO = new ClbrSchemeEO();
        clbrSchemeEO.setId(clbrSchemeVO.getId());
        ClbrSchemeEO schemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)clbrSchemeEO);
        if (Objects.isNull((Object)schemeEO)) {
            throw new BusinessRuntimeException("\u6b64\u8bb0\u5f55\u5df2\u88ab\u5220\u9664");
        }
        List clbrSchemeEOList = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        List<ClbrSchemeEO> clbrSchemeEOListNotContainSelf = clbrSchemeEOList.stream().filter(item -> !item.getId().equals(clbrSchemeVO.getId()) && Objects.equals(1, item.getLeafFlag())).collect(Collectors.toList());
        if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getTitle()) && (sameNameScheme = clbrSchemeEOListNotContainSelf.stream().filter(item -> item.getTitle().equals(clbrSchemeVO.getTitle())).findAny()).isPresent()) {
            throw new BusinessRuntimeException("\u65b9\u6848\u540d\u79f0\u91cd\u590d");
        }
        if (Objects.equals(1, schemeEO.getLeafFlag())) {
            ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)schemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getClbrTypes())) {
                clbrSchemeInfoEO.setClbrTypes(clbrSchemeVO.getClbrTypes());
            }
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getOppClbrTypes())) {
                clbrSchemeInfoEO.setOppClbrTypes(clbrSchemeVO.getOppClbrTypes());
            }
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getRelations())) {
                List<String> relations = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(clbrSchemeVO.getRelations().split(","))));
                clbrSchemeInfoEO.setRelations(String.join((CharSequence)",", relations));
            }
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getOppRelations())) {
                List<String> oppRelations = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(clbrSchemeVO.getOppRelations().split(","))));
                clbrSchemeInfoEO.setOppRelations(String.join((CharSequence)",", oppRelations));
            }
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getFlowControlType())) {
                clbrSchemeEO.setFlowControlType(clbrSchemeVO.getFlowControlType());
            }
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeVO.getVchrControlType())) {
                clbrSchemeEO.setVchrControlType(clbrSchemeVO.getVchrControlType());
            }
            clbrSchemeEO.setTitle(clbrSchemeVO.getTitle());
            ClbrSchemeDTO clbrSchemeDTO = new ClbrSchemeDTO();
            BeanUtils.copyProperties(clbrSchemeInfoEO, clbrSchemeDTO);
            String repeatScheme = this.verifyOnly(clbrSchemeEOListNotContainSelf, clbrSchemeDTO);
            if (StringUtils.isNotBlank((CharSequence)repeatScheme)) {
                throw new BusinessRuntimeException("\u6b64\u65b9\u6848\u4e0e[" + repeatScheme + "]\u65b9\u6848\u91cd\u590d");
            }
            clbrSchemeEO.setClbrInfo(JSONUtil.toJSONString((Object)clbrSchemeInfoEO));
        } else {
            clbrSchemeEO.setTitle(clbrSchemeVO.getTitle());
            clbrSchemeEO.setSchemeDesc(clbrSchemeVO.getSchemeDesc());
        }
        this.clbrSchemeDao.updateSelective((BaseEntity)clbrSchemeEO);
        return true;
    }

    private void checkParams(ClbrSchemeVO clbrSchemeVO) {
        if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getParentId()) || Objects.isNull(clbrSchemeVO.getLeafFlag())) {
            throw new BusinessRuntimeException("\u65b9\u6848\u6811\u7236\u8282\u70b9\u548c\u662f\u5426\u53f6\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getTitle())) {
            throw new BusinessRuntimeException("\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        if (Boolean.TRUE.equals(clbrSchemeVO.getLeafFlag())) {
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getRelations())) {
                throw new BusinessRuntimeException("\u53d1\u8d77\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getOppClbrTypes())) {
                throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getOppRelations())) {
                throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getFlowControlType())) {
                throw new BusinessRuntimeException("\u6d41\u7a0b\u63a7\u5236\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getVchrControlType())) {
                throw new BusinessRuntimeException("\u51ed\u8bc1\u63a7\u5236\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
            if (StringUtils.isBlank((CharSequence)clbrSchemeVO.getClbrTypes())) {
                throw new BusinessRuntimeException("\u53d1\u8d77\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002");
            }
        }
    }

    @Override
    public PageInfo<ClbrSchemeVO> list(ClbrSchemeCondition clbrSchemeCondition) {
        Integer pageNum = clbrSchemeCondition.getPageNum();
        Integer pageSize = clbrSchemeCondition.getPageSize();
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            throw new BusinessRuntimeException("\u5206\u9875\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        List<ClbrSchemeEO> requiredList = this.listByCondition(clbrSchemeCondition);
        if (CollectionUtils.isEmpty(requiredList)) {
            return PageInfo.empty();
        }
        List pageList = requiredList.stream().skip((long)(clbrSchemeCondition.getPageNum() - 1) * (long)clbrSchemeCondition.getPageSize().intValue()).limit(clbrSchemeCondition.getPageSize().intValue()).sorted(Comparator.comparing(ClbrSchemeEO::getParentId).thenComparing(ClbrSchemeEO::getCreateTime).reversed()).collect(Collectors.toList());
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Map<String, String> clbrTypeMap = clbrTypeBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Map<String, String> relationMap = relationBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List clbrSchemeVOS = pageList.stream().map(clbrSchemeEO -> this.buildResultObject((ClbrSchemeEO)((Object)clbrSchemeEO), clbrTypeMap, relationMap)).collect(Collectors.toList());
        return PageInfo.of(clbrSchemeVOS, (int)clbrSchemeCondition.getPageNum(), (int)clbrSchemeCondition.getPageSize(), (int)requiredList.size());
    }

    @Override
    public List<ClbrSchemeEO> listByCondition(ClbrSchemeCondition clbrSchemeCondition) {
        List<ClbrSchemeTreeVO> clbrSchemeTreeDTOS = this.listAllLeafSchemes(clbrSchemeCondition.getId());
        if (CollectionUtils.isEmpty(clbrSchemeTreeDTOS)) {
            return Collections.emptyList();
        }
        List clbrSchemeEOList = clbrSchemeTreeDTOS.stream().map(clbrSchemeTreeDTO -> {
            ClbrSchemeEO clbrSchemeEO = new ClbrSchemeEO();
            BeanUtils.copyProperties(clbrSchemeTreeDTO, (Object)clbrSchemeEO);
            return clbrSchemeEO;
        }).collect(Collectors.toList());
        ArrayList<Object> condClbrTypes = new ArrayList();
        ArrayList<Object> condOppClbrTypes = new ArrayList();
        ArrayList<Object> condRelations = new ArrayList();
        ArrayList<Object> condOppRelations = new ArrayList();
        if (StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getClbrTypes())) {
            condClbrTypes = new ArrayList<String>(Arrays.asList(clbrSchemeCondition.getClbrTypes().split(",")));
        }
        if (StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getOppClbrTypes())) {
            condOppClbrTypes = new ArrayList<String>(Arrays.asList(clbrSchemeCondition.getOppClbrTypes().split(",")));
        }
        if (StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getRelations())) {
            condRelations = new ArrayList<String>(Arrays.asList(clbrSchemeCondition.getRelations().split(",")));
        }
        if (StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getOppRelations())) {
            condOppRelations = new ArrayList<String>(Arrays.asList(clbrSchemeCondition.getOppRelations().split(",")));
        }
        ArrayList<ClbrSchemeEO> requiredList = new ArrayList<ClbrSchemeEO>();
        for (ClbrSchemeEO clbrSchemeEO : clbrSchemeEOList) {
            ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)clbrSchemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
            ArrayList<String> clbrTypes = new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getClbrTypes().split(",")));
            ArrayList<String> oppClbrTypes = new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getOppClbrTypes().split(",")));
            List<String> relations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getRelations().split(","))));
            List<String> oppRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(clbrSchemeInfoEO.getOppRelations().split(","))));
            if (StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getFlowControlType()) && !clbrSchemeEO.getFlowControlType().equals(clbrSchemeCondition.getFlowControlType()) || StringUtils.isNotBlank((CharSequence)clbrSchemeCondition.getVchrControlType()) && !clbrSchemeEO.getVchrControlType().equals(clbrSchemeCondition.getVchrControlType()) || CollectionUtils.isNotEmpty(condClbrTypes) && !clbrTypes.containsAll(condClbrTypes) || CollectionUtils.isNotEmpty(condOppClbrTypes) && !oppClbrTypes.containsAll(condOppClbrTypes) || CollectionUtils.isNotEmpty(condRelations) && !relations.containsAll(condRelations) || CollectionUtils.isNotEmpty(condOppRelations) && !oppRelations.containsAll(condOppRelations)) continue;
            requiredList.add(clbrSchemeEO);
        }
        return requiredList;
    }

    @Override
    public List<ClbrSchemeDTO> listByConditionToDTO(ClbrSchemeCondition clbrSchemeCondition) {
        List<ClbrSchemeEO> clbrSchemeEOList = this.listByCondition(clbrSchemeCondition);
        return clbrSchemeEOList.stream().map(clbrSchemeEO -> {
            ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)clbrSchemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
            ClbrSchemeDTO clbrSchemeDTO = new ClbrSchemeDTO();
            BeanUtils.copyProperties(clbrSchemeInfoEO, clbrSchemeDTO);
            BeanUtils.copyProperties(clbrSchemeEO, clbrSchemeDTO);
            return clbrSchemeDTO;
        }).collect(Collectors.toList());
    }

    private ClbrSchemeVO buildResultObject(ClbrSchemeEO clbrSchemeEO, Map<String, String> allClbrTypeMap, Map<String, String> allRelationMap) {
        ClbrSchemeVO resultClbrSchemeVo = new ClbrSchemeVO();
        BeanUtils.copyProperties((Object)clbrSchemeEO, resultClbrSchemeVo);
        ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)clbrSchemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
        String[] clbrTypeArray = clbrSchemeInfoEO.getClbrTypes().split(",");
        Map clbrTypesMap = resultClbrSchemeVo.getClbrTypesMap();
        String clbrTypeName = this.name5CodeConvert(clbrTypeArray, allClbrTypeMap, clbrTypesMap);
        resultClbrSchemeVo.setClbrTypes(clbrTypeName);
        String[] oppClbrTypeArray = clbrSchemeInfoEO.getOppClbrTypes().split(",");
        Map oppClbrTypesMap = resultClbrSchemeVo.getOppClbrTypesMap();
        String oppClbrTypeName = this.name5CodeConvert(oppClbrTypeArray, allClbrTypeMap, oppClbrTypesMap);
        resultClbrSchemeVo.setOppClbrTypes(oppClbrTypeName);
        String[] relations = clbrSchemeInfoEO.getRelations().split(",");
        Map relationsMap = resultClbrSchemeVo.getRelationsMap();
        String relationNames = this.name5CodeConvert(relations, allRelationMap, relationsMap);
        resultClbrSchemeVo.setRelations(relationNames);
        String[] oppRelations = clbrSchemeInfoEO.getOppRelations().split(",");
        Map oppRelationsMap = resultClbrSchemeVo.getOppRelationsMap();
        String oppRelationNames = this.name5CodeConvert(oppRelations, allRelationMap, oppRelationsMap);
        resultClbrSchemeVo.setOppRelations(oppRelationNames);
        resultClbrSchemeVo.setFlowControlType(Objects.requireNonNull(ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType())).getTitle());
        resultClbrSchemeVo.setVchrControlType(Objects.requireNonNull(ClbrVchrControlEnum.getEnumByCode((String)clbrSchemeEO.getVchrControlType())).getTitle());
        return resultClbrSchemeVo;
    }

    private String name5CodeConvert(String[] target, Map<String, String> mapping, Map<String, String> relationMap) {
        StringBuilder builder = new StringBuilder();
        for (String str : target) {
            String name = mapping.get(str);
            if (!StringUtils.isNotBlank((CharSequence)name)) continue;
            builder.append(name).append(",");
            if (!Objects.nonNull(relationMap)) continue;
            relationMap.put(str, name);
        }
        return builder.substring(0, builder.length() > 0 ? builder.length() - 1 : 0);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(List<String> schemeIds) {
        List<ClbrSchemeEO> clbrSchemeEOList;
        if (CollectionUtils.isEmpty(schemeIds)) {
            return;
        }
        if (ClbrUtils.SELECTED_SCHEME_TREE.equals(schemeIds.size()) && CollectionUtils.isNotEmpty(clbrSchemeEOList = this.clbrSchemeDao.selectDirectChildSchemes(schemeIds.get(0)))) {
            throw new BusinessRuntimeException("\u8be5\u5206\u7ec4\u4e0b\u5b58\u5728\u6570\u636e\u6216\u5206\u7ec4\uff0c\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        this.clbrSchemeDao.deleteByIds(schemeIds);
    }

    @Override
    public StringBuilder clbrSchemeImport(List<ClbrSchemeExcelModel> rowDatas) {
        StringBuilder log = new StringBuilder();
        if (CollectionUtils.isEmpty(rowDatas)) {
            return log;
        }
        ArrayList<ClbrSchemeEO> clbrSchemeEOList = new ArrayList<ClbrSchemeEO>();
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Set<String> clbrTypeSet = clbrTypeBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Set<String> relationSet = relationBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        List allClbrSchemeEOList = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        List<ClbrSchemeEO> leafNode = allClbrSchemeEOList.stream().filter(clbrSchemeEO -> Objects.equals(1, clbrSchemeEO.getLeafFlag())).collect(Collectors.toList());
        Map<String, ClbrSchemeEO> schemeNodeMap = leafNode.stream().collect(Collectors.toMap(ClbrSchemeEO::getTitle, k -> k, (k1, k2) -> k2));
        Map<String, ClbrSchemeEO> groupNodeMap = allClbrSchemeEOList.stream().filter(clbrSchemeEO -> Objects.equals(0, clbrSchemeEO.getLeafFlag())).collect(Collectors.toMap(ClbrSchemeEO::getTitle, k -> k, (k1, k2) -> k2));
        HashSet<Integer> invalidSet = new HashSet<Integer>();
        for (int i = 0; i < rowDatas.size(); ++i) {
            ClbrSchemeExcelModel clbrSchemeExcelModel = rowDatas.get(i);
            boolean flag = this.verifyExcelParam(clbrSchemeExcelModel, clbrTypeSet, relationSet, groupNodeMap, log, i);
            if (flag) continue;
            invalidSet.add(i);
        }
        Set<Integer> repeatedSet = this.verifySelfOnly(rowDatas, log);
        for (int i = 0; i < rowDatas.size(); ++i) {
            if (repeatedSet.contains(i) || invalidSet.contains(i)) continue;
            ClbrSchemeExcelModel clbrSchemeExcelModel = rowDatas.get(i);
            ClbrSchemeDTO clbrSchemeDTO = new ClbrSchemeDTO();
            BeanUtils.copyProperties(clbrSchemeExcelModel, clbrSchemeDTO);
            String repeatScheme = this.verifyOnly(leafNode, clbrSchemeDTO);
            if (StringUtils.isNotBlank((CharSequence)repeatScheme)) {
                log.append("\u7b2c").append(i + 2).append("\u884c\u65b9\u6848\u4e0e[").append(repeatScheme).append("]\u65b9\u6848\u91cd\u590d\n");
                continue;
            }
            clbrSchemeDTO.setLeafFlag(Boolean.valueOf(true));
            clbrSchemeDTO.setParentId(groupNodeMap.get(clbrSchemeDTO.getParentId()).getId());
            clbrSchemeDTO.setFlowControlType(Objects.requireNonNull(ClbrFlowControlEnum.getEnumByTitle((String)clbrSchemeExcelModel.getFlowControlType())).getCode());
            clbrSchemeDTO.setVchrControlType(Objects.requireNonNull(ClbrVchrControlEnum.getEnumByTitle((String)clbrSchemeExcelModel.getVchrControlType())).getCode());
            ClbrSchemeEO clbrSchemeEO2 = this.initBeforeSave(clbrSchemeDTO);
            clbrSchemeEOList.add(clbrSchemeEO2);
        }
        if (rowDatas.size() == clbrSchemeEOList.size()) {
            ArrayList addList = new ArrayList();
            ArrayList update = new ArrayList();
            clbrSchemeEOList.forEach(clbrSchemeEO -> {
                if (schemeNodeMap.containsKey(clbrSchemeEO.getTitle())) {
                    clbrSchemeEO.setId(((ClbrSchemeEO)((Object)((Object)schemeNodeMap.get(clbrSchemeEO.getTitle())))).getId());
                    update.add(clbrSchemeEO);
                } else {
                    addList.add(clbrSchemeEO);
                }
            });
            this.clbrSchemeDao.addBatch(addList);
            this.clbrSchemeDao.updateBatch(update);
        }
        return log;
    }

    private boolean verifyExcelParam(ClbrSchemeExcelModel clbrSchemeExcelModel, Set<String> clbrTypeSet, Set<String> relationSet, Map<String, ClbrSchemeEO> groupNameMap, StringBuilder log, int rowNum) {
        String[] oppRelationArray;
        String[] relationArray;
        String[] oppClbrTypes;
        String[] clbrTypes;
        String clbrType = clbrSchemeExcelModel.getClbrTypes();
        String oppRelation = clbrSchemeExcelModel.getOppRelations();
        String relation = clbrSchemeExcelModel.getRelations();
        String oppClbrType = clbrSchemeExcelModel.getOppClbrTypes();
        String flowControlType = clbrSchemeExcelModel.getFlowControlType();
        String vchrControlType = clbrSchemeExcelModel.getVchrControlType();
        if (StringUtils.isBlank((CharSequence)clbrSchemeExcelModel.getTitle())) {
            log.append("\u7b2c").append(rowNum + 2).append("\u65b9\u6848\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)clbrSchemeExcelModel.getParentId())) {
            log.append("\u7b2c").append(rowNum + 2).append("\u7236\u7ea7\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)clbrType)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u53d1\u8d77\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)oppClbrType)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u63a5\u6536\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)relation)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u53d1\u8d77\u65b9\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)oppRelation)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u63a5\u6536\u65b9\u5355\u4f4d\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)flowControlType) || null == ClbrFlowControlEnum.getEnumByTitle((String)flowControlType)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u6d41\u7a0b\u63a7\u5236\u7c7b\u578b\u4e0d\u5b58\u5728\n");
            return false;
        }
        if (StringUtils.isBlank((CharSequence)vchrControlType) || null == ClbrVchrControlEnum.getEnumByTitle((String)vchrControlType)) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u51ed\u8bc1\u63a7\u5236\u7c7b\u578b\u4e0d\u5b58\u5728\n");
            return false;
        }
        if (groupNameMap.containsKey(clbrSchemeExcelModel.getTitle())) {
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u65b9\u6848\u540d\u79f0\u4e0e\u5206\u7ec4\u540d\u79f0\u91cd\u590d\n");
        }
        if (!groupNameMap.containsKey(clbrSchemeExcelModel.getParentId())) {
            log.append("\u7b2c").append(rowNum + 2).append("\u7236\u7ea7\u5206\u7ec4\u540d\u79f0\u4e0d\u5b58\u5728\n");
            return false;
        }
        for (String s : clbrTypes = clbrType.split(",")) {
            if (clbrTypeSet.contains(s)) continue;
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u53d1\u8d77\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801\u4e0d\u5b58\u5728\n");
            return false;
        }
        for (String s : oppClbrTypes = oppClbrType.split(",")) {
            if (clbrTypeSet.contains(s)) continue;
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u63a5\u6536\u65b9\u4e1a\u52a1\u7c7b\u578b\u4ee3\u7801\u4e0d\u5b58\u5728\n");
            return false;
        }
        for (String s : relationArray = relation.split(",")) {
            if (relationSet.contains(s)) continue;
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u53d1\u8d77\u65b9\u5355\u4f4d\u4ee3\u7801\u4e0d\u5b58\u5728\n");
            return false;
        }
        for (String s : oppRelationArray = oppRelation.split(",")) {
            if (relationSet.contains(s)) continue;
            log.append("\u7b2c").append(rowNum + 2).append("\u884c\u63a5\u6536\u65b9\u5355\u4f4d\u4ee3\u7801\u4e0d\u5b58\u5728\n");
            return false;
        }
        return true;
    }

    private Set<Integer> verifySelfOnly(List<ClbrSchemeExcelModel> rowDatas, StringBuilder log) {
        HashSet<Integer> repeatedSet = new HashSet<Integer>();
        for (int i = 0; i < rowDatas.size(); ++i) {
            ClbrSchemeExcelModel targetModel = rowDatas.get(i);
            ArrayList<String> targetClbrTypes = new ArrayList<String>(Arrays.asList(targetModel.getClbrTypes().split(",")));
            List<String> targetRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(targetModel.getRelations().split(","))));
            List<String> targetOppRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(targetModel.getOppRelations().split(","))));
            for (int j = rowDatas.size() - 1; j > i; --j) {
                ClbrSchemeExcelModel containerModel = rowDatas.get(j);
                if (targetModel.getTitle().equals(containerModel.getTitle())) {
                    log.append("\u7b2c").append(i + 2).append("\u884c\u65b9\u6848\u540d\u79f0\u4e0e").append("\u7b2c").append(j + 2).append("\u884c\u65b9\u6848\u540d\u79f0\u91cd\u590d,\u4e14\u5747\u672a\u5bfc\u5165\n");
                }
                ArrayList<String> containerClbrTypes = new ArrayList<String>(Arrays.asList(containerModel.getClbrTypes().split(",")));
                List<String> containerRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(containerModel.getRelations().split(","))));
                List<String> containerOppRelations = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(containerModel.getOppRelations().split(","))));
                containerClbrTypes.retainAll(targetClbrTypes);
                containerRelations.retainAll(targetRelations);
                containerOppRelations.retainAll(targetOppRelations);
                if (!CollectionUtils.isNotEmpty(containerClbrTypes) || !CollectionUtils.isNotEmpty(containerRelations) || !CollectionUtils.isNotEmpty(containerOppRelations)) continue;
                log.append("\u7b2c").append(i + 2).append("\u884c\u65b9\u6848\u4e0e").append("\u7b2c").append(j + 2).append("\u884c\u65b9\u6848\u5b58\u5728\u91cd\u590d\u5185\u5bb9,\u4e14\u5747\u672a\u5bfc\u5165\n");
                repeatedSet.add(i);
                repeatedSet.add(j);
            }
        }
        return repeatedSet;
    }

    @Override
    public List<ClbrSchemeExcelModel> clbrSchemeExport(ClbrSchemeCondition clbrSchemeCondition) {
        List<ClbrSchemeEO> clbrSchemeEOList = this.listByCondition(clbrSchemeCondition);
        List pageList = clbrSchemeEOList.stream().skip((long)(clbrSchemeCondition.getPageNum() - 1) * (long)clbrSchemeCondition.getPageSize().intValue()).limit(clbrSchemeCondition.getPageSize().intValue()).sorted(Comparator.comparing(ClbrSchemeEO::getParentId).thenComparing(ClbrSchemeEO::getCreateTime).reversed()).collect(Collectors.toList());
        List allClbrSchemeEOList = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        Map<String, ClbrSchemeEO> schemeName2IDMap = allClbrSchemeEOList.stream().collect(Collectors.toMap(DefaultTableEntity::getId, k -> k, (k1, k2) -> k2));
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Map<String, String> clbrTypeMap = clbrTypeBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Map<String, String> relationMap = relationBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        return pageList.stream().map(clbrSchemeEO -> {
            ClbrSchemeInfoEO clbrSchemeInfoEO = (ClbrSchemeInfoEO)JSONUtil.parseObject((String)clbrSchemeEO.getClbrInfo(), ClbrSchemeInfoEO.class);
            ClbrSchemeExcelModel clbrSchemeExcelModel = new ClbrSchemeExcelModel();
            BeanUtils.copyProperties(clbrSchemeInfoEO, clbrSchemeExcelModel);
            clbrSchemeExcelModel.setClbrTypesName(this.name5CodeConvert(clbrSchemeExcelModel.getClbrTypes().split(","), clbrTypeMap, null));
            clbrSchemeExcelModel.setOppClbrTypesName(this.name5CodeConvert(clbrSchemeExcelModel.getOppClbrTypes().split(","), clbrTypeMap, null));
            clbrSchemeExcelModel.setRelationsName(this.name5CodeConvert(clbrSchemeExcelModel.getRelations().split(","), relationMap, null));
            clbrSchemeExcelModel.setOppRelationsName(this.name5CodeConvert(clbrSchemeExcelModel.getOppRelations().split(","), relationMap, null));
            clbrSchemeExcelModel.setFlowControlType(Objects.requireNonNull(ClbrFlowControlEnum.getEnumByCode((String)clbrSchemeEO.getFlowControlType())).getTitle());
            clbrSchemeExcelModel.setVchrControlType(Objects.requireNonNull(ClbrVchrControlEnum.getEnumByCode((String)clbrSchemeEO.getVchrControlType())).getTitle());
            clbrSchemeExcelModel.setTitle(clbrSchemeEO.getTitle());
            clbrSchemeExcelModel.setParentId(((ClbrSchemeEO)((Object)((Object)schemeName2IDMap.get(clbrSchemeEO.getParentId())))).getTitle());
            return clbrSchemeExcelModel;
        }).collect(Collectors.toList());
    }

    @Override
    public ClbrSchemeDTO getFirstClbrScheme(String initiatorClbrType, String receiverClbrType, String initiatorRelation, String receiverRelation) {
        ClbrSchemeCondition schemeCondition = new ClbrSchemeCondition();
        schemeCondition.setClbrTypes(initiatorClbrType);
        schemeCondition.setRelations(initiatorRelation);
        schemeCondition.setOppClbrTypes(receiverClbrType);
        schemeCondition.setOppRelations(receiverRelation);
        schemeCondition.setPageNum(Integer.valueOf(1));
        schemeCondition.setPageSize(Integer.valueOf(1));
        List<ClbrSchemeDTO> clbrSchemeDTOS = this.listByConditionToDTO(schemeCondition);
        if (CollectionUtils.isEmpty(clbrSchemeDTOS)) {
            return null;
        }
        ClbrSchemeDTO clbrSchemeDTO = clbrSchemeDTOS.get(0);
        return clbrSchemeDTO;
    }

    @Override
    public List<ClbrSchemeTreeVO> listTree() {
        ClbrSchemeEO rootClbrSchemeEo = this.initRoot();
        ClbrSchemeTreeVO rootClbrSchemeTreeDTO = this.eoToTreeVoConvert(rootClbrSchemeEo);
        List clbrSchemeVOS = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        List clbrSchemeTreeDTOS = clbrSchemeVOS.stream().map(this::eoToTreeVoConvert).collect(Collectors.toList());
        Map<String, List<ClbrSchemeTreeVO>> parentId2DirectChildNode = clbrSchemeTreeDTOS.stream().collect(Collectors.groupingBy(ClbrSchemeTreeVO::getParentId));
        ClbrSchemeTreeVO wholeTree = this.loadChildTreeNode(rootClbrSchemeTreeDTO, parentId2DirectChildNode);
        ArrayList<ClbrSchemeTreeVO> clbrSchemeTreeDTOList = new ArrayList<ClbrSchemeTreeVO>();
        clbrSchemeTreeDTOList.add(wholeTree);
        return clbrSchemeTreeDTOList;
    }

    private ClbrSchemeEO initRoot() {
        List<ClbrSchemeEO> rootList = this.clbrSchemeDao.selectDirectChildSchemes(ClbrUtils.ROOT_PARENT_ID);
        if (CollectionUtils.isEmpty(rootList)) {
            ClbrSchemeEO rootClbrSchemeEo = new ClbrSchemeEO();
            rootClbrSchemeEo.setId(UUIDUtils.newUUIDStr());
            rootClbrSchemeEo.setLeafFlag(0);
            rootClbrSchemeEo.setParentId(ClbrUtils.ROOT_PARENT_ID);
            rootClbrSchemeEo.setTitle("\u6240\u6709\u534f\u540c\u65b9\u6848");
            rootClbrSchemeEo.setCreateTime(new Date());
            this.clbrSchemeDao.add((BaseEntity)rootClbrSchemeEo);
            return rootClbrSchemeEo;
        }
        return rootList.get(0);
    }

    private ClbrSchemeTreeVO loadChildTreeNode(ClbrSchemeTreeVO ClbrSchemeTreeDTO, Map<String, List<ClbrSchemeTreeVO>> parentId2DirectChildNode) {
        List<ClbrSchemeTreeVO> clbrSchemeTreeDTOS = parentId2DirectChildNode.get(ClbrSchemeTreeDTO.getId());
        if (CollectionUtils.isNotEmpty(clbrSchemeTreeDTOS)) {
            for (ClbrSchemeTreeVO clbrSchemeTreeDTO : clbrSchemeTreeDTOS) {
                if (clbrSchemeTreeDTO.getLeafFlag().booleanValue()) continue;
                ClbrSchemeTreeDTO.addChildren(this.loadChildTreeNode(clbrSchemeTreeDTO, parentId2DirectChildNode));
            }
        }
        return ClbrSchemeTreeDTO;
    }

    private ClbrSchemeTreeVO eoToTreeVoConvert(ClbrSchemeEO clbrSchemeEO) {
        ClbrSchemeTreeVO ClbrSchemeTreeDTO = new ClbrSchemeTreeVO();
        BeanUtils.copyProperties((Object)clbrSchemeEO, ClbrSchemeTreeDTO);
        if (Objects.equals(1, clbrSchemeEO.getLeafFlag())) {
            ClbrSchemeTreeDTO.setLeafFlag(Boolean.valueOf(true));
        } else {
            ClbrSchemeTreeDTO.setLeafFlag(Boolean.valueOf(false));
        }
        return ClbrSchemeTreeDTO;
    }

    private List<ClbrSchemeTreeVO> listAllLeafSchemes(String parentId) {
        List clbrSchemeVOS = this.clbrSchemeDao.selectList((BaseEntity)new ClbrSchemeEO());
        if (StringUtils.isBlank((CharSequence)parentId) || ClbrUtils.ROOT_PARENT_ID.equals(parentId)) {
            return clbrSchemeVOS.stream().filter(item -> Objects.equals(1, item.getLeafFlag())).map(this::eoToTreeVoConvert).collect(Collectors.toList());
        }
        ClbrSchemeEO param = new ClbrSchemeEO();
        param.setId(parentId);
        ClbrSchemeEO clbrSchemeEO = (ClbrSchemeEO)this.clbrSchemeDao.selectByEntity((BaseEntity)param);
        if (Objects.isNull((Object)clbrSchemeEO)) {
            return new ArrayList<ClbrSchemeTreeVO>();
        }
        if (Objects.equals(1, clbrSchemeEO.getLeafFlag())) {
            ArrayList<ClbrSchemeTreeVO> clbrSchemeTreeDTOS = new ArrayList<ClbrSchemeTreeVO>();
            clbrSchemeTreeDTOS.add(this.eoToTreeVoConvert(clbrSchemeEO));
            return clbrSchemeTreeDTOS;
        }
        List clbrSchemeTreeDTOS = clbrSchemeVOS.stream().map(this::eoToTreeVoConvert).collect(Collectors.toList());
        Map<String, List<ClbrSchemeTreeVO>> parentId2DirectChildNode = clbrSchemeTreeDTOS.stream().collect(Collectors.groupingBy(ClbrSchemeTreeVO::getParentId));
        ArrayList<ClbrSchemeTreeVO> schemeTreeDTOS = new ArrayList<ClbrSchemeTreeVO>();
        this.loadAllChildLeaf(schemeTreeDTOS, parentId, parentId2DirectChildNode);
        return schemeTreeDTOS;
    }

    private void loadAllChildLeaf(List<ClbrSchemeTreeVO> clbrSchemeTreeDTOS, String parentId, Map<String, List<ClbrSchemeTreeVO>> parentId2DirectChildNode) {
        List<ClbrSchemeTreeVO> clbrSchemeTreeDTOList = parentId2DirectChildNode.get(parentId);
        if (CollectionUtils.isEmpty(clbrSchemeTreeDTOList)) {
            return;
        }
        for (ClbrSchemeTreeVO clbrSchemeTreeDTO : clbrSchemeTreeDTOList) {
            if (Boolean.TRUE.equals(clbrSchemeTreeDTO.getLeafFlag())) {
                clbrSchemeTreeDTOS.add(clbrSchemeTreeDTO);
                continue;
            }
            this.loadAllChildLeaf(clbrSchemeTreeDTOS, clbrSchemeTreeDTO.getId(), parentId2DirectChildNode);
        }
    }
}

