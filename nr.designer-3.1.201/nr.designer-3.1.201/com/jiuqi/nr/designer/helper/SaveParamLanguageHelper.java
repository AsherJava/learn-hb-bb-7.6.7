/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class SaveParamLanguageHelper {
    public ArrayList<DesParamLanguage> ParamLanguageObjectToParamLanguage(ParamLanguageObject[] paramLanguages) {
        ArrayList<DesParamLanguage> desParamLanguagesList = new ArrayList<DesParamLanguage>();
        for (ParamLanguageObject paramLanguageObject : paramLanguages) {
            DesParamLanguage desParamLanguage = new DesParamLanguage();
            desParamLanguage.setKey(UUIDUtils.getKey());
            desParamLanguage.setResourceKey(paramLanguageObject.getResourceKey());
            desParamLanguage.setLanguageType(String.valueOf(paramLanguageObject.getLanguageType()));
            desParamLanguage.setResourceType(LanguageResourceType.valueOf((int)paramLanguageObject.getResourceType()));
            desParamLanguage.setLanguageInfo(paramLanguageObject.getLanguageInfo());
            desParamLanguagesList.add(desParamLanguage);
        }
        return desParamLanguagesList;
    }
}

