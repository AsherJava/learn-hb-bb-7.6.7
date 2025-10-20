/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 */
package com.jiuqi.gcreport.lease.leasebill.bill.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.lease.leasebill.bill.impl.AbstractLeaseBillModel;
import com.jiuqi.gcreport.lease.leasebill.enums.InputTypeEnum;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaseSubBillCac {
    private MasterData masterData = new MasterData();
    private AbstractLeaseBillModel leaseBillModel;

    LeaseSubBillCac(DataRow dataRow, AbstractLeaseBillModel leaseBillModel) {
        this.leaseBillModel = leaseBillModel;
        this.initMaterData(dataRow);
    }

    public List<Map<String, Object>> doCalc(DataRow dataRow) {
        this.checkMasterData();
        List subBillItemList = this.leaseBillModel.getTable(this.leaseBillModel.getSubTableName()).getRowsData();
        if (null == this.masterData.getCollectBeginDate()) {
            this.masterData.setCollectBeginDate(this.masterData.getLeaseBeginDate());
        }
        int collectBeginMonth = DateUtils.getDateFieldValue((Date)this.masterData.getCollectBeginDate(), (int)2);
        int leaseBeginMonthTemp = DateUtils.getDateFieldValue((Date)this.masterData.getLeaseBeginDate(), (int)2);
        int monthDiff = collectBeginMonth - leaseBeginMonthTemp;
        this.setParamsAndSubItem(subBillItemList);
        int leaseBeginYear = DateUtils.getDateFieldValue((Date)this.masterData.getLeaseBeginDate(), (int)1);
        int leaseBeginMonth = DateUtils.getDateFieldValue((Date)this.masterData.getLeaseBeginDate(), (int)2);
        List subBillRecordList = subBillItemList;
        boolean beginAmtFirstlyFlag = false;
        boolean containSubManualData = false;
        int collectBeginYear = DateUtils.getDateFieldValue((Date)this.masterData.getCollectBeginDate(), (int)1);
        if (subBillItemList.size() > 0) {
            containSubManualData = true;
            monthDiff = leaseBeginYear == collectBeginYear && leaseBeginMonth < collectBeginMonth ? collectBeginMonth - leaseBeginMonth : (leaseBeginYear < collectBeginYear ? (collectBeginYear - leaseBeginYear) * 12 - leaseBeginMonth + collectBeginMonth : 0);
        }
        for (int i = 0; i < this.masterData.getLeaseHold(); ++i) {
            HashMap<String, Object> oneSubBillRecord = new HashMap<String, Object>();
            this.initBasicField(oneSubBillRecord, dataRow.getString("ID"));
            int year = (i + leaseBeginMonth - 1) / 12 + leaseBeginYear;
            int month = (i + leaseBeginMonth) % 12 == 0 ? 12 : (i + leaseBeginMonth) % 12;
            oneSubBillRecord.put("CHANGEMONTH", year + "-" + (month > 9 ? Integer.valueOf(month) : "0" + month));
            if ((year > collectBeginYear || year == collectBeginYear && month >= collectBeginMonth) && this.masterData.getCollectAmtPeriodNum() > 0 && i >= monthDiff && Math.abs(month - collectBeginMonth) % this.masterData.getCollectAmtPeriodNum() == 0) {
                beginAmtFirstlyFlag = this.calcDebtPart(subBillRecordList, oneSubBillRecord, beginAmtFirstlyFlag, containSubManualData, i, monthDiff);
            }
            this.calcAssetsPart(subBillRecordList, oneSubBillRecord, i, leaseBeginMonth);
            subBillRecordList.add(oneSubBillRecord);
        }
        return subBillRecordList;
    }

    private boolean calcDebtPart(List<Map<String, Object>> newItemList, Map<String, Object> subBillFieldCode2ValueMap, boolean beginAmtFirstlyFlag, boolean hasManualFlag, int i, int monthDiff) {
        if (hasManualFlag) {
            beginAmtFirstlyFlag = true;
            if (i != 0 && i - monthDiff == 0 || i == 0 && monthDiff == 0) {
                beginAmtFirstlyFlag = false;
            }
            ++i;
        }
        if (i == 0 || i == monthDiff || (i - monthDiff) % this.masterData.getCollectAmtPeriodNum() == 0 || hasManualFlag) {
            if (beginAmtFirstlyFlag) {
                Map<String, Object> lastItem = newItemList.get(newItemList.size() - this.masterData.getCollectAmtPeriodNum());
                subBillFieldCode2ValueMap.put("BEGINNAMT", lastItem.get("ENDAMT"));
            } else {
                subBillFieldCode2ValueMap.put("BEGINNAMT", NumberUtils.sub((double)this.masterData.getInitLeasePayAmt(), (double)this.masterData.getInitUnfinIncome()));
                beginAmtFirstlyFlag = true;
            }
            String unfinAmortizeCode = this.leaseBillModel.calcFn_unfinAmortize();
            subBillFieldCode2ValueMap.put(unfinAmortizeCode, NumberUtils.round((double)NumberUtils.mul((double)ConverterUtils.getAsDouble((Object)subBillFieldCode2ValueMap.get("BEGINNAMT")), (double)(this.masterData.discountRate / 100.0))));
            double collectAmt = NumberUtils.round((double)this.masterData.getCollectAmt());
            subBillFieldCode2ValueMap.put("LEASEPAYAMT", collectAmt);
            double sumAmt = NumberUtils.sum((Double)ConverterUtils.getAsDouble((Object)subBillFieldCode2ValueMap.get("BEGINNAMT")), (Double)ConverterUtils.getAsDouble((Object)subBillFieldCode2ValueMap.get(unfinAmortizeCode)));
            double endAmt = NumberUtils.sub((double)sumAmt, (double)collectAmt);
            subBillFieldCode2ValueMap.put("ENDAMT", endAmt);
            if (this.masterData.getLeaseHold() - i <= this.masterData.getCollectAmtPeriodNum()) {
                subBillFieldCode2ValueMap.put("ENDAMT", 0.0);
                subBillFieldCode2ValueMap.put("LEASEPAYAMT", this.masterData.getLastPeriodCollectAmt());
                subBillFieldCode2ValueMap.put(unfinAmortizeCode, NumberUtils.sub((double)this.masterData.getLastPeriodCollectAmt(), (double)ConverterUtils.getAsDoubleValue((Object)subBillFieldCode2ValueMap.get("BEGINNAMT"))));
            }
        }
        return beginAmtFirstlyFlag;
    }

    private void calcAssetsPart(List<Map<String, Object>> newItemList, Map<String, Object> subBillFieldCode2ValueMap, int i, int leaseBeginMonth) {
        double monthlyDepreAmt = NumberUtils.round((double)this.masterData.getMonthlyDepreAmt());
        subBillFieldCode2ValueMap.put("MONTHLYDEPREAMT", NumberUtils.round((double)this.masterData.getMonthlyDepreAmt()));
        Map<Object, Object> lastItem = new HashMap();
        if (i == 0 && CollectionUtils.isEmpty(newItemList)) {
            subBillFieldCode2ValueMap.put(this.leaseBillModel.calcFn_reaseFinBeginAmt(), NumberUtils.round((double)this.masterData.getInitFinLeaseAsset()));
            subBillFieldCode2ValueMap.put("CUMULATIVEDEPRE", monthlyDepreAmt);
            subBillFieldCode2ValueMap.put("YEARDEPREAMT", monthlyDepreAmt);
        } else {
            lastItem = newItemList.get(newItemList.size() - 1);
            subBillFieldCode2ValueMap.put(this.leaseBillModel.calcFn_reaseFinBeginAmt(), lastItem.get(this.leaseBillModel.calcFn_reaseFinEndAmt()));
            subBillFieldCode2ValueMap.put("CUMULATIVEDEPRE", NumberUtils.sum((double)ConverterUtils.getAsDoubleValue((Object)lastItem.get("CUMULATIVEDEPRE")), (double)monthlyDepreAmt));
            if ((i + leaseBeginMonth - 1) % 12 == 0) {
                subBillFieldCode2ValueMap.put("YEARDEPREAMT", NumberUtils.round((double)this.masterData.getMonthlyDepreAmt()));
            } else {
                subBillFieldCode2ValueMap.put("YEARDEPREAMT", NumberUtils.sum((double)ConverterUtils.getAsDoubleValue((Object)lastItem.get("YEARDEPREAMT")), (double)monthlyDepreAmt));
            }
        }
        subBillFieldCode2ValueMap.put(this.leaseBillModel.calcFn_reaseFinEndAmt(), NumberUtils.sub((double)ConverterUtils.getAsDoubleValue((Object)subBillFieldCode2ValueMap.get(this.leaseBillModel.calcFn_reaseFinBeginAmt())), (double)monthlyDepreAmt));
        String modelType = this.leaseBillModel.getDefine().getModelType();
        if (i == this.masterData.getLeaseHold() - 1 && "TenantryBillModel".equals(modelType)) {
            subBillFieldCode2ValueMap.put(this.leaseBillModel.calcFn_reaseFinEndAmt(), 0.0);
            subBillFieldCode2ValueMap.put("MONTHLYDEPREAMT", lastItem.get(this.leaseBillModel.calcFn_reaseFinEndAmt()));
            subBillFieldCode2ValueMap.put("YEARDEPREAMT", NumberUtils.sum((Double)ConverterUtils.getAsDouble((Object)lastItem.get("YEARDEPREAMT")), (Double)((Double)subBillFieldCode2ValueMap.get("MONTHLYDEPREAMT"))));
            subBillFieldCode2ValueMap.put("CUMULATIVEDEPRE", NumberUtils.sum((Double)ConverterUtils.getAsDouble((Object)lastItem.get("CUMULATIVEDEPRE")), (Double)((Double)subBillFieldCode2ValueMap.get("MONTHLYDEPREAMT"))));
        }
        subBillFieldCode2ValueMap.put("INPUTTYPE", InputTypeEnum.AUTO.getCode());
    }

    private void initMaterData(DataRow dataRow) {
        this.masterData.setCollectAmtPeriodNum(CollectAmtPeriodEnum.getCollectAmtPeriodNum(dataRow.getString("COLLECTAMTPERIOD")));
        this.masterData.setLeaseHold(ConverterUtils.getAsInteger((Object)dataRow.getString("LEASEHOLD"), (Integer)0));
        this.masterData.setLeaseBeginDate(dataRow.getDate("LEASEBEGINDATE"));
        this.masterData.setInitLeasePayAmt(ConverterUtils.getAsDouble((Object)dataRow.getString("INITLEASEPAYAMT"), (Double)0.0));
        this.masterData.setLastPeriodCollectAmt(ConverterUtils.getAsDouble((Object)dataRow.getString("LASTPERIODCOLLECTAMT"), (Double)0.0));
        this.masterData.setDiscountRate(ConverterUtils.getAsDouble((Object)dataRow.getString("DISCOUNTRATE"), (Double)0.0));
        this.masterData.setCollectAmt(ConverterUtils.getAsDouble((Object)dataRow.getString("COLLECTAMT"), (Double)0.0));
        this.masterData.setCollectAmt(ConverterUtils.getAsDouble((Object)dataRow.getString("COLLECTAMT"), (Double)0.0));
        this.masterData.setMonthlyDepreAmt(ConverterUtils.getAsDouble((Object)dataRow.getString("MONTHLYDEPREAMT"), (Double)0.0));
        this.masterData.setCollectBeginDate(dataRow.getDate("COLLECTBEGINDATE"));
        this.masterData.setInitFinLeaseAsset(ConverterUtils.getAsDouble((Object)dataRow.getValue(this.leaseBillModel.calcFn_initFinLeaseAsset()), (Double)0.0));
        this.masterData.setInitUnfinIncome(ConverterUtils.getAsDouble((Object)dataRow.getValue(this.leaseBillModel.calcFn_initUnfinIncome()), (Double)0.0));
    }

    private void checkMasterData() {
        if (this.masterData.getLeaseHold() == 0) {
            throw new BillException("\u8bf7\u586b\u5199\u79df\u8d41\u671f\u9650(\u6708)");
        }
        if (this.masterData.getLeaseBeginDate() == null) {
            throw new BillException("\u8bf7\u586b\u5199\u79df\u8d41\u5f00\u59cb\u65e5");
        }
    }

    private void setParamsAndSubItem(List<Map<String, Object>> subBillItemList) {
        int index = 0;
        String monthStrTemp = "0000-01";
        for (int i = 0; i < subBillItemList.size(); ++i) {
            Map<String, Object> subBillItem = subBillItemList.get(i);
            HashMap inputTypeMap = (HashMap)subBillItem.get("INPUTTYPE");
            if (null == inputTypeMap || !InputTypeEnum.MANUAL.getCode().equals(inputTypeMap.get("name"))) continue;
            String monthStr = (String)subBillItem.get("CHANGEMONTH");
            this.calcManualRowData(subBillItemList, i, subBillItem);
            if (monthStr.compareTo(monthStrTemp) <= 0) continue;
            monthStrTemp = monthStr;
            index = i;
        }
        if ("0000-01".equals(monthStrTemp)) {
            subBillItemList.clear();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtils.parse((String)monthStrTemp, (String)"yyyy-MM"));
            cal.add(2, 1);
            this.masterData.setLeaseBeginDate(cal.getTime());
            subBillItemList.removeAll(subBillItemList.subList(index + 1, subBillItemList.size()));
            this.masterData.setLeaseHold(this.masterData.getLeaseHold() - (index + 1));
            ((DataTableImpl)this.leaseBillModel.getData().getTables().get(this.leaseBillModel.getSubTableName())).setRowsData(subBillItemList);
        }
    }

    private void calcManualRowData(List<Map<String, Object>> subBillItemList, int i, Map<String, Object> subBillItem) {
        Double beginAmt = ConverterUtils.getAsDouble((Object)subBillItem.get("BEGINNAMT"));
        if (null != beginAmt) {
            double sumAmt = NumberUtils.sumDouble((Object)subBillItem.get("BEGINNAMT"), (Object)subBillItem.get(this.leaseBillModel.calcFn_unfinAmortize()));
            double endAmt = NumberUtils.subDouble((Object)sumAmt, (Object)subBillItem.get("LEASEPAYAMT"));
            subBillItem.put("ENDAMT", endAmt);
        }
        subBillItem.put(this.leaseBillModel.calcFn_reaseFinEndAmt(), NumberUtils.subDouble((Object)subBillItem.get(this.leaseBillModel.calcFn_reaseFinBeginAmt()), (Object)subBillItem.get("MONTHLYDEPREAMT")));
        if (i == 0) {
            subBillItem.put("YEARDEPREAMT", subBillItem.get("MONTHLYDEPREAMT"));
            subBillItem.put("CUMULATIVEDEPRE", subBillItem.get("MONTHLYDEPREAMT"));
        } else {
            Map<String, Object> lastSubBillItem = subBillItemList.get(i - 1);
            String monthStr = (String)subBillItem.get("CHANGEMONTH");
            if (monthStr.substring(5).equals("01")) {
                subBillItem.put("YEARDEPREAMT", subBillItem.get("MONTHLYDEPREAMT"));
            } else {
                subBillItem.put("YEARDEPREAMT", NumberUtils.sumDouble((Object)lastSubBillItem.get("YEARDEPREAMT"), (Object)subBillItem.get("MONTHLYDEPREAMT")));
            }
            subBillItem.put("CUMULATIVEDEPRE", NumberUtils.sumDouble((Object)lastSubBillItem.get("CUMULATIVEDEPRE"), (Object)subBillItem.get("MONTHLYDEPREAMT")));
        }
    }

    private void initBasicField(Map<String, Object> subBillFieldCode2ValueMap, String masterId) {
        subBillFieldCode2ValueMap.put("MASTERID", masterId);
        subBillFieldCode2ValueMap.put("ID", UUIDOrderUtils.newUUIDStr());
        subBillFieldCode2ValueMap.put("VER", System.currentTimeMillis());
    }

    class MasterData {
        private int collectAmtPeriodNum;
        private int leaseHold;
        private Date leaseBeginDate;
        private double initLeasePayAmt;
        private double discountRate;
        private double collectAmt;
        private double lastPeriodCollectAmt;
        private double initFinLeaseAsset;
        private double monthlyDepreAmt;
        private double initUnfinIncome;
        private Date collectBeginDate;

        MasterData() {
        }

        public int getCollectAmtPeriodNum() {
            return this.collectAmtPeriodNum;
        }

        public void setCollectAmtPeriodNum(int collectAmtPeriodNum) {
            this.collectAmtPeriodNum = collectAmtPeriodNum;
        }

        public int getLeaseHold() {
            return this.leaseHold;
        }

        public void setLeaseHold(int leaseHold) {
            this.leaseHold = leaseHold;
        }

        public double getInitLeasePayAmt() {
            return this.initLeasePayAmt;
        }

        public void setInitLeasePayAmt(double initLeasePayAmt) {
            this.initLeasePayAmt = initLeasePayAmt;
        }

        public double getDiscountRate() {
            return this.discountRate;
        }

        public void setDiscountRate(double discountRate) {
            this.discountRate = discountRate;
        }

        public double getCollectAmt() {
            return this.collectAmt;
        }

        public void setCollectAmt(double collectAmt) {
            this.collectAmt = collectAmt;
        }

        public double getInitFinLeaseAsset() {
            return this.initFinLeaseAsset;
        }

        public void setInitFinLeaseAsset(double initFinLeaseAsset) {
            this.initFinLeaseAsset = initFinLeaseAsset;
        }

        public double getMonthlyDepreAmt() {
            return this.monthlyDepreAmt;
        }

        public void setMonthlyDepreAmt(double monthlyDepreAmt) {
            this.monthlyDepreAmt = monthlyDepreAmt;
        }

        public Date getLeaseBeginDate() {
            return this.leaseBeginDate;
        }

        public void setLeaseBeginDate(Date leaseBeginDate) {
            this.leaseBeginDate = leaseBeginDate;
        }

        public double getInitUnfinIncome() {
            return this.initUnfinIncome;
        }

        public void setInitUnfinIncome(double initUnfinIncome) {
            this.initUnfinIncome = initUnfinIncome;
        }

        public Date getCollectBeginDate() {
            return this.collectBeginDate;
        }

        public void setCollectBeginDate(Date collectBeginDate) {
            this.collectBeginDate = collectBeginDate;
        }

        public double getLastPeriodCollectAmt() {
            return this.lastPeriodCollectAmt;
        }

        public void setLastPeriodCollectAmt(double lastPeriodCollectAmt) {
            this.lastPeriodCollectAmt = lastPeriodCollectAmt;
        }
    }

    public static enum CollectAmtPeriodEnum {
        Y("Y", "\u6708", 1),
        J("J", "\u5b63\u5ea6", 3),
        H("H", "\u534a\u5e74", 6),
        N("N", "\u5e74", 12),
        B("B", "\u4e0d\u5b9a\u671f", 0);

        private String code;
        private String title;
        private int value;

        private CollectAmtPeriodEnum(String code, String title, int value) {
            this.code = code;
            this.title = title;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public String getTitle() {
            return this.title;
        }

        public int getValue() {
            return this.value;
        }

        private static int getCollectAmtPeriodNum(String collectAmtPeriod) {
            int collectAmtPeriodNum = Y.getCode().equals(collectAmtPeriod) ? Y.getValue() : (J.getCode().equals(collectAmtPeriod) ? J.getValue() : (H.getCode().equals(collectAmtPeriod) ? H.getValue() : (N.getCode().equals(collectAmtPeriod) ? N.getValue() : B.getValue())));
            return collectAmtPeriodNum;
        }
    }
}

