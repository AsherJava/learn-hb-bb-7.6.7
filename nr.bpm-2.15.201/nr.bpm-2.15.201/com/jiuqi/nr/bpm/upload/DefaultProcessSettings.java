/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.upload;

import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.List;

public interface DefaultProcessSettings {
    public FormSchemeDefine getFormScheme();

    public boolean startupSubmitCheck();

    public boolean startupConfirm();

    public boolean needUploadComment();

    public boolean isRetrivable();

    public boolean isFormEditableAfterUpload();

    public List<String> getUploadEntityKeys();

    public GrantedToEntityAndRoleParameter getSubmitCheckTaskActorStrategyParameter();
}

