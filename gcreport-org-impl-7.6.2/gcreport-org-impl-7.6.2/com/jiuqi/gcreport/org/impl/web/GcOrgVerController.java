/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.org.api.intf.GcOrgVerClient
 *  com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  javax.validation.Valid
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.org.impl.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.org.api.intf.GcOrgVerClient;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
class GcOrgVerController
implements GcOrgVerClient {
    GcOrgVerController() {
    }

    private GcOrgVerTool getVerTool() {
        return GcOrgVerTool.getInstance();
    }

    public BusinessResponseEntity<OrgVersionVO> findOrgVerByName(@RequestBody GcOrgVerApiParam param) {
        OrgVersionVO ver = this.getVerTool().getOrgVersionByCode(param.getOrgType(), param.getOrgVerName());
        if (ver == null) {
            return BusinessResponseEntity.error((String)"\u4e0d\u5b58\u5728\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u7248\u672c");
        }
        return BusinessResponseEntity.ok((Object)ver);
    }

    public BusinessResponseEntity<List<OrgVersionVO>> queryAllOrgVersion(@RequestBody GcOrgVerApiParam param) {
        List<Object> vers = this.getVerTool().listOrgVersion(param.getOrgType());
        if (vers == null) {
            return BusinessResponseEntity.error((String)"\u4e0d\u5b58\u5728\u7ec4\u7ec7\u673a\u7248\u672c");
        }
        vers = vers.stream().sorted(Comparator.comparing(OrgVersionVO::getValidTime).reversed()).collect(Collectors.toList());
        return BusinessResponseEntity.ok(vers);
    }

    public BusinessResponseEntity<List<OrgTypeTreeVO>> orgTypeTree() {
        List<OrgTypeVO> typeList = GcOrgTypeTool.getInstance().listOrgType();
        ArrayList<OrgTypeTreeVO> root = new ArrayList<OrgTypeTreeVO>();
        if (typeList == null) {
            return BusinessResponseEntity.ok(root);
        }
        for (OrgTypeVO orgTypeVO : typeList) {
            if ("MD_ORG".equals(orgTypeVO.getName())) continue;
            OrgTypeTreeVO orgTypeTreeVO = new OrgTypeTreeVO(orgTypeVO.getId(), orgTypeVO.getTitle(), Boolean.valueOf(true));
            root.add(orgTypeTreeVO);
            List<OrgVersionVO> vers = this.getVerTool().listOrgVersion(orgTypeVO.getName());
            if (CollectionUtils.isEmpty(vers)) continue;
            for (OrgVersionVO ver : vers) {
                orgTypeTreeVO.getChildren().add(new OrgTypeTreeVO(ver.getId(), ver.getTitle(), Boolean.valueOf(false)));
            }
        }
        return BusinessResponseEntity.ok(root);
    }

    public BusinessResponseEntity<String> updateOrgVersion(@Valid @RequestBody OrgVersionVO orgVer) {
        Objects.requireNonNull(orgVer);
        String id = orgVer.getId();
        if (id == null) {
            throw new BusinessRuntimeException("\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u7248\u672c\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u64cd\u4f5c\u3002");
        }
        OrgVersionVO type = this.getVerTool().getOrgVersionByCode(orgVer.getOrgType().getName(), orgVer.getName());
        if (type != null && !type.getId().equals(id)) {
            return BusinessResponseEntity.error((String)"\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7248\u672c");
        }
        this.getVerTool().modifyOrgVersion(orgVer);
        return BusinessResponseEntity.ok((Object)id);
    }

    public BusinessResponseEntity<OrgVersionVO> addOrgVersion(@Valid @RequestBody OrgVersionVO orgVer) {
        List<OrgVersionVO> vos;
        List voList;
        String id = orgVer.getId();
        if (id == null) {
            orgVer.setId(UUIDUtils.newUUIDStr());
        }
        if (StringUtils.hasText(orgVer.getPeriodStr())) {
            Date parse = DateUtils.parse((String)orgVer.getPeriodStr(), (String)"yyyy-MM");
            orgVer.setValidTime(parse);
        }
        if (!CollectionUtils.isEmpty(voList = (vos = this.getVerTool().listOrgVersion(orgVer.getOrgType().getName())).stream().filter(orgVersionVO -> orgVersionVO.getName().equals(orgVer.getName())).collect(Collectors.toList()))) {
            return BusinessResponseEntity.error((String)"\u5df2\u7ecf\u5b58\u5728\u7ec4\u7ec7\u673a\u6784\u7248\u672c");
        }
        this.getVerTool().createOrgVersion(orgVer);
        return BusinessResponseEntity.ok((Object)orgVer);
    }

    public BusinessResponseEntity<String> removeOrgVersion(@Valid OrgVersionVO orgVer) {
        this.getVerTool().removeOrgVersion(orgVer);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<String> splitOrgVersion(@Valid OrgVersionVO orgVer) {
        Date parse = DateUtils.parse((String)orgVer.getPeriodStr(), (String)"yyyy-MM");
        orgVer.setValidTime(parse);
        OrgVersionDO orgVerDTO = new OrgVersionDO();
        orgVerDTO.setCategoryname(orgVer.getOrgType().getName());
        OrgVersionClient orgVersionClient = (OrgVersionClient)SpringContextUtils.getBean(OrgVersionClient.class);
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(orgVer.getOrgType().getName());
        param.setTitle(orgVer.getTitle());
        OrgVersionDO orgVersionDO = orgVersionClient.get(param);
        if (orgVersionDO != null) {
            throw new BusinessRuntimeException("\u7248\u672c\u6807\u9898\u91cd\u590d");
        }
        PlatformTransactionManager platformTransactionManager = (PlatformTransactionManager)ApplicationContextRegister.getBean(PlatformTransactionManager.class);
        TransactionDefinition transactionDefinition = (TransactionDefinition)ApplicationContextRegister.getBean(TransactionDefinition.class);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        try {
            this.getVerTool().splitOrgVersion(orgVer);
            platformTransactionManager.commit(transactionStatus);
        }
        catch (Exception e) {
            platformTransactionManager.rollback(transactionStatus);
            orgVersionClient.syncCache(orgVerDTO);
            return BusinessResponseEntity.error((String)"\u62c6\u5206\u5931\u8d25");
        }
        orgVersionClient.syncCache(orgVerDTO);
        return BusinessResponseEntity.ok((Object)"\u62c6\u5206\u6210\u529f");
    }
}

