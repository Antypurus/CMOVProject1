package Common.QR;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

public class QR extends Application {

    private final static int white = 0xFFFFFFFF;
    private final static int black = 0xFF000000;
    private QRReadResultHandler handler = null;

    /**
     *
     * @param data
     * @return
     * @throws WriterException
     */
    public static Bitmap GenerateQRCode(String data) throws WriterException {
        final int width = 500;
        final int height = 500;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? black : white;
            }
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmp;
    }

    public void StartQRScanner(Activity activity, QRReadResultHandler handler) {
        this.handler = handler;
        new IntentIntegrator(activity).initiateScan();
    }

    public void ResultHandler(int requestCode, int resultCode, Intent data, Activity activity)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                this.handler.Handler(result.getContents());
            }
        }
    }

}
