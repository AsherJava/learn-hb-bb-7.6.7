/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.impl.data.DataImpl;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnObject;
import com.jiuqi.va.biz.intf.action.ActionReturnUtil;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.Utils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Action
extends NamedElement {
    @Override
    public String getName();

    public String getTitle();

    public String getGroupId();

    public String getActionPriority();

    default public String getDesc() {
        return null;
    }

    default public int getSortFlag() {
        return Integer.valueOf(this.getGroupId() + this.getActionPriority());
    }

    default public boolean isInner() {
        return false;
    }

    public String getIcon();

    @Deprecated
    default public String[] getModelParams() {
        return null;
    }

    public Class<? extends Model> getDependModel();

    default public List<Class<? extends Model>> getDependModels() {
        return Collections.emptyList();
    }

    default public void execute(Model model, Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    default public Object executeReturn(Model model, Map<String, Object> params) {
        this.execute(model, params);
        return null;
    }

    default public void invoke(Model model, ActionRequest request, ActionResponse response) {
        Object executeReturn = this.executeReturn(model, request.getParams());
        if (executeReturn instanceof ActionReturnObject) {
            response.setSuccess(((ActionReturnObject)executeReturn).isSuccess());
            response.setReturnMessage(executeReturn);
        } else {
            response.setReturnValue(executeReturn);
        }
    }

    default public boolean before(Model model, ActionRequest request, ActionResponse response) {
        RulerImpl rulerImpl = model.getPlugins().find(RulerImpl.class);
        if (rulerImpl == null) {
            return true;
        }
        List<CheckResult> checkMessages = rulerImpl.getRulerExecutor().beforeAction(this.getName(), Stream.of(FormulaType.WARN).collect(Collectors.toList()), true);
        if (checkMessages != null && !checkMessages.isEmpty()) {
            response.getCheckMessages().addAll(checkMessages);
            return false;
        }
        Map<String, Object> params = request.getParams();
        if (params == null || params.get("confirms") == null && params.get("confirmed") == null) {
            checkMessages = rulerImpl.getRulerExecutor().beforeAction(this.getName(), Stream.of(FormulaType.WARN).collect(Collectors.toList()), false);
            if (checkMessages == null || checkMessages.isEmpty()) {
                return true;
            }
            DataImpl dataImpl = model.getPlugins().find(DataImpl.class);
            String masterTableName = dataImpl.getMasterTable().getName();
            List<CheckResult> unique = Utils.handleErrorMsg(masterTableName, checkMessages);
            response.setReturnValue(ActionReturnUtil.returnConfirmMessage(unique, this.getName()));
            return false;
        }
        return true;
    }

    default public void after(Model model, ActionRequest request, ActionResponse response) {
    }

    default public void final$(Model model, ActionRequest request, ActionResponse response) {
    }

    default public Map<String, Set<String>> getTriggerFields(ModelDefine modelDefine) {
        return null;
    }

    default public List<PluginCheckResultDTO> checkActionConfig(ModelDefine modelDefine, Map<String, Object> params) {
        return null;
    }

    default public ActionCategory getActionCategory() {
        return null;
    }
}

