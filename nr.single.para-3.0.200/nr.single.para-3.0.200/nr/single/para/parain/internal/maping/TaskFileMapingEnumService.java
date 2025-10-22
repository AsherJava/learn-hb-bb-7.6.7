/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.impl.ContextImpl
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 */
package nr.single.para.parain.internal.maping;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.impl.ContextImpl;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Map;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.maping.ITaskFileMapingEnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFileMapingEnumService
implements ITaskFileMapingEnumService {
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private IDataDefinitionRuntimeController dataRuntimeController;
    @Autowired
    private IDesignDataSchemeService dataSchemeSevice;

    @Override
    public void mapingEnumTableDefines(TaskImportContext importContext) throws Exception {
        String taskId = importContext.getTaskKey();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        if (null == formScheme) {
            throw new Exception("\u8be5\u4efb\u52a1\u4e0d\u5b58\u5728\uff0c\u5148\u5bfc\u5165!");
        }
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        String fileFlag = "";
        DesignTaskDefine task = this.viewController.queryTaskDefine(formScheme.getTaskKey());
        DesignDataScheme dataScheme = this.dataSchemeSevice.getDataScheme(task.getDataScheme());
        if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
            fileFlag = dataScheme.getPrefix();
        }
        ContextImpl curContext = new ContextImpl();
        curContext.setTaskKey(taskId);
        importContext.getMapScheme().getEnumInfos().clear();
        Map enums = importContext.getParaInfo().getEnunList();
        for (Map.Entry entry : enums.entrySet()) {
            EnumsItemModel singleEnum = (EnumsItemModel)entry.getValue();
            if (StringUtils.isEmpty((String)singleEnum.getCode())) continue;
            String enumName = String.format("MD%s_%s", StringUtils.isEmpty((String)fileFlag) ? "" : "_" + fileFlag, singleEnum.getCode().toUpperCase());
            boolean enumIsBBLX = "BBLX".equalsIgnoreCase(singleEnum.getCode());
            if (enumIsBBLX) {
                enumName = String.format("MD%s%s", "_" + singleEnum.getCode().toUpperCase(), StringUtils.isEmpty((String)fileFlag) ? "" : "_" + fileFlag);
            }
            BaseDataDefineClient client = (BaseDataDefineClient)ApplicationContextRegister.getBean(BaseDataDefineClient.class);
            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
            baseDataDefineDTO.setTenantName(tenantName);
            baseDataDefineDTO.setName(enumName);
            BaseDataDefineDO table = client.get(baseDataDefineDTO);
            this.UpdateEnumMap(importContext, singleEnum, table);
        }
    }

    private void UpdateEnumMap(TaskImportContext importContext, EnumsItemModel singleEnum, BaseDataDefineDO table) {
        List enumList = importContext.getMapScheme().getEnumInfos();
        SingleFileEnumInfo mapEnum = importContext.getMapScheme().getNewEnumInfo();
        mapEnum.setEnumCode(singleEnum.getCode());
        mapEnum.setEnumTitle(singleEnum.getTitle());
        if (null != table) {
            mapEnum.setNetTableCode(table.getName());
            mapEnum.setNetTableKey(table.getId().toString());
        }
        enumList.add(mapEnum);
        Map enums = singleEnum.getEnumItemList();
        for (DataInfo item : singleEnum.getItemDataList()) {
            SingleFileEnumItem mapItem = mapEnum.getNewEnumItem();
            mapItem.setItemCode(item.getCode());
            mapItem.setItemTitle(item.getName());
            mapItem.setNetItemCode(item.getCode());
            mapItem.setNetItemTitle(item.getName());
            mapEnum.getEnumItems().add(mapItem);
        }
    }
}

