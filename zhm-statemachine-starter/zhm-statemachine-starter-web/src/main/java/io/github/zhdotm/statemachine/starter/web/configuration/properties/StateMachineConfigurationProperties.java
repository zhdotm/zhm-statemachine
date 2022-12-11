package io.github.zhdotm.statemachine.starter.web.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhihao.mao
 */

@ConfigurationProperties(prefix = "zhm.statemachine")
public class StateMachineConfigurationProperties {

    @Getter
    @Setter
    private Boolean enable = Boolean.FALSE;

    @Getter
    @Setter
    private Boolean print = Boolean.FALSE;
}
