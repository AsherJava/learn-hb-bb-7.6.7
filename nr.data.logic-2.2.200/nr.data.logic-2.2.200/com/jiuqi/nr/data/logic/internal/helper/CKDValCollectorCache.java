/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService
 *  com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.internal.helper;

import com.jiuqi.bsp.contentcheckrules.service.ContentCheckByGroupService;
import com.jiuqi.bsp.contentcheckrules.service.impl.ContentCheckServiceFactory;
import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidatorProvider;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import com.jiuqi.nr.data.logic.internal.helper.CKDValidateCollector;
import com.jiuqi.nr.data.logic.spi.ICKDRuleProvider;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.lang.NonNull;

public class CKDValCollectorCache {
    private final Map<String, CKDValidateCollector> cache = new HashMap<String, CKDValidateCollector>();
    private final List<ICheckDesValidatorProvider> checkDesValidatorProviders;
    private final ICKDRuleProvider ruleProvider;
    private final ContentCheckServiceFactory csFactory;

    public CKDValCollectorCache(@NonNull List<ICheckDesValidatorProvider> checkDesValidatorProviders) {
        this.checkDesValidatorProviders = checkDesValidatorProviders;
        this.ruleProvider = (ICKDRuleProvider)BeanUtil.getBean(ICKDRuleProvider.class);
        this.csFactory = (ContentCheckServiceFactory)BeanUtil.getBean(ContentCheckServiceFactory.class);
    }

    public CKDValidateCollector getCKDValidateCollector(CheckDesContext context) {
        String formulaSchemeKey = context.getFormulaSchemeKey();
        if (this.cache.containsKey(formulaSchemeKey)) {
            return this.cache.get(formulaSchemeKey);
        }
        String ruleGroupKey = this.ruleProvider.getRuleGroupKey(context);
        ContentCheckByGroupService checkService = this.csFactory.getCheckService();
        CKDValidateCollector ckdValidateCollector = new CKDValidateCollector(this.checkDesValidatorProviders, checkService, ruleGroupKey, context);
        this.cache.put(formulaSchemeKey, ckdValidateCollector);
        return ckdValidateCollector;
    }
}

