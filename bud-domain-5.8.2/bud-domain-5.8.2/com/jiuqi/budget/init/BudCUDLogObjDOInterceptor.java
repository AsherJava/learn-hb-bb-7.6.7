/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  org.apache.ibatis.executor.Executor
 *  org.apache.ibatis.mapping.MappedStatement
 *  org.apache.ibatis.mapping.SqlCommandType
 *  org.apache.ibatis.plugin.Interceptor
 *  org.apache.ibatis.plugin.Intercepts
 *  org.apache.ibatis.plugin.Invocation
 *  org.apache.ibatis.plugin.Plugin
 *  org.apache.ibatis.plugin.Signature
 */
package com.jiuqi.budget.init;

import com.jiuqi.budget.init.CUDLogObjDODeclare;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import java.time.LocalDateTime;
import java.util.Properties;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

@Component(value="BudCUDLogObjDOInterceptor")
@Intercepts(value={@Signature(type=Executor.class, method="update", args={MappedStatement.class, Object.class})})
public class BudCUDLogObjDOInterceptor
implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement)args[0];
        Object parameter = args[1];
        if (!(parameter instanceof CUDLogObjDODeclare)) {
            return invocation.proceed();
        }
        CUDLogObjDODeclare cuLogObj = (CUDLogObjDODeclare)parameter;
        String userName = this.getUserName();
        LocalDateTime now = LocalDateTime.now();
        if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
            if (cuLogObj.getCreateTime() == null) {
                cuLogObj.setCreateTime(now);
            }
            if (cuLogObj.getCreator() == null) {
                cuLogObj.setCreator(userName);
            }
        } else if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
            if (cuLogObj.getModifyTime() == null) {
                cuLogObj.setModifyTime(now);
            }
            if (cuLogObj.getModifier() == null) {
                cuLogObj.setModifier(userName);
            }
        }
        return invocation.proceed();
    }

    private String getUserName() {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user != null) {
            return user.getUsername();
        }
        NpContext context = NpContextHolder.getContext();
        return context != null ? context.getUserName() : null;
    }

    public Object plugin(Object target) {
        return Plugin.wrap((Object)target, (Interceptor)this);
    }

    public void setProperties(Properties properties) {
    }
}

