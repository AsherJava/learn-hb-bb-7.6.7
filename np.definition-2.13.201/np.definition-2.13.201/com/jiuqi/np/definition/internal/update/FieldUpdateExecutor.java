/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.np.definition.internal.update;

import com.jiuqi.np.definition.internal.update.FieldUpdateProcessor;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldUpdateExecutor
implements CustomClassExecutor {
    public void execute(DataSource dataSource) {
        Logger logger = LoggerFactory.getLogger(FieldUpdateExecutor.class);
        logger.info("\u663e\u793a\u683c\u5f0f\uff1a\u5f00\u59cb\u5f02\u6b65\u5347\u7ea7\u8bbe\u8ba1\u671f\u5386\u53f2\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
        FieldUpdateProcessor bean = SpringUtil.getBean(FieldUpdateProcessor.class);
        bean.updateField();
        logger.info("\u663e\u793a\u683c\u5f0f\uff1a\u5f00\u59cb\u5f02\u6b65\u5347\u7ea7\u8fd0\u884c\u671f\u5386\u53f2\u6570\u636e\uff0c\u8bf7\u52ff\u5173\u95ed\u670d\u52a1");
        bean.updateRunField();
    }
}

