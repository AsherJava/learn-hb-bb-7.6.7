/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent
 *  com.jiuqi.np.authz2.event.EntityIdentityGrantedEvent
 *  com.jiuqi.np.authz2.event.EntityIdentityRevokedEvent
 *  com.jiuqi.np.authz2.event.RoleRelationIdentityEvent
 *  com.jiuqi.np.authz2.event.RoleRelationIdentityEvent$RoleRelationIdentityChangeItem
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.user.event.UserOrgChangedEvent
 */
package com.jiuqi.gcreport.org.impl.cache.listener;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent;
import com.jiuqi.gcreport.org.impl.cache.listener.GcOrgCacheMessageSubscriber;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgCacheManage;
import com.jiuqi.np.authz2.event.EntityIdentityGrantedEvent;
import com.jiuqi.np.authz2.event.EntityIdentityRevokedEvent;
import com.jiuqi.np.authz2.event.RoleRelationIdentityEvent;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.user.event.UserOrgChangedEvent;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcOrgCacheListener {
    @Autowired
    private GcOrgCacheManage manager;

    @EventListener
    public void onIdentityRoleChanged(EntityIdentityGrantedEvent event) {
        GcOrgDataAuthzChangeEvent gcEvent = new GcOrgDataAuthzChangeEvent();
        event.getChangedItemIterator().forEachRemaining(vo -> {
            if (!StringUtils.isEmpty(vo.getIdentityId())) {
                gcEvent.addUser(vo.getIdentityId());
            }
            if (!StringUtils.isEmpty(vo.getEntityTableKey())) {
                IDataDefinitionRuntimeController runtimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
                try {
                    TableDefine tableDefine = runtimeController.queryTableDefine(vo.getEntityTableKey());
                    gcEvent.addType(tableDefine.getCode());
                }
                catch (Exception e) {
                    gcEvent.addType("MD_ORG");
                    e.printStackTrace();
                }
            }
        });
        this.publishRefreshMessage((GcOrgDataCacheBaseEvent)gcEvent);
    }

    @EventListener
    public void onPrivilegeChanged(EntityIdentityRevokedEvent event) {
        GcOrgDataAuthzChangeEvent gcEvent = new GcOrgDataAuthzChangeEvent();
        event.getChangedItemIterator().forEachRemaining(vo -> {
            if (StringUtils.hasText(vo.getIdentityId())) {
                gcEvent.addUser(vo.getIdentityId());
            }
            if (StringUtils.hasText(vo.getEntityTableKey())) {
                gcEvent.addType(vo.getEntityTableKey());
            }
        });
        this.publishRefreshMessage((GcOrgDataCacheBaseEvent)gcEvent);
    }

    @EventListener
    public void onPrivilegeChanged(UserOrgChangedEvent event) {
        GcOrgDataAuthzChangeEvent gcEvent = new GcOrgDataAuthzChangeEvent();
        gcEvent.addUser(event.getUserId());
        this.publishRefreshMessage((GcOrgDataCacheBaseEvent)gcEvent);
    }

    @EventListener
    public void onOrgDataChanged(GcOrgDataItemChangeEvent event) {
        if (!StringUtils.isEmpty(event.getCacheName())) {
            this.publishRefreshMessage((GcOrgDataCacheBaseEvent)event);
        }
    }

    @EventListener
    public void onOrgDataChanged(GcOrgDataCacheClearEvent event) {
        this.publishRefreshMessage((GcOrgDataCacheBaseEvent)event);
    }

    public void publishRefreshMessage(GcOrgDataCacheBaseEvent event) {
        new GcOrgCacheMessageSubscriber().onMessage(this.manager, event);
    }

    @EventListener
    public void onOrgTypeChanged(GcOrgTypeChangeEvent event) {
        new GcOrgCacheMessageSubscriber().onTypeVersionMessage(this.manager, (GcOrgBaseEvent<?>)event);
    }

    @EventListener
    public void onOrgVersionChanged(GcOrgVersionChangeEvent event) {
        new GcOrgCacheMessageSubscriber().onTypeVersionMessage(this.manager, (GcOrgBaseEvent<?>)event);
    }

    @EventListener(value={RoleRelationIdentityEvent.class})
    public void RoleRelationIdentityEvent(RoleRelationIdentityEvent event) {
        List changedItems = event.getChangedItems();
        List<String> userIds = changedItems.stream().map(RoleRelationIdentityEvent.RoleRelationIdentityChangeItem::getIdentityId).collect(Collectors.toList());
        GcOrgDataAuthzChangeEvent gcEvent = new GcOrgDataAuthzChangeEvent();
        userIds.forEach(arg_0 -> ((GcOrgDataAuthzChangeEvent)gcEvent).addUser(arg_0));
        gcEvent.addType("MD_ORG");
        this.publishRefreshMessage((GcOrgDataCacheBaseEvent)gcEvent);
    }
}

