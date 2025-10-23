/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.migration.syncscheme.controller;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;
import com.jiuqi.nr.migration.syncscheme.exception.SyncSchemeErrorEnum;
import com.jiuqi.nr.migration.syncscheme.service.ISyncSchemeGroupService;
import com.jiuqi.nr.migration.syncscheme.service.ISyncSchemeService;
import com.jiuqi.nr.migration.syncscheme.tree.ISyncSchemeGroupTreeService;
import com.jiuqi.nr.migration.syncscheme.tree.SyncSchemeGroupTreeNode;
import com.jiuqi.nr.migration.syncscheme.tree.TreeNode;
import com.jiuqi.nr.migration.syncscheme.vo.CheckParam;
import com.jiuqi.nr.migration.syncscheme.vo.SchemeData;
import com.jiuqi.nr.migration.syncscheme.vo.SyncSchemeGroupVO;
import com.jiuqi.nr.migration.syncscheme.vo.SyncSchemeVO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/syncscheme"})
public class SyncSchemeController {
    @Autowired
    private ISyncSchemeGroupService syncSchemeGroupService;
    @Autowired
    private ISyncSchemeService syncSchemeService;
    @Autowired
    private ISyncSchemeGroupTreeService syncSchemeGroupTreeService;

    @PostMapping(value={"/check-group-title"})
    public void checkGroupTitle(@RequestBody CheckParam checkParam) throws JQException {
        try {
            SyncSchemeGroup group = new SyncSchemeGroup();
            group.setKey(checkParam.getKey());
            group.setTitle(checkParam.getTitle());
            group.setParent(checkParam.getGroup());
            this.syncSchemeGroupService.check(checkParam.isUpdate(), group);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_TITLE_HAS_EXIST, (Throwable)e);
        }
    }

    @PostMapping(value={"/add-group"})
    public String addGroup(@RequestBody SyncSchemeGroup group) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(group.getParent())) {
                group.setParent(null);
            }
            this.syncSchemeGroupService.add(group);
            return group.getKey();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_ADD_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/update-group"})
    public String updateGroup(@RequestBody SyncSchemeGroup group) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(group.getParent())) {
                group.setParent(null);
            }
            this.syncSchemeGroupService.update(group);
            return group.getKey();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_UPDATE_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/delete-group/{key}"})
    public void deleteGroup(@PathVariable(value="key") String key) throws JQException {
        try {
            this.syncSchemeGroupService.delete(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_DEL_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/moveup-group/{key}"})
    public void moveUpGroup(@PathVariable(value="key") String key) throws JQException {
        try {
            this.syncSchemeGroupService.moveUp(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_MOVEUP_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/movedown-group/{key}"})
    public void moveDownGroup(@PathVariable(value="key") String key) throws JQException {
        try {
            this.syncSchemeGroupService.moveDown(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_MOVEDOWN_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/group-list/{parentKey}"})
    public List<SyncSchemeGroupVO> getGroupList(@PathVariable(value="parentKey") String parentKey) throws JQException {
        try {
            return this.syncSchemeGroupService.getByParent(parentKey).stream().map(SyncSchemeGroupVO::new).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_QUERY_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/group-tree/root"})
    public List<TreeNode<SyncSchemeGroupTreeNode>> getRoot() throws JQException {
        try {
            return this.syncSchemeGroupTreeService.getRoot();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_TREE_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/group-tree/children/{parentKey}"})
    public List<TreeNode<SyncSchemeGroupTreeNode>> getChildren(@PathVariable(value="parentKey") String parentKey) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(parentKey)) {
                parentKey = null;
            }
            return this.syncSchemeGroupTreeService.getChildren(parentKey);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_TREE_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/group-tree/location/{key}"})
    public List<TreeNode<SyncSchemeGroupTreeNode>> groupLocation(@PathVariable(value="key") String key) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(key)) {
                key = null;
            }
            return this.syncSchemeGroupTreeService.location(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEMEGROUP_TREE_LOCATION_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/check-scheme-title"})
    public void checkSchemeTitle(@RequestBody CheckParam checkParam) throws JQException {
        try {
            SyncScheme scheme = new SyncScheme();
            scheme.setKey(checkParam.getKey());
            scheme.setTitle(checkParam.getTitle());
            this.syncSchemeService.check(checkParam.isUpdate(), scheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_TITLE_HAS_EXIST, (Throwable)e);
        }
    }

    @PostMapping(value={"/check-scheme-code"})
    public void checkSchemeCode(@RequestBody CheckParam checkParam) throws JQException {
        try {
            SyncScheme scheme = new SyncScheme();
            scheme.setKey(checkParam.getKey());
            scheme.setCode(checkParam.getCode());
            this.syncSchemeService.check(checkParam.isUpdate(), scheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_CODE_HAS_EXIST, (Throwable)e);
        }
    }

    @PostMapping(value={"/add-scheme"})
    public String addScheme(@RequestBody SyncScheme scheme) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(scheme.getGroup())) {
                scheme.setGroup(null);
            }
            this.syncSchemeService.add(scheme);
            return scheme.getKey();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_ADD_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/get-scheme-data/{schemeKey}"})
    public String getSchemeData(@PathVariable(value="schemeKey") String schemeKey) throws JQException {
        try {
            SyncScheme byKey = this.syncSchemeService.getByKey(schemeKey);
            if (byKey.getData() == null) {
                return "";
            }
            return byKey.getData();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_QUERY_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/update-scheme"})
    public String updateScheme(@RequestBody SyncScheme scheme) throws JQException {
        try {
            if ("00000000-0000-0000-0000-000000000000".equals(scheme.getGroup())) {
                scheme.setGroup(null);
            }
            this.syncSchemeService.update(scheme);
            return scheme.getKey();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_UPDATE_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/save-scheme-data"})
    public void saveSchemeData(@RequestBody SchemeData schemeData) throws JQException {
        try {
            SyncScheme scheme = this.syncSchemeService.getByKey(schemeData.getKey());
            scheme.setData(schemeData.getData());
            scheme.setUpdateTime(new Date().getTime());
            this.syncSchemeService.updateData(scheme);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_DATA_SAVE_ERROR, (Throwable)e);
        }
    }

    @PostMapping(value={"/delete-scheme"})
    public void batchDeleteScheme(@RequestBody List<String> keys) throws JQException {
        try {
            this.syncSchemeService.batchDelete(keys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_DEL_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/moveup-scheme/{key}"})
    public void moveUpScheme(@PathVariable(value="key") String key) throws JQException {
        try {
            this.syncSchemeService.moveUp(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_MOVEUP_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/movedown-scheme/{key}"})
    public void moveDownScheme(@PathVariable(value="key") String key) throws JQException {
        try {
            this.syncSchemeService.moveDown(key);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_MOVEDOWN_ERROR, (Throwable)e);
        }
    }

    @GetMapping(value={"/scheme-list/{parentKey}"})
    public List<SyncSchemeVO> getSchemeList(@PathVariable(value="parentKey") String parentKey) throws JQException {
        try {
            ArrayList<SyncSchemeVO> result = new ArrayList<SyncSchemeVO>();
            if ("00000000-0000-0000-0000-000000000000".equals(parentKey)) {
                parentKey = null;
            }
            HashMap<String, String> groupTitleMap = new HashMap<String, String>();
            List<SyncScheme> schemeList = this.syncSchemeService.getByGroup(parentKey);
            for (int i = 0; i < schemeList.size(); ++i) {
                SyncScheme scheme = schemeList.get(i);
                if (scheme.getGroup() == null) {
                    SyncSchemeVO schemeVO = new SyncSchemeVO(scheme, "\u5168\u90e8\u6570\u636e\u540c\u6b65\u65b9\u6848");
                    schemeVO.setFirst(i == 0);
                    schemeVO.setLast(i == schemeList.size() - 1);
                    result.add(schemeVO);
                    continue;
                }
                String groupTitle = (String)groupTitleMap.get(scheme.getGroup());
                if (groupTitle == null) {
                    groupTitle = this.syncSchemeGroupService.getByKey(scheme.getGroup()).getTitle();
                    groupTitleMap.put(scheme.getGroup(), groupTitle);
                }
                SyncSchemeVO schemeVO = new SyncSchemeVO(scheme, groupTitle);
                schemeVO.setFirst(i == 0);
                schemeVO.setLast(i == schemeList.size() - 1);
                result.add(schemeVO);
            }
            return result;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)SyncSchemeErrorEnum.SYNCSCHEME_QUERY_ERROR, (Throwable)e);
        }
    }
}

