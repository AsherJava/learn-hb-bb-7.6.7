/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.attachment.domain.AttachmentConfigItemDO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDTO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.attachment.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.attachment.common.FTPFileUtil;
import com.jiuqi.va.attachment.common.MongoDBUtil;
import com.jiuqi.va.attachment.dao.VaAttachmentModeDao;
import com.jiuqi.va.attachment.dao.VaAttachmentSchemeDao;
import com.jiuqi.va.attachment.domain.AttachmentConfigItemDO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDTO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.service.AttachmentSchemeService;
import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value="AttachmentSchemeServiceImpl")
public class AttachmentSchemeServiceImpl
implements AttachmentSchemeService {
    @Autowired
    private VaAttachmentSchemeDao attachmentSchemeDao;
    @Autowired
    private VaAttachmentModeDao attachmentModeDao;
    @Value(value="${upload.path}")
    private String serverPath;
    private List<AttachmentHandleIntf> attachmentHandleIntfList;

    private List<AttachmentHandleIntf> getAttachmentHandleIntfList() {
        if (this.attachmentHandleIntfList == null) {
            this.attachmentHandleIntfList = new ArrayList<AttachmentHandleIntf>();
            Map<String, AttachmentHandleIntf> intfMap = ApplicationContextRegister.getApplicationContext().getBeansOfType(AttachmentHandleIntf.class);
            if (!intfMap.isEmpty()) {
                this.attachmentHandleIntfList.addAll(intfMap.values());
            }
        }
        return this.attachmentHandleIntfList;
    }

    @Override
    public R add(AttachmentSchemeDO attachment) {
        int num = 0;
        attachment.setId(UUID.randomUUID());
        attachment.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        attachment.setCreatetime(new Date());
        attachment.setCreateuser(ShiroUtil.getUser().getId());
        AttachmentSchemeDO attachmentSchemeDO = new AttachmentSchemeDO();
        attachmentSchemeDO.setName(attachment.getName());
        num = this.attachmentSchemeDao.selectCount(attachmentSchemeDO);
        if (num > 0) {
            return R.error();
        }
        if (attachment.getStoremode() != 0 && attachment.getStoremode() != 1 && attachment.getStoremode() != 2) {
            ObjectNode extendDataNode = JSONUtil.parseObject((String)attachment.getConfig());
            extendDataNode.remove("attpath");
            String jsonString = JSONUtil.toJSONString((Object)extendDataNode);
            attachment.setConfig(jsonString);
        }
        this.attachmentSchemeDao.insert(attachment);
        return R.ok();
    }

    @Override
    public R update(AttachmentSchemeDO attachment) {
        if (attachment.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        attachment.setCreatetime(new Date());
        attachment.setCreateuser(ShiroUtil.getUser().getId());
        if (attachment.getStoremode() != 0 && attachment.getStoremode() != 1 && attachment.getStoremode() != 2) {
            ObjectNode extendDataNode = JSONUtil.parseObject((String)attachment.getConfig());
            extendDataNode.remove("attpath");
            String jsonString = JSONUtil.toJSONString((Object)extendDataNode);
            attachment.setConfig(jsonString);
        }
        this.attachmentSchemeDao.updateByPrimaryKeySelective(attachment);
        return R.ok();
    }

    @Override
    public R delete(AttachmentSchemeDO attachment) {
        if (attachment.getId() == null) {
            return R.error((String)"\u8bf7\u6c42\u53c2\u6570\u6709\u8bef");
        }
        AttachmentSchemeDO attachmenta = new AttachmentSchemeDO();
        attachmenta.setId(attachment.getId());
        this.attachmentSchemeDao.delete(attachmenta);
        return R.ok();
    }

    @Override
    public PageVO<TreeVO<AttachmentConfigItemDO>> tree(AttachmentSchemeDO attachmentSchemeDO) {
        PageVO page = new PageVO();
        TreeVO root = new TreeVO();
        root.setId("root");
        root.setText("\u5b58\u50a8\u65b9\u6848");
        root.setHasParent(false);
        root.setHasChildren(true);
        root.setChecked(true);
        HashMap<String, Boolean> state = new HashMap<String, Boolean>(16);
        state.put("opened", true);
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        HashMap<String, String> rootNode = new HashMap<String, String>();
        rootNode.put("name", "root");
        rootNode.put("title", "root");
        attributes.put("param", rootNode);
        attributes.put("type", "root");
        root.setAttributes(attributes);
        ArrayList<TreeVO> nodes = new ArrayList<TreeVO>();
        String splitKey = "###";
        AttachmentSchemeDTO dscheme = new AttachmentSchemeDTO();
        dscheme.setName("DEFAULTSCHEME");
        dscheme.setTitle("\u670d\u52a1\u5668\u5b58\u50a8\u65b9\u6848");
        dscheme.setStartflag(Integer.valueOf(1));
        dscheme.setStoremode(Integer.valueOf(2));
        dscheme.setDegree(Integer.valueOf(2));
        dscheme.setServerpath(this.serverPath);
        AttachmentSchemeDO schemeDo = new AttachmentSchemeDO();
        int schemeNum = 0;
        schemeDo.setId(UUID.randomUUID());
        schemeDo.setVer(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        schemeDo.setCreatetime(new Date());
        schemeDo.setCreateuser(ShiroUtil.getUser().getId());
        schemeDo.setName("DEFAULTSCHEME");
        schemeDo.setTitle("\u670d\u52a1\u5668\u5b58\u50a8\u65b9\u6848");
        schemeDo.setStartflag(Integer.valueOf(1));
        schemeDo.setStoremode(Integer.valueOf(2));
        schemeDo.setDegree(Integer.valueOf(2));
        AttachmentSchemeDO schemeDoSelect = new AttachmentSchemeDO();
        schemeDoSelect.setName("DEFAULTSCHEME");
        schemeNum = this.attachmentSchemeDao.selectCount(schemeDoSelect);
        if (schemeNum == 0) {
            this.attachmentSchemeDao.insert(schemeDo);
        }
        AttachmentModeDO modeDEF = new AttachmentModeDO();
        modeDEF.setSchemename("DEFAULTSCHEME");
        String modeDEFcount = Integer.toString(this.attachmentModeDao.selectCount(modeDEF));
        TreeVO dnode = new TreeVO();
        dnode.setId("scheme" + splitKey + "DEFAULTSCHEME");
        dnode.setParentid("root");
        dnode.setText("\u670d\u52a1\u5668\u5b58\u50a8\u65b9\u6848");
        state = new HashMap();
        state.put("opened", false);
        dnode.setState(state);
        dnode.setChecked(false);
        attributes = new HashMap();
        attributes.put("param", dscheme);
        attributes.put("type", "scheme");
        attributes.put("modeNum", modeDEFcount);
        dnode.setAttributes(attributes);
        nodes.add(dnode);
        List<AttachmentSchemeDTO> schemes = this.attachmentSchemeDao.getAllSchemeList(new AttachmentSchemeDTO());
        for (AttachmentSchemeDTO attachmentSchemeDTO : schemes) {
            if ("DEFAULTSCHEME".equals(attachmentSchemeDTO.getName())) continue;
            TreeVO node = new TreeVO();
            attachmentSchemeDTO.setServerpath(this.serverPath);
            node.setId("scheme" + splitKey + attachmentSchemeDTO.getName());
            node.setParentid("root");
            node.setText(attachmentSchemeDTO.getTitle());
            state = new HashMap();
            state.put("opened", false);
            node.setState(state);
            node.setChecked(false);
            attributes = new HashMap();
            for (AttachmentHandleIntf handle : this.getAttachmentHandleIntfList()) {
                if (handle.getStoremode() != attachmentSchemeDTO.getStoremode().intValue()) continue;
                handle.parseSchemeConfig((AttachmentSchemeDO)attachmentSchemeDTO);
                break;
            }
            attributes.put("param", attachmentSchemeDTO);
            attributes.put("type", "scheme");
            node.setAttributes(attributes);
            nodes.add(node);
        }
        List<AttachmentModeDTO> modes = this.attachmentModeDao.getAllAttList(new AttachmentModeDO());
        for (AttachmentModeDTO mode : modes) {
            mode.setServerpath(this.serverPath);
            TreeVO node = new TreeVO();
            node.setId("mode" + splitKey + mode.getName());
            node.setParentid("scheme" + splitKey + mode.getSchemename());
            node.setText(mode.getTitle());
            state = new HashMap();
            state.put("opened", false);
            node.setState(state);
            node.setChecked(false);
            attributes = new HashMap();
            attributes.put("param", mode);
            attributes.put("type", "mode");
            node.setAttributes(attributes);
            nodes.add(node);
        }
        TreeVO treeVO = BuildTreeUtil.build(nodes, (TreeVO)root);
        ArrayList<TreeVO> rows = new ArrayList<TreeVO>();
        rows.add(treeVO);
        page.setRows(rows);
        page.setRs(R.ok());
        return page;
    }

    @Override
    public R connect(AttachmentSchemeDO attachment) {
        SchemeEntity schemeEntity = new SchemeEntity();
        schemeEntity.setSchemeConfig(attachment.getConfig());
        if (attachment.getStoremode() == 0) {
            return FTPFileUtil.connectFtp(schemeEntity);
        }
        if (attachment.getStoremode() == 1) {
            return FTPFileUtil.connectSftp(schemeEntity);
        }
        if (attachment.getStoremode() == 4) {
            return MongoDBUtil.connectMD(schemeEntity);
        }
        return R.error((String)"\u8fde\u63a5\u5931\u8d25");
    }

    @Override
    public AttachmentSchemeDO get(AttachmentSchemeDO param) {
        return (AttachmentSchemeDO)this.attachmentSchemeDao.selectOne(param);
    }

    @Override
    public R checkScheme(AttachmentSchemeDO attachment) {
        int num = 0;
        AttachmentSchemeDO attachmentSchemeDO = new AttachmentSchemeDO();
        attachmentSchemeDO.setName(attachment.getName());
        num = this.attachmentSchemeDao.selectCount(attachmentSchemeDO);
        if (num > 0) {
            return R.error();
        }
        return R.ok();
    }
}

