package wakeb.tech.drb.imagepiker.features;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import wakeb.tech.drb.imagepiker.features.IpCons;
import wakeb.tech.drb.imagepiker.features.cameraonly.ImagePickerCameraOnly;
import wakeb.tech.drb.imagepiker.helper.ConfigUtils;
import wakeb.tech.drb.imagepiker.helper.IpLogger;
import wakeb.tech.drb.imagepiker.helper.LocaleManager;
import wakeb.tech.drb.imagepiker.model.Image;

public abstract class ImagePicker {

    private ImagePickerConfig config;

    public abstract void start();

    public abstract void start(int requestCode);

    public static class ImagePickerWithActivity extends ImagePicker {

        private Activity activity;

        public ImagePickerWithActivity(Activity activity) {
            this.activity = activity;
            init(activity);
        }

        @Override
        public void start(int requestCode) {
            activity.startActivityForResult(getIntent(activity), requestCode);
        }

        @Override
        public void start() {
            activity.startActivityForResult(getIntent(activity), wakeb.tech.drb.imagepiker.features.IpCons.RC_IMAGE_PICKER);
        }
    }

    public static class ImagePickerWithFragment extends ImagePicker {

        private Fragment fragment;

        public ImagePickerWithFragment(Fragment fragment) {
            this.fragment = fragment;
            init(fragment.requireContext());
        }

        @Override
        public void start(int requestCode) {
            fragment.startActivityForResult(getIntent(fragment.getActivity()), requestCode);
        }

        @Override
        public void start() {
            fragment.startActivityForResult(getIntent(fragment.getActivity()), wakeb.tech.drb.imagepiker.features.IpCons.RC_IMAGE_PICKER);
        }
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public void init(Context context) {
        config = ImagePickerConfigFactory.createDefault(context);
    }

    public static ImagePickerWithActivity create(Activity activity) {
        return new ImagePickerWithActivity(activity);
    }

    public static ImagePickerWithFragment create(Fragment fragment) {
        return new ImagePickerWithFragment(fragment);
    }

    public static ImagePickerCameraOnly cameraOnly() {
        return new ImagePickerCameraOnly();
    }

    /* --------------------------------------------------- */
    /* > Builder */
    /* --------------------------------------------------- */

    public ImagePicker single() {
        config.setMode(wakeb.tech.drb.imagepiker.features.IpCons.MODE_SINGLE);
        return this;
    }

    public ImagePicker multi() {
        config.setMode(wakeb.tech.drb.imagepiker.features.IpCons.MODE_MULTIPLE);
        return this;
    }

    public ImagePicker returnMode(@NonNull ReturnMode returnMode) {
        config.setReturnMode(returnMode);
        return this;
    }

    public ImagePicker limit(int count) {
        config.setLimit(count);
        return this;
    }

    public ImagePicker showCamera(boolean show) {
        config.setShowCamera(show);
        return this;
    }

    public ImagePicker toolbarArrowColor(@ColorInt int color) {
        config.setArrowColor(color);
        return this;
    }

    public ImagePicker toolbarFolderTitle(String title) {
        config.setFolderTitle(title);
        return this;
    }

    public ImagePicker toolbarImageTitle(String title) {
        config.setImageTitle(title);
        return this;
    }

    public ImagePicker toolbarDoneButtonText(String text) {
        config.setDoneButtonText(text);
        return this;
    }

    public ImagePicker origin(ArrayList<Image> images) {
        config.setSelectedImages(images);
        return this;
    }

    public ImagePicker exclude(ArrayList<Image> images) {
        config.setExcludedImages(images);
        return this;
    }

    public ImagePicker excludeFiles(ArrayList<File> files) {
        config.setExcludedImageFiles(files);
        return this;
    }

    public ImagePicker folderMode(boolean folderMode) {
        config.setFolderMode(folderMode);
        return this;
    }


    public ImagePicker includeVideo(boolean includeVideo) {
        config.setIncludeVideo(includeVideo);
        return this;
    }

    public ImagePicker onlyVideo(boolean onlyVideo) {
        config.setOnlyVideo(onlyVideo);
        return this;
    }

    public ImagePicker includeAnimation(boolean includeAnimation) {
        config.setIncludeAnimation(includeAnimation);
        return this;
    }

    public ImagePicker imageDirectory(String directory) {
        config.setImageDirectory(directory);
        return this;
    }

    public ImagePicker imageFullDirectory(String fullPath) {
        config.setImageFullDirectory(fullPath);
        return this;
    }

    public ImagePicker theme(@StyleRes int theme) {
        config.setTheme(theme);
        return this;
    }

    public ImagePicker enableLog(boolean isEnable) {
        IpLogger.getInstance().setEnable(isEnable);
        return this;
    }

    public ImagePicker language(String language) {
        config.setLanguage(language);
        return this;
    }

    public ImagePickerConfig getConfig() {
        LocaleManager.setLanguage(config.getLanguage());
        return ConfigUtils.checkConfig(config);
    }

    public Intent getIntent(Context context) {
        ImagePickerConfig config = getConfig();
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerConfig.class.getSimpleName(), config);
        return intent;
    }

    /* --------------------------------------------------- */
    /* > Helper */
    /* --------------------------------------------------- */

    public static boolean shouldHandle(int requestCode, int resultCode, Intent data) {
        return resultCode == Activity.RESULT_OK
                && requestCode == wakeb.tech.drb.imagepiker.features.IpCons.RC_IMAGE_PICKER
                && data != null;
    }

    public static List<Image> getImages(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getParcelableArrayListExtra(IpCons.EXTRA_SELECTED_IMAGES);
    }

    public static Image getFirstImageOrNull(Intent intent) {
        List<Image> images = getImages(intent);
        if (images == null || images.isEmpty()) {
            return null;
        }
        return images.get(0);
    }
}
