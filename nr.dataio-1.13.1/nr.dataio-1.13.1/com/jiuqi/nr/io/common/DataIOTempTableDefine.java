/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.IndexMeta
 */
package com.jiuqi.nr.io.common;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.IndexMeta;
import com.jiuqi.nr.io.sb.bean.MdCodeTempTableInfo;
import com.jiuqi.nr.io.sb.bean.SbIdTempTableInfo;
import com.jiuqi.nr.io.tz.bean.JioTempTableInfo;
import com.jiuqi.nr.io.tz.bean.StateTempTableInfo;
import com.jiuqi.nr.io.tz.bean.TzFzTempTableInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataIOTempTableDefine
extends BaseTempTableDefine {
    private static final String TYPE_TITLE = "\u5bfc\u5165\u5bfc\u51fa\u52a8\u6001\u4e34\u65f6\u8868\u5b9a\u4e49";
    private List<LogicField> logicFields;
    private List<String> primaryKeyFields;
    private final List<IndexMeta> indexMetas = new ArrayList<IndexMeta>();

    public DataIOTempTableDefine() {
    }

    public DataIOTempTableDefine(MdCodeTempTableInfo mdCodeTempTableInfo) {
        this.logicFields = mdCodeTempTableInfo.getLogicFields();
        this.primaryKeyFields = mdCodeTempTableInfo.getPrimaryKeyFields();
    }

    public DataIOTempTableDefine(SbIdTempTableInfo sbIdTempTableInfo) {
        this.logicFields = sbIdTempTableInfo.getLogicFields();
        this.primaryKeyFields = sbIdTempTableInfo.getPrimaryKeyFields();
    }

    public DataIOTempTableDefine(JioTempTableInfo jioTempTableInfo) {
        this.logicFields = jioTempTableInfo.getLogicFields();
        this.primaryKeyFields = jioTempTableInfo.getPrimaryKeyFields();
    }

    public DataIOTempTableDefine(TzFzTempTableInfo tzFzTempTableInfo) {
        this.logicFields = tzFzTempTableInfo.getLogicFields();
        this.primaryKeyFields = tzFzTempTableInfo.getPrimaryKeyFields();
        this.indexMetas.addAll(tzFzTempTableInfo.getTempIndices());
    }

    public DataIOTempTableDefine(StateTempTableInfo stateTempTableInfo) {
        this.logicFields = stateTempTableInfo.getLogicFields();
        this.primaryKeyFields = stateTempTableInfo.getPrimaryKeyFields();
    }

    public boolean isDynamic() {
        return true;
    }

    public String getType() {
        return "DATAIO";
    }

    public String getTypeTitle() {
        return TYPE_TITLE;
    }

    public List<LogicField> getLogicFields() {
        return this.logicFields;
    }

    public List<String> getPrimaryKeyFields() {
        return this.primaryKeyFields;
    }

    public String getModuleName() {
        return this.getTypeTitle();
    }

    public String getDesc() {
        return TYPE_TITLE;
    }

    public List<IndexMeta> getIndexes() {
        return this.indexMetas;
    }
}

