/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.impl.countersign.group;

import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.IActorStrategyCountSign;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.impl.countersign.group.IQueryGroupCount;
import com.jiuqi.nr.bpm.impl.countersign.group.QueryGroupNumByRole;
import com.jiuqi.nr.bpm.impl.countersign.group.QueryGroupNumByUser;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryGroupFactory {
    private static final String ROLE = "role";
    private static final String USER = "user";
    @Autowired
    private List<IActorStrategyCountSign> actorStrategyCountSigns;
    @Autowired
    private List<ActorStrategy> actorStrategy;
    @Autowired
    private CustomWorkFolwService customWorkFolwServiceImpl;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Resource
    private WorkflowSettingService settingService;

    public IQueryGroupCount getQueryGroupCount(String userOrRole) {
        IQueryGroupCount queryGroupCount = null;
        if (ROLE.equals(userOrRole)) {
            queryGroupCount = new QueryGroupNumByRole(this.actorStrategyCountSigns, this.customWorkFolwServiceImpl, this.settingService);
        } else if (USER.equals(userOrRole)) {
            queryGroupCount = new QueryGroupNumByUser(this.actorStrategy, this.customWorkFolwServiceImpl, this.systemIdentityService, this.settingService);
        }
        return queryGroupCount;
    }

    public IQueryGroupCount getQueryGroupCount(boolean roleType) {
        IQueryGroupCount queryGroupCount = null;
        queryGroupCount = roleType ? new QueryGroupNumByRole(this.actorStrategyCountSigns, this.customWorkFolwServiceImpl, this.settingService) : new QueryGroupNumByUser(this.actorStrategy, this.customWorkFolwServiceImpl, this.systemIdentityService, this.settingService);
        return queryGroupCount;
    }
}

