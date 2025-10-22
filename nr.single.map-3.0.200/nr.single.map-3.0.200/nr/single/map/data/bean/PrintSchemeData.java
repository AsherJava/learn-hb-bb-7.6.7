/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;

public class PrintSchemeData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String key;
    private String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

