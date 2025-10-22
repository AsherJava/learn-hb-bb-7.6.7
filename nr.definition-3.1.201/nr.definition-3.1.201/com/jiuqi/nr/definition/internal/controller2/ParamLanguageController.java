/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.common.utils.StringUtils
 */
package com.jiuqi.nr.definition.internal.controller2;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.paramlanguage.cache.ParamLanguageCache;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamLanguageController
implements IParamLanguageController {
    @Autowired
    private ParamLanguageCache paramLanguageCache;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private LanguageTypeUtil languageTypeUtil;

    private int getLanguageType(String languageType) {
        try {
            if (StringUtils.isNotEmpty((String)languageType)) {
                return Integer.parseInt(languageType);
            }
            return this.languageTypeUtil.getCurrentLanguageType();
        }
        catch (Exception e) {
            return this.languageTypeUtil.getCurrentLanguageType();
        }
    }

    @Override
    public String getTaskTitle(String taskKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(taskKey, this.getLanguageType(languageType));
    }

    @Override
    public String getFormSchemeTitle(String formSchemeKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(formSchemeKey, this.getLanguageType(languageType));
    }

    @Override
    public String getFormulaSchemeTitle(String formulaSchemeKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(formulaSchemeKey, this.getLanguageType(languageType));
    }

    @Override
    public String getPrintSchemeTitle(String printSchemeKey, String languageType) {
        return null;
    }

    @Override
    public String getFormGroupTitle(String formGroupKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(formGroupKey, this.getLanguageType(languageType));
    }

    @Override
    public String getFormTitle(String formKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(formKey, this.getLanguageType(languageType));
    }

    @Override
    public byte[] getFormStyle(String formKey, String languageType) {
        return this.paramLanguageCache.queryBigDataParamLanguage(formKey, "FORM_DATA", this.getLanguageType(languageType));
    }

    @Override
    public List<RegionTabSettingDefine> getDataRegionTab(String regionSettingKey, String languageType) {
        byte[] otherData = this.paramLanguageCache.queryBigDataParamLanguage(regionSettingKey, "REGION_TAB", this.getLanguageType(languageType));
        byte[] defaultData = this.paramLanguageCache.queryBigDataParamLanguage(regionSettingKey, "REGION_TAB", this.defaultLanguageService.getDefaultLanguage());
        if (null == otherData) {
            if (defaultData != null) {
                return new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData(defaultData));
            }
        } else if (defaultData != null) {
            List<DesignRegionTabSettingDefine> defaultDatas = RegionTabSettingData.bytesToRegionTabSettingData(defaultData);
            List<DesignRegionTabSettingDefine> otherDatas = RegionTabSettingData.bytesToRegionTabSettingData(otherData);
            RegionTabSettingData.mergeTabSetting(defaultDatas, otherDatas);
            RegionTabSettingData.transAllData(defaultDatas);
            ArrayList<RegionTabSettingDefine> regionTabSettingDataList = new ArrayList<RegionTabSettingDefine>(defaultDatas);
            return regionTabSettingDataList;
        }
        return null;
    }

    @Override
    public byte[] getDesignFormStyle(String formKey, String languageType) {
        return this.paramLanguageCache.queryBigDataParamLanguage(formKey, "FORM_DATA", this.getLanguageType(languageType));
    }

    @Override
    public String getFormulaDescript(String formulaKey, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(formulaKey, this.getLanguageType(languageType));
    }

    @Override
    public String getFullingGuide(String formKey, String languageType) {
        byte[] bytes = this.paramLanguageCache.queryBigDataParamLanguage(formKey, "FILLING_GUIDE", this.getLanguageType(languageType));
        if (null != bytes) {
            return DesignFormDefineBigDataUtil.bytesToString(bytes);
        }
        return null;
    }

    @Override
    public String getTaskOrgLink(String taskOrgLinkID, String languageType) {
        return this.paramLanguageCache.queryParamLanguage(taskOrgLinkID, this.getLanguageType(languageType));
    }
}

