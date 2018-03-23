package com.mycompany.myinventory;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hakbak.adapter.GridAdapter;
import com.hakbak.javafiles.HomeItem;

public class MainActivity extends ActionBarActivity {
	private GridView gridView;
	private GridAdapter adapter;
	private List<HomeItem> homeItemsList;
	private Toolbar mToolbar;
	public InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setHomeItems();

		// ** Admob Code */
		AdView adView = (AdView) this.findViewById(R.id.adView);
		AdRequest request = new AdRequest.Builder().build();
		adView.loadAd(request);
		interstitial = new InterstitialAd(getBaseContext());
		interstitial.setAdUnitId(getResources().getString(
				R.string.interstitial_id));
		interstitial.loadAd(request);

		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new GridAdapter(this, homeItemsList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				takeAction(position);
			}
		});
	}

	private void setHomeItems() {
		homeItemsList = new ArrayList<HomeItem>();
		homeItemsList.add(new HomeItem("Add", R.drawable.ic_add));
		homeItemsList.add(new HomeItem("Edit", R.drawable.ic_edit));
		homeItemsList.add(new HomeItem("View", R.drawable.ic_view));
		homeItemsList.add(new HomeItem("Report", R.drawable.ic_reports));
		homeItemsList.add(new HomeItem("Setting", R.drawable.ic_settings));
		homeItemsList.add(new HomeItem("Search", R.drawable.ic_search));
	}

	private void takeAction(int position) {
		switch (position) {
		case 0:
			startActivity(new Intent(MainActivity.this,
					AddProductActivity.class));
			break;
		case 1:
			Intent editItemIntent = new Intent(MainActivity.this,
					ItemListActivity.class);
			editItemIntent.setAction("EDIT");
			startActivity(editItemIntent);
			break;
		case 2:
			Intent viewItemIntent = new Intent(MainActivity.this,
					ItemListActivity.class);
			viewItemIntent.setAction("VIEW");
			startActivity(viewItemIntent);
			break;
		case 3:
			startActivity(new Intent(MainActivity.this,
					CategoryReportActivity.class));
			break;
		case 4:
			startActivity(new Intent(MainActivity.this, SettingActivity.class));
			break;
		case 5:
			startActivity(new Intent(MainActivity.this,
					SearchItemActivity.class));
			break;
		default:
			break;
		}
	}
	@Override
	public void onBackPressed() {
		if (interstitial.isLoaded()) {
			interstitial.show();
		}
		super.onBackPressed();
	}
}
