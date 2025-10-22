/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.gcreport.inputdata.util;

import com.jiuqi.gcreport.inputdata.inputdata.dao.impl.InputDataDaoImpl;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputLoggerUtils {
    private static Logger logger = LoggerFactory.getLogger(InputDataDaoImpl.class);

    public static void info(Collection<InputDataEO> inputItems) {
    }

    public static void infoByIds(Collection<String> inputItemIds) {
    }

    public static void infoByIds(Collection<String> inputItemIds, String description, String taskId) {
    }

    private static String getUserName() {
        return NpContextHolder.getContext().getUser() == null ? "" : NpContextHolder.getContext().getUser().getName();
    }
}

