package org.owasp.barbarus;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.http.MediaType.TEXT_PLAIN;

public class SseResponseBuilder implements SseEmitter.SseEventBuilder {

    private final Set<ResponseBodyEmitter.DataWithMediaType> dataToSend =
        new LinkedHashSet<>(4);

    private StringBuilder sb;

    @Override
    public SseResponseBuilder comment(String comment) {
        append(":").append(comment != null ? comment : "").append("\n");
        return this;
    }

    @Override
    public SseResponseBuilder name(String name) {
        append("event:").append(name != null ? name : "").append("\n");
        return this;
    }

    @Override
    public SseResponseBuilder id(String id) {
        append("id:").append(id != null ? id : "").append("\n");
        return this;
    }

    @Override
    public SseResponseBuilder reconnectTime(long reconnectTimeMillis) {
        append("retry:").append(String.valueOf(reconnectTimeMillis)).append("\n");
        return this;
    }

    @Override
    public SseResponseBuilder data(Object object) {
        return data(object, null);
    }

    @Override
    public SseResponseBuilder data(Object object, MediaType mediaType) {
        append("data:");
        saveAppendedText();
        this.dataToSend.add(new ResponseBodyEmitter.DataWithMediaType(object, mediaType));
        append("\n");
        return this;
    }

    SseResponseBuilder append(String text) {
        if (this.sb == null) {
            this.sb = new StringBuilder();
        }
        this.sb.append(text);
        return this;
    }

    @Override
    public Set<ResponseBodyEmitter.DataWithMediaType> build() {
        if (!StringUtils.hasLength(this.sb) && this.dataToSend.isEmpty()) {
            return Collections.<ResponseBodyEmitter.DataWithMediaType>emptySet();
        }
        append("\n");
        saveAppendedText();
        return this.dataToSend;
    }

    private void saveAppendedText() {
        if (this.sb != null) {
            this.dataToSend.add(new ResponseBodyEmitter.DataWithMediaType(this.sb.toString(), TEXT_PLAIN));
            this.sb = null;
        }
    }
}
