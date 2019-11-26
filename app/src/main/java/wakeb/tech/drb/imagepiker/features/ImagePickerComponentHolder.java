package wakeb.tech.drb.imagepiker.features;

import android.content.Context;

import wakeb.tech.drb.imagepiker.features.fileloader.DefaultImageFileLoader;
import wakeb.tech.drb.imagepiker.features.fileloader.ImageFileLoader;
import wakeb.tech.drb.imagepiker.features.imageloader.DefaultImageLoader;
import wakeb.tech.drb.imagepiker.features.imageloader.ImageLoader;

public class ImagePickerComponentHolder {

    private static ImagePickerComponentHolder INSTANCE;

    public static ImagePickerComponentHolder getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImagePickerComponentHolder();
        }
        return INSTANCE;
    }

    /* --------------------------------------------------- */
    /* > End of Singleton */
    /* --------------------------------------------------- */

    private Context context;
    private ImageLoader imageLoader;
    private ImageFileLoader imageFileLoader;

    public void init(Context context) {
        this.context = context;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            return getDefaultImageLoader();
        }
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public ImageFileLoader getImageFileLoader() {
        if (imageFileLoader == null) {
            return getDefaultImageFileLoader();
        }
        return imageFileLoader;
    }

    public void setImageFileLoader(ImageFileLoader imageFileLoader) {
        this.imageFileLoader = imageFileLoader;
    }

    /* --------------------------------------------------- */
    /* > Default */
    /* --------------------------------------------------- */

    private ImageLoader defaultImageLoader;
    private ImageFileLoader defaultImageFileLoader;

    public ImageFileLoader getDefaultImageFileLoader() {
        if (defaultImageFileLoader == null) {
            defaultImageFileLoader = new DefaultImageFileLoader(context);
        }
        return defaultImageFileLoader;
    }

    public ImageLoader getDefaultImageLoader() {
        if (defaultImageLoader == null) {
            defaultImageLoader = new DefaultImageLoader();
        }
        return defaultImageLoader;
    }
}
