/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nr.form.selector.context;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.form.selector.context.FormTreeContextImpl;
import com.jiuqi.nr.form.selector.context.IFormQueryContext;
import com.jiuqi.nr.form.selector.context.IFormQueryHelper;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class FormQueryHelperImpl
implements IFormQueryHelper {
    private FormSchemeDefine currentFormScheme;
    private IFormQueryContext formQueryContext;
    private IRunTimeViewController runTimeView;
    private FormTreeContextImpl contextImpl;

    public FormQueryHelperImpl(IFormQueryContext formQueryContext, IRunTimeViewController runTimeView) {
        this.formQueryContext = formQueryContext;
        this.runTimeView = runTimeView;
    }

    public FormQueryHelperImpl(IFormQueryContext formQueryContext, IRunTimeViewController runTimeView, FormTreeContextImpl contextImpl) {
        this(formQueryContext, runTimeView);
        this.contextImpl = contextImpl;
    }

    @Override
    public String getTaskKey() {
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine();
        if (formSchemeDefine != null) {
            return formSchemeDefine.getTaskKey();
        }
        return this.formQueryContext.getTaskKey();
    }

    @Override
    public String getFormSchemeKey() {
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine();
        if (formSchemeDefine != null) {
            return formSchemeDefine.getKey();
        }
        return null;
    }

    @Override
    public String getPeriod() {
        return this.formQueryContext.getPeriod();
    }

    @Override
    public Map<String, DimensionValue> getDimValueSet() {
        return this.formQueryContext.getDimValueSet();
    }

    @Override
    public JSONObject getCustomVariable() {
        return this.formQueryContext.getCustomVariable();
    }

    @Override
    public FormSchemeDefine getFormSchemeDefine() {
        if (null == this.currentFormScheme) {
            this.currentFormScheme = this.runTimeView.getFormScheme(this.formQueryContext.getFormSchemeKey());
            if (null == this.currentFormScheme) {
                this.currentFormScheme = this.getFormScheme(this.formQueryContext.getTaskKey(), this.getPeriod());
            }
        }
        return this.currentFormScheme;
    }

    @Override
    public List<FormGroupDefine> queryRootGroupsByFormScheme(String formSchemeKey) {
        return this.runTimeView.queryRootGroupsByFormScheme(formSchemeKey);
    }

    @Override
    public List<FormDefine> queryAllFormDefinesByFormScheme(String formSchemeKey) {
        return this.runTimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
    }

    @Override
    public List<FormDefine> queryAllFormDefinesByTask(String taskKey) {
        return this.runTimeView.queryAllFormDefinesByTask(taskKey);
    }

    @Override
    public List<FormDefine> getAllFormsInGroupWithoutOrder(String formGroupKey) {
        try {
            return this.runTimeView.getAllFormsInGroupWithoutOrder(formGroupKey);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JtableContext translate2JTableContext(IFormQueryContext formQueryContext) {
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(formQueryContext.getTaskKey());
        jtableContext.setFormSchemeKey(formQueryContext.getFormSchemeKey());
        Map<String, DimensionValue> dimValueSet = formQueryContext.getDimValueSet();
        if (dimValueSet == null) {
            dimValueSet = new HashMap<String, DimensionValue>();
        }
        jtableContext.setDimensionSet(dimValueSet);
        return jtableContext;
    }

    @Override
    public boolean isBatchDimValueSet() {
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine();
        IEntityMetaService service = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        IEntityDefine entityDefine = service.queryEntity(formSchemeDefine.getDw());
        String dimensionName = entityDefine.getDimensionName();
        Map<String, DimensionValue> dimValueSet = this.getDimValueSet();
        return dimValueSet == null || !dimValueSet.containsKey(dimensionName) || dimValueSet.get(dimensionName) == null || !StringUtils.isNotEmpty((String)dimValueSet.get(dimensionName).getValue()) || dimValueSet.get(dimensionName).getValue().split(";").length != 1;
    }

    @Override
    public List<String> getCheckForms() {
        return this.contextImpl.getCheckForms();
    }

    private FormSchemeDefine getFormScheme(String task, String period) {
        FormSchemeDefine formSchemeDefine = null;
        if (StringUtils.isNotEmpty((String)task) && StringUtils.isNotEmpty((String)period)) {
            try {
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeView.querySchemePeriodLinkByPeriodAndTask(period, task);
                if (null != schemePeriodLinkDefine) {
                    formSchemeDefine = this.runTimeView.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return formSchemeDefine;
    }
}

