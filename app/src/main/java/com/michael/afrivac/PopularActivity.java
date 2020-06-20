package com.michael.afrivac;
import androidx.appcompat.app.*;
import android.os.*;
import androidx.appcompat.widget.*;
import java.util.*;
import android.graphics.*;
import android.widget.*;
import com.michael.afrivac.PopularAdapter.*;
import com.michael.afrivac.DiscoverAdapter.*;
import android.view.*;

public class PopularActivity extends AppCompatActivity
{
	
	private ArrayList<PopularAdapter.Item> popularDests;

	private ArrayList<DiscoverAdapter.Item> discoverDests;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popular);
		popularDests = new ArrayList<PopularAdapter.Item>();
		popularDests.add(new PopularAdapter.Item("Nigeria","Lagos",R.drawable.cairo,4.5,4059590));
		popularDests.add(new PopularAdapter.Item("Nigeria","Lagos",R.drawable.cairo,4.5,4059590));
		popularDests.add(new PopularAdapter.Item("Egypt","Cairo",R.drawable.camel,4.1,4040));
		discoverDests = new ArrayList<DiscoverAdapter.Item>();
		discoverDests.add(new DiscoverAdapter.Item("Nigeria","Lagos",R.drawable.cairo));
		discoverDests.add(new DiscoverAdapter.Item("Nigeria","Lagos",R.drawable.cairo));
		RecyclerView discoverRecycler = findViewById(R.id.activity_popular_discoverRecycleview);
		RecyclerView popularRecycler = findViewById(R.id.activity_popular_popular_RecycleView);
		discoverRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
		popularRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		discoverRecycler.setItemAnimator(new DefaultItemAnimator());
		popularRecycler.setItemAnimator(new DefaultItemAnimator());
		popularRecycler.setAdapter(new PopularAdapter(this,popularDests,null));
		discoverRecycler.setAdapter(new DiscoverAdapter(this,discoverDests,null));
	}
	
}