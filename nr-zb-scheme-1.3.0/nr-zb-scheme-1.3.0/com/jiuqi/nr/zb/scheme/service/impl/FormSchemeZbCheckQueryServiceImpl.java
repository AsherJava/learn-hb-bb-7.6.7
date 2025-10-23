/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 */
package com.jiuqi.nr.zb.scheme.service.impl;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.zb.scheme.common.OperationType;
import com.jiuqi.nr.zb.scheme.common.ZbDiffType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.dao.IZbCheckItemDao;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbCheckItemDO;
import com.jiuqi.nr.zb.scheme.service.IFormSchemeZbCheckQueryService;
import com.jiuqi.nr.zb.scheme.service.IZbSchemeService;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckExportParam;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckQueryParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeZbCheckQueryServiceImpl
implements IFormSchemeZbCheckQueryService {
    @Autowired
    private IZbCheckItemDao zbCheckItemDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IZbSchemeService zbSchemeService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;

    @Override
    public List<ZbCheckItemDTO> queryByForm(String checkKey, String formKey) {
        Assert.hasLength(checkKey, "checkKey length is 0");
        Assert.hasLength(formKey, "formKey length is 0");
        return this.queryInternal(new ZbCheckQueryParam(checkKey, formKey), new TempCache());
    }

    @Override
    public List<ZbCheckItemDTO> filterByCond(ZbCheckQueryParam param) {
        return this.queryInternal(param, new TempCache());
    }

    @Override
    public List<ZbCheckItemDTO> search(ZbCheckQueryParam param) {
        return this.queryInternal(param, new TempCache());
    }

    @Override
    public List<ZbCheckItemDTO> queryForExport(ZbCheckExportParam param) {
        ZbCheckQueryParam queryParam = new ZbCheckQueryParam(param.getDiffTypes(), param.getOperType());
        queryParam.setCheckKey(param.getCheckKey());
        queryParam.setFormKey(param.getFormKey());
        queryParam.setFormGroupKey(param.getFormGroupKey());
        queryParam.setKeywords(param.getKeywords());
        return this.queryInternal(queryParam, new TempCache());
    }

    private List<ZbCheckItemDO> queryByParam(ZbCheckQueryParam param) {
        Assert.notNull((Object)param, "zbCheckSearchParam is null");
        String checkKey = param.getCheckKey();
        String formGroupKey = param.getFormGroupKey();
        String formKey = param.getFormKey();
        List<ZbCheckItemDO> zbCheckItemDOS = StringUtils.hasLength(formKey) ? this.zbCheckItemDao.listByCheckAndForm(checkKey, formKey).stream().filter(new ZbCheckSearchPredicate(param)).collect(Collectors.toList()) : (StringUtils.hasLength(formGroupKey) ? this.zbCheckItemDao.listByCheckAndFormGroup(checkKey, formGroupKey).stream().filter(new ZbCheckSearchPredicate(param)).collect(Collectors.toList()) : this.zbCheckItemDao.listByCheck(checkKey).stream().filter(new ZbCheckSearchPredicate(param)).collect(Collectors.toList()));
        return zbCheckItemDOS;
    }

    private List<ZbCheckItemDTO> queryInternal(ZbCheckQueryParam param, TempCache tempCache) {
        List<ZbCheckItemDO> zbCheckItemDOS = this.queryByParam(param);
        List dataFieldKeys = zbCheckItemDOS.stream().map(ZbCheckItemDO::getDataFieldKey).collect(Collectors.toList());
        List<String> zbInfoKeys = zbCheckItemDOS.stream().map(ZbCheckItemDO::getZbInfoKey).filter(StringUtils::hasLength).collect(Collectors.toList());
        Map dataFieldMap = this.designDataSchemeService.getDataFields(dataFieldKeys).stream().collect(Collectors.toMap(Basic::getKey, Function.identity()));
        HashMap zbInfoMap = CollectionUtils.isEmpty(zbInfoKeys) ? new HashMap() : this.zbSchemeService.listZbInfoByKeys(zbInfoKeys).stream().limit(1000L).collect(Collectors.toMap(ZbInfo::getKey, Function.identity()));
        return zbCheckItemDOS.stream().map(zbCheckItemDO -> this.do2dto((ZbCheckItemDO)zbCheckItemDO, (DesignDataField)dataFieldMap.get(zbCheckItemDO.getDataFieldKey()), (ZbInfo)zbInfoMap.get(zbCheckItemDO.getZbInfoKey()), tempCache)).filter(itemDTO -> !StringUtils.hasLength(param.getKeywords()) || itemDTO.getDataField().getTitle().contains(param.getKeywords()) || itemDTO.getDataField().getCode().contains(param.getKeywords())).collect(Collectors.toList());
    }

    private ZbCheckItemDTO do2dto(ZbCheckItemDO itemDO, DesignDataField dataField, ZbInfo zbInfo, TempCache tempCache) {
        ZbCheckItemDTO itemDTO = new ZbCheckItemDTO();
        itemDTO.setCheckKey(itemDO.getCheckKey());
        itemDTO.setPath(itemDO.getDataFieldPath());
        itemDTO.setOperationType(OperationType.typeOf(itemDO.getOperType()));
        itemDTO.setFormSchemeKey(itemDO.getFormSchemeKey());
        itemDTO.setFormGroupKey(itemDO.getFormGroupKey());
        itemDTO.setFormKey(itemDO.getFormKey());
        DesignFormDefine formDefine = tempCache.getForm(itemDO.getFormKey());
        if (formDefine == null) {
            formDefine = this.designTimeViewController.getForm(itemDO.getFormKey());
            tempCache.putForm(formDefine);
        }
        itemDTO.setFormCode(formDefine.getFormCode());
        itemDTO.setFormTitle(formDefine.getTitle());
        itemDTO.setDataField((DataField)dataField);
        itemDTO.setZbInfo(zbInfo);
        int diffType = itemDO.getDiffType();
        for (ZbDiffType type : ZbDiffType.values()) {
            if ((diffType & type.getType()) == 0) continue;
            itemDTO.addDiffType(type);
        }
        return itemDTO;
    }

    static class TempCache {
        private final Map<String, DesignFormDefine> formCache = new HashMap<String, DesignFormDefine>();

        public DesignFormDefine getForm(String key) {
            return this.formCache.get(key);
        }

        public void putForm(DesignFormDefine formDefine) {
            this.formCache.put(formDefine.getKey(), formDefine);
        }
    }

    static class ZbCheckSearchPredicate
    implements Predicate<ZbCheckItemDO> {
        private final ZbCheckQueryParam param;

        public ZbCheckSearchPredicate(ZbCheckQueryParam param) {
            this.param = param;
        }

        @Override
        public boolean test(ZbCheckItemDO zbCheckItemDO) {
            List<Integer> diffTypes = this.param.getDiffTypes();
            int operType = this.param.getOperType();
            boolean operMatch = operType == 0 || operType == zbCheckItemDO.getOperType();
            boolean diffMatch = true;
            if (!CollectionUtils.isEmpty(diffTypes)) {
                for (Integer diffType : diffTypes) {
                    diffMatch = (zbCheckItemDO.getDiffType() & diffType) != 0;
                    if (!diffMatch) continue;
                    break;
                }
            }
            return diffMatch && operMatch;
        }
    }
}

