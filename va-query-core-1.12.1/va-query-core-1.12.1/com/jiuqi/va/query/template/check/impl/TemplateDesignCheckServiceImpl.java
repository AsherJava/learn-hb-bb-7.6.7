/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 */
package com.jiuqi.va.query.template.check.impl;

import com.jiuqi.va.query.template.check.QueryDesignCheck;
import com.jiuqi.va.query.template.check.TemplateDesignCheckService;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TemplateDesignCheckServiceImpl
implements TemplateDesignCheckService {
    @Autowired
    private List<QueryDesignCheck> queryDesignChecks;

    @Override
    public List<QueryPluginCheckVO> checkPlugin(QueryTemplate queryTemplate) {
        ArrayList<QueryPluginCheckVO> checkVOS = new ArrayList<QueryPluginCheckVO>();
        List sorted = this.queryDesignChecks.stream().sorted(Comparator.comparingInt(QueryDesignCheck::order)).collect(Collectors.toList());
        for (QueryDesignCheck queryDesignCheck : sorted) {
            QueryPluginCheckVO queryPluginCheckVO = queryDesignCheck.checkPlugin(queryTemplate);
            if (queryPluginCheckVO == null || CollectionUtils.isEmpty(queryPluginCheckVO.getResult())) continue;
            checkVOS.add(queryPluginCheckVO);
        }
        return checkVOS;
    }
}

