/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.extend;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.context.ApplicationEvent;

public class RuntimeParamChangeEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 2862847399115517223L;
    private final RuntimeParamChangeSource source;

    public RuntimeParamChangeEvent(RuntimeParamChangeSource source) {
        super(source);
        this.source = source;
    }

    @Override
    public RuntimeParamChangeSource getSource() {
        return this.source;
    }

    public static class RuntimeParamChangeSource
    implements Serializable {
        private static final long serialVersionUID = 1L;
        private final Map<ParamResourceType, List<ParamDeployItem>> deployItems = new EnumMap<ParamResourceType, List<ParamDeployItem>>(ParamResourceType.class);

        public RuntimeParamChangeSource(ParamDeployItem paramDeployItem) {
            this.deployItems.put(paramDeployItem.getType(), Collections.singletonList(paramDeployItem));
        }

        public RuntimeParamChangeSource(List<ParamDeployItem> paramDeployItems) {
            for (ParamDeployItem paramDeployItem : paramDeployItems) {
                this.deployItems.computeIfAbsent(paramDeployItem.getType(), k -> new ArrayList()).add(paramDeployItem);
            }
        }

        public boolean containsDeployItem(ParamResourceType type) {
            return this.deployItems.containsKey(type);
        }

        public List<ParamDeployItem> getDeployItems(ParamResourceType type) {
            return this.deployItems.get(type);
        }
    }
}

