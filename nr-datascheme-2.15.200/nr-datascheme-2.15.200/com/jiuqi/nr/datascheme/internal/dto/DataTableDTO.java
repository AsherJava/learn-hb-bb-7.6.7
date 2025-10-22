/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Pattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataTableDTO
implements DataTable,
TableDefine {
    private static final long serialVersionUID = -8107780909976241121L;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String key;
    @Size(max=40, message="{text.size}")
    @NotNull(message="{dataSchemeKey.notNull}")
    protected @Size(max=40, message="{text.size}") @NotNull(message="{dataSchemeKey.notNull}") String dataSchemeKey;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String dataGroupKey;
    @Pattern(regexp="^[A-Za-z]\\w{0,30}", message="{code.notReg}")
    @NotNull(message="{code.notNull}")
    protected @Pattern(regexp="^[A-Za-z]\\w{0,30}", message="{code.notReg}") @NotNull(message="{code.notNull}") String code;
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    protected @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @NotNull(message="{table.dataTableType.notNull}")
    protected @NotNull(message="{table.dataTableType.notNull}") DataTableType dataTableType;
    @Size(max=1000, message="{desc.size}")
    protected @Size(max=1000, message="{desc.size}") String desc;
    protected String bizKeysStr;
    protected String[] bizKeys;
    protected String[] gatherFieldKeys;
    protected DataTableGatherType dataTableGatherType;
    @Size(max=20, message="{text.size}")
    protected @Size(max=20, message="{text.size}") String version;
    @Size(max=5, message="{text.size}")
    protected @Size(max=5, message="{text.size}") String level;
    @Size(max=10, message="{text.size}")
    protected @Size(max=10, message="{text.size}") String order;
    protected Boolean repeatCode;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    protected Instant updateTime;
    protected String owner;
    protected Boolean trackHistory;
    protected Boolean syncError = false;
    protected String expression;
    protected String alias;

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

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
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

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DataTableGatherType getDataTableGatherType() {
        return this.dataTableGatherType;
    }

    public void setDataTableGatherType(DataTableGatherType dataTableGatherType) {
        this.dataTableGatherType = dataTableGatherType;
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

    public Boolean getRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(Boolean repeatCode) {
        this.repeatCode = repeatCode;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String[] getBizKeys() {
        return this.bizKeys;
    }

    public void setBizKeys(String[] bizKeys) {
        this.bizKeys = bizKeys;
    }

    public String[] getGatherFieldKeys() {
        return this.gatherFieldKeys;
    }

    public void setGatherFieldKeys(String[] gatherFieldKeys) {
        this.gatherFieldKeys = gatherFieldKeys;
    }

    public Boolean getTrackHistory() {
        return this.trackHistory;
    }

    public void setTrackHistory(Boolean trackHistory) {
        this.trackHistory = trackHistory;
    }

    public Boolean getSyncError() {
        return this.syncError;
    }

    public void setSyncError(Boolean syncError) {
        this.syncError = syncError;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public DataTableDTO clone() {
        try {
            return (DataTableDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public String toString() {
        return "DataTableDTO{key='" + this.key + '\'' + ", dataSchemeKey='" + this.dataSchemeKey + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", dataTableType=" + this.dataTableType + ", desc='" + this.desc + '\'' + ", bizKeys=" + Arrays.toString(this.bizKeys) + ", dataTableGatherType=" + this.dataTableGatherType + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", repeatCode=" + this.repeatCode + ", trackHistory=" + this.trackHistory + ", updateTime=" + this.updateTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataTableDTO that = (DataTableDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
    }

    public static DataTableDTO valueOf(DataTable dataTable) {
        if (dataTable == null) {
            return null;
        }
        DataTableDTO dto = new DataTableDTO();
        DataTableDTO.copyProperties(dataTable, dto);
        return dto;
    }

    public static void copyProperties(DataTable o, DataTableDTO t) {
        t.setKey(o.getKey());
        t.setDataSchemeKey(o.getDataSchemeKey());
        t.setDataGroupKey(o.getDataGroupKey());
        t.setCode(o.getCode());
        t.setTitle(o.getTitle());
        t.setDataTableType(o.getDataTableType());
        t.setDesc(o.getDesc());
        t.setBizKeys(o.getBizKeys());
        t.setGatherFieldKeys(o.getGatherFieldKeys());
        t.setDataTableGatherType(o.getDataTableGatherType());
        t.setVersion(o.getVersion());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setRepeatCode(o.getRepeatCode());
        t.setUpdateTime(o.getUpdateTime());
        t.setOwner(o.getOwner());
        t.setTrackHistory(o.getTrackHistory());
        t.setSyncError(o.getSyncError());
        t.setExpression(o.getExpression());
        t.setAlias(o.getAlias());
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @JsonIgnore
    @Deprecated
    public String getBizKeyFieldsStr() {
        if (null == this.bizKeysStr) {
            this.bizKeysStr = null == this.bizKeys ? "" : Arrays.stream(this.bizKeys).collect(Collectors.joining(";"));
        }
        return this.bizKeysStr;
    }

    @JsonIgnore
    @Deprecated
    public TableKind getKind() {
        return super.getKind();
    }

    @JsonIgnore
    @Deprecated
    public String getDescription() {
        return super.getDescription();
    }

    @JsonIgnore
    @Deprecated
    public TableGatherType getGatherType() {
        return super.getGatherType();
    }

    @JsonIgnore
    @Deprecated
    public String[] getBizKeyFieldsID() {
        return super.getBizKeyFieldsID();
    }

    @JsonIgnore
    @Deprecated
    public String getOwnerLevelAndId() {
        return super.getOwnerLevelAndId();
    }
}

