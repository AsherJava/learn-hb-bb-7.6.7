/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDefineImpl;
import com.jiuqi.va.biz.impl.value.TypedContainerImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelContext;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.ref.RefDataBuffer;
import com.jiuqi.va.biz.intf.ref.RefDataFilter;
import com.jiuqi.va.biz.intf.value.TypedContainer;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public class ModelImpl
implements Model {
    private ModelContext context;
    private ModelDefine define;
    private LinkedHashMap<String, Plugin> plugins = new LinkedHashMap();
    private TypedContainerImpl<Plugin> pluginContainer;
    private boolean enableRule;
    private RefDataBuffer refDataBuffer = new RefDataBuffer();

    public boolean isEnableRule() {
        return this.enableRule;
    }

    public void setEnableRule(boolean enableRule) {
        this.enableRule = enableRule;
    }

    @Override
    public ModelContext getContext() {
        return this.context;
    }

    void setContext(ModelContext context) {
        this.context = context;
    }

    @Override
    public ModelDefine getDefine() {
        return this.define;
    }

    void setDefine(ModelDefine define) {
        this.define = define;
    }

    void addPlugin(Plugin plugin) {
        if (this.plugins.putIfAbsent(plugin.getType(), plugin) != null) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.modelimpl.pluginrepetition"));
        }
        this.pluginContainer = null;
    }

    @Override
    public TypedContainer<Plugin> getPlugins() {
        if (this.pluginContainer == null) {
            this.pluginContainer = new TypedContainerImpl<Plugin>(this.plugins);
        }
        return this.pluginContainer;
    }

    @Override
    public RefDataBuffer getRefDataBuffer() {
        return this.refDataBuffer;
    }

    @Override
    public Map<String, Object> getRefValue(int refTableType, String refTableName, String id) {
        if (Utils.isEmpty(id)) {
            return null;
        }
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).find(id);
    }

    @Override
    public Map<String, Object> matchValue(int refTableType, String refTableName, String text) {
        if (Utils.isEmpty(text)) {
            return null;
        }
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).match(text);
    }

    @Override
    public Map<String, Object> toViewValue(int refTableType, String refTableName, String showType, Map<String, Object> value) {
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).toViewValue(showType, value);
    }

    @Override
    public Object findRefFieldValue(int refTableType, String refTableName, String id, String fieldName) {
        if (Utils.isEmpty(id)) {
            return null;
        }
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).findRefFieldValue(id, fieldName);
    }

    @Override
    public Map<String, Map<String, Object>> getAll(int refTableType, String refTableName) {
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).list();
    }

    @Override
    public Stream<Map<String, Object>> findRefObjectsByParam(int refTableType, String refTableName, Map<String, Object> filterMap) {
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).filter(filterMap).stream();
    }

    @Override
    public Stream<Map<String, Object>> findRefObjectsByExpression(int refTableType, String refTableName, String expression) {
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).filter(expression).stream();
    }

    @Override
    public Stream<Map<String, Object>> findRefObjectsByFilter(int refTableType, String refTableName, RefDataFilter filterCondition) {
        return this.refDataBuffer.getRefTableMap(refTableType, refTableName, this.getDimValues()).filter(filterCondition).stream();
    }

    @Override
    public List<String> createVerifyCodes(List<String> billCodes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String createVerifyCode(String billCode) {
        if (Utils.isEmpty(billCode)) {
            return "";
        }
        List<String> verifyCodes = this.createVerifyCodes(Arrays.asList(billCode.split(",")));
        return Utils.join(verifyCodes, ",");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void executeAction(Action action, ActionRequest request, ActionResponse response) {
        try {
            UserLoginDTO user;
            String triggerOrigin = this.context.getTriggerOrigin();
            if (StringUtils.hasText(triggerOrigin) && (user = ShiroUtil.getUser()) != null && "super".equalsIgnoreCase(user.getMgrFlag())) {
                throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.system.admin.not.permission"));
            }
            String schemeCode = (String)((ModelContextImpl)this.getContext()).getContextValue("SchemeCode");
            ViewDefineImpl viewDefine = (ViewDefineImpl)this.getPlugins().get("view").getDefine();
            if (!StringUtils.hasText(schemeCode)) {
                schemeCode = viewDefine.getDefaultSchemeCode();
            } else {
                Map<String, String> wizardFirstViews = viewDefine.getWizardFirstViews();
                if (wizardFirstViews.containsKey(schemeCode)) {
                    Object curView = ((ModelContextImpl)this.getContext()).getContextValue("X--curView");
                    schemeCode = curView != null ? (String)curView : wizardFirstViews.get(schemeCode);
                }
            }
            ((ModelContextImpl)this.getContext()).setContextValue("curView", schemeCode);
            boolean result = this.beforeAction(action, request, response);
            response.setSuccess(result);
            if (result) {
                this.invokeAction(action, request, response);
            }
            if (response.isSuccess()) {
                this.afterAction(action, request, response);
            }
        }
        finally {
            this.finalAction(action, request, response);
        }
    }

    protected boolean beforeAction(Action action, ActionRequest request, ActionResponse response) {
        return action.before(this, request, response) && ((ModelDefineImpl)this.define).beforeAction(this, action, request, response);
    }

    protected void invokeAction(Action action, ActionRequest request, ActionResponse response) {
        action.invoke(this, request, response);
        ((ModelDefineImpl)this.define).invokeAction(this, action, request, response);
    }

    protected void afterAction(Action action, ActionRequest request, ActionResponse response) {
        action.after(this, request, response);
        ((ModelDefineImpl)this.define).afterAction(this, action, request, response);
    }

    protected void finalAction(Action action, ActionRequest request, ActionResponse response) {
        action.final$(this, request, response);
        ((ModelDefineImpl)this.define).finalAction(this, action, request, response);
    }
}

