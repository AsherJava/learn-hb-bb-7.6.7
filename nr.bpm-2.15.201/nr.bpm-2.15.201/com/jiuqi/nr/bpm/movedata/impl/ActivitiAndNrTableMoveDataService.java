/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.movedata.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.movedata.ActivitiAndNrTable;
import com.jiuqi.nr.bpm.movedata.ActivitiTable;
import com.jiuqi.nr.bpm.movedata.IActivitiAndNrTableMoveDataService;
import com.jiuqi.nr.bpm.movedata.IActivitiTableMoveDataForSyncService;
import com.jiuqi.nr.bpm.movedata.IActivitiTableMoveDataService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivitiAndNrTableMoveDataService
implements IActivitiAndNrTableMoveDataService {
    @Autowired
    IActivitiTableMoveDataService activitiTableMoveDataService;
    @Autowired
    IActivitiTableMoveDataForSyncService activitiTableMoveDataForSyncService;
    @Autowired
    NrParameterUtils nrParameterUtils;

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={RuntimeException.class, Exception.class})
    public void importTable(BusinessKey businessKey, ActivitiAndNrTable activitiAndNrTable) {
        this.activitiTableMoveDataForSyncService.importActivitiTable(activitiAndNrTable.getActivitiTable(), businessKey.toString());
        this.activitiTableMoveDataService.importUploadActionsNew(businessKey, activitiAndNrTable.getUploadRecordNew());
        this.activitiTableMoveDataService.importUploadStateNew(businessKey, activitiAndNrTable.getUploadStateNew());
    }

    @Override
    public ActivitiAndNrTable exportTable(BusinessKey businessKey, DimensionValueSet dimensionValueSet) {
        ActivitiAndNrTable activitiAndNrTable = new ActivitiAndNrTable();
        FormSchemeDefine formSchemeDefine = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        ActivitiTable activitiTable = this.activitiTableMoveDataForSyncService.exportActivitiTable(businessKey.toString());
        List<UploadRecordNew> uploadRecordNews = this.activitiTableMoveDataService.exportUploadActionsNew(dimensionValueSet, businessKey.getFormKey(), formSchemeDefine);
        UploadStateNew uploadStateNew = this.activitiTableMoveDataService.exportUploadStateNew(dimensionValueSet, businessKey.getFormKey(), formSchemeDefine);
        activitiAndNrTable.setActivitiTable(activitiTable);
        activitiAndNrTable.setUploadRecordNew(uploadRecordNews);
        if (uploadStateNew.getEntities() != null) {
            activitiAndNrTable.setUploadStateNew(uploadStateNew);
        }
        return activitiAndNrTable;
    }
}

