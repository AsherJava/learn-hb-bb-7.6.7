/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.i18n.entity;

import com.jiuqi.nr.datascheme.i18n.entity.DataSchemeBasicI18n;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDO;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_I18N")
@DBAnno.DBLink(linkWith=DataFieldDO.class, linkField="key", field="key")
public class DataSchemeI18nDO
implements DataSchemeBasicI18n {
    public static final String CLZ_FIELD_KEY = "key";
    private static final long serialVersionUID = -8266545901721247068L;
    @DBAnno.DBField(dbField="DI_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="DI_TYPE", isPk=true)
    private String type;
    @DBAnno.DBField(dbField="DI_TITLE")
    private String title;
    @DBAnno.DBField(dbField="DI_DESC")
    private String desc;
    @DBAnno.DBField(dbField="DI_DS_KEY")
    private String dataSchemeKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DataSchemeI18nDO other = (DataSchemeI18nDO)obj;
        if (this.key == null ? other.key != null : !this.key.equals(other.key)) {
            return false;
        }
        return !(this.type == null ? other.type != null : !this.type.equals(other.type));
    }
}

