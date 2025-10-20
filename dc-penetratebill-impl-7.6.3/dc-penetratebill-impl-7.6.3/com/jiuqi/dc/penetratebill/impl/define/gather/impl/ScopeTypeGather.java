/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.penetratebill.IScopeType
 */
package com.jiuqi.dc.penetratebill.impl.define.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.penetratebill.IScopeType;
import com.jiuqi.dc.penetratebill.impl.define.gather.IScopeTypeGather;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScopeTypeGather
implements InitializingBean,
IScopeTypeGather {
    @Autowired(required=false)
    private List<IScopeType> scopeTypeList;
    private final Map<String, IScopeType> scopeTypeMap = new ConcurrentHashMap<String, IScopeType>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.scopeTypeMap.clear();
        if (CollectionUtils.isEmpty(this.scopeTypeList)) {
            this.scopeTypeList = new ArrayList<IScopeType>();
        }
        this.scopeTypeList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u8054\u67e5\u5355\u636e\u65b9\u6848\u9002\u7528\u8303\u56f4\u7c7b\u578b{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.scopeTypeMap.containsKey(item.getCode())) {
                this.scopeTypeMap.put(item.getCode(), (IScopeType)item);
            } else {
                this.logger.warn("\u8054\u67e5\u5355\u636e\u65b9\u6848\u9002\u7528\u8303\u56f4\u7c7b\u578b{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
        });
    }

    @Override
    public IScopeType getScopeType(String code) {
        return this.scopeTypeMap.get(code);
    }

    @Override
    public List<IScopeType> list() {
        return Collections.unmodifiableList(new ArrayList<IScopeType>(this.scopeTypeMap.values()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

