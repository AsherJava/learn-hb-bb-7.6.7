/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.parse.strategy;

import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.parse.BaseTypeParseStrategy;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import org.springframework.util.ObjectUtils;

public class ObjParseStrategy
extends BaseTypeParseStrategy {
    @Override
    protected int getDataType() {
        return 0;
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, DataField field, Object data) {
        if (link == null && field == null) {
            return this.okValue(data);
        }
        ReturnRes returnRes = this.checkNonNull(link, field, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        if (ObjectUtils.isEmpty(data)) {
            return this.okValue(null);
        }
        return this.okValue(data);
    }

    @Override
    public ParseReturnRes checkParse(DataLinkDefine link, IFMDMAttribute attribute, Object data) {
        if (link == null && attribute == null) {
            return this.okValue(data);
        }
        ReturnRes returnRes = this.checkNonNull(link, attribute, data);
        if (returnRes != null && returnRes.getCode() != 0) {
            return new ParseReturnRes(returnRes);
        }
        if (data == null) {
            return this.okValue(null);
        }
        return this.okValue(data);
    }
}

