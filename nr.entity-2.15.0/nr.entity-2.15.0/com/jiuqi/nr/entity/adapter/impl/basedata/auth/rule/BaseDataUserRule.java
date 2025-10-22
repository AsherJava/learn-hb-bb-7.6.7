/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.BaseAuthRule;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class BaseDataUserRule
extends BaseAuthRule {
    public String getName() {
        return "BD_BELONG";
    }

    public String getTitle() {
        return "\u7528\u6237\u5173\u8054\u7684\u57fa\u7840\u6570\u636e";
    }

    @Override
    protected Set<String> loadAuthCodes(BaseDataAuthFindDTO findDTO) {
        return this.iBaseDataRelationService.getObjectCodesByUser(findDTO);
    }

    @Override
    protected Set<String> loadAuthUsers(BaseDataAuthFindDTO findDTO) {
        findDTO.getParam().setAuthType(BaseDataOption.AuthType.NONE);
        return this.iBaseDataRelationService.getUserIds(findDTO.getParam());
    }
}

