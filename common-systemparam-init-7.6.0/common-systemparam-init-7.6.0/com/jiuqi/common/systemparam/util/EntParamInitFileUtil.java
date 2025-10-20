/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.common.systemparam.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.systemparam.config.EntParamInitBaseUrlConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ClassPathResource;

public class EntParamInitFileUtil {
    public static String getFullFilePath(String fileUrl, String prodLine) {
        String paramInitBasePath = ((EntParamInitBaseUrlConfig)SpringContextUtils.getBean(EntParamInitBaseUrlConfig.class)).getParamInitBasePath();
        if (StringUtils.isEmpty((String)paramInitBasePath)) {
            return fileUrl;
        }
        String fullFileName = paramInitBasePath + prodLine + File.separator + fileUrl;
        File file = new File(fullFileName);
        if (file.exists()) {
            return fullFileName;
        }
        throw new BusinessRuntimeException("\u672a\u627e\u5230\u8def\u5f84" + fullFileName + "\u4e0b\u7684\u53c2\u6570\u6587\u4ef6\uff0c\u8bf7\u68c0\u67e5");
    }

    public static InputStream getResourceInputStream(String fileUrl) {
        try {
            File file = new File(fileUrl);
            if (file.exists()) {
                return new FileInputStream(file);
            }
            ClassPathResource localFileResource = new ClassPathResource(fileUrl);
            if (!localFileResource.exists()) {
                throw new BusinessRuntimeException("\u672a\u627e\u5230\u9ed8\u8ba4\u8def\u5f84\u4e0b\u7684" + fileUrl + "\u53c2\u6570\u6587\u4ef6\uff0c\u8bf7\u68c0\u67e5");
            }
            return localFileResource.getInputStream();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u8def\u5f84" + fileUrl + "\u4e0b\u7684\u53c2\u6570\u6587\u4ef6\uff0c\u8bf7\u68c0\u67e5", (Throwable)e);
        }
    }
}

