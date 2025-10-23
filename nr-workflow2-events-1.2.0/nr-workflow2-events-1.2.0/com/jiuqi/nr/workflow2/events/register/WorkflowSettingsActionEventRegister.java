/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.registry.ActionEventRegistry
 */
package com.jiuqi.nr.workflow2.events.register;

import com.jiuqi.nr.workflow2.engine.core.settings.registry.ActionEventRegistry;

public class WorkflowSettingsActionEventRegister {
    static {
        ActionEventRegistry.registerActionEvent((String)"act_submit", (String)"complete-calculation-event");
        ActionEventRegistry.registerActionEvent((String)"act_submit", (String)"complete-review-event");
        ActionEventRegistry.registerActionEvent((String)"act_submit", (String)"check-unit-node-event");
        ActionEventRegistry.registerActionEvent((String)"act_submit", (String)"send-message-notice-event");
        ActionEventRegistry.registerActionEvent((String)"act_upload", (String)"complete-calculation-event");
        ActionEventRegistry.registerActionEvent((String)"act_upload", (String)"complete-review-event");
        ActionEventRegistry.registerActionEvent((String)"act_upload", (String)"check-unit-node-event");
        ActionEventRegistry.registerActionEvent((String)"act_upload", (String)"send-message-notice-event");
        ActionEventRegistry.registerActionEvent((String)"act_return", (String)"send-message-notice-event");
        ActionEventRegistry.registerActionEvent((String)"act_confirm", (String)"send-message-notice-event");
        ActionEventRegistry.registerActionEvent((String)"act_reject", (String)"made-data-snapshot-version-event");
        ActionEventRegistry.registerActionEvent((String)"act_reject", (String)"send-message-notice-event");
        ActionEventRegistry.registerActionEvent((String)"act_apply_reject", (String)"send-message-notice-event");
    }
}

