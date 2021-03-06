package com.framgia.music_40.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.music_40.R;
import com.framgia.music_40.data.model.Genres;
import com.framgia.music_40.utils.OnItemRecyclerViewClickListener;
import java.util.List;

public class ListGenresAdapter extends RecyclerView.Adapter<ListGenresAdapter.ViewHolder> {

    private Context mContext;
    private List<Genres> mGenresList;
    private OnItemRecyclerViewClickListener mListener;

    ListGenresAdapter(Context context, List<Genres> genresList,
            OnItemRecyclerViewClickListener listener) {
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

        private TextView mGenresName;
        private ImageView mGenresImage;
        private OnItemRecyclerViewClickListener mListener;

        ViewHolder(@NonNull View itemView, OnItemRecyclerViewClickListener listener) {
            super(itemView);
            mGenresImage = itemView.findViewById(R.id.image_cover);
            mGenresName = itemView.findViewById(R.id.text_view);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        void BindData(Genres genres) {
            mGenresName.setText(genres.getGenreName());
            Glide.with(mContext)
                    .load(genres.getGenreImage())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image))
                    .into(mGenresImage);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(getAdapterPosition());
            }
        }
    }
}
