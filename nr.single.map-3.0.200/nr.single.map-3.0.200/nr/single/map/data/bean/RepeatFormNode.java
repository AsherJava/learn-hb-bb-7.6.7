/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.bean;

import java.io.Serializable;

public class RepeatFormNode
implements Serializable {
    private static final long serialVersionUID = 7315447531200183812L;
    private String formKey;
    private String formCode;
    private String formTitle;
    private int formType;
    private int repeatMode;

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public int getFormType() {
        return this.formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }
}

