package sansam.statemachine.v2;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * <p>
 * StateMachineConfig
 * 配置状态机，通过注解打开状态机功能。
 * 配置类一般要继承EnumStateMachineConfigurerAdapter类，
 * 并且重写一些configure方法以配置状态机的初始状态以及事件与状态转移的联系。
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 17:11
 */
@Configuration
@EnableStateMachine // 开启状态机配置
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<RegStateEnum, RegEventEnum> {
    @Override
    public void configure(StateMachineStateConfigurer<RegStateEnum, RegEventEnum> states) throws Exception {
        states.withStates()
                .initial(RegStateEnum.UNCONNECTED)
                .states(EnumSet.allOf(RegStateEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RegStateEnum, RegEventEnum> transitions) throws Exception {
        transitions.withExternal()
                // connect unconnected 2 connect
                .source(RegStateEnum.UNCONNECTED)
                .target(RegStateEnum.CONNECTED)
                .event(RegEventEnum.CONNECT)
                // beginToLogin connect 2 logining
                .and().withExternal()
                .source(RegStateEnum.CONNECTED)
                .target(RegStateEnum.LOGINING)
                .event(RegEventEnum.BEGIN_TO_LOGIN)
                // login failure LOGINING -> UNCONNECTED
                .and().withExternal()
                .source(RegStateEnum.LOGINING)
                .target(RegStateEnum.UNCONNECTED)
                .event(RegEventEnum.LOGIN_FAILURE)
                // login success LOGINING -> LOGIN_INTO_SYSTEM
                .and().withExternal()
                .source(RegStateEnum.LOGINING)
                .target(RegStateEnum.LOGIN_INTO_SYSTEM)
                .event(RegEventEnum.LOGIN_SUCCESS)
                // logout LOGIN_INTO_SYSTEM -> UNCONNECTED
                .and().withExternal()
                .source(RegStateEnum.LOGIN_INTO_SYSTEM)
                .target(RegStateEnum.UNCONNECTED)
                .event(RegEventEnum.LOGOUT);
    }
}
