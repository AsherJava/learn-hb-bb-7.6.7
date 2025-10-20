/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.biz.ModelDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.domain.meta.OperateType
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 */
package com.jiuqi.va.bizmeta.service.impl;

import com.jiuqi.va.bizmeta.common.consts.MetaState;
import com.jiuqi.va.bizmeta.common.consts.MetaTypeEnum;
import com.jiuqi.va.bizmeta.common.utils.MetaUtils;
import com.jiuqi.va.bizmeta.dao.IMetaDataAuthDao;
import com.jiuqi.va.bizmeta.domain.dimension.MetaGroupDim;
import com.jiuqi.va.bizmeta.domain.metaauth.AuthMetaVO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDTO;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthDim;
import com.jiuqi.va.bizmeta.domain.metaauth.MetaAuthVO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupDTO;
import com.jiuqi.va.bizmeta.domain.metagroup.MetaGroupVO;
import com.jiuqi.va.bizmeta.domain.metainfo.MetaInfoPageDTO;
import com.jiuqi.va.bizmeta.domain.metamodel.MetaModelDTO;
import com.jiuqi.va.bizmeta.service.IMetaAuthService;
import com.jiuqi.va.bizmeta.service.IMetaBaseInfoService;
import com.jiuqi.va.bizmeta.service.IMetaGroupService;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.bizmeta.service.IMetaOptionService;
import com.jiuqi.va.domain.biz.ModelDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.MetaInfoDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.domain.meta.OperateType;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MetaAuthServiceImpl
implements IMetaAuthService {
    @Autowired
    private IMetaInfoService metaInfoService;
    @Autowired
    private IMetaBaseInfoService metaBaseInfoService;
    @Autowired
    private IMetaGroupService groupService;
    @Autowired
    private IMetaDataAuthDao metaDataAuthDao;
    @Autowired
    private AuthRoleClient authRoleClient;
    @Autowired
    private IMetaOptionService metaOptionService;

    @Override
    public List<AuthMetaVO> listDetail(MetaAuthDTO infoPageDTO) {
        ArrayList<AuthMetaVO> AuthMetaList = new ArrayList<AuthMetaVO>();
        List<MetaAuthDO> metaAuthVOS = this.metaDataAuthDao.queryMetaAuth(infoPageDTO);
        for (MetaAuthDO authDo : metaAuthVOS) {
            AuthMetaVO authVO = new AuthMetaVO();
            authVO.setAuthtype(authDo.getAuthtype());
            authVO.setBizname(authDo.getBizname());
            authVO.setGroupflag(authDo.getGroupflag());
            authVO.setBiztype(authDo.getBiztype());
            authVO.setMetaType(authDo.getMetaType());
            authVO.setAuthflag(authDo.getAuthflag());
            authVO.setId(authDo.getId());
            authVO.setUniqueCode(authDo.getUniqueCode());
            authVO.setAtauthorize(authDo.getAtauthorize());
            AuthMetaList.add(authVO);
        }
        return AuthMetaList;
    }

    @Override
    public R updateDetail(List<MetaAuthDTO> datas) {
        for (MetaAuthDTO metaAuth : datas) {
            Integer authflag = metaAuth.getAuthflag();
            Integer atauthorize = metaAuth.getAtauthorize();
            metaAuth.setAuthflag(null);
            MetaAuthDO metaAuthDO = this.metaDataAuthDao.getMetaAuth(metaAuth);
            if (metaAuthDO != null) {
                if (authflag == null) {
                    authflag = metaAuthDO.getAuthflag();
                }
                if (atauthorize == null) {
                    atauthorize = metaAuthDO.getAtauthorize();
                }
                if (authflag.equals(metaAuthDO.getAuthflag()) && atauthorize.equals(metaAuthDO.getAtauthorize())) continue;
                if (!(authflag != 0 && authflag != 2 || atauthorize != 0 && atauthorize != 2)) {
                    this.metaDataAuthDao.delete((Object)metaAuthDO);
                    continue;
                }
                metaAuthDO.setAuthflag(authflag);
                metaAuthDO.setAtauthorize(atauthorize);
                this.metaDataAuthDao.updateByPrimaryKeySelective((Object)metaAuthDO);
                continue;
            }
            if (authflag != 1 && atauthorize != 1) continue;
            metaAuth.setId(UUID.randomUUID());
            metaAuth.setAuthflag(authflag);
            metaAuth.setAtauthorize(atauthorize);
            this.metaDataAuthDao.insert((Object)metaAuth);
        }
        return R.ok();
    }

    @Override
    public MetaAuthVO getUserAuth(MetaInfoPageDTO infoPageDTO) {
        MetaAuthVO authVO = new MetaAuthVO();
        List<MetaGroupDTO> groupList = this.groupService.getChildGroupList(infoPageDTO.getModule(), infoPageDTO.getMetaType(), infoPageDTO.getGroupId(), OperateType.EXECUTE);
        ArrayList<MetaAuthDim> dimList = new ArrayList<MetaAuthDim>(16);
        authVO.setMetaAuthDims(dimList);
        List<Object> infoDTOs = new ArrayList(16);
        infoPageDTO.setOperateType(OperateType.EXECUTE);
        List<ModelDTO> modelDTOs = this.metaBaseInfoService.gatherModelsAll();
        HashMap<String, String> groupParent = new HashMap<String, String>();
        MetaAuthDTO metaAuthParam = new MetaAuthDTO();
        metaAuthParam.setBiztype((Integer)infoPageDTO.getExtInfo("biztype"));
        metaAuthParam.setBizname((String)infoPageDTO.getExtInfo("bizname"));
        List<MetaAuthDO> metaAuthDOS = this.metaDataAuthDao.queryMetaAuth(metaAuthParam);
        HashMap<String, String> metaAuthMap = new HashMap<String, String>();
        for (MetaAuthDO metaAuthDO : metaAuthDOS) {
            if (metaAuthDO.getGroupflag() != 1) continue;
            StringBuilder builder = new StringBuilder();
            int authFlag = metaAuthDO.getAuthflag() == 1 ? 1 : 0;
            int authorize = metaAuthDO.getAtauthorize() == 1 ? 1 : 0;
            builder.append(authFlag).append("#").append(authorize);
            metaAuthMap.put(metaAuthDO.getUniqueCode(), builder.toString());
        }
        UserLoginDTO user = ShiroUtil.getUser();
        List roleDOS = this.authRoleClient.listByUser((UserDO)user);
        boolean accessFlag = false;
        for (RoleDO role : roleDOS) {
            if (!role.getName().equals("SYSTEM") && !role.getName().equals("BUSINESS_MANAGER")) continue;
            accessFlag = true;
        }
        MetaAuthDTO metaAuthDTO = new MetaAuthDTO();
        metaAuthDTO.setMetaType(MetaTypeEnum.WORKFLOW.getName());
        metaAuthDTO.setBizname(user.getUsername());
        R r = this.get(metaAuthDTO);
        HashMap data = (HashMap)r.get((Object)"data");
        ArrayList groupData = (ArrayList)data.get("group");
        ArrayList defineData = (ArrayList)data.get("define");
        HashMap<String, String> groupMap = new HashMap<String, String>();
        for (MetaGroupDTO group : groupList) {
            this.addRelationshipToMap(group, groupMap);
            if (accessFlag) {
                MetaAuthDim metaAuthDim = this.copyGroupToAuthDim(group);
                metaAuthDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, group.getMetaType()));
                if (!dimList.isEmpty()) {
                    groupParent.put("parentName", group.getParentName());
                } else {
                    groupParent.put("groupName", group.getName());
                }
                dimList.add(metaAuthDim);
                continue;
            }
            for (Object meta : groupData) {
                if (meta.getAtauthorize() != 1 || !meta.getUniqueCode().equals(group.getUniqueCode())) continue;
                MetaAuthDim metaAuthDim = this.copyGroupToAuthDim(group);
                metaAuthDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, group.getMetaType()));
                if (!dimList.isEmpty()) {
                    groupParent.put("parentName", group.getParentName());
                } else {
                    groupParent.put("groupName", group.getName());
                }
                dimList.add(metaAuthDim);
            }
        }
        ArrayList<String> parentList = new ArrayList<String>();
        int parentAuth = 0;
        int parentAuthorize = 0;
        if (dimList.size() > 0) {
            this.getGroupParent(groupMap, ((MetaAuthDim)dimList.get(0)).getUniqueCode(), parentList);
            for (String parent : parentList) {
                if (!metaAuthMap.containsKey(parent)) continue;
                String[] split = ((String)metaAuthMap.get(parent)).split("#");
                if ("1".equals(split[0])) {
                    parentAuth = 1;
                }
                if (!"1".equals(split[1])) continue;
                parentAuthorize = 1;
            }
        }
        authVO.setInheritType(parentAuth + "#" + parentAuthorize);
        if (!StringUtils.hasText(infoPageDTO.getModule())) {
            throw new RuntimeException("\u6a21\u5757\u540d\u4e3a\u7a7a");
        }
        boolean isPagination = infoPageDTO.isPagination();
        if (isPagination) {
            infoPageDTO.setPagination(false);
        }
        ArrayList<String> groupNames = new ArrayList<String>(8);
        if (groupParent.containsKey("parentName")) {
            groupNames.add((String)groupParent.get("parentName"));
        } else {
            groupNames.add((String)groupParent.get("groupName"));
        }
        infoPageDTO.setGroupNames(groupNames);
        infoPageDTO.setGroupId(null);
        infoDTOs = this.metaInfoService.getMetaList(infoPageDTO);
        for (MetaInfoDTO metaInfoDTO : infoDTOs) {
            if (accessFlag) {
                MetaAuthDim metaAuthDim = this.copyMetaInfoToDim(metaInfoDTO);
                metaAuthDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, metaInfoDTO.getModelName()));
                dimList.add(metaAuthDim);
                continue;
            }
            for (MetaAuthDO meta : defineData) {
                if (meta.getAtauthorize() != 1 || !meta.getUniqueCode().equals(metaInfoDTO.getUniqueCode())) continue;
                MetaAuthDim metaAuthDim = this.copyMetaInfoToDim(metaInfoDTO);
                metaAuthDim.setModelTitle(MetaUtils.getModelTitle(modelDTOs, metaInfoDTO.getModelName()));
                dimList.add(metaAuthDim);
            }
        }
        authVO.setMetaAuthDims(dimList);
        for (MetaAuthDim metaAuthDim : dimList) {
            for (MetaAuthDO auth : metaAuthDOS) {
                if (!metaAuthDim.getUniqueCode().equals(auth.getUniqueCode())) continue;
                if (metaAuthDim.getGroup().booleanValue() && auth.getGroupflag() == 1) {
                    metaAuthDim.setAuthflag(auth.getAuthflag());
                    metaAuthDim.setAtauthorize(auth.getAtauthorize());
                    break;
                }
                if (auth.getGroupflag() != 0 || metaAuthDim.getGroup().booleanValue()) continue;
                metaAuthDim.setAuthflag(auth.getAuthflag());
                metaAuthDim.setAtauthorize(auth.getAtauthorize());
                break;
            }
            if (metaAuthDim.getAtauthorize() == null) {
                metaAuthDim.setAtauthorize(0);
            }
            if (metaAuthDim.getAuthflag() != null) continue;
            metaAuthDim.setAuthflag(0);
        }
        return authVO;
    }

    private MetaAuthDim copyMetaInfoToDim(MetaInfoDTO info) {
        MetaAuthDim metaAuthDim = new MetaAuthDim();
        metaAuthDim.setGroupName(info.getGroupName());
        metaAuthDim.setId(info.getId());
        metaAuthDim.setMetaType(info.getMetaType());
        metaAuthDim.setModelName(info.getModuleName());
        metaAuthDim.setModuleName(info.getModuleName());
        metaAuthDim.setName(info.getName());
        metaAuthDim.setTitle(info.getTitle());
        metaAuthDim.setGroup(false);
        metaAuthDim.setUniqueCode(info.getUniqueCode());
        return metaAuthDim;
    }

    private MetaAuthDim copyGroupToAuthDim(MetaGroupDTO group) {
        MetaAuthDim metaAuthDim = new MetaAuthDim();
        metaAuthDim.setGroupName(group.getName());
        metaAuthDim.setName(group.getName());
        metaAuthDim.setId(group.getId());
        metaAuthDim.setMetaType(group.getMetaType());
        metaAuthDim.setModuleName(group.getModuleName());
        metaAuthDim.setGroup(true);
        metaAuthDim.setUniqueCode(group.getUniqueCode());
        metaAuthDim.setTitle(group.getTitle());
        return metaAuthDim;
    }

    private void getGroupParent(HashMap<String, String> groupMap, String uniqueCode, ArrayList<String> groupList) {
        if ("-".equals(uniqueCode) || !groupMap.containsKey(uniqueCode)) {
            return;
        }
        String parent = groupMap.get(uniqueCode);
        if (!"-".equals(parent)) {
            groupList.add(parent);
        }
        this.getGroupParent(groupMap, parent, groupList);
    }

    @Override
    public R get(MetaAuthDTO metaAuthDTO) {
        List<MetaAuthDO> select = this.metaDataAuthDao.queryMetaAuth(metaAuthDTO);
        HashMap data = new HashMap();
        ArrayList<MetaAuthDO> groupList = new ArrayList<MetaAuthDO>(16);
        ArrayList<MetaAuthDO> defineList = new ArrayList<MetaAuthDO>(16);
        for (MetaAuthDO meta : select) {
            if (1 == meta.getGroupflag()) {
                groupList.add(meta);
                continue;
            }
            if (0 != meta.getGroupflag()) continue;
            defineList.add(meta);
        }
        data.put("group", groupList);
        data.put("define", defineList);
        R r = R.ok();
        r.put("data", data);
        return r;
    }

    @Override
    public Set<String> checkUserAuth(MetaAuthDTO param) {
        UserLoginDTO user = ShiroUtil.getUser();
        StringBuilder sb = new StringBuilder();
        sb.append(" ( (biztype=0 and bizname in ('-'");
        List roles = this.authRoleClient.listByUser((UserDO)user);
        if (roles != null && roles.size() > 0) {
            for (RoleDO roleDO : roles) {
                sb.append(",'").append(roleDO.getName()).append("'");
            }
        }
        sb.append(")) or (biztype=1 and bizname='").append(user.getUsername()).append("') ) ");
        param.setAuthtype(1);
        param.setSqlCondition(sb.toString());
        List<MetaAuthDO> auths = this.metaDataAuthDao.listAuthority(param);
        Set<String> authNames = auths.stream().map(MetaAuthDO::getUniqueCode).collect(Collectors.toSet());
        List<MetaGroupDTO> groupDTOs = this.groupService.getGroupList(null, param.getMetaType(), OperateType.DESIGN);
        Set<String> allGroupAuth = this.getAllGroupAuth(groupDTOs, param.getMetaType());
        List<MetaInfoDO> workflow = this.metaInfoService.getMetaListByMetaType(param.getMetaType());
        for (MetaInfoDO workflowDO : workflow) {
            String uniqueCode = workflowDO.getUniqueCode();
            if (!allGroupAuth.contains(uniqueCode)) continue;
            authNames.add(uniqueCode);
        }
        return authNames;
    }

    @Override
    public Set<String> getAllGroupAuth(List<MetaGroupDTO> groupDTOs, String metaType) {
        OptionItemVO optionItemVO;
        HashSet<String> groupSet = new HashSet<String>(8);
        HashMap<String, String> groupRelationship = new HashMap<String, String>();
        for (MetaGroupDTO groupDto : groupDTOs) {
            this.addRelationshipToMap(groupDto, groupRelationship);
            groupSet.add(groupDto.getUniqueCode());
        }
        UserLoginDTO user = ShiroUtil.getUser();
        OptionItemDTO optionParam = new OptionItemDTO();
        optionParam.setName("META001");
        List<OptionItemVO> optionItemList = this.metaOptionService.list(optionParam);
        if (user.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = optionItemList.get(0)).getVal().equals("1") && (MetaTypeEnum.WORKFLOW.getName().equals(metaType) || metaType == null)) {
            MetaAuthDTO metaAuthCheck = new MetaAuthDTO();
            metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
            metaAuthCheck.setGroupflag(1);
            metaAuthCheck.setMetaType(MetaTypeEnum.WORKFLOW.getName());
            Set<String> userAuth = this.queryUserAuth(metaAuthCheck);
            groupSet.clear();
            for (String name : userAuth) {
                groupSet.add(name);
                this.groupService.addChildrenToSet(name, groupRelationship, groupSet);
            }
            return groupSet;
        }
        return groupSet;
    }

    @Override
    public MetaGroupVO getAllGroup(MetaModelDTO infoDTO) {
        if ("all".equals(infoDTO.getMetaType())) {
            infoDTO.setMetaType(null);
        }
        List<MetaGroupDTO> groupDTOs = this.groupService.getGroupList(infoDTO.getModuleName(), infoDTO.getMetaType(), infoDTO.getOperateType());
        ArrayList<MetaGroupDim> groupDims = new ArrayList<MetaGroupDim>();
        for (MetaGroupDTO metaGroupDTO : groupDTOs) {
            MetaGroupDim groupDim = new MetaGroupDim();
            groupDim.setId(metaGroupDTO.getId());
            groupDim.setMetaType(metaGroupDTO.getMetaType());
            groupDim.setModuleName(metaGroupDTO.getModuleName());
            groupDim.setName(metaGroupDTO.getName());
            groupDim.setParentName(metaGroupDTO.getParentName());
            groupDim.setTitle(metaGroupDTO.getTitle());
            groupDim.setVersion(metaGroupDTO.getVersionNo());
            groupDim.setRowVersion(metaGroupDTO.getRowVersion());
            groupDim.setState(metaGroupDTO.getState());
            groupDim.setUniqueCode(metaGroupDTO.getUniqueCode());
            groupDims.add(groupDim);
        }
        MetaGroupVO groupVO = new MetaGroupVO();
        groupVO.setGroups(groupDims);
        return groupVO;
    }

    @Override
    public <T> void addRelationshipToMap(T groupT, HashMap<String, String> groupRelationship) {
        MetaGroupDTO group = new MetaGroupDTO();
        if (groupT instanceof MetaGroupDTO) {
            group = (MetaGroupDTO)((Object)groupT);
        } else if (groupT instanceof MetaGroupDO) {
            MetaGroupDO groupDo = (MetaGroupDO)((Object)groupT);
            group.setMetaType(groupDo.getMetaType());
            group.setModuleName(groupDo.getModuleName());
            group.setParentName(groupDo.getParentName());
            group.setUniqueCode(groupDo.getUniqueCode());
        }
        if (StringUtils.hasText(group.getParentName())) {
            if (MetaTypeEnum.BILL.getName().equals(group.getMetaType())) {
                groupRelationship.put(group.getUniqueCode(), group.getModuleName() + "_B_" + group.getParentName());
            } else if (MetaTypeEnum.BILLLIST.getName().equals(group.getMetaType())) {
                groupRelationship.put(group.getUniqueCode(), group.getModuleName() + "_L_" + group.getParentName());
            } else if (MetaTypeEnum.WORKFLOW.getName().equals(group.getMetaType())) {
                groupRelationship.put(group.getUniqueCode(), group.getModuleName() + "_W_" + group.getParentName());
            }
        } else {
            groupRelationship.put(group.getUniqueCode(), "-");
        }
    }

    @Override
    public List<MetaGroupDTO> getAuthGroupList(String module, String metaType, OperateType operateType) {
        String UNIQUE_CODE = "UNIQUECODE";
        List<MetaGroupDTO> groupDTOs = this.groupService.getGroupList(module, metaType, operateType);
        if (metaType == null || MetaTypeEnum.WORKFLOW.getName().equals(metaType)) {
            OptionItemVO optionItemVO;
            UserLoginDTO user = ShiroUtil.getUser();
            OptionItemDTO optionParam = new OptionItemDTO();
            optionParam.setName("META001");
            List<OptionItemVO> optionItemList = this.metaOptionService.list(optionParam);
            if (user.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = optionItemList.get(0)).getVal().equals("1")) {
                HashMap<String, String> groupRelationship = new HashMap<String, String>();
                for (MetaGroupDTO groupDto : groupDTOs) {
                    this.addRelationshipToMap(groupDto, groupRelationship);
                }
                MetaAuthDTO metaAuthCheck = new MetaAuthDTO();
                metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
                metaAuthCheck.setGroupflag(1);
                metaAuthCheck.setMetaType(MetaTypeEnum.WORKFLOW.getName());
                Set<String> userAuth = this.checkUserAuth(metaAuthCheck);
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>(16);
                HashSet<String> groupSet = new HashSet<String>(8);
                for (String name : userAuth) {
                    groupSet.add(name);
                    this.groupService.addChildrenToSet(name, groupRelationship, groupSet);
                    hashMap.put(name, 1);
                }
                ArrayList<MetaGroupDTO> infoTemp = new ArrayList<MetaGroupDTO>();
                HashMap<String, String> groupTemp = new HashMap<String, String>();
                MetaInfoPageDTO pageDTO = new MetaInfoPageDTO();
                pageDTO.setMetaType(MetaTypeEnum.WORKFLOW.getName());
                pageDTO.setModule(module);
                ArrayList listEdition = new ArrayList(Optional.ofNullable(this.metaInfoService.getMetaEditionGroup(pageDTO)).orElse(Collections.emptyList()));
                for (Map map : listEdition) {
                    this.groupService.addGroupToList(groupTemp, groupRelationship, String.valueOf(map.get("UNIQUECODE")));
                }
                metaAuthCheck.setGroupflag(0);
                Set<String> userDefine = this.checkUserAuth(metaAuthCheck);
                ArrayList listDeploy = new ArrayList(Optional.ofNullable(this.metaInfoService.getMetaInfoGroup(pageDTO)).orElse(Collections.emptyList()));
                for (Map map : listDeploy) {
                    if (!userDefine.contains(map.get("UNIQUECODE").toString())) continue;
                    this.groupService.addGroupToList(groupTemp, groupRelationship, map.get("UNIQUECODE").toString());
                }
                for (MetaGroupDTO dto : groupDTOs) {
                    if (dto.getMetaType().equals(MetaTypeEnum.WORKFLOW.getName()) && !hashMap.containsKey(dto.getUniqueCode()) && !groupTemp.containsKey(dto.getUniqueCode()) && dto.getState() == MetaState.DEPLOYED.getValue() && !groupSet.contains(dto.getUniqueCode())) continue;
                    infoTemp.add(dto);
                    groupTemp.put(dto.getUniqueCode(), dto.getUniqueCode());
                }
                groupDTOs = infoTemp;
            }
        }
        return groupDTOs;
    }

    @Override
    public List<MetaInfoDTO> getAuthMetaList(MetaInfoPageDTO infoPageDTO) {
        List<MetaInfoDTO> infoDTOs = this.metaInfoService.getMetaList(infoPageDTO);
        if (MetaTypeEnum.WORKFLOW.getName().equals(infoPageDTO.getMetaType()) || "all".equals(infoPageDTO.getMetaType())) {
            OptionItemVO optionItemVO;
            UserLoginDTO user = ShiroUtil.getUser();
            OptionItemDTO optionParam = new OptionItemDTO();
            optionParam.setName("META001");
            List<OptionItemVO> optionItemList = this.metaOptionService.list(optionParam);
            if (user.getMgrFlag().equals("normal") && optionItemList != null && !optionItemList.isEmpty() && (optionItemVO = optionItemList.get(0)).getVal().equals("1")) {
                List<MetaGroupDTO> groupDTOs = this.groupService.getGroupList(infoPageDTO.getModule(), infoPageDTO.getMetaType(), infoPageDTO.getOperateType());
                Set<String> allGroupAuth = this.getAllGroupAuth(groupDTOs, infoPageDTO.getMetaType());
                MetaAuthDTO metaAuthCheck = new MetaAuthDTO();
                metaAuthCheck.setTenantName(ShiroUtil.getTenantName());
                metaAuthCheck.setGroupflag(0);
                metaAuthCheck.setMetaType(infoPageDTO.getMetaType());
                Set<String> userAuth = this.checkUserAuth(metaAuthCheck);
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>(16);
                for (String name : userAuth) {
                    hashMap.put(name, 1);
                }
                ArrayList<MetaInfoDTO> infoTemp = new ArrayList<MetaInfoDTO>();
                for (MetaInfoDTO dto : infoDTOs) {
                    String uniqueCode = dto.getUniqueCode();
                    if (user.getId().equals(dto.getUsername()) && (dto.getState().equals(MetaState.APPENDED.getValue()) || dto.getState().equals(MetaState.MODIFIED.getValue()))) {
                        infoTemp.add(dto);
                        continue;
                    }
                    if (!hashMap.containsKey(dto.getUniqueCode()) && !allGroupAuth.contains(uniqueCode)) continue;
                    infoTemp.add(dto);
                }
                if (infoTemp.isEmpty()) {
                    return null;
                }
                infoDTOs = infoTemp;
            }
        }
        return infoDTOs;
    }

    private Set<String> queryUserAuth(MetaAuthDTO param) {
        UserLoginDTO user = ShiroUtil.getUser();
        StringBuilder sb = new StringBuilder();
        sb.append(" ( (biztype=0 and bizname in ('-'");
        List roles = this.authRoleClient.listByUser((UserDO)user);
        if (roles != null && roles.size() > 0) {
            for (RoleDO roleDO : roles) {
                sb.append(",'").append(roleDO.getName()).append("'");
            }
        }
        sb.append(")) or (biztype=1 and bizname='").append(user.getUsername()).append("') ) ");
        param.setAuthtype(1);
        param.setSqlCondition(sb.toString());
        List<MetaAuthDO> auths = this.metaDataAuthDao.listAuthority(param);
        Set<String> authNames = auths.stream().map(MetaAuthDO::getUniqueCode).collect(Collectors.toSet());
        return authNames;
    }
}

