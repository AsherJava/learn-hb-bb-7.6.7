/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

public class CommonParamDTO {
    private String key;
    private String code;
    private String title;

    public CommonParamDTO() {
    }

    public CommonParamDTO(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

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

    public String toString() {
        return "CommonParamDTO{key='" + this.key + '\'' + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + '}';
    }
}

