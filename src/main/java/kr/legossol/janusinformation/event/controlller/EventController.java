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
                String emitter = node.get("emitter").asText();
                int type = node.get("type").asInt();
                if (type == 1) {
                    //session event
                    JanusEventInfoV1Vo.SessionVo sessionVo = objectMapper.treeToValue(node, JanusEventInfoV1Vo.SessionVo.class);
                } else if (type == 2) {
                    //handle event
                    JanusEventInfoV1Vo.HandlerVo handlerEventVo = objectMapper.treeToValue(node, JanusEventInfoV1Vo.HandlerVo.class);
                    log.info("typed value:: {}",handlerEventVo.toString());
                } else if (type == 64) {
                    //plugin evnet 방 입장 등

                } else if (type == 256) {
                    String status = node.get("status").asText();
                    if (status.equals("started")) {
                        log.info("janus service started on {}, startUp Service info::{}",emitter,node);
                        return;
                    } else if (status.equals("shutdown")) {
                        log.info("janus service stopped :: {}", emitter);
                        return;
                    }
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
