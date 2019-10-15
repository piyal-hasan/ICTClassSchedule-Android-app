package com.example.piyal.classrutine.batabase.viewModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyal.classrutine.R;
import com.example.piyal.classrutine.batabase.model.RutineModel;

import java.util.List;

/**
 * Created by piyal on 5/9/2018.
 */
public class RutineView extends RecyclerView.Adapter<RutineView.RecyclerViewHolde> {
    Context context;
    List<RutineModel> rutineModels;
    private OnItemClickListner listner;
    public void setOnItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }
    public interface OnItemClickListner{

        void OnItemClick(int position);
        void OnItemEdit(int position);
        void OnItemdelete(int position);

    }
    public RutineView(Context context, List<RutineModel> rutineModels) {
        this.context = context;
        this.rutineModels = rutineModels;
    }

    @Override
    public RecyclerViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.rutine_view,null);
        return new RecyclerViewHolde(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolde holder, int position) {
        String view="Course Code: "+rutineModels.get(position).getCoursecode()+"\n"+"Course Teacher: "+rutineModels.get(position).getCourse_teacher()+"\n"+"" +
                "Class Time: "+rutineModels.get(position).getClass_time();
           holder.cousecodetx.setText(view);
//        holder.courseteachertx.setText(rutineModels.get(position).getCourse_teacher());
//        holder.classtime.setText(rutineModels.get(position).getClass_time());
    }
    @Override
    public int getItemCount() {
        return rutineModels.size();
    }

    class RecyclerViewHolde extends RecyclerView.ViewHolder {
        TextView cousecodetx,courseteachertx,classtime;
        public RecyclerViewHolde(View itemView) {
            super(itemView);
            cousecodetx=itemView.findViewById(R.id.coursecode);
//            classtime=itemView.findViewById(R.id.classtime);
//            courseteachertx=itemView.findViewById(R.id.courseteacher);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listner!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listner.OnItemEdit(position);
                        }
                    }
                }
            });
        }
    }
}
