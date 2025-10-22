/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.language;

import com.jiuqi.nr.datascheme.i18n.language.ILanguageType;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.io.Serializable;

@DBAnno.DBTable(dbTable="NR_LANGUAGE_TYPE")
public class LanguageTypeDO
implements ILanguageType,
Serializable {
    private static final long serialVersionUID = 5949979319581605959L;
    @DBAnno.DBField(dbField="LT_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="LT_LANGUAGE")
    private String language;
    @DBAnno.DBField(dbField="LT_ISDEFAULT", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isDefault;
    @DBAnno.DBField(dbField="LT_MESSAGE")
    private String message;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

