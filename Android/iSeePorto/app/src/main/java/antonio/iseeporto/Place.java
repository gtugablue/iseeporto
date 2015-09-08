package antonio.iseeporto;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Antonio on 08-09-2015.
 */
public class Place extends android.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createVisitFrag();
        return inflater.inflate(R.layout.place, container, false);
    }

    public void createVisitFrag()
    {
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        Visitar v = new Visitar();
        transaction.add(R.id.avaliacao, v, "Visitar");
        transaction.commit();
    }

    public void visitarLocal()
    {
        EvaluateThumbs avaliacao = new EvaluateThumbs();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, avaliacao, "Avaliar");
        transaction.commit();
    }

    public void retirarVisita()
    {
        Visitar v = new Visitar();
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.avaliacao, v, "Visitar");
        transaction.commit();
    }
}
