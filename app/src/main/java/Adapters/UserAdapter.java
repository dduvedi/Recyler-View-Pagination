package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import Data.User;
import Data.UserCollection;
import Utility.SquareImageView;
import in.rasta.userpagintation.R;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM = 0;

    private UserCollection userCollections;
    private Context context;

    public UserAdapter(Context context) {
        this.context = context;
        this.userCollections = new UserCollection();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        viewHolder = getViewHolder(viewGroup, inflater);
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new UserViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UserViewHolder userViewHolder = (UserViewHolder) viewHolder;
        userViewHolder.setIsRecyclable(false);
        User user = userCollections.getUsers().get(i);
        userViewHolder.userName.setText(user.getName());
        Glide.with(context).load(getUrl(user.getImage())).into(userViewHolder.userImage);
        LinearLayout innerItemContainer = new LinearLayout(context);
        innerItemContainer.setOrientation(LinearLayout.VERTICAL);

        boolean isOdd = user.getItems().size() % 2 != 0;
        if (isOdd)
            innerItemContainer = createOddView(innerItemContainer, user.getItems().get(0));

        innerItemContainer = createEvenViews(innerItemContainer, user.getItems(), isOdd);

        userViewHolder.itemContainer.addView(innerItemContainer);

    }

    private LinearLayout createOddView(LinearLayout innerItemContainer, String imageUrl) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        innerItemContainer.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(context);

        LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        imageViewParams.setMargins(6, 6, 6, 6);
        imageView.setLayoutParams(imageViewParams);
        Glide.with(context).load(getUrl(imageUrl)).into(imageView);


        innerItemContainer.addView(imageView);
        return innerItemContainer;
    }

    private LinearLayout createEvenViews(LinearLayout innerItemContainer, List<String> items, boolean isOdd) {
        int count = items.size() / 2;
        int i = 0;
        if (isOdd)
            i = 1;

        for (; i <= count; i++) {

            LinearLayout linearLayout = new LinearLayout(context);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(10);
            linearLayout.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 5);
            imageViewParams.setMargins(6, 6, 6, 6);

            SquareImageView imageView = new SquareImageView(context);
            imageView.setLayoutParams(imageViewParams);
            Glide.with(context).load(getUrl(items.get(i))).into(imageView);
            linearLayout.addView(imageView);

            SquareImageView imageView1 = new SquareImageView(context);

            imageView1.setLayoutParams(imageViewParams);
            Glide.with(context).load(getUrl(items.get(i < items.size() - 1 ? i + 1 : i))).into(imageView1);
            linearLayout.addView(imageView1);
            innerItemContainer.addView(linearLayout);

        }
        return innerItemContainer;
    }

    private String getUrl(String url) {
        return url.replace("http", "https") + ".png";
    }

    @Override
    public int getItemCount() {
        return userCollections.getUsers() == null ? 0 : userCollections.getUsers().size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }


    public void add(UserCollection userCollection) {
        int size = 0;
        if (userCollections != null && userCollections.getUsers() != null) {
            size = userCollections.getUsers().size();
            userCollections.setUsers(new ArrayList<User>());
            userCollections.getUsers().addAll(userCollection.getUsers());
        } else {
            userCollections.setUsers(new ArrayList<User>());
            userCollections.getUsers().addAll(userCollection.getUsers());
        }

        notifyItemRangeChanged(size, userCollections.getUsers().size());
    }

    protected class UserViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView userImage;
        public TextView userName;
        public LinearLayout itemContainer;

        public UserViewHolder(View itemView) {
            super(itemView);
            userImage = (CircularImageView) itemView.findViewById(R.id.userImage);
            userName = (TextView) itemView.findViewById(R.id.userName);
            itemContainer = (LinearLayout) itemView.findViewById(R.id.itemContainer);
        }
    }
}
