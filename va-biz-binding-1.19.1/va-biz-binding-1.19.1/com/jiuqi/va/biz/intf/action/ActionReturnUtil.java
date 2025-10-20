/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnCheckMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnConfirmTemplate;
import com.jiuqi.va.biz.intf.action.ActionReturnGlobalMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnModalMessage;
import com.jiuqi.va.biz.intf.action.ActionReturnModalTemplate;
import com.jiuqi.va.biz.intf.action.ActionReturnUrl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.io.InputStream;
import java.util.List;

public class ActionReturnUtil {
    public static final Object returnFile(InputStream input, String fileType) {
        throw new UnsupportedOperationException();
    }

    public static final ActionReturnConfirmMessage returnConfirmMessage(String message) {
        ActionReturnConfirmMessage returnObject = new ActionReturnConfirmMessage();
        returnObject.setContent(message);
        return returnObject;
    }

    public static final ActionReturnConfirmMessage returnConfirmMessage(String message, String confirmFrom) {
        ActionReturnConfirmMessage returnObject = new ActionReturnConfirmMessage();
        returnObject.setContent(message);
        returnObject.setConfirmFrom(confirmFrom);
        return returnObject;
    }

    public static final ActionReturnConfirmMessage returnConfirmMessage(List<CheckResult> checkResults, String confirmFrom) {
        ActionReturnConfirmMessage returnObject = new ActionReturnConfirmMessage();
        returnObject.setCheckResults(checkResults);
        returnObject.setConfirmFrom(confirmFrom);
        return returnObject;
    }

    public static final ActionReturnConfirmTemplate returnConfirmTemplate(String template, String renderType, String confirmFrom) {
        ActionReturnConfirmTemplate returnObject = new ActionReturnConfirmTemplate();
        returnObject.getTemplateDeclare().declareFromJSON(template);
        returnObject.setRenderType(renderType);
        returnObject.setConfirmFrom(confirmFrom);
        return returnObject;
    }

    public static final ActionReturnConfirmTemplate returnConfirmTemplate(String template, String renderType) {
        ActionReturnConfirmTemplate returnObject = new ActionReturnConfirmTemplate();
        returnObject.getTemplateDeclare().declareFromJSON(template);
        returnObject.setRenderType(renderType);
        return returnObject;
    }

    public static final ActionReturnGlobalMessage returnGlobalMessage(String message, String messageType) {
        ActionReturnGlobalMessage returnObject = new ActionReturnGlobalMessage();
        returnObject.setContent(message);
        returnObject.setMessageType(messageType);
        return returnObject;
    }

    public static final ActionReturnModalMessage returnModalMessage(String message, String messageType) {
        ActionReturnModalMessage returnObject = new ActionReturnModalMessage();
        returnObject.setContent(message);
        returnObject.setMessageType(messageType);
        return returnObject;
    }

    public static final ActionReturnModalTemplate returnModalTemplate(String template, String renderType) {
        ActionReturnModalTemplate returnObject = new ActionReturnModalTemplate();
        returnObject.getTemplateDeclare().declareFromJSON(template);
        returnObject.setRenderType(renderType);
        returnObject.setHideFooter(false);
        return returnObject;
    }

    public static final ActionReturnCheckMessage returnCheckMessage(String message, List<CheckResult> messages) {
        ActionReturnCheckMessage returnObject = new ActionReturnCheckMessage();
        returnObject.setMessages(messages);
        return returnObject;
    }

    public static final ActionReturnUrl returnUrl(String url, String name, String specs, Boolean replace) {
        ActionReturnUrl returnObject = new ActionReturnUrl();
        returnObject.setName(name);
        returnObject.setUrl(url);
        returnObject.setReplace(replace == null ? false : replace);
        returnObject.setSpecs(specs);
        return returnObject;
    }

    public static final ActionReturnUrl returnUrl(String url, String name, String specs, Boolean replace, Boolean doublescan) {
        ActionReturnUrl returnObject = new ActionReturnUrl();
        returnObject.setName(name);
        returnObject.setUrl(url);
        returnObject.setSpecs(specs);
        returnObject.setReplace(replace == null ? false : replace);
        returnObject.setDoublescan(doublescan == null ? false : doublescan);
        return returnObject;
    }
}

