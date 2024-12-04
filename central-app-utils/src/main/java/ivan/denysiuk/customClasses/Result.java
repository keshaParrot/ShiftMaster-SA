package ivan.denysiuk.customClasses;

import lombok.*;

@Getter
@Builder
public class Result<T> {
    @Getter
    private final T value;
    @Getter
    private final String message;
    private boolean error = false;


    private Result(T value, String message, boolean error) {
        this.value = value;
        this.message = message;
        this.error = error;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value,null,false);
    }
    public static <T> Result<T> success(T value, String message) {
        return new Result<>(value, message,false);
    }
    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error,true);
    }

    public boolean isSuccess() {
        return !error;
    }
    public boolean hasError() {
        return error;
    }
    public boolean isPresent() {
        return value!=null;
    }

}
