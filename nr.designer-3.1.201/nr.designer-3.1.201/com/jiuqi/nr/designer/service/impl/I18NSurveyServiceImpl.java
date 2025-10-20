/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.designer.i18n.I18NSurveyItem;
import com.jiuqi.nr.designer.service.I18NSurveyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="i18NSurveyService")
public class I18NSurveyServiceImpl
implements I18NSurveyService {
    @Autowired
    @Qualifier(value="nr")
    private I18nHelper i18nHelper;

    @Override
    public HashMap<String, String> getAllI18nSurveyItem() {
        HashMap<String, String> i18nSurveyMap = new HashMap<String, String>();
        ArrayList<String> i18NSurveyCode = I18NSurveyItem.I18NSurvey.getI18NSurveyCode();
        List i18NSurveyTitle = this.i18nHelper.getMessage(i18NSurveyCode);
        for (int i = 0; i < i18NSurveyCode.size(); ++i) {
            i18nSurveyMap.put((String)i18NSurveyCode.get(i), (String)i18NSurveyTitle.get(i));
        }
        return i18nSurveyMap;
    }
}

