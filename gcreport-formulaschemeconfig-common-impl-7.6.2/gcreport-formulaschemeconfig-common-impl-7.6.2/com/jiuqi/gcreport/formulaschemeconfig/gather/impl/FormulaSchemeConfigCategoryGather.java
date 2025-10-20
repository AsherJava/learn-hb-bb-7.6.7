/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory
 */
package com.jiuqi.gcreport.formulaschemeconfig.gather.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.gather.IFormulaSchemeConfigCategoryGather;
import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory;
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
public class FormulaSchemeConfigCategoryGather
implements InitializingBean,
IFormulaSchemeConfigCategoryGather {
    @Autowired(required=false)
    private List<IFormulaSchemeConfigCategory> registeredCategoryList;
    private final List<IFormulaSchemeConfigCategory> schemeCategoryList = new ArrayList<IFormulaSchemeConfigCategory>();
    private final Map<String, IFormulaSchemeConfigCategory> schemeCategoryMap = new ConcurrentHashMap<String, IFormulaSchemeConfigCategory>(16);
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        this.schemeCategoryMap.clear();
        this.schemeCategoryList.clear();
        if (CollectionUtils.isEmpty(this.registeredCategoryList)) {
            this.registeredCategoryList = new ArrayList<IFormulaSchemeConfigCategory>();
        }
        this.registeredCategoryList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getCode())) {
                this.logger.warn("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u7c7b\u522b{}\u4ee3\u7801\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.schemeCategoryMap.containsKey(item.getCode())) {
                this.schemeCategoryMap.put(item.getCode(), (IFormulaSchemeConfigCategory)item);
                this.schemeCategoryList.add((IFormulaSchemeConfigCategory)item);
            } else {
                this.logger.warn("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u7c7b\u522b{}\u91cd\u590d\u6ce8\u518c\uff0c\u5df2\u81ea\u52a8\u8df3\u8fc7", (Object)item.getClass());
            }
        });
        this.schemeCategoryList.sort((p1, p2) -> p1.getOrder() - p2.getOrder());
    }

    @Override
    public List<IFormulaSchemeConfigCategory> list() {
        if (CollectionUtils.isEmpty(this.schemeCategoryList)) {
            this.logger.error("\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u7c7b\u522b\u83b7\u53d6\u4e3a\u7a7a");
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.schemeCategoryList);
    }

    @Override
    public IFormulaSchemeConfigCategory get(String schemeCategoryCode) {
        Assert.isNotEmpty((String)schemeCategoryCode, (String)"\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u7c7b\u522b\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        IFormulaSchemeConfigCategory bizModelCategory = this.schemeCategoryMap.get(schemeCategoryCode);
        Assert.isNotNull((Object)bizModelCategory, (String)"\u53d6\u6570\u4e0e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848\u7c7b\u522b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", (Object[])new Object[]{schemeCategoryCode});
        return bizModelCategory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

