/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.service.impl;

import com.jiuqi.nr.task.form.ext.FormDefineResourceExtSupport;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import com.jiuqi.nr.task.form.ext.service.ILinkConfigExtService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class LinkConfigExtServiceImpl
extends FormDefineResourceExtSupport
implements ILinkConfigExtService {
    public LinkConfigExtServiceImpl(List<IFormDefineResourceExt> formDefineResourceExts) {
        super(formDefineResourceExts);
    }

    @Override
    public Map<String, List<ConfigDTO>> listConfigs(String formKey, List<String> keys) {
        HashMap<String, List<ConfigDTO>> configs = new HashMap<String, List<ConfigDTO>>();
        for (IFormDefineResourceExt formDefineResourceExt : this.getFormDefineResourceExts()) {
            if (formDefineResourceExt.getLinkConfigExt() == null) continue;
            this.collectResult(configs, formDefineResourceExt.getLinkConfigExt().listConfigs(formKey, keys));
        }
        return configs;
    }

    @Override
    public void saveConfigs(String formKey, List<ConfigDTO> datas) {
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            if (ext.getLinkConfigExt() == null) continue;
            ext.getLinkConfigExt().saveConfigs(formKey, datas);
        }
    }

    @Override
    public List<ConfigDTO> listConfigs(String linkKey) {
        ArrayList<ConfigDTO> list = new ArrayList<ConfigDTO>();
        for (IFormDefineResourceExt ext : this.getFormDefineResourceExts()) {
            if (ext.getLinkConfigExt() == null) continue;
            list.add(ext.getLinkConfigExt().getConfig(linkKey));
        }
        return list;
    }
}

