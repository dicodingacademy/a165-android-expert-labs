package com.dicoding.mymaps

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.mymaps.databinding.ActivityMainBinding
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.extension.style.layers.properties.generated.ProjectionName
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor
import com.mapbox.maps.extension.style.projection.generated.projection
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.GesturesPlugin
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.plugin.viewport.viewport


class MainActivity : AppCompatActivity() {

    companion object {
        private const val ICON_ID = "ICON_ID"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var btnNavigation: Button

    //    private lateinit var mapboxMap: MapboxMap
//    private lateinit var symbolManager: SymbolManager
//    private lateinit var locationComponent: LocationComponent
//    private lateinit var mylocation: LatLng
    private lateinit var permissionsManager: PermissionsManager
//    private lateinit var navigationMapRoute: NavigationMapRoute
//    private var currentRoute: DirectionsRoute? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnNavigation = findViewById(R.id.btnNavigation)

        binding.mapView.scalebar.enabled = false
        binding.mapView.mapboxMap.apply {
            loadStyle(style(Style.STANDARD) {
                +projection(ProjectionName.MERCATOR)
            })
        }

        showDicodingSpace()
        showMyLocation()
//        addMarkerOnClick()
//        showNavigation()
    }

    private fun showDicodingSpace() {
        val dicodingspace = Point.fromLngLat(107.6338462, -6.8957643)
        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val pointAnnotationOptions =
            PointAnnotationOptions().withPoint(dicodingspace)
                .withIconImage(BitmapFactory.decodeResource(resources, R.drawable.red_marker))
                .withIconAnchor(IconAnchor.BOTTOM)
                .withIconSize(0.3)
                .withTextField("Dicoding Space")
                .withTextHaloColor("rgba(255, 255, 255, 100)")
                .withTextHaloWidth(5.0)
                .withTextAnchor(TextAnchor.TOP)
                .withIconOffset(listOf(0.0, 1.5))
                .withDraggable(true)
        pointAnnotationManager.create(pointAnnotationOptions)

        val cameraPosition = CameraOptions.Builder()
            .zoom(8.0)
            .center(dicodingspace)
            .build()
        binding.mapView.mapboxMap.setCamera(cameraPosition)
    }

    @SuppressLint("MissingPermission")
    private fun showMyLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            with(binding.mapView) {
                location.locationPuck = createDefault2DPuck(withBearing = true)
                location.enabled = true
                location.puckBearing = PuckBearing.COURSE
                location.puckBearingEnabled = true
                viewport.transitionTo(
                    targetState = viewport.makeFollowPuckViewportState(),
                    transition = viewport.makeImmediateViewportTransition()
                )

                val locationService = LocationServiceFactory.getOrCreate()
                var locationProvider: DeviceLocationProvider? = null

                val request = LocationProviderRequest.Builder()
                    .interval(
                        IntervalSettings.Builder().interval(0L).minimumInterval(0L)
                            .maximumInterval(0L).build()
                    )
                    .displacement(0F)
                    .accuracy(AccuracyLevel.HIGHEST)
                    .build();

                val result = locationService.getDeviceLocationProvider(request)
                if (result.isValue) {
                    locationProvider = result.value!!
                    locationProvider.getLastLocation {
                        it?.let { location ->
                            val cameraPosition = CameraOptions.Builder()
                                .zoom(8.0)
                                .center(Point.fromLngLat(location.longitude, location.latitude))
                                .build()
                            binding.mapView.mapboxMap.setCamera(cameraPosition)
                        }
                    }
                } else {
                    Log.e("", "Failed to get device location provider")
                }
            }


        } else {
            permissionsManager = PermissionsManager(object : PermissionsListener {
                override fun onExplanationNeeded(permissionsToExplain: List<String>) {
                    Toast.makeText(
                        this@MainActivity,
                        "Anda harus mengizinkan location permission untuk menggunakan aplikasi ini",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionResult(granted: Boolean) {
                    if (granted) {
                        showMyLocation()
                    } else {
                        finish()
                    }
                }
            })
            permissionsManager.requestLocationPermissions(this)
        }
    }

//    private fun addMarkerOnClick() {
//        binding.mapView.addTouchables().addOnMapClickListener { point ->
//            symbolManager.deleteAll()
//
//            symbolManager.create(
//                SymbolOptions()
//                    .withLatLng(LatLng(point.latitude, point.longitude))
//                    .withIconImage(ICON_ID)
//                    .withDraggable(true)
//            )
//
//            val destination = Point.fromLngLat(point.longitude, point.latitude)
//            val origin = Point.fromLngLat(mylocation.longitude, mylocation.latitude)
//            requestRoute(origin, destination)
//
//            btnNavigation.visibility = View.VISIBLE
//            true
//        }
//    }
//
//    private fun requestRoute(origin: Point, destination: Point) {
//        navigationMapRoute.updateRouteVisibilityTo(false)
//        NavigationRoute.builder(this)
//            .accessToken(getString(R.string.access_token))
//            .origin(origin)
//            .destination(destination)
//            .build()
//            .getRoute(object : retrofit2.Callback<DirectionsResponse> {
//                override fun onResponse(
//                    call: retrofit2.Call<DirectionsResponse>,
//                    response: retrofit2.Response<DirectionsResponse>
//                ) {
//                    if (response.body() == null) {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "No routes found, make sure you set the right user and access token.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        return
//                    } else if (response.body()?.routes()?.size == 0) {
//                        Toast.makeText(this@MainActivity, "No routes found.", Toast.LENGTH_SHORT)
//                            .show()
//                        return
//                    }
//
//                    currentRoute = response.body()?.routes()?.get(0)
//
//                    navigationMapRoute.addRoute(currentRoute)
//
//                }
//
//                override fun onFailure(call: retrofit2.Call<DirectionsResponse>, t: Throwable) {
//                    Toast.makeText(this@MainActivity, "Error : $t", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    private fun showNavigation() {
//        btnNavigation.setOnClickListener {
//            val simulateRoute = true
//
//            val options = NavigationLauncherOptions.builder()
//                .directionsRoute(currentRoute)
//                .shouldSimulateRoute(simulateRoute)
//                .build()
//
//            NavigationLauncher.startNavigation(this, options)
//        }
//    }
}