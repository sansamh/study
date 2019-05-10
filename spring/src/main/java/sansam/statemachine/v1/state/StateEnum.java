package sansam.statemachine.v1.state;

/**
 * <p>
 * StateEnum 状态枚举
 * </p>
 *
 * @author houcb
 * @since 2019-05-09 15:21
 */
public enum StateEnum {

    /**
     * 未连接
     */
    UNCONNECTED(0, "UNCONNECTED"),
    /**
     * 已连接
     */
    CONNECTED(1, "CONNECTED"),
    /**
     * 正在登录
     */
    BEGINLOGINNING(2, "BEGINLOGINNING"),
    /**
     * 登录失败
     */
    LOGINFAILURE(3, "LOGINFAILURE"),
    /**
     * 已登录
     */
    LOGINSUCCESS(4, "LOGINSUCCESS"),
    /**
     * 登出
     */
    LOGOUT(5, "LOGOUT");

    private int key;
    private String value;

    StateEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

}
