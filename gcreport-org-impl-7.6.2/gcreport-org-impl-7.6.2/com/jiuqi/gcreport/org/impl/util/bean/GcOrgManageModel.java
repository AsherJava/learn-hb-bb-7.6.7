/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.gcreport.org.api.vo.OrgToJsonVO
 *  com.jiuqi.va.domain.org.OrgDTO
 */
package com.jiuqi.gcreport.org.impl.util.bean;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgBaseModel;
import com.jiuqi.gcreport.org.impl.util.base.OrgParse;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.va.domain.org.OrgDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class GcOrgManageModel
extends GcOrgBaseModel
implements ApplicationEventPublisherAware,
InitializingBean {
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private FGcOrgEditService editService;

    GcOrgManageModel() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GcOrgModelProvider.setGcOrgManageModel(this);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishEvent(EventChangeTypeEnum changeType, GcOrgBaseParam param, OrgToJsonVO org) {
        GcOrgDataItemChangeEvent event = new GcOrgDataItemChangeEvent(param.getType().getName());
        this.eventPublisher.publishEvent(event);
    }

    public FGcOrgEditService getOrgEditService() {
        return this.editService;
    }

    public OrgDTO convertOrg(OrgToJsonVO vo, GcOrgBaseParam param) throws RuntimeException {
        try {
            return OrgParse.toVaJsonVo(vo, param);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

