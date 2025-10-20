/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.front.FrontModelDefine
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableImpl
 *  com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl
 *  com.jiuqi.va.biz.impl.model.ModelContextImpl
 *  com.jiuqi.va.biz.intf.ActionCategory
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.ActionReturnObject
 *  com.jiuqi.va.biz.intf.data.DataAccess
 *  com.jiuqi.va.biz.intf.data.DataRowState
 *  com.jiuqi.va.biz.intf.data.DataState
 *  com.jiuqi.va.biz.intf.model.BillFrontDefineService
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.view.intf.ExternalViewDefine
 *  com.jiuqi.va.biz.view.intf.ExternalViewManager
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.va.bill.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillEditService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.impl.BillIncEditServiceImpl;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableImpl;
import com.jiuqi.va.biz.impl.data.DataTableNodeContainerImpl;
import com.jiuqi.va.biz.impl.model.ModelContextImpl;
import com.jiuqi.va.biz.intf.ActionCategory;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.ActionReturnObject;
import com.jiuqi.va.biz.intf.data.DataAccess;
import com.jiuqi.va.biz.intf.data.DataRowState;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.model.BillFrontDefineService;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.view.intf.ExternalViewDefine;
import com.jiuqi.va.biz.view.intf.ExternalViewManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BillEditServiceImpl
implements BillEditService {
    private static final Logger logger = LoggerFactory.getLogger(BillEditServiceImpl.class);
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private ActionManager actionManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private DataAccess dataAccess;
    @Autowired(required=false)
    private ExternalViewManager externalViewManager;
    @Autowired
    private BillFrontDefineService billFrontDefineService;

    public static void loadBill(BillModel model, Object dataId) {
        UUID id;
        if (dataId == null) {
            return;
        }
        try {
            id = (UUID)Convert.cast((Object)dataId, UUID.class);
        }
        catch (Exception e) {
            id = null;
        }
        if (id == null || !id.toString().equals(dataId.toString())) {
            model.loadByCode(dataId.toString());
        } else {
            model.loadById(id);
        }
    }

    public static void loadBillByCode(BillModel model, Object billCode) {
        if (billCode == null) {
            return;
        }
        model.loadByCode(billCode.toString());
    }

    public static void loadBillById(BillModel model, Object billId) {
        if (billId == null) {
            return;
        }
        model.loadById(Convert.cast((Object)billId, UUID.class));
    }

    private Map<String, Object> wrap(BillModelImpl model, String viewName, long defineVer, String schemeCode) {
        Object computeDateTimeFields;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        Map tablesData = DataState.NEW.equals((Object)model.getData().getState()) ? model.getData().getTablesData() : model.getData().getFilterTablesData();
        Object detailFilterDataId = model.getContext().getContextValue("X--detailFilterDataId");
        if (detailFilterDataId != null) {
            map.put("detailFilterDataId", detailFilterDataId);
        }
        if ((computeDateTimeFields = model.getContext().getContextValue("X--computeDateTimeFields")) != null) {
            map.put("computeDateTimeFields", computeDateTimeFields);
        }
        map.put("data", tablesData);
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            if (model.getContext().getContextValue("X--designData") != null) {
                ((ModelContextImpl)model.getContext()).setPreview(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
                BillDefine billDefine = (BillDefine)mapper.convertValue(model.getContext().getContextValue("X--designData"), BillDefine.class);
                FrontModelDefine frontModelDefine = new FrontModelDefine((ModelDefine)billDefine, false, viewName);
                map.put("define", frontModelDefine);
            } else {
                map.put("define", this.billFrontDefineService.getDefine((Model)model, schemeCode, viewName));
            }
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        return map;
    }

    private Map<String, Object> wrap(BillModelImpl model, String viewName, long defineVer) {
        Object computeDateTimeFields;
        HashMap<String, Object> map = new HashMap<String, Object>(16);
        map.put("id", UUID.randomUUID());
        Map filterTablesData = model.getData().getFilterTablesData();
        Object detailFilterDataId = model.getContext().getContextValue("X--detailFilterDataId");
        if (detailFilterDataId != null) {
            map.put("detailFilterDataId", detailFilterDataId);
        }
        if ((computeDateTimeFields = model.getContext().getContextValue("X--computeDateTimeFields")) != null) {
            map.put("computeDateTimeFields", computeDateTimeFields);
        }
        map.put("data", filterTablesData);
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            if (model.getContext().getContextValue("X--designData") != null) {
                ((ModelContextImpl)model.getContext()).setPreview(true);
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
                BillDefine billDefine = (BillDefine)mapper.convertValue(model.getContext().getContextValue("X--designData"), BillDefine.class);
                FrontModelDefine frontModelDefine = new FrontModelDefine((ModelDefine)billDefine, false, viewName);
                map.put("define", frontModelDefine);
            } else {
                map.put("define", this.billFrontDefineService.getDefine((Model)model, null, viewName));
            }
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        return map;
    }

    private Map<String, Object> wrap(BillModelImpl model, String viewName, long defineVer, Object executeReturn, String schmecode) {
        Object computeDateTimeFields;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        Map tablesData = model.getData().getFilterTablesData();
        Object detailFilterDataId = model.getContext().getContextValue("X--detailFilterDataId");
        if (detailFilterDataId != null) {
            map.put("detailFilterDataId", detailFilterDataId);
        }
        if ((computeDateTimeFields = model.getContext().getContextValue("X--computeDateTimeFields")) != null) {
            map.put("computeDateTimeFields", computeDateTimeFields);
        }
        map.put("data", tablesData);
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", this.billFrontDefineService.getDefine((Model)model, schmecode, viewName));
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        map.put("result", executeReturn);
        return map;
    }

    @Override
    public Map<String, Object> add(BillContext context, String defineCode, long defineVer, String viewName, String schemeCode, Map<String, Object> param) {
        BillModelImpl model;
        if (context.getContextValue("X--designData") != null) {
            ((ModelContextImpl)context).setPreview(true);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            BillDefine billDefine = (BillDefine)mapper.convertValue(context.getContextValue("X--designData"), BillDefine.class);
            String billType = billDefine.getModelType();
            ModelType modelType = (ModelType)this.modelManager.find(billType);
            modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName());
            model = (BillModelImpl)this.billDefineService.createModel(context, billDefine);
        } else {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
        }
        this.executeModelAdd(param, model);
        return this.wrap(model, viewName, defineVer, schemeCode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeModelAdd(Map<String, Object> param, BillModelImpl model) {
        model.getRuler().getRulerExecutor().setEnable(true);
        try {
            Action action = (Action)this.actionManager.get("bill-add");
            if (action == null) {
                if (param != null) {
                    model.add(param);
                } else {
                    model.add();
                }
            } else {
                ActionRequest request = new ActionRequest();
                if (param != null) {
                    request.setParams(param);
                }
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
            }
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    @Override
    public Map<String, Object> add(BillContextImpl context, String defineCode, long defineVer, String viewName, Map<String, Object> param, String externalViewName) {
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)context, defineCode, externalViewName);
        this.executeModelAdd(param, model);
        ExternalViewDefine externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName);
        if (externalViewDefine != null) {
            return this.externalViewWrap(model, viewName, defineVer, externalViewDefine);
        }
        return this.wrap(model, viewName, defineVer);
    }

    private Map<String, Object> externalViewWrap(BillModelImpl model, String viewName, long defineVer, ExternalViewDefine externalViewDefine) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", UUID.randomUUID());
        map.put("data", model.getData().getTablesData());
        map.put("state", model.getData().getState().getValue());
        if (defineVer == 0L || defineVer != model.getDefine().getMetaInfo().getVersion()) {
            map.put("define", this.billFrontDefineService.getExternalDefine((Model)model, externalViewDefine.getName(), viewName));
        }
        if (model.getContext().getVerifyCode() != null) {
            map.put("verifyCode", model.getContext().getVerifyCode());
        }
        return map;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> edit(BillContext context, String defineCode, long defineVer, String dataId, String viewName) {
        BillModelImpl model;
        block5: {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
            BillEditServiceImpl.loadBill(model, dataId);
            model.getRuler().getRulerExecutor().setEnable(true);
            try {
                Action action = (Action)this.actionManager.get("bill-edit");
                if (action == null) {
                    model.edit();
                    break block5;
                }
                Object sceneKey = context.getContextValue("scene_key");
                if (sceneKey != null && sceneKey.equals("billlist")) {
                    model.getContext().setContextValue("X--consistency", false);
                }
                ActionRequest request = new ActionRequest();
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
                Object executeReturn = null;
                executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                Map<String, Object> map = this.wrap(model, viewName, defineVer, executeReturn, null);
                return map;
            }
            finally {
                model.getRuler().getRulerExecutor().setEnable(false);
            }
        }
        return this.wrap(model, viewName, defineVer);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> edit(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode) {
        BillModelImpl model;
        block7: {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
            BillEditServiceImpl.loadBillByCode(model, dataId);
            model.getRuler().getRulerExecutor().setEnable(true);
            try {
                boolean billChange;
                Action action = null;
                boolean bl = billChange = model.getContext().getContextValue("X--loadChangeData") != null;
                if (billChange) {
                    action = (Action)this.actionManager.get("va-billChange-edit");
                } else {
                    action = (Action)this.actionManager.get("bill-edit");
                    Object sceneKey = context.getContextValue("scene_key");
                    if (sceneKey != null && sceneKey.equals("billlist")) {
                        model.getContext().setContextValue("X--consistency", false);
                    }
                }
                if (action == null) {
                    model.edit();
                    break block7;
                }
                ActionRequest request = new ActionRequest();
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
                Object executeReturn = null;
                executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                Map<String, Object> map = this.wrap(model, viewName, defineVer, executeReturn, schemeCode);
                return map;
            }
            finally {
                model.getRuler().getRulerExecutor().setEnable(false);
            }
        }
        return this.wrap(model, viewName, defineVer);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName) {
        BillDefine define = this.billDefineService.getDefine(defineCode);
        return this.load(context, define, defineVer, dataId, viewName, null);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode) {
        BillDefine define;
        if (context.getContextValue("X--designData") != null) {
            ((ModelContextImpl)context).setPreview(true);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            define = (BillDefine)mapper.convertValue(context.getContextValue("X--designData"), BillDefine.class);
        } else {
            define = this.billDefineService.getDefine(defineCode);
        }
        if (StringUtils.hasText(schemeCode)) {
            return this.load(context, define, defineVer, dataId, viewName, schemeCode, null);
        }
        return this.load(context, define, defineVer, dataId, viewName, null);
    }

    @Override
    public Map<String, Object> load(BillContext context, String defineCode, long defineVer, String dataId, String viewName, String schemeCode, String externalViewName) {
        BillDefine define = this.billDefineService.getDefine(defineCode, externalViewName);
        if (StringUtils.hasText(schemeCode)) {
            return this.load(context, define, defineVer, dataId, viewName, schemeCode, externalViewName);
        }
        return this.load(context, define, defineVer, dataId, viewName, externalViewName);
    }

    @Override
    public Map<String, Object> load(BillContext context, BillDefine define, String dataId, String viewName) {
        return this.load(context, define, 0L, dataId, viewName, null);
    }

    public Map<String, Object> load(BillContext context, BillDefine define, long defineVer, String dataId, String viewName, String externalViewName) {
        ExternalViewDefine externalViewDefine;
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel(context, define);
        if (context.getContextValue("loadById") != null) {
            BillEditServiceImpl.loadBillById(model, dataId);
        } else {
            BillEditServiceImpl.loadBillByCode(model, dataId);
        }
        model.getRuler().getRulerExecutor().setEnable(true);
        model.getRuler().getRulerExecutor().setEnable(false);
        if (StringUtils.hasText(externalViewName) && (externalViewDefine = (ExternalViewDefine)this.externalViewManager.find(externalViewName)) != null) {
            return this.externalViewWrap(model, viewName, defineVer, externalViewDefine);
        }
        return this.wrap(model, viewName, defineVer);
    }

    public Map<String, Object> load(BillContext context, BillDefine define, long defineVer, String dataId, String viewName, String schemeCode, String externalViewName) {
        BillModelImpl model = (BillModelImpl)this.billDefineService.createModel(context, define);
        if (context.getContextValue("loadById") != null) {
            BillEditServiceImpl.loadBillById(model, dataId);
        } else {
            BillEditServiceImpl.loadBillByCode(model, dataId);
        }
        model.getRuler().getRulerExecutor().setEnable(true);
        model.getRuler().getRulerExecutor().setEnable(false);
        return this.wrap(model, viewName, defineVer, schemeCode);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object> sync(BillContext context, String defineCode, long defineVer, String dataId, String viewName, Map<String, Object> data, String externalViewName) {
        Object computeDateTimeFields;
        Object detailFilterDataId;
        Object contextValue;
        BillModelImpl model;
        String type = (String)data.get("type");
        Map modelParams = (Map)data.get("model");
        Map params = (Map)data.get("params");
        List subActions = (List)data.get("actions");
        Action action = (Action)this.actionManager.get(type);
        Map modelData = (Map)modelParams.get("data");
        if (type.equals("set-value") || subActions != null && subActions.size() > 0) {
            Map map = null;
            if (type.equals("set-value")) {
                map = params;
            } else {
                Map setValueSubAction = null;
                for (Map subAction : subActions) {
                    String subType = (String)subAction.get("type");
                    if (!subType.equals("set-value")) continue;
                    setValueSubAction = subAction;
                    break;
                }
                if (setValueSubAction != null) {
                    map = (Map)setValueSubAction.get("params");
                }
            }
            if (map != null) {
                String tableName = (String)map.get("tableName");
                String fieldName = (String)map.get("fieldName");
                int rowIndex = (Integer)map.get("rowIndex");
                Object oldValue = map.get("oldValue");
                ((Map)((List)modelData.get(tableName)).get(rowIndex)).put(fieldName, oldValue);
            }
        }
        DataState modelState = (DataState)Convert.cast(modelParams.get("state"), DataState.class);
        String modelBillCode = (String)modelParams.get("master.BILLCODE");
        long modelBillVer = (Long)Convert.cast(modelParams.get("master.VER"), Long.TYPE);
        Object define = data.get("define");
        if (define == null && context.getContextValue("X--designData") != null) {
            define = context.getContextValue("X--designData");
        }
        if (define != null) {
            ((ModelContextImpl)context).setPreview(true);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
            BillDefine billDefine = (BillDefine)mapper.convertValue(define, BillDefine.class);
            String billType = billDefine.getModelType();
            ModelType modelType = (ModelType)this.modelManager.find(billType);
            if (StringUtils.hasText(externalViewName)) {
                modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName(), externalViewName);
            } else {
                modelType.initModelDefine((ModelDefine)billDefine, billDefine.getName());
            }
            model = (BillModelImpl)this.billDefineService.createModel(context, billDefine);
        } else if (StringUtils.hasText(externalViewName)) {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode, externalViewName);
            if (defineVer != 0L && defineVer != model.getDefine().getMetaInfo().getVersion()) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.billchangerefresh"));
            }
        } else {
            model = (BillModelImpl)this.billDefineService.createModel(context, defineCode);
        }
        if (ActionCategory.EDIT.equals((Object)action.getActionCategory()) && (contextValue = model.getContext().getContextValue("X--detailFilterDataId")) != null) {
            model.getContext().setContextValue("X--detailFilterDataId", null);
        }
        model.getContext().setContextValue("ActionType", type);
        if (modelBillCode != null) {
            BillEditServiceImpl.loadBillByCode(model, modelBillCode);
            long oldVer = model.getMaster().getVersion();
            long newVer = modelBillVer;
            if (oldVer != newVer) {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.datachangerefresh"));
            }
        } else if (modelData != null) {
            switch (modelState) {
                case NEW: {
                    model.getData().create();
                    model.getData().setTablesData(modelData);
                    break;
                }
                case EDIT: {
                    long newVer;
                    long oldVer;
                    String id = (String)((Map)((List)modelData.get(model.getMasterTable().getName())).get(0)).get("ID");
                    BillEditServiceImpl.loadBillById(model, id);
                    Object contextValue2 = model.getContext().getContextValue("X--detailFilterDataId");
                    if (contextValue2 != null) {
                        int anInt = model.getMaster().getInt("BILLSTATE");
                        if (BillState.AUDITING.getValue() == anInt || BillState.COMMITTED.getValue() == anInt) {
                            model.getData().edit();
                            model.getData().setTablesData(modelData);
                            break;
                        }
                    }
                    if ((oldVer = model.getMaster().getVersion()) != (newVer = ((Long)Convert.cast(((Map)((List)modelData.get(model.getMasterTable().getName())).get(0)).get("VER"), Long.TYPE)).longValue())) {
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.datachangerefresh"));
                    }
                    model.getData().edit();
                    model.getData().setTablesData(modelData);
                    break;
                }
                case BROWSE: {
                    String masterId = (String)((Map)((List)modelData.get(model.getMasterTable().getName())).get(0)).get("ID");
                    BillEditServiceImpl.loadBillById(model, masterId);
                    long oldVer = model.getMaster().getVersion();
                    long newVer = (Long)Convert.cast(((Map)((List)modelData.get(model.getMasterTable().getName())).get(0)).get("VER"), Long.TYPE);
                    Object consistency = model.getContext().getContextValue("X--consistency");
                    if (oldVer != newVer || consistency == null) break;
                    model.getContext().setContextValue("X--consistency", true);
                    break;
                }
                default: {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.billmusteditstate"));
                }
            }
        }
        Object executeReturn = null;
        ArrayList executeSubReturns = new ArrayList();
        Map tablesData = null;
        model.getRuler().getRulerExecutor().setEnable(true);
        try {
            if (subActions != null && subActions.size() > 0) {
                Object contextValue3;
                subActions.forEach(o -> {
                    String sub_type = (String)o.get("type");
                    Map sub_params = (Map)o.get("params");
                    Action sub_action = (Action)this.actionManager.get(sub_type);
                    ActionRequest sub_request = new ActionRequest();
                    sub_request.setParams(sub_params);
                    ActionResponse sub_response = new ActionResponse();
                    model.executeAction(sub_action, sub_request, sub_response);
                    if (sub_response.getReturnValue() != null) {
                        executeSubReturns.add(sub_response.getReturnValue());
                    } else {
                        executeSubReturns.add(sub_response.getReturnMessage());
                    }
                });
                tablesData = ActionCategory.EDIT.equals((Object)action.getActionCategory()) ? model.getData().getFilterTablesData() : ((contextValue3 = model.getContext().getContextValue("X--detailFilterDataId")) != null ? model.getData().getFilterTablesData() : model.getData().getTablesData());
            }
            try {
                ActionRequest request = new ActionRequest();
                request.setParams(params);
                if (params != null && params.containsKey("confirms")) {
                    model.getContext().setContextValue("X--confirms", params.get("confirms"));
                }
                ActionResponse response = new ActionResponse();
                model.executeAction(action, request, response);
                if (!response.isSuccess() && response.getCheckMessages() != null && response.getCheckMessages().size() > 0) {
                    throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executefailed"), response.getCheckMessages());
                }
                executeReturn = response.getReturnValue() != null ? response.getReturnValue() : response.getReturnMessage();
                BillIncEditServiceImpl.setConfirms(model, executeReturn);
            }
            catch (BillException e) {
                if (e.getTablesData() == null) {
                    e.setTablesData(tablesData);
                }
                throw e;
            }
            catch (DataAccessException e) {
                BillException billException = new BillException(BillCoreI18nUtil.getMessage("va.billcore.billeditservice.executeexception"), e);
                billException.setTablesData(tablesData);
                billException.setInvisible(true);
                throw billException;
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                BillException billException = new BillException(e.getMessage(), e);
                billException.setTablesData(tablesData);
                billException.setInvisible(true);
                throw billException;
            }
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
        HashMap<String, Object> result = new HashMap<String, Object>();
        Object loadChangeData = model.getContext().getContextValue("X--loadChangeData");
        if (loadChangeData != null && ((Boolean)loadChangeData).booleanValue() && model.getContext().getContextValue("X--editTableFields") != null) {
            result.put("editTableFields", model.getContext().getContextValue("X--editTableFields"));
        }
        result.put("result", executeReturn);
        result.put("subActionResults", executeSubReturns);
        if (model.getData().getState() != DataState.NONE) {
            result.put("id", model.getMaster().getId());
        }
        if (executeReturn instanceof ActionReturnObject && !((ActionReturnObject)executeReturn).isSuccess()) {
            if (tablesData != null) {
                result.put("data", tablesData);
            }
        } else {
            Object filterDataId;
            detailFilterDataId = model.getContext().getContextValue("X--detailFilterDataId");
            if (detailFilterDataId != null) {
                result.put("detailFilterDataId", detailFilterDataId);
            }
            Map filterTablesData = ActionCategory.EDIT.equals((Object)action.getActionCategory()) ? model.getData().getFilterTablesData() : ((filterDataId = model.getContext().getContextValue("X--detailFilterDataId")) != null ? model.getData().getFilterTablesData() : model.getData().getTablesData());
            result.put("data", filterTablesData);
            result.put("state", model.getData().getState().getValue());
        }
        if ((detailFilterDataId = model.getContext().getContextValue("X--detailFilterDataId")) != null) {
            result.put("detailFilterDataId", detailFilterDataId);
        }
        if ((computeDateTimeFields = model.getContext().getContextValue("X--computeDateTimeFields")) != null) {
            result.put("computeDateTimeFields", computeDateTimeFields);
        }
        if (model.getContext().getVerifyCode() != null) {
            result.put("verifyCode", model.getContext().getVerifyCode());
        }
        if (context.getContextValue("X--isModified") != null && !ActionCategory.SAVE.equals((Object)action.getActionCategory())) {
            boolean isModified = false;
            if (DataState.NEW.equals((Object)modelState)) {
                isModified = true;
            } else {
                DataTableNodeContainerImpl tables = model.getData().getTables();
                for (int i = 0; i < tables.size(); ++i) {
                    DataTableImpl dataTable = (DataTableImpl)tables.get(i);
                    if (dataTable.getDeletedRows().findAny().isPresent()) {
                        isModified = true;
                        break;
                    }
                    for (DataRowImpl dataRow : dataTable.getRowList()) {
                        if (!DataRowState.APPENDED.equals((Object)dataRow.getState()) && !DataRowState.MODIFIED.equals((Object)dataRow.getState()) && !DataRowState.DELETED.equals((Object)dataRow.getState())) continue;
                        isModified = true;
                        break;
                    }
                    if (isModified) break;
                }
            }
            result.put("isModified", isModified);
        }
        return result;
    }
}

