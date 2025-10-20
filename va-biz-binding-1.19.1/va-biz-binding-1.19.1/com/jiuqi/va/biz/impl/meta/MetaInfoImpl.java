/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.impl.meta;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.intf.meta.MetaInfo;
import java.util.UUID;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class MetaInfoImpl
implements MetaInfo {
    private UUID id;
    private long version;
    private String title;
    private String groupName;
    private String metaType;
    private String modelType;
    private String module;
    private String name;

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public long getVersion() {
        return this.version;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getModule() {
        return this.module;
    }

    @Override
    public String getMetaType() {
        return this.metaType;
    }

    @Override
    public String getModelType() {
        return this.modelType;
    }

    @Override
    public String getGroupName() {
        return this.groupName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

