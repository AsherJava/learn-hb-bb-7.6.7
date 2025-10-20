/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.fasterxml.jackson.databind.annotation.JsonTypeResolver
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.scene.intf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.scene.intf.SceneEditPropBuilder;
import java.util.UUID;
import org.springframework.util.StringUtils;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="type", visible=false, include=JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonTypeResolver(value=SceneEditPropBuilder.class)
@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public interface SceneEditProp {
    public UUID getId();

    public String getType();

    public String getRemark();

    public String getExpression();

    @JsonIgnore
    public IExpression getCompileExpression();

    public String getPropTable();

    default public boolean isEnable() {
        return StringUtils.hasText(this.getExpression());
    }
}

