/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.va.biz.impl.value.NamedManagerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.utils.Utils;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Lazy(value=false)
public class ActionManagerImpl
extends NamedManagerImpl<Action>
implements ActionManager {
    private Map<UUID, Action> idMap;
    private static ActionManagerImpl instance;

    @Override
    public List<Action> getActionList(Class<? extends Model> modelClass) {
        return this.stream().filter(action -> {
            if (action.getDependModel().isAssignableFrom(modelClass)) {
                return true;
            }
            if (!CollectionUtils.isEmpty(action.getDependModels())) {
                return action.getDependModels().stream().filter(o -> o.isAssignableFrom(modelClass)).findFirst().isPresent();
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Action> getAllActionList() {
        return this.stream().collect(Collectors.toList());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        instance = this;
        ActionManagerImpl.instance.idMap = this.stream().collect(Collectors.toMap(o -> Utils.normalizeId(o.getName()), o -> o));
    }

    public static Action findAction(UUID id) {
        return ActionManagerImpl.instance.idMap.get(id);
    }
}

