/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.user.UserDO
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.BaseAuthRule;
import com.jiuqi.nr.entity.adapter.impl.basedata.util.BaseDataAdapterUtil;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class OrgAllChildrenBaseDataRule
extends BaseAuthRule {
    @Autowired
    protected IEntityMetaService entityMetaService;

    public String getName() {
        return "ORG_ALL_CHILDREN";
    }

    public String getTitle() {
        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u7684\u6240\u6709\u4e0b\u7ea7\u673a\u6784\u5173\u8054\u7684\u57fa\u7840\u6570\u636e";
    }

    @Override
    protected Set<String> loadAuthCodes(BaseDataAuthFindDTO findDTO) {
        UserDO user = findDTO.getUser();
        String unitCode = user.getUnitcode();
        HashSet<String> empty = new HashSet<String>();
        if (!StringUtils.hasLength(unitCode)) {
            return empty;
        }
        BaseDataDTO param = findDTO.getParam();
        String orgEntityId = this.getRefOrgEntityId(param);
        if (orgEntityId == null) {
            return empty;
        }
        String orgTableName = this.getTableName(orgEntityId);
        Set<String> allOrgCodes = this.orgDataWithParentCode(param, orgTableName, unitCode, this.getQueryChildrenType());
        if (CollectionUtils.isEmpty(allOrgCodes)) {
            return empty;
        }
        return this.iBaseDataRelationService.getObjectCodesByCodes(param, allOrgCodes);
    }

    protected OrgDataOption.QueryChildrenType getQueryChildrenType() {
        return OrgDataOption.QueryChildrenType.ALL_CHILDREN;
    }

    protected String getRefOrgEntityId(BaseDataDTO param) {
        String entityId = BaseDataAdapterUtil.getEntityIdByBaseDataCode(param.getTableName());
        List<IEntityRefer> entityRefer = this.entityMetaService.getEntityRefer(entityId);
        String orgEntityId = null;
        for (IEntityRefer iEntityRefer : entityRefer) {
            if (!"MASTERID".equals(iEntityRefer.getOwnField()) && !"masterid".equals(iEntityRefer.getOwnField())) continue;
            orgEntityId = iEntityRefer.getReferEntityId();
            break;
        }
        return orgEntityId;
    }

    @Override
    protected Set<String> loadAuthUsers(BaseDataAuthFindDTO findDTO) {
        String orgTableName;
        OrgDO orgItems;
        String orgEntityId;
        Object orgCodeObj;
        BaseDataDTO param = findDTO.getParam();
        findDTO.getParam().setAuthType(BaseDataOption.AuthType.NONE);
        BaseDataDO baseDataItem = this.getBaseDataItem(param.getTableName(), param.getObjectcode(), param.getVersionDate());
        if (baseDataItem != null && (orgCodeObj = baseDataItem.get((Object)"masterid")) != null && (orgEntityId = this.getRefOrgEntityId(param)) != null && (orgItems = this.getOrgItems(orgTableName = this.getTableName(orgEntityId), orgCodeObj.toString(), findDTO.getParam().getVersionDate())) != null) {
            Set<String> orgCodes = this.getParentCodes(orgItems);
            return this.baseDataAuthQueryDao.getRelateUserIDByOrg(orgCodes);
        }
        return Collections.emptySet();
    }

    protected Set<String> getParentCodes(OrgDO orgItems) {
        String parents = orgItems.getParents();
        HashSet<String> orgCodes = new HashSet<String>();
        String selfCode = orgItems.getCode();
        if (StringUtils.hasLength(parents)) {
            orgCodes.addAll(Arrays.asList(parents.split("/")));
        }
        orgCodes.remove(selfCode);
        return orgCodes;
    }
}

