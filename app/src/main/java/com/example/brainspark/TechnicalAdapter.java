package com.example.brainspark;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TechnicalAdapter extends RecyclerView.Adapter<TechnicalAdapter.ViewHolder> {

    List<TechnicalModel> list;

    public TechnicalAdapter(List<TechnicalModel> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion, tvAnswer;
        Button btnShowAnswer;

        public ViewHolder(View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            btnShowAnswer = itemView.findViewById(R.id.btnShowAnswer);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.technical_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TechnicalModel q = list.get(position);

        holder.tvQuestion.setText((position + 1) + ". " + q.getQuestion());
        holder.tvAnswer.setText(q.getAnswer());

        holder.tvAnswer.setVisibility(View.GONE);
        holder.btnShowAnswer.setText("Show Answer");

        holder.btnShowAnswer.setOnClickListener(v -> {
            if (holder.tvAnswer.getVisibility() == View.GONE) {
                holder.tvAnswer.setVisibility(View.VISIBLE);
                holder.btnShowAnswer.setText("Hide Answer");
            } else {
                holder.tvAnswer.setVisibility(View.GONE);
                holder.btnShowAnswer.setText("Show Answer");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}