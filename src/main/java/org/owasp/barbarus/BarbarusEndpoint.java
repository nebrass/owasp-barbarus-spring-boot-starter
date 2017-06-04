package org.owasp.barbarus;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(path = "${barbarus.controller.root}")
public class BarbarusEndpoint {
    //TODO clusteriser la liste
    private final Map<String, SseEmitter> emitters;
    private final EventsPublisher publisher;
    private final BarbarusProperties barbarusProperties;

    public BarbarusEndpoint(EventsPublisher publisher, BarbarusProperties barbarusProperties) {
        this.emitters = new ConcurrentHashMap<>();
        this.publisher = publisher;
        this.barbarusProperties = barbarusProperties;
    }

    @GetMapping(value = "${barbarus.controller.sse-emitter}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEmitter() throws IOException {
        String viewId = UUID.randomUUID().toString();
        //TODO timeout personnalisé
        SseEmitter emitter = new SseEmitter();
        emitter.onCompletion(() -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));

        this.insertNewEmitter(viewId, emitter);

        SseResponseBuilder builder = new SseResponseBuilder();
        builder.name(this.barbarusProperties.getViewId());
        builder.data(viewId);

        emitter.send(builder);

        return emitter;
    }

    @CachePut(value = "barbarusEmitters", key = "#viewId + 1")
    public SseEmitter insertNewEmitter(String viewId, SseEmitter emitter) {
        return emitters.put(viewId, emitter);
    }

    @PostMapping(path = "${barbarus.controller.login}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void login(@RequestBody BarbarusLoginDto loginDto) {
        this.publisher.publishEvent(loginDto);
    }

    @Cacheable("barbarusEmitters")
    public Map<String, SseEmitter> getEmitters() {
        return emitters;
    }
}
