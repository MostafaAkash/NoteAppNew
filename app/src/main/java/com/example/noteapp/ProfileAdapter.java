package com.example.noteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileAdapter extends ListAdapter<Profile,ProfileAdapter.ProfileViewHolder> {
    //private Context context;
   // private List<Profile> profileList;
    private OnitemClickListener listener;

    public ProfileAdapter() {
        //this.context = context;
        //this.profileList = profileList;
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Profile> DIFF_CALLBACK = new DiffUtil.ItemCallback<Profile>() {
        @Override
        public boolean areItemsTheSame(@NonNull Profile oldItem, @NonNull Profile newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Profile oldItem, @NonNull Profile newItem) {
            return (oldItem.getPriority()==newItem.getPriority()) &&
                    (oldItem.getAge()==newItem.getAge())&&
                    (oldItem.getRoll().equals(newItem.getRoll()))&&
                    (oldItem.getName().equals(newItem.getName()));
        }
    };
    /*
    void setProfile(List<Profile> profileList)
    {
        this.profileList = profileList;
        notifyDataSetChanged();
    }*/

    public Profile getProfileatPosition(int position)
    {
        return getItem(position);
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.demolist,parent,false);
        ProfileViewHolder holder = new ProfileViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        Profile profile = getItem(position);
        holder.textViewId.setText(String.valueOf(profile.getId()));
        holder.textViewAge.setText(String.valueOf(profile.getAge()));
        holder.textViewPriority.setText(String.valueOf(profile.getPriority()));
        holder.textViewName.setText(profile.getName());
        holder.textViewRoll.setText(profile.getRoll());
        holder.imageView.setImageResource(R.drawable.imageicon);


    }

  /*
    @Override
    public int getItemCount() {
        return profileList.size();
    }*/


    class ProfileViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textViewName,textViewId,textViewRoll,textViewPriority,textViewAge;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageIdDemoList);
            textViewId = itemView.findViewById(R.id.ownIdListDemo);
            textViewName = itemView.findViewById(R.id.nameIdListDemo);
            textViewRoll = itemView.findViewById(R.id.rollIdListDemo);
            textViewPriority = itemView.findViewById(R.id.priorityIdListDemo);
            textViewAge= itemView.findViewById(R.id.ageIdListDemo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null && position!=RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }

    }

    public interface OnitemClickListener
    {
        void onItemClick(Profile profile);
    }
    public void setOnItemClickListener(OnitemClickListener listener)
    {
        this.listener = listener;
    }
}
