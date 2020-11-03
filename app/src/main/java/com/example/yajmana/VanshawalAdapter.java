package com.example.yajmana;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class VanshawalAdapter extends RecyclerView.Adapter<VanshawalAdapter.VanshawalViewHolder> {
    //Context mCtx;
    private List<Vanshawal> vanshawalList;
    private VanshawalViewHolder vanshawalViewHolder;
    private Context ctx;
    private Fragment fragment;

    public VanshawalAdapter(Context context, List<Vanshawal> vanshawalList) {
        ctx=context;
        this.vanshawalList = vanshawalList;
        Log.e("vanshawalList","Adapter List:"+ new Gson().toJson(vanshawalList));
    }

    @NonNull
    @Override
    public VanshawalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        vanshawalViewHolder = new VanshawalViewHolder(view);
        return vanshawalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VanshawalViewHolder holder, int position) {
        holder.vanshawal_no.setText(vanshawalList.get(position).getVanshawal_no());
        holder.full_name.setText( vanshawalList.get(position).getLate_fullname());
        holder.city.setText(vanshawalList.get(position).getCity());
        holder.taluka.setText(vanshawalList.get(position).getTaluka());
        holder.district.setText(vanshawalList.get(position).getDistrict());
        holder.caste.setText(vanshawalList.get(position).getCaste());
        holder.death_anniversary.setText(vanshawalList.get(position).getDeath_anniversary());
        holder.mobile_no.setText(vanshawalList.get(position).getMobile());
        String gender = vanshawalList.get(position).getGender();
        //Female image
        if(gender.equals(ctx.getResources().getString(R.string.female_string))){
            holder.image.setBackgroundResource(R.drawable.person_female);
        }

        holder.btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_feedback(vanshawalList.get(position).getVanshawal_no(), vanshawalList.get(position).getYajman());
            }
        });

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_detail_fragment(vanshawalList.get(position).getVanshawal_no());
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_edit_fragment(vanshawalList.get(position).getVanshawal_no());
            }
        });
    }

    private void open_edit_fragment(String vanshawal_no) {
        fragment = new EditFragment(vanshawal_no);
        NavigateActivity navigateActivity = (NavigateActivity) this.ctx;
        navigateActivity.loadFragment(fragment, navigateActivity.getString(R.string.yajmana_detail_info));
    }

    private void open_detail_fragment(String vanshawalNo) {
        fragment = new DetailFragment(vanshawalNo);
        NavigateActivity navigateActivity = (NavigateActivity) this.ctx;
        navigateActivity.loadFragment(fragment, navigateActivity.getString(R.string.yajmana_detail_info));
        Log.d("Detail fragment","Detail fragment loaded.");
    }

    private void go_to_feedback(String vanshawalNo, String yajman){
        fragment = new AddFeedbackFragment(vanshawalNo, yajman);
        NavigateActivity navigateActivity = ( NavigateActivity ) this.ctx;
        navigateActivity.loadFragment(fragment, navigateActivity.getString(R.string.new_feedback));
    }

    @Override
    public int getItemCount() {
        return vanshawalList.size();
    }

    class VanshawalViewHolder extends RecyclerView.ViewHolder {
        private TextView full_name, city, taluka, district, caste, death_anniversary, mobile_no, vanshawal_no;
        private ImageView image;
        private Button btnFeedback, btnDetail, btnEdit;
        public VanshawalViewHolder(View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.full_name);
            image = itemView.findViewById(R.id.imageView);
            city = itemView.findViewById(R.id.city);
            taluka = itemView.findViewById(R.id.taluka);
            district = itemView.findViewById(R.id.district);
            caste = itemView.findViewById(R.id.caste);
            death_anniversary = itemView.findViewById(R.id.death_anniversary);
            mobile_no = itemView.findViewById(R.id.relation);
            vanshawal_no = itemView.findViewById(R.id.vanshawal_no);
            btnFeedback = itemView.findViewById(R.id.btnFeedback);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
