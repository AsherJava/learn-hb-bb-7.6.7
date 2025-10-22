/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 */
package com.jiuqi.nr.designer.paramlanguage.service;

import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ResultReportObject;
import java.util.List;
import java.util.Map;

public interface ParamLanguageService {
    public boolean checkLanguageRepeat(ParamLanguageObject var1);

    public String queryLanguage(String var1, LanguageResourceType var2, String var3);

    public String queryLanguageInfoByResource(ParamLanguageObject var1);

    public ResultReportObject queryReportWithParamLanguage(ResultReportObject var1, String var2);

    public Map<String, List<RegionTabSettingDefine>> queryRegionSettingWithParamLanguage(String var1, String var2) throws Exception;

    public boolean checkEnableMultiLanguage();

    public Map<String, String> queryFillingGuideWithParamLanguage(String var1, String var2) throws Exception;
}

