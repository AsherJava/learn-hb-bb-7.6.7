/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.gcoppunit.action.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.inputdata.gcoppunit.GcOppUnitCondition;
import com.jiuqi.gcreport.inputdata.gcoppunit.service.GcOppUnitQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GcOppUnitPageAction
extends AbstractGcActionItem {
    @Autowired
    private GcOppUnitQueryService service;

    protected GcOppUnitPageAction() {
        super("gcOppUnitPageAction", "\u5bf9\u65b9\u5355\u4f4d", "\u663e\u793a\u5bf9\u65b9\u5355\u4f4d\u4e3a\u5f53\u524d\u5b9a\u4f4d\u5355\u4f4d\u53ca\u4e0b\u7ea7\u5355\u4f4d\u7684\u5185\u90e8\u8868\u6570\u636e", "#icon-_GJYdaima");
    }

    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcActionItemEnv actionItemEnv) {
        GcOppUnitCondition condition = (GcOppUnitCondition)JsonUtils.readValue((String)actionItemEnv.getActionParam(), GcOppUnitCondition.class);
        return this.service.excute(condition);
    }
}

