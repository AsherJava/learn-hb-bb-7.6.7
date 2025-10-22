/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.internal.bean;

import java.io.Serializable;

public class DictionaryData
implements Serializable {
    private static final long serialVersionUID = 904415320423682576L;
    private String code;
    private String title;

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
}

