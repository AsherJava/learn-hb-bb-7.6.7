/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.formula.intf.TableFieldNode
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.util.ObjectUtils;

public class ModelNode
extends DynamicNode
implements IAssignable,
TableFieldNode {
    private static final long serialVersionUID = 8893338282455473543L;
    public final DataTableDefine tableDefine;
    public final DataFieldDefine fieldDefine;
    private int dataType = 0;
    private boolean removeRepeat = true;
    private BaseDataClient baseDataClient;
    private OrgDataClient orgDataClient;

    public ModelNode(Token token, DataTableDefine tableDefine, DataFieldDefine fieldDefine) {
        super(token);
        this.tableDefine = tableDefine;
        this.fieldDefine = fieldDefine;
    }

    private BaseDataClient getBaseDataClient() {
        if (this.baseDataClient == null) {
            return (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        }
        return this.baseDataClient;
    }

    private OrgDataClient getOrgDataClient() {
        if (this.orgDataClient == null) {
            return (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        }
        return this.orgDataClient;
    }

    public boolean isMultiChoice() {
        return this.fieldDefine.isMultiChoice();
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        ModelDataContext dataContext = (ModelDataContext)context;
        DataRow dataRow = Convert.cast(this.getRow(dataContext), DataRow.class);
        int type = this.getType(context);
        Object v = dataContext.analyticalValue(value, type);
        Model model = dataContext.model;
        if (value instanceof ArrayData) {
            if (!this.fieldDefine.isMultiChoice()) {
                if (DataFieldType.CALC.equals((Object)this.fieldDefine.getFieldType())) {
                    ArrayList<Object> list = new ArrayList<Object>();
                    for (int i = 0; i < ((ArrayData)value).size(); ++i) {
                        list.add(((ArrayData)value).get(i));
                    }
                    if (dataRow != null) {
                        dataRow.setCalcValue(this.fieldDefine.getName(), list);
                    }
                    return 1;
                }
                throw new SyntaxException(BizBindingI18nUtil.getMessage("va.bizbinding.modelnode.assignmenttypenotmatch"));
            }
            ArrayData arrayData = (ArrayData)value;
            ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0; i < arrayData.size(); ++i) {
                Map<String, Object> map;
                Object fieldValue = arrayData.get(i);
                if (ObjectUtils.isEmpty(fieldValue) || (map = this.checkBaseData(model, dataRow, fieldValue)) == null) continue;
                if (this.fieldDefine.getRefTableType() == 1) {
                    list.add(map.get("objectcode"));
                    continue;
                }
                list.add(fieldValue);
            }
            dataRow.setValue(this.fieldDefine.getName(), list);
            return 1;
        }
        if (!ObjectUtils.isEmpty(v) && this.fieldDefine.getRefTableType() != 0) {
            Map<String, Object> map = this.checkBaseData(model, dataRow, v);
            if (map == null) {
                v = null;
            } else if (this.fieldDefine.getRefTableType() == 1) {
                v = map.get("objectcode");
            }
        }
        if (this.fieldDefine.getName().startsWith("CALC_$CMP_") && v instanceof Calendar && type == 0) {
            ModelContextImpl modelContext = (ModelContextImpl)((ModelDataContext)context).model.getContext();
            Object contextValue = modelContext.getContextValue("X--computeDateTimeFields");
            if (contextValue == null) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(this.fieldDefine.getName());
                modelContext.setContextValue("X--computeDateTimeFields", list);
            } else {
                List list = Convert.cast(contextValue, List.class);
                if (!list.contains(this.fieldDefine.getName())) {
                    list.add(this.fieldDefine.getName());
                }
            }
        }
        dataRow.setValue(this.fieldDefine.getName(), v);
        return 1;
    }

    private Map<String, Object> checkBaseData(Model model, DataRow dataRow, Object fieldValue) {
        Object authMark;
        Map<String, Object> refValue;
        Map<String, Object> dimValues = model.getDimValues(this.fieldDefine, dataRow);
        String mdControl = ((DataFieldDefineImpl)this.fieldDefine).getMdControl();
        boolean leafFlag = "ALL".equals(mdControl);
        if (mdControl != null && !leafFlag) {
            dimValues.put("LEAFFLAG", true);
        }
        if (this.fieldDefine.getRefTableType() == 1) {
            refValue = this.queryBaseData(Convert.cast(fieldValue, String.class), this.fieldDefine, dimValues);
        } else if (this.fieldDefine.getRefTableType() == 4) {
            refValue = this.queryOrgData(Convert.cast(fieldValue, String.class), this.fieldDefine.getRefTableName(), dimValues);
        } else {
            RefTableDataMap refTableMap = model.getRefDataBuffer().getRefTableMap(this.fieldDefine.getRefTableType(), this.fieldDefine.getRefTableName(), dimValues);
            refValue = refTableMap.find(Convert.cast(fieldValue, String.class));
        }
        if (refValue != null && !leafFlag) {
            if ("ONLYLEAF".equals(mdControl) && !Convert.cast(refValue.get("isLeaf"), Boolean.TYPE).booleanValue()) {
                return null;
            }
            if ("ONLYNOTLEAF".equals(mdControl) && Convert.cast(refValue.get("isLeaf"), Boolean.TYPE).booleanValue()) {
                return null;
            }
        }
        if (refValue == null) {
            return null;
        }
        if (!((DataFieldDefineImpl)this.fieldDefine).isIgnorePermission() && (authMark = refValue.get("authMark")) != null && !Convert.cast(authMark, Boolean.TYPE).booleanValue()) {
            return null;
        }
        return refValue;
    }

    private Map<String, Object> queryBaseData(String objectcode, DataFieldDefine fieldDefine, Map<String, Object> dimValues) {
        String tableName = fieldDefine.getRefTableName();
        BaseDataDTO baseDataDTO = this.setBaseDataDTO(objectcode, tableName, dimValues, fieldDefine.isQueryStop());
        PageVO pageVO = this.getBaseDataClient().list(baseDataDTO);
        if (pageVO.getRows().size() == 0) {
            BaseDataDTO newBaseDataDTO = this.setBaseDataDTO(objectcode, tableName, dimValues, fieldDefine.isQueryStop());
            newBaseDataDTO.setObjectcode(null);
            newBaseDataDTO.setCode(objectcode);
            pageVO = this.getBaseDataClient().list(newBaseDataDTO);
            if (pageVO.getRows().size() == 1) {
                HashMap map = new HashMap((Map)pageVO.getRows().get(0));
                return Collections.unmodifiableMap(map);
            }
            return null;
        }
        HashMap map = new HashMap((Map)pageVO.getRows().get(0));
        return Collections.unmodifiableMap(map);
    }

    private BaseDataDTO setBaseDataDTO(String objectcode, String tableName, Map<String, Object> dimValues, boolean isQueryStop) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setObjectcode(objectcode);
        basedataDTO.setTableName(tableName);
        basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) continue;
            basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
        basedataDTO.setAuthType(((DataFieldDefineImpl)this.fieldDefine).isIgnorePermission() ? BaseDataOption.AuthType.NONE : BaseDataOption.AuthType.ACCESS);
        if (dimValues.containsKey("LEAFFLAG")) {
            basedataDTO.setLeafFlag(Boolean.valueOf(true));
        }
        if (Utils.isEmpty(basedataDTO.getTenantName())) {
            basedataDTO.setTenantName(Env.getTenantName());
        }
        if (isQueryStop) {
            basedataDTO.setStopflag(Integer.valueOf(-1));
        }
        return basedataDTO;
    }

    private Map<String, Object> queryOrgData(String objectcode, String tableName, Map<String, Object> dimValues) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCode(objectcode);
        orgDTO.setCategoryname(tableName);
        orgDTO.setTenantName(Env.getTenantName());
        orgDTO.setAuthType(((DataFieldDefineImpl)this.fieldDefine).isIgnorePermission() ? OrgDataOption.AuthType.NONE : OrgDataOption.AuthType.ACCESS);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
        if (dimValues.containsKey("LEAFFLAG")) {
            orgDTO.setLeafFlag(Boolean.valueOf(true));
        }
        orgDTO.setStopflag(Integer.valueOf(-1));
        PageVO pageVO = this.getOrgDataClient().list(orgDTO);
        if (pageVO.getRows().size() == 0) {
            return null;
        }
        HashMap map = new HashMap((Map)pageVO.getRows().get(0));
        return Collections.unmodifiableMap(map);
    }

    public int getType(IContext context) throws SyntaxException {
        if (this.dataType != 0) {
            return this.dataType;
        }
        if ("CREATEUSER".equals(this.fieldDefine.getName())) {
            return 0;
        }
        if (this.fieldDefine.isMultiChoice() && (this.fieldDefine.getRefTableType() == 1 || this.fieldDefine.getRefTableType() == 4)) {
            return 11;
        }
        return ((ModelDataContext)context).getType(this.fieldDefine.getValueType());
    }

    public Object evaluate(IContext context) throws SyntaxException {
        ModelDataContext dataContext = (ModelDataContext)context;
        Object rowObject = this.getRow(dataContext);
        int dataType = this.getType((IContext)dataContext);
        if (rowObject instanceof DataRow) {
            List<String> value = null;
            DataRowImpl dataRow = (DataRowImpl)rowObject;
            if (this.fieldDefine.getRefTableType() != 0 && this.fieldDefine.getRefTableType() != 3 && this.fieldDefine.getValueType() == ValueType.IDENTIFY || this.fieldDefine.getRefTableType() == 3 && this.fieldDefine.isMultiChoiceStore()) {
                value = dataRow.getMultiValue(this.fieldDefine.getName());
                if (value == null) {
                    return null;
                }
                return dataContext.valueOf(value, 6);
            }
            value = dataRow.getValue(this.fieldDefine.getName());
            if (value == null) {
                return null;
            }
            return dataContext.valueOf(value, dataType);
        }
        if (rowObject instanceof Map) {
            Object value = ((Map)rowObject).get(this.fieldDefine.getName());
            if (value == null) {
                return null;
            }
            return dataContext.valueOf(value, dataType);
        }
        HashSet<Object> uniqueResults = new HashSet<Object>();
        ArrayList<Object> results = new ArrayList<Object>();
        List rowDatas = (List)rowObject;
        boolean isMuilt = this.fieldDefine.getRefTableType() != 0 && this.fieldDefine.getRefTableType() != 3 && this.fieldDefine.getValueType() == ValueType.IDENTIFY || this.fieldDefine.getRefTableType() == 3 && this.fieldDefine.isMultiChoiceStore();
        for (DataRowImpl rowData : rowDatas) {
            Object value = isMuilt ? rowData.getMultiValue(this.fieldDefine.getName()) : rowData.getValue(this.fieldDefine.getName());
            if (value == null) continue;
            if (this.removeRepeat) {
                uniqueResults.add(dataContext.valueOf(value, dataType));
                continue;
            }
            results.add(dataContext.valueOf(value, dataType));
        }
        this.dataType = 11;
        return dataContext.valueOf(this.removeRepeat ? Arrays.asList(uniqueResults.toArray()) : results, dataType);
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("%s[%s]", this.tableDefine.getName(), this.fieldDefine.getName()));
    }

    private Object getRow(ModelDataContext dataContext) {
        String tableName = this.tableDefine.getName();
        if (dataContext.get(tableName) == null) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.refdatanode.currrowempty", new Object[]{this.toString()}));
        }
        return dataContext.get(tableName);
    }

    public String getTableName() {
        return this.tableDefine.getName();
    }

    public String getFieldName() {
        return this.fieldDefine.getName();
    }

    public void setRemoveRepeat(boolean removeRepeat) {
        this.removeRepeat = removeRepeat;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        Object value;
        int type;
        ModelDataContext dataContext = (ModelDataContext)context;
        if (dataContext.get(this.tableDefine.getTableName()) == null) {
            buffer.append(this.tableDefine.getTableName());
            buffer.append('.');
            buffer.append(this.fieldDefine.getFieldName());
            return;
        }
        try {
            type = this.getType(context);
            value = this.evaluate(context);
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
        if (type == 33) {
            type = 6;
            value = Convert.cast(value, String.class);
        }
        DataNode.toSQL((StringBuilder)buffer, (int)type, (Object)value, (ISQLInfo)info);
    }
}

