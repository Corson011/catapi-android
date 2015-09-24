package org.otfusion.votecats.service.images.picasso;

import android.content.Context;
import android.media.MediaScannerConnection;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.otfusion.votecats.common.model.Cat;
import org.otfusion.votecats.service.images.StorageImageService;
import org.otfusion.votecats.util.FileUtils;

import java.io.File;

import javax.inject.Inject;

public class StorageImagePicassoServiceImpl implements StorageImageService {

    private final Context _context;

    @Inject
    public StorageImagePicassoServiceImpl(Context context) {
        _context = context;
    }

    @Override
    public void saveImageIntoStorage(Cat cat) {
        Target target = new StorageTarget(cat, _context);
        Picasso.with(_context).load(cat.getImageUrl()).into(target);
    }

    @Override
    public void deleteImageFromStorage(Cat cat) {
        File file = FileUtils.getFile(cat);
        if (file != null) {
            file.delete();
            MediaScannerConnection.scanFile(_context, new String[]{file.getPath()}, null, null);
        }
    }
}
