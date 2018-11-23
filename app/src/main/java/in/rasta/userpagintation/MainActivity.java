package in.rasta.userpagintation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;

import APICalls.ApiInterface;
import APICalls.ApiUtils;
import Adapters.UserAdapter;
import Data.UserResponse;
import Utility.PaginationScrollListener;
import Utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private UserAdapter userAdapter;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;

    private int offset = 1;
    private int limit = 15;
    private int limitIncreaseConstant = 1;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();
        initializePagination();
        getUsers();
    }

    private void loadViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initializePagination() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(userAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {

                loadNextPage();
            }


            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

        });
    }

    private void getUsers() {
        Util.showProgressDialog(MainActivity.this, "Loading users...");
        ApiInterface apiService = ApiUtils.getClient().create(ApiInterface.class);

        final Call<UserResponse> users = apiService.getUsers(offset, limit);

        users.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Util.removeProgressDialog();

                    userAdapter = new UserAdapter(MainActivity.this);
                    userAdapter.add(response.body().getData());
                    recyclerView.setAdapter(userAdapter);

                    if (!response.body().getData().isHas_more())
                        isLastPage = true;
                } else {
                    Util.removeProgressDialog();
                    try {
                        Util.getErrorMessage(response.errorBody().string(), response.code(), MainActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Util.removeProgressDialog();
                Util.showToast(MainActivity.this, "Something went wrong...");
            }
        });
    }

    private void loadNextPage() {
        Util.showProgressDialog(MainActivity.this, "Loading users...");

        ApiInterface apiService = ApiUtils.getClient().create(ApiInterface.class);

        Call<UserResponse> users = apiService.getUsers(offset, limit * ++limitIncreaseConstant);

        users.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Util.removeProgressDialog();
                    userAdapter.add(response.body().getData());

                    if (!response.body().getData().isHas_more())
                        isLastPage = true;
                } else {
                    Util.removeProgressDialog();
                    try {
                        Util.getErrorMessage(response.errorBody().string(), response.code(), MainActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Util.removeProgressDialog();
                Util.showToast(MainActivity.this, "Something went wrong...");
            }
        });
    }
}
