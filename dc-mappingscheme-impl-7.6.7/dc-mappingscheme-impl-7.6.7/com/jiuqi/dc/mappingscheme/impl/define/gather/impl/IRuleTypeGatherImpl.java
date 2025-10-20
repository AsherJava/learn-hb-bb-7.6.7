/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.common.RuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IExtRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IRuleTypeGatherImpl
implements InitializingBean,
IRuleTypeGather {
    @Autowired(required=false)
    private List<IExtRuleType> ruleTypeList;
    private final Map<String, IRuleType> ruleTypeMap = new ConcurrentHashMap<String, IRuleType>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        Stream.of(RuleType.values()).forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u6620\u5c04\u89c4\u5219\u7c7b\u578b\u63d2\u4ef6{}\u4ee3\u7801\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.ruleTypeMap.computeIfAbsent(item.getCode(), k -> item);
        });
        if (!CollectionUtils.isEmpty(this.ruleTypeList)) {
            this.ruleTypeList.forEach(item -> {
                if (StringUtils.isEmpty((String)item.getCode())) {
                    this.logger.warn("\u6620\u5c04\u89c4\u5219\u7c7b\u578b\u63d2\u4ef6{}\u4ee3\u7801\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                    return;
                }
                this.ruleTypeMap.computeIfAbsent(item.getCode(), k -> item);
            });
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    @Override
    public IRuleType getRuleTypeByCode(String code) {
        return this.ruleTypeMap.get(code);
    }

    @Override
    public List<IRuleType> listAll() {
        return new ArrayList<IRuleType>(this.ruleTypeMap.values());
    }
}

