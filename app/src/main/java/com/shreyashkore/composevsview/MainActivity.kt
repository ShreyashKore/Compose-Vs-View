package com.shreyashkore.composevsview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.shreyashkore.composevsview.data.FAKE_PROFILES
import com.shreyashkore.composevsview.data.Profile
import com.shreyashkore.composevsview.ui.theme.ComposeVsViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeVsViewTheme {
                HomeScreen(
                    onProfileClick = this@MainActivity::onProfileClick,
                    profiles = FAKE_PROFILES
                )
            }
        }
    }

    private fun onProfileClick(profile: Profile) {
        Toast.makeText(this, "${profile.name}'s profile clicked!", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    onProfileClick: (profile: Profile) -> Unit,
    profiles: List<Profile>
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {

        LazyColumn(Modifier.padding(it).semantics { testTagsAsResourceId = true }.testTag("lazy_column")) {
            itemsIndexed(profiles) { i, profile ->
                ProfileItem(
                    onClick = { onProfileClick(profile) },
                    profile = profile,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(
    profile: Profile,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(6.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = profile.image,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(54.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsyncImage(
                        model = profile.companyImage,
                        contentDescription = "Company Image",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = profile.company,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                Text(
                    text = profile.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = profile.phone,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeVsViewTheme {
        HomeScreen({}, FAKE_PROFILES)
    }
}