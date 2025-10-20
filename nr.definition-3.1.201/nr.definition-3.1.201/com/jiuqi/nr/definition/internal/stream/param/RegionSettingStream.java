/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeRegionSettingDefine;

public class RegionSettingStream
extends AbstractParamStream<RegionSettingDefine> {
    private IParamLanguageController languageController;

    public RegionSettingStream(RegionSettingDefine param) {
        super(param);
    }

    public RegionSettingStream(RegionSettingDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(RegionSettingDefine taskDefine, String entityKeyData, String entityId) {
        return true;
    }

    @Override
    RegionSettingDefine transI18n(RegionSettingDefine regionSettingDefine) {
        I18nRunTimeRegionSettingDefine i18nRunTimeTaskDefine = new I18nRunTimeRegionSettingDefine(regionSettingDefine);
        i18nRunTimeTaskDefine.setRegionTabSetting(this.languageController.getDataRegionTab(regionSettingDefine.getKey(), null));
        return i18nRunTimeTaskDefine;
    }
}

