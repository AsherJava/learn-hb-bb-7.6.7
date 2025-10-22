/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.gcreport.asset.formula.function.rule.asset;

import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.period.PeriodType;
import java.util.Calendar;
import java.util.Date;

public class AssetDepreMonth {
    public static final String ASSETTYPE_FA = "FA";
    public static final String ASSETTYPE_IA = "IA";
    public static final String ASSETTYPE_OGA = "OGA";
    public static final String ASSETTYPE_DFA = "DFA";
    public static final String ASSETTYPE_TFA = "TFA";
    public static final String TIMETYPE_CURR = "CURR";
    public static final String TIMETYPE_PRIOR = "PRIOR";

    public int depreMonth(DefaultTableEntity master, YearPeriodDO ypDO, String assetType, String timeType) {
        Date disposalDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"DISPOSALDATE");
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        if (purchaseDate == null) {
            return 0;
        }
        int initDpcaYear = InvestBillTool.getIntValue((DefaultTableEntity)master, (String)"INITDPCAYEAR");
        int dpcaMonth = (int)Math.ceil(InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR"));
        Date expireDate = null;
        if (dpcaMonth > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(purchaseDate);
            calendar.add(2, dpcaMonth);
            expireDate = calendar.getTime();
        }
        Calendar disposalCalendar = null;
        if (disposalDate != null) {
            disposalCalendar = Calendar.getInstance();
            disposalCalendar.setTime(disposalDate);
        }
        Calendar expireCalendar = null;
        if (expireDate != null) {
            expireCalendar = Calendar.getInstance();
            expireCalendar.setTime(expireDate);
        }
        Calendar purchaseCalendar = Calendar.getInstance();
        purchaseCalendar.setTime(purchaseDate);
        int depreciationMonth = 0;
        if (disposalCalendar != null && disposalCalendar.get(1) < ypDO.getYear()) {
            return 0;
        }
        if (ASSETTYPE_FA.equalsIgnoreCase(assetType)) {
            depreciationMonth = this.fixAssetDepreMonth(timeType, expireCalendar, purchaseCalendar, disposalCalendar, ypDO, initDpcaYear);
        } else if (ASSETTYPE_IA.equalsIgnoreCase(assetType)) {
            depreciationMonth = this.intangibleAssetDepreMonth(timeType, expireCalendar, purchaseCalendar, disposalCalendar, ypDO, initDpcaYear);
        }
        return Math.max(depreciationMonth, 0);
    }

    private int intangibleAssetDepreMonth(String timeType, Calendar expireCalendar, Calendar purchaseCalendar, Calendar disposalCalendar, YearPeriodDO ypDO, int initDpcaYear) {
        if (TIMETYPE_CURR.equalsIgnoreCase(timeType)) {
            return this.currIntangibleDepreMonth(expireCalendar, purchaseCalendar, disposalCalendar, ypDO);
        }
        if (TIMETYPE_PRIOR.equalsIgnoreCase(timeType)) {
            return this.priorIntangibleDepreMonth(expireCalendar, purchaseCalendar, disposalCalendar, ypDO, initDpcaYear);
        }
        return 0;
    }

    private int fixAssetDepreMonth(String timeType, Calendar expireCalendar, Calendar purchaseCalendar, Calendar disposalCalendar, YearPeriodDO ypDO, int initDpcaYear) {
        if (TIMETYPE_CURR.equalsIgnoreCase(timeType)) {
            return this.currFixDepreMonth(disposalCalendar, expireCalendar, purchaseCalendar, ypDO);
        }
        if (TIMETYPE_PRIOR.equalsIgnoreCase(timeType)) {
            return this.priorFixDepreMonth(disposalCalendar, expireCalendar, purchaseCalendar, ypDO, initDpcaYear);
        }
        return 0;
    }

    private int currFixDepreMonth(Calendar disposalCalendar, Calendar expireCalendar, Calendar purchaseCalendar, YearPeriodDO ypDO) {
        int purchaseYear;
        Calendar minDate = Calendar.getInstance();
        minDate.setTime(ypDO.getEndDate());
        if (disposalCalendar != null) {
            Calendar calendar = minDate = minDate.compareTo(disposalCalendar) > 0 ? disposalCalendar : minDate;
        }
        if (expireCalendar != null) {
            if (expireCalendar.get(1) < ypDO.getYear()) {
                return 0;
            }
            Calendar calendar = minDate = minDate.compareTo(expireCalendar) > 0 ? expireCalendar : minDate;
        }
        if ((purchaseYear = purchaseCalendar.get(1)) == ypDO.getYear()) {
            return minDate.get(2) - purchaseCalendar.get(2);
        }
        if (purchaseYear < ypDO.getYear()) {
            return minDate.get(2) + 1;
        }
        return 0;
    }

