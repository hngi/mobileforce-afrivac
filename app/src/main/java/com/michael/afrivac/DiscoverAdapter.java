package com.michael.afrivac;
import android.view.*;
import java.util.*;
import androidx.appcompat.widget.*;
import android.widget.*;
import android.content.*;
public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.ViewHolder>
{
	List<Item> arrayList;

	private PopularActivity ctx;
	public static class Item{
		String country;
		String city;
		int imageResource;
		public Item(String country,
					String city,
					int imageResource){
						this.country=country;
						this.city=city;
						this.imageResource=imageResource;
					}
		
	}
	public static abstract class OnItemClickListener{
		public abstract void onItemClick(DiscoverAdapter adapter, ViewHolder viewholder, int position);
	}
	int selected=-1;
	View selectedView;

	private OnItemClickListener itemClickListener;

	public DiscoverAdapter(PopularActivity ctx, List<Item> items, OnItemClickListener b)
	{
		this.arrayList = items;
		this.ctx = ctx;
		this.itemClickListener = b;
	}
	@Override
	public  ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(ctx).inflate(R.layout.activity_popular_viewpager_fragment, viewGroup, false);
		view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
		view.setClipToOutline(true);
		return new ViewHolder(view);
	}
	@Override
	public  void onBindViewHolder(final ViewHolder viewHolder, int position)
	{
		viewHolder.city.setText(arrayList.get(position).city);
		viewHolder.country.setText(arrayList.get(position).country);
		viewHolder.image.setImageResource(arrayList.get(position).imageResource);
		if(position==selected){
			viewHolder.itemView.setSelected(true);
			selectedView = viewHolder.itemView;
		}
		else viewHolder.itemView.setSelected(false);
		if (itemClickListener != null)
			viewHolder.setOnClickListener(itemClickListener);
	}
	@Override
	public int getItemCount()
	{
		return arrayList.size();
	}
	public class ViewHolder extends RecyclerView.ViewHolder
	{

		TextView city;

		TextView country;
		ImageView image;
		public ViewHolder(View itemView)
		{
			super(itemView);
			city = itemView.findViewById(R.id.activity_popular_viewpager_fragmentCity);
			country = itemView.findViewById(R.id.activity_popular_viewpager_fragmentCountry);
			image = itemView.findViewById(R.id.activity_popular_viewpager_fragmentImage);
		}
		public void setOnClickListener(final OnItemClickListener itemClickListener)
		{
			this.itemView.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						itemClickListener.onItemClick(DiscoverAdapter.this, ViewHolder.this, getPosition());
					}


				}
			);
		}
	}
}