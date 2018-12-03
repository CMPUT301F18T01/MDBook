package com.example.mdbook;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Custom adapter to add card views into a recycler view. Used to connect data from the
 * problems to the card view.
 *
 * @author Thomas Chan
 * @see ListProblemActivity
 * @version 1.0.0
 */



public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemAdapterHolder> {
    private ArrayList<Problem> problems;
    private OnItemClickListener mListener;

    /**
     * Listens for clicks then notifies OnitemsClicks with the position so when the recycler view is
     * clicked, click the card view too
     */
    public interface OnItemClickListener{
        void OnItemClick(int Position);
    }

    /**
     * Sets global OnItemClickListener
     * @param listener Is passed a OnItemClickLister to set the global listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this. mListener = listener;
    }


    /**
     * Creates the Adapter to hold the card view in the recycler view
     */
    public static class ProblemAdapterHolder extends RecyclerView.ViewHolder{

        public TextView text;
        public TextView text2;
        public TextView text3;


        /**
         * Adapter to click the card view when the recycler view is clicked
         * @param itemView The card view
         * @param listener OnItemClickListener from recycler view
         */
        public ProblemAdapterHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.text = itemView.findViewById(R.id.mtext);
            this.text2 =itemView.findViewById(R.id.mtext2);
            this.text3 =itemView.findViewById(R.id.mtext3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    /**
     * Sets the global problems array list
     * @param problems passed in by ListProblemActivities
     */
    public ProblemAdapter(ArrayList<Problem> problems){
        this.problems = problems;
    }


    /**
     * Creates the cards in the recycler view
     * @param viewGroup ??
     * @param i index
     * @return
     */
    @NonNull
    @Override
    public ProblemAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.problem_item,viewGroup,false);
        ProblemAdapterHolder viewholder = new ProblemAdapterHolder(v,mListener);
        return viewholder;
    }

    /**
     * Sets the Text in the card
     * @param problemAdapterHolder Adapter that connects from card to recycler view
     * @param i position of objects
     */
    @Override
    public void onBindViewHolder(@NonNull ProblemAdapterHolder problemAdapterHolder, int i) {
        Problem currentitem = problems.get(i);

        problemAdapterHolder.text.setText(currentitem.getTitle());
        problemAdapterHolder.text2.setText(currentitem.getDescription());
        problemAdapterHolder.text3.setText(currentitem.getDate().toString());
    }

    /**
     * Gets item count
     * @return size of problems
     */
    @Override
    public int getItemCount() {
        return problems.size();
    }
}
