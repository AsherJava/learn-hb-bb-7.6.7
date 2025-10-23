/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.mapping2.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.mapping2.bean.FormMappingDO;
import com.jiuqi.nr.mapping2.common.NrMappingUtil;
import com.jiuqi.nr.mapping2.dao.FormMappingDao;
import com.jiuqi.nr.mapping2.dto.FormMappingDTO;
import com.jiuqi.nr.mapping2.provider.NrMappingParam;
import com.jiuqi.nr.mapping2.service.IFormMappingService;
import com.jiuqi.nr.mapping2.web.vo.CommonTreeNode;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FormMappingServiceImpl
implements IFormMappingService {
    private static final String ROOT_KEY = "-";
    @Autowired
    private FormMappingDao formMappingDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IMappingSchemeService mappingSchemeService;

    @Override
    public List<FormMappingDTO> list(String schemeKey, String groupKey) {
        MappingScheme mappingScheme = this.mappingSchemeService.getSchemeByKey(schemeKey);
        if (mappingScheme == null) {
            throw new RuntimeException("\u4e0d\u5b58\u5728\u7684\u6620\u5c04\u65b9\u6848");
        }
        List<FormMappingDO> list = this.formMappingDao.list(schemeKey);
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(mappingScheme);
        List formDefines = !StringUtils.hasText(groupKey) || ROOT_KEY.equals(groupKey) ? this.runTimeViewController.listFormByFormScheme(mappingParam.getFormSchemeKey()) : this.runTimeViewController.listFormByGroup(groupKey, mappingParam.getFormSchemeKey());
        Map<String, FormMappingDO> mappingMap = list.stream().collect(Collectors.toMap(FormMappingDO::getSourceKey, e -> e));
        return formDefines.stream().map(e -> {
            FormMappingDO mappingDO = (FormMappingDO)mappingMap.get(e.getKey());
            if (mappingDO == null) {
                mappingDO = new FormMappingDO();
                mappingDO.setKey(UUID.randomUUID().toString());
                mappingDO.setSourceKey(e.getKey());
                mappingDO.setSchemeKey(schemeKey);
            }
            return FormMappingDTO.getInstance(mappingDO, e);
        }).collect(Collectors.toList());
    }

    @Override
    public void create(String schemeKey, List<FormMappingDTO> mappings) {
        ArrayList<FormMappingDO> rows = new ArrayList<FormMappingDO>();
        for (FormMappingDTO mapping : mappings) {
            FormMappingDO mappingDO = FormMappingServiceImpl.transfer(schemeKey, mapping);
            rows.add(mappingDO);
        }
        this.formMappingDao.batchInsert(rows);
    }

    @NotNull
    private static FormMappingDO transfer(String schemeKey, FormMappingDTO mapping) {
        FormMappingDO mappingDO = new FormMappingDO();
        mappingDO.setKey(mapping.getKey());
        mappingDO.setSchemeKey(schemeKey);
        mappingDO.setSourceKey(mapping.getSourceKey());
        mappingDO.setTargetCode(mapping.getTargetCode());
        mappingDO.setTargetTitle(mapping.getTargetTitle());
        return mappingDO;
    }

    @Override
    public void update(String schemeKey, List<FormMappingDTO> modifyRows) {
        List<FormMappingDO> mapping = this.formMappingDao.list(schemeKey);
        Set formKeys = mapping.stream().map(FormMappingDO::getSourceKey).collect(Collectors.toSet());
        ArrayList<FormMappingDO> insertRows = new ArrayList<FormMappingDO>();
        ArrayList<FormMappingDO> updateRows = new ArrayList<FormMappingDO>();
        modifyRows.forEach(e -> {
            if (!formKeys.contains(e.getSourceKey())) {
                insertRows.add(FormMappingServiceImpl.transfer(schemeKey, e));
            } else {
                updateRows.add(FormMappingServiceImpl.transfer(schemeKey, e));
            }
        });
        this.formMappingDao.batchUpdate(updateRows);
        this.formMappingDao.batchInsert(insertRows);
    }

    @Override
    public void clean(String schemeKey, String groupKey) {
        if (ROOT_KEY.equals(groupKey)) {
            this.formMappingDao.delete(schemeKey);
        } else {
            MappingScheme mappingScheme = this.mappingSchemeService.getSchemeByKey(schemeKey);
            NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(mappingScheme);
            List formDefines = this.runTimeViewController.listFormByFormScheme(mappingParam.getFormSchemeKey());
            this.formMappingDao.delete(schemeKey, (String[])formDefines.stream().map(IBaseMetaItem::getKey).toArray(String[]::new));
        }
    }

    @Override
    public List<CommonTreeNode> initTree(String schemeKey) {
        ArrayList<CommonTreeNode> tree = new ArrayList<CommonTreeNode>();
        MappingScheme mappingScheme = this.mappingSchemeService.getSchemeByKey(schemeKey);
        NrMappingParam mappingParam = NrMappingUtil.getNrMappingParam(mappingScheme);
        List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(mappingParam.getFormSchemeKey());
        CommonTreeNode root = new CommonTreeNode(ROOT_KEY, "ROOT", "\u6240\u6709\u62a5\u8868");
        root.setExpand(true);
        root.setSelected(true);
        tree.add(root);
        ArrayList<CommonTreeNode> children = new ArrayList<CommonTreeNode>();
        formGroupDefines.forEach(e -> children.add(new CommonTreeNode(e.getKey(), e.getCode(), e.getTitle())));
        root.setChildren(children);
        return tree;
    }
}

