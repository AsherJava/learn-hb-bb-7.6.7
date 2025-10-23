/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext;

import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.IFormDefineResourceExt;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class FormDefineResourceExtSupport {
    private final List<IFormDefineResourceExt> formDefineResourceExts = new ArrayList<IFormDefineResourceExt>();
    private final Map<String, IFormDefineResourceExt> formDefineResourceExtCache = new LinkedHashMap<String, IFormDefineResourceExt>();

    protected FormDefineResourceExtSupport(List<IFormDefineResourceExt> formDefineResourceExts) {
        if (formDefineResourceExts != null) {
            formDefineResourceExts.stream().sorted(Comparator.comparing(IFormDefineResourceExt::getOrder)).forEach(this.formDefineResourceExts::add);
            this.formDefineResourceExts.forEach(ext -> this.formDefineResourceExtCache.put(ext.getCode(), (IFormDefineResourceExt)ext));
        }
    }

    public List<IFormDefineResourceExt> getFormDefineResourceExts() {
        return this.formDefineResourceExts;
    }

    public Map<String, IFormDefineResourceExt> getFormDefineResourceExtCache() {
        return this.formDefineResourceExtCache;
    }

    protected void collectResult(Map<String, List<ConfigDTO>> configs, Map<String, List<ConfigDTO>> queryListMap) {
        if (queryListMap != null) {
            queryListMap.forEach((query, value) -> {
                List configDTOS = configs.computeIfAbsent((String)query, k -> new ArrayList());
                configDTOS.addAll(value);
            });
        }
    }
}

