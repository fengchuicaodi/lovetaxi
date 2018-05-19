package com.by.taxi.lovetaxi.overlay;

import java.util.List;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.Doorway;
import com.amap.api.services.route.RouteBusLineItem;
import com.amap.api.services.route.RouteBusWalkItem;
import com.amap.api.services.route.WalkStep;

public class BusRouteOverlay extends RouteOverlay {

	private BusPath busPath;
	private LatLng latLng;

	public BusRouteOverlay(Context context, AMap amap, BusPath path,
			LatLonPoint start, LatLonPoint end) {
		super(context);
		this.busPath = path;
		startPoint = AMapServicesUtil.convertToLatLng(start);
		endPoint = AMapServicesUtil.convertToLatLng(end);
		mAMap = amap;
	}

	/**
	 * 绘制节点和线�?
	 */
	public void addToMap() {
		List<BusStep> busSteps = busPath.getSteps();
		for (int i = 0; i < busSteps.size(); i++) {
			BusStep busStep = busSteps.get(i);
			if (i < busSteps.size() - 1) {
				BusStep busStep1 = busSteps.get(i + 1);// 取得当前下一个BusStep对象
				// 假如步行和公交之间连接有断开，就把步行最后一个经纬度点和公交第一个经纬度点连接起来，避免断线问题
				if (busStep.getWalk() != null && busStep.getBusLine() != null) {
					LatLng walkLatLng = AMapServicesUtil
							.convertToLatLng(busStep
									.getWalk()
									.getSteps()
									.get(busStep.getWalk().getSteps().size() - 1)
									.getPolyline()
									.get(busStep
											.getWalk()
											.getSteps()
											.get(busStep.getWalk().getSteps()
													.size() - 1).getPolyline()
											.size() - 1));
					LatLonPoint busEntrancePoint = getEntrancePoint(busStep);
					if (busEntrancePoint == null) {
						busEntrancePoint = busStep.getBusLine().getPolyline()
								.get(0);
					}
					LatLng busLatLng = AMapServicesUtil
							.convertToLatLng(busEntrancePoint);
					Polyline walkPolyline = mAMap
							.addPolyline(new PolylineOptions()
									.add(walkLatLng, busLatLng)
									.width(getBuslineWidth())
									.color(getWalkColor()));
					allPolyLines.add(walkPolyline);
				}
				// 假如公交和步行之间连接有断开，就把上�?��公交�?���?��经纬度点和下�?��步行第一个经纬度点连接起来，避免断线问题
				if (busStep.getBusLine() != null && busStep1.getWalk() != null) {
					LatLonPoint busExitPoint = getExitPoint(busStep);
					LatLonPoint busLastPoint = busStep.getBusLine()
							.getPolyline()
							.get(busStep.getBusLine().getPolyline().size() - 1);
					if (busExitPoint == null) {
						busExitPoint = busLastPoint;
					} else {
						LatLng busLastLatLng = AMapServicesUtil
								.convertToLatLng(busLastPoint);
						LatLng busExitLatLng = AMapServicesUtil
								.convertToLatLng(busExitPoint);
						Polyline busLast2busExit = mAMap
								.addPolyline(new PolylineOptions()
										.add(busLastLatLng, busExitLatLng)
										.width(getBuslineWidth())
										.color(getWalkColor()));
						allPolyLines.add(busLast2busExit);

					}
					LatLng endbusLatLng = AMapServicesUtil
							.convertToLatLng(busExitPoint);
					LatLng startwalkLatLng = AMapServicesUtil
							.convertToLatLng(busStep1.getWalk().getSteps()
									.get(0).getPolyline().get(0));
					Polyline addPolyline = mAMap
							.addPolyline(new PolylineOptions()
									.add(endbusLatLng, startwalkLatLng)
									.width(getBuslineWidth())
									.color(getWalkColor()));
					allPolyLines.add(addPolyline);
				}
				// 假如两个公交换乘中间没有步行，就把上�?��公交�?���?��经纬度点和下�?��公交第一个经纬度点连接起来，避免断线问题
				if (busStep.getBusLine() != null && busStep1.getWalk() == null
						&& busStep1.getBusLine() != null) {
					LatLonPoint busExitPoint = getExitPoint(busStep);
					if (busExitPoint == null) {
						busExitPoint = busStep
								.getBusLine()
								.getPolyline()
								.get(busStep.getBusLine().getPolyline().size() - 1);
					}

					LatLng endbusLatLng = AMapServicesUtil
							.convertToLatLng(busExitPoint);
					LatLonPoint busEnterancePoint = getEntrancePoint(busStep1);
					if (busEnterancePoint == null) {
						busEnterancePoint = busStep1.getBusLine().getPolyline()
								.get(0);
					}
					LatLng startbusLatLng = AMapServicesUtil
							.convertToLatLng(busEnterancePoint);
					drawLineArrow(endbusLatLng, startbusLatLng);//
					// 断线用带箭头的直线连�?
				}
				if (busStep.getBusLine() != null && busStep1.getWalk() == null
						&& busStep1.getBusLine() != null) {
					LatLng endbusLatLng = AMapServicesUtil
							.convertToLatLng(busStep
									.getBusLine()
									.getPolyline()
									.get(busStep.getBusLine().getPolyline()
											.size() - 1));
					LatLng startbusLatLng = AMapServicesUtil
							.convertToLatLng(busStep1.getBusLine()
									.getPolyline().get(0));
					if (startbusLatLng.latitude - endbusLatLng.latitude > 0.0001
							|| startbusLatLng.longitude
									- endbusLatLng.longitude > 0.0001) {
						drawLineArrow(endbusLatLng, startbusLatLng);// 断线用带箭头的直线连�?
					}
				}
				// 假如两个步行换乘中间没有公交，就把上�?��步行�?���?��经纬度点和下�?��步行第一个经纬度点连接起来，避免断线问题 More
				if (busStep.getWalk() != null && busStep.getBusLine() != null) {
					LatLonPoint enterPoint = getEntrancePoint(busStep);
					if (enterPoint == null) {
						enterPoint = busStep
								.getWalk()
								.getSteps()
								.get(busStep.getWalk().getSteps().size() - 1)
								.getPolyline()
								.get(busStep
										.getWalk()
										.getSteps()
										.get(busStep.getWalk().getSteps()
												.size() - 1).getPolyline()
										.size() - 1);
					}
					LatLng walkLatLng = AMapServicesUtil
							.convertToLatLng(enterPoint);
					LatLng busLatLng = AMapServicesUtil.convertToLatLng(busStep
							.getBusLine().getPolyline().get(0));
					Polyline morePolyline = mAMap
							.addPolyline(new PolylineOptions()
									.add(walkLatLng, busLatLng).width(3)
									.color(getWalkColor())
									.width(getBuslineWidth()));
					allPolyLines.add(morePolyline);
				}

			}

			if (busStep.getWalk() != null
					&& busStep.getWalk().getSteps().size() > 0) {
				RouteBusWalkItem routeBusWalkItem = busStep.getWalk();
				List<WalkStep> walkSteps = routeBusWalkItem.getSteps();
				for (int j = 0; j < walkSteps.size(); j++) {
					WalkStep walkStep = walkSteps.get(j);
					LatLng latLng = AMapServicesUtil.convertToLatLng(walkStep
							.getPolyline().get(0));
					String road = walkStep.getRoad();// 道路名字
					String instruction = getWalkSnippet(walkSteps);// 步行导航信息
					List<LatLng> listWalkPolyline = AMapServicesUtil
							.convertArrList(walkStep.getPolyline());
					this.latLng = listWalkPolyline
							.get(listWalkPolyline.size() - 1);
					if (j == 0) {
						Marker marker = mAMap.addMarker(new MarkerOptions()
								.position(latLng).title(road)
								.snippet(instruction).anchor(0.5f, 0.5f)
								.icon(getWalkBitmapDescriptor()));
						stationMarkers.add(marker);
					}

					Polyline walkPolyline = mAMap
							.addPolyline(new PolylineOptions()
									.addAll(listWalkPolyline)
									.color(getWalkColor())
									.width(getBuslineWidth()));
					allPolyLines.add(walkPolyline);
					// 假如步行前一段的终点和下�?��的起点有断开，断�?��画直线连接起来，避免断线问题
					if (j < walkSteps.size() - 1) {
						LatLng lastLatLng = listWalkPolyline
								.get(listWalkPolyline.size() - 1);
						LatLng firstlatLatLng = AMapServicesUtil
								.convertToLatLng(walkSteps.get(j + 1)
										.getPolyline().get(0));
						if (!(lastLatLng.equals(firstlatLatLng))) {
							Polyline firstPolyline = mAMap
									.addPolyline(new PolylineOptions()
											.add(lastLatLng, firstlatLatLng)
											.width(getBuslineWidth())
											.color(getWalkColor()));
							allPolyLines.add(firstPolyline);
						}
					}

				}
			} else {
				if (busStep.getBusLine() == null) {
					Polyline commPolyline = mAMap
							.addPolyline(new PolylineOptions()
									.add(this.latLng, endPoint)
									.color(getWalkColor())
									.width(getBuslineWidth()));
					allPolyLines.add(commPolyline);
				}
			}
			if (busStep.getBusLine() != null) {
				RouteBusLineItem routeBusLineItem = busStep.getBusLine();
				List<LatLng> listPolyline = AMapServicesUtil
						.convertArrList(routeBusLineItem.getPolyline());
				BusStationItem startBusStation = routeBusLineItem
						.getDepartureBusStation();
				Polyline polyline = mAMap.addPolyline(new PolylineOptions()
						.addAll(listPolyline).color(getBusColor())
						.width(getBuslineWidth()));
				allPolyLines.add(polyline);
				Marker busStartMarker = mAMap.addMarker(new MarkerOptions()
						.position(
								AMapServicesUtil
										.convertToLatLng(startBusStation
												.getLatLonPoint()))
						.title(routeBusLineItem.getBusLineName())
						.snippet(getBusSnippet(routeBusLineItem))
						.anchor(0.5f, 0.5f).icon(getBusBitmapDescriptor()));
				stationMarkers.add(busStartMarker);
			}
		}
		addStartAndEndMarker();
	}

