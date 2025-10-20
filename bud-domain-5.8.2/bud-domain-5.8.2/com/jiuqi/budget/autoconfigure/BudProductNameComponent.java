/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Configuration(value="BudProductNameComponent")
@PropertySource(value={"classpath:bud-product.properties"})
@Order(value=-2147483648)
public class BudProductNameComponent
implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(BudProductNameComponent.class);
    @Value(value="${jiuqi.gmc3.productName}")
    private String productName;

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public void afterPropertiesSet() {
        logger.info("BUDGET\u4ea7\u54c1\u540d\u79f0\uff1a{}", (Object)this.productName);
    }
}

