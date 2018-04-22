package com.garcia.felipe.redditapp.HomeList.UI.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.garcia.felipe.redditapp.Helpers.Image.ImageLoader;
import com.garcia.felipe.redditapp.Models.MultimediaItem;
import com.garcia.felipe.redditapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewAdapter extends RecyclerView.Adapter implements Filterable {


    private final ArrayList<MultimediaItem> originalList;
    private ValueFilter valueFilter;
    private ArrayList<MultimediaItem> filteredItems;

    private final ImageLoader imageLoader;
    private final OnItemClickListener onItemClickListener;

    public ListViewAdapter(ArrayList<MultimediaItem> itemList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.imageLoader = imageLoader;
        this.filteredItems = itemList;
        this.originalList = itemList;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        MultimediaItem item = filteredItems.get(position);
        itemViewHolder.setItemData(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void add(MultimediaItem item) {
        filteredItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public void onCloseSearch() {
        filteredItems = originalList;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements ListViewAdapterView {

        @BindView(R.id.overview)
        TextView overview;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.voteAverage)
        TextView voteAverage;
        @BindView(R.id.posterImage)
        ImageView posterImage;
        private View view;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;

        ItemViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        private void setClickListener(final MultimediaItem item, final OnItemClickListener onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(item);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(item);
                    return false;
                }
            });
        }

        @Override
        public void setTitle(String string) {
            title.setText(string);
        }

        @Override
        public void setOverview(String string) {
            overview.setText(string);
        }

        @Override
        public void setImage(String string) {
            imageLoader.load(posterImage, string, progressBar);
        }

        @Override
        public void setVoteAverage(String string) {
            voteAverage.setText(string);
        }

        void setItemData(MultimediaItem item, OnItemClickListener onItemClickListener) {
            setTitle(item.getTitle());
            setVoteAverage(item.getVoteAverage());
            setOverview(item.getOverview());
            setImage(item.getPosterPath());
            setClickListener(item, onItemClickListener);
        }
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<MultimediaItem> filterItems = new ArrayList<>();
                for (int i = 0; i < filteredItems.size(); i++) {
                    MultimediaItem currentItem = filteredItems.get(i);
                    if (currentItem.valueFound(constraint.toString())) {
                        filterItems.add(currentItem);
                    }
                }

                filteredItems = filterItems;
                results.values = filteredItems;
                results.count = filteredItems.size();
            } else {
                results.values = false;
                results.count = 0;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    }
}
