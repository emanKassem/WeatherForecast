package com.golyv.forecast.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.golyv.forecast.R
import com.golyv.forecast.domain.model.CurrentWeatherForecast
import com.golyv.forecast.domain.model.HourlyForecast
import com.golyv.forecast.domain.model.UV
import com.golyv.forecast.domain.model.WeatherForecastModel
import com.golyv.forecast.presentation.components.saveToDisk
import com.golyv.uicomponents.screens.error.model.Error
import com.golyv.forecast.presentation.model.ForecastUiEvent
import com.golyv.forecast.presentation.model.ForecastUiState
import com.golyv.forecast.presentation.model.HomeTab
import com.golyv.uicomponents.loading.LoadingScreen
import com.golyv.uicomponents.permission.WithPermission
import com.golyv.uicomponents.screens.error.ErrorScreen
import com.golyv.uicomponents.snackbar.SnackBar
import com.golyv.uicomponents.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@Composable
fun WeatherScreen(
    weatherForecastViewModel: WeatherForecastViewModel,
    onCameraClicked: () -> Unit
) {
    val context = LocalContext.current
    val hasInternet = weatherForecastViewModel.hasInternet.collectAsStateWithLifecycle()
    val forecastUiState = weatherForecastViewModel.forecastUiState.collectAsStateWithLifecycle()
    val capturedImageUri = weatherForecastViewModel.capturedImageUri.collectAsStateWithLifecycle()
    val imagesHistory = weatherForecastViewModel.imagesHistory.collectAsStateWithLifecycle()
    var permissionDenied by remember {
        mutableStateOf(false)
    }
    if (!hasInternet.value) {
        ErrorScreen(Error.INTERNET_CONNECTION)
    } else {
        WithPermission(
            permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
            rationaleMessage = stringResource(R.string.location_permission_required),
            onCanceled = {
                permissionDenied = true
            }
        ) {
            LaunchedEffect(Unit) {
                getCurrentLocation(context) { location: Location ->
                    weatherForecastViewModel.onUiEvent(
                        ForecastUiEvent.PermissionGranted(
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
            WeatherScreen(
                forecastUiState = forecastUiState.value,
                capturedImageUri = capturedImageUri.value,
                imagesHistory = imagesHistory.value,
                onCameraClicked = onCameraClicked,
                onUiEvent = weatherForecastViewModel::onUiEvent
            )
        }
        if (permissionDenied)
            ErrorScreen(error = Error.PERMISSION_DENIED)
    }
}

@Composable
fun WeatherScreen(
    forecastUiState: ForecastUiState,
    capturedImageUri: String?,
    imagesHistory: List<String>?,
    onUiEvent: (ForecastUiEvent) -> Unit,
    onCameraClicked: () -> Unit
) {
    when (forecastUiState) {
        is ForecastUiState.Failure -> {
            ErrorScreen(Error.fromValue(forecastUiState.message))
        }

        is ForecastUiState.Loading -> LoadingScreen()
        is ForecastUiState.Success -> WeatherSuccessScreen(
            forecastUiState.weatherForecastModel,
            capturedImageUri,
            imagesHistory,
            onUiEvent,
            onCameraClicked
        )
    }
}

@Composable
private fun WeatherSuccessScreen(
    weatherForecastModel: WeatherForecastModel,
    capturedImageUri: String?,
    imagesHistory: List<String>?,
    onUiEvent: (ForecastUiEvent) -> Unit,
    onCameraClicked: () -> Unit
) {

    var selectedTab by remember {
        mutableStateOf(HomeTab.FORECAST)
    }
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = weatherForecastModel.timezone,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = weatherForecastModel.current.description,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        HomeTabs(
            selectedTab = selectedTab,
            onUiEvent = onUiEvent
        ) {
            selectedTab = it
        }

        if (selectedTab == HomeTab.FORECAST) {
            ForecastScreen(
                weatherForecastModel,
                capturedImageUri,
                onUiEvent,
                onCameraClicked
            )
        } else {
            HistoryScreen(imagesHistory)
        }
    }

}

@Composable
fun ForecastScreen(
    weatherForecastModel: WeatherForecastModel,
    capturedImageUri: String?,
    onUiEvent: (ForecastUiEvent) -> Unit,
    onCameraClicked: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    val context = LocalContext.current
    var showSnackBar by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.camera_bg),
                    contentDescription = ""
                )
                Icon(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = CircleShape
                        )
                        .padding(8.dp)
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clickable {
                            if (capturedImageUri == null)
                                onCameraClicked()
                            else {
                                coroutineScope.launch {
                                    val uri = graphicsLayer.toImageBitmap().asAndroidBitmap()
                                        .saveToDisk(context).toString()
                                    onUiEvent(ForecastUiEvent.ImageSaved(uri))
                                    showSnackBar = true

                                }
                            }
                        },
                    painter = if (capturedImageUri == null)
                        painterResource(R.drawable.camera) else
                        painterResource(R.drawable.save_image),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(paddingValues)
                .drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                }
        ) {
            capturedImageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(Uri.parse(it)),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = weatherForecastModel.current.temperature,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontSize = 60.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    AsyncImage(
                        model = weatherForecastModel.current.icon,
                        modifier = Modifier.size(100.dp),
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(weatherForecastModel.hourly) { hourlyForecast ->
                            Column(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.inverseSurface,
                                        shape = CircleShape
                                    )
                                    .padding(horizontal = 8.dp, vertical = 24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Text(
                                    text = hourlyForecast.time,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                AsyncImage(
                                    model = hourlyForecast.icon,
                                    modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = CircleShape
                                        )
                                        .padding(8.dp)
                                        .size(24.dp),
                                    contentDescription = ""
                                )

                                Text(
                                    text = hourlyForecast.temperature,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        WeatherStatusCard(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(R.drawable.humidity),
                            description = stringResource(
                                R.string.humidity,
                                weatherForecastModel.current.humidity
                            )
                        )
                        WeatherStatusCard(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(R.drawable.wind_speed),
                            description = stringResource(
                                R.string.wind_speed,
                                weatherForecastModel.current.windSpeed
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        WeatherStatusCard(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(R.drawable.sun),
                            description = weatherForecastModel.current.uvi.value
                        )
                        WeatherStatusCard(
                            modifier = Modifier.weight(1f),
                            icon = painterResource(R.drawable.pressure),
                            description = stringResource(
                                R.string.pressure,
                                weatherForecastModel.current.airPressure
                            )
                        )
                    }
                }
            }
            SnackBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                message = stringResource(R.string.image_saved_to_files),
                showSb = showSnackBar
            ) {
                showSnackBar = it
            }
        }
    }
}

