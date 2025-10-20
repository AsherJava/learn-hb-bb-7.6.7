/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.organization.exchange.auth.OrgRuleResourceCategory
 *  com.jiuqi.va.organization.service.impl.OrgAuthServiceImpl
 */
package com.jiuqi.gcreport.listedcompanyauthz.rule;

import com.jiuqi.gcreport.listedcompanyauthz.rule.ListedCompanyOrgAuthServiceImpl;
import com.jiuqi.gcreport.listedcompanyauthz.rule.ListedCompanyOrgRuleResourceCategory;
import com.jiuqi.va.organization.exchange.auth.OrgRuleResourceCategory;
import com.jiuqi.va.organization.service.impl.OrgAuthServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public class ListedCompanyFactoryPostProcessor
implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.clearnBeans(beanFactory, OrgAuthServiceImpl.class, ListedCompanyOrgAuthServiceImpl.class);
        this.clearnBeans(beanFactory, OrgRuleResourceCategory.class, ListedCompanyOrgRuleResourceCategory.class);
    }

    private void clearnBeans(ConfigurableListableBeanFactory beanFactory, Class<?> deleteClz, Class<?> excludeClz) {
        String[] allOrgs = beanFactory.getBeanNamesForType(deleteClz);
        String[] listOrgs = beanFactory.getBeanNamesForType(excludeClz);
        ArrayList<String> beanNames = new ArrayList<String>();
        beanNames.addAll(Arrays.asList(allOrgs));
        beanNames.removeAll(Arrays.asList(listOrgs));
        for (String beanName : beanNames) {
            if (!(beanFactory instanceof BeanDefinitionRegistry)) continue;
            ((BeanDefinitionRegistry)((Object)beanFactory)).removeBeanDefinition(beanName);
        }
    }
}

