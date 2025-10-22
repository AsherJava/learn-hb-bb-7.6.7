/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.basedata.auth.dao.VaBaseDataAuthDao
 *  com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO
 *  com.jiuqi.va.basedata.auth.domain.BaseDataAuthDTO
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataAuthClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.basedata.auth.dao.BaseDataAuthQueryDao;
import com.jiuqi.nr.entity.adapter.provider.IEntityAuthProvider;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataAuthRuleExtend;
import com.jiuqi.va.basedata.auth.dao.VaBaseDataAuthDao;
import com.jiuqi.va.basedata.auth.domain.BaseDataAuthDO;
import com.jiuqi.va.basedata.auth.domain.BaseDataAuthDTO;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataAuthClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="entityBaseDataAuthProvider")
public class EntityBaseDataAuthProvider
implements IEntityAuthProvider {
    private static final String SHARE_FORCE_CHECK = "shareForceCheck";
    @Autowired
    protected VaBaseDataAuthDao vaBaseDataAuthDao;
    @Autowired
    private List<IBaseDataAuthRuleExtend> baseDataAuthRuleExtends;
    @Autowired
    protected RoleService roleService;
    @Autowired
    private BaseDataAuthQueryDao baseDataAuthQueryDao;
    @Autowired
    protected SystemIdentityService systemIdentityService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataAuthClient authClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String entityKeyData, Date queryVersionDate) {
        PageVO list;
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(entityId);
        baseDataFilter.setObjectcode(entityKeyData);
        baseDataFilter.setVersionDate(queryVersionDate);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.valueOf((String)authType));
        if (this.isIsolation(entityId)) {
            baseDataFilter.setUnitcode(this.getCurrentOrgCode());
            baseDataFilter.put(SHARE_FORCE_CHECK, (Object)true);
        }
        if ((list = this.baseDataClient.list(baseDataFilter)) != null) {
            return list.getTotal() > 0;
        }
        return false;
    }

    @Override
    public Map<String, Boolean> canAuthorityEntityKey(String authType, String entityId, Set<String> entityKeyDatas, Date queryVersionDate) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>(entityKeyDatas.size());
        for (String entityKeyData : entityKeyDatas) {
            result.put(entityKeyData, Boolean.FALSE);
        }
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(entityId);
        baseDataFilter.setBaseDataObjectcodes(new ArrayList<String>(entityKeyDatas));
        baseDataFilter.setVersionDate(queryVersionDate);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.valueOf((String)authType));
        if (this.isIsolation(entityId)) {
            baseDataFilter.setUnitcode(this.getCurrentOrgCode());
            baseDataFilter.put(SHARE_FORCE_CHECK, (Object)true);
        }
        PageVO list = this.baseDataClient.list(baseDataFilter);
        List rows = list.getRows();
        for (BaseDataDO row : rows) {
            result.put(row.getObjectcode(), Boolean.TRUE);
        }
        return result;
    }

    private String getCurrentOrgCode() {
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user == null) {
            return null;
        }
        return user.getOrgCode();
    }

    private boolean isIsolation(String tableName) {
        BaseDataDefineDTO basedataDefine = new BaseDataDefineDTO();
        basedataDefine.setName(tableName);
        BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(basedataDefine);
        if (baseDataDefineDO == null) {
            return false;
        }
        return baseDataDefineDO.getSharetype() == null ? false : 0 != baseDataDefineDO.getSharetype();
    }

    @Override
    public boolean canAuthorityEntityKey(String authType, String entityId, String identityId, String entityKeyData, Date queryVersionDate) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(identityId);
        UserDO user = this.authUserClient.get(userDTO);
        if (user == null) {
            return false;
        }
        BaseDataAuthFindDTO authFindDTO = new BaseDataAuthFindDTO();
        authFindDTO.setUser(user);
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName(entityId);
        param.setAuthType(BaseDataOption.AuthType.valueOf((String)authType));
        param.setVersionDate(queryVersionDate);
        authFindDTO.setParam(param);
        BaseDataDO baseDataDO = new BaseDataDO();
        baseDataDO.setObjectcode(entityKeyData);
        baseDataDO.setTableName(entityId);
        authFindDTO.setDatas(new BaseDataDO[]{baseDataDO});
        Set auth = this.authClient.findAuth(authFindDTO);
        return !CollectionUtils.isEmpty(auth);
    }

    @Override
    public Set<String> getCanOperateEntityKeys(String authType, String entityId, Date versionEndDate) {
        BaseDataDTO baseDataFilter = new BaseDataDTO();
        baseDataFilter.setTableName(entityId);
        baseDataFilter.setVersionDate(versionEndDate);
        baseDataFilter.setAuthType(BaseDataOption.AuthType.valueOf((String)authType));
        if (this.isIsolation(entityId)) {
            baseDataFilter.setUnitcode(this.getCurrentOrgCode());
            baseDataFilter.put(SHARE_FORCE_CHECK, (Object)true);
        }
        PageVO list = this.baseDataClient.list(baseDataFilter);
        List rows = list.getRows();
        return rows.stream().map(BaseDataDO::getObjectcode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getCanOperateIdentities(String authType, String entityId, String entityKeyData, Date versionEndDate) {
        List<BaseDataAuthDO> detailAuthDOList;
        BaseDataOption.AuthType currentAuthType = BaseDataOption.AuthType.valueOf((String)authType);
        HashSet<String> userIds = new HashSet<String>();
        List<BaseDataAuthDO> ruleAuthDOList = this.getBaseDataAuth(entityId, currentAuthType);
        Set<String> allRule = this.extractAllRules(ruleAuthDOList);
        Map<String, List<BaseDataAuthDO>> roleRuleMap = this.groupRulesByRole(ruleAuthDOList);
        for (Map.Entry<String, List<BaseDataAuthDO>> roleRules : roleRuleMap.entrySet()) {
            List<BaseDataAuthDO> rules = roleRules.getValue();
            String role = roleRules.getKey();
            Optional roleOptional = this.roleService.findByName(role);
            if (!roleOptional.isPresent()) continue;
            role = ((Role)roleOptional.get()).getId();
            HashSet<String> userRoleRuleSet = null;
            block1: for (BaseDataAuthDO rule : rules) {
                for (IBaseDataAuthRuleExtend baseDataAuthRuleExtend : this.baseDataAuthRuleExtends) {
                    String ruleCode = baseDataAuthRuleExtend.getExtRulePrefix() + baseDataAuthRuleExtend.getName();
                    if (!rule.getObjectcode().equals(ruleCode)) continue;
                    Set<String> users = baseDataAuthRuleExtend.getGrantedIdentities(role, entityId, entityKeyData, versionEndDate, authType);
                    if (userRoleRuleSet == null) {
                        userRoleRuleSet = new HashSet<String>(users);
                    } else {
                        userRoleRuleSet.retainAll(users);
                    }
                    if (!userRoleRuleSet.isEmpty()) continue;
                    break block1;
                }
            }
            if (userRoleRuleSet == null) continue;
            userIds.addAll(userRoleRuleSet);
        }
        this.addAllRuleUsers(allRule, userIds);
        BaseDataDO row = this.getCurrentBaseDataItem(entityId, entityKeyData, versionEndDate);
        if (row != null && (detailAuthDOList = this.getDetailAuth(entityId, row.getObjectcode())) != null) {
            this.mergeUserId(userIds, detailAuthDOList, currentAuthType);
        }
        userIds.addAll(this.systemIdentityService.getAllSystemIdentities());
        return userIds;
    }

    private void addAllRuleUsers(Set<String> allRule, Set<String> userIds) {
        List byNames = this.roleService.getByNames(new ArrayList<String>(allRule));
        for (Role role : byNames) {
            List identityIdByRole = this.roleService.getIdentityIdByRole(role.getId());
            userIds.addAll(identityIdByRole);
        }
    }

    private Set<String> extractAllRules(List<BaseDataAuthDO> ruleAuthDOList) {
        HashSet<String> allRule = new HashSet<String>();
        for (BaseDataAuthDO authDO : ruleAuthDOList) {
            if (!"RULE_ALL".equals(authDO.getObjectcode())) continue;
            allRule.add(authDO.getBizname());
        }
        return allRule;
    }

    private Map<String, List<BaseDataAuthDO>> groupRulesByRole(List<BaseDataAuthDO> ruleAuthDOList) {
        HashMap<String, List<BaseDataAuthDO>> roleRuleMap = new HashMap<String, List<BaseDataAuthDO>>();
        for (BaseDataAuthDO authDO : ruleAuthDOList) {
            if ("RULE_ALL".equals(authDO.getObjectcode())) continue;
            List rules = roleRuleMap.computeIfAbsent(authDO.getBizname(), r -> new ArrayList());
            rules.add(authDO);
        }
        return roleRuleMap;
    }

    private BaseDataDO getCurrentBaseDataItem(String tableName, String entityKeyData, Date versionEndDate) {
        PageVO pageVO;
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setVersionDate(versionEndDate);
        baseDataDTO.setObjectcode(entityKeyData);
        if (this.isIsolation(tableName)) {
            baseDataDTO.setUnitcode(this.getCurrentOrgCode());
            baseDataDTO.put(SHARE_FORCE_CHECK, (Object)true);
        }
        if ((pageVO = this.baseDataClient.list(baseDataDTO)) != null && pageVO.getTotal() > 0) {
            return (BaseDataDO)pageVO.getRows().get(0);
        }
        return null;
    }

    private void mergeUserId(Set<String> userIds, List<BaseDataAuthDO> detailAuthDOList, BaseDataOption.AuthType currentAuthType) {
        HashSet<String> allow = new HashSet<String>();
        HashSet<String> reject = new HashSet<String>();
        for (BaseDataAuthDO authDO : detailAuthDOList) {
            Integer authValue = authDO.getAuthValue(currentAuthType);
            if (authValue == 1) {
                allow.add(authDO.getBizname());
                continue;
            }
            if (authValue != 2) continue;
            reject.add(authDO.getBizname());
        }
        Set<String> allowUserId = this.baseDataAuthQueryDao.getUserId(allow);
        Set<String> rejectUserId = this.baseDataAuthQueryDao.getUserId(reject);
        if (allowUserId != null) {
            userIds.addAll(allowUserId);
        }
        if (rejectUserId != null) {
            userIds.removeIf(rejectUserId::contains);
        }
    }

    private List<BaseDataAuthDO> getDetailAuth(String tableName, String objCode) {
        BaseDataAuthDTO detailAuthDTO = new BaseDataAuthDTO();
        detailAuthDTO.setBiztype(Integer.valueOf(1));
        detailAuthDTO.setAuthtype(Integer.valueOf(1));
        detailAuthDTO.setDefinename(tableName);
        detailAuthDTO.setObjectcode(objCode);
        return this.vaBaseDataAuthDao.select(detailAuthDTO);
    }

    private List<BaseDataAuthDO> getBaseDataAuth(String tableName, BaseDataOption.AuthType currentAuthType) {
        BaseDataAuthDTO ruleAuthDTO = new BaseDataAuthDTO();
        ruleAuthDTO.setBiztype(Integer.valueOf(0));
        ruleAuthDTO.setAuthtype(Integer.valueOf(0));
        ruleAuthDTO.setAuthValue(currentAuthType, Integer.valueOf(1));
        ruleAuthDTO.setDefinename(tableName);
        List select = this.vaBaseDataAuthDao.select(ruleAuthDTO);
        if (select != null) {
            return select;
        }
        return Collections.emptyList();
    }
}

