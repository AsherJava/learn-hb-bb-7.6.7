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
import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceKind;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DBAnno.DBTable(dbTable="NR_DATARESOURCE")
public class DataResourceDO
implements DataResource {
    private static final long serialVersionUID = 1412393597699629352L;
    @DBAnno.DBField(dbField="DR_KEY", isPk=true)
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{key.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{key.notBlank}") String key;
    @DBAnno.DBField(dbField="DIM_KEY")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String dimKey;
    @DBAnno.DBField(dbField="DR_TITLE")
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    private @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @DBAnno.DBField(dbField="DR_DESC")
    @Size(max=1000, message="{text.size}")
    private @Size(max=1000, message="{text.size}") String desc;
    @DBAnno.DBField(dbField="DD_KEY")
    @Size(max=40, message="{text.size}")
    @NotBlank(message="{resourceDefineKey.notBlank}")
    private @Size(max=40, message="{text.size}") @NotBlank(message="{resourceDefineKey.notBlank}") String resourceDefineKey;
    @DBAnno.DBField(dbField="DR_KIND", tranWith="transDataResourceKind", appType=DataResourceKind.class, dbType=int.class)
    @NotNull(message="{resourceKind.notBlank}")
    private @NotNull(message="{resourceKind.notBlank}") DataResourceKind resourceKind;
    @DBAnno.DBField(dbField="DR_PARENT_KEY")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String parentKey;
    @DBAnno.DBField(dbField="DR_ORDER", isOrder=true)
    @Size(max=10, message="{text.size}")
    @NotBlank(message="{order.notBlank}")
    private @Size(max=10, message="{text.size}") @NotBlank(message="{order.notBlank}") String order;
    @DBAnno.DBField(dbField="DR_SOURCE")
    @Size(max=64, message="{text.size}")
    private @Size(max=64, message="{text.size}") String source;
    @DBAnno.DBField(dbField="DR_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Instant updateTime;
    @DBAnno.DBField(dbField="DT_KEY")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String dataTableKey;
    @DBAnno.DBField(dbField="DS_KEY")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String dataSchemeKey;
    @DBAnno.DBField(dbField="DR_ZB")
    @Size(max=40, message="{text.size}")
    private @Size(max=40, message="{text.size}") String linkZb;

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
    public String getResourceDefineKey() {
        return this.resourceDefineKey;
    }

    @Override
    public void setResourceDefineKey(String resourceTreeKey) {
        this.resourceDefineKey = resourceTreeKey;
    }

    @Override
    public DataResourceKind getResourceKind() {
        return this.resourceKind;
    }

    @Override
    public void setResourceKind(DataResourceKind resourceKind) {
        this.resourceKind = resourceKind;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getLinkZb() {
        return this.linkZb;
    }

    @Override
    public void setLinkZb(String linkZb) {
        this.linkZb = linkZb;
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
    public String getDimKey() {
        return this.dimKey;
    }

    @Override
    public void setDimKey(String dimKey) {
        this.dimKey = dimKey;
    }

    @Override
    public String getDataTableKey() {
        return this.dataTableKey;
    }

    @Override
    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    @Override
    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataResourceDO groupDO = (DataResourceDO)o;
        return Objects.equals(this.key, groupDO.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public DataResourceDO clone() {
        try {
            return (DataResourceDO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public String toString() {
        return "DataResourceDO{key='" + this.key + '\'' + ", dimKey='" + this.dimKey + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", resourceDefineKey='" + this.resourceDefineKey + '\'' + ", resourceKind=" + (Object)((Object)this.resourceKind) + ", parentKey='" + this.parentKey + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + '}';
    }
}

