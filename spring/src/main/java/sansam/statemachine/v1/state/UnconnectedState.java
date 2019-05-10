package sansam.statemachine.v1.state;

import sansam.statemachine.v1.Context;

/**
 * <p>
 * UnconnectedState
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 15:46
 */
public class UnconnectedState extends AbstractState {

    public UnconnectedState(StateEnum stateEnum) {
        super(stateEnum);
    }

    @Override
    public IState connect(Context context) {
        IState nextState = Context.CONNECT_STATE;
        System.out.println(String.format("Switch state from %s to %s", context.getState(), nextState));
        return nextState;

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
        return null;
    }
}
