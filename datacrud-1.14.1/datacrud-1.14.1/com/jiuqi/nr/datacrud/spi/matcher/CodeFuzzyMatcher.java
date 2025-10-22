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
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=70)
public class CodeFuzzyMatcher
implements IEntityRowMatcher {
    @Override
    public IEntityRow match(MatchSource matchSource, MatchValue matchValue) {
        if (matchSource.isEntityMatchAll()) {
            return null;
        }
        IEntityRow entityRow = null;
        block0: for (String innerValue : matchValue.getInnerValues()) {
            List<IEntityRow> allRows = matchSource.getEntityTableWrapper().getAllRows();
            innerValue = innerValue.toUpperCase();
            for (IEntityRow row : allRows) {
                if (!row.getCode().toUpperCase().contains(innerValue)) continue;
                entityRow = row;
                continue block0;
            }
        }
        return entityRow;
    }
}

