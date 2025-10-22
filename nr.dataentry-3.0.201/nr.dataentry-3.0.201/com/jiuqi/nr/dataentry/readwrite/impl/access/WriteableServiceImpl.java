/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.common.AccessLevel
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class WriteableServiceImpl
implements IDataExtendAccessItemService {
    private static final String WRITE_ABLE_MSG = "\u5f53\u524d\u6a21\u5f0f\u4e3a\u53ea\u8bfb\uff0c\u4e0d\u53ef\u5199";
    private Function<String, String> noAccessResion = code -> Optional.ofNullable(code).filter(e -> e.equals("1")).orElse(WRITE_ABLE_MSG);

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public int getOrder() {
        return -2;
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        JtableContext context = (JtableContext)param.getParams();
        Map variableMap = context.getVariableMap();
        if (variableMap != null && variableMap.get("writeable") != null && ((Boolean)variableMap.get("writeable")).booleanValue()) {
            return new AccessCode(this.name());
        }
        return new AccessCode(this.name(), "2");
    }

    public String name() {
        return "writeable";
    }

    public IAccessMessage getAccessMessage() {
        return code -> this.noAccessResion.apply(code);
    }

    public boolean isServerAccess() {
        return true;
    }

    public AccessLevel getLevel() {
        return AccessLevel.UNIT;
    }
}

