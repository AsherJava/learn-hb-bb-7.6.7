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
public class EntityNoDataAccessServiceImpl
implements IDataExtendAccessItemService {
    private static final String EMPTY_NODATA = "\u7ef4\u5ea6\u4e3a\u7a7a\u4e0d\u53ef\u67e5\u770b";
    private Function<String, String> noAccessResion = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(EMPTY_NODATA);

    public int getOrder() {
        return 2;
    }

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        boolean entityNoData = (Boolean)param.getParams();
        if (entityNoData) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return this.readable(param, formSchemeKey, masterKey, formKey);
    }

    public String name() {
        return "entityNoData";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessResion.apply(code);
    }
}

