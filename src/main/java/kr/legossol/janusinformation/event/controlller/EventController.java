package kr.legossol.janusinformation.event.controlller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import kr.legossol.janusinformation.event.vo.JanusEventInfoV1Vo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "janus/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final ObjectMapper objectMapper;

    @PostMapping
    public void listenEvent(@RequestBody String data) {
        log.info("full data : {}", data);
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(data);

            for (JsonNode node : jsonNode) {
                int type = node.get("type").asInt();
                if (type == 2) {
                    JanusEventInfoV1Vo.HandlerVo handlerEventVo = objectMapper.treeToValue(node, JanusEventInfoV1Vo.HandlerVo.class);
                    log.info("typed value:: {}",handlerEventVo.toString());
//                    log.info("\r\nnode :: {} \r\n type::{}", node, type);
                }
            }
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }


    @PostMapping(value = "/hi")
    public void listen(@RequestParam(value = "req") String request) {
        log.info(request);
    }


}
