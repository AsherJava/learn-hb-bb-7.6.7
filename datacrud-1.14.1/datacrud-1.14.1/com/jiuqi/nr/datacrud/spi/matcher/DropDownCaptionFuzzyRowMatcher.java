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
@Order(value=60)
public class DropDownCaptionFuzzyRowMatcher
implements IEntityRowMatcher {
    @Override
    public IEntityRow match(MatchSource matchSource, MatchValue matchValue) {
        String nameValue;
        int nameIndex;
        List<IEntityRow> allRows;
        String[] paths;
        String codeValue;
        int codeIndex;
        String entityKey;
        if (matchSource.isEntityMatchAll()) {
            return null;
        }
        List<String> dropDownCaptionCodes = matchSource.getDropDownCaptionCodes();
        List<String> innerValues = matchValue.getInnerValues();
        if (innerValues.size() != dropDownCaptionCodes.size()) {
            return null;
        }
        String enumShowFullPath = matchSource.getEnumShowFullPath();
        IEntityModel entityModel = matchSource.getEntityModel();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute codeField = entityModel.getCodeField();
        IEntityAttribute nameField = entityModel.getNameField();
        IEntityTableWrapper entityTableWrapper = matchSource.getEntityTableWrapper();
        IEntityRow entityRow = null;
        int bizKeyIndex = dropDownCaptionCodes.indexOf(bizKeyField.getCode());
        if (bizKeyIndex >= 0 && StringUtils.hasLength(entityKey = innerValues.get(bizKeyIndex))) {
            if (bizKeyField.getCode().equals(enumShowFullPath)) {
                String[] paths2 = entityKey.split("/");
                entityKey = paths2[paths2.length - 1];
            }
            entityKey = entityKey.toUpperCase();
            List<IEntityRow> allRows2 = entityTableWrapper.getAllRows();
            for (IEntityRow row : allRows2) {
                if (!row.getEntityKeyData().contains(entityKey)) continue;
                entityRow = row;
                break;
            }
        }
        if (entityRow == null && (codeIndex = dropDownCaptionCodes.indexOf(codeField.getCode())) >= 0 && StringUtils.hasLength(codeValue = innerValues.get(codeIndex))) {
            if (codeField.getCode().equals(enumShowFullPath)) {
                paths = codeValue.split("/");
                codeValue = paths[paths.length - 1];
            }
            codeValue = codeValue.toUpperCase();
            allRows = entityTableWrapper.getAllRows();
            for (IEntityRow row : allRows) {
                if (!row.getCode().contains(codeValue)) continue;
                entityRow = row;
                break;
            }
        }
        if (entityRow == null && (nameIndex = dropDownCaptionCodes.indexOf(nameField.getCode())) > 0 && StringUtils.hasLength(nameValue = innerValues.get(nameIndex))) {
            if (nameField.getCode().equals(enumShowFullPath)) {
                paths = nameValue.split("/");
                nameValue = paths[paths.length - 1];
            }
            nameValue = nameValue.toUpperCase();
            allRows = entityTableWrapper.getAllRows();
            for (IEntityRow row : allRows) {
                if (!row.getTitle().contains(nameValue)) continue;
                entityRow = row;
                break;
            }
        }
        return entityRow;
    }
}

