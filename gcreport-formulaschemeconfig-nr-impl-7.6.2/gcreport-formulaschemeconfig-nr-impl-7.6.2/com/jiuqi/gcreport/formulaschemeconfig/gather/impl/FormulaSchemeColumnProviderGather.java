/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeColumnProvider
 */
package com.jiuqi.gcreport.formulaschemeconfig.gather.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.formulaschemeconfig.gather.IFormulaSchemeColumnProviderGather;
import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeColumnProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormulaSchemeColumnProviderGather
implements InitializingBean,
IFormulaSchemeColumnProviderGather {
    @Autowired(required=false)
    private List<IFormulaSchemeColumnProvider> registeredProviderList;
    private final List<String> columnProviderList = new ArrayList<String>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.columnProviderList.clear();
        if (CollectionUtils.isEmpty(this.registeredProviderList)) {
            this.logger.warn("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u8868\u683c\u5217\u63d0\u4f9b\u5668\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c");
        } else {
            this.registeredProviderList.forEach(item -> this.columnProviderList.add(item.getCode()));
        }
    }

    @Override
    public List<String> list() {
        if (CollectionUtils.isEmpty(this.columnProviderList)) {
            this.logger.error("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u5217\u83b7\u53d6\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.columnProviderList);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

