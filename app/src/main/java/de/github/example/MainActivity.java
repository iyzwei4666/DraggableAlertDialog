package de.github.example;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.github.draggabledialog.DraggableAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DraggableAlertDialog dialog = new DraggableAlertDialog(this);
        TextView textView = new TextView(this);
        textView.setText("我的内容");
        textView.setPadding(50 ,50, 50, 50);
        textView.setBackgroundColor(R.color.colorAccent);
        dialog.setView(textView);


        findViewById(R.id.txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

            }
        });

    }
}
