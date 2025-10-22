/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.period.internal.fieldinit;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.EntityInfo;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PeriodFieldInitProcessor {
    Logger logger = LoggerFactory.getLogger(PeriodFieldInitProcessor.class);
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String QUERY_PERIOD = "select * from nr_period";
    public static final String P_CODE = "P_CODE";
    public static final String P_TYPE = "P_TYPE";
    public static final String UPDATE_SQL = "UPDATE NR_PERIOD SET P_MAXFISCALMONTH=%s,P_MINFISCALMONTH=%s,P_MINYEAR=%s,P_MAXYEAR=%s WHERE P_CODE='%s' ";

    public void createData() {
        try {
            List periods = this.jdbcTemplate.queryForList(QUERY_PERIOD);
            for (Map def : periods) {
                String code = (String)def.get(P_CODE);
                int i = Integer.parseInt(def.get(P_TYPE).toString());
                EntityInfo entityInfo = this.createEntityInfo(code, PeriodType.fromType((int)i));
                String format = String.format(UPDATE_SQL, entityInfo.getMaxFiscalMonth(), entityInfo.getMinFiscalMonth(), entityInfo.getMinYear(), entityInfo.getMaxYear(), code);
                this.jdbcTemplate.execute(format);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    private EntityInfo createEntityInfo(String code, PeriodType periodType) throws Exception {
        List<PeriodDataDefineImpl> dataListByCode = this.periodDataDao.getDataListWithOutSimpleTitle(code);
        if (dataListByCode.isEmpty()) {
            return new EntityInfo(-1, -1, -1, -1);
        }
        int maxDataYear = dataListByCode.stream().max(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
        int minDataYear = dataListByCode.stream().min(Comparator.comparing(PeriodDataDefineImpl::getYear)).get().getYear();
        if (!(PeriodType.CUSTOM.equals((Object)periodType) || PeriodType.DAY.equals((Object)periodType) || PeriodType.WEEK.equals((Object)periodType))) {
            List minYearCodes = dataListByCode.stream().filter(e -> e.getYear() == minDataYear).map(q -> q.getCode()).collect(Collectors.toList());
            int minPeriod = 1;
            for (String minYearCode : minYearCodes) {
                int i = Integer.parseInt(minYearCode.substring(5));
                if (i != 0) continue;
                minPeriod = 0;
            }
            int maxPeriod = 1;
            for (String minYearCode : minYearCodes) {
                int i = Integer.parseInt(minYearCode.substring(5));
                maxPeriod = Integer.max(maxPeriod, i);
            }
            return new EntityInfo(maxPeriod, minPeriod, minDataYear, maxDataYear);
        }
        return new EntityInfo(-1, -1, minDataYear, maxDataYear);
    }
}

