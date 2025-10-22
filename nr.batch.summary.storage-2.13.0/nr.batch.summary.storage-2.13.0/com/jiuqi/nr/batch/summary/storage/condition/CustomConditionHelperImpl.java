/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.summary.storage.condition;

import com.jiuqi.nr.batch.summary.storage.CustomCalibreDao;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionHelper;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProvider;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRowProviderImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CustomConditionHelperImpl
implements CustomConditionHelper {
    @Resource
    private CustomCalibreDao customCalibreDao;

    @Override
    public CustomConditionRowProvider getTreeProvider(String schemeKey) {
        return new CustomConditionRowProviderImpl(this.customCalibreDao.findConditionRow(schemeKey));
    }
}

