package com.example.manoloreche.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.manoloreche.myapplication.sql.DatabaseContract;

/**
 * Created by ManoloReche on 03/12/2017.
 */

public class UsersCusrsorAdapter extends CursorAdapter {
    public UsersCusrsorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_user,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView nameText = (TextView) view.findViewById(R.id.tv_name);
        final ImageView avatarImage = (ImageView) view.findViewById(R.id.iv_avatar);
        String name = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.NAME)) + " " + cursor.getString(cursor.getColumnIndex(DatabaseContract.User.SURENAME));
        String avatarUri = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.PROFILE_PICTURE));

        nameText.setText(name);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatarUri))
                .asBitmap()
                .error(R.mipmap.ic_profile_image)
                .centerCrop()
                .into(new BitmapImageViewTarget(avatarImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        avatarImage.setImageDrawable(drawable);
                    }
                });

    }
}
