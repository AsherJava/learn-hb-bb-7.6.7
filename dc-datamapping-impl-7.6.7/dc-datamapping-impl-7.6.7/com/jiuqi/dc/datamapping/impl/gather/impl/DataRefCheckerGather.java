/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.datamapping.impl.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.datamapping.impl.gather.IDataRefCheckerGather;
import com.jiuqi.dc.datamapping.impl.intf.IDataRefChecker;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataRefCheckerGather
implements InitializingBean,
IDataRefCheckerGather {
    @Autowired(required=false)
    private List<IDataRefChecker> checkerList;
    private Map<String, IDataRefChecker> checkerMap = CollectionUtils.newHashMap();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.checkerList)) {
            this.checkerMap.clear();
            return;
        }
        this.checkerList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getTableName())) {
                this.logger.warn("\u6570\u636e\u6620\u5c04\u68c0\u67e5\u5668\u8868\u540d\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.checkerMap.containsKey(item.getTableName())) {
                this.checkerMap.put(item.getTableName(), (IDataRefChecker)item);
            }
        });
    }

    @Override
    public IDataRefChecker getByTableName(String tableName) {
        return Optional.ofNullable(this.checkerMap.get(tableName)).orElse(this.checkerMap.get("BASEDATA"));
    }

    @Override
    public List<IDataRefChecker> getCheckerList() {
        return this.checkerMap.values().stream().collect(Collectors.toList());
    }
}

