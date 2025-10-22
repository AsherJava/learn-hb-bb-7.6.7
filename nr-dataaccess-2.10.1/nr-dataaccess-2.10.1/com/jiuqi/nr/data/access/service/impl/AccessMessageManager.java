/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.data.access.service.impl;

import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataAccessItemService;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class AccessMessageManager {
    private static final Logger logger = LoggerFactory.getLogger(AccessMessageManager.class);
    private final Map<String, IAccessMessage> nameMessage = new ConcurrentHashMap<String, IAccessMessage>();
    @Autowired(required=false)
    private List<IDataAccessItemService> accessBaseServices;
    @Autowired(required=false)
    private List<IDataExtendAccessItemService> accessBaseExtendsServices;
    @Autowired
    @Qualifier(value="access")
    private I18nHelper i18nHelper;

    @PostConstruct
    public void init() {
        for (IDataAccessItemService iDataAccessItemService : this.accessBaseServices) {
            this.nameMessage.put(iDataAccessItemService.name(), iDataAccessItemService.getAccessMessage());
        }
        for (IDataExtendAccessItemService iDataExtendAccessItemService : this.accessBaseExtendsServices) {
            this.nameMessage.put(iDataExtendAccessItemService.name(), iDataExtendAccessItemService.getAccessMessage());
        }
    }

    public IAccessMessage getAccessMessage(String name) {
        return this.nameMessage.get(name);
    }

    public String getMessage(String name, String code) {
        IAccessMessage accessMessage = this.getAccessMessage(name);
        if (accessMessage == null) {
            return "";
        }
        String defaultMessage = accessMessage.getMessage(code);
        String message = this.i18nHelper.getMessage(name + code, null, defaultMessage);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("\u72b6\u6001\u5f15\u64ce-getMessage-\u65e0\u6743\u9650\u539f\u56e0\u67e5\u8be2 \u6743\u9650\u9879:[%s];\u65e0\u6743\u9650\u539f\u56e0:[%s]", name, message));
        }
        return message;
    }
}

