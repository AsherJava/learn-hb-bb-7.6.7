/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.datacrud.impl.crud.inner;

import com.jiuqi.nr.datacrud.api.IEntityRowSearcher;
import com.jiuqi.nr.datacrud.spi.IEntityRowMatcher;
import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.datacrud.spi.entity.MatchValue;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityRowSearcher
implements IEntityRowSearcher {
    @Autowired
    private List<IEntityRowMatcher> entityRowMatchers;

    @Override
    public IEntityRow search(MatchSource matchSource, String value) {
        MatchValue matchValue = new MatchValue();
        matchValue.setValue(value);
        List<String> list = this.splitInputData(value);
        matchValue.setInnerValues(list);
        for (IEntityRowMatcher entityRowMatcher : this.entityRowMatchers) {
            IEntityRow match = entityRowMatcher.match(matchSource, matchValue);
            if (match == null) continue;
            return match;
        }
        return null;
    }

    public List<String> splitInputData(String data) {
        ArrayList<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); ++i) {
            char currentChar = data.charAt(i);
            sb.append(currentChar);
            if (i < data.length() - 1) {
                char next = data.charAt(i + 1);
                if (currentChar == '|' && next == currentChar) continue;
            }
            if (i > 0) {
                char pre = data.charAt(i - 1);
                if (currentChar == '|' && pre == currentChar) continue;
            }
            if (currentChar != ';' && currentChar != '|') continue;
            sb.setLength(sb.length() - 1);
            result.add(sb.toString());
            sb.setLength(0);
        }
        result.add(sb.toString());
        return result;
    }
}

