package uder.uder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uder.uder.HelperClasses.Order;
import uder.uder.HelperClasses.Product;

import static uder.uder.R.drawable.cart;


/**
 * Created by cazza223 on 4/15/2017.
 */

public class OrderAdapter extends ArrayAdapter<Order> {
    Context context;
    private ArrayList<Order> orders;

    private static class ViewHolder{
        TextView orderHeader;
        TextView orderContents;
    }

    public OrderAdapter(Context context, ArrayList<Order> orders){
        super(context, R.layout.order_list_item, orders);
        this.context = context;
        this.orders = orders;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Order order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.order_list_item, parent, false);
        viewHolder.orderHeader = (TextView) convertView.findViewById(R.id.orderHeader);
        viewHolder.orderContents = (TextView) convertView.findViewById(R.id.orderProducts);


        convertView.setTag(viewHolder);


        lastPosition = position;

        viewHolder.orderHeader.setText("OrderID: " + order.getOrder_id() + " \nDelivery Address: \n" + order.getAddress());
        viewHolder.orderContents.setText(order.toString());

        // Return the completed view to render on screen
        return convertView;
    }


}
