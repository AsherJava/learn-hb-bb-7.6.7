/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.system.check.model.response.EntityObj;
import com.jiuqi.nr.system.check.model.response.PeriodObj;
import com.jiuqi.nr.system.check.model.response.SchemeObj;
import com.jiuqi.nr.system.check.service.SCCustomPeriodService;
import com.jiuqi.nr.system.check.service.SCFormSchemeService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCFormSchemeServiceImpl
implements SCFormSchemeService {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private SCCustomPeriodService scCustomPeriodService;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public List<SchemeObj> getAllSchemesInTask(String taskKey) throws Exception {
        ArrayList<SchemeObj> schemeObjs = new ArrayList<SchemeObj>();
        List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskKey);
        if (formSchemeDefines != null) {
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            IDataAssist iDataAssist = this.iDataAccessProvider.newDataAssist(executorContext);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                SchemeObj schemeObj = new SchemeObj(formSchemeDefine);
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                TableModelDefine periodEntityTableModel = periodAdapter.getPeriodEntityTableModel(formSchemeDefine.getDateTime());
                PeriodObj periodObj = new PeriodObj();
                periodObj.setKey(periodEntityTableModel.getID());
                periodObj.setTitle(periodEntityTableModel.getTitle());
                PeriodType periodType = formSchemeDefine.getPeriodType();
                if (periodType == PeriodType.CUSTOM) {
                    periodObj.setPeriodType(periodType.type());
                    periodObj.setDefaultPeriod(false);
                    List<IPeriodRow> customPeriodDataList = this.scCustomPeriodService.getCustomPeriodDataList(formSchemeDefine.getDateTime());
                    periodObj.setCustomPeriodDataList(customPeriodDataList);
                } else {
                    periodObj.setPeriodType(periodType.type());
                    periodObj.setDefaultPeriod(true);
                }
                schemeObj.setPeriodObj(periodObj);
                ArrayList<EntityObj> entityObjs = new ArrayList<EntityObj>();
                TableModelDefine dwTableModel = this.iEntityMetaService.getTableModel(formSchemeDefine.getDw());
                EntityObj entityObj = new EntityObj();
                entityObj.setKey(dwTableModel.getID());
                entityObj.setTitle(dwTableModel.getTitle());
                entityObj.setViewKey(formSchemeDefine.getDw());
                String dimensionName = this.iEntityMetaService.getDimensionName(formSchemeDefine.getDw());
                entityObj.setDimensionName(dimensionName);
                entityObjs.add(entityObj);
                if (StringUtils.isNotEmpty((String)formSchemeDefine.getDims())) {
                    String[] dims;
                    for (String dim : dims = formSchemeDefine.getDims().split(";")) {
                        TableModelDefine dimTableModel = this.iEntityMetaService.getTableModel(dim);
                        EntityObj dimentityObj = new EntityObj();
                        dimentityObj.setKey(dimTableModel.getID());
                        dimentityObj.setTitle(dimTableModel.getTitle());
                        dimentityObj.setViewKey(dim);
                        String dimdimensionName = this.iEntityMetaService.getDimensionName(dim);
                        dimentityObj.setDimensionName(dimdimensionName);
                        entityObjs.add(dimentityObj);
                    }
                }
                schemeObj.setEntityObjs(entityObjs);
                schemeObjs.add(schemeObj);
            }
        }
        return schemeObjs;
    }
}

