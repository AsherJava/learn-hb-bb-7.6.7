/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum
 *  com.jiuqi.dc.base.common.enums.OpenWayEnum
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 *  com.jiuqi.dc.base.common.intf.impl.TreeDTO
 *  com.jiuqi.dc.base.common.penetratebill.IScopeType
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.penetratebill.client.common.CustomParam
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateContext
 *  com.jiuqi.dc.penetratebill.client.common.PenetrateParam
 *  com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO
 *  com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO
 *  com.jiuqi.np.log.LogHelper
 *  javax.validation.constraints.NotBlank
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.penetratebill.impl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.enums.DcFunctionModuleEnum;
import com.jiuqi.dc.base.common.enums.OpenWayEnum;
import com.jiuqi.dc.base.common.exception.CheckRuntimeException;
import com.jiuqi.dc.base.common.intf.impl.TreeDTO;
import com.jiuqi.dc.base.common.penetratebill.IScopeType;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.penetratebill.client.common.CustomParam;
import com.jiuqi.dc.penetratebill.client.common.PenetrateContext;
import com.jiuqi.dc.penetratebill.client.common.PenetrateParam;
import com.jiuqi.dc.penetratebill.client.dto.MatchResultDTO;
import com.jiuqi.dc.penetratebill.client.dto.PenetrateSchemeDTO;
import com.jiuqi.dc.penetratebill.impl.dao.PenetrateBillDao;
import com.jiuqi.dc.penetratebill.impl.define.IPenetrateHandler;
import com.jiuqi.dc.penetratebill.impl.define.gather.IPenetrateHandlerGather;
import com.jiuqi.dc.penetratebill.impl.define.gather.IScopeTypeGather;
import com.jiuqi.dc.penetratebill.impl.entity.PenetrateSchemeEO;
import com.jiuqi.dc.penetratebill.impl.service.PenetrateBillService;
import com.jiuqi.dc.penetratebill.impl.service.impl.PenetrateSchemeCacheProvider;
import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PenetrateBillServiceImpl
implements PenetrateBillService {
    @Autowired
    private PenetrateBillDao dao;
    @Autowired
    private PenetrateSchemeCacheProvider cacheProvider;
    @Autowired
    private IScopeTypeGather scopeTypeGather;
    @Autowired
    private IPenetrateHandlerGather penetrateHandlerGather;

    @Override
    public List<BaseDataShowVO> listScopeType() {
        return this.scopeTypeGather.list().stream().sorted(Comparator.comparing(IScopeType::getOrdinal)).map(scope -> {
            BaseDataShowVO showVO = new BaseDataShowVO();
            showVO.setCode(scope.getCode());
            showVO.setTitle(scope.getName());
            return showVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BaseDataShowVO> listHandler() {
        return this.penetrateHandlerGather.list().stream().sorted(Comparator.comparing(IPenetrateHandler::getOrdinal)).map(handler -> {
            BaseDataShowVO showVO = new BaseDataShowVO();
            showVO.setCode(handler.getCode());
            showVO.setTitle(handler.getName());
            return showVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BaseDataShowVO> listOpenWay() {
        return Arrays.stream(OpenWayEnum.values()).map(openWay -> {
            BaseDataShowVO showVO = new BaseDataShowVO();
            showVO.setCode(openWay.getCode());
            showVO.setTitle(openWay.getTitle());
            return showVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CustomParam> listParam(@NotBlank(message="\u7a7f\u900f\u5904\u7406\u5668\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u7a7f\u900f\u5904\u7406\u5668\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String handlerCode) {
        IPenetrateHandler handler = this.penetrateHandlerGather.list().stream().filter(e -> e.getCode().equals(handlerCode)).findFirst().orElse(null);
        Assert.isNotNull((Object)handler, (String)"\u7a7f\u900f\u5904\u7406\u5668\u4e0d\u5b58\u5728", (Object[])new Object[0]);
        return handler.getCustomParam();
    }

    @Override
    public List<TreeDTO> tree(PenetrateSchemeDTO dto) {
        ArrayList<TreeDTO> tree = new ArrayList<TreeDTO>(1);
        TreeDTO root = new TreeDTO();
        root.setId("root");
        root.setCode("root");
        root.setParentId("-");
        root.setTitle("\u8054\u67e5\u5355\u636e\u65b9\u6848");
        List treeNodes = this.listAll().stream().sorted(Comparator.comparing(PenetrateSchemeDTO::getCreateDate)).map(this::convert2TreeNode).collect(Collectors.toList());
        root.setChildren(treeNodes);
        tree.add(root);
        return tree;
    }

    @Override
    public List<PenetrateSchemeDTO> listAll() {
        return this.cacheProvider.list();
    }

    @Override
    public PenetrateSchemeDTO getById(@NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") @NotBlank(message="\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a") String id) {
        return this.findDataById(id, null);
    }

    private PenetrateSchemeDTO findDataById(String id, Long ver) {
        return this.listAll().stream().filter(item -> id.equals(item.getId()) && (ver == null || ver.compareTo(item.getVer()) == 0)).findFirst().orElse(null);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean create(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") PenetrateSchemeDTO dto) {
        PenetrateSchemeEO schemeEO = (PenetrateSchemeEO)((Object)BeanConvertUtil.convert((Object)dto, PenetrateSchemeEO.class, (String[])new String[0]));
        String id = schemeEO.getId();
        if (StringUtils.isEmpty((String)id)) {
            schemeEO.setId(UUIDUtils.newUUIDStr());
        }
        schemeEO.setVer(0L);
        schemeEO.setCreateDate(DateUtils.nowTimeStr());
        this.dao.insert(schemeEO);
        dto.setId(schemeEO.getId());
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u65b0\u589e", dto.getSchemeName(), dto.getHandlerCode()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.PENETRATEBILL.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean modify(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") PenetrateSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        PenetrateSchemeEO schemeEO = (PenetrateSchemeEO)((Object)BeanConvertUtil.convert((Object)dto, PenetrateSchemeEO.class, (String[])new String[0]));
        int i = this.dao.update(schemeEO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u4fee\u6539", dto.getSchemeName(), dto.getHandlerCode()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.PENETRATEBILL.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean delete(@NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") PenetrateSchemeDTO dto) {
        Assert.isNotEmpty((String)dto.getId(), (String)"\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotNull((Object)dto.getVer(), (String)"\u7248\u672c\u53f7\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        PenetrateSchemeDTO savedSchemeEO = this.findDataById(dto.getId(), dto.getVer());
        Assert.isNotNull((Object)savedSchemeEO, (String)"\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5", (Object[])new Object[0]);
        PenetrateSchemeEO schemeEO = new PenetrateSchemeEO();
        schemeEO.setId(dto.getId());
        schemeEO.setVer(dto.getVer());
        int i = this.dao.delete(schemeEO);
        if (i != 1) {
            throw new CheckRuntimeException("\u6570\u636e\u5df2\u88ab\u4fee\u6539\u6216\u5220\u9664\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        this.cacheProvider.syncCache();
        String title = StringUtils.join((Object[])new String[]{"\u5220\u9664", dto.getSchemeName(), dto.getHandlerCode()}, (String)"-");
        LogHelper.info((String)DcFunctionModuleEnum.PENETRATEBILL.getFullModuleName(), (String)title, (String)JsonUtils.writeValueAsString((Object)dto));
        return true;
    }

    @Override
    public MatchResultDTO match(PenetrateContext context) {
        Assert.isNotEmpty((String)context.getUnitCode(), (String)"\u7a7f\u900f\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        String unitCode = context.getUnitCode();
        MatchResultDTO result = new MatchResultDTO();
        for (PenetrateSchemeDTO dto : this.listAll()) {
            IScopeType scopeType = this.scopeTypeGather.getScopeType(dto.getScopeType());
            Assert.isNotNull((Object)scopeType, (String)"\u672a\u627e\u5230\u5bf9\u5e94\u7684\u9002\u7528\u8303\u56f4\u7c7b\u578b", (Object[])new Object[0]);
            boolean match = scopeType.match(unitCode, dto.getScopeValue());
            if (!match) continue;
            result.setMatch(true);
            result.setSchemeId(dto.getId());
            result.setBillNoField(dto.getBillNoField());
            return result;
        }
        result.setMatch(false);
        return result;
    }

    @Override
    public PenetrateParam penetrate(PenetrateContext context) {
        Assert.isNotEmpty((String)context.getSchemeId(), (String)"\u7a7f\u900f\u65b9\u6848\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        PenetrateSchemeDTO matchScheme = this.listAll().stream().filter(dto -> dto.getId().equals(context.getSchemeId())).findFirst().orElse(null);
        Assert.isNotNull((Object)matchScheme, (String)"\u7a7f\u900f\u65b9\u6848\u4e0d\u5b58\u5728", (Object[])new Object[0]);
        IPenetrateHandler penetrateHandler = this.penetrateHandlerGather.getPenetrateHandler(matchScheme.getHandlerCode());
        String customParam = matchScheme.getCustomParam();
        String openWay = matchScheme.getOpenWay();
        String billNoField = matchScheme.getBillNoField();
        List customParams = (List)JsonUtils.readValue((String)customParam, (TypeReference)new TypeReference<List<CustomParam>>(){});
        return penetrateHandler.handlePenetrate(context, customParams, openWay, billNoField);
    }

    private TreeDTO convert2TreeNode(PenetrateSchemeDTO scheme) {
        TreeDTO node = new TreeDTO();
        node.setId(scheme.getId());
        node.setTitle(scheme.getSchemeName());
        node.setParentCode("-");
        node.setLeaf(Boolean.valueOf(true));
        return node;
    }
}

