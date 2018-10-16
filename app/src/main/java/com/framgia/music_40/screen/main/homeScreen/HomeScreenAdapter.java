package com.framgia.music_40.screen.main.homeScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Genres;
import java.util.List;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ViewHolder> {

    private Context mContext;
    private List<Genres> mGenresList;

    HomeScreenAdapter(Context context, List<Genres> genresList) {
        mContext = context;
        mGenresList = genresList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item_home_screen, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.BindData(mGenresList.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenresList != null ? mGenresList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private static final String DRAWABLE = "drawable";

        private TextView mTextView;
        private ImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_cover);
            mTextView = itemView.findViewById(R.id.text_view);
        }

        void BindData(Genres genres) {
            mTextView.setText(genres.getGenreName());
            mImageView.setImageResource(mContext.getResources()
                    .getIdentifier(genres.getGenreImage(), DRAWABLE, mContext.getPackageName()));
        }
    }
}
