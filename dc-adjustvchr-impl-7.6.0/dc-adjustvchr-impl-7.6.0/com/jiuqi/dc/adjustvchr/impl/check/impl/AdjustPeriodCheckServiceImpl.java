/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import com.jiuqi.dc.adjustvchr.impl.utils.AdjustVchrImportCheckUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AdjustPeriodCheckServiceImpl
implements AdjustVchrImportCheckService {
    private static final String ERROR_TYPE_MSG = "\u7b2c%1$s\u884c\u8d77\u59cb\u6216\u622a\u6b62\u65f6\u95f4\u6570\u636e\u7c7b\u578b\u4e0d\u6b63\u786e\uff0c\u8bf7\u53c2\u8003\u8bf4\u660e\u793a\u4f8b\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String START_THEN_END_MSG = "\u7b2c%1$s\u884c\u8d77\u59cb\u671f\u95f4\u5927\u4e8e\u622a\u6b62\u671f\u95f4\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String DIFF_SELECT_MSG = "\u7b2c%1$s\u884c\u671f\u95f4\u7c7b\u578b\u4e0e\u754c\u9762\u4e0a\u9009\u53d6\u7684\u671f\u95f4\u7c7b\u578b\u4e0d\u4e00\u81f4\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String END_THEN_SELECT_MSG = "\u7b2c%1$s\u884c\u8d77\u59cb\u671f\u95f4\u5c0f\u4e8e\u754c\u9762\u4e0a\u9009\u53d6\u7684\u671f\u95f4\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String NO_CONFIRM_PERIOD_MSG = "\u7ec4\u7ec7\u673a\u6784\u3010%1$s\u3011\u51ed\u8bc1\u7f16\u53f7\u3010%2$s\u3011\u5bf9\u5e94\u7684\u5206\u5f55\u671f\u95f4\u4e0d\u7edf\u4e00\uff0c\u8bf7\u786e\u8ba4\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        StringBuilder errorCheckImportMsg = new StringBuilder();
        HashSet<Integer> errorTypeIndex = new HashSet<Integer>();
        HashSet<Integer> startThenEndIndex = new HashSet<Integer>();
        HashSet<Integer> diffSelectIndex = new HashSet<Integer>();
        HashSet<Integer> endThenSelectIndex = new HashSet<Integer>();
        for (int i = 0; i < importData.size(); ++i) {
            int rowIndex = startIndex + i;
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            String startType = this.checkDataType(itemImportVO.getStartPeriod());
            String endType = this.checkDataType(itemImportVO.getEndPeriod());
            if (StringUtils.isNull((String)startType) || StringUtils.isNull((String)endType)) {
                errorTypeIndex.add(rowIndex);
                continue;
            }
            if (!startType.equals(importParam.getPeriodType()) || !endType.equals(importParam.getPeriodType())) {
                diffSelectIndex.add(rowIndex);
                continue;
            }
            Integer[] result = this.getStartAndEndPeriod(rowIndex, importData.get(i), errorTypeIndex, startType);
            if (result.length == 0) continue;
            if (result[0] > result[1]) {
                startThenEndIndex.add(rowIndex);
                continue;
            }
            if (result[0] >= importParam.getAffectPeriodStart()) continue;
            endThenSelectIndex.add(rowIndex);
        }
        if (!CollectionUtils.isEmpty(errorTypeIndex)) {
            errorCheckImportMsg.append(String.format(ERROR_TYPE_MSG, errorTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(startThenEndIndex)) {
            errorCheckImportMsg.append(String.format(START_THEN_END_MSG, startThenEndIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(diffSelectIndex)) {
            errorCheckImportMsg.append(String.format(DIFF_SELECT_MSG, diffSelectIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!CollectionUtils.isEmpty(endThenSelectIndex)) {
            errorCheckImportMsg.append(String.format(END_THEN_SELECT_MSG, endThenSelectIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!StringUtils.isEmpty((String)errorCheckImportMsg.toString())) {
            return errorCheckImportMsg.toString();
        }
        Map<String, List<AdjustVchrItemImportVO>> tempVoucherData = AdjustVchrImportCheckUtil.getSaveData(importData);
        for (Map.Entry<String, List<AdjustVchrItemImportVO>> voucherData : tempVoucherData.entrySet()) {
            HashSet<String> startPeriodSet = new HashSet<String>();
            HashSet<String> endPeriodSet = new HashSet<String>();
            for (AdjustVchrItemImportVO item : voucherData.getValue()) {
                startPeriodSet.add(String.valueOf(item.getStartPeriod()));
                endPeriodSet.add(String.valueOf(item.getEndPeriod()));
            }
            if (startPeriodSet.size() <= 1 && endPeriodSet.size() <= 1) continue;
            String[] vchrMsg = voucherData.getKey().split("\\|");
            errorCheckImportMsg.append(String.format(NO_CONFIRM_PERIOD_MSG, vchrMsg[0], vchrMsg[1]));
            break;
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 1;
    }

    private String checkDataType(String colValue) {
        if (colValue.contains("\u6708")) {
            return "Y";
        }
        if (colValue.contains("\u5b63\u5ea6")) {
            return "J";
        }
        if (colValue.contains("\u534a\u5e74")) {
            return "H";
        }
        if (colValue.contains("\u5e74")) {
            return "N";
        }
        return "";
    }

    private Integer[] getStartAndEndPeriod(int rowIndex, AdjustVchrItemImportVO rowData, Set<Integer> errorTypeIndex, String periodType) {
        String endValue;
        Integer[] result = new Integer[2];
        String startValue = rowData.getStartPeriod().replace(" ", "");
        if (startValue.contains("\u534a\u5e74")) {
            if (startValue.equals("\u4e0a\u534a\u5e74")) {
                startValue = "1";
            } else if (startValue.equals("\u4e0b\u534a\u5e74")) {
                startValue = "2";
            }
        }
        if ((endValue = rowData.getEndPeriod().replace(" ", "")).contains("\u534a\u5e74")) {
            if (endValue.equals("\u4e0a\u534a\u5e74")) {
                endValue = "1";
            } else if (endValue.equals("\u4e0b\u534a\u5e74")) {
                endValue = "2";
            }
        }
        startValue = startValue.replaceAll("[\\u4e00-\\u9fa5]", "");
        endValue = endValue.replaceAll("[\\u4e00-\\u9fa5]", "");
        if (StringUtils.isNull((String)startValue) || StringUtils.isNull((String)endValue)) {
            errorTypeIndex.add(rowIndex);
            return new Integer[0];
        }
        int start = 0;
        int end = 0;
        try {
            start = Integer.parseInt(startValue);
            end = Integer.parseInt(endValue);
            if (periodType.equals("Y") && (start < 0 || start > 12 || end < 0 || end > 12)) {
                errorTypeIndex.add(rowIndex);
                return new Integer[0];
            }
            if (periodType.equals("J") && (start < 0 || start > 4 || end < 0 || end > 4)) {
                errorTypeIndex.add(rowIndex);
                return new Integer[0];
            }
            if (periodType.equals("H") && (start < 0 || start > 2 || end < 0 || end > 2)) {
                errorTypeIndex.add(rowIndex);
                return new Integer[0];
            }
            if (!(periodType.equals("N") || start >= 0 && start <= 9999 && end >= 0 && end <= 9999)) {
                errorTypeIndex.add(rowIndex);
                return new Integer[0];
            }
        }
        catch (Exception e) {
            errorTypeIndex.add(rowIndex);
            return new Integer[0];
        }
        result[0] = start;
        result[1] = end;
        return result;
    }
}

