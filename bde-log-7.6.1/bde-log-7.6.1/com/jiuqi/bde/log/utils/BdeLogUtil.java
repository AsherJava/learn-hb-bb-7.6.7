/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.option.BdeOptionTypeEnum
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.nvwa.systemoption.vo.SystemOptionSave
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.bde.log.utils;

import com.jiuqi.bde.common.option.BdeOptionTypeEnum;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItemValue;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.nvwa.systemoption.vo.SystemOptionSave;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class BdeLogUtil {
    private static Logger logger = LoggerFactory.getLogger("com.jiuqi.bde.log.fetch");
    @Autowired
    private INvwaSystemOptionService optionService;
    private static BdeLogUtil loggerUtil;
    public static final String NEXT_LINE;

    @PostConstruct
    public void init() {
        loggerUtil = this;
        BdeLogUtil.loggerUtil.optionService = this.optionService;
    }

    public static void recordLog(String requestTaskId, String logModule, Object params, String message) {
        Assert.isNotEmpty((String)requestTaskId);
        if (!BdeLogUtil.isDebug()) {
            return;
        }
        BdeLogUtil.doRecordLog(requestTaskId, logModule, params, message);
    }

    public static void forceRecordLog(String requestTaskId, String logModule, Object params, String message) {
        BdeLogUtil.doRecordLog(requestTaskId, logModule, params, message);
    }

    private static void doRecordLog(String requestTaskId, String logModule, Object params, String message) {
        Assert.isNotEmpty((String)requestTaskId);
        String paramsStr = null;
        paramsStr = params == null ? "" : (params instanceof String ? params.toString() : JsonUtils.writeValueAsString((Object)params));
        String LOG_TMPL = NEXT_LINE + "************************************BDE-DEBUG-LOG*******************************************" + NEXT_LINE + "\u4efb\u52a1\u6807\u8bc6\uff1a\u3010{}\u3011\t\t\u6807\u9898\uff1a\u3010{}\u3011" + NEXT_LINE + "\u53c2\u6570\uff1a{}" + NEXT_LINE + "\u8be6\u7ec6\u4fe1\u606f\uff1a" + NEXT_LINE + "{}" + NEXT_LINE + "*********************************************************************************************" + NEXT_LINE;
        logger.info(LOG_TMPL, requestTaskId, logModule, paramsStr, message);
    }

    public static boolean isDebug() {
        if (!BdeCommonUtil.isStandaloneServer()) {
            return false;
        }
        return "1".equals(BdeLogUtil.loggerUtil.optionService.findValueById("BDE_IS_DEBUG"));
    }

    public static String getDimCode(String formId, String regionId) {
        return String.format("%1$s,%2$s", formId, regionId);
    }

    public static void resetDebugMode(String username) {
        BdeCommonUtil.initNpUser((String)username);
        SystemOptionSave debugSysOptionValue = new SystemOptionSave();
        debugSysOptionValue.setKey(BdeOptionTypeEnum.SYSTEM.getCode());
        ArrayList<SystemOptionItemValue> debugSysOptionItemValues = new ArrayList<SystemOptionItemValue>();
        SystemOptionItemValue systemOptionItemValueCostThreshold = new SystemOptionItemValue("BDE_IS_DEBUG", "0");
        debugSysOptionItemValues.add(systemOptionItemValueCostThreshold);
        debugSysOptionValue.setItemValues(debugSysOptionItemValues);
        ((INvwaSystemOptionService)ApplicationContextRegister.getBean(INvwaSystemOptionService.class)).save(debugSysOptionValue);
    }

    static {
        NEXT_LINE = System.getProperty("line.separator");
    }
}

