package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.ArrayList;


import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<String> images = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();
    private List<String> userNames = new ArrayList<>();
    private List<String> lPP = new ArrayList<>();
    private List<String> nbCoeur = new ArrayList<>();
    private String coeur;
    private Context context;
    private int height;
    private int width;
    private ImageView image;

    public RecyclerViewAdapter(Context context, List<String> URL,
                               List<String> imageNames, List<String> userNames,
                               List<String> lPP, String coeur,
                               List<String> nbCoeur) {

        this.context = context;
        for (String valeur : URL) {
            images.add(valeur);
        }
        this.imageNames = imageNames;
        this.userNames = userNames;
        this.lPP = lPP;
        this.coeur = coeur;
        this.nbCoeur = nbCoeur;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);

        Log.d("width",""+width); // 0...
        height = view.getHeight();
        width = view.getWidth();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener(context);
        final GestureDetector gd = new GestureDetector(context, gestureListener);
        final GestureDetector gestureLong = new GestureDetector(context,new LongClick());

        holder.parentLayout.setClickable(true);
        holder.parentLayout.setFocusable(true);

        holder.user_name.setClickable(true);
        holder.user_name.setFocusable(true);

        holder.photo_profil.setClickable(true);
        holder.photo_profil.setFocusable(true);

        holder.coeur.setClickable(true);
        holder.coeur.setFocusable(true);

        image.setClickable(true);
        image.setFocusable(true);

        holder.parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.user_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.photo_profil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.coeur.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                gestureLong.onTouchEvent(motionEvent);
                return false;
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(images.get(position))
                .apply(new RequestOptions().fitCenter().override(2000, 500)). // 400,400
                into(image);

        Glide.with(context)
                .asBitmap()
                .load(lPP.get(position))
                .apply(new RequestOptions().fitCenter().override(400, 400)). // 400,400
                into(holder.photo_profil);

        Glide.with(context)
                .asBitmap()
                .load(R.drawable.ic_launcher_background)
                .apply(new RequestOptions().fitCenter().override(60, 60)).
                into(holder.coeur);

        holder.image_name.setText(imageNames.get(position));
        holder.nbCoeur.setText(nbCoeur.get(position));
        holder.user_name.setText(userNames.get(position));

/*        holder.photo_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: PP on: "+userNames.get(position));

                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: image on: "+userNames.get(position));

                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        holder.coeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: coeur on: "+userNames.get(position));

                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: pseudo on: "+userNames.get(position));

                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return imageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView photo_profil,coeur;

        TextView image_name,user_name,nbCoeur;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_name = itemView.findViewById(R.id.image_name);
            image =  itemView.findViewById(R.id.imageView);
            user_name = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            photo_profil = itemView.findViewById(R.id.profile_image);
            coeur = itemView.findViewById(R.id.coeur);
            nbCoeur = itemView.findViewById(R.id.nb_coeur);
        }
    }

    private class LongClick extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(context, "LONG TOUCH", Toast.LENGTH_SHORT).show();
        }

    }


}

