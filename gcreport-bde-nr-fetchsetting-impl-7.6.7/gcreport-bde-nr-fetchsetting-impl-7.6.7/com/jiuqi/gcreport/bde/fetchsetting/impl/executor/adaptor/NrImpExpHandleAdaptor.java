/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.adaptor;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.IFetchSettingImpExpHandleAdaptor;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataField;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataLink;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpDataTable;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFieldDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils.FetchSettingNrUtil;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrImpExpHandleAdaptor
implements IFetchSettingImpExpHandleAdaptor {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    @Override
    public String getBizType() {
        return BizTypeEnum.NR.getCode();
    }

    @Override
    public List<ImpExpFormDefine> listFormDefine(String formSchemeId, List<String> formKeyList) {
        List list = CollectionUtils.isEmpty(formKeyList) ? this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeId) : this.runTimeViewController.queryFormsById(formKeyList);
        if (list == null) {
            return CollectionUtils.newArrayList();
        }
        return list.stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(item -> {
            ImpExpFormDefine formDefine = new ImpExpFormDefine();
            formDefine.setKey(item.getKey());
            formDefine.setCode(item.getFormCode());
            formDefine.setName(item.getTitle());
            return formDefine;
        }).collect(Collectors.toList());
    }

    @Override
    public ImpExpFieldDefine getFieldDefineInfo(String formKey, String regionKey, ImpExpDataTable dataTable, ImpExpDataField dataField, ImpExpDataLink dataLink, FixedAdaptSettingVO adaptSettingVO) {
        Assert.isNotEmpty((String)formKey);
        Assert.isNotNull((Object)regionKey);
        Assert.isNotNull((Object)dataTable);
        Assert.isNotNull((Object)dataField);
        boolean isOnlyFieleDefineCode = "1".equals(this.nvwaSystemOptionService.findValueById("BDE_FETCH_SETTING_OUTPUT_ZB_CODE_MAPPING_CODE"));
        return new ImpExpFieldDefine(isOnlyFieleDefineCode ? dataField.getCode() : FetchSettingNrUtil.getRpFieldDefineCode(dataTable.getCode(), dataField.getCode()), dataField.getName());
    }

    @Override
    public List<ImpExpDataLink> getDataLinkDefines(String formKey, String regionKey) {
        Assert.isNotEmpty((String)formKey);
        Assert.isNotEmpty((String)regionKey);
        List dataLinkList = this.runTimeViewController.getAllLinksInRegion(regionKey).stream().filter(item -> item.getPosY() != 0 && item.getPosX() != 0).sorted((item1, item2) -> {
            if (item1.getPosY() != item2.getPosY()) {
                return item1.getPosY() - item2.getPosY();
            }
            return item1.getPosX() - item2.getPosX();
        }).collect(Collectors.toList());
        return dataLinkList.stream().map(item -> {
            ImpExpDataLink dataLink = new ImpExpDataLink();
            dataLink.setKey(item.getKey());
            dataLink.setName(item.getTitle());
            dataLink.setDataFieldId(item.getLinkExpression());
            return dataLink;
        }).collect(Collectors.toList());
    }

    @Override
    public ImpExpDataLink findDataLinkByMap(Map<String, ImpExpDataLink> codeMap, Map<String, ImpExpDataLink> nameMap, String fieldDefineCode) {
        Assert.isNotNull(codeMap);
        Assert.isNotEmpty(nameMap);
        ImpExpDataLink dataLinkDefine = null;
        if (!StringUtils.isEmpty((String)fieldDefineCode)) {
            dataLinkDefine = codeMap.get(fieldDefineCode);
        }
        if (dataLinkDefine == null) {
            dataLinkDefine = nameMap.get(fieldDefineCode);
        }
        return dataLinkDefine;
    }

    @Override
    public ImpExpDataLink getDataLinkDefine(String formKey, String regionKey, String dataLinkKey) {
        Assert.isNotEmpty((String)formKey);
        Assert.isNotEmpty((String)regionKey);
        Assert.isNotEmpty((String)dataLinkKey);
        DataLinkDefine queryDataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
        if (queryDataLinkDefine == null) {
            ImpExpDataLink dataLink = new ImpExpDataLink();
            dataLink.setKey(dataLinkKey);
            return dataLink;
        }
        ImpExpDataLink dataLink = new ImpExpDataLink();
        dataLink.setKey(queryDataLinkDefine.getKey());
        dataLink.setName(queryDataLinkDefine.getTitle());
        dataLink.setDataFieldId(queryDataLinkDefine.getLinkExpression());
        return dataLink;
    }

    @Override
    public ImpExpDataField getDataField(String formKey, String regionKey, String dataFieldId) {
        Assert.isNotEmpty((String)formKey);
        Assert.isNotEmpty((String)regionKey);
        Assert.isNotEmpty((String)dataFieldId);
        DataField dataField = this.runtimeDataSchemeService.getDataField(dataFieldId);
        if (dataField == null) {
            return null;
        }
        ImpExpDataField impExpDataField = new ImpExpDataField();
        impExpDataField.setKey(dataField.getKey());
        impExpDataField.setCode(dataField.getCode());
        impExpDataField.setName(dataField.getTitle());
        impExpDataField.setDataTableKey(dataField.getDataTableKey());
        return impExpDataField;
    }

    @Override
    public ImpExpDataTable getDataTable(String formKey, String regionKey, String dataTableId) {
        Assert.isNotEmpty((String)formKey);
        Assert.isNotEmpty((String)regionKey);
        Assert.isNotEmpty((String)dataTableId);
        DataTable dataTable = this.runtimeDataSchemeService.getDataTable(dataTableId);
        if (dataTable == null) {
            return null;
        }
        ImpExpDataTable impExpDataTable = new ImpExpDataTable();
        impExpDataTable.setKey(dataTable.getKey());
        impExpDataTable.setCode(dataTable.getCode());
        impExpDataTable.setName(dataTable.getTitle());
        return impExpDataTable;
    }
}

