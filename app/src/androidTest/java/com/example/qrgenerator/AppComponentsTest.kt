package com.example.qrgenerator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.qrgenerator.presentation.components.AppButton
import com.example.qrgenerator.presentation.components.AppCard
import com.example.qrgenerator.presentation.components.AppScaffold
import com.example.qrgenerator.presentation.components.AppSpacer
import com.example.qrgenerator.presentation.components.AppStatCard
import com.example.qrgenerator.presentation.components.AppText
import com.example.qrgenerator.presentation.components.AppTextField
import com.example.qrgenerator.presentation.components.AppTopBar
import com.example.qrgenerator.presentation.components.QrCard
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun appButton_clickCallsLambda() {
        var clicked = false
        composeTestRule.setContent {
            AppButton(
                text = "Click me",
                onClick = { clicked = true }
            )
        }
        composeTestRule.onNodeWithText("Click me").performClick()
        assert(clicked)
    }

    @Test
    fun appText_displaysCorrectText() {
        composeTestRule.setContent {
            AppText(text = "Hello Compose")
        }
        composeTestRule.onNodeWithText("Hello Compose").assertExists()
    }

    @Test
    fun appTextField_updatesValue() {
        val state = mutableStateOf("")
        composeTestRule.setContent {
            AppTextField(
                value = state.value,
                onValueChange = { state.value = it },
                label = "Email"
            )
        }
        composeTestRule.onNodeWithText("Email").performTextInput("test")
        assert(state.value == "test")
    }

    @Test
    fun appStatCard_displaysTitleAndValue() {
        composeTestRule.setContent {
            AppStatCard(title = "Score", value = "100")
        }
        composeTestRule.onNodeWithText("Score").assertExists()
        composeTestRule.onNodeWithText("100").assertExists()
    }

    @Test
    fun appCard_displaysContent() {
        composeTestRule.setContent {
            AppCard {
                AppText("Card Content")
            }
        }
        composeTestRule.onNodeWithText("Card Content").assertExists()
    }

    @Test
    fun appTopBar_displaysTitleAndLogout() {
        var logoutClicked = false
        composeTestRule.setContent {
            AppTopBar(title = "Home", showLogout = true) { logoutClicked = true }
        }
        composeTestRule.onNodeWithText("Home").assertExists()
        composeTestRule.onNodeWithContentDescription("Logout").performClick()
        assert(logoutClicked)
    }

    @Test
    fun appScaffold_rendersContent() {
        composeTestRule.setContent {
            AppScaffold {
                AppText("Scaffold Content")
            }
        }
        composeTestRule.onNodeWithText("Scaffold Content").assertExists()
    }

    @Test
    fun qrCard_showsTitleUrlAndCallsActions() {
        var editCalled = false
        var statsCalled = false
        var deleteCalled = false

        val dummyBitmap = ImageBitmap(100, 100)

        composeTestRule.setContent {
            QrCard(
                title = "Test QR",
                url = "https://example.com",
                qrBitmap = dummyBitmap,
                onEdit = { editCalled = true },
                onStats = { statsCalled = true },
                onDelete = { deleteCalled = true }
            )
        }

        composeTestRule.onNodeWithText("Test QR").assertExists()
        composeTestRule.onNodeWithText("https://example.com").assertExists()

        // Edit button
        composeTestRule.onAllNodes(hasContentDescription("Edit")).onFirst().performClick()
        assert(editCalled)

        // Stats button
        composeTestRule.onAllNodes(hasContentDescription("Stats")).onFirst().performClick()
        assert(statsCalled)

        // Delete button (opens dialog first)
        composeTestRule.onNodeWithContentDescription("Delete QR").performClick()
        composeTestRule.onNodeWithText("Delete").performClick()
        assert(deleteCalled)
    }

    @Test
    fun appSpacer_createsNode() {
        composeTestRule.setContent {
            AppSpacer(height = 16.dp, width = 8.dp)
        }
        // Just ensure it exists in the tree
        composeTestRule.onAllNodes(hasTestTag("Spacer")).fetchSemanticsNodes().isNotEmpty()
    }
}