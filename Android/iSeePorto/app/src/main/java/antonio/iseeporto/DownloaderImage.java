package antonio.iseeporto;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Antonio on 12-09-2015.
 */
public class DownloaderImage {

    public void downloadImage(ImageView imagePlace, final View view, String url)
    {
        //define a imagem do utilizador
        DownloadImageTask downloadImageTask = new DownloadImageTask(imagePlace){
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

        downloadImageTask.execute(url);
    }
}
