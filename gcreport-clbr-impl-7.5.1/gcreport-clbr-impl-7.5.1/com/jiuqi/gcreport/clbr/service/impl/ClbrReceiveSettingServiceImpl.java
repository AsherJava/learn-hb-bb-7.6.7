/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.enums.AuthType
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService
 *  com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  org.apache.commons.collections4.CollectionUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.clbr.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.enums.AuthType;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.clbr.converter.ClbrReceiveSettingConverter;
import com.jiuqi.gcreport.clbr.dao.ClbrReceiveSettingDao;
import com.jiuqi.gcreport.clbr.dto.ClbrReceiveSettingDTO;
import com.jiuqi.gcreport.clbr.entity.ClbrReceiveSettingEO;
import com.jiuqi.gcreport.clbr.executor.model.ClbrReceiveSettingExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import com.jiuqi.gcreport.clbr.util.RelationUtils;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ClbrReceiveSettingServiceImpl
implements ClbrReceiveSettingService {
    private static final Logger log = LoggerFactory.getLogger(ClbrReceiveSettingServiceImpl.class);
    @Autowired
    private ClbrReceiveSettingDao clbrReceiveSettingDao;
    @Autowired
    private GcBaseDataService gcBaseDataService;
    @Autowired
    private UserService<User> userService;

    @Override
    public List<ClbrReceiveSettingDTO> listByUserOrRole(String userName, String roleCode) {
        String[] usernames = null;
        String[] roleCodes = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)userName)) {
            usernames = userName.split(",");
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)roleCode)) {
            roleCodes = roleCode.split(",");
        }
        ArrayList<ClbrReceiveSettingEO> filterEos = new ArrayList<ClbrReceiveSettingEO>();
        List eos = this.clbrReceiveSettingDao.selectList((BaseEntity)new ClbrReceiveSettingEO());
        for (ClbrReceiveSettingEO clbrReceiveSettingEO : eos) {
            String name = clbrReceiveSettingEO.getUserNames();
            String role = clbrReceiveSettingEO.getRoleCodes();
            String[] names = null;
            String[] roles = null;
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)name)) {
                names = name.split(",");
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)role)) {
                roles = role.split(",");
            }
            if (Objects.nonNull(names) && Objects.nonNull(usernames)) {
                ArrayList<String> nameList = new ArrayList<String>(Arrays.asList(names));
                nameList.retainAll(new ArrayList<String>(Arrays.asList(usernames)));
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(nameList)) {
                    filterEos.add(clbrReceiveSettingEO);
                    continue;
                }
            }
            if (!Objects.nonNull(roles) || !Objects.nonNull(roleCodes)) continue;
            ArrayList<String> roleList = new ArrayList<String>(Arrays.asList(roles));
            roleList.retainAll(new ArrayList<String>(Arrays.asList(roleCodes)));
            if (!org.apache.commons.collections4.CollectionUtils.isNotEmpty(roleList)) continue;
            filterEos.add(clbrReceiveSettingEO);
        }
        return ClbrReceiveSettingConverter.convertEO2DTO(filterEos);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean save(ClbrReceiveSettingVO clbrReceiveSettingVO) {
        ClbrReceiveSettingDTO clbrReceiveSettingDTO = new ClbrReceiveSettingDTO();
        BeanUtils.copyProperties(clbrReceiveSettingVO, clbrReceiveSettingDTO);
        this.verifyParam(clbrReceiveSettingDTO);
        String oppRelation = clbrReceiveSettingDTO.getOppRelation();
        List<String> unitCodeOnlyParent = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(oppRelation.split(","))));
        ClbrReceiveSettingEO clbrReceiveSettingEO = new ClbrReceiveSettingEO();
        BeanUtils.copyProperties(clbrReceiveSettingVO, (Object)clbrReceiveSettingEO);
        clbrReceiveSettingEO.setOppRelation(String.join((CharSequence)",", unitCodeOnlyParent));
        clbrReceiveSettingEO.setId(UUIDUtils.newUUIDStr());
        this.clbrReceiveSettingDao.add((BaseEntity)clbrReceiveSettingEO);
        return true;
    }

    private void verifyParam(ClbrReceiveSettingDTO clbrReceiveSettingDTO) {
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)clbrReceiveSettingDTO.getOppClbrTypes())) {
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)clbrReceiveSettingDTO.getOppRelation())) {
            throw new BusinessRuntimeException("\u63a5\u6536\u65b9\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)clbrReceiveSettingDTO.getRoleCodes()) && org.apache.commons.lang3.StringUtils.isBlank((CharSequence)clbrReceiveSettingDTO.getUserNames())) {
            throw new BusinessRuntimeException("\u89d2\u8272\u548c\u7528\u6237\u4e0d\u80fd\u540c\u65f6\u4e3a\u7a7a");
        }
    }

    @Override
    public PageInfo<ClbrReceiveSettingVO> query(ClbrReceiveSettingCondition clbrReceiveSettingCondition) {
        Integer pageNum = clbrReceiveSettingCondition.getPageNum();
        Integer pageSize = clbrReceiveSettingCondition.getPageSize();
        if (Objects.isNull(pageNum) || Objects.isNull(pageSize)) {
            throw new BusinessRuntimeException("\u5206\u9875\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
        int totalCount = this.clbrReceiveSettingDao.countByEntity((BaseEntity)new ClbrReceiveSettingEO());
        if (totalCount <= 0) {
            return PageInfo.empty();
        }
        List<ClbrReceiveSettingEO> clbrReceiveSettingEOS = this.clbrReceiveSettingDao.selectOrderListByPaging((pageNum - 1) * pageSize, pageNum * pageSize);
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Map<String, String> clbrTypeMap = clbrTypeBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Map<String, String> relationMap = relationBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List clbrReceiveSettingVOS = clbrReceiveSettingEOS.stream().map(clbrReceiveSettingEO -> {
            String[] oppRelations;
            String[] oppClbrTypes = clbrReceiveSettingEO.getOppClbrTypes().split(",");
            ClbrReceiveSettingVO clbrReceiveSettingVO = new ClbrReceiveSettingVO();
            Map oppRelationMap = clbrReceiveSettingVO.getOppRelationMap();
            for (String oppRelationCode : oppRelations = clbrReceiveSettingEO.getOppRelation().split(",")) {
                oppRelationMap.put(oppRelationCode, relationMap.get(oppRelationCode));
            }
            BeanUtils.copyProperties(clbrReceiveSettingEO, clbrReceiveSettingVO);
            StringBuilder oppRelationBuilder = new StringBuilder();
            oppRelationMap.forEach((key, value) -> oppRelationBuilder.append((String)key).append("|").append((String)value).append(","));
            String oppRelation = oppRelationBuilder.substring(0, oppRelationBuilder.length() > 0 ? oppRelationBuilder.length() - 1 : 0);
            clbrReceiveSettingVO.setOppRelation(oppRelation);
            clbrReceiveSettingVO.setOppClbrTypes(this.name5CodeConvert(oppClbrTypes, clbrTypeMap, clbrReceiveSettingVO.getOppClbrTypesMap()));
            String userNames = clbrReceiveSettingEO.getUserNames();
            Map<String, String> usersMap = new HashMap<String, String>();
            if (org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)userNames)) {
                List users = this.userService.getByUsername(userNames.split(","));
                usersMap = users.stream().collect(Collectors.toMap(User::getName, User::getFullname, (k1, k2) -> k2));
            }
            StringBuilder builder = new StringBuilder();
            usersMap.forEach((key, value) -> builder.append((String)key).append("|").append((String)value).append(","));
            String names = builder.substring(0, builder.length() > 0 ? builder.length() - 1 : 0);
            clbrReceiveSettingVO.setUserNames(names);
            clbrReceiveSettingVO.setUsersMap(usersMap);
            return clbrReceiveSettingVO;
        }).collect(Collectors.toList());
        return PageInfo.of(clbrReceiveSettingVOS, (int)pageNum, (int)pageSize, (int)totalCount);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(List<String> settingIds) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(settingIds)) {
            return;
        }
        this.clbrReceiveSettingDao.deleteByIds(settingIds);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean edit(ClbrReceiveSettingVO clbrReceiveSettingVO) {
        if (org.apache.commons.lang3.StringUtils.isBlank((CharSequence)clbrReceiveSettingVO.getId())) {
            log.warn("\u6d88\u606f\u65b9\u6848Id\u4e3a\u7a7a,\u8bf7\u6c42\u53c2\u6570={}", (Object)clbrReceiveSettingVO);
            throw new BusinessRuntimeException("\u7f16\u8f91\u6d88\u606f\u914d\u7f6e\u5f02\u5e38");
        }
        ClbrReceiveSettingDTO clbrReceiveSettingDTO = new ClbrReceiveSettingDTO();
        BeanUtils.copyProperties(clbrReceiveSettingVO, clbrReceiveSettingDTO);
        this.verifyParam(clbrReceiveSettingDTO);
        ClbrReceiveSettingEO clbrReceiveSettingEO = new ClbrReceiveSettingEO();
        BeanUtils.copyProperties(clbrReceiveSettingVO, (Object)clbrReceiveSettingEO);
        String oppRelation = clbrReceiveSettingVO.getOppRelation();
        List<String> unitCodeOnlyParent = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(oppRelation.split(","))));
        clbrReceiveSettingEO.setOppRelation(String.join((CharSequence)",", unitCodeOnlyParent));
        this.clbrReceiveSettingDao.updateSelective((BaseEntity)clbrReceiveSettingEO);
        return true;
    }

    @Override
    public StringBuilder settingImport(List<ClbrReceiveSettingExcelModel> rowDatas) {
        StringBuilder log = new StringBuilder();
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Set clbrTypeSet = clbrTypeBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Set relationSet = relationBaseData.stream().map(GcBaseData::getObjectCode).collect(Collectors.toSet());
        ArrayList<ClbrReceiveSettingEO> clbrReceiveSettingEOS = new ArrayList<ClbrReceiveSettingEO>();
        block2: for (int i = 0; i < rowDatas.size(); ++i) {
            String[] oppRelations;
            String[] oppClbrTypes;
            ClbrReceiveSettingExcelModel clbrReceiveSettingExcelModel = rowDatas.get(i);
            ClbrReceiveSettingDTO clbrReceiveSettingDTO = new ClbrReceiveSettingDTO();
            BeanUtils.copyProperties(clbrReceiveSettingExcelModel, clbrReceiveSettingDTO);
            try {
                this.verifyParam(clbrReceiveSettingDTO);
            }
            catch (BusinessRuntimeException e) {
                log.append("\u7b2c").append(i + 2).append("\u884c\u914d\u7f6e").append(e.getMessage()).append("\n");
                continue;
            }
            for (String s : oppClbrTypes = clbrReceiveSettingDTO.getOppClbrTypes().split(",")) {
                if (clbrTypeSet.contains(s)) continue;
                log.append("\u7b2c").append(i + 2).append("\u884c\u63a5\u6536\u65b9\u4e1a\u52a1\u7c7b\u578b\u4e0d\u5b58\u5728\n");
                continue block2;
            }
            for (String relation : oppRelations = clbrReceiveSettingDTO.getOppRelation().split(",")) {
                if (relationSet.contains(relation)) continue;
                log.append("\u7b2c").append(i + 2).append("\u884c\u63a5\u6536\u65b9\u4e0d\u5b58\u5728\n");
                continue block2;
            }
            ClbrReceiveSettingEO clbrReceiveSettingEO = new ClbrReceiveSettingEO();
            BeanUtils.copyProperties(clbrReceiveSettingDTO, (Object)clbrReceiveSettingEO);
            List<String> unitCodeOnlyParent = RelationUtils.getUnitCodeOnlyParent(new ArrayList<String>(Arrays.asList(oppRelations)));
            clbrReceiveSettingEO.setOppRelation(String.join((CharSequence)",", unitCodeOnlyParent));
            clbrReceiveSettingEO.setId(UUIDUtils.newUUIDStr());
            clbrReceiveSettingEOS.add(clbrReceiveSettingEO);
        }
        if (rowDatas.size() == clbrReceiveSettingEOS.size()) {
            this.clbrReceiveSettingDao.addBatch(clbrReceiveSettingEOS);
        }
        return log;
    }

    @Override
    public List<ClbrReceiveSettingExcelModel> settingExport() {
        List clbrReceiveSettingEOS = this.clbrReceiveSettingDao.selectList((BaseEntity)new ClbrReceiveSettingEO());
        List clbrTypeBaseData = this.gcBaseDataService.queryBasedataItems("MD_CLBRTYPE", AuthType.NONE);
        Map<String, String> clbrTypeMap = clbrTypeBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        List relationBaseData = this.gcBaseDataService.queryBasedataItems("MD_RELATION", AuthType.NONE);
        Map<String, String> relationMap = relationBaseData.stream().collect(Collectors.toMap(GcBaseData::getObjectCode, gcBaseData -> gcBaseData.getFieldVal("NAME").toString(), (k1, k2) -> k2));
        return clbrReceiveSettingEOS.stream().map(clbrReceiveSettingEO -> {
            ClbrReceiveSettingExcelModel clbrReceiveSettingExcelModel = new ClbrReceiveSettingExcelModel();
            BeanUtils.copyProperties(clbrReceiveSettingEO, clbrReceiveSettingExcelModel);
            clbrReceiveSettingExcelModel.setOppClbrTypesName(this.name5CodeConvert(clbrReceiveSettingExcelModel.getOppClbrTypes().split(","), clbrTypeMap, null));
            clbrReceiveSettingExcelModel.setOppRelationName(this.name5CodeConvert(clbrReceiveSettingExcelModel.getOppRelation().split(","), relationMap, null));
            return clbrReceiveSettingExcelModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ClbrReceiveSettingVO> queryForUser(ClbrReceiveSettingCondition clbrReceiveSettingCondition) {
        ArrayList<ClbrReceiveSettingVO> clbrReceiveSettingVOS = new ArrayList<ClbrReceiveSettingVO>();
        List clbrReceiveSettingEOS = this.clbrReceiveSettingDao.selectList((BaseEntity)new ClbrReceiveSettingEO());
        for (ClbrReceiveSettingEO clbrReceiveSettingEO : clbrReceiveSettingEOS) {
            String[] oppClbeTypes = clbrReceiveSettingEO.getOppClbrTypes().split(",");
            String[] oppRelations = clbrReceiveSettingEO.getOppRelation().split(",");
            List<String> subOppRelationList = RelationUtils.queryAllItems(new ArrayList<String>(Arrays.asList(oppRelations)));
            if (!Arrays.asList(oppClbeTypes).contains(clbrReceiveSettingCondition.getOppClbrType()) || !subOppRelationList.contains(clbrReceiveSettingCondition.getOppRelation())) continue;
            ClbrReceiveSettingVO clbrReceiveSettingVO = new ClbrReceiveSettingVO();
            BeanUtils.copyProperties((Object)clbrReceiveSettingEO, clbrReceiveSettingVO);
            clbrReceiveSettingVOS.add(clbrReceiveSettingVO);
        }
        return clbrReceiveSettingVOS;
    }

    @Override
    public Map<String, Set<String>> getReceiveClbrType2Relations(String userName, String roleCode) {
        List<ClbrReceiveSettingDTO> clbrReceiveSettingDTOs = this.listByUserOrRole(userName, roleCode);
        HashMap<String, Set<String>> receiveClbrType2Relations = new HashMap<String, Set<String>>();
        clbrReceiveSettingDTOs.stream().forEach(clbrReceiveSettingDTO -> {
            List<String> oppRelation = Arrays.asList(StringUtils.tokenizeToStringArray(clbrReceiveSettingDTO.getOppRelation(), ","));
            List<String> oppClbrTypes = Arrays.asList(StringUtils.tokenizeToStringArray(clbrReceiveSettingDTO.getOppClbrTypes(), ","));
            oppClbrTypes.stream().forEach(oppClbrType -> {
                if (ObjectUtils.isEmpty(oppClbrType)) {
                    return;
                }
                if (receiveClbrType2Relations.get(oppClbrType) == null) {
                    receiveClbrType2Relations.put((String)oppClbrType, new HashSet());
                }
                ((Set)receiveClbrType2Relations.get(oppClbrType)).addAll(RelationUtils.queryAllItems(oppRelation));
            });
        });
        return receiveClbrType2Relations;
    }

    @Override
    public boolean exist() {
        List clbrReceiveSettingEOS = this.clbrReceiveSettingDao.selectList((BaseEntity)new ClbrReceiveSettingEO());
        return !CollectionUtils.isEmpty((Collection)clbrReceiveSettingEOS);
    }

    private String name5CodeConvert(String[] target, Map<String, String> mapping, Map<String, String> oppRelationMap) {
        StringBuilder builder = new StringBuilder();
        for (String str : target) {
            String typeName = mapping.get(str);
            if (!org.apache.commons.lang3.StringUtils.isNotBlank((CharSequence)typeName)) continue;
            builder.append(typeName).append(",");
            if (!Objects.nonNull(oppRelationMap)) continue;
            oppRelationMap.put(str, typeName);
        }
        return builder.substring(0, builder.length() > 0 ? builder.length() - 1 : 0);
    }
}

