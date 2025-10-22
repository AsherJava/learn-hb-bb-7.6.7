/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.journalsingle.common.MDConst
 *  com.jiuqi.gcreport.journalsingle.vo.JournalVO
 */
package com.jiuqi.gcreport.journalsingle.check;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.journalsingle.common.MDConst;
import com.jiuqi.gcreport.journalsingle.vo.JournalVO;
import java.math.BigDecimal;
import java.util.List;

public class JournalVOChecker {
    public static void checkItemValid(JournalVO vo) {
        if (vo.getTaskId() == null) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getSchemeId() == null) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u65b9\u6848\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getAcctYear() == 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u5e74\u5ea6\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getAcctPeriod() == 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u671f\u95f4\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getAdjType() == MDConst.ADJTYPE_MERGE) {
            if (vo.getUnionRuleId() == null) {
                throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u5408\u5e76\u89c4\u5219\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
            }
            if (vo.getUnitVo() == null && vo.getOppUnitVo() == null) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getGroupIndex() + 1 + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u8d37\u65b9\u5355\u4f4d\u5fc5\u987b\u6709\u4e00\u65b9\u6709\u503c\u3002");
            }
            if (vo.getDc() == 1) {
                if (vo.getOppUnitVo() != null) {
                    throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u8d37\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
                }
                if (vo.getCredit() != 0.0) {
                    throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u8d37\u65b9\u91d1\u989d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
                }
            }
            if (vo.getDc() == -1) {
                if (vo.getUnitVo() != null) {
                    throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
                }
                if (vo.getDebit() != 0.0) {
                    throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u91d1\u989d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
                }
            }
        }
        if (vo.getSubjectVo() == null) {
            throw new BusinessRuntimeException("\u7b2c" + (vo.getGroupIndex() + 1) + "\u7ec4\uff0c\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
    }

    public static void checkGlobalValid(List<JournalVO> list) {
        BigDecimal debitSum = new BigDecimal(0);
        BigDecimal creditSum = new BigDecimal(0);
        boolean dxjeIsNull = false;
        for (JournalVO vo : list) {
            if (vo.getDebit() != null) {
                debitSum = NumberUtils.sum((BigDecimal)debitSum, (double)NumberUtils.round((double)vo.getDebit()));
            }
            if (vo.getCredit() != null) {
                creditSum = NumberUtils.sum((BigDecimal)creditSum, (double)NumberUtils.round((double)vo.getCredit()));
            }
            if (vo.getDebit() != null || vo.getCredit() != null) continue;
            dxjeIsNull = true;
        }
        if (NumberUtils.sub((BigDecimal)debitSum, (BigDecimal)creditSum).compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u7b2c" + (list.get(0).getGroupIndex() + 1) + "\u7ec4\uff0c\u5206\u5f55\u501f\u8d37\u4e0d\u5e73\u3002");
        }
        if (dxjeIsNull || list.get(0) != null && !MDConst.ADJTYPE_MERGE.equals(list.get(0).getAdjType()) && (debitSum.compareTo(BigDecimal.ZERO) == 0 || creditSum.compareTo(BigDecimal.ZERO) == 0)) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u7b2c" + (list.get(0).getGroupIndex() + 1) + "\u7ec4\uff0c\u5206\u5f55\u91d1\u989d\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        }
    }
}

