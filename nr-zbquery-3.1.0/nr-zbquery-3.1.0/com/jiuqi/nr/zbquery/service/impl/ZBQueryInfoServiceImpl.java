/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException
 *  com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 *  com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.zbquery.bean.ZBQueryInfo;
import com.jiuqi.nr.zbquery.bean.ZBQueryParam;
import com.jiuqi.nr.zbquery.common.ZBQueryErrorEnum;
import com.jiuqi.nr.zbquery.dao.ZBQueryInfoDao;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.service.ZBQueryInfoService;
import com.jiuqi.nr.zbquery.util.HyperLinkDataCover;
import com.jiuqi.nr.zbquery.util.QuerySystemOptionUtils;
import com.jiuqi.nr.zbquery.util.SerializeUtils;
import com.jiuqi.nvwa.dataanalyze.DataAnalyzeResourceException;
import com.jiuqi.nvwa.dataanalyze.dao.ResourceTreeNodeDao;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import com.jiuqi.nvwa.dataanalyze.service.ResourceTreeNodeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ZBQueryInfoServiceImpl
implements ZBQueryInfoService {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryInfoServiceImpl.class);
    @Autowired
    private ZBQueryInfoDao zbQueryInfoDao;
    @Autowired
    private ResourceTreeNodeService resourceTreeNodeService;
    @Autowired
    private ResourceTreeNodeDao resourceTreeNodeDao;

    @Override
    public void addQueryInfo(ZBQueryInfo zbQueryInfo) throws JQException {
        this.zbQueryInfoDao.addQueryInfo(zbQueryInfo);
    }

    @Override
    public ZBQueryInfo saveAsQueryInfo(String srcQueryId, ZBQueryInfo zbQueryInfo) throws JQException {
        ZBQueryModel zbQueryModel = this.getQueryInfoData(srcQueryId);
        this.zbQueryInfoDao.addQueryInfo(zbQueryInfo);
        this.saveQueryInfoData(zbQueryModel, zbQueryInfo.getId());
        return zbQueryInfo;
    }

    @Override
    public void modifyQueryInfo(ZBQueryInfo zbQueryInfo) throws JQException {
        this.zbQueryInfoDao.modifyQueryInfo(zbQueryInfo);
    }

    @Override
    public String deleteQueryInfoByIds(List<String> queryInfoIds) throws JQException {
        this.zbQueryInfoDao.deleteQueryInfoByIds(queryInfoIds);
        return "";
    }

    @Override
    public void deleteAll() throws JQException {
        this.zbQueryInfoDao.deleteAll();
    }

    @Override
    public void deleteQueryInfoById(String queryInfoId) throws JQException {
        this.zbQueryInfoDao.deleteQueryInfoById(queryInfoId);
    }

    @Override
    public ZBQueryInfo getQueryInfoById(String queryInfoId) {
        return this.zbQueryInfoDao.getQueryInfoById(queryInfoId);
    }

    @Override
    public List<ZBQueryInfo> getQueryInfoByGroup(String parentId) {
        List<String> queryInfoIds = new ArrayList<String>();
        try {
            queryInfoIds = this.resourceTreeNodeService.getChildren(parentId).stream().filter(resourceTreeNode -> resourceTreeNode.getType().equals("com.jiuqi.nr.zbquery.manage")).map(ResourceTreeNode::getGuid).collect(Collectors.toList());
        }
        catch (DataAnalyzeResourceException e) {
            logger.error(e.getMessage(), e);
        }
        return this.zbQueryInfoDao.getQueryInfoByIds(queryInfoIds);
    }

    @Override
    public List<String> getQueryInfoByGroups(List<String> groupIds) {
        return this.zbQueryInfoDao.getQueryInfoByGroups(groupIds);
    }

    @Override
    public void saveQueryInfoData(ZBQueryModel zbQueryModel, String zbQueryInfoId) throws JQException {
        try {
            byte[] data = SerializeUtils.jsonSerializeToByte(zbQueryModel);
            this.zbQueryInfoDao.saveQueryInfoData(data, zbQueryInfoId);
            this.updateRsourceNode(zbQueryInfoId);
        }
        catch (JsonProcessingException e) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_001, (Throwable)e);
        }
    }

    private void updateRsourceNode(String zbQueryInfoId) {
        ResourceTreeNode node = null;
        try {
            node = this.resourceTreeNodeDao.get(zbQueryInfoId);
            if (node != null) {
                this.resourceTreeNodeDao.update(node);
            }
        }
        catch (Exception e) {
            logger.info("==\u4fee\u6539\u67e5\u8be2\u6a21\u677f\u65f6\uff1a\u540c\u6b65\u66f4\u65b0\u8d44\u6e90\u6811node\u7684\u64cd\u4f5c\u65f6\u95f4\uff0c\u53ef\u80fd\u5f71\u54cd\uff1a\u6a21\u677f\u5bfc\u5165");
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ZBQueryModel getQueryInfoData(String zbQueryInfoId) throws JQException {
        return this.getQueryInfoData(zbQueryInfoId, null);
    }

    @Override
    public ZBQueryModel getQueryInfoData(String zbQueryInfoId, ZBQueryParam param) throws JQException {
        ZBQueryModel zbQueryModel = null;
        try {
            byte[] data = this.zbQueryInfoDao.getQueryInfoDataById(zbQueryInfoId);
            if (data != null) {
                zbQueryModel = SerializeUtils.jsonDeserialize(data);
            }
        }
        catch (IOException e) {
            throw new JQException((ErrorEnum)ZBQueryErrorEnum.ZBQUERY_EXCEPTION_001, (Throwable)e);
        }
        if (param != null && zbQueryModel != null && StringUtils.hasLength(param.getLinkMessage())) {
            JSONObject linkMessage = new JSONObject(param.getLinkMessage());
            HyperLinkDataCover.coverDefaultValues(zbQueryModel, linkMessage);
        }
        return zbQueryModel;
    }

    @Override
    public List<ZBQueryInfo> getAllQueryInfo() {
        return this.zbQueryInfoDao.getAllQueryInfo();
    }

    @Override
    public ZBQueryModel createQueryModel() {
        ZBQueryModel model = new ZBQueryModel();
        model.getOption().setQueryDetailRecord(QuerySystemOptionUtils.getQueryDetailRecord());
        model.getOption().setNullDisplayMode(QuerySystemOptionUtils.getNullDisplayMode());
        model.getOption().setZeroDisplayMode(QuerySystemOptionUtils.getZeroDisplayMode());
        return model;
    }
}

