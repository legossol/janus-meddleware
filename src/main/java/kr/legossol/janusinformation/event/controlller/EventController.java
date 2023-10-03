package kr.legossol.janusinformation.event.controlller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.legossol.janusinformation.event.dto.EventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "janus/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final ObjectMapper objectMapper;

    @PostMapping
    public void listenEvent(@RequestBody String eventDto) {
        log.info("request :: {} ,type :: {}", eventDto, eventDto.getClass());
        List<EventDto> decodedJson = new ArrayList<>();
        try {
            decodedJson = objectMapper.readValue(eventDto, new TypeReference<List<EventDto>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.info("들어왔다");
        for (EventDto eachEvent : decodedJson) {
            log.info("emitter : {}",eachEvent.getEmitter());
            log.info("type : {}",eachEvent.getType());
            log.info("session_id : {}",eachEvent.getSession_id());
            log.info("event : {}",eachEvent.getEvent());
        }
    }

    @PostMapping(value = "/hi")
    public void listen(@RequestParam(value = "req") String request) {
        log.info(request);
    }


}
