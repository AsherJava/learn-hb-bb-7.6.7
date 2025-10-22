/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.paramio.CustomBusiness
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate
 *  com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplateImpl
 *  com.jiuqi.nr.unit.treestore.uselector.dao.IFilterSchemeDao
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.transfer;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.paramio.CustomBusiness;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplate;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplateImpl;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterSchemeDao;
import com.jiuqi.nr.unit.uselector.transfer.FilterTemplate;
import com.jiuqi.nr.unit.uselector.transfer.FilterTemplateDTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FilterTemplateBusiness
implements CustomBusiness {
    @Resource
    private IDesignTimeViewController iDesignTimeViewController;
    @Resource
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Resource
    private IFilterSchemeDao filterSchemeDao;

    public boolean checkBusiness(String businessKey, String taskKey) {
        return true;
    }

    public IMetaItem getData(String taskKey) {
        AtomicBoolean isDisplay = new AtomicBoolean(false);
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return null;
        }
        String owner = NpContextHolder.getContext().getUserId();
        List filterSchemeTemplates = this.filterSchemeDao.find(owner, taskDefine.getDw());
        if (filterSchemeTemplates != null && !filterSchemeTemplates.isEmpty()) {
            isDisplay.set(true);
        }
        if (!isDisplay.get()) {
            List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
            dataSchemeDimension.forEach(dimension -> {
                List filterSchemeTemplates_scene = this.filterSchemeDao.find(owner, dimension.getDimKey());
                if (filterSchemeTemplates_scene != null && !filterSchemeTemplates_scene.isEmpty()) {
                    isDisplay.set(true);
                }
            });
        }
        if (!isDisplay.get()) {
            return null;
        }
        FilterTemplate filterTemplate = new FilterTemplate();
        filterTemplate.setKey(taskKey);
        filterTemplate.setTitle("\u5355\u4f4d\u9009\u62e9\u5668\u9ad8\u7ea7\u7b5b\u9009\u65b9\u6848");
        filterTemplate.setUpdateTime(new Date());
        return filterTemplate;
    }

    public byte[] exportData(String businessKey, String taskKey) {
        byte[] bytes;
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return null;
        }
        String owner = NpContextHolder.getContext().getUserId();
        List filterSchemeTemplates = this.filterSchemeDao.find(owner, taskDefine.getDw());
        ArrayList datas = new ArrayList();
        filterSchemeTemplates.forEach(tem -> {
            FilterTemplateDTO filterTemplate = new FilterTemplateDTO();
            filterTemplate.setKey(tem.getKey());
            filterTemplate.setTitle(tem.getTitle());
            filterTemplate.setOwner(tem.getOwner());
            filterTemplate.setEntityId(tem.getEntityId());
            filterTemplate.setShared(tem.isShared());
            filterTemplate.setTemplateJSON(tem.getTemplate().toJSON());
            filterTemplate.setCreateTime(tem.getCreateTime());
            datas.add(filterTemplate);
        });
        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        dataSchemeDimension.forEach(dimension -> {
            List filterSchemeTemplates_scene = this.filterSchemeDao.find(owner, dimension.getDimKey());
            filterSchemeTemplates_scene.forEach(tem -> {
                FilterTemplateDTO filterTemplate = new FilterTemplateDTO();
                filterTemplate.setKey(tem.getKey());
                filterTemplate.setTitle(tem.getTitle());
                filterTemplate.setOwner(tem.getOwner());
                filterTemplate.setEntityId(tem.getEntityId());
                filterTemplate.setShared(tem.isShared());
                filterTemplate.setTemplateJSON(tem.getTemplate().toJSON());
                filterTemplate.setCreateTime(tem.getCreateTime());
                datas.add(filterTemplate);
            });
        });
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(datas);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public void importData(String businessKey, String taskKey, byte[] data) {
        DesignTaskDefine taskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            return;
        }
        List dataSchemeDimension = this.iDesignDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
        List dimKeys = dataSchemeDimension.stream().map(DataDimension::getDimKey).collect(Collectors.toList());
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(data);
            ObjectInputStream oi = new ObjectInputStream(bi);
            List datas = (List)oi.readObject();
            datas.forEach(tem -> {
                if (this.filterSchemeDao.find(tem.getKey()) != null) {
                    USFilterSchemeImpl filterScheme = new USFilterSchemeImpl();
                    filterScheme.setKey(tem.getKey());
                    filterScheme.setTitle(tem.getTitle());
                    filterScheme.setOwner(NpContextHolder.getContext().getUserId());
                    filterScheme.setEntityId(tem.getEntityId());
                    filterScheme.setShared(tem.isShared());
                    filterScheme.setTemplate((USFilterTemplate)new USFilterTemplateImpl(tem.getTemplateJSON()));
                    filterScheme.setCreateTime(tem.getCreateTime());
                    this.filterSchemeDao.update((USFilterScheme)filterScheme);
                } else if (tem.getEntityId().equals(taskDefine.getDw()) || dimKeys.contains(tem.getEntityId())) {
                    USFilterSchemeImpl filterScheme = new USFilterSchemeImpl();
                    filterScheme.setKey(tem.getKey());
                    filterScheme.setTitle(tem.getTitle());
                    filterScheme.setOwner(NpContextHolder.getContext().getUserId());
                    filterScheme.setEntityId(tem.getEntityId());
                    filterScheme.setShared(tem.isShared());
                    filterScheme.setTemplate((USFilterTemplate)new USFilterTemplateImpl(tem.getTemplateJSON()));
                    filterScheme.setCreateTime(tem.getCreateTime());
                    this.filterSchemeDao.insert((USFilterScheme)filterScheme);
                }
            });
            bi.close();
            oi.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

