/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.expression.filter.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.nr.expression.filter.parse.EntityNode;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class EntityNodeProvider {
    @Autowired
    private DataModelService dataModelService;

    public IASTNode find(Token token, String fieldName, TableModelDefine table) {
        Assert.hasText(fieldName, "\u65e0\u6cd5\u5e94\u7528\u8bed\u6cd5\u89e3\u6790\u5668\u63d0\u4f9b\u7684\u53c2\u6570\u3002");
        Assert.isTrue(table != null, "\u5b58\u50a8\u8868\u4e3a\u7a7a\u3002");
        ColumnModelDefine field = this.dataModelService.getColumnModelDefineByCode(table.getID(), fieldName);
        if (field == null) {
            return null;
        }
        if (field.isMultival() && StringUtils.hasText(field.getReferColumnID())) {
            return new EntityNode(token, fieldName, field.getColumnType().getValue(), true);
        }
        int type = field.getColumnType().getValue();
        if (type == 5) {
            type = 3;
        }
        return new EntityNode(token, fieldName, type);
    }
}

