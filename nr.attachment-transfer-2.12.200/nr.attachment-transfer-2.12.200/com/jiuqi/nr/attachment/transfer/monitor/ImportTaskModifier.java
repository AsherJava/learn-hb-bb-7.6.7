/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.dao.IImportTaskDao;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import java.util.Map;

public class ImportTaskModifier
implements IStatusModifier {
    private final IImportTaskDao importTaskDao;

    public ImportTaskModifier(IImportTaskDao importTaskDao) {
        this.importTaskDao = importTaskDao;
    }

    @Override
    public void ready(String key) {
        this.importTaskDao.updateStatus(key, 0);
    }

    @Override
    public void start(String key) {
        this.importTaskDao.updateStatus(key, 1);
    }

    @Override
    public void pause(String key) {
        this.importTaskDao.updateStatus(key, 2);
    }

    @Override
    public void resume(String key) {
        this.importTaskDao.updateStatus(key, 1);
    }

    @Override
    public void end(String key, boolean success) {
        this.importTaskDao.updateStatus(key, 3);
    }

    @Override
    public void cancel(String key) {
        this.importTaskDao.updateStatus(key, 3);
    }

    @Override
    public void error(String key, String content) {
        this.importTaskDao.updateStatus(key, 3);
    }

    @Override
    public void modify(String key, Map<String, Object> value) {
    }
}

