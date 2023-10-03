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
public class DateInfo {
    private String event;
    private boolean admin_api;
    private String ip;
    private int port;
}
