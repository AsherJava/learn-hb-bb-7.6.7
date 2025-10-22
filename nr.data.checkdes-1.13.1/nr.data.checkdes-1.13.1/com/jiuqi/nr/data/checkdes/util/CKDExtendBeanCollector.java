/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.checkdes.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.checkdes.service.ICKDImpValidator;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class CKDExtendBeanCollector {
    private final Map<String, ICKDImpValidator> ckdImpValidatorMap = new HashMap<String, ICKDImpValidator>();

    public CKDExtendBeanCollector(Map<String, ICKDImpValidator> beans) {
        for (Map.Entry<String, ICKDImpValidator> e : beans.entrySet()) {
            if (StringUtils.isEmpty((String)e.getValue().name())) {
                throw new IllegalArgumentException("ICKDImpValidator.name() must not return null or \"\"!-check your implementation:" + e.getKey());
            }
            if (this.ckdImpValidatorMap.containsKey(e.getValue().name())) {
                throw new IllegalArgumentException(e.getValue().name() + ":repeated ICKDImpValidator.name(),try a new name! ");
            }
            this.ckdImpValidatorMap.put(e.getValue().name(), e.getValue());
        }
    }

    public ICKDImpValidator getCKDImpValidatorByName(String ckdImpValidatorName) {
        if (this.ckdImpValidatorMap.containsKey(ckdImpValidatorName)) {
            return this.ckdImpValidatorMap.get(ckdImpValidatorName);
        }
        throw new IllegalArgumentException("can not find ICKDImpValidator by your name:" + ckdImpValidatorName);
    }
}

