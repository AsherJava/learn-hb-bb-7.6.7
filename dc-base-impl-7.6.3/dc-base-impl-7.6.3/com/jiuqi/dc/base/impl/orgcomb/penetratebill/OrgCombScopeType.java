/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.base.common.penetratebill.IScopeType
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 */
package com.jiuqi.dc.base.impl.orgcomb.penetratebill;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.penetratebill.IScopeType;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.base.impl.orgcomb.service.OrgCombDefineService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrgCombScopeType
implements IScopeType {
    @Autowired
    private OrgCombDefineService orgCombDefineService;

    public String getCode() {
        return "ORGCOMB";
    }

    public String getName() {
        return "\u5355\u4f4d\u7ec4\u5408";
    }

    public String getOrdinal() {
        return "20";
    }

    public boolean match(String unitCode, String scopeValue) {
        BaseDataShowVO data = (BaseDataShowVO)JsonUtils.readValue((String)scopeValue, (TypeReference)new TypeReference<BaseDataShowVO>(){});
        List<String> orgCombCodes = this.orgCombDefineService.findOrgCombCodes(unitCode);
        return !CollectionUtils.isEmpty(orgCombCodes) && orgCombCodes.contains(data.getCode());
    }
}

