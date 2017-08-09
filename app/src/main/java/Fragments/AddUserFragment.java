package Fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mand.myapplication.R;


public class AddUserFragment extends DialogFragment {
    public EditText correo,usuario,password;
    public static AddUserFragment newInstance(){
        AddUserFragment auf = new AddUserFragment();

        return auf;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle b){
        View v = null;
        v = layoutInflater.inflate(R.layout.fragment_add_user, null);
        correo = (EditText) v.findViewById(R.id.emailRegister);
        usuario = (EditText) v.findViewById(R.id.usernameRegister);
        password = (EditText) v.findViewById(R.id.passwordRegister);
        return v;
    }

    public void limpiarInputs(){
        correo.setText("");
        usuario.setText("");
        password.setText("");
    }
}
