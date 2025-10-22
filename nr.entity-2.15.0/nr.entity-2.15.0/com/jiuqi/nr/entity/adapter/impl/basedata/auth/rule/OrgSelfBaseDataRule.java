/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.rule.BaseAuthRule;
import com.jiuqi.va.domain.basedata.BaseDataAuthFindDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.user.UserDO;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrgSelfBaseDataRule
extends BaseAuthRule {
    public String getName() {
        return "ORG_BELONG";
    }

    public String getTitle() {
        return "\u7528\u6237\u6240\u5c5e\u673a\u6784\u5173\u8054\u7684\u57fa\u7840\u6570\u636e";
    }

    @Override
    protected Set<String> loadAuthCodes(BaseDataAuthFindDTO findDTO) {
        BaseDataDTO param = findDTO.getParam();
        UserDO user = findDTO.getUser();
        String unitCode = user.getUnitcode();
        if (!StringUtils.hasLength(unitCode)) {
            return new HashSet<String>();
        }
        return this.iBaseDataRelationService.getObjectCodesByCodes(param, Collections.singleton(unitCode));
    }

    @Override
    protected Set<String> loadAuthUsers(BaseDataAuthFindDTO findDTO) {
        Object orgCodeObj;
        BaseDataDTO param = findDTO.getParam();
        findDTO.getParam().setAuthType(BaseDataOption.AuthType.NONE);
        BaseDataDO baseDataItem = this.getBaseDataItem(param.getTableName(), param.getObjectcode(), param.getVersionDate());
        if (baseDataItem != null && (orgCodeObj = baseDataItem.get((Object)"masterid")) != null) {
            return this.baseDataAuthQueryDao.getRelateUserIDByOrg(Collections.singletonList(orgCodeObj.toString()));
        }
        return Collections.emptySet();
    }
}

