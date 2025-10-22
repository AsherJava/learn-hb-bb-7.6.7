/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$EventType
 *  com.jiuqi.va.domain.org.ZB
 *  com.jiuqi.va.event.OrgEvent
 */
package com.jiuqi.nr.formtype.org.extend;

import com.jiuqi.nr.formtype.org.extend.FormTypeOrgDataExtendService;
import com.jiuqi.nr.formtype.service.IFormTypeApplyService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.ZB;
import com.jiuqi.va.event.OrgEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FormTypeOrgDataEventListener
implements ApplicationListener<OrgEvent> {
    @Autowired
    private IFormTypeApplyService iFormTypeApplyService;
    @Autowired
    private FormTypeOrgDataExtendService formTypeOrgDataExtendService;

    @Override
    public void onApplicationEvent(OrgEvent event) {
        if (OrgDataOption.EventType.DELETE == event.getEventType()) {
            return;
        }
        OrgDTO orgDTO = event.getOrgDTO();
        OrgDTO orgOldDTO = event.getOrgOldDTO();
        if (Boolean.TRUE.equals(orgDTO.get((Object)"ignoreCategoryAdd"))) {
            return;
        }
        if (Boolean.TRUE.equals(orgDTO.get((Object)"_ORG_EXTEND_BATCH_ADD"))) {
            return;
        }
        if (orgDTO.containsKey((Object)"importstate")) {
            return;
        }
        if (!this.iFormTypeApplyService.enableNrFormTypeMgr()) {
            return;
        }
        if (this.iFormTypeApplyService.isMdOrg(event.getOrgCategoryDO().getName())) {
            return;
        }
        ZB formTypeZb = this.iFormTypeApplyService.getFormTypeZb(event.getOrgCategoryDO().getName());
        if (null == formTypeZb) {
            return;
        }
        if (OrgDataOption.EventType.ADD == event.getEventType()) {
            this.formTypeOrgDataExtendService.doAddExtends(formTypeZb, (OrgDO)orgDTO);
        } else if (OrgDataOption.EventType.UPDATE == event.getEventType()) {
            this.formTypeOrgDataExtendService.doUpdateExtends(formTypeZb, (OrgDO)orgDTO, (OrgDO)orgOldDTO);
        }
    }
}

