/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.ref.RefTableDataMap
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.intf.RulerFields
 *  com.jiuqi.va.biz.ruler.intf.RulerItem
 *  com.jiuqi.va.biz.ruler.intf.TriggerEvent
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  com.jiuqi.va.feign.client.OrgCategoryClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.bill.ruler;

import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.RulerFields;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.ruler.intf.TriggerEvent;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgAuthClient;
import com.jiuqi.va.feign.client.OrgCategoryClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InitMasterTable
implements RulerItem {
    public String getName() {
        return "BillInitMasterTable";
    }

    public String getTitle() {
        return "\u521d\u59cb\u5316\u4e3b\u8868\u5b57\u6bb5\u503c";
    }

    public String getRulerType() {
        return "Fixed";
    }

    public Set<String> getTriggerTypes() {
        return Stream.of("after-add-row").collect(Collectors.toSet());
    }

    public Map<String, Map<String, Boolean>> getTriggerFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        return RulerFields.build().noFields(masterTableName, true).fields();
    }

    public Map<String, Map<String, Boolean>> getAssignFields(ModelDefine define) {
        DataDefine data = (DataDefine)define.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        List<String> fields = Arrays.asList("VER", "DEFINECODE", "BILLDATE", "UNITCODE", "CREATEUSER", "CREATETIME", "BILLCODE");
        return RulerFields.build().fields(masterTableName, fields, true).fields();
    }

    public void execute(Model model, Stream<TriggerEvent> events) {
        BillModelImpl billModel = (BillModelImpl)model;
        String unitCode = billModel.getContext().getUnitCode();
        String unitTableName = ((DataFieldDefine)billModel.getMasterTable().getDefine().getFields().get("UNITCODE")).getRefTableName();
        unitTableName = billModel.getUnitCategory(unitTableName);
        if (unitCode != null) {
            int stopFlag;
            RefTableDataMap refTableMap;
            Map map;
            Map<String, Object> dimValues = billModel.getDimValues();
            DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)billModel.getMasterTable().getDefine().getFields().get("UNITCODE");
            if (dataFieldDefine.getMdControl() != null && !"ALL".equals(dataFieldDefine.getMdControl())) {
                dimValues.put("LEAFFLAG", true);
            }
            if ((map = (refTableMap = billModel.getRefDataBuffer().getRefTableMap(4, unitTableName, dimValues)).find(unitCode)) != null) {
                Object authMark = map.get("authMark");
                if (authMark != null && !((Boolean)Convert.cast(authMark, Boolean.TYPE)).booleanValue() && !dataFieldDefine.isIgnorePermission()) {
                    map = null;
                }
                if (map != null && dimValues.containsKey("LEAFFLAG")) {
                    if ("ONLYLEAF".equals(dataFieldDefine.getMdControl()) && !((Boolean)Convert.cast(map.get("isLeaf"), Boolean.TYPE)).booleanValue()) {
                        map = null;
                    }
                    if ("ONLYNOTLEAF".equals(dataFieldDefine.getMdControl()) && ((Boolean)Convert.cast(map.get("isLeaf"), Boolean.TYPE)).booleanValue()) {
                        map = null;
                    }
                }
            }
            if (map == null && !Utils.isEmpty((String)billModel.getContext().getTriggerOrigin())) {
                OrgCategoryClient orgCategoryClient = (OrgCategoryClient)ApplicationContextRegister.getBean(OrgCategoryClient.class);
                OrgCategoryDO orgCatDTO = new OrgCategoryDO();
                orgCatDTO.setName(unitTableName);
                PageVO pageVO = orgCategoryClient.list(orgCatDTO);
                if (pageVO.getTotal() == 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.initmastertable.getunittypefailed") + unitTableName, true);
                }
                OrgAuthClient orgAuthClient = (OrgAuthClient)ApplicationContextRegister.getBean(OrgAuthClient.class);
                OrgDTO orgDTO = new OrgDTO();
                orgDTO.setCategoryname(unitTableName);
                orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
                R existCategroyAuth = orgAuthClient.existCategoryAuth(orgDTO);
                if (existCategroyAuth.getCode() == 0 && !((Boolean)existCategroyAuth.get((Object)"exist")).booleanValue()) {
                    String message = BillCoreI18nUtil.getMessage("va.billcore.initmastertable.notauthorized", new Object[]{((OrgCategoryDO)pageVO.getRows().get(0)).getTitle()});
                    throw new BillException(message, true);
                }
            }
            if (map != null && (stopFlag = ((Integer)Convert.cast(map.get("stopflag"), Integer.TYPE)).intValue()) != 0) {
                map = null;
            }
            if (map == null) {
                unitCode = null;
            }
        }
        String staticUnitCode = unitCode;
        events.forEach(e -> {
            DataRow master = e.getRow();
            master.setValue("VER", (Object)System.currentTimeMillis());
            master.setValue("DEFINECODE", (Object)billModel.getDefine().getName());
            master.setValue("BILLDATE", (Object)billModel.getContext().getBizDate());
            master.setValue("UNITCODE", (Object)staticUnitCode);
            master.setValue("CREATEUSER", (Object)billModel.getContext().getUserCode());
            master.setValue("CREATETIME", (Object)System.currentTimeMillis());
        });
    }
}

