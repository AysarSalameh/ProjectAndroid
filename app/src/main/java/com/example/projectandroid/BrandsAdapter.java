package com.example.projectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BrandsAdapter extends ArrayAdapter<Brands> {
    private Context context;
    private List<Brands> brandsList;

    public BrandsAdapter(Context context, List<Brands> brandsList) {
        super(context, 0, brandsList);
        this.context = context;
        this.brandsList = brandsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        Brands brand = brandsList.get(position);

        ImageView brandLogo = convertView.findViewById(R.id.brand_logo);
        TextView brandName = convertView.findViewById(R.id.brand_name);

        brandName.setText(brand.getName_brand());
        Glide.with(context).load(brand.getLogo()).into(brandLogo);

        return convertView;
    }
}
