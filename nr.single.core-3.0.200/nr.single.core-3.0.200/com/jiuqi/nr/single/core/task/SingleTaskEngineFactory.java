/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.nr.single.core.task;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.single.core.exception.SingleFileException;
import com.jiuqi.nr.single.core.task.ISingleTaskEngine;
import com.jiuqi.nr.single.core.task.SingleTaskDirEngine;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import com.jiuqi.nr.single.core.task.SingleTaskFileEngine;
import com.jiuqi.nr.single.core.task.file.ISingleTaskFileEngine;
import com.jiuqi.nr.single.core.util.SinglePathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleTaskEngineFactory {
    private static final Logger logger = LoggerFactory.getLogger(SingleTaskEngineFactory.class);

    private SingleTaskEngineFactory() {
    }

    public static SingleTaskEngineFactory getInstance() {
        return SingleTaskEngineFactoryInstance.INSTANCE;
    }

    public ISingleTaskEngine getDirEngine(String dir) {
        return new SingleTaskDirEngine(dir);
    }

    public ISingleTaskFileEngine getFileEngine(String fileName) throws SingleTaskException {
        try {
            String opentaskDir = SinglePathUtil.getExportTempFilePath("jioOpenTask");
            String taskDir = SinglePathUtil.createNewPath(opentaskDir, OrderGenerator.newOrder() + ".TSK");
            return new SingleTaskFileEngine(fileName, taskDir);
        }
        catch (SingleFileException e) {
            logger.error(e.getMessage(), e);
            throw new SingleTaskException(e.getMessage(), e);
        }
    }

    public ISingleTaskFileEngine getFileEngine(String fileName, String dir) {
        return new SingleTaskFileEngine(fileName, dir);
    }

    private static class SingleTaskEngineFactoryInstance {
        private static final SingleTaskEngineFactory INSTANCE = new SingleTaskEngineFactory();

        private SingleTaskEngineFactoryInstance() {
        }
    }
}

