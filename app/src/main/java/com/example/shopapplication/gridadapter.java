package com.example.shopapplication;

        import android.content.Context;
        import android.content.Intent;
        import androidx.recyclerview.widget.RecyclerView;

        import android.graphics.Bitmap;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.ImageRequest;
        import com.android.volley.toolbox.NetworkImageView;
        import com.android.volley.toolbox.Volley;

        import java.io.ByteArrayOutputStream;
        import java.util.ArrayList;

        import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public  class gridadapter extends RecyclerView.Adapter<gridadapter.MyViewHolder> implements Filterable {
    ArrayList<datamodel> data;
    ArrayList<datamodel> mFilteredList;
    Context context;
    int width;
    LinearLayout gridlayout;
    String URL1;


    public gridadapter(Context context, ArrayList gdata , int width) {
        this.context = context;
        this.data = gdata;
        mFilteredList = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(mFilteredList.get(position).getIntname().toString());
        holder.price.setText("Rs,"+data.get(position).getPrice());
        holder.discountprice.setText("Rs."+data.get(position).getDistprice());
        URL1 = mFilteredList.get(position).getImgeresource().toString();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        // Initialize a new ImageRequest
        ImageRequest imageRequest = new ImageRequest( URL1
                , // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        holder.image.setImageBitmap(response);
                        Log.d("productimageproduct", response.toString());

                        // Save this downloaded bitmap to internal storage

                    }
                },
                0, // Image width
                0, // Image height
                ImageView.ScaleType.FIT_START, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();
                        Log.d("errorimage",error.toString());

                    }
                }
        );

        requestQueue.add(imageRequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
               int pos = holder.getAdapterPosition();


                Intent intent = new Intent(context, product.class);// put image data in Intent
                intent.putExtra("ItemName",data.get(pos).getIntname());
                intent.putExtra("Price",(int)data.get(pos).getPrice());
                intent.putExtra("DiscountPrice",(int)data.get(pos).getDistprice());
                intent.putExtra("ItemId",(int)data.get(pos).getItemid());
                intent.putExtra("ImageUrl" , data.get(pos).getImgeresource());

                context.startActivity(intent); // start Intent
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public  Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = data;
                } else {

                    ArrayList<datamodel> filteredList = new ArrayList<>();

                    for (datamodel item : data) {

                        if (item.getIntname().toLowerCase().contains(charString) ) {

                            filteredList.add(item);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

               mFilteredList = (ArrayList<datamodel>) filterResults.values;
                Log.d("filte","search executed");
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name, price, discountprice;
        ImageView image;
        NetworkImageView nv;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            gridlayout = itemView.findViewById(R.id.grid_item_linear_layout);
            ViewGroup.LayoutParams params = gridlayout.getLayoutParams();
// Changes the height and width to the specified *pixels*
            params.width = 720;
            gridlayout.setLayoutParams(params);
            name = (TextView) itemView.findViewById(R.id.product_item_name);
            image = itemView.findViewById(R.id.product_item_image);
            price = (TextView) itemView.findViewById(R.id.product_item_price);
            discountprice = (TextView) itemView.findViewById(R.id.product_item_discount_price);
        }
    }



}

