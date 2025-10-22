/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.Consts$EnumField
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.designer.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.designer.bean.BaseDataImplDeserializer;
import com.jiuqi.nr.designer.bean.BaseDataImplSerializer;
import com.jiuqi.nr.designer.bean.DataFormatFactory;
import com.jiuqi.nr.designer.bean.DicStringUtils;
import com.jiuqi.nr.designer.bean.EnumConstants;
import com.jiuqi.nr.designer.bean.IBaseData;
import com.jiuqi.nr.designer.bean.ImportPropVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@JsonSerialize(using=BaseDataImplSerializer.class)
@JsonDeserialize(using=BaseDataImplDeserializer.class)
public class BaseDataImpl
implements IBaseData,
Serializable {
    public static final String TARGET_CODE_ID = "id";
    public static final String TARGET_CODE_KEY = "key";
    public static final String TARGET_CODE_CODE = "code";
    public static final String TARGET_CODE_TITLE = "title";
    public static final String TARGET_CODE_ORDINAL = "ordinal";
    public static final String TARGET_CODE_PARENTID = "parentid";
    public static final String TARGET_CODE_LEAF = "leaf";
    public static final String TARGET_CODE_PARENTS = "parents";
    public static final String TARGET_CODE_BIZKEY = "bizkey";
    public static final String TARGET_CODE_REFS = "refs";
    private static final long serialVersionUID = 4570100977940238706L;
    private String id;
    private String key;
    private String code;
    private String title;
    private String ordinal;
    private String parentid;
    private boolean leaf = true;
    private String[] parents;
    private Map<String, Object> valueMap = new HashMap<String, Object>();
    private Map<String, String> indexMap = new HashMap<String, String>();

    public BaseDataImpl() {
    }

    public BaseDataImpl(String id, String code, String title, String ordinal, String parentid, boolean leaf) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.ordinal = ordinal;
        this.parentid = parentid;
        this.leaf = leaf;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String getParentid() {
        return this.parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public boolean isLeaf() {
        return this.leaf;
    }

    @Override
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    @Override
    public String[] getParents() {
        return this.parents;
    }

    public void setParents(String[] parents) {
        this.parents = parents;
    }

    @Override
    public List<InternalRefs> getRefs() {
        Object ref = this.valueMap.get(TARGET_CODE_REFS);
        return ref == null ? null : (List)ref;
    }

    @Override
    public void setRefs(List<InternalRefs> refs) {
        List old = new ArrayList();
        Object refObj = this.valueMap.get(TARGET_CODE_REFS);
        if (!"".equals(refObj) && refObj != null) {
            old = (List)refObj;
            for (InternalRefs ref : old) {
                refs.add(ref);
            }
        }
        this.valueMap.put(TARGET_CODE_REFS, refs);
    }

    @Override
    public Map<String, Object> getValueMap() {
        return this.valueMap;
    }

    @Override
    public Object getFieldValue(String key) {
        return this.valueMap.get(key);
    }

    @Override
    public void setFieldValue(String key, Object title) {
        if (key == null) {
            throw new RuntimeException("\u952e\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        this.valueMap.put(key, title);
    }

    @Override
    public void addIndex(String index, String value) {
        this.indexMap.put(value, index);
    }

    @Override
    public String getIndex(String value) {
        return this.indexMap.get(value);
    }

    public static BaseDataImpl buildDicItem(IEntityRow row) {
        if (row == null) {
            return null;
        }
        BaseDataImpl impl = new BaseDataImpl();
        impl.id = row.getRecKey();
        impl.key = row.getRecKey();
        impl.title = row.getTitle();
        if (row.getCode() != null) {
            impl.code = row.getCode();
        }
        impl.parents = row.getParentsEntityKeyDataPath();
        impl.setFieldValue(TARGET_CODE_BIZKEY, row.getEntityKeyData());
        String parentKey = row.getParentEntityKey();
        if (StringUtils.isNotEmpty((String)parentKey) && !"VOID".equals(parentKey)) {
            impl.parentid = parentKey;
        }
        IFieldsInfo fields = row.getFieldsInfo();
        int fieldCount = fields.getFieldCount();
        for (int i = 0; i < fieldCount; ++i) {
            Object obj;
            String fieldCode;
            block7: {
                FieldDefine field = fields.getFieldDefine(i);
                if (field == null) continue;
                fieldCode = field.getCode();
                obj = null;
                try {
                    AbstractData rowData = row.getValue(field);
                    if (rowData.isNull) break block7;
                    obj = DataFormatFactory.formatData(field.getType(), rowData.getAsObject());
                }
                catch (Exception e) {
                    continue;
                }
            }
            if (fieldCode.equals(Consts.EnumField.ENUM_FIELD_ORDER.code)) {
                impl.ordinal = obj == null ? null : obj.toString();
                continue;
            }
            impl.setFieldValue(fieldCode.toLowerCase(), obj);
        }
        return impl;
    }

    public static BaseDataImpl buildBaseData(String[] row, List<ImportPropVO.TitleMapping> titleMapping, Integer rowIndex) {
        BaseDataImpl impl = new BaseDataImpl();
        for (ImportPropVO.TitleMapping title : titleMapping) {
            String value = row[title.getIndex()];
            impl.addIndex(DicStringUtils.buildExcelIndex(title.getIndex(), rowIndex), DicStringUtils.buildExcelValue(value, title.getCode()));
            if (title.getCode().equals(Consts.EnumField.ENUM_FIELD_CODE.code.toLowerCase())) {
                impl.setCode(value);
                continue;
            }
            if (title.getCode().equals(Consts.EnumField.ENUM_FIELD_TITLE.code.toLowerCase()) || title.getCode().equals(EnumConstants.HistoryField.ENUM_FIELD_TITLE.code.toLowerCase())) {
                impl.setTitle(value);
                impl.setFieldValue(title.getCode(), value);
                continue;
            }
            if (title.getCode().equals(Consts.EnumField.ENUM_FIELD_PARENT.code.toLowerCase()) || title.getCode().equals(EnumConstants.HistoryField.ENUM_FIELD_PARENT.code.toLowerCase())) {
                impl.setParentid(value);
                impl.setFieldValue(title.getCode(), value);
                continue;
            }
            if (title.getCode().equals(Consts.EnumField.ENUM_FIELD_ORDER.code.toLowerCase())) {
                impl.setOrdinal(value);
                continue;
            }
            impl.setFieldValue(title.getCode(), value);
        }
        return impl;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseDataImpl)) {
            return false;
        }
        BaseDataImpl baseData = (BaseDataImpl)o;
        return this.leaf == baseData.leaf && Objects.equals(this.id, baseData.id) && Objects.equals(this.key, baseData.key) && Objects.equals(this.code, baseData.code) && Objects.equals(this.title, baseData.title) && Objects.equals(this.ordinal, baseData.ordinal) && Objects.equals(this.parentid, baseData.parentid) && Arrays.equals(this.parents, baseData.parents) && Objects.equals(this.valueMap, baseData.valueMap);
    }

    public int hashCode() {
        int result = Objects.hash(this.id, this.key, this.code, this.title, this.ordinal, this.parentid, this.leaf, this.valueMap);
        result = 31 * result + Arrays.hashCode(this.parents);
        return result;
    }

    public class InternalRefs {
        private String code;
        private Map<String, String> value = new HashMap<String, String>();

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Map<String, String> getValue() {
            return this.value;
        }

        public void setValueMap(String key, String value) {
            this.value.put(key, value);
        }
    }
}

