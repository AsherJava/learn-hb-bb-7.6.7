/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IDimensionFilter
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.efdc.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.efdc.bean.EntityDataObject;
import com.jiuqi.nr.efdc.internal.pojo.AssistEntitys;
import com.jiuqi.nr.efdc.internal.pojo.BasePOJOImpl;
import com.jiuqi.nr.efdc.internal.pojo.EntityQueryVO;
import com.jiuqi.nr.efdc.internal.utils.EFDCConstants;
import com.jiuqi.nr.efdc.internal.utils.NrResult;
import com.jiuqi.nr.efdc.pojo.EntityData;
import com.jiuqi.nr.efdc.service.DataCenterService;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.unit.uselector.cacheset.USelectorResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor={NpRollbackException.class})
public class DataCenterServiceImpl
implements DataCenterService {
    private static final Logger log = LoggerFactory.getLogger(DataCenterServiceImpl.class);
    @Autowired
    private IEntityViewRunTimeController runtimeViewCtrl;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeCtrl;
    @Autowired
    private IFormulaRunTimeController formCtrl;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private USelectorResultSet uSelectorResultSet;

    @Override
    public NrResult getAllTask() {
        NrResult result = new NrResult();
        ArrayList<BasePOJOImpl> taskList = new ArrayList<BasePOJOImpl>();
        List allTaskDefines = new ArrayList();
        try {
            allTaskDefines = this.runTimeAuthViewController.getAllTaskDefines();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (TaskDefine define : allTaskDefines) {
            if (define == null) continue;
            BasePOJOImpl impl = new BasePOJOImpl();
            impl.setKey(define.getKey());
            impl.setTitle(define.getTitle());
            impl.setCode(define.getTaskCode());
            taskList.add(impl);
        }
        result.setData(taskList);
        result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        return result;
    }

    @Override
    public List<FormulaSchemeDefine> getAllFormulaSchemeByFromScheme(String fromScheme) {
        return this.formCtrl.getAllCWFormulaSchemeDefinesByFormScheme(fromScheme);
    }

    @Override
    public List<FormulaSchemeDefine> getAllRPTFormulaSchemeDefinesByFormScheme(String fromScheme) {
        return this.formCtrl.getAllRPTFormulaSchemeDefinesByFormScheme(fromScheme);
    }

    @Override
    public List<EntityDataObject> getEntityData(String viewKey) {
        ArrayList<EntityDataObject> all = new ArrayList<EntityDataObject>();
        try {
            EntityViewDefine entityViewDefine = this.runtimeViewCtrl.buildEntityView(viewKey);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.setEntityView(entityViewDefine);
            IEntityTable iEntityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.runtimeCtrl));
            List allRows = iEntityTable.getAllRows();
            for (IEntityRow row : allRows) {
                EntityDataObject entData = EntityData.buildData(row);
                entData.setViewKey(viewKey);
                all.add(entData);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return all;
    }

    @Override
    public List<AssistEntitys> getAssistDimData(EntityQueryVO vo) {
        ArrayList<AssistEntitys> assistEntitys = new ArrayList<AssistEntitys>();
        List<String> assistViews = vo.getAssistViews();
        ReferRelation referRelation = new ReferRelation();
        try {
            EntityViewDefine mainEntityView = this.runtimeViewCtrl.buildEntityView(vo.getViewKey());
            referRelation.setViewDefine(mainEntityView);
            ArrayList<String> range = new ArrayList<String>();
            range.add(vo.getDimKey());
            referRelation.setRange(range);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        ExecutorContext context = new ExecutorContext(this.runtimeCtrl);
        for (String assistViewKey : assistViews) {
            ArrayList<EntityDataObject> all = new ArrayList<EntityDataObject>();
            IEntityTable rs = null;
            try {
                IDimensionFilter dimensionFilter = this.runTimeViewController.getDimensionFilter(vo.getFormSchemeKey(), assistViewKey);
                EntityViewDefine entityViewDefine = this.runtimeViewCtrl.buildEntityView(dimensionFilter);
                IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
                entityQuery.sorted(true);
                entityQuery.setEntityView(entityViewDefine);
                entityQuery.addReferRelation(referRelation);
                rs = entityQuery.executeReader((IContext)context);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            List allRows = rs.getAllRows();
            for (IEntityRow row : allRows) {
                EntityDataObject entData = EntityData.buildData(row);
                entData.setViewKey(assistViewKey);
                all.add(entData);
            }
            AssistEntitys assistEntity = new AssistEntitys(assistViewKey, all);
            assistEntitys.add(assistEntity);
        }
        return assistEntitys;
    }

    @Override
    public NrResult getEntity(String viewKey) {
        NrResult result = new NrResult();
        try {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(viewKey);
            BasePOJOImpl impl = new BasePOJOImpl();
            impl.setKey(entityDefine.getId());
            impl.setTitle(entityDefine.getTitle());
            impl.setCode(entityDefine.getCode());
            result.setData(impl);
            result.setStatus(EFDCConstants.RETURN_200_SUCCEES);
        }
        catch (Exception e) {
            result.setStatus(EFDCConstants.RETURN_FAIL);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public PeriodWrapper transformToPeriod(String type) {
        String periodTypeCode = type;
        if ("B".equals(periodTypeCode)) {
            periodTypeCode = "N";
        }
        char periodCode = periodTypeCode.charAt(0);
        int periodType = PeriodConsts.codeToType((int)periodCode);
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)periodType);
        return currentPeriod;
    }

    @Override
    public List<EntityDataObject> getChildrenData(String key, String viewKey) {
        ArrayList<EntityDataObject> list = new ArrayList<EntityDataObject>();
        IEntityTable iEntityTable = null;
        try {
            EntityViewDefine entityViewDefine = this.runtimeViewCtrl.buildEntityView(viewKey);
            IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
            entityQuery.sorted(true);
            entityQuery.setEntityView(entityViewDefine);
            iEntityTable = entityQuery.executeReader((IContext)new ExecutorContext(this.runtimeCtrl));
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        List allRows = iEntityTable.getChildRows(key);
        for (IEntityRow row : allRows) {
            EntityDataObject entData = EntityData.buildData(row);
            entData.setViewKey(viewKey);
            int children = this.hashChildren(iEntityTable, row.getEntityKeyData());
            entData.setChildCount(children);
            list.add(entData);
        }
        return list;
    }

    @Override
    public List<String> getSelectedEntity(String key) {
        return this.uSelectorResultSet.getFilterSet(key);
    }

    private int hashChildren(IEntityTable iEntityTable, String code) {
        List childRows = new ArrayList();
        childRows = iEntityTable.getChildRows(code);
        return childRows.size();
    }
}

