/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.QueryPluginCheckVO
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 */
package com.jiuqi.va.query.template.check;

import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import java.util.List;

public interface TemplateDesignCheckService {
    public List<QueryPluginCheckVO> checkPlugin(QueryTemplate var1);
}

