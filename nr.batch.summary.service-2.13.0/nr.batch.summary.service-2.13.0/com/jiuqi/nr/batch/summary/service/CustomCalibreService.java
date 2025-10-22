/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme
 *  com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine
 */
package com.jiuqi.nr.batch.summary.service;

import com.jiuqi.nr.batch.summary.service.enumeration.SchemeServiceState;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.batch.summary.storage.entity.SummaryScheme;
import com.jiuqi.nr.batch.summary.storage.entity.impl.SummarySchemeDefine;
import java.util.List;

public interface CustomCalibreService {
    public SchemeServiceState saveCustomCalibreRows(SummarySchemeDefine var1, List<CustomCalibreRow> var2);

    public SchemeServiceState modifyCustomCalibreRows(SummarySchemeDefine var1, List<CustomCalibreRow> var2);

    public SchemeServiceState deleteCustomCalibreRows(SummaryScheme var1);
}

