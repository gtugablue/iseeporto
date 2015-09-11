package antonio.iseeporto;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Antonio on 11-09-2015.
 */
public class ImageDownload {
    private ImageView imageShow;
    private View view;

    public ImageDownload (ImageView temp, View viewTemp)
    {
        imageShow = temp;
        view = viewTemp;
    }

    //define a imagem do utilizador
    final DownloadImageTask downloadImageTask = new DownloadImageTask(imageShow){
        @Override
        protected void onPostExecute(final Bitmap result) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    bmImage.setImageBitmap(result);
                }
            });
        }
    };
}
