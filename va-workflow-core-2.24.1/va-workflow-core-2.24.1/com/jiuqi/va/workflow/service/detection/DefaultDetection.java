/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.detection;

import com.jiuqi.va.workflow.service.detection.BaseDetectionHandler;
import org.springframework.stereotype.Component;

@Component
public class DefaultDetection
extends BaseDetectionHandler {
    @Override
    public String getName() {
        return "Default";
    }
}

