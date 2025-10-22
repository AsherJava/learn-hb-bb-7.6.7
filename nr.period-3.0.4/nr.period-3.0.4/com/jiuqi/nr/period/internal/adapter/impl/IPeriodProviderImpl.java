/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.period.internal.adapter.impl;

import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.period.cache.PeriodDataRowCache;
import com.jiuqi.nr.period.cache.PeriodEntityCache;
import com.jiuqi.nr.period.common.language.LanguageCommon;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.I18nPeriodRow;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IPeriodProviderImpl
extends DefaultPeriodAdapter
implements IPeriodProvider {
    private String periodId;
    private PeriodDataRowCache periodDataRowCache;
    private PeriodEntityCache periodEntityCache;

    public IPeriodProviderImpl() {
    }

    public IPeriodProviderImpl(String periodId, PeriodDataRowCache periodDataRowCache, PeriodEntityCache periodEntityCache) {
        this.periodId = periodId;
        this.periodDataRowCache = periodDataRowCache;
        this.periodEntityCache = periodEntityCache;
    }

    private IPeriodRow getPeriodData(String periodId, String period) {
        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(periodId);
        IPeriodRow periodData = this.periodDataRowCache.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return null;
        }
        I18nPeriodRow i18nPeriodRow = new I18nPeriodRow(periodData);
        i18nPeriodRow.setTitle(LanguageCommon.getPeriodItemTitle(periodByKey.getPeriodType(), periodData));
        return i18nPeriodRow;
    }

    private IPeriodRow getPeriodData(String periodId, PeriodWrapper periodWrapper) {
        if (null == periodWrapper) {
            return null;
        }
        IPeriodEntity periodByKey = this.getPeriodEntity();
        if (PeriodType.CUSTOM.equals((Object)periodByKey.getPeriodType()) || PeriodUtils.isPeriod13(periodByKey.getCode(), periodByKey.getPeriodType())) {
            IPeriodRow periodData = this.periodDataRowCache.getPeriodData(periodByKey.getKey(), periodWrapper.toString());
            if (null == periodData) {
                return null;
            }
            I18nPeriodRow i18nPeriodRow = new I18nPeriodRow(periodData);
            i18nPeriodRow.setTitle(LanguageCommon.getPeriodItemTitle(periodByKey.getPeriodType(), periodData));
            return i18nPeriodRow;
        }
        return this.getPeriodData(this.periodId, periodWrapper.toString());
    }

    private List<IPeriodRow> getDataListByKey(String periodId) {
        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(periodId);
        List<IPeriodRow> allPeriodData = this.periodDataRowCache.getDataListByKey(periodId);
        if (null == allPeriodData) {
            return new ArrayList<IPeriodRow>();
        }
        ArrayList<IPeriodRow> datas = new ArrayList<IPeriodRow>();
        for (IPeriodRow allPeriodDatum : allPeriodData) {
            I18nPeriodRow i18nPeriodRow = new I18nPeriodRow(allPeriodDatum);
            i18nPeriodRow.setTitle(LanguageCommon.getPeriodItemTitle(periodByKey.getPeriodType(), allPeriodDatum));
            datas.add(i18nPeriodRow);
        }
        return datas;
    }

    private IPeriodRow getCurPeriod(IPeriodEntity queryPeriod) {
        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(this.periodId);
        IPeriodRow curPeriod = this.periodDataRowCache.getCurPeriod(queryPeriod);
        if (null == curPeriod) {
            return null;
        }
        I18nPeriodRow i18nPeriodRow = new I18nPeriodRow(curPeriod);
        i18nPeriodRow.setTitle(LanguageCommon.getPeriodItemTitle(periodByKey.getPeriodType(), curPeriod));
        return i18nPeriodRow;
    }

    public String getPeriodTitle(String period) {
        IPeriodRow periodData = this.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return "";
        }
        return periodData.getTitle();
    }

    public Date[] getPeriodDateRegion(String period) throws ParseException {
        if (this.isDefaultPeriodAdapter(this.getPeriodEntity())) {
            return super.getPeriodDateRegion(period);
        }
        IPeriodRow periodData = this.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return new Date[]{null, null};
        }
        return new Date[]{periodData.getStartDate(), periodData.getEndDate()};
    }

    public String[] getPeriodDateStrRegion(String period) throws ParseException {
        if (this.isDefaultPeriodAdapter(this.getPeriodEntity())) {
            return super.getPeriodDateStrRegion(period);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        IPeriodRow periodData = this.getPeriodData(this.periodId, period);
        String[] res = new String[]{null, null};
        if (null != periodData) {
            if (null != periodData.getStartDate()) {
                res[0] = sdf.format(periodData.getStartDate());
            }
            if (null != periodData.getEndDate()) {
                res[1] = sdf.format(periodData.getEndDate());
            }
        }
        return res;
    }

    @Override
    public IPeriodEntity getPeriodEntity() {
        return this.periodEntityCache.getPeriodByKey(this.periodId);
    }

    public String modify(String period, PeriodModifier modifier, IPeriodAdapter targetPeriodAdapter) {
        if (null != targetPeriodAdapter && targetPeriodAdapter instanceof IPeriodProvider) {
            IPeriodProvider provider = (IPeriodProvider)targetPeriodAdapter;
            return this.modifyAlias(period, modifier, provider);
        }
        return this.modify(period, modifier);
    }

    public boolean modify(PeriodWrapper periodWrapper, PeriodModifier modifier, IPeriodAdapter targetPeriodAdapter) {
        String modify = this.modify(periodWrapper.toString(), modifier, targetPeriodAdapter);
        PeriodWrapper periodWrapperTemp = new PeriodWrapper(modify);
        periodWrapper.setYear(periodWrapperTemp.getYear());
        periodWrapper.setPeriod(periodWrapperTemp.getPeriod());
        if ("1900N0001".equals(modify)) {
            periodWrapper.setType(PeriodType.YEAR.type());
            return false;
        }
        return true;
    }

    private String modifyAlias(String period, PeriodModifier modifier, IPeriodProvider aliasPeriodProvider) {
        IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
        if (null != aliasPeriodProvider && !periodEntity.getCode().equals(aliasPeriodProvider.getPeriodEntity().getCode())) {
            if (PeriodUtils.isDefault(periodEntity.getType()) && PeriodUtils.isDefault(aliasPeriodProvider.getPeriodEntity().getType())) {
                return this.defaultPeriodModify(period, modifier, aliasPeriodProvider);
            }
            return this.customPeriodModify(period, modifier, aliasPeriodProvider);
        }
        return this.modify(period, modifier);
    }

    private String defaultPeriodModify(String period, PeriodModifier modifier, IPeriodProvider aliasPeriodProvider) {
        int index;
        IPeriodRow iPeriodRow;
        List<IPeriodRow> targetData;
        block17: {
            Calendar calendar;
            IPeriodEntity targetEntity;
            block16: {
                IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
                IPeriodRow periodData = this.periodDataRowCache.getPeriodData(this.periodId, period);
                if (null == periodData) {
                    return "1900N0001";
                }
                targetEntity = aliasPeriodProvider.getPeriodEntity();
                if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType()) && !targetEntity.getPeriodType().equals((Object)PeriodType.MONTH)) {
                    int i = Integer.parseInt(periodData.getCode().substring(5));
                    if (i == 0) {
                        periodData = this.periodDataRowCache.getPeriodData(this.periodId, periodData.getYear() + "Y0001");
                    } else if (i > 12) {
                        periodData = this.periodDataRowCache.getPeriodData(this.periodId, periodData.getYear() + "Y0012");
                    }
                }
                Calendar currPeriod = PeriodUtils.getCalendar(periodData.getStartDate());
                calendar = null;
                calendar = modifier.getYearFlag() == 1 ? PeriodUtils.getCalendar(modifier.getYearModifier(), currPeriod.get(2), currPeriod.get(5)) : PeriodUtils.getCalendar(periodData.getYear() + modifier.getYearModifier(), currPeriod.get(2), currPeriod.get(5));
                targetData = aliasPeriodProvider.getPeriodItems();
                int year = calendar.get(1);
                List targetDataByYear = targetData.stream().filter(e -> year == e.getYear()).collect(Collectors.toList());
                if (targetDataByYear.isEmpty()) {
                    return "1900N0001";
                }
                if (modifier.getPeriodFlag() == 1) {
                    if (modifier.getPeriodModifier() < 0 || modifier.getPeriodModifier() > targetDataByYear.size()) {
                        return "1900N0001";
                    }
                    if (PeriodUtils.isPeriod13(targetEntity.getCode(), targetEntity.getPeriodType())) {
                        boolean has0 = false;
                        for (IPeriodRow iPeriodRow2 : targetDataByYear) {
                            int i = Integer.parseInt(iPeriodRow2.getCode().substring(5));
                            if (i != 0) continue;
                            has0 = true;
                        }
                        if (has0) {
                            return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier())).getCode();
                        }
                        if (modifier.getPeriodModifier() == 0) {
                            return "1900N0001";
                        }
                        return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier() - 1)).getCode();
                    }
                    if (modifier.getPeriodModifier() == 0) {
                        return "1900N0001";
                    }
                    return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier() - 1)).getCode();
                }
                iPeriodRow = null;
                index = -1;
                if (!PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType()) || !targetEntity.getPeriodType().equals((Object)PeriodType.MONTH)) break block16;
                String codeStr = year + PeriodUtils.getPeriodType(PeriodType.MONTH) + periodData.getCode().substring(5);
                List collect = targetData.stream().filter(e -> e.getCode().equals(codeStr)).collect(Collectors.toList());
                if (collect.isEmpty()) break block17;
                iPeriodRow = (IPeriodRow)collect.get(0);
                index = this.findPeriodByDataIndex(targetData, iPeriodRow);
                break block17;
            }
            for (int i = 0; i < targetData.size(); ++i) {
                int qi;
                if (PeriodUtils.isPeriod13(targetEntity.getCode(), targetEntity.getPeriodType()) && ((qi = Integer.parseInt(targetData.get(i).getCode().substring(5))) == 0 || qi > 12)) continue;
                Calendar start = PeriodUtils.getCalendar(targetData.get(i).getStartDate());
                Calendar end = PeriodUtils.getCalendar(targetData.get(i).getEndDate());
                if (calendar.getTime().getTime() < start.getTime().getTime() || calendar.getTime().getTime() > end.getTime().getTime()) continue;
                iPeriodRow = targetData.get(i);
                index = i;
                break;
            }
        }
        if (null == iPeriodRow) {
            return "1900N0001";
        }
        if ((index += modifier.getPeriodModifier()) < 0 || index >= targetData.size()) {
            return "1900N0001";
        }
        return targetData.get(index).getCode();
    }

    private String customPeriodModify(String period, PeriodModifier modifier, IPeriodProvider aliasPeriodProvider) {
        int currPeriodIndex;
        IPeriodEntity aliasEntity = aliasPeriodProvider.getPeriodEntity();
        List<IPeriodRow> dataListByKey = this.getDataListByKey(this.periodId);
        IPeriodRow periodData = this.periodDataRowCache.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return "1900N0001";
        }
        int year = periodData.getYear();
        year = modifier.getYearFlag() == 1 ? modifier.getYearModifier() : (year += modifier.getYearModifier());
        int finalYear = year;
        List targetDataList = aliasPeriodProvider.getPeriodItems().stream().filter(e -> e.getStartDate() != null).collect(Collectors.toList());
        List offsetYearAft = targetDataList.stream().filter(e -> finalYear == e.getYear()).collect(Collectors.toList());
        if (offsetYearAft.isEmpty()) {
            return "1900N0001";
        }
        IPeriodRow start = null;
        int offset = 0;
        if (modifier.getYearFlag() == 1) {
            if (modifier.getPeriodFlag() == 1) {
                if (modifier.getPeriodModifier() <= 0 || modifier.getPeriodModifier() > offsetYearAft.size()) {
                    return "1900N0001";
                }
                start = (IPeriodRow)offsetYearAft.get(0);
                offset = modifier.getPeriodModifier() - 1;
            } else {
                currPeriodIndex = this.findPeriodByYearIndex(dataListByKey, periodData);
                if (offsetYearAft.size() <= currPeriodIndex) {
                    if (!aliasEntity.getPeriodType().equals((Object)PeriodType.YEAR)) {
                        return "1900N0001";
                    }
                    currPeriodIndex = 0;
                }
                start = (IPeriodRow)offsetYearAft.get(currPeriodIndex);
                offset = modifier.getPeriodModifier();
            }
        } else if (modifier.getPeriodFlag() == 1) {
            if (modifier.getPeriodModifier() <= 0 || modifier.getPeriodModifier() > offsetYearAft.size()) {
                return "1900N0001";
            }
            start = (IPeriodRow)offsetYearAft.get(0);
            offset = modifier.getPeriodModifier() > 0 ? modifier.getPeriodModifier() - 1 : modifier.getPeriodModifier();
        } else {
            currPeriodIndex = this.findPeriodByYearIndex(dataListByKey, periodData);
            if (offsetYearAft.size() <= currPeriodIndex) {
                return "1900N0001";
            }
            start = (IPeriodRow)offsetYearAft.get(currPeriodIndex);
            offset = modifier.getPeriodModifier();
        }
        if (null == start) {
            return "1900N0001";
        }
        IPeriodRow finalStart = start;
        int periodIndex = IntStream.range(0, targetDataList.size()).filter(e -> ((IPeriodRow)targetDataList.get(e)).getCode().equals(finalStart.getCode())).findFirst().getAsInt();
        if ((periodIndex += offset) < 0 || periodIndex > targetDataList.size() - 1) {
            return "1900N0001";
        }
        return ((IPeriodRow)targetDataList.get(periodIndex)).getCode();
    }

    private String customPeriodModify(String period, PeriodModifier modifier) {
        int currPeriodIndex;
        IPeriodEntity aliasEntity = this.getPeriodEntity();
        List<IPeriodRow> dataListByKey = this.getDataListByKey(this.periodId);
        IPeriodRow periodData = this.periodDataRowCache.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return "1900N0001";
        }
        int year = periodData.getYear();
        year = modifier.getYearFlag() == 1 ? modifier.getYearModifier() : (year += modifier.getYearModifier());
        int finalYear = year;
        List targetDataList = this.getPeriodItems().stream().filter(e -> e.getStartDate() != null).collect(Collectors.toList());
        List offsetYearAft = targetDataList.stream().filter(e -> finalYear == e.getYear()).collect(Collectors.toList());
        if (offsetYearAft.isEmpty()) {
            return "1900N0001";
        }
        IPeriodRow start = null;
        int offset = 0;
        if (modifier.getYearFlag() == 1) {
            if (modifier.getPeriodFlag() == 1) {
                if (modifier.getPeriodModifier() <= 0 || modifier.getPeriodModifier() > offsetYearAft.size()) {
                    return "1900N0001";
                }
                start = (IPeriodRow)offsetYearAft.get(0);
                offset = modifier.getPeriodModifier() - 1;
            } else {
                currPeriodIndex = this.findPeriodByYearIndex(dataListByKey, periodData);
                if (offsetYearAft.size() <= currPeriodIndex) {
                    if (!aliasEntity.getPeriodType().equals((Object)PeriodType.YEAR)) {
                        return "1900N0001";
                    }
                    currPeriodIndex = 0;
                }
                start = (IPeriodRow)offsetYearAft.get(currPeriodIndex);
                offset = modifier.getPeriodModifier();
            }
        } else if (modifier.getPeriodFlag() == 1) {
            if (modifier.getPeriodModifier() <= 0 || modifier.getPeriodModifier() > offsetYearAft.size()) {
                return "1900N0001";
            }
            start = (IPeriodRow)offsetYearAft.get(0);
            offset = modifier.getPeriodModifier() > 0 ? modifier.getPeriodModifier() - 1 : modifier.getPeriodModifier();
        } else {
            currPeriodIndex = this.findPeriodByYearIndex(dataListByKey, periodData);
            if (offsetYearAft.size() <= currPeriodIndex) {
                return "1900N0001";
            }
            start = (IPeriodRow)offsetYearAft.get(currPeriodIndex);
            offset = modifier.getPeriodModifier();
        }
        if (null == start) {
            return "1900N0001";
        }
        IPeriodRow finalStart = start;
        int periodIndex = IntStream.range(0, targetDataList.size()).filter(e -> ((IPeriodRow)targetDataList.get(e)).getCode().equals(finalStart.getCode())).findFirst().getAsInt();
        if ((periodIndex += offset) < 0 || periodIndex > targetDataList.size() - 1) {
            return "1900N0001";
        }
        return ((IPeriodRow)targetDataList.get(periodIndex)).getCode();
    }

    public String modify(String period, PeriodModifier modifier) {
        int i;
        IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
        if (PeriodType.CUSTOM.equals((Object)periodEntity.getPeriodType())) {
            return this.customPeriodModify(period, modifier);
        }
        if (!PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType()) && !PeriodType.WEEK.equals((Object)periodEntity.getPeriodType())) {
            return super.modify(period, modifier);
        }
        IPeriodRow periodData = this.periodDataRowCache.getPeriodData(this.periodId, period);
        if (null == periodData) {
            return "1900N0001";
        }
        Calendar currPeriod = PeriodUtils.getCalendar(periodData.getStartDate());
        Calendar calendar = null;
        calendar = modifier.getYearFlag() == 1 ? PeriodUtils.getCalendar(modifier.getYearModifier(), currPeriod.get(2), currPeriod.get(5)) : PeriodUtils.getCalendar(periodData.getYear() + modifier.getYearModifier(), currPeriod.get(2), currPeriod.get(5));
        List<IPeriodRow> targetData = this.getPeriodItems();
        int year = calendar.get(1);
        List targetDataByYear = targetData.stream().filter(e -> year == e.getYear()).collect(Collectors.toList());
        if (targetDataByYear.isEmpty()) {
            return "1900N0001";
        }
        if (modifier.getPeriodFlag() == 1) {
            if (modifier.getPeriodModifier() < 0 || modifier.getPeriodModifier() > targetDataByYear.size()) {
                return "1900N0001";
            }
            if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
                boolean has0 = false;
                for (IPeriodRow iPeriodRow : targetDataByYear) {
                    int i2 = Integer.parseInt(iPeriodRow.getCode().substring(5));
                    if (i2 != 0) continue;
                    has0 = true;
                }
                if (has0) {
                    return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier())).getCode();
                }
                if (modifier.getPeriodModifier() == 0) {
                    return "1900N0001";
                }
                return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier() - 1)).getCode();
            }
            if (modifier.getPeriodModifier() == 0) {
                return "1900N0001";
            }
            return ((IPeriodRow)targetDataByYear.get(modifier.getPeriodModifier() - 1)).getCode();
        }
        boolean period0or13 = false;
        if (PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType()) && ((i = Integer.parseInt(periodData.getCode().substring(5))) == 0 || i > 12)) {
            period0or13 = true;
        }
        IPeriodRow iPeriodRow = null;
        int index = -1;
        if (period0or13) {
            String tarCode = calendar.get(1) + PeriodUtils.getPeriodType(PeriodType.MONTH) + periodData.getCode().substring(5);
            iPeriodRow = this.periodDataRowCache.getPeriodData(this.periodId, tarCode);
            index = this.findPeriodByDataIndex(targetData, iPeriodRow);
            index += modifier.getPeriodModifier();
        } else {
            for (int i3 = 0; i3 < targetData.size(); ++i3) {
                int qi;
                if (periodEntity.getPeriodType().equals((Object)PeriodType.MONTH) && ((qi = Integer.parseInt(targetData.get(i3).getCode().substring(5))) == 0 || qi > 12)) continue;
                Calendar start = PeriodUtils.getCalendar(targetData.get(i3).getStartDate());
                Calendar end = PeriodUtils.getCalendar(targetData.get(i3).getEndDate());
                if (calendar.getTime().getTime() < start.getTime().getTime() || calendar.getTime().getTime() > end.getTime().getTime()) continue;
                iPeriodRow = targetData.get(i3);
                index = i3;
                break;
            }
            if (null == iPeriodRow) {
                return "1900N0001";
            }
            index += modifier.getPeriodModifier();
        }
        if (index < 0 || index >= targetData.size()) {
            return "1900N0001";
        }
        return targetData.get(index).getCode();
    }

    private int findPeriodByYearIndex(List<IPeriodRow> dataListByKey, IPeriodRow periodData) {
        List currPeriodYear = dataListByKey.stream().filter(e -> periodData.getYear() == e.getYear()).collect(Collectors.toList());
        int currPeriodIndex = -1;
        OptionalInt first = IntStream.range(0, currPeriodYear.size()).filter(e -> ((IPeriodRow)currPeriodYear.get(e)).getCode().equals(periodData.getCode())).findFirst();
        if (first.isPresent()) {
            currPeriodIndex = first.getAsInt();
        }
        return currPeriodIndex;
    }

    private int findPeriodByDataIndex(List<IPeriodRow> dataListByKey, IPeriodRow periodData) {
        int currPeriodIndex = -1;
        OptionalInt first = IntStream.range(0, dataListByKey.size()).filter(e -> ((IPeriodRow)dataListByKey.get(e)).getCode().equals(periodData.getCode())).findFirst();
        if (first.isPresent()) {
            currPeriodIndex = first.getAsInt();
        }
        return currPeriodIndex;
    }

    public int getPeriodType(String period) {
        int periodDataType = PeriodType.DEFAULT.type();
        periodDataType = this.periodEntityCache.getPeriodByKey(this.periodId).getType().type();
        return periodDataType;
    }

    public boolean modify(PeriodWrapper periodWrapper, PeriodModifier modifier) {
        String modify = this.modify(periodWrapper.toString(), modifier);
        PeriodWrapper periodWrapperTemp = new PeriodWrapper(modify);
        periodWrapper.setYear(periodWrapperTemp.getYear());
        periodWrapper.setPeriod(periodWrapperTemp.getPeriod());
        if ("1900N0001".equals(modify)) {
            periodWrapper.setType(PeriodType.YEAR.type());
            return false;
        }
        return true;
    }

    public boolean modifyYear(PeriodWrapper periodWrapper, int modifyCount) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setYearModifier(modifyCount);
        return this.modify(periodWrapper, periodModifier);
    }

    public boolean modifyPeriod(PeriodWrapper periodWrapper, int modifyCount) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(modifyCount);
        String modify = this.modify(periodWrapper.toString(), periodModifier);
        PeriodWrapper periodWrapperTemp = new PeriodWrapper(modify);
        periodWrapper.setYear(periodWrapperTemp.getYear());
        periodWrapper.setPeriod(periodWrapperTemp.getPeriod());
        if ("1900N0001".equals(modify)) {
            periodWrapper.setType(PeriodType.YEAR.type());
            return false;
        }
        return true;
    }

    public String getPeriodTitle(PeriodWrapper periodWrapper) {
        IPeriodRow periodData = this.getPeriodData(this.periodId, periodWrapper);
        if (null == periodData) {
            return "";
        }
        return periodData.getTitle();
    }

    public Date[] getPeriodDateRegion(PeriodWrapper periodWrapper) throws ParseException {
        if (this.isDefaultPeriodAdapter(this.getPeriodEntity())) {
            return super.getPeriodDateRegion(periodWrapper);
        }
        IPeriodRow periodData = this.getPeriodData(this.periodId, periodWrapper);
        if (null == periodData) {
            return new Date[]{null, null};
        }
        return new Date[]{periodData.getStartDate(), periodData.getEndDate()};
    }

    public String[] getPeriodDateStrRegion(PeriodWrapper periodWrapper) throws ParseException {
        if (this.isDefaultPeriodAdapter(this.getPeriodEntity())) {
            return super.getPeriodDateStrRegion(periodWrapper);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        IPeriodRow periodData = this.getPeriodData(this.periodId, periodWrapper);
        String[] res = new String[]{null, null};
        if (null != periodData) {
            if (null != periodData.getStartDate()) {
                res[0] = sdf.format(periodData.getStartDate());
            }
            if (null != periodData.getEndDate()) {
                res[1] = sdf.format(periodData.getEndDate());
            }
        }
        return res;
    }

    public boolean priorYear(PeriodWrapper periodWrapper) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setYearModifier(-1);
        return this.modify(periodWrapper, periodModifier);
    }

    public boolean nextYear(PeriodWrapper periodWrapper) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setYearModifier(1);
        return this.modify(periodWrapper, periodModifier);
    }

    public boolean priorPeriod(PeriodWrapper periodWrapper) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(-1);
        String modify = this.modify(periodWrapper.toString(), periodModifier);
        PeriodWrapper periodWrapperTemp = new PeriodWrapper(modify);
        periodWrapper.setYear(periodWrapperTemp.getYear());
        periodWrapper.setPeriod(periodWrapperTemp.getPeriod());
        if ("1900N0001".equals(modify)) {
            periodWrapper.setType(PeriodType.YEAR.type());
            return false;
        }
        return true;
    }

    public boolean nextPeriod(PeriodWrapper periodWrapper) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(1);
        String modify = this.modify(periodWrapper.toString(), periodModifier);
        PeriodWrapper periodWrapperTemp = new PeriodWrapper(modify);
        periodWrapper.setYear(periodWrapperTemp.getYear());
        periodWrapper.setPeriod(periodWrapperTemp.getPeriod());
        if ("1900N0001".equals(modify)) {
            periodWrapper.setType(PeriodType.YEAR.type());
            return false;
        }
        return true;
    }

    @Override
    public List<IPeriodRow> getPeriodItems() {
        return this.getDataListByKey(this.periodId);
    }

    @Override
    public List<PeriodDataSelectObject> getPeriodDataByModifyTitle() {
        IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
        PeriodPropertyGroup periodPropertyGroup = periodEntity.getPeriodPropertyGroup();
        List<IPeriodRow> periodItems = this.getPeriodItems();
        if (periodEntity.getPeriodType().equals((Object)PeriodType.CUSTOM)) {
            return this.periodRowToPeriodDataSelectObject(periodPropertyGroup, periodItems);
        }
        ArrayList<IPeriodRow> titleModifyList = new ArrayList<IPeriodRow>();
        for (IPeriodRow periodItem : periodItems) {
            if (periodItem.getTitle().equals(PeriodUtils.getDateStrFromPeriod(periodItem.getCode()))) continue;
            titleModifyList.add(periodItem);
        }
        return titleModifyList.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
    }

    private List<PeriodDataSelectObject> periodRowToPeriodDataSelectObject(PeriodPropertyGroup periodPropertyGroup, List<IPeriodRow> periodItems) {
        List<Object> periodDataSelectObjects = new ArrayList();
        if (periodPropertyGroup == null) {
            periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
        } else {
            switch (periodPropertyGroup) {
                case PERIOD_GROUP_BY_YEAR: {
                    periodDataSelectObjects = periodItems.stream().map(a -> {
                        PeriodDataSelectObject periodDataSelectObject = PeriodDataSelectObject.defineToObject(a);
                        periodDataSelectObject.setGroup(a.getYear() + PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR.getGroupName());
                        return periodDataSelectObject;
                    }).collect(Collectors.toList());
                    break;
                }
                default: {
                    periodDataSelectObjects = periodItems.stream().map(PeriodDataSelectObject::defineToObject).collect(Collectors.toList());
                }
            }
        }
        return periodDataSelectObjects;
    }

    @Override
    public IPeriodRow getCurPeriod() {
        return this.getCurPeriod(this.periodEntityCache.getPeriodByKey(this.periodId));
    }

    @Override
    public Date[] getPeriodDateRegion() {
        List<IPeriodRow> periodItems = this.getPeriodItems();
        IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
        Date startdate = null;
        Date enddate = null;
        if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code()) {
            if (periodItems.size() != 0) {
                startdate = periodItems.get(0).getStartDate();
                enddate = periodItems.get(periodItems.size() - 1).getEndDate();
            }
        } else {
            startdate = PeriodUtils.minDefine(periodItems).getStartDate();
            enddate = PeriodUtils.maxDefine(periodItems).getStartDate();
        }
        return new Date[]{startdate, enddate};
    }

    @Override
    public String[] getPeriodCodeRegion() {
        List<IPeriodRow> periodItems = this.getPeriodItems();
        String startdate = null;
        String enddate = null;
        IPeriodEntity periodEntity = this.periodEntityCache.getPeriodByKey(this.periodId);
        if (PeriodType.CUSTOM.code() == periodEntity.getPeriodType().code() || PeriodUtils.isPeriod13(periodEntity.getCode(), periodEntity.getPeriodType())) {
            if (periodItems.size() != 0) {
                startdate = periodItems.get(0).getCode();
                enddate = periodItems.get(periodItems.size() - 1).getCode();
            }
        } else {
            startdate = PeriodUtils.minDefine(periodItems).getCode();
            enddate = PeriodUtils.maxDefine(periodItems).getCode();
        }
        return new String[]{startdate, enddate};
    }

    @Override
    public int comparePeriod(String left, String right) {
        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(this.periodId);
        if (PeriodType.CUSTOM.code() == periodByKey.getPeriodType().code()) {
            List<IPeriodRow> dataList = this.getDataListByKey(this.periodId);
            int leftIndex = 0;
            int rightIndex = 0;
            for (int i = 0; i < dataList.size(); ++i) {
                if (dataList.get(i).getCode().equals(left)) {
                    leftIndex = i;
                }
                if (!dataList.get(i).getCode().equals(right)) continue;
                rightIndex = i;
            }
            if (leftIndex == rightIndex) {
                return 0;
            }
            if (leftIndex > rightIndex) {
                return 1;
            }
            return -1;
        }
        return PeriodUtils.comparePeriod(left, right);
    }

    @Override
    public String nextPeriod(String periodCode) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(1);
        return this.modify(periodCode, periodModifier);
    }

    @Override
    public String priorPeriod(String periodCode) {
        PeriodModifier periodModifier = new PeriodModifier();
        periodModifier.setPeriodModifier(-1);
        return this.modify(periodCode, periodModifier);
    }

    private boolean isDefaultPeriodAdapter(IPeriodEntity entity) {
        return !PeriodType.CUSTOM.equals((Object)entity.getPeriodType()) && !PeriodType.WEEK.equals((Object)entity.getPeriodType()) && !PeriodUtils.isPeriod13(entity.getCode(), entity.getPeriodType());
    }
}

