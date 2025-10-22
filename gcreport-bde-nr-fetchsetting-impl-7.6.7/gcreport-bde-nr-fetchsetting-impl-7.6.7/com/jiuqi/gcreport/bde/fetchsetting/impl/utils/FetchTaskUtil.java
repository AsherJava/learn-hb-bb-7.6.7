/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.GcPeriodUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.context.cxt.impl.DsContextImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.va.domain.common.MD5Util
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.GcPeriodUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.context.cxt.impl.DsContextImpl;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.va.domain.common.MD5Util;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchTaskUtil {
    public static String getEntityIdByTaskAndCtx(String taskKey) {
        boolean taskEnableMultiOrg = FetchTaskUtil.taskEnableMultiOrg(taskKey);
        if (taskEnableMultiOrg) {
            DsContext dsContext = DsContextHolder.getDsContext();
            Assert.isNotNull((Object)dsContext, (String)"\u62a5\u8868\u4e0a\u4e0b\u6587\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            String entityId = dsContext.getContextEntityId();
            Assert.isNotEmpty((String)entityId, (String)"\u62a5\u8868\u53e3\u5f84\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
            return entityId;
        }
        TaskDefine taskDefine = ((com.jiuqi.nr.definition.controller.IRunTimeViewController)ApplicationContextRegister.getBean(com.jiuqi.nr.definition.controller.IRunTimeViewController.class)).queryTaskDefine(taskKey);
        Assert.isNotNull((Object)taskDefine.getDw(), (String)String.format("\u6839\u636e\u62a5\u8868\u4efb\u52a1%1$s\u672a\u83b7\u53d6\u5230\u5355\u4f4d\u7c7b\u578b\uff0c\u65e0\u6cd5\u4f7f\u7528\u9002\u5e94\u6761\u4ef6", taskKey), (Object[])new Object[0]);
        return taskDefine.getDw();
    }

    public static String getOrgTypeByTaskAndCtx(String taskKey) {
        String orgType = EntityUtils.getId((String)FetchTaskUtil.getEntityIdByTaskAndCtx(taskKey));
        return orgType;
    }

    public static void buildNrCtxByOrgType(String orgType) {
        Assert.isNotEmpty((String)orgType);
        if (orgType.contains("@")) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u53c2\u6570\u4e0d\u80fd\u5305\u542b\u2018@\u2019,\u8bf7\u68c0\u67e5");
        }
        String entityId = orgType + "@ORG";
        FetchTaskUtil.buildNrCtxEntityId(entityId);
    }

    public static void buildNrCtxEntityId(String entityId) {
        Assert.isNotEmpty((String)entityId);
        if (!entityId.contains("@")) {
            throw new BusinessRuntimeException(String.format("\u53e3\u5f84\u53c2\u6570\u3010%1$s\u3011\u683c\u5f0f\u4e0d\u6b63\u786e,\u8bf7\u68c0\u67e5", entityId));
        }
        DsContext context = DsContextHolder.getDsContext();
        DsContextImpl dsContext = (DsContextImpl)context;
        dsContext.setEntityId(entityId);
    }

    public static boolean taskEnableMultiOrg(String taskKey) {
        TaskOrgLinkListStream taskOrgLinkListStream = ((IRunTimeViewController)ApplicationContextRegister.getBean(IRunTimeViewController.class)).listTaskOrgLinkStreamByTask(taskKey);
        return taskOrgLinkListStream.getList() != null && taskOrgLinkListStream.getList().size() > 1;
    }

    public static String newDimensionUniqueKey(EfdcInfo efdcInfo) {
        StringBuffer masterKey = new StringBuffer();
        masterKey.append("efdc_").append(efdcInfo.getTaskKey()).append("_").append(efdcInfo.getFormKey()).append("_").append(efdcInfo.getFormSchemeKey());
        Map dimensionSet = efdcInfo.getDimensionSet();
        for (String dimensionSetKey : dimensionSet.keySet()) {
            masterKey.append("_").append(dimensionSetKey).append("=").append(dimensionSet.get(dimensionSetKey));
        }
        masterKey.append(efdcInfo.isOverwrite() ? "1" : "0");
        masterKey.append(efdcInfo.isContainsUnbVou() ? "1" : "0");
        return MD5Util.encrypt((String)masterKey.toString()).toString();
    }

    public static String[] parseDataTime(String dataTimeStr) {
        return GcPeriodUtils.getTimesArr((String)dataTimeStr);
    }

    public static String getExceptionStackStr(Throwable e) {
        StringBuffer result = new StringBuffer();
        if (!StringUtils.isEmpty((String)e.getMessage()) && e.getMessage().endsWith("\n")) {
            result.append(e.getMessage().replace("\n", ""));
        } else {
            result.append(e.getMessage());
        }
        if (e.getStackTrace() != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                result.append(element.toString()).append("\n");
            }
        }
        return result.toString();
    }

    public static String getErrorMsg(String msg) {
        StringBuffer result = new StringBuffer();
        if (msg == null) {
            return "";
        }
        if (StringUtils.isEmpty((String)msg)) {
            return "";
        }
        if (msg.contains("\u5806\u6808\u4fe1\u606f:")) {
            msg = msg.substring(0, msg.indexOf("\u5806\u6808\u4fe1\u606f:"));
        }
        if (msg.contains("com.")) {
            msg = msg.substring(0, msg.indexOf("com."));
        }
        if (msg.endsWith("\n")) {
            msg = msg.replace("\n", "");
        }
        return result.toString();
    }

    public static List<String> splitToList(String source, String splitChar) {
        if (source == null || source.trim().length() == 0) {
            return new ArrayList<String>(32);
        }
        return CollectionUtils.newArrayList((Object[])source.split(splitChar));
    }
}

