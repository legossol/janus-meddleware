package kr.legossol.janusinformation.common.feign;

import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import kr.legossol.janusinformation.common.gson.GsonConfig;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

/*
 * <font color="red">주의 !!!</font> <br/>
 * {@link org.springframework.context.annotation.Configuration} annotation을 명시하지 말 것! <br/>
 * <a href="https://blog.leocat.kr/notes/2019/03/27/feign-open-feign-configuration">참고</a>
 * <a href="https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html#spring-cloud-feign-overriding-defaults">Official Document</>
 **/
public class FeignClientConfig {

    @Bean
    @Qualifier(GsonConfig.CUSTOM_HTTP_MESSAGE_CONVERTERS_BEAN_NAME)
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> customConverters) {
        return new ResponseEntityDecoder(new SpringDecoder(customConverters));
    }
    @Bean
    @Qualifier(GsonConfig.CUSTOM_HTTP_MESSAGE_CONVERTERS_BEAN_NAME)
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> customConverters) {
        return new SpringEncoder(customConverters);
    }
    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(1000, 2000, 3);
    }
    @Bean
    @ConditionalOnProperty("feign.auth.basic.enabled")
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(@Value("${feign.auth.basic.id:msatest}") String basicAuthId, @Value("${feign.auth.basic.password:msaauthtest}") String basicAuthPassword) {
        return new BasicAuthRequestInterceptor(basicAuthId, basicAuthPassword);
    }
}
