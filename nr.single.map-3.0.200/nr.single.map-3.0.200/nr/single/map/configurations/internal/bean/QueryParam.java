/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.internal.bean;

public class QueryParam {
    private String mappingKey;
    private String schemeKey;
    private String repotKey;
    private String keyWords;

    public QueryParam() {
    }

    public QueryParam(String mappingKey, String schemeKey, String repotKey, String keyWords) {
        this.mappingKey = mappingKey;
        this.schemeKey = schemeKey;
        this.repotKey = repotKey;
        this.keyWords = keyWords;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getRepotKey() {
        return this.repotKey;
    }

    public void setRepotKey(String repotKey) {
        this.repotKey = repotKey;
    }

    public String getKeyWords() {
        return this.keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}

