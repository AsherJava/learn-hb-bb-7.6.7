/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 */
package com.jiuqi.nr.bpm.impl.upload.modeling;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.upload.DefaultProcessSettings;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultProcessSettingsWrapper
implements DefaultProcessSettings {
    private final FormSchemeDefine formScheme;
    private final TaskFlowsDefine taskFlowsDefine;

    public DefaultProcessSettingsWrapper(FormSchemeDefine formScheme, TaskFlowsDefine taskFlowsDefine) {
        this.formScheme = formScheme;
        this.taskFlowsDefine = taskFlowsDefine;
    }

    @Override
    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    @Override
    public boolean startupSubmitCheck() {
        return this.taskFlowsDefine.isUnitSubmitForCensorship();
    }

    @Override
    public boolean startupConfirm() {
        return this.taskFlowsDefine.isDataConfirm();
    }

    @Override
    public boolean needUploadComment() {
        return this.taskFlowsDefine.isSubmitExplain();
    }

    @Override
    public boolean isRetrivable() {
        return this.taskFlowsDefine.isAllowTakeBack();
    }

    @Override
    public boolean isFormEditableAfterUpload() {
        return this.taskFlowsDefine.isAllowModifyData();
    }

    @Override
    public List<String> getUploadEntityKeys() {
        if (StringUtils.isEmpty((String)this.taskFlowsDefine.getDesignTableDefines())) {
            return Collections.emptyList();
        }
        return Arrays.asList(this.taskFlowsDefine.getDesignTableDefines().split(";")).stream().map(t -> t).collect(Collectors.toList());
    }

    @Override
    public GrantedToEntityAndRoleParameter getSubmitCheckTaskActorStrategyParameter() {
        if (!this.taskFlowsDefine.isUnitSubmitForCensorship()) {
            return null;
        }
        GrantedToEntityAndRoleParameter parameter = new GrantedToEntityAndRoleParameter();
        Set<String> roleIdSet = StringUtils.isEmpty((String)this.taskFlowsDefine.getSelectedRoleKey()) ? Collections.emptySet() : Arrays.stream(this.taskFlowsDefine.getSelectedRoleKey().split(";")).collect(Collectors.toSet());
        parameter.setRoleIdSet(roleIdSet);
        return parameter;
    }
}

