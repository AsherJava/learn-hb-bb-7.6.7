/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class PeriodDataSumDataAccessServiceImpl
implements IDataExtendAccessItemService {
    private static final String DATA_SUM_MSG = "\u6c47\u603b\u6570\u636e\uff0c\u4e0d\u53ef\u5199";
    private Function<String, String> noAccessResion = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(DATA_SUM_MSG);

    public int getOrder() {
        return -1;
    }

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        Boolean isDataSum = (Boolean)param.getParams();
        if (isDataSum.booleanValue()) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public String name() {
        return "periodDataSumData";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessResion.apply(code);
    }
}

