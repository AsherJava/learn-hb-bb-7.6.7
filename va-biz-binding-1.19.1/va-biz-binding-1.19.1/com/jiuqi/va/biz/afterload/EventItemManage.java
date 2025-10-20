/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.afterload;

import com.jiuqi.va.biz.afterload.EventItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class EventItemManage {
    @Autowired(required=false)
    private List<EventItem> eventItems;

    public List<EventItem> getEventItems() {
        if (CollectionUtils.isEmpty(this.eventItems)) {
            return new ArrayList<EventItem>();
        }
        return this.eventItems.stream().sorted(Comparator.comparingInt(EventItem::getSortNum)).collect(Collectors.toList());
    }
}

