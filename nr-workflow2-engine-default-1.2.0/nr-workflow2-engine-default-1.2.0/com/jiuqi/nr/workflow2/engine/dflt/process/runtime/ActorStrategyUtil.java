/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectSet;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKeyCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value=false)
@Component
public class ActorStrategyUtil
implements InitializingBean {
    private static ActorStrategyUtil INSTACNE;
    @Autowired
    private IActorStrategyFactory actorStrategyFactory;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SystemUserService systemUserService;

    public static ActorStrategyUtil getInstance() {
        return INSTACNE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        INSTACNE = this;
    }

    public boolean actorIsMatch(Collection<IActorStrategy> actorStrategies, IActor actor, RuntimeBusinessKey businessKey) {
        for (IActorStrategy actorStrategy : actorStrategies) {
            IActorStrategyExecutor executor = this.getActorStrategyExecutor(actorStrategy);
            if (!executor.isMatch(actor, businessKey)) continue;
            return true;
        }
        return false;
    }

    public List<String> getActors(Collection<IActorStrategy> actorStrategies, RuntimeBusinessKey businessKey) {
        ArrayList<String> participants = new ArrayList<String>();
        for (IActorStrategy actorStrategy : actorStrategies) {
            Set actors = this.getActorStrategyExecutor(actorStrategy).getMatchUsers(businessKey);
            participants.addAll(actors);
        }
        return participants;
    }

    public IBusinessObjectSet getMatchBusinessKeys(Collection<IActorStrategy> actorStrategies, IActor actor, RuntimeBusinessKeyCollection businessKeys) {
        BusinessObjectSet businessObjectSet = new BusinessObjectSet();
        for (IActorStrategy actorStrategy : actorStrategies) {
            IActorStrategyExecutor executor = this.getActorStrategyExecutor(actorStrategy);
            businessObjectSet.addAll((Collection)executor.getMatchBusinessKeys(actor, businessKeys));
        }
        return businessObjectSet;
    }

    private IActorStrategyExecutor getActorStrategyExecutor(IActorStrategy actorStrategy) {
        return this.actorStrategyFactory.getActorStrategyExecutor(actorStrategy.getDefinitionId(), actorStrategy.getParameter());
    }

    public boolean actorIsGrantedToRole(IActor actor, String roleId) {
        return this.roleService.isGranted(roleId, actor.getIdentityId());
    }

    public boolean actorIsSystemUser(IActor actor) {
        return this.systemUserService.find(actor.getUserId()).isPresent();
    }
}

