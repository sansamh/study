package sansam.statemachine.v1.state;

import sansam.statemachine.v1.Context;

/**
 * <p>
 * statemachine.v1.state.IState
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 15:28
 */
public interface IState {

    IState connect(Context context);

    IState beginLogin(Context context);

    IState loginFailure(Context context);

    IState loginSuccess(Context context);

    IState logOut(Context context);

}
