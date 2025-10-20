/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.gcreport.formulaschemeconfig.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.formulaschemeconfig.utils.GCAdjTypeEnum;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NrFormulaSchemeConfigUtils {
    public static final String REPORTFETCH = "reportFetch";
    private static NrFormulaSchemeConfigUtils nrFormulaSchemeConfigUtil;
    @Autowired
    @Lazy
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;

    @PostConstruct
    public void init() {
        nrFormulaSchemeConfigUtil = this;
        NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iDesignTimeViewController = this.iDesignTimeViewController;
        NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iRunTimeViewController = this.iRunTimeViewController;
        NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iEntityMetaService = this.iEntityMetaService;
    }

    public static boolean enableTaskMultiOrg(String taskKey) {
        List taskOrgList = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iRunTimeViewController.listTaskOrgLinkStreamByTask(taskKey).getList();
        return !CollectionUtils.isEmpty((Collection)taskOrgList) && taskOrgList.size() > 1;
    }

    public static String getEntityIdByTaskKeyAndCtx(String taskKey) {
        Assert.isNotEmpty((String)taskKey, (String)"\u4efb\u52a1\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        TaskDefine taskDefine = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iRunTimeViewController.getTask(taskKey);
        Assert.isNotNull((Object)taskDefine, (String)String.format("\u6839\u636e\u4efb\u52a1\u6807\u8bc6\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", taskKey), (Object[])new Object[0]);
        if (NrFormulaSchemeConfigUtils.enableTaskMultiOrg(taskKey)) {
            DsContext dsContext = DsContextHolder.getDsContext();
            if (dsContext == null || StringUtils.isEmpty((String)dsContext.getContextEntityId())) {
                throw new BusinessRuntimeException(String.format("\u5f53\u524d\u62a5\u8868\u4efb\u52a1\u3010%1$s\u3011\u5305\u542b\u591a\u53e3\u5f84\u914d\u7f6e\uff0c\u4e0a\u4e0b\u6587\u53e3\u5f84\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", taskDefine.getTitle()));
            }
            return dsContext.getContextEntityId();
        }
        return taskDefine.getDw();
    }

    public static String getEntityIdBySchemeIdAndCtx(String schemeId) {
        Assert.isNotEmpty((String)schemeId, (String)"\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        FormSchemeDefine formScheme = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iRunTimeViewController.getFormScheme(schemeId);
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", schemeId), (Object[])new Object[0]);
        if (NrFormulaSchemeConfigUtils.enableTaskMultiOrg(formScheme.getTaskKey())) {
            DsContext dsContext = DsContextHolder.getDsContext();
            if (dsContext == null || StringUtils.isEmpty((String)dsContext.getContextEntityId())) {
                throw new BusinessRuntimeException(String.format("\u5f53\u524d\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u5173\u8054\u7684\u4efb\u52a1\u5305\u542b\u591a\u53e3\u5f84\u914d\u7f6e\uff0c\u4e0a\u4e0b\u6587\u53e3\u5f84\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", formScheme.getTitle()));
            }
            return dsContext.getContextEntityId();
        }
        return formScheme.getDw();
    }

    public static String getEntityIdBySchemeIdAndEntityId(String schemeId, String entityId) {
        Assert.isNotEmpty((String)schemeId, (String)"\u62a5\u8868\u65b9\u6848\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)entityId, (String)"\u53e3\u5f84\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        FormSchemeDefine formScheme = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iRunTimeViewController.getFormScheme(schemeId);
        Assert.isNotNull((Object)formScheme, (String)String.format("\u6839\u636e\u62a5\u8868\u65b9\u6848\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u6570\u636e\u9879", schemeId), (Object[])new Object[0]);
        if (NrFormulaSchemeConfigUtils.enableTaskMultiOrg(formScheme.getTaskKey())) {
            return entityId;
        }
        return formScheme.getDw();
    }

    public static boolean isExistCurrencyDim(String taskId) {
        DesignTaskDefine taskDefine = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iDesignTimeViewController.getTask(taskId);
        if (null != taskDefine) {
            String[] dimArry;
            String dims = taskDefine.getDims();
            if (StringUtils.isEmpty((String)dims)) {
                return false;
            }
            for (String entityId : dimArry = dims.split(";")) {
                TableModelDefine tableDefine = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iEntityMetaService.getTableModel(entityId);
                if (null == tableDefine || !"MD_CURRENCY".equals(tableDefine.getCode())) continue;
                return true;
            }
        }
        return false;
    }

    public static List<String> listTitleName(String taskId, String tabSelect) {
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId);
        ArrayList<String> titleNames = new ArrayList<String>();
        titleNames.add("\u62a5\u8868\u4efb\u52a1");
        titleNames.add("\u62a5\u8868\u65b9\u6848");
        titleNames.add("\u53e3\u5f84");
        if ("batchStrategy".equals(tabSelect)) {
            titleNames.add("\u5408\u5e76\u5355\u4f4d");
            titleNames.add("\u62a5\u8868\u7c7b\u578b");
        } else if ("batchUnit".equals(tabSelect)) {
            titleNames.add("\u5355\u4f4d");
        }
        if (isExistCurrency) {
            titleNames.add("\u5e01\u522b");
        }
        titleNames.add("\u53d6\u6570\u65b9\u6848");
        titleNames.add("\u53d6\u6570\u540e\u8fd0\u7b97");
        titleNames.add("\u5b8c\u6210\u5408\u5e76\u540e\u8fd0\u7b97");
        titleNames.add("\u5141\u8bb8\u5916\u5e01\u6298\u7b97");
        titleNames.add("\u6298\u7b97\u4e3a\u5f53\u524d\u5e01\u79cd\u540e\u8fd0\u7b97");
        titleNames.add("\u975e\u540c\u63a7\u5904\u7f6e\u63d0\u53d6\u4e0a\u5e74\u540c\u671f\u6570");
        titleNames.add("\u540c\u63a7\u3001\u975e\u540c\u63a7\u63d0\u53d6\u540e\u8fd0\u7b97");
        titleNames.add("\u8fc7\u8d26\u540e\u8fd0\u7b97");
        return titleNames;
    }

    public static List<String> listTitleCode(String taskId, String tabSelect) {
        boolean isExistCurrency = NrFormulaSchemeConfigUtils.isExistCurrencyDim(taskId);
        ArrayList<String> titleCodes = new ArrayList<String>();
        titleCodes.add("taskId");
        titleCodes.add("schemeId");
        titleCodes.add("entityId");
        if ("batchStrategy".equals(tabSelect)) {
            titleCodes.add("orgId");
            titleCodes.add("bblx");
        } else if ("batchUnit".equals(tabSelect)) {
            titleCodes.add("orgId");
        }
        if (isExistCurrency) {
            titleCodes.add("currencyCode");
        }
        titleCodes.add("fetchScheme");
        titleCodes.add("fetchAfterScheme");
        titleCodes.add("completeMerge");
        titleCodes.add("convertSystemScheme");
        titleCodes.add("convertAfterScheme");
        titleCodes.add("unSaCtDeExtLaYeNumSaPer");
        titleCodes.add("sameCtrlExtAfterScheme");
        titleCodes.add("postingScheme");
        return titleCodes;
    }

    public static void dimensionMapAdjTypeValue(String taskId, Map<String, String> dimensionMap) {
        if (NrFormulaSchemeConfigUtils.isExisAdjType(taskId)) {
            dimensionMap.put("MD_GCADJTYPE", GCAdjTypeEnum.BEFOREADJ.getCode());
        }
    }

    public static boolean isExisAdjType(String taskId) {
        DesignTaskDefine taskDefine = NrFormulaSchemeConfigUtils.nrFormulaSchemeConfigUtil.iDesignTimeViewController.getTask(taskId);
        String dimes = taskDefine.getDims();
        return !StringUtils.isEmpty((String)dimes) && dimes.indexOf("MD_GCADJTYPE") > -1;
    }

    public static List<String> listBillTitleName() {
        ArrayList<String> titleNames = new ArrayList<String>();
        titleNames.add("\u5355\u636e");
        titleNames.add("\u5355\u4f4d\u8303\u56f4");
        titleNames.add("\u53d6\u6570\u65b9\u6848");
        return titleNames;
    }

    public static List<String> listTitleCode() {
        ArrayList<String> titleCodes = new ArrayList<String>();
        titleCodes.add("billId");
        titleCodes.add("orgId");
        titleCodes.add("fetchScheme");
        return titleCodes;
    }
}

