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
import com.jiuqi.dc.mappingscheme.impl.define.IExtRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IExtRuleTypeGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IRuleTypeGather;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IExtRuleTypeGatherImpl
implements InitializingBean,
IExtRuleTypeGather {
    @Autowired(required=false)
    private List<IExtRuleType> extRuleTypeList;
    @Autowired(required=false)
    private IRuleTypeGather ruleTypeGather;
    private final Map<String, List<String>> extRuleTypeMap = new ConcurrentHashMap<String, List<String>>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.extRuleTypeList)) {
            this.extRuleTypeMap.clear();
            return;
        }
        this.extRuleTypeList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getPluginTypeCode())) {
                this.logger.warn("\u6269\u5c55\u6620\u5c04\u89c4\u5219\u7c7b\u578b{}\u63d2\u4ef6\u7c7b\u578b\u4ee3\u7801\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            StringUtils.split((String)item.getPluginTypeCode(), (String)";").forEach(pluginTypeCode -> {
                ArrayList codeList = this.extRuleTypeMap.get(pluginTypeCode);
                if (Objects.isNull(codeList)) {
                    codeList = CollectionUtils.newArrayList();
                }
                codeList.add(item.getCode());
                this.extRuleTypeMap.put((String)pluginTypeCode, codeList);
            });
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }

    @Override
    public List<IRuleType> getExtListByPluginCode(String pluginTypeCode) {
        List<String> codeList = this.extRuleTypeMap.get(pluginTypeCode);
        if (!CollectionUtils.isEmpty(codeList)) {
            return codeList.stream().map(e -> this.ruleTypeGather.getRuleTypeByCode((String)e)).collect(Collectors.toList());
        }
        return CollectionUtils.newArrayList();
    }
}

