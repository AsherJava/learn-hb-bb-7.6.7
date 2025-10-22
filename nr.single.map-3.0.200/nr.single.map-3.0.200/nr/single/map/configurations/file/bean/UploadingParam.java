/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.bean;

public class UploadingParam {
    private String fileName;
    private String cacheKey;
    private String cacheType;

    public UploadingParam(String fileName, String cacheKey, String cacheType) {
        this.fileName = fileName;
        this.cacheKey = cacheKey;
        this.cacheType = cacheType;
    }

    public UploadingParam() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCacheKey() {
        return this.cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public String getCacheType() {
        return this.cacheType;
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }
}

