/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.web.vo;

import com.jiuqi.nr.mapping.bean.UnitRule;
import com.jiuqi.nr.mapping.web.dto.UnitMappingDTO;
import java.util.List;

public class UnitMappingVO {
    private String entityId;
    private String datetime;
    private String period;
    private List<UnitMappingDTO> mappings;
    private UnitRule importRule;
    private UnitRule exportRule;
    private boolean isJIO;
    private boolean isVersion;

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<UnitMappingDTO> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<UnitMappingDTO> mappings) {
        this.mappings = mappings;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public UnitRule getImportRule() {
        return this.importRule;
    }

    public void setImportRule(UnitRule importRule) {
        this.importRule = importRule;
    }

    public UnitRule getExportRule() {
        return this.exportRule;
    }

    public void setExportRule(UnitRule exportRule) {
        this.exportRule = exportRule;
    }

    public boolean isJIO() {
        return this.isJIO;
    }

    public void setJIO(boolean JIO) {
        this.isJIO = JIO;
    }

    public boolean isVersion() {
        return this.isVersion;
    }

    public void setIsVersion(boolean version) {
        this.isVersion = version;
    }
}

