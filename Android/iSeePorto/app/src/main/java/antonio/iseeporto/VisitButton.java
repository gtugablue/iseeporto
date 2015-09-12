package antonio.iseeporto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Antonio on 08-09-2015.
 */
public class VisitButton extends Fragment {

    ImageButton visitarB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.visit_place, container, false);

        visitarB = (ImageButton) view.findViewById(R.id.visitarB);
        visitarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Place) getFragmentManager().findFragmentByTag("Place")).visitarLocal();
            }
        });

        return view;
    }
}
