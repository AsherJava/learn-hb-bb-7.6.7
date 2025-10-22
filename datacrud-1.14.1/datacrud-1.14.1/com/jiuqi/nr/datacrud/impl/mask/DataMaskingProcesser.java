/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataMaskingProcesser
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.datacrud.impl.mask;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataMaskingProcesser;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataMaskingProcesser
implements IDataMaskingProcesser {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DesensitizedEncryptor encryptor;
    private final Logger logger = LoggerFactory.getLogger(DataMaskingProcesser.class);

    public String getMaskingData(ExecutorContext context, ColumnModelDefine field, String value) {
        if (field == null || value == null) {
            return value;
        }
        String maskCode = Optional.ofNullable(field.getID()).map(arg_0 -> ((IRuntimeDataSchemeService)this.runtimeDataSchemeService).getDeployInfoByColumnKey(arg_0)).map(DataFieldDeployInfo::getDataFieldKey).filter(StringUtils::hasLength).map(arg_0 -> ((IRuntimeDataSchemeService)this.runtimeDataSchemeService).getDataField(arg_0)).map(DataField::getDataMaskCode).filter(StringUtils::hasLength).orElse(null);
        if (maskCode != null) {
            try {
                return this.encryptor.desensitize(maskCode, value);
            }
            catch (Exception e) {
                this.logger.warn(" \u8131\u654f\u6267\u884c\u5931\u8d25 | \u7801\u503c:{} | \u539f\u503c:{} | \u9519\u8bef:{}", maskCode, value, e.getMessage());
            }
        }
        return value;
    }
}

