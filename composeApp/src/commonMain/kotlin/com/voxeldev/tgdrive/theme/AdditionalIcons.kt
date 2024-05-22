package com.voxeldev.tgdrive.theme

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * This object contains icons copy-pasted from the
 * Material Icons Extended library
 * @author nvoxel
 */
internal object AdditionalIcons {

    private var _filterList: ImageVector? = null
    val FilterList: ImageVector
        get() {
            if (_filterList != null) {
                return _filterList!!
            }
            _filterList = materialIcon(name = "Filled.FilterList") {
                materialPath {
                    moveTo(10.0f, 18.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(-4.0f)
                    verticalLineToRelative(2.0f)
                    close()
                    moveTo(3.0f, 6.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(18.0f)
                    lineTo(21.0f, 6.0f)
                    lineTo(3.0f, 6.0f)
                    close()
                    moveTo(6.0f, 13.0f)
                    horizontalLineToRelative(12.0f)
                    verticalLineToRelative(-2.0f)
                    lineTo(6.0f, 11.0f)
                    verticalLineToRelative(2.0f)
                    close()
                }
            }
            return _filterList!!
        }

    private var _restartAlt: ImageVector? = null
    val RestartAlt: ImageVector
        get() {
            if (_restartAlt != null) {
                return _restartAlt!!
            }
            _restartAlt = materialIcon(name = "Filled.RestartAlt") {
                materialPath {
                    moveTo(12.0f, 5.0f)
                    verticalLineTo(2.0f)
                    lineTo(8.0f, 6.0f)
                    lineToRelative(4.0f, 4.0f)
                    verticalLineTo(7.0f)
                    curveToRelative(3.31f, 0.0f, 6.0f, 2.69f, 6.0f, 6.0f)
                    curveToRelative(0.0f, 2.97f, -2.17f, 5.43f, -5.0f, 5.91f)
                    verticalLineToRelative(2.02f)
                    curveToRelative(3.95f, -0.49f, 7.0f, -3.85f, 7.0f, -7.93f)
                    curveTo(20.0f, 8.58f, 16.42f, 5.0f, 12.0f, 5.0f)
                    close()
                }
                materialPath {
                    moveTo(6.0f, 13.0f)
                    curveToRelative(0.0f, -1.65f, 0.67f, -3.15f, 1.76f, -4.24f)
                    lineTo(6.34f, 7.34f)
                    curveTo(4.9f, 8.79f, 4.0f, 10.79f, 4.0f, 13.0f)
                    curveToRelative(0.0f, 4.08f, 3.05f, 7.44f, 7.0f, 7.93f)
                    verticalLineToRelative(-2.02f)
                    curveTo(8.17f, 18.43f, 6.0f, 15.97f, 6.0f, 13.0f)
                    close()
                }
            }
            return _restartAlt!!
        }

    private var _visibility: ImageVector? = null
    val Visibility: ImageVector
        get() {
            if (_visibility != null) {
                return _visibility!!
            }
            _visibility = materialIcon(name = "Filled.Visibility") {
                materialPath {
                    moveTo(12.0f, 4.5f)
                    curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
                    curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                    reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
                    curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                    close()
                    moveTo(12.0f, 17.0f)
                    curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                    reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
                    reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
                    reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
                    close()
                    moveTo(12.0f, 9.0f)
                    curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                    reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                    reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
                    reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                    close()
                }
            }
            return _visibility!!
        }

    private var _visibilityOff: ImageVector? = null
    val VisibilityOff: ImageVector
        get() {
            if (_visibilityOff != null) {
                return _visibilityOff!!
            }
            _visibilityOff = materialIcon(name = "Filled.VisibilityOff") {
                materialPath {
                    moveTo(12.0f, 7.0f)
                    curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
                    curveToRelative(0.0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
                    lineToRelative(2.92f, 2.92f)
                    curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
                    curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
                    curveToRelative(-1.4f, 0.0f, -2.74f, 0.25f, -3.98f, 0.7f)
                    lineToRelative(2.16f, 2.16f)
                    curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
                    close()
                    moveTo(2.0f, 4.27f)
                    lineToRelative(2.28f, 2.28f)
                    lineToRelative(0.46f, 0.46f)
                    curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1.0f, 12.0f)
                    curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
                    curveToRelative(1.55f, 0.0f, 3.03f, -0.3f, 4.38f, -0.84f)
                    lineToRelative(0.42f, 0.42f)
                    lineTo(19.73f, 22.0f)
                    lineTo(21.0f, 20.73f)
                    lineTo(3.27f, 3.0f)
                    lineTo(2.0f, 4.27f)
                    close()
                    moveTo(7.53f, 9.8f)
                    lineToRelative(1.55f, 1.55f)
                    curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                    curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
                    curveToRelative(0.22f, 0.0f, 0.44f, -0.03f, 0.65f, -0.08f)
                    lineToRelative(1.55f, 1.55f)
                    curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
                    curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
                    curveToRelative(0.0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
                    close()
                    moveTo(11.84f, 9.02f)
                    lineToRelative(3.15f, 3.15f)
                    lineToRelative(0.02f, -0.16f)
                    curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
                    lineToRelative(-0.17f, 0.01f)
                    close()
                }
            }
            return _visibilityOff!!
        }

    private var _star: ImageVector? = null

    val Star: ImageVector
        get() {
            if (_star != null) {
                return _star!!
            }
            _star = materialIcon(name = "Outlined.StarOutline") {
                materialPath {
                    moveTo(22.0f, 9.24f)
                    lineToRelative(-7.19f, -0.62f)
                    lineTo(12.0f, 2.0f)
                    lineTo(9.19f, 8.63f)
                    lineTo(2.0f, 9.24f)
                    lineToRelative(5.46f, 4.73f)
                    lineTo(5.82f, 21.0f)
                    lineTo(12.0f, 17.27f)
                    lineTo(18.18f, 21.0f)
                    lineToRelative(-1.63f, -7.03f)
                    lineTo(22.0f, 9.24f)
                    close()
                    moveTo(12.0f, 15.4f)
                    lineToRelative(-3.76f, 2.27f)
                    lineToRelative(1.0f, -4.28f)
                    lineToRelative(-3.32f, -2.88f)
                    lineToRelative(4.38f, -0.38f)
                    lineTo(12.0f, 6.1f)
                    lineToRelative(1.71f, 4.04f)
                    lineToRelative(4.38f, 0.38f)
                    lineToRelative(-3.32f, 2.88f)
                    lineToRelative(1.0f, 4.28f)
                    lineTo(12.0f, 15.4f)
                    close()
                }
            }
            return _star!!
        }

    private var _starFilled: ImageVector? = null

    val StarFilled: ImageVector
        get() {
            if (_starFilled != null) {
                return _starFilled!!
            }
            _starFilled = materialIcon(name = "Filled.Star") {
                materialPath {
                    moveTo(12.0f, 17.27f)
                    lineTo(18.18f, 21.0f)
                    lineToRelative(-1.64f, -7.03f)
                    lineTo(22.0f, 9.24f)
                    lineToRelative(-7.19f, -0.61f)
                    lineTo(12.0f, 2.0f)
                    lineTo(9.19f, 8.63f)
                    lineTo(2.0f, 9.24f)
                    lineToRelative(5.46f, 4.73f)
                    lineTo(5.82f, 21.0f)
                    close()
                }
            }
            return _starFilled!!
        }

    private var _folder: ImageVector? = null


    val Folder: ImageVector
        get() {
            if (_folder != null) {
                return _folder!!
            }
            _folder = materialIcon(name = "Outlined.Folder") {
                materialPath {
                    moveTo(9.17f, 6.0f)
                    lineToRelative(2.0f, 2.0f)
                    horizontalLineTo(20.0f)
                    verticalLineToRelative(10.0f)
                    horizontalLineTo(4.0f)
                    verticalLineTo(6.0f)
                    horizontalLineToRelative(5.17f)
                    moveTo(10.0f, 4.0f)
                    horizontalLineTo(4.0f)
                    curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                    lineTo(2.0f, 18.0f)
                    curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                    horizontalLineToRelative(16.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    verticalLineTo(8.0f)
                    curveToRelative(0.0f, -1.1f, -0.9f, -2.0f, -2.0f, -2.0f)
                    horizontalLineToRelative(-8.0f)
                    lineToRelative(-2.0f, -2.0f)
                    close()
                }
            }
            return _folder!!
        }

    private var _settings: ImageVector? = null

    val Settings: ImageVector
        get() {
            if (_settings != null) {
                return _settings!!
            }
            _settings = materialIcon(name = "Outlined.Settings") {
                materialPath {
                    moveTo(19.43f, 12.98f)
                    curveToRelative(0.04f, -0.32f, 0.07f, -0.64f, 0.07f, -0.98f)
                    curveToRelative(0.0f, -0.34f, -0.03f, -0.66f, -0.07f, -0.98f)
                    lineToRelative(2.11f, -1.65f)
                    curveToRelative(0.19f, -0.15f, 0.24f, -0.42f, 0.12f, -0.64f)
                    lineToRelative(-2.0f, -3.46f)
                    curveToRelative(-0.09f, -0.16f, -0.26f, -0.25f, -0.44f, -0.25f)
                    curveToRelative(-0.06f, 0.0f, -0.12f, 0.01f, -0.17f, 0.03f)
                    lineToRelative(-2.49f, 1.0f)
                    curveToRelative(-0.52f, -0.4f, -1.08f, -0.73f, -1.69f, -0.98f)
                    lineToRelative(-0.38f, -2.65f)
                    curveTo(14.46f, 2.18f, 14.25f, 2.0f, 14.0f, 2.0f)
                    horizontalLineToRelative(-4.0f)
                    curveToRelative(-0.25f, 0.0f, -0.46f, 0.18f, -0.49f, 0.42f)
                    lineToRelative(-0.38f, 2.65f)
                    curveToRelative(-0.61f, 0.25f, -1.17f, 0.59f, -1.69f, 0.98f)
                    lineToRelative(-2.49f, -1.0f)
                    curveToRelative(-0.06f, -0.02f, -0.12f, -0.03f, -0.18f, -0.03f)
                    curveToRelative(-0.17f, 0.0f, -0.34f, 0.09f, -0.43f, 0.25f)
                    lineToRelative(-2.0f, 3.46f)
                    curveToRelative(-0.13f, 0.22f, -0.07f, 0.49f, 0.12f, 0.64f)
                    lineToRelative(2.11f, 1.65f)
                    curveToRelative(-0.04f, 0.32f, -0.07f, 0.65f, -0.07f, 0.98f)
                    curveToRelative(0.0f, 0.33f, 0.03f, 0.66f, 0.07f, 0.98f)
                    lineToRelative(-2.11f, 1.65f)
                    curveToRelative(-0.19f, 0.15f, -0.24f, 0.42f, -0.12f, 0.64f)
                    lineToRelative(2.0f, 3.46f)
                    curveToRelative(0.09f, 0.16f, 0.26f, 0.25f, 0.44f, 0.25f)
                    curveToRelative(0.06f, 0.0f, 0.12f, -0.01f, 0.17f, -0.03f)
                    lineToRelative(2.49f, -1.0f)
                    curveToRelative(0.52f, 0.4f, 1.08f, 0.73f, 1.69f, 0.98f)
                    lineToRelative(0.38f, 2.65f)
                    curveToRelative(0.03f, 0.24f, 0.24f, 0.42f, 0.49f, 0.42f)
                    horizontalLineToRelative(4.0f)
                    curveToRelative(0.25f, 0.0f, 0.46f, -0.18f, 0.49f, -0.42f)
                    lineToRelative(0.38f, -2.65f)
                    curveToRelative(0.61f, -0.25f, 1.17f, -0.59f, 1.69f, -0.98f)
                    lineToRelative(2.49f, 1.0f)
                    curveToRelative(0.06f, 0.02f, 0.12f, 0.03f, 0.18f, 0.03f)
                    curveToRelative(0.17f, 0.0f, 0.34f, -0.09f, 0.43f, -0.25f)
                    lineToRelative(2.0f, -3.46f)
                    curveToRelative(0.12f, -0.22f, 0.07f, -0.49f, -0.12f, -0.64f)
                    lineToRelative(-2.11f, -1.65f)
                    close()
                    moveTo(17.45f, 11.27f)
                    curveToRelative(0.04f, 0.31f, 0.05f, 0.52f, 0.05f, 0.73f)
                    curveToRelative(0.0f, 0.21f, -0.02f, 0.43f, -0.05f, 0.73f)
                    lineToRelative(-0.14f, 1.13f)
                    lineToRelative(0.89f, 0.7f)
                    lineToRelative(1.08f, 0.84f)
                    lineToRelative(-0.7f, 1.21f)
                    lineToRelative(-1.27f, -0.51f)
                    lineToRelative(-1.04f, -0.42f)
                    lineToRelative(-0.9f, 0.68f)
                    curveToRelative(-0.43f, 0.32f, -0.84f, 0.56f, -1.25f, 0.73f)
                    lineToRelative(-1.06f, 0.43f)
                    lineToRelative(-0.16f, 1.13f)
                    lineToRelative(-0.2f, 1.35f)
                    horizontalLineToRelative(-1.4f)
                    lineToRelative(-0.19f, -1.35f)
                    lineToRelative(-0.16f, -1.13f)
                    lineToRelative(-1.06f, -0.43f)
                    curveToRelative(-0.43f, -0.18f, -0.83f, -0.41f, -1.23f, -0.71f)
                    lineToRelative(-0.91f, -0.7f)
                    lineToRelative(-1.06f, 0.43f)
                    lineToRelative(-1.27f, 0.51f)
                    lineToRelative(-0.7f, -1.21f)
                    lineToRelative(1.08f, -0.84f)
                    lineToRelative(0.89f, -0.7f)
                    lineToRelative(-0.14f, -1.13f)
                    curveToRelative(-0.03f, -0.31f, -0.05f, -0.54f, -0.05f, -0.74f)
                    reflectiveCurveToRelative(0.02f, -0.43f, 0.05f, -0.73f)
                    lineToRelative(0.14f, -1.13f)
                    lineToRelative(-0.89f, -0.7f)
                    lineToRelative(-1.08f, -0.84f)
                    lineToRelative(0.7f, -1.21f)
                    lineToRelative(1.27f, 0.51f)
                    lineToRelative(1.04f, 0.42f)
                    lineToRelative(0.9f, -0.68f)
                    curveToRelative(0.43f, -0.32f, 0.84f, -0.56f, 1.25f, -0.73f)
                    lineToRelative(1.06f, -0.43f)
                    lineToRelative(0.16f, -1.13f)
                    lineToRelative(0.2f, -1.35f)
                    horizontalLineToRelative(1.39f)
                    lineToRelative(0.19f, 1.35f)
                    lineToRelative(0.16f, 1.13f)
                    lineToRelative(1.06f, 0.43f)
                    curveToRelative(0.43f, 0.18f, 0.83f, 0.41f, 1.23f, 0.71f)
                    lineToRelative(0.91f, 0.7f)
                    lineToRelative(1.06f, -0.43f)
                    lineToRelative(1.27f, -0.51f)
                    lineToRelative(0.7f, 1.21f)
                    lineToRelative(-1.07f, 0.85f)
                    lineToRelative(-0.89f, 0.7f)
                    lineToRelative(0.14f, 1.13f)
                    close()
                    moveTo(12.0f, 8.0f)
                    curveToRelative(-2.21f, 0.0f, -4.0f, 1.79f, -4.0f, 4.0f)
                    reflectiveCurveToRelative(1.79f, 4.0f, 4.0f, 4.0f)
                    reflectiveCurveToRelative(4.0f, -1.79f, 4.0f, -4.0f)
                    reflectiveCurveToRelative(-1.79f, -4.0f, -4.0f, -4.0f)
                    close()
                    moveTo(12.0f, 14.0f)
                    curveToRelative(-1.1f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
                    reflectiveCurveToRelative(0.9f, -2.0f, 2.0f, -2.0f)
                    reflectiveCurveToRelative(2.0f, 0.9f, 2.0f, 2.0f)
                    reflectiveCurveToRelative(-0.9f, 2.0f, -2.0f, 2.0f)
                    close()
                }
            }
            return _settings!!
        }

    private var _selectUploadFile: ImageVector? = null

    val SelectUploadFile: ImageVector
        get() {
            if (_selectUploadFile != null) {
                return _selectUploadFile!!
            }
            _selectUploadFile = materialIcon(name = "Filled.SelectUploadFile") {
                materialPath {
                    moveTo(14.0f, 2.0f)
                    lineTo(6.0f, 2.0f)
                    curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                    lineTo(4.0f, 20.0f)
                    curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 1.99f, 2.0f)
                    lineTo(18.0f, 22.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    lineTo(20.0f, 8.0f)
                    lineToRelative(-6.0f, -6.0f)
                    close()
                    moveTo(18.0f, 20.0f)
                    lineTo(6.0f, 20.0f)
                    lineTo(6.0f, 4.0f)
                    horizontalLineToRelative(7.0f)
                    verticalLineToRelative(5.0f)
                    horizontalLineToRelative(5.0f)
                    verticalLineToRelative(11.0f)
                    close()
                    moveTo(8.0f, 15.01f)
                    lineToRelative(1.41f, 1.41f)
                    lineTo(11.0f, 14.84f)
                    lineTo(11.0f, 19.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(-4.16f)
                    lineToRelative(1.59f, 1.59f)
                    lineTo(16.0f, 15.01f)
                    lineTo(12.01f, 11.0f)
                    close()
                }
            }
            return _selectUploadFile!!
        }

    private var _upload: ImageVector? = null

    val Upload: ImageVector
        get() {
            if (_upload != null) {
                return _upload!!
            }
            _upload = materialIcon(name = "Filled.Upload") {
                materialPath {
                    moveTo(5.0f, 20.0f)
                    horizontalLineToRelative(14.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineTo(5.0f)
                    verticalLineTo(20.0f)
                    close()
                    moveTo(5.0f, 10.0f)
                    horizontalLineToRelative(4.0f)
                    verticalLineToRelative(6.0f)
                    horizontalLineToRelative(6.0f)
                    verticalLineToRelative(-6.0f)
                    horizontalLineToRelative(4.0f)
                    lineToRelative(-7.0f, -7.0f)
                    lineTo(5.0f, 10.0f)
                    close()
                }
            }
            return _upload!!
        }

    private var _arrowBack: ImageVector? = null

    val ArrowBack: ImageVector
        get() {
            if (_arrowBack != null) {
                return _arrowBack!!
            }
            _arrowBack = materialIcon(name = "AutoMirrored.Filled.ArrowBack", autoMirror = true) {
                materialPath {
                    moveTo(20.0f, 11.0f)
                    horizontalLineTo(7.83f)
                    lineToRelative(5.59f, -5.59f)
                    lineTo(12.0f, 4.0f)
                    lineToRelative(-8.0f, 8.0f)
                    lineToRelative(8.0f, 8.0f)
                    lineToRelative(1.41f, -1.41f)
                    lineTo(7.83f, 13.0f)
                    horizontalLineTo(20.0f)
                    verticalLineToRelative(-2.0f)
                    close()
                }
            }
            return _arrowBack!!
        }

    private var _moreVert: ImageVector? = null

    val MoreVert: ImageVector
        get() {
            if (_moreVert != null) {
                return _moreVert!!
            }
            _moreVert = materialIcon(name = "Filled.MoreVert") {
                materialPath {
                    moveTo(12.0f, 8.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                    reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                    reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                    close()
                    moveTo(12.0f, 10.0f)
                    curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                    reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                    reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                    reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                    close()
                    moveTo(12.0f, 16.0f)
                    curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                    reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                    reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                    reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                    close()
                }
            }
            return _moreVert!!
        }

    private var _edit: ImageVector? = null

    val Edit: ImageVector
        get() {
            if (_edit != null) {
                return _edit!!
            }
            _edit = materialIcon(name = "Filled.Edit") {
                materialPath {
                    moveTo(3.0f, 17.25f)
                    verticalLineTo(21.0f)
                    horizontalLineToRelative(3.75f)
                    lineTo(17.81f, 9.94f)
                    lineToRelative(-3.75f, -3.75f)
                    lineTo(3.0f, 17.25f)
                    close()
                    moveTo(20.71f, 7.04f)
                    curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                    lineToRelative(-2.34f, -2.34f)
                    curveToRelative(-0.39f, -0.39f, -1.02f, -0.39f, -1.41f, 0.0f)
                    lineToRelative(-1.83f, 1.83f)
                    lineToRelative(3.75f, 3.75f)
                    lineToRelative(1.83f, -1.83f)
                    close()
                }
            }
            return _edit!!
        }

    private var _share: ImageVector? = null

    val Share: ImageVector
        get() {
            if (_share != null) {
                return _share!!
            }
            _share = materialIcon(name = "Filled.Share") {
                materialPath {
                    moveTo(18.0f, 16.08f)
                    curveToRelative(-0.76f, 0.0f, -1.44f, 0.3f, -1.96f, 0.77f)
                    lineTo(8.91f, 12.7f)
                    curveToRelative(0.05f, -0.23f, 0.09f, -0.46f, 0.09f, -0.7f)
                    reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
                    lineToRelative(7.05f, -4.11f)
                    curveToRelative(0.54f, 0.5f, 1.25f, 0.81f, 2.04f, 0.81f)
                    curveToRelative(1.66f, 0.0f, 3.0f, -1.34f, 3.0f, -3.0f)
                    reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
                    reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
                    curveToRelative(0.0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
                    lineTo(8.04f, 9.81f)
                    curveTo(7.5f, 9.31f, 6.79f, 9.0f, 6.0f, 9.0f)
                    curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
                    reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
                    curveToRelative(0.79f, 0.0f, 1.5f, -0.31f, 2.04f, -0.81f)
                    lineToRelative(7.12f, 4.16f)
                    curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
                    curveToRelative(0.0f, 1.61f, 1.31f, 2.92f, 2.92f, 2.92f)
                    curveToRelative(1.61f, 0.0f, 2.92f, -1.31f, 2.92f, -2.92f)
                    reflectiveCurveToRelative(-1.31f, -2.92f, -2.92f, -2.92f)
                    close()
                }
            }
            return _share!!
        }

    private var _deleteForever: ImageVector? = null

    val DeleteForever: ImageVector
        get() {
            if (_deleteForever != null) {
                return _deleteForever!!
            }
            _deleteForever = materialIcon(name = "Filled.DeleteForever") {
                materialPath {
                    moveTo(6.0f, 19.0f)
                    curveToRelative(0.0f, 1.1f, 0.9f, 2.0f, 2.0f, 2.0f)
                    horizontalLineToRelative(8.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    lineTo(18.0f, 7.0f)
                    lineTo(6.0f, 7.0f)
                    verticalLineToRelative(12.0f)
                    close()
                    moveTo(8.46f, 11.88f)
                    lineToRelative(1.41f, -1.41f)
                    lineTo(12.0f, 12.59f)
                    lineToRelative(2.12f, -2.12f)
                    lineToRelative(1.41f, 1.41f)
                    lineTo(13.41f, 14.0f)
                    lineToRelative(2.12f, 2.12f)
                    lineToRelative(-1.41f, 1.41f)
                    lineTo(12.0f, 15.41f)
                    lineToRelative(-2.12f, 2.12f)
                    lineToRelative(-1.41f, -1.41f)
                    lineTo(10.59f, 14.0f)
                    lineToRelative(-2.13f, -2.12f)
                    close()
                    moveTo(15.5f, 4.0f)
                    lineToRelative(-1.0f, -1.0f)
                    horizontalLineToRelative(-5.0f)
                    lineToRelative(-1.0f, 1.0f)
                    lineTo(5.0f, 4.0f)
                    verticalLineToRelative(2.0f)
                    horizontalLineToRelative(14.0f)
                    lineTo(19.0f, 4.0f)
                    close()
                }
            }
            return _deleteForever!!
        }

    private var _add: ImageVector? = null

    val Add: ImageVector
        get() {
            if (_add != null) {
                return _add!!
            }
            _add = materialIcon(name = "Filled.Add") {
                materialPath {
                    moveTo(19.0f, 13.0f)
                    horizontalLineToRelative(-6.0f)
                    verticalLineToRelative(6.0f)
                    horizontalLineToRelative(-2.0f)
                    verticalLineToRelative(-6.0f)
                    horizontalLineTo(5.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(6.0f)
                    verticalLineTo(5.0f)
                    horizontalLineToRelative(2.0f)
                    verticalLineToRelative(6.0f)
                    horizontalLineToRelative(6.0f)
                    verticalLineToRelative(2.0f)
                    close()
                }
            }
            return _add!!
        }

    private var _description: ImageVector? = null

    val Description: ImageVector
        get() {
            if (_description != null) {
                return _description!!
            }
            _description = materialIcon(name = "Filled.Description") {
                materialPath {
                    moveTo(14.0f, 2.0f)
                    lineTo(6.0f, 2.0f)
                    curveToRelative(-1.1f, 0.0f, -1.99f, 0.9f, -1.99f, 2.0f)
                    lineTo(4.0f, 20.0f)
                    curveToRelative(0.0f, 1.1f, 0.89f, 2.0f, 1.99f, 2.0f)
                    lineTo(18.0f, 22.0f)
                    curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                    lineTo(20.0f, 8.0f)
                    lineToRelative(-6.0f, -6.0f)
                    close()
                    moveTo(16.0f, 18.0f)
                    lineTo(8.0f, 18.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(8.0f)
                    verticalLineToRelative(2.0f)
                    close()
                    moveTo(16.0f, 14.0f)
                    lineTo(8.0f, 14.0f)
                    verticalLineToRelative(-2.0f)
                    horizontalLineToRelative(8.0f)
                    verticalLineToRelative(2.0f)
                    close()
                    moveTo(13.0f, 9.0f)
                    lineTo(13.0f, 3.5f)
                    lineTo(18.5f, 9.0f)
                    lineTo(13.0f, 9.0f)
                    close()
                }
            }
            return _description!!
        }
}
