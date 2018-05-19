package com.by.taxi.lovetaxi.overlay;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;

public class BusLineOverlay {
	private BusLineItem mBusLineItem;
	private AMap mAMap;
	private ArrayList<Marker> mBusStationMarks = new ArrayList<Marker>();
	private Polyline mBusLinePolyline;
	private List<BusStationItem> mBusStations;
	private Bitmap startBit, endBit, busBit;
	private AssetManager am;
	private Context mContext;

	public BusLineOverlay(Context context, AMap amap, BusLineItem busLineItem) {
		mContext = context;
		mBusLineItem = busLineItem;
		this.mAMap = amap;
		mBusStations = mBusLineItem.getBusStations();
		am = mContext.getResources().getAssets();
	}

	public void addToMap() {
		List<LatLonPoint> pointList = mBusLineItem.getDirectionsCoordinates();
		List<LatLng> listPolyline = AMapServicesUtil.convertArrList(pointList);
		mBusLinePolyline = mAMap.addPolyline(new PolylineOptions()
				.addAll(listPolyline).color(getBusColor())
				.width(getBuslineWidth()));
		if (mBusStations.size() < 1) {
			return;
		}
		for (int i = 1; i < mBusStations.size() - 1; i++) {
			Marker marker = mAMap.addMarker(getMarkerOptions(i));
			mBusStationMarks.add(marker);
		}
		Marker markerStart = mAMap.addMarker(getMarkerOptions(0));
		mBusStationMarks.add(markerStart);
		Marker markerEnd = mAMap
				.addMarker(getMarkerOptions(mBusStations.size() - 1));
		mBusStationMarks.add(markerEnd);

	}

	public void removeFromMap() {
		if (mBusLinePolyline != null) {
			mBusLinePolyline.remove();
		}
		for (Marker mark : mBusStationMarks) {
			mark.remove();
		}
		destroyBit();
	}

	private void destroyBit() {
		if (startBit != null) {
			startBit.recycle();
			startBit = null;
		}
		if (endBit != null) {
			endBit.recycle();
			endBit = null;
		}
		if (busBit != null) {
			busBit.recycle();
			busBit = null;
		}
	}

	public void zoomToSpan() {
		if (mAMap == null)
			return;
		List<LatLonPoint> coordin = mBusLineItem.getDirectionsCoordinates();
		if (coordin != null && coordin.size() > 0) {
			LatLngBounds bounds = getLatLngBounds(coordin);
			mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 5));
		}
	}

	private LatLngBounds getLatLngBounds(List<LatLonPoint> coordin) {
		LatLngBounds.Builder b = LatLngBounds.builder();
		for (int i = 0; i < coordin.size(); i++) {
			b.include(new LatLng(coordin.get(i).getLatitude(), coordin.get(i)
					.getLongitude()));
		}
		return b.build();
	}

	private MarkerOptions getMarkerOptions(int index) {
		MarkerOptions options = new MarkerOptions()
				.position(
						new LatLng(mBusStations.get(index).getLatLonPoint()
								.getLatitude(), mBusStations.get(index)
								.getLatLonPoint().getLongitude()))
				.title(getTitle(index)).snippet(getSnippet(index));
		if (index == 0) {
			options.icon(getStartBitmapDescriptor());
		} else if (index == mBusStations.size() - 1) {
			options.icon(getEndBitmapDescriptor());
		} else {
			options.anchor(0.5f, 0.5f);
			options.icon(getBusBitmapDescriptor());
		}
		return options;
	}

	protected BitmapDescriptor getStartBitmapDescriptor() {
		return getBitDes(startBit, "amap_start.png");
	}

	protected BitmapDescriptor getEndBitmapDescriptor() {
		return getBitDes(endBit, "amap_end.png");
	}

	protected BitmapDescriptor getBusBitmapDescriptor() {
		return getBitDes(endBit, "amap_bus.png");
	}

	protected String getTitle(int index) {
		return mBusStations.get(index).getBusStationName();

	}

	protected String getSnippet(int index) {
		return "";
	}

	public int getBusStationIndex(Marker marker) {
		for (int i = 0; i < mBusStationMarks.size(); i++) {
			if (mBusStationMarks.get(i).equals(marker)) {
				return i;
			}
		}
		return -1;
	}

	public BusStationItem getBusStationItem(int index) {
		if (index < 0 || index >= mBusStations.size()) {
			return null;
		}
		return mBusStations.get(index);
	}

	protected int getBusColor() {
		return Color.parseColor("#537edc");
	}

	protected float getBuslineWidth() {
		return 5;
	}

	private BitmapDescriptor getBitDes(Bitmap bitmap, String fileName) {
		InputStream stream;
		try {
			stream = am.open(fileName);
			bitmap = BitmapFactory.decodeStream(stream);
			bitmap = AMapServicesUtil.zoomBitmap(bitmap, 5);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
		return BitmapDescriptorFactory.fromBitmap(bitmap);
	}
}
