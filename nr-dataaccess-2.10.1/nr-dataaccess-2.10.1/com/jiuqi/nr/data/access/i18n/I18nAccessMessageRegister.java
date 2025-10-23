/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.data.access.i18n;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.data.access.service.IDataAccessItemBaseService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class I18nAccessMessageRegister
implements I18NResource {
    private static final long serialVersionUID = -8253502389972008675L;

    public String name() {
        return "\u65b0\u62a5\u8868/\u8bfb\u5199\u6743\u9650/\u65e0\u6743\u9650\u539f\u56e0";
    }

    public String getNameSpace() {
        return "access";
    }

    public List<I18NResourceItem> getResource(String parentId) {
        Map<String, IDataAccessItemBaseService> beansOfType = SpringBeanUtils.getApplicationContext().getBeansOfType(IDataAccessItemBaseService.class);
        Collection<IDataAccessItemBaseService> accessBaseServices = beansOfType.values();
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (null == parentId || StringUtils.isEmpty((String)parentId)) {
            for (IDataAccessItemBaseService accessBaseService : accessBaseServices) {
                List<String> codeList = accessBaseService.getCodeList();
                for (String accessCode : codeList) {
                    String message = accessBaseService.getAccessMessage().getMessage(accessCode);
                    if (!StringUtils.isNotEmpty((String)message) || accessCode.equals("1")) continue;
                    resourceObjects.add(new I18NResourceItem(accessBaseService.name().toUpperCase() + "_" + accessCode, message));
                }
            }
        }
        return resourceObjects;
    }
}

