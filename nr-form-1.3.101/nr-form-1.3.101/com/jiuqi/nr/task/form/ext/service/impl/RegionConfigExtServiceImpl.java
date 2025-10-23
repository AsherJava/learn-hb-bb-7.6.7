/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.service.impl;

import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.service.IRegionConfigExtService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RegionConfigExtServiceImpl
extends FormDefineResourceExtSupport
implements IRegionConfigExtService {
    protected RegionConfigExtServiceImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
    }

    @Override
    public Map<String, List<ConfigDTO>> listConfigs(String formKey, List<String> keys) {
        HashMap<String, List<ConfigDTO>> configs = new HashMap<String, List<ConfigDTO>>();
        for (IFormDefineResourceExt formDefineResourceExt : this.getFormDefineResourceExts()) {
            if (formDefineResourceExt.getRegionConfigExt() == null) continue;
            this.collectResult(configs, formDefineResourceExt.getRegionConfigExt().listConfigs(formKey, keys));
        }
        return configs;
    }

    @Override
    public void saveConfigs(String formKey, List<ConfigDTO> datas) {
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            if (ext.getRegionConfigExt() == null) continue;
            ext.getRegionConfigExt().saveConfigs(formKey, datas);
        }
    }

    @Override
    public List<ConfigDTO> listConfigs(String regionKey) {
        ArrayList<ConfigDTO> list = new ArrayList<ConfigDTO>();
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            if (ext.getRegionConfigExt() == null) continue;
            list.add(ext.getRegionConfigExt().getConfig(regionKey));
        }
        return list;
    }
}

