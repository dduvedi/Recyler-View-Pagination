package APICalls;

import Data.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<UserResponse> getUsers(@Query("offset") int offset, @Query("limit") int limit);

}
