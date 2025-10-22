/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.dao.IGenerateRecordDao;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateStatusModifier
implements IStatusModifier {
    private final IGenerateRecordDao recordDao;

    public GenerateStatusModifier(IGenerateRecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Override
    public void ready(String key) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.GenerateStatus.READY.getStatus());
        this.modify(key, param);
    }

    @Override
    public void start(String key) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.GenerateStatus.DOING.getStatus());
        param.put("AR_START_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void pause(String key) {
    }

    @Override
    public void resume(String key) {
    }

    @Override
    public void end(String key, boolean success) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", success ? Constant.GenerateStatus.SUCCESS.getStatus() : Constant.GenerateStatus.FAIL.getStatus());
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void cancel(String key) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.GenerateStatus.CANCEL.getStatus());
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void error(String key, String content) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.GenerateStatus.FAIL.getStatus());
        param.put("AR_CONTENT", content);
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void modify(String key, Map<String, Object> value) {
        this.recordDao.updateField(key, value);
    }
}

