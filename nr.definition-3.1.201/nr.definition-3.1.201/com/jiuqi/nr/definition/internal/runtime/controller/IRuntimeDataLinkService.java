/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IRuntimeDataLinkService {
    public DataLinkDefine queryDataLink(String var1);

    public List<DataLinkDefine> getDataLinksInForm(String var1);

    public List<DataLinkDefine> getDataLinksInRegion(String var1);

    public List<String> getFieldKeysInRegion(String var1);

    public List<String> getFieldKeysInForm(String var1);

    public DataLinkDefine queryDataLinkDefineByXY(String var1, int var2, int var3);

    public DataLinkDefine queryDataLinkDefineByColRow(String var1, int var2, int var3);

    public DataLinkDefine queryDataLinkDefineByUniquecode(String var1, String var2);

    public List<DataLinkDefine> queryDataLinkDefineByUniquecodes(String var1, Collection<String> var2);

    public List<DataLinkDefine> getDataLinksInFormByField(String var1, String var2);

    public List<DataLinkDefine> getDataLinksInRegionByField(String var1, String var2);

    public List<DataLinkDefine> getDataLinksByField(String var1) throws Exception;

    public BigDataDefine getAttachmentDataFromLink(String var1);

    default public DataLinkDefine getDataLink(String dataLinkKey, String formScheme) {
        return this.queryDataLink(dataLinkKey);
    }

    default public BigDataDefine getLinkFileSetting(String linkKey, String formSchemeKey) {
        return this.getAttachmentDataFromLink(linkKey);
    }

    default public List<DataLinkDefine> listDataLinkByForm(String formKey, String formSchemeKey) {
        return this.getDataLinksInForm(formKey);
    }

    default public List<DataLinkDefine> listDataLinkByDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.getDataLinksInRegion(dataRegionKey);
    }

    default public DataLinkDefine getDataLinkByFormAndPos(String formKey, int posX, int posY, String formSchemeKey) {
        return this.queryDataLinkDefineByXY(formKey, posX, posY);
    }

    default public DataLinkDefine getDataLinkByFormAndColRow(String formKey, int col, int row, String formSchemeKey) {
        return this.queryDataLinkDefineByColRow(formKey, col, row);
    }

    default public DataLinkDefine getDataLinkByFormAndUniquecode(String formKey, String uniqueCode, String formSchemeKey) {
        return this.queryDataLinkDefineByUniquecode(formKey, uniqueCode);
    }

    default public List<String> listFieldKeyByDataRegion(String dataRegionKey, String formSchemeKey) {
        return this.getFieldKeysInRegion(dataRegionKey);
    }

    default public List<String> listFieldKeyByForm(String formKey, String formSchemeKey) {
        return this.getFieldKeysInForm(formKey);
    }

    public List<DataLinkDefine> getDataLinkByFormAndLinkExp(String var1, String var2, String var3);

    public Set<String> listLinkExpressionByFormKey(String var1, String var2);
}

