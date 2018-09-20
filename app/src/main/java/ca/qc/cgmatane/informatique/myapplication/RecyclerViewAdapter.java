package ca.qc.cgmatane.informatique.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{ //TYPE

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> imageNames = new ArrayList<>();
    private ArrayList<String> userNames = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter( Context context,ArrayList<String> images,ArrayList<String> imageNames,ArrayList<String> userNames) {

        this.context = context;
        this.images = images;
        this.imageNames = imageNames;
        this.userNames=userNames;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder: called");

        Glide.with(context)
                .asBitmap()
                .load(images.get(position))
                .apply(new RequestOptions().fitCenter()).
                        into(holder.image);

        holder.image_name.setText(imageNames.get(position));

        holder.user_name.setText(userNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: "+userNames.get(position));

                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView image_name,user_name;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_name = itemView.findViewById(R.id.image_name);
            image =  itemView.findViewById(R.id.imageView);
            user_name = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}