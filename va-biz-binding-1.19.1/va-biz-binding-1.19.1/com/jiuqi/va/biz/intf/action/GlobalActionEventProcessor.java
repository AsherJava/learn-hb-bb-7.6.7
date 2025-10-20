/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;

public interface GlobalActionEventProcessor {
    default public boolean beforeAction(Model model, Action action, ActionRequest request, ActionResponse response) {
        return true;
    }

    default public void invokeAction(Model model, Action action, ActionRequest request, ActionResponse response) {
    }

    default public void afterAction(Model model, Action action, ActionRequest request, ActionResponse response) {
    }

    default public void finalAction(Model model, Action action, ActionRequest request, ActionResponse response) {
    }
}

