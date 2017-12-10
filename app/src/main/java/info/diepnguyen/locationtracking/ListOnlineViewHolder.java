package info.diepnguyen.locationtracking;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 11/19/17.
 */

public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtEmail;
    ItemClickListener itemClickListener;

    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txtEmail = (TextView) itemView.findViewById(R.id.txt_email);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}
