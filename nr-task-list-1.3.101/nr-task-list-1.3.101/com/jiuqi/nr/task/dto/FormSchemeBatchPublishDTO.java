/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FormSchemeBatchPublishDTO {
    private final Set<String> formSchemeKeys;
    private final boolean deployDataScheme;

    public FormSchemeBatchPublishDTO(Collection<String> formSchemeKeys, boolean deployDataScheme) {
        this.formSchemeKeys = new HashSet<String>(formSchemeKeys);
        this.deployDataScheme = deployDataScheme;
    }

    public Set<String> getFormSchemeKeys() {
        return this.formSchemeKeys;
    }

    public boolean isDeployDataScheme() {
        return this.deployDataScheme;
    }
}

