package org.keremcomert.nasacrawler.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import org.keremcomert.nasacrawler.api.Repository
import org.keremcomert.nasacrawler.util.CURRENT_ROVER
import org.keremcomert.nasacrawler.util.ROVER_CURIOSITY

/**
 * Each photo has its view model, defined to keep in line with the MVVM Architecture Pattern.
 * This provides safety in cases of app's variables being killed in Fragment's lifecycles and
 * low memory conditions. It also creates another layer between the view and the data, as it would
 * be bad practice to let Fragment handle the state of the data. As Google advises, the Fragment and
 * Activity classes should only be concerned with user interactions.
 * @param repository is provided to access the middle-ware to fetch data
 * @param state is provided by Android's lifecycle methods, and makes sure that the livedata remains
 * safe when there is a change in state (such as app being on background or screen orientation changes.)
 * The Assisted annotation is used, since sometimes Hilt handles the data injection, and sometimes it is handled
 * by the user, hence the name Asisted.
 */
class PhotoViewModel @ViewModelInject constructor(private val repository: Repository,
                                                    @Assisted state: SavedStateHandle
): ViewModel() {

    private val currentRover = state.getLiveData(CURRENT_ROVER, ROVER_CURIOSITY)

    /**
     * Retrieve the results from the middle-ware, by using liveData's switchMap() method.
     * switchMap() is used to make sure that the data change happens when there is at least one observer,
     * in the app's case that is the adapter that fills the rvPhotos.
     */
    val photos = currentRover.switchMap { repository.getResults(it!!).cachedIn(viewModelScope) }
}