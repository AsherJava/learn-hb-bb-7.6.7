/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$EventType
 *  com.jiuqi.va.event.OrgEvent
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.gcreport.org.impl.callback.GcAutoCalcFieldModifyService;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.event.OrgEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component(value="OrgDataOperateListener")
public class OrgDataOperateListener
implements ApplicationListener<OrgEvent> {
    private static final Logger logger = LoggerFactory.getLogger(OrgDataOperateListener.class);
    @Autowired
    private GcAutoCalcFieldModifyService gcAutoCalcFieldModifyService;

    @Override
    public void onApplicationEvent(OrgEvent orgEvent) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return;
        }
        OrgDTO newOrgDTO = orgEvent.getOrgDTO();
        if (newOrgDTO.containsKey((Object)"_ORG_EXTEND_BATCH_ADD")) {
            return;
        }
        String parents = newOrgDTO.getParents();
        String tableName = orgEvent.getOrgCategoryDO().getName();
        if (!StringUtils.hasText(parents)) {
            return;
        }
        String gcParents = null;
        if ((orgEvent.getEventType().equals((Object)OrgDataOption.EventType.UPDATE) || orgEvent.getEventType().equals((Object)OrgDataOption.EventType.ADD)) && StringUtils.hasText(parents) && !"-".equals(parents)) {
            GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
            gcParents = InspectOrgUtils.getGcParentsByOldParents(parents, gcOrgCodeConfig);
        }
        Object existParents = newOrgDTO.get((Object)"GCPARENTS".toLowerCase());
        if (!ObjectUtils.isEmpty(gcParents) && existParents != null && gcParents.equalsIgnoreCase(existParents.toString())) {
            return;
        }
        this.gcAutoCalcFieldModifyService.updateGcParents(tableName, gcParents, newOrgDTO);
    }
}

