/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.bizmodel.define.gather.impl;

import com.jiuqi.bde.bizmodel.define.gather.IBaseDataMatcherGather;
import com.jiuqi.bde.bizmodel.define.match.IBaseDataMatcher;
import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BdeBaseDataMatcherGather
implements IBaseDataMatcherGather {
    private List<IBaseDataMatcher> registeredMatcherList;
    private static final Logger LOGGER = LoggerFactory.getLogger(BdeBaseDataMatcherGather.class);
    private static Map<String, IBaseDataMatcher> baseDataMatcherMap = new ConcurrentHashMap<String, IBaseDataMatcher>();

    public BdeBaseDataMatcherGather(@Autowired(required=false) List<IBaseDataMatcher> registeredMatcherList) {
        this.registeredMatcherList = registeredMatcherList;
        this.init();
    }

    private void init() {
        try {
            baseDataMatcherMap.clear();
            if (CollectionUtils.isEmpty(this.registeredMatcherList)) {
                this.registeredMatcherList = new ArrayList<IBaseDataMatcher>();
            }
            baseDataMatcherMap = this.registeredMatcherList.stream().collect(Collectors.toMap(IBaseDataMatcher::getMatchCode, Function.identity(), (o1, o2) -> o2));
        }
        catch (Exception e) {
            LOGGER.error("\u89c4\u5219\u5339\u914d\u5668\u521d\u59cb\u5316\u51fa\u73b0\u9519\u8bef", e);
        }
    }

    @Override
    public IBaseDataMatcher getSubjectMather() {
        return baseDataMatcherMap.get(MatchRuleEnum.TREE.getCode()) == null ? baseDataMatcherMap.get(MatchRuleEnum.LIKE.getCode()) : baseDataMatcherMap.get(MatchRuleEnum.TREE.getCode());
    }

    @Override
    public IBaseDataMatcher getAssistMatcher(String matchRule) {
        return baseDataMatcherMap.get(matchRule);
    }
}

