/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import com.jiuqi.nr.attachment.transfer.common.Constant;
import com.jiuqi.nr.attachment.transfer.dao.IImportRecordDao;
import com.jiuqi.nr.attachment.transfer.domain.ImportRecordDO;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImportStatusModifier
implements IStatusModifier {
    private final IImportRecordDao recordDao;

    public ImportStatusModifier(IImportRecordDao recordDao) {
        this.recordDao = recordDao;
    }

    @Override
    public void ready(String key) {
        ImportRecordDO recordDO = this.recordDao.get(key);
        int executeNum = recordDO.getExecuteNum();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.ImportStatus.READY.getStatus());
        param.put("AR_EXE_NUM", executeNum + 1);
        this.modify(key, param);
    }

    @Override
    public void start(String key) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.ImportStatus.DOING.getStatus());
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
        param.put("AR_STATUS", success ? Constant.ImportStatus.SUCCESS.getStatus() : Constant.ImportStatus.FAIL.getStatus());
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void cancel(String key) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.ImportStatus.CANCEL.getStatus());
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void error(String key, String content) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("AR_STATUS", Constant.ImportStatus.FAIL.getStatus());
        param.put("AR_CONTENT", content);
        param.put("AR_END_TIME", new Date());
        this.modify(key, param);
    }

    @Override
    public void modify(String key, Map<String, Object> value) {
        this.recordDao.updateField(key, value);
    }
}

