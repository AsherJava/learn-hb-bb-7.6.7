/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.ParseReturnRes
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.impl.parse.strategy.EnumParseStrategy
 *  com.jiuqi.nr.datacrud.spi.IEntityTableFactory
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.fielddatacrud.impl.strategy;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ParseReturnRes;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.impl.parse.strategy.EnumParseStrategy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class CsvEnumParseStrategy
extends EnumParseStrategy {
    private final FieldRelation fieldRelation;
    private int entityValueMode = 0;

    public int getEntityValueMode() {
        return this.entityValueMode;
    }

    public void setEntityValueMode(int entityValueMode) {
        this.entityValueMode = entityValueMode;
    }

    public CsvEnumParseStrategy(FieldRelation fieldRelation, IEntityTableFactory entityTableFactory) {
        super(null, entityTableFactory);
        this.fieldRelation = fieldRelation;
    }

    public ParseReturnRes checkParse(DataLinkDefine link, DataField field, Object data) {
        String refDataEntityKey;
        if (field != null && StringUtils.hasLength(refDataEntityKey = field.getRefDataEntityKey())) {
            ReturnRes returnRes = this.checkNonNull(link, field, data);
            if (returnRes != null && returnRes.getCode() != 0) {
                return new ParseReturnRes(returnRes);
            }
            if (ObjectUtils.isEmpty(data)) {
                return this.okValue(null);
            }
            HashSet<String> chooseEntity = new HashSet<String>();
            ParseReturnRes res = this.parseEntityIds(field, chooseEntity, data);
            if (res != null) {
                return res;
            }
            int precision = field.getPrecision() == null ? 0 : field.getPrecision();
            String value = String.join((CharSequence)";", chooseEntity);
            if (precision > 0 && precision < value.length()) {
                String message = "\u6307\u6807:" + field.getTitle() + ";\u6570\u636e\u8d85\u51fa\u6307\u6807\u957f\u5ea6" + precision + "\u3002\u9519\u8bef\u503c:" + value;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(data, message);
            }
            return this.okValue(value);
        }
        return super.checkParse(link, field, data);
    }

    public ParseReturnRes parseEntityIds(DataField field, Set<String> chooseEntity, Object data) {
        String dataStr = data.toString();
        boolean single = !field.isAllowMultipleSelect();
        List entityItems = this.splitEnumCode(dataStr, true);
        IMetaData metaData = this.fieldRelation.addMetaDataByField(field.getKey());
        if (this.entityTableWrapper == null) {
            this.entityTableWrapper = this.entityTableFactory.getEntityTable((ParamRelation)this.fieldRelation, this.currMasterKey, metaData);
        } else {
            this.entityTableFactory.reBuildEntityTable((ParamRelation)this.fieldRelation, this.currMasterKey, metaData, this.entityTableWrapper);
        }
        for (String keyOrCode : entityItems) {
            IEntityRow entityRow = null;
            if (this.entityValueMode == 0) {
                entityRow = this.entityTableWrapper.findByEntityKey(keyOrCode);
            } else if (this.entityValueMode == 1) {
                entityRow = this.entityTableWrapper.findByCode(keyOrCode);
            }
            if (entityRow != null) {
                chooseEntity.add(entityRow.getEntityKeyData());
            } else if (Boolean.TRUE.equals(field.isAllowUndefinedCode())) {
                chooseEntity.add(keyOrCode);
            } else {
                String message = "\u672a\u627e\u5230\u5bf9\u5e94\u7684\u679a\u4e3e\u9879\uff0c\u6216\u8005\u627e\u5230\u7684\u679a\u4e3e\u9879\u4e0d\u7b26\u5408\u6761\u4ef6\u3002\u9519\u8bef\u503c:" + dataStr;
                this.crudLogger.dataCheckFail(message);
                return this.errRes(dataStr, message);
            }
            if (!single) continue;
            break;
        }
        return null;
    }
}

