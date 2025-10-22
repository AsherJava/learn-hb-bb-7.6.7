/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataAccessExtraResultService
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataentry.readwrite.impl.DataPublishAuthorityReadWriteAccessImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DataPublishAuthorityAccessServiceImpl
implements IDataExtendAccessItemService,
IDataAccessExtraResultService<List<String>> {
    private static final Logger logger = LoggerFactory.getLogger(DataPublishAuthorityReadWriteAccessImpl.class);
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IJtableParamService jtableParamService;

    public int getOrder() {
        return 10;
    }

    public AccessCode visible(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem pram, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public Optional<List<String>> getExtraResult(AccessItem param, String formSchemeKey, DimensionCombination collectionKey, String formKey) {
        Assert.notNull((Object)param, "param is must not be null!");
        Map map = (Map)param.getParams();
        List formKeys = (List)map.get("formKeys");
        ArrayList<String> resformKeys = new ArrayList<String>();
        try {
            DimensionValueSet masterKey = collectionKey.toDimensionValueSet();
            EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(formSchemeKey);
            String dimensionValue = String.valueOf(masterKey.getValue(targetEntityInfo.getDimensionName()));
            boolean canPublishEntity = this.entityAuthorityService.canPublishEntity(targetEntityInfo.getKey(), dimensionValue, Consts.DATE_VERSION_MIN_VALUE, Consts.DATE_VERSION_MAX_VALUE);
            if (canPublishEntity) {
                DefinitionAuthorityProvider authorityProvider = (DefinitionAuthorityProvider)BeanUtil.getBean(DefinitionAuthorityProvider.class);
                for (String form : formKeys) {
                    if (!authorityProvider.canPublish(form, (String)collectionKey.getValue(targetEntityInfo.getDimensionName()), targetEntityInfo.getKey())) continue;
                    resformKeys.add(form);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u5355\u4f4d\u53d1\u5e03\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return Optional.ofNullable(resformKeys);
    }

    public String name() {
        return "dataPublishAuthority";
    }

    public IAccessMessage getAccessMessage() {
        return code -> "1";
    }

    public List<String> getCodeList() {
        return Arrays.asList("1");
    }
}

