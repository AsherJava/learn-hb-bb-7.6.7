/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nvwa.resourceview.action.ActionContext
 *  com.jiuqi.nvwa.resourceview.action.ActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.action.ActionResult
 *  com.jiuqi.nvwa.resourceview.action.CustomModalActionInteractSetting
 *  com.jiuqi.nvwa.resourceview.query.ResourceNode
 *  com.jiuqi.nvwa.workbench.common.Result
 *  com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO
 *  com.jiuqi.nvwa.workbench.myanalysis.dataset.util.DataSetConvert
 *  com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService
 *  com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisSaveAsDataSetTableAction
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.workbench.myanalysis.action;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.zbquery.model.LayoutField;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.workbench.myanalysis.dataset.ZBQueryWBDSModel;
import com.jiuqi.nvwa.resourceview.action.ActionContext;
import com.jiuqi.nvwa.resourceview.action.ActionInteractSetting;
import com.jiuqi.nvwa.resourceview.action.ActionResult;
import com.jiuqi.nvwa.resourceview.action.CustomModalActionInteractSetting;
import com.jiuqi.nvwa.resourceview.query.ResourceNode;
import com.jiuqi.nvwa.workbench.common.Result;
import com.jiuqi.nvwa.workbench.myanalysis.bean.dto.MyAnalysisDataDTO;
import com.jiuqi.nvwa.workbench.myanalysis.dataset.util.DataSetConvert;
import com.jiuqi.nvwa.workbench.myanalysis.service.IMyAnalysisDataService;
import com.jiuqi.nvwa.workbench.myanalysis.view.table.action.MyAnalysisSaveAsDataSetTableAction;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

public class ZBQuerySaveAsDataSetTableAction
extends MyAnalysisSaveAsDataSetTableAction {
    protected IMyAnalysisDataService maDataService;

    public ZBQuerySaveAsDataSetTableAction() {
        this.interactSetting = this.getInteractSetting();
        this.maDataService = (IMyAnalysisDataService)SpringBeanUtils.getBean(IMyAnalysisDataService.class);
    }

    public boolean visible() {
        return true;
    }

    public ActionResult run(ActionContext actionContext) throws Exception {
        return this.run(actionContext, null);
    }

    public ActionResult run(ActionContext actionContext, String param) throws Exception {
        JSONObject jsonObject = new JSONObject(param);
        String dsTitle = jsonObject.optString("title");
        String dsGroup = StringUtils.isEmpty((String)jsonObject.optString("groupId")) ? null : jsonObject.optString("groupId");
        ResourceNode currOperTableNode = actionContext.getCurrOperTableNode();
        String zbqueryModelData = this.maDataService.getExtDataById(currOperTableNode.getId());
        JSONObject queryModelJson = StringUtils.isNotEmpty((String)zbqueryModelData) ? new JSONObject(zbqueryModelData).optJSONObject("queryModel") : new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ZBQueryModel zbQueryModel = (ZBQueryModel)objectMapper.readValue(queryModelJson.toString(), ZBQueryModel.class);
        List<LayoutField> cols = zbQueryModel.getLayout().getCols();
        List<LayoutField> rows = zbQueryModel.getLayout().getRows();
        if (!CollectionUtils.isEmpty(cols)) {
            rows.addAll(cols);
            cols.clear();
        }
        queryModelJson = new JSONObject(objectMapper.writeValueAsString((Object)zbQueryModel));
        MyAnalysisDataDTO dsDataDTO = new MyAnalysisDataDTO();
        dsDataDTO.setId(UUIDUtils.getKey());
        dsDataDTO.setTitle(dsTitle);
        dsDataDTO.setGroup(dsGroup);
        dsDataDTO.setCreator(NpContextHolder.getContext().getUserId());
        dsDataDTO.setResourceType("com.jiuqi.nvwa.workbench.dataset");
        dsDataDTO.setSubResourceType("com.jiuqi.nr.dataset.zbquery.workbench");
        dsDataDTO.setModifyTime(new Date());
        Result validateResult = DataSetConvert.validateDSData((MyAnalysisDataDTO)dsDataDTO, (boolean)false, (boolean)false, (IMyAnalysisDataService)this.maDataService);
        if (!validateResult.isSuccess()) {
            return ActionResult.error(null, (String)validateResult.getMessage());
        }
        ZBQueryWBDSModel dsModel = new ZBQueryWBDSModel();
        dsModel._setGuid(dsDataDTO.getId());
        dsModel.setName(dsDataDTO.getName());
        dsModel.setTitle(dsDataDTO.getTitle());
        JSONObject queryModelExtData = new JSONObject();
        queryModelExtData.put("zbQueryModel", (Object)queryModelJson);
        JSONObject dsDefineExtData = new JSONObject();
        dsDefineExtData.put("zbQueryDSDefine", (Object)queryModelExtData);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(dsDefineExtData.toString().getBytes(StandardCharsets.UTF_8));
        dsModel.loadExt(inputStream);
        JSONObject extDataJson = new JSONObject();
        dsModel.toJSON(extDataJson);
        dsDataDTO.setData(extDataJson.toString());
        this.maDataService.add(dsDataDTO);
        ActionResult result = ActionResult.success(null, null);
        result.setRefresh(true);
        return result;
    }

    public ActionInteractSetting getInteractSetting() {
        CustomModalActionInteractSetting interactSetting = new CustomModalActionInteractSetting();
        interactSetting.setPluginName("nr-zbquery-myanalysis");
        interactSetting.setPluginType("nr-zbquery-myanalysis-plugin");
        interactSetting.setExpose("ZBQuerySaveAsDataSetModal");
        return interactSetting;
    }
}

