package sansam.statemachine.v1;

import lombok.Data;
import sansam.statemachine.v1.state.LogOutState;
import sansam.statemachine.v1.state.IState;
import sansam.statemachine.v1.state.StateEnum;
import sansam.statemachine.v1.state.UnconnectedState;

/**
 * <p>
 * Context
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 15:29
 */
@Data
public class Context {

    public static final IState UNCONNECT_STATE = new UnconnectedState(StateEnum.UNCONNECTED);

    public static final IState CONNECT_STATE = new LogOutState(StateEnum.CONNECTED);

    private IState state;

    public Context(IState state) {
        this.state = state;
    }

    public void connect() {
        this.setState(this.state.connect(this));
    }

    public void logOut() {
        setState(this.state.logOut(this));
    }
}
