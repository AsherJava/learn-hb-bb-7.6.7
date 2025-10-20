/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 */
package com.jiuqi.va.biz.encrypt;

import com.jiuqi.va.biz.encrypt.LcdpEncryptDES;
import com.jiuqi.va.biz.encrypt.LcdpEncryptProperties;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
public class CustomAspect {
    @Autowired
    private LcdpEncryptProperties lcdpEncryptProperties;

    @Before(value="execution(* com.jiuqi.va.feign.client.BaseDataClient.exist(..)) || execution(* com.jiuqi.va.feign.client.BaseDataClient.list(..))")
    public void beforeBaseDataMethodExecution(JoinPoint joinPoint) {
        if (this.getPrimaryImpl(BaseDataClient.class) == null) {
            this.modifyParameters(joinPoint.getArgs());
        }
    }

    @Before(value="execution(* com.jiuqi.va.feign.client.OrgDataClient.list(..))")
    public void beforeOrgDataMethodExecution(JoinPoint joinPoint) {
        if (this.getPrimaryImpl(OrgDataClient.class) == null) {
            this.modifyParameters(joinPoint.getArgs());
        }
    }

    private void modifyParameters(Object[] args) {
        Object expression;
        if (!this.lcdpEncryptProperties.isEnable()) {
            return;
        }
        Map dto = (Map)args[0];
        if (dto.containsKey("vaBizFormula") && !ObjectUtils.isEmpty(expression = dto.get("expression"))) {
            String encrypt = LcdpEncryptDES.encrypt(String.valueOf(expression));
            dto.put("expression", encrypt);
            dto.put("decrypted", true);
        }
    }

    public <T> T getPrimaryImpl(Class<T> feignClient) {
        Map<String, T> beans = ApplicationContextRegister.getApplicationContext().getBeansOfType(feignClient);
        if (beans != null && beans.size() > 0) {
            for (T t : beans.values()) {
                Class<?> targetClass = AopProxyUtils.ultimateTargetClass(t);
                if (!targetClass.isAnnotationPresent(Primary.class)) continue;
                return t;
            }
        }
        return null;
    }
}

