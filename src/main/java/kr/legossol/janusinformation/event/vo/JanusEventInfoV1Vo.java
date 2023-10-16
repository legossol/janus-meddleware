package kr.legossol.janusinformation.event.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JanusEventInfoV1Vo {

    @NotNull
    private String emitter;
    private int type;
    private String timestamp;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class HandlerVo{
        private long handleId;
        private long sessionId;
        private String opaqueId;
        private HandlerEventVo event;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class HandlerEventVo{
        private String name;
        private String plugin;
        private String opaqueId;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(ToStringStyle.MULTI_LINE_STYLE);
        }
    }

    // type = 256
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoreVo{
        private int subtype;
        private CoreEventVo event;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class CoreEventVo{
        private String status;
        private int signum;
    }
    // type = 1
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SessionVo{
        private int sessionId;
        private SessionEventVo event;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SessionEventVo{
        private String name;
        private SessionTransportInfoVo transport;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SessionTransportInfoVo{
        private String transport;
        private int id;
    }
    // type = 2
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PluginVo{
        private Long sessionId;
        private Long handleId;
        private String opaqueId;
        private PluginEventVo event;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PluginEventVo{
        private String name;
        private String plugin;
        private PluginEventDataVo data;//can null
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class PluginEventDataVo{
        private String event;
        private String room;
        private int id;
        private int privateId;
        private String display;
    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    // type =128 socket
    public static class SocketVo{
        private SocketEventVo event;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SocketEventVo{
        private String transport;
        private String id;
        private SocketEventDataVo data;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    static class SocketEventDataVo{
        private String event;
        private boolean adminApi;
        private String ip;
    }
}
