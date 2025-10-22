/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.period.internal.datatype;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.period.internal.datatype.PeriodAddDataTypeProcessor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodAddDataTypeExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) throws Exception {
        Logger logger = LoggerFactory.getLogger(PeriodAddDataTypeProcessor.class);
        logger.info("\u5f00\u59cb\u8865\u5145\u65f6\u671f\u7b80\u79f0\u5b57\u6bb5");
        PeriodAddDataTypeProcessor bean = (PeriodAddDataTypeProcessor)SpringBeanUtils.getBean(PeriodAddDataTypeProcessor.class);
        bean.createData();
        logger.info("\u65f6\u671f\u7b80\u79f0\u5b57\u6bb5\u8865\u5145\u5b8c\u6210");
    }
}

