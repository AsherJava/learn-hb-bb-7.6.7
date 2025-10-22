/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.period.service.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodException;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.modal.impl.PeriodDefineImpl;
import com.jiuqi.nr.period.service.PeriodAdapterService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PeriodAdapterServiceImpl
implements PeriodAdapterService {
    private static final Logger logger = LoggerFactory.getLogger(PeriodAdapterServiceImpl.class);
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    private PeriodDataDao periodDataDao;

    @Override
    public List<IPeriodEntity> getPeriodList() {
        ArrayList<IPeriodEntity> iList = new ArrayList<IPeriodEntity>();
        try {
            List<PeriodDefineImpl> periodList = this.periodDao.getPeriodList();
            periodList = periodList.stream().sorted(Comparator.comparingInt(a -> this.solution(a.getType().type()))).collect(Collectors.toList());
            PeriodUtils.interfaceTransform(periodList, iList);
        }
        catch (JQException e) {
            logger.error(e.getMessage(), e);
        }
        return iList;
    }

    private int solution(int type) {
        return type == 6 ? 7 : (type == 7 ? 6 : type);
    }

    @Override
    public IPeriodEntity getPeriodByKey(String key) {
        return this.periodDao.queryPeriodByKey(key);
    }

    @Override
    public IPeriodEntity getPeriodByCode(String code) {
        return this.periodDao.queryPeriodByCode(code);
    }

    @Override
    public List<IPeriodRow> getDataListByCode(String code) {
        ArrayList<IPeriodRow> iList = new ArrayList<IPeriodRow>();
        try {
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(code);
            PeriodUtils.interfaceTransform(dataListByCode, iList);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return iList;
    }

    @Override
    public List<IPeriodRow> getDataListByKey(String key) {
        ArrayList<IPeriodRow> iList = new ArrayList<IPeriodRow>();
        try {
            IPeriodEntity queryPeriod = this.periodDao.queryPeriodByKey(key);
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(queryPeriod.getCode());
            this.setSimpleTitle(queryPeriod.getPeriodType(), dataListByCode);
            PeriodUtils.interfaceTransform(dataListByCode, iList);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return iList;
    }

    private void setSimpleTitle(PeriodType type, List<PeriodDataDefineImpl> iPeriodRows) {
        for (PeriodDataDefineImpl iPeriodRow : iPeriodRows) {
            if (!StringUtils.isEmpty(iPeriodRow.getSimpleTitle())) continue;
            iPeriodRow.setSimpleTitle(PeriodUtils.getDefaultShowTitle(type, iPeriodRow));
        }
    }

    @Override
    public Date[] getPeriodDateRegion(String period) {
        Date start = null;
        Date end = null;
        try {
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(period);
            start = PeriodUtils.minDate(dataListByCode);
            end = PeriodUtils.maxDate(dataListByCode);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new Date[]{start, end};
    }

    @Override
    public String[] getPeriodDateStrRegion(String period) {
        String start = null;
        String end = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(period);
            Date startDate = PeriodUtils.minDate(dataListByCode);
            Date endDate = PeriodUtils.maxDate(dataListByCode);
            if (startDate != null) {
                start = sdf.format(startDate);
            }
            if (endDate != null) {
                end = sdf.format(endDate);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new String[]{start, end};
    }

    @Override
    public int getPeriodType(String period) {
        IPeriodEntity queryPeriodByCode = this.periodDao.queryPeriodByCode(period);
        return queryPeriodByCode.getType().type();
    }

    @Override
    public IPeriodRow getPeriodData(String periodId, String period) {
        IPeriodEntity periodDefine = this.periodDao.queryPeriodByKey(periodId);
        PeriodDataDefineImpl dataByDataCode = null;
        try {
            dataByDataCode = this.periodDataDao.getDataByDataCode(periodDefine.getCode(), period);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dataByDataCode;
    }

    @Override
    public int getPeriodDataType(String period) throws JQException {
        if (StringUtils.isEmpty(period) || period.length() != 9) {
            throw new JQException((ErrorEnum)PeriodException.PERIOD_EXCEPTION_118);
        }
        return PeriodUtils.periodOfType(period.substring(4, 5)).type();
    }

    @Override
    public IPeriodRow modify(String tableCode, String period, int modify) throws Exception {
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListByCode(tableCode);
        int idx = -1;
        for (int i = 0; i < dataListByCode.size(); ++i) {
            if (!dataListByCode.get(i).getCode().equals(period)) continue;
            idx = i;
            break;
        }
        if (0 == modify) {
            return dataListByCode.get(idx);
        }
        if (modify < 0) {
            if (idx - Math.abs(modify) < 0) {
                return null;
            }
            return dataListByCode.get(idx - Math.abs(modify));
        }
        if (modify > 0) {
            if (idx + modify >= dataListByCode.size()) {
                return null;
            }
            return dataListByCode.get(idx + modify);
        }
        return null;
    }

    @Override
    public IPeriodRow modify(String tableId, String period, PeriodModifier modifier) throws Exception {
        IPeriodEntity periodByKey = this.periodDao.queryPeriodByKey(tableId);
        if (modifier.getYearFlag() == 0) {
            return this.modify(periodByKey.getCode(), period, modifier.getPeriodModifier());
        }
        int modifyCount = modifier.getPeriodModifier();
        switch (periodByKey.getType()) {
            case YEAR: {
                modifyCount += 0 * modifier.getYearModifier();
                break;
            }
            case HALFYEAR: {
                modifyCount += 2 * modifier.getYearModifier();
                break;
            }
            case SEASON: {
                modifyCount += 4 * modifier.getYearModifier();
                break;
            }
            case MONTH: {
                modifyCount += 12 * modifier.getYearModifier();
                break;
            }
            case TENDAY: {
                modifyCount += 36 * modifier.getYearModifier();
                break;
            }
            case WEEK: {
                modifyCount += 52 * modifier.getYearModifier();
                break;
            }
            case DAY: 
            case CUSTOM: {
                modifyCount += 365 * modifier.getYearModifier();
                break;
            }
        }
        return this.modify(periodByKey.getCode(), period, modifyCount);
    }

    @Override
    public IPeriodRow customModify(String tableId, String period, PeriodModifier modifier) throws Exception {
        IPeriodEntity periodByKey = this.periodDao.queryPeriodByKey(tableId);
        if (modifier.getYearFlag() == 0) {
            return this.modify(periodByKey.getCode(), period, modifier.getPeriodModifier());
        }
        int modifyCount = modifier.getPeriodModifier();
        switch (PeriodType.fromType((int)modifier.getPeriodType())) {
            case YEAR: {
                modifyCount += 0 * modifier.getYearModifier();
                break;
            }
            case HALFYEAR: {
                modifyCount += 2 * modifier.getYearModifier();
                break;
            }
            case SEASON: {
                modifyCount += 4 * modifier.getYearModifier();
                break;
            }
            case MONTH: {
                modifyCount += 12 * modifier.getYearModifier();
                break;
            }
            case TENDAY: {
                modifyCount += 36 * modifier.getYearModifier();
                break;
            }
            case WEEK: {
                modifyCount += 52 * modifier.getYearModifier();
                break;
            }
            case DAY: 
            case CUSTOM: {
                modifyCount += 365 * modifier.getYearModifier();
                break;
            }
        }
        return this.modify(periodByKey.getCode(), period, modifyCount);
    }

    @Override
    public IPeriodRow getCurPeriod(String periodId) {
        IPeriodEntity queryPeriod = this.periodDao.queryPeriodByKey(periodId);
        String res = "";
        res = PeriodUtils.getPeriodFromDate(queryPeriod.getType().type(), new Date());
        if (queryPeriod.getType().type() == PeriodType.CUSTOM.type()) {
            res = LocalDate.now().getYear() + PeriodUtils.getPeriodType(PeriodType.CUSTOM) + LocalDate.now().getDayOfYear();
        }
        PeriodDataDefineImpl dataByDataCode = null;
        try {
            dataByDataCode = this.periodDataDao.getDataByDataCode(queryPeriod.getCode(), res);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dataByDataCode;
    }

    public TableModelDefine getPeriodEntity(String entityId) {
        IPeriodEntity queryPeriodByKey = this.periodDao.queryPeriodByKey(entityId);
        return this.periodDataDao.getTableModelDefine(queryPeriodByKey.getCode());
    }

    public TableModelDefine getPeriodTableByTableCode(String entityId) {
        return this.periodDataDao.getTableModelDefine(entityId);
    }

    public TableModelDefine getPeriodTableByTableKey(String key) {
        return this.periodDataDao.getTableModelDefineByKey(key);
    }

    @Override
    public List<IPeriodEntity> getPeriodByType(PeriodType periodType) {
        return this.periodDao.queryPeriodByPeriodType(periodType.type());
    }
}

