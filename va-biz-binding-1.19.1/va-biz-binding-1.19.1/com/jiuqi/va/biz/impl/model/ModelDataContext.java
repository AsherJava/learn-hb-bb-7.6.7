/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.va.biz.impl.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.va.biz.impl.model.FormulaContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.ref.RefDataBuffer;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

public class ModelDataContext
extends FormulaContext {
    public final Model model;
    public final ModelDefine modelDefine;
    private Map<String, Object> params = null;
    private Map<String, ValueType> valueTypes = null;
    public static final String MODLE_PARAM_NODE_TYPE = "model_param_node_type";
    public static final String MODLE_PARAM_NODE_TABLENAME = "model_param_node_tablename";

    public ModelDataContext(Model model) {
        this.model = model;
        this.modelDefine = model.getDefine();
    }

    @Override
    public String getTenantName() {
        if (this.model == null) {
            return super.getTenantName();
        }
        String tenantName = this.model.getContext().getTenantName();
        if (!StringUtils.hasText(tenantName)) {
            return super.getTenantName();
        }
        return tenantName;
    }

    public ModelDataContext(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
        this.model = null;
    }

    public void put(String paramKey, Object paramValue) {
        if (this.params == null) {
            this.params = new HashMap<String, Object>();
        }
        this.params.put(paramKey, paramValue);
    }

    public Object get(String paramKey) {
        if (this.params == null) {
            return null;
        }
        return this.params.get(paramKey);
    }

    public void setFieldValueType(String paramKey, ValueType valueType) {
        if (this.valueTypes == null) {
            this.valueTypes = new HashMap<String, ValueType>();
        }
        this.valueTypes.put(paramKey, valueType);
    }

    public ValueType getFieldValueType(String paramKey) {
        if (this.valueTypes == null) {
            return null;
        }
        return this.valueTypes.get(paramKey);
    }

    public boolean hasKey(String key) {
        if (this.params == null) {
            return false;
        }
        return this.params.containsKey(key);
    }

    public int getType(ValueType valueType) throws SyntaxException {
        switch (valueType) {
            case AUTO: {
                return 0;
            }
            case BINARY: {
                throw new SyntaxException(BizBindingI18nUtil.getMessage("va.bizbinding.dataimpl.loadsubtabledatafailed") + "BINARY");
            }
            case BOOLEAN: {
                return 1;
            }
            case BYTE: {
                return 3;
            }
            case DATE: {
                return 2;
            }
            case DATETIME: {
                return 2;
            }
            case DECIMAL: {
                return 10;
            }
            case DOUBLE: {
                return 3;
            }
            case IDENTIFY: {
                return 33;
            }
            case INTEGER: {
                return 3;
            }
            case LONG: {
                return 3;
            }
            case SHORT: {
                return 3;
            }
            case STRING: {
                return 6;
            }
            case TIME: {
                return 2;
            }
        }
        return -1;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    @Override
    public RefDataBuffer getRefDataBuffer() {
        return this.model.getRefDataBuffer();
    }

    @Override
    public String getUnitCode() {
        Data data = this.model.getPlugins().get(Data.class);
        ListContainer<? extends DataRow> master = data.getMasterTable().getRows();
        if (master.size() == 0) {
            return this.model.getContext().getUnitCode();
        }
        return master.get(0).getString("UNITCODE");
    }

    @Override
    public Date getBizDate() {
        Data data = this.model.getPlugins().get(Data.class);
        ListContainer<? extends DataRow> master = data.getMasterTable().getRows();
        if (master.size() == 0) {
            return this.model.getContext().getBizDate();
        }
        return master.get(0).getDate("BILLDATE");
    }
}

