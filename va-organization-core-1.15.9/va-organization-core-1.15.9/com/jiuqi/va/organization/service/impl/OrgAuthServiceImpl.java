/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrgContext
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.role.RoleDTO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserOrgDTO
 *  com.jiuqi.va.extend.OrgAuthExtendProvider
 *  com.jiuqi.va.extend.OrgAuthExtendProvider$Method
 *  com.jiuqi.va.extend.OrgAuthRuleExtend
 *  com.jiuqi.va.extend.OrgDataAuthTypeExtend
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.organization.service.impl;

import com.jiuqi.va.domain.common.OrgContext;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.role.RoleDTO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserOrgDTO;
import com.jiuqi.va.extend.OrgAuthExtendProvider;
import com.jiuqi.va.extend.OrgAuthRuleExtend;
import com.jiuqi.va.extend.OrgDataAuthTypeExtend;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.organization.common.FormatValidationUtil;
import com.jiuqi.va.organization.common.OrgCoreI18nUtil;
import com.jiuqi.va.organization.dao.VaOrgAuthDao;
import com.jiuqi.va.organization.domain.OrgAuthDO;
import com.jiuqi.va.organization.domain.OrgAuthDTO;
import com.jiuqi.va.organization.domain.OrgAuthItem;
import com.jiuqi.va.organization.domain.OrgAuthVO;
import com.jiuqi.va.organization.service.OrgAuthService;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service(value="vaOrgAuthServiceImpl")
public class OrgAuthServiceImpl
implements OrgAuthService {
    private static Logger logger = LoggerFactory.getLogger(OrgAuthServiceImpl.class);
    private static String[] fixedAuthTypes = new String[]{"MANAGE", "ACCESS", "WRITE", "EDIT", "REPORT", "SUBMIT", "APPROVAL"};
    private static String strNone = "-";
    private static String strRoleName = "roleName";
    private static String strRuleName = "ruleName";
    private static String strIdentity = "identity";
    private static String strUsername = "username";
    private static String strUnitcode = "unitcode";
    private static String rsOptSuccess = "org.success.common.operate";
    private static String rsParamEmpty = "org.error.parameter.data.empty";
    private static String roleEveryone = "EVERYONE";
    private static String userConvertFlag = "npConverted";
    @Autowired
    private VaOrgAuthDao orgAuthDao;
    @Autowired
    private AuthUserClient userClient;
    @Autowired
    private AuthRoleClient roleClient;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired(required=false)
    private List<OrgAuthRuleExtend> authRuleExtendList;
    @Autowired(required=false)
    private List<OrgDataAuthTypeExtend> authTypeExtends;
    @Autowired(required=false)
    private List<OrgAuthExtendProvider> authExtendsProvides;

    @Override
    public OrgAuthDO get(OrgAuthDTO orgAuthDTO) {
        List<OrgAuthDO> list = this.orgAuthDao.select(orgAuthDTO);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageVO<OrgAuthVO> listDetail(OrgAuthDTO param) {
        OrgDTO dataParam = param.getOrgDataDTO();
        dataParam.setCategoryname(param.getOrgcategory());
        PageVO<OrgDO> dataPage = this.orgDataService.list(dataParam);
        if (dataPage.getTotal() == 0) {
            return new PageVO(true);
        }
        HashMap<String, OrgAuthDO> authRef = new HashMap<String, OrgAuthDO>();
        List<OrgAuthDO> allOrgData = this.orgAuthDao.select(param);
        if (allOrgData != null && !allOrgData.isEmpty()) {
            for (OrgAuthDO orgAuthDO : allOrgData) {
                authRef.put(orgAuthDO.getOrgname(), orgAuthDO);
            }
        }
        PageVO endPage = new PageVO();
        endPage.setRs(R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0])));
        endPage.setTotal(dataPage.getTotal());
        LinkedHashMap<String, OrgAuthVO> rows = new LinkedHashMap<String, OrgAuthVO>();
        for (OrgDO orgDO : dataPage.getRows()) {
            OrgAuthVO vo = new OrgAuthVO();
            vo.setBiztype(param.getBiztype());
            vo.setBizname(param.getBizname());
            vo.setAuthtype(1);
            vo.setOrgcategory(param.getOrgcategory());
            vo.setOrgname(orgDO.getCode());
            vo.setOrgtitle(orgDO.getName());
            if (authRef.containsKey(orgDO.getCode())) {
                vo.putAll((Map)authRef.get(orgDO.getCode()));
            }
            for (String authItem : this.getAllAuthItems()) {
                vo.setAuthValue(authItem, vo.getAuthValue(authItem));
            }
            rows.put(orgDO.getCode(), vo);
        }
        if (param.isForShow() && param.getBiztype() == 1) {
            Set rowKeySet = rows.keySet();
            UserDO user = new UserDO();
            user.setUsername(param.getBizname());
            OrgDTO showParam = new OrgDTO();
            showParam.setCategoryname(dataParam.getCategoryname());
            Set<String> authSet = null;
            for (String authType : this.getAllAuthTypes()) {
                showParam.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
                authSet = this.listAuthOrg(user, showParam, false);
                if (authSet == null || authSet.isEmpty()) continue;
                for (String key : authSet) {
                    if (!rowKeySet.contains(key)) continue;
                    ((OrgAuthVO)rows.get(key)).put(("AT" + authType).toLowerCase() + "_p", (Object)1);
                }
            }
        }
        endPage.setRows(new ArrayList(rows.values()));
        return endPage;
    }

    private Set<String> getAllAuthTypes() {
        LinkedHashSet<String> list = new LinkedHashSet<String>();
        Collections.addAll(list, fixedAuthTypes);
        List<String> authTypeExtendNameList = this.getAuthTypeExtendName();
        if (authTypeExtendNameList != null) {
            list.addAll(authTypeExtendNameList);
        }
        return list;
    }

    private List<String> getAllAuthItems() {
        ArrayList<String> list = new ArrayList<String>();
        for (String authName : this.getAllAuthTypes()) {
            list.add(("AT" + authName).toLowerCase());
        }
        return list;
    }

    @Override
    @Transactional
    public R updateDetail(List<OrgAuthDO> orgAuths) {
        return this.update(orgAuths, 1);
    }

    @Override
    public PageVO<OrgAuthVO> listRule(OrgAuthDTO param) {
        ArrayList<OrgAuthVO> orgAuthVOList = new ArrayList<OrgAuthVO>();
        if (roleEveryone.equals(param.getBizname())) {
            param.setBizname(strNone);
        }
        this.ruleList("rule_all", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.all", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_belong", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.belong", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_direct_children", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.belongAndDirect", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_all_children", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.belongAndAll", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_both", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.both", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_both_direct_childre", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.bothAndDirect", new Object[0]), orgAuthVOList, param);
        this.ruleList("rule_both_all_children", OrgCoreI18nUtil.getMessage("org.attribute.auth.rule.bothAndAll", new Object[0]), orgAuthVOList, param);
        if (this.authRuleExtendList != null) {
            for (OrgAuthRuleExtend ruleExtend : this.authRuleExtendList) {
                this.ruleList(ruleExtend.getRuleName(), ruleExtend.getRuleTitle(), orgAuthVOList, param);
            }
        }
        if (param.containsKey(strIdentity)) {
            UserDO user = new UserDO();
            user.setId((String)param.get(strIdentity));
            user.setUsername((String)param.get(strUsername));
            user.setUnitcode((String)param.get(strUnitcode));
            user.addExtInfo(userConvertFlag, (Object)true);
            StringBuilder sb = new StringBuilder();
            List roles = this.roleClient.listNameByUser(user);
            sb.append(" BIZTYPE=0 and BIZNAME in ('-'");
            if (roles != null && !roles.isEmpty()) {
                int i = 0;
                for (String roleName : roles) {
                    param.put("role_" + i, (Object)roleName);
                    sb.append(", #{").append("role_").append(i).append(",jdbcType=VARCHAR}");
                    ++i;
                }
            }
            sb.append(") ");
            String authItem = FormatValidationUtil.checkInjection(param.get("authItem").toString());
            sb.append(" and " + authItem + "=1");
            param.setSqlCondition(sb.toString());
            Set<String> rules = this.orgAuthDao.listOrgCode(param);
            if (rules == null || rules.isEmpty()) {
                return new PageVO(true);
            }
            OrgAuthVO orgAuthVO = null;
            Iterator it = orgAuthVOList.iterator();
            while (it.hasNext()) {
                orgAuthVO = (OrgAuthVO)it.next();
                if (rules.contains(orgAuthVO.getOrgname())) continue;
                it.remove();
            }
        }
        PageVO page = new PageVO();
        page.setRs(R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0])));
        page.setRows(orgAuthVOList);
        page.setTotal(orgAuthVOList.size());
        return page;
    }

    private void ruleList(String name, String title, List<OrgAuthVO> orgAuthVOList, OrgAuthDTO orgAuthDTO) {
        OrgAuthVO oavo = new OrgAuthVO();
        oavo.setOrgcategory(strNone);
        oavo.setOrgname(name);
        oavo.setOrgtitle(title);
        oavo.setBiztype(0);
        oavo.setBizname(orgAuthDTO.getBizname());
        oavo.setAuthtype(0);
        if (orgAuthDTO.getBizname() != null) {
            orgAuthDTO.setOrgcategory(strNone);
            orgAuthDTO.setOrgname(name);
            OrgAuthDO oado = this.get(orgAuthDTO);
            if (oado != null) {
                oavo.putAll(oado);
            }
        }
        for (String authItem : this.getAllAuthItems()) {
            oavo.setAuthValue(authItem, oavo.getAuthValue(authItem));
        }
        orgAuthVOList.add(oavo);
    }

    @Override
    @Transactional
    public R updateRule(List<OrgAuthDO> orgAuths) {
        return this.update(orgAuths, 0);
    }

    private R update(List<OrgAuthDO> orgAuths, int authType) {
        if (orgAuths == null || orgAuths.isEmpty()) {
            return R.error((String)OrgCoreI18nUtil.getMessage(rsParamEmpty, new Object[0]));
        }
        OrgAuthDO param = orgAuths.get(0);
        OrgAuthDTO authParam = new OrgAuthDTO();
        authParam.setBiztype(param.getBiztype());
        authParam.setBizname(param.getBizname());
        authParam.setAuthtype(authType);
        if (authType == 1) {
            authParam.setOrgcategory(param.getOrgcategory());
        } else {
            authParam.setOrgcategory(strNone);
        }
        String orgname = null;
        for (OrgAuthDO orgAuthDO : orgAuths) {
            orgname = orgAuthDO.getOrgname();
            if (!StringUtils.hasText(orgname) || strNone.equals(orgname)) continue;
            authParam.setOrgname(orgname);
            this.update(orgAuthDO, authParam);
        }
        return R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0]));
    }

    private void update(OrgAuthDO orgAuthDO, OrgAuthDTO authParam) {
        boolean emptyFlag = true;
        for (String authItem : this.getAllAuthItems()) {
            if (orgAuthDO.getAuthValue(authItem) == 0) continue;
            emptyFlag = false;
            break;
        }
        if (emptyFlag) {
            this.orgAuthDao.delete(authParam);
            return;
        }
        OrgAuthDO oldData = this.get(authParam);
        if (oldData != null) {
            orgAuthDO.setId(oldData.getId());
            this.orgAuthDao.update(orgAuthDO);
        } else {
            orgAuthDO.setId(UUID.randomUUID());
            this.orgAuthDao.insert(orgAuthDO);
        }
    }

    @Override
    public Set<String> listAuthOrg(UserDO user, OrgDTO orgDataDTO) {
        return this.listAuthOrg(user, orgDataDTO, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<String> listAuthOrg(UserDO user, OrgDTO orgDataDTO, boolean containsUserDetail) {
        String tenantName = orgDataDTO.getTenantName();
        String categoryName = orgDataDTO.getCategoryname();
        HashSet<String> orgSet = new HashSet<String>();
        boolean isCurrentCategory = OrgContext.isCurrentCategory((String)tenantName, (String)categoryName);
        Map currColIndex = OrgContext.getColIndexContext();
        HashMap oldColIndex = null;
        HashMap oldColAllIndex = null;
        if (!isCurrentCategory) {
            if (currColIndex != null) {
                oldColIndex = new HashMap(currColIndex);
                oldColAllIndex = new HashMap(OrgContext.getColAllKeyContext());
            }
            OrgContext.bindColIndex((String)tenantName, (String)categoryName);
        }
        try {
            Object isAdmin;
            Object extInfo;
            Object npConverted = user.getExtInfo(userConvertFlag);
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
                    HashSet<String> hashSet = orgSet;
                    return hashSet;
                }
                extInfo = user2.getExtInfo();
                if (extInfo != null && user.getExtInfo() != null) {
                    extInfo.putAll(user.getExtInfo());
                }
                user = user2;
            }
            if (this.authExtendsProvides != null) {
                for (OrgAuthExtendProvider provider : this.authExtendsProvides) {
                    if (!provider.isEnabled(user, orgDataDTO, OrgAuthExtendProvider.Method.LIST_AUTH_ORG)) continue;
                    extInfo = provider.listAuthOrg(user, orgDataDTO);
                    return extInfo;
                }
            }
            if ((isAdmin = user.getExtInfo("isAdmin")) != null && ((Boolean)isAdmin).booleanValue()) {
                OrgDTO codeParamDTO = new OrgDTO();
                codeParamDTO.setTenantName(tenantName);
                codeParamDTO.setCategoryname(categoryName);
                codeParamDTO.setVersionDate(orgDataDTO.getVersionDate());
                codeParamDTO.setCode("-");
                codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
                extInfo = this.orgDataCacheService.getRefCodeList(codeParamDTO);
                return extInfo;
            }
            OrgAuthDTO orgAuthDO = new OrgAuthDTO();
            orgAuthDO.setOrgDataDTO(orgDataDTO);
            Object isBizMgr = user.getExtInfo("isBizMgr");
            if (orgDataDTO.getAuthType() == OrgDataOption.AuthType.MANAGE && (orgDataDTO.isBusinessManager() || isBizMgr != null && ((Boolean)isBizMgr).booleanValue())) {
                HashSet<String> fixedRules = new HashSet<String>();
                fixedRules.add("rule_belong");
                fixedRules.add("rule_all_children");
                fixedRules.add("rule_both");
                fixedRules.add("rule_both_all_children");
                Set<String> set = this.getRuleAuth(orgAuthDO.getOrgDataDTO(), user, fixedRules, true);
                return set;
            }
            List roles = this.roleClient.listNameByUser(user);
            List<OrgAuthItem> authItemList = this.listAuthItem(orgAuthDO, roles, (UserDO)(containsUserDetail ? user : null));
            if (authItemList == null || authItemList.isEmpty()) {
                this.addBelongMdOrg(orgDataDTO.getCategoryname(), user.getUnitcode(), orgSet);
                HashSet<String> hashSet = orgSet;
                return hashSet;
            }
            HashSet<String> hasRules = new HashSet<String>();
            HashSet<String> refuseSets = new HashSet<String>();
            for (OrgAuthItem item : authItemList) {
                if (item.getAuthtype() == 0) {
                    hasRules.add(item.getOrgname());
                    continue;
                }
                if (item.getAuthflag() == 1) {
                    orgSet.add(item.getOrgname());
                    continue;
                }
                if (item.getAuthflag() != 2) continue;
                refuseSets.add(item.getOrgname());
            }
            Set<String> ruleSets = this.getRuleAuth(orgAuthDO.getOrgDataDTO(), user, hasRules, true);
            if (!ruleSets.isEmpty()) {
                orgSet.addAll(ruleSets);
            }
            if (!refuseSets.isEmpty()) {
                orgSet.removeAll(refuseSets);
            }
        }
        catch (Throwable e) {
            logger.error("\u7ec4\u7ec7\u673a\u6784\u6743\u9650\u83b7\u53d6\u5931\u8d25", e);
        }
        finally {
            if (!isCurrentCategory) {
                if (currColIndex == null) {
                    OrgContext.unbindColIndex();
                } else {
                    OrgContext.bindColIndex((String)tenantName, (String)categoryName, oldColIndex, oldColAllIndex);
                }
            }
        }
        return orgSet;
    }

    private List<OrgAuthItem> listAuthItem(OrgAuthDTO orgAuthDO, List<String> roles, UserDO user) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ( (BIZTYPE=0 and BIZNAME in ('-'");
        if (roles != null && !roles.isEmpty()) {
            int i = 0;
            for (String roleName : roles) {
                orgAuthDO.put("role_" + i, (Object)roleName);
                sb.append(", #{").append("role_").append(i).append(",jdbcType=VARCHAR}");
                ++i;
            }
        }
        if (user != null) {
            orgAuthDO.put("user_0", (Object)user.getUsername());
            sb.append(")) or (BIZTYPE=1 and BIZNAME = #{user_0, jdbcType=VARCHAR}) ) ");
        } else {
            sb.append(")) )");
        }
        orgAuthDO.setOrgcategory(orgAuthDO.getOrgDataDTO().getCategoryname());
        sb.append(" and ORGCATEGORY in('-', #{orgcategory, jdbcType=VARCHAR})");
        orgAuthDO.setSqlCondition(sb.toString());
        return this.orgAuthDao.listAuthItem(orgAuthDO);
    }

    private void addBelongMdOrg(String categoryname, String belongOrg, Set<String> orgSet) {
        if ("MD_ORG".equalsIgnoreCase(categoryname) && StringUtils.hasText(belongOrg)) {
            orgSet.add(belongOrg);
        }
    }

    private Set<String> getRuleAuth(OrgDTO orgDataDTO, UserDO user, Set<String> rules, boolean addBelongMdOrg) {
        HashSet<String> orgSet = new HashSet<String>();
        String belongOrg = user.getUnitcode();
        if (addBelongMdOrg) {
            this.addBelongMdOrg(orgDataDTO.getCategoryname(), belongOrg, orgSet);
        }
        if (rules == null || rules.isEmpty()) {
            return orgSet;
        }
        OrgDTO codeParamDTO = new OrgDTO();
        codeParamDTO.setTenantName(orgDataDTO.getTenantName());
        codeParamDTO.setCategoryname(orgDataDTO.getCategoryname());
        codeParamDTO.setVersionDate(orgDataDTO.getVersionDate());
        if (rules.contains("rule_all")) {
            codeParamDTO.setCode("-");
            codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
            Set<String> codeSet = this.orgDataCacheService.getRefCodeList(codeParamDTO);
            if (!codeSet.isEmpty()) {
                orgSet.addAll(codeSet);
            }
            orgSet.add("-");
            this.ruleExtendRun(orgDataDTO, user, rules, orgSet);
            return orgSet;
        }
        if (StringUtils.hasText(belongOrg)) {
            Set<String> codeSet;
            if (rules.contains("rule_belong")) {
                orgSet.add(belongOrg);
            }
            if (rules.contains("rule_all_children")) {
                codeParamDTO.setCode(belongOrg);
                codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
                codeSet = this.orgDataCacheService.getRefCodeList(codeParamDTO);
                if (!codeSet.isEmpty()) {
                    orgSet.addAll(codeSet);
                }
            } else if (rules.contains("rule_direct_children")) {
                codeParamDTO.setCode(belongOrg);
                codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
                codeSet = this.orgDataCacheService.getRefCodeList(codeParamDTO);
                if (!codeSet.isEmpty()) {
                    orgSet.addAll(codeSet);
                }
            }
        }
        UserOrgDTO uoDTO = new UserOrgDTO();
        uoDTO.setUsername(user.getUsername());
        uoDTO.addExtInfo("contextIdentity", (Object)user.getId());
        uoDTO = this.userClient.listBothOrg(uoDTO);
        if (uoDTO != null) {
            List bothOrgs = uoDTO.getOrgCodes();
            if (bothOrgs == null || bothOrgs.isEmpty()) {
                this.ruleExtendRun(orgDataDTO, user, rules, orgSet);
                return orgSet;
            }
            if (rules.contains("rule_both")) {
                orgSet.addAll(bothOrgs);
            }
            if (rules.contains("rule_both_all_children")) {
                for (String bcode : bothOrgs) {
                    codeParamDTO.setCode(bcode);
                    codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.ALL_CHILDREN);
                    Set<String> codeSet = this.orgDataCacheService.getRefCodeList(codeParamDTO);
                    if (codeSet.isEmpty()) continue;
                    orgSet.addAll(codeSet);
                }
            } else if (rules.contains("rule_both_direct_childre")) {
                for (String bcode : bothOrgs) {
                    codeParamDTO.setCode(bcode);
                    codeParamDTO.setQueryChildrenType(OrgDataOption.QueryChildrenType.DIRECT_CHILDREN);
                    Set<String> codeSet = this.orgDataCacheService.getRefCodeList(codeParamDTO);
                    if (codeSet.isEmpty()) continue;
                    orgSet.addAll(codeSet);
                }
            }
        }
        this.ruleExtendRun(orgDataDTO, user, rules, orgSet);
        return orgSet;
    }

    private void ruleExtendRun(OrgDTO orgDataDTO, UserDO user, Set<String> rules, Set<String> orgSet) {
        if (this.authRuleExtendList != null) {
            for (OrgAuthRuleExtend ruleExtend : this.authRuleExtendList) {
                if (!rules.contains(ruleExtend.getRuleName())) continue;
                ruleExtend.filterData(user, orgDataDTO, orgSet);
            }
        }
    }

    @Override
    public R existCategoryAuth(UserDO user, OrgDTO param) {
        Set<String> sets = this.listAuthOrg(user, param);
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0]));
        rs.put("exist", (Object)(sets != null && !sets.isEmpty() ? 1 : 0));
        return rs;
    }

    @Override
    public R existDataAuth(UserDO user, OrgDTO orgDTO) {
        Object isAdmin;
        String orgCode = orgDTO.getCode();
        if (orgCode == null) {
            R rs = R.error((String)"\u7f3a\u5c11code\u53c2\u6570");
            rs.put("exist", (Object)false);
            return rs;
        }
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0]));
        Object npConverted = user.getExtInfo(userConvertFlag);
        if (npConverted == null || !((Boolean)npConverted).booleanValue()) {
            UserDTO udto = new UserDTO();
            udto.setId(user.getId());
            udto.setUsername(user.getUsername());
            udto.addExtInfo("onlyNeedBasicInfo", (Object)true);
            udto.addExtInfo("judgeBizMgr", (Object)(orgDTO.getAuthType() == OrgDataOption.AuthType.MANAGE ? 1 : 0));
            UserDO user2 = this.userClient.get(udto);
            if (user2 == null) {
                rs.put("exist", (Object)false);
                return rs;
            }
            Map extInfo = user2.getExtInfo();
            if (extInfo != null && user.getExtInfo() != null) {
                extInfo.putAll(user.getExtInfo());
            }
            user = user2;
        }
        if (this.authExtendsProvides != null) {
            for (OrgAuthExtendProvider provider : this.authExtendsProvides) {
                if (!provider.isEnabled(user, orgDTO, OrgAuthExtendProvider.Method.EXIST_DATA_AUTH)) continue;
                rs.put("exist", (Object)provider.existDataAuth(user, orgDTO));
                return rs;
            }
        }
        if ((isAdmin = user.getExtInfo("isAdmin")) != null && ((Boolean)isAdmin).booleanValue()) {
            rs.put("exist", (Object)true);
            return rs;
        }
        if ("MD_ORG".equalsIgnoreCase(orgDTO.getCategoryname()) && orgCode.equals(user.getUnitcode())) {
            rs.put("exist", (Object)true);
            return rs;
        }
        OrgAuthDTO orgAuthDO = new OrgAuthDTO();
        orgAuthDO.setOrgDataDTO(orgDTO);
        Object isBizMgr = user.getExtInfo("isBizMgr");
        if (orgDTO.getAuthType() == OrgDataOption.AuthType.MANAGE && (orgDTO.isBusinessManager() || isBizMgr != null && ((Boolean)isBizMgr).booleanValue())) {
            HashSet<String> fixedRules = new HashSet<String>();
            fixedRules.add("rule_belong");
            fixedRules.add("rule_all_children");
            fixedRules.add("rule_both");
            fixedRules.add("rule_both_all_children");
            Set<String> ruleSets = this.getRuleAuth(orgAuthDO.getOrgDataDTO(), user, fixedRules, true);
            rs.put("exist", (Object)ruleSets.contains(orgCode));
            return rs;
        }
        List roles = this.roleClient.listNameByUser(user);
        List<OrgAuthItem> authItemList = this.listAuthItem(orgAuthDO, roles, user);
        if (authItemList == null || authItemList.isEmpty()) {
            rs.put("exist", (Object)false);
            return rs;
        }
        HashSet<String> hasRules = new HashSet<String>();
        HashSet<String> detailSets = new HashSet<String>();
        HashSet<String> refuseSets = new HashSet<String>();
        for (OrgAuthItem item : authItemList) {
            if (item.getAuthtype() == 0) {
                hasRules.add(item.getOrgname());
                continue;
            }
            if (item.getAuthflag() == 1) {
                detailSets.add(item.getOrgname());
                continue;
            }
            if (item.getAuthflag() != 2) continue;
            refuseSets.add(item.getOrgname());
        }
        if (!refuseSets.isEmpty() && refuseSets.contains(orgCode)) {
            rs.put("exist", (Object)false);
            return rs;
        }
        if (!detailSets.isEmpty() && detailSets.contains(orgCode)) {
            rs.put("exist", (Object)true);
            return rs;
        }
        Set<String> ruleSets = this.getRuleAuth(orgAuthDO.getOrgDataDTO(), user, hasRules, true);
        if (!ruleSets.isEmpty() && ruleSets.contains(orgCode)) {
            rs.put("exist", (Object)true);
            return rs;
        }
        rs.put("exist", (Object)false);
        return rs;
    }

    @Override
    public List<String> getAuthTypeExtendName() {
        if (this.authTypeExtends != null) {
            HashSet hasAuth = new HashSet();
            ArrayList<String> nameList = new ArrayList<String>();
            for (OrgDataAuthTypeExtend authTypeExtend : this.authTypeExtends) {
                Map authTypeMap = authTypeExtend.getAuthType();
                for (String name : authTypeMap.keySet()) {
                    if (hasAuth.contains(name)) continue;
                    nameList.add(name);
                }
            }
            return nameList;
        }
        return null;
    }

    @Override
    public List<Map<String, String>> getAuthType() {
        if (this.authTypeExtends != null) {
            HashSet hasAuth = new HashSet();
            ArrayList<Map<String, String>> authTypeExtendMap = new ArrayList<Map<String, String>>();
            for (OrgDataAuthTypeExtend authTypeExtendClass : this.authTypeExtends) {
                Map authTypeMap = authTypeExtendClass.getAuthType();
                authTypeMap.forEach((k, v) -> {
                    if (!hasAuth.contains(k)) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", (String)k);
                        map.put("title", (String)v);
                        authTypeExtendMap.add(map);
                        hasAuth.add(k);
                    }
                });
            }
            return authTypeExtendMap;
        }
        return null;
    }

    @Override
    public R findPath(OrgAuthDTO param) {
        StringBuilder sb;
        OrgDTO orgDataDTO = param.getOrgDataDTO();
        String authItem = FormatValidationUtil.checkInjection(param.get("authItem").toString());
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authItem.substring(2).toUpperCase()));
        int authType = param.getAuthtype();
        OrgDTO codeParamDTO = new OrgDTO();
        codeParamDTO.setCategoryname(orgDataDTO.getCategoryname());
        codeParamDTO.setVersionDate(orgDataDTO.getVersionDate());
        codeParamDTO.setAuthType(OrgDataOption.AuthType.NONE);
        UserDO user = new UserDO();
        user.setId((String)param.get(strIdentity));
        user.setUsername((String)param.get(strUsername));
        user.setUnitcode((String)param.get(strUnitcode));
        user.addExtInfo(userConvertFlag, (Object)true);
        List roles = null;
        Set<String> orgCodes = null;
        if (authType == -1) {
            orgCodes = this.listAuthOrg(user, orgDataDTO);
        } else if (authType == 0) {
            sb = new StringBuilder();
            sb.append(authItem + "=1");
            sb.append(" and AUTHTYPE=0 and BIZTYPE=0 and BIZNAME in ('-'");
            roles = this.roleClient.listNameByUser(user);
            if (roles != null && !roles.isEmpty()) {
                int i = 0;
                for (String roleName : roles) {
                    param.put("role_" + i, (Object)roleName);
                    sb.append(", #{").append("role_").append(i).append(",jdbcType=VARCHAR}");
                    ++i;
                }
            }
            sb.append(") ");
            if (param.containsKey(strRuleName)) {
                String ruleName = (String)param.get(strRuleName);
                param.put("rule_0", (Object)ruleName);
                sb.append(" and ORGNAME = #{rule_0}");
                param.setSqlCondition(sb.toString());
                orgCodes = this.getRuleAuth(codeParamDTO, user, Collections.singleton(ruleName), false);
            } else {
                param.setSqlCondition(sb.toString());
                Set<String> rules = this.orgAuthDao.listOrgCode(param);
                orgCodes = this.getRuleAuth(codeParamDTO, user, rules, true);
            }
        } else {
            sb = new StringBuilder();
            sb.append(authItem + "=" + (authType == 2 ? 2 : 1));
            if (authType == 1 || authType == 11 || authType == 10 || authType == 2) {
                param.setOrgcategory(param.getOrgDataDTO().getCategoryname());
                sb.append(" and ORGCATEGORY = #{orgcategory, jdbcType=VARCHAR}");
            }
            sb.append(" and AUTHTYPE=1 and (");
            boolean needUsername = false;
            if (authType == 1 || authType == 11 || authType == 2) {
                needUsername = true;
                param.put("user_0", (Object)user.getUsername());
                sb.append(" (BIZTYPE=1 and BIZNAME = #{user_0, jdbcType=VARCHAR}) ");
            }
            if (authType == 1 || authType == 10) {
                roles = this.roleClient.listNameByUser(user);
                if (needUsername) {
                    sb.append(" or ");
                }
                if (param.containsKey(strRoleName)) {
                    param.put("role_0", param.get(strRoleName));
                    sb.append(" (BIZTYPE=0 and BIZNAME = #{role_0, jdbcType=VARCHAR}) ");
                } else {
                    sb.append(" (BIZTYPE=0 and BIZNAME in ('-'");
                    if (roles != null && !roles.isEmpty()) {
                        int i = 0;
                        for (String roleName : roles) {
                            param.put("role_" + i, (Object)roleName);
                            sb.append(", #{").append("role_").append(i).append(",jdbcType=VARCHAR}");
                            ++i;
                        }
                    }
                    sb.append(")) ");
                }
            }
            sb.append(") ");
            param.setSqlCondition(sb.toString());
            orgCodes = this.orgAuthDao.listOrgCode(param);
        }
        R rs = R.ok((String)OrgCoreI18nUtil.getMessage(rsOptSuccess, new Object[0]));
        if (authType == 10 && !param.containsKey(strRoleName) || authType == 0 && param.containsKey(strRuleName)) {
            Set<String> bizNames = this.orgAuthDao.listBizName(param);
            if (bizNames.contains(strNone)) {
                bizNames.add(roleEveryone);
            }
            RoleDTO roleParam = new RoleDTO();
            roleParam.setRoleNames((String[])bizNames.stream().toArray(String[]::new));
            rs.put("roles", (Object)this.roleClient.list(roleParam).getRows());
        }
        if (orgCodes == null || orgCodes.isEmpty()) {
            return rs;
        }
        codeParamDTO.setOrgCodes(new ArrayList<String>(orgCodes));
        PageVO<OrgDO> page = this.orgDataService.list(codeParamDTO);
        if (page.getTotal() > 0) {
            ArrayList<String> result = new ArrayList<String>();
            for (OrgDO org : page.getRows()) {
                result.add(org.getOrgcode() + " " + org.getName());
            }
            rs.put("result", result);
        }
        return rs;
    }
}

