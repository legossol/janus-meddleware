package kr.legossol.janusinformation.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    private String name;
    private String status;
    private String id;
    private DateInfo data;;
    private JanusInfo info;
    private Transport transport;
}
