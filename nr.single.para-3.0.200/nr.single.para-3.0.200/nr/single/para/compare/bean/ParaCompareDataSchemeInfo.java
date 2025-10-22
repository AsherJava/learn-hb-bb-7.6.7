/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.compare.bean;

import java.io.Serializable;

public class ParaCompareDataSchemeInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dataSchemeKey;
    private String dataSchemeCode;
    private String dataSchemeTitle;
    private String dataPrefix;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public String getDataPrefix() {
        return this.dataPrefix;
    }

    public void setDataPrefix(String dataPrefix) {
        this.dataPrefix = dataPrefix;
    }
}

