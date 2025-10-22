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
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.dataresource.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.dataresource.DataLinkKind;
import com.jiuqi.nr.dataresource.DataResourceLink;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DBAnno.DBTable(dbTable="NR_DATARESOURCE_LINK")
public class DataResourceLinkDO
implements DataResourceLink {
    private static final long serialVersionUID = 5247592753564047550L;
    @DBAnno.DBField(dbField="DR_KEY", isPk=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{group.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{group.notBlank}") String groupKey;
    @DBAnno.DBField(dbField="DD_KEY")
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{resourceDefineKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{resourceDefineKey.notBlank}") String resourceDefineKey;
    @DBAnno.DBField(dbField="DF_KEY", isPk=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{dataFieldKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{dataFieldKey.notBlank}") String dataFieldKey;
    @DBAnno.DBField(dbField="DL_KIND", tranWith="transDataLinkKind", appType=DataLinkKind.class, dbType=int.class)
    @NotNull(message="{linkKind.notBlank}")
    private @NotNull(message="{linkKind.notBlank}") DataLinkKind kind;
    @DBAnno.DBField(dbField="DL_ORDER", isOrder=true)
    @Size(max=10, message="{text.size}")
    @NotBlank(message="{order.notBlank}")
    private @Size(max=10, message="{text.size}") @NotBlank(message="{order.notBlank}") String order;
    @DBAnno.DBField(dbField="DL_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant updateTime;

    @Override
    public String getGroupKey() {
        return this.groupKey;
    }

    @Override
    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @Override
    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    @Override
    public void setResourceDefineKey(String resourceDefineKey) {
        this.resourceDefineKey = resourceDefineKey;
    }

    @Override
    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    @Override
    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    @Override
    public DataLinkKind getKind() {
        return this.kind;
    }

    @Override
    public void setKind(DataLinkKind kind) {
        this.kind = kind;
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
        return "ResourceGroupLinkDO{groupKey='" + this.groupKey + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", dataFieldKey='" + this.dataFieldKey + '\'' + ", kind=" + (Object)((Object)this.kind) + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }
}