@Composable
fun HistoryScreen(imagesHistory: List<String>?) {
    imagesHistory?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(imagesHistory) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        painter = rememberAsyncImagePainter(Uri.parse(it)),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "No Captured Images Found",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }

}

@Composable
fun HomeTabs(
    selectedTab: HomeTab,
    onUiEvent: (ForecastUiEvent) -> Unit,
    onTabSelected: (HomeTab) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                shape = MaterialTheme.shapes.large
            )
    ) {
        Row {
            Text(
                modifier = Modifier
                    .then(
                        if (selectedTab == HomeTab.FORECAST)
                            Modifier.background(
                                color = MaterialTheme.colorScheme.inverseSurface,
                                shape = MaterialTheme.shapes.large
                            )
                        else
                            Modifier
                    )
                    .clickable { onTabSelected(HomeTab.FORECAST) }
                    .padding(16.dp),
                text = stringResource(R.string.forecast),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                modifier = Modifier
                    .then(
                        if (selectedTab == HomeTab.HISTORY)
                            Modifier.background(
                                color = MaterialTheme.colorScheme.inverseSurface,
                                shape = MaterialTheme.shapes.large
                            )
                        else
                            Modifier
                    )
                    .clickable {
                        onTabSelected(HomeTab.HISTORY)
                        onUiEvent(ForecastUiEvent.HistorySelected)
                    }
                    .padding(16.dp),
                text = stringResource(R.string.history),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
private fun WeatherStatusCard(
    modifier: Modifier,
    networkIcon: String? = null,
    icon: Painter? = null,
    description: String
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.primary
                    ),
                    start = Offset(0.0f, 20.0f),
                    end = Offset(0.0f, 100.0f),

                    ),
                shape = CircleShape
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        networkIcon?.let {
            AsyncImage(
                modifier = Modifier.size(32.dp),
                model = it,
                contentDescription = ""
            )
        }
        icon?.let {
            Image(
                modifier = Modifier.size(32.dp),
                painter = icon,
                contentDescription = ""
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, onSuccess: (Location) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        location?.let { onSuccess(it) }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    AppTheme {
        WeatherScreen(
            ForecastUiState.Success(
                WeatherForecastModel(
                    timezone = "Yogyakarta",
                    current = CurrentWeatherForecast(
                        currentStatus = "Rainy",
                        description = "Heavy Rain",
                        temperature = "30",
                        icon = "",
                        humidity = "20",
                        visibility = 100,
                        windSpeed = 30,
                        uvi = UV.LOW,
                        airPressure = "50"
                    ),
                    hourly = listOf(
                        HourlyForecast(
                            time = "7 PM",
                            icon = "",
                            temperature = "22"
                        ), HourlyForecast(
                            time = "7 PM",
                            icon = "",
                            temperature = "22"
                        ),
                        HourlyForecast(
                            time = "7 PM",
                            icon = "",
                            temperature = "22"
                        ),
                        HourlyForecast(
                            time = "7 PM",
                            icon = "",
                            temperature = "22"
                        ),
                        HourlyForecast(
                            time = "7 PM",
                            icon = "",
                            temperature = "22"
                        )
                    )
                )
            ),
            capturedImageUri = null, imagesHistory = listOf("", "", ""), {}) {}
    }
}