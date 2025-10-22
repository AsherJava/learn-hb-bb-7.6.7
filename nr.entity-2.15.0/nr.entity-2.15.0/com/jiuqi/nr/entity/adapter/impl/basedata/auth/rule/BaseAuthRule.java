/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.basedata.auth.dao.VaBaseDataAuthDao
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.apache.shiro.util.Assert
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.entity.adapter.impl.basedata.auth.dao.BaseDataAuthQueryDao;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataAuthRuleEnableService;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataAuthRuleExtend;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataRelationService;
import com.jiuqi.va.basedata.auth.dao.VaBaseDataAuthDao;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public abstract class BaseAuthRule
implements IBaseDataAuthRuleExtend {
    @Autowired
    protected BaseDataAuthQueryDao baseDataAuthQueryDao;
    @Autowired
    protected VaBaseDataAuthDao vaBaseDataAuthDao;
    @Autowired
    protected BaseDataClient baseDataClient;
    @Autowired(required=false)
    protected IBaseDataAuthRuleEnableService baseDataAuthRuleEnableService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected AuthRoleClient authRoleClient;
    @Autowired
    protected OrgDataClient orgDataClient;
    @Autowired
    protected IBaseDataRelationService iBaseDataRelationService;

    public boolean isEnable(BaseDataDTO param) {
        if (this.baseDataAuthRuleEnableService == null) {
            return false;
        }
        return this.baseDataAuthRuleEnableService.isEnable(param);
    }

    public Set<String> hasAuth(BaseDataAuthFindDTO findDTO) {
        return this.loadAuthCodes(findDTO);
    }

    protected abstract Set<String> loadAuthCodes(BaseDataAuthFindDTO var1);

    @Override
    public Set<String> getGrantedIdentities(String role, String entityId, String objectCode, Date versionDate, String authType) {
        BaseDataAuthFindDTO findDTO = new BaseDataAuthFindDTO();
        BaseDataDTO param = new BaseDataDTO();
        param.setTenantName(ShiroUtil.getTenantName());
        param.setTableName(entityId);
        param.setVersionDate(versionDate);
        param.setObjectcode(objectCode);
        param.setAuthType(BaseDataOption.AuthType.valueOf((String)authType));
        if (param.getAuthType() == BaseDataOption.AuthType.NONE) {
            return Collections.emptySet();
        }
        findDTO.setParam(param);
        Set<String> refUserIds = this.loadAuthUsers(findDTO);
        if (CollectionUtils.isEmpty(refUserIds)) {
            return new HashSet<String>();
        }
        if (role.equals("-")) {
            return refUserIds;
        }
        HashSet<String> refUserIdsTemp = new HashSet<String>();
        for (String refUserId : refUserIds) {
            Set roleSet = this.roleService.getIdByIdentity(refUserId);
            if (!roleSet.contains(role)) continue;
            refUserIdsTemp.add(refUserId);
        }
        return refUserIdsTemp;
    }

    protected abstract Set<String> loadAuthUsers(BaseDataAuthFindDTO var1);

    protected BaseDataDO getBaseDataItem(String tableName, String key, Date versionDate) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataDTO.setTableName(tableName);
        baseDataDTO.setObjectcode(key);
        baseDataDTO.setVersionDate(versionDate);
        PageVO list = this.baseDataClient.list(baseDataDTO);
        if (list != null && list.getTotal() > 0) {
            return (BaseDataDO)list.getRows().get(0);
        }
        return null;
    }

    protected Set<String> baseDataChildrenWithUserId(BaseDataDTO param, String parentCode, BaseDataOption.QueryChildrenType type) {
        BaseDataDTO baseDataDTO = this.initParam(param);
        baseDataDTO.setObjectcode(parentCode);
        baseDataDTO.setQueryChildrenType(type);
        return this.queryObjCodes(baseDataDTO);
    }

    private BaseDataDTO initParam(BaseDataDTO param) {
        ContextUser user;
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTenantName(param.getTenantName());
        basedataDTO.setTableName(param.getTableName());
        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        basedataDTO.setVersionDate(param.getVersionDate());
        basedataDTO.setOrderBy(Collections.emptyList());
        basedataDTO.setStopflag(param.getStopflag());
        Integer shareType = (Integer)param.get((Object)"defineSharetype");
        if (shareType != null && shareType != 0 && (user = NpContextHolder.getContext().getUser()) != null) {
            basedataDTO.setUnitcode(user.getOrgCode());
        }
        return basedataDTO;
    }

    private Set<String> queryObjCodes(BaseDataDTO basedataDTO) {
        PageVO pageVO = this.baseDataClient.list(basedataDTO);
        if (pageVO != null && pageVO.getTotal() > 0) {
            HashSet<String> res = new HashSet<String>();
            List list = pageVO.getRows();
            for (BaseDataDO baseDataDO : list) {
                res.add(baseDataDO.getObjectcode());
            }
            return res;
        }
        return new HashSet<String>();
    }

    protected Set<String> orgDataWithParentCode(BaseDataDTO param, String tableName, String unitCode, OrgDataOption.QueryChildrenType queryChildrenType) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(tableName);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setOrderBy(Collections.emptyList());
        orgDTO.setStopflag(param.getStopflag());
        orgDTO.setCode(unitCode);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        orgDTO.setQueryChildrenType(queryChildrenType);
        PageVO orgDOPageVO = this.orgDataClient.list(orgDTO);
        if (orgDOPageVO != null && orgDOPageVO.getTotal() > 0) {
            HashSet<String> orgCodes = new HashSet<String>();
            for (OrgDO row : orgDOPageVO.getRows()) {
                orgCodes.add(row.getCode());
            }
            return orgCodes;
        }
        return Collections.emptySet();
    }

    protected OrgDO getOrgItems(String orgTableName, String code, Date versionData) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(orgTableName);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setVersionDate(versionData);
        orgDTO.setCode(code);
        orgDTO.setOrderBy(Collections.emptyList());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.BASIC);
        PageVO list = this.orgDataClient.list(orgDTO);
        if (list != null && list.getRows() != null) {
            return list.getRows().stream().findFirst().orElse(null);
        }
        return null;
    }

    protected String getTableName(String entityId) {
        Assert.isTrue((BaseDataAdapterUtil.isBaseData(entityId) || OrgAdapterUtil.isOrg(entityId) ? 1 : 0) != 0, (String)("\u4e0d\u662f\u5b9e\u4f53ID\uff1a" + entityId));
        return entityId.substring(0, entityId.lastIndexOf("@"));
    }
}

