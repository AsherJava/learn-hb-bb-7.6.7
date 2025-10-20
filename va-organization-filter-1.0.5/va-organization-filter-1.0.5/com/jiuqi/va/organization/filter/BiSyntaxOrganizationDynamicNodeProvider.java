/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilterContext;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class BiSyntaxOrganizationDynamicNodeProvider
implements IDynamicNodeProvider {
    @Autowired
    private DataModelClient dataModelClient;

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        Object obj;
        Assert.hasText(refName, "\u65e0\u6cd5\u5e94\u7528\u8bed\u6cd5\u89e3\u6790\u5668\u63d0\u4f9b\u7684\u53c2\u6570\u3002");
        Assert.isInstanceOf(BiSyntaxOrganizationFilterContext.class, (Object)context, "\u65e0\u6cd5\u8bc6\u522b\u4e0a\u4e0b\u6587\u3002");
        String tableCode = ((BiSyntaxOrganizationFilterContext)context).getParam().getCategoryname();
        DataModelDTO dataModelParam = new DataModelDTO();
        dataModelParam.setName(tableCode);
        DataModelDO dataModel = this.dataModelClient.get(dataModelParam);
        if (dataModel == null) {
            return null;
        }
        DataModelColumn field = null;
        for (DataModelColumn dataModelColumn : dataModel.getColumns()) {
            if (!dataModelColumn.getColumnName().equalsIgnoreCase(refName)) continue;
            field = dataModelColumn;
            break;
        }
        if (field == null) {
            return null;
        }
        if (StringUtils.hasText(field.getMapping()) && field.getMappingType() == 1 && (obj = dataModel.getExtInfo("multipleColumn")) != null) {
            Collection multipleColumn = null;
            multipleColumn = obj instanceof Collection ? (Collection)obj : (Collection)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)obj), Collection.class);
            if (multipleColumn != null && multipleColumn.contains(field.getColumnName())) {
                return new BiSyntaxOrganizationNode(token, refName.toLowerCase(Locale.ROOT), 6, true);
            }
        }
        int colType = 0;
        DataModelType.ColumnType columnType = field.getColumnType();
        if (columnType == DataModelType.ColumnType.UUID) {
            colType = 6;
        } else if (columnType == DataModelType.ColumnType.NVARCHAR) {
            colType = 6;
        } else if (columnType == DataModelType.ColumnType.DATE) {
            colType = 2;
        } else if (columnType == DataModelType.ColumnType.TIMESTAMP) {
            colType = 2;
        } else if (columnType == DataModelType.ColumnType.CLOB) {
            colType = 12;
        } else if (columnType == DataModelType.ColumnType.INTEGER) {
            colType = 3;
        } else if (columnType == DataModelType.ColumnType.NUMERIC) {
            colType = 10;
        }
        return new BiSyntaxOrganizationNode(token, refName.toLowerCase(Locale.ROOT), colType);
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        Assert.isTrue(objPath != null && objPath.size() >= 2, "\u65e0\u6cd5\u5e94\u7528\u8bed\u6cd5\u89e3\u6790\u5668\u63d0\u4f9b\u7684\u53c2\u6570\u3002");
        return this.find(context, token, objPath.get(1));
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }
}

