/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.BuildTreeUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.TreeVO
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.domain.task.OpenApiRegisterTask
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  io.jsonwebtoken.Claims
 *  io.jsonwebtoken.Jws
 *  io.jsonwebtoken.JwtBuilder
 *  io.jsonwebtoken.Jwts
 *  io.jsonwebtoken.Jwts$SIG
 *  io.jsonwebtoken.security.Keys
 *  io.jsonwebtoken.security.SecureDigestAlgorithm
 *  org.apache.shiro.codec.Base64
 */
package com.jiuqi.va.openapi.service.impl;

import com.jiuqi.va.domain.common.BuildTreeUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.TreeVO;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.domain.task.OpenApiRegisterTask;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.openapi.dao.VaOpenApiAuthDao;
import com.jiuqi.va.openapi.dao.VaOpenApiRegisterDao;
import com.jiuqi.va.openapi.domain.OpenApiAuthDO;
import com.jiuqi.va.openapi.domain.OpenApiAuthDTO;
import com.jiuqi.va.openapi.service.VaOpenApiService;
import com.jiuqi.va.openapi.service.impl.help.VaOpenApiCacheService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import java.math.BigDecimal;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VaOpenApiServiceImpl
implements VaOpenApiService {
    @Autowired
    private VaOpenApiRegisterDao registerDao;
    @Autowired
    private VaOpenApiAuthDao authDao;
    @Autowired
    private VaOpenApiCacheService openApiCacheService;
    @Autowired(required=false)
    private List<OpenApiRegisterTask> taskList;

    @Override
    public R registerApi(OpenApiRegisterDO openApiRegisterDO) {
        OpenApiRegisterDO param = new OpenApiRegisterDO();
        param.setTenantName(openApiRegisterDO.getTenantName());
        param.setName(openApiRegisterDO.getName());
        OpenApiRegisterDO old = (OpenApiRegisterDO)this.registerDao.selectOne(param);
        if (old != null) {
            openApiRegisterDO.setId(old.getId());
            this.registerDao.updateByPrimaryKey(openApiRegisterDO);
        } else {
            openApiRegisterDO.setId(UUID.randomUUID());
            this.registerDao.insert(openApiRegisterDO);
        }
        return R.ok();
    }

    @Override
    public R removeApi(OpenApiRegisterDO openApiRegisterDO) {
        return this.registerDao.delete(openApiRegisterDO) > 0 ? R.ok() : R.error();
    }

    @Override
    public TreeVO<OpenApiRegisterDO> treeApi(OpenApiRegisterDO param) {
        TreeVO root = new TreeVO();
        root.setId("-");
        root.setParentid("#");
        root.setText("-");
        root.setIcon("ivu-icon ivu-icon-ios-cube-outline");
        ArrayList<OpenApiRegisterDO> registerDOs = new ArrayList<OpenApiRegisterDO>();
        List apis = null;
        HashSet<String> nameSet = new HashSet<String>();
        if (this.taskList != null) {
            for (OpenApiRegisterTask task : this.taskList) {
                apis = task.getApis();
                if (apis == null || apis.isEmpty()) continue;
                for (OpenApiRegisterDO apiDO : apis) {
                    nameSet.add(apiDO.getName());
                    registerDOs.add(apiDO);
                }
            }
        }
        if ((apis = this.registerDao.select(param)) != null && !apis.isEmpty()) {
            for (OpenApiRegisterDO apiDO : apis) {
                if (nameSet.contains(apiDO.getName())) continue;
                nameSet.add(apiDO.getName());
                registerDOs.add(apiDO);
            }
        }
        if (registerDOs.isEmpty()) {
            return root;
        }
        ArrayList<TreeVO> trees = new ArrayList<TreeVO>();
        HashSet<String> parents = new HashSet<String>();
        for (OpenApiRegisterDO registerDO : registerDOs) {
            TreeVO tree;
            if (!parents.contains(registerDO.getGrouptitle())) {
                parents.add(registerDO.getGrouptitle());
                tree = new TreeVO();
                tree.setId("#" + registerDO.getGrouptitle());
                tree.setParentid("-");
                tree.setText(registerDO.getGrouptitle());
                tree.setIcon("ivu-icon ivu-icon-ios-folder-outline");
                trees.add(tree);
            }
            tree = new TreeVO();
            tree.setId(registerDO.getName());
            tree.setParentid("#" + registerDO.getGrouptitle());
            tree.setText(registerDO.getTitle());
            tree.setIcon("ivu-icon ivu-icon-md-menu");
            trees.add(tree);
        }
        return BuildTreeUtil.build(trees, (TreeVO)root);
    }

    @Override
    public OpenApiAuthDO getAuth(OpenApiAuthDTO param) {
        List<OpenApiAuthDO> list = this.listAuth(param);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<OpenApiAuthDO> listAuth(OpenApiAuthDTO param) {
        List<OpenApiAuthDO> list = this.authDao.list(param);
        if (list != null && !list.isEmpty()) {
            String authData = null;
            for (OpenApiAuthDO openApiAuthDO : list) {
                authData = openApiAuthDO.getAuthdata();
                if (!StringUtils.hasText(authData)) continue;
                openApiAuthDO.setApiRegisterList(JSONUtil.parseArray((String)authData, OpenApiRegisterDO.class));
            }
        }
        return list;
    }

    @Override
    public int countAuth(OpenApiAuthDTO param) {
        return this.authDao.count(param);
    }

    @Override
    public R addAuth(OpenApiAuthDO openApiDO) {
        int flag;
        OpenApiAuthDTO param = new OpenApiAuthDTO();
        param.setClientid(openApiDO.getClientid());
        OpenApiAuthDO old = this.getAuth(param);
        if (old != null) {
            return R.error((String)"\u5ba2\u6237\u7aef\u6807\u8bc6\u5df2\u5b58\u5728");
        }
        if (openApiDO.getExpiretime() == null) {
            openApiDO.setExpiretime(new BigDecimal(1));
        }
        openApiDO.setId(UUID.randomUUID());
        openApiDO.setStopflag(0);
        openApiDO.setOpenid(this.getOpenid(openApiDO));
        List<OpenApiRegisterDO> arDOs = openApiDO.getApiRegisterList();
        if (arDOs != null) {
            openApiDO.setAuthdata(JSONUtil.toJSONString(openApiDO.getApiRegisterList()));
        }
        if ((flag = this.authDao.insert((Object)openApiDO)) > 0) {
            this.openApiCacheService.update(openApiDO);
            this.openApiCacheService.publishMsg(openApiDO, true, false, false);
        }
        return flag > 0 ? R.ok() : R.error();
    }

    @Override
    public R updateAuth(OpenApiAuthDO openApiDO) {
        int flag;
        List<OpenApiRegisterDO> arDOs;
        OpenApiAuthDTO param = new OpenApiAuthDTO();
        param.setClientid(openApiDO.getClientid());
        OpenApiAuthDO old = this.getAuth(param);
        if (old == null) {
            return R.error((String)"\u6570\u636e\u5df2\u4e0d\u5b58\u5728");
        }
        if (!old.getId().equals(openApiDO.getId())) {
            return R.error((String)"\u5ba2\u6237\u7aef\u6807\u8bc6\u5df2\u5b58\u5728");
        }
        openApiDO.setId(old.getId());
        if (StringUtils.hasText(openApiDO.getRandomcode())) {
            openApiDO.setOpenid(this.getOpenid(openApiDO));
        }
        if ((arDOs = openApiDO.getApiRegisterList()) != null) {
            openApiDO.setAuthdata(JSONUtil.toJSONString(openApiDO.getApiRegisterList()));
        }
        if ((flag = this.authDao.updateByPrimaryKeySelective((Object)openApiDO)) > 0) {
            this.openApiCacheService.update(openApiDO);
            this.openApiCacheService.publishMsg(openApiDO, true, false, false);
        }
        return flag > 0 ? R.ok() : R.error();
    }

    @Override
    public R removeAuth(List<OpenApiAuthDO> objs) {
        if (objs == null) {
            return R.error();
        }
        for (OpenApiAuthDO openApiAuthDO : objs) {
            if (openApiAuthDO.getClientid() == null) continue;
            this.authDao.delete((Object)openApiAuthDO);
            this.openApiCacheService.remove(openApiAuthDO);
            this.openApiCacheService.publishMsg(openApiAuthDO, false, true, false);
        }
        return R.ok();
    }

    @Override
    public R stopAuth(List<OpenApiAuthDO> objs) {
        if (objs == null) {
            return R.error();
        }
        for (OpenApiAuthDO openApiAuthDO : objs) {
            if (openApiAuthDO.getClientid() == null || openApiAuthDO.getStopflag() == null) continue;
            this.authDao.updateByPrimaryKeySelective((Object)openApiAuthDO);
            this.openApiCacheService.stop(openApiAuthDO);
            this.openApiCacheService.publishMsg(openApiAuthDO, false, false, true);
        }
        return R.ok();
    }

    private String getOpenid(OpenApiAuthDO openApiDO) {
        String secretKey = openApiDO.getTenantName() + "#" + openApiDO.getClientid() + "#" + openApiDO.getRandomcode();
        return Base64.encodeToString((byte[])secretKey.getBytes()) + "JqYbz==";
    }

    @Override
    public String createJWT(OpenApiAuthDO openApiDO) {
        Object fromEsp;
        String subject = openApiDO.getOpenid() + "#" + Base64.encodeToString((byte[])openApiDO.getAuthdata().getBytes());
        if (openApiDO.getExtInfo("username") != null) {
            subject = subject + "#" + Base64.encodeToString((byte[])openApiDO.getExtInfo("username").toString().getBytes());
        }
        if ((fromEsp = RequestContextUtil.getAttribute((String)"Content-Esp")) != null && fromEsp.toString().startsWith("A3cllWc1lma=")) {
            subject = subject + "#@fromEsp";
        }
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = this.generalKey();
        JwtBuilder builder = Jwts.builder().subject(subject).issuedAt(now).signWith((Key)secretKey, (SecureDigestAlgorithm)Jwts.SIG.HS256);
        long singExpire = openApiDO.getExpiretime().longValue();
        if (singExpire >= 0L) {
            long expMillis = nowMillis + singExpire * 1000L;
            Date expDate = new Date(expMillis);
            builder.expiration(expDate);
        }
        return builder.compact();
    }

    @Override
    public Claims parseJWT(String jwt) {
        SecretKey secretKey = this.generalKey();
        Jws jws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims((CharSequence)jwt);
        return (Claims)jws.getPayload();
    }

    private SecretKey generalKey() {
        byte[] encodedKey = new byte[]{74, 81, 49, 49, 49, 49, 48, 53, 64, 106, 105, 117, 113, 105, 38, 108, 105, 117, 121, 97, 110, 104, 117, 105, 64, 106, 105, 117, 113, 105};
        return Keys.hmacShaKeyFor((byte[])Base64.encode((byte[])encodedKey));
    }
}

