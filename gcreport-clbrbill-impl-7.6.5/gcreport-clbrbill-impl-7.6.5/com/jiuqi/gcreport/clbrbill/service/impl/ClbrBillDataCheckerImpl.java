/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 */
package com.jiuqi.gcreport.clbrbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillDataChecker;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ClbrBillDataCheckerImpl
implements ClbrBillDataChecker {
    @Override
    public String findExistSubBillData(Map<String, Object> billRowData) {
        String srcBillCode = ConverterUtils.getAsString((Object)billRowData.get("SRCBILLCODE"));
        if (StringUtils.isEmpty((String)srcBillCode)) {
            throw new BusinessRuntimeException("\u5171\u4eab\u5355\u636e\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String billDefine = ConverterUtils.getAsString((Object)billRowData.get("BILLDEFINE"));
        if (StringUtils.isEmpty((String)billDefine)) {
            throw new BusinessRuntimeException("\u5355\u636e\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a");
        }
        List maps = EntNativeSqlDefaultDao.getInstance().selectMap("select BILLCODE from GC_CLBRSRCBILLITEM where SRCBILLCODE = ? and BILLDEFINE = ?", new Object[]{srcBillCode, billDefine});
        if (!CollectionUtils.isEmpty((Collection)maps)) {
            return ConverterUtils.getAsString(((Map)maps.get(0)).get("BILLCODE"));
        }
        return null;
    }
}

