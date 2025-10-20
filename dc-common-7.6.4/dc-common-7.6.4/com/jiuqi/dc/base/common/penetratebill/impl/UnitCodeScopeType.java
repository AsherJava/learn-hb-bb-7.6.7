/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.dc.base.common.penetratebill.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.base.common.penetratebill.IScopeType;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UnitCodeScopeType
implements IScopeType {
    @Override
    public String getCode() {
        return "UNITCODE";
    }

    @Override
    public String getName() {
        return "\u7ec4\u7ec7\u673a\u6784";
    }

    @Override
    public String getOrdinal() {
        return "30";
    }

    @Override
    public boolean match(String unitCode, String scopeValue) {
        List dataList = (List)JsonUtils.readValue((String)scopeValue, (TypeReference)new TypeReference<List<BaseDataShowVO>>(){});
        if (!CollectionUtils.isEmpty((Collection)dataList)) {
            List matchingList = dataList.stream().map(BaseDataShowVO::getCode).collect(Collectors.toList());
            return matchingList.contains(unitCode);
        }
        return false;
    }
}

