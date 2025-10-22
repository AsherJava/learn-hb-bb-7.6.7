/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;

public class JioMatchResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String configKey;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigKey() {
        return this.configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}

