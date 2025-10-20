/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.BizType
 */
package com.jiuqi.va.workflow.config;

import com.jiuqi.va.domain.workflow.BizType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component(value="VaWorkflowBizModuleConfig")
@ConfigurationProperties(prefix="workflow-biztype")
public class BizTypeConfig {
    private List<BizType> types;
    private Map<String, BizType> defaults = new HashMap<String, BizType>();

    public Map<String, BizType> getDefaults() {
        return this.defaults;
    }

    public void setDefaults(Map<String, BizType> defaults) {
        this.defaults = defaults;
    }

    public List<BizType> getTypes() {
        if (!CollectionUtils.isEmpty(this.types)) {
            return this.types;
        }
        if (!CollectionUtils.isEmpty(this.defaults)) {
            this.types = new ArrayList<BizType>(this.defaults.values());
        }
        return this.types;
    }

    public void setTypes(List<BizType> types) {
        this.types = types;
    }
}

