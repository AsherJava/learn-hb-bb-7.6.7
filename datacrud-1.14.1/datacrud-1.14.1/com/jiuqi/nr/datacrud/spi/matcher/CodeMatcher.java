/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.datacrud.spi.matcher;

import com.jiuqi.nr.datacrud.spi.IEntityRowMatcher;
import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.datacrud.spi.entity.MatchValue;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=30)
public class CodeMatcher
implements IEntityRowMatcher {
    @Override
    public IEntityRow match(MatchSource matchSource, MatchValue matchValue) {
        IEntityRow entityRow = null;
        for (String innerValue : matchValue.getInnerValues()) {
            entityRow = matchSource.getEntityTableWrapper().findByCode(innerValue);
            if (entityRow == null) continue;
            break;
        }
        return entityRow;
    }
}

