package io.locative.app.persistent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import io.locative.app.R;
import io.locative.app.model.Geofences;

public enum Storage {

    // TODO try to centralize storage access here

    INSTANCE;

    public Geofences.Geofence insertOrUpdateFence(Geofences.Geofence fence, Context context) {
        final String QUERY = GeofenceProvider.Geofence.KEY_CUSTOMID + " = ?";
        final String[] PARAMETERS = new String[]{fence.subtitle};
        Geofences.Geofence returnFence = fence;
        final Uri URL = Uri.parse("content://" + context.getString(R.string.authority) + "/geofences");
        ContentResolver resolver = context.getContentResolver();
        Cursor existingCursor = resolver.query(URL, null, QUERY, PARAMETERS, null);
        try {
            if (existingCursor != null && existingCursor.getCount() > 0) {
                resolver.update(URL, makeContentValuesForGeofence(fence), QUERY, PARAMETERS);
            } else {
                Uri result = resolver.insert(URL, makeContentValuesForGeofence(fence));
                returnFence = fence.setId(result.getLastPathSegment());
            }
        }finally {
            if (existingCursor != null) {
                existingCursor.close();
            }
        }

        return returnFence;
    }

    @NonNull
    private ContentValues makeContentValuesForGeofence(Geofences.Geofence fence) {
        ContentValues values = new ContentValues();
        values.put(GeofenceProvider.Geofence.KEY_NAME, fence.title);
        values.put(GeofenceProvider.Geofence.KEY_RADIUS, fence.radiusMeters);
        values.put(GeofenceProvider.Geofence.KEY_CUSTOMID, fence.subtitle);
        values.put(GeofenceProvider.Geofence.KEY_ENTER_METHOD, (int) fence.importValues.get(GeofenceProvider.Geofence.KEY_ENTER_METHOD));
        values.put(GeofenceProvider.Geofence.KEY_ENTER_URL, (String) fence.importValues.get(GeofenceProvider.Geofence.KEY_ENTER_URL));
        values.put(GeofenceProvider.Geofence.KEY_TRIGGER, fence.triggers);
        values.put(GeofenceProvider.Geofence.KEY_EXIT_METHOD, (int) fence.importValues.get(GeofenceProvider.Geofence.KEY_EXIT_METHOD));
        values.put(GeofenceProvider.Geofence.KEY_EXIT_URL, (String) fence.importValues.get(GeofenceProvider.Geofence.KEY_EXIT_URL));
        values.put(GeofenceProvider.Geofence.KEY_HTTP_AUTH, (int) fence.importValues.get(GeofenceProvider.Geofence.KEY_HTTP_AUTH));
        values.put(GeofenceProvider.Geofence.KEY_HTTP_USERNAME, (String) fence.importValues.get(GeofenceProvider.Geofence.KEY_HTTP_USERNAME));
        values.put(GeofenceProvider.Geofence.KEY_HTTP_PASSWORD, (String) fence.importValues.get(GeofenceProvider.Geofence.KEY_HTTP_PASSWORD));
        values.put(GeofenceProvider.Geofence.KEY_LATITUDE, fence.latitude);
        values.put(GeofenceProvider.Geofence.KEY_LONGITUDE, fence.longitude);
        return values;
    }
}
