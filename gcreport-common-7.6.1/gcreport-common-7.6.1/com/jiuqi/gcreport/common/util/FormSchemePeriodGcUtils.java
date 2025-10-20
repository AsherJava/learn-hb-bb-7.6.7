/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gcreport.common.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class FormSchemePeriodGcUtils {
    public static String[] getFromToPeriodByFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine == null) {
            return new String[]{"", ""};
        }
        return FormSchemePeriodGcUtils.getFromToPeriodByFormSchemeKey(formSchemeDefine.getKey());
    }

    public static String[] getFromToPeriodByFormSchemeKey(String formSchemeKey) {
        List schemePeriodLinkDefines;
        String[] fromToPeriod = new String[]{"", ""};
        try {
            schemePeriodLinkDefines = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).querySchemePeriodLinkByScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u65b9\u6848\u65f6\u671f\u8303\u56f4\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
        }
        if (CollectionUtils.isEmpty((Collection)schemePeriodLinkDefines)) {
            return fromToPeriod;
        }
        schemePeriodLinkDefines.sort(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey));
        fromToPeriod[0] = ((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(0)).getPeriodKey();
        fromToPeriod[1] = ((SchemePeriodLinkDefine)schemePeriodLinkDefines.get(schemePeriodLinkDefines.size() - 1)).getPeriodKey();
        return fromToPeriod;
    }
}

