/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.template.vo.TemplateInfoVO
 *  com.jiuqi.va.query.tree.enumerate.TreeNodeType
 *  com.jiuqi.va.query.tree.vo.GroupVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.query.tree.service.impl;

import com.jiuqi.va.query.cache.QueryCacheManage;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.template.dao.QueryTemplateInfoDao;
import com.jiuqi.va.query.template.dao.TemplateInfoDao;
import com.jiuqi.va.query.template.service.TemplateDesignService;
import com.jiuqi.va.query.template.vo.TemplateInfoVO;
import com.jiuqi.va.query.tree.dao.QueryGroupDao;
import com.jiuqi.va.query.tree.enumerate.TreeNodeType;
import com.jiuqi.va.query.tree.service.MenuTreeService;
import com.jiuqi.va.query.tree.vo.GroupVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class MenuTreeServiceImpl
implements MenuTreeService {
    @Autowired
    private TemplateInfoDao templateInfoDao;
    @Autowired
    private QueryGroupDao queryGroupDao;
    @Autowired
    private QueryTemplateInfoDao queryTemplateInfoDao;
    @Autowired
    private QueryCacheManage queryCacheManage;
    @Autowired
    private TemplateDesignService designService;

    @Override
    public List<MenuTreeVO> getTree() {
        return this.queryCacheManage.initTree(() -> {
            List<GroupVO> allGroups = this.queryGroupDao.getAllGroups();
            if (CollectionUtils.isEmpty(allGroups)) {
                return new ArrayList();
            }
            List groupMenuList = allGroups.stream().map(this::groupToMenuTreeVO).collect(Collectors.toList());
            List<TemplateInfoVO> allTemplates = this.queryTemplateInfoDao.getAllTemplates();
            if (CollectionUtils.isEmpty(allTemplates)) {
                return groupMenuList;
            }
            List<MenuTreeVO> templateMenuList = allTemplates.stream().map(this::templateInfoToMenuTreeVO).collect(Collectors.toList());
            templateMenuList.addAll(groupMenuList);
            return this.collectionToCacheTree(templateMenuList);
        });
    }

    @Override
    public MenuTreeVO getMenuByParam(List<MenuTreeVO> tree, List<MenuTreeVO> parent, Predicate<MenuTreeVO> predicate) {
        if (tree == null || tree.isEmpty()) {
            return null;
        }
        for (MenuTreeVO c : tree) {
            MenuTreeVO data;
            if (predicate.test(c)) {
                parent.add(c);
                return c;
            }
            if (c.getChildren() == null || c.getChildren().isEmpty() || (data = this.getMenuByParam(c.getChildren(), parent, predicate)) == null) continue;
            parent.add(c);
            return data;
        }
        return null;
    }

    @Override
    public MenuTreeVO getMenuVO(String id) {
        return this.getMenuByParam(this.getTree(), vo -> vo.getId().equals(id));
    }

    @Override
    public List<MenuTreeVO> treeInit() {
        LinkedList<MenuTreeVO> result = new LinkedList<MenuTreeVO>();
        List<GroupVO> groups = this.queryGroupDao.getGroupsByParentGroupId("00000000-0000-0000-0000-000000000000");
        for (GroupVO groupVO : groups) {
            MenuTreeVO rootMenu = this.groupToMenuTreeVO(groupVO);
            this.initMenuTreeChildren(rootMenu, groupVO);
            result.add(rootMenu);
        }
        return result;
    }

    @Override
    public List<MenuTreeVO> treeInit(List<String> templateIds) {
        LinkedList<MenuTreeVO> result = new LinkedList<MenuTreeVO>();
        List<GroupVO> groups = this.queryGroupDao.getGroupsByParentGroupId("00000000-0000-0000-0000-000000000000");
        for (GroupVO groupVO : groups) {
            MenuTreeVO rootMenu = this.groupToMenuTreeVO(groupVO);
            if (!this.initMenuTreeChildrenByTemplateIds(rootMenu, groupVO, templateIds)) continue;
            result.add(rootMenu);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void querySave(TemplateInfoVO baseInfoVO) {
        TemplateInfoVO templateInfoByCode;
        if (!StringUtils.hasText(baseInfoVO.getId())) {
            baseInfoVO.setId(DCQueryUUIDUtil.getUUIDStr());
        }
        if ((templateInfoByCode = this.templateInfoDao.getTemplateInfoByCode(baseInfoVO.getCode())) != null) {
            throw new DefinedQueryRuntimeException("\u91cd\u590d\u7684\u6807\u8bc6\uff1a" + baseInfoVO.getCode());
        }
        this.templateInfoDao.saveTemplateInfo(baseInfoVO);
        this.queryCacheManage.clearTreeCache();
    }

    private boolean initMenuTreeChildrenByTemplateIds(MenuTreeVO rootMenu, GroupVO groupVO, List<String> templateIds) {
        LinkedList<MenuTreeVO> childrenList = new LinkedList<MenuTreeVO>();
        List<GroupVO> groups = this.queryGroupDao.getGroupsByParentGroupId(groupVO.getId());
        for (GroupVO group : groups) {
            MenuTreeVO child = this.groupToMenuTreeVO(group);
            if (!this.initMenuTreeChildrenByTemplateIds(child, group, templateIds)) continue;
            childrenList.add(child);
        }
        List<TemplateInfoVO> templates = this.queryTemplateInfoDao.getTemplatesByGroupId(groupVO.getId());
        for (TemplateInfoVO templateInfo : templates) {
            if (!templateIds.contains(templateInfo.getId())) continue;
            MenuTreeVO templateChild = this.templateInfoToMenuTreeVO(templateInfo);
            childrenList.add(templateChild);
        }
        rootMenu.setChildren(childrenList);
        return !childrenList.isEmpty();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String groupSave(GroupVO groupVO) {
        List<GroupVO> groups;
        if (this.queryGroupDao.hasGroupByCode(groupVO.getCode())) {
            throw new DefinedQueryRuntimeException("\u8be5\u5206\u7ec4\u6807\u8bc6\u5df2\u7ecf\u5b58\u5728\uff0c\u4fdd\u5b58\u5931\u8d25\uff01");
        }
        if (DCQueryStringHandle.isEmpty(groupVO.getParentId())) {
            groupVO.setParentId("00000000-0000-0000-0000-000000000000");
        }
        if ((groups = this.queryGroupDao.getGroupsByParentGroupId(groupVO.getParentId())) == null || groups.isEmpty()) {
            groupVO.setSortOrder(1);
        } else {
            groupVO.setSortOrder(groups.get(groups.size() - 1).getSortOrder() + 1);
        }
        String uuidstr = DCQueryUUIDUtil.getUUIDStr();
        if (groupVO.getId() == null) {
            groupVO.setId(uuidstr);
        }
        this.queryGroupDao.save(groupVO);
        this.queryCacheManage.clearTreeCache();
        return uuidstr;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void groupDelete(String groupId) {
        if (Boolean.TRUE.equals(this.queryTemplateInfoDao.hasTemplatesByGroupId(groupId))) {
            throw new DefinedQueryRuntimeException("\u5206\u7ec4\u4e0b\u8fd8\u6709\u672a\u5220\u9664\u7684\u67e5\u8be2\u4fe1\u606f\uff0c\u4e0d\u5141\u8bb8\u5220\u9664");
        }
        this.queryGroupDao.delete(groupId);
        this.queryCacheManage.clearTreeCache();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void templateDelete(String templateId) {
        this.designService.removeTemplate(templateId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void groupUpdate(GroupVO groupVO) {
        if (!this.queryGroupDao.hasGroupByGroupId(groupVO.getId())) {
            throw new DefinedQueryRuntimeException("\u8981\u4fee\u6539\u7684\u5206\u7ec4\u4e0d\u5b58\u5728\uff0c\u53ef\u80fd\u5df2\u7ecf\u88ab\u5220\u9664");
        }
        this.queryGroupDao.update(groupVO);
        this.queryCacheManage.clearTreeCache();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveMenuTree(List<MenuTreeVO> menuTree, Map<String, TemplateInfoVO> templateIdToVOMap, List<String> skipImportCodeList, Map<String, String> groupMap) {
        MenuTreeServiceImpl menuTreeService = DCQuerySpringContextUtils.getBean(MenuTreeServiceImpl.class);
        for (MenuTreeVO menuTreeVO : menuTree) {
            if (TreeNodeType.GROUP.toString().equals(menuTreeVO.getNodeType())) {
                GroupVO groupVO = new GroupVO();
                groupVO.setId(menuTreeVO.getId());
                groupVO.setCode(menuTreeVO.getCode());
                groupVO.setDescription(menuTreeVO.getDescription());
                groupVO.setParentId(menuTreeVO.getParentId());
                groupVO.setTitle(menuTreeVO.getTitle());
                GroupVO curGroup = this.queryGroupDao.getGroupByCode(menuTreeVO.getCode());
                if (curGroup != null) {
                    groupMap.put(menuTreeVO.getId(), curGroup.getId());
                    this.queryGroupDao.updateByCode(groupVO);
                } else {
                    String actGrpID;
                    String parentId = groupVO.getParentId();
                    if (StringUtils.hasText(parentId) && StringUtils.hasText(actGrpID = groupMap.get(parentId))) {
                        groupVO.setParentId(actGrpID);
                    }
                    menuTreeService.groupSave(groupVO);
                }
            }
            if (menuTreeVO.getChildren() != null && !menuTreeVO.getChildren().isEmpty()) {
                menuTreeService.saveMenuTree(menuTreeVO.getChildren(), templateIdToVOMap, skipImportCodeList, groupMap);
            }
            if (!TreeNodeType.QUERY.toString().equals(menuTreeVO.getNodeType()) || skipImportCodeList.contains(menuTreeVO.getCode())) continue;
            TemplateInfoVO templateInfo = templateIdToVOMap.get(menuTreeVO.getCode());
            TemplateInfoVO templatesByCode = this.queryTemplateInfoDao.getTemplatesByCode(menuTreeVO.getCode());
            if (Objects.nonNull(templatesByCode)) {
                this.queryTemplateInfoDao.updateByCode(templateInfo);
                continue;
            }
            List<TemplateInfoVO> templates = this.queryTemplateInfoDao.getTemplatesByGroupId(menuTreeVO.getParentId());
            if (templates == null || templates.isEmpty()) {
                templateInfo.setSortOrder(Integer.valueOf(1));
            } else {
                templateInfo.setSortOrder(Integer.valueOf(templates.get(templates.size() - 1).getSortOrder() + 1));
            }
            String actualGrpID = groupMap.get(templateInfo.getGroupId());
            if (actualGrpID != null) {
                templateInfo.setGroupId(actualGrpID);
            }
            this.queryTemplateInfoDao.save(templateInfo);
        }
    }

    private MenuTreeVO groupToMenuTreeVO(GroupVO groupVO) {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setCode(groupVO.getCode());
        menuTreeVO.setDescription(groupVO.getDescription());
        menuTreeVO.setId(groupVO.getId());
        menuTreeVO.setNodeType(TreeNodeType.GROUP.toString());
        menuTreeVO.setParentId(groupVO.getParentId());
        menuTreeVO.setSortOrder(Integer.valueOf(groupVO.getSortOrder()));
        menuTreeVO.setTitle(groupVO.getTitle());
        return menuTreeVO;
    }

    private void initMenuTreeChildren(MenuTreeVO rootMenu, GroupVO groupVO) {
        LinkedList<MenuTreeVO> childrenList = new LinkedList<MenuTreeVO>();
        List<GroupVO> groups = this.queryGroupDao.getGroupsByParentGroupId(groupVO.getId());
        for (GroupVO group : groups) {
            MenuTreeVO child = this.groupToMenuTreeVO(group);
            this.initMenuTreeChildren(child, group);
            childrenList.add(child);
        }
        List<TemplateInfoVO> templates = this.queryTemplateInfoDao.getTemplatesByGroupId(groupVO.getId());
        for (TemplateInfoVO templateInfo : templates) {
            MenuTreeVO templateChild = this.templateInfoToMenuTreeVO(templateInfo);
            childrenList.add(templateChild);
        }
        rootMenu.setChildren(childrenList);
    }

    private MenuTreeVO templateInfoToMenuTreeVO(TemplateInfoVO templateInfo) {
        MenuTreeVO menuTreeVO = new MenuTreeVO();
        menuTreeVO.setCode(templateInfo.getCode());
        menuTreeVO.setDescription(templateInfo.getDescription());
        menuTreeVO.setId(templateInfo.getId());
        menuTreeVO.setNodeType(TreeNodeType.QUERY.toString());
        menuTreeVO.setParentId(templateInfo.getGroupId());
        menuTreeVO.setSortOrder(templateInfo.getSortOrder());
        menuTreeVO.setTitle(templateInfo.getTitle());
        menuTreeVO.setDatasourceCode(templateInfo.getDatasourceCode());
        menuTreeVO.setType(templateInfo.getType());
        return menuTreeVO;
    }

    @Override
    public List<TemplateInfoVO> listInit() {
        return this.queryTemplateInfoDao.getAllTemplates();
    }

    protected List<MenuTreeVO> collectionToCacheTree(Collection<MenuTreeVO> list) {
        Map datas = list.stream().collect(Collectors.toMap(MenuTreeVO::getId, Function.identity(), (o1, o2) -> o1));
        HashMap<String, List<MenuTreeVO>> fatherSonMap = new HashMap<String, List<MenuTreeVO>>();
        for (MenuTreeVO menuTreeVO : list) {
            ArrayList<MenuTreeVO> children;
            String parentId = menuTreeVO.getParentId();
            if (!StringUtils.hasText(parentId) || !datas.containsKey(parentId)) {
                parentId = "00000000-0000-0000-0000-000000000000";
            }
            if ((children = (ArrayList<MenuTreeVO>)fatherSonMap.get(parentId)) == null) {
                children = new ArrayList<MenuTreeVO>();
            }
            children.add(menuTreeVO);
            fatherSonMap.put(parentId, children);
        }
        List topNodes = (List)fatherSonMap.get("00000000-0000-0000-0000-000000000000");
        for (MenuTreeVO orgDO : topNodes) {
            orgDO.setParentId("00000000-0000-0000-0000-000000000000");
            orgDO.setParents(orgDO.getCode());
            this.resetparents(orgDO, fatherSonMap);
        }
        ArrayList<MenuTreeVO> arrayList = new ArrayList<MenuTreeVO>();
        list.forEach(vo -> {
            MenuTreeVO pobj = (MenuTreeVO)datas.get(vo.getParentId());
            if (pobj != null) {
                pobj.addChildren((MenuTreeVO)datas.get(vo.getId()));
            } else {
                tree.add((MenuTreeVO)datas.get(vo.getId()));
            }
        });
        return arrayList;
    }

    protected MenuTreeVO getMenuByParam(List<MenuTreeVO> tree, Predicate<MenuTreeVO> predicate) {
        if (tree == null || tree.isEmpty()) {
            return null;
        }
        for (MenuTreeVO c : tree) {
            MenuTreeVO data;
            if (predicate.test(c)) {
                return c;
            }
            if (c.getChildren() == null || c.getChildren().isEmpty() || (data = this.getMenuByParam(c.getChildren(), predicate)) == null) continue;
            return data;
        }
        return null;
    }

    private void resetparents(MenuTreeVO father, Map<String, List<MenuTreeVO>> fatherSonMap) {
        if (father == null || fatherSonMap == null || fatherSonMap.isEmpty()) {
            return;
        }
        List<MenuTreeVO> children = fatherSonMap.get(father.getId());
        if (children == null || children.isEmpty()) {
            return;
        }
        for (MenuTreeVO child : children) {
            String grandParents = father.getParents();
            grandParents = StringUtils.hasText(grandParents) ? grandParents + "/" : "";
            child.setParents(grandParents + child.getCode());
            this.resetparents(child, fatherSonMap);
        }
    }
}

