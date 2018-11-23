package Data;

public class UserResponse {

    private String status;
    private String message;
    private UserCollection data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserCollection getData() {
        return data;
    }

    public void setData(UserCollection data) {
        this.data = data;
    }
}
