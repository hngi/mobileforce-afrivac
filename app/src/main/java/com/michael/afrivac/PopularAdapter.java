package com.michael.afrivac;
import android.view.*;
import java.util.*;
import androidx.appcompat.widget.*;
import android.widget.*;
import android.content.*;
import android.widget.LinearLayout.*;
import android.content.res.*;
public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder>
{
	List<Item> arrayList;

	private PopularActivity ctx;
	public static class Item{
		String title;
		String subtitle;
		int imageResource;
		double rating;
		int ratingCount;
		public Item(String title,
			String subtitle,
			int imageResource,
			double rating,
			int ratingCount){
	this.title = title;
	this.subtitle=subtitle;
	this.imageResource=imageResource;
	this.rating=rating;
	this.ratingCount=ratingCount;
	
			}
		
	}
	public static abstract class OnItemClickListener{
		public abstract void onItemClick(PopularAdapter adapter, ViewHolder viewholder, int position);
	}
	int selected=-1;
	View selectedView;

	private OnItemClickListener itemClickListener;

	public PopularAdapter(PopularActivity ctx, List<Item> items, OnItemClickListener b)
	{
		this.arrayList = items;
		this.ctx = ctx;
		this.itemClickListener = b;
	}
	@Override
	public  ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(ctx).inflate(R.layout.activity_popular_card_small, viewGroup, false);
		view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
		view.setClipToOutline(true);
		return new ViewHolder(view);
		
		}
	@Override
	public  void onBindViewHolder(final ViewHolder viewHolder, int position)
	{
		viewHolder.title.setText(arrayList.get(position).title);
		viewHolder.subtitle.setText(arrayList.get(position).subtitle);
		viewHolder.image.setImageResource(arrayList.get(position).imageResource);
		if(position == 0){
			viewHolder.itemView.setElevation(10);
			RecyclerView.LayoutParams b = (RecyclerView.LayoutParams) viewHolder.itemView.getLayoutParams();
			b.rightMargin=0;
			b.leftMargin=0;
		}
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
		TextView title;
		TextView subtitle;
		ImageView image;
		public ViewHolder(View itemView)
		{
			super(itemView);
			title = (TextView) itemView.findViewById(R.id.activity_popular_cardTitle);
			subtitle = (TextView) itemView.findViewById(R.id.activity_popular_cardSubtitle);
			image = itemView.findViewById(R.id.activity_popular_cardImage);
		}
		public void setOnClickListener(final OnItemClickListener itemClickListener)
		{
			this.itemView.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View p1)
					{
						itemClickListener.onItemClick(PopularAdapter.this, ViewHolder.this, getPosition());
					}


				}
			);
		}
	}
}