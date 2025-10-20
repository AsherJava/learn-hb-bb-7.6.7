/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.AfterLoadEvent;
import com.jiuqi.va.biz.afterload.EventItemManage;
import com.jiuqi.va.biz.afterload.EventOption;
import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AfterLoadEventImpl
extends PluginImpl
implements AfterLoadEvent {
    private static final Logger logger = LoggerFactory.getLogger(AfterLoadEventImpl.class);
    private List<EventOption> options = new ArrayList<EventOption>();

    @Override
    public Model getModel() {
        return super.getModel();
    }

    @Override
    public PluginDefine getDefine() {
        return super.getDefine();
    }

    @Override
    public void initEvents() {
        EventItemManage eventItemManage = (EventItemManage)ApplicationContextRegister.getBean(EventItemManage.class);
        ArrayList<EventOption> eventOptions = new ArrayList<EventOption>();
        Model model = this.getModel();
        eventItemManage.getEventItems().forEach(eventItem -> {
            EventOption option;
            if (eventItem.enable(model) && (option = eventItem.execute(model)) != null) {
                eventOptions.add(option);
            }
        });
        this.setOptions(eventOptions);
    }

    public List<EventOption> getOptions() {
        return this.options;
    }

    public void setOptions(List<EventOption> options) {
        this.options = options;
    }
}

