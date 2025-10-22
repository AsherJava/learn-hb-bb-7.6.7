/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.math.BigDecimal;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemOptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(SystemOptionUtil.class);
    private final INvwaSystemOptionService nvwaSystemOptionService;
    private final ITaskOptionController taskOptionController;

    @Autowired
    public SystemOptionUtil(INvwaSystemOptionService nvwaSystemOptionService, ITaskOptionController taskOptionController) {
        this.nvwaSystemOptionService = nvwaSystemOptionService;
        this.taskOptionController = taskOptionController;
    }

    public boolean calCheckExecuteImmediately() {
        String value = this.nvwaSystemOptionService.get("nr-data-entry-group", "CALC_CHECK_EXE");
        if (org.springframework.util.StringUtils.hasLength(value)) {
            return "1".equals(value);
        }
        return false;
    }

    public int getBatchSplitCount() {
        int optionSize;
        int size = 500;
        String option = this.nvwaSystemOptionService.get("nr-audit-group", "BATCH_PARALLEL_SPLIT_COUNT");
        if (StringUtils.isNotEmpty((String)option) && (optionSize = Integer.parseInt(option)) > 0) {
            size = optionSize;
        }
        return size;
    }

    public int getCalculateCount(String formulaSchemeKey) {
        HashMap<String, Integer> calculateCountMap = new HashMap<String, Integer>();
        String calculateFormulaCountStr = this.nvwaSystemOptionService.get("nr-calculation-group", "CALCULATE_FORMULA_COUNT");
        if (StringUtils.isNotEmpty((String)calculateFormulaCountStr)) {
            try {
                String[] calculateFormulaCountStrList;
                for (String calculateFormulaCount : calculateFormulaCountStrList = calculateFormulaCountStr.split(";")) {
                    String[] calculateFormulaCountMsgs = calculateFormulaCount.split(":");
                    if (calculateFormulaCountMsgs.length != 2) continue;
                    String calFormulaSchemeKey = calculateFormulaCountMsgs[0];
                    int calCount = Integer.parseInt(calculateFormulaCountMsgs[1]);
                    if (calCount <= 0) continue;
                    calculateCountMap.put(calFormulaSchemeKey, calCount);
                }
            }
            catch (Exception e) {
                logger.error("\u516c\u5f0f\u8fd0\u7b97\u6b21\u6570\u89e3\u6790\u5931\u8d25,\u51fa\u9519\u539f\u56e0\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
        }
        int calCount = 1;
        if (calculateCountMap.containsKey(formulaSchemeKey)) {
            calCount = (Integer)calculateCountMap.get(formulaSchemeKey);
        }
        return calCount;
    }

    public int getCheckCharNum() {
        try {
            String charNumOfErrorMsg = this.nvwaSystemOptionService.get("nr-audit-group", "CHAR_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charNumOfErrorMsg)) {
                return Integer.parseInt(charNumOfErrorMsg);
            }
            return 10;
        }
        catch (Exception e) {
            return 10;
        }
    }

    public int getCheckCharMaxNum() {
        try {
            String charMAXNumOfErrorMsg = this.nvwaSystemOptionService.get("nr-audit-group", "MAX_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charMAXNumOfErrorMsg)) {
                return Integer.parseInt(charMAXNumOfErrorMsg);
            }
            return 172;
        }
        catch (Exception e) {
            return 172;
        }
    }

    public boolean ckdContainsChinese() {
        try {
            String charMAXNumOfErrorMsg = this.nvwaSystemOptionService.get("nr-audit-group", "ERROR_MSG_CONTAIN_CHINESE_CHAR");
            return "1".equals(charMAXNumOfErrorMsg);
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean recordCheckStatus() {
        try {
            String recordCheckStatus = this.nvwaSystemOptionService.get("nr-audit-group", "@nr/check/record-check-status");
            return "1".equals(recordCheckStatus);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public BigDecimal getAllowableError(String taskKey) {
        try {
            String value = this.taskOptionController.getValue(taskKey, "@nr/CHECK_ALLOW_ERROR");
            return new BigDecimal(value);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

