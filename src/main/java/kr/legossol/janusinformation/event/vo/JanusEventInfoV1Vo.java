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

    /**
     * {
     *     "emitter": "MyJanusInstance",
     *     "type": 2,
     *     "timestamp": 1697425929933669,
     *     "session_id": 4364200986980670,
     *     "handle_id": 6140227625672782,
     *     "opaque_id": "streamingtest-4x0qATq12ZED",
     *     "event": {
     *         "name": "attached",
     *         "plugin": "janus.plugin.streaming",
     *         "opaque_id": "streamingtest-4x0qATq12ZED"
     *     }
     * }
     *  {
     *         "emitter": "MyJanusInstance",
     *         "type": 2,
     *         "timestamp": 1697431895736753,
     *         "session_id": 4011451211942792,
     *         "handle_id": 4129493471587510,
     *         "opaque_id": "streamingtest-b8XRNO8oLVqP",
     *         "event": {
     *             "name": "attached",
     *             "plugin": "janus.plugin.streaming",
     *             "opaque_id": "streamingtest-b8XRNO8oLVqP"
     *         }
     *     }
     */
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

    /**
     *  {
     *         "emitter": "MyJanusInstance",
     *         "type": 1,
     *         "timestamp": 1697431895714365,
     *         "session_id": 4011451211942792, 세션아이디로 누군가 뭘 만들걸 알 수 있을 듯
     *         "event": {
     *             "name": "created",
     *             "transport": {
     *                 "transport": "janus.transport.http",
     *                 "id": "0x7f0be00095a0"
     *             }
     *         }
     *     },
     */
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
        private String name; // created등
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
    /**
     * type =64
     * {
     *     "emitter": "MyJanusInstance",
     *     "type": 64,
     *     "timestamp": 1697434710456556,
     *     "session_id": 2831066724700536,
     *     "handle_id": 1350460530388704,
     *     "opaque_id": "streamingtest-mbKxVTetLMwn",
     *     "event": {
     *         "plugin": "janus.plugin.streaming",
     *         "data": {
     *             "status": "starting",
     *             "id": 1
     *         }
     *     }
     * }
     * {
     *     "emitter": "MyJanusInstance",
     *     "type": 64,
     *     "timestamp": 1666924980198028,
     *     "session_id": 1210004910893710,
     *     "handle_id": 6598715083910126,
     *     "opaque_id": "사용자 고유ID", // client측에서 설정
     *     "event": {
     *        "plugin": "janus.plugin.audiobridge",
     *        "data": {
     *           "event": "joined",
     *           "room": "audioRoom1",
     *           "id": 448482516212195,
     *           "private_id": 2786398561,
     *           "display": "홍길동"
     *        }
     *     }
     *  }
     *
     */


    /**
     * type = 256
     * [
     *     {
     *         "emitter": "MyJanusInstance",
     *         "type": 256,
     *         "subtype": 1,
     *         "timestamp": 1697431791024043,
     *         "event": {
     *             "status": "started",
     *             "info": {
     *                 "janus": "server_info",
     *                 "name": "Janus WebRTC Server",
     *                 "version": 104,
     *                 "version_string": "0.10.4",
     *                 "author": "Meetecho s.r.l.",
     *                 "commit-hash": "not-a-git-repo",
     *                 "compile-time": "Mon Aug 31 18:39:44 EDT 2020",
     *                 "log-to-stdout": true,
     *                 "log-to-file": true,
     *                 "log-path": "/var/log/janus.log",
     *                 "data_channels": false,
     *                 "accepting-new-sessions": true,
     *                 "session-timeout": 60,
     *                 "reclaim-session-timeout": 0,
     *                 "candidates-timeout": 45,
     *                 "server-name": "MyJanusInstance",
     *                 "local-ip": "172.17.0.3",
     *                 "public-ip": "172.17.0.3",
     *                 "ipv6": false,
     *                 "ice-lite": false,
     *                 "ice-tcp": false,
     *                 "full-trickle": false,
     *                 "mdns-enabled": true,
     *                 "min-nack-queue": 200,
     *                 "twcc-period": 200,
     *                 "stun-server": "172.17.0.2:3478",
     *                 "static-event-loops": 0,
     *                 "api_secret": false,
     *                 "auth_token": false,
     *                 "event_handlers": true,
     *                 "opaqueid_in_api": false,
     *                 "dependencies": {
     *                     "glib2": "2.56.4",
     *                     "jansson": "2.11",
     *                     "libnice": "0.1.17",
     *                     "libsrtp": "libsrtp 1.5.4",
     *                     "libcurl": "7.61.1",
     *                     "crypto": "OpenSSL 1.1.1c FIPS  28 May 2019"
     *                 },
     *                 "transports": {
     *                     "janus.transport.http": {
     *                         "name": "JANUS REST (HTTP/HTTPS) transport plugin",
     *                         "author": "Meetecho s.r.l.",
     *                         "description": "This transport plugin adds REST (HTTP/HTTPS) support to the Janus API via libmicrohttpd.",
     *                         "version_string": "0.0.2",
     *                         "version": 2
     *                     },
     *                     "janus.transport.websockets": {
     *                         "name": "JANUS WebSockets transport plugin",
     *                         "author": "Meetecho s.r.l.",
     *                         "description": "This transport plugin adds WebSockets support to the Janus API via libwebsockets.",
     *                         "version_string": "0.0.1",
     *                         "version": 1
     *                     }
     *                 },
     *                 "events": {
     *                     "janus.eventhandler.gelfevh": {
     *                         "name": "JANUS GelfEventHandler plugin",
     *                         "author": "Mirko Brankovic <mirkobrankovic@gmail.com>",
     *                         "description": "This is event handler plugin for Janus, which forwards events via TCP/UDP to GELF server.",
     *                         "version_string": "0.0.1",
     *                         "version": 1
     *                     },
     *                     "janus.eventhandler.sampleevh": {
     *                         "name": "JANUS SampleEventHandler plugin",
     *                         "author": "Meetecho s.r.l.",
     *                         "description": "This is a trivial sample event handler plugin for Janus, which forwards events via HTTP POST.",
     *                         "version_string": "0.0.1",
     *                         "version": 1
     *                     }
     *                 },
     *                 "loggers": {},
     *                 "plugins": {
     *                 "janus.plugin.streaming": {
     *                         "name": "JANUS Streaming plugin",
     *                         "author": "Meetecho s.r.l.",
     *                         "description": "This is a streaming plugin for Janus, allowing WebRTC peers to watch/listen to pre-recorded files or media generated by an external source.",
     *                         "version_string": "0.0.8",
     *                         "version": 8
     *                     },
     *                     "janus.plugin.sip": {
     *                         "name": "JANUS SIP plugin",
     *                         "author": "Meetecho s.r.l.",
     *                         "description": "This is a simple SIP plugin for Janus, allowing WebRTC peers to register at a SIP server and call SIP user agents through a Janus instance.",
     *                         "version_string": "0.0.8",
     *                         "version": 8
     *                     }
     *                 }
     *             }
     *         }
     *     }
     * ]
     *
     * [
     *    {
     *       "emitter": "MyJanusInstance",
     *       "type": 256,
     *       "subtype": 2,
     *       "timestamp": 1697459440420233,
     *       "event": {
     *          "status": "shutdown",
     *          "signum": 2
     *       }
     *    }
     * ]
     */
}
