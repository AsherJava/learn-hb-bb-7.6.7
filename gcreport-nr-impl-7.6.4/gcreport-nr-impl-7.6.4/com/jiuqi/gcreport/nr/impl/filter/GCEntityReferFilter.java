/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.ext.filter.IEntityReferFilter
 *  com.jiuqi.nr.entity.model.IEntityRefer
 */
package com.jiuqi.gcreport.nr.impl.filter;

import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.model.IEntityRefer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class GCEntityReferFilter
implements IEntityReferFilter {
    public List<IEntityRefer> getFilterAttribute(List<IEntityRefer> refs) {
        ArrayList<IEntityRefer> filterRefer = new ArrayList<IEntityRefer>(refs.size());
        Map<String, List<IEntityRefer>> referMap = refs.stream().collect(Collectors.groupingBy(IEntityRefer::getReferEntityId));
        referMap.forEach((key, value) -> {
            if ("MD_CURRENCY".equalsIgnoreCase((String)key)) {
                Optional<IEntityRefer> currncyId = value.stream().filter(iEntityRefer -> "CURRENCYIDS".equalsIgnoreCase(iEntityRefer.getOwnField())).findFirst();
                if (currncyId.isPresent()) {
                    filterRefer.add(currncyId.get());
                }
            } else {
                filterRefer.addAll((Collection<IEntityRefer>)value);
            }
        });
        return filterRefer;
    }
}

