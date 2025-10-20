/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.IBizModelExtFieldDeclare;
import com.jiuqi.common.base.BusinessRuntimeException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizModelExtFieldDeclareGather {
    private Map<String, IBizModelExtFieldDeclare<?>> bizModelExtFieldDeclareMap;

    public BizModelExtFieldDeclareGather(@Autowired List<IBizModelExtFieldDeclare<?>> bizModelExtFieldDeclareList) {
        if (bizModelExtFieldDeclareList == null || bizModelExtFieldDeclareList.isEmpty()) {
            return;
        }
        this.bizModelExtFieldDeclareMap = new HashMap(bizModelExtFieldDeclareList.size());
        for (IBizModelExtFieldDeclare<?> bizModelExtFieldDeclare : bizModelExtFieldDeclareList) {
            this.bizModelExtFieldDeclareMap.put(bizModelExtFieldDeclare.getCode(), bizModelExtFieldDeclare);
        }
    }

    public IBizModelExtFieldDeclare<?> getBizModelExtFieldDeclareByCode(String code) {
        if (this.bizModelExtFieldDeclareMap == null) {
            throw new BusinessRuntimeException("\u672a\u6536\u96c6\u5230\u4efb\u4f55\u4e1a\u52a1\u6a21\u578b\u62d3\u5c55\u5b57\u6bb5\u58f0\u660e");
        }
        IBizModelExtFieldDeclare<?> iBizModelExtFieldDeclare = this.bizModelExtFieldDeclareMap.get(code);
        if (iBizModelExtFieldDeclare == null) {
            throw new BusinessRuntimeException(String.format("\u672a\u627e\u5230\u4ee3\u7801\u4e3a\u3010%s\u3011\u7684\u4e1a\u52a1\u6a21\u578b\u62d3\u5c55\u5b57\u6bb5\u58f0\u660e", code));
        }
        return iBizModelExtFieldDeclare;
    }
}

