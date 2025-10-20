/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.common.option;

import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.option.IBdeOptionDeclareItems;
import com.jiuqi.bde.common.option.IBdeSystemOptionItemHandler;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeOptionDeclareItemsGather {
    private List<IBdeOptionDeclareItems> registeredIBdeOptionDeclareItemsList;
    private List<IBdeSystemOptionItemHandler> registeredOptionItemHandlerList;
    private final Map<BdeOptionTypeEnum, List<IBdeOptionDeclareItems>> optionItemsDeclareMap = new ConcurrentHashMap<BdeOptionTypeEnum, List<IBdeOptionDeclareItems>>();
    private static final Logger logger = LoggerFactory.getLogger(BdeOptionDeclareItemsGather.class);

    public BdeOptionDeclareItemsGather(@Autowired(required=false) List<IBdeOptionDeclareItems> registeredIBdeOptionDeclareItemsList, @Autowired(required=false) List<IBdeSystemOptionItemHandler> registeredOptionItemHandlerList) {
        this.registeredIBdeOptionDeclareItemsList = registeredIBdeOptionDeclareItemsList;
        this.registeredOptionItemHandlerList = registeredOptionItemHandlerList;
        this.init();
    }

    public List<IBdeOptionDeclareItems> getOptionItemsDeclareByType(BdeOptionTypeEnum optionType) {
        return this.optionItemsDeclareMap.get((Object)optionType);
    }

    public List<IBdeSystemOptionItemHandler> listOptionItemHandler() {
        return this.registeredOptionItemHandlerList;
    }

    private void init() {
        try {
            this.reload();
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u9009\u9879\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    public void reload() {
        this.optionItemsDeclareMap.clear();
        if (CollectionUtils.isEmpty(this.registeredIBdeOptionDeclareItemsList)) {
            this.registeredIBdeOptionDeclareItemsList = new ArrayList<IBdeOptionDeclareItems>();
        }
        HashMap optionMap = new HashMap(this.registeredIBdeOptionDeclareItemsList.size());
        this.registeredIBdeOptionDeclareItemsList.forEach(item -> {
            if (item.getOptionType() == null) {
                logger.warn("\u7cfb\u7edf\u8bbe\u7f6e{}\uff0c\u7c7b\u522b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (StringUtils.isEmpty((String)item.getId()) || StringUtils.isEmpty((String)item.getTitle())) {
                logger.warn("\u7cfb\u7edf\u8bbe\u7f6e{}\uff0c\u6807\u8bc6\u6216\u6807\u9898\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            optionMap.computeIfAbsent(item.getOptionType(), k -> new ArrayList());
            ((List)optionMap.get((Object)item.getOptionType())).add(item);
        });
        for (Map.Entry optionTypeListEntry : optionMap.entrySet()) {
            this.optionItemsDeclareMap.put((BdeOptionTypeEnum)((Object)optionTypeListEntry.getKey()), ((List)optionTypeListEntry.getValue()).stream().sorted((a, b) -> a.getOrder() - b.getOrder()).collect(Collectors.toList()));
        }
        if (this.registeredOptionItemHandlerList == null) {
            this.registeredOptionItemHandlerList = new ArrayList<IBdeSystemOptionItemHandler>();
        }
    }
}