	private String getWalkSnippet(List<WalkStep> walkSteps) {
		float disNum = 0;
		for (WalkStep step : walkSteps) {
			disNum += step.getDistance();
		}
		return "\u6B65\u884C" + disNum + "\u7C73";
	}

	/**
	 * 绘制带有箭头的直�?
	 */
	public void drawLineArrow(LatLng start, LatLng end) {
		mAMap.addPolyline(new PolylineOptions().add(start, end).width(3)
				.color(getBusColor()).width(getBuslineWidth()));// 绘制直线
	}

	private String getBusSnippet(RouteBusLineItem routeBusLineItem) {
		return "("
				+ routeBusLineItem.getDepartureBusStation().getBusStationName()
				+ "-->"
				+ routeBusLineItem.getArrivalBusStation().getBusStationName()
				+ ") \u7ECF\u8FC7" + (routeBusLineItem.getPassStationNum() + 1) + "\u7AD9";
	}
	protected float getBuslineWidth() {
		return 5;
	}

	private LatLonPoint getExitPoint(BusStep busStep) {
		Doorway doorway = busStep.getExit();
		if (doorway == null) {
			return null;
		}
		return doorway.getLatLonPoint();

	}

	private LatLonPoint getEntrancePoint(BusStep busStep) {
		Doorway doorway = busStep.getEntrance();
		if (doorway == null) {
			return null;
		}
		return doorway.getLatLonPoint();
	}
}