    private int priorFixDepreMonth(Calendar disposalCalendar, Calendar expireCalendar, Calendar purchaseCalendar, YearPeriodDO ypDO, int initDpcaYear) {
        if (purchaseCalendar.get(1) == ypDO.getYear()) {
            return 0;
        }
        if (purchaseCalendar.get(1) < ypDO.getYear()) {
            int purchaseYear = purchaseCalendar.get(1);
            int purchaseMonth = purchaseCalendar.get(2) + 1;
            if (expireCalendar.get(1) < ypDO.getYear()) {
                int expireYear = expireCalendar.get(1);
                int expireMonth = expireCalendar.get(2) + 1;
                return (expireYear - purchaseYear) * 12 - purchaseMonth + expireMonth;
            }
            int uiYear = ypDO.getYear();
            if (initDpcaYear > 0) {
                return (uiYear - initDpcaYear) * 12;
            }
            return (uiYear - purchaseYear) * 12 - purchaseMonth;
        }
        return 0;
    }

    private int currIntangibleDepreMonth(Calendar expireCalendar, Calendar purchaseCalendar, Calendar disposalCalendar, YearPeriodDO ypDO) {
        int purchaseYear;
        Calendar current = Calendar.getInstance();
        current.setTime(ypDO.getEndDate());
        boolean isDisposal = false;
        Calendar min = null;
        if (disposalCalendar != null && disposalCalendar.compareTo(current) <= 0) {
            min = disposalCalendar;
            isDisposal = true;
        }
        if (expireCalendar != null && expireCalendar.compareTo(current) <= 0) {
            if (expireCalendar.get(1) < ypDO.getYear()) {
                return 0;
            }
            Calendar calendar = isDisposal ? (min.compareTo(expireCalendar) > 0 ? expireCalendar : min) : (min = expireCalendar);
        }
        if ((purchaseYear = purchaseCalendar.get(1)) == ypDO.getYear()) {
            return min != null ? min.get(2) - purchaseCalendar.get(2) : current.get(2) - purchaseCalendar.get(2) + 1;
        }
        if (purchaseYear < ypDO.getYear()) {
            return min != null ? min.get(2) : current.get(2) + 1;
        }
        return 0;
    }

    private int priorIntangibleDepreMonth(Calendar expireCalendar, Calendar purchaseCalendar, Calendar disposalCalendar, YearPeriodDO ypDO, int initDpcaYear) {
        if (purchaseCalendar.get(1) == ypDO.getYear()) {
            return 0;
        }
        if (purchaseCalendar.get(1) < ypDO.getYear()) {
            int purchaseYear = purchaseCalendar.get(1);
            int purchaseMonth = purchaseCalendar.get(2) + 1;
            if (expireCalendar.get(1) < ypDO.getYear()) {
                int expireYear = expireCalendar.get(1);
                int expireMonth = expireCalendar.get(2) + 1;
                return (expireYear - purchaseYear) * 12 - purchaseMonth + expireMonth;
            }
            int uiYear = ypDO.getYear();
            if (initDpcaYear > 0) {
                return (uiYear - initDpcaYear) * 12;
            }
            return (uiYear - purchaseYear) * 12 - purchaseMonth + 1;
        }
        return 0;
    }

    private boolean isCurrTermDisposal(Date disposalDate, YearPeriodDO ypDO) {
        if (null == disposalDate) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(disposalDate);
        if (calendar.get(1) != ypDO.getYear()) {
            return false;
        }
        if (ypDO.getType() == 4) {
            int disposalMonth = calendar.get(2) + 1;
            return disposalMonth == ypDO.getPeriod();
        }
        PeriodType type = PeriodType.fromType((int)ypDO.getType());
        YearPeriodDO destPeriodUtil = YearPeriodUtil.transform(null, (int)type.type(), (Calendar)calendar);
        return destPeriodUtil.getEndDate().equals(ypDO.getEndDate());
    }
}

