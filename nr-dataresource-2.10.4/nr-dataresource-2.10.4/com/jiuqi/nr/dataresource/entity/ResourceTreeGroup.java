/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer
 *  com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.dataresource.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@DBAnno.DBTable(dbTable="NR_DATARESOURCE_DEFINEGROUP")
public class ResourceTreeGroup
implements DataResourceDefineGroup {
    private static final long serialVersionUID = 3891201503351765413L;
    @DBAnno.DBField(dbField="DG_KEY", isPk=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{key.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{key.notBlank}") String key;
    @DBAnno.DBField(dbField="DG_TITLE")
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    private @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @DBAnno.DBField(dbField="DG_DESC")
    @Size(max=1000, message="{text.size}")
    private @Size(max=1000, message="{text.size}") String desc;
    @DBAnno.DBField(dbField="DG_ORDER", isOrder=true)
    @Size(max=10, message="{text.size}")
    @NotBlank(message="{order.notBlank}")
    private @Size(max=10, message="{text.size}") @NotBlank(message="{order.notBlank}") String order;
    @DBAnno.DBField(dbField="DG_PARENT_KEY")
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{parentKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{parentKey.notBlank}") String parentKey;
    @DBAnno.DBField(dbField="DG_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant updateTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return -1;
        }
        if (this.order == null) {
            return 1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public String toString() {
        return "ResourceTreeGroup{key='" + this.key + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", order='" + this.order + '\'' + ", parentKey='" + this.parentKey + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ResourceTreeGroup that = (ResourceTreeGroup)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }
}

