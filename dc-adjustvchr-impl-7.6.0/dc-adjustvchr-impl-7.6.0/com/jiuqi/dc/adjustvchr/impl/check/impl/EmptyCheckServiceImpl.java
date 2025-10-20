/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO
 */
package com.jiuqi.dc.adjustvchr.impl.check.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.client.vo.AdjustVchrSysOptionVO;
import com.jiuqi.dc.adjustvchr.impl.check.AdjustVchrImportCheckService;
import com.jiuqi.dc.adjustvchr.impl.impexp.entity.AdjustVchrItemImportVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EmptyCheckServiceImpl
implements AdjustVchrImportCheckService {
    private static final String EMPTY_UNIT_MSG = "\u7b2c%1$s\u884c\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_VCHRNUM_MSG = "\u7b2c%1$s\u884c\u51ed\u8bc1\u7f16\u53f7\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_ADJUSTTYPE_MSG = "\u7b2c%1$s\u884c\u8c03\u6574\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_ADJUSTPERIOD_MSG = "\u7b2c%1$s\u884c\u8c03\u6574\u671f\u95f4\u8d77\u6b62\u5747\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_ITEMORDER_MSG = "\u7b2c%1$s\u884c\u884c\u53f7\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_SUBJ_MSG = "\u7b2c%1$s\u884c\u79d1\u76ee\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";
    private static final String EMPTY_DIGEST_MSG = "\u7b2c%1$s\u884c\u6458\u8981\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u8865\u5145\u5b8c\u6574\u540e\u91cd\u65b0\u5bfc\u5165\u3002\n";

    @Override
    public String checkImportData(int startIndex, List<AdjustVchrItemImportVO> importData, AdjustVoucherQueryDTO importParam, AdjustVchrSysOptionVO optionVO, boolean convertEnable) {
        StringBuilder errorCheckImportMsg = new StringBuilder();
        ArrayList<Integer> emptyUnitIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptyVchrNumIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptyAdjustTypeIndex = new ArrayList<Integer>();
        HashSet<Integer> emptyAdjustPeriodIndex = new HashSet<Integer>();
        ArrayList<Integer> emptyItemOrderIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptySubjectIndex = new ArrayList<Integer>();
        ArrayList<Integer> emptyDigestIndex = new ArrayList<Integer>();
        for (int i = 0; i < importData.size(); ++i) {
            int rowIndex = startIndex + i;
            AdjustVchrItemImportVO itemImportVO = importData.get(i);
            if (StringUtils.isNull((String)itemImportVO.getUnit())) {
                emptyUnitIndex.add(rowIndex);
                continue;
            }
            if (StringUtils.isNull((String)itemImportVO.getVchrNum())) {
                emptyVchrNumIndex.add(rowIndex);
                continue;
            }
            if (StringUtils.isNull((String)itemImportVO.getAdjustType())) {
                emptyAdjustTypeIndex.add(rowIndex);
                continue;
            }
            if (StringUtils.isNull((String)itemImportVO.getStartPeriod()) || StringUtils.isNull((String)itemImportVO.getEndPeriod())) {
                emptyAdjustPeriodIndex.add(rowIndex);
                continue;
            }
            if (StringUtils.isNull((String)itemImportVO.getItemOrder())) {
                emptyItemOrderIndex.add(rowIndex);
                continue;
            }
            if (StringUtils.isNull((String)itemImportVO.getSubject())) {
                emptySubjectIndex.add(rowIndex);
                continue;
            }
            if (!optionVO.isDigestNotNull() || !StringUtils.isNull((String)itemImportVO.getDigest())) continue;
            emptyDigestIndex.add(rowIndex);
        }
        if (!emptyUnitIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_UNIT_MSG, emptyUnitIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptyVchrNumIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_VCHRNUM_MSG, emptyVchrNumIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptyAdjustTypeIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_ADJUSTTYPE_MSG, emptyAdjustTypeIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptyAdjustPeriodIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_ADJUSTPERIOD_MSG, emptyAdjustPeriodIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptyItemOrderIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_ITEMORDER_MSG, emptyItemOrderIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptySubjectIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_SUBJ_MSG, emptySubjectIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        if (!emptyDigestIndex.isEmpty()) {
            errorCheckImportMsg.append(String.format(EMPTY_DIGEST_MSG, emptyDigestIndex.stream().map(String::valueOf).collect(Collectors.joining("\uff0c"))));
        }
        return errorCheckImportMsg.toString();
    }

    @Override
    public int checkOrder() {
        return 0;
    }
}

