/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.definition.i18n;

import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.dao.AuditTypeDefineDao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class I18nAuditRegister
implements I18NResource {
    private static final long serialVersionUID = 2689653124182745877L;
    private static final Logger logger = LoggerFactory.getLogger(I18nAuditRegister.class);
    public static final String NAME_SPACE = "audit_type";
    static final String NAME = "\u65b0\u62a5\u8868/\u4efb\u52a1\u8bbe\u8ba1/\u5ba1\u6838\u7c7b\u578b";

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAME_SPACE;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        List<AuditType> queryAllAuditType;
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        AuditTypeDefineDao auditTypeDefineDao = BeanUtil.getBean(AuditTypeDefineDao.class);
        try {
            queryAllAuditType = auditTypeDefineDao.list();
        }
        catch (Exception e) {
            queryAllAuditType = Collections.emptyList();
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (!StringUtils.hasText(parentId)) {
            queryAllAuditType.forEach(value -> resourceObjects.add(new I18NResourceItem(NAME_SPACE + value.getCode(), value.getTitle())));
        }
        return resourceObjects;
    }
}

