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
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@DBAnno.DBTable(dbTable="NR_DATARESOURCE_DEFINE")
public class ResourceTreeDO
implements DataResourceDefine {
    private static final long serialVersionUID = -3368010245652599565L;
    @DBAnno.DBField(dbField="DD_KEY", isPk=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{key.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{key.notBlank}") String key;
    @DBAnno.DBField(dbField="DIM_KEY")
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{dimKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{dimKey.notBlank}") String dimKey;
    @DBAnno.DBField(dbField="DD_TITLE")
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    private @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @DBAnno.DBField(dbField="DD_DESC")
    @Size(max=1000, message="{text.size}")
    private @Size(max=1000, message="{text.size}") String desc;
    @DBAnno.DBField(dbField="DG_KEY")
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{group.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{group.notBlank}") String groupKey;
    @DBAnno.DBField(dbField="DD_ORDER", isOrder=true)
    @Size(max=10, message="{text.size}")
    @NotBlank(message="{order.notBlank}")
    private @Size(max=10, message="{text.size}") @NotBlank(message="{order.notBlank}") String order;
    @DBAnno.DBField(dbField="DD_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="DD_PERIOD")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String period;

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

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    @Override
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public Instant getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public String getPeriod() {
        return this.period;
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

    @Override
    public String getDimKey() {
        return this.dimKey;
    }

    @Override
    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    public String toString() {
        return "ResourceTreeDO{key='" + this.key + '\'' + ", dimKey='" + this.dimKey + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", groupKey='" + this.groupKey + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ResourceTreeDO treeDO = (ResourceTreeDO)o;
        return Objects.equals(this.key, treeDO.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public ResourceTreeDO clone() {
        try {
            return (ResourceTreeDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public ResourceTreeDO() {
    }

    public ResourceTreeDO(String key) {
        this.key = key;
    }
}

