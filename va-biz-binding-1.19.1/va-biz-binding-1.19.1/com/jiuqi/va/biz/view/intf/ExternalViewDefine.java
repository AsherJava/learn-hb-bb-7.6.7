/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.intf;

import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.ruler.intf.Formula;
import java.util.List;
import java.util.Map;

public interface ExternalViewDefine
extends NamedElement {
    public Map<String, Object> getTemplate(String var1);

    public List<Formula> getFormulas(String var1);

    default public Long getVer(String definecode) {
        return null;
    }

    public List<Map<String, Object>> getSchemes(String var1);

    default public Map<String, Object> getTemplate(String definecode, String schemeCode) {
        return this.getTemplate(definecode);
    }

    default public Object getPlugin(String definecode, String pluginCode) {
        return null;
    }
}

