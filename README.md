# ViewToBitmap
View To Bitmap
### 何谓沉浸式状态栏

简单来说，沉浸式状态栏本质上是要给系统状态栏着色，当这个颜色和我们*Activity*中的*ToolBar*或者*ActionBar*所使用的颜色一致时就会有沉浸式的效果咯；
那么我们应该怎么才能给系统状态栏着色呢？

### 怎么给状态栏着色

谷歌后知后觉，在 *API21* 中终于添加了相应的方法
```java
/**
For this to take effect,the window must be drawing the system bar backgrounds with 
android.view.WindowManager.LayoutParams#FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS and 
android.view.WindowManager.LayoutParams#FLAG_TRANSLUCENT_STATUS must not be set
**/
setStatusColor(int color)
```
