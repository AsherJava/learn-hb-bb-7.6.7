/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.EntityUtils
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.entity.adapter.impl.org.util.OrgAdapterUtil;
import com.jiuqi.nr.entity.component.currency.OrgCurrencyService;
import com.jiuqi.nr.entity.component.currency.dto.CurrencyCheckDTO;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class IEntityDefineAssistImpl
implements IEntityDefineAssist {
    @Autowired
    private OrgCurrencyService orgCurrencyService;

    @Override
    public boolean existCurrencyAttributes(String entityId) {
        boolean isOrg = OrgAdapterUtil.isOrg(entityId);
        if (!isOrg) {
            return false;
        }
        String orgCategory = EntityUtils.getId((String)entityId);
        CurrencyCheckDTO currencyCheckDTO = this.orgCurrencyService.existCurrencyAttribute(orgCategory);
        return CollectionUtils.isEmpty(currencyCheckDTO.getFixAttributes()) && CollectionUtils.isEmpty(currencyCheckDTO.getGeneratorAttributes());
    }
}

