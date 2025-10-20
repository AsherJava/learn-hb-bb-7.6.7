/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.billcore.util.InvestBillTool
 *  com.jiuqi.gcreport.billcore.util.SQLHelper
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.calculate.service.AssetsBillDepreItemCalcCache
 *  com.jiuqi.gcreport.common.nr.function.IGcFunction
 *  com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.asset.formula.function.rule.asset;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.asset.assetbill.dao.CommonAssetBillDao;
import com.jiuqi.gcreport.asset.assetbill.enums.CommonAssetDepreItemOgaTypeEnum;
import com.jiuqi.gcreport.asset.assetbill.enums.CommonAssetDepreItemTypeEnum;
import com.jiuqi.gcreport.billcore.util.InvestBillTool;
import com.jiuqi.gcreport.billcore.util.SQLHelper;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.calculate.service.AssetsBillDepreItemCalcCache;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import com.jiuqi.gcreport.common.util.OffsetVchrItemNumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DetailCommonAssetDepreFunction
extends Function
implements IGcFunction {
    private static final long serialVersionUID = 1L;
    private static final String AVERAGE_AGE_METHOD = "01";
    private static final String COMPREHENSIVE_DEPRECIATION_METHOD = "02";
    public static final String FUNCTION_NAME = "DetailCommonAssetDepre";
    public static final String SN_CODE = "sn";

    public DetailCommonAssetDepreFunction() {
        this.parameters().add(new Parameter("assetType", 6, "\u8d44\u4ea7\u7c7b\u578b"));
        this.parameters().add(new Parameter("timeType", 6, "\u91c7\u8d2d\u65f6\u95f4\u7c7b\u578b"));
    }

    @Transactional(rollbackFor={Exception.class})
    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int disposalYear;
        QueryContext queryContext = (QueryContext)context;
        GcReportExceutorContext executorContext = (GcReportExceutorContext)queryContext.getExeContext();
        DefaultTableEntity master = executorContext.getData();
        String assetType = (String)parameters.get(0).evaluate(null);
        String timeType = (String)parameters.get(1).evaluate(null);
        YearPeriodDO yearPeriod = YearPeriodUtil.transform(null, (String)queryContext.getMasterKeys().getValue("DATATIME").toString());
        List<DefaultTableEntity> itemDepreList = this.getItemDepreList(master, assetType, yearPeriod);
        double result = 0.0;
        int calculateYear = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)1);
        int calculateMonth = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)2);
        Date disposalDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"DISPOSALDATE");
        if (disposalDate != null && calculateYear > (disposalYear = DateUtils.getDateFieldValue((Date)disposalDate, (int)1))) {
            return result;
        }
        for (DefaultTableEntity item : itemDepreList) {
            Date depreDate = InvestBillTool.getDateValue((DefaultTableEntity)item, (String)"DPCAMONTH");
            if (depreDate == null) continue;
            int depreYear = DateUtils.getDateFieldValue((Date)depreDate, (int)1);
            int depreMonth = DateUtils.getDateFieldValue((Date)depreDate, (int)2);
            if ("CURR".equalsIgnoreCase(timeType) ? depreYear != calculateYear || depreMonth > calculateMonth : "PRIOR".equalsIgnoreCase(timeType) && depreYear >= calculateYear) continue;
            double doubleValue = InvestBillTool.getDoubleValue((DefaultTableEntity)item, (String)"DPCAAMT");
            result += doubleValue;
        }
        return result;
    }

    private List<DefaultTableEntity> getItemDepreList(DefaultTableEntity master, String assetType, YearPeriodDO yearPeriod) {
        String sn = InvestBillTool.getStringValue((DefaultTableEntity)master, (String)SN_CODE);
        AssetsBillDepreItemCalcCache assetsBillDepreItemCalcCache = (AssetsBillDepreItemCalcCache)SpringContextUtils.getBean(AssetsBillDepreItemCalcCache.class);
        return assetsBillDepreItemCalcCache.getCacheData(sn, master.getId(), () -> this.getDefaultTableEntities(master, assetType, yearPeriod));
    }

    private List<DefaultTableEntity> getDefaultTableEntities(DefaultTableEntity master, String assetType, YearPeriodDO yearPeriod) {
        CommonAssetBillDao commonAssetBillDao = (CommonAssetBillDao)SpringContextUtils.getBean(CommonAssetBillDao.class);
        commonAssetBillDao.deleteCommonItemByMastId(master.getId(), CommonAssetDepreItemTypeEnum.AUTO, yearPeriod.getBeginDate(), yearPeriod.getEndDate());
        List<DefaultTableEntity> dbItemDepreList = commonAssetBillDao.listCommonItemByMasterId(master.getId(), yearPeriod.getBeginDate());
        double ordinal = commonAssetBillDao.getMinOrdinalByMasterId(master.getId()) - 1.0;
        if (CollectionUtils.isEmpty(dbItemDepreList)) {
            dbItemDepreList = new ArrayList<DefaultTableEntity>();
        }
        double dbItemDepreAmtSum = dbItemDepreList.stream().mapToDouble(item -> InvestBillTool.getDoubleValue((DefaultTableEntity)item, (String)"DPCAAMT")).summaryStatistics().getSum();
        dbItemDepreAmtSum = OffsetVchrItemNumberUtils.round((double)dbItemDepreAmtSum);
        List<DefaultTableEntity> masterList = Arrays.asList(master);
        if ("OGA".equals(assetType)) {
            masterList = this.splitMaster(master);
        }
        for (DefaultTableEntity masterItem : masterList) {
            Double currentMonthAmt;
            Object ogaType = masterItem.getFieldValue("OGATYPE");
            if (ogaType != null) {
                dbItemDepreAmtSum = this.getOgaDbItemDepreAmtSum(ogaType.toString(), dbItemDepreList);
            }
            if ((currentMonthAmt = this.calcCurrentMonthAmt(masterItem, assetType, dbItemDepreAmtSum, yearPeriod)) == null) continue;
            currentMonthAmt = NumberUtils.round((double)currentMonthAmt);
            double currentMonthAmtBack = currentMonthAmt;
            currentMonthAmt = this.checkCurrentMonthAmt(masterItem, dbItemDepreAmtSum, currentMonthAmt);
            String memo = this.getMemo(currentMonthAmtBack, currentMonthAmt, ogaType);
            dbItemDepreList.add(this.saveItemAndGetDefaultTableEntity(masterItem, ogaType, yearPeriod, currentMonthAmt, memo, ordinal));
        }
        return dbItemDepreList;
    }

    private String getMemo(double currentMonthAmtBack, Double currentMonthAmt, Object ogaType) {
        StringBuffer memo = new StringBuffer();
        if (CommonAssetDepreItemOgaTypeEnum.CURRENT.equals(ogaType)) {
            memo.append("\u5f53\u5e74\u8d44\u4ea7");
        } else if (CommonAssetDepreItemOgaTypeEnum.DELAY.equals(ogaType)) {
            memo.append("\u5ef6\u8fdf\u8d44\u4ea7");
        } else {
            memo.append("\u5408\u5e76");
        }
        memo.append(currentMonthAmtBack - currentMonthAmt == 0.0 ? "\u81ea\u52a8\u6298\u65e7" : "\u81ea\u52a8\u6298\u65e7\uff08\u6821\u9a8c\u8ba1\u7b97\uff09");
        return memo.toString();
    }

    private double getOgaDbItemDepreAmtSum(String ogaType, List<DefaultTableEntity> dbItemDepreList) {
        return dbItemDepreList.stream().mapToDouble(item -> {
            if (!ogaType.equals(item.getFieldValue("OGATYPE"))) {
                return 0.0;
            }
            return InvestBillTool.getDoubleValue((DefaultTableEntity)item, (String)"DPCAAMT");
        }).summaryStatistics().getSum();
    }

    private DefaultTableEntity saveItemAndGetDefaultTableEntity(DefaultTableEntity master, Object ogaType, YearPeriodDO yearPeriod, double currentMonthAmt, String memo, double ordinal) {
        String id = UUIDUtils.newUUIDStr();
        SQLHelper sqlHelper = new SQLHelper("GC_COMMONASSETBILLITEM");
        HashMap<String, Object> record = new HashMap<String, Object>();
        record.put("ID", id);
        record.put("VER", System.currentTimeMillis());
        record.put("MASTERID", master.getId());
        record.put("ORDINAL", ordinal);
        record.put("BILLCODE", InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"BILLCODE"));
        record.put("UNITCODE", InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"UNITCODE"));
        record.put("OPPUNITCODE", InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"OPPUNITCODE"));
        record.put("DPCATYPE", (Object)CommonAssetDepreItemTypeEnum.AUTO);
        record.put("DPCAMONTH", yearPeriod.getEndDate());
        record.put("DPCAAMT", currentMonthAmt);
        record.put("MEMO", memo);
        record.put("OGATYPE", ogaType);
        sqlHelper.saveData(record);
        DefaultTableEntity currentMonthAmtEntity = new DefaultTableEntity();
        currentMonthAmtEntity.setId(id);
        currentMonthAmtEntity.resetFields(record);
        return currentMonthAmtEntity;
    }

    private double checkCurrentMonthAmt(DefaultTableEntity master, double dbItemDepreAmtSum, double currentMonthAmt) {
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double rmValueRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"RMVALUERATE");
        double surplusUnrealizedGainLoss = unrealizedGainLoss - assetsLoss;
        double totalDepreAmt = surplusUnrealizedGainLoss * (100.0 - rmValueRate) / 100.0;
        if (surplusUnrealizedGainLoss > 0.0) {
            if (dbItemDepreAmtSum + currentMonthAmt > totalDepreAmt && (currentMonthAmt = totalDepreAmt - dbItemDepreAmtSum) < 0.0) {
                currentMonthAmt = 0.0;
            }
        } else if (surplusUnrealizedGainLoss < 0.0) {
            if (dbItemDepreAmtSum + currentMonthAmt < totalDepreAmt && (currentMonthAmt = totalDepreAmt - dbItemDepreAmtSum) > 0.0) {
                currentMonthAmt = 0.0;
            }
        } else {
            currentMonthAmt = 0.0;
        }
        currentMonthAmt = OffsetVchrItemNumberUtils.round((double)currentMonthAmt);
        return currentMonthAmt;
    }

    private Double calcCurrentMonthAmt(DefaultTableEntity master, String assetType, double dbItemDepreAmtSum, YearPeriodDO yearPeriod) {
        String dpcaMethod = InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD");
        double dpcaMonth = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        if (AVERAGE_AGE_METHOD.equals(dpcaMethod) && dpcaMonth == 0.0) {
            throw new BusinessRuntimeException(String.format("\u8d44\u4ea7\u7c7b\u578b\u3010%1$s\u3011\u4f7f\u7528\u5e74\u9650\u5e73\u5747\u6cd5\u6298\u65e7\u65f6\u5269\u4f59\u6298\u65e7\u6708\u4e0d\u80fd\u4e3a0", assetType));
        }
        if ("DFA".equals(assetType)) {
            DefaultTableEntity master1 = new DefaultTableEntity();
            BeanUtils.copyProperties(master, master1);
            master1.getFields().putAll(master.getFields());
            master1.addFieldValue("PURCHASEDATE", (Object)this.getPurchaseDateDayOfYear(master));
            master = master1;
            assetType = "FA";
        }
        if ("TFA".equals(assetType)) {
            return this.calcOgaCurrentMonthAmt(master, yearPeriod, dbItemDepreAmtSum);
        }
        if (!"OGA".equals(assetType)) {
            return this.getDepreAmtByAssetTypeAndTimeType(master, assetType, dbItemDepreAmtSum, yearPeriod);
        }
        Object ogaType = master.getFieldValue("OGATYPE");
        if (CommonAssetDepreItemOgaTypeEnum.CURRENT.equals(ogaType)) {
            return this.calcOgaCurrentMonthAmt(master, yearPeriod, dbItemDepreAmtSum);
        }
        if (CommonAssetDepreItemOgaTypeEnum.DELAY.equals(ogaType)) {
            return this.getDepreAmtByAssetTypeAndTimeType(master, "FA", dbItemDepreAmtSum, yearPeriod);
        }
        return 0.0;
    }

    private Double calcOgaCurrentMonthAmt(DefaultTableEntity master, YearPeriodDO yearPeriod, double dbItemDepreAmtSum) {
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        int calculateYear = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)1);
        int calculateMonth = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)2);
        int purchaseYear = DateUtils.getDateFieldValue((Date)purchaseDate, (int)1);
        int purchaseMonth = DateUtils.getDateFieldValue((Date)purchaseDate, (int)2);
        if (!this.ogaNeedGenerate(master, yearPeriod)) {
            return null;
        }
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double rmValueRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"RMVALUERATE");
        double dpcaMonth = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        String dpcaMethod = InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD");
        if (COMPREHENSIVE_DEPRECIATION_METHOD.equals(dpcaMethod)) {
            double totalDepreAmt = (unrealizedGainLoss - assetsLoss) * (100.0 - rmValueRate) / 100.0;
            double dpcaRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCARATE");
            if (calculateYear == purchaseYear && calculateMonth == purchaseMonth) {
                return totalDepreAmt * dpcaRate / 100.0 * (double)purchaseMonth;
            }
            return totalDepreAmt * dpcaRate / 100.0;
        }
        double totalDepreAmt = (unrealizedGainLoss - assetsLoss - dbItemDepreAmtSum) * (100.0 - rmValueRate) / 100.0;
        if (calculateYear == purchaseYear && calculateMonth == purchaseMonth) {
            return totalDepreAmt / dpcaMonth * (double)purchaseMonth;
        }
        return totalDepreAmt / (dpcaMonth - (double)((calculateYear - purchaseYear) * 12 + calculateMonth - 1));
    }

    private boolean ogaNeedGenerate(DefaultTableEntity master, YearPeriodDO yearPeriod) {
        boolean needCheckExpireDate = AVERAGE_AGE_METHOD.equals(InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD"));
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        Date disposalDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"DISPOSALDATE");
        double dpcaMonth = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        int calculateYear = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)1);
        int calculateMonth = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)2);
        int purchaseYear = DateUtils.getDateFieldValue((Date)purchaseDate, (int)1);
        int purchaseMonth = DateUtils.getDateFieldValue((Date)purchaseDate, (int)2);
        Date expireDate = dpcaMonth <= 12.0 ? DateUtils.lastDateOf((int)purchaseYear, (int)12) : DateUtils.shifting((Date)DateUtils.lastDateOf((int)(purchaseYear + 1), (int)1), (int)2, (int)((int)Math.ceil(dpcaMonth) - 13));
        int expireYear = DateUtils.getDateFieldValue((Date)expireDate, (int)1);
        int expireMonth = DateUtils.getDateFieldValue((Date)expireDate, (int)2);
        if (purchaseYear > calculateYear) {
            return false;
        }
        if (purchaseYear == calculateYear && purchaseMonth > calculateMonth) {
            return false;
        }
        if (needCheckExpireDate && expireYear < calculateYear) {
            return false;
        }
        if (needCheckExpireDate && expireYear == calculateYear && expireMonth < calculateMonth) {
            return false;
        }
        if (disposalDate == null) {
            return true;
        }
        int disposalYear = DateUtils.getDateFieldValue((Date)disposalDate, (int)1);
        int disposalMonth = DateUtils.getDateFieldValue((Date)disposalDate, (int)2);
        if (disposalYear < calculateYear) {
            return false;
        }
        return disposalYear != calculateYear || disposalMonth >= calculateMonth;
    }

    private List<DefaultTableEntity> splitMaster(DefaultTableEntity master) {
        if (master == null) {
            return Collections.emptyList();
        }
        DefaultTableEntity master1 = new DefaultTableEntity();
        DefaultTableEntity master2 = new DefaultTableEntity();
        BeanUtils.copyProperties(master, master1);
        BeanUtils.copyProperties(master, master2);
        master1.getFields().putAll(master.getFields());
        master2.getFields().putAll(master.getFields());
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double initDpcaAmt = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"INITDPCAAMT");
        master1.addFieldValue("UNREALIZEDGAINLOSS", (Object)(unrealizedGainLoss / 2.0));
        master2.addFieldValue("UNREALIZEDGAINLOSS", (Object)(unrealizedGainLoss / 2.0));
        master1.addFieldValue("ASSETSLOSS", (Object)(assetsLoss / 2.0));
        master2.addFieldValue("ASSETSLOSS", (Object)(assetsLoss / 2.0));
        master1.addFieldValue("INITDPCAAMT", (Object)(initDpcaAmt / 2.0));
        master2.addFieldValue("INITDPCAAMT", (Object)(initDpcaAmt / 2.0));
        master1.addFieldValue("DPCAMETHOD", (Object)AVERAGE_AGE_METHOD);
        master2.addFieldValue("DPCAMETHOD", (Object)AVERAGE_AGE_METHOD);
        master1.addFieldValue("OGATYPE", (Object)CommonAssetDepreItemOgaTypeEnum.DELAY);
        master2.addFieldValue("OGATYPE", (Object)CommonAssetDepreItemOgaTypeEnum.CURRENT);
        master1.addFieldValue("PURCHASEDATE", (Object)this.getPurchaseDateDayOfYear(master));
        return Arrays.asList(master1, master2);
    }

    private Date getPurchaseDateDayOfYear(DefaultTableEntity master) {
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(purchaseDate);
        cal.set(6, cal.getActualMaximum(6));
        return cal.getTime();
    }

    private Double getDepreAmtByAssetTypeAndTimeType(DefaultTableEntity master, String assetType, double dbItemDepreAmtSum, YearPeriodDO yearPeriod) {
        double unrealizedGainLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"UNREALIZEDGAINLOSS");
        double assetsLoss = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"ASSETSLOSS");
        double rmValueRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"RMVALUERATE");
        double totalDepreAmt = unrealizedGainLoss * (100.0 - rmValueRate) / 100.0 - assetsLoss - dbItemDepreAmtSum;
        String dpcaMethod = InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD");
        if (!this.needGenerate(assetType, yearPeriod, master)) {
            return null;
        }
        if (AVERAGE_AGE_METHOD.equals(dpcaMethod)) {
            double surplusDepreMonth = this.getSurplusDepreMonth(master, assetType, yearPeriod);
            if (surplusDepreMonth == 0.0) {
                return 0.0;
            }
            return totalDepreAmt / surplusDepreMonth;
        }
        if (COMPREHENSIVE_DEPRECIATION_METHOD.equals(dpcaMethod)) {
            totalDepreAmt = (unrealizedGainLoss - assetsLoss) * (100.0 - rmValueRate) / 100.0;
            double dpcaRate = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCARATE");
            return totalDepreAmt * dpcaRate / 100.0;
        }
        return 0.0;
    }

    private double getSurplusDepreMonth(DefaultTableEntity master, String assetType, YearPeriodDO yearPeriod) {
        double dpcaYear = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        int calculateYear = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)1);
        int calculateMonth = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)2);
        int purchaseYear = DateUtils.getDateFieldValue((Date)purchaseDate, (int)1);
        int purchaseMonth = DateUtils.getDateFieldValue((Date)purchaseDate, (int)2);
        if ("FA".equalsIgnoreCase(assetType)) {
            return dpcaYear - (double)((calculateYear - purchaseYear) * 12 + calculateMonth - purchaseMonth - 1);
        }
        if ("IA".equalsIgnoreCase(assetType)) {
            return dpcaYear - (double)((calculateYear - purchaseYear) * 12 + calculateMonth - purchaseMonth);
        }
        return 0.0;
    }

    private boolean needGenerate(String assetType, YearPeriodDO yearPeriod, DefaultTableEntity master) {
        boolean needCheckExpireDate = AVERAGE_AGE_METHOD.equals(InvestBillTool.getStringValue((DefaultTableEntity)master, (String)"DPCAMETHOD"));
        double dpcaMonth = InvestBillTool.getDoubleValue((DefaultTableEntity)master, (String)"DPCAYEAR");
        Date purchaseDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"PURCHASEDATE");
        Date disposalDate = InvestBillTool.getDateValue((DefaultTableEntity)master, (String)"DISPOSALDATE");
        if ("FA".equalsIgnoreCase(assetType)) {
            purchaseDate = DateUtils.shifting((Date)purchaseDate, (int)2, (int)1);
            if (disposalDate != null) {
                disposalDate = DateUtils.shifting((Date)disposalDate, (int)2, (int)1);
            }
        }
        Date expireDate = DateUtils.shifting((Date)purchaseDate, (int)2, (int)((int)Math.ceil(dpcaMonth)));
        int calculateYear = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)1);
        int calculateMonth = DateUtils.getDateFieldValue((Date)yearPeriod.getEndDate(), (int)2);
        int purchaseYear = DateUtils.getDateFieldValue((Date)purchaseDate, (int)1);
        int purchaseMonth = DateUtils.getDateFieldValue((Date)purchaseDate, (int)2);
        int expireYear = DateUtils.getDateFieldValue((Date)expireDate, (int)1);
        int expireMonth = DateUtils.getDateFieldValue((Date)expireDate, (int)2);
        if (purchaseYear > calculateYear) {
            return false;
        }
        if (purchaseYear == calculateYear && purchaseMonth > calculateMonth) {
            return false;
        }
        if (needCheckExpireDate && expireYear < calculateYear) {
            return false;
        }
        if (needCheckExpireDate && expireYear == calculateYear && expireMonth <= calculateMonth) {
            return false;
        }
        if (disposalDate == null) {
            return true;
        }
        int disposalYear = DateUtils.getDateFieldValue((Date)disposalDate, (int)1);
        int disposalMonth = DateUtils.getDateFieldValue((Date)disposalDate, (int)2);
        if (disposalYear < calculateYear) {
            return false;
        }
        return disposalYear != calculateYear || disposalMonth > calculateMonth;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8bf4\u660e\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.title()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("assetType").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bFA(\u56fa\u5b9a\u8d44\u4ea7),IA(\u65e0\u5f62\u8d44\u4ea7),OGA(\u6cb9\u6c14\u8d44\u4ea7),DFA(\u5ef6\u8fdf\u56fa\u5b9a\u8d44\u4ea7),TFA(\u5f53\u5e74\u56fa\u5b9a\u8d44\u4ea7)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("timeType").append("\uff1a").append(DataType.toString((int)6)).append("\uff1bCURR\uff08\u5f53\u524d\u5e74\uff09,PRIOR\uff08\u4ee5\u524d\u5e74\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)3)).append("\uff1a").append(DataType.toString((int)3)).append("\uff1b").append("\u660e\u7ec6\u6298\u65e7\u989d\u7684\u548c").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u4ee5\u524d\u5e74\u65e0\u5f62\u8d44\u4ea7\u6298\u65e7\u603b\u6298\u65e7\u989d ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("DetailCommonAssetDepre('IA','PRIOR')").append(FunctionUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u660e\u7ec6\u5e38\u89c4\u8d44\u4ea7\u53f0\u8d26\u6298\u65e7";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 3;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String assetType = (String)parameters.get(0).evaluate(null);
        String timeType = (String)parameters.get(1).evaluate(null);
        Assert.isTrue((boolean)this.validateAssetType(assetType), (String)"DetailCommonAssetDepre\u51fd\u6570\u7b2c\u4e00\u4e2a\u53c2\u6570\u4e0d\u5408\u6cd5", (Object[])new Object[0]);
        Assert.isTrue((boolean)this.validateTimeType(timeType), (String)"DetailCommonAssetDepre\u51fd\u6570\u7b2c\u4e8c\u4e2a\u53c2\u6570\u4e0d\u5408\u6cd5", (Object[])new Object[0]);
        return super.validate(context, parameters);
    }

    private boolean validateTimeType(String timeType) {
        if ("CURR".equalsIgnoreCase(timeType)) {
            return true;
        }
        return "PRIOR".equalsIgnoreCase(timeType);
    }

    private boolean validateAssetType(String assetType) {
        if ("FA".equalsIgnoreCase(assetType)) {
            return true;
        }
        if ("IA".equalsIgnoreCase(assetType)) {
            return true;
        }
        if ("DFA".equalsIgnoreCase(assetType)) {
            return true;
        }
        if ("TFA".equalsIgnoreCase(assetType)) {
            return true;
        }
        return "OGA".equalsIgnoreCase(assetType);
    }
}

