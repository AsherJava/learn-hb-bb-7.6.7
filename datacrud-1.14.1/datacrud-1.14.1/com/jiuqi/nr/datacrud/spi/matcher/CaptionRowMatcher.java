/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 */
package com.jiuqi.nr.datacrud.spi.matcher;

import com.jiuqi.nr.datacrud.spi.IEntityRowMatcher;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.datacrud.spi.entity.MatchValue;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import java.util.List;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(value=10)
public class CaptionRowMatcher
implements IEntityRowMatcher {
    @Override
    public IEntityRow match(MatchSource matchSource, MatchValue matchValue) {
        String nameValue;
        int nameIndex;
        String codeValue;
        int codeIndex;
        String entityKey;
        List<String> captionCodes = matchSource.getCaptionCodes();
        List<String> innerValues = matchValue.getInnerValues();
        if (innerValues.size() != captionCodes.size()) {
            return null;
        }
        String enumShowFullPath = matchSource.getEnumShowFullPath();
        IEntityModel entityModel = matchSource.getEntityModel();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute nameField = entityModel.getNameField();
        int bizKeyIndex = captionCodes.indexOf(bizKeyField.getCode());
        IEntityTableWrapper entityTableWrapper = matchSource.getEntityTableWrapper();
        IEntityRow entityRow = null;
        if (bizKeyIndex >= 0 && StringUtils.hasLength(entityKey = innerValues.get(bizKeyIndex))) {
            if (bizKeyField.getCode().equals(enumShowFullPath)) {
                String[] paths = entityKey.split("/");
                entityKey = paths[paths.length - 1];
            }
            if ((entityRow = entityTableWrapper.findByEntityKey(entityKey)) != null) {
                return entityRow;
            }
        }
        if ((codeIndex = captionCodes.indexOf(codeField.getCode())) >= 0 && StringUtils.hasLength(codeValue = innerValues.get(codeIndex))) {
            if (codeField.getCode().equals(enumShowFullPath)) {
                String[] paths = codeValue.split("/");
                codeValue = paths[paths.length - 1];
            }
            if ((entityRow = entityTableWrapper.findByCode(codeValue)) != null) {
                return entityRow;
            }
        }
        if ((nameIndex = captionCodes.indexOf(nameField.getCode())) >= 0 && StringUtils.hasLength(nameValue = innerValues.get(nameIndex))) {
            if (nameField.getCode().equals(enumShowFullPath)) {
                String[] paths = nameValue.split("/");
                nameValue = paths[paths.length - 1];
            }
            if ((entityRow = this.matchEntityRow(entityTableWrapper, nameField.getCode(), nameValue)) != null) {
                return entityRow;
            }
        }
        for (int i = 0; i < captionCodes.size(); ++i) {
            String attrCode = captionCodes.get(i);
            if (bizKeyField.getCode().equals(attrCode) || codeField.getCode().equals(attrCode) || nameField.getCode().equals(attrCode) || (entityRow = this.matchEntityRow(entityTableWrapper, attrCode, innerValues.get(i))) == null) continue;
            return entityRow;
        }
        return entityRow;
    }

    private IEntityRow matchEntityRow(IEntityTableWrapper entityTableWrapper, String attrCode, String attrValue) {
        List<IEntityRow> allRows = entityTableWrapper.getAllRows();
        if (attrValue == null) {
            attrValue = "";
        }
        for (IEntityRow row : allRows) {
            if (!attrValue.equals(row.getAsString(attrCode))) continue;
            return row;
        }
        return null;
    }
}

