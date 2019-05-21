package sansam.statemachine.v1;

import lombok.Data;
import sansam.statemachine.v1.state.StateEnum;
import sansam.statemachine.v1.state.UnconnectedState;

/**
 * <p>
 * Test
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 16:01
 */
public class Test {

    @Data
    static class Result {
        private String code;
        private String msg;
    }

    public static void main(String[] args) {
//        System.out.println(test().code);
        Context context = new Context(new UnconnectedState(StateEnum.UNCONNECTED));
        context.connect();
        context.logOut();
    }

    public static Result test() {
        Result result = new Result();
        String code;
        String msg;
        try {
            code = "1";
            msg = "success";
            result.setCode(code);
            result.setMsg(msg);
            int i = 1/0;
        } catch (Exception e) {
            code = "-1";
            msg = "failed";
            result.setCode(code);
            result.setMsg(msg);
        }
        return result;
    }

    public <T> void toArray(T[] a) {

    }
}
