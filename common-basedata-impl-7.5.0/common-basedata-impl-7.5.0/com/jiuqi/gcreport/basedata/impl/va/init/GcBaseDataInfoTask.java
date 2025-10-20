/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.datamodel.exchange.nvwa.base.FDataModelEditEntiy
 *  com.jiuqi.va.datamodel.exchange.nvwa.base.TableType
 *  com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelTableDefine
 *  com.jiuqi.va.datamodel.exchange.nvwa.task.FModelDataViewTask
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gcreport.basedata.impl.va.init;

import com.jiuqi.va.datamodel.exchange.nvwa.base.FDataModelEditEntiy;
import com.jiuqi.va.datamodel.exchange.nvwa.base.TableType;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelTableDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.task.FModelDataViewTask;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class GcBaseDataInfoTask
implements FModelDataViewTask {
    public static final String GROUP_ENUM_ID = new UUID(88L, 80L).toString();
    public static final String GROUP_ENUM_CODE = "BASEDATA_ENUM";
    public static final String GROUP_ENUM_TITLE = "\u57fa\u7840\u6570\u636e(\u679a\u4e3e)";
    public static final String GROUP_ENUM_PARENT = "2a2e54ea-8dfa-4c8d-a2dc-70d79367f306";
    public static int TABLE_KIND_DICTIONARY_VALUE = 3;

    public boolean initTableDefine(DataModelTableDefine table, FDataModelEditEntiy entiy) {
        super.initTableDefine(table, entiy);
        try {
            BaseDataDefineDO define = this.getBaseDataDo(entiy);
            if (define == null) {
                return false;
            }
            if ("GC_BASEDATA_ENUM".equals(define.getGroupname())) {
                table.setKind(Integer.valueOf(TABLE_KIND_DICTIONARY_VALUE));
                table.getOwnerGroupID().setId(GROUP_ENUM_PARENT);
                table.getOwnerGroupID().setCode("root_enum");
                table.getOwnerGroupID().setTitle("\u679a\u4e3e\u5b57\u5178\u6839\u5206\u7ec4");
                table.getOwnerGroupID().setParentid(null);
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private BaseDataDefineDO getBaseDataDo(FDataModelEditEntiy entiy) {
        Object data = entiy.getVdataModel().getExtInfo("baseDataDefine");
        if (data == null) {
            return null;
        }
        if (data instanceof BaseDataDefineDTO) {
            return (BaseDataDefineDTO)data;
        }
        return (BaseDataDefineDO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)data), BaseDataDefineDTO.class);
    }

    public boolean accept(TableType type) {
        return TableType.BASEDATA == type;
    }
}

