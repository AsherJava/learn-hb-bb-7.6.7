/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 */
package com.jiuqi.nr.workflow2.form.reject.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectFormRecordDao;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectOperateRecordDao;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectQueryService;
import com.jiuqi.nr.workflow2.form.reject.service.IFormRejectRunTimeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class FormRejectRunTimeService
implements IFormRejectRunTimeService {
    @Autowired
    protected IRejectFormRecordDao rejectFormRecordDao;
    @Autowired
    protected IRejectOperateRecordDao rejectOperateRecordDao;
    @Autowired
    protected IFormRejectQueryService formRejectQueryService;

    @Override
    public void clearFormRejectRecord(String taskKey, String period, List<DimensionCombination> combinations) {
        for (DimensionCombination combination : combinations) {
            this.rejectFormRecordDao.deleteRows(this.formRejectQueryService.getFRStatusTableModelDefine(taskKey, period), combination);
        }
    }

    @Override
    public void updateFormRejectRecords(String taskKey, String period, List<IFormObject> formObjects, String status) {
    }

    public void updateFormRejectRecords(String taskKey, String period, DimensionCombination combination, List<String> formIds, String status) {
        this.rejectFormRecordDao.updateRows(this.formRejectQueryService.getFRStatusTableModelDefine(taskKey, period), combination, formIds, status);
    }

    @Override
    public void insertFormRejectRecords(String taskKey, String period, List<IFormObject> formObjects, String status) {
        this.rejectFormRecordDao.insertRows(this.formRejectQueryService.getFRStatusTableModelDefine(taskKey, period), formObjects, status);
    }

    @Override
    public void insertOperateFormRecords(String taskKey, String period, List<RejectOperateRecordEntity> entities) {
        this.rejectOperateRecordDao.insertRows(this.formRejectQueryService.getFROperateTableModelDefine(taskKey, period), entities);
    }
}

