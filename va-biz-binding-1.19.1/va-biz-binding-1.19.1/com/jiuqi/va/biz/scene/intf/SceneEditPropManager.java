/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.jsontype.NamedType
 */
package com.jiuqi.va.biz.scene.intf;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import com.jiuqi.va.biz.scene.intf.SceneEditPropBuilder;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class SceneEditPropManager
implements InitializingBean {
    @Autowired
    private List<SceneEditProp> ruleNodes;

    @Override
    public void afterPropertiesSet() throws Exception {
        SceneEditPropBuilder.setSubtypes(this.ruleNodes.stream().map(o -> new NamedType(o.getClass(), o.getType())).collect(Collectors.toList()));
    }
}

