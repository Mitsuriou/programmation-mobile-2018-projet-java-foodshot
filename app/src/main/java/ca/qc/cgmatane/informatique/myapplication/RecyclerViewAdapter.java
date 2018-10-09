package ca.qc.cgmatane.informatique.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{//TYPE

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> imageNames = new ArrayList<>();
    private ArrayList<String> userNames = new ArrayList<>();
    private ArrayList<String> lPP = new ArrayList<>();
    private ArrayList<String> nbCoeur = new ArrayList<>();
    private String coeur;
    private Context context;
    private int height;
    private int width;
    private ImageView image;
    private BitmapUtils bitmapUtils;


    private String imageBitmap;
    private String imageProfil;

    public RecyclerViewAdapter( Context context,ArrayList<String> URL,ArrayList<String> imageNames,ArrayList<String> userNames, ArrayList<String> lPP,String coeur,ArrayList<String> nbCoeur) {

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
        Log.d(TAG,"onBindViewHolder: called");

        try{
            image.setImageBitmap(StringToBitMap(""+new BitmapUtils(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    imageBitmap=result;
                }
            }).execute(images.get(position)).get()));
        } catch (Exception e){

        }

        try{
            holder.photo_profil.setImageBitmap(StringToBitMap(""+new BitmapUtils(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    imageProfil=result;
                }
            }).execute(lPP.get(position)).get()));
        } catch (Exception e){

        }

        try{
            holder.photo_profil.setImageBitmap(StringToBitMap(""+new BitmapUtils(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    imageProfil=result;
                }
            }).execute(lPP.get(position)).get()));
        } catch (Exception e){

        }

        Glide.with(context)
                .asBitmap()
                .load(R.drawable.ic_launcher_background)
                .apply(new RequestOptions().fitCenter().override(60, 60)).
                into(holder.coeur);

        holder.image_name.setText(imageNames.get(position));
        holder.nbCoeur.setText(nbCoeur.get(position));
        holder.user_name.setText(userNames.get(position));

        holder.photo_profil.setOnClickListener(new View.OnClickListener() {
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
        });
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

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}