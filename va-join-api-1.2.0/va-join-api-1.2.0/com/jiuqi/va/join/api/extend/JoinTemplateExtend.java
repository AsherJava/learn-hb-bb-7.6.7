/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.join.api.extend;

import com.jiuqi.va.join.api.common.JoinTemplate;
import com.jiuqi.va.join.api.domain.JoinDeclare;
import com.jiuqi.va.join.api.domain.JoinListener;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JoinTemplateExtend
extends JoinTemplate {
    public static final Logger logger = LoggerFactory.getLogger(JoinTemplateExtend.class);

    public String getName();

    public boolean isEnabled();

    default public void batchAddDeclare(Collection<JoinDeclare> joinDeclares) {
        for (JoinDeclare declare : joinDeclares) {
            this.addDeclare(declare);
        }
    }

    default public void batchAddListener(Collection<JoinListener> joinListeners) {
        for (JoinListener listener : joinListeners) {
            this.addListener(listener);
        }
    }

    default public void handleFormerNames(Collection<JoinDeclare> joinDeclares) {
        logger.error("\u6ce8\u610f\uff1a\u6b64\u6269\u5c55\u672a\u5904\u7406\u66fe\u7528\u540d - " + this.getClass().getName());
    }
}

