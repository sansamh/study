package io.sansam.test;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * <p>
 * EstateBaseResponse
 * </p>
 *
 * @author houcb
 * @since 2019-09-05 10:52
 */
public class EstateBaseResponse implements Serializable {

    private String status;
    private String msg;

    private String data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EstateBaseResponse.class.getSimpleName() + "[", "]")
                .add("status='" + status + "'")
                .add("msg='" + msg + "'")
                .add("data='" + data + "'")
                .toString();
    }
}
