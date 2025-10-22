/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.context.EnvCenter
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO
 *  com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 */
package com.jiuqi.gcreport.listedcompanyauthz.service.impl;

import com.jiuqi.gcreport.common.context.EnvCenter;
import com.jiuqi.gcreport.listedcompanyauthz.base.BeanUtil;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyAuthzDao;
import com.jiuqi.gcreport.listedcompanyauthz.dao.FListedCompanyDao;
import com.jiuqi.gcreport.listedcompanyauthz.domain.ListedCompanySaveParam;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyAuthzEO;
import com.jiuqi.gcreport.listedcompanyauthz.entity.ListedCompanyEO;
import com.jiuqi.gcreport.listedcompanyauthz.listener.ListedCompanyAndUserinitExecutor;
import com.jiuqi.gcreport.listedcompanyauthz.service.FListedCompanyService;
import com.jiuqi.gcreport.listedcompanyauthz.service.impl.ListedCompanyCacheService;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyAuthzVO;
import com.jiuqi.gcreport.listedcompanyauthz.vo.ListedCompanyVO;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ListedCompanyServiceImpl
implements FListedCompanyService {
    @Autowired
    private ListedCompanyCacheService cacheService;
    @Autowired
    private FListedCompanyDao listedCompanyDao;
    @Autowired
    private FListedCompanyAuthzDao companyAuthzDao;
    @Autowired
    private UserService<User> userService;

    @Override
    public List<ListedCompanyEO> query(ListedCompanyVO param) {
        return this.listedCompanyDao.query(param);
    }

    @Override
    public ListedCompanyEO get(String id) {
        return this.listedCompanyDao.get(id);
    }

    @Override
    public boolean save(List<ListedCompanyVO> params) {
        ListedCompanySaveParam<ListedCompanyEO> datas = new ListedCompanySaveParam<ListedCompanyEO>();
        params.forEach(vo -> {
            ListedCompanyEO eo = BeanUtil.companyVO2EO(vo);
            try {
                this.checkFormatEO(datas, eo);
            }
            catch (Exception e) {
                vo.setErrorMsg(e.getMessage());
            }
        });
        if (datas.size() == params.size()) {
            this.listedCompanyDao.saveAll(datas);
            datas.getDeleteDatas().forEach(v -> this.deleteByOrgCode(v.getOrgCode()));
            datas.getInsertDatas().forEach(v -> ListedCompanyAndUserinitExecutor.initListedCompanyUserAuthz(v.getOrgCode()));
            this.cacheService.publishOrgCacheEvent(new String[0]);
            return true;
        }
        return false;
    }

    @Override
    public int delete(ListedCompanyEO param) {
        this.deleteById(param.getId());
        int i = this.listedCompanyDao.delete(param);
        this.cacheService.publishOrgCacheEvent(new String[0]);
        return i;
    }

    private int deleteById(String id) {
        if (!StringUtils.hasText(id)) {
            throw new RuntimeException("orgCode \u4e0d\u80fd\u4e3a\u7a7a");
        }
        ListedCompanyEO companyEO = this.listedCompanyDao.get(id);
        if (ObjectUtils.isEmpty((Object)companyEO)) {
            throw new RuntimeException("\u8981\u5220\u9664\u7684\u6570\u636e\u4e0d\u5b58\u5728");
        }
        ListedCompanyAuthzVO p = new ListedCompanyAuthzVO();
        p.setOrgCode(companyEO.getOrgCode());
        List<ListedCompanyAuthzEO> query = this.companyAuthzDao.query(p);
        if (query != null && query.size() > 0) {
            query.forEach(v -> this.companyAuthzDao.delete((ListedCompanyAuthzEO)((Object)v)));
        }
        return 1;
    }

    private int deleteByOrgCode(String orgCode) {
        ListedCompanyAuthzVO p = new ListedCompanyAuthzVO();
        p.setOrgCode(orgCode);
        List<ListedCompanyAuthzEO> query = this.companyAuthzDao.query(p);
        if (query != null && query.size() > 0) {
            query.forEach(v -> this.companyAuthzDao.delete((ListedCompanyAuthzEO)((Object)v)));
        }
        return 1;
    }

    private ListedCompanyEO checkFormatEO(ListedCompanySaveParam<ListedCompanyEO> datas, ListedCompanyEO param) {
        ListedCompanyEO old;
        if (StringUtils.isEmpty(param.getOrgCode())) {
            throw new NullPointerException("\u5355\u4f4d\u4ee3\u7801\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty(param.getOrgTitle())) {
            throw new NullPointerException("\u5355\u4f4d\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        if (StringUtils.isEmpty(param.getUserName())) {
            throw new NullPointerException("\u5355\u4f4d\u7ba1\u7406\u5458\u767b\u5f55\u540d\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        }
        User user = this.userService.getByUsername(param.getUserName());
        if (user == null) {
            throw new NullPointerException("\u7528\u6237\u3010" + param.getUserName() + "\u3011\u4e0d\u5b58\u5728\u6216\u8005\u662f\u7cfb\u7edf\u7528\u6237\uff0c\u8bf7\u91cd\u65b0\u8bbe\u7f6e\u7528\u6237\u3002");
        }
        param.setUserId(user.getId());
        param.setUserTitle(user.getNickname());
        ListedCompanyVO p = new ListedCompanyVO();
        p.setId(param.getId());
        p.setOrgCode(param.getOrgCode());
        p.setUserName(param.getUserName());
        List<ListedCompanyEO> db = this.listedCompanyDao.query(p);
        if (db != null && db.size() > 0) {
            throw new NullPointerException("\u5355\u4f4d\u3010" + param.getOrgCode() + "|" + param.getOrgTitle() + "\u3011\u7ba1\u7406\u5458\u7528\u6237\u3010" + param.getUserName() + "|" + param.getUserTitle() + "\u3011\u5df2\u5b58\u5728\u3002");
        }
        param.setCreateTime(new Date());
        param.setCreateUser(EnvCenter.getCurrentUser().getName());
        param.setModifyUser(EnvCenter.getCurrentUser().getName());
        param.setModifyTime(new Date());
        if (!StringUtils.isEmpty(param.getId()) && (old = this.listedCompanyDao.get(param.getId())) == null) {
            datas.addDelete(old);
        }
        datas.addInsert(param);
        return param;
    }
}

