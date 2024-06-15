package ivan.denysiuk.unswer;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Result<T> {
    private T value;
    private String error;

    public Result(T value) {
        this.value = value;
    }
    public Result(String error) {
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
