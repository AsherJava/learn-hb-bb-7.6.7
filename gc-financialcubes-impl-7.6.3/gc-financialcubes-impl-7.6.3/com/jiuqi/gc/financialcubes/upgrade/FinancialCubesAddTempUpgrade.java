/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager
 *  com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService
 *  com.jiuqi.gcreport.dimension.internal.enums.DimensionPublishStateEnum
 *  com.jiuqi.gcreport.dimension.internal.service.impl.DimensionManageServiceImpl
 *  com.jiuqi.gcreport.dimension.vo.DimensionPublishVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionQueryVO
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 */
package com.jiuqi.gc.financialcubes.upgrade;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.definition.impl.basic.dao.internal.EntDaoCacheManager;
import com.jiuqi.gcreport.definition.impl.basic.init.table.service.impl.DefinitionAutoCollectionService;
import com.jiuqi.gcreport.dimension.internal.enums.DimensionPublishStateEnum;
import com.jiuqi.gcreport.dimension.internal.service.impl.DimensionManageServiceImpl;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishVO;
import com.jiuqi.gcreport.dimension.vo.DimensionQueryVO;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class FinancialCubesAddTempUpgrade
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void execute(DataSource dataSource) throws Exception {
    }

    private void repairTempTableDimField() {
        this.logger.info("\u5f00\u59cb\u540c\u6b65\u4e34\u65f6\u8868\u6570\u636e\u5efa\u6a21");
        DefinitionAutoCollectionService definitionAutoCollectionService = (DefinitionAutoCollectionService)SpringContextUtils.getBean(DefinitionAutoCollectionService.class);
        this.logger.info("\u5b8c\u6210\u540c\u6b65\u4e34\u65f6\u8868\u6570\u636e\u5efa\u6a21");
        this.initDimDao();
        DimensionManageServiceImpl dimensionManageService = (DimensionManageServiceImpl)SpringContextUtils.getBean(DimensionManageServiceImpl.class);
        HashSet<String> finCubesScopeSet = new HashSet<String>(Arrays.asList("GC_FINCUBES_DIM", "GC_FINCUBES_CF", "GC_FINCUBES_AGING"));
        List dimensionQueryVOS = dimensionManageService.listDimensions().stream().filter(item -> {
            if (!DimensionPublishStateEnum.SUCCESS.getCode().equals(item.getPublishedFlag())) {
                return false;
            }
            List effectScopeCodes = item.getEffectScopeCodes();
            if (CollectionUtils.isEmpty((Collection)effectScopeCodes)) {
                return false;
            }
            return !Collections.disjoint(effectScopeCodes, finCubesScopeSet);
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dimensionQueryVOS)) {
            this.logger.info("\u6ca1\u6709\u9700\u8981\u4fee\u590d\u7684\u7ef4\u5ea6\u5b57\u6bb5\u8df3\u8fc7\u5904\u7406");
            return;
        }
        DimensionPublishVO publishVO = new DimensionPublishVO();
        publishVO.setGlobalFlag(Boolean.valueOf(false));
        for (DimensionQueryVO queryVo : dimensionQueryVOS) {
            try {
                DimensionVO vo = new DimensionVO();
                BeanUtils.copyProperties(queryVo, vo);
                this.logger.info("\u5f00\u59cb\u540c\u6b65\u3010{}\u3011\u7ef4\u5ea6", (Object)vo.getTitle());
                this.logger.info("\u3010{}\u3011\u7ef4\u5ea6\u540c\u6b65\u5b8c\u6210\u3002", (Object)vo.getTitle());
            }
            catch (Exception e) {
                this.logger.error("\u3010" + queryVo.getTitle() + "\u3011\u7ef4\u5ea6\u540c\u6b65\u5f02\u5e38:" + e.getMessage(), e);
            }
        }
    }

    private void initDimDao() {
        EntDaoCacheManager manager = (EntDaoCacheManager)SpringBeanUtils.getBean(EntDaoCacheManager.class);
    }

    private void initUserInfo() throws JQException {
        NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        NpContextUser contextUser = this.buildUserContext();
        npContext.setUser((ContextUser)contextUser);
        npContext.setIdentity((ContextIdentity)this.buildIdentityContext(contextUser));
        String tenantId = "__default_tenant__";
        npContext.setTenant(tenantId);
        NpContextHolder.setContext((NpContext)npContext);
    }

    private NpContextUser buildUserContext() {
        NpContextUser userContext = new NpContextUser();
        SystemUserService sysUserService = (SystemUserService)SpringContextUtils.getBean(SystemUserService.class);
        SystemUser user = (SystemUser)sysUserService.getByUsername("admin");
        if (user == null) {
            user = (SystemUser)sysUserService.getUsers().get(0);
        }
        userContext.setId("SYSTEM.ROOT");
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        identity.setId(contextUser.getId());
        identity.setTitle(contextUser.getName());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }
}

