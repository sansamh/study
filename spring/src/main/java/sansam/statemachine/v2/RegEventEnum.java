package sansam.statemachine.v2;

/**
 * <p>
 * RegEventEnum
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 17:10
 */
public enum RegEventEnum {

    // 连接
    CONNECT,
    // 开始登录
    BEGIN_TO_LOGIN,
    // 登录成功
    LOGIN_SUCCESS,
    // 登录失败
    LOGIN_FAILURE,
    // 注销登录
    LOGOUT;
}
