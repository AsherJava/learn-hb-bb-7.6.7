/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.org.impl.fieldManager.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.fieldManager.dao.GcOrgTypeDao;
import com.jiuqi.gcreport.org.impl.fieldManager.service.GcOrgTypeService;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgTypeTool;
import com.jiuqi.gcreport.org.impl.util.internal.GcOrgVerTool;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcOrgTypeServiceImpl
implements GcOrgTypeService {
    @Autowired
    private GcOrgTypeDao orgTypeDao;
    @Autowired
    private BaseDataClient baseDataService;
    private static final Logger logger = LoggerFactory.getLogger(GcOrgTypeServiceImpl.class);

    private GcOrgTypeTool getTypeTool() {
        return GcOrgTypeTool.getInstance();
    }

    private GcOrgVerTool getVerTool() {
        return GcOrgVerTool.getInstance();
    }

    @Override
    public R copyOrgType(OrgTypeVO vo, String corgType, String corgVer) {
        if (StringUtils.isEmpty((String)corgType) || StringUtils.isEmpty((String)corgVer)) {
            throw new RuntimeException("\u53c2\u7167\u5355\u4f4d\u7c7b\u578b\u548c\u5355\u4f4d\u7248\u672c\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            this.getTypeTool().createOrgType(vo);
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u5355\u4f4d\u7c7b\u578b\u5931\u8d25", e);
            return R.error((String)"\u521b\u5efa\u5355\u4f4d\u7c7b\u578b\u5931\u8d25");
        }
        OrgVersionVO version = this.getVerTool().getOrgVersionByCode(corgType, corgVer);
        try {
            this.orgTypeDao.copyTypeVerData(version, vo.getName());
        }
        catch (Exception e) {
            this.getTypeTool().removeOrgType(vo.getName());
            logger.error("\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u5931\u8d25", e);
            return R.error((String)("\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u5931\u8d25" + e.getMessage()));
        }
        try {
            this.addOrgTypeBaseData(vo);
        }
        catch (Exception e) {
            logger.error("\u751f\u6210\u5355\u4f4d\u7c7b\u578b\u57fa\u7840\u6570\u636e\u5931\u8d25", e);
            return R.error((String)"\u751f\u6210\u5355\u4f4d\u7c7b\u578b\u57fa\u7840\u6570\u636e\u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u6dfb\u52a0");
        }
        return R.ok();
    }

    @Override
    public void addOrgTypeBaseData(OrgTypeVO vo) {
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setCode(vo.getName());
        baseDataDTO.setName(vo.getTitle());
        baseDataDTO.setParentcode("-");
        baseDataDTO.setShortname(vo.getTitle());
        baseDataDTO.setStopflag(null);
        baseDataDTO.setTableName("MD_GCORGTYPE");
        this.baseDataService.add(baseDataDTO);
    }

    @Override
    public void updateOrgTypeBaseData(OrgTypeVO vo) {
        try {
            BaseDataDTO baseDataDTO = new BaseDataDTO();
            baseDataDTO.setCode(vo.getName());
            baseDataDTO.setTableName("MD_GCORGTYPE");
            R exist = this.baseDataService.exist(baseDataDTO);
            if (exist.getCode() != 0) {
                logger.error("\u540c\u6b65\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u5931\u8d25" + exist.getMsg());
                return;
            }
            BaseDataDO oldBaseData = (BaseDataDO)exist.get((Object)"data");
            baseDataDTO.putAll((Map)oldBaseData);
            baseDataDTO.setCode(vo.getName());
            baseDataDTO.setName(vo.getTitle());
            this.baseDataService.update(baseDataDTO);
        }
        catch (Exception e) {
            logger.error("\u540c\u6b65\u4fee\u6539\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u5931\u8d25", e);
        }
    }
}

