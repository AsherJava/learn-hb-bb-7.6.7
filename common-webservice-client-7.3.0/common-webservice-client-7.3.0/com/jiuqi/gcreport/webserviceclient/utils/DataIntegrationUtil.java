/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.common.crypto.Crypto
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.etl.common.EtlServeEntity
 *  com.jiuqi.nr.etl.service.internal.EtlServeEntityDao
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 */
package com.jiuqi.gcreport.webserviceclient.utils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nr.common.crypto.Crypto;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.etl.common.EtlServeEntity;
import com.jiuqi.nr.etl.service.internal.EtlServeEntityDao;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataIntegrationUtil {
    private static Logger logger = LoggerFactory.getLogger(DataIntegrationUtil.class);

    public static Map<String, DimensionValue> getDimensionSet(ExecuteContext context, String taskKey) throws AutomationException {
        String periodStr = context.getParameterValue("DATATIME");
        String currencyCode = context.getParameterValue("CURRENCY");
        String orgCodes = context.getParameterValue("UNITCODE");
        String orgType = context.getParameterValue("ORGTYPE");
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName("DATATIME");
        dimensionValue.setValue(periodStr);
        dimensionSet.put("DATATIME", dimensionValue);
        dimensionValue = new DimensionValue();
        dimensionValue.setName("MD_ORG");
        dimensionValue.setValue(orgCodes);
        dimensionSet.put("MD_ORG", dimensionValue);
        dimensionValue = new DimensionValue();
        dimensionValue.setName("MD_CURRENCY");
        dimensionValue.setValue(currencyCode);
        dimensionSet.put("MD_CURRENCY", dimensionValue);
        dimensionValue = new DimensionValue();
        dimensionValue.setName("MD_GCORGTYPE");
        dimensionValue.setValue(orgType);
        dimensionSet.put("MD_GCORGTYPE", dimensionValue);
        if (DataIntegrationUtil.isExisAdjType(taskKey)) {
            dimensionValue = new DimensionValue();
            dimensionValue.setName("MD_GCADJTYPE");
            dimensionValue.setValue("BEFOREADJ");
            dimensionSet.put("MD_GCADJTYPE", dimensionValue);
        }
        return dimensionSet;
    }

    public static boolean isExisAdjType(String taskId) {
        RuntimeViewController runtimeViewController = (RuntimeViewController)SpringContextUtils.getBean(RuntimeViewController.class);
        TaskDefine taskDefine = runtimeViewController.queryTaskDefine(taskId);
        String dimes = taskDefine.getDims();
        return !com.jiuqi.common.base.util.StringUtils.isEmpty((String)dimes) && dimes.indexOf("MD_GCADJTYPE") > -1;
    }

    public static boolean validity(String url, String userName) {
        if (StringUtils.isEmpty((String)url)) {
            logger.warn("\u6570\u636e\u96c6\u6210\u670d\u52a1\u5730\u5740\u4e3a\u7a7a\uff01");
            return false;
        }
        if (StringUtils.isEmpty((String)userName)) {
            logger.warn("\u6570\u636e\u96c6\u6210\u670d\u52a1\u7528\u6237\u540d\u4e3a\u7a7a\uff01");
            return false;
        }
        return true;
    }

    public static EtlServeEntity getEtlServeEntity() {
        Optional etlServeEntity = ((EtlServeEntityDao)SpringContextUtils.getBean(EtlServeEntityDao.class)).getServerInfo();
        EtlServeEntity serveEntity = (EtlServeEntity)etlServeEntity.get();
        return serveEntity;
    }

    public static String getPassword(EtlServeEntity serveEntity) {
        String pw = serveEntity.getPwd();
        try {
            pw = Crypto.desEncrypt((String)pw);
        }
        catch (Exception e) {
            throw new RuntimeException("\u5bc6\u7801\u65e0\u6cd5\u89e3\u5bc6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\u5bc6\u7801", e);
        }
        return pw;
    }
}

