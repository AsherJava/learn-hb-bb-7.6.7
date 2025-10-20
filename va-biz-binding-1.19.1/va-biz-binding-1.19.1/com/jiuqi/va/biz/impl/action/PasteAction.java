/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.biz.impl.action;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.cache.BaseDataDefineCache;
import com.jiuqi.va.biz.impl.action.ActionBase;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataField;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Component
public class PasteAction
extends ActionBase {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private OrgDataClient orgDataClient;

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getTitle() {
        return "\u7c98\u8d34";
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public boolean isInner() {
        return true;
    }

    @Override
    public void execute(Model model, Map<String, Object> params) {
        Map<UUID, Map<String, List<Formula>>> map = model.getDefine().getPlugins().get(RulerDefineImpl.class).getObjectFormulaMap().get("field");
        Map<String, Object> setted = Stream.of("$UNSET").collect(Collectors.toMap(o -> o, o -> false));
        DataTable table = this.getTable(model, params);
        int rowIndex = this.getRowIndex(table, params);
        Map values = (Map)params.get("values");
        List rows = (List)params.get("rows");
        Boolean isObjectCode = (Boolean)params.get("isObjectCode");
        for (int i = 0; i < rows.size(); ++i) {
            DataRow row;
            Map o2 = (Map)rows.get(i);
            if (values != null) {
                o2.putAll(values);
            }
            if (rowIndex + i < table.getRows().size()) {
                row = table.getRows().get(rowIndex + i);
            } else {
                Object groupId;
                Object masterId;
                HashMap<String, Object> keyValues = new HashMap<String, Object>();
                Object id = o2.remove("ID");
                if (id != null) {
                    keyValues.put("ID", id);
                }
                if ((masterId = o2.remove("MASTERID")) != null) {
                    keyValues.put("MASTERID", masterId);
                }
                if ((groupId = o2.remove("GROUPID")) != null) {
                    keyValues.put("GROUPID", groupId);
                }
                row = table.appendRow(keyValues);
                o2.put("ID", row.getId());
            }
            row.setData(setted);
            o2.forEach((k, v) -> {
                DataField field = table.getFields().find((String)k);
                if (field == null) {
                    return;
                }
                if (!(v instanceof String)) {
                    row.setValueWithCheck(field.getIndex(), null);
                    return;
                }
                String str = ((String)v).trim();
                if (str.length() == 0) {
                    row.setValueWithCheck(field.getIndex(), null);
                    return;
                }
                DataFieldDefineImpl fieldDefine = (DataFieldDefineImpl)field.getDefine();
                boolean ignorePermission = fieldDefine.isIgnorePermission();
                String mdControl = fieldDefine.getMdControl();
                boolean isOnlyLeaf = mdControl != null && mdControl.equals("ONLYLEAF");
                boolean isOnluNotLeaf = mdControl != null && mdControl.equals("ONLYNOTLEAF");
                int refTableType = fieldDefine.getRefTableType();
                String tableName = fieldDefine.getRefTableName();
                IExpression expression = this.getFilterExpression(map, fieldDefine);
                Map<String, Object> dimValues = model.getDimValues(fieldDefine, row);
                if (fieldDefine.isMultiChoiceStore()) {
                    String[] sValues = str.split(",");
                    List value = Stream.of(sValues).map(val -> this.handleRefTableData(model, table, row, field, (String)val, fieldDefine, ignorePermission, isOnlyLeaf, isOnluNotLeaf, refTableType, tableName, expression, dimValues, isObjectCode)).filter(val -> !ObjectUtils.isEmpty(val)).collect(Collectors.toList());
                    row.setValueWithCheck(field.getIndex(), value);
                } else if (refTableType != 0) {
                    Object value = this.handleRefTableData(model, table, row, field, str, fieldDefine, ignorePermission, isOnlyLeaf, isOnluNotLeaf, refTableType, tableName, expression, dimValues, isObjectCode);
                    row.setValueWithCheck(field.getIndex(), value);
                } else if (fieldDefine.getValueType() == ValueType.DECIMAL || fieldDefine.getValueType() == ValueType.DOUBLE || fieldDefine.getValueType() == ValueType.INTEGER) {
                    String value = str.replace(",", "");
                    row.setValueWithCheck(field.getIndex(), (Object)value);
                } else {
                    row.setValueWithCheck(field.getIndex(), (Object)str);
                }
            });
        }
    }

    private Object handleRefTableData(Model model, DataTable table, DataRow row, DataField field, String str, DataFieldDefineImpl fieldDefine, boolean ignorePermission, boolean isOnlyLeaf, boolean isOnluNotLeaf, int refTableType, String tableName, IExpression expression, Map<String, Object> dimValues, Boolean isObjectCode) {
        if (refTableType == 1) {
            String objectcode;
            List list;
            PageVO pageVO;
            BaseDataDTO basedataDTO = new BaseDataDTO();
            basedataDTO.setTableName(fieldDefine.getRefTableName());
            basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
            for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
                if (entry.getKey().equals("BIZDATE")) continue;
                basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            if (isObjectCode != null && isObjectCode.booleanValue()) {
                basedataDTO.setObjectcode(str);
            } else {
                basedataDTO.setName(str);
            }
            basedataDTO.setShowType(fieldDefine.getShowType());
            basedataDTO.setShowFullPath(Boolean.valueOf(fieldDefine.isShowFullPath()));
            if (ignorePermission) {
                basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
            }
            if (fieldDefine.isQueryStop()) {
                basedataDTO.setStopflag(Integer.valueOf(-1));
            }
            if ((pageVO = this.baseDataClient.list(new BaseDataDTO((Map)basedataDTO))).getRows().size() == 0) {
                basedataDTO.setName(null);
                basedataDTO.setCode(str);
                pageVO = this.baseDataClient.list(new BaseDataDTO((Map)basedataDTO));
                if (pageVO.getRows().size() == 1 && (list = this.searchBaseDataByObjectCode(basedataDTO, objectcode = ((BaseDataDO)pageVO.getRows().get(0)).getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf).getRows()).size() == 1) {
                    Map<String, Object> value = (Map<String, Object>)list.get(0);
                    if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                        return null;
                    }
                    value = this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName()) ? this.toViewValue(value) : null;
                    return value;
                }
            } else if (pageVO.getRows().size() == 1 && (list = this.searchBaseDataByObjectCode(basedataDTO, objectcode = ((BaseDataDO)pageVO.getRows().get(0)).getObjectcode(), fieldDefine, isOnluNotLeaf || isOnlyLeaf).getRows()).size() == 1) {
                Map<String, Object> value = (Map<String, Object>)list.get(0);
                if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                    return null;
                }
                value = this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName()) ? this.toViewValue(value) : null;
                return value;
            }
            if (!str.contains("/")) {
                String objectcode2;
                List list2;
                String[] codeTitle = str.split(" ");
                String code = codeTitle[0];
                basedataDTO = new BaseDataDTO();
                basedataDTO.setTableName(fieldDefine.getRefTableName());
                basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
                for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
                    if (entry.getKey().equals("BIZDATE")) continue;
                    basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
                }
                basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                basedataDTO.setCode(code);
                basedataDTO.setShowType(fieldDefine.getShowType());
                basedataDTO.setShowFullPath(Boolean.valueOf(fieldDefine.isShowFullPath()));
                if (ignorePermission) {
                    basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                }
                if (fieldDefine.isQueryStop()) {
                    basedataDTO.setStopflag(Integer.valueOf(-1));
                }
                if ((pageVO = this.baseDataClient.list(new BaseDataDTO((Map)basedataDTO))).getRows().size() == 1 && (list2 = this.searchBaseDataByObjectCode(basedataDTO, objectcode2 = ((BaseDataDO)pageVO.getRows().get(0)).getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf).getRows()).size() == 1) {
                    Map<String, Object> value = (Map<String, Object>)list2.get(0);
                    if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                        return null;
                    }
                    value = this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName()) ? this.toViewValue(value) : null;
                    return value;
                }
            } else {
                String text;
                String[] strs = str.split("/");
                Object value = this.seachBasedataByName(model, table, row, field, fieldDefine, expression, dimValues, strs, text = strs[strs.length - 1]);
                if (value != null) {
                    return value;
                }
                value = this.searchBasedataByCode(model, table, row, field, fieldDefine, expression, dimValues, strs, text);
                if (value != null) {
                    return value;
                }
                String[] codeTitle = text.split(" ");
                String code = codeTitle[0];
                value = this.searchBasedataByCode(model, table, row, field, fieldDefine, expression, dimValues, strs, code);
                if (value != null) {
                    return value;
                }
            }
        } else {
            RefTableDataMap refTableDataMap = model.getRefDataBuffer().getRefTableMap(refTableType, fieldDefine.getRefTableName(), dimValues);
            Map<String, Object> value = refTableDataMap.match(str);
            if (!this.checkMdControl(isOnlyLeaf, isOnluNotLeaf, value, tableName, refTableType, dimValues, ignorePermission)) {
                return null;
            }
            value = this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName()) ? refTableDataMap.toViewValue(fieldDefine.getShowType(), value) : null;
            return value;
        }
        return null;
    }

    private boolean checkMdControl(boolean isOnlyLeaf, boolean isOnluNotLeaf, Map<String, Object> value, String tableName, int refTableType, Map<String, Object> dimValues, boolean ignorePermission) {
        if (value == null) {
            return true;
        }
        if (refTableType == 4) {
            if (isOnlyLeaf) {
                Object isLeaf;
                Object object = isLeaf = value.get("isLeaf") == null ? this.getOrgDataIsLeaf(value, tableName, ignorePermission) : value.get("isLeaf");
                if (isLeaf == null) {
                    return false;
                }
                return (Boolean)isLeaf;
            }
            if (isOnluNotLeaf) {
                Object isLeaf;
                Object object = isLeaf = value.get("isLeaf") == null ? this.getOrgDataIsLeaf(value, tableName, ignorePermission) : value.get("isLeaf");
                if (isLeaf == null) {
                    return false;
                }
                return (Boolean)isLeaf == false;
            }
        }
        return true;
    }

    private Object getOrgDataIsLeaf(Map<String, Object> value, String tableName, boolean ignorePermission) {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(tableName);
        orgDTO.setStopflag(Integer.valueOf(-1));
        orgDTO.setCode(String.valueOf(value.get("code")));
        orgDTO.setTenantName(Env.getTenantName());
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setLeafFlag(Boolean.valueOf(true));
        if (ignorePermission) {
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        }
        PageVO pageVO = this.orgDataClient.list(orgDTO);
        OrgDO orgDO = (OrgDO)pageVO.getRows().get(0);
        return orgDO.get((Object)"isLeaf");
    }

    private PageVO<BaseDataDO> searchBaseDataByObjectCode(BaseDataDTO oldBasedataDTO, String objectcode, DataFieldDefineImpl fieldDefine, boolean isLeaf) {
        BaseDataDefineDO baseDataDefine;
        BaseDataDTO basedataDTO = new BaseDataDTO((Map)oldBasedataDTO);
        basedataDTO.setCode(null);
        basedataDTO.setName(null);
        basedataDTO.setObjectcode(objectcode);
        basedataDTO.setLeafFlag(Boolean.valueOf(isLeaf));
        if (fieldDefine.isQueryStop()) {
            basedataDTO.setStopflag(Integer.valueOf(-1));
        }
        if (fieldDefine.isIgnorePermission()) {
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        }
        if ((baseDataDefine = BaseDataDefineCache.getBaseDataDefine(fieldDefine.getRefTableName())) == null) {
            return new PageVO(true);
        }
        if (baseDataDefine.getSharetype() == BaseDataConsts.BASEDATA_SHARETYPE_SHARE) {
            return this.baseDataClient.list(basedataDTO);
        }
        basedataDTO.put("shareForceCheck", (Object)true);
        if (baseDataDefine.getSharetype() == BaseDataConsts.BASEDATA_SHARETYPE_ISOLATIONANDSHARE) {
            basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase()), "-"));
        } else if (baseDataDefine.getSharetype() == BaseDataConsts.BASEDATA_SHARETYPE_ISOLATIONANDDOWN) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setQueryParentType(OrgDataOption.QueryParentType.ALL_PARENT);
            orgDTO.setStopflag(Integer.valueOf(-1));
            PageVO list = this.orgDataClient.list(orgDTO);
            if (!CollectionUtils.isEmpty(list.getRows())) {
                List unitcodeList = list.getRows().stream().map(o -> o.getCode()).collect(Collectors.toList());
                unitcodeList.add((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
                basedataDTO.put("shareUnitcodes", unitcodeList);
            } else {
                basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
            }
        } else {
            basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
        }
        return this.baseDataClient.list(basedataDTO);
    }

    private Object seachBasedataByName(Model model, DataTable table, DataRow row, DataField field, DataFieldDefineImpl fieldDefine, IExpression expression, Map<String, Object> dimValues, String[] strs, String text) {
        String objectcode;
        PageVO<BaseDataDO> list;
        PageVO pageVO;
        String mdControl = fieldDefine.getMdControl();
        boolean ignorePermission = fieldDefine.isIgnorePermission();
        boolean isOnlyLeaf = mdControl != null && mdControl.equals("ONLYLEAF");
        boolean isOnluNotLeaf = mdControl != null && mdControl.equals("ONLYNOTLEAF");
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(fieldDefine.getRefTableName());
        basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) continue;
            basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        basedataDTO.setName(text);
        basedataDTO.setShowType(fieldDefine.getShowType());
        basedataDTO.setShowFullPath(Boolean.valueOf(fieldDefine.isShowFullPath()));
        if (ignorePermission) {
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        }
        if (fieldDefine.isQueryStop()) {
            basedataDTO.setStopflag(Integer.valueOf(-1));
        }
        if ((pageVO = this.baseDataClient.list(basedataDTO)).getRows().size() == 1 && (list = this.searchBaseDataByObjectCode(basedataDTO, objectcode = ((BaseDataDO)pageVO.getRows().get(0)).getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf)).getRows().size() == 1) {
            Map<String, Object> value = (Map<String, Object>)list.getRows().get(0);
            if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                return false;
            }
            if (this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName())) {
                if (list.getRs().containsKey((Object)"showTitle")) {
                    Map map = (Map)list.getRs().get((Object)"showTitle");
                    value.put("showTitle", map.get(objectcode));
                }
                value = this.toViewValue(value);
            } else {
                value = null;
            }
            return value;
        }
        if (pageVO.getRows().size() > 1) {
            String objectcode2;
            PageVO<BaseDataDO> doPageVO;
            List list2 = pageVO.getRows();
            BaseDataDO result = null;
            for (int j = 0; j < list2.size(); ++j) {
                BaseDataDO baseDataDO = (BaseDataDO)list2.get(j);
                String[] parensts = baseDataDO.getParents().split("/");
                if (parensts.length != strs.length) continue;
                boolean parentSame = true;
                for (int l = parensts.length - 2; l >= 0; --l) {
                    String name;
                    basedataDTO = new BaseDataDTO();
                    basedataDTO.setTableName(fieldDefine.getRefTableName());
                    basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
                    for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
                        if (entry.getKey().equals("BIZDATE")) continue;
                        basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                    basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                    if (fieldDefine.isQueryStop()) {
                        basedataDTO.setStopflag(Integer.valueOf(-1));
                    }
                    basedataDTO.setCode(parensts[l]);
                    if (ignorePermission) {
                        basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                    }
                    if ((name = ((BaseDataDO)(pageVO = this.baseDataClient.list(basedataDTO)).getRows().get(0)).getName()).equals(strs[l])) continue;
                    parentSame = false;
                    break;
                }
                if (!parentSame) continue;
                result = baseDataDO;
                break;
            }
            if (result != null && (doPageVO = this.searchBaseDataByObjectCode(basedataDTO, objectcode2 = result.getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf)).getRows().size() == 1) {
                Map<String, Object> value = (Map<String, Object>)doPageVO.getRows().get(0);
                if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                    return null;
                }
                if (this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName())) {
                    if (doPageVO.getRs().containsKey((Object)"showTitle")) {
                        Map map = (Map)doPageVO.getRs().get((Object)"showTitle");
                        value.put("showTitle", map.get(objectcode2));
                    }
                    value = this.toViewValue(value);
                } else {
                    value = null;
                }
                return value;
            }
        }
        return null;
    }

    private Object searchBasedataByCode(Model model, DataTable table, DataRow row, DataField field, DataFieldDefineImpl fieldDefine, IExpression expression, Map<String, Object> dimValues, String[] strs, String text) {
        String objectcode;
        PageVO<BaseDataDO> list;
        PageVO pageVO;
        String mdControl = fieldDefine.getMdControl();
        boolean ignorePermission = fieldDefine.isIgnorePermission();
        boolean isOnlyLeaf = mdControl != null && mdControl.equals("ONLYLEAF");
        boolean isOnluNotLeaf = mdControl != null && mdControl.equals("ONLYNOTLEAF");
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(fieldDefine.getRefTableName());
        basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) continue;
            basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        basedataDTO.setCode(text);
        basedataDTO.setShowType(fieldDefine.getShowType());
        basedataDTO.setShowFullPath(Boolean.valueOf(fieldDefine.isShowFullPath()));
        if (ignorePermission) {
            basedataDTO.setAuthType(BaseDataOption.AuthType.NONE);
        }
        if (fieldDefine.isQueryStop()) {
            basedataDTO.setStopflag(Integer.valueOf(-1));
        }
        if ((pageVO = this.baseDataClient.list(basedataDTO)).getRows().size() == 1 && (list = this.searchBaseDataByObjectCode(basedataDTO, objectcode = ((BaseDataDO)pageVO.getRows().get(0)).getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf)).getRows().size() == 1) {
            Map<String, Object> value = (Map<String, Object>)list.getRows().get(0);
            if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                return null;
            }
            if (this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName())) {
                if (list.getRs().containsKey((Object)"showTitle")) {
                    Map map = (Map)list.getRs().get((Object)"showTitle");
                    value.put("showTitle", map.get(objectcode));
                }
                value = this.toViewValue(value);
            } else {
                value = null;
            }
            return value;
        }
        if (pageVO.getRows().size() > 1) {
            String objectcode2;
            PageVO<BaseDataDO> doPageVO;
            List list2 = pageVO.getRows();
            BaseDataDO result = null;
            for (int j = 0; j < list2.size(); ++j) {
                BaseDataDO baseDataDO = (BaseDataDO)list2.get(j);
                String[] parensts = baseDataDO.getParents().split("/");
                if (parensts.length != strs.length) continue;
                boolean parentSame = true;
                for (int l = parensts.length - 2; l >= 0; --l) {
                    if (parensts[l].equals(strs[l])) continue;
                    parentSame = false;
                    break;
                }
                if (!parentSame) continue;
                result = baseDataDO;
                break;
            }
            if (result != null && (doPageVO = this.searchBaseDataByObjectCode(basedataDTO, objectcode2 = result.getObjectcode(), fieldDefine, isOnlyLeaf || isOnluNotLeaf)).getRows().size() == 1) {
                Map<String, Object> value = (Map<String, Object>)doPageVO.getRows().get(0);
                if (!this.checkBaseDataMdController(isOnlyLeaf, isOnluNotLeaf, value)) {
                    return null;
                }
                if (this.filter(model, expression, table.getName(), row, value, fieldDefine.getRefTableName())) {
                    if (doPageVO.getRs().containsKey((Object)"showTitle")) {
                        Map map = (Map)doPageVO.getRs().get((Object)"showTitle");
                        value.put("showTitle", map.get(objectcode2));
                    }
                    value = this.toViewValue(value);
                } else {
                    value = null;
                }
                return value;
            }
        }
        return null;
    }

    private Map<String, Object> toViewValue(Map<String, Object> values) {
        if (values == null) {
            return null;
        }
        HashMap<String, Object> resultValues = new HashMap<String, Object>();
        resultValues.put("name", values.get("objectcode"));
        Object title = values.get("name");
        resultValues.put("title", title);
        Object showTitle = values.get("showTitle");
        if (!ObjectUtils.isEmpty(showTitle)) {
            resultValues.put("showTitle", showTitle);
        }
        return resultValues;
    }

    private IExpression getFilterExpression(Map<UUID, Map<String, List<Formula>>> fieldMap, DataFieldDefine field) {
        if (fieldMap == null) {
            return null;
        }
        Map<String, List<Formula>> propMap = fieldMap.get(field.getId());
        if (propMap == null) {
            return null;
        }
        List<Formula> list = propMap.get(RulerConsts.FORMULA_OBJECT_PROP_FIELD_FILTER);
        if (list == null || list.size() != 1) {
            return null;
        }
        return (IExpression)list.get(0).getCompiledExpression();
    }

    private boolean filter(Model model, IExpression expression, String tableName, DataRow row, Map<String, Object> value, String refTableName) {
        if (value == null) {
            return false;
        }
        if (expression == null) {
            return true;
        }
        Map<String, DataRow> rowMap = Stream.of(row).collect(Collectors.toMap(k -> tableName, v -> v));
        FormulaUtils.adjustFormulaRows(model.getPlugins().get(Data.class), rowMap);
        try {
            return FormulaUtils.executeFilter(model, expression, rowMap, value, refTableName);
        }
        catch (SyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkBaseDataMdController(boolean isOnlyLeaf, boolean isOnluNotLeaf, Map<String, Object> value) {
        Object isLeaf = value.get("isLeaf");
        if (isOnlyLeaf) {
            if (isLeaf == null) {
                return false;
            }
            return (Boolean)isLeaf;
        }
        if (isOnluNotLeaf) {
            if (isLeaf == null) {
                return false;
            }
            return (Boolean)isLeaf == false;
        }
        return true;
    }
}

