package dk.clanie.core;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.clanie.core.util.JsonService;

@AutoConfiguration
@ConditionalOnClass({JsonService.class, ObjectMapper.class})
public class ClanieCoreAutoConfiguration {


	@Bean
    @Lazy
    @ConditionalOnMissingBean
    JsonService jsonService(ObjectMapper objectMapper) {
        return new JsonService(objectMapper);
    }


}
