/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignTaskDefineService
 *  com.jiuqi.nr.entity.component.tree.service.EntityTreeService
 *  com.jiuqi.nr.entity.component.tree.vo.TreeNode
 *  com.jiuqi.nr.entity.component.tree.vo.TreeParam
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.filterTemplate.service.impl;

import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFilterTemplateService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkDefineService;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import com.jiuqi.nr.entity.component.tree.vo.TreeParam;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.filterTemplate.dao.FilterTemplateDao;
import com.jiuqi.nr.filterTemplate.exception.FilterTemplateRunTimeException;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDO;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.filterTemplate.util.ObjConvert;
import com.jiuqi.nr.filterTemplate.web.vo.FilterTemplateSearchVO;
import com.jiuqi.nr.filterTemplate.web.vo.SaveTipsVO;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FilterTemplateServiceImpl
implements IFilterTemplateService {
    @Autowired
    private FilterTemplateDao filterTemplateDao;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignTaskDefineService taskDefineService;
    @Autowired
    private DesignDataLinkDefineService dataLinkDefineService;
    @Autowired
    private IRunTimeFilterTemplateService runTimeFilterTemplateService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private EntityTreeService entityTreeService;
    @Autowired
    private IDataDefinitionDesignTimeController iDataDefinitionDesignTimeController;
    private static final ObjConvert objConvert = new ObjConvert();

    @Override
    public FilterTemplateDTO init() {
        FilterTemplateDTO entityView = new FilterTemplateDTO();
        entityView.setFilterTemplateID(UUID.randomUUID().toString());
        entityView.setOrder(OrderGenerator.newOrder());
        entityView.setUpdateTime(Instant.now());
        return entityView;
    }

    @Override
    public String insert(FilterTemplateDTO filterTemplateDTO) {
        if (StringUtils.hasText(filterTemplateDTO.getEntityID())) {
            filterTemplateDTO.setEntityID(filterTemplateDTO.getEntityID());
        } else if (StringUtils.hasText(filterTemplateDTO.getFieldKey())) {
            try {
                DesignFieldDefine fieldDefine = this.iDataDefinitionDesignTimeController.queryFieldDefine(filterTemplateDTO.getFieldKey());
                filterTemplateDTO.setEntityID(fieldDefine.getEntityKey());
            }
            catch (Exception e) {
                throw new FilterTemplateRunTimeException("\u67e5\u8be2\u5b9e\u4f53\u51fa\u9519");
            }
        }
        return this.filterTemplateDao.insert(new FilterTemplateDO(filterTemplateDTO));
    }

    @Override
    public void batchInsert(List<FilterTemplateDTO> filterTemplates) {
        List<FilterTemplateDO> filterTemplateDOs = objConvert.FilterTemplateDTO2DO(filterTemplates);
        Assert.notNull(filterTemplates, "filterTemplateDO must not be null.");
        for (FilterTemplateDTO entityViewDefineData : filterTemplates) {
            if (entityViewDefineData.getFilterTemplateID() == null) {
                entityViewDefineData.setFilterTemplateID(UUID.randomUUID().toString());
            }
            if (entityViewDefineData.getOrder() == null) {
                entityViewDefineData.setOrder(OrderGenerator.newOrder());
            }
            if (entityViewDefineData.getUpdateTime() != null) continue;
            entityViewDefineData.setUpdateTime(Instant.now());
        }
        this.filterTemplateDao.batchInsert(filterTemplateDOs.toArray());
    }

    @Override
    public void delete(String filterTemplateID) {
        this.filterTemplateDao.delete(filterTemplateID);
        this.runTimeFilterTemplateService.refreshView(filterTemplateID);
    }

    @Override
    public void batchDelete(List<String> filterTemplateID) {
        this.filterTemplateDao.batchDelete(filterTemplateID.toArray());
    }

    @Override
    public FilterTemplateDTO getFilterTemplate(String filterTemplateID) {
        return new FilterTemplateDTO(this.filterTemplateDao.get(filterTemplateID));
    }

    @Override
    public void update(FilterTemplateDTO filterTemplateDTO) {
        filterTemplateDTO.setUpdateTime(null);
        this.filterTemplateDao.update(new FilterTemplateDO(filterTemplateDTO));
        List allRelTasks = this.taskDefineService.getRelFtTasks(filterTemplateDTO.getFilterTemplateID());
        HashSet taskDefines = new HashSet(allRelTasks);
        if (!CollectionUtils.isEmpty(taskDefines)) {
            for (DesignTaskDefine taskDefine : taskDefines) {
                taskDefine.setUpdateTime(new Date());
                this.designTimeViewController.updateTask(taskDefine);
            }
        }
        List allRelRegions = this.dataLinkDefineService.listAllRegionRelFilterTemplate(filterTemplateDTO.getFilterTemplateID());
        HashSet<String> allRelForms = new HashSet<String>();
        for (String region : allRelRegions) {
            DesignDataRegionDefine dataRegionDefine = this.designTimeViewController.getDataRegion(region);
            String formKey = dataRegionDefine.getFormKey();
            allRelForms.add(formKey);
        }
        if (!CollectionUtils.isEmpty(allRelForms)) {
            for (String formKey : allRelForms) {
                DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
                formDefine.setUpdateTime(new Date());
                this.designTimeViewController.updateForm(formDefine);
            }
        }
        this.runTimeFilterTemplateService.refreshView(filterTemplateDTO.getFilterTemplateID());
    }

    @Override
    public void batchUpdate(List<FilterTemplateDTO> filterTemplateDTO) {
        List<FilterTemplateDO> filterTemplateDOs = objConvert.FilterTemplateDTO2DO(filterTemplateDTO);
        for (FilterTemplateDTO entityViewDefineData : filterTemplateDTO) {
            if (entityViewDefineData.getUpdateTime() != null) continue;
            entityViewDefineData.setUpdateTime(Instant.now());
        }
        this.filterTemplateDao.batchUpdate(filterTemplateDOs.toArray());
    }

    @Override
    public List<FilterTemplateDTO> listAll() {
        return this.listAllFilterTemplate();
    }

    @Override
    public FilterTemplateDTO copy(String filterTemplateID) {
        Assert.notNull((Object)filterTemplateID, "filterTemplateID must not be null");
        FilterTemplateDTO entityViewDefine = this.getFilterTemplate(filterTemplateID);
        if (entityViewDefine != null) {
            FilterTemplateDTO newEntityView = this.init();
            newEntityView.setEntityID(entityViewDefine.getEntityID());
            newEntityView.setFilterTemplateTitle(this.getCopyEntityViewTitle(entityViewDefine.getFilterTemplateTitle()));
            newEntityView.setFilterContent(entityViewDefine.getFilterContent());
            return newEntityView;
        }
        return null;
    }

    @Override
    public SaveTipsVO saveTips(String filterTemplateID) {
        SaveTipsVO saveTipsVO = new SaveTipsVO();
        FilterTemplateDO filterTemplateDO = this.filterTemplateDao.get(filterTemplateID);
        if (filterTemplateDO != null) {
            saveTipsVO.setFilterTemplateID(filterTemplateDO.getFilterTemplateID());
            saveTipsVO.setEntityID(filterTemplateDO.getEntityID());
        }
        ArrayList<Object> allTasks = new ArrayList<Object>();
        List taskDefine = this.taskDefineService.getRelFtTasks(filterTemplateID);
        if (taskDefine != null && taskDefine.size() > 0) {
            Set relDwTasks = taskDefine.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            allTasks.addAll(relDwTasks);
        }
        List allLinks = this.dataLinkDefineService.listRelDataLinks(filterTemplateID);
        for (DesignDataLinkDefine linkDefine : allLinks) {
            String dataRegion = linkDefine.getRegionKey();
            DesignDataRegionDefine dataRegionDefine = this.designTimeViewController.getDataRegion(dataRegion);
            String formKey = dataRegionDefine.getFormKey();
            DesignFormDefine formDefine = this.designTimeViewController.getForm(formKey);
            String formSchemeKey = formDefine.getFormScheme();
            DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
            allTasks.add(formSchemeDefine.getTaskKey());
        }
        saveTipsVO.setTasks(allTasks.size());
        return saveTipsVO;
    }

    @Override
    public List<FilterTemplateDTO> getByEntity(String entityID) {
        return objConvert.FilterTemplateDO2DTO(this.filterTemplateDao.getByEntity(entityID));
    }

    @Override
    public List<FilterTemplateDTO> getByTaskRefEntity(String taskID) {
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(taskID);
        if (taskDefine == null) {
            throw new FilterTemplateRunTimeException("\u672a\u67e5\u8be2\u5230\u4efb\u52a1");
        }
        return this.getByEntity(taskDefine.getDw());
    }

    @Override
    public List<FilterTemplateDTO> getByFormSchemeRefEntity(String formSchemeID) {
        String taskID = this.designTimeViewController.getFormScheme(formSchemeID).getTaskKey();
        return this.getByTaskRefEntity(taskID);
    }

    @Override
    public List<FilterTemplateDTO> getByDataLinkRefEntity(String fieldKey) {
        String entityID;
        try {
            DesignFieldDefine fieldDefine = this.iDataDefinitionDesignTimeController.queryFieldDefine(fieldKey);
            entityID = fieldDefine.getEntityKey();
        }
        catch (Exception e) {
            throw new FilterTemplateRunTimeException("\u672a\u627e\u5230\u5bf9\u5e94\u5b9e\u4f53");
        }
        return this.getByEntity(entityID);
    }

    @Override
    public List<FilterTemplateSearchVO> search(FilterTemplateSearchVO searchVO) {
        ArrayList<FilterTemplateSearchVO> searchResults = new ArrayList<FilterTemplateSearchVO>();
        List<FilterTemplateSearchVO> searchFilterTemplateResults = this.searchFilterTemplate(searchVO.getKeyWords());
        List<FilterTemplateSearchVO> searchEntityResults = this.searchEntity(searchVO.getKeyWords());
        searchResults.addAll(searchFilterTemplateResults);
        searchResults.addAll(searchEntityResults);
        return searchResults;
    }

    private List<FilterTemplateSearchVO> searchFilterTemplate(String keyWords) {
        ArrayList<FilterTemplateSearchVO> searchResults = new ArrayList<FilterTemplateSearchVO>();
        List<FilterTemplateDTO> allFilterTemplates = this.listAllFilterTemplate();
        List<Object> associatedFilterTemplates = new ArrayList();
        if (allFilterTemplates != null && allFilterTemplates.size() > 0) {
            associatedFilterTemplates = allFilterTemplates.stream().filter(filterTemplate -> filterTemplate.getFilterTemplateTitle().contains(keyWords)).collect(Collectors.toList());
        }
        if (associatedFilterTemplates != null && associatedFilterTemplates.size() > 0) {
            for (FilterTemplateDTO entityView : associatedFilterTemplates) {
                FilterTemplateSearchVO searchResult = new FilterTemplateSearchVO();
                searchResult.setResultType(1);
                searchResult.setFilterTemplateID(entityView.getFilterTemplateID());
                searchResult.setFilterTemplateTitle(entityView.getFilterTemplateTitle());
                searchResult.setEntityID(entityView.getEntityID());
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityView.getEntityID());
                String entityTitle = "";
                String entityCode = "";
                if (entityDefine != null) {
                    entityTitle = entityDefine.getTitle();
                    entityCode = entityDefine.getCode();
                }
                searchResult.setPath(entityView.getFilterTemplateTitle() + "/[" + entityCode + "]" + entityTitle);
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }

    private List<FilterTemplateSearchVO> searchEntity(String keyWords) {
        ArrayList<FilterTemplateSearchVO> searchResults = new ArrayList<FilterTemplateSearchVO>();
        TreeParam searchParam = new TreeParam();
        searchParam.setKeyWords(keyWords);
        searchParam.setShowContent(Integer.valueOf(1));
        List searchNodes = this.entityTreeService.searchNodes(searchParam);
        if (searchNodes != null && searchNodes.size() > 0) {
            for (TreeNode treeNode : searchNodes) {
                FilterTemplateSearchVO searchResult = new FilterTemplateSearchVO();
                searchResult.setResultType(2);
                searchResult.setEntityID(treeNode.getKey());
                searchResult.setEntityCode(treeNode.getCode());
                searchResult.setEntityTitle(treeNode.getTitle());
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }

    public List<FilterTemplateDTO> listAllFilterTemplate() {
        List<FilterTemplateDO> filterTemplates = this.filterTemplateDao.ListAll();
        return objConvert.FilterTemplateDO2DTO(filterTemplates);
    }

    public String getCopyEntityViewTitle(String originTitle) {
        int index = 0;
        String newEntityViewBaseTitle = originTitle;
        String newEntityViewTitle = newEntityViewBaseTitle + "_FB";
        List<FilterTemplateDTO> entityViews = this.listAll();
        if (entityViews != null && entityViews.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (FilterTemplateDTO entityView : entityViews) {
                    String title = entityView.getFilterTemplateTitle();
                    if (!newEntityViewTitle.equals(title)) continue;
                    newEntityViewTitle = newEntityViewBaseTitle + "_FB" + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        if (newEntityViewTitle.length() > 50) {
            throw new FilterTemplateRunTimeException("\u590d\u5236\u6a21\u677f\u540d\u79f0\u8d85\u8fc750\uff0c\u8bf7\u4fee\u6539\u540d\u5b57");
        }
        return newEntityViewTitle;
    }
}

