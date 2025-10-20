/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.certification.bean.NvwaCertify
 *  com.jiuqi.nvwa.certification.dao.INvwaCertifyDao
 */
package com.jiuqi.gcreport.oauth2.service.impl;

import com.jiuqi.gcreport.oauth2.dao.NvwaCertifyDaoExtend;
import com.jiuqi.gcreport.oauth2.service.GcNvwaCertifyService;
import com.jiuqi.nvwa.certification.bean.NvwaCertify;
import com.jiuqi.nvwa.certification.dao.INvwaCertifyDao;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service(value="gcNvwaCertifyService")
public class GcNvwaCertifyServiceImpl
implements GcNvwaCertifyService {
    private static final Logger logger = LoggerFactory.getLogger(GcNvwaCertifyServiceImpl.class);
    @Autowired
    @Qualifier(value="com.jiuqi.gcreport.oauth2.dao.impl.NvwaCertifyDaoExtend")
    private NvwaCertifyDaoExtend nvwaCertifyDaoExtend;
    @Lazy
    @Autowired
    private INvwaCertifyDao nvwaCertifyDao;

    @Override
    public NvwaCertify getGcOAuth2TypeNvwaCertify(String type) {
        List<NvwaCertify> ncList = this.nvwaCertifyDaoExtend.getNvwaCertifyByType(type);
        if (ncList.size() == 1) {
            return ncList.get(0);
        }
        if (ncList.size() >= 1) {
            logger.warn("\u6309\u7c7b\u578b[{}]\u67e5\u627e\u8ba4\u8bc1\u670d\u52a1\u7c7b\u578b\u53d1\u73b0\u591a\u4e2a\u914d\u7f6e\uff0c\u4e3a\u4fdd\u8bc1\u5386\u53f2\u517c\u5bb9\u53d6\u7b2c\u4e00\u4e2a\u914d\u7f6e\u9879\u8fd4\u56de\uff0c\u5982\u6709\u95ee\u9898\u8bf7\u8003\u8651\u4f7f\u7528getNvwaCertifyByTypeFirstIfMoreUseCode()", (Object)type);
            return ncList.get(0);
        }
        logger.warn("\u6309\u7c7b\u578b[{}]\u67e5\u627e\u8ba4\u8bc1\u670d\u52a1\u7c7b\u578b\u6ca1\u627e\u5230\u76f8\u5173\u914d\u7f6e", (Object)type);
        return null;
    }

    @Override
    public NvwaCertify getNvwaCertifyByTypeFirstIfMoreUseCode(String type, String code) {
        List<NvwaCertify> ncList = this.nvwaCertifyDaoExtend.getNvwaCertifyByType(type);
        if (ncList.size() == 1) {
            return ncList.get(0);
        }
        if (ncList.size() > 1 && StringUtils.hasText(code)) {
            for (NvwaCertify nc : ncList) {
                if (!code.equals(nc.getCode())) continue;
                return nc;
            }
        }
        logger.warn("\u6309\u7c7b\u578b[{}]\u4e0ecode[{}]\u67e5\u627e\u8ba4\u8bc1\u670d\u52a1\u7c7b\u578b\u6ca1\u627e\u5230\u76f8\u5173\u914d\u7f6e\uff0cncList={}", type, code, ncList);
        return null;
    }

    @Override
    public NvwaCertify getNvwaCertifyByCode(String code) {
        return this.nvwaCertifyDao.selectByCode(code);
    }
}

