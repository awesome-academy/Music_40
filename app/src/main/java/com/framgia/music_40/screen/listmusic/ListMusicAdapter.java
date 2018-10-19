package com.framgia.music_40.screen.listmusic;

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
import com.framgia.music_40.data.model.Music;
import com.framgia.music_40.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.ViewHolder> {

    private Context mContext;
    private List<Music> mMusicList;
    private OnItemRecyclerViewClickListener mListener;

    ListMusicAdapter(Context context, OnItemRecyclerViewClickListener listener) {
        mContext = context;
        mMusicList = new ArrayList<>();
        mListener = listener;
    }

    void updateListMusic(List<Music> musicList) {
        if (musicList != null) {
            mMusicList.clear();
            mMusicList.addAll(musicList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_music_list_screen, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.BindData(mMusicList.get(i));
    }

    @Override
    public int getItemCount() {
        return mMusicList != null ? mMusicList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMusicTitle;
        private ImageView mMusicImage;
        private OnItemRecyclerViewClickListener mListener;

        ViewHolder(@NonNull View itemView, OnItemRecyclerViewClickListener listener) {
            super(itemView);
            mMusicImage = itemView.findViewById(R.id.image_music);
            mMusicTitle = itemView.findViewById(R.id.text_view_title);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        void BindData(Music music) {
            mMusicTitle.setText(music.getMusicName());
            Glide.with(mContext)
                    .load(music.getImage())
                    .apply(new RequestOptions().placeholder(R.drawable.no_image))
                    .into(mMusicImage);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(getAdapterPosition());
            }
        }
    }
}
