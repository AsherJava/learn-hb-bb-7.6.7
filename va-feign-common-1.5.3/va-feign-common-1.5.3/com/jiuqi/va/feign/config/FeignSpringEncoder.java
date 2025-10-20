/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.RequestTemplate
 *  feign.codec.EncodeException
 *  feign.codec.Encoder
 *  feign.form.spring.SpringFormEncoder
 *  org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpOutputMessage
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.ByteArrayHttpMessageConverter
 *  org.springframework.http.converter.GenericHttpMessageConverter
 *  org.springframework.http.converter.HttpMessageConversionException
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.feign.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartFile;

public class FeignSpringEncoder
implements Encoder {
    private static final Logger log = LoggerFactory.getLogger(FeignSpringEncoder.class);
    private final SpringFormEncoder springFormEncoder;
    private final ObjectFactory<HttpMessageConverters> messageConverters;
    private final ObjectProvider<HttpMessageConverterCustomizer> customizers;
    private List<HttpMessageConverter<?>> converters;

    public FeignSpringEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        this(new SpringFormEncoder(), messageConverters, null);
    }

    public FeignSpringEncoder(SpringFormEncoder springFormEncoder, ObjectFactory<HttpMessageConverters> messageConverters, ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        this.springFormEncoder = springFormEncoder;
        this.messageConverters = messageConverters;
        this.customizers = customizers;
    }

    public void encode(Object requestBody, Type bodyType, RequestTemplate request) throws EncodeException {
        if (requestBody != null) {
            Collection contentTypes = (Collection)request.headers().get("Content-Type");
            MediaType requestContentType = null;
            if (contentTypes != null && !contentTypes.isEmpty()) {
                String type = (String)contentTypes.iterator().next();
                requestContentType = MediaType.valueOf((String)type);
            }
            if (this.isFormRelatedContentType(requestContentType)) {
                this.springFormEncoder.encode(requestBody, bodyType, request);
                return;
            }
            if (bodyType == MultipartFile.class) {
                log.warn("For MultipartFile to be handled correctly, the 'consumes' parameter of @RequestMapping should be specified as MediaType.MULTIPART_FORM_DATA_VALUE");
            }
            this.encodeWithMessageConverter(requestBody, bodyType, request, requestContentType);
        }
    }

    private void encodeWithMessageConverter(Object requestBody, Type bodyType, RequestTemplate request, MediaType requestContentType) {
        this.initConvertersIfRequired();
        for (HttpMessageConverter<?> messageConverter : this.converters) {
            FeignOutputMessage outputMessage;
            try {
                outputMessage = messageConverter instanceof GenericHttpMessageConverter ? this.checkAndWrite(requestBody, bodyType, requestContentType, (GenericHttpMessageConverter)messageConverter, request) : this.checkAndWrite(requestBody, requestContentType, messageConverter, request);
            }
            catch (IOException | HttpMessageConversionException ex) {
                throw new EncodeException("Error converting request body", ex);
            }
            if (outputMessage == null) continue;
            request.headers(null);
            request.headers(new LinkedHashMap(outputMessage.getHeaders()));
            Charset charset = this.shouldHaveNullCharset(messageConverter, outputMessage) ? null : StandardCharsets.UTF_8;
            request.body(outputMessage.getOutputStream().toByteArray(), charset);
            return;
        }
        String message = "Could not write request: no suitable HttpMessageConverter found for request type [" + requestBody.getClass().getName() + "]";
        if (requestContentType != null) {
            message = message + " and content type [" + requestContentType + "]";
        }
        throw new EncodeException(message);
    }

    private void initConvertersIfRequired() {
        if (this.converters == null) {
            this.converters = this.messageConverters.getObject().getConverters();
            this.customizers.forEach(customizer -> customizer.accept(this.converters));
        }
    }

    private boolean shouldHaveNullCharset(HttpMessageConverter messageConverter, FeignOutputMessage outputMessage) {
        return this.binaryContentType(outputMessage) || messageConverter instanceof ByteArrayHttpMessageConverter;
    }

    private FeignOutputMessage checkAndWrite(Object body, MediaType contentType, HttpMessageConverter converter, RequestTemplate request) throws IOException {
        if (converter.canWrite(body.getClass(), contentType)) {
            this.logBeforeWrite(body, contentType, converter);
            FeignOutputMessage outputMessage = new FeignOutputMessage(request);
            converter.write(body, contentType, (HttpOutputMessage)outputMessage);
            return outputMessage;
        }
        return null;
    }

    private FeignOutputMessage checkAndWrite(Object body, Type genericType, MediaType contentType, GenericHttpMessageConverter converter, RequestTemplate request) throws IOException {
        if (converter.canWrite(genericType, body.getClass(), contentType)) {
            this.logBeforeWrite(body, contentType, (HttpMessageConverter)converter);
            FeignOutputMessage outputMessage = new FeignOutputMessage(request);
            converter.write(body, genericType, contentType, (HttpOutputMessage)outputMessage);
            return outputMessage;
        }
        return null;
    }

    private void logBeforeWrite(Object requestBody, MediaType requestContentType, HttpMessageConverter messageConverter) {
        if (log.isDebugEnabled()) {
            if (requestContentType != null) {
                log.debug("Writing [" + requestBody + "] as \"" + requestContentType + "\" using [" + messageConverter + "]");
            } else {
                log.debug("Writing [" + requestBody + "] using [" + messageConverter + "]");
            }
        }
    }

    private boolean isFormRelatedContentType(MediaType requestContentType) {
        return this.isMultipartType(requestContentType) || this.isFormUrlEncoded(requestContentType);
    }

    private boolean isMultipartType(MediaType requestContentType) {
        return Arrays.asList(MediaType.MULTIPART_FORM_DATA, MediaType.MULTIPART_MIXED, MediaType.MULTIPART_RELATED).contains(requestContentType);
    }

    private boolean isFormUrlEncoded(MediaType requestContentType) {
        return Objects.equals(MediaType.APPLICATION_FORM_URLENCODED, requestContentType);
    }

    protected boolean binaryContentType(FeignOutputMessage outputMessage) {
        MediaType contentType = outputMessage.getHeaders().getContentType();
        return contentType == null || Stream.of(MediaType.APPLICATION_CBOR, MediaType.APPLICATION_OCTET_STREAM, MediaType.APPLICATION_PDF, MediaType.IMAGE_GIF, MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG).anyMatch(mediaType -> mediaType.includes(contentType));
    }

    protected final class FeignOutputMessage
    implements HttpOutputMessage {
        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final HttpHeaders httpHeaders;

        private FeignOutputMessage(RequestTemplate request) {
            this.httpHeaders = this.getHttpHeaders(request.headers());
        }

        public OutputStream getBody() throws IOException {
            return this.getOutputStream();
        }

        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public ByteArrayOutputStream getOutputStream() {
            return this.outputStream;
        }

        HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
            HttpHeaders httpHeaders = new HttpHeaders();
            for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
                httpHeaders.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
            }
            return httpHeaders;
        }
    }
}

