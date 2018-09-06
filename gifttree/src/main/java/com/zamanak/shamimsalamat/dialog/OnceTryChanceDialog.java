package com.zamanak.shamimsalamat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import com.zamanak.shamimsalamat.interfaces.OnceTryChanceListener;
import com.zamanak.shamimsalamat.R;

/**
 * Created by a.Raghibdoust on 11/5/2017.
 */

public class OnceTryChanceDialog extends Dialog implements View.OnClickListener {

    AppCompatButton btnOk;

    //Define listener
    private OnceTryChanceListener listener;

    //Set Listener
    public void setListener(OnceTryChanceListener listener) {
        this.listener = listener;
    }

    public OnceTryChanceDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(1);
        View view = View.inflate(getContext(), R.layout.dialog_once_try_chance, null);
        setContentView(view);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = -1;
        getWindow().setAttributes(params);
        //ButterKnife.bind(this, view);
        setCancelable(false);

        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==btnOk.getId()){
            listener.onSuccess();
        }

    }
}
