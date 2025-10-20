/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.dc.base.common.executor;

import com.jiuqi.dc.base.common.executor.DcDeployTableProcessor;
import com.jiuqi.dc.base.common.utils.DefinitionUtil;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitTableAlter
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(InitTableAlter.class);

    public void execute(DataSource dataSource) throws Exception {
        Map<String, DefinitionTableV> tableMap = DefinitionUtil.getDefinitionTableMap();
        for (DefinitionTableV tableDefine : tableMap.values()) {
            try {
                DcDeployTableProcessor.newInstance(tableDefine).deploy();
            }
            catch (Exception e) {
                logger.error("\u8868\u7ed3\u6784\u521d\u59cb\u5316" + tableDefine.getTableName() + "\u5931\u8d25", e);
            }
        }
    }
}

