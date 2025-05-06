package com.sahar.snkrsa.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahar.snkrsa.R;
import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.ItemCart;
import com.sahar.snkrsa.model.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private Cart cart;

    public CartAdapter(Context context, Cart cart) {
        this.context = context;
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        if (cart != null && cart.getItemCarts() != null && position < cart.getItemCarts().size()) {
            ItemCart item_cart = cart.getItemCarts().get(position);
            holder.bind(item_cart);
        }
    }

    @Override
    public int getItemCount() {
        return (cart != null && cart.getItemCarts() != null) ? cart.getItemCarts().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name, product_price, product_amount, product_size;
        ImageButton ibPlus, ibMinus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_amount = itemView.findViewById(R.id.product_amount);
            product_size = itemView.findViewById(R.id.product_size);
            ibPlus = itemView.findViewById(R.id.ibPlus);
            ibMinus = itemView.findViewById(R.id.ibMinus);
        }

        public void bind(final ItemCart item_cart) {
            Product product = item_cart.getProduct();
            int amount = item_cart.getAmount();

            product_image.setImageBitmap(ImageUtil.convertFrom64base(product.getImageName()));
            product_name.setText(product.getName());
            product_price.setText(product.getPrice() + "₪");
            product_amount.setText(String.valueOf(amount));
            product_size.setText(product.getSize());

            ibPlus.setOnClickListener(v -> {
                item_cart.setAmount(item_cart.getAmount() + 1);
                product_amount.setText(String.valueOf(item_cart.getAmount()));
            });

            ibMinus.setOnClickListener(v -> {
                if (item_cart.getAmount() > 0) {
                    item_cart.setAmount(item_cart.getAmount() - 1);
                    product_amount.setText(String.valueOf(item_cart.getAmount()));
                }
            });
        }
    }
}
