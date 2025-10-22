/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.spi.TypeParseStrategy
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.spi.TypeParseStrategy;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.ImpMode;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.dto.AccessDTO;
import com.jiuqi.nr.fielddatacrud.impl.dto.SaveResDTO;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NoPermissionUpdater
implements TableUpdater {
    private final FieldSaveInfo saveInfo;
    private final FieldRelation fieldRelation;
    protected final Set<String> noPermissionDw = new LinkedHashSet<String>();
    protected final Set<String> unAccessDw = new HashSet<String>();
    private int mdCodeIndex = -1;

    public NoPermissionUpdater(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        this.saveInfo = saveInfo;
        this.fieldRelation = fieldRelation;
        for (int i = 0; i < saveInfo.getFields().size(); ++i) {
            IMetaData metaData = saveInfo.getFields().get(i);
            if (!"MDCODE".equals(metaData.getCode())) continue;
            this.mdCodeIndex = i;
            break;
        }
    }

    @Override
    public void installParseStrategy() {
    }

    @Override
    public void setRowByDw(boolean rowByDw) {
    }

    @Override
    public void registerParseStrategy(int type, TypeParseStrategy parseStrategy) {
    }

    @Override
    public ReturnRes addRow(List<Object> values) throws CrudOperateException {
        if (this.mdCodeIndex != -1) {
            String dw = (String)values.get(this.mdCodeIndex);
            if (this.unAccessDw.contains(dw)) {
                this.noPermissionDw.add(dw);
                return ReturnRes.build((int)1101);
            }
            return ReturnRes.build((int)1103);
        }
        return ReturnRes.success();
    }

    @Override
    public ReturnRes addRow(Object[] values) throws CrudOperateException {
        if (this.mdCodeIndex != -1) {
            String dw = (String)values[this.mdCodeIndex];
            if (this.unAccessDw.contains(dw)) {
                this.noPermissionDw.add(dw);
                return ReturnRes.build((int)1101);
            }
            return ReturnRes.build((int)1103);
        }
        return ReturnRes.success();
    }

    @Override
    public void commit() throws CrudOperateException {
    }

    @Override
    public SaveRes getSaveRes() {
        SaveResDTO saveRes = new SaveResDTO();
        saveRes.setSaveDw(Collections.emptyList());
        saveRes.setFailDw(Collections.emptyList());
        saveRes.setFailMessage(Collections.emptyMap());
        saveRes.setNoPermissionDw(this.noPermissionDw);
        saveRes.setCount(0);
        return saveRes;
    }

    public void setAccessDTO(AccessDTO accessDTO) {
        for (DimensionValueSet noAccessMasterKey : accessDTO.getNoAccessMasterKeys()) {
            Object value = noAccessMasterKey.getValue(this.fieldRelation.getDwDimName());
            if (value == null) continue;
            this.unAccessDw.add(value.toString());
        }
        if (this.saveInfo.getMode() == ImpMode.FULL && this.saveInfo.getAuthMode() != ResouceType.NONE) {
            this.noPermissionDw.addAll(this.unAccessDw);
        }
    }
}

