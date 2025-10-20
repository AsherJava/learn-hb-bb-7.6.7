/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.blob.util.BeanUtil
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.dao.RuntimeFmlCompileParamProvider;
import com.jiuqi.nr.definition.internal.env.FmlTraceFieldDefine;
import com.jiuqi.nr.definition.internal.env.FmlTrackExecEnvironment;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FmlTrackColumnModelFinder
implements IColumnModelFinder {
    private Map<String, Map<String, ColumnModelDefine>> dataScheme_columnMap = new HashMap<String, Map<String, ColumnModelDefine>>();
    private Map<String, Map<String, ColumnModelDefine>> entity_columnMap = new HashMap<String, Map<String, ColumnModelDefine>>();
    private static final Logger logger = LoggerFactory.getLogger(FmlTrackColumnModelFinder.class);
    private final RuntimeFmlCompileParamProvider paramProvider = (RuntimeFmlCompileParamProvider)BeanUtil.getBean(RuntimeFmlCompileParamProvider.class);
    private final IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtil.getBean(IEntityMetaService.class);
    private final IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);

    public FmlTrackColumnModelFinder(ExecutorContext context) {
        this.initParam(context);
    }

    private void initParam(ExecutorContext context) {
        FmlTrackExecEnvironment env = (FmlTrackExecEnvironment)context.getEnv();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(env.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        this.initFormSchemeColumnParam(taskDefine.getDataScheme());
        this.initDWColumnParam(formScheme);
    }

    private void initDWColumnParam(FormSchemeDefine formScheme) {
        TableModelDefine tableModel = this.entityMetaService.getTableModel(formScheme.getDw());
        List<ColumnModelDefine> columnModelsInTable = this.paramProvider.getColumnModelsInTable(tableModel.getID());
        Map columnModelDefineMap = columnModelsInTable.stream().filter(o -> StringUtils.hasText(o.getCode())).collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity(), (o1, o2) -> o1));
        this.entity_columnMap.put(formScheme.getDw(), columnModelDefineMap);
    }

    private void initFormSchemeColumnParam(String dataSchemeKey) {
        List<ColumnModelDefine> allZBColumnModelInScheme = this.paramProvider.getAllZBColumnModelInScheme(dataSchemeKey);
        Map columnModelDefineMap = allZBColumnModelInScheme.stream().filter(o -> StringUtils.hasText(o.getCode())).collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity(), (o1, o2) -> o1));
        this.dataScheme_columnMap.put(dataSchemeKey, columnModelDefineMap);
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode) throws Exception {
        FmlTrackExecEnvironment env = (FmlTrackExecEnvironment)context.getEnv();
        String formSchemeKey = env.getFormSchemeKey();
        if (!StringUtils.hasText(formSchemeKey)) {
            return null;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(env.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        ColumnModelDefine columnModelDefine = this.findColumnModelDefine(context, taskDefine.getDataScheme(), fieldCode);
        if (columnModelDefine != null) {
            return columnModelDefine;
        }
        return this.findDWColumnModelDefine(formScheme, fieldCode);
    }

    private ColumnModelDefine findDWColumnModelDefine(FormSchemeDefine formScheme, String fieldCode) {
        Map<String, ColumnModelDefine> columnModelDefineMap = this.entity_columnMap.get(formScheme.getDw());
        if (columnModelDefineMap == null) {
            this.initDWColumnParam(formScheme);
            columnModelDefineMap = this.entity_columnMap.get(formScheme.getDw());
        }
        return columnModelDefineMap.get(fieldCode);
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode, int periodType) throws Exception {
        return this.findColumnModelDefine(context, fieldCode);
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String dataScheme, String fieldCode) throws Exception {
        if (!StringUtils.hasText(dataScheme)) {
            return null;
        }
        Map<String, ColumnModelDefine> columnModelDefineMap = this.dataScheme_columnMap.get(dataScheme);
        if (columnModelDefineMap == null) {
            this.initFormSchemeColumnParam(dataScheme);
            columnModelDefineMap = this.dataScheme_columnMap.get(dataScheme);
        }
        return columnModelDefineMap.get(fieldCode);
    }

    public ColumnModelDefine findColumnModelDefine(FieldDefine fieldDefine) throws Exception {
        return null;
    }

    public FieldDefine findFieldDefine(ColumnModelDefine columnModelDefine) throws Exception {
        return new FmlTraceFieldDefine(columnModelDefine);
    }

    public List<ColumnModelDefine> getAllColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        return null;
    }

    public TableModelDefine getTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        return null;
    }
}

