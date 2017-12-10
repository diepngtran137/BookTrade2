package info.diepnguyen.locationtracking;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 11/28/17.
 */

public class BookListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView bookName,authorName, userEmail;
    public ImageView bookImg;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BookListViewHolder(View itemView) {
        super(itemView);
        bookName = (TextView)itemView.findViewById(R.id.book_name);
        authorName = (TextView)itemView.findViewById(R.id.book_author);
        userEmail = (TextView)itemView.findViewById(R.id.user_email);
        bookImg = (ImageView)itemView.findViewById(R.id.book_image);
        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());

    }
}
