package com.sahar.snkrsa.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahar.snkrsa.R;
import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.Item;
import com.sahar.snkrsa.model.ItemCart;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.utils.ImageUtil;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return ;
//    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_cart_item, parent, false);
        }

        ImageView product_image;
        TextView product_name, product_price, product_amount, product_size;


        for (ItemCart item_cart : cart.getItemCarts()) {
            Product product = item_cart.getProduct();
            int amount = item_cart.getAmount();

            product_image = convertView.findViewById(R.id.product_image);
            product_name = convertView.findViewById(R.id.product_name);
            product_price = convertView.findViewById(R.id.product_price);
            product_amount = convertView.findViewById(R.id.product_amount);
            product_size = convertView.findViewById(R.id.product_size);

            product_image.setImageBitmap(ImageUtil.convertFrom64base(product.getImageName()));
            product_name.setText(product.getName());
            product_price.setText(product.getPrice() + "");
            product_amount.setText(amount);
            product_size.setText(product.getSize());
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}