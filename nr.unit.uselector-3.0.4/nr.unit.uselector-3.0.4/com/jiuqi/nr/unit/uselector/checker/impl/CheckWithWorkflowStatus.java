/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper
 *  com.jiuiqi.nr.unit.treebase.entity.filter.WorkFlowEntityRowFilter
 *  com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy
 *  com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery
 *  com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState
 *  com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper
 *  com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContextWrapper;
import com.jiuiqi.nr.unit.treebase.entity.filter.WorkFlowEntityRowFilter;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.UnitTreeEntityDataQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkFlowTreeState;
import com.jiuqi.nr.bpm.de.dataflow.tree.util.TreeState;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nHelper;
import com.jiuqi.nr.unit.treecommon.i18n.uselector.UnitSelectorI18nKeys;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CheckWithWorkflowStatus
implements IRowChecker {
    public static final String KEYWORD = "#check-with-workflow-status";
    @Resource
    private TreeState flowWorkState;
    @Resource
    private UnitSelectorI18nHelper i18nHelper;
    @Resource
    private IUnitTreeContextWrapper contextWrapper;
    @Resource
    private UnitTreeEntityDataQuery entityDataQuery;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return this.i18nHelper.getMessage(UnitSelectorI18nKeys.CHECK_WITH_WORKFLOW_STATUS.i18nKey, "\u4e0a\u62a5\u72b6\u6001");
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.QUICK_SELECTION, CheckerGroup.FILTER_SCHEME};
    }

    @Override
    public DisplayType getDisplayType() {
        return DisplayType.CHECKBOX;
    }

    @Override
    public boolean isDisplay(IUnitTreeContext ctx) {
        return ctx.getEntityQueryPloy() == IEntityQueryPloy.MAIN_DIM_QUERY && this.contextWrapper.isOpenWorkFlow(ctx.getFormScheme());
    }

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public boolean isChecked() {
        return true;
    }

    @Override
    public IRowCheckExecutor getExecutor(final IUnitTreeContext ctx) {
        return new IRowCheckExecutor(){

            @Override
            public IFilterCheckValues getValues() {
                IFilterCheckValuesImpl values = new IFilterCheckValuesImpl();
                List dimNames = CheckWithWorkflowStatus.this.contextWrapper.getReportEntityDimensionName(ctx.getFormScheme());
                HashMap<String, String> runtimePara = new HashMap<String, String>();
                dimNames.forEach(dimName -> runtimePara.put((String)dimName, ""));
                values.setRuntimePara(runtimePara);
                List<Map<String, String>> checkValue = values.getValues();
                if (null != checkValue) {
                    List actions = CheckWithWorkflowStatus.this.flowWorkState.getWorkFlowActions(ctx.getFormScheme().getKey());
                    for (WorkFlowTreeState state : actions) {
                        HashMap<String, String> v = new HashMap<String, String>();
                        v.put("value", state.getCode());
                        v.put("text", state.getTitle());
                        values.getValues().add(v);
                    }
                }
                return values;
            }

            @Override
            public List<String> executeCheck(IFilterCheckValues values, IUSelectorEntityRowProvider entityRowsProvider) {
                List<Map<String, String>> valueMaps;
                if (null != values && CheckWithWorkflowStatus.this.canExecuteCheck(ctx) && null != (valueMaps = values.getValues())) {
                    List codesOfStatus = valueMaps.stream().map(map -> (String)map.get("value")).collect(Collectors.toList());
                    WorkFlowEntityRowFilter filter = new WorkFlowEntityRowFilter(ctx, (IUnitTreeEntityDataQuery)CheckWithWorkflowStatus.this.entityDataQuery, codesOfStatus);
                    List matchRows = filter.getMatchResultSet(codesOfStatus);
                    if (null != matchRows && !matchRows.isEmpty()) {
                        return matchRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                    }
                }
                return new ArrayList<String>();
            }
        };
    }

    private boolean canExecuteCheck(IUnitTreeContext ctx) {
        if (!this.contextWrapper.isOpenADJUST(ctx.getTaskDefine())) {
            return true;
        }
        Map dimValueSet = ctx.getDimValueSet();
        return ctx.getPeriodEntity() != null && StringUtils.isNotEmpty((String)ctx.getPeriod()) && dimValueSet != null && !dimValueSet.isEmpty();
    }
}

