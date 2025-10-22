/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.domain.Page
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.io.record.service.impl;

import com.jiuqi.np.user.domain.Page;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.record.bean.FormStatisticLog;
import com.jiuqi.nr.io.record.dao.FormStatisticDao;
import com.jiuqi.nr.io.record.service.FormStatisticService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormStatisticServiceImpl
implements FormStatisticService {
    @Autowired
    private FormStatisticDao formStatisticDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public Page<FormStatisticLog> queryStatisticLogs(String recKey, String factoryId, int page, int size) {
        Page<FormStatisticLog> result = this.formStatisticDao.queryStatisticLogs(recKey, factoryId, page, size);
        List records = result.getRecords();
        for (FormStatisticLog record : records) {
            FormDefine formDefine = this.runTimeViewController.queryFormById(record.getFormKey());
            record.setFormCode(formDefine.getFormCode());
            record.setFormTitle(formDefine.getTitle());
        }
        return result;
    }

    @Override
    public void saveStatisticLogs(List<FormStatisticLog> logs) {
        this.formStatisticDao.batchInsert(logs);
    }
}

