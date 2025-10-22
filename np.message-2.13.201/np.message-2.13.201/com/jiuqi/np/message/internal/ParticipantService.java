/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.Identity
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.authz2.service.IdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 */
package com.jiuqi.np.message.internal;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.Identity;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.authz2.service.IdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@Deprecated
public class ParticipantService {
    final RoleService roleService;
    final IdentityService identityService;
    final EntityIdentityService entityLinkService;

    public ParticipantService(RoleService roleService, IdentityService identityService, EntityIdentityService entityLinkService) {
        this.roleService = roleService;
        this.identityService = identityService;
        this.entityLinkService = entityLinkService;
    }

    public List<String> findParticipantId(String userId) {
        LinkedList<String> participantIds = new LinkedList<String>();
        participantIds.add(userId);
        List identityList = this.identityService.getByUser(userId);
        for (Identity identity : identityList) {
            participantIds.addAll(this.roleService.getByIdentity(identity.getId()).stream().map(Identifiable::getId).collect(Collectors.toList()));
            participantIds.addAll(this.entityLinkService.getGrantedEntityKeys(identity.getId()).keySet());
        }
        return participantIds;
    }
}

