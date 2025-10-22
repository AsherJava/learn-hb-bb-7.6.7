/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.upload.domain;

public class ZBInfoDO {
    private String title;
    private String value;

    public ZBInfoDO() {
    }

    public ZBInfoDO(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

