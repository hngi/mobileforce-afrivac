package com.michael.afrivac.ui.popular_destination;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.michael.afrivac.R;
import com.michael.afrivac.model.DestinationSuggestion;

import java.util.ArrayList;
import java.util.List;

class AutoCompleteAdapter extends ArrayAdapter<DestinationSuggestion> {
        private Context context;
        private int resourceId;
        private List<DestinationSuggestion> initialItems, tempItems;

        public AutoCompleteAdapter(@NonNull Context context, int resourceId, List<DestinationSuggestion> initialItems) {
            super(context, resourceId, initialItems);
            this.initialItems = initialItems;
            this.context = context;
            this.resourceId = resourceId;
            tempItems = new ArrayList<>(initialItems);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            try {
                if (convertView == null) {
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    view = inflater.inflate(resourceId, parent, false);
                }

                DestinationSuggestion fruit = getItem(position);
                TextView title = view.findViewById(R.id.title);
                ImageView imageView = view.findViewById(R.id.icon);
                imageView.setImageResource(fruit.getIcon());
                title.setText(fruit.getText());
                View divider = view.findViewById(R.id.divider);
                divider.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return view;
        }

    @Nullable
        @Override
        public DestinationSuggestion getItem(int position) {
            return initialItems.get(position);
        }
        @Override
        public int getCount() {
            return initialItems == null ? 0 : initialItems.size();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @NonNull
        @Override
        public Filter getFilter() {
            return fruitFilter;
        }
        private Filter fruitFilter = new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                DestinationSuggestion fruit = (DestinationSuggestion) resultValue;
                return fruit.getText();
            }
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<DestinationSuggestion> suggestions;

                if (charSequence != null) {
                    suggestions = new ArrayList<>();
                    for (DestinationSuggestion fruit: tempItems) {
                        if (fruit.getText().toLowerCase()
                                .contains(charSequence.toString().toLowerCase())) {
                            suggestions.add(fruit);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }
            @Override
            protected void publishResults (CharSequence charSequence, FilterResults filterResults) {
                ArrayList<DestinationSuggestion> tempValues = (ArrayList<DestinationSuggestion>) filterResults.values;
                if (filterResults != null && filterResults.count > 0) {
                    clear();
                    for (DestinationSuggestion fruitObj : tempValues) {
                        add(fruitObj);
                    }
                    notifyDataSetChanged();
                } else {
                    clear();
                    notifyDataSetChanged();
                }
            }
        };

}
