/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.journalsingle.common.MDConst
 *  com.jiuqi.gcreport.journalsingle.vo.CommJournalVO
 */
package com.jiuqi.gcreport.journalsingle.check;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.journalsingle.common.MDConst;
import com.jiuqi.gcreport.journalsingle.vo.CommJournalVO;
import java.util.List;

public class CommJournalVOChecker {
    public static void checkItemValid(CommJournalVO vo) {
        if (vo.getAcctYear() == 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u5e74\u5ea6\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getAcctPeriod() == 0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u671f\u95f4\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty((String)vo.getSubjectCode())) {
            throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (vo.getDc() == 1) {
            if (vo.getCreditUnitId() != null) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u8d37\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
            }
            if (vo.getCredit() != 0.0) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u8d37\u65b9\u91d1\u989d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
            }
        }
        if (vo.getDc() == -1) {
            if (vo.getDebitUnitId() != null) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u5355\u4f4d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
            }
            if (vo.getDebit() != 0.0) {
                throw new BusinessRuntimeException("\u7b2c" + vo.getSortOrder() + "\u884c\u5206\u5f55\u9519\u8bef\uff1a\u501f\u65b9\u91d1\u989d\u4e0d\u5141\u8bb8\u6709\u503c\u3002");
            }
        }
    }

    public static void checkGlobalValid(List<CommJournalVO> list) {
        double debitSum = 0.0;
        double creditSum = 0.0;
        String debitOrg = null;
        String creditOrg = null;
        for (CommJournalVO vo : list) {
            debitSum = NumberUtils.sum((double)debitSum, (double)vo.getDebit());
            creditSum = NumberUtils.sum((double)creditSum, (double)vo.getCredit());
            if (!vo.getAdjType().equals(MDConst.ADJTYPE_MERGE)) continue;
            if (vo.getDebitUnitId() != null) {
                if (list.size() > 2 && debitOrg != null && !vo.getDebitUnitId().equals(debitOrg)) {
                    throw new BusinessRuntimeException("\u501f\u65b9\u5355\u4f4d\u8981\u6c42\u4e00\u81f4");
                }
                debitOrg = vo.getDebitUnitId();
            }
            if (vo.getCreditUnitId() == null) continue;
            if (list.size() > 2 && creditOrg != null && !vo.getCreditUnitId().equals(creditOrg)) {
                throw new BusinessRuntimeException("\u8d37\u65b9\u5355\u4f4d\u8981\u6c42\u4e00\u81f4");
            }
            creditOrg = vo.getCreditUnitId();
        }
        if (NumberUtils.sub((double)debitSum, (double)creditSum) != 0.0) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u9519\u8bef\uff1a\u5206\u5f55\u501f\u8d37\u4e0d\u5e73\u3002");
        }
    }
}

