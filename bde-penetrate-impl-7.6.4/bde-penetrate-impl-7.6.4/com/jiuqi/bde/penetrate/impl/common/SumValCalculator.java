/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.SumTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.bde.penetrate.impl.common;

import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.SumTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.penetrate.client.dto.PenetrateBaseDTO;
import com.jiuqi.bde.penetrate.impl.common.RowTypeEnum;
import com.jiuqi.bde.penetrate.impl.core.intf.PenetrateBalance;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.va.domain.common.PageVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SumValCalculator {
    private PenetrateBaseDTO condi;
    private SumTypeEnum sumType;
    private List<PenetrateBalance> balanceList;

    public SumValCalculator(PenetrateBaseDTO condi, List<PenetrateBalance> balanceList) {
        this.condi = condi;
        this.sumType = condi.getSumType() == null ? SumTypeEnum.FMX : SumTypeEnum.fromCode((String)condi.getSumType());
        this.balanceList = balanceList;
    }

    public PageVO<PenetrateBalance> cacl() {
        HashMap<String, PenetrateBalance> sumBalanceMap = new HashMap<String, PenetrateBalance>(this.sumType == SumTypeEnum.MX ? this.balanceList.size() : this.balanceList.size() / 3);
        for (PenetrateBalance penetrateBalance : this.balanceList) {
            String sumKey = this.getSumKey(penetrateBalance);
            sumBalanceMap.computeIfAbsent(sumKey, key -> {
                PenetrateBalance visualBalance = new PenetrateBalance();
                visualBalance.putAll(penetrateBalance);
                visualBalance.setNc(BigDecimal.ZERO);
                visualBalance.setDsum(BigDecimal.ZERO);
                visualBalance.setCsum(BigDecimal.ZERO);
                visualBalance.setYe(BigDecimal.ZERO);
                visualBalance.setOrgnc(BigDecimal.ZERO);
                visualBalance.setOrgnd(BigDecimal.ZERO);
                visualBalance.setOrgnc(BigDecimal.ZERO);
                visualBalance.setOrgnYe(BigDecimal.ZERO);
                if (SumTypeEnum.FMX == this.sumType) {
                    visualBalance.setSubjectCode(this.condi.getSubjectCode());
                }
                return visualBalance;
            });
            PenetrateBalance sumBalance = (PenetrateBalance)sumBalanceMap.get(sumKey);
            sumBalance.setNc(NumberUtils.sum((BigDecimal)sumBalance.getNc(), (BigDecimal)penetrateBalance.getNc()));
            sumBalance.setDsum(NumberUtils.sum((BigDecimal)sumBalance.getDsum(), (BigDecimal)penetrateBalance.getDsum()));
            sumBalance.setCsum(NumberUtils.sum((BigDecimal)sumBalance.getCsum(), (BigDecimal)penetrateBalance.getCsum()));
            sumBalance.setYe(NumberUtils.sum((BigDecimal)sumBalance.getYe(), (BigDecimal)penetrateBalance.getYe()));
            sumBalance.setOrgnNc(NumberUtils.sum((BigDecimal)sumBalance.getNc(), (BigDecimal)penetrateBalance.getOrgnNc()));
            sumBalance.setOrgnDsum(NumberUtils.sum((BigDecimal)sumBalance.getDsum(), (BigDecimal)penetrateBalance.getOrgnDsum()));
            sumBalance.setOrgnCsum(NumberUtils.sum((BigDecimal)sumBalance.getCsum(), (BigDecimal)penetrateBalance.getOrgnCsum()));
            sumBalance.setOrgnYe(NumberUtils.sum((BigDecimal)sumBalance.getYe(), (BigDecimal)penetrateBalance.getOrgnYe()));
        }
        PenetrateBalance total = new PenetrateBalance();
        total.setRowType(RowTypeEnum.TOTAL.ordinal());
        total.setSubjectCode(GcI18nUtil.getMessage((String)"bde.accountant.total"));
        for (PenetrateBalance balance : sumBalanceMap.values()) {
            balance.setJnc(this.getSumVal(FetchTypeEnum.JNC, balance.getNc()));
            balance.setDnc(this.getSumVal(FetchTypeEnum.DNC, balance.getNc()));
            balance.setJyh(this.getSumVal(FetchTypeEnum.JYH, balance.getYe()));
            balance.setDyh(this.getSumVal(FetchTypeEnum.DYH, balance.getYe()));
            balance.setWjnc(this.getSumVal(FetchTypeEnum.WJNC, balance.getOrgnNc()));
            balance.setWdnc(this.getSumVal(FetchTypeEnum.WDNC, balance.getOrgnNc()));
            balance.setWjyh(this.getSumVal(FetchTypeEnum.WJYH, balance.getOrgnYe()));
            balance.setWdyh(this.getSumVal(FetchTypeEnum.WDYH, balance.getOrgnYe()));
            total.setJnc(NumberUtils.sum((BigDecimal)total.getJnc(), (BigDecimal)balance.getJnc()));
            total.setDnc(NumberUtils.sum((BigDecimal)total.getDnc(), (BigDecimal)balance.getDnc()));
            total.setJyh(NumberUtils.sum((BigDecimal)total.getJyh(), (BigDecimal)balance.getJyh()));
            total.setDyh(NumberUtils.sum((BigDecimal)total.getDyh(), (BigDecimal)balance.getDyh()));
            total.setWjnc(NumberUtils.sum((BigDecimal)total.getWjnc(), (BigDecimal)balance.getWjnc()));
            total.setWdnc(NumberUtils.sum((BigDecimal)total.getWdnc(), (BigDecimal)balance.getWdnc()));
            total.setWjyh(NumberUtils.sum((BigDecimal)total.getWjyh(), (BigDecimal)balance.getWjyh()));
            total.setWdyh(NumberUtils.sum((BigDecimal)total.getWdyh(), (BigDecimal)balance.getWdyh()));
            total.setDsum(NumberUtils.sum((BigDecimal)total.getDsum(), (BigDecimal)balance.getDsum()));
            total.setCsum(NumberUtils.sum((BigDecimal)total.getCsum(), (BigDecimal)balance.getCsum()));
            total.setOrgnDsum(NumberUtils.sum((BigDecimal)total.getDsum(), (BigDecimal)balance.getOrgnDsum()));
            total.setOrgnCsum(NumberUtils.sum((BigDecimal)total.getCsum(), (BigDecimal)balance.getOrgnCsum()));
        }
        List list = sumBalanceMap.entrySet().stream().sorted(new Comparator<Map.Entry<String, PenetrateBalance>>(){

            @Override
            public int compare(Map.Entry<String, PenetrateBalance> o1, Map.Entry<String, PenetrateBalance> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        }).map(item -> (PenetrateBalance)item.getValue()).collect(Collectors.toList());
        ArrayList result = CollectionUtils.newArrayList((Object[])new PenetrateBalance[]{total});
        List detailData = list.stream().skip(this.condi.getOffset().intValue()).limit(this.condi.getLimit().intValue()).collect(Collectors.toList());
        result.addAll(detailData);
        return new PageVO((List)result, list.size());
    }

    protected String getSumKey(PenetrateBalance assBalance) {
        StringBuffer assistKey = new StringBuffer(this.sumType == SumTypeEnum.FMX ? "#" : assBalance.getSubjectCode());
        for (Dimension dimension : this.condi.getAssTypeList()) {
            assistKey.append("|").append(ModelExecuteUtil.getValByDefault(assBalance.get(dimension.getDimCode())));
        }
        return assistKey.toString();
    }

    public BigDecimal getSumVal(FetchTypeEnum fetchType, BigDecimal sumVal) {
        switch (fetchType) {
            case JNC: 
            case JYH: 
            case WJNC: 
            case WJYH: {
                if (sumVal.compareTo(BigDecimal.ZERO) > 0) {
                    return sumVal;
                }
                return BigDecimal.ZERO;
            }
            case DNC: 
            case DYH: 
            case WDNC: 
            case WDYH: {
                if (sumVal.compareTo(BigDecimal.ZERO) < 0) {
                    return sumVal.abs();
                }
                return BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }
}

