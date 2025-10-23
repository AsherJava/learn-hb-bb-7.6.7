/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.ApiOperation
 *  org.apache.shiro.authz.annotation.RequiresPermissions
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.zbquery.bean.ZBQueryGroup;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.bean.ZBQueryParam;
import com.jiuqi.nr.zbquery.common.ZBQueryConst;
import com.jiuqi.nr.zbquery.manage.ZBQueryManageDataProvider;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.vo.ModelGroupTreeNodeVO;
import com.jiuqi.nr.zbquery.rest.vo.QueryParamVO;
import com.jiuqi.nr.zbquery.service.ZBQueryGroupService;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.ZBQueryLogHelper;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@JQRestController
@RequestMapping(value={"/api/zbquery/manage/info"})
public class ZBQueryInfoController {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryManageDataProvider.class);
    @Autowired
    private ZBQueryInfoService zbQueryInfoService;
    @Autowired
    private ZBQueryGroupService zbQueryGroupService;

    @GetMapping(value={"/get_queryinfos/{groupId}"})
    public List<ZBQueryInfo> getQueryInfos(@PathVariable String groupId) {
        return this.zbQueryInfoService.getQueryInfoByGroup(groupId);
    }

    @PostMapping(value={"/add_queryinfo"})
    public void addQueryInfo(@RequestBody ZBQueryInfo zbQueryInfo) throws JQException {
        try {
            if (zbQueryInfo.getLevel() == null) {
                zbQueryInfo.setLevel("0");
            }
            this.zbQueryInfoService.addQueryInfo(zbQueryInfo);
            ZBQueryLogHelper.info("\u65b0\u589e\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u65b0\u589e\u6210\u529f", zbQueryInfo.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u65b0\u589e\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u65b0\u589e\u5931\u8d25\uff1a%s", zbQueryInfo.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value={"/save_as_queryinfo"})
    public void saveAsQueryInfo(@RequestBody ZBQueryInfo zbQueryInfo) throws JQException {
        try {
            if (zbQueryInfo.getLevel() == null) {
                zbQueryInfo.setLevel("0");
            }
            this.zbQueryInfoService.saveAsQueryInfo("", zbQueryInfo);
            ZBQueryLogHelper.info("\u53e6\u5b58\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u53e6\u5b58\u6210\u529f", zbQueryInfo.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u53e6\u5b58\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u53e6\u5b58\u5931\u8d25\uff1a%s", zbQueryInfo.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value={"/modify_queryinfo"})
    public void modifyQueryInfos(@RequestBody ZBQueryInfo zbQueryInfo) throws JQException {
        try {
            if (zbQueryInfo.getLevel() == null) {
                zbQueryInfo.setLevel("0");
            }
            this.zbQueryInfoService.modifyQueryInfo(zbQueryInfo);
            ZBQueryLogHelper.info("\u4fee\u6539\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u4fee\u6539\u6210\u529f", zbQueryInfo.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u4fee\u6539\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f[%s]\u4fee\u6539\u5931\u8d25\uff1a%s", zbQueryInfo.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping(value={"/delete_queryinfo"})
    public String deleteQueryInfos(@RequestBody List<String> queryInfoIds) throws JQException {
        try {
            String msg = this.zbQueryInfoService.deleteQueryInfoByIds(queryInfoIds);
            ZBQueryLogHelper.info("\u5220\u9664\u67e5\u8be2\u6a21\u677f", "\u6a21\u677f\u5220\u9664\u6210\u529f");
            return msg;
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u5220\u9664\u67e5\u8be2\u6a21\u677f", String.format("\u6a21\u677f\u5220\u9664\u5931\u8d25\uff1a%s", e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping(value={"/init_queryinfo_grouptree/{groupId}"})
    public List<ModelGroupTreeNodeVO> initQueryInfoGroupTree(@PathVariable String groupId) {
        Map<String, List<ZBQueryGroup>> groupMap = this.zbQueryGroupService.getParentIdMap(true);
        ArrayList<String> groupIds = new ArrayList<String>();
        ModelGroupTreeNodeVO modelGroup = new ModelGroupTreeNodeVO();
        modelGroup.setId("00000000-0000-0000-0000-000000000000");
        modelGroup.setLabel(ZBQueryConst.TITLE_ALLZBQUERYGROUPS);
        this.zbQueryGroupService.getParentId(groupIds, groupId);
        return this.getChildNode(groupMap, modelGroup, groupId, groupIds);
    }

    private List<ModelGroupTreeNodeVO> getChildNode(Map<String, List<ZBQueryGroup>> groupMap, ModelGroupTreeNodeVO node, String groupId, List<String> groupIds) {
        ArrayList<ModelGroupTreeNodeVO> childrenTreeNodes = new ArrayList<ModelGroupTreeNodeVO>();
        if (groupMap.containsKey(node.getId())) {
            List<ZBQueryGroup> list = groupMap.get(node.getId());
            for (ZBQueryGroup zBQueryGroup : list) {
                ModelGroupTreeNodeVO modelGroup = new ModelGroupTreeNodeVO();
                modelGroup.setId(zBQueryGroup.getId());
                modelGroup.setLabel(zBQueryGroup.getTitle());
                if (groupIds.contains(zBQueryGroup.getId())) {
                    modelGroup.setIsDefaultExpanded(true);
                } else {
                    modelGroup.setIsDefaultExpanded(false);
                }
                modelGroup.setChildren(this.getChildNode(groupMap, modelGroup, groupId, groupIds));
                childrenTreeNodes.add(modelGroup);
            }
        }
        return childrenTreeNodes;
    }

    @ApiOperation(value="\u4fdd\u5b58\u6307\u6807\u67e5\u8be2\u6a21\u578b")
    @PostMapping(value={"/add_querymodel"})
    public void saveQueryInfoData(@RequestBody ZBQueryModel zbQueryModel, @RequestParam String zbQueryInfoId) throws JQException {
        ZBQueryInfo zbQueryInfo = this.zbQueryInfoService.getQueryInfoById(zbQueryInfoId);
        try {
            this.zbQueryInfoService.saveQueryInfoData(zbQueryModel, zbQueryInfoId);
            ZBQueryLogHelper.info("\u4fdd\u5b58\u6307\u6807\u67e5\u8be2\u6a21\u578b", String.format("\u6a21\u677f[%s]\u6307\u6807\u67e5\u8be2\u6a21\u578b\u4fdd\u5b58\u6210\u529f", zbQueryInfo.getTitle()));
        }
        catch (JQException e) {
            ZBQueryLogHelper.error("\u4fdd\u5b58\u6307\u6807\u67e5\u8be2\u6a21\u578b", String.format("\u6a21\u677f[%s]\u6307\u6807\u67e5\u8be2\u6a21\u578b\u4fdd\u5b58\u5931\u8d25\uff1a%s", zbQueryInfo.getTitle(), e.getMessage()));
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u67e5\u8be2\u6a21\u578b")
    @GetMapping(value={"/get_querymodel/{zbQueryInfoId}"})
    public ZBQueryModel getQueryInfoData(@PathVariable String zbQueryInfoId) throws JQException {
        return this.zbQueryInfoService.getQueryInfoData(zbQueryInfoId);
    }

    @ApiOperation(value="\u83b7\u53d6\u7a7a\u6307\u6807\u67e5\u8be2\u6a21\u578b-\u542b\u7cfb\u7edf\u9009\u9879\u914d\u7f6e ")
    @GetMapping(value={"/get_empty_querymodel"})
    public ZBQueryModel getEmptyQueryInfoData() {
        return this.zbQueryInfoService.createQueryModel();
    }

    @ApiOperation(value="\u83b7\u53d6\u6307\u6807\u67e5\u8be2\u6a21\u578b")
    @PostMapping(value={"/get_querymodel_param/{zbQueryInfoId}"})
    @RequiresPermissions(value={"nr:zbquery:querymodel"})
    public ZBQueryModel getQueryInfoDataByParam(@PathVariable String zbQueryInfoId, @RequestBody QueryParamVO queryParamVo) throws JQException {
        return this.zbQueryInfoService.getQueryInfoData(zbQueryInfoId, new ZBQueryParam(queryParamVo));
    }
}

