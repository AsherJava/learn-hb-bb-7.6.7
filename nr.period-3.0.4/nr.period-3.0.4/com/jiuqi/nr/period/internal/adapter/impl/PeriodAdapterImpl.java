/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.period.internal.adapter.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.cache.PeriodDataRowCache;
import com.jiuqi.nr.period.cache.PeriodEntityCache;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.adapter.impl.IPeriodProviderImpl;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodAdapterImpl
implements IPeriodEntityAdapter {
    private static final long serialVersionUID = 1L;
    @Autowired
    private PeriodEntityCache periodEntityCache;
    @Autowired
    private PeriodDataRowCache periodDataRowCache;

    public String getId() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public int getOrder() {
        return 0;
    }

    @Override
    public boolean isPeriodEntity(String entityId) {
        if (StringUtils.isEmpty(entityId)) {
            return false;
        }
        entityId = PeriodUtils.removeSuffix(entityId);
        List<IPeriodEntity> periodList = this.periodEntityCache.getPeriodList();
        for (IPeriodEntity ip : periodList) {
            if (!ip.getKey().equals(entityId)) continue;
            return true;
        }
        return false;
    }

    @Override
    public TableModelDefine getPeriodEntityTableModel(String entityId) {
        entityId = PeriodUtils.removeSuffix(entityId);
        return this.periodEntityCache.getPeriodEntity(entityId);
    }

    @Override
    public IPeriodProvider getPeriodProvider(String entityId) {
        entityId = PeriodUtils.removeSuffix(entityId);
        return new IPeriodProviderImpl(entityId, this.periodDataRowCache, this.periodEntityCache);
    }

    @Override
    public List<IPeriodEntity> getPeriodEntity() {
        return this.periodEntityCache.getPeriodList();
    }

    @Override
    public IPeriodEntity getPeriodEntity(String entityId) {
        entityId = PeriodUtils.removeSuffix(entityId);
        return this.periodEntityCache.getPeriodByKey(entityId);
    }

    @Override
    public TableModelDefine getPeriodViewByMasterKey(String viewKeys) {
        String[] split;
        if (StringUtils.isEmpty(viewKeys)) {
            return null;
        }
        for (String s : split = viewKeys.split(";")) {
            if (!this.isPeriodEntity(s)) continue;
            return this.periodEntityCache.getPeriodEntity(s);
        }
        return null;
    }

    @Override
    public IPeriodEntity getIPeriodByTableKey(String tableKey) {
        TableModelDefine periodTableByTableKey = this.periodEntityCache.getPeriodTableByTableKey(tableKey);
        if (periodTableByTableKey != null) {
            IPeriodEntity periodByCode = this.periodEntityCache.getPeriodByCode(PeriodUtils.removePerfix(periodTableByTableKey.getCode()));
            return periodByCode;
        }
        return null;
    }

    @Override
    public List<IPeriodEntity> getPeriodEntityByPeriodType(PeriodType periodType) {
        return this.periodEntityCache.getPeriodByType(periodType);
    }

    @Override
    public String getPeriodDimensionName(String entityId) {
        return NrPeriodConst.DATETIME;
    }

    @Override
    public String getPeriodDimensionName() {
        return NrPeriodConst.DATETIME;
    }

    @Override
    public List<ColumnModelDefine> getPeriodEntityColumnModel(String entityId) {
        return this.periodEntityCache.getPeriodEntityColumnModel(entityId);
    }

    @Override
    public List<ColumnModelDefine> getPeriodEntityShowColumnModel(String entityId) {
        return this.periodEntityCache.getPeriodEntityShowColumnModel(entityId);
    }

    @Override
    public Date getStartDateByPeriodCode(String periodCode, String periodEntityKey) {
        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(periodEntityKey);
        Date date = null;
        if (periodByKey != null) {
            IPeriodProvider periodProvider = this.getPeriodProvider(periodByKey.getKey());
            List<IPeriodRow> periodItems = periodProvider.getPeriodItems();
            for (IPeriodRow row : periodItems) {
                if (!row.getCode().equals(periodCode)) continue;
                date = row.getStartDate();
            }
        }
        if (null == date) {
            date = new Date();
        }
        return date;
    }

    @Override
    public List<String> getPeriodCodeByDataRegion(String periodEntityKey, String startDate, String endDate) {
        ArrayList<String> periodDataRegion;
        block13: {
            Date endDateOfPeriod;
            Date startDateOfPeriod;
            List<IPeriodRow> periodItems;
            block16: {
                block15: {
                    String endDateCopy;
                    String startDataCopy;
                    block14: {
                        periodDataRegion = new ArrayList<String>();
                        IPeriodEntity periodByKey = this.periodEntityCache.getPeriodByKey(periodEntityKey);
                        if (null == periodByKey) break block13;
                        IPeriodProvider periodProvider = this.getPeriodProvider(periodEntityKey);
                        periodItems = periodProvider.getPeriodItems();
                        startDataCopy = "";
                        endDateCopy = "";
                        if (StringUtils.isNotEmpty(startDate)) {
                            startDataCopy = startDate;
                        }
                        if (StringUtils.isNotEmpty(endDate)) {
                            endDateCopy = endDate;
                        }
                        if (!PeriodType.CUSTOM.equals((Object)periodByKey.getPeriodType()) && !PeriodUtils.isPeriod13(periodByKey.getCode(), periodByKey.getPeriodType())) break block14;
                        boolean isStart = false;
                        for (IPeriodRow periodItem : periodItems) {
                            if (periodItem.getCode().equals(startDataCopy)) {
                                isStart = true;
                            }
                            if (periodItem.getCode().equals(endDateCopy)) {
                                if (isStart) {
                                    periodDataRegion.add(periodItem.getCode());
                                }
                                isStart = false;
                                continue;
                            }
                            if (!isStart) continue;
                            periodDataRegion.add(periodItem.getCode());
                        }
                        break block13;
                    }
                    startDateOfPeriod = null;
                    endDateOfPeriod = null;
                    try {
                        startDateOfPeriod = PeriodUtils.getStartDateOfPeriod(startDataCopy, true);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    try {
                        endDateOfPeriod = PeriodUtils.getStartDateOfPeriod(endDateCopy, true);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (startDateOfPeriod == null || endDateOfPeriod == null) break block15;
                    for (IPeriodRow periodItem : periodItems) {
                        if (startDateOfPeriod.after(periodItem.getStartDate()) || endDateOfPeriod.before(periodItem.getStartDate())) continue;
                        periodDataRegion.add(periodItem.getCode());
                    }
                    break block13;
                }
                if (startDateOfPeriod == null || endDateOfPeriod != null) break block16;
                for (IPeriodRow periodItem : periodItems) {
                    if (startDateOfPeriod.after(periodItem.getStartDate())) continue;
                    periodDataRegion.add(periodItem.getCode());
                }
                break block13;
            }
            if (startDateOfPeriod != null || endDateOfPeriod == null) break block13;
            for (IPeriodRow periodItem : periodItems) {
                if (endDateOfPeriod.before(periodItem.getStartDate())) continue;
                periodDataRegion.add(periodItem.getCode());
            }
        }
        return periodDataRegion;
    }
}

