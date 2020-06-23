package com.michael.afrivac.ui.findHotel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michael.afrivac.R;
import com.michael.afrivac.model.HotelSuggestions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class AutoCompleteHotelAdapter extends ArrayAdapter<HotelSuggestions> {
    private Context context;
    private int resource;
    private List<HotelSuggestions> mainItems, tempItems;


    public AutoCompleteHotelAdapter(@NonNull Context context, int resource, @NonNull List<HotelSuggestions> mainItems) {
        super(context, resource, mainItems);
        this.mainItems= mainItems;
        this.context = context;
        this.resource = resource;
        tempItems = new ArrayList<>(mainItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resource, parent, false);
            }

            HotelSuggestions fruit = getItem(position);
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
    public HotelSuggestions getItem(int position) {
        return mainItems.get(position);
    }
    @Override
    public int getCount() {
        return mainItems == null ? 0 : mainItems.size();
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
            HotelSuggestions fruit = (HotelSuggestions) resultValue;
            return fruit.getText();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<HotelSuggestions> suggestions;

            if (charSequence != null) {
                suggestions = new ArrayList<>();
                for (HotelSuggestions fruit: tempItems) {
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
            ArrayList<HotelSuggestions> tempValues = (ArrayList<HotelSuggestions>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (HotelSuggestions fruitObj : tempValues) {
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
