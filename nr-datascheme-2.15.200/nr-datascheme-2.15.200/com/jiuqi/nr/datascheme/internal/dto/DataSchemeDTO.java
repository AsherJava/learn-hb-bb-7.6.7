/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 *  com.jiuqi.nr.datascheme.api.type.DataSchemeType
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Pattern
 *  javax.validation.constraints.Size
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataSchemeType;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonDeserializer;
import com.jiuqi.nr.datascheme.common.jackson.InstantJsonSerializer;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSchemeDTO
implements DataScheme {
    private static final long serialVersionUID = -6008221301646806050L;
    @Size(max=40, message="{text.size}")
    protected @Size(max=40, message="{text.size}") String key;
    @Pattern(regexp="^[A-Za-z]\\w{0,19}", message="{code.notReg}")
    @NotNull(message="{code.notNull}")
    protected @Pattern(regexp="^[A-Za-z]\\w{0,19}", message="{code.notReg}") @NotNull(message="{code.notNull}") String code;
    @NotBlank(message="{title.notBlank}")
    @Size(min=1, max=200, message="{title.size}")
    protected @NotBlank(message="{title.notBlank}") @Size(min=1, max=200, message="{title.size}") String title;
    @Size(max=1000, message="{desc.size}")
    protected @Size(max=1000, message="{desc.size}") String desc;
    @NotNull(message="{dataGroupKey.notNull}")
    @Size(max=40, message="{text.size}")
    protected @NotNull(message="{dataGroupKey.notNull}") @Size(max=40, message="{text.size}") String dataGroupKey;
    @Pattern(regexp="^[A-Za-z]\\w{0,3}", message="{scheme.prefix.notReg}")
    protected @Pattern(regexp="^[A-Za-z]\\w{0,3}", message="{scheme.prefix.notReg}") String prefix;
    @NotNull(message="{scheme.auto.notNull}")
    protected @NotNull(message="{scheme.auto.notNull}") Boolean auto;
    @Size(max=20, message="{text.size}")
    protected @Size(max=20, message="{text.size}") String version;
    @Size(max=5, message="{text.size}")
    protected @Size(max=5, message="{text.size}") String level;
    @Size(max=10, message="{text.size}")
    protected @Size(max=10, message="{text.size}") String order;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    protected Instant updateTime;
    protected String creator;
    @NotNull(message="{scheme.type.notNull}")
    protected @NotNull(message="{scheme.type.notNull}") DataSchemeType type = DataSchemeType.NR;
    protected String bizCode;
    protected Boolean gatherDB;
    protected String encryptScene;
    protected String zbSchemeKey;
    protected String zbSchemeVersion;
    protected String calibre;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getAuto() {
        return this.auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
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

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public DataSchemeType getType() {
        return this.type;
    }

    public void setType(DataSchemeType type) {
        this.type = type;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Boolean getGatherDB() {
        return this.gatherDB;
    }

    public void setGatherDB(Boolean gatherDB) {
        this.gatherDB = gatherDB;
    }

    public String getEncryptScene() {
        return this.encryptScene;
    }

    public void setEncryptScene(String encryptScene) {
        this.encryptScene = encryptScene;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeVersion() {
        return this.zbSchemeVersion;
    }

    public void setZbSchemeVersion(String versionKey) {
        this.zbSchemeVersion = versionKey;
    }

    public String getCalibre() {
        return this.calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String toString() {
        return "DataSchemeDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", desc='" + this.desc + '\'' + ", dataGroupKey='" + this.dataGroupKey + '\'' + ", prefix='" + this.prefix + '\'' + ", auto=" + this.auto + ", version='" + this.version + '\'' + ", level='" + this.level + '\'' + ", order='" + this.order + '\'' + ", updateTime=" + this.updateTime + ", creator='" + this.creator + '\'' + ", type=" + this.type + ", bizCode='" + this.bizCode + '\'' + ", gatherDB=" + this.gatherDB + ", encryptScene='" + this.encryptScene + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DataSchemeDTO that = (DataSchemeDTO)o;
        return Objects.equals(this.key, that.key);
    }

    public int hashCode() {
        return this.key != null ? this.key.hashCode() : 0;
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

    public DataSchemeDTO clone() {
        try {
            return (DataSchemeDTO)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("\u6df1\u5ea6\u590d\u5236\u6570\u636e\u51fa\u9519", e);
        }
    }

    public static DataSchemeDTO valueOf(DataScheme o) {
        if (o == null) {
            return null;
        }
        DataSchemeDTO dto = new DataSchemeDTO();
        DataSchemeDTO.copyProperties(o, dto);
        return dto;
    }

    public static void copyProperties(DataScheme o, DataSchemeDTO t) {
        t.setKey(o.getKey());
        t.setCode(o.getCode());
        t.setLevel(o.getLevel());
        t.setOrder(o.getOrder());
        t.setTitle(o.getTitle());
        t.setUpdateTime(o.getUpdateTime());
        t.setAuto(o.getAuto());
        t.setDataGroupKey(o.getDataGroupKey());
        t.setDesc(o.getDesc());
        t.setPrefix(o.getPrefix());
        t.setVersion(o.getVersion());
        t.setCreator(o.getCreator());
        t.setType(o.getType());
        t.setBizCode(o.getBizCode());
        t.setGatherDB(o.getGatherDB());
        t.setEncryptScene(o.getEncryptScene());
        t.setZbSchemeKey(o.getZbSchemeKey());
        t.setZbSchemeVersion(o.getZbSchemeVersion());
        t.setCalibre(o.getCalibre());
    }
}

