package de.smartdev.lastorderapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SecondFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;
    private OnFragmentInteractionListener mListener;
    FirebaseRecyclerAdapter<AnzeigeEntry, AnzeigeEntryViewHolder> mAdapter;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(int param1) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView=inflater.inflate(R.layout.fragment_second, container, false);
        configureView(inflatedView);
        return inflatedView;
    }

    private void configureView(View inflatedView) {
        final Firebase firebase = new Firebase("https://lob.firebaseio.com/");
        firebase.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot anzSnapshot : dataSnapshot.getChildren()) {
                    AnzeigeEntry anzeigeEntry = anzSnapshot.getValue(AnzeigeEntry.class);
                    Log.i("FirebaseUI/4", "Successful read " + anzeigeEntry.getAnzeigenText());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.w("FirebaseUI/4", "The read failed: " + firebaseError.getMessage());
            }
        });
        RecyclerView recyclerView = (RecyclerView) inflatedView.findViewById(R.id.card_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FirebaseRecyclerAdapter<AnzeigeEntry, AnzeigeEntryViewHolder>(AnzeigeEntry.class, R.layout.fragment_list_recyclerview_card, AnzeigeEntryViewHolder.class, firebase) {
            @Override
            public void populateViewHolder(AnzeigeEntryViewHolder entryViewHolder, AnzeigeEntry anzeigeEntry) {
                /*entryViewHolder.anzeigenText.setText(anzeigeEntry.getAnzeigenText());
                entryViewHolder.anzeigenAdresse.setText(anzeigeEntry.getAdresse());
                entryViewHolder.anzeigenTags.setText(anzeigeEntry.getTags());
                entryViewHolder.anzeigeID.setText(anzeigeEntry.getId());
                entryViewHolder.anzeigeUserID.setText(anzeigeEntry.getUserAnzeigeId());
                entryViewHolder.anzeigeLifeTime.setText(anzeigeEntry.getLifetime());
                entryViewHolder.anzeigenRestaurantName.setText(anzeigeEntry.getRestaurantName());*/
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
