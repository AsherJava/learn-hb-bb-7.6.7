/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.view.impl.ControlManagerImpl
 *  com.jiuqi.va.biz.view.intf.Composite
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.va.bill.utils;

import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class BillCoreI18nUtil {
    public static final String ResourceNotFound = "Resource not found";
    public static final String WORKFLOW_ACTIVITI_I18NKEY = "va_activiti_";
    @Autowired
    @Qualifier(value="billCoreMessageSource")
    private MessageSource messageSourceBean;
    public static MessageSource messageSource;

    @PostConstruct
    private void init() {
        messageSource = this.messageSourceBean;
    }

    public static String getMessage(String key) {
        return BillCoreI18nUtil.getMessage(key, null);
    }

    public static String getMessage(String key, Object[] args) {
        try {
            Locale locale = null;
            locale = VaI18nParamUtil.getTranslationEnabled() == false || LocaleContextHolder.getLocaleContext() == null || LocaleContextHolder.getLocaleContext().getLocale() == null ? Locale.CHINA : LocaleContextHolder.getLocale();
            String message = messageSource.getMessage(key, args, locale);
            if (!StringUtils.hasText(message)) {
                message = ResourceNotFound;
            }
            return message;
        }
        catch (Exception e) {
            return ResourceNotFound;
        }
    }

    public static String getWorkflowActivitiMessage(String message) {
        try {
            String i18nMessage;
            String info;
            String string = info = StringUtils.hasText(message) ? message : "";
            if (info.contains("$")) {
                String key = info.split("\\$")[1];
                String str = StringUtils.hasText(BillCoreI18nUtil.getMessage(key)) ? BillCoreI18nUtil.getMessage(key) : key;
                i18nMessage = info.split("\\$")[0] + str;
            } else {
                String key = info;
                i18nMessage = StringUtils.hasText(BillCoreI18nUtil.getMessage(key)) ? BillCoreI18nUtil.getMessage(key) : key;
            }
            return i18nMessage;
        }
        catch (Exception e) {
            return message;
        }
    }

    public static List<VaI18nResourceItem> getI18nResourceByControlName(Map<String, Object> template, String controlName) {
        ArrayList<VaI18nResourceItem> controlResourceList = new ArrayList<VaI18nResourceItem>();
        Composite composite = (Composite)ControlManagerImpl.createControl(template);
        ArrayList<Control> list = new ArrayList<Control>();
        BillCoreI18nUtil.parseBillDefine(list, composite, controlName);
        if (list.size() > 0) {
            for (Control control : list) {
                VaI18nResourceItem controlItem = new VaI18nResourceItem();
                controlItem.setName(control.getId().toString());
                if (control.getProps().containsKey("title")) {
                    controlItem.setTitle(control.getProps().get("title").toString());
                } else {
                    controlItem.setTitle(control.getId().toString());
                }
                controlResourceList.add(controlItem);
            }
        }
        return controlResourceList;
    }

    public static void parseBillDefine(List<Control> list, Composite composite, String controlName) {
        List controls = composite.getChildren();
        for (Control control : controls) {
            if (controlName.equals(control.getType())) {
                list.add(control);
            }
            if (!(control instanceof Composite)) continue;
            BillCoreI18nUtil.parseBillDefine(list, (Composite)control, controlName);
        }
    }
}

