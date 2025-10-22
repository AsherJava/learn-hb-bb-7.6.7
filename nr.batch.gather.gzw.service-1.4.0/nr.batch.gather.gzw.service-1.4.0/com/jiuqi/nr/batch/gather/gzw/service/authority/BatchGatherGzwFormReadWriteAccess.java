/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.access.param.AccessCode
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.param.IAccessMessage
 *  com.jiuqi.nr.data.access.service.IDataExtendAccessItemService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.batch.gather.gzw.service.authority;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.batch.gather.gzw.service.executor.EntityExecutor;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.param.AccessCode;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.param.IAccessMessage;
import com.jiuqi.nr.data.access.service.IDataExtendAccessItemService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BatchGatherGzwFormReadWriteAccess
implements IDataExtendAccessItemService {
    public static final String NAME = "BATCH_GATHER_GZW_FORM_RWA";
    public static final String DIMENSION_NAME_DATATIME = "DATATIME";
    public static final String TYPE_OF_SELECT_SUMMARY_TABLE = "H";
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private EntityExecutor entityExecutor;

    public AccessCode visible(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode readable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        return new AccessCode(this.name());
    }

    public AccessCode writeable(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        IEntityModel entityModel;
        IEntityAttribute bblxField;
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String contextEntityId = DsContextHolder.getDsContext().getContextEntityId();
        if (!StringUtils.hasLength(contextEntityId)) {
            contextEntityId = formScheme.getDw();
        }
        if ((bblxField = (entityModel = this.entityMetaService.getEntityModel(contextEntityId)).getBblxField()) == null) {
            return new AccessCode(this.name());
        }
        String bblxFieldKey = bblxField.getCode();
        DimensionValueSet dimensionValueSet = masterKey.toDimensionValueSet();
        List<IEntityRow> entityRows = this.entityExecutor.getEntityDataByMasterKeys(contextEntityId, formScheme.getDateTime(), dimensionValueSet);
        Optional result_entityRow = entityRows.stream().findFirst();
        if (!result_entityRow.isPresent()) {
            return new AccessCode(this.name());
        }
        String bblx = ((IEntityRow)result_entityRow.get()).getValue(bblxFieldKey).getAsString();
        if (bblx != null && bblx.equals(TYPE_OF_SELECT_SUMMARY_TABLE)) {
            return new AccessCode(this.name(), "2");
        }
        return new AccessCode(this.name());
    }

    public String name() {
        return NAME;
    }

    public boolean isServerAccess() {
        return true;
    }

    public IAccessMessage getAccessMessage() {
        return code -> "\u6c47\u603b\u5355\u4f4d\u4e2d\u7684\u6570\u636e\u4e0d\u5141\u8bb8\u7f16\u8f91\uff01";
    }

    public int getOrder() {
        return 0;
    }
}

