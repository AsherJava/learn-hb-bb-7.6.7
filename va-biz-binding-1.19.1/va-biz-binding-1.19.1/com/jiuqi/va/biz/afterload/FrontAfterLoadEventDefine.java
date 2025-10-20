/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.AfterLoadDefineImpl;
import com.jiuqi.va.biz.afterload.EventItem;
import com.jiuqi.va.biz.afterload.EventItemManage;
import com.jiuqi.va.biz.afterload.EventOption;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FrontAfterLoadEventDefine
extends FrontPluginDefine {
    private List<EventOption> options = new ArrayList<EventOption>();
    private transient AfterLoadDefineImpl afterLoadDefine;

    public FrontAfterLoadEventDefine(FrontModelDefine frontModelDefine, PluginDefine pluginDefine) {
        super(frontModelDefine, pluginDefine);
        this.afterLoadDefine = (AfterLoadDefineImpl)pluginDefine;
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    public List<EventOption> getOptions() {
        return this.options;
    }

    public void setOptions(List<EventOption> options) {
        this.options = options;
    }

    public AfterLoadDefineImpl getAfterLoadDefine() {
        return this.afterLoadDefine;
    }

    public void setAfterLoadDefine(AfterLoadDefineImpl afterLoadDefine) {
        this.afterLoadDefine = afterLoadDefine;
    }

    @Override
    protected Map<String, Set<String>> getTableFields(ModelDefine modelDefine) {
        HashMap<String, Set<String>> fields = new HashMap<String, Set<String>>();
        EventItemManage eventItemManage = (EventItemManage)ApplicationContextRegister.getBean(EventItemManage.class);
        for (EventItem eventItem : eventItemManage.getEventItems()) {
            Map<String, Set<String>> optionFields = eventItem.getTriggerFields(modelDefine);
            if (optionFields == null) continue;
            fields.putAll(optionFields);
        }
        return fields;
    }
}

