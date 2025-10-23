/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.select.common.Mode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataContext {
    private String dataScheme;
    private IPeriodEntity periodEntity;
    private List<String> selectRegions;
    private List<String> selectedPeriod;
    private String adjust;
    private List<IPeriodRow> iPeriodRowList = new ArrayList<IPeriodRow>();
    private List<AdjustPeriod> adjustPeriodList;
    private Mode mode;
    private String rangeStart;
    private String rangeEnd;
    private PeriodEngineService periodEngineService;
    private IAdjustPeriodService iAdjustPeriodService;
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    public DataContext(PeriodEngineService periodEngineService, IAdjustPeriodService iAdjustPeriodService, IRuntimeDataSchemeService iRuntimeDataSchemeService) {
        this.periodEngineService = periodEngineService;
        this.iAdjustPeriodService = iAdjustPeriodService;
        this.iRuntimeDataSchemeService = iRuntimeDataSchemeService;
    }

    public String getTitle() {
        ArrayList<String> titles = new ArrayList<String>();
        if (this.selectedPeriod.size() == 1 && StringUtils.isNotEmpty((String)this.adjust) && !"0".equals(this.adjust)) {
            String title = "";
            for (IPeriodRow iPeriodRow : this.iPeriodRowList) {
                if (!iPeriodRow.getCode().equals(this.selectedPeriod.get(0))) continue;
                title = iPeriodRow.getTitle();
                break;
            }
            for (AdjustPeriod adjustPeriod : this.adjustPeriodList) {
                if (!adjustPeriod.getCode().equals(this.adjust)) continue;
                title = title + " " + adjustPeriod.getTitle();
            }
            titles.add(title);
        } else {
            block2: for (String code : this.selectedPeriod) {
                for (IPeriodRow iPeriodRow : this.iPeriodRowList) {
                    if (!iPeriodRow.getCode().equals(code)) continue;
                    titles.add(iPeriodRow.getTitle());
                    continue block2;
                }
            }
        }
        return titles.stream().collect(Collectors.joining(";"));
    }

    public String getRangeStart() {
        return this.rangeStart;
    }

    public void setRangeStart(String rangeStart) {
        this.rangeStart = rangeStart;
    }

    public String getRangeEnd() {
        return this.rangeEnd;
    }

    public void setRangeEnd(String rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<IPeriodRow> getiPeriodRowList() {
        if (this.iPeriodRowList.isEmpty()) {
            this.iPeriodRowList = this.periodEngineService.getPeriodAdapter().getPeriodProvider(this.periodEntity.getCode()).getPeriodItems();
        }
        return this.iPeriodRowList;
    }

    public List<IPeriodRow> getDataByYear(int year) {
        return this.getiPeriodRowList().stream().filter(e -> year == e.getYear()).collect(Collectors.toList());
    }

    public List<IPeriodRow> getDataByYearAndMonth(int year, int month) {
        return this.getiPeriodRowList().stream().filter(e -> year == e.getYear() && month == e.getMonth()).collect(Collectors.toList());
    }

    public List<AdjustPeriod> getAdjustPeriodList() {
        if (null == this.adjustPeriodList) {
            this.adjustPeriodList = this.iRuntimeDataSchemeService.enableAdjustPeriod(this.dataScheme) != false ? this.iAdjustPeriodService.queryAdjustPeriods(this.dataScheme) : new ArrayList<AdjustPeriod>();
        }
        return this.adjustPeriodList;
    }

    public boolean isYearGroup() {
        return PeriodType.CUSTOM.equals((Object)this.periodEntity.getPeriodType()) && this.periodEntity.getPeriodPropertyGroup() != null;
    }

    public List<String> panelDepth() {
        PeriodType periodType = this.periodEntity.getPeriodType();
        boolean yearGroup = null != this.periodEntity.getPeriodPropertyGroup();
        List<String> data = new ArrayList<String>();
        switch (periodType) {
            case YEAR: {
                String[] year = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR)};
                data = Arrays.asList(year);
                break;
            }
            case HALFYEAR: {
                String[] halfyear = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.HALFYEAR)};
                data = Arrays.asList(halfyear);
                break;
            }
            case SEASON: {
                String[] season = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.SEASON)};
                data = Arrays.asList(season);
                break;
            }
            case MONTH: {
                String[] month = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.MONTH)};
                data = Arrays.asList(month);
                break;
            }
            case TENDAY: {
                String[] tenday = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.MONTH), PeriodUtils.getPeriodType((PeriodType)PeriodType.TENDAY)};
                data = Arrays.asList(tenday);
                break;
            }
            case WEEK: {
                String[] week = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.MONTH), PeriodUtils.getPeriodType((PeriodType)PeriodType.WEEK)};
                data = Arrays.asList(week);
                break;
            }
            case DAY: {
                String[] day = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.MONTH), PeriodUtils.getPeriodType((PeriodType)PeriodType.DAY)};
                data = Arrays.asList(day);
                break;
            }
            case CUSTOM: {
                if (yearGroup) {
                    String[] custom = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.YEAR), PeriodUtils.getPeriodType((PeriodType)PeriodType.CUSTOM)};
                    data = Arrays.asList(custom);
                    break;
                }
                String[] custom = new String[]{PeriodUtils.getPeriodType((PeriodType)PeriodType.CUSTOM)};
                data = Arrays.asList(custom);
            }
        }
        return data;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public IPeriodEntity getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    public List<String> getSelectRegions() {
        return this.selectRegions;
    }

    public void setSelectRegions(List<String> selectRegions) {
        this.selectRegions = selectRegions;
    }

    public List<String> getSelectedPeriod() {
        return this.selectedPeriod;
    }

    public void setSelectedPeriod(List<String> selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}

