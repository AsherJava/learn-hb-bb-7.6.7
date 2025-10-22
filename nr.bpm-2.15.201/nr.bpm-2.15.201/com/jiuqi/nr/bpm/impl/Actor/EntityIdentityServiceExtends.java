/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.OrgIdentityService
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.user.feign.client.NvwaUserClient
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.OrgIdentityService;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityIdentityServiceExtends {
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private NvwaUserClient nvwaUserClient;
    @Autowired
    private OrgIdentityService orgIdentityService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IdentityService identityService;

    public Collection<String> getGrantedIdentityKeysWithEntity(String entityKeyData) {
        return this.getUserIdsByEntityData(entityKeyData);
    }

    public Boolean isGrantedWithEntity(String formSchemeKey, EntityViewDefine entityDefine, String entityKeyData, String identityId, String period) {
        Date queryVersionStartDate = null;
        Date queryVersionDate = null;
        if (period != null) {
            try {
                FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(formScheme.getDateTime());
                Date[] periodDateRegion = periodProvider.getPeriodDateRegion(period);
                queryVersionStartDate = periodDateRegion[0];
                queryVersionDate = periodDateRegion[1];
            }
            catch (ParseException formScheme) {
                // empty catch block
            }
        }
        try {
            return this.entityAuthorityService.canReadEntity(entityDefine.getEntityId(), entityKeyData, queryVersionStartDate, queryVersionDate);
        }
        catch (UnauthorizedEntityException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Set<String> getUserIdsByEntityData(String entityCode) {
        HashSet<String> Identitys = new HashSet<String>();
        List userIdAndIdentitys = this.identityService.getUserIdAndIdentityMappUserIdByOrgCode(entityCode);
        Identitys.addAll(userIdAndIdentitys);
        Collection grantedIdentitys = this.orgIdentityService.getUserIdAndIdentityMappUserIdByOrgCode(entityCode);
        for (String identity : grantedIdentitys) {
            Identitys.add(identity);
        }
        return Identitys;
    }
}

