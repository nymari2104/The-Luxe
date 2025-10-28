package com.example.theluxe.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.theluxe.R;
import com.example.theluxe.model.Order;
import android.content.Intent;
import com.example.theluxe.view.activities.OrderDetailActivity;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        holder.textViewOrderId.setText("Order #" + order.getOrderId().substring(0, 8));
        holder.textViewOrderDate.setText("Date: " + sdf.format(order.getOrderDate()));
        holder.textViewOrderStatus.setText("Status: " + order.getStatus());
        holder.textViewOrderTotal.setText(String.format("Total: %,.0f₫", order.getTotalAmount()));
        
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
            intent.putExtra("ORDER_ID", order.getOrderId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId, textViewOrderDate, textViewOrderStatus, textViewOrderTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewOrderStatus = itemView.findViewById(R.id.textViewOrderStatus);
            textViewOrderTotal = itemView.findViewById(R.id.textViewOrderTotal);
        }
    }
}
