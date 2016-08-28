package com.testapps.movieslist.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.testapps.movieslist.R;
import com.testapps.movieslist.models.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener {

    private ArrayList<Movie> mItemsList;
    private Context mContext;
    private OnItemClickListener mListener;

    public MovieAdapter(Context context, ArrayList<Movie> listItems, OnItemClickListener onItemClickListener) {
        this.mItemsList = listItems;
        this.mContext = context;
        mListener = onItemClickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public MovieAdapter(Context context, ArrayList<Movie> listData) {
        this.mContext = context;
        this.mItemsList = listData;
    }

    public void refresh(ArrayList<Movie> listData) {
        this.mItemsList = listData;
        notifyDataSetChanged();
    }

    public void clear() {
        mItemsList.clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mItemsList.isEmpty();
    }

    public Movie getMovieData(int i) {
        return mItemsList.get(i);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Movie eachMovie = mItemsList.get(position);

        Picasso.with(mContext)
                .load(eachMovie.getPosterUrl())
                .error(R.color.gray_overlay)
                .placeholder(R.color.gray_overlay)
                .into(holder.mPoster);

        holder.tvTitle.setText(eachMovie.getTitle());
        holder.tvOverview.setText(eachMovie.getOverview());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_thumbnail) ImageView mPoster;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_movie_overview) TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}