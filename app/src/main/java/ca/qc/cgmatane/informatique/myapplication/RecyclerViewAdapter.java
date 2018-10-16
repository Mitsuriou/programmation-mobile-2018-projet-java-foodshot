package ca.qc.cgmatane.informatique.myapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends ListAdapter<Publication, RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView photo_profil, coeur;

        TextView image_titre, user_name, nbCoeur;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_titre = itemView.findViewById(R.id.image_name);
            image = itemView.findViewById(R.id.imageView);
            user_name = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            photo_profil = itemView.findViewById(R.id.profile_image);
            coeur = itemView.findViewById(R.id.coeur);
            nbCoeur = itemView.findViewById(R.id.nb_coeur);
        }
    }

    private ArrayList<Publication> listePublication;
    private int id_utilisateur=14;
    private String coeur = "/res/mipmap/ic_launcher/ic_launcher.png";
    private Context context;
    private ImageView image;
    private GestureDetector gestureDetector;
    private GestureDetectorCompat gestureDetectorCompat;

    public RecyclerViewAdapter(Context context, ArrayList<Publication> p_listePublication) {

        super(DIFF_CALLBACK);

        this.context = context;
        this.listePublication = new ArrayList<>();
        this.listePublication.addAll(p_listePublication);

        // Ajouter l'id_utilisateur via les préférences partagés.


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.setIsRecyclable(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener();
        final GestureDetector gd = new GestureDetector(context, gestureListener);
        final GestureDetector gestureLong = new GestureDetector(context, new GestureLongClick());

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


        RequestOptions ro = new RequestOptions();
        ro.placeholder(R.drawable.ic_launcher_background);

        Glide.with(context)
                .applyDefaultRequestOptions(ro)
                .asBitmap()
                .load(listePublication.get(position).getURLimage())
                .apply(new RequestOptions().fitCenter().override(2000, 500)) // 400,400
                .into(image);

        Glide.with(context)
                .applyDefaultRequestOptions(ro)
                .asBitmap()
                .load(listePublication.get(position).getURLprofil())
                .apply(new RequestOptions().fitCenter().override(400, 400)). // 400,400
                into(holder.photo_profil);

        Glide.with(context)
                .applyDefaultRequestOptions(ro)
                .asBitmap()
                .load(R.drawable.ic_launcher_background)
                .apply(new RequestOptions().fitCenter().override(60, 60)).
                into(holder.coeur);

        holder.image_titre.setText(listePublication.get(position).getDescImage());
        holder.nbCoeur.setText("" + listePublication.get(position).getNbLike());
        holder.user_name.setText(listePublication.get(position).getUsername());

        final Activity activity = (Activity) context;

//        holder.photo_profil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ThemeColors.switchThemeColor(activity,2);
//
//            }
//        });
//
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listePublication.get(position).getJ_aime()){
                    EnleveAimeAPI enleveAimeAPI = new EnleveAimeAPI(id_utilisateur, listePublication.get(position).getId());
                    try {
                        enleveAimeAPI.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    AjoutAimeAPI ajoutAimeAPI = new AjoutAimeAPI(id_utilisateur, listePublication.get(position).getId());
                    try {
                        ajoutAimeAPI.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
//
//        holder.coeur.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AjoutAimeAPI ajoutAimeAPI = new AjoutAimeAPI(id_utilisateur, listePublication.get(position).getId());
//            }
//        });
//
//        holder.user_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG,"onClick: pseudo on: "+userNames.get(position));
//
//                //Toast.makeText(context,imageNames.get(position),Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public static final DiffUtil.ItemCallback<Publication> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Publication>() {

                @Override
                public boolean areItemsTheSame(Publication oldItem, Publication newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Publication oldItem, Publication newItem) {
                    return (oldItem.getId() == newItem.getId());
                }
                // les deux font la même chose, car une publication n'est pas modifiable.
            };

    @Override
    public int getItemCount() {
        return listePublication.size();
    }

    public ArrayList<Publication> getListePublication() {
        return this.listePublication;
    }

    public void ajouterPublication(Publication publication) {
        this.listePublication.add(publication);
    }

}

