/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelContextImpl
 */
package com.jiuqi.va.bill.impl;

import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BillContextImpl
extends ModelContextImpl
implements BillContext {
    private static final List<String> DEFAULT_KEYS = Arrays.asList("TenantName", "UnitCode", "UserCode", "BizDate");
    private static final String PREFIXKEY = "X--";

    @Override
    public String getVerifyCode() {
        return (String)this.map.get("VerifyCode");
    }

    public void setVerifyCode(String value) {
        this.map.put("VerifyCode", value);
    }

    @Override
    public void setContextValue(String key, Object value) {
        if (DEFAULT_KEYS.contains(key)) {
            return;
        }
        this.map.put(key, value);
    }

    @Override
    public void setContextValue(Map<String, Object> contextValueMap) {
        if (contextValueMap != null) {
            contextValueMap.keySet().forEach(key -> this.setContextValue(PREFIXKEY + key, contextValueMap.get(key)));
        }
    }

    @Override
    public Map<String, Object> getCustomContext() {
        Set keySet = this.map.keySet();
        HashMap<String, Object> customContext = new HashMap<String, Object>(16);
        keySet.forEach(s -> {
            if (s.startsWith(PREFIXKEY)) {
                customContext.put((String)s, this.map.get(s));
            }
        });
        return customContext;
    }

    @Override
    public Object getContextValue(String key) {
        return this.map.get(key);
    }

    @Override
    public boolean isDisableVerify() {
        return this.map.getOrDefault("disableVerify", false);
    }

    public void setDisableVerify(boolean value) {
        this.map.put("disableVerify", value);
    }

    @Override
    public String getSchemeCode() {
        return (String)this.map.get("SchemeCode");
    }

    public void setSchemeCode(String schemeCode) {
        this.map.put("SchemeCode", schemeCode);
    }

    @Override
    public boolean isDisable18n() {
        return this.map.getOrDefault("disableI18n", false);
    }

    public void setDisable18n(boolean value) {
        this.map.put("disableI18n", value);
    }
}

