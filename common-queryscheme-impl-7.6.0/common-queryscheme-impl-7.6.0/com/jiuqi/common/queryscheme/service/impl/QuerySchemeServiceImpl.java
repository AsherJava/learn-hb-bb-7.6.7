/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.common.queryscheme.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.queryscheme.common.QuerySchemeI18nConst;
import com.jiuqi.common.queryscheme.common.QuerySchemeStoreTypeEnum;
import com.jiuqi.common.queryscheme.dao.QuerySchemeDao;
import com.jiuqi.common.queryscheme.eo.QuerySchemeEO;
import com.jiuqi.common.queryscheme.service.QuerySchemeService;
import com.jiuqi.common.queryscheme.util.QuerySchemeOrderGenerator;
import com.jiuqi.common.queryscheme.util.QuerySchemeUUIDOrderUtils;
import com.jiuqi.common.queryscheme.vo.QuerySchemeVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuerySchemeServiceImpl
implements QuerySchemeService {
    @Autowired
    private QuerySchemeDao querySchemeDao;

    @Override
    public QuerySchemeVO getQueryScheme(String id) {
        QuerySchemeEO eo = this.querySchemeDao.getQuerySchemeById(id);
        return this.convertEO2VO(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public QuerySchemeVO save(QuerySchemeVO querySchemeVO) {
        if (querySchemeVO == null) {
            return null;
        }
        Assert.isNotEmpty((String)querySchemeVO.getSchemeName(), (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.SCHEME_NOT_EMPTY), (Object[])new Object[0]);
        Assert.isNotEmpty((String)querySchemeVO.getResourceId(), (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.RESOURCE_NOT_EMPTY), (Object[])new Object[0]);
        ContextUser user = this.getUserInfo();
        QuerySchemeEO eo = this.convertVO2EO(querySchemeVO);
        eo.setUserId(user.getId());
        eo.setUsername(user.getName());
        eo.setStoreType(StringUtils.isEmpty((String)eo.getOptionData()) || eo.getOptionData().length() < 1000 ? QuerySchemeStoreTypeEnum.CURRENT_TABLE.getStoreValue() : QuerySchemeStoreTypeEnum.CLOB_TABLE.getStoreValue());
        if (StringUtils.isEmpty((String)eo.getId())) {
            eo.setId(QuerySchemeUUIDOrderUtils.newUUIDStr());
            eo.setSortOrder(QuerySchemeOrderGenerator.newOrderShort());
            this.querySchemeDao.insert(eo);
        } else {
            this.querySchemeDao.update(eo);
        }
        return this.convertEO2VO(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public QuerySchemeVO moveUp(QuerySchemeVO querySchemeVO) {
        QuerySchemeEO priorQuerySchemeEO = this.querySchemeDao.getPriorQuerySchemeByCondition(querySchemeVO.getUsername(), querySchemeVO.getResourceId(), querySchemeVO.getOptionType(), querySchemeVO.getSortOrder());
        Assert.isNotNull((Object)priorQuerySchemeEO, (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.NOT_MOVE_UP), (Object[])new Object[0]);
        QuerySchemeEO eo = this.convertVO2EO(querySchemeVO);
        this.exchangeSortOrder(priorQuerySchemeEO, eo);
        return this.convertEO2VO(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public QuerySchemeVO moveDown(QuerySchemeVO querySchemeVO) {
        QuerySchemeEO nextQuerySchemeEO = this.querySchemeDao.getNextQuerySchemeByCondition(querySchemeVO.getUsername(), querySchemeVO.getResourceId(), querySchemeVO.getOptionType(), querySchemeVO.getSortOrder());
        Assert.isNotNull((Object)nextQuerySchemeEO, (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.NOT_MOVE_DOWN), (Object[])new Object[0]);
        QuerySchemeEO eo = this.convertVO2EO(querySchemeVO);
        this.exchangeSortOrder(nextQuerySchemeEO, eo);
        return this.convertEO2VO(eo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void moveTo(QuerySchemeVO param, String nextQuerySchemeId) {
        QuerySchemeEO prevQueryScheme;
        long newTimestamp;
        Assert.isNotNull((Object)param, (String)"\u53c2\u6570\u975e\u6cd5\uff0c\u8bf7\u68c0\u67e5", (Object[])new Object[0]);
        Assert.isNotEmpty((String)param.getId(), (String)"\u53c2\u6570 id \u4e0d\u80fd\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5", (Object[])new Object[0]);
        QuerySchemeEO nextQueryScheme = null;
        QuerySchemeEO movedQueryScheme = this.querySchemeDao.getQuerySchemeById(param.getId());
        Assert.isNotNull((Object)movedQueryScheme, (String)"\u65b9\u6848\u4e0d\u5b58\u5728", (Object[])new Object[0]);
        if (StringUtils.isEmpty((String)nextQuerySchemeId)) {
            newTimestamp = System.currentTimeMillis();
            prevQueryScheme = this.querySchemeDao.getLastQueryScheme(this.getUserInfo().getName(), movedQueryScheme.getResourceId(), movedQueryScheme.getOptionType());
            Assert.isNotNull((Object)prevQueryScheme, (String)"\u4e0d\u5b58\u5728\u67e5\u8be2\u65b9\u6848\uff0c\u8bf7\u5148\u521b\u5efa", (Object[])new Object[0]);
        } else {
            nextQueryScheme = this.querySchemeDao.getQuerySchemeById(nextQuerySchemeId);
            Assert.isNotNull((Object)nextQueryScheme, (String)String.format("\u6839\u636eID\u3010%s\u3011\u83b7\u53d6\u65b9\u6848\u5931\u8d25\uff0c\u65b9\u6848\u4e0d\u5b58\u5728", nextQuerySchemeId), (Object[])new Object[0]);
            String nextSortOrder = nextQueryScheme.getSortOrder();
            long nextTimestamp = QuerySchemeOrderGenerator.reverseOrderShort(nextSortOrder);
            newTimestamp = nextTimestamp - 1L;
            prevQueryScheme = this.querySchemeDao.getPriorQuerySchemeByCondition(nextQueryScheme.getUsername(), nextQueryScheme.getResourceId(), nextQueryScheme.getOptionType(), nextQueryScheme.getSortOrder());
        }
        Assert.isTrue((prevQueryScheme != null || nextQueryScheme != null ? 1 : 0) != 0, (String)"\u53c2\u6570\u975e\u6cd5\uff0c\u8bf7\u68c0\u67e5", (Object[])new Object[0]);
        if (this.notMoved(movedQueryScheme, prevQueryScheme, nextQueryScheme)) {
            return;
        }
        String newOrder = QuerySchemeOrderGenerator.newOrderShort(newTimestamp);
        movedQueryScheme.setSortOrder(newOrder);
        this.querySchemeDao.update(movedQueryScheme);
        while (prevQueryScheme != null && newTimestamp == QuerySchemeOrderGenerator.reverseOrderShort(prevQueryScheme.getSortOrder())) {
            QuerySchemeEO prevPrevQueryScheme = this.querySchemeDao.getPriorQuerySchemeByCondition(prevQueryScheme.getUsername(), prevQueryScheme.getResourceId(), prevQueryScheme.getOptionType(), prevQueryScheme.getSortOrder());
            newOrder = QuerySchemeOrderGenerator.newOrderShort(newTimestamp - 1L);
            --newTimestamp;
            prevQueryScheme.setSortOrder(newOrder);
            this.querySchemeDao.update(prevQueryScheme);
            prevQueryScheme = prevPrevQueryScheme;
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void setDefault(QuerySchemeVO querySchemeVO) {
        this.querySchemeDao.removeSelectByCondition(querySchemeVO.getUsername(), querySchemeVO.getResourceId(), querySchemeVO.getOptionType());
        this.querySchemeDao.updateSelectFlagById(querySchemeVO.getId(), true);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(String id) {
        this.querySchemeDao.delete(id);
    }

    @Override
    public List<QuerySchemeVO> listByResourceIdAndOptionType(String resourceId, String optionType) {
        ContextUser user = this.getUserInfo();
        List<QuerySchemeEO> querySchemeEOList = this.querySchemeDao.listQuerySchemesByUserName(user.getName(), resourceId, optionType);
        return this.convertEOList2VOList(querySchemeEOList);
    }

    private List<QuerySchemeVO> convertEOList2VOList(List<QuerySchemeEO> querySchemeEOList) {
        if (CollectionUtils.isEmpty(querySchemeEOList)) {
            return Collections.EMPTY_LIST;
        }
        ArrayList<QuerySchemeVO> querySchemeVOList = new ArrayList<QuerySchemeVO>(querySchemeEOList.size());
        for (QuerySchemeEO eo : querySchemeEOList) {
            querySchemeVOList.add(this.convertEO2VO(eo));
        }
        return querySchemeVOList;
    }

    private QuerySchemeVO convertEO2VO(QuerySchemeEO eo) {
        if (eo == null) {
            return null;
        }
        QuerySchemeVO vo = new QuerySchemeVO();
        BeanUtils.copyProperties(eo, vo);
        return vo;
    }

    private QuerySchemeEO convertVO2EO(QuerySchemeVO querySchemeVO) {
        if (querySchemeVO == null) {
            return null;
        }
        QuerySchemeEO eo = new QuerySchemeEO();
        BeanUtils.copyProperties(querySchemeVO, eo);
        return eo;
    }

    private ContextUser getUserInfo() {
        ContextUser user = NpContextHolder.getContext().getUser();
        Assert.isNotNull((Object)user, (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.USER_INFO_NOT_FIND), (Object[])new Object[0]);
        Assert.isNotEmpty((String)user.getId(), (String)GcI18nUtil.getMessage((String)QuerySchemeI18nConst.USER_ID_NOT_FIND), (Object[])new Object[0]);
        return user;
    }

    private void exchangeSortOrder(QuerySchemeEO eo1, QuerySchemeEO eo2) {
        this.querySchemeDao.updateSortOrderById(eo1.getId(), eo2.getSortOrder());
        this.querySchemeDao.updateSortOrderById(eo2.getId(), eo1.getSortOrder());
    }

    @Override
    public void cancelDefault(QuerySchemeVO querySchemeVO) {
        if (StringUtils.isEmpty((String)querySchemeVO.getId())) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        this.querySchemeDao.updateSelectFlagById(querySchemeVO.getId(), false);
    }

    @Override
    public void rename(QuerySchemeVO querySchemeVO) {
        if (StringUtils.isEmpty((String)querySchemeVO.getId())) {
            throw new BusinessRuntimeException("\u65b9\u6848\u4e0d\u5b58\u5728");
        }
        this.querySchemeDao.rename(querySchemeVO.getId(), querySchemeVO.getSchemeName());
    }

    private boolean notMoved(QuerySchemeEO movedQueryScheme, QuerySchemeEO prevQueryScheme, QuerySchemeEO nextQueryScheme) {
        return prevQueryScheme != null && movedQueryScheme.getId().equals(prevQueryScheme.getId()) || nextQueryScheme != null && movedQueryScheme.getId().equals(nextQueryScheme.getId());
    }
}

