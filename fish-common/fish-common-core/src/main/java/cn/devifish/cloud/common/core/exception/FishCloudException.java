package cn.devifish.cloud.common.core.exception;

/**
 * FishCloudException
 * 框架基础异常，框架派生的异常均需继承
 *
 * @author Devifish
 * @date 2020/7/2 10:15
 */
public class FishCloudException extends RuntimeException {

    public FishCloudException() {
    }

    public FishCloudException(String message) {
        super(message);
    }

    public FishCloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public FishCloudException(Throwable cause) {
        super(cause);
    }
}