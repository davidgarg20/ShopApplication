package com.example.shopapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopapplication.R;
import com.example.shopapplication.TemporaryDatabase;


public class Cart extends AppCompatActivity {

    TemporaryDatabase tb;
    Button proceed;
    Cursor cursor;
    LinearLayout layout;

    TextView[] itemname,unit , amount,qty;
    ImageView[] delete;
    ImageView image;

    String[] ITEMNAME ,UNIT,QTY,ITEMID;
    Integer[] AMOUNT ,PRICE;
    byte[]  bytep;
    TextView Total,Totalitem;
    Integer TOTAL=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        proceed = findViewById(R.id.cart_order);
        layout = findViewById(R.id.cart_itemlayout);
        Total = findViewById(R.id.cart_footer_price);
        Totalitem = findViewById(R.id.cart_footer_quantity);

        tb = new TemporaryDatabase(this);

        cursor = tb.gettemporder();
        itemname = new TextView[1000];
        unit = new TextView[1000];
        amount = new TextView[1000];
        qty = new TextView[1000];
        delete = new ImageView[1000];

        ITEMID = new String[1000];
        ITEMNAME = new String[1000];
        UNIT = new String[1000];
        AMOUNT = new Integer[1000];
        PRICE = new Integer[1000];
        QTY = new String[1000];




        int tt= cursor.getCount();
        Integer ii = tt;

        cursor.moveToFirst();
        for(int i = 0 ; i<tt; i++)
        {  ITEMID[i] = cursor.getString(0);
            ITEMNAME[i] = cursor.getString(1);
           QTY[i] = cursor.getString(2);
           PRICE[i] = cursor.getInt(4);
           UNIT[i]=cursor.getString(5);
           AMOUNT[i]=cursor.getInt(6);
           bytep = cursor.getBlob(7);



           Log.d("cartitemnaem",bytep.toString());
            cursor.moveToNext();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.list_item_cart_product,null);
            layout.addView(rowView,layout.getChildCount());
            itemname[i]=rowView.findViewById(R.id.cart1_product_name);
           qty[i]=rowView.findViewById(R.id.cart1_product_quantity);
            amount[i]=rowView.findViewById(R.id.cart1_product_amount);
            delete[i]=rowView.findViewById(R.id.cart_product_delete);
            image=rowView.findViewById(R.id.cart1_product_image);
            final int k =i;

            delete[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tb.deleteData(ITEMID[k]);

                    TOTAL=TOTAL-AMOUNT[k];
                    Total.setText("Rs."+TOTAL.toString());

                }
            });
            itemname[i].setText(ITEMNAME[i]);
            qty[i].setText(QTY[i].toString());
            amount[i].setText("Rs."+AMOUNT[i].toString());
          image.setImageBitmap(BitmapFactory.decodeByteArray(bytep, 0, bytep.length));

            TOTAL=TOTAL+AMOUNT[i];
        }


        Total.setText("Rs."+TOTAL.toString());
        Totalitem.setText(ii.toString()+" items");



        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Cart.this,CartFinal.class);
                startActivity(i);
            }
        });

    }
}
