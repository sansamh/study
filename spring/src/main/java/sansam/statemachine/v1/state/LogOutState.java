package sansam.statemachine.v1.state;

import sansam.statemachine.v1.Context;

/**
 * <p>
 * LogOutState
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 16:41
 */
public class LogOutState extends AbstractState {
    public LogOutState(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState connect(Context context) {
        return null;
    }

    @Override
    public IState beginLogin(Context context) {
        return null;
    }

    @Override
    public IState loginFailure(Context context) {
        return null;
    }

    @Override
    public IState loginSuccess(Context context) {
        return null;
    }

    @Override
    public IState logOut(Context context) {
        IState nextState = Context.UNCONNECT_STATE;
        System.out.println(String.format("Switch state from %s to %s", context.getState(), nextState));
        return nextState;
    }
}
