/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum
 *  com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 */
package com.jiuqi.gcreport.org.impl.util.bean;

import com.jiuqi.gcreport.org.api.enums.EventChangeTypeEnum;
import com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.init.GcTableConstructor;
import com.jiuqi.gcreport.org.impl.util.base.GcOrgBaseModel;
import com.jiuqi.gcreport.org.impl.util.bean.GcOrgModelProvider;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class GcOrgOtherModel
extends GcOrgBaseModel
implements ApplicationEventPublisherAware,
InitializingBean {
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private GcTableConstructor initService;
    @Autowired
    private FGcOrgTypeVersionDao typeVerDao;

    GcOrgOtherModel() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GcOrgModelProvider.setGcOrgOtherModel(this);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public void publishEvent(EventChangeTypeEnum changeType, OrgTypeVO type) {
        GcOrgTypeChangeEvent event = new GcOrgTypeChangeEvent(changeType, type);
        this.eventPublisher.publishEvent((ApplicationEvent)event);
    }

    public void publishEvent(EventChangeTypeEnum changeType, OrgTypeVO type, List<OrgVersionVO> datas) {
        GcOrgVersionChangeEvent event = new GcOrgVersionChangeEvent(changeType, type, datas);
        this.eventPublisher.publishEvent((ApplicationEvent)event);
    }

    public void publishTable(OrgCategoryDO type) {
        type.setExtinfo(this.initService.getTempFields());
    }

    public void publishTable(OrgCategoryDO type, String temple) throws Exception {
        type.setExtinfo(temple);
    }

    public FGcOrgTypeVersionDao getOrgTypeDao() {
        return this.typeVerDao;
    }
}

