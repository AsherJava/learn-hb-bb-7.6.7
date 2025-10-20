/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.apache.shiro.session.Session
 *  org.apache.shiro.session.UnknownSessionException
 *  org.apache.shiro.session.mgt.eis.AbstractSessionDAO
 *  org.apache.shiro.subject.Subject
 *  org.apache.shiro.subject.support.DefaultSubjectContext
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.shiro.config.redis;

import com.jiuqi.va.shiro.config.optimize.MyWebUtil;
import com.jiuqi.va.shiro.config.redis.MyRedisManager;
import com.jiuqi.va.shiro.config.redis.MyRedisSerializeUtil;
import com.jiuqi.va.shiro.config.subject.MySubjectExtProvider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ThreadContext;

public class MyRedisSessionDAO
extends AbstractSessionDAO {
    private MyRedisManager redisManager;
    private String redisKeyPrefix = "shiro_redis_session:";
    private String threadSessionKey = "shiro_thread_session";

    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        if (session.getAttribute((Object)DefaultSubjectContext.AUTHENTICATED_SESSION_KEY) != null) {
            session.setTimeout((long)this.redisManager.getExpire() * 1000L);
            byte[] key = this.getByteKey(session.getId());
            byte[] value = MyRedisSerializeUtil.serialize(session);
            this.redisManager.set(key, value, this.redisManager.getExpire());
        }
    }

    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        this.redisManager.del(this.getByteKey(session.getId()));
    }

    public int countActiveSessions() {
        Set<byte[]> keys = this.redisManager.keys(this.redisKeyPrefix + "*");
        if (keys != null) {
            return keys.size();
        }
        return 0;
    }

    public Collection<Session> getActiveSessions() {
        HashSet<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = this.redisManager.keys(this.redisKeyPrefix + "*");
        if (keys == null || keys.isEmpty()) {
            return sessions;
        }
        List<Object> datas = null;
        if (keys.size() > 200) {
            datas = new ArrayList();
            HashSet<byte[]> tmpKeys = new HashSet<byte[]>();
            int i = 1;
            for (byte[] key : keys) {
                tmpKeys.add(key);
                if (i % 200 == 0 || i == keys.size()) {
                    try {
                        Thread.sleep(200L);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    List<byte[]> tmpData = this.redisManager.multiGet(tmpKeys);
                    if (tmpData != null && !tmpData.isEmpty()) {
                        datas.addAll(tmpData);
                    }
                    tmpKeys.clear();
                }
                ++i;
            }
        } else {
            datas = this.redisManager.multiGet(keys);
            if (datas == null || datas.isEmpty()) {
                return sessions;
            }
        }
        if (datas != null && !datas.isEmpty()) {
            Session session = null;
            for (byte[] byArray : datas) {
                if (byArray == null || byArray.length <= 0 || (session = MyRedisSerializeUtil.deserialize(byArray)) == null || session.getAttribute((Object)DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) continue;
                sessions.add(session);
            }
        }
        return sessions;
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        this.holdThreadSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        Session session = this.getThreadSession();
        if (session != null && session.getId().equals(sessionId)) {
            return session;
        }
        Session s = MyRedisSerializeUtil.deserialize(this.redisManager.get(this.getByteKey(sessionId)));
        this.holdThreadSession(s);
        return s;
    }

    private void holdThreadSession(Session s) {
        HttpServletRequest request = MyWebUtil.getRequest();
        if (request != null) {
            request.setAttribute(this.threadSessionKey, (Object)s);
            return;
        }
        Subject sbj = ThreadContext.getSubject();
        if (sbj instanceof MySubjectExtProvider) {
            ((MySubjectExtProvider)sbj).setThreadSesstion(s);
        }
    }

    private Session getThreadSession() {
        HttpServletRequest request = MyWebUtil.getRequest();
        if (request != null) {
            return (Session)request.getAttribute(this.threadSessionKey);
        }
        Subject sbj = ThreadContext.getSubject();
        if (sbj instanceof MySubjectExtProvider) {
            return ((MySubjectExtProvider)sbj).getThreadSSesstion();
        }
        return null;
    }

    private byte[] getByteKey(Serializable sessionId) {
        return (this.redisKeyPrefix + sessionId).getBytes();
    }

    public MyRedisManager getRedisManager() {
        return this.redisManager;
    }

    public void setRedisManager(MyRedisManager redisManager) {
        this.redisManager = redisManager;
        this.redisManager.init();
    }

    public String getKeyPrefix() {
        return this.redisKeyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.redisKeyPrefix = keyPrefix;
    }
}

