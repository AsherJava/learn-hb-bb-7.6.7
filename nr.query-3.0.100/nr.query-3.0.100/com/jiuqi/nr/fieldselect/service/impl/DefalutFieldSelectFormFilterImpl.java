/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fieldselect.service.impl;

import com.jiuqi.nr.fieldselect.service.IFieldSelectFormFilterService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DefalutFieldSelectFormFilterImpl
implements IFieldSelectFormFilterService {
    @Override
    public boolean filterForm(Map<String, String> filterCondition, String formKey) {
        return false;
    }
}

