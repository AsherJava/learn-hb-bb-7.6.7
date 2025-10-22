/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.dao.IGenerateTaskDao;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import java.util.Map;

public class GenerateTaskModifier
implements IStatusModifier {
    private final IGenerateTaskDao generateTaskDao;

    public GenerateTaskModifier(IGenerateTaskDao generateTaskDao) {
        this.generateTaskDao = generateTaskDao;
    }

    @Override
    public void ready(String key) {
        this.generateTaskDao.updateStatus(key, 0);
    }

    @Override
    public void start(String key) {
        this.generateTaskDao.updateStatus(key, 1);
    }

    @Override
    public void pause(String key) {
        this.generateTaskDao.updateStatus(key, 2);
    }

    @Override
    public void resume(String key) {
        this.generateTaskDao.updateStatus(key, 1);
    }

    @Override
    public void end(String key, boolean success) {
        this.generateTaskDao.updateStatus(key, 3);
    }

    @Override
    public void cancel(String key) {
        this.generateTaskDao.updateStatus(key, 3);
    }

    @Override
    public void error(String key, String content) {
        this.generateTaskDao.updateStatus(key, 3);
    }

    @Override
    public void modify(String key, Map<String, Object> value) {
    }
}

