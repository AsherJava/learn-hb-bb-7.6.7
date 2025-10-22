/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.offsetitem.upgrade;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeOffsetItemOrientFormula
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
        EntNativeSqlDefaultDao formulaDao = EntNativeSqlDefaultDao.newInstance((String)"GC_CONSFORMULA", ConsolidatedFormulaEO.class);
        List formulaEOList = formulaDao.selectEntity("SELECT * FROM GC_CONSFORMULA", new Object[0]);
        if (CollectionUtils.isEmpty((Collection)formulaEOList)) {
            return;
        }
        this.logger.info("\u5408\u5e76\u4f53\u7cfb\u516c\u5f0f\u4e2d\u7684 GC_OFFSETVCHRITEM[ORIENT] \u5347\u7ea7\u4e3a OffsetItemOrient()\u51fd\u6570.\u5f00\u59cb\u5347\u7ea7\u3002");
        ArrayList updateList = new ArrayList();
        formulaEOList.forEach(item -> {
            String oldFormula = item.getFormula();
            if (StringUtils.isEmpty((String)oldFormula)) {
                return;
            }
            if (!oldFormula.toUpperCase().contains("GC_OFFSETVCHRITEM[ORIENT]")) {
                return;
            }
            item.setFormula(oldFormula.replaceAll("(?i)GC_OFFSETVCHRITEM\\[ORIENT\\]", "OffsetItemOrient()"));
            this.logger.info(String.format("\u539f\u516c\u5f0f[%1$s]\u5347\u7ea7\u4e3a[%2$s]", oldFormula, item.getFormula()));
            updateList.add(item);
        });
        if (!CollectionUtils.isEmpty(updateList)) {
            formulaDao.updateBatch(updateList);
        }
        this.logger.info("\u5408\u5e76\u4f53\u7cfb\u516c\u5f0f\u4e2d\u7684 GC_OFFSETVCHRITEM[ORIENT] \u5347\u7ea7\u4e3a OffsetItemOrient()\u51fd\u6570.\u5347\u7ea7\u5b8c\u6210\u3002");
    }
}

