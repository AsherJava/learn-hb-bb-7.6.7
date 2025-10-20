/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  org.apache.commons.lang3.ObjectUtils
 */
package com.jiuqi.gcreport.oauth2.extend.dingtalk.util;

import com.jiuqi.gcreport.oauth2.extend.dingtalk.DingtalkCertifyExtInfo;
import com.jiuqi.gcreport.oauth2.util.JsonUtils;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class DingtalkNvwaCertifyUtil {
    private static final Logger logger = LoggerFactory.getLogger(DingtalkNvwaCertifyUtil.class);

    public static DingtalkCertifyExtInfo getExtInfoFromJson(NvwaCertify nvwaCertify) {
        Assert.notNull((Object)nvwaCertify, "\u9489\u9489\u5355\u70b9\u7c7b\u578b\u7684\u8ba4\u8bc1\u670d\u52a1\u672a\u914d\u7f6e\uff0c\u8bf7\u68c0\u67e5");
        String extraInfo = nvwaCertify.getExtraInfo();
        if (ObjectUtils.isEmpty((Object)extraInfo)) {
            logger.warn("\u6269\u5c55\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
            throw new IllegalArgumentException("\u6269\u5c55\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        DingtalkCertifyExtInfo ext = null;
        try {
            ext = JsonUtils.readValue(extraInfo, DingtalkCertifyExtInfo.class);
        }
        catch (Exception e) {
            logger.warn("\u8bf7\u68c0\u67e5\u8ba4\u8bc1\u670d\u52a1\u6269\u5c55\u4fe1\u606f\u914d\u7f6e\u662f\u5426\u6b63\u786e\uff0cextraInfo=" + extraInfo, e);
            throw new IllegalArgumentException("\u8bf7\u68c0\u67e5\u8ba4\u8bc1\u670d\u52a1\u6269\u5c55\u4fe1\u606f\u914d\u7f6e\u662f\u5426\u6b63\u786e", e);
        }
        Assert.hasText(ext.getRedirectUri(), "\u8ba4\u8bc1\u670d\u52a1\u6269\u5c55\u4fe1\u606fredirectUri\u4e0d\u80fd\u4e3a\u7a7a");
        return ext;
    }
}

