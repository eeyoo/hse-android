package com.huazi.project.hse.adapter;

import com.huazi.project.hse.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;
	
	public ImageAdapter(Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageResource(mThumbIds[position]);
		return imageView;
	}

	private Integer[] mThumbIds =  {
		R.drawable.sample_0, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_3,
		R.drawable.sample_4, R.drawable.sample_5,
		R.drawable.sample_6, R.drawable.sample_7,
		R.drawable.sample_4, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_3,
		R.drawable.sample_5, R.drawable.sample_0,
		R.drawable.sample_6, R.drawable.sample_2,
		R.drawable.sample_7, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_6,
		R.drawable.sample_3, R.drawable.sample_4,
		R.drawable.sample_6, R.drawable.sample_0,
		R.drawable.sample_0, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_3,
		R.drawable.sample_4, R.drawable.sample_5,
		R.drawable.sample_6, R.drawable.sample_7,
		R.drawable.sample_4, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_3,
		R.drawable.sample_5, R.drawable.sample_0,
		R.drawable.sample_6, R.drawable.sample_2,
		R.drawable.sample_7, R.drawable.sample_1,
		R.drawable.sample_2, R.drawable.sample_6,
		R.drawable.sample_3, R.drawable.sample_4
	};
}
