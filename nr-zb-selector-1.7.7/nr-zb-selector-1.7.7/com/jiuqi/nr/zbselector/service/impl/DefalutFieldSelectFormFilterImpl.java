/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.zbselector.service.impl;

import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.zbselector.service.IFieldSelectFormFilterService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component(value="defalutFieldSelectFormFilterImpl")
public class DefalutFieldSelectFormFilterImpl
implements IFieldSelectFormFilterService {
    @Override
    public boolean filterForm(Map<String, String> filterCondition, String formKey) {
        return false;
    }

    @Override
    public boolean filterField(Map<String, String> filterCondition, FormDefine formDefine, DataLinkDefine dataLinkDefine) {
        return false;
    }
}

