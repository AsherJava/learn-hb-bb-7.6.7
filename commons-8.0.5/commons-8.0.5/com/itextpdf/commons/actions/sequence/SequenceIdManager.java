/*
 * Decompiled with CFR 0.152.
 */
package com.itextpdf.commons.actions.sequence;

import com.itextpdf.commons.actions.sequence.AbstractIdentifiableElement;
import com.itextpdf.commons.actions.sequence.SequenceId;
import com.itextpdf.commons.utils.MessageFormatUtil;

public final class SequenceIdManager {
    private SequenceIdManager() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setSequenceId(AbstractIdentifiableElement element, SequenceId sequenceId) {
        AbstractIdentifiableElement abstractIdentifiableElement = element;
        synchronized (abstractIdentifiableElement) {
            if (element.getSequenceId() != null) {
                throw new IllegalStateException(MessageFormatUtil.format("Element already has sequence id: {0}, new id {1} will be ignored", element.getSequenceId().getId(), sequenceId.getId()));
            }
            element.setSequenceId(sequenceId);
        }
    }

    public static SequenceId getSequenceId(AbstractIdentifiableElement element) {
        return element.getSequenceId();
    }
}

