/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.unit.treestore.uselector.bean;

import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class USFilterTemplateImpl
implements USFilterTemplate {
    private static final Logger log = LoggerFactory.getLogger(USFilterTemplateImpl.class);
    private JSONObject template;

    public USFilterTemplateImpl(String template) {
        this.toTemplate(template);
    }

    @Override
    public String toJSON() {
        if (null != this.template) {
            return this.template.toString();
        }
        return null;
    }

    @Override
    public USFilterTemplate toTemplate(String json) {
        this.template = JavaBeanUtils.toJSONObject((String)json);
        return this;
    }

    @Override
    public JSONObject getTemplate() {
        return this.template;
    }
}

