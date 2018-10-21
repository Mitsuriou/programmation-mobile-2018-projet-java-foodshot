package ca.qc.cgmatane.informatique.foodshot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import ca.qc.cgmatane.informatique.foodshot.modele.ModelePublication;
import ca.qc.cgmatane.informatique.foodshot.serveur.AjoutAimeAPI;
import ca.qc.cgmatane.informatique.foodshot.serveur.SupprimerAimeAPI;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends ListAdapter<ModelePublication, RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageProfil;
        CircleImageView imageCoeur;

        TextView titre;
        TextView nomUtilisateur;
        TextView nbrCoeur;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.image_name);
            image = itemView.findViewById(R.id.imageView);
            nomUtilisateur = itemView.findViewById(R.id.user_name);
            parentLayout = itemView.findViewById(R.id.layout_list_item);
            imageProfil = itemView.findViewById(R.id.profile_image);
            imageCoeur = itemView.findViewById(R.id.coeur);
            nbrCoeur = itemView.findViewById(R.id.nb_coeur);
        }
    }

    private ArrayList<ModelePublication> listePublications;
    private int idUtilisateur;
    // private String coeur = "/res/mipmap/ic_launcher/ic_launcher.png";
    private Context contexte;
    private ImageView image;

    public RecyclerViewAdapter(int idUtilisateur, Context contexte, ArrayList<ModelePublication> listePublications) {
        super(DIFF_CALLBACK);

        this.idUtilisateur = idUtilisateur;

        this.contexte = contexte;
        this.listePublications = new ArrayList<>();
        this.listePublications.addAll(listePublications);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vue = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(vue);
        holder.setIsRecyclable(true);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        GestureDetector.SimpleOnGestureListener gestureListener = new GestureListener(this.contexte);
        final GestureDetector detecteurGestes = new GestureDetector(contexte, gestureListener);
        final GestureDetector detecteurAppuiLong = new GestureDetector(contexte, new GestureLongClick());

        holder.parentLayout.setClickable(true);
        holder.parentLayout.setFocusable(true);

        holder.nomUtilisateur.setClickable(true);
        holder.nomUtilisateur.setFocusable(true);

        holder.imageProfil.setClickable(true);
        holder.imageProfil.setFocusable(true);

        holder.imageCoeur.setClickable(true);
        holder.imageCoeur.setFocusable(true);

        image.setClickable(true);
        image.setFocusable(true);

        holder.parentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detecteurGestes.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.nomUtilisateur.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detecteurGestes.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.imageProfil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detecteurGestes.onTouchEvent(motionEvent);
                return false;
            }
        });

        holder.imageCoeur.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detecteurGestes.onTouchEvent(motionEvent);
                return false;
            }
        });

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detecteurGestes.onTouchEvent(motionEvent);
                detecteurAppuiLong.onTouchEvent(motionEvent);
                return false;
            }
        });

        Glide.with(contexte)
                .asBitmap()
                .load(listePublications.get(position).getURLImage())
                .apply(new RequestOptions().fitCenter().override(2000, 500)) // 400,400
                .into(image);

        Glide.with(contexte)
                .asBitmap()
                .load(listePublications.get(position).getURLProfil())
                .apply(new RequestOptions().fitCenter().override(400, 400)). // 400,400
                into(holder.imageProfil);

        Glide.with(contexte)
                .asBitmap()
                .load(R.drawable.ic_launcher_background)
                .apply(new RequestOptions().fitCenter().override(60, 60)).
                into(holder.imageCoeur);

        holder.titre.setText(listePublications.get(position).getTitre());
        holder.nbrCoeur.setText("" + listePublications.get(position).getNbrLike());
        holder.nomUtilisateur.setText(listePublications.get(position).getNomUtilisateur());

        holder.imageCoeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listePublications.get(position).isJaime()){
                    SupprimerAimeAPI supprimerAimeAPI = new SupprimerAimeAPI(idUtilisateur, listePublications.get(position).getId());
                    try {
                        supprimerAimeAPI.execute().get();
                        listePublications.get(position).setJaime(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    AjoutAimeAPI ajoutAimeAPI = new AjoutAimeAPI(idUtilisateur, listePublications.get(position).getId());
                    try {
                        ajoutAimeAPI.execute().get();
                        listePublications.get(position).setJaime(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static final DiffUtil.ItemCallback<ModelePublication> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ModelePublication>() {

                // les deux font la même chose, car une publication n'est pas modifiable :
                @Override
                public boolean areItemsTheSame(ModelePublication ancienObjet, ModelePublication nouvelObjet) {
                    return ancienObjet.getId() == nouvelObjet.getId();
                }

                @Override
                public boolean areContentsTheSame(ModelePublication ancienObjet, ModelePublication nouvelObjet) {
                    return (ancienObjet.getId() == nouvelObjet.getId());
                }

            };

    @Override
    public int getItemCount() {
        return listePublications.size();
    }

    public ArrayList<ModelePublication> getListePublications() {
        return this.listePublications;
    }

    public void ajouterPublication(ModelePublication publication) {
        this.listePublications.add(publication);
    }

}

