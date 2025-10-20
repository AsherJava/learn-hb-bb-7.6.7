/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.designer.paramlanguage.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.service.ParamLanguageService;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultFormGroupObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultFormObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultReportObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ParamLanguageServiceImpl
implements ParamLanguageService {
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private DesignFormDefineService formService;
    @Autowired
    private LanguageTypeService languageTypeService;
    private static final String LANGUAGECONFIG = "languages";

    @Override
    public boolean checkEnableMultiLanguage() {
        return true;
    }

    @Override
    public boolean checkLanguageRepeat(ParamLanguageObject paramLanguage) {
        Assert.notNull((Object)paramLanguage.getResourceKey(), "\u8d44\u6e90Key\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(paramLanguage.getResourceKey(), LanguageResourceType.valueOf((int)paramLanguage.getResourceType()), String.valueOf(paramLanguage.getLanguageType()));
        return desParamLanguages.size() > 0 ? ((DesParamLanguage)desParamLanguages.get(0)).getLanguageInfo().equals(paramLanguage.getLanguageInfo()) : false;
    }

    @Override
    public String queryLanguage(String resourceKey, LanguageResourceType resourceType, String languageType) {
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(resourceKey, resourceType, languageType);
        if (desParamLanguages.size() > 0) {
            return ((DesParamLanguage)desParamLanguages.get(0)).getLanguageInfo();
        }
        return null;
    }

    @Override
    public String queryLanguageInfoByResource(ParamLanguageObject paramLanguage) {
        Assert.notNull((Object)paramLanguage.getResourceKey(), "\u8d44\u6e90Key\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        List desParamLanguages = this.desParamLanguageDao.queryLanguage(paramLanguage.getResourceKey(), LanguageResourceType.valueOf((int)paramLanguage.getResourceType()), String.valueOf(paramLanguage.getLanguageType()));
        if (desParamLanguages.size() > 0) {
            return ((DesParamLanguage)desParamLanguages.get(0)).getLanguageInfo();
        }
        return "";
    }

    @Override
    public ResultReportObject queryReportWithParamLanguage(ResultReportObject resultReportObject, String languageType) {
        List<ResultFormGroupObject> forms = resultReportObject.getForms();
        List<ResultFormGroupObject> tree = resultReportObject.getTree();
        for (ResultFormGroupObject formGroup : forms) {
            List<ResultFormObject> children;
            String formGroupLanguage = this.queryLanguage(formGroup.getId(), LanguageResourceType.FORMGROUPTITLE, languageType);
            if (StringUtils.isNotEmpty((String)formGroupLanguage)) {
                formGroup.setTitle(formGroupLanguage);
            }
            if ((children = formGroup.getChildren()).size() <= 0) continue;
            for (ResultFormObject form : children) {
                String formLanguage = this.queryLanguage(form.getId(), LanguageResourceType.FORMTITLE, languageType);
                if (!StringUtils.isNotEmpty((String)formLanguage)) continue;
                form.setTitle(formLanguage);
            }
        }
        tree = forms;
        return resultReportObject;
    }

    @Override
    public Map<String, List<RegionTabSettingDefine>> queryRegionSettingWithParamLanguage(String formKey, String languageType) throws Exception {
        List regionDefineList = this.iDesignTimeViewController.getAllRegionsInForm(formKey);
        HashMap<String, List<RegionTabSettingDefine>> regionTabSettingMap = new HashMap<String, List<RegionTabSettingDefine>>();
        for (DesignDataRegionDefine designDataRegionDefine : regionDefineList) {
            if (designDataRegionDefine.getRegionSettingKey() == null) continue;
            if (this.languageTypeService.queryDefaultLanguage().equals(languageType)) {
                byte[] data = this.formService.getBigData(designDataRegionDefine.getRegionSettingKey(), "REGION_TAB");
                if (data == null) continue;
                ArrayList regionTabSettingDataList = new ArrayList(RegionTabSettingData.bytesToRegionTabSettingData((byte[])data));
                regionTabSettingMap.put(designDataRegionDefine.getKey(), regionTabSettingDataList);
                continue;
            }
            byte[] otherData = this.formService.getBigDataByLanguageType(designDataRegionDefine.getRegionSettingKey(), "REGION_TAB", Integer.parseInt(languageType));
            byte[] defaultData = this.formService.getBigData(designDataRegionDefine.getRegionSettingKey(), "REGION_TAB");
            if (null == otherData) {
                if (defaultData == null) continue;
                ArrayList regionTabSettingDataList = new ArrayList(RegionTabSettingData.bytesToRegionTabSettingData((byte[])defaultData));
                regionTabSettingMap.put(designDataRegionDefine.getKey(), regionTabSettingDataList);
                continue;
            }
            if (defaultData == null) continue;
            List defaultDatas = RegionTabSettingData.bytesToRegionTabSettingData((byte[])defaultData);
            List otherDatas = RegionTabSettingData.bytesToRegionTabSettingData((byte[])otherData);
            RegionTabSettingData.mergeTabSetting((List)defaultDatas, (List)otherDatas);
            RegionTabSettingData.transAllData((List)defaultDatas);
            ArrayList regionTabSettingDataList = new ArrayList(defaultDatas);
            regionTabSettingMap.put(designDataRegionDefine.getKey(), regionTabSettingDataList);
        }
        return regionTabSettingMap;
    }

    @Override
    public Map<String, String> queryFillingGuideWithParamLanguage(String formKey, String languageType) throws Exception {
        String fillingGuide = "";
        DesignFormDefine designFormDefine = this.iDesignTimeViewController.querySoftFormDefine(formKey);
        if (null != designFormDefine) {
            byte[] otherData;
            byte[] defaultData = this.formService.getBigData(designFormDefine.getKey(), "FILLING_GUIDE");
            if (null != defaultData) {
                fillingGuide = DesignFormDefineBigDataUtil.bytesToString((byte[])defaultData);
            }
            if (!this.languageTypeService.queryDefaultLanguage().equals(languageType) && null != (otherData = this.formService.getBigDataByLanguageType(designFormDefine.getKey(), "FILLING_GUIDE", Integer.parseInt(languageType)))) {
                fillingGuide = DesignFormDefineBigDataUtil.bytesToString((byte[])otherData);
            }
        }
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(formKey, fillingGuide);
        return data;
    }
}

