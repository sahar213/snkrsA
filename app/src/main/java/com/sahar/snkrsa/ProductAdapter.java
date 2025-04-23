package com.sahar.snkrsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sahar.snkrsa.model.Item;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.utils.ImageUtil;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_product, parent, false);
        }

        Product product = products.get(position);

        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);

        // הצגת המידע
        productName.setText(product.getName());
        productPrice.setText(product.getPrice()+"");


       productImage.setImageBitmap(ImageUtil.convertFrom64base(product.getImageName()));

        // הצגת התמונה מתוך drawable
  //      String imageName = product.getImageName(); // קבל את שם התמונה
   //     int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
     //   if (imageResId != 0) {
   //         productImage.setImageResource(imageResId); // הצגת התמונה
   //     } else {
            // אם לא נמצאה התמונה, הצג תמונה ברירת מחדל
   //         productImage.setImageResource(R.drawable.ic_launcher_background);
     //   }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductPage.class);
          intent.putExtra("product", product);
            context.startActivity(intent);
        });

        return convertView;
    }
}