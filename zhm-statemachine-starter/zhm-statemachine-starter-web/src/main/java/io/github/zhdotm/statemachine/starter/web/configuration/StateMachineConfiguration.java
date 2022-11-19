package io.github.zhdotm.statemachine.starter.web.configuration;

import io.github.zhdotm.statemachine.starter.web.configuration.properties.StateMachineConfigurationProperties;
import io.github.zhdotm.statemachine.starter.web.processor.StateMachineProcessor;
import io.github.zhdotm.statemachine.starter.web.runner.StateMachineRunner;
import io.github.zhdotm.statemachine.starter.web.support.StateMachineSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhihao.mao
 */

@Configuration
@ConditionalOnProperty(prefix = "zhm.statemachine", name = "enable", havingValue = "true")
@EnableConfigurationProperties(value = {StateMachineConfigurationProperties.class})
public class StateMachineConfiguration {

    @Bean
    public StateMachineProcessor stateMachineProcessor() {

        return new StateMachineProcessor();
    }

    @Bean
    public StateMachineRunner stateMachineRunner() {

        return new StateMachineRunner();
    }

    @Bean
    public StateMachineSupport stateMachineSupport() {

        return new StateMachineSupport();
    }
}
