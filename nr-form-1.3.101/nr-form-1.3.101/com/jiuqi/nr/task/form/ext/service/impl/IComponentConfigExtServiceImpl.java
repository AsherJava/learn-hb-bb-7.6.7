/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.service.impl;

import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.service.IComponentConfigExtService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IComponentConfigExtServiceImpl
extends FormDefineResourceExtSupport
implements IComponentConfigExtService {
    protected IComponentConfigExtServiceImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
    }

    @Override
    public List<ConfigDTO> getDefaultConfigs() {
        ArrayList<ConfigDTO> configDTOS = new ArrayList<ConfigDTO>();
        for (IFormDefineResourceExt defineResourceExt : this.getFormDefineResourceExts()) {
            if (defineResourceExt.getComponentConfigExt() == null) continue;
            configDTOS.add(defineResourceExt.getComponentConfigExt().getDefaultConfig());
        }
        return configDTOS;
    }

    @Override
    public List<ConfigDTO> getConfigs(String formKey) {
        ArrayList<ConfigDTO> configDTOS = new ArrayList<ConfigDTO>();
        for (IFormDefineResourceExt defineResourceExt : this.getFormDefineResourceExts()) {
            if (defineResourceExt.getComponentConfigExt() == null) continue;
            configDTOS.add(defineResourceExt.getComponentConfigExt().getConfig(formKey));
        }
        return configDTOS;
    }

    @Override
    public void saveConfigs(String formKey, List<ConfigDTO> configDTOS) {
        if (configDTOS == null || configDTOS.isEmpty()) {
            return;
        }
        for (ConfigDTO configDTO : configDTOS) {
            IFormDefineResourceExt formDefineResourceExt = this.getFormDefineResourceExtCache().get(configDTO.getCode());
            if (formDefineResourceExt == null || formDefineResourceExt.getComponentConfigExt() == null) continue;
            formDefineResourceExt.getComponentConfigExt().saveConfig(formKey, configDTO);
        }
    }
}

