/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.definition.deploy.extend;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.function.Consumer;

public interface IParamDeployFinishListener {
    default public void execute(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
    }

    default public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.execute(define, warningConsumer, progressConsumer);
    }

    default public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.execute(define, warningConsumer, progressConsumer);
    }

    default public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.execute(define, warningConsumer, progressConsumer);
    }
}

