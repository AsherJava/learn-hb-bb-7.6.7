/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 */
package com.jiuqi.bde.common.certify;

import com.jiuqi.common.base.util.Assert;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BdeRequestCertifyConfig {
    private static String appName;
    private static Set<String> certifyNameSet;
    private static String moduleName;

    public static String getAppName() {
        return appName;
    }

    @Value(value="${jiuqi.bde.certification.app-name:BDE}")
    public void setAppName(String serviceName) {
        appName = serviceName.toLowerCase();
    }

    public static Set<String> getCertifyName() {
        return certifyNameSet;
    }

    @Value(value="${jiuqi.bde.certify.certification-name:CLIENT}")
    public void setCertifyName(String certifyName) {
        Assert.isNotEmpty((String)certifyName, (String)"\u8ba4\u8bc1\u670d\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String[] certifyNameArr = certifyName.split(",");
        certifyNameSet = new HashSet<String>();
        for (String certifyNameStr : certifyNameArr) {
            certifyNameSet.add(certifyNameStr);
        }
    }

    public static String getModuleName() {
        return moduleName;
    }

    @Value(value="${jiuqi.bde.certify.module-name:CLIENT}")
    public void setModuleName(String moduleName) {
        BdeRequestCertifyConfig.moduleName = moduleName;
    }
}

