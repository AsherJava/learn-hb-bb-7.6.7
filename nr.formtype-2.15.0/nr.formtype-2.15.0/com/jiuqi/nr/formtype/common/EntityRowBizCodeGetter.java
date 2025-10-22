/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.formtype.common;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class EntityRowBizCodeGetter {
    private boolean enableNrFormTypeMgr;
    private IEntityTable iEntityTable;
    private String formTypeZbName;
    private Map<String, FormTypeDataDefine> formTypeDataMap;

    public EntityRowBizCodeGetter(IEntityTable iEntityTable, boolean enableNrFormTypeMgr, List<FormTypeDataDefine> formTypeDatas) {
        this.iEntityTable = iEntityTable;
        this.enableNrFormTypeMgr = enableNrFormTypeMgr;
        IEntityModel entityModel = iEntityTable.getEntityModel();
        IEntityAttribute bblxField = entityModel.getBblxField();
        this.formTypeZbName = bblxField.getCode();
        this.formTypeDataMap = null == formTypeDatas ? Collections.emptyMap() : formTypeDatas.stream().collect(Collectors.toMap(FormTypeDataDefine::getCode, v -> v));
    }

    public String getBizCode(String orgcode, String bblx, String splitChar) {
        if (!this.enableNrFormTypeMgr || !StringUtils.hasLength(bblx)) {
            return orgcode;
        }
        IEntityRow iEntityRow = this.getIEntityRow(orgcode, bblx);
        return this.getBizCode(iEntityRow, splitChar);
    }

    public String getBizCode(IEntityRow iEntityRow, String splitChar) {
        IEntityRow parentEntityRow;
        if (!this.enableNrFormTypeMgr) {
            return iEntityRow.getCode();
        }
        FormTypeDataDefine formTypeData = this.formTypeDataMap.get(iEntityRow.getAsString(this.formTypeZbName));
        if (null == formTypeData) {
            return iEntityRow.getCode();
        }
        if (UnitNature.JTCEB == formTypeData.getUnitNatrue()) {
            return this.concat(iEntityRow.getCode(), formTypeData.getCode(), splitChar);
        }
        if (UnitNature.JCFHB == formTypeData.getUnitNatrue() && null != (parentEntityRow = this.iEntityTable.findByEntityKey(iEntityRow.getParentEntityKey())) && parentEntityRow.getCode().equals(iEntityRow.getCode())) {
            return this.concat(iEntityRow.getCode(), formTypeData.getCode(), splitChar);
        }
        return iEntityRow.getCode();
    }

    private String concat(String orgcode, String bblx, String splitChar) {
        return StringUtils.hasLength(splitChar) ? orgcode + splitChar + bblx : orgcode + bblx;
    }

    public IEntityRow getIEntityRowByBizCode(String bizCode, String splitChar) {
        String orgcode = null;
        String bblx = null;
        if (StringUtils.hasLength(splitChar)) {
            int lastIndexOf = bizCode.lastIndexOf(splitChar);
            if (-1 == lastIndexOf) {
                orgcode = bizCode;
            } else {
                orgcode = bizCode.substring(0, lastIndexOf);
                bblx = bizCode.substring(lastIndexOf + 1);
            }
        } else {
            orgcode = bizCode;
        }
        return this.getIEntityRow(orgcode, bblx);
    }

    public IEntityRow getIEntityRow(String orgcode, String bblx) {
        List<IEntityRow> rows = this.getByOrgcode(orgcode);
        if (CollectionUtils.isEmpty(rows)) {
            return null;
        }
        if (StringUtils.hasText(bblx)) {
            for (IEntityRow row : rows) {
                if (!bblx.equals(row.getAsString(this.formTypeZbName))) continue;
                return row;
            }
            return null;
        }
        return rows.get(0);
    }

    private List<IEntityRow> getByOrgcode(String orgcode) {
        return this.iEntityTable.findAllByCode(orgcode);
    }
}

