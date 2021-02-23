package org.keremcomert.nasacrawler.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * This is the boilerplate class to define the app as a whole. Hilt will use this to trigger
 * component generation.
 */
@HiltAndroidApp
class NasaCrawlerApp: Application()