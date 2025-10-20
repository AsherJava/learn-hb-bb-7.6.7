/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.lmax.disruptor.WorkHandler
 *  io.netty.channel.ChannelOption
 *  io.netty.handler.ssl.SslContext
 *  io.netty.handler.ssl.SslContextBuilder
 *  io.netty.handler.ssl.util.InsecureTrustManagerFactory
 *  org.springframework.http.client.reactive.ClientHttpConnector
 *  org.springframework.http.client.reactive.ReactorClientHttpConnector
 *  org.springframework.web.reactive.function.client.WebClient
 *  org.springframework.web.reactive.function.client.WebClient$RequestBodySpec
 *  reactor.netty.http.client.HttpClient
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorRecord;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorSystemOptionValueManager;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEvent;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorPostServerRetryManager;
import com.lmax.disruptor.WorkHandler;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.net.ssl.SSLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

public class MonitorEventWorkHandler
implements WorkHandler<MonitorEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorEventWorkHandler.class);
    private static final String URL_SUFFIX = "/v3/segments";
    private static final int MAX_TIMEOUT_MS = 15000;
    private static final long DATA_EXPIRATION_MS = 300000L;
    private List<MonitorRecord> recordList = new ArrayList<MonitorRecord>();
    private long firstRecordTimestamp = 0L;
    private volatile MonitorSystemOptionValueManager properties;
    private final MonitorPostServerRetryManager retryManager = MonitorPostServerRetryManager.getInstance();
    private final WebClient webClient = this.createRestTemplateWithTimeout();

    private WebClient createRestTemplateWithTimeout() {
        HttpClient httpClient;
        SslContext sslContext = null;
        try {
            sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }
        catch (SSLException e) {
            LOGGER.error("\u76d1\u63a7\u5e73\u53f0\uff1aSslContext\u521d\u59cb\u5316\u62a5\u9519\uff1a", e);
        }
        if (sslContext != null) {
            SslContext finalSslContext = sslContext;
            httpClient = (HttpClient)((HttpClient)HttpClient.create().responseTimeout(Duration.ofMillis(15000L)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)15000)).secure(spec -> spec.sslContext(finalSslContext)).followRedirect(true).proxyWithSystemProperties();
        } else {
            httpClient = (HttpClient)((HttpClient)HttpClient.create().responseTimeout(Duration.ofMillis(15000L)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (Object)15000)).followRedirect(true).proxyWithSystemProperties();
        }
        return WebClient.builder().clientConnector((ClientHttpConnector)new ReactorClientHttpConnector(httpClient)).build();
    }

    public void onEvent(MonitorEvent event) {
        this.putRecord(event.getMonitorRecord());
    }

    private void putRecord(MonitorRecord record) {
        if (!this.retryManager.isServerAvailable()) {
            LOGGER.warn("\u76d1\u63a7\u5e73\u53f0\uff1aSkyWalking\u670d\u52a1\u6682\u65f6\u4e0d\u53ef\u7528\uff0c\u672c\u5730\u8bb0\u5f55\uff1a{}", (Object)record);
        } else {
            if (this.recordList.isEmpty()) {
                this.firstRecordTimestamp = System.currentTimeMillis();
            }
            this.recordList.add(record);
            this.trySend();
        }
    }

    private void trySend() {
        if (this.recordList.isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        if ((this.recordList.size() >= this.getConfig().getBatchSize() || now - this.firstRecordTimestamp >= 300000L) && this.retryManager.canSend()) {
            ArrayList<MonitorRecord> sendList = new ArrayList<MonitorRecord>(this.recordList);
            this.recordList = new ArrayList<MonitorRecord>();
            LinkedHashMap<String, MonitorRecord> recordMap = new LinkedHashMap<String, MonitorRecord>();
            for (MonitorRecord record : sendList) {
                String segmentId = record.getTraceSegmentId();
                boolean currentIsNotFinish = record.getSpans().get(0).getOperationName().startsWith("NOT FINISH:");
                if (recordMap.containsKey(segmentId)) {
                    MonitorRecord existing = (MonitorRecord)recordMap.get(segmentId);
                    boolean existingIsNotFinish = existing.getSpans().get(0).getOperationName().startsWith("NOT FINISH:");
                    if (existingIsNotFinish && !currentIsNotFinish) {
                        recordMap.put(segmentId, record);
                        continue;
                    }
                    if (!existingIsNotFinish && currentIsNotFinish) continue;
                    recordMap.put(segmentId, record);
                    continue;
                }
                recordMap.put(segmentId, record);
            }
            this.attemptSend(new ArrayList<MonitorRecord>(recordMap.values()));
        }
    }

    private void attemptSend(List<MonitorRecord> sendList) {
        int size = sendList.size();
        String url = this.getConfig().getHttpUrl() + URL_SUFFIX;
        ((WebClient.RequestBodySpec)this.webClient.post().uri(url, new Object[0])).bodyValue(sendList).retrieve().toBodilessEntity().doOnSuccess(response -> this.retryManager.sendSuccess()).doOnError(error -> {
            LOGGER.error("\u76d1\u63a7\u5e73\u53f0\uff1aSkyWalking\u53d1\u9001\u5931\u8d25\uff0c\u4e22\u5f03\u8bb0\u5f55\u961f\u5217 {}\uff0c\u62a5\u9519\u4fe1\u606f\uff1a{}", (Object)size, (Object)error.getMessage());
            this.retryManager.sendFailed();
        }).subscribe();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MonitorSystemOptionValueManager getConfig() {
        if (this.properties == null) {
            MonitorEventWorkHandler monitorEventWorkHandler = this;
            synchronized (monitorEventWorkHandler) {
                if (this.properties == null) {
                    this.properties = (MonitorSystemOptionValueManager)SpringBeanUtils.getBean(MonitorSystemOptionValueManager.class);
                }
            }
        }
        return this.properties;
    }
}

