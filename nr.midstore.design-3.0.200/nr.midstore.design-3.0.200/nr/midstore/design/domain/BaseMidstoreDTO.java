/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.design.domain;

import java.util.HashMap;
import java.util.Map;

public class BaseMidstoreDTO {
    protected String key;
    private Map<String, Object> values;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, Object> getValues() {
        if (this.values == null) {
            this.values = new HashMap<String, Object>();
        }
        return this.values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}

