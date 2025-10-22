/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Identifiable
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.authz2.service.RoleService
 */
package com.jiuqi.nr.message.manager;

import com.jiuqi.np.authz2.Identifiable;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.authz2.service.RoleService;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class ParticipantHelper {
    private final RoleService roleService;
    private final EntityIdentityService entityLinkService;

    public ParticipantHelper(RoleService roleService, EntityIdentityService entityLinkService) {
        this.roleService = roleService;
        this.entityLinkService = entityLinkService;
    }

    public List<String> collectRelatedParticipantId(String userId) {
        LinkedList<String> participantIds = new LinkedList<String>();
        participantIds.add("FFFFFFFF-FFFF-FFFF-MMMM-FFFFFFFFFFFF");
        participantIds.add(userId);
        participantIds.addAll(this.roleService.getByIdentity(userId).stream().map(Identifiable::getId).collect(Collectors.toList()));
        participantIds.addAll(this.entityLinkService.getGrantedEntityKeys(userId).keySet());
        return participantIds;
    }
}

