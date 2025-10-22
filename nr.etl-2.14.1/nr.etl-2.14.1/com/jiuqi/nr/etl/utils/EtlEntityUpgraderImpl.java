/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.etl.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EtlEntityUpgraderImpl
implements IEntityUpgrader {
    @Resource
    RunTimeAuthViewController runTimeAuthViewController;
    @Resource
    private IEntityViewRunTimeController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    private static final Logger log = LoggerFactory.getLogger(EtlEntityUpgraderImpl.class);

    public IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u83b7\u53d6\u4e3b\u4f53\u96c6\u5408\u5931\u8d25\uff01", e.getCause());
        }
        return rsSet;
    }

    public List<IEntityRow> getEntityData(String formSchemeKey, DimensionValueSet dim) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            if (formScheme == null) {
                throw new Exception("\u672a\u83b7\u53d6\u5230\u62a5\u8868\uff1a\u8bf7\u68c0\u67e5\u662f\u5426\u8bbe\u7f6e\u4efb\u52a1\u6267\u884c\u7528\u6237");
            }
            EntityViewDefine dwEntityViewDefine = this.entityViewRunTimeController.buildEntityView(formScheme.getDw());
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setEntityView(dwEntityViewDefine);
            iEntityQuery.setMasterKeys(dim);
            IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)new ExecutorContext(this.runtimeController));
            allRows = iEntityTable.getAllRows();
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return allRows;
    }

    public List<IEntityRow> getEntityData(String formSchemeKey, String period) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            EntityViewDefine datatimeEntityViewDefine = this.entityViewRunTimeController.buildEntityView(formScheme.getDw());
            DimensionValueSet valueSet = new DimensionValueSet();
            String[] periodList = period.split(";");
            if (periodList.length > 1) {
                for (String curPeriod : periodList) {
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue("DATATIME", (Object)curPeriod);
                    IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                    iEntityQuery.setEntityView(datatimeEntityViewDefine);
                    iEntityQuery.setMasterKeys(valueSet);
                    IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)new ExecutorContext(this.runtimeController));
                    for (IEntityRow entityRow : iEntityTable.getAllRows()) {
                        allRows.add(entityRow);
                    }
                }
            } else {
                if (period != null) {
                    valueSet.setValue("DATATIME", (Object)period);
                }
                IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
                iEntityQuery.setEntityView(datatimeEntityViewDefine);
                iEntityQuery.setMasterKeys(valueSet);
                IEntityTable iEntityTable = iEntityQuery.executeReader((IContext)new ExecutorContext(this.runtimeController));
                allRows = iEntityTable.getAllRows();
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return allRows;
    }
}

