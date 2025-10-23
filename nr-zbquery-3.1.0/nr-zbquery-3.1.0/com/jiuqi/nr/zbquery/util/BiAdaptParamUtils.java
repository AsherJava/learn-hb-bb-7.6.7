/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bql.interpret.BiAdaptParam
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.zbquery.model.FieldGroup;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.QueryLayout;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.util.FullNameWrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class BiAdaptParamUtils {
    public static void buildParam(BiAdaptParam biAdaptParam, ZBQueryModel zbQueryModel) {
        BiAdaptParamUtils.buildSelectedFieldStrList(biAdaptParam, zbQueryModel);
    }

    public static void buildParamOfLayout(BiAdaptParam biAdaptParam, ZBQueryModel zbQueryModel) {
        QueryLayout queryLayout = zbQueryModel.getLayout();
        if (!(queryLayout == null || CollectionUtils.isEmpty(queryLayout.getCols()) && CollectionUtils.isEmpty(queryLayout.getRows()))) {
            HashMap<String, QueryObject> queryObjectMap = new HashMap<String, QueryObject>();
            BiAdaptParamUtils.buildSelectedFieldMap(queryObjectMap, zbQueryModel.getQueryObjects());
            BiAdaptParamUtils.buildLayoutFieldStrList(biAdaptParam, queryLayout.getCols(), queryObjectMap);
            BiAdaptParamUtils.buildLayoutFieldStrList(biAdaptParam, queryLayout.getRows(), queryObjectMap);
        }
    }

    private static void buildSelectedFieldStrList(BiAdaptParam biAdaptParam, ZBQueryModel zbQueryModel) {
        List<QueryObject> queryObjects = zbQueryModel.getQueryObjects();
        if (!CollectionUtils.isEmpty(queryObjects)) {
            queryObjects.forEach(object -> BiAdaptParamUtils.putOneSelectedFieldStr(object, biAdaptParam.getSelectedFields()));
        }
    }

    private static void putOneSelectedFieldStr(QueryObject queryObject, List<String> selectedFields) {
        List<QueryObject> children;
        QueryObjectType type = queryObject.getType();
        if (QueryObjectType.ZB.equals((Object)type) || QueryObjectType.DIMENSIONATTRIBUTE.equals((Object)type)) {
            selectedFields.add(BiAdaptParamUtils.buildOneFieldStr(queryObject.getType(), queryObject.getFullName()));
        } else if (QueryObjectType.GROUP.equals((Object)type) && !CollectionUtils.isEmpty(children = ((FieldGroup)queryObject).getChildren())) {
            children.forEach(child -> BiAdaptParamUtils.putOneSelectedFieldStr(child, selectedFields));
        }
    }

    private static String buildOneFieldStr(QueryObjectType type, String fullName) {
        FullNameWrapper fullNameWrapper = new FullNameWrapper(type, fullName);
        return fullNameWrapper.getQueryName();
    }

    private static void buildSelectedFieldMap(Map<String, QueryObject> queryObjectMap, List<QueryObject> queryObjects) {
        queryObjects.forEach(queryObject -> {
            queryObjectMap.put(queryObject.getFullName(), (QueryObject)queryObject);
            if (QueryObjectType.GROUP.equals((Object)queryObject.getType())) {
                BiAdaptParamUtils.buildSelectedFieldMap(queryObjectMap, ((FieldGroup)queryObject).getChildren());
            }
        });
    }

    private static void buildLayoutFieldStrList(BiAdaptParam biAdaptParam, List<LayoutField> layoutFields, Map<String, QueryObject> queryObjectMap) {
        if (!CollectionUtils.isEmpty(layoutFields)) {
            List layoutFieldParams = biAdaptParam.getSelectedFields();
            layoutFields.forEach(layoutField -> BiAdaptParamUtils.putOneLayoutFieldStrList((QueryObject)queryObjectMap.get(layoutField.getFullName()), layoutFieldParams));
        }
    }

    private static void putOneLayoutFieldStrList(QueryObject queryObject, List<String> selectedFields) {
        if (queryObject.isVisible()) {
            List<QueryObject> children;
            QueryObjectType type = queryObject.getType();
            if (QueryObjectType.ZB.equals((Object)type) || QueryObjectType.DIMENSIONATTRIBUTE.equals((Object)type)) {
                selectedFields.add(BiAdaptParamUtils.buildOneFieldStr(queryObject.getType(), queryObject.getFullName()));
            } else if (QueryObjectType.GROUP.equals((Object)type) && !CollectionUtils.isEmpty(children = ((FieldGroup)queryObject).getChildren())) {
                children.forEach(child -> BiAdaptParamUtils.putOneLayoutFieldStrList(child, selectedFields));
            }
        }
    }
}

