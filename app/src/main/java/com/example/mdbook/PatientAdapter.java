package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Creates an activity for the user to add patients
 * Displays a list of all the patients already added
 *
 * @see com.example.mdbook.Patient
 * @see
 *
 * @author Raj Kapadia
 * @author Thomas Chan
 *
 * @version 0.0.1
 */

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientAdapterHolder> {

    private ArrayList<String> patientIDs;
    private PatientAdapter.OnItemClickListener mListener;
    private UserManager userManager;

    /**
     * Sets the global problems array list
     * @param patientIDs passed in by ListProblemActivities
     */
    public PatientAdapter(ArrayList<String> patientIDs){
        this.patientIDs = patientIDs;
    }

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
    public void setOnItemClickListener(PatientAdapter.OnItemClickListener listener){
        this. mListener = listener;
    }


    /**
     * Creates the Adapter to hold the card view in the recycler view
     */
    public static class PatientAdapterHolder extends RecyclerView.ViewHolder{

        public TextView patientName;
        public TextView patientProblems;


        /**
         * Adapter to click the card view when the recycler view is clicked
         * @param itemView The card view
         * @param listener OnItemClickListener from recycler view
         */
        public PatientAdapterHolder(@NonNull View itemView, final PatientAdapter.OnItemClickListener listener) {
            super(itemView);
            this.patientName = itemView.findViewById(R.id.patientName);
            this.patientProblems =itemView.findViewById(R.id.patientProblems);
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
     * Creates the cards in the recycler view
     * @param viewGroup ??
     * @param i index
     * @return
     */
    @NonNull
    @Override
    public PatientAdapter.PatientAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_item,viewGroup,false);
        PatientAdapter.PatientAdapterHolder  viewholder = new PatientAdapter.PatientAdapterHolder (v,mListener);
        return viewholder;
    }


    /**
     * Sets the Text in the card
     * @param patientAdapterHolder Adapter that connects from card to recycler view
     * @param i position of objects
     */
    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.PatientAdapterHolder patientAdapterHolder, int i) {
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        Caregiver caregiver = (Caregiver) UserController.getController().getUser();
        String currentitem = patientIDs.get(i);
        patientAdapterHolder.patientName.setText("Patient: " + currentitem);
        try {
            Patient patient = (Patient) userManager.fetchUser(currentitem);
            patientAdapterHolder.patientProblems.setText("# of problems: " + patient.getProblems().size());
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets item count
     * @return size of problems
     */
    @Override
    public int getItemCount() {
       return patientIDs.size();
    }
}
