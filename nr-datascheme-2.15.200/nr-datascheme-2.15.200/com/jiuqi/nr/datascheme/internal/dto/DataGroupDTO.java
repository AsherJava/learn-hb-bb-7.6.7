/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataGroupDTO
implements DataGroup {
    private static final long serialVersionUID = -7209398129059354000L;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String key;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String dataSchemeKey;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String code;
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    protected @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @Size(max=1000, message="{desc.size}")
    protected @Size(max=1000, message="{desc.size}") String desc;
    @NotNull(message="{group.dataGroupKind.notNull}")
    protected @NotNull(message="{group.dataGroupKind.notNull}") DataGroupKind dataGroupKind;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String parentKey;
    @Size(max=20, message="{text.size}")
    protected @Size(max=20, message="{text.size}") String version;
    @Size(max=5, message="{text.size}")
    protected @Size(max=5, message="{text.size}") String level;
    @Size(max=10, message="{text.size}")
    protected @Size(max=10, message="{text.size}") String order;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    protected Instant updateTime;

    public static DataGroupDTO valueOf(DataGroup dataGroup) {
        if (dataGroup == null) {
            return null;
        }
        DataGroupDTO dto = new DataGroupDTO();
        DataGroupDTO.copyProperties(dataGroup, dto);
        return dto;
    }

    public static void copyProperties(DataGroup o, DataGroupDTO t) {
        t.setKey(o.getKey());
        t.setCode(o.getCode());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setTitle(o.getTitle());
        t.setUpdateTime(o.getUpdateTime());
        t.setDesc(o.getDesc());
        t.setVersion(o.getVersion());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setDataGroupKind(o.getDataGroupKind());
        t.setParentKey(o.getParentKey());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public DataGroupKind getDataGroupKind() {
        return this.dataGroupKind;
    }

    public void setDataGroupKind(DataGroupKind dataGroupKind) {
        this.dataGroupKind = dataGroupKind;
    }

    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }

    public DataGroupDTO clone() {
        try {
            return (DataGroupDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataGroupDTO that = (DataGroupDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public String toString() {
        return "DataGroupDTO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKind=" + this.dataGroupKind + ", parentKey='" + this.parentKey + '\'' + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + '}';
    }
}

