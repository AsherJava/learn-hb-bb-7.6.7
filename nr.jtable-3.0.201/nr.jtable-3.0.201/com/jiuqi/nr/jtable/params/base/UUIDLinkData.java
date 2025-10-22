/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.importdata.ImportErrorDataInfo
 *  com.jiuqi.nr.common.importdata.SaveErrorDataInfo
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.jtable.params.base;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.importdata.ImportErrorDataInfo;
import com.jiuqi.nr.common.importdata.SaveErrorDataInfo;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.jtable.common.LinkType;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DataLinkStyleUtil;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.UUID;

public class UUIDLinkData
extends LinkData {
    public UUIDLinkData(DataLinkDefine dataLinkDefine, FieldDefine fieldDefine) {
        super(dataLinkDefine, fieldDefine);
        this.type = LinkType.LINK_TYPE_UUID.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, fieldDefine);
    }

    public UUIDLinkData(DataLinkDefine dataLinkDefine, ColumnModelDefine columnModelDefine) {
        super(dataLinkDefine, columnModelDefine);
        this.type = LinkType.LINK_TYPE_UUID.getValue();
        this.style = DataLinkStyleUtil.getOtherLinkStyle(dataLinkDefine, columnModelDefine);
    }

    @Override
    public Object getFormatData(AbstractData data, DataFormaterCache dataFormaterCache) {
        Object superData = super.getFormatData(data, dataFormaterCache);
        if (!(superData instanceof AbstractData)) {
            return superData;
        }
        return data.getAsString();
    }

    @Override
    public Object getData(Object value, DataFormaterCache dataFormaterCache, SaveErrorDataInfo saveErrorDataInfo) {
        if (saveErrorDataInfo == null) {
            saveErrorDataInfo = new ImportErrorDataInfo();
        }
        super.getData(value, dataFormaterCache, saveErrorDataInfo);
        if (value == null || StringUtils.isEmpty((String)value.toString()) || "null".equals(value.toString())) {
            return null;
        }
        String formatValue = value.toString();
        if (UUIDUtils.isUUID((String)formatValue)) {
            return UUID.fromString(formatValue);
        }
        return null;
    }
}

