package sansam.statemachine.v1.state;

import lombok.Data;

/**
 * <p>
 * AbstractState
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 15:44
 */
@Data
public abstract class AbstractState implements IState {

    private StateEnum stateEnum;

    public AbstractState(StateEnum stateEnum) {
        this.stateEnum = stateEnum;
    }

}
