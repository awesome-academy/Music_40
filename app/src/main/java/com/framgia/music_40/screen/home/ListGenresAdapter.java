package com.framgia.music_40.screen.home;

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

public class ListGenresAdapter extends RecyclerView.Adapter<ListGenresAdapter.ViewHolder> {

    private Context mContext;
    private List<Genres> mGenresList;
    private OnItemListGenresClick mListener;

    ListGenresAdapter(Context context, List<Genres> genresList, OnItemListGenresClick listener) {
        mContext = context;
        mGenresList = genresList;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_item_home_screen, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.BindData(mGenresList.get(i));
    }

    @Override
    public int getItemCount() {
        return mGenresList != null ? mGenresList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String DRAWABLE = "drawable";

        private TextView mTextView;
        private ImageView mImageView;
        private OnItemListGenresClick mListener;

        ViewHolder(@NonNull View itemView, OnItemListGenresClick listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_cover);
            mTextView = itemView.findViewById(R.id.text_view);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        void BindData(Genres genres) {
            mTextView.setText(genres.getGenreName());
            mImageView.setImageResource(mContext.getResources()
                    .getIdentifier(genres.getGenreImage(), DRAWABLE, mContext.getPackageName()));
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(getAdapterPosition());
            }
        }
    }
}
