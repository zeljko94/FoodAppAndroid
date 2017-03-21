package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.sve_mo.fpmoz.zeljko.foodapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NarudzbeFragment extends Fragment {


    public NarudzbeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_narudzbe, container, false);
    }

}
