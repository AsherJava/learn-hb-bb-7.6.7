/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.web.util.BusinessLogUtils
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserOrgDTO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.organization.dao.VaOrgAuthDao
 *  com.jiuqi.va.organization.domain.OrgAuthDO
 *  com.jiuqi.va.organization.domain.OrgAuthDTO
 *  com.jiuqi.va.organization.service.impl.OrgAuthServiceImpl
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 */
package com.jiuqi.gcreport.listedcompanyauthz.rule;

import com.jiuqi.common.web.util.BusinessLogUtils;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.listedcompanyauthz.rule.base.EnumAuthzRuleType;
import com.jiuqi.gcreport.listedcompanyauthz.service.impl.ListedCompanyCacheService;
import com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserOrgDTO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.organization.dao.VaOrgAuthDao;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.service.impl.OrgAuthServiceImpl;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
@Primary
public class ListedCompanyOrgAuthServiceImpl
extends OrgAuthServiceImpl {
    @Autowired
    private VaOrgAuthDao orgAuthDao;
    @Autowired
    private AuthUserClient userClient;
    @Autowired
    private AuthRoleClient roleClient;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private ListedCompanyCacheService cacheService;
    private NedisCacheManager manager = DefaultCacheProvider.getCacheManager((String)"cachemanage_gcreport_orgdata_caffeine");

    public Set<String> listAuthOrg(UserDO user, OrgDTO orgDataDTO) {
        return this.listAuthOrg(this.isUseListedCompany(), user, orgDataDTO);
    }

    public R updateRule(List<OrgAuthDO> orgAuths) {
        GcOrgDataAuthzChangeEvent authzChangeEvent = new GcOrgDataAuthzChangeEvent();
        authzChangeEvent.addType("MD_ORG");
        R r = super.updateRule(orgAuths);
        try {
            UserDTO userDTO = new UserDTO();
            PageVO pageVO = new PageVO();
            Iterator<OrgAuthDO> iterator = orgAuths.iterator();
            if (iterator.hasNext()) {
                OrgAuthDO orgAuth = iterator.next();
                userDTO.setRoleName(orgAuth.getBizname());
                pageVO = this.userClient.list(userDTO);
            }
            if (CollectionUtils.isEmpty(pageVO.getRows())) {
                return r;
            }
            authzChangeEvent.getUsers().addAll(pageVO.getRows().stream().map(UserDO::getId).collect(Collectors.toList()));
            this.clearUserCache(authzChangeEvent);
        }
        catch (Exception e) {
            BusinessLogUtils.operate((String)"\u89d2\u8272\u89c4\u5219", (String)"\u66f4\u65b0\u89d2\u8272\u89c4\u5219\uff0c\u6e05\u7a7a\u5408\u5e76\u7f13\u5b58\u5f02\u5e38", (String)e.getMessage());
        }
        return r;
    }

    public void clearUserCache(GcOrgDataAuthzChangeEvent event) {
        if (event.getUsers().isEmpty()) {
            return;
        }
        Set cacheNames = event.getCacheNames();
        boolean clearAll = false;
        if (cacheNames.contains("MD_ORG")) {
            clearAll = true;
        }
        boolean finalClearAll = clearAll;
        this.manager.getCacheNames().forEach(v -> {
            if (finalClearAll) {
                NedisCache cache = this.manager.getCache(v);
                cache.mEvict((Collection)event.getUsers());
            } else if (cacheNames.isEmpty() || cacheNames.contains(v)) {
                NedisCache cache = this.manager.getCache(v);
                cache.mEvict((Collection)event.getUsers());
            }
        });
    }

    private Set<String> listAuthOrg(boolean useListedCompany, UserDO user, OrgDTO orgDataDTO) {
        Set<String> ruleSets;
        List<String> noAuthzlc;
        if (!useListedCompany) {
            return super.listAuthOrg(user, orgDataDTO);
        }
        HashSet<String> orgSet = new HashSet<String>();
        Object npConverted = user.getExtInfo("npConverted");
        if (npConverted == null || !((Boolean)npConverted).booleanValue()) {
            UserDTO udto = new UserDTO();
            udto.setId(user.getId());
            udto.setUsername(user.getUsername());
            udto.setUnitcode(user.getUnitcode());
            udto.setExtInfo(user.getExtInfo());
            udto.addExtInfo("onlyNeedBasicInfo", (Object)true);
            udto.addExtInfo("judgeBizMgr", (Object)(orgDataDTO.getAuthType() == OrgDataOption.AuthType.MANAGE ? 1 : 0));
            UserDO user2 = this.userClient.get(udto);
            if (user2 == null) {
                return orgSet;
            }
            Map extInfo = user2.getExtInfo();
            if (extInfo != null && user.getExtInfo() != null) {
                extInfo.putAll(user.getExtInfo());
            }
            user = user2;
        }
        if (CollectionUtils.isEmpty(noAuthzlc = this.getAllNoAuthzListedCompanyParents(user, orgDataDTO))) {
            return super.listAuthOrg(user, orgDataDTO);
        }
        Object isAdmin = user.getExtInfo("isAdmin");
        if (isAdmin != null && ((Boolean)isAdmin).booleanValue()) {
            List<OrgDO> orgAll = this.listAllOrg(orgDataDTO);
            for (OrgDO org : orgAll) {
                orgSet.add(org.getCode());
            }
            return orgSet;
        }
        String belongOrg = user.getUnitcode();
        if ("MD_ORG".equalsIgnoreCase(orgDataDTO.getCategoryname()) && StringUtils.hasText(belongOrg)) {
            orgSet.add(belongOrg);
        }
        OrgAuthDTO orgAuthDO = new OrgAuthDTO();
        orgAuthDO.setOrgDataDTO(orgDataDTO);
        List roles = this.roleClient.listByUser(user);
        Set<String> detailSets = this.getDetailAuth(orgAuthDO, roles, user);
        if (!CollectionUtils.isEmpty(detailSets)) {
            orgSet.addAll(detailSets);
        }
        if (!CollectionUtils.isEmpty(ruleSets = this.getRuleAuth(orgAuthDO, roles, user))) {
            orgSet.addAll(ruleSets);
        }
        return orgSet;
    }

    private Set<String> getRuleAuth(OrgAuthDTO orgAuthDO, List<RoleDO> roles, UserDO user) {
        HashSet<String> orgSet;
        block9: {
            List bothOrgs;
            List<OrgDO> orgAll;
            String belongOrg;
            OrgDTO orgDataDTO;
            Set<String> rules;
            block10: {
                orgSet = new HashSet<String>();
                Object isBizMgr = user.getExtInfo("isBizMgr");
                if (orgAuthDO.getOrgDataDTO().getAuthType() == OrgDataOption.AuthType.MANAGE && (orgAuthDO.getOrgDataDTO().isBusinessManager() || isBizMgr != null && ((Boolean)isBizMgr).booleanValue())) {
                    HashSet<String> fixedRules = new HashSet<String>();
                    fixedRules.add("rule_belong");
                    fixedRules.add("rule_all_children");
                    fixedRules.add("rule_both");
                    fixedRules.add("rule_both_all_children");
                    rules = fixedRules;
                } else {
                    rules = this.getRulesByRole(roles, orgAuthDO);
                }
                if (CollectionUtils.isEmpty(rules)) break block9;
                orgDataDTO = orgAuthDO.getOrgDataDTO();
                belongOrg = user.getUnitcode();
                orgAll = null;
                if (!rules.contains("rule_all")) break block10;
                if (orgAll == null) {
                    orgAll = this.listAllOrg(orgDataDTO);
                }
                for (OrgDO org : orgAll) {
                    orgSet.add(org.getCode());
                }
                break block9;
            }
            if (StringUtils.hasText(belongOrg)) {
                List<String> belongNoAuths = this.getAllNoAuthzListedCompanysByFilter(user, orgDataDTO, Arrays.asList(belongOrg));
                EnumAuthzRuleType type = EnumAuthzRuleType.findOf(rules.contains("rule_belong"), rules.contains("rule_all_children"), rules.contains("rule_direct_children"));
                if (orgAll == null) {
                    orgAll = this.listAllOrg(orgDataDTO);
                }
                ArrayList<String> accept = new ArrayList<String>();
                accept.add(belongOrg);
                for (OrgDO org : orgAll) {
                    if (!type.isAuthzOrg(org, accept, belongNoAuths)) continue;
                    orgSet.add(org.getCode());
                }
            }
            UserOrgDTO uoDTO = new UserOrgDTO();
            uoDTO.setUsername(user.getUsername());
            uoDTO = this.userClient.listBothOrg(uoDTO);
            if (uoDTO == null || (bothOrgs = uoDTO.getOrgCodes()) == null || bothOrgs.isEmpty()) break block9;
            List<String> bothNoAuths = this.getAllNoAuthzListedCompanysByFilter(user, orgDataDTO, bothOrgs);
            EnumAuthzRuleType type = EnumAuthzRuleType.findOf(rules.contains("rule_both"), rules.contains("rule_both_all_children"), rules.contains("rule_both_direct_childre"));
            if (orgAll == null) {
                orgAll = this.listAllOrg(orgDataDTO);
            }
            for (OrgDO org : orgAll) {
                if (!type.isAuthzOrg(org, bothOrgs, bothNoAuths)) continue;
                orgSet.add(org.getCode());
            }
        }
        return orgSet;
    }

    private Set<String> getDetailAuth(OrgAuthDTO orgAuthDO, List<RoleDO> roles, UserDO user) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ( (biztype=0 and bizname in ('-'");
        if (roles != null && roles.size() > 0) {
            for (RoleDO roleDO : roles) {
                sb.append(",'").append(roleDO.getName()).append("'");
            }
        }
        sb.append(")) or (biztype=1 and bizname='").append(user.getUsername()).append("') ) ");
        sb.append(" and ORGCATEGORY in('-', #{orgcategory, jdbcType=VARCHAR})");
        orgAuthDO.setAuthtype(Integer.valueOf(1));
        orgAuthDO.setOrgcategory(orgAuthDO.getOrgDataDTO().getCategoryname());
        orgAuthDO.setSqlCondition(sb.toString());
        return this.listAuthOrgCode(orgAuthDO);
    }

    private Set<String> getRulesByRole(List<RoleDO> roles, OrgAuthDTO orgAuthDO) {
        orgAuthDO.setBiztype(Integer.valueOf(0));
        orgAuthDO.setAuthtype(Integer.valueOf(0));
        orgAuthDO.setOrgcategory("-");
        StringBuilder sb = new StringBuilder();
        sb.append(" bizname in ('-'");
        if (roles != null && roles.size() > 0) {
            for (RoleDO roleDO : roles) {
                sb.append(",'").append(roleDO.getName()).append("'");
            }
        }
        sb.append(") ");
        orgAuthDO.setSqlCondition(sb.toString());
        return this.listAuthOrgCode(orgAuthDO);
    }

    private Set listAuthOrgCode(OrgAuthDTO orgAuthDO) {
        List orgs = this.orgAuthDao.listAuthItem(orgAuthDO);
        if (null == orgs) {
            return Collections.EMPTY_SET;
        }
        return orgs.stream().map(orgAuthItem -> orgAuthItem.getOrgname()).collect(Collectors.toSet());
    }

    private List<OrgDO> getOrgByCodes(OrgDTO orgDataDTO, Collection<String> codes) {
        if (codes == null || codes.size() < 0) {
            return new ArrayList<OrgDO>();
        }
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDataDTO.getCategoryname());
        param.setVersionDate(orgDataDTO.getVersionDate());
        param.setStopflag(Integer.valueOf(-1));
        param.setRecoveryflag(Integer.valueOf(-1));
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setOrgCodes(new ArrayList<String>(codes));
        ArrayList orgAll = this.orgDataCacheService.listBasicCacheData(param);
        if (orgAll == null) {
            orgAll = new ArrayList();
        }
        return orgAll;
    }

    private List<OrgDO> listAllOrg(OrgDTO orgDataDTO) {
        String tenantName = orgDataDTO.getTenantName();
        String categoryName = orgDataDTO.getCategoryname().toUpperCase();
        String hasKey = tenantName + ":OrgAuthService:" + categoryName;
        if (orgDataDTO.getExtInfo() != null && orgDataDTO.getExtInfo().containsKey(hasKey)) {
            return (List)orgDataDTO.getExtInfo().get(hasKey);
        }
        OrgDTO param = new OrgDTO();
        param.setCategoryname(orgDataDTO.getCategoryname());
        param.setVersionDate(orgDataDTO.getVersionDate());
        param.setStopflag(Integer.valueOf(-1));
        param.setRecoveryflag(Integer.valueOf(-1));
        param.setAuthType(OrgDataOption.AuthType.NONE);
        ArrayList<OrgDO> orgAll = this.orgDataCacheService.listBasicCacheData(param);
        if (orgAll == null) {
            orgAll = new ArrayList<OrgDO>();
        }
        orgDataDTO.addExtInfo(hasKey, orgAll);
        return orgAll;
    }

    private List<String> getAllNoAuthzListedCompanyParents(UserDO user, OrgDTO orgDataDTO) {
        Map<String, String> datas = this.getAllNoAuthzListedCompanyParentMaps(user, orgDataDTO);
        return new ArrayList<String>(datas.values());
    }

    private List<String> getAllNoAuthzListedCompanysByFilter(UserDO user, OrgDTO orgDataDTO, Collection<String> filter) {
        Map<String, String> datas = this.getAllNoAuthzListedCompanyParentMaps(user, orgDataDTO);
        ArrayList<String> companys = new ArrayList<String>();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            if (!filter.stream().anyMatch(v -> this.checkOrg((String)entry.getKey(), (String)v))) continue;
            companys.add(entry.getValue());
        }
        return companys;
    }

    private boolean checkOrg(String parents, String filter) {
        return parents.contains(this.getCodeWithSplit(filter));
    }

    private Map<String, String> getAllNoAuthzListedCompanyParentMaps(UserDO user, OrgDTO orgDataDTO) {
        String tenantName = orgDataDTO.getTenantName();
        String categoryName = orgDataDTO.getCategoryname().toUpperCase();
        String hasKey = tenantName + ":ListedCompanyAuthService:" + categoryName + ":" + user.getUsername();
        if (orgDataDTO.getExtInfo() != null && orgDataDTO.getExtInfo().containsKey(hasKey)) {
            return (Map)orgDataDTO.getExtInfo().get(hasKey);
        }
        List<String> org = this.getAllNoAuthzOrg(user.getUsername());
        List<OrgDO> ldCompanys = this.getOrgByCodes(orgDataDTO, org);
        Map<String, String> collect = ldCompanys.stream().collect(Collectors.toMap(f -> this.getCodeWithLeftSplit(f.getParents()), f -> this.getCodeWithSplit(f.getCode())));
        orgDataDTO.addExtInfo(hasKey, collect);
        return collect;
    }

    private List<String> getAllNoAuthzOrg(String userName) {
        List<String> listedCompany = this.cacheService.getListedCompany();
        List<String> listedCompanyAuthzByUser = this.cacheService.getListedCompanyAuthzByUser(userName);
        if (listedCompany == null) {
            return new ArrayList<String>();
        }
        if (listedCompanyAuthzByUser == null) {
            return listedCompany;
        }
        listedCompany.removeAll(listedCompanyAuthzByUser);
        return listedCompany;
    }

    private String getCodeWithLeftSplit(String code) {
        return "/" + code;
    }

    private String getCodeWithSplit(String code) {
        return "/" + code + "/";
    }

    private Set<String> filterAuthzOrg(UserDO user, OrgDTO orgDataDTO, Set<String> orgCodes) {
        List<String> exclude = this.getAllNoAuthzListedCompanysByFilter(user, orgDataDTO, orgCodes);
        List<OrgDO> orgByCodes = this.getOrgByCodes(orgDataDTO, orgCodes);
        return orgByCodes.stream().filter(v -> EnumAuthzRuleType.isAuthzByExcludeOrg(exclude, v.getParents())).map(v -> v.getCode()).collect(Collectors.toSet());
    }

    private boolean isUseListedCompany() {
        String value = GcSystermOptionTool.getOptionValue((String)"BEGIN_LISTEDCOMPANY_AUTHZ");
        return "1".equalsIgnoreCase(value);
    }
}

