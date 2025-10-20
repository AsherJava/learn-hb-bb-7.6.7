/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.authz.LicenceException
 *  com.jiuqi.bi.authz.licence.LicenceManager
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.springframework.http.HttpStatus
 */
package com.jiuqi.nvwa.sf.adapter.spring.aop;

import com.jiuqi.bi.authz.LicenceException;
import com.jiuqi.bi.authz.licence.LicenceManager;
import com.jiuqi.nvwa.sf.Framework;
import com.jiuqi.nvwa.sf.anno.Licence;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.OutputStream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LicenceAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object licenceCheck(ProceedingJoinPoint point, Licence licence) throws Throwable {
        try {
            Framework framework = Framework.getInstance();
            LicenceManager licenceManager = framework.getLicenceManager();
            Object funcPointValue = licenceManager.getFuncPointValue(framework.getProductId(), licence.module(), licence.point());
            if (Boolean.TRUE.equals(funcPointValue)) {
                return point.proceed();
            }
        }
        catch (LicenceException e) {
            this.logger.error(e.getMessage());
        }
        StringBuffer buffer = new StringBuffer();
        OutputStream outputStream = RequestContextUtil.getOutputStream();
        try {
            if (null != outputStream) {
                RequestContextUtil.setResponseStatus((int)HttpStatus.INTERNAL_SERVER_ERROR.value());
                buffer.append("{\"code\":\"500\",\"message\":\"\u8be5\u529f\u80fd\u6ca1\u6709\u6a21\u5757\u8bb8\u53ef\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\",\"datas\":\"");
                buffer.append(licence.module());
                buffer.append(" ");
                buffer.append(licence.point());
                buffer.append("\"}");
                RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
                RequestContextUtil.setResponseContentType((String)"application/json");
                outputStream.write(buffer.toString().getBytes("UTF-8"));
                outputStream.flush();
            }
        }
        finally {
            if (null != outputStream) {
                outputStream.close();
            }
        }
        this.logger.error("\u529f\u80fd\u70b9[{}:{}]\u672a\u88ab\u6388\u6743", (Object)licence.module(), (Object)licence.point());
        return buffer.toString();
    }

    @Around(value="@within(licence)")
    public Object aroundClass(ProceedingJoinPoint point, Licence licence) throws Throwable {
        return this.licenceCheck(point, licence);
    }

    @Around(value="@annotation(licence)")
    public Object aroundMethod(ProceedingJoinPoint point, Licence licence) throws Throwable {
        return this.licenceCheck(point, licence);
    }
}

