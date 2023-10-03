package kr.legossol.janusinformation.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

    private String emitter;
    private double subtype;
    private int type;
    private String timestamp;
    private String session_id;
    private Event event;

    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.JSON_STYLE);
    }
}
