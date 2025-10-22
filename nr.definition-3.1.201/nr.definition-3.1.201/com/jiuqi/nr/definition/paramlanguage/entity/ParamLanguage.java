/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.paramlanguage.entity;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import java.util.Date;

@DBAnno.DBTable(dbTable="DES_PARAM_LANGUAGE")
public class ParamLanguage
implements IMetaItem {
    private static final long serialVersionUID = 829151758404295939L;
    @DBAnno.DBField(dbField="PL_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="PL_RESOURCE_KEY")
    private String resourceKey;
    @DBAnno.DBField(dbField="PL_RESOURCE_TYPE", tranWith="transformLanguageResourceType", dbType=Integer.class, appType=LanguageResourceType.class)
    private LanguageResourceType resourceType;
    @DBAnno.DBField(dbField="PL_LANGUAGE_TYPE")
    private String languageType;
    @DBAnno.DBField(dbField="PL_LANGUAGE_INFO")
    private String languageInfo;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResourceKey() {
        return this.resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public LanguageResourceType getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(LanguageResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getLanguageType() {
        return this.languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getLanguageInfo() {
        return this.languageInfo;
    }

    public void setLanguageInfo(String languageInfo) {
        this.languageInfo = languageInfo;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }
}

