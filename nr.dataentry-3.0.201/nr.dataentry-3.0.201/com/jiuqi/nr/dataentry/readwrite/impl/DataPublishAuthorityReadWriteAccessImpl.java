/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataPublishAuthorityParam;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteStatusAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataPublishAuthorityReadWriteAccessImpl
implements IReadWriteStatusAccess<List<String>> {
    private static final Logger logger = LoggerFactory.getLogger(DataPublishAuthorityReadWriteAccessImpl.class);
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IJtableParamService jtableParamService;

    @Override
    public String getName() {
        return "dataPublishAuthority";
    }

    @Override
    public List<String> getStatus(ReadWriteAccessItem item, JtableContext context) throws Exception {
        ArrayList<String> formKeys = new ArrayList<String>();
        DataPublishAuthorityParam param = new DataPublishAuthorityParam();
        param.setContext(context);
        Map map = (Map)item.getParams();
        param.setFormKeys((List)map.get("formKeys"));
        try {
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            String dimensionValue = ((DimensionValue)context.getDimensionSet().get(targetEntityInfo.getDimensionName())).getValue();
            boolean canPublishEntity = this.entityAuthorityService.canPublishEntity(targetEntityInfo.getKey(), dimensionValue, Consts.DATE_VERSION_MIN_VALUE, Consts.DATE_VERSION_MAX_VALUE);
            if (canPublishEntity) {
                DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
                for (String formKey : param.getFormKeys()) {
                    if (!authorityProvider.canPublish(formKey)) continue;
                    formKeys.add(formKey);
                }
            } else {
                formKeys = new ArrayList();
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u5355\u4f4d\u53d1\u5e03\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return formKeys;
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, List<String> status, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, List<String> status, JtableContext context) throws Exception {
        return new ReadWriteAccessDesc(true, "");
    }
}

