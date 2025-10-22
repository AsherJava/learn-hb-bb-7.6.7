/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.period.internal.datatype;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.dao.PeriodDao;
import com.jiuqi.nr.period.dao.PeriodDataDao;
import com.jiuqi.nr.period.modal.impl.PeriodDataDefineImpl;
import com.jiuqi.nr.period.util.TitleState;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PeriodAddDataTypeProcessor {
    Logger logger = LoggerFactory.getLogger(PeriodAddDataTypeProcessor.class);
    @Autowired
    private PeriodDataDao periodDataDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DesignDataModelService designDataModelService;
    @Autowired
    private PeriodDao periodDao;
    @Autowired
    DataModelDeployService dataModelDeployService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    public static final String QUERY_PERIOD = "select * from nr_period";
    public static final String UPDATE_SQL = "UPDATE NR_PERIOD SET P_DATATYPE=%s WHERE P_CODE='%s' ";

    public void createData() {
        try {
            List periodList = this.jdbcTemplate.queryForList(QUERY_PERIOD);
            for (Map periodDefine : periodList) {
                String code = (String)periodDefine.get("P_CODE");
                PeriodType type = PeriodType.fromType((int)Integer.parseInt(periodDefine.get("P_TYPE").toString()));
                int dataType = TitleState.NONE.getValue();
                if (PeriodType.CUSTOM.equals((Object)type)) {
                    dataType |= TitleState.TITLE.getValue();
                } else if (PeriodUtils.isPeriod13(code, type)) {
                    dataType = dataType | TitleState.TITLE.getValue() | TitleState.SIMPLE_TITLE.getValue();
                } else {
                    List<PeriodDataDefineImpl> list = this.periodDataDao.getDataListSqls(code);
                    for (PeriodDataDefineImpl define : list) {
                        if (define.getTitle().equals(PeriodUtils.getDateStrFromPeriod(define.getCode()))) continue;
                        dataType |= TitleState.TITLE.getValue();
                        break;
                    }
                    for (PeriodDataDefineImpl define : list) {
                        if (!StringUtils.isNotEmpty(define.getSimpleTitle())) continue;
                        dataType |= TitleState.SIMPLE_TITLE.getValue();
                        break;
                    }
                }
                String format = String.format(UPDATE_SQL, dataType, code);
                this.jdbcTemplate.execute(format);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

