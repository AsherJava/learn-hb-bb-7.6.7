/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.authz.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.authz.service.IEntityService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityServiceImpl
implements IEntityService {
    private static final Logger logger = LoggerFactory.getLogger(EntityServiceImpl.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Resource
    private IDataAccessProvider dataAccessProvider;
    @Resource
    private IEntityMetaService entityMetaService;

    @Override
    public List<IEntityRow> getDirectChildrenData(String taskKey, String formSchemeKey, String entityDataKey, String period) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            EntityViewDefine entityView = this.getEntityViewDefine(taskKey, formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", (Object)period);
            IEntityTable entityQuerySet = this.entityQuerySet(entityView, childrenDims, formScheme);
            childrenRows = entityQuerySet.getChildRows(entityDataKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    @Override
    public List<IEntityRow> getAllChildrenData(String taskKey, String formSchemeKey, String entityDataKey, String period) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            EntityViewDefine entityView = this.getEntityViewDefine(taskKey, formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", (Object)period);
            IEntityTable entityQuerySet = this.entityQuerySet(entityView, childrenDims, formScheme);
            childrenRows = entityQuerySet.getAllChildRows(entityDataKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    @Override
    public List<String> getDirectChildrens(String taskKey, String formSchemeKey, String entityDataKey, String period) {
        List<String> childrenRows = new ArrayList<String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            EntityViewDefine entityView = this.getEntityViewDefine(taskKey, formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", (Object)period);
            IEntityTable entityQuerySet = this.entityQuerySet(entityView, childrenDims, formScheme);
            List childRows = entityQuerySet.getChildRows(entityDataKey);
            if (childRows != null && childRows.size() > 0) {
                childrenRows = childRows.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return childrenRows;
    }

    @Override
    public List<String> getAllChildrens(String taskKey, String formSchemeKey, String entityDataKey, String period) {
        List<String> childrenRows = new ArrayList<String>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            EntityViewDefine entityView = this.getEntityViewDefine(taskKey, formSchemeKey);
            DimensionValueSet childrenDims = new DimensionValueSet();
            childrenDims.setValue("DATATIME", (Object)period);
            IEntityTable entityQuerySet = this.entityQuerySet(entityView, childrenDims, formScheme);
            List allChildRows = entityQuerySet.getAllChildRows(entityDataKey);
            if (allChildRows != null && allChildRows.size() > 0) {
                childrenRows = allChildRows.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return childrenRows;
    }

    @Override
    public List<IEntityRow> searchEntityRow(String taskKey, String formSchemeKey, String keyword, String period) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            EntityViewDefine entityView = this.getEntityViewDefine(taskKey, formSchemeKey);
            DimensionValueSet dims = null;
            if (period != null) {
                dims = new DimensionValueSet();
                dims.setValue("DATATIME", (Object)period);
            }
            IEntityTable entityQuerySet = this.entityQuerySet(entityView, dims, formScheme, keyword);
            return entityQuerySet.getAllRows();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<IEntityRow>();
        }
    }

    private String translate2Formula(String entityId, String keywords) {
        IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(entityId);
        String tableName = iEntityDefine.getCode();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute nameField = entityModel.getNameField();
        return String.format("%s OR %s", this.matchFunction(keywords, tableName, nameField.getCode()), this.matchFunction(keywords, tableName, codeField.getCode()));
    }

    private String matchFunction(String keyword, String tableName, String fieldCode) {
        return String.format("POS('%s', %s[%s]) > 0", keyword, tableName, fieldCode);
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, FormSchemeDefine formScheme, String keyWord) {
        IEntityQuery query = this.buildQuery(valueSet, formScheme);
        if (StringUtils.isNotEmpty((String)keyWord)) {
            String formula = this.translate2Formula(view.getEntityId(), keyWord);
            query.setExpression(formula);
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = this.buildExcutorContext(valueSet, formScheme);
        IEntityTable rsSet = null;
        try {
            rsSet = query.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u83b7\u53d6\u4e3b\u4f53\u96c6\u5408\u5931\u8d25\uff01", e.getCause());
        }
        return rsSet;
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, FormSchemeDefine formScheme) {
        return this.entityQuerySet(view, valueSet, formScheme, null);
    }

    private com.jiuqi.nr.entity.engine.executors.ExecutorContext buildExcutorContext(DimensionValueSet valueSet, FormSchemeDefine formScheme) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(valueSet);
        if (!StringUtils.isEmpty((String)formScheme.getKey())) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formScheme.getKey()));
        }
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    public IEntityQuery buildQuery(DimensionValueSet valueSet, FormSchemeDefine formScheme) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(valueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityView = this.runTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
        query.setEntityView(entityView);
        query.sorted(true);
        return query;
    }

    public EntityViewDefine getEntityViewDefine(String taskKey, String formSchemeKey) {
        try {
            if (StringUtils.isNotEmpty((String)taskKey)) {
                return this.runTimeViewController.getViewByTaskDefineKey(taskKey);
            }
            return this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getMainDimName(EntityViewDefine viewDefine) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((ExecutorContext)context);
        return dataAssist.getDimensionName(viewDefine);
    }
}

