/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.init;

import com.jiuqi.gcreport.definition.impl.anno.DBInitEnum;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.basic.init.EntityTableCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.lang.NonNull;

public class DbBeanFactoryPostProcessor
implements BeanFactoryPostProcessor {
    private Logger logger = LoggerFactory.getLogger(DbBeanFactoryPostProcessor.class);

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.processTableBeans(beanFactory);
        this.processDataInitBeans(beanFactory);
    }

    private void processTableBeans(ConfigurableListableBeanFactory beanFactory) {
        String[] beanNames;
        for (String beanName : beanNames = beanFactory.getBeanNamesForAnnotation(DBTable.class)) {
            BeanDefinition beanDefine = beanFactory.getBeanDefinition(beanName);
            if (beanDefine instanceof ScannedGenericBeanDefinition) {
                Class<?> cls = beanFactory.getType(beanName);
                assert (cls != null);
                if (!BaseEntity.class.isAssignableFrom(cls)) {
                    this.logger.warn("bean \u201c{}\u201d\u6709\u6ce8\u89e3@DBTable\uff0c\u4f46\u662f\u6ca1\u6709\u7ee7\u627fAbstractIdRecverEntity\uff0c\u65e0\u6cd5\u540c\u6b65\u5efa\u6a21", (Object)beanName);
                    continue;
                }
                BaseEntity bean = beanFactory.getBean(beanName, BaseEntity.class);
                EntityTableCollector.getInstance().addEntityType(bean, cls.getAnnotation(DBTable.class));
            }
            if (!(beanFactory instanceof BeanDefinitionRegistry)) continue;
            ((BeanDefinitionRegistry)((Object)beanFactory)).removeBeanDefinition(beanName);
        }
    }

    private void processDataInitBeans(ConfigurableListableBeanFactory beanFactory) {
        String[] enumNames;
        for (String beanName : enumNames = beanFactory.getBeanNamesForAnnotation(DBInitEnum.class)) {
            BeanDefinition beanDefine = beanFactory.getBeanDefinition(beanName);
            if (beanDefine instanceof ScannedGenericBeanDefinition) {
                Class<?> cls = beanFactory.getType(beanName);
                assert (cls != null);
                EntityTableCollector.getInstance().addInitDataType(cls);
            }
            if (!(beanFactory instanceof BeanDefinitionRegistry)) continue;
            ((BeanDefinitionRegistry)((Object)beanFactory)).removeBeanDefinition(beanName);
        }
    }
}

