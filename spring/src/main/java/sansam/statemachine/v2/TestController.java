package sansam.statemachine.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * TestController
 * </p>
 *
 * @author houcb
 * @since 2019-05-10 15:52
 */
@RestController
public class TestController {

    @Autowired
    private StateMachine<RegStateEnum, RegEventEnum> stateMachine;

    @RequestMapping("/")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/stateMachine")
    public void test() {
        stateMachine.start();
        stateMachine.sendEvent(RegEventEnum.CONNECT);
        stateMachine.sendEvent(RegEventEnum.BEGIN_TO_LOGIN);
        stateMachine.sendEvent(RegEventEnum.LOGIN_FAILURE);
        stateMachine.sendEvent(RegEventEnum.LOGOUT);
    }

}
