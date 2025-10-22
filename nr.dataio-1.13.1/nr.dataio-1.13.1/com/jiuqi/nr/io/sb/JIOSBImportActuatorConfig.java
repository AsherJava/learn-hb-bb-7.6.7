/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.sb;

import com.jiuqi.nr.io.sb.ImportMode;
import com.jiuqi.nr.io.sb.SBImportActuatorConfig;
import com.jiuqi.nr.io.sb.SBImportActuatorType;
import java.util.HashMap;
import java.util.Map;

public class JIOSBImportActuatorConfig
implements SBImportActuatorConfig {
    private Map<String, Object> configItems = new HashMap<String, Object>();
    private boolean isBatchByUnit = false;

    public JIOSBImportActuatorConfig() {
    }

    public JIOSBImportActuatorConfig(boolean isBatchByUnit) {
        this.isBatchByUnit = isBatchByUnit;
    }

    @Override
    public SBImportActuatorType getImportActuatorType() {
        return (SBImportActuatorType)((Object)this.configItems.get("TYPE"));
    }

    public String getDestTable() {
        return (String)this.configItems.get("DEST_TABLE");
    }

    public String getDestPeriod() {
        return (String)this.configItems.get("DEST_PERIOD");
    }

    @Override
    public ImportMode getImportMode() {
        return (ImportMode)((Object)this.configItems.get("IMPORT_MODE"));
    }

    public boolean isSpecifySbId() {
        Object o = this.configItems.get("SBID_SPECIFY");
        if (o == null) {
            return false;
        }
        return Boolean.TRUE.equals(o);
    }

    @Override
    public Map<String, Object> configItems() {
        return this.configItems;
    }

    public String toString() {
        return "\u5bfc\u5165\u914d\u7f6e\u4fe1\u606f configItems=" + this.configItems;
    }

    public boolean isBatchByUnit() {
        return this.isBatchByUnit;
    }
}

