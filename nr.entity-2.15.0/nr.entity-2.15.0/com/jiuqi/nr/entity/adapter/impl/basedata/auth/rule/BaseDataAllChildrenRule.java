/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.BaseAuthRule;
import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.BaseDataUserRule;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BaseDataAllChildrenRule
extends BaseAuthRule {
    @Autowired
    private BaseDataUserRule baseDataUserRule;

    public String getName() {
        return "BD_ALL_CHILDREN";
    }

    public String getTitle() {
        return "\u7528\u6237\u5173\u8054\u57fa\u7840\u6570\u636e\u7684\u6240\u6709\u4e0b\u7ea7";
    }

    @Override
    protected Set<String> loadAuthCodes(BaseDataAuthFindDTO findDTO) {
        HashSet<String> codeSet = new HashSet<String>();
        Set<String> objectCodes = this.baseDataUserRule.hasAuth(findDTO);
        if (CollectionUtils.isEmpty(objectCodes)) {
            return codeSet;
        }
        for (String parentCode : objectCodes) {
            Set<String> strings = this.baseDataChildrenWithUserId(findDTO.getParam(), parentCode, BaseDataOption.QueryChildrenType.ALL_CHILDREN);
            codeSet.addAll(strings);
        }
        return codeSet;
    }

    @Override
    protected Set<String> loadAuthUsers(BaseDataAuthFindDTO findDTO) {
        BaseDataDTO param = findDTO.getParam();
        findDTO.getParam().setAuthType(BaseDataOption.AuthType.NONE);
        BaseDataDO baseDataItem = this.getBaseDataItem(param.getTableName(), param.getObjectcode(), param.getVersionDate());
        HashSet<String> userRef = new HashSet<String>();
        if (baseDataItem != null) {
            userRef.addAll(this.iBaseDataRelationService.getUserIds(param));
            String parents = baseDataItem.getParents();
            if (StringUtils.hasLength(parents)) {
                String[] parent = parents.split("/");
                for (int i = parent.length - 1; i >= 0; --i) {
                    String per = parent[i];
                    if (per.equals(param.getObjectcode())) continue;
                    param.setObjectcode(per);
                    userRef.addAll(this.iBaseDataRelationService.getUserIds(param));
                }
            }
        }
        return userRef;
    }
}

