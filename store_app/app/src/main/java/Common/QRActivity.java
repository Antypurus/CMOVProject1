package Common;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public abstract class QRActivity extends AppCompatActivity implements QRReadResultHandler{

    protected QR QRCodeReader = new QR();

    @Override
    public abstract void Handler(String data);

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        QRCodeReader.ResultHandler(requestCode, resultCode, data, this);
    }
}
