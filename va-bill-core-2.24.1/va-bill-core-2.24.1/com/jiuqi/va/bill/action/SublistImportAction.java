/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.value.NamedContainerImpl
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.decenc.DecEncFile
 *  com.jiuqi.va.biz.intf.decenc.DecEncFileManage
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.value.MissingObjectException
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.domain.BillSublistImportVO;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.impl.SublistActionReturnObject;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.service.BillSublistImportService;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.bill.utils.ExcelUtil;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.decenc.DecEncFile;
import com.jiuqi.va.biz.intf.decenc.DecEncFileManage;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.MissingObjectException;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class SublistImportAction
extends BillActionBase {
    private static final String SUBLIST_IMPORT = "SUBLIST_IMPORT_";
    @Autowired
    private BillSublistImportService billSublistImportService;
    @Autowired
    private DecEncFileManage decEncFileManage;
    @Autowired
    private BillDefineService billDefineService;

    public String getName() {
        return "sublist-import";
    }

    public String getTitle() {
        return "\u5bfc\u5165\uff08\u5b50\u8868\uff09";
    }

    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        super.invoke(model, request, response);
    }

    @Override
    public Object executeReturn(BillModel model, Map<String, Object> params) {
        if ("sublist-import-execute".equals(params.get("type"))) {
            Object encryptType;
            List template = (List)params.get("template");
            String file = (String)params.get("file");
            String tableName = (String)params.get("tableName");
            Integer startRow = (Integer)params.get("startRow");
            Integer startColumn = (Integer)params.get("startColumn");
            byte[] data = Base64.getDecoder().decode(file);
            Object isEncrypt = params.get("isEncrypt");
            if (isEncrypt != null && ((Boolean)isEncrypt).booleanValue() && !ObjectUtils.isEmpty(encryptType = params.get("encryptType"))) {
                DecEncFile decEncFile = DecEncFileManage.get((String)((String)encryptType));
                data = decEncFile.decrypt(data);
            }
            Map<String, Object> readXls = ExcelUtil.readXls(data, (BillModelImpl)model, tableName, template, startRow, startColumn);
            SublistActionReturnObject<Map<String, Object>> result = new SublistActionReturnObject<Map<String, Object>>();
            result.setResult(readXls);
            String uuid = UUID.randomUUID().toString();
            String cacheKey = SUBLIST_IMPORT + uuid;
            BillSublistImportVO billSublistImportVO = new BillSublistImportVO();
            billSublistImportVO.setSuccessData(0);
            billSublistImportVO.setErrorData(0);
            this.billSublistImportService.syncSublistProgress(cacheKey, billSublistImportVO);
            HashMap<String, Object> newParam = new HashMap<String, Object>(params);
            newParam.putAll(readXls);
            UserLoginDTO loginUser = ShiroUtil.getUser();
            loginUser.addExtInfo("JTOKENID", (Object)ShiroUtil.getToken());
            BillModelImpl billModel = (BillModelImpl)model;
            BillModel newModel = this.billDefineService.createModel((BillContext)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)billModel.getContext()), BillContextImpl.class), billModel.getDefine().getName());
            if (params.get("curTimezone") != null) {
                newModel.getContext().setContextValue("X--timeZone", params.get("curTimezone"));
            }
            newModel.getData().create();
            newModel.getData().setTablesData(billModel.getData().getTablesData(false));
            this.billSublistImportService.checkExcelData(newModel, newParam, cacheKey, loginUser, LocaleContextHolder.getLocale());
            readXls.put("cacheKey", cacheKey);
            return result;
        }
        if ("sublist-import-save".equals(params.get("type"))) {
            return this.billSublistImportService.saveExcelData(model, params);
        }
        if ("sublist-import-download".equals(params.get("type"))) {
            List template = (List)params.get("template");
            String templateData = ExcelUtil.getTemplate(template);
            SublistActionReturnObject<String> result = new SublistActionReturnObject<String>();
            result.setResult(templateData);
            return result;
        }
        return null;
    }

    @Override
    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_EDIT;
    }

    public String getActionPriority() {
        return "026";
    }

    public List<PluginCheckResultDTO> checkActionConfig(ModelDefine modelDefine, Map<String, Object> params) {
        DataTableDefineImpl dataTableDefine;
        Object template = params.get("template");
        if (ObjectUtils.isEmpty(template)) {
            return null;
        }
        String tableName = BillUtils.valueToString(params.get("tableName"));
        if (tableName == null) {
            return null;
        }
        ArrayList<PluginCheckResultDTO> checkResultDTOS = new ArrayList<PluginCheckResultDTO>();
        DataDefineImpl data = (DataDefineImpl)modelDefine.getPlugins().get("data");
        try {
            dataTableDefine = (DataTableDefineImpl)data.getTables().get(tableName);
        }
        catch (MissingObjectException m) {
            checkResultDTOS.add(this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5b50\u8868\u5bfc\u5165\u914d\u7f6e\u4e2d\u5f53\u524d\u5b50\u8868\u5728\u6570\u636e\u5efa\u6a21\u4e2d\u4e0d\u5b58\u5728\uff1a" + tableName, ""));
            return checkResultDTOS;
        }
        NamedContainerImpl fields = dataTableDefine.getFields();
        List subTemplate = (List)template;
        for (Map map : subTemplate) {
            DataFieldDefineImpl dataFieldDefine;
            String fieldName = BillUtils.valueToString(map.get("fieldName"));
            try {
                dataFieldDefine = (DataFieldDefineImpl)fields.get(fieldName);
            }
            catch (MissingObjectException m) {
                checkResultDTOS.add(this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5b50\u8868\u5bfc\u5165\u914d\u7f6e\u4e2d\u5f53\u524d\u5b57\u6bb5\u5728\u6570\u636e\u5efa\u6a21\u4e2d\u4e0d\u5b58\u5728\uff1a" + tableName + "\u3010" + fieldName + "\u3011", ""));
                continue;
            }
            String refTableName = BillUtils.valueToString(map.get("refTableName"));
            if (refTableName == null && !StringUtils.hasText(dataFieldDefine.getRefTableName()) || refTableName != null && StringUtils.hasText(dataFieldDefine.getRefTableName())) continue;
            checkResultDTOS.add(this.getPluginCheckResultDTO(PluginCheckType.ERROR, "\u5b50\u8868\u5bfc\u5165\u914d\u7f6e\u4e2d\u5f53\u524d\u5b57\u6bb5\u5728\u6570\u636e\u5efa\u6a21\u4e2d\u5df2\u53d1\u751f\u53d8\u5316\uff1a" + tableName + "\u3010" + fieldName + "\u3011", ""));
        }
        return checkResultDTOS.size() > 0 ? checkResultDTOS : null;
    }

    private PluginCheckResultDTO getPluginCheckResultDTO(PluginCheckType checkType, String message, String objectPath) {
        PluginCheckResultDTO pluginCheckResultDTO = new PluginCheckResultDTO();
        pluginCheckResultDTO.setObjectpath(objectPath);
        pluginCheckResultDTO.setType(checkType);
        pluginCheckResultDTO.setMessage(message);
        return pluginCheckResultDTO;
    }
}

